package com.practice.splitwise.services;

import com.practice.splitwise.data.*;
import com.practice.splitwise.dtos.requests.InsertExpenseDTO;
import com.practice.splitwise.exceptions.ExpenseNotFoundException;
import com.practice.splitwise.repositories.ExpenseRepository;
import com.practice.splitwise.repositories.FriendshipRepository;
import com.practice.splitwise.repositories.GroupParticipantsRepository;
import com.practice.splitwise.repositories.SpenderRepository;
import com.practice.splitwise.utilities.Utilities;

import java.util.*;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.practice.splitwise.utilities.Utilities.*;


@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final PersonService personService;
    private final GroupParticipantsRepository groupParticipantsRepository;
//    private final GroupService groupService;
    private final SpenderRepository spenderRepository;
    private final FriendshipRepository friendshipRepository;

    public ExpenseService(ExpenseRepository expenseRepository, PersonService personService,
                          GroupParticipantsRepository groupParticipantsRepository, SpenderRepository spenderRepository, FriendshipRepository friendshipRepository){
        this.expenseRepository = expenseRepository;
        this.personService = personService;
        this.groupParticipantsRepository = groupParticipantsRepository;
//        this.groupService = groupService;
        this.spenderRepository = spenderRepository;
        this.friendshipRepository = friendshipRepository;
    }

    public Expense getExpenseById(Long expenseId) {
        return expenseRepository.findById(expenseId).orElseThrow(ExpenseNotFoundException::new);
    }

    public List<Expense> getAllExpenses() {
        return Utilities.IterableToList(expenseRepository.findAll());
    }

    public Long insertExpense(Expense expense) {
        return expenseRepository.save(expense).getId();
    }

    public Expense updateExpense(Long id, Expense expense) {
        expense.setId(id);
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> getAllExpensesForPerson(Long personId) {
        Person person = personService.getPersonById(personId);
        return expenseRepository.findByAddedBy(person);
    }

    public Expense getExpenseByIdForPerson(Long personId, Long expenseId) {
        Expense expense = getExpenseById(expenseId);
        if(expense.getAddedBy().longValue() != personId)
            throw new ExpenseNotFoundException();
        return expense;
    }

    public Long insertExpenseToGroup(Long personId, Expense expense) {
        return insertExpenseForPersonGetObject(personId, expense).getId();
    }

    public Expense updateExpenseForPerson(Long personId, Long expenseid, Expense expense) {
        expense.setId(expenseid);
        return insertExpenseForPersonGetObject(personId, expense);
    }

    public void deleteExpenseForPerson(Long personID, Long expenseId) {
        Expense expense = getExpenseByIdForPerson(personID, expenseId);
        expenseRepository.delete(expense);
    }

    private Expense insertExpenseForPersonGetObject(Long personId, Expense expense) {
        Person person = personService.getPersonById(personId);
        expense.setAddedBy(person.getId());
        return expenseRepository.save(expense);
    }

    public Long insertExpenseForPerson(Long personId, Long groupId, Expense expense) {
        Group group = new Group();
        Expense expenseWithAddedBy = insertExpenseForPersonGetObject(personId, expense);
//        group.addExpense(expenseWithAddedBy);
        return expenseWithAddedBy.getId();
    }

    public Long insertExpenseForPerson(InsertExpenseDTO insertExpenseDTO) {
        // unequal division
        // create and save expense object
        Long expenseID = createExpense(insertExpenseDTO);
        // calculation of splits
        Map<Pair<Long,Long>,Double> amountSplitMap = calculateShares(insertExpenseDTO);
        // create and save spender and beneficiary
        createSpenderAndBeneficiary(insertExpenseDTO, expenseID, amountSplitMap);
        // update friendship
        if(Objects.nonNull(insertExpenseDTO.getGroupId())){
            updateFriendshipAndGroupParticipantsForGroup(insertExpenseDTO,amountSplitMap);
        }
        else updateFriendship(insertExpenseDTO.getAmount().getCurrency(), amountSplitMap);
        return expenseID;
    }

    private void updateFriendshipAndGroupParticipantsForGroup(InsertExpenseDTO insertExpenseDTO, Map<Pair<Long, Long>, Double> amountSplitMap) {
        Currency currency = insertExpenseDTO.getAmount().getCurrency();
        Optional<List<GroupParticipants>> groupParticipantsOp = groupParticipantsRepository.findByGroupId(insertExpenseDTO.getGroupId());
        if(!groupParticipantsOp.isPresent()){
            //:TODO
            return;
        }
        List<GroupParticipants> groupParticipants = groupParticipantsOp.get();
        List<Long> groupParticipantsId = groupParticipants.stream().map(GroupParticipants::getParticipant).collect(Collectors.toList());
        Set<Long> mergedUniqueList = Stream.concat(insertExpenseDTO.getSpenderList().stream(), insertExpenseDTO.getBeneficiaryList().stream())
                .collect(Collectors.toSet());
        boolean allElementsPresent = new HashSet<>(groupParticipantsId).containsAll(mergedUniqueList);
        if (!allElementsPresent) {
            //:TODO
            return;
        }
        else {
            Map<Long, Double> amountDeltaPerPerson = new HashMap<>();

            for (Map.Entry<Pair<Long,Long>,Double> entry: amountSplitMap.entrySet()){
                // spender 1 2 3   bene 2 3 4 5   amount 60
                // 1-2 5, 1-3 5, 1-4 5, 1-5 5
                // 2-2 5, 2-3 5, 2-4 5, 2-5 5
                amountDeltaPerPerson.put(entry.getKey().getFirst(), amountDeltaPerPerson.getOrDefault(entry.getKey().getFirst(),0.0)-entry.getValue());
                amountDeltaPerPerson.put(entry.getKey().getSecond(), amountDeltaPerPerson.getOrDefault(entry.getKey().getSecond(),0.0)+entry.getValue());

                // update friendship
                Optional<Friendship> friendshipOp = friendshipRepository.getFriendshipBySelfAndFriendAndGroupId(entry.getKey().getFirst(), entry.getKey().getSecond(), insertExpenseDTO.getGroupId());
                if(!friendshipOp.isPresent()){
                    friendshipRepository.save(Friendship.builder()
                            .self(entry.getKey().getFirst())
                            .friend(entry.getKey().getSecond())
                                    .groupId(insertExpenseDTO.getGroupId())
                            .amount(mapAmountToString(entry.getValue(),currency)).build());
                }
                else{
                    Friendship friendship = friendshipOp.get();
                    friendship.setAmount(mapAmountToString(mapStringToAmount(friendship.getAmount()).getValue() + entry.getValue(),currency));
                    friendshipRepository.save(friendship);
                }

            }

            // update participants
            groupParticipantsRepository.saveAll(groupParticipants.stream().map(groupParticipants1 -> updateAmountInGroupParticipants(groupParticipants1, amountDeltaPerPerson, currency)).collect(Collectors.toList()));

        }
    }

    private GroupParticipants updateAmountInGroupParticipants(GroupParticipants groupParticipants, Map<Long, Double> amountDeltaPerPerson, Currency currency) {
        if(amountDeltaPerPerson.containsKey(groupParticipants.getParticipant())){
//            groupParticipants.setAmount();
            groupParticipants.setAmount(mapAmountToString(mapStringToAmount(groupParticipants.getAmount()).getValue() + amountDeltaPerPerson.get(groupParticipants.getParticipant()),currency));
        }
        return groupParticipants;
    }

    private void updateFriendship(Currency currency, Map<Pair<Long, Long>, Double> amountSplitMap) {
        for (Map.Entry<Pair<Long,Long>,Double> entry: amountSplitMap.entrySet()){
            Optional<Friendship> friendshipOp = friendshipRepository.getFriendshipBySelfAndFriend(entry.getKey().getFirst(), entry.getKey().getSecond());
            if(!friendshipOp.isPresent()){
                //TODO:exception
                return;
            }
            Friendship friendship = friendshipOp.get();
            friendship.setAmount(mapAmountToString(mapStringToAmount(friendship.getAmount()).getValue() + entry.getValue(),currency));
            friendshipRepository.save(friendship);
        }
    }

    private void createSpenderAndBeneficiary(InsertExpenseDTO insertExpenseDTO, Long expenseID, Map<Pair<Long, Long>, Double> amountSplitMap) {
        for(Map.Entry<Pair<Long,Long>,Double> entry: amountSplitMap.entrySet()){
            Spender spender = Spender.builder()
                    .expenseId(expenseID)
                    .fromUserId(entry.getKey().getFirst())
                    .toUserId(entry.getKey().getSecond())
                    .groupId(insertExpenseDTO.getGroupId())
                    .amount(mapAmountToString(entry.getValue(), insertExpenseDTO.getAmount().getCurrency()))
                    .build();
            spenderRepository.save(spender);
        }
    }




    private Map<Pair<Long, Long>, Double> calculateShares(InsertExpenseDTO insertExpenseDTO) {
        Map<Pair<Long, Long>, Double> amountSplitMap = new HashMap<>();
        List<Long> spenderList = insertExpenseDTO.getSpenderList();
        List<Long> beneficiaryList = insertExpenseDTO.getBeneficiaryList();
        double amount = insertExpenseDTO.getAmount().getValue();
        double rough = amount/(spenderList.size()*beneficiaryList.size()); //jhol : (solved) extra amount borne by spenders

        for(Long spender: spenderList){
            for(Long beneficiary: beneficiaryList){
                if (!spenderList.contains(beneficiary)) {
                    amountSplitMap.put(Pair.of(spender,beneficiary),getPortionInDouble(rough));
                }
            }
        }

        return amountSplitMap;
    }

    private Long createExpense(InsertExpenseDTO insertExpenseDTO) {
        return expenseRepository.save(Expense.builder()
                .addedBy(insertExpenseDTO.getAddedByPersonId())
                .date(Timestamp.valueOf(LocalDateTime.now()))
                .category(insertExpenseDTO.getCategory())
                .amount(mapAmountToString(insertExpenseDTO.getAmount().getValue(), insertExpenseDTO.getAmount().getCurrency()))
                .groupId(insertExpenseDTO.getGroupId())
                .build()).getId();
    }

//    private Long insertExpenseT

}

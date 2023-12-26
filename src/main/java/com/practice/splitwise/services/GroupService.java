package com.practice.splitwise.services;

import com.practice.splitwise.data.*;
import com.practice.splitwise.dtos.requests.CreateGroupDTO;
import com.practice.splitwise.exceptions.GroupNotFoundException;
import com.practice.splitwise.repositories.FriendshipRepository;
import com.practice.splitwise.repositories.GroupParticipantsRepository;
import com.practice.splitwise.repositories.GroupRepository;
import com.practice.splitwise.utilities.Utilities;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.practice.splitwise.utilities.Utilities.mapAmountToString;


@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final PersonService personService;
    private final ExpenseService expenseService;
    private final FriendshipRepository friendshipRepository;
    private final GroupParticipantsRepository groupParticipantsRepository;


    public GroupService(GroupRepository groupRepository, ExpenseService expenseService, PersonService personService, FriendshipRepository friendshipRepository, GroupParticipantsRepository groupParticipantsRepository) {
        this.groupRepository = groupRepository;
        this.personService = personService;
        this.expenseService = expenseService;
        this.friendshipRepository = friendshipRepository;
        this.groupParticipantsRepository = groupParticipantsRepository;
    }

    public List<Group> getAllGroups() {
        return Utilities.IterableToList(groupRepository.findAll());
    }

    public Long createGroup(CreateGroupDTO createGroupDTO) {
        // save group in table and grab id to make entry in GroupParticipants
        Long groupId = groupRepository.save(Group.builder().name(createGroupDTO.getName()).build()).getId();
        // add participants
        addGroupParticipants(createGroupDTO.getGroupParticipants(), groupId);
        // friendship check
        addNonExistentFriendship(createGroupDTO.getGroupParticipants());

        // save

        return groupId;

    }

    private void addNonExistentFriendship(List<Long> groupParticipants) {
        IntStream.range(0, groupParticipants.size() - 1)
                .mapToObj(i -> new AbstractMap.SimpleEntry<>(groupParticipants.get(i), groupParticipants.get(i + 1)))
                .forEach(this::checkNAddFriendship);
    }

    private void checkNAddFriendship(AbstractMap.SimpleEntry<Long, Long> pair) {
        Optional<Friendship> friendshipBySelfAndFriendOp = friendshipRepository.getFriendshipBySelfAndFriend(pair.getKey(), pair.getValue());
        if (!friendshipBySelfAndFriendOp.isPresent()) {
            friendshipRepository.save(Friendship.builder().self(pair.getKey()).friend(pair.getValue())
                    .amount(mapAmountToString(0, Currency.getInstance("INR"))).build());
        }
    }

    private void addGroupParticipants(List<Long> groupParticipants, Long groupId) {
        List<GroupParticipants> participants = groupParticipants.stream().map(participant -> GroupParticipants.builder()
                .participant(participant).groupId(groupId)
                .amount(mapAmountToString(0, Currency.getInstance("INR")))
                .build()).collect(Collectors.toList());
        groupParticipantsRepository.saveAll(participants);
    }

    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
    }

    public Group updateGroup(Long id, Group group) {
        group.setId(id);
        return groupRepository.save(group);
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    public Group addPersonToGroup(Long groupId, Long personId) {
        Person person = personService.getPersonById(personId);
        Group group = getGroupById(groupId);
//        group.addMembers(person);
        return groupRepository.save(group);
    }

    public Group removePersonFromGroup(Long groupId, Long personId) {
        Person person = personService.getPersonById(personId);
        Group group = getGroupById(groupId);
//        group.removeMembers(person);
        return groupRepository.save(group);
    }

    public Long addExpense(Long groupId, Expense expense) {
//        Group group = getGroupById(groupId);
//        group.addExpense(expense);
//        Group savedGroup = groupRepository.save(group);
        return 1L;
    }

    public List<Expense> getExpenseList(Long groupId) {
//        Group group = getGroupById(groupId);
        return new ArrayList<Expense>();
    }

    public Group addExpenseToGroup(Long groupId, Long expenseId) {

//        Expense expense = expenseService.getExpenseById(expenseId);
//        Group group = getGroupById(groupId);
//        group.addExpense(expense);
        return new Group();
    }

    public Group removeExpenseFromGroup(Long groupId, Long expenseId) {
        Expense expense = expenseService.getExpenseById(expenseId);
        Group group = getGroupById(groupId);
//        group.removeExpense(expense);
        return groupRepository.save(group);
    }
}

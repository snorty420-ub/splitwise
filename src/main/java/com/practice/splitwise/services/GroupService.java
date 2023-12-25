package com.practice.splitwise.services;

import com.practice.splitwise.data.Expense;
import com.practice.splitwise.data.Person;
import com.practice.splitwise.data.Group;
import com.practice.splitwise.exceptions.GroupNotFoundException;
import com.practice.splitwise.repositories.GroupRepository;
import com.practice.splitwise.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupService {

    private GroupRepository groupRepository;
    private PersonService personService;
    private ExpenseService expenseService;


    @Autowired
    public GroupService(GroupRepository groupRepository, ExpenseService expenseService, PersonService personService){
        this.groupRepository = groupRepository;
        this.personService = personService;
        this.expenseService = expenseService;
    }

    public List<Group> getAllGroups() {
        return Utilities.IterableToList(groupRepository.findAll());
    }

    public Long createGroup(Group group) {
        return groupRepository.save(group).getId();

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
        group.addMembers(person);
        return groupRepository.save(group);
    }

    public Group removePersonFromGroup(Long groupId, Long personId){
        Person person = personService.getPersonById(personId);
        Group group = getGroupById(groupId);
        group.removeMembers(person);
        return groupRepository.save(group);
    }

    public Long addExpense(Long groupId, Expense expense){
//        Group group = getGroupById(groupId);
//        group.addExpense(expense);
//        Group savedGroup = groupRepository.save(group);
        return 1L;
    }

    public List<Expense> getExpenseList(Long groupId){
        Group group = getGroupById(groupId);
        return group.getExpenseList();
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
        group.removeExpense(expense);
        return groupRepository.save(group);
    }
}

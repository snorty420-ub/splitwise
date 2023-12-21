package com.practice.splitwise.controllers;

import com.practice.splitwise.data.Group;
import com.practice.splitwise.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @GetMapping
    public List<Group> getGroups(){
        return groupService.getAllGroups();
    }

    @GetMapping("/{id}")
    public Group getGroupById(@PathVariable("id") Long id){
        return groupService.getGroupById(id);
    }

    @PostMapping
    public Long createGroup(@RequestBody Group person){
        return groupService.createGroup(person);
    }

    @PutMapping("/{id}")
    public Group updateGroup(@PathVariable Long id, @RequestBody Group group){
        return groupService.updateGroup(id, group);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id){
        groupService.deleteGroup(id);
    }

    @PostMapping("/{groupId}/persons/{personId}/add")
    public Group addPersonToGroup(@PathVariable Long groupId, @PathVariable Long personId){
        return groupService.addPersonToGroup(groupId,personId);
    }

    @PostMapping("/{groupId}/persons/{personId}/remove")
    public Group removePersonFromGroup(@PathVariable Long groupId, @PathVariable Long personId){
        return groupService.removePersonFromGroup(groupId,personId);
    }

    @PostMapping("/{groupId}/expenses/{expenseId}/add")
    public Group addExpenseToGroup(@PathVariable Long groupId, @PathVariable Long expenseId){
        return groupService.addExpenseToGroup(groupId, expenseId);
    }

    @PostMapping("/{groupId}/expenses/{expenseId}/remove")
    public Group removeExpenseFromGroup(@PathVariable Long groupId, @PathVariable Long expenseId){
        return groupService.removeExpenseFromGroup(groupId, expenseId);
    }
}

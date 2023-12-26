package com.practice.splitwise.dtos.requests;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateGroupDTO implements Serializable {
    private String name;
    private List<Long> groupParticipants;
}

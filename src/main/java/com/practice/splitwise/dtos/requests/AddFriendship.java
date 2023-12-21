package com.practice.splitwise.dtos.requests;

import lombok.Data;

import java.io.Serializable;


@Data
public class AddFriendship implements Serializable {

    private Long person1;
    private Long person2;
}

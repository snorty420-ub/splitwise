package com.practice.splitwise.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * info of a person's debt/credit status
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table
public class GroupParticipants {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long groupId;
    private Long participant;
    private String amount;
}

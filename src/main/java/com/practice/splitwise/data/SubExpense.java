package com.practice.splitwise.data;

import com.practice.splitwise.data.enums.Category;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Data
@Table
@Builder
@AllArgsConstructor
public class SubExpense implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    private Long id;
    private Long groupId;
    private Long addedBy;
    private String name;
    private Timestamp date;
    private Category category;
    private String amount;
    private Long expenseId;

}

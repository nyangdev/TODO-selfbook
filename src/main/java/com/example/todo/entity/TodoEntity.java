package com.example.todo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_todos")
@Builder
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 500, nullable = false)
    private String writer;

    private LocalDate dueDate;

    // title change
    public void changeTitle(String title) {
        this.title = title;
    }

    // writer change
    public void changeWriter(String writer) {
        this.writer = writer;
    }

    // dueDate change
    public void changeDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
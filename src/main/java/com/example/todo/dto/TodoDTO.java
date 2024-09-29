package com.example.todo.dto;

import com.example.todo.entity.TodoEntity;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {

    private Long mno;

    @NotEmpty // 문자열이 비어있지않을것
    private String title;

    @NotEmpty
    private String writer;

    @FutureOrPresent // 현재 혹은 미래
    private LocalDate dueDate;

    // TodoEntity의 데이터를 담을 수 있는 TodoDTO 클래스
    public TodoDTO(TodoEntity todoEntity) {
        this.mno = todoEntity.getMno();
        this.title = todoEntity.getTitle();
        this.writer = todoEntity.getWriter();
        this.dueDate = todoEntity.getDueDate();
    }

    // dto를 엔티티 객체로 변환
    public TodoEntity toEntity() {
        return TodoEntity.builder()
                .mno(mno)
                .title(title)
                .writer(writer)
                .dueDate(dueDate)
                .build();
    }
}

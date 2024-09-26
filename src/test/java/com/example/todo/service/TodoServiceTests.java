package com.example.todo.service;

import com.example.todo.dto.TodoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class TodoServiceTests {

    @Autowired
    private TodoService todoService;

    @Test
    public void testRegister() {

        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTitle("Test Todo");
        todoDTO.setWriter("user00");
        todoDTO.setDueDate(LocalDate.of(2025, 12, 23));

        TodoDTO resultDTO = todoService.register(todoDTO);

        System.out.println(resultDTO);
    }

    @Test
    public void testRead() {

        Long mno = 104L;

        TodoDTO todoDTO = todoService.read(mno);

        System.out.println(todoDTO);
    }

    @Test
    public void testRemove() {
        Long mno = 3L;

        todoService.remove(mno);
    }

    // 정보 수정
    @Test
    public void testModify() {

        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setMno(102L);
        todoDTO.setTitle("수정된 제목");
        todoDTO.setWriter("fix1");
        todoDTO.setDueDate(LocalDate.now());

        todoService.modify(todoDTO);
    }
}

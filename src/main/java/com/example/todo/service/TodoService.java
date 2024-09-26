package com.example.todo.service;

import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.TodoEntity;
import com.example.todo.exception.EntityNotFoundException;
import com.example.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    // 반환값은 DTO로
    public TodoDTO register(TodoDTO todoDTO) {

        // DTO를 엔티티 객체로 변환
        TodoEntity todoEntity = todoDTO.toEntity();

        // todoRegister 이용해서 저장
        todoRepository.save(todoEntity);

        // DTO에 저장된 번호를 저장해서 반환
        return new TodoDTO(todoEntity);
    }

    public TodoDTO read(Long mno) {

        // 특정 번호의 dto 가져오기
        Optional<TodoDTO> result = todoRepository.getDTO(mno);

        TodoDTO todoDTO = result.orElseThrow(
                () -> new EntityNotFoundException("Todo " + mno + " not found")
        );

        return todoDTO;
    }

    // 삭제
    public void remove(Long mno) {

        Optional<TodoEntity> result = todoRepository.findById(mno);

        TodoEntity todoEntity = result.orElseThrow(
                () -> new EntityNotFoundException("Todo " + mno + " not found")
        );

        todoRepository.delete(todoEntity);
    }

    public TodoDTO modify(TodoDTO todoDTO) {

        Optional<TodoEntity> result = todoRepository.findById(todoDTO.getMno());

        TodoEntity todoEntity = result.orElseThrow(
                () -> new EntityNotFoundException("Todo " + todoDTO.getMno() + " not found")
        );

        todoEntity.changeTitle(todoDTO.getTitle());
        todoEntity.changeWriter(todoDTO.getWriter());
        todoEntity.changeDueDate(todoDTO.getDueDate());

        return new TodoDTO(todoEntity);
    }
}
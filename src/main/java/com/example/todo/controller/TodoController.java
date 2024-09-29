package com.example.todo.controller;

import com.example.todo.dto.PageRequestDTO;
import com.example.todo.dto.TodoDTO;
import com.example.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/vi/todos")
@Log4j2
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    // 등록
    @PostMapping
    public ResponseEntity<TodoDTO> register(@RequestBody @Validated TodoDTO todoDTO) {

        log.info("register.............");
        log.info(todoDTO);

//        return null;

        todoDTO.setMno(null);

        return ResponseEntity.ok(todoService.register(todoDTO));
    }

    // 조회
    @GetMapping("/{mno}")
    public ResponseEntity<TodoDTO> read(@PathVariable("mno") Long mno) {

        log.info("read............");
        log.info(mno);

        return ResponseEntity.ok(todoService.read(mno));
    }

    // 수정
    @PutMapping("/{mno}")
    public ResponseEntity<TodoDTO> modify(@PathVariable("mno") Long mno, @Validated @RequestBody TodoDTO todoDTO) {

        log.info("modify............");
        log.info(mno);
        log.info(todoDTO);

        // DTO에 번호를 저장
        todoDTO.setMno(mno);

        TodoDTO modifiedTodoDTO = todoService.modify(todoDTO);

        return ResponseEntity.ok(modifiedTodoDTO);
    }

    // 삭제
    @DeleteMapping("/{mno}")
    public ResponseEntity<Map<String, String>> remove(@PathVariable("mno") Long mno) {

        log.info("remove.............");
        log.info(mno);

        todoService.remove(mno);

        Map<String, String> result = Map.of("result", "success");

        return ResponseEntity.ok(result);
    }

    // 목록
    @GetMapping("/list")
    public ResponseEntity<Page<TodoDTO>> list(@Validated PageRequestDTO pageRequestDTO) {

        log.info("list............");

        return ResponseEntity.ok(todoService.getList(pageRequestDTO));
    }
}

package com.example.todo.repository.search;

import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.TodoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoSearch {
    // 1. 별도의 인터페이스 설계
    // 2. 해당 인터페이스의 구현 클래스 작성 - Q도메인 클래스 사용
    // 3. 기존 리포지토리에 추가
    Page<TodoEntity> search1(Pageable pageable);

    Page<TodoDTO> searchDTO(Pageable pageable);
}

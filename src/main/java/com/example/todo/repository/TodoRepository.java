package com.example.todo.repository;

import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.TodoEntity;
import com.example.todo.repository.search.TodoSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<TodoEntity, Long>, TodoSearch {

    // repository 에서 페이징 처리
//    @Query("select t from TodoEntity t")
    @Query(
            value = "select * from tbl_todos t ",
            countQuery = " select count(*) from tbl_todos ",
            nativeQuery = true
    )
    Page<TodoEntity> listAll(Pageable pageable);

    // 특정 키워드 검색
    @Query("select t from TodoEntity t " +
    "where t.title like %:keyword% and t.mno > 0 order by t.mno desc")
    Page<TodoEntity> listOfTitle(@Param("keyword") String keyword, Pageable pageable);

    // 특정의 번호의 TodoDTO 데이터 가져오기
    @Query("select t from TodoEntity t where t.mno = :mno")
    Optional<TodoDTO> getDTO(@Param("mno") Long mno);
}
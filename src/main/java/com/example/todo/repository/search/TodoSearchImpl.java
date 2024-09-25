package com.example.todo.repository.search;

import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.QTodoEntity;
import com.example.todo.entity.TodoEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {

    public TodoSearchImpl() {
        super(TodoEntity.class);
    }

    @Override
    public Page<TodoEntity> search1(Pageable pageable) {

        log.info("search1...........");

        QTodoEntity todoEntity = QTodoEntity.todoEntity;

        JPQLQuery<TodoEntity> query = from(todoEntity);

        query.where(todoEntity.mno.gt(0L));

        getQuerydsl().applyPagination(pageable, query);

        java.util.List<TodoEntity> entityList = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(entityList, pageable, count);
    }

    @Override
    public Page<TodoDTO> searchDTO(Pageable pageable) {

        log.info("searchDTO..........");

        QTodoEntity todoEntity = QTodoEntity.todoEntity;

        JPQLQuery<TodoEntity> query = from(todoEntity);

        query.where(todoEntity.mno.gt(0L));

        getQuerydsl().applyPagination(pageable, query);

        // dto 반환
//        JPQLQuery<TodoDTO> dtoQuery =
//                query.select(Projections.constructor(TodoDTO.class, todoEntity));

        JPQLQuery<TodoDTO> dtoQuery = query.select(
                Projections.bean(TodoDTO.class,
                        todoEntity.mno,
                        todoEntity.title,
                        todoEntity.writer,
                        todoEntity.dueDate)
        );

        java.util.List<TodoDTO> dtoList = dtoQuery.fetch();

        long count = dtoQuery.fetchCount();

        return new PageImpl<>(dtoList, pageable, count);
    }
}
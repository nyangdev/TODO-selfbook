package com.example.todo.repository;

import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.TodoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //실제 db 사용을 위한 설정
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void testInsert() {
        TodoEntity todoEntity = TodoEntity.builder()
                .title("부트 끝내기")
                .writer("user00")
                .dueDate(LocalDate.of(2025, 12, 23))
                .build();

        todoRepository.save(todoEntity);

        System.out.println("New TodoEntity MNO: " + todoEntity.getMno());
    }

    // 더미 데이터 삽입
    @Test
    public void testInsertDummies() {

        for(int i = 0; i < 100; i++) {
            TodoEntity todoEntity = TodoEntity.builder()
                    .title("Test Todo..." + i)
                    .writer("tester" + i)
                    .dueDate(LocalDate.of(2025, 11, 30))
                    .build();

            todoRepository.save(todoEntity);
            System.out.println("New TodoEntity MNO: " + todoEntity.getMno());
        }
    }

    // findById()의 경우 해당 데이터가 존재하지 않을수도 있기때문에 Optional 리턴 타입으로 사용
    @Test
    public void testRead() {

        Long mno = 58L;

        Optional<TodoEntity> result = todoRepository.findById(mno);

        // 결과가 존재한다면
        result.ifPresent(todoEntity -> {
            System.out.println(todoEntity);
        });
    }

    @Test
    @Transactional
    public void testRead2() {
        Long mno = 55L;

        Optional<TodoEntity> result = todoRepository.findById(mno);

        result.ifPresent(todoEntity -> {
            System.out.println(todoEntity);
        });

        Optional<TodoEntity> result2 = todoRepository.findById(mno);

        // 영속 컨텍스트 1차 캐시
        result2.ifPresent(todoEntity -> {
            System.out.println(todoEntity);
        });
    }

    @Test
    @Transactional
    @Commit
    public void testUpdateDirtyCheck() {

        Long mno = 58L;

        Optional<TodoEntity> result = todoRepository.findById(mno);

        TodoEntity todoEntity = result.get();

        System.out.println("OLD : " + todoEntity);

        todoEntity.changeTitle("Changed Title..." + Math.random());
        todoEntity.changeWriter("Changed Writer..." + Math.random());

        System.out.println("NEW : " + todoEntity);
    }

    @Test
    @Commit
    public void testUpdateDetached() {
        Long mno = 58L;

        Optional<TodoEntity> result = todoRepository.findById(mno);

        TodoEntity todoEntity = result.get();

        System.out.println("OLD : " + todoEntity);

        todoEntity.changeTitle("Changed Title..." + Math.random());
        todoEntity.changeWriter("Changed Writer..." + Math.random());

        System.out.println("NEW : " + todoEntity);

        todoRepository.save(todoEntity);
    }

    @Test
    @Transactional
    @Commit
    public void testDelete() {
        Long mno = 101L;

        Optional<TodoEntity> result = todoRepository.findById(mno);

        result.ifPresent(todoEntity -> {
            todoRepository.delete(todoEntity);
        });
    }

    @Test
    @Transactional
    @Commit
    public void testDeleteById() {

        Long mno = 100L;

        todoRepository.deleteById(mno);
    }

    @Test
    public void testPaging() {
        // 페이지 번호, 사이즈, 정렬 조건
        // 정렬 조건은 mno 칼럼의 역순으로 지정
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno")
                .descending());
        // 페이지 번호의 인덱스 값은 0부터 시작
        // 실제로는 1 페이지

        Page<TodoEntity> result = todoRepository.findAll(pageable);

        System.out.println(result.getTotalPages());
        System.out.println(result.getTotalElements());

        java.util.List<TodoEntity> todoEntityList = result.getContent();

        todoEntityList.forEach(todoEntity -> {
            System.out.println(todoEntity);
        });
    }

    @Test
    public void testPaging2() {
        // repository를 통한 paging 처리 test
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno")
                .descending());

//        Page<TodoEntity> result = todoRepository.listAll(pageable);
        Page<TodoEntity> result = todoRepository.listOfTitle("Test Todo", pageable);

        System.out.println(result.getContent());
    }

    @Test
    public void testSearch1() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno")
                .descending());

        Page<TodoEntity> result = todoRepository.search1(pageable);

        System.out.println(result.getTotalPages());
        System.out.println(result.getTotalElements());

        java.util.List<TodoEntity> todoEntityList = result.getContent();

        todoEntityList.forEach(todoEntity -> {
            System.out.println(todoEntity);
        });
    }

    @Test
    public void testGetTodoDTO() {
        Long mno = 59L;

        Optional<TodoDTO> result = todoRepository.getDTO(mno);

        result.ifPresent(todoDTO -> {
            System.out.println(todoDTO);
        });
    }

    @Test
    public void testSearchDTO() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno")
                .descending());

        Page<TodoDTO> result = todoRepository.searchDTO(pageable);

        System.out.println(result.getTotalPages());
        System.out.println(result.getTotalElements());

        java.util.List<TodoDTO> dtoList = result.getContent();

        dtoList.forEach(todoDTO -> {
            System.out.println(todoDTO);
        });
    }
}
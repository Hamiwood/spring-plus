package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.naming.directory.SearchResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class TodoCustomRepositoryImpl implements TodoCustomRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId){
        Todo result = queryFactory
                .selectFrom(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<TodoSearchResponse> searchTodos(String title,
                                  String managerName,
                                  LocalDateTime startDateTime,
                                  LocalDateTime endDateTime,
                                  Pageable pageable){

        JPQLQuery<TodoSearchResponse> query = queryFactory
                .select(Projections.constructor(TodoSearchResponse.class,
                todo.title,
                manager.countDistinct().intValue(), comment.countDistinct().intValue()
        ))
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(todo.comments, comment)
                .where(
                        title != null ? todo.title.containsIgnoreCase(title) : null,
                        managerName != null ? manager.user.username.containsIgnoreCase(managerName) : null,
                        startDateTime != null ? todo.createdAt.goe(startDateTime) : null,
                        endDateTime != null ? todo.createdAt.loe(endDateTime) : null
                )
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc());

        List<TodoSearchResponse> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query.fetchCount();

        return new PageImpl<>(results, pageable, total);
    }
}

package com.crud.tasks.repository;

import com.crud.tasks.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface TaskRepository extends CrudRepository<Task,Long> {
    @Override
    List<Task> findAll();

    Optional<Task> findById(final Long id);

    @Override
    @SuppressWarnings("unchecked")
    Task save(final Task task);

    Integer deleteById(final Long id);
}

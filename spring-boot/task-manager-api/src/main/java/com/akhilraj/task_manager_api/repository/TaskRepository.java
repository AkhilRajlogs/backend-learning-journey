package com.akhilraj.task_manager_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.akhilraj.task_manager_api.model.Task;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCompleted(boolean completed);
}
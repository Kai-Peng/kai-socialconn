package com.kai.socialconn.dao;

import com.kai.socialconn.bean.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDao {
    public Task getTask(String id);

    public int insertTask(Task task);

    public int updateTask(Task task);

    public Task findByInfoOrTask(Task task);

    public int deleteTask(String id);
}

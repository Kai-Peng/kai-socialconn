package com.kai.socialconn.service;

import com.kai.socialconn.bean.Task;

import java.util.List;

public interface TaskService {
    default public List<Task> getTaskList(){
        return null;
    };
    public List<Task> getTaskList(String userId);

    public Task getTaskById(String id);

    public int insertTask(Task task);

    public int insertTasks(List<Task> tasks);

    public Task updateTask(Task task);

    public int deleteTask(String id);
}

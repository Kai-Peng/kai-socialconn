package com.kai.socialconn.service;

import com.kai.socialconn.bean.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{
    @Override
    public List<Task> getTaskList() {
        List<Task> taskList = new ArrayList<Task>();
        Task task = new Task();
        task.setId("1");
        task.setType("Eating out");
        task.setInfo("I want to find a special restaurant for eating beef.");
        task.setModifyUser("kai");
        task.setTime("2018-11-05");
        taskList.add(task);
        return taskList;
    }
}

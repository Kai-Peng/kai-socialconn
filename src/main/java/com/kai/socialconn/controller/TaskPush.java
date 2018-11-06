package com.kai.socialconn.controller;

import com.kai.socialconn.bean.Task;
import com.kai.socialconn.service.TaskService;
import com.kai.socialconn.service.TaskValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TaskPush {
    @Autowired
    private TaskService taskService = null;

    @Autowired
    private TaskValidator taskValidator = null;

    @RequestMapping("/")
    public List<Task> getTaskList(){
        List<Task> tempList = taskService.getTaskList();
//        tempList = null;
        if(taskValidator.isLegal(tempList)){
            return tempList;
        }else{
            return null;
        }
    }
}

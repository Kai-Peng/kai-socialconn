package com.kai.socialconn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TaskListPage {
    @RequestMapping("/TaskList")
    public String taskListPage(){

        return "taskListPage";
    }
}

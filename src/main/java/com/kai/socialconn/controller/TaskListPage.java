package com.kai.socialconn.controller;

import com.kai.socialconn.bean.Task;
import com.kai.socialconn.bean.TypeEnum;
import com.kai.socialconn.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TaskListPage {
    @Autowired
    private TaskService taskService = null;

    @RequestMapping("/TaskList")
    public String taskListPage(ModelMap model){
        Task task = taskService.getTaskById("1");
        model.addAttribute("task",task);
        return "taskListPage";
    }

    @RequestMapping("/insertTask")
    @ResponseBody
    public Map<String, Object> insertTask(String type, String info, String modifyUser){
        Task task = new Task();
        task.setInfo(info);
        task.setModifyUser(modifyUser);
        task.setTime(LocalDateTime.now().toString());
        task.setType(TypeEnum.getEnumById(Integer.parseInt(type)));

        int updateRslt = taskService.insertTask(task);
        Map<String, Object> result = new HashMap<>();
        result.put("success", updateRslt==1);
        result.put("task", task);
        return result;
    }
}

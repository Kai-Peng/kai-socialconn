package com.kai.socialconn.controller;

import com.kai.socialconn.bean.Task;
import com.kai.socialconn.bean.TypeEnum;
import com.kai.socialconn.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/springCache")
public class SpringCacheController {
    @Autowired
    private TaskService taskS = null;

    @RequestMapping("/getTaskInfo")
    @ResponseBody
    public Task getTaskInfoById(String id){
        return taskS.getTaskById(id);
    }

    @RequestMapping("/insertTask")
    @ResponseBody
    public Task insertTask(){
        Task task = new Task();
        task.setType(TypeEnum.SWIM);
        task.setInfo("I want to watch a movie and go to swimming.");
        task.setModifyUser("kai");
        task.setTime("2018-11-11");
        taskS.insertTask(task);
        return task;
    }

    @RequestMapping("/updateTask")
    @ResponseBody
    public Task updateTask(){
        Task task = this.getTaskInfoById("1");
        task.setTime("2018-11-11");
        task.setModifyUser("roboot");
        return taskS.updateTask(task);
    }

    @RequestMapping("/deleteTask")
    @ResponseBody
    public Map<String, Object> deleteTask(String id){
        int i = taskS.deleteTask(id);
        Map<String,Object> map = new HashMap<>();

        if(i>0){
            map.put("result","success");
        }else {
            map.put("result","failure");
        }
        return map;
    }
}

package com.kai.socialconn.service;

import com.kai.socialconn.bean.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskValidatorImpl implements TaskValidator {
    @Override
    public boolean isLegal(List<Task> taskList) {

        if(taskList!=null && taskList.size()>0){
            return true;
        }
        return false;
    }
}

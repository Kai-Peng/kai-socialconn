package com.kai.socialconn.service;

import com.kai.socialconn.bean.Task;

import java.util.List;

public interface TaskValidator {
    public boolean isLegal(List<Task> taskList);
}

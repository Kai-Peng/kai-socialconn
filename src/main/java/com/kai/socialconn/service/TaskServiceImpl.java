package com.kai.socialconn.service;

import com.kai.socialconn.bean.Task;
import com.kai.socialconn.bean.TypeEnum;
import com.kai.socialconn.dao.TaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{
    @Autowired
    private TaskDao taskDao = null;

    @Override
    public List<Task> getTaskList(String userId) {
        List<Task> taskList = new ArrayList<Task>();
        Task task = new Task();
        task.setId("1");
        task.setType(TypeEnum.EATOUT);
        task.setInfo("I want to find a special restaurant for eating beef.");
        task.setModifyUser("kai");
        task.setTime("2018-11-05");
        taskList.add(task);
        return taskList;
    }

    @Override
    @Cacheable(value = "redisCache",key="'redis_taskinfo_'+#id")
    public Task getTaskById(String id){
        return taskDao.getTask(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 1)
    @CachePut(value = "redisCache",key = "'redis_taskinfo_'+#result.id")
    public int insertTask(Task task){
        return taskDao.insertTask(task);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int insertTasks(List<Task> tasks) {
        int count =0;
        for (Task task:tasks) {
            count += this.insertTask(task);
        }
        return count;
    }

    @Override
    @Transactional
    @CachePut(value = "redisCache",condition = "#result!='null'",key = "'redis_taskinfo_'+#task.id")
    public Task updateTask(Task task) {
        int i = taskDao.updateTask(task);
        if(i>0){
            return task;
        }else {
            return null;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "redisCache",key = "'redis_taskinfo_'+#id",beforeInvocation = false)
    public int deleteTask(String id) {
        return taskDao.deleteTask(id);
    }


}

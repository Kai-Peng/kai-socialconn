package com.kai.socialconn.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 对单个的组件设置其生命周期内不一样的行为特征：创建、销毁等
 */
@Component
public class Designer implements BeanNameAware, BeanFactoryAware, ApplicationContextAware,
        InitializingBean, DisposableBean {

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println(" ["+this.getClass().getSimpleName()+"] 调用BeanFactoryAware的setBeanFactory");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println(" ["+this.getClass().getSimpleName()+"] 调用BeanNameAware的setBeanName");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(" ["+this.getClass().getSimpleName()+"] 调用DisposableBean的destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(" ["+this.getClass().getSimpleName()+"] 调用InitializingBean的afterPropertiesSet");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(" ["+this.getClass().getSimpleName()+"] 调用ApplicationContextAware的setApplicationContext");
    }

    @PostConstruct
    public void init(){
        System.out.println(" ["+this.getClass().getSimpleName()+"] 注释@Poststruct定义的自定义初始化方法");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println(" ["+this.getClass().getSimpleName()+"] 注释@PreDestroy定义的自定义销毁方法");
    }
}

package com.kai.socialconn.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 改变所有bean初始化时的行为
 */
@Component
public class BeanPostProcessorSelfDefine implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcessor调用postProcessorAfterInitialization方法，参数【"+
                bean.getClass().getSimpleName()+"】 【"+beanName+"】 ");
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcessor调用postProcessBeforeInitialization方法，参数【"+
                bean.getClass().getSimpleName()+"】 【"+beanName+"】 ");
        return bean;
    }
}

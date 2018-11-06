package com.kai.socialconn.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {
    private Object target;
    /**
     * create proxy object
     */
    public static Object getInstance(Object target){
        CglibProxy cglibProxy = new CglibProxy();
        cglibProxy.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cglibProxy.target.getClass());
        enhancer.setCallback(cglibProxy);
        return enhancer.create();
    }

    /**
     *
     * @param o
     * @param method
     * @param objects
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.err.println("#################I'm a cglib proxy object.##################");
        //reflect method
        Object returnObj = methodProxy.invokeSuper(o, objects);
        System.err.println("##################reflect method had been invoked by cglib object.####");
        return returnObj;
    }
}

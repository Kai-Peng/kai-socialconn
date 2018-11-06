package com.kai.socialconn.proxy;

public class ProxyTest {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        HelloService proxy = (HelloService) ProxyBean.getProxyBean(helloService,new MyInterceptor());
        proxy.sayHello(" peng kai");
        System.out.println("\n##################################################" +
                "name is null!!######################\n");
        proxy.sayHello(null);


        HelloService proxy_cglib = (HelloService) CglibProxy.getInstance(helloService);
        proxy_cglib.sayHello(" cglib peng kai cglib");
        System.out.println("\n##################################################" +
                "name is null!!######################\n");
        proxy_cglib.sayHello(null);
    }
}

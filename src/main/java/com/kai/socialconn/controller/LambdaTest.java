package com.kai.socialconn.controller;

public class LambdaTest {
    public static void main(String[] args) {
        new Thread(()->
                System.out.println("test")).start();
    }
}

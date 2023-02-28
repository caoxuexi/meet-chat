package com.caostudy.mybatis.utils;

/**
 * @author: CaoStudy
 * @date: 2022-01-21 18:59
 * @desc: 啊、test
 */
public class Test {
    public static void main(String[] args) {

        Thread t=new Thread(){

            public void run(){

                pong();

            }

        };

        t.run();

        System.out.println("ping");

    }

    static void pong(){

        System.out.println("pong");

    }
}

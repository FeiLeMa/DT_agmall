package com.alag.agmall.business.module.maxnotify.server.thread;

import java.util.concurrent.*;

/**
 * @program: threadpool-test
 * @description: 用于商户通知的线程池
 * @author: Alag
 * @create: 2019-08-18 19:44
 * @email: alag256@aliyun.com
 **/
public class MyTreadPool {
    private static MyTreadPool myTreadPool = new MyTreadPool();
    private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(128);
    public static ExecutorService exector = new ThreadPoolExecutor(100,
            100,
            0,
            TimeUnit.SECONDS,
            workQueue,
            new ThreadPoolExecutor.AbortPolicy());
    private MyTreadPool(){}
}

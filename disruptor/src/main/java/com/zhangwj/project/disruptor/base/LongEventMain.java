package com.zhangwj.project.disruptor.base;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LongEventMain {

    public static void main(String[] args) {
        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        //创建工厂
        LongEventFactory factory = new LongEventFactory();

        //创建bufferSize,也就是ringbuffer的大小
        int bufferSize = 1024 * 1024;

        //创建disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, bufferSize, executorService, ProducerType.SINGLE, new YieldingWaitStrategy());

        //设置消费方法
        disruptor.handleEventsWith(new LongEventHandler());

        //启动
        disruptor.start();


        //Disruptor事件发布
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        for(long l = 0; l < 100; ++l){
            producer.publish(l);
        }

        disruptor.shutdown();
        executorService.shutdown();
    }
}

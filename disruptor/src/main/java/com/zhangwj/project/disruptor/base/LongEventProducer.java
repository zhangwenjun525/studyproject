package com.zhangwj.project.disruptor.base;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class LongEventProducer {

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

/*    public void publish(Long value){
        long sequence = ringBuffer.next();
        try{
            LongEvent event = ringBuffer.get(sequence);
            event.setValue(value);
        }finally {
            ringBuffer.publish(sequence);
        }
    }*/

    public void publish(Long value){
       ringBuffer.publishEvent(new EventTranslatorOneArg<LongEvent, Long>() {
           @Override
           public void translateTo(LongEvent longEvent, long l, Long aLong) {
               longEvent.setValue(aLong);
           }
       }, value);
    }
}

package master_worker;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/28
 * Time: 11:20
 */
public class Worker implements Runnable {

    /**
     * 用于保存任务
     */
    private ConcurrentLinkedQueue<Task> taskQueue = new ConcurrentLinkedQueue<>();


    /**
     * 用于保存所有任务处理后的结果
     */
    private ConcurrentHashMap<Integer, Integer> results = new ConcurrentHashMap<>();


    @Override
    public void run() {
        while (true) {
            Task poll = taskQueue.poll();
            if (null == poll) break;
            Integer result = handleTask(poll);
            results.put(poll.getId(), result);
        }
    }

    public void setTaskQueue(ConcurrentLinkedQueue<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public void setResults(ConcurrentHashMap<Integer, Integer> results) {
        this.results = results;
    }

    private Integer handleTask(Task task) {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getPrice();
    }

}

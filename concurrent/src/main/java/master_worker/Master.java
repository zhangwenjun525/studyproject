package master_worker;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/28
 * Time: 11:21
 */
public class Master {

    /**
     * 用于保存任务
     */
    private ConcurrentLinkedQueue<Task> taskQueue = new ConcurrentLinkedQueue<>();

    /**
     * 用于保存work
     */
    private HashMap<String, Runnable> works = new HashMap<>();

    /**
     * 用于保存所有任务处理后的结果
     */
    private ConcurrentHashMap<Integer, Integer> results = new ConcurrentHashMap<>();


    public Master(Worker worker, int count) {
        worker.setResults(results);
        worker.setTaskQueue(taskQueue);

        for (int i = 0; i < 100; ++i) {
            works.put(Integer.toString(i + 1), new Thread(worker));
        }
    }

    /**
     * 将任务加入任务队列
     *
     * @param task
     */
    public void sumbit(Task task) {
        taskQueue.add(task);
    }

    public boolean isFinish() {
        Set<Map.Entry<String, Runnable>> entrySet = works.entrySet();
        for (Map.Entry<String, Runnable> entry : entrySet) {
            Thread thread = (Thread) entry.getValue();
            if (Thread.State.TERMINATED != thread.getState()) {
                return false;
            }
        }
        return true;
    }

    public void execute() {
        Set<Map.Entry<String, Runnable>> entrySet = works.entrySet();
        for (Map.Entry<String, Runnable> entry : entrySet) {
            Thread thread = (Thread) entry.getValue();
            thread.start();
        }
    }


    public int getResult() {
        while (true) {
            if (isFinish()) {
                break;
            }
        }

        int result = 0;
        Set<Map.Entry<Integer, Integer>> entrySet = results.entrySet();
        for (Map.Entry<Integer, Integer> entry : entrySet) {
            result += entry.getValue();
        }

        return result;
    }

    public static void main(String[] args) {
        Master master = new Master(new Worker(), 100);
        Random random = new Random();
        for (int i = 0; i < 1000; ++i) {
            master.sumbit(new Task(i + 1, random.nextInt(1000)));
        }

        Instant start = Instant.now();
        master.execute();
        System.out.println(master.getResult());
        Instant end = Instant.now();
        System.out.println("消耗: " + Duration.between(start, end).toMillis());
    }


}

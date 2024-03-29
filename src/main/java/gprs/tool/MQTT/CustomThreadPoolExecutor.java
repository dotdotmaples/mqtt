package gprs.tool.MQTT;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;  
  
  
/**
* @ClassName: CustomThreadPoolExecutor
* @Description: 线程池管理
* @author guobin
* @date 2019年2月26日
*/
@Component
public class CustomThreadPoolExecutor {  
    private static ExecutorService pool = null;  
//    private static ThreadPoolTaskScheduler pool = null;  
      
    /** 
     * 线程池初始化方法 
     *  
     * corePoolSize 核心线程池大小----10 
     * maximumPoolSize 最大线程池大小----30 
     * keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间----30+单位TimeUnit 
     * TimeUnit keepAliveTime时间单位----TimeUnit.MINUTES 
     * workQueue 阻塞队列----new ArrayBlockingQueue<Runnable>(10)====10容量的阻塞队列 
     * threadFactory 新建线程工厂----new CustomThreadFactory()====定制的线程工厂 
     * rejectedExecutionHandler 当提交任务数超过maxmumPoolSize+workQueue之和时, 
     *                          即当提交第41个任务时(前面线程都没有执行完,此测试方法中用sleep(100)), 
     *                          任务会交给RejectedExecutionHandler来处理 
     */  
//    public void init() {  
//        pool = new ThreadPoolExecutor(  
//                10,  
//                30,  
//                30,  
//                TimeUnit.MINUTES,  
//                new LinkedBlockingQueue<Runnable>(),  
//                new CustomThreadFactory(),
//                new CustomRejectedExecutionHandler());  
//    }  
    
    public static ExecutorService getCustomThreadPoolExecutor() { 
//        if (null == pool) {
            if (null == pool || pool.isShutdown()) {
            pool = new ThreadPoolExecutor(  
                    15,  
                    50,  
                    30,  
                    TimeUnit.MINUTES,  
                    new LinkedBlockingQueue<Runnable>(),  
                    Executors.defaultThreadFactory(),
                    new CustomRejectedExecutionHandler()); 
//            pool = new ThreadPoolTaskScheduler();
//            pool.setPoolSize(20);
//            pool.setThreadNamePrefix("taskExecutor-");
//            pool.setWaitForTasksToCompleteOnShutdown(true);
//            pool.setAwaitTerminationSeconds(60);
//            pool.initialize();
        }
        return pool;  
    }  
      
    public static void destory() {  
        if(pool != null && !pool.isShutdown()) {  
            pool.shutdown();
        }  
    }  
      
//    private static class CustomThreadFactory implements ThreadFactory {  
//  
//        private AtomicInteger count = new AtomicInteger(0);  
//          
//        @Override  
//        public Thread newThread(Runnable r) {  
//            Thread t = new Thread(r);  
//            String threadName = CustomThreadPoolExecutor.class.getSimpleName() + count.addAndGet(1);  
////            System.out.println(threadName);  
//            t.setName(threadName);  
//            
//            return t;  
//        }  
//    }  
      
      
    private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {  
  
        @Override  
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {  
            // 记录异常  
            // 报警处理等  
            // 超出堵塞列队(如果设置了堵塞值)，自行处理
            System.err.println("Thread error.............");  
        }  
    }  
      
      
      
    // 测试构造的线程池  
    public static void main(String[] args) {  
          
//        ExecutorService pool = CustomThreadPoolExecutor.getCustomThreadPoolExecutor();  
//        for(int i=1; i<100; i++) {  
//            final int a = i;
//            pool.execute(new Runnable() {  
//                @Override  
//                public void run() {  
//                    try {  
//                        System.out.println(Thread.currentThread().getName() + "--in " + a);
//                        Thread.sleep(3000);  
////                        System.out.println(Thread.currentThread().getName() + "--out " + a);
//                    } catch (InterruptedException e) {  
//                        e.printStackTrace();  
//                    }  
//                }  
//            });  
//        }  
          
          
          
        // 2.销毁----此处不能销毁,因为任务没有提交执行完,如果销毁线程池,任务也就无法执行了  
        // exec.destory();  
          
        try {  
            Thread.sleep(10000);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
}
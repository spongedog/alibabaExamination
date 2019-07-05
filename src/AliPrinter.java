import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 三个线程交替打印
 * @author 郑凯努 date 2019/7/5
 */
public class AliPrinter {

    /**
     * 线程安全的计数用来控制线程顺序打印
     */
    private AtomicInteger count;

    /**
     * 控制线程是否运行
     */
    private volatile boolean flag;

    private Runnable runnableA;

    private Runnable runnableB;

    private Runnable runnableC;

    public Runnable getRunnableA() {
        return runnableA;
    }

    public Runnable getRunnableB() {
        return runnableB;
    }

    public Runnable getRunnableC() {
        return runnableC;
    }

    public AliPrinter() {
        count = new AtomicInteger();
        flag = true;
        runnableA = () -> {
            while (flag) {
                if (count.get() == 0) {
                    System.out.print("a");
                    count.getAndIncrement();
                }
            }
        };

        runnableB = () -> {
            while (flag) {
                if (count.get() == 1) {
                    System.out.print("l");
                    count.getAndIncrement();
                }
            }
        };

        runnableC = () -> {
            while (flag) {
                if (count.get() == 2) {
                    System.out.print("i");
                    count.set(0);
                }
            }
        };

    }

    public void stop() {
        flag = false;
    }

    public static void main(String[] args) throws InterruptedException {
        var executor = new ThreadPoolExecutor(3, 3,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());

        var printer = new AliPrinter();

        executor.execute(printer.getRunnableA());
        executor.execute(printer.getRunnableB());
        executor.execute(printer.getRunnableC());

        Thread.sleep(5000);

        printer.stop();
    }
}

package hwl.model;

import hwl.model.table.Courses;
import hwl.model.table.Groups;
import hwl.model.table.Scores;
import hwl.model.table.Students;

import java.io.*;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * “数据库”
 */
public class Database {

    /**
     * 用于执行IO操作的线程池
     */
    final ExecutorService ioExecutor = Executors.newSingleThreadExecutor();

    /**
     * 学生表
     */
    public final Students students = new Students(this);

    /**
     * 班级表
     */
    public final Groups groups = new Groups(this);

    /**
     * 课程表
     */
    public final Courses courses = new Courses(this);

    /**
     * 成绩表
     */
    public final Scores scores = new Scores(this);

    /**
     * 监听被截获的异常
     */
    public interface OnErrorListener {
        /**
         * 有被截获的异常
         *
         * @param exc 被截获的异常
         */
        void onError(Exception exc);
    }

    private final Vector<OnErrorListener> onErrorListeners = new Vector<>();

    /**
     * 添加截获异常监听器
     *
     * @param listener 截获异常监听器
     * @return 是否添加成功
     */
    public boolean addOnErrorListener(OnErrorListener listener) {
        return onErrorListeners.add(listener);
    }

    /**
     * 移除截获异常监听器
     *
     * @param listener 截获异常监听器
     * @return 是否移除成功
     */
    public boolean removeOnErrorListener(OnErrorListener listener) {
        return onErrorListeners.remove(listener);
    }

    /**
     * 使用IO线程池从文件读取数据
     */
    public void postReload() {
        ioExecutor.submit(() -> {
            try (FileInputStream fis = new FileInputStream("dat")) {
                try (DataInputStream dis = new DataInputStream(fis)) {
                    students.read(dis);
                    groups.read(dis);
                    courses.read(dis);
                    scores.read(dis);
                }
                System.out.printf("Reloaded! Student: %d, Group: %d, Course: %d, Score: %d\n", students.size(), groups.size(), courses.size(), scores.size());
            } catch (FileNotFoundException exc) {
                // pass
            } catch (Exception exc) {
                onErrorListeners.forEach(listener -> listener.onError(exc));
            }
        });
    }

    /**
     * 使用IO线程池将数据写入文件
     */
    public void postSave() {
        ioExecutor.submit(() -> {
            try (FileOutputStream fos = new FileOutputStream("dat")) {
                try (DataOutputStream dos = new DataOutputStream(fos)) {
                    students.write(dos);
                    groups.write(dos);
                    courses.write(dos);
                    scores.write(dos);
                }
                System.out.println("Saved!");
            } catch (Exception exc) {
                onErrorListeners.forEach(listener -> listener.onError(exc));
            }
        });
    }

    public void shutdownAndJoin() {
        try {
            ioExecutor.shutdown();
            ioExecutor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (Exception exc) {
            onErrorListeners.forEach(listener -> listener.onError(exc));
        }
    }

    static private final Object instanceLock = new Object();

    static private Database INSTANCE = null;

    /**
     * 获取Database的唯一实例
     *
     * @return Database的唯一实例
     */
    static public Database getInstance() {
        if (INSTANCE == null) {
            synchronized (instanceLock) {
                if (INSTANCE == null) {
                    INSTANCE = new Database();
                }
            }
        }
        return INSTANCE;
    }
}

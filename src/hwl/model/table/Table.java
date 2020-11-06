package hwl.model.table;

import hwl.model.item.Item;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

/**
 * 数据表接口
 */
public interface Table<P, T extends Item<P>> {

    /**
     * 监听数据表的更新
     *
     * @param <T> 数据项类型
     */
    interface OnUpdateListener<T> {

        /**
         * 有新数据项被插入
         *
         * @param newItem 新数据项
         */
        void onInsert(T newItem);

        /**
         * 有数据项被更改
         *
         * @param oldItem 旧数据项
         * @param newItem 新数据项
         */
        void onModify(T oldItem, T newItem);

        /**
         * 有数据项被删除
         *
         * @param removedItem 被删除数据项
         */
        void onRemove(T removedItem);
    }

    /**
     * 添加数据表更新监听器
     *
     * @param listener 数据表更新监听器
     * @return 是否添加成功
     */
    boolean addOnUpdateListener(OnUpdateListener<T> listener);

    /**
     * 移除数据表更新监听器
     *
     * @param listener 数据表更新监听器
     * @return 是否移除成功
     */
    boolean removeOnUpdateListener(OnUpdateListener<T> listener);

    /**
     * 获取数据项数目
     *
     * @return 数据项数目
     */
    int size();

    boolean contains(P primitiveKey);

    /**
     * 获取数据项
     *
     * @param primitiveKey 数据项主键
     * @return 数据项，若不存在则为null
     */
    T get(P primitiveKey);

    /**
     * 获取符合条件的数据项
     *
     * @return 所有符合条件的数据项组成的列表，按主键升序排序
     */
    List<T> get(Predicate<? super T> predicate);

    /**
     * 获取所有数据项
     *
     * @return 所有数据项组成的列表，按主键升序排序
     */
    List<T> getAll();

    /**
     * 添加或更新数据项
     *
     * @param item 数据项
     * @return 更新前的数据项，若之前不存在该数据项则为null
     */
    T put(T item);

    /**
     * 删除数据项
     *
     * @param primitiveKey 数据项主键
     * @return 被删除的数据项
     */
    T remove(P primitiveKey);

    /**
     * 将数据表写入DataOutputStream
     *
     * @param dos DataOutputStream
     * @throws IOException IOException
     */
    void write(DataOutputStream dos) throws IOException;

    /**
     * 从DataInputStream读入数据，并覆盖原数据表中的数据
     *
     * @param dis DataInputStream
     * @throws IOException IOException
     */
    void read(DataInputStream dis) throws IOException;
}

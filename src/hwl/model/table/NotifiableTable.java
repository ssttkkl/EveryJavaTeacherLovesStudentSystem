package hwl.model.table;

import hwl.model.item.Item;

public interface NotifiableTable<P, T extends Item<P>> extends Table<P, T> {

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
         * @param removedItem 被删除的数据项
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
}
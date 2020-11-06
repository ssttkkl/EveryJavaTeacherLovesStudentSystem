package hwl.model.table;

import hwl.model.Database;
import hwl.model.ItemSerializer;
import hwl.model.item.Item;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 数据表抽象类
 */
public abstract class AbstractTable<P, T extends Item<P>> implements Table<P, T> {

    /**
     * 数据表所属数据库
     */
    private final Database db;

    public AbstractTable(Database database) {
        this.db = database;
    }

    private final Vector<OnUpdateListener<T>> listeners = new Vector<>();

    @Override
    public boolean addOnUpdateListener(OnUpdateListener<T> listener) {
        return listeners.add(listener);
    }

    @Override
    public boolean removeOnUpdateListener(OnUpdateListener<T> listener) {
        return listeners.remove(listener);
    }

    /**
     * 保存所有数据项
     */
    private final ConcurrentHashMap<P, T> data = new ConcurrentHashMap<>();

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public T get(P primitiveKey) {
        return data.get(primitiveKey);
    }

    @Override
    public boolean contains(P primitiveKey) {
        return data.containsKey(primitiveKey);
    }

    @Override
    public List<T> get(Predicate<? super T> predicate) {
        return data.values().stream().filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public List<T> getAll() {
        return get(t -> true);
    }

    @Override
    public T put(T item) {
        T old = data.put(item.getPrimitiveKey(), item);

        System.out.println("Put: " + item.toString());

        db.postSave();

        if (old != null) {
            for (OnUpdateListener<T> l : listeners)
                l.onModify(old, item);
        } else {
            for (OnUpdateListener<T> l : listeners)
                l.onInsert(item);
        }

        return old;
    }

    @Override
    public T remove(P primitiveKey) {
        T old = data.remove(primitiveKey);

        if (old != null) {
            System.out.println("Remove: " + old.toString());

            db.postSave();

            for (OnUpdateListener<T> l : listeners)
                l.onRemove(old);
        }

        return old;
    }

    /**
     * 获取ItemSerializer
     *
     * @return ItemSerializer
     */
    protected abstract ItemSerializer<T> getItemSerializer();

    @Override
    public void write(DataOutputStream dos) throws IOException {
        ArrayList<T> list = new ArrayList<>(data.values());

        dos.writeInt(list.size());

        ItemSerializer<T> serializer = getItemSerializer();
        for (T t : list) {
            serializer.write(dos, t);
        }
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        ItemSerializer<T> serializer = getItemSerializer();

        int n = dis.readInt();
        ArrayList<T> list = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            T t = serializer.read(dis);
            list.add(t);
        }

        ArrayList<T> old;
        synchronized (data) {
            old = new ArrayList<>(data.values());
            data.clear();
            for (T t : list) {
                data.put(t.getPrimitiveKey(), t);
            }
        }

        for (T t : old) {
            for (OnUpdateListener<T> l : listeners)
                l.onRemove(t);
        }

        for (T t : list) {
            for (OnUpdateListener<T> l : listeners)
                l.onInsert(t);
        }
    }
}

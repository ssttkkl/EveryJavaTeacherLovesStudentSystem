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

public abstract class AbstractTable<P, T extends Item<P>> implements NotifiableTable<P, T> {

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

    private void fireInsert(T newItem) {
        listeners.forEach(l -> l.onInsert(newItem));
    }

    private void fireModify(T oldItem, T newItem) {
        listeners.forEach(l -> l.onModify(oldItem, newItem));
    }

    private void fireRemove(T removedItem) {
        listeners.forEach(l -> l.onRemove(removedItem));
    }

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
        return data.values().stream().filter(predicate).collect(Collectors.toList());
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
            fireModify(old, item);
        } else {
            fireInsert(item);
        }
        return old;
    }

    @Override
    public T remove(P primitiveKey) {
        T old = data.remove(primitiveKey);
        if (old != null) {
            System.out.println("Remove: " + old.toString());
            db.postSave();
            fireRemove(old);
        }
        return old;
    }

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
        int n = dis.readInt();
        ArrayList<T> list = new ArrayList<>(n);

        ItemSerializer<T> serializer = getItemSerializer();
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
            fireRemove(t);
        }
        for (T t : list) {
            fireInsert(t);
        }
    }
}

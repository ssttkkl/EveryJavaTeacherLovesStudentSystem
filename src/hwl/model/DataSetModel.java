package hwl.model;

import hwl.model.item.Item;

import java.util.*;
import java.util.stream.Collectors;

public abstract class DataSetModel<P, T extends Item<P>> {

    private final Comparator<P> primitiveComparator;

    public DataSetModel(Comparator<P> primitiveComparator) {
        this(Collections.emptyList(), primitiveComparator);
    }

    public DataSetModel(Collection<T> initialItems, Comparator<P> primitiveComparator) {
        this.primitiveComparator = primitiveComparator;

        List<T> sorted = initialItems.stream()
                .sorted(Comparator.comparing(Item::getPrimitiveKey, primitiveComparator))
                .collect(Collectors.toList());
        data.addAll(sorted);
    }

    private final Vector<T> data = new Vector<>();

    public int size() {
        return data.size();
    }

    public T get(int index) {
        return data.get(index);
    }

    private int lowerBound(P id) {
        int begin = 0, end = data.size();
        while (begin < end) {
            int mid = (begin + end) / 2;
            P midId = data.get(mid).getPrimitiveKey();
            if (primitiveComparator.compare(midId, id) >= 0) {
                end = mid;
            } else {
                begin = mid + 1;
            }
        }
        return begin;
    }

    void insert(T item) {
        int index = lowerBound(item.getPrimitiveKey());
        if (index < data.size() && data.get(index).getPrimitiveKey().equals(item.getPrimitiveKey()))
            throw new IllegalArgumentException("ID already exists.");

        data.add(index, item);
        fireItemAdded(index);
    }

    void modify(P id, T newItem) {
        int index = lowerBound(id);
        if (index >= data.size() || !data.get(index).getPrimitiveKey().equals(id))
            throw new IllegalArgumentException("ID does not exist.");

        data.set(index, newItem);
        fireItemChanged(index);
    }

    void remove(P id) {
        int index = lowerBound(id);
        if (index >= data.size() || !data.get(index).getPrimitiveKey().equals(id))
            throw new IllegalArgumentException("ID does not exist.");

        data.remove(index);
        fireItemRemoved(index);
    }

    protected abstract void fireItemAdded(int index);

    protected abstract void fireItemChanged(int index);

    protected abstract void fireItemRemoved(int index);
}

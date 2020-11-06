package hwl.model;

import hwl.model.item.Item;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.Collection;
import java.util.Comparator;
import java.util.Vector;

public class ItemListModel<P, T extends Item<P>> extends DataSetModel<P, T> implements ListModel<T> {

    public ItemListModel(Comparator<P> primitiveComparator) {
        super(primitiveComparator);
    }

    public ItemListModel(Collection<T> initialItems, Comparator<P> primitiveComparator) {
        super(initialItems, primitiveComparator);
    }

    private final Vector<ListDataListener> listDataListeners = new Vector<>();

    @Override
    public void addListDataListener(ListDataListener listener) {
        listDataListeners.add(listener);
    }

    @Override
    public void removeListDataListener(ListDataListener listener) {
        listDataListeners.remove(listener);
    }

    @Override
    protected void fireItemAdded(int index) {
        ListDataEvent e = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index);
        for (ListDataListener l : listDataListeners)
            l.intervalAdded(e);
    }

    @Override
    protected void fireItemChanged(int index) {
        ListDataEvent e = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, index, index);
        for (ListDataListener l : listDataListeners)
            l.contentsChanged(e);
    }

    @Override
    protected void fireItemRemoved(int index) {
        ListDataEvent e = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index, index);
        for (ListDataListener l : listDataListeners)
            l.intervalRemoved(e);
    }

    @Override
    public int getSize() {
        return size();
    }

    @Override
    public T getElementAt(int index) {
        return get(index);
    }
}

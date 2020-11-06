package hwl.model;

import hwl.model.item.Item;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.Collection;
import java.util.Comparator;
import java.util.Vector;
import java.util.function.Function;

public class ItemTableModel<P, T extends Item<P>> extends DataSetModel<P, T> implements TableModel {

    private final String[] columnNames;

    private final Function<T, ?>[] columnGetters;

    private final Class<?>[] columnClasses;

    public ItemTableModel(String[] columnNames,
                          Function<T, ?>[] columnGetters,
                          Class<?>[] columnClasses,
                          Collection<T> initialItems, Comparator<P> primitiveComparator) {
        super(initialItems, primitiveComparator);

        assert columnNames.length == columnGetters.length;
        assert columnGetters.length == columnClasses.length;

        this.columnNames = columnNames;
        this.columnGetters = columnGetters;
        this.columnClasses = columnClasses;
    }

    @Override
    public int getRowCount() {
        return size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return columnGetters[columnIndex].apply(get(rowIndex));
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException();
    }

    private final Vector<TableModelListener> listeners = new Vector<>();

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    @Override
    protected void fireItemAdded(int row) {
        TableModelEvent e = new TableModelEvent(this, row, row, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
        listeners.forEach(l -> l.tableChanged(e));
    }

    @Override
    protected void fireItemChanged(int row) {
        TableModelEvent e = new TableModelEvent(this, row, row, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE);
        listeners.forEach(l -> l.tableChanged(e));
    }

    @Override
    protected void fireItemRemoved(int row) {
        TableModelEvent e = new TableModelEvent(this, row, row, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        listeners.forEach(l -> l.tableChanged(e));
    }

    public void notifyColumnChanged(int column) {
        TableModelEvent e = new TableModelEvent(this, 0, getRowCount() - 1, column, TableModelEvent.UPDATE);
        listeners.forEach(l -> l.tableChanged(e));
    }
}

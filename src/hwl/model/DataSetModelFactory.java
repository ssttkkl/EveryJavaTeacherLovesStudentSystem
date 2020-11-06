package hwl.model;

import hwl.model.item.Item;
import hwl.model.table.Table;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

public class DataSetModelFactory {

    public static <P extends Comparable<P>, T extends Item<P>>
    ItemListModel<P, T> newListModel(Table<P, T> table) {
        return newListModel(table, Comparator.comparing(o -> o), o -> true);
    }

    public static <P, T extends Item<P>>
    ItemListModel<P, T> newListModel(Table<P, T> table,
                                     Comparator<P> primitiveComparor) {
        return newListModel(table, primitiveComparor, o -> true);
    }

    public static <P extends Comparable<P>, T extends Item<P>>
    ItemListModel<P, T> newListModel(Table<P, T> table,
                                     Predicate<T> filter) {
        return newListModel(table, Comparator.comparing(o -> o), filter);
    }

    public static <P, T extends Item<P>>
    ItemListModel<P, T> newListModel(Table<P, T> table,
                                     Comparator<P> primitiveComparor,
                                     Predicate<T> filter) {
        ItemListModel<P, T> model = new ItemListModel<P, T>(table.get(filter), primitiveComparor);
        makeModel(model, table, filter);
        return model;
    }

    public static <P extends Comparable<P>, T extends Item<P>>
    ItemComboBoxModel<P, T> newComboBoxModel(Table<P, T> table) {
        return newComboBoxModel(table, Comparator.comparing(o -> o), o -> true);
    }

    public static <P, T extends Item<P>>
    ItemComboBoxModel<P, T> newComboBoxModel(Table<P, T> table,
                                             Comparator<P> primitiveComparor) {
        return newComboBoxModel(table, primitiveComparor, o -> true);
    }

    public static <P extends Comparable<P>, T extends Item<P>>
    ItemComboBoxModel<P, T> newComboBoxModel(Table<P, T> table,
                                             Predicate<T> filter) {
        return newComboBoxModel(table, Comparator.comparing(o -> o), filter);
    }

    public static <P, T extends Item<P>>
    ItemComboBoxModel<P, T> newComboBoxModel(Table<P, T> table,
                                             Comparator<P> primitiveComparor,
                                             Predicate<T> filter) {
        ItemComboBoxModel<P, T> model = new ItemComboBoxModel<P, T>(table.get(filter), primitiveComparor);
        makeModel(model, table, filter);
        return model;
    }

    public static <P extends Comparable<P>, T extends Item<P>>
    ItemTableModel<P, T> newTableModel(String[] columnNames,
                                       Function<T, ?>[] columnGetters,
                                       Class<?>[] columnClasses,
                                       Table<P, T> table) {
        return newTableModel(columnNames, columnGetters, columnClasses,
                table, Comparator.comparing(o -> o), o -> true);
    }

    public static <P, T extends Item<P>>
    ItemTableModel<P, T> newTableModel(String[] columnNames,
                                       Function<T, ?>[] columnGetters,
                                       Class<?>[] columnClasses,
                                       Table<P, T> table,
                                       Comparator<P> primitiveComparor) {
        return newTableModel(columnNames, columnGetters, columnClasses,
                table, primitiveComparor, o -> true);
    }

    public static <P extends Comparable<P>, T extends Item<P>>
    ItemTableModel<P, T> newTableModel(String[] columnNames,
                                       Function<T, ?>[] columnGetters,
                                       Class<?>[] columnClasses,
                                       Table<P, T> table,
                                       Predicate<T> filter) {
        return newTableModel(columnNames, columnGetters, columnClasses,
                table, Comparator.comparing(o -> o), filter);
    }

    public static <P, T extends Item<P>>
    ItemTableModel<P, T> newTableModel(String[] columnNames,
                                       Function<T, ?>[] columnGetters,
                                       Class<?>[] columnClasses,
                                       Table<P, T> table,
                                       Comparator<P> primitiveComparor,
                                       Predicate<T> filter) {
        ItemTableModel<P, T> model = new ItemTableModel<P, T>(columnNames, columnGetters, columnClasses, table.get(filter), primitiveComparor);
        makeModel(model, table, filter);
        return model;
    }

    private static <P, T extends Item<P>>
    void makeModel(DataSetModel<P, T> model,
                   Table<P, T> table,
                   Predicate<T> filter) {
        table.addOnUpdateListener(new Table.OnUpdateListener<>() {
            @Override
            public void onInsert(T newItem) {
                if (filter.test(newItem))
                    model.insert(newItem);
            }

            @Override
            public void onModify(T oldItem, T newItem) {
                boolean oldTest = filter.test(oldItem);
                boolean newTest = filter.test(newItem);
                if (oldTest) {
                    if (newTest)
                        model.modify(newItem.getId(), newItem);
                    else
                        model.remove(newItem.getId());
                } else if (newTest)
                    model.insert(newItem);
            }

            @Override
            public void onRemove(T removedItem) {
                if (filter.test(removedItem))
                    model.remove(removedItem.getId());
            }
        });
    }
}

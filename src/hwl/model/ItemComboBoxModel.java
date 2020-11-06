package hwl.model;

import hwl.model.item.Item;

import javax.swing.*;
import java.util.Collection;
import java.util.Comparator;

public class ItemComboBoxModel<P, T extends Item<P>> extends ItemListModel<P, T> implements ComboBoxModel<T> {

    public ItemComboBoxModel(Comparator<P> primitiveComparator) {
        super(primitiveComparator);
    }

    public ItemComboBoxModel(Collection<T> initialItems, Comparator<P> primitiveComparator) {
        super(initialItems, primitiveComparator);
    }

    private Object selectedItem = null;

    @Override
    public void setSelectedItem(Object anItem) {
        selectedItem = anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selectedItem;
    }
}

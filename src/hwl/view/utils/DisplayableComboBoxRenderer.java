package hwl.view.utils;

import hwl.model.Displayable;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

public class DisplayableComboBoxRenderer<T extends Displayable> extends BasicComboBoxRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value != null)
            setText(((Displayable) value).getDisplayText());
        return this;
    }
}


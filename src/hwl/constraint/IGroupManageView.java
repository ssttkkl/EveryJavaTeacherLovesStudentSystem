package hwl.constraint;

import hwl.model.ItemTableModel;
import hwl.model.item.Group;

public interface IGroupManageView {
    void setTableModel(ItemTableModel<Integer, Group> model);

    void setEditButtonEnabled(boolean isEnabled);

    void setRemoveButtonEnabled(boolean isEnabled);

    int getSelectedRow();

    String showAddGroupDialog();

    String showRenameGroupDialog(String oldName);

    boolean showConfirmRemoveGroupDialog();

    void showStudentManageWindow(int groupId);
}

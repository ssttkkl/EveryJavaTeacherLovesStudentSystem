package hwl.constraint;

import hwl.model.item.Score;

import javax.swing.table.TableModel;

public interface IScoreManageView {

    void setTitle(String title);

    void setTableModel(TableModel model);

    void setEditButtonEnabled(boolean isEnabled);

    void setRemoveButtonEnabled(boolean isEnabled);

    int getSelectedRow();

    void showAddDialog(int studentId, IAddScoreView.OnSaveListener callback);

    String showEditDialog(Score s);

    boolean showConfirmRemoveDialog();

    void showErrorDialog(String message);
}

package hwl.constraint;

import hwl.model.item.Student;

import javax.swing.table.TableModel;

public interface IStudentManageView {

    void setTitle(String title);

    void setTableModel(TableModel model);

    void setEditButtonEnabled(boolean isEnabled);

    void setRemoveButtonEnabled(boolean isEnabled);

    int getSelectedRow();

    void showScoreManageWindow(int studentId);

    void showAddStudentDialog(IEditStudentView.OnSaveListener callback);

    void showEditStudentDialog(Student s, IEditStudentView.OnSaveListener callback);

    boolean showConfirmRemoveStudentDialog();
}

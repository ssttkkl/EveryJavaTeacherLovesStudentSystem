package hwl.constraint;

import hwl.model.ItemTableModel;
import hwl.model.item.Course;

public interface ICourseManageView {
    void setTableModel(ItemTableModel<Integer, Course> model);

    void setEditButtonEnabled(boolean isEnabled);

    void setRemoveButtonEnabled(boolean isEnabled);

    int getSelectedRow();

    void showAddCourseDialog(IEditCourseView.OnSaveListener callback);

    void showEditCourseDialog(Course s, IEditCourseView.OnSaveListener callback);

    boolean showConfirmRemoveCourseDialog();
}

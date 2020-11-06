package hwl.constraint;

import hwl.model.item.Course;

import javax.swing.*;

public interface IAddScoreView {

    interface OnSaveListener {
        void onSave(int courseId, double point);
    }

    void setTitle(String title);

    void close();

    void setCourseComboBoxModel(ComboBoxModel<Course> model);

    void showErrorDialog(String message);

    Course getScoreCourse();

    String getScorePoint();
}

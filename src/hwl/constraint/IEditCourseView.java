package hwl.constraint;

public interface IEditCourseView {

    interface OnSaveListener {
        void onSave(String name, String number, double point);
    }

    int ADD = 0;

    int EDIT = 1;

    void setTitle(String title);

    void close();

    void showErrorDialog(String message);

    String getCourseName();

    String getCourseNumber();

    String getCoursePoint();
}

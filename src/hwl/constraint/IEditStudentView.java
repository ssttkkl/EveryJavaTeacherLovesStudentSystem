package hwl.constraint;

import hwl.model.Sex;

public interface IEditStudentView {

    interface OnSaveListener {
        void onSave(String name, String number, Sex sex);
    }

    int ADD = 0;

    int EDIT = 1;

    void setTitle(String title);

    void close();

    String getStudentName();

    String getStudentNumber();

    Sex getStudentSex();
}

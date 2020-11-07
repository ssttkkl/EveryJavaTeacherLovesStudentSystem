package hwl.controller;

import hwl.view.EditCourseDialog;

import java.awt.event.ActionEvent;

public class EditCourseController {

    private final EditCourseDialog view;

    private final EditCourseDialog.OnSaveListener callback;

    public EditCourseController(EditCourseDialog view, int mode, EditCourseDialog.OnSaveListener callback) {
        this.view = view;
        this.callback = callback;

        switch (mode) {
            case EditCourseDialog.ADD:
                this.view.setTitle("添加课程");
                break;
            case EditCourseDialog.EDIT:
                this.view.setTitle("编辑课程");
                break;
        }
    }

    public void onClickOkButton(ActionEvent e) {
        if (callback != null) {
            try {
                String dstr = view.getCoursePoint();
                double d;
                try {
                    d = Double.parseDouble(dstr);
                } catch (NumberFormatException exc) {
                    throw new IllegalStateException("请输入正确的学分");
                }
                callback.onSave(view.getCourseName(), view.getCourseNumber(), d);
                view.close();
            } catch (IllegalStateException exc) {
                view.showErrorDialog(exc.getMessage());
            }
        }
    }

    public void onClickCancelButton(ActionEvent e) {
        view.close();
    }
}

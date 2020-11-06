package hwl.controller;

import hwl.constraint.IEditCourseController;
import hwl.constraint.IEditCourseView;

import java.awt.event.ActionEvent;

public class EditCourseController implements IEditCourseController {

    private final IEditCourseView view;

    private final IEditCourseView.OnSaveListener callback;

    public EditCourseController(IEditCourseView view, int mode, IEditCourseView.OnSaveListener callback) {
        this.view = view;
        this.callback = callback;

        switch (mode) {
            case IEditCourseView.ADD:
                this.view.setTitle("添加课程");
                break;
            case IEditCourseView.EDIT:
                this.view.setTitle("编辑课程");
                break;
        }
    }

    @Override
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

    @Override
    public void onClickCancelButton(ActionEvent e) {
        view.close();
    }
}

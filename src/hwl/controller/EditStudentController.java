package hwl.controller;

import hwl.constraint.IEditStudentController;
import hwl.constraint.IEditStudentView;

import java.awt.event.ActionEvent;

public class EditStudentController implements IEditStudentController {

    private final IEditStudentView view;

    private final IEditStudentView.OnSaveListener callback;

    public EditStudentController(IEditStudentView view, int mode, IEditStudentView.OnSaveListener callback) {
        this.view = view;
        this.callback = callback;

        switch (mode) {
            case IEditStudentView.ADD:
                this.view.setTitle("添加学生");
                break;
            case IEditStudentView.EDIT:
                this.view.setTitle("编辑学生");
                break;
        }
    }

    @Override
    public void onClickOkButton(ActionEvent e) {
        if (callback != null)
            callback.onSave(view.getStudentName(), view.getStudentNumber(), view.getStudentSex());
        view.close();
    }

    @Override
    public void onClickCancelButton(ActionEvent e) {
        view.close();
    }
}

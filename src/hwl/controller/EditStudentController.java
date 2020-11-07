package hwl.controller;

import hwl.view.EditStudentDialog;

import java.awt.event.ActionEvent;

public class EditStudentController {

    private final EditStudentDialog view;

    private final EditStudentDialog.OnSaveListener callback;

    public EditStudentController(EditStudentDialog view, int mode, EditStudentDialog.OnSaveListener callback) {
        this.view = view;
        this.callback = callback;

        switch (mode) {
            case EditStudentDialog.ADD:
                this.view.setTitle("添加学生");
                break;
            case EditStudentDialog.EDIT:
                this.view.setTitle("编辑学生");
                break;
        }
    }

    public void onClickOkButton(ActionEvent e) {
        if (callback != null)
            callback.onSave(view.getStudentName(), view.getStudentNumber(), view.getStudentSex());
        view.close();
    }

    public void onClickCancelButton(ActionEvent e) {
        view.close();
    }
}

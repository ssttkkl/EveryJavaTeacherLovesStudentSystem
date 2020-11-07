package hwl.controller;

import hwl.model.DataSetModelFactory;
import hwl.model.Database;
import hwl.model.IntPair;
import hwl.model.item.Course;
import hwl.view.AddScoreDialog;

import java.awt.event.ActionEvent;

public class AddScoreController {

    private final AddScoreDialog view;

    private final AddScoreDialog.OnSaveListener callback;

    public AddScoreController(AddScoreDialog view, int studentId, AddScoreDialog.OnSaveListener callback) {
        this.view = view;
        this.callback = callback;

        this.view.setCourseComboBoxModel(DataSetModelFactory.newComboBoxModel(Database.getInstance().courses,
                c -> !Database.getInstance().scores.contains(new IntPair(studentId, c.id))));
    }

    public void onClickOkButton(ActionEvent e) {
        if (callback != null) {
            try {
                String dstr = view.getScorePoint();
                double d;
                try {
                    d = Double.parseDouble(dstr);
                } catch (NumberFormatException exc) {
                    throw new IllegalStateException("请输入正确的成绩");
                }

                Course c = view.getScoreCourse();
                if (c == null)
                    throw new IllegalStateException("请选择课程");

                callback.onSave(c.id, d);
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

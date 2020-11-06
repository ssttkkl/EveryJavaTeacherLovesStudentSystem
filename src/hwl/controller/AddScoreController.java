package hwl.controller;

import hwl.constraint.IAddScoreController;
import hwl.constraint.IAddScoreView;
import hwl.model.DataSetModelFactory;
import hwl.model.Database;
import hwl.model.IntPair;
import hwl.model.item.Course;

import java.awt.event.ActionEvent;

public class AddScoreController implements IAddScoreController {

    private final IAddScoreView view;

    private final IAddScoreView.OnSaveListener callback;

    public AddScoreController(IAddScoreView view, int studentId, IAddScoreView.OnSaveListener callback) {
        this.view = view;
        this.callback = callback;

        this.view.setCourseComboBoxModel(DataSetModelFactory.newComboBoxModel(Database.getInstance().courses,
                c -> !Database.getInstance().scores.contains(new IntPair(studentId, c.getId()))));
    }

    @Override
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

                callback.onSave(c.getId(), d);
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

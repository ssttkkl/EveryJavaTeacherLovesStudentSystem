package hwl.controller;

import hwl.model.DataSetModelFactory;
import hwl.model.Database;
import hwl.model.IntPair;
import hwl.model.ItemTableModel;
import hwl.model.item.Score;
import hwl.model.item.Student;
import hwl.view.ScoreManageWindow;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.util.Comparator;
import java.util.function.Function;

public class ScoreManageController {

    private final ScoreManageWindow view;

    private final int studentId;

    private final ItemTableModel<IntPair, Score> tableModel;

    private Student getStudent() {
        return Database.getInstance().students.get(studentId);
    }

    public ScoreManageController(ScoreManageWindow view, int studentId) {
        this.view = view;
        this.studentId = studentId;

        this.view.setTitle(String.format("成绩管理（学生：%s）", getStudent().name));

        tableModel = DataSetModelFactory.newTableModel(
                new String[]{"课程名称", "课程编号", "学分", "成绩"},
                new Function[]{
                        s -> ((Score) s).getCourse().name,
                        s -> ((Score) s).getCourse().number,
                        s -> ((Score) s).getCourse().point,
                        s -> ((Score) s).point
                },
                new Class[]{
                        String.class,
                        String.class,
                        Double.class,
                        Double.class
                },
                Database.getInstance().scores,
                Comparator.comparing(o -> o),
                s -> s.studentId == studentId
        );
        this.view.setTableModel(tableModel);

        this.view.setEditButtonEnabled(false);
        this.view.setRemoveButtonEnabled(false);
    }

    public void onClickAddButton(ActionEvent e) {
        view.showAddDialog(studentId, (courseId, point) ->
                Database.getInstance().scores.emplace(studentId, courseId, point));
    }

    public void onClickEditButton(ActionEvent e) {
        int index = view.getSelectedRow();
        Score s = tableModel.get(index);

        String pointStr = view.showEditDialog(s);
        if (pointStr == null)
            return;

        try {
            double point = Double.parseDouble(pointStr);
            Database.getInstance().scores.emplace(s.studentId, s.courseId, point);
        } catch (NumberFormatException exception) {
            view.showErrorDialog("请输入正确的成绩");
        }
    }

    public void onClickRemoveButton(ActionEvent e) {
        int index = view.getSelectedRow();
        Score s = tableModel.get(index);

        if (s != null && view.showConfirmRemoveDialog()) {
            Database.getInstance().scores.remove(s.getPrimitiveKey());
        }
    }

    public void onSelectionChanged(ListSelectionEvent e) {
        view.setEditButtonEnabled(true);
        view.setRemoveButtonEnabled(true);
    }
}

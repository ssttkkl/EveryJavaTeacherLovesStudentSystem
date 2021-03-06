package hwl.controller;

import hwl.model.DataSetModelFactory;
import hwl.model.Database;
import hwl.model.ItemTableModel;
import hwl.model.info.CourseInfo;
import hwl.model.item.Course;
import hwl.view.CourseManagePanel;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CourseManageController {

    private final CourseManagePanel view;

    private final ItemTableModel<Integer, Course> tableModel;

    private final Map<Integer, CourseInfo> courseInfos = new HashMap<>();

    public CourseManageController(CourseManagePanel view) {
        this.view = view;

        tableModel = DataSetModelFactory.newTableModel(
                new String[]{"课程名称", "课程编号", "学分", "平均成绩"},
                new Function[]{
                        c -> ((Course) c).name,
                        c -> ((Course) c).number,
                        c -> ((Course) c).point,
                        c -> {
                            CourseInfo info = courseInfos.get(((Course) c).id);
                            return info != null ? info.averageScore : null;
                        }
                },
                new Class[]{
                        String.class,
                        String.class,
                        Double.class,
                        Double.class
                },
                Database.getInstance().courses
        );
        this.view.setTableModel(tableModel);

        this.view.setEditButtonEnabled(false);
        this.view.setRemoveButtonEnabled(false);
    }

    public void onClickCalcButton(ActionEvent e) {
        courseInfos.clear();
        for (int i = 0; i < tableModel.size(); i++) {
            Course c = tableModel.get(i);
            courseInfos.put(c.id, CourseInfo.calc(c.getPrimitiveKey()));
        }
        tableModel.notifyColumnChanged(3);
    }

    public void onClickAddButton(ActionEvent e) {
        view.showAddCourseDialog((name, number, point) ->
                Database.getInstance().courses.emplace(name, number, point));
    }

    public void onClickEditButton(ActionEvent e) {
        int index = view.getSelectedRow();
        Course c = tableModel.get(index);
        view.showEditCourseDialog(c, (name, number, point) ->
                Database.getInstance().courses.emplace(c.id, name, number, point));
    }

    public void onClickRemoveButton(ActionEvent e) {
        int index = view.getSelectedRow();
        Course c = tableModel.get(index);
        if (view.showConfirmRemoveCourseDialog())
            Database.getInstance().courses.remove(c.id);
    }

    public void onSelectionChanged(ListSelectionEvent e) {
        this.view.setEditButtonEnabled(true);
        this.view.setRemoveButtonEnabled(true);
    }
}

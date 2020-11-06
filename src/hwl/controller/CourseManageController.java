package hwl.controller;

import hwl.constraint.ICourseManageController;
import hwl.constraint.ICourseManageView;
import hwl.model.DataSetModelFactory;
import hwl.model.Database;
import hwl.model.ItemTableModel;
import hwl.model.info.CourseInfo;
import hwl.model.item.Course;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CourseManageController implements ICourseManageController {

    private final ICourseManageView view;

    private final ItemTableModel<Integer, Course> tableModel;

    private final Map<Integer, CourseInfo> courseInfos = new HashMap<>();

    public CourseManageController(ICourseManageView view) {
        this.view = view;

        tableModel = DataSetModelFactory.newTableModel(
                new String[]{"课程名称", "课程编号", "学分", "平均成绩"},
                new Function[]{
                        c -> ((Course) c).getName(),
                        c -> ((Course) c).getNumber(),
                        c -> ((Course) c).getPoint(),
                        c -> {
                            CourseInfo info = courseInfos.get(((Course) c).getId());
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

    @Override
    public void onClickCalcButton(ActionEvent e) {
        courseInfos.clear();
        for (int i = 0; i < tableModel.size(); i++) {
            Course c = tableModel.get(i);
            courseInfos.put(c.getId(), CourseInfo.calc(c.getId()));
        }
        tableModel.notifyColumnChanged(3);
    }

    @Override
    public void onClickAddButton(ActionEvent e) {
        view.showAddCourseDialog((name, number, point) ->
                Database.getInstance().courses.emplace(name, number, point));
    }

    @Override
    public void onClickEditButton(ActionEvent e) {
        int index = view.getSelectedRow();
        Course c = tableModel.get(index);
        view.showEditCourseDialog(c, (name, number, point) ->
                Database.getInstance().courses.put(new Course(c.getId(), name, number, point)));
    }

    @Override
    public void onClickRemoveButton(ActionEvent e) {
        int index = view.getSelectedRow();
        Course c = tableModel.get(index);
        if (view.showConfirmRemoveCourseDialog())
            Database.getInstance().groups.remove(c.getId());
    }

    @Override
    public void onSelectionChanged(ListSelectionEvent e) {
        this.view.setEditButtonEnabled(true);
        this.view.setRemoveButtonEnabled(true);
    }
}

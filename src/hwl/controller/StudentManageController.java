package hwl.controller;

import hwl.constraint.IStudentManageController;
import hwl.constraint.IStudentManageView;
import hwl.model.DataSetModelFactory;
import hwl.model.Database;
import hwl.model.ItemTableModel;
import hwl.model.info.StudentInfo;
import hwl.model.item.Group;
import hwl.model.item.Student;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class StudentManageController implements IStudentManageController {

    private final IStudentManageView view;

    private final int groupId;

    private final ItemTableModel<Integer, Student> tableModel;

    private final Map<Integer, StudentInfo> studentInfos = new HashMap<>();

    private Group getGroup() {
        return Database.getInstance().groups.get(groupId);
    }

    public StudentManageController(IStudentManageView view, int groupId) {
        this.view = view;
        this.groupId = groupId;

        this.view.setTitle(String.format("学生管理（班级：%s）", getGroup().name));

        tableModel = DataSetModelFactory.newTableModel(
                new String[]{"姓名", "学号", "性别", "总成绩", "平均成绩", "总学分"},
                new Function[]{
                        stu -> ((Student) stu).name,
                        stu -> ((Student) stu).number,
                        stu -> ((Student) stu).sex.getDisplayText(),
                        stu -> {
                            StudentInfo info = studentInfos.get(((Student) stu).id);
                            return info != null ? info.totalScore : null;
                        },
                        stu -> {
                            StudentInfo info = studentInfos.get(((Student) stu).id);
                            return info != null ? info.averageScore : null;
                        },
                        stu -> {
                            StudentInfo info = studentInfos.get(((Student) stu).id);
                            return info != null ? info.totalPoint : null;
                        }
                },
                new Class[]{
                        String.class,
                        String.class,
                        String.class,
                        Double.class,
                        Double.class,
                        Double.class
                },
                Database.getInstance().students,
                Comparator.comparing(o -> o),
                stu -> stu.groupId == groupId
        );
        this.view.setTableModel(tableModel);

        this.view.setEditButtonEnabled(false);
        this.view.setRemoveButtonEnabled(false);
    }

    @Override
    public void onDoubleClickTable(int row) {
        Student s = tableModel.get(row);
        view.showScoreManageWindow(s.id);
    }

    @Override
    public void onClickAddButton(ActionEvent e) {
        view.showAddStudentDialog(
                (name, number, sex) -> Database.getInstance().students.emplace(name, number, sex, groupId));
    }

    @Override
    public void onClickEditButton(ActionEvent e) {
        int index = view.getSelectedRow();
        Student s = tableModel.get(index);
        view.showEditStudentDialog(s, (name, number, sex) -> Database.getInstance().students.put(new Student(s.id, name, number, sex, s.groupId)));
    }

    @Override
    public void onClickRemoveButton(ActionEvent e) {
        int index = view.getSelectedRow();
        Student s = tableModel.get(index);
        if (s != null && view.showConfirmRemoveStudentDialog()) {
            Database.getInstance().students.remove(s.id);
        }
    }

    @Override
    public void onSelectionChanged(ListSelectionEvent e) {
        view.setEditButtonEnabled(true);
        view.setRemoveButtonEnabled(true);
    }

    @Override
    public void onClickCalcButton(ActionEvent e) {
        studentInfos.clear();
        for (int i = 0; i < tableModel.size(); i++) {
            Student s = tableModel.get(i);
            studentInfos.put(s.id, StudentInfo.calc(s.id));
        }
        tableModel.notifyColumnChanged(3);
        tableModel.notifyColumnChanged(4);
        tableModel.notifyColumnChanged(5);
    }
}

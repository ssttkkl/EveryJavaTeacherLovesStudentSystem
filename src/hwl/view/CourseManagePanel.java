package hwl.view;

import hwl.controller.CourseManageController;
import hwl.model.ItemTableModel;
import hwl.model.item.Course;

import javax.swing.*;
import java.awt.*;

public class CourseManagePanel extends JPanel {

    private final Frame parent;

    private final JTable table = new JTable();

    private final JButton editButton = new JButton("编辑课程");
    private final JButton removeButton = new JButton("删除课程");

    public CourseManagePanel(Frame parent) {
        this.parent = parent;
        CourseManageController controller = new CourseManageController(this);

        this.setLayout(new BorderLayout());

        // BEGIN draw operation buttons
        JPanel optPanel = new JPanel();
        optPanel.setLayout(new FlowLayout());

        JButton calcButton = new JButton("计算");
        calcButton.addActionListener(controller::onClickCalcButton);
        optPanel.add(calcButton);
        JButton addButton = new JButton("添加课程");
        addButton.addActionListener(controller::onClickAddButton);
        optPanel.add(addButton);
        editButton.addActionListener(controller::onClickEditButton);
        optPanel.add(editButton);
        removeButton.addActionListener(controller::onClickRemoveButton);
        optPanel.add(removeButton);

        this.add(optPanel, BorderLayout.NORTH);
        // END draw operation buttons

        // BEGIN draw list

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(controller::onSelectionChanged);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.add(scrollPane, BorderLayout.CENTER);
        // END draw list
    }

    public void setTableModel(ItemTableModel<Integer, Course> model) {
        table.setModel(model);
    }

    public void setEditButtonEnabled(boolean isEnabled) {
        editButton.setEnabled(isEnabled);
    }

    public void setRemoveButtonEnabled(boolean isEnabled) {
        removeButton.setEnabled(isEnabled);
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    public void showAddCourseDialog(EditCourseDialog.OnSaveListener callback) {
        EditCourseDialog d = new EditCourseDialog(parent, EditCourseDialog.ADD, callback);
        d.setVisible(true);
    }

    public void showEditCourseDialog(Course s, EditCourseDialog.OnSaveListener callback) {
        EditCourseDialog d = new EditCourseDialog(parent, EditCourseDialog.EDIT,
                s.name, s.number, s.point, callback);
        d.setVisible(true);
    }

    public boolean showConfirmRemoveCourseDialog() {
        return JOptionPane.showConfirmDialog(this,
                "确认删除吗？",
                "删除课程",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}

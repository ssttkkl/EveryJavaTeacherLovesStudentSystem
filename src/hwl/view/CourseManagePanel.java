package hwl.view;

import hwl.constraint.ICourseManageController;
import hwl.constraint.ICourseManageView;
import hwl.constraint.IEditCourseView;
import hwl.controller.CourseManageController;
import hwl.model.ItemTableModel;
import hwl.model.item.Course;

import javax.swing.*;
import java.awt.*;

public class CourseManagePanel extends JPanel implements ICourseManageView {

    private final Frame parent;
    private final ICourseManageController controller;

    private final JTable table = new JTable();

    private final JButton calcButton = new JButton("计算");
    private final JButton addButton = new JButton("添加课程");
    private final JButton editButton = new JButton("编辑课程");
    private final JButton removeButton = new JButton("删除课程");

    public CourseManagePanel(Frame parent) {
        this.parent = parent;
        this.controller = new CourseManageController(this);

        this.setLayout(new BorderLayout());

        // BEGIN draw operation buttons
        JPanel optPanel = new JPanel();
        optPanel.setLayout(new FlowLayout());

        calcButton.addActionListener(controller::onClickCalcButton);
        optPanel.add(calcButton);
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

    @Override
    public void setTableModel(ItemTableModel<Integer, Course> model) {
        table.setModel(model);
    }

    @Override
    public void setEditButtonEnabled(boolean isEnabled) {
        editButton.setEnabled(isEnabled);
    }

    @Override
    public void setRemoveButtonEnabled(boolean isEnabled) {
        removeButton.setEnabled(isEnabled);
    }

    @Override
    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    @Override
    public void showAddCourseDialog(IEditCourseView.OnSaveListener callback) {
        EditCourseDialog d = new EditCourseDialog(parent, IEditCourseView.ADD, callback);
        d.setVisible(true);
    }

    @Override
    public void showEditCourseDialog(Course s, IEditCourseView.OnSaveListener callback) {
        EditCourseDialog d = new EditCourseDialog(parent, IEditCourseView.EDIT,
                s.getName(), s.getNumber(), s.getPoint(), callback);
        d.setVisible(true);
    }

    @Override
    public boolean showConfirmRemoveCourseDialog() {
        return JOptionPane.showConfirmDialog(this,
                "确认删除吗？",
                "删除课程",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}

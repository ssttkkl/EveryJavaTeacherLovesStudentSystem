package hwl.view;

import hwl.controller.StudentManageController;
import hwl.model.item.Student;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StudentManageWindow extends JFrame {

    private final StudentManageController controller;

    private final JTable table = new JTable();

    private final JButton editButton = new JButton("编辑学生");
    private final JButton removeButton = new JButton("删除学生");

    public StudentManageWindow(int groupId) {
        controller = new StudentManageController(this, groupId);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // BEGIN draw operation buttons
        JPanel optPanel = new JPanel();
        optPanel.setLayout(new FlowLayout());

        JButton calcButton = new JButton("计算");
        calcButton.addActionListener(controller::onClickCalcButton);
        optPanel.add(calcButton);
        JButton addButton = new JButton("添加学生");
        addButton.addActionListener(controller::onClickAddButton);
        optPanel.add(addButton);
        editButton.addActionListener(controller::onClickEditButton);
        optPanel.add(editButton);
        removeButton.addActionListener(controller::onClickRemoveButton);
        optPanel.add(removeButton);

        this.add(optPanel, BorderLayout.NORTH);
        // END draw operation buttons

        // BEGIN draw table
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(controller::onSelectionChanged);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    controller.onDoubleClickTable(table.getSelectedRow());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        this.add(scrollPane, BorderLayout.CENTER);
        // END draw list

        this.setSize(800, 600);
    }

    public void setTableModel(TableModel model) {
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

    public void showScoreManageWindow(int studentId) {
        ScoreManageWindow w = new ScoreManageWindow(studentId);
        w.setVisible(true);
    }

    public void showAddStudentDialog(EditStudentDialog.OnSaveListener callback) {
        EditStudentDialog d = new EditStudentDialog(this, EditStudentDialog.ADD, callback);
        d.setVisible(true);
    }

    public void showEditStudentDialog(Student s, EditStudentDialog.OnSaveListener callback) {
        EditStudentDialog d = new EditStudentDialog(this, EditStudentDialog.EDIT,
                s.name, s.number, s.sex, callback);
        d.setVisible(true);
    }

    public boolean showConfirmRemoveStudentDialog() {
        return JOptionPane.showConfirmDialog(this,
                "确认删除吗？",
                "删除学生",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}

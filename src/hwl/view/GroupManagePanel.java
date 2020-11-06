package hwl.view;

import hwl.constraint.IGroupManageController;
import hwl.constraint.IGroupManageView;
import hwl.controller.GroupManageController;
import hwl.model.ItemTableModel;
import hwl.model.item.Group;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GroupManagePanel extends JPanel implements IGroupManageView {

    private final IGroupManageController controller;

    private final JTable table = new JTable();

    private final JButton editButton = new JButton("编辑班级");
    private final JButton removeButton = new JButton("删除班级");

    public GroupManagePanel() {
        controller = new GroupManageController(this);

        this.setLayout(new BorderLayout());

        // BEGIN draw operation buttons
        JPanel optPanel = new JPanel();
        optPanel.setLayout(new FlowLayout());

        JButton calcButton = new JButton("计算");
        calcButton.addActionListener(controller::onClickCalcButton);
        optPanel.add(calcButton);
        JButton addButton = new JButton("添加班级");
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
    }

    @Override
    public void setTableModel(ItemTableModel<Integer, Group> model) {
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
    public String showAddGroupDialog() {
        return JOptionPane.showInputDialog(this,
                "班级名称",
                "添加班级",
                JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public String showRenameGroupDialog(String oldName) {
        return (String) JOptionPane.showInputDialog(this,
                "班级名称",
                "编辑班级",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                oldName);
    }

    @Override
    public boolean showConfirmRemoveGroupDialog() {
        return JOptionPane.showConfirmDialog(this,
                "确认删除吗？",
                "删除班级",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    @Override
    public void showStudentManageWindow(int groupId) {
        StudentManageWindow w = new StudentManageWindow(groupId);
        w.setVisible(true);
    }
}

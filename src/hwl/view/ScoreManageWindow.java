package hwl.view;

import hwl.constraint.IAddScoreView;
import hwl.constraint.IScoreManageController;
import hwl.constraint.IScoreManageView;
import hwl.controller.ScoreManageController;
import hwl.model.item.Score;
import hwl.view.utils.DisplayableListCellRenderer;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ScoreManageWindow extends JFrame implements IScoreManageView {

    private final IScoreManageController controller;

    private final JTable table = new JTable();

    private final JButton addButton = new JButton("添加成绩");
    private final JButton editButton = new JButton("编辑成绩");
    private final JButton removeButton = new JButton("删除成绩");

    public ScoreManageWindow(int studentId) {
        controller = new ScoreManageController(this, studentId);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // BEGIN draw operation buttons
        JPanel optPanel = new JPanel();
        optPanel.setLayout(new FlowLayout());

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

        this.setSize(800, 600);
    }

    @Override
    public void setTableModel(TableModel model) {
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
    public void showAddDialog(int studentId, IAddScoreView.OnSaveListener callback) {
        AddScoreDialog d = new AddScoreDialog(this, studentId, callback);
        d.setVisible(true);
    }

    @Override
    public String showEditDialog(Score s) {
        return (String) JOptionPane.showInputDialog(this,
                "成绩",
                "编辑成绩",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                String.format("%.1f", s.getPoint()));
    }

    @Override
    public boolean showConfirmRemoveDialog() {
        return JOptionPane.showConfirmDialog(this,
                "确认删除吗？",
                "删除学生",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    @Override
    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "错误",
                JOptionPane.ERROR_MESSAGE);
    }
}

package hwl.view;

import hwl.controller.ScoreManageController;
import hwl.model.item.Score;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

public class ScoreManageWindow extends JFrame {

    private final JTable table = new JTable();

    private final JButton editButton = new JButton("编辑成绩");
    private final JButton removeButton = new JButton("删除成绩");

    public ScoreManageWindow(int studentId) {
        ScoreManageController controller = new ScoreManageController(this, studentId);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // BEGIN draw operation buttons
        JPanel optPanel = new JPanel();
        optPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("添加成绩");
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

    public void showAddDialog(int studentId, AddScoreDialog.OnSaveListener callback) {
        AddScoreDialog d = new AddScoreDialog(this, studentId, callback);
        d.setVisible(true);
    }

    public String showEditDialog(Score s) {
        return (String) JOptionPane.showInputDialog(this,
                "成绩",
                "编辑成绩",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                String.format("%.1f", s.point));
    }

    public boolean showConfirmRemoveDialog() {
        return JOptionPane.showConfirmDialog(this,
                "确认删除吗？",
                "删除学生",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "错误",
                JOptionPane.ERROR_MESSAGE);
    }
}

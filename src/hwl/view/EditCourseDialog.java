package hwl.view;

import hwl.controller.EditCourseController;

import javax.swing.*;
import java.awt.*;

import static javax.swing.GroupLayout.Alignment.*;

public class EditCourseDialog extends JDialog {

    public static final int ADD = 0;
    public static final int EDIT = 1;
    private final JTextField nameTextField = new JTextField("", 15);
    private final JTextField numberTextField = new JTextField("", 15);
    private final JTextField pointTextField = new JTextField("", 15);

    public EditCourseDialog(Frame owner, int mode, OnSaveListener callback) {
        this(owner, mode, "", "", 0.0, callback);
    }

    public EditCourseDialog(Frame owner, int mode, String name, String number, double point, OnSaveListener callback) {
        super(owner, "编辑学生信息", true);

        EditCourseController controller = new EditCourseController(this, mode, callback);

        Container mPanel = getContentPane();

        JPanel formPanel = new JPanel();

        GroupLayout groupLayout = new GroupLayout(formPanel);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        formPanel.setLayout(groupLayout);

        nameTextField.setText(name);
        numberTextField.setText(number);
        pointTextField.setText(String.format("%.1f", point));

        JLabel nameLabel = new JLabel("课程名称");
        JLabel numberLabel = new JLabel("课程编号");
        JLabel pointLabel = new JLabel("学分");

        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(LEADING)
                        .addComponent(nameLabel)
                        .addComponent(numberLabel)
                        .addComponent(pointLabel))
                .addGroup(groupLayout.createParallelGroup(TRAILING)
                        .addComponent(nameTextField)
                        .addComponent(numberTextField)
                        .addComponent(pointTextField)));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(BASELINE).addComponent(nameLabel).addComponent(nameTextField))
                .addGroup(groupLayout.createParallelGroup(BASELINE).addComponent(numberLabel).addComponent(numberTextField))
                .addGroup(groupLayout.createParallelGroup(BASELINE).addComponent(pointLabel).addComponent(pointTextField)));

        mPanel.add(formPanel, BorderLayout.CENTER);

        JPanel optPanel = new JPanel();
        optPanel.setLayout(new FlowLayout());

        JButton okButton = new JButton("确定");
        okButton.addActionListener(controller::onClickOkButton);
        optPanel.add(okButton);
        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(controller::onClickCancelButton);
        optPanel.add(cancelButton);

        mPanel.add(optPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(okButton);
        pack();
        setMinimumSize(getSize());
    }


    public void close() {
        setVisible(false);
    }

    public String getCourseName() {
        return nameTextField.getText();
    }

    public String getCourseNumber() {
        return numberTextField.getText();
    }

    public String getCoursePoint() {
        return pointTextField.getText();
    }

    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "错误", JOptionPane.ERROR_MESSAGE);
    }

    public interface OnSaveListener {
        void onSave(String name, String number, double point);
    }
}

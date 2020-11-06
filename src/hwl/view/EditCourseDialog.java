package hwl.view;

import hwl.constraint.IEditCourseController;
import hwl.constraint.IEditCourseView;
import hwl.controller.EditCourseController;

import javax.swing.*;
import java.awt.*;

import static javax.swing.GroupLayout.Alignment.*;

public class EditCourseDialog extends JDialog implements IEditCourseView {

    private final IEditCourseController controller;

    private final JTextField nameTextField = new JTextField("", 15);
    private final JTextField numberTextField = new JTextField("", 15);
    private final JTextField pointTextField = new JTextField("", 15);

    private final JButton okButton = new JButton("确定");
    private final JButton cancelButton = new JButton("取消");

    public EditCourseDialog(Frame owner, int mode, OnSaveListener callback) {
        this(owner, mode, "", "", 0.0, callback);
    }

    public EditCourseDialog(Frame owner, int mode, String name, String number, double point, OnSaveListener callback) {
        super(owner, "编辑学生信息", true);

        controller = new EditCourseController(this, mode, callback);

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

        okButton.addActionListener(controller::onClickOkButton);
        optPanel.add(okButton);
        cancelButton.addActionListener(controller::onClickCancelButton);
        optPanel.add(cancelButton);

        mPanel.add(optPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(okButton);
        pack();
        setMinimumSize(getSize());
    }


    @Override
    public void close() {
        setVisible(false);
    }

    @Override
    public String getCourseName() {
        return nameTextField.getText();
    }

    @Override
    public String getCourseNumber() {
        return numberTextField.getText();
    }

    @Override
    public String getCoursePoint() {
        return pointTextField.getText();
    }

    @Override
    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "错误", JOptionPane.ERROR_MESSAGE);
    }
}

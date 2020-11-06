package hwl.view;

import hwl.constraint.IAddScoreController;
import hwl.constraint.IAddScoreView;
import hwl.controller.AddScoreController;
import hwl.model.item.Course;
import hwl.view.utils.DisplayableComboBoxRenderer;

import javax.swing.*;
import java.awt.*;

import static javax.swing.GroupLayout.Alignment.*;

public class AddScoreDialog extends JDialog implements IAddScoreView {

    private final IAddScoreController controller;

    private final JComboBox<Course> courseComboBox = new JComboBox<>();
    private final JTextField pointTextField = new JTextField("", 15);

    private final JButton okButton = new JButton("确定");
    private final JButton cancelButton = new JButton("取消");

    public AddScoreDialog(Frame owner, int studentId, OnSaveListener callback) {
        super(owner, "添加课程", true);

        controller = new AddScoreController(this, studentId, callback);

        Container mPanel = getContentPane();

        JPanel formPanel = new JPanel();

        GroupLayout groupLayout = new GroupLayout(formPanel);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        formPanel.setLayout(groupLayout);

        JLabel courseLabel = new JLabel("课程");
        JLabel pointLabel = new JLabel("成绩");

        courseComboBox.setRenderer(new DisplayableComboBoxRenderer<>());

        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(LEADING)
                        .addComponent(courseLabel)
                        .addComponent(pointLabel))
                .addGroup(groupLayout.createParallelGroup(TRAILING)
                        .addComponent(courseComboBox)
                        .addComponent(pointTextField)));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(BASELINE).addComponent(courseLabel).addComponent(courseComboBox))
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
    public void setCourseComboBoxModel(ComboBoxModel<Course> model) {
        courseComboBox.setModel(model);
    }

    @Override
    public Course getScoreCourse() {
        return (Course) courseComboBox.getSelectedItem();
    }

    @Override
    public String getScorePoint() {
        return pointTextField.getText();
    }

    @Override
    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "错误", JOptionPane.ERROR_MESSAGE);
    }
}

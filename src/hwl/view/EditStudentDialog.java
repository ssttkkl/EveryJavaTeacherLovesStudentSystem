package hwl.view;

import hwl.constraint.IEditStudentController;
import hwl.constraint.IEditStudentView;
import hwl.controller.EditStudentController;
import hwl.model.Sex;
import hwl.view.utils.DisplayableComboBoxRenderer;

import javax.swing.*;
import java.awt.*;

import static javax.swing.GroupLayout.Alignment.*;

public class EditStudentDialog extends JDialog implements IEditStudentView {

    private final JTextField nameTextField = new JTextField("", 15);
    private final JTextField numberTextField = new JTextField("", 15);
    private final JComboBox<Sex> sexComboBox = new JComboBox<>(Sex.values());

    public EditStudentDialog(Frame owner, int mode, OnSaveListener callback) {
        this(owner, mode, "", "", Sex.MALE, callback);
    }

    public EditStudentDialog(Frame owner, int mode, String name, String number, Sex sex, OnSaveListener callback) {
        super(owner, "编辑学生信息", true);

        IEditStudentController controller = new EditStudentController(this, mode, callback);

        Container mPanel = getContentPane();

        JPanel formPanel = new JPanel();

        GroupLayout groupLayout = new GroupLayout(formPanel);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        formPanel.setLayout(groupLayout);

        JLabel nameLabel = new JLabel("姓名");
        nameTextField.setText(name);

        JLabel numberLabel = new JLabel("学号");
        numberTextField.setText(number);

        JLabel sexLabel = new JLabel("性别");
        sexComboBox.setRenderer(new DisplayableComboBoxRenderer<>());
        sexComboBox.setSelectedItem(sex);

        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(LEADING)
                        .addComponent(nameLabel)
                        .addComponent(numberLabel)
                        .addComponent(sexLabel))
                .addGroup(groupLayout.createParallelGroup(TRAILING)
                        .addComponent(nameTextField)
                        .addComponent(numberTextField)
                        .addComponent(sexComboBox)));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(BASELINE).addComponent(nameLabel).addComponent(nameTextField))
                .addGroup(groupLayout.createParallelGroup(BASELINE).addComponent(numberLabel).addComponent(numberTextField))
                .addGroup(groupLayout.createParallelGroup(BASELINE).addComponent(sexLabel).addComponent(sexComboBox)));

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


    @Override
    public void close() {
        setVisible(false);
    }

    @Override
    public String getStudentName() {
        return nameTextField.getText();
    }

    @Override
    public String getStudentNumber() {
        return numberTextField.getText();
    }

    @Override
    public Sex getStudentSex() {
        return (Sex) sexComboBox.getSelectedItem();
    }
}

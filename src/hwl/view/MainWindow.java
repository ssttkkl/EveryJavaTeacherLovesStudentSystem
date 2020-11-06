package hwl.view;

import hwl.constraint.IMainController;
import hwl.constraint.IMainView;
import hwl.controller.MainController;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainWindow extends JFrame implements IMainView {

    private final IMainController controller;

    public MainWindow() {
        this.controller = new MainController(this);

        CourseManagePanel courseManagePanel = new CourseManagePanel(this);
        GroupManagePanel groupManagePanel = new GroupManagePanel();

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.add("班级/学生管理", groupManagePanel);
        tabbedPane.add("课程管理", courseManagePanel);
        this.add(tabbedPane);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                controller.onWindowClosed();
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        setTitle("学生成绩管理系统");
        setSize(800, 600);
    }

    @Override
    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "发生错误", JOptionPane.ERROR_MESSAGE);
    }
}

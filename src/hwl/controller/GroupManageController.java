package hwl.controller;

import hwl.constraint.IGroupManageController;
import hwl.constraint.IGroupManageView;
import hwl.model.DataSetModelFactory;
import hwl.model.Database;
import hwl.model.ItemTableModel;
import hwl.model.info.GroupInfo;
import hwl.model.item.Group;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GroupManageController implements IGroupManageController {

    private final IGroupManageView view;

    private final ItemTableModel<Integer, Group> tableModel;

    private final Map<Integer, GroupInfo> groupInfos = new HashMap<>();

    public GroupManageController(IGroupManageView view) {
        this.view = view;

        tableModel = DataSetModelFactory.newTableModel(
                new String[]{"班级名称", "平均成绩"},
                new Function[]{
                        g -> ((Group) g).name,
                        g -> {
                            GroupInfo info = groupInfos.get(((Group) g).id);
                            return info != null ? info.averageScore : null;
                        }
                },
                new Class[]{
                        String.class,
                        Double.class
                },
                Database.getInstance().groups
        );
        this.view.setTableModel(tableModel);

        this.view.setEditButtonEnabled(false);
        this.view.setRemoveButtonEnabled(false);
    }

    @Override
    public void onDoubleClickTable(int index) {
        int groupId = tableModel.get(index).id;
        view.showStudentManageWindow(groupId);
    }

    @Override
    public void onClickCalcButton(ActionEvent e) {
        groupInfos.clear();
        for (int i = 0; i < tableModel.size(); i++) {
            Group s = tableModel.get(i);
            groupInfos.put(s.id, GroupInfo.calc(s.id));
        }
        tableModel.notifyColumnChanged(1);
    }

    @Override
    public void onClickAddButton(ActionEvent e) {
        String name = this.view.showAddGroupDialog();
        if (name != null)
            Database.getInstance().groups.emplace(name);
    }

    @Override
    public void onClickEditButton(ActionEvent e) {
        int index = view.getSelectedRow();
        Group g = tableModel.get(index);

        String name = this.view.showRenameGroupDialog(g.name);
        if (name != null)
            Database.getInstance().groups.put(new Group(g.id, name));
    }

    @Override
    public void onClickRemoveButton(ActionEvent e) {
        int index = view.getSelectedRow();
        Group g = tableModel.get(index);

        if (this.view.showConfirmRemoveGroupDialog())
            Database.getInstance().groups.remove(g.id);
    }

    @Override
    public void onSelectionChanged(ListSelectionEvent e) {
        this.view.setEditButtonEnabled(true);
        this.view.setRemoveButtonEnabled(true);
    }
}

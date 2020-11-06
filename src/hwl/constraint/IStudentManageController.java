package hwl.constraint;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public interface IStudentManageController {

    void onDoubleClickTable(int row);

    void onClickAddButton(ActionEvent e);

    void onClickEditButton(ActionEvent e);

    void onClickRemoveButton(ActionEvent e);

    void onClickCalcButton(ActionEvent e);

    void onSelectionChanged(ListSelectionEvent e);
}

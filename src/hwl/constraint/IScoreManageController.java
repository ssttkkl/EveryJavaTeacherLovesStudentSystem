package hwl.constraint;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;

public interface IScoreManageController {

    void onClickAddButton(ActionEvent e);

    void onClickEditButton(ActionEvent e);

    void onClickRemoveButton(ActionEvent e);

    void onSelectionChanged(ListSelectionEvent e);
}

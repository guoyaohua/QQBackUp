import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Administrator
 */
public class ButtonCellRenderer extends JButton implements ListCellRenderer
{
    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,              // value to display
            int index,                 // cell index
            boolean isSelected,       // is the cell selected
            boolean cellHasFocus)     // the list and the cell have the focus
    {
        String s = value.toString();
        setText(s);
        if (isSelected)
        {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else
        {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);
        return this;
    }
}
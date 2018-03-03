
import static java.awt.AWTEventMulticaster.add;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

public class myList extends JPanel {

    private static final long serialVersionUID = 1L;
    JList mList;
    DefaultListModel mode;

    public myList() {
        setLayout(new BorderLayout());
        mode = new DefaultListModel();
        for (int i = 0; i < 10; i++) {
            mode.addElement("123");
        }
        mList = new JList(mode);
        mList.setCellRenderer(new MyCellRenderer());
        add(new JScrollPane(mList), BorderLayout.CENTER);
    }

    public static void main(String[] args) {

        JFrame jf = new JFrame();
        jf.add(new myList(), BorderLayout.CENTER);
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    class MyCellRenderer extends JButton implements ListCellRenderer {

        public MyCellRenderer() {
            setOpaque(true);
        }

        public Component getListCellRendererComponent(JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {

            setText(value.toString());

            Color background;
            Color foreground;

            // check if this cell represents the current DnD drop location
            JList.DropLocation dropLocation = list.getDropLocation();
            if (dropLocation != null && !dropLocation.isInsert() && dropLocation.getIndex() == index) {

                background = Color.BLUE;
                foreground = Color.WHITE;

                // check if this cell is selected
            } else if (isSelected) {
                background = Color.RED;
                foreground = Color.WHITE;

                // unselected, and not the DnD drop location
            } else {
                background = Color.WHITE;
                foreground = Color.BLACK;
            }

            setBackground(background);
            setForeground(foreground);

            return this;
        }
    }
}
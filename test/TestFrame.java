import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Administrator
 */
public class TestFrame extends JFrame
{
    private String[] data = {"one", "two", "three", "four"};
    private JList dataList;
    private JScrollPane jsp;
    
    /** Creates a new instance of TestFrame */
    public TestFrame()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(100,200);
        dataList = new JList(data);
        dataList.setCellRenderer(new ButtonCellRenderer());
        
        jsp = new JScrollPane(dataList);
        Container c = this.getContentPane();
        c.add(jsp, BorderLayout.CENTER);
        
        this.setVisible(true);
    }
    
    public static void main(String[] args){
        new TestFrame();
    }
}
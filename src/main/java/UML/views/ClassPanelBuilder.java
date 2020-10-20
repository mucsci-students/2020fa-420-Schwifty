package UML.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
public class ClassPanelBuilder implements PanelBuilder
{
    
    private String classData;
    private JPanel panel;
    private JFrame parentWindow;
    private int xLoc = 0;
    private int yLoc = 0;
    Border blackline = BorderFactory.createLineBorder(Color.black);

    public ClassPanelBuilder(String data, JFrame window)
    {
        this.classData = data;
        this.parentWindow = window;
        panel = new JPanel();
    }

    @Override
    public void setParentWindow(JFrame window)
    {
        this.parentWindow = window;
    }

    @Override
    public JPanel buildClassPanel()
    {
        return makeNewClassPanel();
    }
    
    public String getClassData() {
        return classData;
    }

    @Override
    public void setClassData(String classData) {
        this.classData = classData;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JFrame getParentWindow() {
        return parentWindow;
    }

    public Border getBlackline() {
        return blackline;
    }

    public void setBlackline(Border blackline) {
        this.blackline = blackline;
    }
    
    public JPanel makeNewClassPanel() 
    {
        JTextArea classText = new JTextArea(classData);
        classText.setEditable(false);

        //panel.setLayout(new BorderLayout());
        panel.add(classText);
        panel.setBackground(Color.PINK);
        Dimension d = classText.getSize();
        int x = (int)d.getWidth();
        int y = (int)d.getHeight();
        //panel.setBounds(100, 100, 15, 15);
        classText.setBorder(blackline);
        panel.setVisible(true);
        parentWindow.add(panel);
        panel.setLocation(xLoc, yLoc);
        return panel;
    }

}

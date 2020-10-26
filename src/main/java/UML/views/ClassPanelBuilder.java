package UML.views;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Scanner;

public class ClassPanelBuilder implements PanelBuilder
{
    
    private String classData;
    private JPanel panel;
    private DrawPanel parentWindow;
    Border blackline = BorderFactory.createLineBorder(Color.black);

    public ClassPanelBuilder(String data, DrawPanel window)
    {
        this.classData = data;
        this.parentWindow = window;
        panel = new JPanel();
    }

    @Override
    public void setParentWindow(DrawPanel window)
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

    public DrawPanel getParentWindow() {
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
        Scanner lineScanner = new Scanner(classData);
        int longest = 0;
        int height = 0;
        while(lineScanner.hasNextLine())
        {
            String line = lineScanner.nextLine();
            Scanner scanner = new Scanner(line);
            int localBest = 0;
            while(scanner.hasNext())
            {
                localBest++;
                scanner.next();
            }
            ++height;
            if(localBest > longest)
                longest = localBest;
        }
        
        classText.setEditable(false);

        panel.setLayout(new BorderLayout());
        panel.add(classText, BorderLayout.CENTER);
        JPanel left = new JPanel();
        left.setBackground(Color.BLUE);
        JPanel top = new JPanel();
        top.setBackground(Color.darkGray);
        JPanel bottom = new JPanel();
        bottom.setBackground(Color.darkGray);
        panel.add(left, BorderLayout.WEST);
        panel.add(top, BorderLayout.NORTH);
        panel.add(bottom, BorderLayout.SOUTH);
        panel.setBackground(Color.PINK);
        panel.setBounds(0, 500, longest * 90, height * 20);
        left.setPreferredSize(new Dimension(25, height * 20));
        classText.setBorder(blackline);
        panel.setVisible(true);
        //parentWindow.add(panel);
        return panel;
    }

}

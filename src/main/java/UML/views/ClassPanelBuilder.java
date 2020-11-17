package UML.views;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/26/2020
    Purpose: Helper class to build class panels as needed.
 */
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Scanner;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class ClassPanelBuilder implements PanelBuilder
{
    
    private String classData;
    private JPanel panel;
    private DrawPanel parentWindow;
    private JMenuBar miniBar;
    Border blackline = BorderFactory.createLineBorder(Color.black);

    /**
     * Constructs a ClassPanelBuilder object. 
     */
    public ClassPanelBuilder(String data, DrawPanel window)
    {
        this.classData = data;
        this.parentWindow = window;
        panel = new JPanel();
    }

    /**
     * Sets the panel window to a draw panel.
     */
    @Override
    public void setParentWindow(DrawPanel window)
    {
        this.parentWindow = window;
    }

    /**
     * Builds a class panel.
     */
    @Override
    public JPanel buildClassPanel()
    {
        return makeNewClassPanel();
    }
    
    /**
     * Gets the classData field which is the toString() value if the class.
     */
    public String getClassData() 
    {
        return classData;
    }

    /**
     * Sets the classData field.
     */
    @Override
    public void setClassData(String classData) 
    {
        this.classData = classData;
    }

    /**
     * Gets the JPanel.
     */
    public JPanel getPanel() 
    {
        return panel;
    }

    /**
     * Sets the JPanel to a given JPanel.
     */
    public void setPanel(JPanel panel) 
    {
        this.panel = panel;
    }

    /**
     * Gets parent window.
     */
    public DrawPanel getParentWindow() 
    {
        return parentWindow;
    }

    /**
     * Gets border for JPanel.
     */
    public Border getBlackline() 
    {
        return blackline;
    }
    
    /**
     * Sets border for JPanel.
     */
    public void setBlackline(Border blackline) 
    {
        this.blackline = blackline;
    }
    
    /**
     * Returns class toString without relationship information.
     */
    private String getClassText(String data)
    {
        int stop = data.indexOf("Relationships To Others: ");
        return data.substring(0, stop - 32);
    }
    
    /**
    *  Creates the inside area of a class panel
     */
    public JPanel makeNewClassPanel() 
    {
        String newText = getClassText(classData);
        JTextArea classText = new JTextArea(newText);

        String[] firstLine = classText.getText().split("\n");
        String[] line = firstLine[0].split(" ");
        String concat = line[2];

        //Sets the layout and creates panels to be added.
        panel.setLayout(new BorderLayout());
        panel.add(classText, BorderLayout.CENTER);
        JPanel left = new JPanel();
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        JMenuBar miniBar = makeMiniBar(concat);

        //Adds color to panels.
        left.setBackground(Color.darkGray);
        top.setBackground(Color.darkGray);
        bottom.setBackground(Color.darkGray);
        panel.setBackground(Color.PINK);

        //Adds panels + menu bar to main panel.
        panel.add(left, BorderLayout.WEST);
        panel.add(miniBar, BorderLayout.EAST);
        panel.add(top, BorderLayout.NORTH);
        panel.add(bottom, BorderLayout.SOUTH);
        
        //Finishes panel construction.
        classText.setBorder(blackline);
        panel.setVisible(true);
        return panel;
    }

    /**
     * Creates the menubar for the class panel.
     */
    private JMenuBar makeMiniBar(String concat)
    {
        miniBar = new JMenuBar();
        JMenu miniMenu = new JMenu("+"); 
        miniMenu.setBackground(Color.darkGray);
        
        //Field.
        JMenuItem crtField = new JMenuItem("Create field");
        JMenuItem editField = new JMenuItem("Edit Field");

        //Method.
        JMenuItem crtMethod = new JMenuItem("Create method");
        JMenuItem editMethod = new JMenuItem("Edit method");
        
        //Relationship.
        JMenuItem crtRelationship = new JMenuItem("Create relationship");
        JMenuItem delRelationship = new JMenuItem("Delete relationship");

        //Class.
        JMenuItem editClass = new JMenuItem("Edit Class");
      
        JMenuItem[] arr = { crtField, editField, crtMethod, editMethod, crtRelationship, delRelationship, editClass};
        String[] text = { "Create new field", "Edit a field", "Create a method", "Edit a method", "Create a relationship", "Delete a relationship",
                          "Rename a classs", "Delete a class" };
        String[] command = { "CreateField " + concat, "EditField " + concat, "CreateMethod " + concat, "EditMethod " + concat, 
            "CreateRelationship " + concat, "DeleteRelationship " + concat, "EditClass " + concat };

        for (int count = 0; count < 7; ++count) {
            miniMenu.add(arr[count]);
            arr[count].setToolTipText(text[count]);
            arr[count].setActionCommand(command[count]);
        }
        
        miniBar.add(miniMenu);

        return miniBar;
    }

}

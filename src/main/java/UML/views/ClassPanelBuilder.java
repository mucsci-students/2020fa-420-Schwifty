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
    public String getClassData() {
        return classData;
    }

    /**
     * Sets the classData field.
     */
    @Override
    public void setClassData(String classData) {
        this.classData = classData;
    }

    /**
     * Gets the JPanel.
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Sets the JPanel to a given JPanel.
     */
    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    /**
     * Gets parent window.
     */
    public DrawPanel getParentWindow() {
        return parentWindow;
    }

    /**
     * Gets border for JPanel.
     */
    public Border getBlackline() {
        return blackline;
    }
    
    /**
     * Sets border for JPanel.
     */
    public void setBlackline(Border blackline) {
        this.blackline = blackline;
    }
    
    /**
     * Returns class toString without relationship information.
     */
    private String getClassText(String data)
    {
        int stop = data.indexOf("Relationships To Others: ");
        return data.substring(0, stop);
    }
    
    /**
    *  Creates the inside area of a class panel
     */
    public JPanel makeNewClassPanel() 
    {
        String newText = getClassText(classData);
        JTextArea classText = new JTextArea(newText);
        //Uses Scanner to get width/height of Panel.
        Scanner lineScanner = new Scanner(newText);
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
            scanner.close();
            ++height;
            if(localBest > longest)
                longest = localBest;
        }
        lineScanner.close();
        classText.setEditable(false);

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
        left.setBackground(Color.BLUE);
        top.setBackground(Color.darkGray);
        bottom.setBackground(Color.darkGray);
        panel.setBackground(Color.PINK);

        //Adds panels + menu bar to main panel.
        panel.add(left, BorderLayout.WEST);
        panel.add(miniBar, BorderLayout.EAST);
        panel.add(top, BorderLayout.NORTH);
        panel.add(bottom, BorderLayout.SOUTH);
        
        //Sets size of panel pieces and overall panel.
        panel.setBounds(0, 500, longest * 90, height * 20);
        left.setPreferredSize(new Dimension(25, height * 20));

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
        JMenuBar miniBar = new JMenuBar();
        JMenu miniMenu = new JMenu("+"); 
        miniMenu.setBackground(Color.darkGray);
        
        JMenuItem crtField = new JMenuItem("Create field");
        JMenuItem crtMethod = new JMenuItem("Create method");
        JMenuItem delField = new JMenuItem("Delete field");
        JMenuItem delMethod = new JMenuItem("Delete method");
        JMenuItem rnField = new JMenuItem("Rename field");
        JMenuItem rnMethod = new JMenuItem("Rename method");
        JMenuItem chgFieldType = new JMenuItem("Change field type");
        JMenuItem chgMethodType = new JMenuItem("Change method type");
        JMenuItem chgFieldAccess = new JMenuItem("Change Field Access");
        JMenuItem chgMethodAccess = new JMenuItem("Change Method Access");

        JMenuItem[] arr = { crtField, delField, rnField, chgFieldType, crtMethod, delMethod, rnMethod,
            chgFieldAccess, chgMethodAccess, chgMethodType };
        String[] text = { "Create new field", "Delete a named field", "Rename a selected field",
            "Changes the field's type", "Create new method", "Delete a named method", "Rename a selected method",
            "Change field access level", "Change method access level", "Change method type" };
        String[] command = { "CreateField " + concat, "DeleteField " + concat, "RenameField " + concat, "ChangeFieldType " + concat, "CreateMethod " + concat,
            "DeleteMethod " + concat, "RenameMethod "+ concat, "ChangeFieldAccess "+ concat, "ChangeMethodAccess "+concat, "ChangeMethodType " };

        for (int count = 0; count < 10; ++count) {
            miniMenu.add(arr[count]);
            arr[count].setToolTipText(text[count]);
            arr[count].setActionCommand(command[count]);
        }
        miniBar.add(miniMenu);

        return miniBar;
    }
}

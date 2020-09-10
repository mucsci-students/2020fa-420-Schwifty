/*
    Author: Chris and Dominic 
    Date: 09/10/2020
    Purpose: To create the menu system for our GUI UML editor. The menu will have the option to 
    save and load your work under the file menu option. You can create or delete a class by clicking on the class and create or delete. 
    The same is true for attributes and links between classes. Access to created classes and atrributes and links will be handled via 
    entering it's name in a pop up text box. Note: this should perhaps later allow for you to click on the graphical representaion to
    acheive the same thing. 
    GUI done using Java Swing.
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu 
{
   public void make()
   {
        //Main Frame//
        JFrame frame = new JFrame("UML");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        
        //Menu Bar//
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu classes = new JMenu("Class");
        JMenu attributes = new JMenu("Attribute");
        JMenu relate = new JMenu("Link");
        mb.add(file);
        mb.add(classes);
        mb.add(attributes);
        mb.add(relate);

        //Sub-Menus//
        JMenuItem save = new JMenuItem("Save");
        JMenuItem saveAs = new JMenuItem("Save as...");
        JMenuItem load = new JMenuItem("Load...");
        JMenuItem exit = new JMenuItem("Exit");
        
        JMenuItem crtClass = new JMenuItem("Create class");
        JMenuItem deleteClass = new JMenuItem("Delete class");
        JMenuItem rnClass = new JMenuItem("Rename class");
        
        JMenuItem crtAttr = new JMenuItem("Create attribute");
        JMenuItem deleteAttr = new JMenuItem("Delete attribute");
        JMenuItem rnAttr = new JMenuItem("Rename attribute");

        //Add sub-menus//
        file.add(save);
        file.add(saveAs);
        file.add(load);
        file.add(exit);

        classes.add(crtClass);
        classes.add(deleteClass);
        classes.add(rnClass);

        attributes.add(crtAttr);
        attributes.add(deleteAttr);
        attributes.add(rnAttr);

        //Hover text & events//
        save.setToolTipText("Save edited file");
        saveAs.setToolTipText("Save newly created file");
        rnClass.setToolTipText("Load saved file");
        exit.setToolTipText("Exit application");
        exit.addActionListener((event) -> System.exit(0));

        crtClass.setToolTipText("Create new class");
        deleteClass.setToolTipText("Delete a named class");
        rnAttr.setToolTipText("Rename a selected class");
       
        crtAttr.setToolTipText("Create an attribute");
        deleteAttr.setToolTipText("Delete a selected attribute");
        load.setToolTipText("Rename a selected attribute");
       

        //Create action commands
        crtClass.setActionCommand("CreateClass");

        //add listener
        crt.addActionListener(new ButtonClickListener());
        //Set frame locations//
        
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.setVisible(true);

        private class ButtonClickListener implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                String cmd = e.getActionCommand();
                if (cmd.equals("CreateClass"))
                {

                }
            }
        }
   }

}
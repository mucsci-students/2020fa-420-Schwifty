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
    //TODO: This class must be updated when class, attribute, and the main window are complete. 
   public Menu(JFrame window)
   {
       //Menu Bar//
       createMenu(window);  
   }

   private static void createMenu(JFrame window)
   {
       JMenuBar mb = new JMenuBar();
       createFileMenu(mb);
       createClassMenu(mb);
       createAtrributeMenu(mb);
       createRelationshipMenu(mb);
       window.add(mb);
   }

   private static void createFileMenu(JMenuBar mb)
   {
       JMenu file = new JMenu("File");

       //Sub-Menus//
       JMenuItem save = new JMenuItem("Save");
       JMenuItem saveAs = new JMenuItem("Save as...");
       JMenuItem load = new JMenuItem("Load...");
       JMenuItem exit = new JMenuItem("Exit");

       //Add sub-menus//
       file.add(save);
       file.add(saveAs);
       file.add(load);
       file.add(exit);

       //hover tasks and events//
       save.setToolTipText("Save edited file");
       saveAs.setToolTipText("Save newly created file");
       exit.setToolTipText("Exit application");
       exit.addActionListener((event) -> System.exit(0));

       //Add action commands
       save.setActionCommand("Save");
       saveAs.setActionCommand("SaveAs");
       load.setActionCommand("Load");

       //Add action listener
       save.addActionListener(new FileButtonClickListener());
       saveAs.addActionListener(new FileButtonClickListener());
       load.addActionListener(new FileButtonClickListener());

       mb.add(file);
   }

   private static void createClassMenu(JMenuBar mb)
   {
       JMenu classes = new JMenu("Class");
       
       //Create sub menus//
       JMenuItem crtClass = new JMenuItem("Create class");
       JMenuItem deleteClass = new JMenuItem("Delete class");
       JMenuItem rnClass = new JMenuItem("Rename class");

       //add sub-menus//
       classes.add(crtClass);
       classes.add(deleteClass);
       classes.add(rnClass);

       //Hover text & events//
       rnClass.setToolTipText("Rename Class");

       //Create action commands
       crtClass.setActionCommand("Create");
       deleteClass.setActionCommand("Delete");
       rnClass.setActionCommand("Rename");

       //Add action listeners
       crtClass.addActionListener(new ClassButtonClickListener());
       deleteClass.addActionListener(new ClassButtonClickListener());
       rnClass.addActionListener(new ClassButtonClickListener());

       //Add to menu bar
       mb.add(classes);
   }

   private static void createAtrributeMenu(JMenuBar mb)
   {
       JMenu attributes = new JMenu("Attribute");

       //Create attribute sub-menu
       JMenuItem crtAttr = new JMenuItem("Create attribute");
       JMenuItem deleteAttr = new JMenuItem("Delete attribute");
       JMenuItem rnAttr = new JMenuItem("Rename attribute");
       
       //Add attribute sub-menus
       attributes.add(crtAttr);
       attributes.add(deleteAttr);
       attributes.add(rnAttr);

       //Hover text & events//
       crtClass.setToolTipText("Create new class");
       deleteClass.setToolTipText("Delete a named class");
       rnAttr.setToolTipText("Rename a selected class");

       crtAttr.setActionCommand("Create");
       deleteAttr.setActionCommand("Delete");
       rnAttr.setActionCommand("Rename");

       mb.add(attributes);
   }

   private static void createRelationshipMenu(JMenuBar mb)
   {
       JMenu relate = new JMenu("Relate");

       JMenuItem association = new JMenuItem("Association");
       JMenuItem aggregation = new JMenuItem("Aggregation");
       JMenuItem composition = new JMenuItem("Composition");
       JMenuItem generalization = new JMenuItem("Generalization");

       relate.add(association);
       relate.add(aggregation);
       relate.add(composition);
       relate.add(generalization);

       association.setToolTipText("Creates selected relationship between two classes");
       aggregation.setToolTipText("Creates selected relationship between two classes");
       composition.setToolTipText("Creates selected relationship between two classes");
       generalization.setToolTipText("Creates selected relationship between two classes");

       association.setActionCommand("Association");
       aggregation.setActionCommand("Aggregation");
       aggregation.setActionCommand("Composition");
       generalization.setActionCommand("Generalization");

       mb.add(relate);
   }

   
   private class FileButtonClickListener implements ActionListener
   {
       public void actionPerformed(ActionEvent e)
       {
           String cmd = e.getActionCommand();
           if(cmd.equals("Save"))
           {
               //Save contents to file...will require JSON save
           }
           else if(cmd.equals("SaveAs"))
           {
               //Bring up file panel for the user to save as(automatically will choose file type though)
           }
           else if(cmd.equals("Load"))
           {
               //Bring up file panel to load a UML design from JSON
           }
        }
    }

   private class ClassButtonClickListener implements ActionListener
   {
       public void actionPerformed(ActionEvent e)
       {
           String cmd = e.getActionCommand();
           if(cmd.equals("Create"))
           {
               //Load text input box to get the name of the new class to be created.
               String className = JOptionPane.showInputDialog("Enter a name for the class you would like to create:");
               //Create the class. 
           }
           else if(cmd.equals("Delete"))
           {
               //Load dropdown of available classes to delete
               //Get string of users choice
               //Create the class. 
           }
           else if(cmd.equals("Rename"))
           {
               //Load dropdown of created classes 
               //Get string of the users choice
               //rename that class. 
           }
        }
    }

     private class AttributeButtonClickListener implements ActionListener
     {
         public void actionPerformed(ActionEvent e)
         {
             String cmd = e.getActionCommand();
             if(cmd.equals("Create"))
             {
                 //Create a drop down list of created classes
                 //Will need text box for the type and name of an attribute
             }
             else if(cmd.equals("Delete"))
             {
                 //Create a dropdown list of created classes
                 //Load text box to type in the class to be deleted
             }
             else if(cmd.equals("Rename"))
             {
                 //Load text box to type in the class to be renamed
                 //Load a second text input to get the new name
             }
          }
      }

      private class RelationshipButtonClickListener implements ActionListener
      {
          public void actionPerformed(ActionEvent e)
          {
              String cmd = e.getActionCommand();
              if(cmd.equals("Association"))
              {
                  //create a dialog box with two dropdowns of available classes
                  //Create relationship between chosen two
              }
              else if(cmd.equals("Aggregation"))
              {
                  //create a dialog box with two dropdowns of available classes
                  //Create relationship between chosen two
              }
              else if(cmd.equals("Composition"))
              {
                  //create a dialog box with two dropdowns of available classes
                  //Create relationship between chosen two
              }
              else if(cmd.equals("Generalization"))
              {
                  //create a dialog box with two dropdowns of available classes
                  //Create relationship between chosen two
              }
           }
       }
}
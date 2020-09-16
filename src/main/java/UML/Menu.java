package UML;
/*
    Author: Chris, Dominic and Drew
    Date: 09/10/2020
    Purpose: To create the menu system for our GUI UML editor. The menu will have the option to 
    save and load your work under the file menu option. You can create or delete a class by clicking on the class and create or delete. 
    The same is true for attributes and links between classes. Access to created classes and atrributes and links will be handled via 
    entering it's name in a pop up text box. Note: this should perhaps later allow for you to click on the graphical representaion to
    acheive the same thing. 
    GUI done using Java Swing.
*/

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;
import UML.Class.RelationshipType;

public class Menu 
{
    //TODO: This class must be updated when class, attribute, and the main window are complete. 
   private JMenuBar mb;
   private JFrame parentWindow;
   private ArrayList<Class> classStore;
   
   public void createMenu(JFrame window)
   {
       classStore = null;
       parentWindow = window;
       mb = new JMenuBar();
       createFileMenu(mb);
       createClassMenu(mb);
       createAtrributeMenu(mb);
       createRelationshipMenu(mb);
       window.add(mb);
   }
   
   public JMenuBar getMenuBar()
   {
      return mb;
   }
   /**
    * Sets the array list sent in from main. Where all created classes are stored. 
    * @param storage
    */
   public void SetClassStore(ArrayList<Class> storage)
   {
       this.classStore = storage;
   }

   private void createFileMenu(JMenuBar mb)
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
       load.setToolTipText("Load selected project");
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

   private void createClassMenu(JMenuBar mb)
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

   private void createAtrributeMenu(JMenuBar mb)
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
       crtAttr.setToolTipText("Create new attribute");
       deleteAttr.setToolTipText("Delete a named attribute");
       rnAttr.setToolTipText("Rename a selected attribute");

       crtAttr.setActionCommand("Create");
       deleteAttr.setActionCommand("Delete");
       rnAttr.setActionCommand("Rename");

       mb.add(attributes);
   }

   private void createRelationshipMenu(JMenuBar mb)
   {
       JMenu relate = new JMenu("Relate");

       JMenuItem association = new JMenuItem("Association");
       JMenuItem aggregation = new JMenuItem("Aggregation");
       JMenuItem composition = new JMenuItem("Composition");
       JMenuItem generalization = new JMenuItem("Generalization");
       JMenuItem deleteRelate = new JMenuItem("Delete Relationship");

       relate.add(association);
       relate.add(aggregation);
       relate.add(composition);
       relate.add(generalization);
       relate.add(deleteRelate);

       association.setToolTipText("Creates selected relationship between two classes");
       aggregation.setToolTipText("Creates selected relationship between two classes");
       composition.setToolTipText("Creates selected relationship between two classes");
       generalization.setToolTipText("Creates selected relationship between two classes");
       generalization.setToolTipText("Deletes selected relationship between two classes");

       association.setActionCommand("Association");
       aggregation.setActionCommand("Aggregation");
       aggregation.setActionCommand("Composition");
       generalization.setActionCommand("Generalization");
       deleteRelate.setActionCommand("DeleteRelate");

       mb.add(relate);
   }

   /** 
    * Makes and returns a combo box fill with the created classes
   */
   private JComboBox makeClassComboBox()
   {
       JComboBox classBox = new JComboBox(classStore.toArray());
       classBox.setSelectedItem(1);
       return classBox;
   }
   
   private void removeRelationships(Class aClass)
   {
       Map<String, RelationshipType> tempTo = aClass.getRelationshipToOther();
       Map<String, RelationshipType> tempFrom = aClass.getRelationshipFromOther();
       
   }

   /**
    * Finds an element in the storage and returns it. Returns null if nothing found.
    */
   private Class findClass(String name)
   {
       for (Class aClass : classStore) 
       {
           if(aClass.getName().equals(name))
           {
               return aClass;
           }
       }

       return null;
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
               String className = JOptionPane.showInputDialog("Class name: ");
               //Create the class. 
               Class newClass = new Class(className);
               classStore.add(newClass);
           }
           else if(cmd.equals("Delete"))
           {
               //Load dropdown of available classes to delete
               JComboBox classBox = makeClassComboBox();
               String toBeDeleted = JOptionPane.showInputDialog(parentWindow, classBox, "Delete this class", JOptionPane.QUESTION_MESSAGE);
               //Delete the class. 
               //find it in storage array
               Class temp = findClass(toBeDeleted);
               
               classStore.remove(temp);

           }
           else if(cmd.equals("Rename"))
           {
               //Load dropdown of created classes
               JComboBox classBox = makeClassComboBox();
               String toBeRenamed = JOptionPane.showInputDialog(parentWindow, classBox, "Rename this class", JOptionPane.QUESTION_MESSAGE); 
               //Open text dialog to get the new class name. 
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
                 //TODO: Consider making a custom window for this? It would make it look cleaner in the future.

                 //Create a drop down list of created classes
                 JComboBox classBox = makeClassComboBox();
                 String classToAddAttrTo = JOptionPane.showInputDialog(parentWindow, classBox, "Rename this class", JOptionPane.QUESTION_MESSAGE); 
                 //Get Type from user
                 String type = JOptionPane.showInputDialog("Type: ");
                 //Get name from user
                 String name = JOptionPane.showInputDialog("Name: ");

                 //Find the class in the storage and add an attribute to it

             }
             else if(cmd.equals("Delete"))
             {
                 //Create a dropdown list of created classes
                 JComboBox classBox = makeClassComboBox();
                 String classToAddAttrTo = JOptionPane.showInputDialog(parentWindow, classBox, "Select a class: ", JOptionPane.QUESTION_MESSAGE); 

                 //open combo box to list attributes
                 
                 //Delete the selected attribute from the class.
             }
             else if(cmd.equals("Rename"))
             {
                 //Load combo box to get the class to be renamed
                 JComboBox classBox = makeClassComboBox();
                 String classToAddAttrTo = JOptionPane.showInputDialog(parentWindow, classBox, "Select a class: ", JOptionPane.QUESTION_MESSAGE); 
                 //open combo box to list attributes
                 //Open text input for new name.
                 String className = JOptionPane.showInputDialog("Atrribute name: ");
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
              else if(cmd.equals("DeleteRelate"))
              {
                  //create a dialog box with two dropdowns of available classes
                  //delete relationship between chosen two
              }
           }
       }
}
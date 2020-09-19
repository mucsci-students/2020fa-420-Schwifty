
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
import javax.swing.JTextArea;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.GridLayout;




public class Menu 
{
    //TODO: This class must be updated when class, attribute, and the main window are complete. 
   private JMenuBar mb;
   private JFrame parentWindow;
   private ArrayList<Class> classStore;
   private Map<String, JPanel> classPanels;
   
   public void createMenu(JFrame window)
   {
       classStore = new ArrayList<Class>();
       classPanels = new HashMap<String, JPanel>();
       parentWindow = window;
       parentWindow.setLayout(new GridLayout(5,5));
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

       crtAttr.addActionListener(new AttributeButtonClickListener());
       deleteAttr.addActionListener(new AttributeButtonClickListener());
       rnAttr.addActionListener(new AttributeButtonClickListener());
       
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
       deleteRelate.setToolTipText("Deletes selected relationship between two classes");

       association.setActionCommand("Association");
       aggregation.setActionCommand("Aggregation");
       composition.setActionCommand("Composition");
       generalization.setActionCommand("Generalization");
       deleteRelate.setActionCommand("DeleteRelate");

       association.addActionListener(new RelationshipButtonClickListener());
       aggregation.addActionListener(new RelationshipButtonClickListener());
       composition.addActionListener(new RelationshipButtonClickListener());
       generalization.addActionListener(new RelationshipButtonClickListener());
       deleteRelate.addActionListener(new RelationshipButtonClickListener());
 
       mb.add(relate);
   }

   private void makeNewClassPanel(Class aClass)
   {
       JPanel classPanel = new JPanel();
       classPanel.setSize(20,200);
       classPanel.setVisible(true);
       parentWindow.add(classPanel);


       JTextArea classText = new JTextArea(aClass.toString());
       classText.setEditable(false);
       //classText.append(aClass.toString());
       classPanel.add(classText);
       classPanels.put(aClass.getName(), classPanel);
       Border blackline = BorderFactory.createLineBorder(Color.black);
       classText.setBorder(blackline);

   }
   
   /**
    * Updates the relationships visually in the window 
    *
    * Bug! - when adding a relationship the relationship shows
    *        up in relateTo and relateFrom for both classes 
    * @param classOne 
    * @param classTwo
    */
   private void updateDisplayRelationship (Class classOne , Class classTwo)
   {
       JPanel panelOne = classPanels.get(classOne.getName());
       JTextArea textArea = (JTextArea) panelOne.getComponents()[0];
       textArea.setText(classOne.toString());
       classPanels.remove(classOne.getName());
       classPanels.put(classOne.getName(), panelOne);

       JPanel panelTwo = classPanels.get(classTwo.getName());
       JTextArea textAreaTwo = (JTextArea) panelTwo.getComponents()[0];
       textAreaTwo.setText(classTwo.toString());
       classPanels.remove(classTwo.getName());
       classPanels.put(classTwo.getName(), panelTwo);

       parentWindow.revalidate();
       parentWindow.repaint();   
   } 

   /** 
    * Makes and returns a combo box fill with the created classes
   */
   private ArrayList<String> getClassList()
   {
       ArrayList<String> names = new ArrayList<String>();

       for(Class aClass : classStore)
       {
           names.add(aClass.getName());
       }

       return names;
   }
   
   private ArrayList<String> getAttributeList(Set<Attribute> attributesFromClass)
   {
    ArrayList<String> attributes = new ArrayList<String>();
    
    for(Attribute attr : attributesFromClass)
    {
        String type = attr.getType();
        String name = attr.getName();
        attributes.add(type + " " + name);
    }

    return attributes;
   }

   private void removeRelationships(Class aClass)
   {
       //Find the classes that the class in question has a relationship with
       Map<String, RelationshipType> tempTo = aClass.getRelationshipsToOther();
       Map<String, RelationshipType> tempFrom = aClass.getRelationshipsFromOther();

       for(Map.Entry<String, RelationshipType> entry : tempTo.entrySet()) 
       {
            //Go to those classes and get rid of relationships to and from the class in question
            Class temp = findClass(entry.getKey());
            temp.deleteRelationshipFromOther(entry.getValue(), aClass);
            temp.deleteRelationshipToOther(entry.getValue(), aClass);
       }
       
       for(Map.Entry<String, RelationshipType> entry : tempFrom.entrySet()) 
       {
            //Go to those classes and get rid of relationships to and from the class in question
            Class temp = findClass(entry.getKey());
            temp.deleteRelationshipFromOther(entry.getValue(), aClass);
            temp.deleteRelationshipToOther(entry.getValue(), aClass);
       }
       
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
               Class temp = findClass(className);
               if(temp == null) 
               {
                Class newClass = new Class(className);
                classStore.add(newClass);
                makeNewClassPanel(newClass);
               }
           }
           else if(cmd.equals("Delete"))
           {
               //Load dropdown of available classes to delete
               ArrayList<String> classArrayList = getClassList();
               Object[] classList = classArrayList.toArray();
               String toBeDeleted = (String)JOptionPane.showInputDialog(parentWindow, 
                                                                "Delete this class", 
                                                                "Delete a class", 
                                                                JOptionPane.PLAIN_MESSAGE,
                                                                null,
                                                                classList, null);
               //Delete the class. 
               //find it in storage array
               
               Class temp = findClass(toBeDeleted);
               if(findClass(toBeDeleted) != null)
               {
                    JPanel panel = classPanels.get(toBeDeleted);
                    parentWindow.remove(panel);
                    //delete relationships before deleting class
                    removeRelationships(temp);
                    classStore.remove(temp);
                    classPanels.remove(temp.getName());
                    parentWindow.revalidate();
                    parentWindow.repaint();
                    

               }
               

               
           }
           else if(cmd.equals("Rename"))
           {
               //Load dropdown of created classes
               ArrayList<String> classArrayList = getClassList();
               Object[] classList = classArrayList.toArray();
               String toBeRenamed = (String)JOptionPane.showInputDialog(parentWindow, 
                                                                        "Rename this class", 
                                                                        "Rename a class", 
                                                                        JOptionPane.PLAIN_MESSAGE,
                                                                        null,
                                                                        classList, null);
               //Open text dialog to get the new class name. 
               
               String newClassName = (String)JOptionPane.showInputDialog("New Class Name");
               //rename that class.
               //This is done so that we don't give a class a name that is already taken
               if(findClass(newClassName) == null)
               {
                   Class temp = findClass(toBeRenamed);
                   temp.setName(newClassName);
                   JPanel panel = classPanels.get(toBeRenamed);
                   JTextArea textArea = (JTextArea)panel.getComponents()[0];
                   textArea.setText(temp.toString());
                   classPanels.put(newClassName, panel);
                   classPanels.remove(toBeRenamed);
                   parentWindow.revalidate();
                   parentWindow.repaint();
               }
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
                 ArrayList<String> classArrayList = getClassList();
                 Object[] classList = classArrayList.toArray();
                 String className = (String)JOptionPane.showInputDialog(parentWindow, 
                                                                        "Create Attribute for this class", 
                                                                        "Create atrribute", 
                                                                        JOptionPane.PLAIN_MESSAGE,
                                                                        null,
                                                                        classList, null); 
                 //Get Type from user
                 String type = JOptionPane.showInputDialog("Type: ");
                 //Get name from user
                 String name = JOptionPane.showInputDialog(parentWindow,"Name: ", JOptionPane.QUESTION_MESSAGE);

                 //Find the class in the storage and add an attribute to it
                 Class classToAddAttrTo = findClass(className);
                 classToAddAttrTo.addAttribute(type, name);

                 /*      Bugs
                 * when a space is placed after the type or name 
                 * when adding attr delete and rename no longer 
                 * works for that attr.  
                 */
                 JPanel panel = classPanels.get(className);
                 JTextArea textArea = (JTextArea)panel.getComponents()[0];
                 textArea.setText(classToAddAttrTo.toString());
                 classPanels.remove(className);
                 classPanels.put(className, panel);
                 parentWindow.revalidate();
                 parentWindow.repaint();
                 

             }
             else if(cmd.equals("Delete"))
             {
                ArrayList<String> classArrayList = getClassList();
                Object[] classList = classArrayList.toArray();
                String className = (String)JOptionPane.showInputDialog(parentWindow, 
                                                                       "Delete Attribute for this class", 
                                                                       "Delete atrribute", 
                                                                       JOptionPane.PLAIN_MESSAGE,
                                                                       null,
                                                                       classList, null);  
                 //Get class from storage
                 Class classToDeleteFrom = findClass(className);
                 //Get atrributes from the class. 
                 Set<Attribute> attributes = classToDeleteFrom.getAttributes();
                 //Get the atrributes in a combo box
                 ArrayList<String> attributeArrayList = getAttributeList(attributes);
                 Object[] attributeList = attributeArrayList.toArray();
                 //Get attribute to delete
                 String attribute = (String)JOptionPane.showInputDialog(parentWindow, 
                                                                        "Delete this atrribute", 
                                                                        "Delete atrribute", 
                                                                        JOptionPane.PLAIN_MESSAGE,
                                                                        null,
                                                                        attributeList, null); 
                 String[] att = attribute.split(" ");
                 classToDeleteFrom.deleteAttribute(att[1]);
                 //Delete the attribute

                 //delete attr in window
                 JPanel panel = classPanels.get(className); 
                 JTextArea textArea = (JTextArea)panel.getComponents()[0];
                 textArea.setText(classToDeleteFrom.toString());
                 classPanels.remove(className);
                 classPanels.put(className, panel);
                 parentWindow.revalidate();
                 parentWindow.repaint();

             }
             else if(cmd.equals("Rename"))
             {
                 //Load combo box to get the class to be renamed
                 ArrayList<String> classArrayList = getClassList();
                 Object[] classList = classArrayList.toArray();
                 String className = (String)JOptionPane.showInputDialog(parentWindow, 
                                                                        "Rename Attribute for this class", 
                                                                        "Rename atrribute", 
                                                                        JOptionPane.PLAIN_MESSAGE,
                                                                        null,
                                                                        classList, null); 
                 //Get class from storage
                 Class classToRenameFrom = findClass(className);
                 //Get atrributes from the class. 
                 Set<Attribute> attributeSet = classToRenameFrom.getAttributes();
                 ArrayList<String> attributeArrayList = getAttributeList(attributeSet);
                 Object[] attributeList = attributeArrayList.toArray();
                 //Get attribute to rename
                 String attribute = (String)JOptionPane.showInputDialog(parentWindow, 
                                                                        "Rename this attribute", 
                                                                        "Rename atrribute", 
                                                                        JOptionPane.PLAIN_MESSAGE,
                                                                        null,
                                                                        attributeList, null); 
                 String newAttribute = JOptionPane.showInputDialog(parentWindow, "Enter new attribute name: ", JOptionPane.QUESTION_MESSAGE);
                 String[] att = attribute.split(" ");
                 classToRenameFrom.renameAttribute(att[1], newAttribute);
                 //Open text input for new name.
                
                 //rename attr in window
                 JPanel panel = classPanels.get(className); 
                 JTextArea textArea = (JTextArea)panel.getComponents()[0];
                 textArea.setText(classToRenameFrom.toString());
                 classPanels.remove(className);
                 classPanels.put(className, panel);
                 parentWindow.revalidate();
                 parentWindow.repaint();
             }
          }
      }

      private class RelationshipButtonClickListener implements ActionListener
      {             
          public void actionPerformed(ActionEvent e)
          {
              //Prevent class from having a relationship to itself BIG SCARY
              String cmd = e.getActionCommand();
              if(cmd.equals("Association"))
              {
                  //create a dialog box with two dropdowns of available classes
                  ArrayList<String> classArrayList = getClassList();
                  Object[] classList = classArrayList.toArray();
                  String buildRelateOne = (String)JOptionPane.showInputDialog(parentWindow, 
                                                                         "Choose first class", 
                                                                         "Association", 
                                                                         JOptionPane.PLAIN_MESSAGE,
                                                                         null,
                                                                         classList, 
                                                                         null); 
        
                  String buildRelateTwo = (String)JOptionPane.showInputDialog(parentWindow, 
                                                                            "Choose second class", 
                                                                            "Association", 
                                                                            JOptionPane.PLAIN_MESSAGE,
                                                                            null,
                                                                            classList, 
                                                                            null); 
                  //Add the relationship between the classes.
                  Class class1 = findClass(buildRelateOne);
                  Class class2 = findClass(buildRelateTwo);
                  Class.addRelationship(class1, class2, RelationshipType.ASSOCIATION);

                  
                  //Create relationship between chosen two, a relationship from and to must be made
              
                  updateDisplayRelationship(class1, class2);

              }
              else if(cmd.equals("Aggregation"))
              {
                  ArrayList<String> classArrayList = getClassList();
                  Object[] classList = classArrayList.toArray();
                  String buildRelateOne = (String)JOptionPane.showInputDialog(parentWindow, 
                                                                              "Choose first class", 
                                                                              "Aggregation", 
                                                                              JOptionPane.PLAIN_MESSAGE,
                                                                              null,
                                                                              classList, 
                                                                              null); 
                                                                              
                  String buildRelateTwo = (String)JOptionPane.showInputDialog(parentWindow, 
                                                                          "Choose second class", 
                                                                          "Aggregation", 
                                                                          JOptionPane.PLAIN_MESSAGE,
                                                                          null,
                                                                          classList, 
                                                                          null); 
                //Add the relationship between the classes.
                Class class1 = findClass(buildRelateOne);
                Class class2 = findClass(buildRelateTwo);

                  Class.addRelationship(class1, class2, RelationshipType.AGGREGATION);

                 //dispaly relationship 
                 updateDisplayRelationship(class1, class2);

              }
              else if(cmd.equals("Composition"))
              {
                  //create a dialog box with two dropdowns of available classes
                 //create a dialog box with two dropdowns of available classes
                 ArrayList<String> classArrayList = getClassList();
                 Object[] classList = classArrayList.toArray();
                 String buildRelateOne = (String)JOptionPane.showInputDialog(parentWindow, 
                                                                        "Choose first class", 
                                                                        "Association", 
                                                                        JOptionPane.PLAIN_MESSAGE,
                                                                        null,
                                                                        classList, 
                                                                        null); 
       
                 String buildRelateTwo = (String)JOptionPane.showInputDialog(parentWindow, 
                                                                           "Choose second class", 
                                                                           "Association", 
                                                                           JOptionPane.PLAIN_MESSAGE,
                                                                           null,
                                                                           classList, 
                                                                           null); 
                 //Add the relationship between the classes.
                 Class class1 = findClass(buildRelateOne);
                 Class class2 = findClass(buildRelateTwo);
                 Class.addRelationship(class1, class2, RelationshipType.COMPOSITION);

                 //display relationship 
                 updateDisplayRelationship(class1, class2);
              }
              else if(cmd.equals("Generalization"))
              {
                 //create a dialog box with two dropdowns of available classes
                 ArrayList<String> classArrayList = getClassList();
                 Object[] classList = classArrayList.toArray();
                 String buildRelateOne = (String)JOptionPane.showInputDialog(parentWindow, 
                                                                        "Choose first class", 
                                                                        "Association", 
                                                                        JOptionPane.PLAIN_MESSAGE,
                                                                        null,
                                                                        classList, 
                                                                        null); 
       
                 String buildRelateTwo = (String)JOptionPane.showInputDialog(parentWindow, 
                                                                           "Choose second class", 
                                                                           "Association", 
                                                                           JOptionPane.PLAIN_MESSAGE,
                                                                           null,
                                                                           classList, 
                                                                           null); 
                 //Add the relationship between the classes.
                 Class class1 = findClass(buildRelateOne);
                 Class class2 = findClass(buildRelateTwo);
                 Class.addRelationship(class1, class2, RelationshipType.GENERALIZATION);
                
                 //display relationship
                 updateDisplayRelationship(class1, class2);
              }
              else if(cmd.equals("DeleteRelate"))
              {
                  //TODO: Not working correctly, add ability to choose relationship and fix inputDiag
                  //create a dialog box with two dropdowns of available classes
                  ArrayList<String> classArrayList = getClassList();
                  Object[] classList = classArrayList.toArray();
                  String buildRelateOne = (String)JOptionPane.showInputDialog(parentWindow, 
                                                                         "Choose first class", 
                                                                         "Association", 
                                                                         JOptionPane.PLAIN_MESSAGE,
                                                                         null,
                                                                         classList, 
                                                                         null); 
        
                  String buildRelateTwo = (String)JOptionPane.showInputDialog(parentWindow, 
                                                                            "Choose second class", 
                                                                            "Association", 
                                                                            JOptionPane.PLAIN_MESSAGE,
                                                                            null,
                                                                            classList, 
                                                                            null); 
                  //Delete relationship between chosen two
                  Class class1 = findClass(buildRelateOne);
                  Class class2 = findClass(buildRelateTwo);
                  RelationshipType relation = class1.getRelationshipsToOther().get(buildRelateTwo);
                  Class.deleteRelationship(class1, class2, relation);
                  //add a try catch if false is returned
                  
                  //delete relationship from display
                  updateDisplayRelationship(class1, class2);

                }
                //delete relationship between chosen two

              }
           }
}

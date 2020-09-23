/*
    Author: Chris, Dominic, Tyler, Cory and Drew
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
	/**
	 * The model part of the GUI.
	 */
	private Store store;
	/**
     * Holds menu bar used to navigate program.
     */
    private JMenuBar mb;
    /**
     * The UML editor window.
     */
    private JFrame parentWindow;
    /**
     * Tracks if user has inputted a save name for the current content and stores files.
     */
    private String currentLoadedFile;
    /**
     * Stores the classes being used in the UML editor.
     */
    private ArrayList<Class> classStore;
    /**
     * Displays each individual class.
     */
	private Map<String, JPanel> classPanels;

    /**
     * Returns the currently loaded file.
     */
	public String getLoadedFilename()
	{
		return this.currentLoadedFile;
    }
    
    /**
     * Acts as contructor(due to timing with creating the gui elements) of the display as well as assembles menu.
     */
	public void createMenu(JFrame window)
	{
		//sets up the class store and panels
		classStore = new ArrayList<Class>();
		classPanels = new HashMap<String, JPanel>();
		parentWindow = window;
		//By default, this should be an empty string.
		currentLoadedFile = "";
		//Set up the menu with helpers and add them to the main window.
		parentWindow.setLayout(new GridLayout(5,5));
		mb = new JMenuBar();
		createFileMenu(mb);
		createClassMenu(mb);
		createAtrributeMenu(mb);
		createRelationshipMenu(mb);
		window.add(mb);
	}

    /**
     * Returns the mennu bar.
     */
	public JMenuBar getMenuBar()
	{
		return mb;
	}

    /**
     * Creates a file menu.
     */
	private void createFileMenu(JMenuBar mb)
	{
		JMenu file = new JMenu("File");
		//Sub-Menus.
		JMenuItem save = new JMenuItem("Save");
		JMenuItem saveAs = new JMenuItem("Save as...");
		JMenuItem load = new JMenuItem("Load...");
		JMenuItem exit = new JMenuItem("Exit");

		JMenuItem[] arr = {save, saveAs, load, exit};
		String[] text = {"Save edited file", "Save newly created file", "Load selected projectLoad selected project", "Exit application", "Exit application"};
		String[] command = {"Save", "SaveAs", "Load"};
		
		for(int count = 0; count < 4; ++count)
		{
			file.add(arr[count]);
			arr[count].setToolTipText(text[count]);
			if(count < 3)
			{
				arr[count].setActionCommand(command[count]);
				arr[count].addActionListener(new FileButtonClickListener());
			}
			else
				arr[count].addActionListener((event) -> System.exit(0));
		}		/*
		/*
		JMenu file = new JMenu("File");
		//Sub-Menus.
		JMenuItem save = new JMenuItem("Save");
		JMenuItem saveAs = new JMenuItem("Save as...");
		JMenuItem load = new JMenuItem("Load...");
		JMenuItem exit = new JMenuItem("Exit");
		//Add sub-menus.
		file.add(save);
		file.add(saveAs);
		file.add(load);
		file.add(exit);
		//hover tasks and events.
		save.setToolTipText("Save edited file");
		saveAs.setToolTipText("Save newly created file");
		load.setToolTipText("Load selected project");
		exit.setToolTipText("Exit application");
		exit.addActionListener((event) -> System.exit(0));
		//Add action commands.
		save.setActionCommand("Save");
		saveAs.setActionCommand("SaveAs");
		load.setActionCommand("Load");
		//Add action listener.
		save.addActionListener(new FileButtonClickListener());
		saveAs.addActionListener(new FileButtonClickListener());
		load.addActionListener(new FileButtonClickListener());
		*/
		mb.add(file);
	}

    /**
	 * Creates the class menu options by taking in the menu bar and adding them to it. 
	 */
	private void createClassMenu(JMenuBar mb)
	{

		JMenu classes = new JMenu("Class");

		//Create sub menus.
		JMenuItem crtClass = new JMenuItem("Create class");
		JMenuItem deleteClass = new JMenuItem("Delete class");
		JMenuItem rnClass = new JMenuItem("Rename class");

		JMenuItem[] arr = {crtClass, deleteClass, rnClass};
		String[] text = {"Create Class", "Delete Class", "Rename Class"};
		String[] command = {"Create", "Delete", "Rename"};

		for(int count = 0; count < 3; ++count)
		{
			classes.add(arr[count]);
			arr[count].setToolTipText(text[count]);
			arr[count].setActionCommand(command[count]);
			arr[count].addActionListener(new ClassButtonClickListener());
		}
		mb.add(classes);
	}

	/**
	 * Creates the attribute menu options by taking in the menu bar and adding them to it. 
	 */
	private void createAtrributeMenu(JMenuBar mb)
	{
		JMenu attributes = new JMenu("Attribute");
		
		//Create attribute sub-menu.
		JMenuItem crtAttr = new JMenuItem("Create attribute");
		JMenuItem deleteAttr = new JMenuItem("Delete attribute");
		JMenuItem rnAttr = new JMenuItem("Rename attribute");

		JMenuItem[] arr = {crtAttr, deleteAttr, rnAttr};
		String[] text = {"Create new attribute", "Delete a named attribute", "Rename a selected attribute"};
		String[] command = {"Create", "Delete", "Rename"};

		for(int count = 0; count < 3; ++count)
		{
			attributes.add(arr[count]);
			arr[count].setToolTipText(text[count]);
			arr[count].setActionCommand(command[count]);
			arr[count].addActionListener(new AttributeButtonClickListener());		
		}
		mb.add(attributes);
	}

    /**
	 * Creates the relationship menu options by taking in the menu bar and adding them to it. 
	 */
	private void createRelationshipMenu(JMenuBar mb)
	{
		JMenu relate = new JMenu("Relate");
		//Create JMenuItems for each type of relationship and deleting a relationship.
		JMenuItem association = new JMenuItem("Association");
		JMenuItem aggregation = new JMenuItem("Aggregation");
		JMenuItem composition = new JMenuItem("Composition");
		JMenuItem generalization = new JMenuItem("Generalization");
		JMenuItem deleteRelate = new JMenuItem("Delete Relationship");
		//Create arrays of JMenuItems and Strings to use for loop for setting up relationship menu.
		JMenuItem[] arr = {association, aggregation, composition, generalization, deleteRelate};
		String[] names = {"Association", "Aggregation", "Composition", "Generalization", "Delete Relationship"};
		
		for(int count = 0; count < 5; ++count)
		{
			relate.add(arr[count]);
			if(count < 4) 
				arr[count].setToolTipText("Creates selected relationship between two classes");
			else 
				arr[count].setToolTipText("Deletes selected relationship between two classes");
			
			arr[count].setActionCommand(names[count]);
			arr[count].addActionListener(new RelationshipButtonClickListener());
		}
		mb.add(relate);
	}

    /**
     * Creates a panel on the window to display information about a class.
     */
	private void makeNewClassPanel(Class aClass)
	{
		JPanel classPanel = new JPanel();
		classPanel.setSize(20,200);
		classPanel.setVisible(true);
		parentWindow.add(classPanel);
		JTextArea classText = new JTextArea(aClass.toString());
		classText.setEditable(false);
		classPanel.add(classText);
		classPanels.put(aClass.getName(), classPanel);
		Border blackline = BorderFactory.createLineBorder(Color.black);
		classText.setBorder(blackline);

	}

	/**
	 * Updates the relationships visually in the window .
	 *
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
	 * Makes and returns a combo box fill with the created classes.
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
	/**
	 * Takes in a set of attributes that are contained in a 
	 * class and returns them as an array list of strings.
	 */
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

    /**
     * Removes relevant relationships when classes are deleted.
     */
	private void removeRelationships(Class aClass)
	{
		//Find the classes that the class in question has a relationship with
		Map<String, RelationshipType> tempTo = aClass.getRelationshipsToOther();
		Map<String, RelationshipType> tempFrom = aClass.getRelationshipsFromOther();
		for(Map.Entry<String, RelationshipType> entry : tempTo.entrySet()) 
		{
			//Go to those classes and get rid of relationships to and from the class in question.
			Class temp = findClass(entry.getKey());
			temp.deleteRelationshipFromOther(entry.getValue(), aClass);
			temp.deleteRelationshipToOther(entry.getValue(), aClass);
		}
		for(Map.Entry<String, RelationshipType> entry : tempFrom.entrySet()) 
		{
			//Go to those classes and get rid of relationships to and from the class in question.
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
	/**
	 * Gets text from user input(text box)
	 */
	private String getTextFromInput(String message)
	{
		String strToRtn = JOptionPane.showInputDialog(parentWindow, message, JOptionPane.PLAIN_MESSAGE);
		
		return strToRtn;
	}

	private String getResultFromComboBox(String msgOne, String msgTwo, Object[] objArr)
	{
		String strToRtn = (String)JOptionPane.showInputDialog(parentWindow, 
																 msgOne, 
																 msgTwo, 
																 JOptionPane.PLAIN_MESSAGE,
																 null,
																 objArr, 
																 null);
		return strToRtn;
	}
	/**
	 * Private class to handle buttons clicked in the file menu. 
	 */
	private class FileButtonClickListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String cmd = e.getActionCommand();
			if(cmd.equals("Save"))
			{
				//Save contents to file...will require JSON save.
				//TODO: Make me a save screen.
				//If there is a currently loaded file.
				if (!currentLoadedFile.equals(""))
				{
					SaveAndLoad.save(currentLoadedFile, classStore);
				}
				else
				{
					//TODO: Make me a save as screen.
					//Bring up file panel for the user to save as(automatically will choose file type though).
					String fileName = JOptionPane.showInputDialog("Filename: ");
					SaveAndLoad.save(fileName +".json", classStore);
					//TODO: Consider what to do should the file creation fail. 
				}
			}
			else if(cmd.equals("SaveAs"))
			{
				//Bring up file panel for the user to save as(automatically will choose file type though)
				String fileName = JOptionPane.showInputDialog("Filename: ");
				SaveAndLoad.save(fileName + ".json", classStore);
				//TODO: Consider what to do should the file creation fail. 
			}
			else if(cmd.equals("Load"))
			{
				//Bring up file panel to load a UML design from JSON.
				//TODO: Make me a filechooser.
				String fileName = JOptionPane.showInputDialog("Filename: ");
				SaveAndLoad.load(fileName, classStore);
			}
		}
	}
	/**
	 * Private class that handles all button clicks on the Class menu option.
	 */
	private class ClassButtonClickListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String cmd = e.getActionCommand();
			if(cmd.equals("Create"))
			{
				//Load text input box to get the name of the new class to be created.
				String className = getTextFromInput("Class name: ");
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
				//Load dropdown of available classes to delete.
				ArrayList<String> classArrayList = getClassList();
				Object[] classList = classArrayList.toArray();
				
				String toBeDeleted = getResultFromComboBox("Delete this class", "Delete a class", classList);
				//Delete the class. 
				//find it in storage array.
				Class temp = findClass(toBeDeleted);
				
				if(findClass(toBeDeleted) != null)
				{
					JPanel panel = classPanels.get(toBeDeleted);
					parentWindow.remove(panel);
					//Delete relationships before deleting class.
					removeRelationships(temp);
					classStore.remove(temp);
					classPanels.remove(temp.getName());
					parentWindow.revalidate();
					parentWindow.repaint(); 
				}
			}
			else if(cmd.equals("Rename"))
			{
				//Load dropdown of created classes.
				ArrayList<String> classArrayList = getClassList();
				Object[] classList = classArrayList.toArray();
				String toBeRenamed = getResultFromComboBox("Rename this class", "Rename a class", classList);
				//Open text dialog to get the new class name. 
				String newClassName = getTextFromInput("New Class Name");
				//Rename that class.
				//This is done so that we don't give a class a name that is already taken.
				if(findClass(newClassName) == null)
				{
					Class temp = findClass(toBeRenamed);
					temp.setName(newClassName);
					JPanel panel = classPanels.get(toBeRenamed);
					JTextArea textArea = (JTextArea)panel.getComponents()[0];
					textArea.setText(temp.toString());
					classPanels.remove(toBeRenamed);
					classPanels.put(newClassName, panel);
					parentWindow.revalidate();
					parentWindow.repaint();
				}
			}
		}
	}
	/**
	 * Private class that handles all button clicks on the Attribute menu option.
	 */
	private class AttributeButtonClickListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String cmd = e.getActionCommand();
			if(cmd.equals("Create"))
			{
				//TODO: Consider making a custom window for this? It would make it look cleaner in the future.
				//Create a drop down list of created classes.
				ArrayList<String> classArrayList = getClassList();
				Object[] classList = classArrayList.toArray();
				String className = getResultFromComboBox("Create Attribute for this class", "Create atrribute", classList);
				//Get Type from user.
				String type = getTextFromInput("Type: ");
				//Get name from user.
				String name = getTextFromInput("Name: ");
				//Find the class in the storage and add an attribute to it.
				Class classToAddAttrTo = findClass(className);
				classToAddAttrTo.addAttribute(type, name);

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
				String className = getResultFromComboBox("Delete Attribute for this class", "Delete atrribute", classList);
				
				//Get class from storage.
				Class classToDeleteFrom = findClass(className);
				
				//Get atrributes from the class. 
				Set<Attribute> attributes = classToDeleteFrom.getAttributes();
				
				//Get the atrributes in a combo box.
				ArrayList<String> attributeArrayList = getAttributeList(attributes);
				Object[] attributeList = attributeArrayList.toArray();
				
				//Get attribute to delete.
				String attribute = getResultFromComboBox("Delete this atrribute", "Delete atrribute", attributeList);
				String[] att = attribute.split(" ");
				
				//Delete the attribute.
				classToDeleteFrom.deleteAttribute(att[1]);
				
				//Delete attr in window and revalidate/repaint.
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
				//Load combo box to get the class to be renamed.
				ArrayList<String> classArrayList = getClassList();
				Object[] classList = classArrayList.toArray();
				String className = getResultFromComboBox("Rename Attribute for this class", "Rename atrribute", classList);
				//Get class from storage.
				Class classToRenameFrom = findClass(className);
				//Get atrributes from the class. 
				Set<Attribute> attributeSet = classToRenameFrom.getAttributes();
				ArrayList<String> attributeArrayList = getAttributeList(attributeSet);
				Object[] attributeList = attributeArrayList.toArray();
				//Get attribute to rename.
				String attribute = getResultFromComboBox("Rename this attribute", "Rename atrribute", attributeList);
				
				//Open text input for new name.
				String newAttribute = getTextFromInput("Enter new attribute name: ");
				String[] att = attribute.split(" ");
				classToRenameFrom.renameAttribute(att[1], newAttribute);
				JPanel panel = classPanels.get(className); 
				JTextArea textArea = (JTextArea)panel.getComponents()[0];
				textArea.setText(classToRenameFrom.toString());
				//Leave remove before put
				classPanels.remove(className);
				classPanels.put(className, panel);
				parentWindow.revalidate();
				parentWindow.repaint();
			}
		}
	}
	/**
	 * Private class that handles all button clicks on the relationship menu option.
	 */
	private class RelationshipButtonClickListener implements ActionListener
	{             
		public void actionPerformed(ActionEvent e)
		{
			//TODO: Prevent class from having a relationship to itself.
			String cmd = e.getActionCommand();
			if(cmd.equals("Association"))
			{
				//Creates two dialog boxes to get the classes to add the relationship to or from.
				ArrayList<String> classArrayList = getClassList();
				Object[] classList = classArrayList.toArray();
				String buildRelateOne = getResultFromComboBox("Choose first class", "Association", classList);

				String buildRelateTwo = getResultFromComboBox("Choose second class", "Association", classList);
				
				//Add the relationship between the classes.
				Class class1 = findClass(buildRelateOne);
				Class class2 = findClass(buildRelateTwo);
				//Change add relationship.
				class1.addRelationshipToOther(RelationshipType.ASSOCIATION, class2);
				//Create relationship between chosen two, a relationship from and to must be made.
				updateDisplayRelationship(class1, class2);
			}
			else if(cmd.equals("Aggregation"))
			{
				ArrayList<String> classArrayList = getClassList();
				Object[] classList = classArrayList.toArray();
				
				//Creates two dialog boxes to get the classes to add the relationship to or from.
				String buildRelateOne = getResultFromComboBox("Choose first class", "Aggregation", classList);

				String buildRelateTwo = getResultFromComboBox("Choose second class", "Aggregation", classList);

				//Add the relationship between the classes.
				Class class1 = findClass(buildRelateOne);
				Class class2 = findClass(buildRelateTwo);
				//Change add relationship.
				class1.addRelationshipToOther(RelationshipType.AGGREGATION, class2);
				updateDisplayRelationship(class1, class2);
			}
			else if(cmd.equals("Composition"))
			{
				//creates two dialog boxes to get the classes to add the relationship to or from.
				ArrayList<String> classArrayList = getClassList();
				Object[] classList = classArrayList.toArray();
				
				String buildRelateOne = getResultFromComboBox("Choose first class", "Composition",classList);

				String buildRelateTwo = getResultFromComboBox("Choose second class", "Composition", classList);
				
				//Add the relationship between the classes.
				Class class1 = findClass(buildRelateOne);
				Class class2 = findClass(buildRelateTwo);
				//Chnage add relationship
				class1.addRelationshipToOther(RelationshipType.COMPOSITION, class2);
				//display relationship 
				updateDisplayRelationship(class1, class2);
			}
			else if(cmd.equals("Generalization"))
			{
				//Creates two dialog boxes to get the classes to add the relationship to or from.
				ArrayList<String> classArrayList = getClassList();
				Object[] classList = classArrayList.toArray();
				String buildRelateOne = getResultFromComboBox("Choose first class", "Generalization",classList);

				String buildRelateTwo = getResultFromComboBox("Choose first class", "Generalization",classList);
				//Add the relationship between the classes.
				Class class1 = findClass(buildRelateOne);
				Class class2 = findClass(buildRelateTwo);
				//Change add relationship.
				class1.addRelationshipToOther(RelationshipType.GENERALIZATION, class2);
				//Display relationship.
				updateDisplayRelationship(class1, class2);
			}
			else if(cmd.equals("DeleteRelate"))
			{
				//TODO: Not working correctly, need to display the relationship to the user
				//Create a dialog box with two dropdowns of available classes.
				ArrayList<String> classArrayList = getClassList();
				Object[] classList = classArrayList.toArray();
				String buildRelateOne = getResultFromComboBox("Choose first class", " Class",classList);

				String buildRelateTwo = getResultFromComboBox("Choose Second class", " Class",classList);
				
				//Delete relationship between chosen two.
				Class class1 = findClass(buildRelateOne);
				Class class2 = findClass(buildRelateTwo);
				RelationshipType relation = class1.getRelationshipsToOther().get(buildRelateTwo);
				//Change deleteRelationship.
				class1.deleteRelationshipToOther(relation, class2);
				class2.deleteRelationshipToOther(relation, class1);
				//TODO: Add a try catch if false is returned.
				//Delete relationship from display.
				updateDisplayRelationship(class1, class2);
			}
		}
	}
}

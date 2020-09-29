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
import javax.swing.JFileChooser;
import java.awt.event.*;
import java.io.File;
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
     * Stores the classes being used in the UML editor.
     */
    private ArrayList<Class> classStore;
    /**
     * Displays each individual class.
     */
	private Map<String, JPanel> classPanels;

    /**
     * Acts as contructor(due to timing with creating the gui elements) of the display as well as assembles menu.
     */
	public void createMenu(JFrame window)
	{
		store = new Store();
		//sets up the class store and panels
		classStore = new ArrayList<Class>();
		classPanels = new HashMap<String, JPanel>();
		parentWindow = window;
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
		}		
		
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
	 * Creates the field menu options by taking in the menu bar and adding them to it. 
	 */
	private void createAtrributeMenu(JMenuBar mb)//change spelling later on
	{
		JMenu fields = new JMenu("Field");
		
		//Create field sub-menu.
		JMenuItem crtField = new JMenuItem("Create field");
		JMenuItem deleteField = new JMenuItem("Delete field");
		JMenuItem rnField = new JMenuItem("Rename field");
		//Add option to change the field's type.
		JMenuItem chgFieldType = new JMenuItem("Change field type");
		
		//Create method buttons
		JMenuItem crtMethod = new JMenuItem("Create method");
		JMenuItem deleteMethod = new JMenuItem("Delete method");
		JMenuItem rnMethod = new JMenuItem("Rename method");

		JMenuItem[] arr = {crtField, deleteField, rnField, chgFieldType, crtMethod, deleteMethod, rnMethod};
		String[] text = {"Create new field", "Delete a named field", "Rename a selected field","Changes the field's type", "Create new method","Delete a named method", "Rename a selected method"};
		String[] command = {"CreateField", "DeleteField", "RenameField", "ChangeFieldType","CreateMethod", "DeleteMethod", "RenameMethod"};

		for(int count = 0; count < 7; ++count)
		{
			fields.add(arr[count]);
			arr[count].setToolTipText(text[count]);
			arr[count].setActionCommand(command[count]);
			arr[count].addActionListener(new FieldButtonClickListener());		
		}
		mb.add(fields);
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
     * Removes relevant relationships when classes are deleted.
     */
	/*
	private void removeRelationships(Class aClass)
	{
		//Find the classes that the class in question has a relationship with
		Map<String, RelationshipType> tempTo = aClass.getRelationshipsToOther();
		Map<String, RelationshipType> tempFrom = aClass.getRelationshipsFromOther();
		for(Map.Entry<String, RelationshipType> entry : tempTo.entrySet()) 
		{
			//Go to those classes and get rid of relationships to and from the class in question.
			Class temp = store.findClass(entry.getKey());
			temp.deleteRelationshipFromOther(entry.getValue(), aClass);
			temp.deleteRelationshipToOther(entry.getValue(), aClass);
		}
		for(Map.Entry<String, RelationshipType> entry : tempFrom.entrySet()) 
		{
			//Go to those classes and get rid of relationships to and from the class in question.
			Class temp = store.findClass(entry.getKey());
			temp.deleteRelationshipFromOther(entry.getValue(), aClass);
			temp.deleteRelationshipToOther(entry.getValue(), aClass);
		}
	}
	*/
	
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
				File currentLoadedFile = store.getCurrentLoadedFile();
				//Save contents to file...will require JSON save.
				//If there is a currently loaded file.
				if (currentLoadedFile != null)
				{
					SaveAndLoad.save(currentLoadedFile, classStore);
				}
				else
				{
					performSaveAs();
				}
			}
			else if(cmd.equals("SaveAs"))
			{
				performSaveAs();
			}
			else if(cmd.equals("Load"))
			{
				//before clearing the current data, ask if the user wishes to save?
				if(checkForSave())
				{
					performSaveAs();
				}
				else
				{
					//Clear old data.
					for(Map.Entry<String, JPanel> panel : classPanels.entrySet())
					{
						parentWindow.remove(panel.getValue());
					}
					classStore.clear();
					classPanels.clear();
					
					parentWindow.revalidate();
					parentWindow.repaint();
					//Load the new data from file.
					loadData();
				}

			}
		}
		/**
		 * Brings up a box for the user to provide a name for their file.
		 */
		private void performSaveAs()
		{
			JFileChooser fc = new JFileChooser();
			//If there is a currently loaded file.

			//Bring up file panel for the user to save as(automatically will choose file type though in saveandload).
			int returnValue = fc.showSaveDialog(parentWindow);
			//Based on the user imput, save the file. 
			
			if(returnValue == JFileChooser.APPROVE_OPTION)
			{
				SaveAndLoad.save(fc.getSelectedFile(), classStore);
				store.setCurrentLoadedFile(fc.getSelectedFile() );
				JOptionPane.showMessageDialog(parentWindow,"File " + store.getCurrentLoadedFile().getName() + " was saved.", "File Saved", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		private boolean checkForSave()
		{
			boolean save = false;
			int rtnValue = JOptionPane.showConfirmDialog(parentWindow, "Do you want to save first?");

			if(rtnValue == JOptionPane.OK_OPTION)
			{
				save = true;
			}

			return save;
		}

		private void loadData()
		{
			//Bring up file panel to load a UML design from JSON.
			//Make a filechooser
			JFileChooser fc = new JFileChooser();
			int returnValue = fc.showOpenDialog(parentWindow);
			//If the user selected to open this file, open it.
			//TODO: Consider filtering this information to only inlcude JSON filetypes
			if(returnValue == JFileChooser.APPROVE_OPTION)
			{
				File fileToOpen = fc.getSelectedFile();
				SaveAndLoad.load(fileToOpen, classStore);
				store.setCurrentLoadedFile(fileToOpen);

				for(Class aClass : classStore)
				{
					makeNewClassPanel(aClass);
				}

				parentWindow.revalidate();
				parentWindow.repaint();
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
				boolean temp = store.addClass(className);
				if(temp)
					makeNewClassPanel(store.findClass(className));
			}
			else if(cmd.equals("Delete"))
			{
				//get list of objects to display to user
				Object[] classList = store.getClassList().toArray();
				
				//Get the class to be deleted. 
				String toBeDeleted = getResultFromComboBox("Delete this class", "Delete a class", classList);

				//Delete the class. 
				//find it in storage array.
				boolean temp = store.deleteClass(toBeDeleted);

				if(temp)
				{
					JPanel panel = classPanels.get(toBeDeleted);
					parentWindow.remove(panel);
					classPanels.remove(toBeDeleted);
					parentWindow.revalidate();
					parentWindow.repaint(); 
				}
			}
			else if(cmd.equals("Rename"))
			{
				//Load dropdown of created classes.
				Object[] classList = store.getClassList().toArray();

				String toBeRenamed = getResultFromComboBox("Rename this class", "Rename a class", classList);
				//Open text dialog to get the new class name. 
				String newClassName = getTextFromInput("New Class Name");
				//Rename that class.
				//This is done so that we don't give a class a name that is already taken.
				boolean temp = store.renameClass(toBeRenamed, newClassName);
				if(temp)
				{
					JPanel panel = classPanels.get(toBeRenamed);
					JTextArea textArea = (JTextArea)panel.getComponents()[0];
					textArea.setText(store.findClass(newClassName).toString());
					classPanels.remove(toBeRenamed);
					classPanels.put(newClassName, panel);
					parentWindow.revalidate();
					parentWindow.repaint();
				}
			}
		}
	}
	/**
	 * Private class that handles all button clicks on the Field menu option.
	 * 
	 * !!!! Change name to AttributeButtonClickListener? !!!!
	 */
	private class FieldButtonClickListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//Create a drop down list of created classes.
			Object[] classList = store.getClassList().toArray();

			String className = getResultFromComboBox("Create Field for this class", "Create atrribute", classList);

			String cmd = e.getActionCommand();
			if(cmd.equals("CreateField"))
			{
				//TODO: Consider making a custom window for this? It would make it look cleaner in the future.
				//Get Type from user.
				String type = getTextFromInput("Type: ");
				//Get name from user.
				String name = getTextFromInput("Name: ");
				//Add field to the class using received params.
				store.addField(className, type, name);

				JPanel panel = classPanels.get(className);
				JTextArea textArea = (JTextArea)panel.getComponents()[0];
				textArea.setText(store.findClass(className).toString());

				windowRefresh(className, panel);

			}
			else if(cmd.equals("DeleteField"))
			{
				//Get class from storage.
				Class classToDeleteFrom = store.findClass(className);
				
				//Get the atrributes to be placed in a combo box.
				Object[] fieldList = store.getFieldList(classToDeleteFrom.getFields()).toArray();
				
				//Get field to delete.
				String field = getResultFromComboBox("Delete this atrribute", "Delete atrribute", fieldList);
				String[] att = field.split(" ");

				//Delete the field.
				store.deleteField(className, att[1]);
				
				//Delete attr in window and revalidate/repaint.
				JPanel panel = classPanels.get(className); 
				JTextArea textArea = (JTextArea)panel.getComponents()[0];
				textArea.setText(classToDeleteFrom.toString());
				windowRefresh(className, panel);
			}
			else if(cmd.equals("RenameField"))
			{
				//Get class from storage.
				Class classToRenameFrom = store.findClass(className);

				//Get a list of atrributes from the class.
				Object[] fieldList = store.getFieldList(classToRenameFrom.getFields()).toArray();

				//Get field to rename.
				String field = getResultFromComboBox("Rename this field", "Rename atrribute", fieldList);
				
				//Open text input for new name.
				String newField = getTextFromInput("Enter new field name: ");
				String[] att = field.split(" ");

				//Rename the field.
				store.renameField(className, att[1], newField);

				JPanel panel = classPanels.get(className); 
				JTextArea textArea = (JTextArea)panel.getComponents()[0];
				textArea.setText(store.findClass(className).toString());

				windowRefresh(className, panel);
			}
			else if(cmd.equals("ChangeFieldType"))
			{
				//Get class from storage.
				Class classToChangeTypeFrom = store.findClass(className);

				//Get a list of atrributes from the class.
				Object[] fieldList = store.getFieldList(classToChangeTypeFrom.getFields()).toArray();
				
				//Get field to change it's type.
				String field = getResultFromComboBox("Change this field's type", "Change Field Type", fieldList);

				//get the new type from the user and let the store know it can change. 
				String newType = getTextFromInput("New type: ");
				String[] att = field.split(" ");
				store.changeFieldType(className, newType, att[1]);

				JPanel panel = classPanels.get(className); 
				JTextArea textArea = (JTextArea)panel.getComponents()[0];
				textArea.setText(store.findClass(className).toString());

				windowRefresh(className, panel);
			}
			else if(cmd.equals("CreateMethod"))
			{
				//Get Type from user.
				String returnType = getTextFromInput("Return Type: ");
				//Get name from user.
				String name = getTextFromInput("Method Name: ");
				//Get number of parameters from user.
				int paramNum = getNumberFromInput();

				ArrayList<String> params = new ArrayList<String>();
				
				for(int count = 0; count < paramNum; ++count)
				{
					String paramType = getTextFromInput("Parameter Type: ");
					String paramName = getTextFromInput("Parameter Name: ");
					params.add(paramType + " " + paramName);
				}
				//Add field to the class using received params.
				store.addMethod(className, returnType, name, params);
				JPanel panel = classPanels.get(className);
				JTextArea textArea = (JTextArea)panel.getComponents()[0];
				textArea.setText(store.findClass(className).toString());

				windowRefresh(className, panel);

			}
			else if(cmd.equals("DeleteMethod"))
			{
				//Get a list of methods to show user in a combo box.
				Object[] methods = store.getMethodList(store.findClass(className).getMethods()).toArray();
				//Get their choice
				String methodString = getResultFromComboBox("Delete method", "DeleteMethod", methods);


				store.removeMethodByString(store.findClass(className).getMethods(), methodString, className);

				JPanel panel = classPanels.get(className);
				JTextArea textArea = (JTextArea)panel.getComponents()[0];
				textArea.setText(store.findClass(className).toString());

				windowRefresh(className, panel);

			}
			else if(cmd.equals("RenameMethod"))
			{
				Object[] methodList = store.getMethodList(store.findClass(className).getMethods()).toArray();
				String method = getResultFromComboBox("Rename this method", "Rename method", methodList);
				
				String newMethod = getTextFromInput("Enter new method name: ");
				store.renameMethodByString(store.findClass(className).getMethods(), method, className, newMethod);
				
				JPanel panel = classPanels.get(className);
				JTextArea textArea = (JTextArea)panel.getComponents()[0];
				textArea.setText(store.findClass(className).toString());

				windowRefresh(className, panel);
			}
		}
		
		private void windowRefresh(String className, JPanel panel)
		{
			//Leave remove before put
			classPanels.remove(className);
			classPanels.put(className, panel);
			parentWindow.revalidate();
			parentWindow.repaint();
		}

		private int getNumberFromInput()
		{
			int paramNum;
			//Ensure we get a number, defaults to zero on bad input.
			try 
			{
				paramNum = Integer.parseInt(getTextFromInput("Number of Parameters: "));
			}
			catch(NumberFormatException e)
			{
				paramNum = 0;
			}

			return paramNum;
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
				Object[] classList = store.getClassList().toArray();

				String buildRelateOne = getResultFromComboBox("Choose first class", "Association", classList);

				String buildRelateTwo = getResultFromComboBox("Choose second class", "Association", classList);
				
				//Add the relationship between the classes.
				Class class1 = store.findClass(buildRelateOne);
				Class class2 = store.findClass(buildRelateTwo);
				//Change add relationship.
				store.addRelationship(buildRelateOne, buildRelateTwo, RelationshipType.ASSOCIATION);
				//Create relationship between chosen two, a relationship from and to must be made.
				updateDisplayRelationship(class1, class2);
			}
			else if(cmd.equals("Aggregation"))
			{
				Object[] classList = store.getClassList().toArray();
				
				//Creates two dialog boxes to get the classes to add the relationship to or from.
				String buildRelateOne = getResultFromComboBox("Choose first class", "Aggregation", classList);

				String buildRelateTwo = getResultFromComboBox("Choose second class", "Aggregation", classList);

				//Add the relationship between the classes.
				Class class1 = store.findClass(buildRelateOne);
				Class class2 = store.findClass(buildRelateTwo);
				//Change add relationship.
				store.addRelationship(buildRelateOne, buildRelateTwo, RelationshipType.AGGREGATION);
				updateDisplayRelationship(class1, class2);
			}
			else if(cmd.equals("Composition"))
			{
				//creates two dialog boxes to get the classes to add the relationship to or from.
				Object[] classList = store.getClassList().toArray();
				
				String buildRelateOne = getResultFromComboBox("Choose first class", "Composition",classList);

				String buildRelateTwo = getResultFromComboBox("Choose second class", "Composition", classList);
				
				//Add the relationship between the classes.
				Class class1 = store.findClass(buildRelateOne);
				Class class2 = store.findClass(buildRelateTwo);
				//Chnage add relationship
				store.addRelationship(buildRelateOne, buildRelateTwo, RelationshipType.COMPOSITION);
				//display relationship 
				updateDisplayRelationship(class1, class2);
			}
			else if(cmd.equals("Generalization"))
			{
				//Creates two dialog boxes to get the classes to add the relationship to or from.
				Object[] classList = store.getClassList().toArray();

				String buildRelateOne = getResultFromComboBox("Choose first class", "Generalization",classList);

				String buildRelateTwo = getResultFromComboBox("Choose first class", "Generalization",classList);
				//Add the relationship between the classes.
				Class class1 = store.findClass(buildRelateOne);
				Class class2 = store.findClass(buildRelateTwo);
				//Change add relationship.
				store.addRelationship(buildRelateOne, buildRelateTwo, RelationshipType.GENERALIZATION);
				//Display relationship.
				updateDisplayRelationship(class1, class2);
			}
			else if(cmd.equals("Delete Relationship"))
			{
				//TODO: Not working correctly, need to display the relationship to the user
				//Create a dialog box with two dropdowns of available classes.
				Object[] classList = store.getClassList().toArray();

				String buildRelateOne = getResultFromComboBox("Choose first class", " Class",classList);

				String buildRelateTwo = getResultFromComboBox("Choose Second class", " Class",classList);
				
				//Delete relationship between chosen two.
				Class class1 = store.findClass(buildRelateOne);
				Class class2 = store.findClass(buildRelateTwo);
				//Change deleteRelationship.
				store.deleteRelationship(buildRelateOne, buildRelateTwo);
				//TODO: Add a try catch if false is returned.
				//Delete relationship from display.
				updateDisplayRelationship(class1, class2);
			}
		}
	}
}

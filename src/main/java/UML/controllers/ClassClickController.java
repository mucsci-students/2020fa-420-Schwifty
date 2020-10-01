package UML.controllers;

import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import UML.model.Class;
import UML.model.Field;
import UML.model.Method;
import UML.model.Parameter;
import UML.model.SaveAndLoad;
import UML.model.Store;

import UML.views.GraphicalView;

public class ClassClickController implements ActionListener
{
	private Store store;
	private GraphicalView view;
	private Controller controller;
	
	public ClassClickController(Store store, GraphicalView v) {
		this.view = v;
		this.store = store;
		this.controller = new Controller(store, v);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		if(cmd.equals("Create"))
		{
			//Load text input box to get the name of the new class to be created.
			String className = view.getInputFromUser("Class name: ");
			//Create the class.
			controller.createClass(className);
			
		}
		else if(cmd.equals("Delete"))
		{
			//Gets ArrayList of classes to be chosen from.
			ArrayList<String> classList = store.getClassList();
			
			//Get the class to be deleted. 
			String toBeDeleted = view.getChoiceFromUser("Delete this class", "Delete a class", classList);

			//Delete the class. 
			controller.deleteClass(toBeDeleted);
		}
		else if(cmd.equals("Rename"))
		{
			//Load dropdown of created classes.
			ArrayList<String> classList = store.getClassList();

			String toBeRenamed = view.getChoiceFromUser("Rename this class", "Rename a class", classList);
			//Open text dialog to get the new class name. 
			String newClassName = view.getInputFromUser("New Class Name");
			//Rename that class.
			//This is done so that we don't give a class a name that is already taken.
			controller.renameClass(toBeRenamed, newClassName);
		}
	}
}
package UML.controllers;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/06/2020
    Purpose: Creates the action listeners for the class buttons.
 */
import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import UML.model.Store;
import UML.views.View;

public class ClassClickController implements ActionListener
{
	private Store store;
	private View view;
	private Controller controller;
	
	public ClassClickController(Store s, View v, Controller c) {
		this.view = v;
		this.store = s;
		this.controller = c;
	}
	/**
	* Handles actions performed on a class 
	 */
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		if(cmd.equals("Create"))
		{
			//Load text input box to get the name of the new class to be created.
			String className = handleExceptions("Class Name: ", "Invalid class name");
			//Create the class.
			controller.createClass(className);
			String classText = store.findClass(className).toString();
			//view.addListener(new MouseClickAndDragController(store, view, controller), classText);
		}
	}

	/**
	 * Prevents exceptions given exceptional behavior.
	 */
	private String handleExceptions(String prompt, String error)
    {
        boolean canCreate = false;
        String name = view.getInputFromUser(prompt);
			while(!canCreate)
			{
				if(name.trim().equals("") || name.contains(" "))
				{
					view.showError(error);
					name = view.getInputFromUser(prompt);
					canCreate = !(name.trim().equals("") || name.contains(" "));
				}
				else
					canCreate = true;
            }
        return name;
    }
}
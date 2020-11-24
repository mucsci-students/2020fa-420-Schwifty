package UML.controllers;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/06/2020
    Purpose: Creates the action listeners for the class buttons.
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.FlowLayout;

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
	 * Handles actions performed on a class.
	 */
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		if(cmd.equals("Create"))
		{
			String name = "";
			boolean valid = false;
			while(!valid)
        	{
				JPanel panel = new JPanel();
				panel.setLayout(new FlowLayout());
				JTextArea nameArea = new JTextArea(1, 12);
				panel.add(nameArea);
				
				int result = JOptionPane.showConfirmDialog(null, panel,
					"Create class", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);

				if (result == JOptionPane.OK_OPTION) {
					//Get name String.
					name = (String) ((JTextArea) panel.getComponent(0)).getText();
				}
				//Cancel.
				else if(result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION)
				{
					return;
				}

				//Handle exceptional cases.    
				if(name.trim().equals("") || name.contains(" "))
				{
					view.showError("Class name must not contain spaces or be blank.");
				}
				else
					valid = true;
			}
			//Create the class.
			controller.createClass(name);
        }
	}
}
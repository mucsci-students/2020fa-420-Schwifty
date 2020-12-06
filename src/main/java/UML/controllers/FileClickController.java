package UML.controllers;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/06/2020
    Purpose: Creates the action listeners for the file buttons.
 */
import java.awt.event.*;
import UML.views.*;

import UML.model.Store;
import java.io.File;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public class FileClickController implements ActionListener {
	private Store store;
	private View view;
	private Controller controller;

	public FileClickController(Store s, View v, Controller c) {
		this.view = v;
		this.store = s;
		this.controller = c;
	}

	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		if (cmd.equals("Save")) 
		{
			try
			{
				File currentLoadedFile = store.getCurrentLoadedFile();
			
				// Save contents to file...will require JSON save.
				// If there is a currently loaded file.
				if (currentLoadedFile != null) 
				{
					controller.save(currentLoadedFile.getName());
				} 
				else 
				{
					String fileName = view.save();
					controller.save(fileName);
				}
			}
			catch(IOException io)
			{
				view.showError("IOException found");
			}
		} 
		else if (cmd.equals("SaveAs")) 
		{
			try
			{
				String fileName = view.save();
				controller.save(fileName);
			}
			catch(IOException io)
			{
				view.showError("IOException found");
			}
		} 
		else if (cmd.equals("Load")) 
		{
			try
			{
				String fileName = view.load();
				controller.load(fileName);
			}
			catch(IOException io)
			{
				view.showError("IOException found");
			}
			catch(ParseException pe)
			{
				view.showError("IOException found");
			}
		}
	}
}
package UML.controllers;

import java.awt.event.*;

import UML.views.*;

import UML.model.Class;
import UML.model.Field;
import UML.model.Method;
import UML.model.Parameter;
import UML.model.Store;
import UML.model.RelationshipType;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import javax.swing.JPanel;

public class FileClickController implements ActionListener
{
	private Store store;
	private View view;
	private Controller controller;

	public FileClickController(Store s, View v, Controller c) 
	{
		this.view = v;
		this.store = s;
		this.controller = c;
	}

	public void actionPerformed(ActionEvent e) 
	{
		String cmd = e.getActionCommand();
		if (cmd.equals("Save")) 
		{
			File currentLoadedFile = store.getCurrentLoadedFile();
			// Save contents to file...will require JSON save.
			// If there is a currently loaded file.
			if (currentLoadedFile != null) {
				SaveAndLoad.save(currentLoadedFile.getName(), store.getClassStore());
			} else 
			{
				String fileName = view.save();
				controller.save(fileName);
			}
		} 
		else if (cmd.equals("SaveAs")) 
		{
			String fileName = view.save();
			controller.save(fileName);
		} 
		else if (cmd.equals("Load")) 
		{
			String fileName = view.load();
			controller.load(fileName);
		}
	}
}
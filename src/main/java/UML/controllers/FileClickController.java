package UML.controllers;

import java.awt.event.*;

import UML.views.*;

import UML.model.Class;
import UML.model.Field;
import UML.model.Method;
import UML.model.Parameter;
import UML.model.SaveAndLoad;
import UML.model.Store;
import java.io.File;
import java.lang.ModuleLayer.Controller;
import java.util.Map;
import java.util.HashMap;
import javax.swing.JPanel;

public class FileClickController implements ActionListener {
	private Store store;
	private View view;
	private Controller controller;

	public FileClickController(Store store, View v) {
		this.view = v;
		this.store = store;
		this.controller = new Controller(store, v);
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
				SaveAndLoad.save(currentLoadedFile.getName(), store.getClassList());
			} else 
			{
				String fileName = view.save();
				controller.save(fileName);
				store.setCurrentLoadedFile(file);
			}
		} 
		else if (cmd.equals("SaveAs")) 
		{
			performSaveAs();
		} 
		else if (cmd.equals("Load")) 
		{
			// before clearing the current data, ask if the user wishes to save?
			if (checkForSave()) {
				
				performSaveAs(view.save());
			} 
			else {
				// Clear old data.
				for (Map.Entry<String, JPanel> panel : classPanels.entrySet()) 
				{
					parentWindow.remove(panel.getValue());
				}
				classStore.clear();
				classPanels.clear();

				parentWindow.revalidate();
				parentWindow.repaint();
				// Load the new data from file.
				loadData();
			}
		}
	}

	/**
	 * Brings up a box for the user to provide a name for their file.
	 */
	private void performSaveAs(String fileName) 
	{
		File file = controller.save(fileName, classStore);
		store.setCurrentLoadedFile(file);
	}

	private boolean checkForSave() {
		boolean save = false;
		int rtnValue = JOptionPane.showConfirmDialog(parentWindow, "Do you want to save first?");

		if (rtnValue == JOptionPane.OK_OPTION) {
			save = true;
		}

		return save;
	}

	private void loadData() {
		// Bring up file panel to load a UML design from JSON.
		// Make a filechooser
		JFileChooser fc = new JFileChooser();
		int returnValue = fc.showOpenDialog(parentWindow);
		// If the user selected to open this file, open it.
		// TODO: Consider filtering this information to only inlcude JSON filetypes
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File fileToOpen = fc.getSelectedFile();
			SaveAndLoad.load(fileToOpen, classStore);
			store.setCurrentLoadedFile(fileToOpen);

			for (Class aClass : classStore) {
				makeNewClassPanel(aClass);
			}

			parentWindow.revalidate();
			parentWindow.repaint();
		}
	}
}
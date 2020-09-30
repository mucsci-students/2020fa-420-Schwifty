package UML.controllers;

public class FileButtonClickController implements ActionListener
{
	private Store store;
	private View view;

	public FileButtonClickController(Store store, View v) 
	{
		this.view = v;
		this.store = store;
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
				SaveAndLoad.save(currentLoadedFile, classStore);
			} else 
			{
				performSaveAs();
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
				performSaveAs();
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
	private void performSaveAs() {
		JFileChooser fc = new JFileChooser();
		// If there is a currently loaded file.

		// Bring up file panel for the user to save as(automatically will choose file
		// type though in saveandload).
		int returnValue = fc.showSaveDialog(parentWindow);
		// Based on the user imput, save the file.

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			SaveAndLoad.save(fc.getSelectedFile(), classStore);
			store.setCurrentLoadedFile(fc.getSelectedFile());
			JOptionPane.showMessageDialog(parentWindow,
					"File " + store.getCurrentLoadedFile().getName() + " was saved.", "File Saved",
					JOptionPane.INFORMATION_MESSAGE);
		}
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
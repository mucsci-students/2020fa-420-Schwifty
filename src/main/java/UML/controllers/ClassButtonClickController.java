package UML.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ClassButtonClickController implements ActionListener
{
	private Store store;
	private View view;
	
	public ClassButtonClickController(Store store, View v) {
		this.view = v;
		this.store = store;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		if(cmd.equals("Create"))
		{
			//Load text input box to get the name of the new class to be created.
			String className = view.getTextFromInput("Class name: ");
			//Create the class. 
			boolean temp = store.addClass(className);
			if(temp)
				view.makeNewClassPanel(store.findClass(className));
		}
		else if(cmd.equals("Delete"))
		{
			//get list of objects to display to user
			Object[] classList = store.getClassList().toArray();
			
			//Get the class to be deleted. 
			String toBeDeleted = view.getResultFromComboBox("Delete this class", "Delete a class", classList);

			//Delete the class. 
			//find it in storage array.
			boolean temp = store.deleteClass(toBeDeleted);

			if(temp)
			{
				view.refresh();
				
				JPanel panel = view.classPanels.get(toBeDeleted);
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
				view.updateClassPanel(toBeRenamed, newClassName);
				view.refresh();
				// view.updateClassPanel(oldName, newName) {
				JPanel panel = classPanels.get(toBeRenamed);
				JTextArea textArea = (JTextArea)panel.getComponents()[0];
				textArea.setText(store.findClass(newClassName).toString());
				classPanels.remove(toBeRenamed);
				classPanels.put(newClassName, panel);
				parentWindow.revalidate();
				parentWindow.repaint();
				// }
			}
		}
	}
}
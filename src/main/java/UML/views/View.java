package UML.views;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/06/2020
    Purpose: Provides an interface for the views.
 */

import java.awt.event.*;
import java.util.ArrayList;
import UML.controllers.MouseClickAndDragController;
import UML.controllers.VerticalScrollController;

import java.awt.Dimension;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionListener;

public interface View
{
	//updates every part of the class, including attributes within
	void createClass(String name, int x, int y);
	void deleteClass(String name);

	void addRelationship(String from, String to, String type);
	void deleteRelationship(String from, String to);
	
	String getChoiceFromUser(String msgOne, String msgTwo, ArrayList<String> options);
    String getInputFromUser(String prompt);
	
	void addListeners(ActionListener fileListener, ActionListener classListener, ActionListener stateListener);
	void addListener(ActionListener listener);
	void addListener(MouseClickAndDragController mouseListener, String className);
	void addPanelListener(ActionListener controller, String classText);
	void addScrollListener(VerticalScrollController vsc);

	void display(String str);
	void showError(String error);
	String save();
	String load();
	void exit();
	void start();
	void showHelp();
	Dimension getLoc(String name);
	Map<ArrayList<String>, String> getRelationships();
	JFrame getMainWindow();
	Map<String, JPanel> getPanels();
	void setGUIInvisible();
	void setGUIVisible();
}
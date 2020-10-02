package UML.views;
import java.io.File;
import java.awt.event.*;
import java.util.ArrayList;

public interface View
{
	//updates every part of the class, including attributes within
    void updateClass(String oldName, String newName);
	void createClass(String name);
	void deleteClass(String name);

	String getChoiceFromUser(String msgOne, String msgTwo, ArrayList<String> options);
    String getInputFromUser(String prompt);
	
	void addListeners(ActionListener fileListener, ActionListener classListener, ActionListener fieldListener, ActionListener relationshipListener);

	void display(ArrayList<String> str);
	void showError(String error);
	String save();
	String load();
	void exit();
}
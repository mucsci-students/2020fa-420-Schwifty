package UML.views;
import java.io.File;
import java.util.ArrayList;

public interface View
{
	//updates every part of the class, including attributes within
    void updateClass(String oldName, String newName);
	void createClass(String name);
	void deleteClass(String name);
	
	void createField(String className, String type, String name);
	void deleteField(String className, String name);

	void createRelationship(String name);
	void deleteRelationship(String name);
	void updateRelationship(String name);

	String getChoiceFromUser(String msgOne, String msgTwo, ArrayList<String> options);
    String getInputFromUser(String prompt);
	
	void display();
	void showError(String error);
	void save(File fileName);
	void load(File fileName);
	void exit();
}
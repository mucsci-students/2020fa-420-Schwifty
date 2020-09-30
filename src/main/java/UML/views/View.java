package UML.views;

public interface View
{
    void updateClass(String name);
	void createClass(String name);
	void deleteClass(String name);
	
	void createRelationship(String name);
	void deleteRelationship(String name);
	void updateRelationship(String name);

	String getChoiceFromUser(String title, String[] options);
    String getInputFromUser(String prompt);
	
	void display();
	void save(File fileName);
	void load(File fileName);
	void exit();
}
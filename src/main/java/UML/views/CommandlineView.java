package UML.views;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/06/2020
    Purpose: Provides an implementation of the CLI view.
 */
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class CommandlineView implements View {

    @Override
    public void updateClass(String oldName, String newName) {
        System.out.println("Updated class:\n" + newName);
    }

    @Override
    public void createClass(String name) {
        System.out.println("New class:\n" + name);
    }

    @Override
    public void deleteClass(String name) {
        System.out.println("Class deleted");
    }

    @Override
    public String getChoiceFromUser(String msgOne, String msgTwo, ArrayList<String> options) {
        return null;
    }

    @Override
    public String getInputFromUser(String prompt) {
        return null;
    }

    @Override
    public void addListeners(ActionListener fileListener, ActionListener classListener, ActionListener fieldListener,
            ActionListener relationshipListener) {
    }

    @Override
    public void display(ArrayList<String> str) {
        //Need display implemented in Controller or not needed in View interface.
    }

    @Override
    public void showError(String error) {
        System.out.println(error);
    }

    @Override
    public String save() {
        return null;
    }

    @Override
    public String load() {
        return null;
    }

    @Override
    public void exit() {
        //Needs implmented in controller, here, and GUI.
    }

    @Override
    public void start() {
        //Maybe not needed for either?
    }
}
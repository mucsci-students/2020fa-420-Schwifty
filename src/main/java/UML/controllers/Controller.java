package UML.controllers;

public class Controller {
    //Store the model
    private Store store;
    //Store the view
    private View view;
    //Store all the controllers for the gui
    private FileButtonClickController fileClickController;
    private ClassButtonClickController classClickController;
    private FieldButtonClickController fieldClickController;
    private FieldButtonClickController relationshipClickController;

    public Controller(Store store, View view)
    {
        this.store = store;
        this.view = view;
    }

}

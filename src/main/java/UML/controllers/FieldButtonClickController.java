package UML.controllers;

private class FieldButtonClickListener implements ActionListener {
    private Store store;
	private View view;

	public FieldButtonClickController(Store store, View v) 
	{
		this.view = v;
		this.store = store;
	}

    public void actionPerformed(ActionEvent e) {
        // Create a drop down list of created classes.
        Object[] classList = store.getClassList().toArray();

        String className = getResultFromComboBox("Create Field for this class", "Create atrribute", classList);

        String cmd = e.getActionCommand();
        if (cmd.equals("CreateField")) {
            // TODO: Consider making a custom window for this? It would make it look cleaner
            // in the future.
            // Get Type from user.
            String type = getTextFromInput("Type: ");
            // Get name from user.
            String name = getTextFromInput("Name: ");
            // Add field to the class using received params.
            store.addField(className, type, name);

            JPanel panel = classPanels.get(className);
            JTextArea textArea = (JTextArea) panel.getComponents()[0];
            textArea.setText(store.findClass(className).toString());

            windowRefresh(className, panel);

        } else if (cmd.equals("DeleteField")) {
            // Get class from storage.
            Class classToDeleteFrom = store.findClass(className);

            // Get the atrributes to be placed in a combo box.
            Object[] fieldList = store.getFieldList(classToDeleteFrom.getFields()).toArray();

            // Get field to delete.
            String field = getResultFromComboBox("Delete this atrribute", "Delete atrribute", fieldList);
            String[] att = field.split(" ");

            // Delete the field.
            store.deleteField(className, att[1]);

            // Delete attr in window and revalidate/repaint.
            JPanel panel = classPanels.get(className);
            JTextArea textArea = (JTextArea) panel.getComponents()[0];
            textArea.setText(classToDeleteFrom.toString());
            windowRefresh(className, panel);
        } else if (cmd.equals("RenameField")) {
            // Get class from storage.
            Class classToRenameFrom = store.findClass(className);

            // Get a list of atrributes from the class.
            Object[] fieldList = store.getFieldList(classToRenameFrom.getFields()).toArray();

            // Get field to rename.
            String field = getResultFromComboBox("Rename this field", "Rename atrribute", fieldList);

            // Open text input for new name.
            String newField = getTextFromInput("Enter new field name: ");
            String[] att = field.split(" ");

            // Rename the field.
            store.renameField(className, att[1], newField);

            JPanel panel = classPanels.get(className);
            JTextArea textArea = (JTextArea) panel.getComponents()[0];
            textArea.setText(store.findClass(className).toString());

            windowRefresh(className, panel);
        } else if (cmd.equals("ChangeFieldType")) {
            // Get class from storage.
            Class classToChangeTypeFrom = store.findClass(className);

            // Get a list of atrributes from the class.
            Object[] fieldList = store.getFieldList(classToChangeTypeFrom.getFields()).toArray();

            // Get field to change it's type.
            String field = getResultFromComboBox("Change this field's type", "Change Field Type", fieldList);

            // get the new type from the user and let the store know it can change.
            String newType = getTextFromInput("New type: ");
            String[] att = field.split(" ");
            store.changeFieldType(className, newType, att[1]);

            JPanel panel = classPanels.get(className);
            JTextArea textArea = (JTextArea) panel.getComponents()[0];
            textArea.setText(store.findClass(className).toString());

            windowRefresh(className, panel);
        } else if (cmd.equals("CreateMethod")) {
            // Get Type from user.
            String returnType = getTextFromInput("Return Type: ");
            // Get name from user.
            String name = getTextFromInput("Method Name: ");
            // Get number of parameters from user.
            int paramNum = getNumberFromInput();

            ArrayList<String> params = new ArrayList<String>();

            for (int count = 0; count < paramNum; ++count) {
                String paramType = getTextFromInput("Parameter Type: ");
                String paramName = getTextFromInput("Parameter Name: ");
                params.add(paramType + " " + paramName);
            }
            // Add field to the class using received params.
            store.addMethod(className, returnType, name, params);
            JPanel panel = classPanels.get(className);
            JTextArea textArea = (JTextArea) panel.getComponents()[0];
            textArea.setText(store.findClass(className).toString());

            windowRefresh(className, panel);

        } else if (cmd.equals("DeleteMethod")) {
            // Get a list of methods to show user in a combo box.
            Object[] methods = store.getMethodList(store.findClass(className).getMethods()).toArray();
            // Get their choice
            String methodString = getResultFromComboBox("Delete method", "DeleteMethod", methods);

            store.removeMethodByString(store.findClass(className).getMethods(), methodString, className);

            JPanel panel = classPanels.get(className);
            JTextArea textArea = (JTextArea) panel.getComponents()[0];
            textArea.setText(store.findClass(className).toString());

            windowRefresh(className, panel);

        } else if (cmd.equals("RenameMethod")) {
            Object[] methodList = store.getMethodList(store.findClass(className).getMethods()).toArray();
            String method = getResultFromComboBox("Rename this method", "Rename method", methodList);

            String newMethod = getTextFromInput("Enter new method name: ");
            store.renameMethodByString(store.findClass(className).getMethods(), method, className, newMethod);

            JPanel panel = classPanels.get(className);
            JTextArea textArea = (JTextArea) panel.getComponents()[0];
            textArea.setText(store.findClass(className).toString());

            windowRefresh(className, panel);
        }
    }

    private void windowRefresh(String className, JPanel panel) {
        // Leave remove before put
        classPanels.remove(className);
        classPanels.put(className, panel);
        parentWindow.revalidate();
        parentWindow.repaint();
    }

    private int getNumberFromInput() {
        int paramNum;
        // Ensure we get a number, defaults to zero on bad input.
        try {
            paramNum = Integer.parseInt(getTextFromInput("Number of Parameters: "));
        } catch (NumberFormatException e) {
            paramNum = 0;
        }

        return paramNum;
    }
}

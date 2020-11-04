package UML.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import UML.model.Store;
import UML.views.*;

public class EditFieldController implements ActionListener
{
    private Store store;
	private View view;
	private Controller controller;

    /**
     * 
     */
    public EditFieldController(Store s, View v, Controller c) 
    {
		this.view = v;
		this.store = s;
		this.controller = c;
	}

    /**
     * 
     */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String cmd = e.getActionCommand();
        if(cmd.equals("Edit Field"))
        {

        }

    }

}

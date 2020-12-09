package UML.controllers;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/15/2020
    Purpose: Handles mouse click and drag events for the GUI.
 */

import javax.swing.*;
import UML.model.Store;
import UML.views.View;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JColorChooser;

public class MouseClickAndDragController implements MouseListener, MouseMotionListener
{
    private int startDragX, startDragY;
    private Store store;
	private View view;
    private Controller controller;
    
	public MouseClickAndDragController(Store s, View v, Controller c) {
		this.view = v;
		this.store = s;
		this.controller = c;
    }
    
	@Override
    public void mousePressed(MouseEvent e) 
    {
        //If the user left clicks get the panels X and Y pos.
        if(MouseEvent.BUTTON1 == e.getButton())
        {
            startDragX = e.getX();
            startDragY = e.getY();
        }
        //If the user right clicks open a color chooser. 
        else if (MouseEvent.BUTTON3 == e.getButton())
        {
            Object source = e.getSource();
            if(source instanceof JPanel)
            {
                //Bring up color chooser for the panel.
                JPanel found = (JPanel)source;
                JPanel panel = (JPanel)found.getComponent(1);
                JColorChooser cc = new JColorChooser();
                Color color = cc.showDialog(view.getMainWindow(),"Choose a panel color", Color.lightGray);
                panel.setBackground(color);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) 
    {
        Object source = e.getSource();
        if(source instanceof JPanel)
        {
            JPanel found = (JPanel)source;
            JPanel panel = (JPanel)found.getComponent(1);
            JLabel text = (JLabel)panel.getComponent(0);
            //Get the class name from the test on the top panel.
            String className = text.getText().trim();

            UML.model.Class c = store.findClass(className);

            //Get new location.
            int newX = found.getX() + (e.getX() - startDragX);
            int newY = found.getY() + (e.getY() - startDragY);
            c.setLocation(new Dimension(newX, newY));

            //Set the location of the panel.
            found.setLocation(newX, newY);
            view.getMainWindow().repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Do nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //  Do nothing
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        //  Do nothing
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        //  Do nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       // Do nothing
    }
}

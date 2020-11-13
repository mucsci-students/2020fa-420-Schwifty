/*
    Author: Chris, Dominic, Tyler, Cory and Drew
    Date: 09/10/2020
    Purpose: Runs tests on the public methods in the class class.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import org.junit.Before;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.MockitoAnnotations;
import UML.views.*;
import UML.controllers.*;
import UML.model.*;
import java.awt.Dimension;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.awt.Frame;


public class ClassClickTest
{


    
    private Store store = new Store();

    
    private View view = new GraphicalView();

    
    private Controller controller = new Controller(store, view);

   
    
    @Test
    public void testAddClass()
    {
        view.start();
        controller.addListeners();

        UML.model.Class c = new UML.model.Class("a");
        view.createClass(c.toString(), 5, 6);
        assertTrue(view.getPanels().size() == 1);

    }



}



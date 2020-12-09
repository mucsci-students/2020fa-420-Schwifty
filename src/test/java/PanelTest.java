import UML.views.*;
import UML.controllers.*;
import UML.model.*;
import javax.swing.JPanel;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

public class PanelTest 
{

    Store s = new Store();
    View v = new GraphicalView();
    Controller c = new Controller(s,v);

    @Test
    public void testConstructor()
    {
        UML.model.Class test = new UML.model.Class("Test");
        ClassPanelBuilder cpb = new ClassPanelBuilder(test.toString(), new DrawPanel(v));
        assertNotNull(cpb);
    }

    @Test
    public void testGetParentWindow()
    {
        UML.model.Class test = new UML.model.Class("Test");
        ClassPanelBuilder cpb = new ClassPanelBuilder(test.toString(), new DrawPanel(v));
        assertTrue(cpb.getParentWindow() instanceof DrawPanel);
    }

    @Test
    public void testSetParentWindow()
    {
        UML.model.Class test = new UML.model.Class("Test");
        ClassPanelBuilder cpb = new ClassPanelBuilder(test.toString(), new DrawPanel(v));
        DrawPanel dp = new DrawPanel(v);
        cpb.setParentWindow(dp);
        assertEquals(dp, cpb.getParentWindow());
        assertTrue(cpb.getParentWindow() instanceof DrawPanel);
    }

    @Test
    public void testGetClassData()
    {
        UML.model.Class test = new UML.model.Class("Test");
        ClassPanelBuilder cpb = new ClassPanelBuilder(test.toString(), new DrawPanel(v));
        assertTrue(test.toString().equals(cpb.getClassData()));
    }

    @Test
    public void testSetClassData()
    {
        UML.model.Class test = new UML.model.Class("Test");
        ClassPanelBuilder cpb = new ClassPanelBuilder(test.toString(), new DrawPanel(v));
        UML.model.Class test1 = new UML.model.Class("Test1");
        cpb.setClassData(test1.toString());
        assertTrue(test1.toString().equals(cpb.getClassData()));
    }

    @Test
    public void testSetPanel()
    {
        UML.model.Class test = new UML.model.Class("Test");
        ClassPanelBuilder cpb = new ClassPanelBuilder(test.toString(), new DrawPanel(v));
        JPanel panel = new JPanel();
        cpb.setPanel(panel);
        assertTrue(panel.equals(cpb.getPanel()));
    }

    @Test
    public void testGetPanel()
    {
        UML.model.Class test = new UML.model.Class("Test");
        ClassPanelBuilder cpb = new ClassPanelBuilder(test.toString(), new DrawPanel(v));
        assertNotNull(cpb.getPanel());
        assertTrue(cpb.getPanel() instanceof JPanel);
    }

    @Test
    public void testMakeNewClassPanel()
    {
        UML.model.Class test = new UML.model.Class("Test");
        ClassPanelBuilder cpb = new ClassPanelBuilder(test.toString(), new DrawPanel(v));
        assertTrue(cpb.makeNewClassPanel() instanceof JPanel);
    }
}

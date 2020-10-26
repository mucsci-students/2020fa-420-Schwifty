package UML.views;

import UML.model.Class;
import javax.swing.JTextArea;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.border.Border;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.lang.model.util.ElementScanner6;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import UML.controllers.MouseClickAndDragController; 
import java.awt.Dimension;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import java.awt.geom.AffineTransform;
import java.awt.geom.*;
import javafx.scene.transform.Rotate;


public class DrawPanel extends JPanel 
{
    private View view;

    public DrawPanel(View v)
    {
        this.view = v;
    }

    public void paintComponent(Graphics g)
    {
        g.setColor(Color.BLACK);
        for(Map.Entry<ArrayList<String>, String> relationship : view.getRelationships().entrySet())
        {
            Dimension fromLoc = view.getLoc(relationship.getKey().get(0));
            Dimension toLoc = view.getLoc(relationship.getKey().get(1));
            Map<String, JPanel> panels = view.getPanels();
            Dimension fromSize = panels.get(relationship.getKey().get(0)).getSize();
            Dimension toSize = panels.get(relationship.getKey().get(1)).getSize();
            int[] line = getClosest((int)fromLoc.getWidth(), (int)(fromLoc.getWidth() + fromSize.getWidth()), 
                                                (int)fromLoc.getHeight(), (int)(fromLoc.getHeight() + fromSize.getHeight()), 
                                                (int)toLoc.getWidth(), (int)(toLoc.getWidth() + toSize.getWidth()),
                                                (int)toLoc.getHeight(), (int)(toLoc.getHeight() + toSize.getHeight()));                            
            if(relationship.getValue().equals("REALIZATION"))
            {
                //Do a dotted line
                g.setColor(Color.BLUE);
                Graphics2D g2d = (Graphics2D)g;
                BasicStroke defaultStroke = (BasicStroke) g2d.getStroke();
                BasicStroke dashLine = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 10 }, 0);
                g2d.setStroke(dashLine);
                g.drawLine(line[0], line[1], line[2], line[3]);
                g2d.setStroke(defaultStroke);
                //Draw correct shape
                try
                {
                    //File path = new File("/src/main/java/UML/view/");
                    BufferedImage image = ImageIO.read(new File("./Images/Association.png"));
                    //double theta = Math.tan((line[3] - line[1]) / (line[2] - line[0]));
                    //Rotate rotate = new Rotate(theta);
                    BufferedImage rotatedImage = rotate.apply(image);
                    //g2d.rotate(theta);
                    g2d.drawImage(image, line[4], line[5], null);
                    //g2d.rotate(-theta);
                }
                catch(Exception e)
                {

                }
                //g.fillOval(line[4], line[5], 36, 36);
            }
            else
            {
                g.setColor(Color.PINK);
                g.drawLine(line[0], line[1], line[2], line[3]);
                //Draw correct shape
                g.fillOval(line[4], line[5], 36, 36);
            }
        }
    
    }

    /**
     * 
     */
    public void drawCorrectShape(Graphics g, int x, int y, String relationship)
    {
        //Open diamond.
        if(relationship.equals("Aggregation"))
        {
            g.setColor(Color.BLACK);
            //g.rotate(90.0)
            g.clearRect(x, y, 25, 25);

        }
        //Filled diamond.
        else if(relationship.equals("Composition"))
        {
            g.setColor(Color.BLACK);
            g.drawOval(x, y, 25, 25);
        }
        //Open arrow
        else if(relationship.equals("Generalization"))
        {
            g.setColor(Color.MAGENTA);
            g.drawOval(x, y, 25, 25);
        }
        //Filled arrow
        else if(relationship.equals("Realization"))
        {
            g.setColor(Color.CYAN);
            g.drawOval(x, y, 25, 25);
        }
    }

    /**
     * Get's shortest line points between two class panels.
     */
    public int[] getClosest(int fromLeft, int fromRight, int fromUp, int fromDown, int toLeft, int toRight, int toUp, int toDown)
    {
        //fromX, fromY, toX, toY.
        int[] x = new int[6];
        //From is to the right.
        if(fromLeft > toRight)
        {   
            x[0] = fromLeft;
            x[2] = toRight;
            //Up and to the right.
            if(fromDown < toUp)
            {
                x[1] = fromDown;
                x[3] = toUp;
            }
            //Down and to the right.
            else if(fromUp > toDown)
            {
                x[1] = fromUp;
                x[3] = toDown;
            }
            else
            {
                //Directly to the right.
                if(fromUp < toUp)
                {
                    x[1] = ((fromDown - toUp) / 2) + toUp;
                    x[3] = ((fromDown - toUp) / 2) + toUp;
                }
                else
                {
                    x[1] = ((toDown - fromUp) / 2) + fromUp;
                    x[3] = ((toDown - fromUp) / 2) + fromUp;
                }
            }
            x[4] = fromLeft - 36;
            x[5] = x[1] - 18;
        }
        //From is to the left.
        else if(fromRight < toLeft)
        {
            x[0] = fromRight;
            x[2] = toLeft;
            //Up and to the left.
            if(fromDown < toUp)
            {
                x[1] = fromDown;
                x[3] = toUp;
            }
            //Down and to the left.
            else if(fromUp > toDown)
            {
                x[1] = fromUp;
                x[3] = toDown;
            }
            else
            {
                //Directly to the left.
                if(fromUp < toUp)
                {
                    x[1] = ((fromDown - toUp) / 2) + toUp;
                    x[3] = ((fromDown - toUp) / 2) + toUp;
                }
                else
                {
                    x[1] = ((toDown - fromUp) / 2) + fromUp;
                    x[3] = ((toDown - fromUp) / 2) + fromUp;
                }
            }
            x[4] = fromRight;
            x[5] = x[1] - 18;
        }
        //From is above.
        else if(fromDown < toUp)
        {
            x[1] = fromDown;
            x[3] = toUp;
            //Up and to the left.
            if(fromLeft > toRight)
            {
                x[0] = fromRight;
                x[2] = toLeft;
            }
            //Up and to the right.
            else if(fromRight < toLeft)
            {
                x[0] = fromLeft;
                x[2] = toRight;
            }
            else
            {
                //Directly above.
                if(fromLeft < toLeft)
                {
                    x[0] = ((fromRight - toLeft) / 2) + toLeft;
                    x[2] = ((fromRight - toLeft) / 2) + toLeft;
                }
                else
                {
                    x[0] = ((toRight - fromLeft) / 2) + fromLeft;
                    x[2] = ((toRight - fromLeft) / 2) + fromLeft;
                }
            }
            x[4] = x[0] - 18;
            x[5] = fromDown;
        }
        //From is below
        else if(fromUp > toDown)
        {
            x[1] = fromUp;
            x[3] = toDown;
            //Down and to the right.
            if(fromLeft > toRight)
            {
                x[0] = fromLeft;
                x[2] = toRight;
            }
            //Down and to the left.
            else if(fromRight < toLeft)
            {
                x[0] = fromRight;
                x[2] = toLeft;
            }
            else
            {
                //Directly below.
                if(fromLeft < toLeft)
                {
                    x[0] = ((fromRight - toLeft) / 2) + toLeft;
                    x[2] = ((fromRight - toLeft) / 2) + toLeft;
                }
                else 
                {
                    x[0] = ((toRight - fromLeft) / 2) + fromLeft;
                    x[2] = ((toRight - fromLeft) / 2) + fromLeft;
                }
            }
            x[4] = x[0] - 18;
            x[5] = fromUp -36;
        }
        else 
        {
            x[0] = fromLeft;
            x[1] = fromUp;
            x[2] = toLeft;
            x[3] = toUp;
        }

        return x;
    }
}

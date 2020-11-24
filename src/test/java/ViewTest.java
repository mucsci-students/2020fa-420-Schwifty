import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.MockitoAnnotations;
import UML.views.*;
import UML.controllers.*;
import UML.model.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.awt.event.ActionListener;

public class ViewTest 
{

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    @Before
    public void setUpStreams() 
    {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() 
    {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Mock
    Store store;
    @Mock
    Controller controller;

    @Spy
    CommandlineView view = new CommandlineView();

    @Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    }

    private String getOutput(String theString)
    {
        int stop = theString.indexOf("Relationships To Others: ");
        return theString.substring(0, stop - 32);
    }

    @Test
    public void testCreateClass()
    {
        UML.model.Class c = new UML.model.Class("Test");
        
        view.createClass(c.toString(), 0, 0);
        assertEquals("\n" + getOutput(c.toString()) + "\n", outContent.toString());

        verify(view).createClass(c.toString(), 0, 0);
        
        reset(view);
    }

    @Test
    public void testDeleteClass()
    {
        UML.model.Class c = new UML.model.Class("Test");
        view.createClass(c.toString(), 0, 0);
        outContent.reset();
        view.deleteClass("Test");
        assertEquals("Class deleted", outContent.toString().trim());
        verify(view).deleteClass("Test");
        reset(view);
    }
    

    @Test
    public void testAddRelationship()
    {
        UML.model.Class c = new UML.model.Class("Test");
        UML.model.Class c2 = new UML.model.Class("Test2");
        view.createClass(c.toString(), 0, 0);
        view.createClass(c2.toString(), 0, 0);
        outContent.reset();
        view.addRelationship(c.toString(), c2.toString(), "Realization");
        assertEquals("\nAdded " + "Realization" + " between:\n" + getOutput(c.toString()) + "\n\n and \n\n" + getOutput(c2.toString()) + "\n", outContent.toString());
        verify(view).addRelationship(c.toString(), c2.toString(), "Realization");
    }

    @Test
    public void testDeleteRelationship()
    {
        UML.model.Class c = new UML.model.Class("Test");
        UML.model.Class c2 = new UML.model.Class("Test2");
        view.createClass(c.toString(), 0, 0);
        view.createClass(c2.toString(), 0, 0);
        view.addRelationship(c.toString(), c2.toString(), "Realization");
        outContent.reset();
        view.deleteRelationship(c.toString(), c2.toString());
        assertEquals("\nDeleted relationship between:\n" + getOutput(c.toString()) + "\n\n and \n\n" + getOutput(c2.toString()) + "\n", outContent.toString());

        verify(view).deleteRelationship(c.toString(), c2.toString());
    }

    @Test
    public void testHelp()
    {
        outContent.reset();
        String result = "";
        result += "exit:                                                                                                       Close CLI\n";
        result += "help:                                                                                                       Show all options\n";
        result += "showgui:                                                                                                    Displays the GUI\n";
        result += "addc [class]:                                                                                               Create a class\n";
        result += "renamec [oldName] [newName]:                                                                                Rename a class\n";
        result += "deletec [class]:                                                                                            Delete a class\n";
        result += "addf [class] [public or private or protected] [type] [name]:                                                Create a field\n";
        result += "renamef [className] [oldName][newName]:                                                                     Rename a field\n";
        result += "deletef [class] [FieldName]:                                                                                Delete a field\n";
        result += "changefa [class] [fieldName] [public or private or protected]:                                              Change field access\n";
        result += "changeft [class] [fieldName] [newType]:                                                                     Change field type\n";
        result += "addm [class] [public or private or protected] [methodType] [methodName] [[paramType] [paramName] ...]:                                       Add a method\n";
        result += "renamem [class] [public or private or protected] [methodType] [oldMethodName] [[ParamType] [paramName] ...] [newMethodName]:                 Rename a method\n";
        result += "deletem [class] [public or private or protected] [methodType] [methodName] [[paramType] [paramName] ...]:                                    Delete a method\n";
        result += "changema [class] [public or private or protected] [methodType] [methodName] [[paramType] [paramName] ...] [public or private or protected]:  Change method access\n";
        result += "changemt [class] [public or private or protected] [methodType] [methodName] [[paramType] [paramName] ...] [newType]:                         Change method type\n";
        result += "addr [classFrom] [classTo] [relateType]:                                                                    Add a relationship\n";
        result += "deleter [classFrom] [classTo]:                                                                              Delete a relationship\n";
        result += "save [fileName]:                                                                                            Saves to passed in file name\n";
        result += "load [fileName]:                                                                                            Loads the passed in file name\n";
        result += "display [(Optional) className]:                                                                             Displays all classes\n";
        result += "display [className]:                                                                                        Displays a class\n";
        result += "undo:                                                                                                       Reverts to a previous state\n";
        result += "redo:                                                                                                       Restores the latest undo\n";
        view.showHelp();
        assertEquals(outContent.toString(),result);
    }

    @Test
    public void testDsiplay()
    {
        view.display("Test String\nnew line");
        assertEquals("Test String\nnew line", outContent.toString().trim());
        verify(view).display("Test String\nnew line");
    }
    

    @Test
    public void testShowError()
    {
        view.showError("404 cat meme");
        assertEquals("404 cat meme", outContent.toString().trim());
        verify(view).showError("404 cat meme");
    }

    @Test
    public void testSave()
    {
        view.save();
        assertEquals("Your file has been saved.", outContent.toString().trim());
        verify(view).save();
    }

    @Test
    public void testLoad()
    {
        view.load();
        assertEquals("Your file has been loaded.", outContent.toString().trim());
        verify(view).load();
    }


    @Test
    public void testExit()
    {
        view.exit();
        assertEquals("Closing application.", outContent.toString().trim());
        verify(view).exit();
    }

    @Test
    public void testStart()
    {
        view.start();
        String result = "";
        result += "  _____      _             _  __ _\n";
        result += " /  ___|    | |           (_)/ _| |\n";
        result += " \\ `--.  ___| |____      ___| |_| |_ _   _\n";
        result += "  `--. \\/ __| '_ \\ \\ /\\ / / |  _| __| | | |\n";
        result += " /\\__/ / (__| | | \\ V  V /| | | | |_| |_| |\n";
        result += " \\____/ \\___|_| |_|\\_/\\_/ |_|_|  \\__|\\__, |\n";
        result += "                                      __/ |\n";
        result += "                                     |___/ \n";
        assertEquals(outContent.toString(), result);
    }

    @Test
    public void testAddListener() 
    {
        ClassClickController test = new ClassClickController(store, view, controller);
        view.addListener(test);
        assertEquals("", outContent.toString().trim());
        verify(view).addListener(test);
    }

    @Test
    public void testAddListener2() 
    {
        MouseClickAndDragController test = new MouseClickAndDragController(store, view, controller);
        view.addListener(test, "Text");
        assertEquals("", outContent.toString().trim());
        verify(view).addListener(test, "Text");
    }

    @Test
    public void testGetLoc() {
        view.getLoc("String");
        assertEquals("", outContent.toString().trim());
        verify(view).getLoc("String");
    }

    @Test
    public void testGetRelationships() {
        view.getRelationships();
        assertEquals("", outContent.toString().trim());
        verify(view).getRelationships();
    }

    @Test
    public void testGetMainWindow() {
        view.getMainWindow();
        assertEquals("", outContent.toString().trim());
        verify(view).getMainWindow();
    }

    @Test
    public void testGetPanels() {
        view.getPanels();
        assertEquals("", outContent.toString().trim());
        verify(view).getPanels();
    }

    @Test
    public void testGetChoiceFromUser() 
    {
        view.getChoiceFromUser("Hi I am a message", "So am I", new ArrayList<String>());
        assertEquals("", outContent.toString().trim());
        verify(view).getChoiceFromUser("Hi I am a message", "So am I", new ArrayList<String>());
    }

    @Test
    public void testGetInputFromUser() 
    {
        view.getInputFromUser("Hi I am a message");
        assertEquals("", outContent.toString().trim());
        verify(view).getInputFromUser("Hi I am a message");
    }

    @Test
    public void testAddListeners() 
    {
        ActionListener test = new ClassClickController(store, view, controller);
        view.addListeners(test, test, test);
        assertEquals("", outContent.toString().trim());
        verify(view).addListeners(test, test, test);
    }

    @Test
    public void testAddPanelListener() 
    {
        ActionListener test = new ClassClickController(store, view, controller);
        view.addPanelListener(test, "Text");
        assertEquals("", outContent.toString().trim());
        verify(view).addPanelListener(test, "Text");
    }

    @Test
    public void testSetGUIInvisible()
    {
        view.setGUIInvisible();
        assertEquals("", outContent.toString().trim());
        verify(view).setGUIInvisible();
    }

    @Test
    public void testSetGUIVisible()
    {
        String result = "";
        result += "THICCC BOY\n";
                                                                                                                                                          
        result+= "                                         ,z,              ,z,             \n";   
        result+= "                                         ,+i              *xi             \n";                     
        result+= "                                         :MW:             #n+             \n";                     
        result+= "                                         `Mxx`           `nnz`            \n";                     
        result+= "                                         `MnW*           :nnn.            \n";                     
        result+= "                                         `MnMx`         `*nnn.            \n";                     
        result+= "                                         ;MnnM,         `#nnn:             \n";                    
        result+= "                                        `*MxnMi         `znnn*`           \n";                     
        result+= "                                         #xxxxz.        ,nnnn+`            \n";                    
        result+= "                                        `xxnzxM;        ixn++#`           \n";                     
        result+= "                                        `nnnn#x#,      `#nn+in,           \n";                     
        result+= "                                        `xnnxixx:      .nnni;x:           \n";                     
        result+= "                                        `xnnx;zM;`     :xnni:x:          \n";                      
        result+= "                                        ,xnnxi+x#,     +nnn;:n;             \n";                   
        result+= "                                        iMnnxiixx:    `nnnn::zi           \n";                     
        result+= "                                        ;Mnnxi;nx:    ,nnnn::##             \n";                   
        result+= "                                        iMnnn*:zx:    :xnnn;:+z            \n";                    
        result+= "                                        ixnnn*:#xi    *nnnn::+z`        \n";                       
        result+= "                                       ixnnn*:+x#`   #nnnz::*n.           \n";                    
        result+= "                                       *xnnn*:inn`  `nnnnz::ix.          \n";                     
        result+= "                                       ixnnn*:;nx`  .xnnnn::ix,         \n";                      
        result+= "                                       *xnnn+::zx,  ,xnnnz,,;x,        \n";                       
        result+= "                                       ixnnn+::zx:  :xnznz,,:x,         \n";                      
        result+= "                                       ixnnn+,:#xi  ,xnnnz:::x:           \n";                    
        result+= "                                       ;xnnn+::+x*  ,xnnnz::;x:        \n";                       
        result+= "                                       ixnnn#:,*xz` *xnnnz::;x:           \n";                    
        result+= "                                       :xnnn#::*xz` ixnznz::;x:          \n";                     
        result+= "                                       ,xnnn#,:in#  ;xnnnz,:ix,       \n";                        
        result+= "                                       ,xnnnz::;x#` :xnnn#,,ix,        \n";                       
        result+= "                                       ,xnnnz::inz` .xnzn#:,+x,     \n";                          
        result+= "                                       .xnnnz::;nx. .xnnn#:,#x,      \n";                         
        result+= "                                       `nnnnz::;nx. .xnnn+::zx,    \n";                           
        result+= "                                       `znnnn::;nx. `xnnn*:;nn.      \n";                         
        result+= "                                        *xnnn;:;nx. `xnnn*:ixz      \n";                          
        result+= "                                        :xnnni,;nx` `xnzni,+x+       \n";                         
        result+= "                                        .nnzn*,:nx` `xnnni,#xi     \n";                           
        result+= "                                          #nnn#,;nx. `nnnn::zx,    \n";                            
        result+= "                                         ixnnn:;nx. .zxnn:;xn,     \n";                           
        result+= "                                          :xnnn;inx. `#xnz:*xn`   \n";                             
        result+= "                                          `nnnn#*nx:`;#xnz:#x#`   \n";                             
        result+= "                                           ixnnnznx;;zzxnn:nMi`   \n";                             
        result+= "                                          .xnnnxnx+#MnMxnznx:    \n";                             
        result+= "                                          `nnnnnnx#xMxxnnnxn;   \n";                              
        result+= "                                           ixnnnnxnxxnxMMMM#*   \n";                              
        result+= "                                           .xnnnnMnnnnnxnnMz*`  \n";                              
        result+= "                                           ,xnnnnxx@MnnnxnxWx:.      \n";                         
        result+= "                                            .nxnnxnWMnnnnnnnW@M:         \n";                      
        result+= "                                            ,nnnnnxWnnnnnnnnMnxni`         \n";                    
        result+= "                                           ixnnnnxMnnnnnnznnnnx#.        \n";                     
        result+= "                                            znnnnnnxnnnnnnnnnnnnz.          \n";                   
        result+= "                                          .nnnnnnnnnnnnnznnnnnnM:          \n";                   
        result+= "                                          ,xnnnnnnnnxnnnnnnnnnxx#`                 \n";           
        result+= "                                         ,xnnnnznnnxxxnnnnznnxnn.                    \n";        
        result+= "                                          ,xnnnnnnnnxnnxnnzznnnnx;`                  \n";         
        result+= "                                         ,xnznnnnnnxnzznnznnnz+x+.                     \n";      
        result+= "                                          ,xnznnnnnzx#n,znnnznMx#*:                  \n";         
        result+= "                                          ,nzznnnnnnn*W;+nnzzzMM##i,.                  \n";       
        result+= "                                          ,xznnzzzzzn+x*;nnnznnni:+#+:                  \n";      
        result+= "                                         ;nz#i;::::*#+::nnzznn#,,;#M+                    \n";    
        result+= "                                          ;n*:,.,.,,;;,i+nnnnn#;:ii:n*`                \n";       
        result+= "                                          *n;.,.,,,,,i*;;;;znz*:*ii;+:                   \n";     
        result+= "                                         ##,..,..,,;;;i*i::z#:,+*ii+*`                  \n";     
        result+= "                                        .z:......,,,::;+#*,:i.;;,,.*i                   \n";     
        result+= "                                         .#:,..,,,,:*nxxM#:;*ii*,.,,*;                   \n";     
        result+= "                                        :zi..,.,,.,:,nxxx#:,,.;...:#;                   \n";     
        result+= "                                         ;n+;,....,,.,+xxx*`,,,;...:ii                   \n";     
        result+= "                                         ;n#+:......,.;zzz#*+ii,....;*                   \n";     
        result+= "                                         *nzii,.......,#++++i,.,...,,*                  \n";      
        result+= "                                         `#nz+,i,,......,###i,..,....:*                  \n";      
        result+= "                                         ,nzz#.,,.....,i.,::.,;......,i                  \n";      
        result+= "                                      `:znzz#,.,,.,.,.;i:,,;i,.,..,.:+                   \n";     
        result+= "                                     `;nnzzz+,,....,.,,,;i;:,.,.,...:+`                  \n";     
        result+= "                                    .+nzzzzz*......,.......,..,,....:+`                  \n";     
        result+= "                                   ,znzzzzzzi,,...,...,;...,..;,.,.,;n:                  \n";     
        result+= "                                 `*nzzzzzzzz;,,.,...,..;i:.,:i:.....:n#.                 \n";     
        result+= "                                .znzzzzzzzz#,,,.,,,.....,;i;;,.....,;zn*.                 \n";    
        result+= "                              `;nnzzzzzzzzz+,,.....,,.,......,....,,,zzn*`               \n";     
        result+= "                             .+nnzzzzzzzzzzi.,....,,..............,,.+zzni`              \n";     
        result+= "                            ,znnzzzzzzzzzzz:,..,....,.......,....,..,;zzzn;               \n";    
        result+= "                          `innzzzznnzzzzzz+,,...,.,.........,...,.,..,#zzzx;`             \n";    
        result+= "                          ;nnzzzzzzzzzzzzz:................,......,,,.;zzzzn:              \n";   
        result+= "                        ;nzzzzzzzzzzzzzz+,,.......,..................,+zzznn:             \n";   
        result+= "                         :nnzzzzzzzzzzzzzz;,...,.,......,.....,.........;nzzznz,           \n";    
        result+= "                       :nzzzzzzzzznznnnn*.,......,......,.,.,...,...,.,,+zzzzn#.            \n";  
        result+= "                      .nnzzzznzzzznnnxn+,,.,...........................,,zzzzzn+`            \n"; 
        result+= "                      `#nnzzzzzzzznxzzz#:,,............................,.,*zzzzzn;           \n";  
        result+= "                     inzzzzzzzzznnzzzz;,.................,.........,....,:zzzzzzn:           \n"; 
        result+= "                    ,nnzzzzzzzznnzzzz;..,..................`............,.+zzzzzn#`           \n";
        result+= "                   `+nzzzzzzzznnzzzz*,.....,.,............................:zzzzznni           \n";
        result+= "                   ,nzzzzzzzznnzzzz+,..............,,...................,..#zzzzzzz:          \n";
        result+= "                   *zzzzzzzzzxzzzz#:......................................,izzzzzzn+`         \n";
        result+= "                  `zzzzzzzzzxnznzzi,....,..............................,...:zzzzzzzn;         \n";
        result+= "                   ,nzz+i*+znnzzzz#,..,.................................,....zzzzzzzn+`        \n";
        result+= "                   ;n#,````,zzzzzz;...................................,.....,#zznz##zn,        \n";
        result+= "                   i#.:i;;:.:zz#z+,..........................................*z+:;;;:;*`       \n";
        result+= "                   ;;*,i,:.:inzzz:..........,...............................,i+:i,i,+ii:       \n";
        result+= "                  `;*i.;:.```+zz*,.........,........,.....................;:,**:``,:i:zi       \n";
        result+= "                  .*.i`::.```.+z;.........,..............................;,,:,````,:i.*i`      \n";
        result+= "                  :i`;`:,``````:;i,...................,..........,.......;`.``````:::,;*,      \n";
        result+= "                 `i.`:`,.````````.:.......................,..............;`.``````,``:,ii      \n";
        result+= "                 `i.`:```````````::.....,................................,i.`,,``````:`;i      \n";
        result+= "                `i``.```````.+:;:.................................`.......:;i:````````i*      \n";
        result+= "                 `i``````````.*,.............................................;.````````z;      \n";
        result+= "                `i.````` ```.;..........,...,........................,....,.i``````..,z`      \n";
        result+= "                 ;, `````` ``;.............................................::```:``:`*+       \n";
        result+= "                 ,i``````````:.........................................,...;```,``,,.n.       \n";
        result+= "                 `;,``  `,,``.:........,....,.............................,;``:.`,,`++        \n";
        result+= "                  .i`````.:,``;,.............................,...........,.i,;,`,:`*z.      \n";  
        result+= "                   ,i.`.```:``.:........................`,..,..............;i*,i;:zx;        \n"; 
        result+= "                  `+#.,,` `:``;...............,...........,,..,.......,....:+nn##M*      \n";    
        result+= "                   `#zziii.`*;i,.........................,................,.i;.,           \n";   
        result+= "                    +zzznzzz*,...........,.................,.,..`.....,....,*.          \n";      
        result+= "                   izzzzzzz*..............................,...............,*            \n";     
        result+= "                   ,nzzzzzz*..........................................,.,,i,         \n";        
        result+= "                   `#zzzzzz#,.................,.....................,.....*`        \n";         
        result+= "                    inzzzzz#,.,..`........................,.....,..,..,..;;       \n";           
        result+= "                   .nzzzzzz;...........................................,*`       \n";           
        result+= "                    +zzzzzz+..............,............................:*         \n";          
        result+= "                      :nzzzzz#:...`.`.......,............................*.      \n";             
        result+= "                      `#zzzzzzi.........................................:i,      \n";             
        result+= "                       ;nzzzzz#,..................................,....,*..     \n";              
        result+= "                       `#zzzzzzi......................................,+ii;.    \n";              
        result+= "                       ,nzzzzz#:..........,..........................*;,,:+.      \n";           
        result+= "                          *zzzzzz#,..................................,i;::;i*;.`   \n";            
        result+= "                         `#zzzzzz+,................................:*i:,...,*:.   \n";            
        result+= "                           .zzzzzzz+,.............................:ii,,,:i,..,+,   \n";            
        result+= "                           .#nzzzzz+,........................,:i*;.....,.i:.,;;    \n";           
        result+= "                  `      ``,+zzzzzz#;..,.................,,i**:,.........i.,:i   \n";            
        result+= "              `:iiiii#nzzz##+nnnzzzz#*:..........,,:,:;iii;,..........,.,i,:+,    \n";            
        result+= "             ,*:....,.*nzzzzzzznnzzzzzz+;,,;ii+##+**i;:.,.....,.....,,,,:+i*.    \n";             
        result+= "             `*,,::;;.,.inzzzzzzzzzzzzzzzz++i...:;iiii;:::,::,,,,,,,,,:ii*,`   \n";                
        result+= "            ,*;:,,.....,;zzzzzzzzzzzzzzzi,+.      `...,,,:;:;::::;;;;:.`    \n";                  
        result+= "            .*,.....,;;i;.;zzzzzzzzzzzzi,:+.  \n";                                                 
        result+= "            *:.....:;...:,.:#zzzzzz#*;,:i*.   \n";                                                 
        result+= "            *...,.,i.........,:::;,,:;*i.     \n";                                                 
        result+= "           *,,...i...,.....,..,:;**;,``   \n";                                                    
        result+= "            ;;....i.........,:i*i:.`   \n";                                                        
        result+= "            `i;,.,i..,....,i*i,`` \n";                                                             
        result+= "            `;iii+:,,,,;*i,`` \n";                                                                
        result+= "               ```,iiii;;.`\n";
        view.setGUIVisible();
        assertEquals(result, outContent.toString());
        verify(view).setGUIVisible();
    }
}

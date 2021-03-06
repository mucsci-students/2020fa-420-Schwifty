package UML.views;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/06/2020
    Purpose: Provides an implementation of the CLI view.
 */
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import UML.controllers.MouseClickAndDragController;
import UML.controllers.ScrollWheelController;
import java.awt.Dimension;
import UML.model.Store;

public class CommandlineView implements View {

    // ================================================================================================================================================
    // Class changing methods.
    // ================================================================================================================================================

    /**
     * Print new class.
     */
    @Override
    public void createClass(String name, int x, int y) 
    {
        int stop = name.toString().indexOf("Relationships To Others: ");
        String s = name.toString().substring(0, stop - 32);
        System.out.println("\n" + s);
    }

    /**
     * Print deleted class message.
     */
    @Override
    public void deleteClass(String name) 
    {
        System.out.println("Class deleted");
    }

    // ================================================================================================================================================
    // Information displaying methods.
    // ================================================================================================================================================

    /**
     * Print out each class.
     */
    @Override
    public void display(String str) 
    {
        System.out.println(str);
    }

    /**
     * Print error message.
     */
    @Override
    public void showError(String error) 
    {
        System.out.println(error);
    }

    /**
     * Print save message.
     */
    @Override
    public String save() 
    {
        System.out.println("Your file has been saved.");
        return "";
    }

    /**
     * Print load message.
     */
    @Override
    public String load()
    {
        System.out.println("Your file has been loaded.");
        return "";
    }

    /**
     * Print exit message.
     */
    @Override
    public void exit() 
    {
        // Needs implmented in controller, here, and GUI.
        System.out.println("Closing application.");
    }

    /**
     * Run printHeader the cli header.
     */
    @Override
    public void start() 
    {
        printHeader();
    }

    /**
     * Print command list.
     */
    @Override
    public void showHelp() 
    {
        System.out.println(
                "exit:                                                                                                       Close CLI");
        System.out.println(
                "help:                                                                                                       Show all options");
        System.out.println(
                "showgui:                                                                                                    Displays the GUI");
        System.out.println(
                "addc [class]:                                                                                               Create a class");
        System.out.println(
                "renamec [oldName] [newName]:                                                                                Rename a class");
        System.out.println(
                "deletec [class]:                                                                                            Delete a class");
        System.out.println(
                "addf [class] [public or private or protected] [type] [name]:                                                Create a field");
        System.out.println(
                "renamef [className] [oldName][newName]:                                                                     Rename a field");
        System.out.println(
                "deletef [class] [FieldName]:                                                                                Delete a field");
        System.out.println(
                "changefa [class] [fieldName] [public or private or protected]:                                              Change field access");
        System.out.println(
                "changeft [class] [fieldName] [newType]:                                                                     Change field type");
        System.out.println(
                "addm [class] [public or private or protected] [methodType] [methodName] [[paramType] [paramName] ...]:                                       Add a method");
        System.out.println(
                "renamem [class] [public or private or protected] [methodType] [oldMethodName] [[ParamType] [paramName] ...] [newMethodName]:                 Rename a method");
        System.out.println(
                "deletem [class] [public or private or protected] [methodType] [methodName] [[paramType] [paramName] ...]:                                    Delete a method");
        System.out.println(
                "changema [class] [public or private or protected] [methodType] [methodName] [[paramType] [paramName] ...] [public or private or protected]:  Change method access");
        System.out.println(
                "changemt [class] [public or private or protected] [methodType] [methodName] [[paramType] [paramName] ...] [newType]:                         Change method type");
        System.out.println(
                "addr [classFrom] [classTo] [relateType]:                                                                    Add a relationship");
        System.out.println(
                "deleter [classFrom] [classTo]:                                                                              Delete a relationship");
        System.out.println(
                "save [fileName]:                                                                                            Saves to passed in file name");
        System.out.println(
                "load [fileName]:                                                                                            Loads the passed in file name");
        System.out.println(
                "display [(Optional) className]:                                                                             Displays all classes or a single class");
        System.out.println(
                "displayr:                                                                                                   Displays all relationships");
        System.out.println(
                "undo:                                                                                                       Reverts to a previous state");
        System.out.println(
                "redo:                                                                                                       Restores the latest undo");
    }


    /**
     * Prints the cli header.
     */
    private void printHeader() 
    {
        System.out.println("  _____      _             _  __ _");
        System.out.println(" /  ___|    | |           (_)/ _| |");
        System.out.println(" \\ `--.  ___| |____      ___| |_| |_ _   _");
        System.out.println("  `--. \\/ __| '_ \\ \\ /\\ / / |  _| __| | | |");
        System.out.println(" /\\__/ / (__| | | \\ V  V /| | | | |_| |_| |");
        System.out.println(" \\____/ \\___|_| |_|\\_/\\_/ |_|_|  \\__|\\__, |");
        System.out.println("                                      __/ |");
        System.out.println("                                     |___/ ");
    }

    @Override
    public void addRelationship(String from, String to, String type) 
    {
        int stop = from.toString().indexOf("Relationships To Others: ");
        String s = from.toString().substring(0, stop - 32);

        int stop2 = to.toString().indexOf("Relationships To Others: ");
        String s2 = to.toString().substring(0, stop2 - 32);

        System.out.println("\nAdded " + type + " between:\n" + s + "\n\n and \n\n" + s2);
    }

    @Override
    public void deleteRelationship(String from, String to) {
        int stop = from.toString().indexOf("Relationships To Others: ");
        String s = from.toString().substring(0, stop - 32);

        int stop2 = to.toString().indexOf("Relationships To Others: ");
        String s2 = to.toString().substring(0, stop2 - 32);
        System.out.println("\nDeleted relationship between:\n" + s + "\n\n and \n\n" + s2);
    }

    /**
     * Prints a thicc boi.
     */
    public void setGUIVisible() 
    {
        System.out.println("\u001B[5  ___________.__    .__    \u001B[5");          
        System.out.println("\u001B[5m\\__    ___/|  |__ |__| ____  ____  \u001B[5m");
        System.out.println("\u001B[5m  |    |   |  |  \\|  |/ ___\\/ ___\\ \u001B[5m");
        System.out.println("\u001B[5m  |    |   |   Y  \\  \\  \\__\\  \\___ \u001B[5m");
        System.out.println("\u001B[5m  |____|   |___|  /__|\\___  >___  >\u001B[5m");
        System.out.println("\u001B[5m                \\/        \\/    \\/ \u001B[5m");
                                                                                                                                                          
        System.out.println("\033[31m\u001B[5m                                         ,z,              ,z,             \u001B[5m\033[31m");   
        System.out.println("                                         ,+i              *xi             ");                     
        System.out.println("                                         :MW:             #n+             ");                     
        System.out.println("                                         `Mxx`           `nnz`            ");                     
        System.out.println("                                         `MnW*           :nnn.            ");                     
        System.out.println("                                         `MnMx`         `*nnn.            ");                     
        System.out.println("                                         ;MnnM,         `#nnn:             ");                    
        System.out.println("                                        `*MxnMi         `znnn*`           ");                     
        System.out.println("                                         #xxxxz.        ,nnnn+`            ");                    
        System.out.println("                                        `xxnzxM;        ixn++#`           ");                     
        System.out.println("                                        `nnnn#x#,      `#nn+in,           ");                     
        System.out.println("                                        `xnnxixx:      .nnni;x:           ");                     
        System.out.println("                                     `xnnx;zM;`     :xnni:x:          ");                      
        System.out.println("                                        ,xnnxi+x#,     +nnn;:n;             ");                   
        System.out.println("                                        iMnnxiixx:    `nnnn::zi           ");                     
        System.out.println("                                        ;Mnnxi;nx:    ,nnnn::##             ");                   
        System.out.println("                                        iMnnn*:zx:    :xnnn;:+z            ");                    
        System.out.println("\u001B[33m\u001B[5m                                        ixnnn*:#xi    *nnnn::+z`        \u001B[33m\u001B[5m");                       
        System.out.println("                                       ixnnn*:+x#`   #nnnz::*n.           ");                    
        System.out.println("                                       *xnnn*:inn`  `nnnnz::ix.          ");                     
        System.out.println("                                       ixnnn*:;nx`  .xnnnn::ix,         ");                      
        System.out.println("                                       *xnnn+::zx,  ,xnnnz,,;x,        ");                       
        System.out.println("                                       ixnnn+::zx:  :xnznz,,:x,         ");                      
        System.out.println("                                       ixnnn+,:#xi  ,xnnnz:::x:           ");                    
        System.out.println("                                       ;xnnn+::+x*  ,xnnnz::;x:        ");                       
        System.out.println("                                       ixnnn#:,*xz` *xnnnz::;x:           ");                    
        System.out.println("                                       :xnnn#::*xz` ixnznz::;x:          ");                     
        System.out.println("                                       ,xnnn#,:in#  ;xnnnz,:ix,       ");                        
        System.out.println("                                       ,xnnnz::;x#` :xnnn#,,ix,        ");                       
        System.out.println("                                       ,xnnnz::inz` .xnzn#:,+x,     ");                          
        System.out.println("                                       .xnnnz::;nx. .xnnn#:,#x,      ");                         
        System.out.println("                                       `nnnnz::;nx. .xnnn+::zx,    ");                           
        System.out.println("                                       `znnnn::;nx. `xnnn*:;nn.      ");                         
        System.out.println("                                        *xnnn;:;nx. `xnnn*:ixz      ");                          
        System.out.println("                                        :xnnni,;nx` `xnzni,+x+       ");                         
        System.out.println("\u001B[32m\u001B[6m                                        .nnzn*,:nx` `xnnni,#xi     \u001B[32m\u001B[6m");                           
        System.out.println("                                          #nnn#,;nx. `nnnn::zx,    ");                            
        System.out.println("                                         ixnnn:;nx. .zxnn:;xn,     ");                           
        System.out.println("                                          :xnnn;inx. `#xnz:*xn`   ");                             
        System.out.println("                                         `nnnn#*nx:`;#xnz:#x#`   ");                             
        System.out.println("                                           ixnnnznx;;zzxnn:nMi`   ");                             
        System.out.println("                                          .xnnnxnx+#MnMxnznx:    ");                             
        System.out.println("                                          `nnnnnnx#xMxxnnnxn;   ");                              
        System.out.println("                                           ixnnnnxnxxnxMMMM#*   ");                              
        System.out.println("                                           .xnnnnMnnnnnxnnMz*`  ");                              
        System.out.println("                                           ,xnnnnxx@MnnnxnxWx:.      ");                         
        System.out.println("                                            .nxnnxnWMnnnnnnnW@M:         ");                      
        System.out.println("                                            ,nnnnnxWnnnnnnnnMnxni`         ");                    
        System.out.println("                                           ixnnnnxMnnnnnnznnnnx#.        ");                     
        System.out.println("                                            znnnnnnxnnnnnnnnnnnnz.          ");                   
        System.out.println("                                          .nnnnnnnnnnnnnznnnnnnM:          ");                   
        System.out.println("\u001B[34m\u001B[5m                                          ,xnnnnnnnnxnnnnnnnnnxx#`                 \u001B[34m\u001B[5m");           
        System.out.println("                                         ,xnnnnznnnxxxnnnnznnxnn.                    ");        
        System.out.println("                                          ,xnnnnnnnnxnnxnnzznnnnx;`                  ");         
        System.out.println("                                         ,xnznnnnnnxnzznnznnnz+x+.                     ");      
        System.out.println("                                          ,xnznnnnnzx#n,znnnznMx#*:                  ");         
        System.out.println("                                          ,nzznnnnnnn*W;+nnzzzMM##i,.                  ");       
        System.out.println("                                          ,xznnzzzzzn+x*;nnnznnni:+#+:                  ");      
        System.out.println("                                         ;nz#i;::::*#+::nnzznn#,,;#M+                    ");    
        System.out.println("                                          ;n*:,.,.,,;;,i+nnnnn#;:ii:n*`                ");       
        System.out.println("                                         *n;.,.,,,,,i*;;;;znz*:*ii;+:                   ");     
        System.out.println("                                         ##,..,..,,;;;i*i::z#:,+*ii+*`                  ");     
        System.out.println("                                        .z:......,,,::;+#*,:i.;;,,.*i                   ");     
        System.out.println("\u001B[35m\u001B[6m                                         .#:,..,,,,:*nxxM#:;*ii*,.,,*;                   \u001B[35m\u001B[6m");     
        System.out.println("                                        :zi..,.,,.,:,nxxx#:,,.;...:#;                   ");     
        System.out.println("                                         ;n+;,....,,.,+xxx*`,,,;...:ii                   ");     
        System.out.println("                                         ;n#+:......,.;zzz#*+ii,....;*                   ");     
        System.out.println("                                         *nzii,.......,#++++i,.,...,,*                  ");      
        System.out.println("                                         `#nz+,i,,......,###i,..,....:*                  ");      
        System.out.println("                                         ,nzz#.,,.....,i.,::.,;......,i                  ");      
        System.out.println("                                      `:znzz#,.,,.,.,.;i:,,;i,.,..,.:+                   ");     
        System.out.println("                                     `;nnzzz+,,....,.,,,;i;:,.,.,...:+`                  ");     
        System.out.println("                                    .+nzzzzz*......,.......,..,,....:+`                  ");     
        System.out.println("                                   ,znzzzzzzi,,...,...,;...,..;,.,.,;n:                  ");     
        System.out.println("                                 `*nzzzzzzzz;,,.,...,..;i:.,:i:.....:n#.                 ");     
        System.out.println("                                .znzzzzzzzz#,,,.,,,.....,;i;;,.....,;zn*.                 ");    
        System.out.println("                              `;nnzzzzzzzzz+,,.....,,.,......,....,,,zzn*`               ");     
        System.out.println("                             .+nnzzzzzzzzzzi.,....,,..............,,.+zzni`              ");     
        System.out.println("\033[31m\u001B[5m                             ,znnzzzzzzzzzzz:,..,....,.......,....,..,;zzzn;               \033[31m\u001B[5m");    
        System.out.println("                          `innzzzznnzzzzzz+,,...,.,.........,...,.,..,#zzzx;`             ");    
        System.out.println("                          ;nnzzzzzzzzzzzzz:................,......,,,.;zzzzn:              ");   
        System.out.println("                        ;nzzzzzzzzzzzzzz+,,.......,..................,+zzznn:             ");   
        System.out.println("                         :nnzzzzzzzzzzzzzz;,...,.,......,.....,.........;nzzznz,           ");    
        System.out.println("                       :nzzzzzzzzznznnnn*.,......,......,.,.,...,...,.,,+zzzzn#.            ");  
        System.out.println("                      .nnzzzznzzzznnnxn+,,.,...........................,,zzzzzn+`            "); 
        System.out.println("                      `#nnzzzzzzzznxzzz#:,,............................,.,*zzzzzn;           ");  
        System.out.println("\u001B[33m\u001B[5m                     inzzzzzzzzznnzzzz;,.................,.........,....,:zzzzzzn:           \u001B[33m\u001B[5m"); 
        System.out.println("                    ,nnzzzzzzzznnzzzz;..,..................`............,.+zzzzzn#`           ");
        System.out.println("                   `+nzzzzzzzznnzzzz*,.....,.,............................:zzzzznni           ");
        System.out.println("                   ,nzzzzzzzznnzzzz+,..............,,...................,..#zzzzzzz:          ");
        System.out.println("                   *zzzzzzzzzxzzzz#:......................................,izzzzzzn+`         ");
        System.out.println("                  `zzzzzzzzzxnznzzi,....,..............................,...:zzzzzzzn;         ");
        System.out.println("                   ,nzz+i*+znnzzzz#,..,.................................,....zzzzzzzn+`        ");
        System.out.println("                   ;n#,````,zzzzzz;...................................,.....,#zznz##zn,        ");
        System.out.println("                   i#.:i;;:.:zz#z+,..........................................*z+:;;;:;*`       ");
        System.out.println("                   ;;*,i,:.:inzzz:..........,...............................,i+:i,i,+ii:       ");
        System.out.println("                  `;*i.;:.```+zz*,.........,........,.....................;:,**:``,:i:zi       ");
        System.out.println("                  .*.i`::.```.+z;.........,..............................;,,:,````,:i.*i`      ");
        System.out.println("                  :i`;`:,``````:;i,...................,..........,.......;`.``````:::,;*,      ");
        System.out.println("\u001B[32m\u001B[6m                 `i.`:`,.````````.:.......................,..............;`.``````,``:,ii      \u001B[32m\u001B[6m");
        System.out.println("                 `i.`:```````````::.....,................................,i.`,,``````:`;i      ");
        System.out.println("                `i``.```````.+:;:.................................`.......:;i:````````i*      ");
        System.out.println("                 `i``````````.*,.............................................;.````````z;      ");
        System.out.println("                `i.````` ```.;..........,...,........................,....,.i``````..,z`      ");
        System.out.println("                 ;, `````` ``;.............................................::```:``:`*+       ");
        System.out.println("                 ,i``````````:.........................................,...;```,``,,.n.       ");
        System.out.println("                 `;,``  `,,``.:........,....,.............................,;``:.`,,`++        ");
        System.out.println("                  .i`````.:,``;,.............................,...........,.i,;,`,:`*z.      ");  
        System.out.println("                   ,i.`.```:``.:........................`,..,..............;i*,i;:zx;        "); 
        System.out.println("                  `+#.,,` `:``;...............,...........,,..,.......,....:+nn##M*      ");    
        System.out.println("                  `#zziii.`*;i,.........................,................,.i;.,           ");   
        System.out.println("                    +zzznzzz*,...........,.................,.,..`.....,....,*.          ");      
        System.out.println("                   izzzzzzz*..............................,...............,*            ");     
        System.out.println("                  ,nzzzzzz*..........................................,.,,i,         ");        
        System.out.println("\u001B[34m\u001B[5m                   `#zzzzzz#,.................,.....................,.....*`        \u001B[34m\u001B[5m");         
        System.out.println("                    inzzzzz#,.,..`........................,.....,..,..,..;;       ");           
        System.out.println("                   .nzzzzzz;...........................................,*`       ");           
        System.out.println("                    +zzzzzz+..............,............................:*         ");          
        System.out.println("                      :nzzzzz#:...`.`.......,............................*.      ");             
        System.out.println("                      `#zzzzzzi.........................................:i,      ");             
        System.out.println("                       ;nzzzzz#,..................................,....,*..     ");              
        System.out.println("                       `#zzzzzzi......................................,+ii;.    ");              
        System.out.println("                       ,nzzzzz#:..........,..........................*;,,:+.      ");           
        System.out.println("                          *zzzzzz#,..................................,i;::;i*;.`   ");            
        System.out.println("                         `#zzzzzz+,................................:*i:,...,*:.   ");            
        System.out.println("                           .zzzzzzz+,.............................:ii,,,:i,..,+,   ");            
        System.out.println("                           .#nzzzzz+,........................,:i*;.....,.i:.,;;    ");           
        System.out.println("\u001B[35m\u001B[6m                 `      ``,+zzzzzz#;..,.................,,i**:,.........i.,:i   \u001B[35m\u001B[6m" );            
        System.out.println("              `:iiiii#nzzz##+nnnzzzz#*:..........,,:,:;iii;,..........,.,i,:+,    ");            
        System.out.println("             ,*:....,.*nzzzzzzznnzzzzzz+;,,;ii+##+**i;:.,.....,.....,,,,:+i*.    ");             
        System.out.println("             `*,,::;;.,.inzzzzzzzzzzzzzzzz++i...:;iiii;:::,::,,,,,,,,,:ii*,`   ");                
        System.out.println("            ,*;:,,.....,;zzzzzzzzzzzzzzzi,+.      `...,,,:;:;::::;;;;:.`    ");                  
        System.out.println("            .*,.....,;;i;.;zzzzzzzzzzzzi,:+.  ");                                                 
        System.out.println("            *:.....:;...:,.:#zzzzzz#*;,:i*.   ");                                                 
        System.out.println("            *...,.,i.........,:::;,,:;*i.     ");                                                 
        System.out.println("           *,,...i...,.....,..,:;**;,``   ");                                                    
        System.out.println("            ;;....i.........,:i*i:.`   ");                                                        
        System.out.println("            `i;,.,i..,....,i*i,`` ");                                                             
        System.out.println("            `;iii+:,,,,;*i,`` ");                                                                
        System.out.println("               ```,iiii;;.`" );                                                                  
    }


    // ================================================================================================================================================
    // "Do Nothing" methods
    // ================================================================================================================================================

    @Override
    public void addListener(ActionListener listener) {
        // Do nothing.
    }

    @Override
    public void addListener(MouseClickAndDragController mouseListener, String classText) {
        // Do nothing.
    }

    @Override
    public void addListener(ScrollWheelController mousWheelListener) {
        // Do nothing.
    }

    @Override
    public Dimension getLoc(String name) {
        // Do nothing.
        return null;
    }

    @Override
    public Map<ArrayList<String>, String> getRelationships() {
        // Do nothing.
        return null;
    }

    @Override
    public JFrame getMainWindow() {
        // Do nothing.
        return null;
    }

    @Override
    public Map<String, JPanel> getPanels() {
        // Do nothing.
        return null;
    }

    @Override
    public String getChoiceFromUser(String msgOne, String msgTwo, ArrayList<String> options) 
    {
        // Do nothing.
        return null;
    }

    @Override
    public String getInputFromUser(String prompt) 
    {
        // Do nothing.
        return null;
    }

    @Override
    public void addListeners(ActionListener fileListener, ActionListener classListener, ActionListener fieldListener) 
    {
        // Do nothing.
    }

    @Override
    public void addPanelListener(ActionListener listener, String classText) 
    {
        //Do nothing.
    }

    @Override
    public void setGUIInvisible()
    {
        //Do nothing.
    }

    @Override
    public void changeBackground()
    {
        //Do nothing.
    }
}
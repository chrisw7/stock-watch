/**
 *
 * @author Alan
 * a class used to store variables so no duplicates are formed
 *
 */
package stockwatch;
import java.util.ArrayList;

public class VariableStorage extends javax.swing.JDialog{
    //the variable for grouplist
    public static ArrayList groupList = new ArrayList();
    
    public static void clear(){//clear the groups variable
        groupList.clear();
    }
    public static void loadGroup(){//load the groups variable
        groupList = CreateAccnt.loadGroups();
    }
    
}

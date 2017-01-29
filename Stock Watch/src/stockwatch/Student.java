
/**
 *
 * @author Stanley, Alan, Chris
 * a class to store a student's login info
 * December 20 2012
 */
package stockwatch;

public class Student extends User{

    private static String GROUP;//the groupname of the user, all other characteristics are inherited from user class
    //constructor
    public Student(String className, String name, String password, String groupName) {
        //init properties
        CLASS = className;
        NAME = name;
        PASSWORD = password;
        GROUP = groupName;
    }

    public String getName() {//return the name
        return NAME;
    }

    public String getPass() {//reutrn the password
        return PASSWORD;
    }

    public String getClss() {//return the class
        return CLASS;
    }

    public String getGroup() {//return the name
        return GROUP;
    }

    public String toString() {//return the
        // username password group class
        return getName() + "," + getPass() + "," + getGroup() + "," + getClss();
    }
}

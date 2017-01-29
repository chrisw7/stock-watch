
/**
 *
 * Chris Williams, Stanley Hu, Alan Cai
 *  a class to store the basic group information
 *January 8 2013
 */
package stockwatch;

import java.util.ArrayList;

public class Group {
    //variables for the group
    private String grpName;
    private int grpSize = 0;
    private ArrayList studentList = new ArrayList();
//constructor
    public Group(String nm) {
        grpName = nm;

    }

    public String getName() {//returns the name of the group
        return grpName;
    }

    public String toString() {//returns the entire group as a string
        String out = "";
        for (int i = 0; i < studentList.size(); i++) {
            out += studentList.get(i) + ",";
        }
        return out;
    }

    public int getSize() {//get size of the group
        return studentList.size();
    }

        public void plusSize() {//add the size
        grpSize++;
    }

    public void recruit(String me) {//add another student to the group
        studentList.add(grpSize, me);
        grpSize++;
    }
}

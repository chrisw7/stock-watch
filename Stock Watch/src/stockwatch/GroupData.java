/**
 *
 * Stanley Hu, Alan Cai, Chris Williams
 * the group data is the class where all of the stock and game information are stored
 * January 8 2013
 */
package stockwatch;

import java.net.URL;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

public class GroupData {
    //variables to store the filepaths needed for reading files

    private static String fileName = "quotesTEMP.csv";
    private static String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + fileName;
    private Stock stocks[];//declare stocks
    private String groupN;//group code
    private String classN;//class code
    private double freeCash;//amount of free flowing cash
    private static String symb = "";//symbol of stock << 0
    private static String date = "";//last trade date << 1
    private static String priS = "";//price in string << 2
    private static double pric = 00;//last trade price << 2
    private static String namS = "";//full name of the stock << 3
    private static int tolStocks;//total number of unique stocks purchased

    public GroupData(String className, String groupName, double funds, int aoStock) {
        //init properties

        stocks = new Stock[watchList.MAX_STOCKS];
        classN = className;
        groupN = groupName;
        freeCash = funds;
        tolStocks = aoStock;
    }

    public void update() {//updats the current prices for the stocks
        for (int i = 0; i < 10; i++) {
            if (stocks[i] == null) {
                //if the stock is empty, then end loop
                i = 10;
            } else {
                //call the changr current value method of stocks
                stocks[i].changeCurr(checkForStock(1, stocks[i].getSymbol()));
            }
        }
    }

    public int rtnStkLength() {//return the amount of unique stocks
        return tolStocks;
    }

    public String getClss() {//returns the groups class
        return classN;
    }

    public double getFunds() {//returns the groups remaining money
        return freeCash;
    }

    public String getGroup() {//returns the groups name
        return groupN;
    }

    public Stock getStock(int n) {//returns a specific stock
        return stocks[n];
    }

    public Stock[] getAllStocks() {//returns all the stocks
        return stocks;
    }

    public double getWorth() {
        //gets the total worth of the student
        double value = 0;//the total worth

        value += freeCash;

        for (int i = 0; i < watchList.MAX_STOCKS; i++) {
            if (stocks[i] != null) {
                //if stock is not empty, add value to total
                value += stocks[i].getTotal();
            }
        }

        return value;
    }

    public String toString() {
        return "Group Name: " + getGroup() + "\nClass: " + getClss() + "\nWorth: " + getWorth() + "\nFree Cash: " + freeCash;
    }

    private void rearrangeStocks() {
        //re arrange the stocks so there are no empty slots in the middle
        for (int i = 0; i < 9; i++) {
            //go though the array
            if (stocks[i + 1] != null) {
                //if the stock after i is loaded
                if (stocks[i] == null) {
                    //but i is empty

                    //then swap them
                    stocks[i] = stocks[i + 1];
                    stocks[i + 1] = null;
                }
            }
        }
    }

    public boolean sellStock(String st, int amount) {//variable to sell a stock
        if (amount < -1) {//check if its a valid amount
            javax.swing.JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            return false;
        }

        st = st.toUpperCase();
        for (int i = 0; i < watchList.MAX_STOCKS; i++) {
        //if its found
            if (stocks[i] == null) {
                javax.swing.JOptionPane.showMessageDialog(null, "Stock Not Found.");
                return false;
            }
            if (st.compareTo(stocks[i].getSymbol()) == 0) {
                //if they didnt chose to sell all stocks
                if (amount != -1) {
                    //if selling too many stocks
                    if (amount > stocks[i].getNum()) {
                        javax.swing.JOptionPane.showMessageDialog(null, "You can't sell more stocks than you already own.");
                        return false;
                    }
                    //if the number is valid

                    //add cash
                    freeCash += stocks[i].getCVal() * amount;
                    //decrease the stocks you own
                    stocks[i].addAmount(-amount);
                    return true;
                } else {
                    //if they want to sell all stocks
                    //add profit to your cash flow
                    freeCash += stocks[i].getTotal();
                    //delete stock
                    stocks[i] = null;
                    //re arrange the stocks so there are no empty slots in the middle
                    rearrangeStocks();
                    return true;//sold
                }
            }
        }
        //if not found
        javax.swing.JOptionPane.showMessageDialog(null, "Stock Not Found.");
        return false;//not found
    }

    public void addAStock(Stock[] stk) {
        //search for an emptry stock slot
        for(int i = 0; i < watchList.MAX_STOCKS; i++) {
            stocks[i] = stk[i];//add it
        }
    }

    public boolean addAStock(Stock stk) {
        //search for an emptry stock slot
        for (int i = 0; i < 10; i++) {
            if (stocks[i] == null) {
                //found an empty slot, GG
                stocks[i] = stk;//add it

                return true;
            }
        }
        //if no empty slots for stocks left
        javax.swing.JOptionPane.showMessageDialog(null, "You already own the maximum number of allowed stocks, purchased failed.");
        return false;
    }

    public boolean buyStock(String name, int amount) {
        if(amount < -1){//check if its a valid amount
            javax.swing.JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            return false;
        }
        
            //if stock is found
        if (checkForStock(name)) {

            //if insurficient funds
            if ((freeCash - amount * pric) < 0) {
                javax.swing.JOptionPane.showMessageDialog(null, "You don't have enough money for this.");
                return false;
            } else {
                //if funds are enough
                //check if the stock exists in your list of stocks
                boolean stockExist = false;
                int counterCount = 0;
                for (int i = 0; i < 10; i++) {
                    if (stocks[i] == null) {
                    }//do nothing
                    else {//if there is a stock, check the symbol
                        if (symb.compareTo(stocks[i].getSymbol()) == 0) {
                            stockExist = true;
                            counterCount = i;
                            i = 10;
                        }
                    }
                }
                //if you already own at least 1 share,
                if (stockExist) {
                    stocks[counterCount].addAmount(amount);
                    freeCash = freeCash - amount * pric;
                    return true;
                } else {//add the stock to array
                    Stock temp = new Stock(namS, symb, pric, pric, amount, StockWatchView.crrntUser);//the stock you are looking forward to add
                    if (addAStock(temp)) {
                        //if have extra slots for stocks
                        freeCash = freeCash - amount * pric;
                        return true;
                    } else {
                        //already have max stocks
                        return false;
                    }
                }
            }
        }
        //if not found, do nothing
        return false;
    }

    public static double checkForStock(int a, String stockName) {
        //delete previously used files
        File f = new File(filePath);
        f.delete();//deletes previous stock quotes

        stockName = stockName.toUpperCase();//to capital
        try {
            int counter = 0;
            URL website = new URL("http://www.finance.yahoo.com/d/quotes.csv?s=" + stockName + "&f=sd1l1n");
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.getChannel().transferFrom(rbc, 0, 1 << 24);
            //reads in the file
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            String data = br.readLine();//reads in the stock info
            br.close();//close file
            //reinit all variables
            symb = "";
            date = "";
            priS = "";
            namS = "";
            pric = 0;

            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) == '\"') {
                }//do nothing cause its a "
                else {
                    if (data.charAt(i) == ',' & counter < 3) {
                        counter++;//store the values into the next variable
                    } else {
                        //read in the data
                        switch (counter) {
                            case 0:
                                symb += data.charAt(i);
                                break;
                            case 1:
                                date += data.charAt(i);
                                break;
                            case 2:
                                priS += data.charAt(i);
                                break;
                            case 3:
                                namS += data.charAt(i);
                                break;
                            default:
                                javax.swing.JOptionPane.showMessageDialog(null, "Error reading stock details (Error -1337)\nPlease contact a system admin");
                                System.exit(-1337);
                                break;
                        }
                    }
                    for (int j = 0; j < namS.length(); j++) {
                        if (namS.charAt(j) == ',') {
                            namS = namS.substring(0, j - 1);
                            j = 100;
                        }
                    }
                }
            }

            try {//check if there are any errors with the connection or any other errors
                pric = Double.parseDouble(priS);
            } catch (NumberFormatException e) {
                javax.swing.JOptionPane.showMessageDialog(null, "Please check your internet connection \nIf the problem presists, the error code is 9001.");
                System.out.println("symb: " + symb + "\tdate: " + date + "\tpriS: " + priS + "\tnamS: " + namS + "\tpric: " + pric);
                System.exit(9001);
            }

        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Internet connection is required.\nPlease make sure you are connected to the World Wide Web.");
        }

        f.delete();//deletes previous stock quotes

        return pric;//gets you the latest price
    }

    public static boolean checkForStock(String stockName) {


        //clears the file with the same name if it exists
        File f = new File(filePath);
        f.delete();//deletes previous stock quotes

        stockName = stockName.toUpperCase();
        try {
            int counter = 0;
            //downloads data from yahoo finance
            //URL("http://www.finance.yahoo.com/d/quotes.csv?s=msft&f=sd1l1n");
            URL website = new URL("http://www.finance.yahoo.com/d/quotes.csv?s=" + stockName + "&f=sd1l1n");
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.getChannel().transferFrom(rbc, 0, 1 << 24);

            //reads in the file
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            String data = br.readLine();//reads in the stock info
            br.close();//close file

            //reinit all variables
            symb = "";
            date = "";
            priS = "";
            namS = "";
            pric = 0;

            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) == '\"') {
                }//do nothing cause its a "
                else {
                    if (data.charAt(i) == ',' & counter < 3) {
                        counter++;//store the values into the next variable
                    } else {
                        //read in the data
                        switch (counter) {
                            case 0:
                                symb += data.charAt(i);
                                break;
                            case 1:
                                date += data.charAt(i);
                                break;
                            case 2:
                                priS += data.charAt(i);
                                break;
                            case 3:
                                namS += data.charAt(i);
                                break;
                            default:
                                javax.swing.JOptionPane.showMessageDialog(null, "Error reading stock details (Error -1337)\nPlease contact a system admin");
                                System.exit(-1337);
                                break;
                        }
                    }
                    for (int j = 0; j < namS.length(); j++) {
                        if (namS.charAt(j) == ',') {
                            namS = namS.substring(0, j - 1);
                            j = 100;
                        }
                    }
                }
            }

            try {//check for any errors
                pric = Double.parseDouble(priS);
            } catch (NumberFormatException e) {
                javax.swing.JOptionPane.showMessageDialog(null, "Please check your internet connection \nIf the problem presists, the error code is 9001.");
                System.out.println("symb: " + symb + "\tdate: " + date + "\tpriS: " + priS + "\tnamS: " + namS + "\tpric: " + pric);
                System.exit(9001);
            }

        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Internet connection is required.\nPlease make sure you are connected to the World Wide Web.");
        }

        f.delete();//deletes previous stock quotes

        //if stock found
        if (pric > 0) {
        //if the stock has a value, then it exists. Other wise yahoo will give us a value of '0'
        //then good for U !
            javax.swing.JOptionPane.showMessageDialog(null, "Stock found.\n" + symb + "\n" + namS + "\nPrice: " + pric + "\nLast trade date: " + date);
            return true;
        }
        //if not found
        javax.swing.JOptionPane.showMessageDialog(null, "Stock " + stockName + " NOT found.");
        return false;
    }
}

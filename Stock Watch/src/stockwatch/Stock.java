
/**
 *
 * @author sthu5383
 * class to store stock characteristics
 * January 13 2013
 */
package stockwatch;

public class Stock {

    private String name, userNm, symbol;//the name and symbol of a stock
    private double startVal, initialVal, currentVal;//the starting, previous, and current value
    private int numStock;//number of stocks you own

    public Stock(String sName, String sSymbol, double value, double curr, int nStock, String buyerNm) {
        name = sName;//store the name
        symbol = sSymbol;//store symbol
        startVal = value;//store values
        initialVal = value;//initial value
        currentVal = curr;//current last checked value
        numStock = nStock;//store number of stocks
        userNm = buyerNm;//record which grp memeber buys the shares
    }

    public void addAmount(int number){//add a number of stocks
        numStock+=number;
    }
    
    public String getSymbol() {//return the stock symbol
        return symbol;
    }
    public void changeCurr(double value){//change the current value
        currentVal=value;
    }
    public String getName() {//return stocks name
        return name;
    }

    public int getNum() {//return the number of stocks
        return numStock;
    }
    
    public String getNm() {//return the number of stocks
        return userNm;
    }

    public double getChange() {
        //calculating the change: current vs. start
        int change = (int) ((currentVal - startVal) * 100.0);
        //accurate to two decimal spaces
        return change / 100.0;
    }

    public double getSVal() {
        return startVal;
    //returns start value
    }

    public double getCVal() {
        return currentVal;
    //returns current value
    }

    public String toString() {//print everything
        String out = "Name: " + name + "\nSymbol: " + symbol + "\nStarting Value: $" + startVal
                + "\nPrevious Value: $" + initialVal + "\nCurrentValue: $" + currentVal
                + "\nNumber of Stocks Owned: " + numStock + "\nValue of Your Stocks: $" + getTotal();
        return out;
    }

    public String toPureString() {//print stuff needed to write to datafile
        String out = name + "," + symbol + "," + initialVal + "," + currentVal + "," + numStock +  "," + userNm + "\n";
        return out;
    }

    public double getTotal() {
        double total = (int) ((currentVal * numStock) * 100.0);
        //accurate to two decimal spaces
        return total / 100.0;
        //returns the total value
    }
}

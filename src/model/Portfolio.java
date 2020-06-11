package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Portfolio implements I_Portfolio {

    private String name;
    private List<OwnedStock> stockToAmount;

    public Portfolio(String n) {
        name = n;
        stockToAmount = new ArrayList<>();
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public List<OwnedStock> getOwnedStocks() {
        return stockToAmount;
    }

    public double getTotalValue() {
        double total = 0;
        for (int i=0; i<stockToAmount.size(); i++){
            total += getValueOfHolding(stockToAmount.get(i).getName());
        }
        return total;
    }

    public boolean addStock(String ticker, String name,int numberOfShare) {
        Stock s = new Stock(ticker);
        OwnedStock os = new OwnedStock(s, name,numberOfShare);
        return stockToAmount.add(os);
    }

    public double getValueOfHolding(String stockName) {
        int target = 0;
        for (int i=0; i<stockToAmount.size(); i++){
            if (stockToAmount.get(i).getName() == stockName){
                target = i;
                break;
            }
        }
        return stockToAmount.get(target).getNumberOwned() * stockToAmount.get(target).getStock().getPrice();
    }

}

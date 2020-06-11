package model;

import java.util.List;
import java.util.Map;

public interface I_Portfolio<T extends I_OwnedStock> {

    void setName(String n);

    String getName();

    List<T> getOwnedStocks();

    double getTotalValue();

    boolean addStock(String ticker, String name,int numberOfShares);

    double getValueOfHolding(String stockTicker);

}

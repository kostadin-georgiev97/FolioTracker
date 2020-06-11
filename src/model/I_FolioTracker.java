package model;

import java.util.List;

public interface I_FolioTracker<T extends I_Portfolio> {

    boolean addStocksToPortfolio(String portfolioName, String stockTicker, String name, int number);

    T getPortfolio(String portfolioName);
    
    List<T> getPortfolios();

    boolean addPortfolio(String portfolioName);

    boolean removePortfolio(String portfolioName);

    void refreshSharePrice();

    boolean editSharesPortfolio(String portfolioName, String stockName, int newShareNumber);

    boolean deleteStock(String portfolioName, String stockTicker);
    
    String[][] getFolioData(String portfolioName);
    
}

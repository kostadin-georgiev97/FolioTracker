package model;

import java.util.ArrayList;
import java.util.List;

public class FolioTracker implements I_FolioTracker {

    private List<Portfolio> portfolioList = new ArrayList<>();

    public boolean addStocksToPortfolio(String portfolioName, String stockTicker, String name, int number) {
        for (int i=0; i<portfolioList.size(); i++){
            if (portfolioList.get(i).getName().equals(portfolioName)){
                for (int j=0; j<portfolioList.get(i).getOwnedStocks().size(); j++){
                    if (portfolioList.get(i).getOwnedStocks().get(j).getStock().getTicker().equals(stockTicker)){
                        return false;
                    }
                }
                return portfolioList.get(i).addStock(stockTicker, name, number);
            }
        }
        return false;
    }

    public Portfolio getPortfolio(String portfolioName) {
        for (int i=0; i<portfolioList.size(); i++){
            if (portfolioList.get(i).getName().equals(portfolioName)){
                return portfolioList.get(i);
            }
        }
        return null;
    }
    
    @Override
	public List<Portfolio> getPortfolios() {
		return portfolioList;
	}

    public boolean addPortfolio(String portfolioName) {
        for (int i=0; i<portfolioList.size(); i++){
            if(portfolioList.get(i).getName().equals(portfolioName)){
                return false;
            }
        }
        Portfolio p = new Portfolio(portfolioName);
        return portfolioList.add(p);
    }

    public boolean removePortfolio(String portfolioName) {
        for (int i=0; i<portfolioList.size(); i++){
            if (portfolioList.get(i).getName().equals(portfolioName)){
                return portfolioList.remove(portfolioList.get(i));
            }
        }
        return false;
    }

    public void refreshSharePrice() {
        for (int i=0; i<portfolioList.size(); i++){
            for (int j=0; j<portfolioList.get(i).getOwnedStocks().size(); j++){
                portfolioList.get(i).getOwnedStocks().get(j).getStock().updatePrice();
            }
        }
    }
    
    public boolean editSharesPortfolio(String portfolioName, String stockName, int newShareNumber) {
        for(int i=0; i<portfolioList.size(); i++){
            if(portfolioList.get(i).getName().equals(portfolioName)){
                for(int j=0; j<portfolioList.get(i).getOwnedStocks().size(); j++){
                    if(portfolioList.get(i).getOwnedStocks().get(j).getName().equals(stockName)){
                        portfolioList.get(i).getOwnedStocks().get(j).setNumberOwned(newShareNumber);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean deleteStock(String portfolioName, String stockTicker) {
        for (int i=0; i<portfolioList.size(); i++){
            if (portfolioList.get(i).getName().equals(portfolioName)){
                for (int j=0; j<portfolioList.get(i).getOwnedStocks().size(); j++){
                    if (portfolioList.get(i).getOwnedStocks().get(j).getStock().getTicker().equals(stockTicker)){
                        return portfolioList.get(i).getOwnedStocks().remove(portfolioList.get(i).getOwnedStocks().get(j));
                    }
                }
            }
        }
        return false;
	}

    /**
     * gets
     * - Ticker
     * - Name
     * - Number owned
     * - Price per share
     * - Value of holding
     * - Gains
     * @param portfolioName
     * @return
     */
	public String[][] getFolioData(String portfolioName) {
    	List<OwnedStock> stocks = getPortfolio(portfolioName).getOwnedStocks();
    	String[][] data = new String[stocks.size()][6];
        for (int i=0; i < stocks.size(); i++) {
        	data[i][0] = stocks.get(i).getStock().getTicker();
        	data[i][1] = stocks.get(i).getName();
        	data[i][2] = String.valueOf(stocks.get(i).getNumberOwned());
        	data[i][3] = String.valueOf(stocks.get(i).getStock().getPrice());
        	data[i][4] = String.valueOf(getPortfolio(portfolioName).getValueOfHolding(stocks.get(i).getName()));
            data[i][5] = String.valueOf(stocks.get(i).gains());
        }
        return data;
	}


}

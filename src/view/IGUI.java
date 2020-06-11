package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.JTextField;

public interface IGUI {
	
	JMenuItem getMenuItem(String menuItemName);
	
    JButton getButton(String portfolioName, String buttonName);
    
	JFrame getNewPortfolioFrame();
	
    JTextField getTextField(String portfolioName, String textFieldName);
    
    JTextField getNewPortfolioNameTextField();
	
	JButton getNewPortfolioButton();
	
	void makePortfolioTab(String portfolioName);
	
	void editStockFrame(String portfolioName, String tickerSymbol, String stockName, String currentValue, String valueOfHolding, String numberOfShares, String initialValue);
	
	JFrame getEditStockFrame();
	
	void removePortfolioTab(String tabName);
	
	void alert(String message);
	
	void refreshPortfolioTable(String portfolioName, String[][] portfolioData);
	
	void refreshPortfolioTotalValue(String portfolioName, String totalValue);
	
	JTable getTable(String portfolioName);
	
	JTextField getStockTextField(String portfolioName, String tickerSymbol, String textFieldName);
	
	JButton getStockButton(String portfolioName, String tickerSymbol, String buttonName);

	String getSelectedTab();
	
}
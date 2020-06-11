package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JTable;

import model.FolioTracker;
import model.I_FolioTracker;
import model.I_Portfolio;
import model.OwnedStock;
import model.Portfolio;
import view.GUI;
import view.IGUI;

public class Controller implements ActionListener {
	private IGUI gui;
	private I_FolioTracker folioTracker;

	public Controller() {
		gui = new GUI();
		folioTracker = new FolioTracker();

		gui.getMenuItem("Refresh").addActionListener(this::actionPerformed);
		gui.getMenuItem("Open").addActionListener(this::actionPerformed);
		gui.getMenuItem("Save").addActionListener(this::actionPerformed);

		gui.getNewPortfolioButton().addActionListener(this::actionPerformed);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<Portfolio> portfolios = folioTracker.getPortfolios();

		if (e.getSource() == gui.getNewPortfolioButton()) {
			String portfolioName = gui.getNewPortfolioNameTextField().getText();
			createNewPortfolio(portfolioName);
		}
		if (e.getSource() == gui.getMenuItem("Refresh")) {
			for (Portfolio portfolio : portfolios) {
				gui.refreshPortfolioTable(portfolio.getName(), folioTracker.getFolioData(portfolio.getName()));
				gui.refreshPortfolioTotalValue(portfolio.getName(), String.valueOf(portfolio.getTotalValue()));
			}
		}
		if (e.getSource() == gui.getMenuItem("Open")) {
			JFileChooser fileChooser = new JFileChooser();

			int choice = fileChooser.showOpenDialog(null);
			if (choice == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				try {
					FileReader fr = new FileReader(file);
					BufferedReader reader = new BufferedReader(fr);
					String line = reader.readLine();
					Scanner scan = null;

					scan = new Scanner(line);
					scan.next();				 // Portfolio Name
					String portfolioName = scan.next();

					createNewPortfolio(portfolioName);

					reader.readLine();
					line = reader.readLine();
					while (line != null) {
						// Ticker
						scan = new Scanner(line);
						scan.next(); scan.next();
						String stockTicker = scan.next();

						line = reader.readLine(); // Stock Name
						scan = new Scanner(line);
						scan.next(); scan.next(); 
						String stockNamee = scan.next();

						line = reader.readLine(); // Num of shares
						scan = new Scanner(line);
						scan.next(); scan.next(); scan.next(); 
						int number = scan.nextInt();

						reader.readLine(); reader.readLine(); reader.readLine(); 

						folioTracker.addStocksToPortfolio(portfolioName, stockTicker, stockNamee, number);
						gui.refreshPortfolioTable(portfolioName, folioTracker.getFolioData(portfolioName));
						
						line = reader.readLine();
					}
					
					scan.close();
					reader.close();

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		if (e.getSource() == gui.getMenuItem("Save")) {
			JFileChooser fileChooser = new JFileChooser();
			String portfolioName = gui.getSelectedTab();

			int choice = fileChooser.showSaveDialog(null);
			if (choice == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				try {
					FileWriter writer = new FileWriter(file);
					BufferedWriter bw = new BufferedWriter(writer);
					bw.write("Portfolio " + portfolioName);
					bw.newLine();
					bw.write("-----------------------");
					bw.newLine();
					for (int i = 0; i < folioTracker.getFolioData(portfolioName).length; i++) {
						bw.write("Ticker Symbol: " + folioTracker.getFolioData(portfolioName)[i][0]);
						bw.newLine();
						bw.write("Stock Name: " + folioTracker.getFolioData(portfolioName)[i][1]);
						bw.newLine();
						bw.write("Number of Shares: " + folioTracker.getFolioData(portfolioName)[i][2]);
						bw.newLine();
						bw.write("Price per Share: " + folioTracker.getFolioData(portfolioName)[i][3]);
						bw.newLine();
						bw.write("Value of Holding: " + folioTracker.getFolioData(portfolioName)[i][4]);
						bw.newLine();
						bw.write("*******************");
						bw.newLine();
					}
					bw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		// Add Stock and delete portfolio handling
		for (Portfolio portfolio : portfolios) {
			String portfolioName = portfolio.getName();
			if (e.getSource() == gui.getButton(portfolioName, "Add")) {
				addStockToPortfolio(portfolioName);
			}
			if (e.getSource() == gui.getButton(portfolioName, "Delete Portfolio")) {
				removePortfolio(portfolioName);
			}
		}
	}
	
	/**
	 * Creates a new portfolio both in the Model and the View.
	 * @param portfolioName
	 */
	private void createNewPortfolio(String portfolioName) {
		List<Portfolio> portfolios = folioTracker.getPortfolios();
		
		folioTracker.addPortfolio(portfolioName);
		gui.makePortfolioTab(portfolioName);

		I_Portfolio folio = folioTracker.getPortfolio(portfolioName);

		gui.getButton(portfolioName, "Add").addActionListener(this::actionPerformed);
		gui.getTable(portfolioName).addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = (int) ((JTable) e.getSource()).rowAtPoint(e.getPoint());
					String ticker = (String) ((JTable) e.getSource()).getModel().getValueAt(row, 0);
					List<OwnedStock> stocks = folio.getOwnedStocks();
					String stockName = "", currentValue = "", valueOfHolding = "", numberOfShares = "",
							initialValue = "";
					for (OwnedStock stock : stocks) {
						if (stock.getStock().getTicker().equals(ticker)) {
							stockName = stock.getName();
							currentValue = String.valueOf(stock.getStock().getPrice());
							valueOfHolding = String.valueOf(folio.getValueOfHolding(ticker));
							numberOfShares = String.valueOf(stock.getNumberOwned());
							initialValue = String.valueOf(stock.getInitialPrice());
						}
					}
					gui.editStockFrame(portfolioName, ticker, stockName, currentValue, valueOfHolding,
							numberOfShares, initialValue);
					gui.getStockButton(portfolioName, ticker, "save").addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							String newNumberOfShares = gui
									.getStockTextField(portfolioName, ticker, "numberOfShares").getText();
							for (OwnedStock stock : stocks) {
								if (stock.getStock().getTicker().equals(ticker)) {
									stock.setNumberOwned(Integer.valueOf(newNumberOfShares));
								}
							}
							gui.getEditStockFrame().setVisible(false);
							for (Portfolio portfolio : portfolios) {
								gui.refreshPortfolioTable(portfolio.getName(), folioTracker.getFolioData(portfolio.getName()));
								gui.refreshPortfolioTotalValue(portfolio.getName(), String.valueOf(portfolio.getTotalValue()));
							}
						}

					});
					gui.getStockButton(portfolioName, ticker, "delete").addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							folioTracker.deleteStock(portfolioName, ticker);
							gui.getEditStockFrame().setVisible(false);
							for (Portfolio portfolio : portfolios) {
								gui.refreshPortfolioTable(portfolio.getName(), folioTracker.getFolioData(portfolio.getName()));
								gui.refreshPortfolioTotalValue(portfolio.getName(), String.valueOf(portfolio.getTotalValue()));
							}
						}

					});
				}
			}
		});
		gui.getButton(portfolioName, "Delete Portfolio").addActionListener(this::actionPerformed);

		gui.getNewPortfolioNameTextField().setText("");
		gui.getNewPortfolioFrame().setVisible(false);
	}
	
	/**
	 * Adds a stock to a portfolio both in the Model and the View.
	 * @param portfolioName
	 */
	private void addStockToPortfolio(String portfolioName) {
		String stockTicker = gui.getTextField(portfolioName, "Ticker Symbol:").getText().toUpperCase();
		String stockName = gui.getTextField(portfolioName, "Stock Name:").getText();

		// Validation
		if (stockTicker.isEmpty() || stockName.isEmpty() || (gui.getTextField(portfolioName, "Number of Shares:").getText().isEmpty())) {
			gui.alert("All fields must be flled!");
		} else {
			boolean matchingStock = false;
			I_Portfolio folio = folioTracker.getPortfolio(portfolioName);
			List<OwnedStock> stocks = folio.getOwnedStocks();
			for (OwnedStock stock : stocks) {
				if (stock.getStock().getTicker().equals(stockTicker)) {
					matchingStock = true;
				}
			}

			if (matchingStock) {
				gui.alert("This stock is already in the portfolio. You can't add it twice!");
			} else {
				try {
					int numberOfShares = Integer
							.valueOf(gui.getTextField(portfolioName, "Number of Shares:").getText());

					if (numberOfShares < 1) {
						gui.alert("The number of shares must be positive!");
					} else {
						try {

							folioTracker.addStocksToPortfolio(portfolioName, stockTicker, stockName, numberOfShares);

							gui.refreshPortfolioTable(portfolioName, folioTracker.getFolioData(portfolioName));
							gui.refreshPortfolioTotalValue(portfolioName, String.valueOf(folioTracker.getPortfolio(portfolioName).getTotalValue()));
							gui.getTextField(portfolioName, "Ticker Symbol:").setText("");
							gui.getTextField(portfolioName, "Stock Name:").setText("");
							gui.getTextField(portfolioName, "Number of Shares:").setText("");
						} catch (Exception exc) {
							gui.alert("Invalid token or bad connection to server!");
						}
					}
				} catch (NumberFormatException exception) {
					gui.alert("Number of shares must be a whole number!");
				}
			}
		}
	}
	
	/**
	 * Removes a portfolio both from the Model and the View.
	 * @param portfolioName
	 */
	private void removePortfolio(String portfolioName) {
		folioTracker.removePortfolio(portfolioName);
		gui.removePortfolioTab(portfolioName);
		gui.getButton(portfolioName, "Add").removeActionListener(this);
		gui.getButton(portfolioName, "Delete Portfolio").removeActionListener(this);
	}

}

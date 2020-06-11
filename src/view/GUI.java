package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class GUI implements ActionListener, IGUI {

	private JFrame frame, newPortfolioFrame, editStockFrame;
	private JPanel layoutPanel;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem newMenuItem, openMenuItem, saveMenuItem, exitMenuItem, refreshMenuItem;
	private JTabbedPane tabbedPane;
	private JLabel newPortfolioNameLabel;
	private JTextField newPortfolioNameTextField;
	private JButton newPortfolioButton;

	private Map<String, JMenuItem> menuItems = new HashMap<String, JMenuItem>();
	private Map<String, HashMap<String, JButton>> buttons = new HashMap<String, HashMap<String, JButton>>();
	private Map<String, HashMap<String, JTextField>> textFields = new HashMap<String, HashMap<String, JTextField>>();
	private Map<String, DefaultTableModel> tableModels = new HashMap<String, DefaultTableModel>();
	private Map<String, JTable> tables = new HashMap<String, JTable>();
	private Map<String, JLabel> totalValueLabels = new HashMap<String, JLabel>();
	private Map<String, HashMap<String, HashMap<String, JButton>>> stockButtons = new HashMap<String, HashMap<String, HashMap<String, JButton>>>();
	private Map<String, HashMap<String, HashMap<String, JTextField>>> stockTextFields = new HashMap<String, HashMap<String, HashMap<String, JTextField>>>();

	public GUI() {
		frame = new JFrame("Foliotracker");

		// Create the menu bar.
		menuBar = new JMenuBar();

		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menu);

		// a group of JMenuItems
		newMenuItem = new JMenuItem("New", KeyEvent.VK_N);
		menu.add(newMenuItem);
		menuItems.put("New", newMenuItem);
		newMenuItem.addActionListener(this);

		refreshMenuItem = new JMenuItem("Refresh", KeyEvent.VK_R);
		menu.add(refreshMenuItem);
		menuItems.put("Refresh", refreshMenuItem);

		openMenuItem = new JMenuItem("Open", KeyEvent.VK_O);
		menu.add(openMenuItem);
		menuItems.put("Open", openMenuItem);

		saveMenuItem = new JMenuItem("Save", KeyEvent.VK_S);
		menu.add(saveMenuItem);
		menuItems.put("Save", saveMenuItem);

		exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_E);
		menu.add(exitMenuItem);
		menuItems.put("Exit", exitMenuItem);
		exitMenuItem.addActionListener(this);

		layoutPanel = new JPanel(new BorderLayout());

		tabbedPane = new JTabbedPane();
		layoutPanel.add(tabbedPane);

		frame.setJMenuBar(menuBar);
		frame.add(layoutPanel);
		frame.pack();
		frame.setMinimumSize(new Dimension(600, 500));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		newPortfolioFrame();
	}

	private void newPortfolioFrame() {
		newPortfolioFrame = new JFrame("Add new portfolio");

		JPanel newPortfolioLayoutPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();

		newPortfolioNameLabel = new JLabel("Portfolio Name:");
		newPortfolioLayoutPanel.add(newPortfolioNameLabel);
		newPortfolioNameTextField = new JTextField();
		newPortfolioNameTextField.setColumns(20);
		newPortfolioLayoutPanel.add(newPortfolioNameTextField);
		newPortfolioButton = new JButton("Add Portfolio");
		buttonsPanel.add(newPortfolioButton);

		newPortfolioFrame.add(newPortfolioLayoutPanel, BorderLayout.CENTER);
		newPortfolioFrame.add(buttonsPanel, BorderLayout.SOUTH);
		newPortfolioFrame.pack();
		newPortfolioFrame.setMinimumSize(new Dimension(350, 80));
		newPortfolioFrame.setLocationRelativeTo(null);
		newPortfolioFrame.setVisible(false);
	}

	@Override
	public JFrame getNewPortfolioFrame() {
		return newPortfolioFrame;
	}

	@Override
	public JTextField getNewPortfolioNameTextField() {
		return newPortfolioNameTextField;
	}

	@Override
	public JButton getNewPortfolioButton() {
		return newPortfolioButton;
	}

	@Override
	public void makePortfolioTab(String portfolioName) {
		HashMap<String, JButton> innerMapButtons = new HashMap<String, JButton>();
		HashMap<String, JTextField> innerMapTextFields = new HashMap<String, JTextField>();

		JPanel panel = new JPanel(new BorderLayout());

		JToolBar stockToolBar = new JToolBar("stockToolBar");
		JLabel tickerSymbolLabel = new JLabel("Ticker Symbol:");
		JTextField tickerSymbolTextField = new JTextField();
		innerMapTextFields.put("Ticker Symbol:", tickerSymbolTextField);

		JLabel stockNameLabel = new JLabel("Stock Name:");
		JTextField stockNameTextField = new JTextField();
		innerMapTextFields.put("Stock Name:", stockNameTextField);

		JLabel numberOfSharesLabel = new JLabel("Number of Shares:");
		JTextField numberOfSharesTextField = new JTextField();
		innerMapTextFields.put("Number of Shares:", numberOfSharesTextField);
		JButton addStockButton = new JButton("Add");
		innerMapButtons.put("Add", addStockButton);

		stockToolBar.add(tickerSymbolLabel);
		stockToolBar.addSeparator();
		stockToolBar.add(tickerSymbolTextField);
		stockToolBar.addSeparator();
		stockToolBar.add(stockNameLabel);
		stockToolBar.addSeparator();
		stockToolBar.add(stockNameTextField);
		stockToolBar.addSeparator();
		stockToolBar.add(numberOfSharesLabel);
		stockToolBar.addSeparator();
		stockToolBar.add(numberOfSharesTextField);
		stockToolBar.addSeparator();
		stockToolBar.add(addStockButton);

		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.setDataVector(new Object[][] {}, new Object[] { "Ticker Symbol", "Stock Name", "Number of Shares",
				"Price per Share", "Value of Holding", "Change" });
		tableModels.put(portfolioName, tableModel);
		JTable table = new JTable(tableModel);
		tables.put(portfolioName, table);
		table.setDefaultEditor(Object.class, null);
		JScrollPane scroll = new JScrollPane(table);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));
		JLabel totalValueLabel = new JLabel("total value: 0");
		totalValueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		totalValueLabel.setForeground(Color.BLUE);
		totalValueLabels.put(portfolioName, totalValueLabel);
		JButton deletePortfolioButton = new JButton("Delete Portfolio");
		deletePortfolioButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		innerMapButtons.put("Delete Portfolio", deletePortfolioButton);
		bottomPanel.add(totalValueLabel);
		bottomPanel.add(deletePortfolioButton);

		buttons.put(portfolioName, innerMapButtons);
		textFields.put(portfolioName, innerMapTextFields);

		panel.add(stockToolBar, BorderLayout.NORTH);
		panel.add(scroll, BorderLayout.CENTER);
		panel.add(bottomPanel, BorderLayout.SOUTH);
		tabbedPane.addTab(portfolioName, panel);
	}

	@Override
	public void editStockFrame(String portfolioName, String tickerSymbol, String stockName, String currentValue,
			String valueOfHolding, String numberOfShares, String initialValue) {
		editStockFrame = new JFrame("Edit stock '" + tickerSymbol + "' for " + portfolioName);

		JPanel topPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(4, 2));
		JPanel buttonsPanel = new JPanel();

		JLabel portfolioLabel = new JLabel("Editing portfolio: " + portfolioName);
		portfolioLabel.setForeground(Color.BLUE);
		topPanel.add(portfolioLabel);

		JLabel tickerLabel = new JLabel(tickerSymbol);
		centerPanel.add(tickerLabel);
		JLabel stockNameLabel = new JLabel(stockName);
		centerPanel.add(stockNameLabel);
		JLabel currentValueLabel = new JLabel("Current value: " + currentValue);
		centerPanel.add(currentValueLabel);
		JLabel valueOfHoldingLabel = new JLabel("Value of holding: " + valueOfHolding);
		centerPanel.add(valueOfHoldingLabel);

		HashMap<String, HashMap<String, JTextField>> innerMapTextFields = new HashMap<String, HashMap<String, JTextField>>();
		HashMap<String, JTextField> innerMapTextFields2 = new HashMap<String, JTextField>();

		JLabel numberOfSharesLabel = new JLabel("Number of shares:");
		centerPanel.add(numberOfSharesLabel);
		JTextField numberOfSharesTextField = new JTextField(numberOfShares);
		innerMapTextFields2.put("numberOfShares", numberOfSharesTextField);
		centerPanel.add(numberOfSharesTextField);
		JLabel initialValueLabel = new JLabel("Initial value: " + initialValue);
		centerPanel.add(initialValueLabel);

		innerMapTextFields.put(tickerSymbol, innerMapTextFields2);
		stockTextFields.put(portfolioName, innerMapTextFields);

		JLabel totalGainLabel = new JLabel("Total gain: 0.0");
		centerPanel.add(totalGainLabel);
		numberOfSharesTextField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent e) {
				if (!numberOfSharesTextField.getText().equals("")) {
					double totalGain = Double.valueOf(numberOfSharesTextField.getText()) * Double.valueOf(currentValue)
							- Double.valueOf(numberOfShares) * Double.valueOf(currentValue);
					totalGainLabel.setText(String.valueOf("Total gain: " + totalGain));
					if (totalGain > 0) {
						totalGainLabel.setForeground(Color.GREEN);
					} else if (totalGain < 0) {
						totalGainLabel.setForeground(Color.RED);
					} else {
						totalGainLabel.setForeground(Color.BLACK);
					}
				}
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if (!numberOfSharesTextField.getText().equals("")) {
					double totalGain = Double.valueOf(numberOfSharesTextField.getText()) * Double.valueOf(currentValue)
							- Double.valueOf(numberOfShares) * Double.valueOf(currentValue);
					totalGainLabel.setText(String.valueOf("Total gain: " + totalGain));
					if (totalGain > 0) {
						totalGainLabel.setForeground(Color.GREEN);
					} else if (totalGain < 0) {
						totalGainLabel.setForeground(Color.RED);
					} else {
						totalGainLabel.setForeground(Color.BLACK);
					}
				}
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if (!numberOfSharesTextField.getText().equals("")) {
					double totalGain = Double.valueOf(numberOfSharesTextField.getText()) * Double.valueOf(currentValue)
							- Double.valueOf(numberOfShares) * Double.valueOf(currentValue);
					totalGainLabel.setText(String.valueOf("Total gain: " + totalGain));
					if (totalGain > 0) {
						totalGainLabel.setForeground(Color.GREEN);
					} else if (totalGain < 0) {
						totalGainLabel.setForeground(Color.RED);
					} else {
						totalGainLabel.setForeground(Color.BLACK);
					}
				}
			}

		});

		HashMap<String, HashMap<String, JButton>> innerMapButtons = new HashMap<String, HashMap<String, JButton>>();
		HashMap<String, JButton> innerMapButtons2 = new HashMap<String, JButton>();

		JButton saveStockButton = new JButton("Save");
		innerMapButtons2.put("save", saveStockButton);
		buttonsPanel.add(saveStockButton);
		JButton cancelStockButton = new JButton("Cancel");
		cancelStockButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				editStockFrame.setVisible(false);
			}

		});
		buttonsPanel.add(cancelStockButton);
		JButton deleteStockButton = new JButton("Delete");
		innerMapButtons2.put("delete", deleteStockButton);
		buttonsPanel.add(deleteStockButton);

		innerMapButtons.put(tickerSymbol, innerMapButtons2);
		stockButtons.put(portfolioName, innerMapButtons);

		editStockFrame.add(topPanel, BorderLayout.NORTH);
		editStockFrame.add(centerPanel, BorderLayout.CENTER);
		editStockFrame.add(buttonsPanel, BorderLayout.SOUTH);
		editStockFrame.pack();
		editStockFrame.setMinimumSize(new Dimension(350, 200));
		editStockFrame.setLocationRelativeTo(null);
		editStockFrame.setVisible(true);
	}

	@Override
	public JFrame getEditStockFrame() {
		return editStockFrame;
	}

	@Override
	public void removePortfolioTab(String tabName) {
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			String tabTitle = tabbedPane.getTitleAt(i);
			if (tabTitle.equals(tabName)) {
				tabbedPane.remove(i);
				break;
			}
		}
	}

	@Override
	public void alert(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	@Override
	public void refreshPortfolioTable(String portfolioName, String[][] portfolioData) {
		DefaultTableModel tableModel = tableModels.get(portfolioName);
		JTable table = tables.get(portfolioName);
		tableModel.setRowCount(0);
		Object[] row;
		for (String[] stock : portfolioData) {
			row = new Object[stock.length];
			for (int i = 0; i < stock.length; i++) {
				if (i == 5) {
					if (stock[i].equals("0")) {
						row[i] = "SAME";
					} else if (stock[i].equals("1")) {
						row[i] = "UP";
					} else {
						row[i] = "DOWN";
					}
				} else {
					row[i] = stock[i];
				}
			}
			tableModel.addRow(row);
		}
	}
	
	@Override
	public void refreshPortfolioTotalValue(String portfolioName, String totalValue) {
		JLabel totalValueLabel = totalValueLabels.get(portfolioName);
		String label = totalValueLabel.getText().substring(0, 13);
		totalValueLabel.setText(label + totalValue);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newMenuItem) {
			newPortfolioFrame.setVisible(true);
		}
		if (e.getSource() == openMenuItem) {
			// Handle in Controller
		}
		if (e.getSource() == saveMenuItem) {
			// Handle in Controller
		}
		if (e.getSource() == exitMenuItem) {
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}
	}

	@Override
	public JMenuItem getMenuItem(String menuItemName) {
		return menuItems.get(menuItemName);
	}

	@Override
	public JButton getButton(String portfolioName, String buttonName) {
		return buttons.get(portfolioName).get(buttonName);
	}

	@Override
	public JTextField getTextField(String portfolioName, String textFieldName) {
		return textFields.get(portfolioName).get(textFieldName);
	}

	@Override
	public JTable getTable(String portfolioName) {
		return tables.get(portfolioName);
	}

	@Override
	public JTextField getStockTextField(String portfolioName, String tickerSymbol, String textFieldName) {
		return stockTextFields.get(portfolioName).get(tickerSymbol).get(textFieldName);
	}

	@Override
	public JButton getStockButton(String portfolioName, String tickerSymbol, String buttonName) {
		return stockButtons.get(portfolioName).get(tickerSymbol).get(buttonName);
	}

	@Override
	public String getSelectedTab() {
		int selected = tabbedPane.getSelectedIndex();
		return tabbedPane.getTitleAt(selected);
	}
}
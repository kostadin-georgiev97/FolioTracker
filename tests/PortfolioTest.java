

import Model.*;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PortfolioTest {

    @Test
    void getNameTest(){
        I_Portfolio portfolio = new Portfolio("Name");
        assertEquals("Name", portfolio.getName());
    }


    @Test
    void addStockTest(){
        I_Portfolio portfolio = new Portfolio("Name");
        assertAll("Check that actual stock added returns true",
                () -> assertTrue(portfolio.addStock("TSLA", "Tesla", 10))
        );
    }

    @Test
    void getOwnedStockTest(){
        I_Portfolio portfolio = new Portfolio("Name");
        Stock teslaStock = new Stock("TSLA");
        I_OwnedStock oStock = new OwnedStock(teslaStock, "Tesla", 10);
        portfolio.addStock("TSLA", "Tesla", 10);
        List<I_OwnedStock> returnedList = portfolio.getOwnedStocks();
        assertAll("Check that stock is added with correct information",
                () -> assertTrue(returnedList.size() == 1),
                () -> assertEquals(returnedList.get(0).getName(), oStock.getName()),
                () -> assertEquals(returnedList.get(0).getNumberOwned(), oStock.getNumberOwned()),
                () -> assertEquals(returnedList.get(0).getStock().getTicker(), oStock.getStock().getTicker())
        );
    }

    @Test
    void getValueOfHoldingTest(){
        I_Portfolio portfolio = new Portfolio("Name");
        Stock teslaStock = new Stock("TSLA");
        portfolio.addStock("TSLA", "Tesla", 10);
        double referenceValue = teslaStock.getPrice() * 10;
        assertEquals(referenceValue, portfolio.getValueOfHolding("TSLA"));
    }

    @Test
    void setNameTest(){
        I_Portfolio portfolio = new Portfolio("Name");
        portfolio.setName("NewName");
        assertEquals(portfolio.getName(), "NewName");
    }

}

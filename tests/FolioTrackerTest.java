

import Model.FolioTracker;
import Model.I_FolioTracker;
import Model.I_Portfolio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FolioTrackerTest {

    @Test
    void addPortfolioTest(){
        I_FolioTracker folio = new FolioTracker();
        assertAll("Returns false if the portfolio could not be created as it already exists, returns true if the portfolio was created",
                () -> assertTrue(folio.addPortfolio("PortfolioOne")),
                () -> assertFalse(folio.addPortfolio("PortfolioOne"))
        );
    }

    @Test
    void removePortfolioTest(){
        String name = "PortfolioOne";
        I_FolioTracker folio = new FolioTracker();
        assertFalse(folio.removePortfolio(name));
        folio.addPortfolio(name);
        assertTrue(folio.removePortfolio(name));
    }

    @Test
    void getPortfolioTest(){
        String name = "PortfolioOne";
        I_FolioTracker folio = new FolioTracker();
        folio.addPortfolio(name);
        assertEquals(folio.getPortfolio(name).getName(), name);
        assertNull(folio.getPortfolio("Name"));
    }

    @Test
    void getPortfoliosTest(){
        String name = "PortfolioOne";
        I_FolioTracker folio = new FolioTracker();
        folio.addPortfolio(name);
        folio.addPortfolio("SecondPortfolio");
        I_Portfolio port1 = (I_Portfolio)folio.getPortfolios().get(0);
        I_Portfolio port2 = (I_Portfolio)folio.getPortfolios().get(1);
        assertAll("",
                () -> assertTrue(folio.getPortfolios().size() == 2),
                () -> assertEquals(port1.getName(), name),
                () -> assertEquals(port2.getName(), "SecondPortfolio")
                );
    }

    @Test
    void addStocksToPortfolioTest(){
        String name = "PortfolioOne";
        I_FolioTracker folio = new FolioTracker();
        folio.addPortfolio(name);
        assertAll("Returns true if stock could be added, returns false if it could not be added",
                () -> assertTrue(folio.addStocksToPortfolio(name, "TSLA", "Tesla", 10)),
                () -> assertFalse(folio.addStocksToPortfolio("Non-existent", "TSLA", "Tesla", 10)),
                () -> assertFalse(folio.addStocksToPortfolio(name, "TSLA", "Tesla", 10))
                );
    }

    @Test
    void editSharesPortfolioTest(){
        String name = "PortfolioOne";
        I_FolioTracker folio = new FolioTracker();
        folio.addPortfolio(name);
        folio.addStocksToPortfolio(name, "TSLA", "Tesla", 10);
        assertAll("",
                ()-> assertTrue(folio.editSharesPortfolio(name, "Tesla", 20)),
                ()-> assertFalse(folio.editSharesPortfolio(name, "asdf", 20))
        );
    }

    @Test
    void deleteStockTest(){
        String name = "PortfolioOne";
        I_FolioTracker folio = new FolioTracker();
        folio.addPortfolio(name);
        folio.addStocksToPortfolio(name, "TSLA", "Tesla", 10);
        assertAll("",
                () -> assertTrue(folio.deleteStock(name, "TSLA")),
                () -> assertFalse(folio.deleteStock(name, "TSLA"))
                );
    }

    @Test
    void getFolioData(){
        String name = "PortfolioOne";
        I_FolioTracker folio = new FolioTracker();
        folio.addPortfolio(name);
        assertTrue(folio.getFolioData(name).length ==  0);
        folio.addStocksToPortfolio(name, "TSLA", "Tesla", 10);
        assertTrue(folio.getFolioData(name).length !=  0);
    }


}

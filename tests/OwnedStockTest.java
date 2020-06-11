
import Model.I_OwnedStock;
import Model.OwnedStock;
import Model.Stock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OwnedStockTest {

    @Test
    void getNameTest(){
        Stock teslaStock = new Stock("TSLA");
        I_OwnedStock oStock = new OwnedStock(teslaStock, "Tesla", 10);
        oStock.setName("Name");
        assertEquals("Name", oStock.getName());
    }

    @Test
    void getNumberOwnedTest(){
        Stock teslaStock = new Stock("TSLA");
        I_OwnedStock oStock = new OwnedStock(teslaStock, "Tesla", 10);
        assertEquals(10, oStock.getNumberOwned());
    }

    @Test
    void setNumberOwnedTest(){
        Stock teslaStock = new Stock("TSLA");
        I_OwnedStock oStock = new OwnedStock(teslaStock, "Tesla", 10);
        oStock.setNumberOwned(20);
        assertEquals(20, oStock.getNumberOwned());
    }

    @Test
    void GetInitialValueTest(){
        Stock teslaStock = new Stock("TSLA");
        I_OwnedStock oStock = new OwnedStock(teslaStock, "Tesla", 10);
       // oStock.setInitialValue(20.0);
        assertEquals(10.0, oStock.getInitialPrice());
    }

    @Test
    void getStockTest(){
        Stock teslaStock = new Stock("TSLA");
        I_OwnedStock oStock = new OwnedStock(teslaStock, "Tesla", 10);
        assertEquals(teslaStock, oStock.getStock());
    }
}

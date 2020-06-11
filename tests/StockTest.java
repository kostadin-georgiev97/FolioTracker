
import Model.I_Stock;
import Model.Stock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StockTest {

    @Test
    void getTickerTest(){
        String name = "TSLA";
        I_Stock stock = new Stock(name);
        assertEquals(stock.getTicker(), name);
    }

    @Test
    void getPriceTest(){
        String name = "TSLA";
        I_Stock stock = new Stock(name);
        assertNotNull(stock.getPrice());
    }
}

package model;

public class OwnedStock implements I_OwnedStock {

    private Stock stock;
    private String name;
    private int numberOwned;
    private double initialPriceBought;

    public OwnedStock(Stock s, String n, int no){
        stock = s;
        name = n;
        numberOwned = no;
        initialPriceBought = stock.getPrice();
    }

    public Stock getStock() {
        return stock;
    }

    public String getName(){
        return name;
    }

    public int getNumberOwned() {
        return numberOwned;
    }

    public double getInitialPrice() {
        return initialPriceBought;
    }

    public void setNumberOwned(int n){
        numberOwned = n;
    }

    public void setName(String n){
        name = n;
    }

    public int gains(){
        if (initialPriceBought == stock.getPrice()){
            return 0;
        } else if (initialPriceBought < stock.getPrice()){
            return 1;
        } else {
            return -1;
        }
    }
}

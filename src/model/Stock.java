package model;

public class Stock implements I_Stock {

    private String ticker;
    private double price;

    public Stock (String t){
        ticker = t;
        updatePrice();
    }

    public String getTicker(){
        return ticker;
    }

    public double getPrice(){
        return price;
    }

    public void updatePrice(){
        StrathQuoteServer sqs = new StrathQuoteServer();
        String val = "";
        String name = "";
        try {
            val = sqs.getLastValue(ticker);
        } catch (NoSuchTickerException e){
            System.out.println("no such ticker exeption");
        } catch (WebsiteDataException e){
            System.out.println("website data exeption");
        }
        price = Double.valueOf(val);
    }

}

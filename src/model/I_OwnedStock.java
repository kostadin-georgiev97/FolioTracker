package model;

public interface I_OwnedStock<T extends I_Stock> {

    T getStock();

    String getName();

    int getNumberOwned();

    double getInitialPrice();

    void setNumberOwned(int n);

    void setName(String n);

    int gains();
}

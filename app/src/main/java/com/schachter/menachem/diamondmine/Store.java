package com.schachter.menachem.diamondmine;

/**
 * Created by Menachem Schachter on 9/11/2016.
 */
public class Store {

    Hero hero;

    public Store(Hero hero) {
        this.hero = hero;
    }

    public int getPrice(Suit suit, boolean isBonus) {
        throw new RuntimeException("Method not implemented");
    }

    public void buy(Suit suit, boolean isBonus) {
        throw new RuntimeException("Method not implemented");
    }
}

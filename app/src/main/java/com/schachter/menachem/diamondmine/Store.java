package com.schachter.menachem.diamondmine;

import java.util.HashMap;
import com.schachter.menachem.diamondmine.Suit.*;
/**
 * Created by Menachem Schachter on 9/11/2016.
 */
public class Store {

    Hero hero;

    public Store(Hero hero) {
        this.hero = hero;
    }

    public int getPrice(Suit suit, boolean isBonus) {
        if (!isNextAvaliable(suit, isBonus))
            throw new IllegalStateException("No item available");
        if (isBonus)
            return bonusItemList[suit.ordinal()][next(suit, isBonus)];
        else
            return basicItemList[suit.ordinal()][next(suit, isBonus)];
    }

    public int next(Suit suit, boolean isBonus) {
        switch(suit) {
            case CLUBS:
                if (isBonus)
                    return hero.getAttackBonus() + 1;
                else
                    return hero.getMaxAttack() + 1;
            case HEARTS:
                if (isBonus)
                    return hero.getHealthBonus() + 1;
                else
                    return hero.getMaxHealth() + 1;
            case DIAMONDS:
                if (isBonus)
                    return hero.getTreasureBonus() + 1;
            default:
                throw new IllegalArgumentException();
        }
    }

    public boolean isNextAvaliable(Suit suit, boolean isBonus) {
        int next = next(suit, isBonus);
        if (isBonus)
            return next <= 3;
        else
            return next <= 10;
    }

    public void buy(Suit suit, boolean isBonus) {
        int price = getPrice(suit, isBonus);
        if (price > hero.getDiamonds())
            throw new IllegalStateException("Hero does not have enough diamond");
        hero.setDiamonds(hero.getDiamonds() - price);
        switch(suit) {
            case CLUBS:
                if (isBonus)
                    hero.setAttackBonus(hero.getAttackBonus() + 1);
                else
                    hero.setMaxAttack(hero.getMaxAttack() + 1);
                break;
            case HEARTS:
                if (isBonus)
                    hero.setHealthBonus(hero.getHealthBonus() + 1);
                else
                    hero.setMaxHealth(hero.getMaxHealth() + 1);
                break;
            case DIAMONDS:
                if (isBonus) {
                    hero.setTreasureBonus(hero.getTreasureBonus() + 1);
                    break;
                }
            default:
                throw new IllegalArgumentException();
        }
    }

    static final int[] basicClubCost = {-1, -1, -1, 3, 4, 5, 6, 7, 8, 9, 10};
    static final int[] basicHeartCost = {-1, -1, -1, 3, 4, 5, 6, 7, 8, 9, 10};
    static final int[] bonusClubCost = {0, 1, 2, 3};
    static final int[] bonusHeartCost = {0, 1, 2, 3};
    static final int[] bonusDiamondCost = {0, 1, 2, 3};

    static int[][] basicItemList = new int[4][];
    static int[][] bonusItemList = new int[4][];

    static {
        basicItemList[Suit.CLUBS.ordinal()] = basicClubCost;
        basicItemList[Suit.HEARTS.ordinal()] = basicHeartCost;
        bonusItemList[Suit.CLUBS.ordinal()] = bonusClubCost;
        bonusItemList[Suit.HEARTS.ordinal()] = bonusHeartCost;
        bonusItemList[Suit.DIAMONDS.ordinal()] = bonusDiamondCost;

    }


}

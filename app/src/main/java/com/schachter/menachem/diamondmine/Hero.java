package com.schachter.menachem.diamondmine;

import android.content.Context;

/**
 * Created by Menachem Schachter  on 9/8/2016.
 */
public class Hero {

    int health = 2;
    int maxHealth = 2;
    int minAttack = 1;
    int maxAttack = 2;
    int attackBonus = 0;
    int maxLevel = 1;
    int diamonds = 3;
    int healthBonus = 0;
    int treasureBonus = 0;

    static final int BASE_HEAL = 1;

    public void takeDamage() {
        if (getHealth() == 0)
            throw new IllegalStateException("Hero already has 0 hit points");
        else
            setHealth(getHealth() - 1);
    }

    public void heal() {
        int heal = BASE_HEAL + getHealthBonus();
        setHealth(Math.min(getHealth() + heal, getMaxHealth()));
    }

    // Increase the hero's diamonds by the amount won from the level
    public void plunder(Level level) {
        int baseDiamonds = level.getTreasure();
        int bonusDiamonds = Math.min(level.getMaxTreasureBonus(), getTreasureBonus());
        setDiamonds(getDiamonds() + baseDiamonds + bonusDiamonds);
    }

    public int getHealth() {return health;}

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {return maxHealth;}

    public void setMaxHealth(int maxHealth) {this.maxHealth = maxHealth;}

    public int getMinAttack() {
        return minAttack;
    }

    public void setMinAttack(int minAttack) {
        this.minAttack = minAttack;
    }

    public int getMaxAttack() {
        return maxAttack;
    }

    public void setMaxAttack(int maxAttack) {
        this.maxAttack = maxAttack;
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    public void setAttackBonus(int attackBonus) {
        this.attackBonus = attackBonus;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }

    public int getHealthBonus() {
        return healthBonus;
    }

    public void setHealthBonus(int healthBonus) {
        this.healthBonus = healthBonus;
    }

    public int getTreasureBonus() {
        return treasureBonus;
    }

    public void setTreasureBonus(int treasureBonus) {
        this.treasureBonus = treasureBonus;
    }

    public String toString() {
        return String.format("HERO - Health: %d, Attack: %d-%d, Level: %d, Diamonds:%d",
                getHealth(), getMinAttack(), getMaxAttack(), getMaxLevel(), getDiamonds());
    }

}

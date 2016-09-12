package com.schachter.menachem.diamondmine;

import android.content.Context;

/**
 * Created by Menachem Schachter  on 9/8/2016.
 */
public class Level {
    private int enemyMinAttack;
    private int enemyMaxAttack;
    private int enemyBonusAttack;
    private int enemyMaxHealth;
    private int treasure;
    private int maxTreasureBonus;

    private int currentHealth;

    public Level(int enemyMinAttack, int enemyMaxAttack, int enemyBonusAttack, int enemyMaxHealth, int treasure, int maxTreasureBonus) {
        this.enemyMinAttack = enemyMinAttack;
        this.enemyMaxAttack = enemyMaxAttack;
        this.enemyBonusAttack = enemyBonusAttack;
        this.enemyMaxHealth = enemyMaxHealth;
        this.treasure = treasure;
        this.maxTreasureBonus = maxTreasureBonus;
        this.currentHealth = enemyMaxHealth;
    }

    public void damageEnemy() {
        if (getCurrentHealth() == 0)
            throw new IllegalStateException("Enemy already has 0 hit points");
        else
            setCurrentHealth(getCurrentHealth() - 1);
    }

    public int getEnemyMinAttack() {
        return enemyMinAttack;
    }

    public int getEnemyMaxAttack() {
        return enemyMaxAttack;
    }

    public int getEnemyBonusAttack() {
        return enemyBonusAttack;
    }

    public int getEnemyMaxHealth() {
        return enemyMaxHealth;
    }

    public int getTreasure() {
        return treasure;
    }

    public int getMaxTreasureBonus() {
        return maxTreasureBonus;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
}

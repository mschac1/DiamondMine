package com.schachter.menachem.diamondmine;

import android.util.Pair;

import java.util.Random;

/**
 * Created by Menachem Schachter on 9/8/2016.
 */
public class Game {
    Hero hero = new Hero();
    Random rng = new Random();
    int currentLevel = 0;
    int turnCount = 1;
    private Store store = null;

    public Game() {
        newGame();
    }

    public void newGame() {
        hero = new Hero();
        currentLevel = 0;
        turnCount = 1;
        store = new Store(hero);
    }

    public int getTurnCount() {return turnCount;}

    protected void setTurnCount(int turnCount) {this.turnCount = turnCount;}

    public int getCurrentLevel() {return currentLevel;}

    protected void setCurrentLevel(int currentLevel) {this.currentLevel = currentLevel;}

    // Returns true if the fight is over, false if it is not
    public Pair<Integer, Integer> fight(int levelNum) {
        if (getCurrentLevel() !=0 && getCurrentLevel() != levelNum)
            throw new IllegalStateException("Hero is already in battle at another level");
        if (levelNum <= 0 || levelNum > getMaxLevel())
            throw new IllegalStateException("The level number entered is invalid");
        if (levelNum > hero.getMaxLevel())
            throw new IllegalStateException("The hero cannot access this level yet");

        Level level = getLevels()[levelNum];

        // New fight
        if (getCurrentLevel() == 0) {
            setCurrentLevel(levelNum);
            // Refresh the enemy's hit points
            level.setCurrentHealth(level.getEnemyMaxHealth());
        }
        if (level.getCurrentHealth() == 0)
            throw new IllegalStateException("Enemy already has 0 hit points");

        int heroAttack = getAttack(hero.getMinAttack(), hero.getMaxAttack(), hero.getAttackBonus());
        int enemyAttack = getAttack(level.getEnemyMinAttack(), level.getEnemyMaxAttack(), level.getEnemyBonusAttack());

        if (heroAttack <= enemyAttack)
            hero.takeDamage();
        if (heroAttack >= enemyAttack)
            level.damageEnemy();

        // If the fight is over
        if (level.getCurrentHealth() == 0 || hero.getHealth() == 0) {
            // The hero can only plunder the diamonds if he has 1 or more health left
            if (hero.getHealth() > 0) {
                hero.plunder(level);
                hero.heal();
            }
            // If enemy was defeated
            if (level.getCurrentHealth() == 0) {
                if (levelNum == hero.getMaxLevel())
                    hero.setMaxLevel(levelNum + 1);
            }

            // Indicate that the fight is over
            setCurrentLevel(0);
            setTurnCount(getTurnCount() + 1);
        }

        return new Pair<>(heroAttack, enemyAttack);
    }

    public void rest() {
        if (hero.getHealth() != hero.getMaxHealth()) {
            hero.heal();
            setTurnCount(getTurnCount() + 1);
        }
        setCurrentLevel(0);
    }

    public int getCurrentHealth() {
        return hero.getHealth();
    }

    public int getPrice(Suit suit, boolean isBonus) {
        return store.getPrice(suit, isBonus);
    }

    public void buy(Suit suit, boolean isBonus) {
        if (getCurrentLevel() != 0)
            throw new IllegalStateException("Cannot buy while in battle");
        store.buy(suit, isBonus);
    }


    public int nextInStore(Suit suit, boolean isBonus) {return store.next(suit, isBonus);
    }
    protected static final int BONUS_ATTACK_BASE = 11;

    /* This method returns a random attack value selected from the joint set of the values
       between minAttack and maxAttack and also the possible bonus attack values
     */
    protected int getAttack(int minAttack, int maxAttack, int attackBonus) {
        int n = rng.nextInt(maxAttack - minAttack + 1 + attackBonus);
        if (n < attackBonus)
            return BONUS_ATTACK_BASE + n;
        else
            return n - attackBonus + minAttack;
    }

    private int getMaxLevel() {
        return getLevels().length + 1;
    }

    public static final int BASE_MIN_ATTACK = 1;
    public static final int BASE_MAX_ATTACK = 2;
    public static final int BASE_HEALTH = 1;

    private final static Level[] levels = {null,
            new Level(BASE_MIN_ATTACK,     BASE_MAX_ATTACK,     0, BASE_HEALTH, 1, 0),
            new Level(BASE_MIN_ATTACK,     BASE_MAX_ATTACK + 1, 0, BASE_HEALTH, 2, 1),
            new Level(BASE_MIN_ATTACK,     BASE_MAX_ATTACK + 2, 0, BASE_HEALTH, 3, 1),
            new Level(BASE_MIN_ATTACK,     BASE_MAX_ATTACK + 3, 0, BASE_HEALTH + 1, 4, 2),
            new Level(BASE_MIN_ATTACK + 1, BASE_MAX_ATTACK + 4, 1, BASE_HEALTH + 1, 5, 2),
            new Level(BASE_MIN_ATTACK + 1, BASE_MAX_ATTACK + 5, 1, BASE_HEALTH + 1, 6, 2),
            new Level(BASE_MIN_ATTACK + 1, BASE_MAX_ATTACK + 6, 1, BASE_HEALTH + 2, 7, 3),
            new Level(BASE_MIN_ATTACK + 2, BASE_MAX_ATTACK + 7, 2, BASE_HEALTH + 2, 8, 3),
            new Level(BASE_MIN_ATTACK + 2, BASE_MAX_ATTACK + 8, 2, BASE_HEALTH + 2, 9, 3),
            new Level(BASE_MIN_ATTACK + 3, BASE_MAX_ATTACK + 8, 3, BASE_HEALTH + 3, 10, 3),
    };

    public static Level[] getLevels() {
        return levels;
    }


    public boolean isNextAvaliable(Suit suit, boolean isBonus) {
        return store.isNextAvaliable(suit, isBonus);
    }
}

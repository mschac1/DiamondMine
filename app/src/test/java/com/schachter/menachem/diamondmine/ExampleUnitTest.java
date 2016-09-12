package com.schachter.menachem.diamondmine;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    static Game testGame = new Game();
    @Test
    public void getAttack_isCorrect1() throws Exception {
        println("Testing getAttack");
        assertEquals(testGame.getAttack(1,1,0), 1);

        int minAttack = 3;
        int maxAttack  = 7;
        int attackBonus = 3;
        int[] attackValues = new int[Game.BONUS_ATTACK_BASE + attackBonus];
        for (int i = 0; i < 100; i++) {

            int attack = testGame.getAttack(minAttack,maxAttack,attackBonus);
            attackValues[attack]++;
        }
        for (int i = 0; i < minAttack; i++) {
            assertTrue(attackValues[i] == 0);
        }
        for (int i = minAttack; i <= maxAttack; i++) {
            assertTrue(attackValues[i] > 0);
        }
        for (int i = maxAttack + 1; i < Game.BONUS_ATTACK_BASE; i++) {
            assertTrue(attackValues[i] == 0);
        }
        for (int i = Game.BONUS_ATTACK_BASE; i < Game.BONUS_ATTACK_BASE + attackBonus; i++) {
            assertTrue(attackValues[i] > 0);
        }
    }
    @Test
    public void testHeroPrint() {
        System.out.println(testGame.hero);
    }

    // TODO Tests:
    // wrong_level_error, damage_0_hp_enemy_error
    public void println(String s) {System.out.println(s);}
}
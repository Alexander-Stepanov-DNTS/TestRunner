package nic.stepanov.probl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DichotomyCalculatorTest {
    private DichotomyCalculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = new DichotomyCalculator();
    }

    @Test
    public void testInitialSetup() {
        calculator.initialSetup(1.0, 10.0, 0.3);

        assertEquals(1.0, calculator.getA(), 0.0001);
        assertEquals(10.0, calculator.getB(), 0.0001);
    }

    @Test
    public void testPerformDichotomy() {
        calculator.initialSetup(1.0, 10.0, 0.1);
        calculator.performDichotomy();

        assertEquals(5.45, calculator.getX1(), 0.1);
        assertEquals(5.55, calculator.getX2(), 0.1);
    }

    @Test
    public void testUpdateBoundaryA() {
        calculator.initialSetup(1.0, 10.0, 0.1);
        calculator.performDichotomy();

        System.out.println("f(x1) = " + calculator.getX1());
        System.out.println("f(x2) = " + calculator.getX2());

        calculator.updateBoundaryA();

        if (calculator.getX1() > calculator.getX2()) {
            assertTrue(calculator.getA() > 1.0 && calculator.getA() <= calculator.getX1());
        } else {
            assertEquals(1.0, calculator.getA(), 0.0001);
        }
    }

    @Test
    public void testUpdateBoundaryB() {
        calculator.initialSetup(1.0, 10.0, 0.1);
        calculator.performDichotomy();
        calculator.updateBoundaryB();

        assertTrue(calculator.getB() < 10.0 && calculator.getB() >= calculator.getX2());
    }

    @Test
    public void testCheckConvergence() {
        calculator.initialSetup(1.0, 10.0, 0.1);

        while (!calculator.checkConvergence()) {
            calculator.performDichotomy();
            calculator.updateBoundaryA();
            calculator.updateBoundaryB();
        }

        assertTrue(calculator.checkConvergence());
    }
}
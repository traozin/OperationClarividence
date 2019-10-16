package datamining.model;

import java.math.BigDecimal;
import java.math.MathContext;


public class Dice implements Algorithms {

    @Override
    public double calculate(int a, int b, int c, int d) {
        BigDecimal dividend = new BigDecimal(2*a);
        BigDecimal one = new BigDecimal(1);
        BigDecimal divisor = new BigDecimal((2*a) + b + c);
        return one.subtract(dividend.divide(divisor, MathContext.DECIMAL64)).doubleValue();
    }

}


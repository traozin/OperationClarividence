package datamining.model;

import java.math.BigDecimal;
import java.math.MathContext;

public class Jaccard implements Algorithms {

    @Override
    public double calculate(int a, int b, int c, int d) {
        BigDecimal dividend = new BigDecimal(a);
        BigDecimal one = new BigDecimal(1);
        BigDecimal divisor = new BigDecimal(a + b + c);
        return dividend.divide(divisor, MathContext.DECIMAL64).doubleValue();
    }

}


package datamining.model;

import java.math.BigDecimal;
import java.math.MathContext;


public class SMC implements Algorithms {

    @Override
    public double calculate(int a, int b, int c, int d) {
        BigDecimal dividend = new BigDecimal(a + d);
        BigDecimal divisor = new BigDecimal(a + b + c + d);
        BigDecimal one = new BigDecimal(1);
        return one.subtract(dividend.divide(divisor, MathContext.DECIMAL64)).doubleValue();
    }

}

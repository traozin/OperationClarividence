package datamining.model;

public class ContigenceTable {

    private Integer a;
    private Integer b;
    private Integer c;
    private Integer d;

    public ContigenceTable() {
        this(0, 0, 0, 0);
    }
    /**
     * Tabela de contigência para utilização dos calculos nos algorítimos
     * 
     * @param a
     * @param b
     * @param c
     * @param d 
     */
    public ContigenceTable(int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public void addA() {
        this.a++;
    }

    public void addB() {
        this.b++;
    }

    public void addC() {
        this.c++;
    }

    public void addD() {
        this.d++;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public int getD() {
        return d;
    }

}

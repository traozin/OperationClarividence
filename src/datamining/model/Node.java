
package datamining.model;

import java.util.Objects;
import weka.core.Instance;

public class Node implements Comparable<Node> {

    private Instance toCompare;
    private double score;

    public Node(Instance toCompare, double score) {
        this.toCompare = toCompare;
        this.score = score;
    }

    public Instance getToCompare() {
        return toCompare;
    }

    public void setToCompare(Instance toCompare) {
        this.toCompare = toCompare;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(toCompare);
        hash = 89 * hash + (int) (Double.doubleToLongBits(score) ^ (Double.doubleToLongBits(score) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Instance) {
            if (obj.equals(toCompare)) {
                return true;
            } else {
                return false;
            }
        } else {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Node other = (Node) obj;
            if (Double.doubleToLongBits(score) != Double.doubleToLongBits(other.score)) {
                return false;
            }
            if (!Objects.equals(toCompare, other.toCompare)) {
                return false;
            }
            return true;
        }

    }

    @Override
    public int compareTo(Node t) {
        return Double.compare(t.getScore(), getScore());
    }

}

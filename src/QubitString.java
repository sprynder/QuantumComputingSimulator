import java.util.*;

public class QubitString {
    private Qubit[] bits;
    Complex probability;

    public Qubit[] getBits() {
        return bits;
    }

    public void setBits(Qubit[] bits) {
        this.bits = bits;
    }

    public Complex getProbability() {
        return probability;
    }

    public void setProbability(Complex probability) {
        this.probability = probability;
    }

  
    public QubitString(int n, Complex prob) {
        bits = new Qubit[n];
        probability = prob;
        for (int i = 0; i < n; i++) {
            bits[i] = new Qubit(0);
        }
    }

    public QubitString(Qubit[] update, Complex prob) {
        bits = update;
        probability = prob;
    }

    public String toString() {

        String ret = "";
        for (int i = bits.length - 1; i >= 0; i--) {
            ret += bits[i].getVal();
        }
        // ret+= ": Probability of - "+probability;
        return ret;
    }

    public boolean equals(Object o) {
        QubitString wow = (QubitString) o;
        boolean same = true;
        for (int i = 0; i < bits.length; i++) {
            if (bits[i].getVal() != wow.getBits()[i].getVal()) {
                same = false;
            }
        }
        if (same) {
            return true;
        } else {
            return false;
        }
    }

}

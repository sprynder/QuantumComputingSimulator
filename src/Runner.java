import java.io.*;
import java.util.*;

public class Runner {
    private static ArrayList <QubitString> bstring;
    private static ArrayList <Gate> instructions;
    private static int bits;
    private static int readBits;

    public static void main(String[] args) throws IOException {
        bstring = new ArrayList <QubitString>();
        instructions = new ArrayList <Gate>();
        read();
    }

    public static void read() throws FileNotFoundException {
        // fix reading in the file
        
         File jarFile = new File(
         Runner.class.getProtectionDomain().getCodeSource().getLocation().
         getPath()); Scanner sc = new Scanner(new
         File(jarFile.getParentFile(), "test.qasm"));
         
         //Scanner sc = new Scanner(new File("src/test.qasm"));
        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            String[] interpret = str.split(" ");
            if (interpret[0].equals("qreg")) {
                bits = Integer.parseInt("" + interpret[1].charAt(2));
                bstring.add(new QubitString(bits, new Complex(1, 0)));
            } else if (interpret[0].equals("creg")) {
                readBits = Integer.parseInt("" + interpret[1].charAt(2));
            } else if (!(interpret[0].equals("OPENQASM") || interpret[0].equals("include")
                    || interpret[0].equals("") || interpret[0].equals("barrier"))) {
                instructions.add(new Gate(interpret));
            }
        }
        operate();
        printFinal();
        Scanner scin = new Scanner(System.in);
        System.out.println("Enter a number of shots: ");
        int shots = scin.nextInt();
        double[][] prange = new double[bstring.size()][2];
        prange[0][0] = 0;
        prange[0][1] = bstring.get(0).getProbability().real() * 100000;
        for (int i = 1; i < bstring.size(); i++) {
            prange[i][0] = prange[i - 1][1];
            prange[i][1] = prange[i][0] + bstring.get(i).getProbability().real() * 100000;
        }
        int[] counts = new int[bstring.size()];
        for (int i = 0; i < shots; i++) {
            double random = Math.random() * 100000;
            for (int j = 0; j < bstring.size(); j++) {
                if (random <= prange[j][1] && random >= prange[j][0]) {
                    counts[j]++;
                    break;
                }
            }
        }
        for (int i = 0; i < counts.length; i++) {
            System.out.println(bstring.get(i) + ": " + counts[i]);
        }
    }

    public static void operate() {
        for (int i = 0; i < instructions.size(); i++) {
            Gate op = instructions.get(i);
            // System.out.println(op);
            for (int j = bstring.size() - 1; j >= 0; j--) {
                QubitString cur = bstring.remove(j);
                ArrayList <QubitString> toUpdate = op.apply(cur);
                for (QubitString m : toUpdate) {
                    bstring.add(m);
                }
            }
            calculateFinal();
            // System.out.println("UPDATED: " + bstring);
        }
    }

    public static void printFinal() {
        calculateFinal();
        System.out.println("Final State");
        {
            System.out
                    .print("(" + bstring.get(0).getProbability() + ") |" + bstring.get(0) + ">");
            for (int i = 1; i < bstring.size(); i++) {
                System.out.print(
                        " + (" + bstring.get(i).getProbability() + ") |" + bstring.get(i) + ">");
            }
        }
        System.out.println("\nFinal Probabilities");
        for (int i = 0; i < bstring.size(); i++) {
            System.out.println(bstring.get(i) + ": Probability of = "
                    + findprob(bstring.get(i).getProbability()));

            // sets probability
            bstring.get(i).setProbability(findprob(bstring.get(i).getProbability()));
        }
    }

    public static void calculateFinal() {
        ArrayList <QubitString> sumBits = new ArrayList <QubitString>();
        for (int i = 0; i < bstring.size(); i++) {
            if (!sumBits.contains(bstring.get(i))) {
                for (int j = i + 1; j < bstring.size(); j++) {
                    if (bstring.get(i).equals(bstring.get(j))) {
                        bstring.get(i).setProbability(bstring.get(i).getProbability()
                                .plus(bstring.get(j).getProbability()));
                    }
                }
                sumBits.add(bstring.get(i));
            }
        }
        bstring = sumBits;
    }

    public static Complex findprob(Complex mat) {
        Complex tot = mat.times(mat.conj());
        return tot;
    }
}
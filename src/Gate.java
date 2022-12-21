import java.util.*;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Gate {
    private Complex[][] matrix = new Complex[2][2];
    String op;
    int q1;
    int q2;
    int q3;

    public Gate(String[] interp) {
        op = interp[0];
        if (op.equals("cx") || op.equals("swap")) {
            String[] valp = interp[1].split(",");
            // System.out.println(valp[0]+" "+valp[1]);
            q1 = Integer.parseInt(valp[0].charAt(2) + "");
            q2 = Integer.parseInt(valp[1].charAt(2) + "");
            q3 = -1;
        } else if (op.equals("ccx")) {
            String[] valp = interp[1].split(",");
            // System.out.println(valp[0]+" "+valp[1]);
            q1 = Integer.parseInt(valp[0].charAt(2) + "");
            q2 = Integer.parseInt(valp[1].charAt(2) + "");
            q3 = Integer.parseInt(valp[2].charAt(2) + "");
        } else {
            q2 = -1;
            q1 = Integer.parseInt(interp[1].charAt(2) + "");
            if (op.equals("z")) {
                matrix[0][0] = new Complex(1, 0);
                matrix[0][1] = new Complex(0, 0);
                matrix[1][0] = new Complex(0, 0);
                matrix[1][1] = new Complex(-1, 0);
            } else if (op.equals("x")) {
                matrix[0][0] = new Complex(0, 0);
                matrix[0][1] = new Complex(1, 0);
                matrix[1][0] = new Complex(1, 0);
                matrix[1][1] = new Complex(0, 0);
            }
            // start here
            else if (op.equals("h")) {
                // done
                matrix[0][0] = new Complex(1 / (Math.pow(2, 0.5)), 0);
                matrix[0][1] = new Complex(1 / (Math.pow(2, 0.5)), 0);
                matrix[1][0] = new Complex(1 / (Math.pow(2, 0.5)), 0);
                matrix[1][1] = new Complex(-1 / (Math.pow(2, 0.5)), 0);
            } else if (op.equals("id")) {
                // done
                matrix[0][0] = new Complex(1, 0);
                matrix[0][1] = new Complex(0, 0);
                matrix[1][0] = new Complex(0, 0);
                matrix[1][1] = new Complex(1, 0);
            } else if (op.equals("t")) {// done
                matrix[0][0] = new Complex(1, 0);
                matrix[0][1] = new Complex(0, 0);
                matrix[1][0] = new Complex(0, 0);
                matrix[1][1] = new Complex(1 / (Math.pow(2, 0.5)), 1 / (Math.pow(2, 0.5)));
            } else if (op.equals("s")) {
                // done
                matrix[0][0] = new Complex(1, 0);
                matrix[0][1] = new Complex(0, 0);
                matrix[1][0] = new Complex(0, 0);
                matrix[1][1] = new Complex(0, 1);
            } else if (op.equals("tdg")) {
                matrix[0][0] = new Complex(1, 0);
                matrix[0][1] = new Complex(0, 0);
                matrix[1][0] = new Complex(0, 0);
                matrix[1][1] = new Complex(-1 * Math.pow(2, 0.5) / 2, Math.pow(2, 0.5) / 2);
            } else if (op.equals("sdg")) {

                // done
                matrix[0][0] = new Complex(1, 0);
                matrix[0][1] = new Complex(0, 0);
                matrix[1][0] = new Complex(0, 0);
                matrix[1][1] = new Complex(0, -1);
            } else if (op.equals("y")) {
                // done
                matrix[0][0] = new Complex(0, 0);
                matrix[0][1] = new Complex(0, -1);
                matrix[1][0] = new Complex(0, 1);
                matrix[1][1] = new Complex(0, 0);
            } else if ((op.charAt(0) + "").equals("u")) {
                String sub = op.substring(2, op.length() - 1);
                String[] wow = sub.split(",");
                double theta, lambda, phi;
                // System.out.println(wow[0]+" "+wow[1]+" "+wow[2]);
                Expression thetas = new ExpressionBuilder(wow[0]).build();
                theta = thetas.evaluate();
                Expression phis = new ExpressionBuilder(wow[1]).build();
                phi = phis.evaluate();
                Expression lambdas = new ExpressionBuilder(wow[2]).build();
                lambda = lambdas.evaluate();
                matrix[0][0] = new Complex(Math.cos(theta / 2), 0);
                matrix[0][1] = new Complex(Math.sin(theta / 2) * -1 * Math.cos(lambda),
                        Math.sin(theta / 2) * -1 * Math.sin(lambda));

                matrix[1][0] = new Complex(Math.sin(theta / 2) * Math.cos(phi),
                        Math.sin(theta / 2) * Math.sin(phi));
                matrix[1][1] = new Complex(Math.cos(theta / 2) * Math.cos(phi + lambda),
                        Math.cos(theta / 2) * Math.sin(phi + lambda));
                // System.out.println(theta);
            } else if ((op.charAt(0) + "").equals("p")) {
                String sub = op.substring(2, op.length() - 1);
                double theta;

                // System.out.println(sub);
                Expression thetas = new ExpressionBuilder(sub).build();
                theta = thetas.evaluate();
                matrix[0][0] = new Complex(1, 0);
                matrix[0][1] = new Complex(0, 0);
                matrix[1][0] = new Complex(0, 0);
                matrix[1][1] = new Complex(Math.cos(theta), Math.sin(theta));
            } else if ((op.charAt(0) + "" + op.charAt(1) + "").equals("rz")) {
                String sub = op.substring(3, op.length() - 1);
                double theta;

                // System.out.println(sub);
                Expression thetas = new ExpressionBuilder(sub).build();
                theta = thetas.evaluate();
                matrix[0][0] = new Complex(Math.cos(2 * Math.PI - theta / 2),
                        Math.sin(2 * Math.PI - theta / 2));
                matrix[0][1] = new Complex(0, 0);
                matrix[1][0] = new Complex(0, 0);
                matrix[1][1] = new Complex(Math.cos(theta / 2), Math.sin(theta / 2));

            }
            else if (op.equals("sx"))
            {
                matrix[0][0] = new Complex(0.5, 0.5);
                matrix[0][1] = new Complex(0.5, -0.5);
                matrix[1][0] = new Complex(0.5, -0.5);
                matrix[1][1] = new Complex(0.5, 0.5);
            }
            else if (op.equals("sxdg"))
            {
                matrix[0][0] = new Complex(0.5, -0.5);
                matrix[0][1] = new Complex(0.5, 0.5);
                matrix[1][0] = new Complex(0.5,0.5);
                matrix[1][1] = new Complex(0.5, -0.5);
            }
            else if ((op.charAt(0) + "" + op.charAt(1) + "").equals("rx")) {
                String sub = op.substring(3, op.length() - 1);
                double theta;

                // System.out.println(sub);
                Expression thetas = new ExpressionBuilder(sub).build();
                theta = thetas.evaluate();
                matrix[0][0] = new Complex(Math.cos(theta/2),0);
                matrix[0][1] = new Complex(0, -1*Math.sin(theta/2));
                matrix[1][0] = new Complex(0, -1*Math.sin(theta/2));
                matrix[1][1] = new Complex(Math.cos(theta/2),0);

            }
            else if ((op.charAt(0) + "" + op.charAt(1) + "").equals("ry")) {
                String sub = op.substring(3, op.length() - 1);
                double theta;

                // System.out.println(sub);
                Expression thetas = new ExpressionBuilder(sub).build();
                theta = thetas.evaluate();
                matrix[0][0] = new Complex(Math.cos(theta/2),0);
                matrix[0][1] = new Complex(-1*Math.sin(theta/2), 0);
                matrix[1][0] = new Complex(Math.sin(theta/2), 0);
                matrix[1][1] = new Complex(Math.cos(theta/2),0);

            }
        }
    }

    public ArrayList <QubitString> apply(QubitString cur) {
        ArrayList <QubitString> updated = new ArrayList <QubitString>();
        // ex [0 0 0]
        // ex X
        Complex[][] val = new Complex[2][1];
        if (cur.getBits()[q1].getVal() == 0) {
            val[0][0] = new Complex(1, 0);
            val[1][0] = new Complex(0, 0);
        } else {
            val[0][0] = new Complex(0, 0);
            val[1][0] = new Complex(1, 0);
        }
        Complex newProb = new Complex(cur.getProbability().real(),cur.getProbability().imag());
        QubitString curs = new QubitString(cur.getBits().length, newProb);
        for (int i = 0; i < cur.getBits().length; i++) {
            curs.getBits()[i].setVal(cur.getBits()[i].getVal());
        }
        if (op.equals("cx")) {
            if (curs.getBits()[q1].getVal() == 1) {
                curs.getBits()[q2].setVal(Math.abs(curs.getBits()[q2].getVal() - 1));
            }
            updated.add(curs);
        } else if (op.equals("swap")) {
            int temp = curs.getBits()[q2].getVal();
            curs.getBits()[q2].setVal(Math.abs(curs.getBits()[q1].getVal()));
            curs.getBits()[q1].setVal(Math.abs(temp));
            updated.add(curs);
        } else if (op.equals(("ccx"))) {
            if (curs.getBits()[q1].getVal() == 1 && curs.getBits()[q2].getVal() == 1) {
                curs.getBits()[q3].setVal(Math.abs(curs.getBits()[q3].getVal() - 1));
            }
            updated.add(curs);
        } else {
            Complex[][] total = multiply(matrix, val);
            Complex proba = total[0][0];// findproba(total);
            Complex probb = total[1][0];// findprobb(total);

            if (proba.real() != 0 || proba.imag() != 0) {
                Qubit[] wow = new Qubit[curs.getBits().length];
      
                for (int i = 0; i < wow.length; i++) {
                    wow[i] = new Qubit(0);
                    if (i != q1) {
                        wow[i].setVal(curs.getBits()[i].getVal());
                    } else {
                        wow[i] = new Qubit(0);
                    }
                }
                updated.add(new QubitString(wow, proba.times(curs.getProbability())));
            }
            if (probb.real() != 0 || probb.imag() != 0) {
                Qubit[] wow = new Qubit[curs.getBits().length];
                for (int i = 0; i < wow.length; i++) {
                    wow[i] = new Qubit(0);
                    if (i != q1) {
                        wow[i].setVal(curs.getBits()[i].getVal());
                    } else {
                        wow[i] = new Qubit(1);
                    }
                }
                updated.add(new QubitString(wow, probb.times(curs.getProbability())));
            }

        }
        return updated;
    }

    public Complex[][] multiply(Complex[][] one, Complex[][] two) {
        Complex[][] total = new Complex[2][1];
        // System.out.println(one[0][0] + " " + one[0][1]);
        // System.out.println(one[1][0] + " " + one[1][1]);
        // System.out.println(two[0][0] + " "+ two[1][0]);
        total[0][0] = (one[0][0].times(two[0][0])).plus(one[0][1].times(two[1][0]));
        total[1][0] = (one[1][0].times(two[0][0])).plus(one[1][1].times(two[1][0]));

        // System.out.println(total[0][0]);
        // System.out.println(total[1][0]);
        return total;
    }

    public String toString() {
        String str = op + " " + q1 + " " + q2;

        return str;
    }

    public double findproba(Complex[][] mat) {
        Complex tot = mat[0][0].times(mat[0][0].conj());
        return tot.real();
    }

    public double findprobb(Complex[][] mat) {
        Complex tot = mat[1][0].times(mat[1][0].conj());
        return tot.real();
    }

    public Complex[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(Complex[][] matrix) {
        this.matrix = matrix;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public int getQ1() {
        return q1;
    }

    public void setQ1(int q1) {
        this.q1 = q1;
    }

    public int getQ2() {
        return q2;
    }

    public void setQ2(int q2) {
        this.q2 = q2;
    }
}

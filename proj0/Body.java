import java.lang.Math;

public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public static final double G = 6.67e-11;

    /** constructor 1 */
    public Body(double xP, double yP, double xV, double yV, 
                double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;

    }
    /** constructor 2 */
    public Body(Body b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;

    }


    public double calcDistance(Body b) {
        return Math.sqrt(Math.pow(b.xxPos - this.xxPos, 2) + Math.pow(b.yyPos - this.yyPos, 2));
    }

    public double calcForceExertedBy(Body b) {
        return G * this.mass * b.mass / Math.pow(this.calcDistance(b), 2);
    }

    public double calcForceExertedByX(Body b) {
        double dx = b.xxPos - this.xxPos;
        return calcForceExertedBy(b) * dx / calcDistance(b);
    }

    public double calcForceExertedByY(Body b) {
        double dy = b.yyPos - this.yyPos;
        return calcForceExertedBy(b) * dy / calcDistance(b);
    }

    public double calcNetForceExertedByX(Body[] allBodys) {
        double total = 0;
        for (Body b: allBodys) {
            if (!this.equals(b)) {
                total = total + this.calcForceExertedByX(b);
            }
        }
        return total;   
    }

    public double calcNetForceExertedByY(Body[] allBodys) {
        double total = 0;
        for (Body b: allBodys) {
            if (!this.equals(b)) {
                total = total + this.calcForceExertedByY(b);
            }
        }
        return total;
    }

}
public class NBody{

    /** read radius
     *  learn to use In class
     */
    public static double readRadius(String path) {
        In in = new In(path);
        int num_of_planets = in.readInt();
        double radius = in.readDouble();
        return radius;
    }
    /**return an array of the bodys in txtfile */
    public static Body[] readBodies(String path) {
        In in = new In(path);
        int num_of_planets = in.readInt();
        double radius = in.readDouble();
        Body[] body_array = new Body[5]; // need to use new to init!
        for (int i = 0; i < 5; i++) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            body_array[i] = new Body(xP, yP, xV, yV, m, img); //remember to check the index
        }
        return body_array;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Body[] body_array = readBodies(filename);

        

    }
}


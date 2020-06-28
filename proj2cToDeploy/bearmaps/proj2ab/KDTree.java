package bearmaps.proj2ab;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import java.util.Comparator;
import java.util.List;

public class KDTree implements PointSet {

    private Node root;
    public KDTree(List<Point> points) {
        root = null;
        for (Point p : points) {
            root = this.add(p);
        }
    }

    private static class Node {
        private final Point point;
        private Node left;
        private Node right;
        private boolean flag; //true for compareX, false for compareXY

        private Node(Point p) {
            point = p;
            flag = true; //默认true，这样创建第一个节点的时候就没问题，false也行
        }
    }

    /** KDTree insertion */
    public Node add(Point p) {
        return addHelper(p, root);
    }

    /** KDTree insertion */
    private Node addHelper(Point p, Node root) {
        if (root == null) {
            return new Node(p);
        }
        if (p.equals(root.point)) {
            return root;
        }
//        Comparator<Point> compareX = Comparator.comparingDouble(Point::getX); //lambda写法，这里面不太有用
//        int cmp = compareX.compare(p, root.point);
        boolean addToLeft = root.flag && Double.compare(p.getX(), root.point.getX()) < 0; //compareX and p is to the left of root
        boolean addToDown = !root.flag && Double.compare(p.getY(), root.point.getY()) < 0; //compareY and p is under the root
        if (addToLeft || addToDown) {
            root.left = addHelper(p, root.left);
            root.left.flag = !root.flag; //想了半天这样写就行了,子节点flag和父节点相反
        } else {
            root.right = addHelper(p, root.right);
            root.right.flag = !root.flag;
        }
        return root;
    }


    /** KDTree nearest */
    @Override
    public Point nearest(double x, double y) {
        Point target = new Point(x, y);
        Point best = root.point;
        return nearest(root, target, best);
    }

    /** KDTree nearest */
    private Point nearest(Node root, Point target, Point best) {
        if (root == null) {
            return best;
        }
        if (Point.distance(root.point, target) < Point.distance(best, target)) {
            best = root.point;
        }

        Node goodSide, badSide; //需要先创建，不然编译的时候它认为下面的if可能不能到达，然后后面就说goodSide没定义？
        boolean compareX = root.flag && Double.compare(target.getX(), root.point.getX()) < 0;
        boolean compareY = !root.flag && Double.compare(target.getY(), root.point.getY()) < 0;
        if (compareX || compareY) {
            goodSide = root.left;
            badSide = root.right;
        } else {
            goodSide = root.right;
            badSide = root.left;
        }

        best = nearest(goodSide, target, best);

        //根据实际情况看需不需要检查badSide
        boolean checkBadSideX = root.flag && Math.pow(target.getX() - root.point.getX(), 2) < Point.distance(best, target);
        boolean checkBadSideY = !root.flag && Math.pow(target.getY() - root.point.getY(), 2) < Point.distance(best, target);
        if (checkBadSideX || checkBadSideY) {
            best = nearest(badSide, target, best);
        }
        return best;
    }





}

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] allLineSegs;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Null argument");
        }


        ArrayList<LineSegment> segs = new ArrayList<>();
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null || points[i + 1] == null || points[i] == points[i + 1]) {
                throw new IllegalArgumentException("Duplicated points or null elements");
            }

        }

        for (int i = 0; i < pointsCopy.length - 3; i++) {
            for (int j = i + 1; j < pointsCopy.length - 2; j++) {
                for (int k = j + 1; k < pointsCopy.length - 1; k++) {
                    for (int m = k + 1; m < pointsCopy.length; m++) {
                        if (pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i].slopeTo(pointsCopy[k]) && pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i].slopeTo(pointsCopy[m])) {
                            LineSegment ls = new LineSegment(pointsCopy[i], pointsCopy[m]);
                            if (!segs.contains(ls))
                                segs.add(ls);
                        }
                    }
                }
            }
        }
        allLineSegs = segs.toArray(new LineSegment[segs.size()]);
    }


    public int numberOfSegments() {
        return allLineSegs.length;
    }

    public LineSegment[] segments() {
        return allLineSegs;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);      // input file
        int n = in.readInt();         // n-by-n percolation system
        Point[] points = new Point[n];
        BruteCollinearPoints b;
        int k = 0;
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            Point p = new Point(i, j);
            points[k] = p;
            k++;
        }
        b = new BruteCollinearPoints(points);
        LineSegment[] ls = b.segments();
        System.out.println(b.numberOfSegments());
        for (int i = 0; i < b.numberOfSegments(); i++) {
            System.out.println(ls[i]);
            ls[i].draw();
        }
    }
}

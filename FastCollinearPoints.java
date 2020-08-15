import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {


    private final LineSegment[] allLineSegs;


    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Null argument");
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == null || points[i] == points[j]) {
                    throw new IllegalArgumentException("Duplicated points or null elements");
                }
            }
        }


        Point[] pointsBySlope = Arrays.copyOf(points, points.length);
        Point[] pointsByValue = Arrays.copyOf(points, points.length);
        ArrayList<LineSegment> segs = new ArrayList<LineSegment>();
        Arrays.sort(pointsByValue);
        for (int i = 0; i < pointsByValue.length; ++i) {
            Point origin = pointsByValue[i];
            Arrays.sort(pointsBySlope);
            Arrays.sort(pointsBySlope, origin.slopeOrder());
            int ct = 1;
            Point beginning = null;
            for (int j = 0; j < pointsBySlope.length - 1; j++) {
                if (pointsBySlope[j].slopeTo(origin) == pointsBySlope[j + 1].slopeTo(origin)) {
                    ct++;
                    if (ct == 2) {
                        beginning = pointsBySlope[j];
                        ct++;
                    } else if (ct >= 4 && j + 1 == pointsBySlope.length - 1) {
                        if (beginning.compareTo(origin) > 0) {
                            segs.add(new LineSegment(origin, pointsBySlope[j + 1]));
                        }
                        ct = 1;
                    }
                } else if (ct >= 4) {
                    if (beginning.compareTo(origin) > 0) {
                        segs.add(new LineSegment(origin, pointsBySlope[j]));
                    }
                    ct = 1;
                } else {
                    ct = 1;
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
        FastCollinearPoints b;
        int k = 0;
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            Point p = new Point(i, j);
            points[k] = p;
            k++;
        }
        b = new FastCollinearPoints(points);
        LineSegment[] ls = b.segments();
        System.out.println(b.numberOfSegments());
        for (int i = 0; i < b.numberOfSegments(); i++) {
            System.out.println(ls[i]);
            ls[i].draw();
        }
    }
}

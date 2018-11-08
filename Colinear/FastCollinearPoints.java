/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        NullCheckPoints(points);

        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);

        CheckDuplicatePoints(sortedPoints);

        for (int orginIndex = 0; orginIndex < sortedPoints.length; orginIndex++) {
            Point orgin = sortedPoints[orginIndex];

            Point[] bySlope = sortedPoints.clone();
            Arrays.sort(bySlope, orgin.slopeOrder());

            double previousSlope = Double.NaN;

            LinkedList<Point> collinear = new LinkedList<Point>();
            for(int i = 0; i < bySlope.length; i++) {
                Point p = bySlope[i];

                double currentSlope = p.slopeTo(orgin);
                if (currentSlope != previousSlope) {
                    if (collinear.size() >= 3) {
                        collinear.add(orgin);
                        Collections.sort(collinear);
                        if (orgin.compareTo(collinear.getFirst()) == 0) {
                            segments.add(new LineSegment(orgin, collinear.peekLast()));
                        }
                    }
                    collinear.clear();
                }
                collinear.add(p);

                if (i == bySlope.length - 1) {
                    if (collinear.size() >= 3) {
                        collinear.add(orgin);
                        Collections.sort(collinear);
                        if (orgin.compareTo(collinear.getFirst()) == 0) {
                            segments.add(new LineSegment(orgin, collinear.peekLast()));
                        }
                    }
                }

                previousSlope = currentSlope;
            }
        }

    }

    private void CheckDuplicatePoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) throw new IllegalArgumentException();
        }
    }

    private void NullCheckPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

}

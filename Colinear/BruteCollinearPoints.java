/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {

        nullCheckPoints(points);
        checkDuplicatePoints(points);

        for (int i = 0; i < points.length; i++) {
            Point orgin = points[i];

            for (int j = 0; j < points.length; j++) {
                if (j == i) continue;

                double firstSlope = orgin.slopeTo(points[j]);

                for (int k = 0; k < points.length; k++) {
                    if (k == i || k == j) continue;

                    if (orgin.slopeTo(points[k]) == firstSlope) {

                        for (int t = 0; t < points.length; t++) {
                            if (t == k || t == j || t == i) continue;

                            if (orgin.slopeTo(points[t]) == firstSlope) {

                                LinkedList<Point> linePoints = new LinkedList<>();
                                linePoints.add(points[i]);
                                linePoints.add(points[j]);
                                linePoints.add(points[k]);
                                linePoints.add(points[t]);

                                if (areSorted(linePoints, Point::compareTo)) {
                                    segments.add(new LineSegment(points[i], points[t]));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean areSorted(List<Point> points, Comparator<Point> comparator) {
        for (int i = 1; i < points.size(); i++) {
            if (comparator.compare(points.get(i - 1), points.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }

    private void checkDuplicatePoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) throw new IllegalArgumentException();
        }
    }

    private void nullCheckPoints(Point[] points) {
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


/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {

        Point[] pointsClone = points.clone();
        Arrays.sort(pointsClone);

        nullCheckPoints(pointsClone);
        checkDuplicatePoints(pointsClone);

        for (int i = 0; i < pointsClone.length; i++) {
            Point orgin = pointsClone[i];

            for (int j = 0; j < pointsClone.length; j++) {
                if (j == i) continue;

                double firstSlope = orgin.slopeTo(pointsClone[j]);

                for (int k = 0; k < pointsClone.length; k++) {
                    if (k == i || k == j) continue;

                    if (orgin.slopeTo(pointsClone[k]) == firstSlope) {

                        for (int t = 0; t < pointsClone.length; t++) {
                            if (t == k || t == j || t == i) continue;

                            if (orgin.slopeTo(pointsClone[t]) == firstSlope) {

                                LinkedList<Point> linePoints = new LinkedList<>();
                                linePoints.add(pointsClone[i]);
                                linePoints.add(pointsClone[j]);
                                linePoints.add(pointsClone[k]);
                                linePoints.add(pointsClone[t]);

                                if (areSorted(linePoints, Point::compareTo)) {
                                    segments.add(new LineSegment(pointsClone[i], pointsClone[t]));
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


/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int numberOfImputs = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        for (int i = 0; i < numberOfImputs; i++) {
            String element = StdIn.readString();
            queue.enqueue(element);
        }
        for (String s: queue) {
            StdOut.println(s);
        }
    }
}

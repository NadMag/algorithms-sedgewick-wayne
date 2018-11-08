/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int numberToOutput = Integer.parseInt(args[0]);

        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        int inputsRead = 0;

        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            inputsRead++;

            if (inputsRead <= numberToOutput) {
                rq.enqueue(input);
            }
            else if (StdRandom.uniform(0.0, 1.0) >= (1.0 / inputsRead)) {
                rq.dequeue();
                rq.enqueue(input);
            }
        }

        Iterator<String> it = rq.iterator();

        for (int i = 0; i < numberToOutput; i++) {
            StdOut.println(it.next());
        }
    }
}

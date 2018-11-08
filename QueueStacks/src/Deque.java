/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size = 0;

    public Deque() {

    }

    public boolean isEmpty() { return size == 0; }

    public int size() { return size; }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node newFirst = new Node(item);

        if (first == null) {
            last = newFirst;
        }
        else {
            Node prevFirst = first;
            newFirst.Next = prevFirst;
            prevFirst.Previous = newFirst;
        }

        first = newFirst;
        size++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node newLast = new Node(item);

        if (last == null) {
            first = newLast;
        }
        else {
            Node prevLast = last;
            newLast.Previous = prevLast;
            prevLast.Next = newLast;
        }

        last = newLast;
        size++;
    }

    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }

        Node prevFirst = first;
        Node newFirst = prevFirst.Next;

        if (newFirst != null) {
            newFirst.Previous = null;
        }

        first = newFirst;

        size--;

        if (size == 0) {
            last = null;
        }

        return prevFirst.Value;
    }

    public Item removeLast()  {
        if (last == null) {
            throw new NoSuchElementException();
        }

        Node prevLast = last;
        Node newLast = last.Previous;

        if (newLast != null) {
            newLast.Next = null;
        }

        last = newLast;
        size--;

        if (size == 0) {
            first = null;
        }

        return prevLast.Value;
    }

    public Iterator<Item> iterator() {
        return new LinkedListIterator();
    }

    private class Node {
        public Node Next;
        public Node Previous;
        public Item Value;

        public Node(Item value) {
            Value = value;
            Next = null;
            Previous = null;
        }
    }

    private class LinkedListIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() { return current != null; }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = current.Value;
            current = current.Next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
}

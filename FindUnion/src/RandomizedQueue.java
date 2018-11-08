/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private ResizingArray<Item> itemList;

    public RandomizedQueue()
    {
        itemList = new ResizingArray<Item>();

    }
    public boolean isEmpty() { return  itemList.size() == 0; }
    public int size() { return  itemList.size(); }
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        itemList.add(item);
    }
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        int randIndex = StdRandom.uniform(0, size());
        Item randItem = itemList.getElementAt(randIndex);
        Item lastElement = itemList.pop();
        itemList.setElementAt(randIndex, lastElement);

        return randItem;
    }
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        int randIndex = StdRandom.uniform(0, size());
        Item randItem = itemList.getElementAt(randIndex);
        return randItem;
    }
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private int currentIndex;
        private int[] randomIndexsOrder;
        
        public RandomizedIterator() {
            randomIndexsOrder = new int[size()];

            for (int i = 0; i < size(); i++) {
                randomIndexsOrder[i] = i;
            }
            for (int i = size() - 1; i >= 0; i++) {
                int randIndex = StdRandom.uniform(i + 1);
                randomIndexsOrder[randIndex] = i;
                randomIndexsOrder[i] = randIndex;
            }
        }

        @Override
        public boolean hasNext() {
            return currentIndex < randomIndexsOrder.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            int previousIndex = currentIndex;
            currentIndex++;
            return itemList.getElementAt(previousIndex);
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
}

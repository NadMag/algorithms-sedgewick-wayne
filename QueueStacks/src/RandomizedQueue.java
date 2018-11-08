/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private final ResizingArray<Item> itemList;

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
        Item random = itemList.getElementAt(randIndex);
        Item last = itemList.getElementAt(size() -1);
        itemList.setElementAt(randIndex, last);
        itemList.setElementAt(size() -1, random);

        return itemList.pop();
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
        private final int[] randomIndexsOrder;
        
        public RandomizedIterator() {
            randomIndexsOrder = new int[size()];

            for (int i = 0; i < size(); i++) {
                randomIndexsOrder[i] = i;
            }
            for (int i = 0; i < size(); i++) {
                int randIndex = StdRandom.uniform(i + 1);
                int temp  = randomIndexsOrder[randIndex];
                randomIndexsOrder[randIndex] = randomIndexsOrder[i];
                randomIndexsOrder[i] = temp;
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
            throw new UnsupportedOperationException();
        }
    }

    class ResizingArray<T> {

    private int numberOfElements;
    private T[] storageArray = (T[])new Object[2];

    public T getElementAt(int index) {
        return storageArray[index];
    }

    public T setElementAt(int index, T value) {
        T previousValue = storageArray[index];
        storageArray[index] = value;

        return previousValue;
    }

    public ResizingArray<T> add(T element) {

        storageArray[numberOfElements] = element;
        numberOfElements++;

        if (numberOfElements >= storageArray.length) {
            storageArray = cloneResize(numberOfElements * 2);
        }

        return this;
    }

    public T pop() {

        if (size() == 0) throw new NoSuchElementException();

        T lastItem = storageArray[numberOfElements -1];
        numberOfElements--;

        if (numberOfElements == 0) {
            storageArray = cloneResize(2);
        }

        else if (numberOfElements == storageArray.length / 4) {
            storageArray = cloneResize(numberOfElements * 2);
        }

        return lastItem;
    }

    public int size() { return numberOfElements; }

    private T[] cloneResize(int size) {
        T[] resizedArray = (T[]) new Object[size];

        for (int i = 0; i < size(); i++) {
            resizedArray[i] = storageArray[i];
        }
        return resizedArray;
    }

    }
}

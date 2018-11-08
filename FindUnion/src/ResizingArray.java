/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class ResizingArray<T> {

    private int numberOfElements;
    private T[] storageArray;

    public T getElementAt(int index) {
        return storageArray[index];
    }

    public T setElementAt(int index, T value) {
        T previousValue = storageArray[index];
        storageArray[index] = value;

        return previousValue;
    }

    public ResizingArray<T> add(T element) {

        if (numberOfElements + 1 == storageArray.length) {
            storageArray = cloneResize(numberOfElements * 2);
        }

        storageArray[numberOfElements] = element;
        numberOfElements++;

        return this;
    }

    public T pop() {
        T lastItem = storageArray[numberOfElements -1];
        numberOfElements--;

        if (numberOfElements <= storageArray.length / 4) {
            storageArray = cloneResize(numberOfElements * 2);
        }

        return lastItem;
    }

    public int size() { return numberOfElements; }

    private T[] cloneResize(int size) {
        T[] doubleArray = (T[]) new Object[size];
        for (int i = 0; i < storageArray.length; i++) {
            doubleArray[i] = storageArray[i];
        }
        return doubleArray;
    }
}

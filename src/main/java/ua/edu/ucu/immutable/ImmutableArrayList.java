package ua.edu.ucu.immutable;

import java.util.Arrays;

public final class ImmutableArrayList implements ImmutableList {

    private final Object[] array;

    public ImmutableArrayList() {
        array = new Object[0];
    }

    public ImmutableArrayList(Object[] e) {
        array = e.clone();
    }

    @Override
    public ImmutableArrayList add(Object e) {
        return add(array.length, new Object[]{e});
    }

    @Override
    public ImmutableArrayList add(int index, Object e) {
        return addAll(index, new Object[]{e});
    }

    @Override
    public ImmutableArrayList addAll(Object[] c) {
        return addAll(array.length, c);
    }

    @Override
    public ImmutableArrayList addAll(int index, Object[] c) {
        Object[] newArray = new Object[size()+c.length];
        for (int i = 0; i < index; i++) {
            newArray[i] = array[i];
        }
        for (int i = 0; i < c.length; i++) {
            newArray[i + index] = c[i];
        }
        for (int i = index + c.length; i < newArray.length; i++) {
            newArray[i] = array[i - c.length];
        }
        return new ImmutableArrayList(newArray);
    }

    @Override
    public Object get(int index) {
        indexCheck(index);
        return array[index];
    }

    @Override
    public ImmutableArrayList remove(int index) {
        indexCheck(index);
        Object[] newArray = new Object[size() - 1];
        for (int i = 0; i < index; i++) {
            newArray[i] = array[i];
        }
        for (int i = index; i < newArray.length; i++) {
            newArray[i] = array[i + 1];
        }
        return new ImmutableArrayList(newArray);
    }

    @Override
    public ImmutableArrayList set(int index, Object e) {
        indexCheck(index);
        Object[] newArray = toArray();
        newArray[index] = e;
        return new ImmutableArrayList(newArray);
    }

    @Override
    public int indexOf(Object e) {
        for (int i = 0; i < size(); i++) {
            if (array[i] == e) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public ImmutableArrayList clear() {
        return new ImmutableArrayList();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Object[] toArray() {
        return array.clone();
    }

    @Override
    public String toString() {
        return  Arrays.toString(this.toArray());
    }

    private void indexCheck(int index) {
        if (index < 0 || index > size() - 1) {
            throw new IndexOutOfBoundsException();
        }
    }
}

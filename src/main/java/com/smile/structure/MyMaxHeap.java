package com.smile.structure;

import java.util.Arrays;

/**
 * @author LiuXiBin
 * @since 2023/8/23 13:36
 */
public class MyMaxHeap {

    private static final int DEFAULT_INITIAL_CAPACITY = 11;

    private Integer[] heap;

    private int size = 0;

    public MyMaxHeap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public MyMaxHeap(int length) {
        if (length < 1) {
            throw new IllegalArgumentException();
        }
        this.heap = new Integer[length];
    }

    public void push(int element) {
        if (this.size >= this.heap.length) {
            grow(this.heap.length + 10);
        }
        up(size++, element);
    }

    private void grow(int minLength) {
        this.heap = Arrays.copyOf(this.heap, minLength);
    }

    private void up(int index, int element) {
        while (index > 0) {
            int parentIndex = (index - 1) >>> 1;
            int parentElement = this.heap[parentIndex];
            if (element <= parentElement) {
                break;
            }
            this.heap[index] = parentElement;
            index = parentIndex;
        }
        this.heap[index] = element;
    }

    public Integer pop() {
        if (size == 0) {
            return null;
        }
        // 堆顶数据
        int topElement = this.heap[0];
        // 堆底数据
        int bottomElement = this.heap[--size];
        this.heap[size] = null;
        down(0, bottomElement);
        return topElement;
    }

    private void down(int index, int element) {
        int half = size >>> 1;
        while (index < half) {
            int childIndex = (index << 1) + 1;
            int child = this.heap[childIndex];
            int rightIndex = childIndex + 1;
            if (rightIndex < size && child < this.heap[rightIndex]) {
                childIndex = rightIndex;
                child = this.heap[childIndex];
            }
            if (child <= element) {
                break;
            }
            this.heap[index] = child;
            index = childIndex;
        }
        this.heap[index] = element;
    }

}

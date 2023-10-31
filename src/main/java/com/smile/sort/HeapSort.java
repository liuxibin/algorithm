package com.smile.sort;

/**
 * 堆排序
 * @author LiuXiBin
 * @since 2023/8/30 9:34
 */
public class HeapSort {

    private void sort(int[] arr) {
        // 建堆
        for (int i = arr.length - 1; i >= 0; i--) {
            down(arr, i, arr.length);
        }
        // 出堆
        int size = arr.length;
        while (size > 0) {
            swap(arr, 0, --size);
            down(arr, 0, size);
        }
    }

    private void up(int[] arr, int index) {
        while (index > 0) {
            // 头结点
            int headIndex = (index - 1) >>> 1;
            if (arr[index] >= arr[headIndex]) {
                break;
            }
            swap(arr, index, headIndex);
            index = headIndex;
        }
    }

    private void down(int[] arr, int index, int size) {
        int leftIndex = (index << 1) + 1;
        while (leftIndex < size) {
            int rightIndex = leftIndex + 1;

            int minIndex = rightIndex < size && arr[leftIndex] > arr[rightIndex] ? rightIndex : leftIndex;
            if (arr[minIndex] <= arr[index]) {
                break;
            }
            swap(arr, index, minIndex);
            index = minIndex;
            leftIndex = (index << 1) + 1;
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}

package com.smile.sort;

import com.smile.utils.ArrayUtils;

import java.util.Arrays;

/**
 * @author LiuXiBin
 * @since 2023/7/5 13:47
 */
public class QuickSort {

    private static void swap(int[] arr, int i, int k) {
        int temp = arr[i];
        arr[i] = arr[k];
        arr[k] = temp;
    }

    private static int partition(int[] arr, int left, int right) {
        if (left == right) {
            return left;
        }
        int index = left;
        int maxIndex = left;
        while (index < right) {
            if (arr[index] <= arr[right]) {
                swap(arr, index, maxIndex++);
            }
            index++;
        }
        swap(arr, maxIndex, right);
        return maxIndex;
    }

    private static void process(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int middle = partition(arr, left, right);
        process(arr, left, middle - 1);
        process(arr, middle + 1, right);
    }

    private static void quickSort(int[] arr) {
        process(arr, 0, arr.length - 1);
    }

    private static int[] partition2(int[] arr, int left, int right) {
        if (left == right) {
            return new int[]{ left, right };
        }
        int index = left;
        int minIndex = left;
        int maxIndex = right - 1;
        while (index <= maxIndex) {
            if (arr[index] > arr[right]) {
                swap(arr, index, maxIndex--);
            } else if (arr[index] < arr[right]) {
                swap(arr, index++, minIndex++);
            } else {
                index++;
            }
        }
        swap(arr, ++maxIndex, right);
        return new int[]{ minIndex, maxIndex };
    }

    private static void process2(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int[] indexArr = partition2(arr, left, right);
        process2(arr, left, indexArr[0] - 1);
        process2(arr, indexArr[1] + 1, right);
    }

    private static void quickSort2(int[] arr) {
        process2(arr, 0, arr.length - 1);
    }

    private static void comparator(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    swap(arr, i, j);
                }
            }
        }
    }

    public static void main(String[] args) {
        int testTimes = 100000;
        int maxValue = 100;
        int maxSize = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = ArrayUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = ArrayUtils.copyArray(arr1);
            int[] arr3 = ArrayUtils.copyArray(arr1);

            quickSort(arr1);
            quickSort2(arr2);
            comparator(arr3);

            if (!ArrayUtils.isEqual(arr1, arr2) || !ArrayUtils.isEqual(arr1, arr3)) {
                System.out.println(Arrays.toString(arr1));
                System.out.println(Arrays.toString(arr2));
                System.out.println(Arrays.toString(arr3));
                break;
            }
        }
        System.out.println("测试结束");
    }

}

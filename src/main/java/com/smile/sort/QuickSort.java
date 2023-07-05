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

    private static void quickSort(int[] arr) {
        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int middle = partition(arr, left, right);
        process(arr, left, middle - 1);
        process(arr, middle + 1, right);
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

            quickSort(arr1);
            comparator(arr2);

            if (!ArrayUtils.isEqual(arr1, arr2)) {
                System.out.println(Arrays.toString(arr1));
                System.out.println(Arrays.toString(arr2));
                break;
            }
        }
        System.out.println("测试结束");
    }

}

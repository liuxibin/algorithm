package com.smile.sort;

import java.util.Arrays;

/**
 * @author LiuXiBin
 * @since 2023/6/27 14:41
 */
public class MergeSort {

    private static void mergeSort1(int[] arr) {
        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) {
            return;
        }
        // 中点
        int middleIndex = leftIndex + ((rightIndex - leftIndex) >> 1);
        process(arr, leftIndex, middleIndex);
        process(arr, middleIndex + 1, rightIndex);
        merge(arr, leftIndex, middleIndex, rightIndex);
    }

    private static void merge(int[] arr, int leftIndex, int middleIndex, int rightIndex) {
        int[] help = new int[rightIndex - leftIndex + 1];
        int p1 = leftIndex;
        int p2 = middleIndex + 1;

        for (int i = 0;p1 <= middleIndex || p2 <= rightIndex; i++) {
            if (p1 > middleIndex) {
                help[i] = arr[p2++];
            } else if (p2 > rightIndex) {
                help[i] = arr[p1++];
            } else {
                help[i] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
            }
        }
        System.arraycopy(help, 0, arr, leftIndex, help.length);
    }

    private static void mergeSort2(int[] arr) {
        int step = 1;
        while (step <= arr.length) {
            int leftIndex = 0;
            while (leftIndex < arr.length) {
                if (arr.length - leftIndex <= step) {
                    // 不足两个分组 结束本次步长
                    break;
                }
                int middleIndex  = leftIndex + step - 1;
                int rightIndex = Math.min(arr.length - 1, middleIndex + step);
                merge(arr, leftIndex, middleIndex, rightIndex);
                leftIndex = rightIndex + 1;
            }
            step <<= 1;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{3, 3, 6, 12, 4, 7, 2, 1, 9, 11};
        // mergeSort1(arr);
        mergeSort2(arr);
        System.out.println(Arrays.toString(arr));

    }


}

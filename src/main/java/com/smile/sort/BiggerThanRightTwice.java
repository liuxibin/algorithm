package com.smile.sort;

import com.smile.utils.ArrayUtils;

import java.util.Arrays;

/**
 * 在一个数组中，
 * 对于每个数num，求有多少个后面的数 * 2 依然<num，求总个数
 * @author LiuXiBin
 * @since 2023/6/29 15:44
 */
public class BiggerThanRightTwice {

    private static int getBiggerThanRightTwiceNumber(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }
        int middle = left + ((right - left) >> 1);
        return process(arr, left, middle) + process(arr, middle + 1, right) + merge(arr, left, middle, right);
    }

    private static int merge(int[] arr, int left, int middle, int right) {
        int p1 = left;
        int p2 = middle + 1;
        int ans = 0;
        while (p1 <= middle) {
            while (p2 <= right && arr[p1] > arr[p2] * 2) {
                p2++;
            }
            ans += p2 - middle - 1;
            p1++;
        }

        p1 = left;
        p2 = middle + 1;
        int[] help = new int[right - left + 1];

        for (int i = 0; i < help.length; i++) {
            if (p1 > middle) {
                help[i] = arr[p2++];
            } else if (p2 > right) {
                help[i] = arr[p1++];
            } else {
                help[i] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
            }
        }

        for (int i = 0; i < help.length; i++) {
            arr[left + i] = help[i];
        }
        return ans;

    }

    private static int comparator(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int k = i + 1; k < arr.length; k++) {
                if (arr[i] > arr[k] * 2) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = ArrayUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = ArrayUtils.copyArray(arr1);
            int number1 = getBiggerThanRightTwiceNumber(arr1);
            int number2 = comparator(arr2);
            if (number1 != number2) {
                System.out.println("Oops!");
                System.out.println(number1);
                System.out.println(Arrays.toString(arr1));
                System.out.println(number2);
                System.out.println(Arrays.toString(arr2));
                break;
            }
        }
        System.out.println("测试结束");
    }

}

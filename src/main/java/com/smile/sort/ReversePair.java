package com.smile.sort;

import java.util.Arrays;

/**
 * 在一个数组中，
 * 任何一个前面的数a，和任何一个后面的数b，
 * 如果(a,b)是降序的，就称为逆序对
 * 返回数组中所有的逆序对
 * @author LiuXiBin
 * @since 2023/6/29 13:44
 */
public class ReversePair {

    private static int getReversePairNumber(int[] arr) {
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
        int p1 = middle;
        int p2 = right;
        int[] help = new int[right - left + 1];
        int ans = 0;
        for (int i = help.length - 1; i >= 0; i--) {
            if (p1 < left) {
                help[i] = arr[p2--];
            } else if (p2 < middle + 1) {
                help[i] = arr[p1--];
            } else {
                ans += arr[p1] > arr[p2] ? p2 - middle : 0;
                help[i] = arr[p1] > arr[p2] ? arr[p1--] : arr[p2--];
            }
        }
        System.arraycopy(help, 0, arr, left, help.length);
        return ans;
    }

    private static int comparator(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int k = i + 1; k < arr.length; k++) {
                if (arr[i] > arr[k]) {
                    ans++;
                }
            }
        }
        return ans;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (getReversePairNumber(arr1) != comparator(arr2)) {
                System.out.println("Oops!");
                System.out.println(Arrays.toString(arr1));
                System.out.println(Arrays.toString(arr2));
                break;
            }
        }
        System.out.println("测试结束");
    }


}

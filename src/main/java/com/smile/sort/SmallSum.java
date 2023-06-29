package com.smile.sort;

/**
 * 在一个数组中，一个数左边比它小的数的总和，叫数的小和，所有数的小和累加起来，叫数组小和。求数组小和。
 * @author LiuXiBin
 * @since 2023/6/28 13:26
 */
public class SmallSum {

    private static int getSmallSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length -1);
    }

    private static int process(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }
        int middle = left + ((right - left) >> 1);
        return process(arr, left, middle) + process(arr, middle+1, right) + merge(arr, left, middle, right);
    }

    private static int merge(int[] arr, int left, int middle, int right) {
        int p1 = left;
        int p2 = middle + 1;
        int[] help = new int[right - left + 1];
        int ans = 0;
        for (int i = 0; i < help.length; i++) {
            if (p1 > middle) {
                help[i] = arr[p2++];
            } else if (p2 > right) {
                help[i] = arr[p1++];
            } else {
                ans += arr[p1] < arr[p2] ? (right - p2 + 1) * arr[p1] : 0;
                help[i] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
            }
        }
        System.arraycopy(help, 0, arr, left, help.length);
        return ans;
    }

    private static int comparator(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int res = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                res += arr[j] < arr[i] ? arr[j] : 0;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{3, 3, 6, 12, 4, 7, 2, 1, 9, 11};
        int smallSum = getSmallSum(arr);
        int[] arr2 = new int[]{3, 3, 6, 12, 4, 7, 2, 1, 9, 11};
        int comparator = comparator(arr2);
        System.out.println(smallSum);
        System.out.println(comparator);
    }

}

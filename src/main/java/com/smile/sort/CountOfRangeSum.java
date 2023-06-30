package com.smile.sort;

import com.smile.utils.ArrayUtils;

import java.util.Arrays;

/**
 * <a href="https://leetcode.com/problems/count-of-range-sum/">...</a>
 * 给定一个数组arr，两个整数lower和upper，
 * 返回arr中有多少个子数组的累加和在[lower,upper]范围上
 * @author LiuXiBin
 * @since 2023/6/30 13:17
 */
public class CountOfRangeSum {

    private static int getCountOfRangeSum(int[] arr, int lower, int upper) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 计算前缀和数组
        int[] sums = new int[arr.length];
        sums[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            sums[i] = sums[i - 1] + arr[i];
        }
        return process(sums, 0, arr.length - 1, lower, upper);
    }

    private static int process(int[] sums, int left, int right, int lower, int upper) {
        if (left == right) {
            if (sums[left] >= lower && sums[left] <= upper) {
                return 1;
            } else {
                return 0;
            }
        }

        int middle = left + ((right - left) >> 1);
        int leftNum = process(sums, left, middle, lower, upper);
        int rightNum = process(sums, middle + 1, right, lower, upper);
        int mergeNum = merge(sums, left, middle, right, lower, upper);

        return leftNum + rightNum + mergeNum;
    }

    private static int merge(int[] sums, int left, int middle, int right, int lower, int upper) {
        int windowLeft = left;
        int windowRight = left;
        int ans = 0;
        for (int i = middle + 1; i <= right; i++) {
            // 区间 [2,3,4,5,5,6] [3,5]
            int min = sums[i] - upper;
            int max = sums[i] - lower;
            while (windowLeft <= middle && sums[windowLeft] < min) {
                windowLeft++;
            }
            while (windowRight <= middle && sums[windowRight] <= max) {
                windowRight++;
            }
            ans += Math.max(0, windowRight - windowLeft);
        }

        int p1 = left;
        int p2 = middle + 1;
        int[] help = new int[right - left + 1];

        for (int i = 0; i < help.length; i++) {
            if (p1 > middle) {
                help[i] = sums[p2++];
            } else if (p2 > right) {
                help[i] = sums[p1++];
            } else {
                help[i] = sums[p1] < sums[p2] ? sums[p1++] : sums[p2++];
            }
        }

        for (int i = 0; i < help.length; i++) {
            sums[left + i] = help[i];
        }
        return ans;
    }

    private static int comparator(int[] arr, int lower, int upper) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            int sum = 0;
            for (int k = i; k < arr.length; k++) {
                sum += arr[k];
                if (sum >= lower && sum <= upper) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxValue = 100;
        int maxSize = 100;

        int lower = 30;
        int upper = 70;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = ArrayUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = ArrayUtils.copyArray(arr1);
            if (getCountOfRangeSum(arr1, lower, upper) != comparator(arr2, lower, upper)) {
                System.out.println(Arrays.toString(arr1));
                System.out.println(Arrays.toString(arr2));
                break;
            }
        }
        System.out.println("测试结束");
    }

}

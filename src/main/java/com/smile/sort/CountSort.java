package com.smile.sort;

import com.smile.utils.ArrayUtils;

import java.util.Arrays;

/**
 * 计数排序
 * @author LiuXiBin
 * @since 2024/3/15 9:17
 */
public class CountSort {

    // 排序限制：value 范围 不可过大
    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 寻找最大及最小值
        int maxValue = arr[0];
        int minValue = arr[0];
        for (int i = 1; i < arr.length; i++) {
            maxValue = Math.max(maxValue, arr[i]);
            minValue = Math.min(minValue, arr[i]);
        }
        // 初始化计数器
        int[] counts = new int[maxValue - minValue + 1];
        // 开始计数
        for (int v : arr) {
            counts[v - minValue]++;
        }
        // 结果
        for (int i = 0, k = 0; i < counts.length; i++) {
            while (counts[i] > 0) {
                arr[k++] = i + minValue;
                counts[i]--;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("测试开始");
        for (int i = 0; i < 10000; i++) {
            int[] arr1 = ArrayUtils.generateRandomArray(1000, 200);
            int[] arr2 = ArrayUtils.copyArray(arr1);
            Arrays.sort(arr1);
            sort(arr2);
            for (int k = 0; k < arr1.length; k++) {
                if (arr1[k] != arr2[k]) {
                    System.out.println(Arrays.toString(arr1));
                    System.out.println(Arrays.toString(arr2));
                    System.out.println("测试失败");
                    return;
                }
            }
        }
        System.out.println("测试结束");
    }
}

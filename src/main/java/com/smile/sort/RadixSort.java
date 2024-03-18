package com.smile.sort;

import com.smile.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 基数排序
 * @author LiuXiBin
 * @since 2024/3/15 9:52
 */
public class RadixSort {
    // 队列实现方式 空间复杂度略高
    public static void sort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 辅助空间
        List<LinkedList<Integer>> queueList = new ArrayList<>(10);
        for (int i = 0; i<10; i++) {
            queueList.add(new LinkedList<>());
        }
        // 最小值
        int minValue = arr[0];
        // 最大值
        int maxValue = arr[0];
        for (int v : arr) {
            maxValue = Math.max(maxValue, v);
            minValue = Math.min(minValue, v);
        }
        if (minValue < 0) {
            int abs = Math.abs(minValue);
            for (int i = 0; i < arr.length; i++) {
                arr[i] += abs;
            }
            maxValue += abs;
        }
        // 最大值位数
        int digit = 0;
        while (maxValue != 0) {
            maxValue = maxValue/10;
            digit++;
        }

        for (int i = 0; i < digit; i++) {
            // 进
            for (int value : arr) {
                int index = ((value / ((int) Math.pow(10, i))) % 10);
                queueList.get(index).add(value);
            }
            // 出
            int index = 0;
            for (LinkedList<Integer> queue : queueList) {
                while (queue.peek() != null) {
                    arr[index++] = queue.poll();
                }
            }
        }
        // 处理过负数问题 恢复数据
        if (minValue < 0) {
            int abs = Math.abs(minValue);
            for (int i = 0; i < arr.length; i++) {
                arr[i] -= abs;
            }
        }
    }

    // 前缀和实现方式
    public static void sort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 获取最大最小值
        int maxValue = arr[0];
        int minValue = arr[0];
        for (int i = 1; i < arr.length; i++) {
            maxValue = Math.max(maxValue, arr[i]);
            minValue = Math.min(minValue, arr[i]);
        }
        // 最小值处理（为负数时整体补成自然数）
        if (minValue < 0) {
            int abs = Math.abs(minValue);
            for (int i = 0; i < arr.length; i++) {
                arr[i] += abs;
            }
            maxValue += abs;
        }
        // 最大值位数
        int digit = 0;
        while (maxValue != 0) {
            maxValue = maxValue/10;
            digit++;
        }
        int[] help = new int[arr.length];
        for (int i = 0; i < digit; i++) {
            // 计数数组
            int[] countArr = new int[10];
            for (int value : arr) {
                int dig = (value / (int)Math.pow(10, i)) % 10;
                countArr[dig]++;
            }
            // 求和数组
            int[] sumArr = new int[10];
            sumArr[0] = countArr[0];
            for (int k = 1; k < countArr.length; k++) {
                sumArr[k] = sumArr[k-1] + countArr[k];
            }
            // 排序
            for (int k = arr.length - 1; k >= 0; k--) {
                int dig = (arr[k] / (int)Math.pow(10, i)) % 10;
                help[--sumArr[dig]] = arr[k];
            }
            for (int k = 0; k < arr.length; k++) {
                arr[k] = help[k];
            }
        }
        // 处理过负数问题 恢复数据
        if (minValue < 0) {
            int abs = Math.abs(minValue);
            for (int i = 0; i < arr.length; i++) {
                arr[i] -= abs;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("测试开始");
        for (int i = 0; i < 10000; i++) {
            int[] arr1 = ArrayUtils.generateRandomArray(1000, 200);
            int[] arr2 = ArrayUtils.copyArray(arr1);
            int[] arr3 = ArrayUtils.copyArray(arr1);
            Arrays.sort(arr1);
            sort1(arr2);
            sort2(arr3);
            for (int k = 0; k < arr1.length; k++) {
                if (arr1[k] != arr2[k] || arr1[k] != arr3[k]) {
                    System.out.println("测试失败");
                    System.out.println(Arrays.toString(arr1));
                    System.out.println(Arrays.toString(arr2));
                    System.out.println(Arrays.toString(arr3));
                    return;
                }
            }
        }
        System.out.println("测试结束");
    }

}

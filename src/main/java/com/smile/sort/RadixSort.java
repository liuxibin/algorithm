package com.smile.sort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 基数排序
 * @author LiuXiBin
 * @since 2024/3/15 9:52
 */
public class RadixSort {

    public static void sort1(int[] arr) {
        if (arr == null || arr.length > 2) {
            return;
        }
        // 辅助空间
        List<LinkedList<Integer>> queueList = new ArrayList<>(10);
        for (int i = 0; i<10; i++) {
            queueList.add(new LinkedList<>());
        }
        // 获取高位
        int digit = getMaxDigit(arr);

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

    }

    public static int getMaxDigit(int[] arr) {
        // 查询最大值
        int maxValue = arr[0];
        for (int v : arr) {
            maxValue = Math.max(maxValue, v);
        }
        // 最大值位数
        int digit = 0;
        while (maxValue != 0) {
            maxValue = maxValue/10;
            digit++;
        }
        return digit;
    }

}

package com.smile.sort.extend;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 给定很多线段，每个线段都有两个数[start, end]，
 * 表示线段开始位置和结束位置，左右都是闭区间
 * 规定：
 * 1）线段的开始和结束位置一定都是整数值
 * 2）线段重合区域的长度必须>=1
 * 返回线段最多重合区域中，包含了几条线段
 * @author LiuXiBin
 * @since 2023/11/16 15:26
 */
public class CoverMax {

    /**
     * 最简单暴力的方式 通过点找线
     * @param lines 线段集合
     * @return int
     * @author LiuXiBin
     * @since 2023/11/16 16:03
     */
    public static int maxNum1(int[][] lines) {
        // 需处理线段区间
        int start = Integer.MAX_VALUE;
        int end = Integer.MIN_VALUE;
        for (int[] line : lines) {
            start = Math.min(line[0], start);
            end = Math.max(line[1], end);
        }
        int maxNum = 0;
        // 处理整个区间, 比较粗暴，+0.5是为了使判断的点在线段中
        for (double point = start + 0.5; point < end; point++) {
            // 遍历线段 找到存在区间点位的线段
            int tempNum = 0;
            for (int[] line : lines) {
                if (point > line[0] && point < line[1]) {
                    tempNum++;
                }
            }
            maxNum = Math.max(maxNum, tempNum);
        }
        return maxNum;
    }

    /**
     * 使用堆思路 先将线段按起点排序 再将终点放入堆
     * @param lines 线段集合
     * @return int
     * @author LiuXiBin
     * @since 2023/11/16 16:07
     */
    public static int maxNum2(int[][] lines) {
        // 排序线段
        Arrays.sort(lines, Comparator.comparingInt(item -> item[0]));
        int maxNum = 0;
        // 使用小根堆处理
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        // 处理线段
        for (int[] line : lines) {
            // 小根堆中是否存在尾部节点在当前线段之前的线段 存在则出堆
            while (!heap.isEmpty() && heap.peek() <= line[0]) {
                heap.poll();
            }
            heap.add(line[1]);
            maxNum = Math.max(maxNum, heap.size());
        }
        return maxNum;
    }

    /**
     * 生成随机线段
     * @param maxNum 最多线段数量
     * @param minPoint 最小节点
     * @param maxPoint 最大节点
     * @return int[][]
     * @author LiuXiBin
     * @since 2023/11/16 15:48
     */
    public static int[][] generateLines(int maxNum, int minPoint, int maxPoint) {
        // 随机数量的线段[1~maxNum]
        int size = (int)(Math.random() * maxNum + 1);
        // 初始线段数组
        int[][] lines = new int[size][2];
        // 生成线段
        for (int i= 0; i < size; i++) {
            int point1 = minPoint + (int) (Math.random() * (maxPoint - minPoint + 1));
            int point2 = minPoint + (int) (Math.random() * (maxPoint - minPoint + 1));
            if (point1 == point2) {
                point2 = point2 == maxPoint ? point2 - 1 : point2 + 1;
            }
            lines[i][0] = Math.min(point1, point2);
            lines[i][1] = Math.max(point1, point2);
        }
        return lines;
    }

    public static void main(String[] args) {
        System.out.println("计算最大线段重合数开始");
        for (int i = 0; i < 10000; i++) {
            int[][] lines = generateLines(1000, 0, 200);
            if (maxNum1(lines) != maxNum2(lines)) {
                System.out.println("最大线段重合数错误");
            }
        }
        System.out.println("计算最大线段重合数结束");
    }

    /*public static void main(String[] args) {

        List<String> arrList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            String str = shortLink(i);
            System.out.println("短码:::::::" + str);
            if (arrList.contains(str)) {
                System.out.println("重复:::::::" + str);
                return;
            }
            arrList.add(str);
        }

    }

    private static String shortLink(long val) {
        char[] digits = {
                '3' , 'P' , '5' , 'G' , 'U' , 'V' ,
                '6' , '7' , 'a' , 'b' , '8' , 't' ,
                'c' , 'O' , 'L' , '0' , 'B' , 'C' ,
                'i' , 'j' , 'n' , 'W' , 'Q' , 'k' ,
                'o' , 'Z' , 'q' , 'H' , '9' , 'r' ,
                'u' , 'v' , 'z' , 'y' , 'T' , 'R' ,
                'A' , 'F' , 'g' , 'h' , '2' , 'd' ,
                'J' , '1' , 'e' , 'X' , 'l' , 'K' ,
                'M' , 'f' , 'I' , 'w' , 'D' , 'E' ,
                'S' , 'x' , 'm' , '4' , 'N' , 'Y' ,
                's' , 'p'
        };

        Random random = new Random();
        int r = random.nextInt(10);
        long resultValue = Long.parseLong(Integer.toString(r) + (238328 + val));

        StringBuilder str = new StringBuilder();
        while (resultValue != 0) {
            int index = (int)(resultValue % 62);
            str.append(digits[index]);
            resultValue = resultValue / 62;
        }
        return str.toString();
    }*/

}

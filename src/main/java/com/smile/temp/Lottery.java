package com.smile.temp;

import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author LiuXiBin
 * @since 2024/4/7 16:58
 */
public class Lottery {



    public static void pin(String[] redArr, String[] blueArr) {


        Map<String, Integer> oneMap = new HashMap<>();
        Map<String, Integer> towMap = new HashMap<>();
        Map<String, Integer> redMap = new HashMap<>();
        Map<String, Integer> blueMap = new HashMap<>();
        for (int i = 1; i <= 33; i++) {
            redMap.put(String.valueOf(i), 0);
        }
        for (int i = 1; i <= 16; i++) {
            blueMap.put(String.valueOf(i), 0);
        }

        int redIndex = 0;
        for (int blueIndex = 0; blueIndex < blueArr.length; blueIndex++) {
            String blue = blueArr[blueIndex];
            blueMap.put(blue, blueMap.get(blue) != null ? blueMap.get(blue) + 1 : 1);
            String towStr = "";
            while (redIndex < (blueIndex+1) * 6) {
                String red = redArr[redIndex];
                redMap.put(red, redMap.get(red) != null ? redMap.get(red) + 1 : 1);
                towStr += StringUtils.isNotBlank(towStr) ? "_" + red : red;
                redIndex++;
            }
            String oneStr = towStr + "_" + blue;
            oneMap.put(oneStr, oneMap.get(oneStr) != null ? oneMap.get(oneStr) + 1 : 1);
            towMap.put(towStr, towMap.get(towStr) != null ? towMap.get(towStr) + 1 : 1);
        }

        // 排序
        List<Map.Entry<String, Integer>> oneList = new ArrayList<>(oneMap.entrySet());
        oneList.sort(Map.Entry.comparingByValue());
        System.out.println("oneList: " + oneList.subList(oneList.size() - 10, oneList.size()));
        List<Map.Entry<String, Integer>> towList = new ArrayList<>(towMap.entrySet());
        towList.sort(Map.Entry.comparingByValue());
        System.out.println("towList: " + towList.subList(towList.size() - 10, towList.size()));
        List<Map.Entry<String, Integer>> redList = new ArrayList<>(redMap.entrySet());
        redList.sort(Map.Entry.comparingByValue());
        System.out.println("redList: " + redList);
        List<Map.Entry<String, Integer>> blueList = new ArrayList<>(blueMap.entrySet());
        blueList.sort(Map.Entry.comparingByValue());
        System.out.println("blueList: " + blueList);

        System.out.println("test:" + towMap.get("4_11_16_21_29_30"));
        // redList: [21=274, 30=276, 28=278, 3=278, 33=284, 29=285, 16=290, 4=291, 24=294, 13=294, 31=296, 11=296, 23=297, 5=297, 25=301, 8=307, 12=310, 15=313, 18=313, 7=315, 32=319, 10=320, 17=323, 20=323, 27=324, 2=325, 26=329, 19=329, 1=332, 9=333, 22=343, 6=352, 14=359]
        // blueList: [13=91, 10=94, 11=96, 5=96, 2=97, 3=99, 9=101, 14=108, 8=108, 4=110, 6=111, 12=115, 15=116, 7=117, 16=119, 1=122]
        // redList: [29=34, 16=37, 4=37, 11=38, 23=40, 15=40, 21=43, 28=44, 1=46, 30=47, 13=47, 5=48, 24=49, 25=50, 32=50, 14=50, 19=50, 12=51, 6=51, 7=52, 22=53, 31=53, 10=53, 9=53, 3=54, 8=54, 26=55, 33=55, 18=56, 2=56, 20=56, 27=57, 17=61]
        // blueList: [7=10, 10=12, 12=13, 2=13, 9=13, 11=15, 1=16, 3=16, 13=17, 16=18, 14=19, 8=20, 5=21, 6=21, 15=23, 4=23]

        //  4 11 16 21 29 30  10
    }

    public static void main(String[] args) {
        Properties properties = new Properties();
        try (InputStream inputStream = Lottery.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inputStream);

            // 读取并解析数组数据
            String[] array1 = properties.getProperty("red").split(",");
            String[] array2 = properties.getProperty("blue").split(",");

            pin(array1, array2);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

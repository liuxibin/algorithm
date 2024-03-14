package com.smile.tree;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author LiuXiBin
 * @since 2024/3/14 14:37
 */
public class TriePrefixTree {

    private static class Trie {

        private final Node trieTree = new Node();

        private static class Node {

            public int pass;

            public int end;

            public Node[] nexts;

            public Node() {
                this.pass = 0;
                this.end = 0;
                this.nexts = new Node[26];
            }

            @Override
            public String toString() {
                return "Node{" +
                        "pass=" + pass +
                        ", end=" + end +
                        ", nexts=" + Arrays.toString(nexts) +
                        '}';
            }
        }

        public void insert(String str) {
            if (StringUtils.isBlank(str)) {
                return;
            }
            Node node = this.trieTree;
            char[] chars = str.toCharArray();
            for (char cha : chars) {
                node.pass++;
                int index = cha - 'a';
                if (node.nexts[index] == null) {
                    node.nexts[index] = new Node();
                }
                node = node.nexts[index];
            }
            node.pass++;
            node.end++;
        }

        public void delete(String str) {
            if (search(str) == 0) {
                return;
            }
            Node node = this.trieTree;
            char[] chars = str.toCharArray();
            for (char cha : chars) {
                node.pass--;
                int index = cha - 'a';
                if (node.nexts[index].pass == 1) {
                    node.nexts[index] = null;
                } else {
                    node = node.nexts[index];
                }
            }
            node.pass--;
            node.end--;
        }

        public int search(String str) {
            if (StringUtils.isBlank(str)) {
                return 0;
            }
            Node node = this.trieTree;
            char[] chars = str.toCharArray();
            for (char ch : chars) {
                int index = ch - 'a';
                if (node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.end;
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("abc");
        trie.insert("abd");
        trie.insert("abds");
        trie.insert("abds");
        System.out.println(trie.trieTree);
        System.out.println(trie.search("abds"));
        System.out.println(trie.search("abe"));
        trie.delete("abds");
        System.out.println(trie.search("abds"));
        trie.delete("abds");
        System.out.println(trie.search("abds"));
    }
}

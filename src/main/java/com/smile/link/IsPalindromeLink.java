package com.smile.link;

import com.smile.link.domain.Node;

import java.util.Stack;

/**
 * 链表回文结构 1.2.3.3.2.1/1.2.3.2.1
 * @author LiuXiBin
 * @since 2024/3/22 15:21
 */
public class IsPalindromeLink {

    // 使用栈的形式-全部押栈
    public static <T> boolean isPalindrome1(Node<T> head) {
        if (head == null || head.next == null) {
            return true;
        }
        Node<T> cur = head;
        Stack<Node<T>> stack = new Stack<>();
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        cur = head;
        while (cur != null) {
            Node<T> pop = stack.pop();
            if (!cur.value.equals(pop.value)) {
                return false;
            }
            cur = cur.next;
        }
        return true;
    }

    // 使用栈的形式-仅一半入栈
    public static <T> boolean isPalindrome2(Node<T> head) {
        if (head == null || head.next == null) {
            return true;
        }
        Stack<Node<T>> stack = new Stack<>();
        // 经历 1.2.3.4.5
        Node<T> slow = head;
        stack.push(slow);
        // 经历 2.4.6.8
        Node<T> fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            stack.push(slow);
            fast = fast.next.next;
        }
        // 奇数长度弹出中点
        if (fast == null) {
            stack.pop();
        }
        slow = slow.next;
        while (slow != null) {
            if (!slow.value.equals(stack.pop().value)) {
                return false;
            }
            slow = slow.next;
        }
        return true;
    }

    // 不使用内置结构
    public static <T> boolean isPalindrome3(Node<T> head) {
        if (head == null || head.next == null) {
            return true;
        }

        // 经历 1.2.3.4.5
        Node<T> slow = head;
        // 经历 3.5.7.9
        Node<T> fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = head;
        // 反转 1->2->3->4->5  1->2->3<-4<-5
        Node<T> node = slow.next;
        slow.next = null;
        Node<T> temp = null;
        while (node != null) {
            temp = node.next;
            node.next = slow;
            slow = node;
            node = temp;
        }
        // 记录反转后的头结点 后续恢复使用
        temp = slow;
        boolean res = true;
        while (fast != null && slow != null) {
            if (!fast.value.equals(slow.value)) {
                res = false;
                break;
            }
            fast = fast.next;
            slow = slow.next;
        }
        // 恢复链表结构
        slow = temp;
        node = slow.next;
        slow.next = null;
        while (node != null) {
            temp = node.next;
            node.next = slow;
            slow = node;
            node = temp;
        }
        return res;
    }

    public static void main(String[] args) {

        Node<Integer> head = null;
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node<>(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node<>(1);
        head.next = new Node<>(2);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node<>(1);
        head.next = new Node<>(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node<>(1);
        head.next = new Node<>(2);
        head.next.next = new Node<>(3);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node<>(1);
        head.next = new Node<>(2);
        head.next.next = new Node<>(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node<>(1);
        head.next = new Node<>(2);
        head.next.next = new Node<>(3);
        head.next.next.next = new Node<>(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node<>(1);
        head.next = new Node<>(2);
        head.next.next = new Node<>(2);
        head.next.next.next = new Node<>(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node<>(1);
        head.next = new Node<>(2);
        head.next.next = new Node<>(3);
        head.next.next.next = new Node<>(2);
        head.next.next.next.next = new Node<>(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

    }

    public static <T> void printLinkedList(Node<T> node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }
}

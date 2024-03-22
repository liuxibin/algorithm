package com.smile.link;

import com.smile.link.domain.Node;

/**
 * @author LiuXiBin
 * @since 2024/3/21 15:15
 */
public class LinkMidNode {

    // 输入链表头节点，奇数长度返回中点，偶数长度返回上中点
    public static <T> Node<T> getUpMid(Node<T> node) {
        // 0~2个节点均返回头结点
        if (node == null || node.next == null || node.next.next == null) {
            return node;
        }
        // 慢指针
        Node<T> slow = node;
        // 快指针
        Node<T> fast = node.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // 输入链表头节点，奇数长度返回中点，偶数长度返回下中点
    public static <T> Node<T> getDownMid(Node<T> node) {
        // 0~1个节点均返回头结点
        if (node == null || node.next == null) {
            return node;
        }
        // 慢指针，经历1.2.3.4.5.6
        Node<T> slow = node;
        // 快指针，经历1.2.3.4.5.6
        Node<T> fast = node;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static <T> Node<T> getUpMidBefore(Node<T> node) {
        // 0~2个节点均返回null
        if (node == null || node.next == null || node.next.next == null) {
            return null;
        }
        // 慢指针，经历1.2.3.4.5.6
        Node<T> slow = node;
        // 快指针，经历4.6.8.10
        Node<T> fast = node.next.next.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static <T> Node<T> getDownMidBefore(Node<T> node) {
        // 0~1个节点均返回null
        if (node == null || node.next == null) {
            return null;
        }
        // 慢指针，经历1.2.3.4.5.6
        Node<T> slow = node;
        // 快指针，经历4.6.8.10
        Node<T> fast = node.next.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

}

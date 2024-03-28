package com.smile.link;

import com.smile.link.domain.Node;

/**
 * @author LiuXiBin
 * @since 2024/3/26 10:04
 */
public class FindFirstIntersectNode {

    public static <T> Node<T> getIntersectNode(Node<T> head1, Node<T> head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node<T> loop1 = getIntersectNode(head1);
        Node<T> loop2 = getIntersectNode(head2);
        if (loop1 == null && loop2 == null) {
            return noLoop(head1, head2);
        }
        if (loop1 != null && loop2 != null) {
            return bothLoop(head1, loop1, head2, loop2);
        }
        return null;
    }

    public static <T> Node<T> getIntersectNode(Node<T> head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node<T> slow = head.next;
        Node<T> fast = head.next.next;
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    // 两个无环链表相交
    public static <T> Node<T> noLoop(Node<T> head1, Node<T> head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node<T> cur1 = head1;
        Node<T> cur2 = head2;
        int diff = 0;
        while (cur1.next != null) {
            diff++;
            cur1 = cur1.next;
        }

        while (cur2.next != null) {
            diff--;
            cur2 = cur2.next;
        }
        // cur1 & cur2 皆为尾部 尾部不同 不可能相交
        if (cur1 != cur2) {
            return null;
        }
        // cur1 较长链表, cur2 较短
        cur1 = diff >= 0 ? head1 : head2;
        cur2 = cur1 == head1 ? head2 : head1;

        diff = Math.abs(diff);
        // 长链表先走 diff 步
        while (diff > 0) {
            cur1 = cur1.next;
            diff--;
        }
        // 同时走 直到相遇
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    // 两个有环链表相交 1、环外相交 2、环内相交 3、不相交
    public static <T> Node<T> bothLoop(Node<T> head1, Node<T> loop1, Node<T> head2, Node<T> loop2) {
        Node<T> cur1 = head1;
        Node<T> cur2 = head2;
        // 两个有环链表的入环节点相同 说明在环外相交 环外部分结构同无环链表相同
        if (loop1 == loop2) {
            int diff = 0;
            while (cur1 != loop1) {
                diff++;
                cur1 = cur1.next;
            }

            while (cur2 != loop2) {
                diff--;
                cur2 = cur2.next;
            }
            // cur1 较长链表, cur2 较短
            cur1 = diff >= 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;

            diff = Math.abs(diff);
            // 长链表先走 diff 步
            while (diff > 0) {
                cur1 = cur1.next;
                diff--;
            }
            // 同时走 直到相遇
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        } else {
            cur1 = loop1.next;
            // 环中转一圈
            while (cur1 != loop1) {
                // 链表1环中某个节点是链表2的入环节点
                if (cur1 == loop2) {
                    return loop1;
                }
                cur1 = cur1.next;
            }
            // 链表1环中无链表2的入环节点 两链表不相交
            return null;
        }
    }

    public static void main(String[] args) {
        Node<Integer> head1 = new Node<>(1);
        head1.next = new Node<>(2);
        head1.next.next = new Node<>(3);
        head1.next.next.next = new Node<>(4);
        head1.next.next.next.next = new Node<>(5);
        head1.next.next.next.next.next = new Node<>(6);
        head1.next.next.next.next.next.next = head1.next.next;

        Node<Integer> intersectNode = getIntersectNode(head1);
        System.out.println(intersectNode);
    }

}

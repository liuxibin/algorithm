package com.smile.link.domain;

/**
 * @author LiuXiBin
 * @since 2024/3/21 10:22
 */
public class Node<T> {

    public T value;

    public Node<T> next;

    public Node(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{value=" + value + '}';
    }
}

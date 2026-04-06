package co.edu.uptc.model.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class DoubleList<T> {
    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    private class Node<T> {
        public T value;
        public Node next;
        public Node previous;
    }

    private Node head = null;
    private Node tail = null;

    private boolean addIfHeaderEmpty(T value) {
        if (head == null) {
            head = new Node(value, null, null);
            tail = head;
            return true;
        }
        return false;
    }

    public void addFirst(T value) {
        if (addIfHeaderEmpty(value))
            return;
        Node newNode = new Node(value, head, null);
        head.previous = newNode;
        head = newNode;
    }

    public void addLast(T value) {
        if (addIfHeaderEmpty(value))
            return;
        Node newNode = new Node(value, null, tail);
        tail.next = newNode;
        tail = newNode;
    }

    public List<T> getAllList() {
        Node<T> current = head;
        List<T> list = new ArrayList<>();

        while (current != null) {
            list.add((T) current.value);
            current = current.next;
        }
        return list;
    }

    public T removeFirst() {
        if (head == null) return null;
        T value = (T) head.value;
        if (head.next == null) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.previous = null;
        }
        return value;
    }

    public T removeEnd() {
        if (isEmpty()) return null;
        T value = (T) tail.value;
        tail = tail.previous;
        if (tail != null) tail.next = null;
        else head = null;
        return value;
    }

    public boolean isEmpty() {
        return head == null;
    }
}


package DataStructures;

import MazeTile.MazeTile;

public class CircularLinkedList {
        private Node head;
        private int size;
    
        private class Node {
            MazeTile data;
            Node next;
    
            Node(MazeTile data) {
                this.data = data;
            }
        }
    
        public void add(MazeTile data) {
            Node newNode = new Node(data);
            if (head == null) {
                head = newNode;
                head.next = head;
            } else {
                Node temp = head;
                while (temp.next != head) {
                    temp = temp.next;
                }
                temp.next = newNode;
                newNode.next = head;
            }
            size++;
        }
    
        public void rotate() {
            if (head != null) {
                head = head.next;
            }
        }
    
        public MazeTile get(int index) {
            if (head == null) return null;
            Node current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.data;
        }
    
        public int size() {
            return size;
        }
        
        public MazeTile[] toList() {
            if (head == null) {
                return new MazeTile[0]; 
            }
        
            MazeTile[] array = new MazeTile[size]; 
            Node current = head;
            int index = 0;
            
            do {
                array[index++] = current.data;
                current = current.next;
            } while (current != head);
            
            return array;
        }
}

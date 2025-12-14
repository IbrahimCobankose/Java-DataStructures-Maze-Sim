package DataStructures;

public class Stack {
        private Node top;
        private int size;
    
        private class Node {
            String data;
            Node next;
    
            Node(String data) {
                this.data = data;
            }
        }
    
        public void push(String data) {
            Node newNode = new Node(data);
            newNode.next = top;
            top = newNode;
            size++;
        }
    
        public String pop() {
            if (top == null) return null;
            String data = top.data;
            top = top.next;
            size--;
            return data;
        }
    
        public int size() {
            return size;
        }

        public String peek(){
            if(top==null) return null;
            return top.data;
        }
    
        public boolean isEmpty() {
            return top == null;
        }
    
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            Node current = top;
            int count = 0;
            while (current != null && count < 5) {       
                sb.append(current.data);
                if (current.next != null && count < 4) sb.append(", ");
                current = current.next;
                count++;
            }
            sb.append("]");
            return sb.toString();
        }
}

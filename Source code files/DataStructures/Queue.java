package DataStructures;

import Agent.Agent;

    public class Queue {
        public  Node front, rear;
        public  int size;
    
        public  class Node {
            public Agent data;
            public Node next;
    
            Node(Agent data) {
                this.data = data;
            }
        }
    
        public void enqueue(Agent agent) {
            Node newNode = new Node(agent);
            if (rear == null) {
                front = rear = newNode;
            } else {
                rear.next = newNode;
                rear = newNode;
            }
            size++;
        }
    
        public Agent dequeue() {
            if (front == null) return null;
            Agent agent = front.data;
            front = front.next;
            if (front == null) rear = null;
            size--;
            return agent;
        }

        public Agent peek(){
            if(front==null) return null;
            return front.data;
        }
    
        public boolean isEmpty() {
            return front == null;
        }
    
        public int size() {
            return size;
        }
}

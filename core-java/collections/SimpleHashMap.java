package collections;

public class SimpleHashMap<K, V> {

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, V>[] buckets;
    private int capacity;
    private int size;

    @SuppressWarnings("unchecked")
    public SimpleHashMap(int capacity) {
        if (Integer.bitCount(capacity) != 1) {
            throw new IllegalArgumentException("Capacity must be power of 2");
        }
        
        this.capacity = capacity;
        buckets = (Node<K, V>[]) new Node[capacity];
    }

    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null keys not allowed");
        }

        int hash = key.hashCode();
        int index = hash & (capacity - 1); // Assuming capacity in powers of 2

        Node<K, V> head = buckets[index];

        // Case 1: Empty bucket
        if (head == null) {
            buckets[index] = new Node<>(key, value);
            size++;
            return;
        }

        // Case 2: Traverse list
        Node<K, V> temp = head;
        while (temp != null) {
            if (temp.key.equals(key)) {
                temp.value = value; // Update existing key
                return;
            }
            temp = temp.next;
        }

        // Key not found → insert at beginning
        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = head;
        buckets[index] = newNode;
        size++;
    }

    public V get(K key) {
        if (key == null) {
            return null;
        }

        int hash = key.hashCode();
        int index = hash & (capacity - 1);

        Node<K, V> temp = buckets[index];

        while (temp != null) {
            if (temp.key.equals(key)) {
                return temp.value;
            }
            temp = temp.next;
        }

        return null;
    }
}
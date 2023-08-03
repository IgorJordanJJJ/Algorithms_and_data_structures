package ru.geekbrains.lesson4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashMap<K, V> implements Iterable<HashMap.Entity<K, V>> {

    private static final int INIT_BUCKET_COUNT = 16;

    private Bucket[] buckets;
    class Entity{
        K key;
        V value;
    }

    class Bucket<K, V>{

        Node head;

        class Node{
            Node next;
            Entity value;

        }

        public V add(Entity entity){
            Node node = new Node();
            node.value = entity;

            if (head == null){
                head = node;
                return null;
            }

            Node currentNode = head;
            while (true){
                if (currentNode.value.key.equals(entity.key)){
                    V buf = (V)currentNode.value.value;
                    currentNode.value.value = entity.value;
                    return buf;
                }
                if (currentNode.next != null)
                    currentNode = currentNode.next;
                else
                {
                    currentNode.next = node;
                    return null;
                }
            }

        }

        public V get(K key){
            Node node = head;
            while (node != null){
                if (node.value.key.equals(key))
                    return (V)node.value.value;
                node = node.next;
            }
            return null;
        }

        public V remove(K key){
            if (head == null)
                return null;
            if (head.value.key.equals(key)){
                V buf = (V)head.value.value;
                head = head.next;
                return buf;
            }
            else{
                Node node = head;
                while (node.next != null){
                    if (node.next.value.key.equals(key)){
                        V buf = (V)node.next.value.value;
                        node.next = node.next.next;
                        return buf;
                    }
                    node = node.next;
                }
                return null;
            }
        }

    }

    private int calculateBucketIndex(K key){
        int index = key.hashCode() % buckets.length;
        index = Math.abs(index);
        return index;
    }

    @Override
    public Iterator<Entity<K, V>> iterator() {
        return new HashMapIterator();
    }

    // Inner class to represent the HashMapIterator
    private class HashMapIterator implements Iterator<Entity<K, V>> {
        private int bucketIndex = 0;
        private Bucket<K, V> currentNode = null;

        public HashMapIterator() {
            findNextNode();
        }

        private void findNextNode() {
            while (bucketIndex < buckets.length) {
                if (buckets[bucketIndex] != null) {
                    if (currentNode == null) {
                        currentNode = buckets[bucketIndex].head;
                    } else {
                        currentNode = currentNode.next;
                    }
                    if (currentNode != null) {
                        return;
                    }
                }
                bucketIndex++;
            }
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Entity<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Entity<K, V> entity = currentNode.value;
            currentNode = currentNode.next;
            findNextNode();
            return entity;
        }
    }
}

    /**
     * Добавить новую пару ключ + значение
     * @param key ключ
     * @param value значение
     * @return предыдущее значение (при совпадении ключа), иначе null
     */
    public V put(K key, V value){
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null){
            bucket = new Bucket();
            buckets[index] = bucket;
        }

        Entity entity = new Entity();
        entity.key = key;
        entity.value = value;

        return (V)bucket.add(entity);
    }

    public V get(K key){
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null)
            return null;
        return (V)bucket.get(key);
    }

    public V remove(K key){
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null)
            return null;
        return (V)bucket.remove(key);
    }

    public HashMap(){
        //buckets = new Bucket[INIT_BUCKET_COUNT];
        this(INIT_BUCKET_COUNT);
    }

    public HashMap(int initCount){
        buckets = new Bucket[initCount];
    }


}

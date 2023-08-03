package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        MyHashMap<String, Integer> hashMap = new MyHashMap<>();
        hashMap.put("key1", 1);
        hashMap.put("key2", 2);
        hashMap.put("key3", 3);

        for (MyHashMap.Entry<String, Integer> entry : hashMap) {
            System.out.println(entry.key + ": " + entry.value);
        }
    }
}
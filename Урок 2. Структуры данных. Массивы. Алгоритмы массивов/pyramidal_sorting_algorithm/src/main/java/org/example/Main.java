package org.example;

import java.util.Arrays;

public class Main {

    /*Реализовать алгоритм пирамидальной сортировки (сортировка кучей).*/

    /*Сортировка слиянием (Merge Sort)*/
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int n = arr.length;
        int[] temp = new int[n];
        mergeSortHelper(arr, temp, 0, n - 1);
    }

    private static void mergeSortHelper(int[] arr, int[] temp, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortHelper(arr, temp, left, mid);
            mergeSortHelper(arr, temp, mid + 1, right);
            merge(arr, temp, left, mid, right);
        }
    }

    private static void merge(int[] arr, int[] temp, int left, int mid, int right) {
        for (int i = left; i <= right; i++) {
            temp[i] = arr[i];
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) {
                arr[k] = temp[i];
                i++;
            } else {
                arr[k] = temp[j];
                j++;
            }
            k++;
        }

        while (i <= mid) {
            arr[k] = temp[i];
            i++;
            k++;
        }
    }

    /*Пирамидальная сортировка (Heap Sort)*/

    public static void heapSort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        for (int i = n - 1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }

    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest);
        }
    }
    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7};

        System.out.println("Исходный массив:");
        System.out.println(Arrays.toString(arr));

        // Сравнение времени выполнения для сортировки слиянием
        int[] arr1 = arr.clone();
        long startTimeMergeSort = System.nanoTime();
        mergeSort(arr1);
        long endTimeMergeSort = System.nanoTime();
        long durationMergeSort = endTimeMergeSort - startTimeMergeSort;

        System.out.println("Отсортированный массив (Merge Sort):");
        System.out.println(Arrays.toString(arr1));
        System.out.println("Время выполнения Merge Sort: " + durationMergeSort + " нс");

        // Сравнение времени выполнения для пирамидальной сортировки
        int[] arr2 = arr.clone();
        long startTimeHeapSort = System.nanoTime();
        heapSort(arr2);
        long endTimeHeapSort = System.nanoTime();
        long durationHeapSort = endTimeHeapSort - startTimeHeapSort;

        System.out.println("Отсортированный массив (Heap Sort):");
        System.out.println(Arrays.toString(arr2));
        System.out.println("Время выполнения Heap Sort: " + durationHeapSort + " нс");
    }
}
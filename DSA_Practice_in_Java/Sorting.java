package DSA_Practice_in_Java;

// Sorting Algorithms:
// 1. Bubble Sort
// 2. Selection Sort
// 3. Insertion Sort
// 4. Merge Sort
// 5. Quick Sort
// 6. Heap Sort
// 7. Counting Sort

import java.util.Arrays;

// 1. Bubble Sort: O(n^2) time, O(1) space
class BubbleSort {
    public static void sort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped)
                break;
        }
    }
}

// 2. Selection Sort: O(n^2) time, O(1) space
class SelectionSort {
    public static void sort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }
}

// 3. Insertion Sort: O(n^2) time, O(1) space
class InsertionSort {
    public static void sort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
}

// 4. Merge Sort: O(n log n) time, O(n) space
class MergeSort {
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1)
            return;
        mergeSort(arr, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int s, int e) {
        if (s >= e)
            return;

        int mid = s + (e - s) / 2;
        mergeSort(arr, s, mid);
        mergeSort(arr, mid + 1, e);
        merge(arr, s, mid, e);
    }

    private static void merge(int[] arr, int s, int mid, int e) {
        int[] temp = new int[e - s + 1];
        int i = s, j = mid + 1, k = 0;

        while (i <= mid && j <= e) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        while (j <= e) {
            temp[k++] = arr[j++];
        }

        for (int p = 0; p < temp.length; p++) {
            arr[s + p] = temp[p];
        }
    }
}

// 5. Quick Sort: O(n log n) avg time, O(log n) space
class QuickSort {
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1)
            return;
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low >= high)
            return;

        int pivotIndex = partition(arr, low, high);
        quickSort(arr, low, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, high);
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

// 6. Heap Sort: O(n log n) time, O(1) space
class HeapSort {
    public static void sort(int[] arr) {
        int n = arr.length;

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Extract elements from heap one by one
        for (int i = n - 1; i > 0; i--) {
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
}

// 7. Counting Sort: O(n + k) time, O(k) space
class CountingSort {
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1)
            return;

        int max = arr[0], min = arr[0];
        for (int v : arr) {
            if (v > max) max = v;
            if (v < min) min = v;
        }

        int range = max - min + 1;
        int[] count = new int[range];

        for (int v : arr) {
            count[v - min]++;
        }

        int idx = 0;
        for (int i = 0; i < range; i++) {
            while (count[i] > 0) {
                arr[idx++] = i + min;
                count[i]--;
            }
        }
    }
}

// Main class demonstrating each sorting class
public class Sorting {

    public static void main(String[] args) {
        int[] original = { 64, 34, 25, 12, 22, 11, 90, 5 };
        System.out.println("Original Array: " + Arrays.toString(original));
        System.out.println("--------------------------------------------------");

        // 1. Bubble Sort
        int[] a1 = original.clone();
        BubbleSort.sort(a1);
        System.out.println("Bubble Sort:    " + Arrays.toString(a1));

        // 2. Selection Sort
        int[] a2 = original.clone();
        SelectionSort.sort(a2);
        System.out.println("Selection Sort: " + Arrays.toString(a2));

        // 3. Insertion Sort
        int[] a3 = original.clone();
        InsertionSort.sort(a3);
        System.out.println("Insertion Sort: " + Arrays.toString(a3));

        // 4. Merge Sort
        int[] a4 = original.clone();
        MergeSort.sort(a4);
        System.out.println("Merge Sort:     " + Arrays.toString(a4));

        // 5. Quick Sort
        int[] a5 = original.clone();
        QuickSort.sort(a5);
        System.out.println("Quick Sort:     " + Arrays.toString(a5));

        // 6. Heap Sort
        int[] a6 = original.clone();
        HeapSort.sort(a6);
        System.out.println("Heap Sort:      " + Arrays.toString(a6));

        // 7. Counting Sort
        int[] a7 = original.clone();
        CountingSort.sort(a7);
        System.out.println("Counting Sort:  " + Arrays.toString(a7));
    }
}

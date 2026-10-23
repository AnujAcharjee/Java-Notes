package DSA_Practice_in_Java;

import java.util.Arrays;
import java.util.Scanner;

public class MergeSort {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Input with space (1 2 3 4 ...)

        // int n = sc.nextInt();
        // int[] arr = new int[n];

        // for(int i = 0; i < n; i++) {
        // arr[i] = sc.nextInt();
        // }

        // 2. Input with no space (1234..)

        String s = sc.nextLine();
        int[] arr = new int[s.length()];

        for (int i = 0; i < s.length(); i++) {
            arr[i] = s.charAt(i) - '0';
        }

        sc.close();

        sort(arr, 0, arr.length - 1);

        System.out.println(Arrays.toString(arr));
    }

    static void sort(int[] arr, int s, int e) {
        if (s >= e)
            return;

        int mid = s + (e - s) / 2;

        // divide like BS
        sort(arr, s, mid);
        sort(arr, mid + 1, e);

        merge(arr, s, mid, e);
    }

    static void merge(int[] arr, int s, int mid, int e) {
        // After divide we get 2 sorted arrays
        // merge 2 sorted arrays

        int[] temp = new int[e - s + 1];

        int i = s, j = mid + 1;
        int k = 0;

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

        // put the merged sorted elements back in arr
        for (int p = 0; p < temp.length; p++) {
            arr[s + p] = temp[p];
        }
    }
}

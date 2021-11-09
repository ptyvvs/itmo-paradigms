package search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinarySearchMissing {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < args.length; i++){
            list.add(Integer.parseInt(args[i]));
        }
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++){
            array[i] = list.get(i);
        }
        System.err.print("Array: ");
        for (int i = 0; i < array.length; i++){
            System.err.print(array[i] + " ");
        }
        System.err.println();
        int x = array[0];
        if (array.length > 1) {
            array = Arrays.copyOfRange(array, 1, array.length);
        } else {
            array = new int[0];
        }
        int result1 = iterSearch(x, array);
        int result2 = recurSearch(x, array, -1, array.length - 1);
        if (result1 == result2) {
            if (array.length == 0 ||array[result1] == x) {
                System.out.println(result1);
            } else if (array[array.length - 1] > x){
                System.out.println(-1 * (result1 + 1) - 1);
            } else {
                System.out.println(-1 * result1 - 1);
            }
        } else  {
            System.out.println("Result of iterSearch " + result1 + " \nResult of recurSearch " + result2);
        }
    }


    //PRED: x - целое
    public static int iterSearch(int x, int [] a) {
        //INV: ind in (l, r], где ind - индекс искомого элемента
        int l = -1;
        int r = a.length - 1;
        //PRED: l < r
        while (r - l > 1) {
            // PRED: l <= r + 2
            int m = (r + l) / 2;
            //INV: 0 <= m <= n - 1
            //     r - l >= 2, r >= 2 + l, l >= -1, r >= 1; --> m >= 0;
            //     l <= r - 2; r <= n; l <= n - 2; --> m <= n - 1.
            //INV: l < m <= r
            if (a[m] > x) {
                //PRED: m < ind
                l = m;
                //POST: x > a[l'], r' > l' && INV
            } else {
                //PRED: ind <= m
                r = m;
                //POST: x >= a[r'] && r' > l' && INV
            }
            //POST: l' > ind >= r' && INV
        }
        //POST: r - l <= 1, l < ind <= r --> ind == r
        return r;
    }
    //POST: R == r == ind && a is IMMUTABLE

    //PRED: x, r, l - целое && forall i from 0 to n - 1: a[i] >= a[i + 1] && ind(индекс искомого элемента) in (l, r]
    public static int recurSearch(int x, int[] a, int l, int r) {
        int m = (l + r) / 2;
        //INV: l < m <= r
        if (r - l <= 1) {
            //PRED: INV && r - l <= 1 --> ind == m
            return r;
            //POST: R == r == ind && a is IMMUTABLE
        }
        else if ((a[m]) > x) {
            //PRED: ind > m && x, m, r - целые && INV && a is IMMUTABLE
            return recurSearch(x, a, m, r);
        } else {
            //PRED: ind <= m && x, m, r - целые && INV && a is IMMUTABLE
            return recurSearch(x, a, l, m);
        }
    }
    //POST: R == r == ind && a is IMMUTABLE

}


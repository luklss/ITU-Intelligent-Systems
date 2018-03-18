/**
 * Created by lucas on 3/11/2017.
 */
public class test {



    public static void main(String[] args) {
        int[][] multi = new int[][]{
                { 2, 3, 5, 0, 0},
                { 0, 4, 0, 4, 0},
                { 0, 0, 2, 0, 2},
                { 0, 0, 0, 3, 0 },
                { 0, 0, 0, 0, 4 }
        };
        for (int k = 0; k < multi.length; k++) {
            for (int l = 0; l < multi[k].length ; l ++) {
                Integer[] diagonal = new Integer[multi.length];
                int j = multi[k].length - 1;
                for (int i = 0; i < multi.length - 1; i ++){
                    diagonal[i] = multi[i][j];
                    j--;
                }
                Integer[] diagonal2 = new Integer[multi.length];
                int p = multi[k].length - 1;
                for (int i = l; i >= 0; i--) {
                    diagonal2[i] = multi[p][i];
                    System.out.println("i is " + i );
                    System.out.println("p is " + p );
                    System.out.println("value is " + multi[p][i]);
                    p--;
                }
            }
        }
    }
}

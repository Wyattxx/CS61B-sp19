
public class draw_Triangle {
   /**print lower triangle maxtrix with str '*' */
    public static void drawTriangle(final int n) {
       int row = 1;
       int col = 1;
       while (row < n + 1) {
          while (row >= col) {
             System.out.print('*');
             col = col + 1;
          }
          row = row + 1;
          col = 1;
          System.out.println();
       }
    }

    public static void main(final String[] args) {
      draw_Triangle.drawTriangle(10);
    }
 }



 public class find_max { 
   /** find the max number in an array using while loop */
   public static int max(final int[] m) {
      int i = 0;
      int max_num = 0;
      while (i < m.length) {
         if (m[i] > max_num) {
            max_num = m[i];
         }
         i = i + 1;
      }

      return max_num;
   }

   public static void main(final String[] args) {
      final int[] numbers = new int[] { 9, 2, 15, 2, 22, 10, 6 };
      System.out.print(find_max.max(numbers));
      
   }
}


public class find_max { 
   /** find the max number in an array using for loop */
   public static int max(int[] m) {
      int max_num = 0;
      for (int i = 0; i < m.length; i++){
         if (m[i] > max_num){
            max_num = m[i];
         }
      }
      return max_num;
   }
   public static void main(String[] args) {
      int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
      System.out.print(find_max.max(numbers));
      
   }
}

public class BreakContinue {
  public static void windowPosSum(int[] a, int n) {
    /** replaces each element a[i] with the sum of a[i] through a[i + n], but only if a[i] is positive valued. */ 
    for (int i=0; i<a.length; i++){
      if (a[i] <=0){
        continue;
      }
      int total = 0;
      for (int j=0; j<=n; j++){
        total += a[i+j];
        if (i+j+1 == a.length){
          break;
        }
      }
      a[i] = total;
    }
  }

  public static void main(String[] args) {
    int[] a = {1, 2, -3, 4, 5, 4};
    int n = 3;
    windowPosSum(a, n);

    // Should print 4, 8, -3, 13, 9, 4
    System.out.println(java.util.Arrays.toString(a));
  }
}
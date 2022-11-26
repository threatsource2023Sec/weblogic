package com.bea.xbean.common;

public class Levenshtein {
   private static int minimum(int a, int b, int c) {
      int mi = a;
      if (b < a) {
         mi = b;
      }

      if (c < mi) {
         mi = c;
      }

      return mi;
   }

   public static int distance(String s, String t) {
      int n = s.length();
      int m = t.length();
      if (n == 0) {
         return m;
      } else if (m == 0) {
         return n;
      } else {
         int[][] d = new int[n + 1][m + 1];

         int i;
         for(i = 0; i <= n; d[i][0] = i++) {
         }

         int j;
         for(j = 0; j <= m; d[0][j] = j++) {
         }

         for(i = 1; i <= n; ++i) {
            char s_i = s.charAt(i - 1);

            for(j = 1; j <= m; ++j) {
               char t_j = t.charAt(j - 1);
               byte cost;
               if (s_i == t_j) {
                  cost = 0;
               } else {
                  cost = 1;
               }

               d[i][j] = minimum(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + cost);
            }
         }

         return d[n][m];
      }
   }
}

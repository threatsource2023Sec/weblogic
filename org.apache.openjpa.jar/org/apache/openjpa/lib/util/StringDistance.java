package org.apache.openjpa.lib.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class StringDistance {
   public static String getClosestLevenshteinDistance(String str, String[] candidates) {
      return candidates == null ? null : getClosestLevenshteinDistance(str, (Collection)Arrays.asList(candidates));
   }

   public static String getClosestLevenshteinDistance(String str, Collection candidates) {
      return getClosestLevenshteinDistance(str, candidates, Integer.MAX_VALUE);
   }

   public static String getClosestLevenshteinDistance(String str, String[] candidates, int threshold) {
      return candidates == null ? null : getClosestLevenshteinDistance(str, (Collection)Arrays.asList(candidates), threshold);
   }

   public static String getClosestLevenshteinDistance(String str, String[] candidates, float thresholdPercentage) {
      return candidates == null ? null : getClosestLevenshteinDistance(str, (Collection)Arrays.asList(candidates), thresholdPercentage);
   }

   public static String getClosestLevenshteinDistance(String str, Collection candidates, float thresholdPercentage) {
      if (str == null) {
         return null;
      } else {
         thresholdPercentage = Math.min(thresholdPercentage, 1.0F);
         thresholdPercentage = Math.max(thresholdPercentage, 0.0F);
         return getClosestLevenshteinDistance(str, candidates, (int)((float)str.length() * thresholdPercentage));
      }
   }

   public static String getClosestLevenshteinDistance(String str, Collection candidates, int threshold) {
      if (candidates != null && !candidates.isEmpty()) {
         String minString = null;
         int minValue = Integer.MAX_VALUE;
         Iterator i = candidates.iterator();

         while(i.hasNext()) {
            String candidate = (String)i.next();
            int distance = getLevenshteinDistance(str, candidate);
            if (distance < minValue) {
               minValue = distance;
               minString = candidate;
            }
         }

         if (minValue <= threshold) {
            return minString;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public static int getLevenshteinDistance(String s, String t) {
      int n = s.length();
      int m = t.length();
      if (n == 0) {
         return m;
      } else if (m == 0) {
         return n;
      } else {
         int[][] matrix = new int[n + 1][m + 1];

         int i;
         for(i = 0; i <= n; matrix[i][0] = i++) {
         }

         for(i = 0; i <= m; matrix[0][i] = i++) {
         }

         for(i = 1; i <= n; ++i) {
            int si = s.charAt(i - 1);

            for(int j = 1; j <= m; ++j) {
               int tj = t.charAt(j - 1);
               byte cost;
               if (si == tj) {
                  cost = 0;
               } else {
                  cost = 1;
               }

               matrix[i][j] = min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1, matrix[i - 1][j - 1] + cost);
            }
         }

         return matrix[n][m];
      }
   }

   private static int min(int a, int b, int c) {
      int mi = a;
      if (b < a) {
         mi = b;
      }

      if (c < mi) {
         mi = c;
      }

      return mi;
   }
}

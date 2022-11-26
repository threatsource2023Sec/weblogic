package org.python.apache.xerces.impl.xpath.regex;

final class CaseInsensitiveMap {
   private static int CHUNK_SHIFT = 10;
   private static int CHUNK_SIZE;
   private static int CHUNK_MASK;
   private static int INITIAL_CHUNK_COUNT;
   private static int[][][] caseInsensitiveMap;
   private static int LOWER_CASE_MATCH;
   private static int UPPER_CASE_MATCH;

   public static int[] get(int var0) {
      return var0 < 65536 ? getMapping(var0) : null;
   }

   private static int[] getMapping(int var0) {
      int var1 = var0 >>> CHUNK_SHIFT;
      int var2 = var0 & CHUNK_MASK;
      return caseInsensitiveMap[var1][var2];
   }

   private static void buildCaseInsensitiveMap() {
      caseInsensitiveMap = new int[INITIAL_CHUNK_COUNT][CHUNK_SIZE][];

      for(int var0 = 0; var0 < 65536; ++var0) {
         char var1 = Character.toLowerCase((char)var0);
         char var2 = Character.toUpperCase((char)var0);
         if (var1 != var2 || var1 != var0) {
            int[] var3 = new int[2];
            int var4 = 0;
            int[] var5;
            if (var1 != var0) {
               var3[var4++] = var1;
               var3[var4++] = LOWER_CASE_MATCH;
               var5 = getMapping(var1);
               if (var5 != null) {
                  var3 = updateMap(var0, var3, var1, var5, LOWER_CASE_MATCH);
               }
            }

            if (var2 != var0) {
               if (var4 == var3.length) {
                  var3 = expandMap(var3, 2);
               }

               var3[var4++] = var2;
               var3[var4++] = UPPER_CASE_MATCH;
               var5 = getMapping(var2);
               if (var5 != null) {
                  var3 = updateMap(var0, var3, var2, var5, UPPER_CASE_MATCH);
               }
            }

            set(var0, var3);
         }
      }

   }

   private static int[] expandMap(int[] var0, int var1) {
      int var2 = var0.length;
      int[] var3 = new int[var2 + var1];
      System.arraycopy(var0, 0, var3, 0, var2);
      return var3;
   }

   private static void set(int var0, int[] var1) {
      int var2 = var0 >>> CHUNK_SHIFT;
      int var3 = var0 & CHUNK_MASK;
      caseInsensitiveMap[var2][var3] = var1;
   }

   private static int[] updateMap(int var0, int[] var1, int var2, int[] var3, int var4) {
      for(int var5 = 0; var5 < var3.length; var5 += 2) {
         int var6 = var3[var5];
         int[] var7 = getMapping(var6);
         if (var7 != null && contains(var7, var2, var4)) {
            if (!contains(var7, var0)) {
               var7 = expandAndAdd(var7, var0, var4);
               set(var6, var7);
            }

            if (!contains(var1, var6)) {
               var1 = expandAndAdd(var1, var6, var4);
            }
         }
      }

      if (!contains(var3, var0)) {
         var3 = expandAndAdd(var3, var0, var4);
         set(var2, var3);
      }

      return var1;
   }

   private static boolean contains(int[] var0, int var1) {
      for(int var2 = 0; var2 < var0.length; var2 += 2) {
         if (var0[var2] == var1) {
            return true;
         }
      }

      return false;
   }

   private static boolean contains(int[] var0, int var1, int var2) {
      for(int var3 = 0; var3 < var0.length; var3 += 2) {
         if (var0[var3] == var1 && var0[var3 + 1] == var2) {
            return true;
         }
      }

      return false;
   }

   private static int[] expandAndAdd(int[] var0, int var1, int var2) {
      int var3 = var0.length;
      int[] var4 = new int[var3 + 2];
      System.arraycopy(var0, 0, var4, 0, var3);
      var4[var3] = var1;
      var4[var3 + 1] = var2;
      return var4;
   }

   static {
      CHUNK_SIZE = 1 << CHUNK_SHIFT;
      CHUNK_MASK = CHUNK_SIZE - 1;
      INITIAL_CHUNK_COUNT = 64;
      LOWER_CASE_MATCH = 1;
      UPPER_CASE_MATCH = 2;
      buildCaseInsensitiveMap();
   }
}

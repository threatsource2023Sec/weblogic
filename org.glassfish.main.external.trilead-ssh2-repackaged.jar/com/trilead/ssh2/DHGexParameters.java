package com.trilead.ssh2;

public class DHGexParameters {
   private final int min_group_len;
   private final int pref_group_len;
   private final int max_group_len;
   private static final int MIN_ALLOWED = 1024;
   private static final int MAX_ALLOWED = 8192;

   public DHGexParameters() {
      this(1024, 1024, 4096);
   }

   public DHGexParameters(int pref_group_len) {
      if (pref_group_len >= 1024 && pref_group_len <= 8192) {
         this.pref_group_len = pref_group_len;
         this.min_group_len = 0;
         this.max_group_len = 0;
      } else {
         throw new IllegalArgumentException("pref_group_len out of range!");
      }
   }

   public DHGexParameters(int min_group_len, int pref_group_len, int max_group_len) {
      if (min_group_len >= 1024 && min_group_len <= 8192) {
         if (pref_group_len >= 1024 && pref_group_len <= 8192) {
            if (max_group_len >= 1024 && max_group_len <= 8192) {
               if (pref_group_len >= min_group_len && pref_group_len <= max_group_len) {
                  if (max_group_len < min_group_len) {
                     throw new IllegalArgumentException("max_group_len must not be smaller than min_group_len!");
                  } else {
                     this.min_group_len = min_group_len;
                     this.pref_group_len = pref_group_len;
                     this.max_group_len = max_group_len;
                  }
               } else {
                  throw new IllegalArgumentException("pref_group_len is incompatible with min and max!");
               }
            } else {
               throw new IllegalArgumentException("max_group_len out of range!");
            }
         } else {
            throw new IllegalArgumentException("pref_group_len out of range!");
         }
      } else {
         throw new IllegalArgumentException("min_group_len out of range!");
      }
   }

   public int getMax_group_len() {
      return this.max_group_len;
   }

   public int getMin_group_len() {
      return this.min_group_len;
   }

   public int getPref_group_len() {
      return this.pref_group_len;
   }
}

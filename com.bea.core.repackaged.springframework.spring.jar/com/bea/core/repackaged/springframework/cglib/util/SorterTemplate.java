package com.bea.core.repackaged.springframework.cglib.util;

abstract class SorterTemplate {
   private static final int MERGESORT_THRESHOLD = 12;
   private static final int QUICKSORT_THRESHOLD = 7;

   protected abstract void swap(int var1, int var2);

   protected abstract int compare(int var1, int var2);

   protected void quickSort(int lo, int hi) {
      this.quickSortHelper(lo, hi);
      this.insertionSort(lo, hi);
   }

   private void quickSortHelper(int lo, int hi) {
      label47:
      while(true) {
         int diff = hi - lo;
         if (diff <= 7) {
            return;
         }

         int i = (hi + lo) / 2;
         if (this.compare(lo, i) > 0) {
            this.swap(lo, i);
         }

         if (this.compare(lo, hi) > 0) {
            this.swap(lo, hi);
         }

         if (this.compare(i, hi) > 0) {
            this.swap(i, hi);
         }

         int j = hi - 1;
         this.swap(i, j);
         i = lo;
         int v = j;

         while(true) {
            while(true) {
               ++i;
               if (this.compare(i, v) >= 0) {
                  do {
                     --j;
                  } while(this.compare(j, v) > 0);

                  if (j < i) {
                     this.swap(i, hi - 1);
                     if (j - lo <= hi - i + 1) {
                        this.quickSortHelper(lo, j);
                        lo = i + 1;
                        continue label47;
                     }

                     this.quickSortHelper(i + 1, hi);
                     hi = j;
                     continue label47;
                  }

                  this.swap(i, j);
               }
            }
         }
      }
   }

   private void insertionSort(int lo, int hi) {
      for(int i = lo + 1; i <= hi; ++i) {
         for(int j = i; j > lo && this.compare(j - 1, j) > 0; --j) {
            this.swap(j - 1, j);
         }
      }

   }

   protected void mergeSort(int lo, int hi) {
      int diff = hi - lo;
      if (diff <= 12) {
         this.insertionSort(lo, hi);
      } else {
         int mid = lo + diff / 2;
         this.mergeSort(lo, mid);
         this.mergeSort(mid, hi);
         this.merge(lo, mid, hi, mid - lo, hi - mid);
      }
   }

   private void merge(int lo, int pivot, int hi, int len1, int len2) {
      if (len1 != 0 && len2 != 0) {
         if (len1 + len2 == 2) {
            if (this.compare(pivot, lo) < 0) {
               this.swap(pivot, lo);
            }

         } else {
            int first_cut;
            int second_cut;
            int len11;
            int len22;
            if (len1 > len2) {
               len11 = len1 / 2;
               first_cut = lo + len11;
               second_cut = this.lower(pivot, hi, first_cut);
               len22 = second_cut - pivot;
            } else {
               len22 = len2 / 2;
               second_cut = pivot + len22;
               first_cut = this.upper(lo, pivot, second_cut);
               len11 = first_cut - lo;
            }

            this.rotate(first_cut, pivot, second_cut);
            int new_mid = first_cut + len22;
            this.merge(lo, first_cut, new_mid, len11, len22);
            this.merge(new_mid, second_cut, hi, len1 - len11, len2 - len22);
         }
      }
   }

   private void rotate(int lo, int mid, int hi) {
      int lot = lo;
      int hit = mid - 1;

      while(lot < hit) {
         this.swap(lot++, hit--);
      }

      lot = mid;
      hit = hi - 1;

      while(lot < hit) {
         this.swap(lot++, hit--);
      }

      lot = lo;
      hit = hi - 1;

      while(lot < hit) {
         this.swap(lot++, hit--);
      }

   }

   private int lower(int lo, int hi, int val) {
      int len = hi - lo;

      while(len > 0) {
         int half = len / 2;
         int mid = lo + half;
         if (this.compare(mid, val) < 0) {
            lo = mid + 1;
            len = len - half - 1;
         } else {
            len = half;
         }
      }

      return lo;
   }

   private int upper(int lo, int hi, int val) {
      int len = hi - lo;

      while(len > 0) {
         int half = len / 2;
         int mid = lo + half;
         if (this.compare(val, mid) < 0) {
            len = half;
         } else {
            lo = mid + 1;
            len = len - half - 1;
         }
      }

      return lo;
   }
}

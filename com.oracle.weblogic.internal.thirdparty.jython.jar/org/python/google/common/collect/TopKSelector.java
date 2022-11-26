package org.python.google.common.collect;

import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.common.math.IntMath;

@GwtCompatible
final class TopKSelector {
   private final int k;
   private final Comparator comparator;
   private final Object[] buffer;
   private int bufferSize;
   private Object threshold;

   public static TopKSelector least(int k) {
      return least(k, Ordering.natural());
   }

   public static TopKSelector greatest(int k) {
      return greatest(k, Ordering.natural());
   }

   public static TopKSelector least(int k, Comparator comparator) {
      return new TopKSelector(comparator, k);
   }

   public static TopKSelector greatest(int k, Comparator comparator) {
      return new TopKSelector(Ordering.from(comparator).reverse(), k);
   }

   private TopKSelector(Comparator comparator, int k) {
      this.comparator = (Comparator)Preconditions.checkNotNull(comparator, "comparator");
      this.k = k;
      Preconditions.checkArgument(k >= 0, "k must be nonnegative, was %s", k);
      this.buffer = (Object[])(new Object[k * 2]);
      this.bufferSize = 0;
      this.threshold = null;
   }

   public void offer(@Nullable Object elem) {
      if (this.k != 0) {
         if (this.bufferSize == 0) {
            this.buffer[0] = elem;
            this.threshold = elem;
            this.bufferSize = 1;
         } else if (this.bufferSize < this.k) {
            this.buffer[this.bufferSize++] = elem;
            if (this.comparator.compare(elem, this.threshold) > 0) {
               this.threshold = elem;
            }
         } else if (this.comparator.compare(elem, this.threshold) < 0) {
            this.buffer[this.bufferSize++] = elem;
            if (this.bufferSize == 2 * this.k) {
               this.trim();
            }
         }

      }
   }

   private void trim() {
      int left = 0;
      int right = 2 * this.k - 1;
      int minThresholdPosition = 0;
      int iterations = 0;
      int maxIterations = IntMath.log2(right - left, RoundingMode.CEILING) * 3;

      int i;
      while(left < right) {
         i = left + right + 1 >>> 1;
         int pivotNewIndex = this.partition(left, right, i);
         if (pivotNewIndex > this.k) {
            right = pivotNewIndex - 1;
         } else {
            if (pivotNewIndex >= this.k) {
               break;
            }

            left = Math.max(pivotNewIndex, left + 1);
            minThresholdPosition = pivotNewIndex;
         }

         ++iterations;
         if (iterations >= maxIterations) {
            Arrays.sort(this.buffer, left, right, this.comparator);
            break;
         }
      }

      this.bufferSize = this.k;
      this.threshold = this.buffer[minThresholdPosition];

      for(i = minThresholdPosition + 1; i < this.k; ++i) {
         if (this.comparator.compare(this.buffer[i], this.threshold) > 0) {
            this.threshold = this.buffer[i];
         }
      }

   }

   private int partition(int left, int right, int pivotIndex) {
      Object pivotValue = this.buffer[pivotIndex];
      this.buffer[pivotIndex] = this.buffer[right];
      int pivotNewIndex = left;

      for(int i = left; i < right; ++i) {
         if (this.comparator.compare(this.buffer[i], pivotValue) < 0) {
            this.swap(pivotNewIndex, i);
            ++pivotNewIndex;
         }
      }

      this.buffer[right] = this.buffer[pivotNewIndex];
      this.buffer[pivotNewIndex] = pivotValue;
      return pivotNewIndex;
   }

   private void swap(int i, int j) {
      Object tmp = this.buffer[i];
      this.buffer[i] = this.buffer[j];
      this.buffer[j] = tmp;
   }

   public void offerAll(Iterable elements) {
      this.offerAll(elements.iterator());
   }

   public void offerAll(Iterator elements) {
      while(elements.hasNext()) {
         this.offer(elements.next());
      }

   }

   public List topK() {
      Arrays.sort(this.buffer, 0, this.bufferSize, this.comparator);
      if (this.bufferSize > this.k) {
         Arrays.fill(this.buffer, this.k, this.buffer.length, (Object)null);
         this.bufferSize = this.k;
         this.threshold = this.buffer[this.k - 1];
      }

      return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(this.buffer, this.bufferSize)));
   }
}

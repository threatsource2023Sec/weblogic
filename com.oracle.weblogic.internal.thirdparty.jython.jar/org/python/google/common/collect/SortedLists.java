package org.python.google.common.collect;

import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Function;
import org.python.google.common.base.Preconditions;

@GwtCompatible
@Beta
final class SortedLists {
   private SortedLists() {
   }

   public static int binarySearch(List list, Comparable e, KeyPresentBehavior presentBehavior, KeyAbsentBehavior absentBehavior) {
      Preconditions.checkNotNull(e);
      return binarySearch(list, (Object)e, (Comparator)Ordering.natural(), presentBehavior, absentBehavior);
   }

   public static int binarySearch(List list, Function keyFunction, @Nullable Comparable key, KeyPresentBehavior presentBehavior, KeyAbsentBehavior absentBehavior) {
      return binarySearch(list, keyFunction, key, Ordering.natural(), presentBehavior, absentBehavior);
   }

   public static int binarySearch(List list, Function keyFunction, @Nullable Object key, Comparator keyComparator, KeyPresentBehavior presentBehavior, KeyAbsentBehavior absentBehavior) {
      return binarySearch(Lists.transform(list, keyFunction), key, keyComparator, presentBehavior, absentBehavior);
   }

   public static int binarySearch(List list, @Nullable Object key, Comparator comparator, KeyPresentBehavior presentBehavior, KeyAbsentBehavior absentBehavior) {
      Preconditions.checkNotNull(comparator);
      Preconditions.checkNotNull(list);
      Preconditions.checkNotNull(presentBehavior);
      Preconditions.checkNotNull(absentBehavior);
      if (!(list instanceof RandomAccess)) {
         list = Lists.newArrayList((Iterable)list);
      }

      int lower = 0;
      int upper = ((List)list).size() - 1;

      while(lower <= upper) {
         int middle = lower + upper >>> 1;
         int c = comparator.compare(key, ((List)list).get(middle));
         if (c < 0) {
            upper = middle - 1;
         } else {
            if (c <= 0) {
               return lower + presentBehavior.resultIndex(comparator, key, ((List)list).subList(lower, upper + 1), middle - lower);
            }

            lower = middle + 1;
         }
      }

      return absentBehavior.resultIndex(lower);
   }

   public static enum KeyAbsentBehavior {
      NEXT_LOWER {
         int resultIndex(int higherIndex) {
            return higherIndex - 1;
         }
      },
      NEXT_HIGHER {
         public int resultIndex(int higherIndex) {
            return higherIndex;
         }
      },
      INVERTED_INSERTION_INDEX {
         public int resultIndex(int higherIndex) {
            return ~higherIndex;
         }
      };

      private KeyAbsentBehavior() {
      }

      abstract int resultIndex(int var1);

      // $FF: synthetic method
      KeyAbsentBehavior(Object x2) {
         this();
      }
   }

   public static enum KeyPresentBehavior {
      ANY_PRESENT {
         int resultIndex(Comparator comparator, Object key, List list, int foundIndex) {
            return foundIndex;
         }
      },
      LAST_PRESENT {
         int resultIndex(Comparator comparator, Object key, List list, int foundIndex) {
            int lower = foundIndex;
            int upper = list.size() - 1;

            while(lower < upper) {
               int middle = lower + upper + 1 >>> 1;
               int c = comparator.compare(list.get(middle), key);
               if (c > 0) {
                  upper = middle - 1;
               } else {
                  lower = middle;
               }
            }

            return lower;
         }
      },
      FIRST_PRESENT {
         int resultIndex(Comparator comparator, Object key, List list, int foundIndex) {
            int lower = 0;
            int upper = foundIndex;

            while(lower < upper) {
               int middle = lower + upper >>> 1;
               int c = comparator.compare(list.get(middle), key);
               if (c < 0) {
                  lower = middle + 1;
               } else {
                  upper = middle;
               }
            }

            return lower;
         }
      },
      FIRST_AFTER {
         public int resultIndex(Comparator comparator, Object key, List list, int foundIndex) {
            return LAST_PRESENT.resultIndex(comparator, key, list, foundIndex) + 1;
         }
      },
      LAST_BEFORE {
         public int resultIndex(Comparator comparator, Object key, List list, int foundIndex) {
            return FIRST_PRESENT.resultIndex(comparator, key, list, foundIndex) - 1;
         }
      };

      private KeyPresentBehavior() {
      }

      abstract int resultIndex(Comparator var1, Object var2, List var3, int var4);

      // $FF: synthetic method
      KeyPresentBehavior(Object x2) {
         this();
      }
   }
}

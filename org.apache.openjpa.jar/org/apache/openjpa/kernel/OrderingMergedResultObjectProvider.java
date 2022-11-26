package org.apache.openjpa.kernel;

import java.util.Comparator;
import org.apache.openjpa.lib.rop.MergedResultObjectProvider;
import org.apache.openjpa.lib.rop.ResultObjectProvider;

public class OrderingMergedResultObjectProvider extends MergedResultObjectProvider {
   private final StoreQuery.Executor[] _execs;
   private final StoreQuery _query;
   private final Object[] _params;
   private final int _orderings;

   public OrderingMergedResultObjectProvider(ResultObjectProvider[] rops, boolean[] asc, StoreQuery.Executor exec, StoreQuery q, Object[] params) {
      this(rops, asc, new StoreQuery.Executor[]{exec}, q, params);
   }

   public OrderingMergedResultObjectProvider(ResultObjectProvider[] rops, boolean[] asc, StoreQuery.Executor[] execs, StoreQuery q, Object[] params) {
      super(rops, new OrderingComparator(asc));
      this._orderings = asc.length;
      this._execs = execs;
      this._query = q;
      this._params = params;
   }

   protected Object getOrderingValue(Object val, int idx, ResultObjectProvider rop) {
      StoreQuery.Executor exec = this._execs.length == 1 ? this._execs[0] : this._execs[idx];
      if (this._orderings == 1) {
         return exec.getOrderingValue(this._query, this._params, val, 0);
      } else {
         Object[] ret = new Object[this._orderings];

         for(int i = 0; i < this._orderings; ++i) {
            ret[i] = exec.getOrderingValue(this._query, this._params, val, i);
         }

         return ret;
      }
   }

   private static class OrderingComparator implements Comparator {
      private final boolean[] _asc;

      public OrderingComparator(boolean[] asc) {
         this._asc = asc;
      }

      public int compare(Object o1, Object o2) {
         if (this._asc.length == 1) {
            return cmp(o1, o2, this._asc[0]);
         } else {
            Object[] arr1 = (Object[])((Object[])o1);
            Object[] arr2 = (Object[])((Object[])o2);

            for(int i = 0; i < this._asc.length; ++i) {
               int cmp = cmp(arr1[i], arr2[i], this._asc[i]);
               if (cmp != 0) {
                  return cmp;
               }
            }

            return 0;
         }
      }

      private static int cmp(Object o1, Object o2, boolean asc) {
         if (o1 == null && o2 == null) {
            return 0;
         } else if (o1 == null) {
            return asc ? 1 : -1;
         } else if (o2 == null) {
            return asc ? -1 : 1;
         } else {
            int cmp = ((Comparable)o1).compareTo(o2);
            if (!asc) {
               cmp *= -1;
            }

            return cmp;
         }
      }
   }
}

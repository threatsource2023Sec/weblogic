package org.apache.openjpa.slice.jdbc;

import org.apache.openjpa.kernel.StoreQuery;
import org.apache.openjpa.kernel.exps.QueryExpressions;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InternalException;

public class UniqueResultObjectProvider implements ResultObjectProvider {
   private final ResultObjectProvider[] _rops;
   private final StoreQuery _query;
   private final QueryExpressions[] _exps;
   private Object _single;
   private boolean _opened;
   private static final String COUNT = "Count";
   private static final String MAX = "Max";
   private static final String MIN = "Min";
   private static final String SUM = "Sum";
   private static final Localizer _loc = Localizer.forPackage(UniqueResultObjectProvider.class);

   public UniqueResultObjectProvider(ResultObjectProvider[] rops, StoreQuery q, QueryExpressions[] exps) {
      this._rops = rops;
      this._query = q;
      this._exps = exps;
   }

   public boolean absolute(int pos) throws Exception {
      return false;
   }

   public void close() throws Exception {
      this._opened = false;
      ResultObjectProvider[] arr$ = this._rops;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ResultObjectProvider rop = arr$[i$];
         rop.close();
      }

   }

   public Object getResultObject() throws Exception {
      if (!this._opened) {
         throw new InternalException(_loc.get("not-open"));
      } else {
         return this._single;
      }
   }

   public void handleCheckedException(Exception e) {
      this._rops[0].handleCheckedException(e);
   }

   public boolean next() throws Exception {
      if (!this._opened) {
         this.open();
      }

      if (this._single != null) {
         return false;
      } else {
         Value[] values = this._exps[0].projections;
         Object[] single = new Object[values.length];

         for(int i = 0; i < values.length; ++i) {
            Value v = values[i];
            boolean isAggregate = v.isAggregate();
            String op = v.getClass().getSimpleName();
            ResultObjectProvider[] arr$ = this._rops;
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               ResultObjectProvider rop = arr$[i$];
               rop.next();
               Object[] row = (Object[])((Object[])rop.getResultObject());
               if (isAggregate) {
                  if ("Count".equals(op)) {
                     single[i] = this.count(single[i], row[i]);
                  } else if ("Max".equals(op)) {
                     single[i] = this.max(single[i], row[i]);
                  } else if ("Min".equals(op)) {
                     single[i] = this.min(single[i], row[i]);
                  } else {
                     if (!"Sum".equals(op)) {
                        throw new UnsupportedOperationException(_loc.get("aggregate-unsupported", (Object)op).toString());
                     }

                     single[i] = this.sum(single[i], row[i]);
                  }
               } else {
                  single[i] = row[i];
               }
            }
         }

         this._single = single;
         return true;
      }
   }

   Object count(Object current, Object other) {
      return current == null ? other : ((Number)current).longValue() + ((Number)other).longValue();
   }

   Object max(Object current, Object other) {
      return current == null ? other : Math.max(((Number)current).doubleValue(), ((Number)other).doubleValue());
   }

   Object min(Object current, Object other) {
      return current == null ? other : Math.min(((Number)current).doubleValue(), ((Number)other).doubleValue());
   }

   Object sum(Object current, Object other) {
      return current == null ? other : ((Number)current).doubleValue() + ((Number)other).doubleValue();
   }

   public void open() throws Exception {
      ResultObjectProvider[] arr$ = this._rops;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ResultObjectProvider rop = arr$[i$];
         rop.open();
      }

      this._opened = true;
   }

   public void reset() throws Exception {
      this._single = null;
      ResultObjectProvider[] arr$ = this._rops;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ResultObjectProvider rop = arr$[i$];
         rop.reset();
      }

   }

   public int size() throws Exception {
      return 1;
   }

   public boolean supportsRandomAccess() {
      return false;
   }
}

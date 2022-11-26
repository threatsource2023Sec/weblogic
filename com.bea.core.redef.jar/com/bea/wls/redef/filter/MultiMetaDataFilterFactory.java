package com.bea.wls.redef.filter;

import com.bea.wls.redef.ConstructorMetaData;
import com.bea.wls.redef.FieldMetaData;
import com.bea.wls.redef.MethodMetaData;
import java.util.ArrayList;
import java.util.List;
import serp.bytecode.lowlevel.ConstantPoolTable;

public class MultiMetaDataFilterFactory implements MetaDataFilterFactory {
   private final List _delegates = new ArrayList();

   public synchronized boolean addFactory(MetaDataFilterFactory factory) {
      if (factory != null && !this._delegates.contains(factory)) {
         this._delegates.add(factory);
         return true;
      } else {
         return false;
      }
   }

   public synchronized MetaDataFilter newFilter(String clsName, Class prevCls, MetaDataFilter prev, ConstantPoolTable table, byte[] bytes) {
      MetaDataFilter[] filters = null;

      for(int i = 0; i < this._delegates.size(); ++i) {
         MetaDataFilterFactory factory = (MetaDataFilterFactory)this._delegates.get(i);
         MetaDataFilter prevFilter = this.getPreviousFilter(prev, i);
         MetaDataFilter filter = factory.newFilter(clsName, prevCls, prevFilter, table, bytes);
         if (filter == null) {
            return null;
         }

         if (filters != null) {
            filters[i] = filter;
         } else if (filter != NullMetaDataFilter.NULL_FILTER) {
            filters = new MetaDataFilter[this._delegates.size()];

            for(int j = 0; j < i; ++j) {
               filters[j] = NullMetaDataFilter.NULL_FILTER;
            }

            filters[i] = filter;
         }
      }

      return (MetaDataFilter)(filters == null ? NullMetaDataFilter.NULL_FILTER : new MultiMetaDataFilter(filters));
   }

   private MetaDataFilter getPreviousFilter(MetaDataFilter prev, int i) {
      return prev != null && prev != NullMetaDataFilter.NULL_FILTER ? ((MultiMetaDataFilter)prev)._delegates[i] : prev;
   }

   private static class MultiMetaDataFilter implements MetaDataFilter {
      private final MetaDataFilter[] _delegates;

      public MultiMetaDataFilter(MetaDataFilter[] delegates) {
         this._delegates = delegates;
      }

      public boolean eval(FieldMetaData field) {
         MetaDataFilter[] var2 = this._delegates;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MetaDataFilter filter = var2[var4];
            if (!filter.eval(field)) {
               return false;
            }
         }

         return true;
      }

      public boolean eval(ConstructorMetaData cons) {
         MetaDataFilter[] var2 = this._delegates;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MetaDataFilter filter = var2[var4];
            if (!filter.eval(cons)) {
               return false;
            }
         }

         return true;
      }

      public boolean eval(MethodMetaData method) {
         MetaDataFilter[] var2 = this._delegates;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MetaDataFilter filter = var2[var4];
            if (!filter.eval(method)) {
               return false;
            }
         }

         return true;
      }
   }
}

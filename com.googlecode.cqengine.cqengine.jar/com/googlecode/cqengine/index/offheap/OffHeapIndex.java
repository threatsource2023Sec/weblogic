package com.googlecode.cqengine.index.offheap;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.index.sqlite.SimplifiedSQLiteIndex;
import com.googlecode.cqengine.index.support.indextype.OffHeapTypeIndex;
import com.googlecode.cqengine.persistence.offheap.OffHeapPersistence;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.common.WrappedResultSet;

public class OffHeapIndex extends SimplifiedSQLiteIndex implements OffHeapTypeIndex {
   static final int INDEX_RETRIEVAL_COST_DELTA = -10;

   OffHeapIndex(Class persistenceType, Attribute attribute, String tableNameSuffix) {
      super(persistenceType, attribute, tableNameSuffix);
   }

   public ResultSet retrieve(Query query, QueryOptions queryOptions) {
      return new WrappedResultSet(super.retrieve(query, queryOptions)) {
         public int getRetrievalCost() {
            return super.getRetrievalCost() + -10;
         }
      };
   }

   public static OffHeapIndex onAttribute(Attribute attribute) {
      return new OffHeapIndex(OffHeapPersistence.class, attribute, "");
   }
}

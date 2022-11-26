package com.googlecode.cqengine.index.offheap;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.sqlite.support.DBUtils;
import com.googlecode.cqengine.index.support.PartialSortedKeyStatisticsAttributeIndex;
import com.googlecode.cqengine.index.support.SortedKeyStatisticsAttributeIndex;
import com.googlecode.cqengine.index.support.indextype.OffHeapTypeIndex;
import com.googlecode.cqengine.persistence.offheap.OffHeapPersistence;
import com.googlecode.cqengine.query.Query;

public class PartialOffHeapIndex extends PartialSortedKeyStatisticsAttributeIndex implements OffHeapTypeIndex {
   final String tableNameSuffix;

   protected PartialOffHeapIndex(Attribute attribute, Query filterQuery) {
      super(attribute, filterQuery);
      this.tableNameSuffix = "_partial_" + DBUtils.sanitizeForTableName(filterQuery.toString());
   }

   protected SortedKeyStatisticsAttributeIndex createBackingIndex() {
      return new OffHeapIndex(OffHeapPersistence.class, this.attribute, this.tableNameSuffix) {
         public Index getEffectiveIndex() {
            return PartialOffHeapIndex.this.getEffectiveIndex();
         }
      };
   }

   public static PartialOffHeapIndex onAttributeWithFilterQuery(Attribute attribute, Query filterQuery) {
      return new PartialOffHeapIndex(attribute, filterQuery);
   }
}

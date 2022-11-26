package com.googlecode.cqengine.index.disk;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.sqlite.support.DBUtils;
import com.googlecode.cqengine.index.support.PartialSortedKeyStatisticsAttributeIndex;
import com.googlecode.cqengine.index.support.SortedKeyStatisticsAttributeIndex;
import com.googlecode.cqengine.index.support.indextype.DiskTypeIndex;
import com.googlecode.cqengine.persistence.disk.DiskPersistence;
import com.googlecode.cqengine.query.Query;

public class PartialDiskIndex extends PartialSortedKeyStatisticsAttributeIndex implements DiskTypeIndex {
   final String tableNameSuffix;

   protected PartialDiskIndex(Attribute attribute, Query filterQuery) {
      super(attribute, filterQuery);
      this.tableNameSuffix = "_partial_" + DBUtils.sanitizeForTableName(filterQuery.toString());
   }

   protected SortedKeyStatisticsAttributeIndex createBackingIndex() {
      return new DiskIndex(DiskPersistence.class, this.attribute, this.tableNameSuffix) {
         public Index getEffectiveIndex() {
            return PartialDiskIndex.this.getEffectiveIndex();
         }
      };
   }

   public static PartialDiskIndex onAttributeWithFilterQuery(Attribute attribute, Query filterQuery) {
      return new PartialDiskIndex(attribute, filterQuery);
   }
}

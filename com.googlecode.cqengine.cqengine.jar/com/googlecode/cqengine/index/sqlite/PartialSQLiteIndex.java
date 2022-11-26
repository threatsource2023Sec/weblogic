package com.googlecode.cqengine.index.sqlite;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.sqlite.support.DBUtils;
import com.googlecode.cqengine.index.support.PartialSortedKeyStatisticsAttributeIndex;
import com.googlecode.cqengine.index.support.SortedKeyStatisticsAttributeIndex;
import com.googlecode.cqengine.index.support.indextype.NonHeapTypeIndex;
import com.googlecode.cqengine.query.Query;

public class PartialSQLiteIndex extends PartialSortedKeyStatisticsAttributeIndex implements NonHeapTypeIndex {
   final SimpleAttribute primaryKeyAttribute;
   final SimpleAttribute foreignKeyAttribute;
   final String tableNameSuffix;

   protected PartialSQLiteIndex(Attribute attribute, SimpleAttribute primaryKeyAttribute, SimpleAttribute foreignKeyAttribute, Query filterQuery) {
      super(attribute, filterQuery);
      this.primaryKeyAttribute = primaryKeyAttribute;
      this.foreignKeyAttribute = foreignKeyAttribute;
      this.tableNameSuffix = "_partial_" + DBUtils.sanitizeForTableName(filterQuery.toString());
   }

   protected SortedKeyStatisticsAttributeIndex createBackingIndex() {
      return new SQLiteIndex(this.attribute, this.primaryKeyAttribute, this.foreignKeyAttribute, this.tableNameSuffix) {
         public Index getEffectiveIndex() {
            return PartialSQLiteIndex.this.getEffectiveIndex();
         }
      };
   }

   public static PartialSQLiteIndex onAttributeWithFilterQuery(Attribute attribute, SimpleAttribute primaryKeyAttribute, SimpleAttribute foreignKeyAttribute, Query filterQuery) {
      return new PartialSQLiteIndex(attribute, primaryKeyAttribute, foreignKeyAttribute, filterQuery);
   }
}

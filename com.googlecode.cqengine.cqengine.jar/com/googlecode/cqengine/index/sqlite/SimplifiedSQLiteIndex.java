package com.googlecode.cqengine.index.sqlite;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.engine.QueryEngine;
import com.googlecode.cqengine.index.AttributeIndex;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.support.CloseableIterable;
import com.googlecode.cqengine.index.support.SortedKeyStatisticsAttributeIndex;
import com.googlecode.cqengine.index.support.indextype.NonHeapTypeIndex;
import com.googlecode.cqengine.persistence.Persistence;
import com.googlecode.cqengine.persistence.support.ObjectSet;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.QueryFactory;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import java.util.Iterator;

public abstract class SimplifiedSQLiteIndex implements SortedKeyStatisticsAttributeIndex, NonHeapTypeIndex {
   final Class persistenceType;
   final Attribute attribute;
   final String tableNameSuffix;
   volatile SQLiteIndex backingIndex;

   protected SimplifiedSQLiteIndex(Class persistenceType, Attribute attribute, String tableNameSuffix) {
      this.persistenceType = persistenceType;
      this.attribute = attribute;
      this.tableNameSuffix = tableNameSuffix;
   }

   public void init(ObjectStore objectStore, QueryOptions queryOptions) {
      Persistence persistence = getPersistenceFromQueryOptions(queryOptions);
      QueryEngine queryEngine = getQueryEngineFromQueryOptions(queryOptions);
      final SimpleAttribute primaryKeyAttribute = this.getPrimaryKeyFromPersistence(persistence);
      final AttributeIndex primaryKeyIndex = this.getPrimaryKeyIndexFromQueryEngine(primaryKeyAttribute, queryEngine, queryOptions);
      SimpleAttribute foreignKeyAttribute = new SimpleAttribute(primaryKeyAttribute.getAttributeType(), primaryKeyAttribute.getObjectType()) {
         public Object getValue(Comparable primaryKeyValue, QueryOptions queryOptions) {
            return primaryKeyIndex.retrieve(QueryFactory.equal(primaryKeyAttribute, primaryKeyValue), queryOptions).uniqueResult();
         }
      };
      this.backingIndex = new SQLiteIndex(this.attribute, primaryKeyAttribute, foreignKeyAttribute, this.tableNameSuffix) {
         public Index getEffectiveIndex() {
            return SimplifiedSQLiteIndex.this.getEffectiveIndex();
         }
      };
      this.backingIndex.init(objectStore, queryOptions);
   }

   public Index getEffectiveIndex() {
      return this;
   }

   static Persistence getPersistenceFromQueryOptions(QueryOptions queryOptions) {
      Persistence persistence = (Persistence)queryOptions.get(Persistence.class);
      if (persistence == null) {
         throw new IllegalStateException("A required Persistence object was not supplied in query options");
      } else {
         return persistence;
      }
   }

   static QueryEngine getQueryEngineFromQueryOptions(QueryOptions queryOptions) {
      QueryEngine queryEngine = (QueryEngine)queryOptions.get(QueryEngine.class);
      if (queryEngine == null) {
         throw new IllegalStateException("The QueryEngine was not supplied in query options");
      } else {
         return queryEngine;
      }
   }

   SimpleAttribute getPrimaryKeyFromPersistence(Persistence persistence) {
      SimpleAttribute primaryKey = persistence.getPrimaryKeyAttribute();
      if (primaryKey == null) {
         throw new IllegalStateException("This index " + this.getClass().getSimpleName() + " on attribute '" + this.attribute.getAttributeName() + "' cannot be added to the IndexedCollection, because the configured persistence was not configured with a primary key attribute.");
      } else {
         return primaryKey;
      }
   }

   AttributeIndex getPrimaryKeyIndexFromQueryEngine(SimpleAttribute primaryKeyAttribute, QueryEngine queryEngine, QueryOptions queryOptions) {
      Iterator var4 = queryEngine.getIndexes().iterator();

      while(var4.hasNext()) {
         Index index = (Index)var4.next();
         if (index instanceof AttributeIndex) {
            AttributeIndex attributeIndex = (AttributeIndex)index;
            if (primaryKeyAttribute.equals(attributeIndex.getAttribute()) && attributeIndex.supportsQuery(QueryFactory.equal(primaryKeyAttribute, (Object)null), queryOptions)) {
               return attributeIndex;
            }
         }
      }

      throw new IllegalStateException("This index " + this.getClass().getSimpleName() + " on attribute '" + this.attribute.getAttributeName() + "' cannot be added to the IndexedCollection yet, because it requires that an index on the primary key to be added first.");
   }

   SQLiteIndex backingIndex() {
      SQLiteIndex backingIndex = this.backingIndex;
      if (backingIndex == null) {
         throw new IllegalStateException("This index can only be used after it has been added to an IndexedCollection");
      } else {
         return backingIndex;
      }
   }

   public Attribute getAttribute() {
      return this.attribute;
   }

   public boolean isMutable() {
      return true;
   }

   public boolean supportsQuery(Query query, QueryOptions queryOptions) {
      return this.backingIndex().supportsQuery(query, queryOptions);
   }

   public boolean isQuantized() {
      return this.backingIndex().isQuantized();
   }

   public ResultSet retrieve(Query query, QueryOptions queryOptions) {
      return this.backingIndex().retrieve(query, queryOptions);
   }

   public boolean addAll(ObjectSet objectSet, QueryOptions queryOptions) {
      return this.backingIndex().addAll(objectSet, queryOptions);
   }

   public boolean removeAll(ObjectSet objectSet, QueryOptions queryOptions) {
      return this.backingIndex().removeAll(objectSet, queryOptions);
   }

   public void clear(QueryOptions queryOptions) {
      this.backingIndex().clear(queryOptions);
   }

   public CloseableIterable getDistinctKeys(QueryOptions queryOptions) {
      return this.backingIndex().getDistinctKeys(queryOptions);
   }

   public CloseableIterable getDistinctKeys(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return this.backingIndex().getDistinctKeys(lowerBound, lowerInclusive, upperBound, upperInclusive, queryOptions);
   }

   public CloseableIterable getDistinctKeysDescending(QueryOptions queryOptions) {
      return this.backingIndex().getDistinctKeysDescending(queryOptions);
   }

   public CloseableIterable getDistinctKeysDescending(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return this.backingIndex().getDistinctKeysDescending(lowerBound, lowerInclusive, upperBound, upperInclusive, queryOptions);
   }

   public Integer getCountForKey(Comparable key, QueryOptions queryOptions) {
      return this.backingIndex().getCountForKey(key, queryOptions);
   }

   public CloseableIterable getStatisticsForDistinctKeysDescending(QueryOptions queryOptions) {
      return this.backingIndex().getStatisticsForDistinctKeysDescending(queryOptions);
   }

   public Integer getCountOfDistinctKeys(QueryOptions queryOptions) {
      return this.backingIndex().getCountOfDistinctKeys(queryOptions);
   }

   public CloseableIterable getStatisticsForDistinctKeys(QueryOptions queryOptions) {
      return this.backingIndex().getStatisticsForDistinctKeys(queryOptions);
   }

   public CloseableIterable getKeysAndValues(QueryOptions queryOptions) {
      return this.backingIndex().getKeysAndValues(queryOptions);
   }

   public CloseableIterable getKeysAndValues(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return this.backingIndex().getKeysAndValues(lowerBound, lowerInclusive, upperBound, upperInclusive, queryOptions);
   }

   public CloseableIterable getKeysAndValuesDescending(QueryOptions queryOptions) {
      return this.backingIndex().getKeysAndValuesDescending(queryOptions);
   }

   public CloseableIterable getKeysAndValuesDescending(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return this.backingIndex().getKeysAndValuesDescending(lowerBound, lowerInclusive, upperBound, upperInclusive, queryOptions);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         SimplifiedSQLiteIndex that = (SimplifiedSQLiteIndex)o;
         return this.attribute.equals(that.attribute);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.getClass().hashCode();
      result = 31 * result + this.attribute.hashCode();
      return result;
   }
}

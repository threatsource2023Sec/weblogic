package com.googlecode.cqengine.index.compound;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.compound.support.CompoundAttribute;
import com.googlecode.cqengine.index.compound.support.CompoundQuery;
import com.googlecode.cqengine.index.compound.support.CompoundValueTuple;
import com.googlecode.cqengine.index.support.AbstractMapBasedAttributeIndex;
import com.googlecode.cqengine.index.support.CloseableIterable;
import com.googlecode.cqengine.index.support.Factory;
import com.googlecode.cqengine.index.support.KeyStatisticsAttributeIndex;
import com.googlecode.cqengine.index.support.indextype.OnHeapTypeIndex;
import com.googlecode.cqengine.quantizer.Quantizer;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.filter.QuantizedResultSet;
import com.googlecode.cqengine.resultset.stored.StoredResultSet;
import com.googlecode.cqengine.resultset.stored.StoredSetBasedResultSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CompoundIndex extends AbstractMapBasedAttributeIndex implements KeyStatisticsAttributeIndex, OnHeapTypeIndex {
   protected static final int INDEX_RETRIEVAL_COST = 20;
   protected final CompoundAttribute attribute;

   protected CompoundIndex(Factory indexMapFactory, Factory valueSetFactory, CompoundAttribute attribute) {
      super(indexMapFactory, valueSetFactory, attribute, Collections.emptySet());
      this.attribute = attribute;
   }

   public boolean supportsQuery(Query query, QueryOptions queryOptions) {
      if (query instanceof CompoundQuery) {
         CompoundQuery compoundQuery = (CompoundQuery)query;
         return this.attribute.equals(compoundQuery.getCompoundAttribute());
      } else {
         return false;
      }
   }

   public CompoundAttribute getAttribute() {
      return this.attribute;
   }

   public boolean isMutable() {
      return true;
   }

   public Index getEffectiveIndex() {
      return this;
   }

   public ResultSet retrieve(final Query query, final QueryOptions queryOptions) {
      Class queryClass = query.getClass();
      if (queryClass.equals(CompoundQuery.class)) {
         final CompoundQuery compoundQuery = (CompoundQuery)query;
         final CompoundValueTuple valueTuple = compoundQuery.getCompoundValueTuple();
         return new ResultSet() {
            public Iterator iterator() {
               ResultSet rs = (ResultSet)CompoundIndex.this.indexMap.get(CompoundIndex.this.getQuantizedValue(valueTuple));
               return rs == null ? Collections.emptySet().iterator() : CompoundIndex.this.filterForQuantization(rs, compoundQuery, queryOptions).iterator();
            }

            public boolean contains(Object object) {
               ResultSet rs = (ResultSet)CompoundIndex.this.indexMap.get(CompoundIndex.this.getQuantizedValue(valueTuple));
               return rs != null && CompoundIndex.this.filterForQuantization(rs, compoundQuery, queryOptions).contains(object);
            }

            public boolean matches(Object object) {
               return query.matches(object, queryOptions);
            }

            public int size() {
               ResultSet rs = (ResultSet)CompoundIndex.this.indexMap.get(CompoundIndex.this.getQuantizedValue(valueTuple));
               return rs == null ? 0 : CompoundIndex.this.filterForQuantization(rs, compoundQuery, queryOptions).size();
            }

            public int getRetrievalCost() {
               return 20;
            }

            public int getMergeCost() {
               ResultSet rs = (ResultSet)CompoundIndex.this.indexMap.get(CompoundIndex.this.getQuantizedValue(valueTuple));
               return rs == null ? 0 : rs.size();
            }

            public void close() {
            }

            public Query getQuery() {
               return query;
            }

            public QueryOptions getQueryOptions() {
               return queryOptions;
            }
         };
      } else {
         throw new IllegalArgumentException("Unsupported query: " + query);
      }
   }

   public CloseableIterable getDistinctKeys(QueryOptions queryOptions) {
      return super.getDistinctKeys();
   }

   public Integer getCountForKey(CompoundValueTuple key, QueryOptions queryOptions) {
      return super.getCountForKey(key);
   }

   public Integer getCountOfDistinctKeys(QueryOptions queryOptions) {
      return super.getCountOfDistinctKeys(queryOptions);
   }

   public CloseableIterable getStatisticsForDistinctKeys(QueryOptions queryOptions) {
      return super.getStatisticsForDistinctKeys(queryOptions);
   }

   public CloseableIterable getKeysAndValues(QueryOptions queryOptions) {
      return super.getKeysAndValues();
   }

   protected ResultSet filterForQuantization(ResultSet storedResultSet, Query query, QueryOptions queryOptions) {
      return storedResultSet;
   }

   public static CompoundIndex onAttributes(Attribute... attributes) {
      return onAttributes(new DefaultIndexMapFactory(), new DefaultValueSetFactory(), attributes);
   }

   public static CompoundIndex onAttributes(Factory indexMapFactory, Factory valueSetFactory, Attribute... attributes) {
      List attributeList = Arrays.asList(attributes);
      CompoundAttribute compoundAttribute = new CompoundAttribute(attributeList);
      return new CompoundIndex(indexMapFactory, valueSetFactory, compoundAttribute);
   }

   public static CompoundIndex withQuantizerOnAttributes(Quantizer quantizer, Attribute... attributes) {
      return withQuantizerOnAttributes(new DefaultIndexMapFactory(), new DefaultValueSetFactory(), quantizer, attributes);
   }

   public static CompoundIndex withQuantizerOnAttributes(Factory indexMapFactory, Factory valueSetFactory, final Quantizer quantizer, Attribute... attributes) {
      List attributeList = Arrays.asList(attributes);
      CompoundAttribute compoundAttribute = new CompoundAttribute(attributeList);
      return new CompoundIndex(indexMapFactory, valueSetFactory, compoundAttribute) {
         protected ResultSet filterForQuantization(ResultSet resultSet, Query query, QueryOptions queryOptions) {
            return new QuantizedResultSet(resultSet, query, queryOptions);
         }

         protected CompoundValueTuple getQuantizedValue(CompoundValueTuple attributeValue) {
            return (CompoundValueTuple)quantizer.getQuantizedValue(attributeValue);
         }

         public boolean isQuantized() {
            return true;
         }
      };
   }

   public static class DefaultValueSetFactory implements Factory {
      public StoredResultSet create() {
         return new StoredSetBasedResultSet(Collections.newSetFromMap(new ConcurrentHashMap()));
      }
   }

   public static class DefaultIndexMapFactory implements Factory {
      public ConcurrentMap create() {
         return new ConcurrentHashMap();
      }
   }
}

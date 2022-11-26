package com.googlecode.cqengine.index.hash;

import com.googlecode.concurrenttrees.common.LazyIterator;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.support.AbstractMapBasedAttributeIndex;
import com.googlecode.cqengine.index.support.CloseableIterable;
import com.googlecode.cqengine.index.support.Factory;
import com.googlecode.cqengine.index.support.IndexSupport;
import com.googlecode.cqengine.index.support.KeyStatisticsAttributeIndex;
import com.googlecode.cqengine.index.support.indextype.OnHeapTypeIndex;
import com.googlecode.cqengine.quantizer.Quantizer;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.query.simple.Equal;
import com.googlecode.cqengine.query.simple.Has;
import com.googlecode.cqengine.query.simple.In;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.filter.QuantizedResultSet;
import com.googlecode.cqengine.resultset.stored.StoredResultSet;
import com.googlecode.cqengine.resultset.stored.StoredSetBasedResultSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HashIndex extends AbstractMapBasedAttributeIndex implements KeyStatisticsAttributeIndex, OnHeapTypeIndex {
   protected static final int INDEX_RETRIEVAL_COST = 30;

   protected HashIndex(Factory indexMapFactory, Factory valueSetFactory, Attribute attribute) {
      super(indexMapFactory, valueSetFactory, attribute, new HashSet() {
         {
            this.add(Equal.class);
            this.add(In.class);
            this.add(Has.class);
         }
      });
   }

   public boolean isMutable() {
      return true;
   }

   public Index getEffectiveIndex() {
      return this;
   }

   public ResultSet retrieve(Query query, QueryOptions queryOptions) {
      Class queryClass = query.getClass();
      if (queryClass.equals(Equal.class)) {
         Equal equal = (Equal)query;
         return this.retrieveEqual(equal, queryOptions);
      } else if (queryClass.equals(In.class)) {
         In in = (In)query;
         return this.retrieveIn(in, queryOptions);
      } else if (queryClass.equals(Has.class)) {
         return IndexSupport.deduplicateIfNecessary(this.indexMap.values(), query, this.getAttribute(), queryOptions, 30);
      } else {
         throw new IllegalArgumentException("Unsupported query: " + query);
      }
   }

   protected ResultSet retrieveIn(final In in, final QueryOptions queryOptions) {
      Iterable results = new Iterable() {
         public Iterator iterator() {
            return new LazyIterator() {
               final Iterator values = in.getValues().iterator();

               protected ResultSet computeNext() {
                  return this.values.hasNext() ? HashIndex.this.retrieveEqual(new Equal(in.getAttribute(), this.values.next()), queryOptions) : (ResultSet)this.endOfData();
               }
            };
         }
      };
      return IndexSupport.deduplicateIfNecessary(results, in, this.getAttribute(), queryOptions, 30);
   }

   protected ResultSet retrieveEqual(final Equal equal, final QueryOptions queryOptions) {
      return new ResultSet() {
         public Iterator iterator() {
            ResultSet rs = (ResultSet)HashIndex.this.indexMap.get(HashIndex.this.getQuantizedValue(equal.getValue()));
            return rs == null ? Collections.emptySet().iterator() : HashIndex.this.filterForQuantization(rs, equal, queryOptions).iterator();
         }

         public boolean contains(Object object) {
            ResultSet rs = (ResultSet)HashIndex.this.indexMap.get(HashIndex.this.getQuantizedValue(equal.getValue()));
            return rs != null && HashIndex.this.filterForQuantization(rs, equal, queryOptions).contains(object);
         }

         public boolean matches(Object object) {
            return equal.matches(object, queryOptions);
         }

         public int size() {
            ResultSet rs = (ResultSet)HashIndex.this.indexMap.get(HashIndex.this.getQuantizedValue(equal.getValue()));
            return rs == null ? 0 : HashIndex.this.filterForQuantization(rs, equal, queryOptions).size();
         }

         public int getRetrievalCost() {
            return 30;
         }

         public int getMergeCost() {
            ResultSet rs = (ResultSet)HashIndex.this.indexMap.get(HashIndex.this.getQuantizedValue(equal.getValue()));
            return rs == null ? 0 : rs.size();
         }

         public void close() {
         }

         public Query getQuery() {
            return equal;
         }

         public QueryOptions getQueryOptions() {
            return queryOptions;
         }
      };
   }

   protected ResultSet filterForQuantization(ResultSet storedResultSet, Query query, QueryOptions queryOptions) {
      return storedResultSet;
   }

   public Integer getCountForKey(Object key, QueryOptions queryOptions) {
      return super.getCountForKey(key);
   }

   public CloseableIterable getDistinctKeys(QueryOptions queryOptions) {
      return super.getDistinctKeys();
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

   public static HashIndex onAttribute(Attribute attribute) {
      return onAttribute(new DefaultIndexMapFactory(), new DefaultValueSetFactory(), attribute);
   }

   public static HashIndex onSemiUniqueAttribute(Attribute attribute) {
      return onAttribute(new DefaultIndexMapFactory(), new CompactValueSetFactory(), attribute);
   }

   public static HashIndex onAttribute(Factory indexMapFactory, Factory valueSetFactory, Attribute attribute) {
      return new HashIndex(indexMapFactory, valueSetFactory, attribute);
   }

   public static HashIndex withQuantizerOnAttribute(Quantizer quantizer, Attribute attribute) {
      return withQuantizerOnAttribute(new DefaultIndexMapFactory(), new DefaultValueSetFactory(), quantizer, attribute);
   }

   public static HashIndex withQuantizerOnAttribute(Factory indexMapFactory, Factory valueSetFactory, final Quantizer quantizer, Attribute attribute) {
      return new HashIndex(indexMapFactory, valueSetFactory, attribute) {
         protected ResultSet filterForQuantization(ResultSet resultSet, Query query, QueryOptions queryOptions) {
            return new QuantizedResultSet(resultSet, query, queryOptions);
         }

         protected Object getQuantizedValue(Object attributeValue) {
            return quantizer.getQuantizedValue(attributeValue);
         }

         public boolean isQuantized() {
            return true;
         }
      };
   }

   public static class CompactValueSetFactory implements Factory {
      public StoredResultSet create() {
         return new StoredSetBasedResultSet(Collections.newSetFromMap(new ConcurrentHashMap(1, 1.0F, 1)));
      }
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

package com.googlecode.cqengine.index.navigable;

import com.googlecode.concurrenttrees.common.LazyIterator;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.support.AbstractMapBasedAttributeIndex;
import com.googlecode.cqengine.index.support.CloseableIterable;
import com.googlecode.cqengine.index.support.CloseableIterator;
import com.googlecode.cqengine.index.support.Factory;
import com.googlecode.cqengine.index.support.IndexSupport;
import com.googlecode.cqengine.index.support.KeyStatistics;
import com.googlecode.cqengine.index.support.SortedKeyStatisticsAttributeIndex;
import com.googlecode.cqengine.index.support.indextype.OnHeapTypeIndex;
import com.googlecode.cqengine.quantizer.Quantizer;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.query.simple.Between;
import com.googlecode.cqengine.query.simple.Equal;
import com.googlecode.cqengine.query.simple.GreaterThan;
import com.googlecode.cqengine.query.simple.Has;
import com.googlecode.cqengine.query.simple.In;
import com.googlecode.cqengine.query.simple.LessThan;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.filter.QuantizedResultSet;
import com.googlecode.cqengine.resultset.iterator.IteratorUtil;
import com.googlecode.cqengine.resultset.iterator.UnmodifiableIterator;
import com.googlecode.cqengine.resultset.stored.StoredResultSet;
import com.googlecode.cqengine.resultset.stored.StoredSetBasedResultSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class NavigableIndex extends AbstractMapBasedAttributeIndex implements SortedKeyStatisticsAttributeIndex, OnHeapTypeIndex {
   protected static final int INDEX_RETRIEVAL_COST = 40;

   protected NavigableIndex(Factory indexMapFactory, Factory valueSetFactory, Attribute attribute) {
      super(indexMapFactory, valueSetFactory, attribute, new HashSet() {
         {
            this.add(Equal.class);
            this.add(In.class);
            this.add(LessThan.class);
            this.add(GreaterThan.class);
            this.add(Between.class);
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
      final boolean indexIsQuantized = this.isQuantized();
      if (queryClass.equals(Equal.class)) {
         Equal equal = (Equal)query;
         return this.retrieveEqual(equal, queryOptions);
      } else if (queryClass.equals(In.class)) {
         In in = (In)query;
         return this.retrieveIn(in, queryOptions);
      } else if (queryClass.equals(Has.class)) {
         return IndexSupport.deduplicateIfNecessary(((ConcurrentNavigableMap)this.indexMap).values(), query, this.getAttribute(), queryOptions, 40);
      } else {
         IndexRangeLookupFunction lookupFunction;
         if (queryClass.equals(LessThan.class)) {
            final LessThan lessThan = (LessThan)query;
            lookupFunction = new IndexRangeLookupFunction(query, false, true) {
               public Iterable perform() {
                  return ((ConcurrentNavigableMap)NavigableIndex.this.indexMap).headMap((Comparable)NavigableIndex.this.getQuantizedValue(lessThan.getValue()), lessThan.isValueInclusive() || indexIsQuantized).values();
               }
            };
         } else if (queryClass.equals(GreaterThan.class)) {
            final GreaterThan greaterThan = (GreaterThan)query;
            lookupFunction = new IndexRangeLookupFunction(query, true, false) {
               public Iterable perform() {
                  return ((ConcurrentNavigableMap)NavigableIndex.this.indexMap).tailMap((Comparable)NavigableIndex.this.getQuantizedValue(greaterThan.getValue()), greaterThan.isValueInclusive() || indexIsQuantized).values();
               }
            };
         } else {
            if (!queryClass.equals(Between.class)) {
               throw new IllegalStateException("Unsupported query: " + query);
            }

            final Between between = (Between)query;
            lookupFunction = new IndexRangeLookupFunction(query, true, true) {
               public Iterable perform() {
                  return ((ConcurrentNavigableMap)NavigableIndex.this.indexMap).subMap((Comparable)NavigableIndex.this.getQuantizedValue(between.getLowerValue()), between.isLowerInclusive() || indexIsQuantized, (Comparable)NavigableIndex.this.getQuantizedValue(between.getUpperValue()), between.isUpperInclusive() || indexIsQuantized).values();
               }
            };
         }

         Iterable results = lookupFunction.perform();
         results = this.addFilteringForQuantization(results, lookupFunction, queryOptions);
         return IndexSupport.deduplicateIfNecessary(results, query, this.getAttribute(), queryOptions, 40);
      }
   }

   protected ResultSet retrieveIn(final In in, final QueryOptions queryOptions) {
      Iterable results = new Iterable() {
         public Iterator iterator() {
            return new LazyIterator() {
               final Iterator values = in.getValues().iterator();

               protected ResultSet computeNext() {
                  return this.values.hasNext() ? NavigableIndex.this.retrieveEqual(new Equal(in.getAttribute(), (Comparable)this.values.next()), queryOptions) : (ResultSet)this.endOfData();
               }
            };
         }
      };
      return IndexSupport.deduplicateIfNecessary(results, in, this.getAttribute(), queryOptions, 40);
   }

   protected ResultSet retrieveEqual(final Equal equal, final QueryOptions queryOptions) {
      return new ResultSet() {
         public Iterator iterator() {
            ResultSet rs = (ResultSet)((ConcurrentNavigableMap)NavigableIndex.this.indexMap).get(NavigableIndex.this.getQuantizedValue((Comparable)equal.getValue()));
            return rs == null ? Collections.emptySet().iterator() : NavigableIndex.this.filterForQuantization(rs, equal, queryOptions).iterator();
         }

         public boolean contains(Object object) {
            ResultSet rs = (ResultSet)((ConcurrentNavigableMap)NavigableIndex.this.indexMap).get(NavigableIndex.this.getQuantizedValue((Comparable)equal.getValue()));
            return rs != null && NavigableIndex.this.filterForQuantization(rs, equal, queryOptions).contains(object);
         }

         public boolean matches(Object object) {
            return equal.matches(object, queryOptions);
         }

         public int size() {
            ResultSet rs = (ResultSet)((ConcurrentNavigableMap)NavigableIndex.this.indexMap).get(NavigableIndex.this.getQuantizedValue((Comparable)equal.getValue()));
            return rs == null ? 0 : NavigableIndex.this.filterForQuantization(rs, equal, queryOptions).size();
         }

         public int getRetrievalCost() {
            return 40;
         }

         public int getMergeCost() {
            ResultSet rs = (ResultSet)((ConcurrentNavigableMap)NavigableIndex.this.indexMap).get(NavigableIndex.this.getQuantizedValue((Comparable)equal.getValue()));
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

   public CloseableIterable getDistinctKeys(QueryOptions queryOptions) {
      return wrapNonCloseable(this.getDistinctKeysInRange((Comparable)null, true, (Comparable)null, true));
   }

   public CloseableIterable getDistinctKeys(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return wrapNonCloseable(this.getDistinctKeysInRange(lowerBound, lowerInclusive, upperBound, upperInclusive));
   }

   public CloseableIterable getDistinctKeysDescending(QueryOptions queryOptions) {
      return wrapNonCloseable(this.getDistinctKeysInRange((Comparable)null, true, (Comparable)null, true).descendingSet());
   }

   public CloseableIterable getDistinctKeysDescending(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return wrapNonCloseable(this.getDistinctKeysInRange(lowerBound, lowerInclusive, upperBound, upperInclusive).descendingSet());
   }

   NavigableSet getDistinctKeysInRange(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive) {
      NavigableSet results;
      if (lowerBound != null && upperBound != null) {
         results = ((ConcurrentNavigableMap)this.indexMap).keySet().subSet(lowerBound, lowerInclusive, upperBound, upperInclusive);
      } else if (lowerBound != null) {
         results = ((ConcurrentNavigableMap)this.indexMap).keySet().tailSet(lowerBound, lowerInclusive);
      } else if (upperBound != null) {
         results = ((ConcurrentNavigableMap)this.indexMap).keySet().headSet(upperBound, upperInclusive);
      } else {
         results = ((ConcurrentNavigableMap)this.indexMap).keySet();
      }

      return results;
   }

   public Integer getCountForKey(Comparable key, QueryOptions queryOptions) {
      return super.getCountForKey(key);
   }

   public Integer getCountOfDistinctKeys(QueryOptions queryOptions) {
      return super.getCountOfDistinctKeys(queryOptions);
   }

   public CloseableIterable getStatisticsForDistinctKeys(QueryOptions queryOptions) {
      return super.getStatisticsForDistinctKeys(queryOptions);
   }

   public CloseableIterable getStatisticsForDistinctKeysDescending(final QueryOptions queryOptions) {
      final CloseableIterator distinctKeysDescending = this.getDistinctKeysDescending(queryOptions).iterator();
      return wrapNonCloseable(new Iterable() {
         public Iterator iterator() {
            return new LazyIterator() {
               protected KeyStatistics computeNext() {
                  if (distinctKeysDescending.hasNext()) {
                     Comparable key = (Comparable)distinctKeysDescending.next();
                     return new KeyStatistics(key, NavigableIndex.this.getCountForKey(key, queryOptions));
                  } else {
                     return (KeyStatistics)this.endOfData();
                  }
               }
            };
         }
      });
   }

   public CloseableIterable getKeysAndValues(QueryOptions queryOptions) {
      return wrapNonCloseable(IteratorUtil.flatten(this.getKeysAndValuesInRange((Comparable)null, true, (Comparable)null, true)));
   }

   public CloseableIterable getKeysAndValues(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return wrapNonCloseable(IteratorUtil.flatten(this.getKeysAndValuesInRange(lowerBound, lowerInclusive, upperBound, upperInclusive)));
   }

   public CloseableIterable getKeysAndValuesDescending(QueryOptions queryOptions) {
      return wrapNonCloseable(IteratorUtil.flatten(this.getKeysAndValuesInRange((Comparable)null, true, (Comparable)null, true).descendingMap()));
   }

   public CloseableIterable getKeysAndValuesDescending(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return wrapNonCloseable(IteratorUtil.flatten(this.getKeysAndValuesInRange(lowerBound, lowerInclusive, upperBound, upperInclusive).descendingMap()));
   }

   NavigableMap getKeysAndValuesInRange(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive) {
      Object results;
      if (lowerBound != null && upperBound != null) {
         results = ((ConcurrentNavigableMap)this.indexMap).subMap(lowerBound, lowerInclusive, upperBound, upperInclusive);
      } else if (lowerBound != null) {
         results = ((ConcurrentNavigableMap)this.indexMap).tailMap(lowerBound, lowerInclusive);
      } else if (upperBound != null) {
         results = ((ConcurrentNavigableMap)this.indexMap).headMap(upperBound, upperInclusive);
      } else {
         results = (NavigableMap)this.indexMap;
      }

      return (NavigableMap)results;
   }

   protected Iterable addFilteringForQuantization(Iterable resultSets, IndexRangeLookupFunction lookupFunction, QueryOptions queryOptions) {
      return resultSets;
   }

   protected ResultSet filterForQuantization(ResultSet storedResultSet, Query query, QueryOptions queryOptions) {
      return storedResultSet;
   }

   public static NavigableIndex onAttribute(Attribute attribute) {
      return onAttribute(new DefaultIndexMapFactory(), new DefaultValueSetFactory(), attribute);
   }

   public static NavigableIndex onAttribute(Factory indexMapFactory, Factory valueSetFactory, Attribute attribute) {
      return new NavigableIndex(indexMapFactory, valueSetFactory, attribute);
   }

   public static NavigableIndex withQuantizerOnAttribute(Quantizer quantizer, Attribute attribute) {
      return withQuantizerOnAttribute(new DefaultIndexMapFactory(), new DefaultValueSetFactory(), quantizer, attribute);
   }

   public static NavigableIndex withQuantizerOnAttribute(Factory indexMapFactory, Factory valueSetFactory, final Quantizer quantizer, Attribute attribute) {
      return new NavigableIndex(indexMapFactory, valueSetFactory, attribute) {
         protected Iterable addFilteringForQuantization(final Iterable resultSets, final IndexRangeLookupFunction lookupFunction, final QueryOptions queryOptions) {
            return !lookupFunction.filterFirstResultSet && !lookupFunction.filterLastResultSet ? resultSets : new Iterable() {
               public Iterator iterator() {
                  return new UnmodifiableIterator() {
                     Iterator resultSetsIterator = resultSets.iterator();
                     boolean firstResultSet = true;

                     public boolean hasNext() {
                        return this.resultSetsIterator.hasNext();
                     }

                     public ResultSet next() {
                        ResultSet rs = (ResultSet)this.resultSetsIterator.next();
                        if (lookupFunction.filterFirstResultSet && this.firstResultSet) {
                           this.firstResultSet = false;
                           return new QuantizedResultSet(rs, lookupFunction.query, queryOptions);
                        } else {
                           return (ResultSet)(lookupFunction.filterLastResultSet && !this.resultSetsIterator.hasNext() ? new QuantizedResultSet(rs, lookupFunction.query, queryOptions) : rs);
                        }
                     }
                  };
               }
            };
         }

         protected Comparable getQuantizedValue(Comparable attributeValue) {
            return (Comparable)quantizer.getQuantizedValue(attributeValue);
         }

         protected ResultSet filterForQuantization(ResultSet storedResultSet, Query query, QueryOptions queryOptions) {
            return new QuantizedResultSet(storedResultSet, query, queryOptions);
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
      public ConcurrentNavigableMap create() {
         return new ConcurrentSkipListMap();
      }
   }

   protected abstract class IndexRangeLookupFunction {
      protected final boolean filterFirstResultSet;
      protected final boolean filterLastResultSet;
      protected final Query query;

      protected IndexRangeLookupFunction(Query query, boolean filterFirstResultSet, boolean filterLastResultSet) {
         this.query = query;
         this.filterFirstResultSet = filterFirstResultSet;
         this.filterLastResultSet = filterLastResultSet;
      }

      protected abstract Iterable perform();
   }
}

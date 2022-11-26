package com.googlecode.cqengine.index.support;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;

public abstract class PartialSortedKeyStatisticsAttributeIndex extends PartialIndex implements SortedKeyStatisticsAttributeIndex {
   protected PartialSortedKeyStatisticsAttributeIndex(Attribute attribute, Query filterQuery) {
      super(attribute, filterQuery);
   }

   public CloseableIterable getDistinctKeys(QueryOptions queryOptions) {
      return ((SortedKeyStatisticsAttributeIndex)this.backingIndex()).getDistinctKeys(queryOptions);
   }

   public CloseableIterable getDistinctKeys(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return ((SortedKeyStatisticsAttributeIndex)this.backingIndex()).getDistinctKeys(lowerBound, lowerInclusive, upperBound, upperInclusive, queryOptions);
   }

   public CloseableIterable getDistinctKeysDescending(QueryOptions queryOptions) {
      return ((SortedKeyStatisticsAttributeIndex)this.backingIndex()).getDistinctKeysDescending(queryOptions);
   }

   public CloseableIterable getDistinctKeysDescending(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return ((SortedKeyStatisticsAttributeIndex)this.backingIndex()).getDistinctKeysDescending(lowerBound, lowerInclusive, upperBound, upperInclusive, queryOptions);
   }

   public CloseableIterable getStatisticsForDistinctKeysDescending(QueryOptions queryOptions) {
      return ((SortedKeyStatisticsAttributeIndex)this.backingIndex()).getStatisticsForDistinctKeysDescending(queryOptions);
   }

   public CloseableIterable getKeysAndValues(QueryOptions queryOptions) {
      return ((SortedKeyStatisticsAttributeIndex)this.backingIndex()).getKeysAndValues(queryOptions);
   }

   public CloseableIterable getKeysAndValues(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return ((SortedKeyStatisticsAttributeIndex)this.backingIndex()).getKeysAndValues(lowerBound, lowerInclusive, upperBound, upperInclusive, queryOptions);
   }

   public CloseableIterable getKeysAndValuesDescending(QueryOptions queryOptions) {
      return ((SortedKeyStatisticsAttributeIndex)this.backingIndex()).getKeysAndValuesDescending(queryOptions);
   }

   public CloseableIterable getKeysAndValuesDescending(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return ((SortedKeyStatisticsAttributeIndex)this.backingIndex()).getKeysAndValuesDescending(lowerBound, lowerInclusive, upperBound, upperInclusive, queryOptions);
   }

   public Integer getCountForKey(Comparable key, QueryOptions queryOptions) {
      return ((SortedKeyStatisticsAttributeIndex)this.backingIndex()).getCountForKey(key, queryOptions);
   }

   public Integer getCountOfDistinctKeys(QueryOptions queryOptions) {
      return ((SortedKeyStatisticsAttributeIndex)this.backingIndex()).getCountOfDistinctKeys(queryOptions);
   }

   public CloseableIterable getStatisticsForDistinctKeys(QueryOptions queryOptions) {
      return ((SortedKeyStatisticsAttributeIndex)this.backingIndex()).getStatisticsForDistinctKeys(queryOptions);
   }
}

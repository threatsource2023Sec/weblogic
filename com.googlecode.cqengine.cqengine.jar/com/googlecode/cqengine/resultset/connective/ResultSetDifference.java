package com.googlecode.cqengine.resultset.connective;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.common.ResultSets;
import com.googlecode.cqengine.resultset.filter.FilteringIterator;
import com.googlecode.cqengine.resultset.iterator.IteratorUtil;
import java.util.Iterator;

public class ResultSetDifference extends ResultSet {
   final ResultSet firstResultSet;
   final ResultSet secondResultSet;
   final Query query;
   final QueryOptions queryOptions;
   final boolean indexMergeStrategyEnabled;

   public ResultSetDifference(ResultSet firstResultSet, ResultSet secondResultSet, Query query, QueryOptions queryOptions) {
      this(firstResultSet, secondResultSet, query, queryOptions, false);
   }

   public ResultSetDifference(ResultSet firstResultSet, ResultSet secondResultSet, Query query, QueryOptions queryOptions, boolean indexMergeStrategyEnabled) {
      this.firstResultSet = ResultSets.wrapWithCostCachingIfNecessary(firstResultSet);
      this.secondResultSet = ResultSets.wrapWithCostCachingIfNecessary(secondResultSet);
      this.query = query;
      this.queryOptions = queryOptions;
      if (indexMergeStrategyEnabled && this.secondResultSet.getRetrievalCost() == Integer.MAX_VALUE) {
         indexMergeStrategyEnabled = false;
      }

      this.indexMergeStrategyEnabled = indexMergeStrategyEnabled;
   }

   public Iterator iterator() {
      return this.indexMergeStrategyEnabled ? new FilteringIterator(this.firstResultSet.iterator(), this.queryOptions) {
         public boolean isValid(Object object, QueryOptions queryOptions) {
            return !ResultSetDifference.this.secondResultSet.contains(object);
         }
      } : new FilteringIterator(this.firstResultSet.iterator(), this.queryOptions) {
         public boolean isValid(Object object, QueryOptions queryOptions) {
            return !ResultSetDifference.this.secondResultSet.matches(object);
         }
      };
   }

   public boolean contains(Object object) {
      return this.firstResultSet.contains(object) && !this.secondResultSet.contains(object);
   }

   public boolean matches(Object object) {
      return this.query.matches(object, this.queryOptions);
   }

   public int size() {
      return IteratorUtil.countElements(this);
   }

   public int getRetrievalCost() {
      return this.firstResultSet.getRetrievalCost();
   }

   public int getMergeCost() {
      return this.firstResultSet.getMergeCost();
   }

   public void close() {
      this.firstResultSet.close();
      this.secondResultSet.close();
   }

   public Query getQuery() {
      return this.query;
   }

   public QueryOptions getQueryOptions() {
      return this.queryOptions;
   }
}

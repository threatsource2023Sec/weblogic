package com.googlecode.cqengine.resultset.connective;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.common.ResultSets;
import com.googlecode.cqengine.resultset.filter.FilteringIterator;
import com.googlecode.cqengine.resultset.iterator.ConcatenatingIterator;
import com.googlecode.cqengine.resultset.iterator.IteratorUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResultSetUnion extends ResultSet {
   final Query query;
   final Iterable resultSets;
   final QueryOptions queryOptions;
   final boolean indexMergeStrategyEnabled;

   public ResultSetUnion(Iterable resultSets, Query query, QueryOptions queryOptions) {
      this(resultSets, query, queryOptions, false);
   }

   public ResultSetUnion(Iterable resultSets, Query query, QueryOptions queryOptions, boolean indexMergeStrategyEnabled) {
      List costCachingResultSets = ResultSets.wrapWithCostCachingIfNecessary(resultSets);
      this.resultSets = costCachingResultSets;
      this.query = query;
      this.queryOptions = queryOptions;
      if (indexMergeStrategyEnabled) {
         Iterator var6 = costCachingResultSets.iterator();

         while(var6.hasNext()) {
            ResultSet resultSet = (ResultSet)var6.next();
            if (resultSet.getRetrievalCost() == Integer.MAX_VALUE) {
               indexMergeStrategyEnabled = false;
               break;
            }
         }
      }

      this.indexMergeStrategyEnabled = indexMergeStrategyEnabled;
   }

   public Iterator iterator() {
      final List resultSetsAlreadyIterated = new ArrayList();
      Iterator unionAllIterator = new ConcatenatingIterator() {
         private Iterator resultSetsIterator;
         private ResultSet currentResultSet;

         {
            this.resultSetsIterator = ResultSetUnion.this.resultSets.iterator();
            this.currentResultSet = null;
         }

         public Iterator getNextIterator() {
            if (this.currentResultSet != null) {
               resultSetsAlreadyIterated.add(this.currentResultSet);
            }

            if (this.resultSetsIterator.hasNext()) {
               this.currentResultSet = (ResultSet)this.resultSetsIterator.next();
               return this.currentResultSet.iterator();
            } else {
               this.currentResultSet = null;
               return null;
            }
         }
      };
      return this.indexMergeStrategyEnabled ? new FilteringIterator(unionAllIterator, this.queryOptions) {
         public boolean isValid(Object object, QueryOptions queryOptions) {
            Iterator var3 = resultSetsAlreadyIterated.iterator();

            ResultSet resultSet;
            do {
               if (!var3.hasNext()) {
                  return true;
               }

               resultSet = (ResultSet)var3.next();
            } while(!resultSet.contains(object));

            return false;
         }
      } : new FilteringIterator(unionAllIterator, this.queryOptions) {
         public boolean isValid(Object object, QueryOptions queryOptions) {
            Iterator var3 = resultSetsAlreadyIterated.iterator();

            ResultSet resultSet;
            do {
               if (!var3.hasNext()) {
                  return true;
               }

               resultSet = (ResultSet)var3.next();
            } while(!resultSet.matches(object));

            return false;
         }
      };
   }

   public boolean contains(Object object) {
      Iterator var2 = this.resultSets.iterator();

      ResultSet resultSet;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         resultSet = (ResultSet)var2.next();
      } while(!resultSet.contains(object));

      return true;
   }

   public boolean matches(Object object) {
      return this.query.matches(object, this.queryOptions);
   }

   public int size() {
      return IteratorUtil.countElements(this);
   }

   public int getRetrievalCost() {
      long retrievalCost = 0L;

      ResultSet resultSet;
      for(Iterator var3 = this.resultSets.iterator(); var3.hasNext(); retrievalCost += (long)resultSet.getRetrievalCost()) {
         resultSet = (ResultSet)var3.next();
      }

      return (int)Math.min(retrievalCost, 2147483647L);
   }

   public int getMergeCost() {
      long mergeCost = 0L;

      ResultSet resultSet;
      for(Iterator var3 = this.resultSets.iterator(); var3.hasNext(); mergeCost += (long)resultSet.getMergeCost()) {
         resultSet = (ResultSet)var3.next();
      }

      return (int)Math.min(mergeCost, 2147483647L);
   }

   public void close() {
      Iterator var1 = this.resultSets.iterator();

      while(var1.hasNext()) {
         ResultSet resultSet = (ResultSet)var1.next();
         resultSet.close();
      }

   }

   public Query getQuery() {
      return this.query;
   }

   public QueryOptions getQueryOptions() {
      return this.queryOptions;
   }
}

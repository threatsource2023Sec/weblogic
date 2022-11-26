package com.googlecode.cqengine.resultset.connective;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.common.QueryCostComparators;
import com.googlecode.cqengine.resultset.common.ResultSets;
import com.googlecode.cqengine.resultset.filter.FilteringIterator;
import com.googlecode.cqengine.resultset.iterator.IteratorUtil;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ResultSetIntersection extends ResultSet {
   final Query query;
   final List resultSets;
   final QueryOptions queryOptions;
   final boolean indexMergeStrategyEnabled;

   public ResultSetIntersection(Iterable resultSets, Query query, QueryOptions queryOptions) {
      this(resultSets, query, queryOptions, false);
   }

   public ResultSetIntersection(Iterable resultSets, Query query, QueryOptions queryOptions, boolean indexMergeStrategyEnabled) {
      this.query = query;
      this.queryOptions = queryOptions;
      List sortedResultSets = ResultSets.wrapWithCostCachingIfNecessary(resultSets);
      Collections.sort(sortedResultSets, QueryCostComparators.getMergeCostComparator());
      this.resultSets = sortedResultSets;
      if (indexMergeStrategyEnabled) {
         Iterator var6 = sortedResultSets.iterator();

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
      if (this.resultSets.isEmpty()) {
         return Collections.emptySet().iterator();
      } else if (this.resultSets.size() == 1) {
         return ((ResultSet)this.resultSets.get(0)).iterator();
      } else {
         ResultSet lowestMergeCostResultSet = (ResultSet)this.resultSets.get(0);
         final List moreExpensiveResultSets = this.resultSets.subList(1, this.resultSets.size());
         return this.indexMergeStrategyEnabled ? new FilteringIterator(lowestMergeCostResultSet.iterator(), this.queryOptions) {
            public boolean isValid(Object object, QueryOptions queryOptions) {
               Iterator var3 = moreExpensiveResultSets.iterator();

               ResultSet resultSet;
               do {
                  if (!var3.hasNext()) {
                     return true;
                  }

                  resultSet = (ResultSet)var3.next();
               } while(resultSet.contains(object));

               return false;
            }
         } : new FilteringIterator(lowestMergeCostResultSet.iterator(), this.queryOptions) {
            public boolean isValid(Object object, QueryOptions queryOptions) {
               Iterator var3 = moreExpensiveResultSets.iterator();

               ResultSet resultSet;
               do {
                  if (!var3.hasNext()) {
                     return true;
                  }

                  resultSet = (ResultSet)var3.next();
               } while(resultSet.matches(object));

               return false;
            }
         };
      }
   }

   public boolean contains(Object object) {
      if (this.resultSets.isEmpty()) {
         return false;
      } else {
         Iterator var2 = this.resultSets.iterator();

         ResultSet resultSet;
         do {
            if (!var2.hasNext()) {
               return true;
            }

            resultSet = (ResultSet)var2.next();
         } while(resultSet.contains(object));

         return false;
      }
   }

   public boolean matches(Object object) {
      return this.query.matches(object, this.queryOptions);
   }

   public int size() {
      return IteratorUtil.countElements(this);
   }

   public int getRetrievalCost() {
      if (this.resultSets.isEmpty()) {
         return 0;
      } else {
         ResultSet lowestMergeCostResultSet = (ResultSet)this.resultSets.get(0);
         return lowestMergeCostResultSet.getRetrievalCost();
      }
   }

   public int getMergeCost() {
      if (this.resultSets.isEmpty()) {
         return 0;
      } else {
         ResultSet lowestMergeCostResultSet = (ResultSet)this.resultSets.get(0);
         return lowestMergeCostResultSet.getMergeCost();
      }
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

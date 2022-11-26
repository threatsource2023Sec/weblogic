package com.googlecode.cqengine.resultset.connective;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.common.ResultSets;
import com.googlecode.cqengine.resultset.iterator.ConcatenatingIterator;
import java.util.Iterator;

public class ResultSetUnionAll extends ResultSet {
   final Query query;
   final QueryOptions queryOptions;
   private final Iterable resultSets;

   public ResultSetUnionAll(Iterable resultSets, Query query, QueryOptions queryOptions) {
      this.resultSets = ResultSets.wrapWithCostCachingIfNecessary(resultSets);
      this.query = query;
      this.queryOptions = queryOptions;
   }

   public Iterator iterator() {
      return new ConcatenatingIterator() {
         Iterator resultSetsIterator;

         {
            this.resultSetsIterator = ResultSetUnionAll.this.resultSets.iterator();
         }

         public Iterator getNextIterator() {
            return this.resultSetsIterator.hasNext() ? ((ResultSet)this.resultSetsIterator.next()).iterator() : null;
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
      int size = 0;

      ResultSet resultSet;
      for(Iterator var2 = this.resultSets.iterator(); var2.hasNext(); size += resultSet.size()) {
         resultSet = (ResultSet)var2.next();
      }

      return size;
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

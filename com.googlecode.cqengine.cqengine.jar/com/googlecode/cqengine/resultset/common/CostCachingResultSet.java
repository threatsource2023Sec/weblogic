package com.googlecode.cqengine.resultset.common;

import com.googlecode.cqengine.resultset.ResultSet;

public class CostCachingResultSet extends WrappedResultSet {
   volatile int cachedMergeCost = -1;
   volatile int cachedRetrievalCost = -1;

   public CostCachingResultSet(ResultSet wrappedResultSet) {
      super(wrappedResultSet);
   }

   public int getRetrievalCost() {
      return this.cachedRetrievalCost != -1 ? this.cachedRetrievalCost : (this.cachedRetrievalCost = super.getRetrievalCost());
   }

   public int getMergeCost() {
      return this.cachedMergeCost != -1 ? this.cachedMergeCost : (this.cachedMergeCost = super.getMergeCost());
   }
}

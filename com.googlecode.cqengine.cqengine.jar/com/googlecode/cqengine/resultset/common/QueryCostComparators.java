package com.googlecode.cqengine.resultset.common;

import com.googlecode.cqengine.resultset.ResultSet;
import java.util.Comparator;

public class QueryCostComparators {
   private static final Comparator RETRIEVAL_COST_COMPARATOR = new RetrievalCostComparator();
   private static final Comparator MERGE_COST_COMPARATOR = new MergeCostComparator();

   public static Comparator getRetrievalCostComparator() {
      return RETRIEVAL_COST_COMPARATOR;
   }

   public static Comparator getMergeCostComparator() {
      return MERGE_COST_COMPARATOR;
   }

   static class MergeCostComparator implements Comparator {
      public int compare(ResultSet o1, ResultSet o2) {
         int o1MergeCost = o1.getMergeCost();
         int o2MergeCost = o2.getMergeCost();
         if (o1MergeCost < o2MergeCost) {
            return -1;
         } else {
            return o1MergeCost > o2MergeCost ? 1 : 0;
         }
      }
   }

   static class RetrievalCostComparator implements Comparator {
      public int compare(ResultSet o1, ResultSet o2) {
         int o1RetrievalCost = o1.getRetrievalCost();
         int o2RetrievalCost = o2.getRetrievalCost();
         if (o1RetrievalCost < o2RetrievalCost) {
            return -1;
         } else {
            return o1RetrievalCost > o2RetrievalCost ? 1 : 0;
         }
      }
   }
}

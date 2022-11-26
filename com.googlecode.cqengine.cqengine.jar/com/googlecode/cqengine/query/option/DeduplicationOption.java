package com.googlecode.cqengine.query.option;

public class DeduplicationOption {
   private final DeduplicationStrategy strategy;

   public DeduplicationOption(DeduplicationStrategy strategy) {
      this.strategy = strategy;
   }

   public DeduplicationStrategy getStrategy() {
      return this.strategy;
   }

   public static boolean isLogicalElimination(QueryOptions queryOptions) {
      DeduplicationOption option = (DeduplicationOption)queryOptions.get(DeduplicationOption.class);
      return option != null && DeduplicationStrategy.LOGICAL_ELIMINATION.equals(option.getStrategy());
   }

   public static boolean isMaterialize(QueryOptions queryOptions) {
      DeduplicationOption option = (DeduplicationOption)queryOptions.get(DeduplicationOption.class);
      return option != null && DeduplicationStrategy.MATERIALIZE.equals(option.getStrategy());
   }

   public String toString() {
      return "deduplicate(" + this.strategy + ")";
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof DeduplicationOption)) {
         return false;
      } else {
         DeduplicationOption that = (DeduplicationOption)o;
         return this.strategy == that.strategy;
      }
   }

   public int hashCode() {
      return this.strategy.hashCode();
   }
}

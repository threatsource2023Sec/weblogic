package com.googlecode.cqengine.query.option;

public class IsolationOption {
   private final IsolationLevel isolationLevel;

   public IsolationOption(IsolationLevel isolationLevel) {
      this.isolationLevel = isolationLevel;
   }

   public IsolationLevel getIsolationLevel() {
      return this.isolationLevel;
   }

   public String toString() {
      return "isolationLevel(" + this.isolationLevel + ")";
   }

   public static boolean isIsolationLevel(QueryOptions queryOptions, IsolationLevel level) {
      IsolationOption option = (IsolationOption)queryOptions.get(IsolationOption.class);
      return option != null && level.equals(option.getIsolationLevel());
   }
}

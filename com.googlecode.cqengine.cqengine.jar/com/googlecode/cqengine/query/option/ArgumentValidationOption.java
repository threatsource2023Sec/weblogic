package com.googlecode.cqengine.query.option;

public class ArgumentValidationOption {
   private final ArgumentValidationStrategy strategy;

   public ArgumentValidationOption(ArgumentValidationStrategy strategy) {
      this.strategy = strategy;
   }

   public ArgumentValidationStrategy getStrategy() {
      return this.strategy;
   }

   public static boolean isSkip(QueryOptions queryOptions) {
      ArgumentValidationOption option = (ArgumentValidationOption)queryOptions.get(ArgumentValidationOption.class);
      return option != null && ArgumentValidationStrategy.SKIP.equals(option.getStrategy());
   }

   public String toString() {
      return "argumentValidation(" + this.strategy + ")";
   }
}

package com.bea.logging;

public class LogFilterExpressionConfigImpl implements LogFilterExpressionConfig {
   private String logFilterExpression;
   private String name;

   public String getLogFilterExpression() {
      return this.logFilterExpression;
   }

   public String getName() {
      return this.name;
   }

   public void setLogFilterExpression(String filter) {
      this.logFilterExpression = filter;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String toString() {
      return this.name;
   }
}

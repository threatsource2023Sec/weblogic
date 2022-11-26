package com.bea.core.repackaged.springframework.jmx.export.metadata;

public class AbstractJmxAttribute {
   private String description = "";
   private int currencyTimeLimit = -1;

   public void setDescription(String description) {
      this.description = description;
   }

   public String getDescription() {
      return this.description;
   }

   public void setCurrencyTimeLimit(int currencyTimeLimit) {
      this.currencyTimeLimit = currencyTimeLimit;
   }

   public int getCurrencyTimeLimit() {
      return this.currencyTimeLimit;
   }
}

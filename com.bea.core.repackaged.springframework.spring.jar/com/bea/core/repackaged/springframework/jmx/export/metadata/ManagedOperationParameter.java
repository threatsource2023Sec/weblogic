package com.bea.core.repackaged.springframework.jmx.export.metadata;

public class ManagedOperationParameter {
   private int index = 0;
   private String name = "";
   private String description = "";

   public void setIndex(int index) {
      this.index = index;
   }

   public int getIndex() {
      return this.index;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getDescription() {
      return this.description;
   }
}

package com.bea.core.repackaged.springframework.beans.support;

import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.Serializable;

public class MutableSortDefinition implements SortDefinition, Serializable {
   private String property = "";
   private boolean ignoreCase = true;
   private boolean ascending = true;
   private boolean toggleAscendingOnProperty = false;

   public MutableSortDefinition() {
   }

   public MutableSortDefinition(SortDefinition source) {
      this.property = source.getProperty();
      this.ignoreCase = source.isIgnoreCase();
      this.ascending = source.isAscending();
   }

   public MutableSortDefinition(String property, boolean ignoreCase, boolean ascending) {
      this.property = property;
      this.ignoreCase = ignoreCase;
      this.ascending = ascending;
   }

   public MutableSortDefinition(boolean toggleAscendingOnSameProperty) {
      this.toggleAscendingOnProperty = toggleAscendingOnSameProperty;
   }

   public void setProperty(String property) {
      if (!StringUtils.hasLength(property)) {
         this.property = "";
      } else {
         if (this.isToggleAscendingOnProperty()) {
            this.ascending = !property.equals(this.property) || !this.ascending;
         }

         this.property = property;
      }

   }

   public String getProperty() {
      return this.property;
   }

   public void setIgnoreCase(boolean ignoreCase) {
      this.ignoreCase = ignoreCase;
   }

   public boolean isIgnoreCase() {
      return this.ignoreCase;
   }

   public void setAscending(boolean ascending) {
      this.ascending = ascending;
   }

   public boolean isAscending() {
      return this.ascending;
   }

   public void setToggleAscendingOnProperty(boolean toggleAscendingOnProperty) {
      this.toggleAscendingOnProperty = toggleAscendingOnProperty;
   }

   public boolean isToggleAscendingOnProperty() {
      return this.toggleAscendingOnProperty;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof SortDefinition)) {
         return false;
      } else {
         SortDefinition otherSd = (SortDefinition)other;
         return this.getProperty().equals(otherSd.getProperty()) && this.isAscending() == otherSd.isAscending() && this.isIgnoreCase() == otherSd.isIgnoreCase();
      }
   }

   public int hashCode() {
      int hashCode = this.getProperty().hashCode();
      hashCode = 29 * hashCode + (this.isIgnoreCase() ? 1 : 0);
      hashCode = 29 * hashCode + (this.isAscending() ? 1 : 0);
      return hashCode;
   }
}

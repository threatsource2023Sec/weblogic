package com.googlecode.cqengine.query.option;

import java.util.Iterator;
import java.util.List;

public class OrderByOption {
   private final List attributeOrders;

   public OrderByOption(List attributeOrders) {
      if (attributeOrders.isEmpty()) {
         throw new IllegalArgumentException("The list of attribute orders cannot be empty");
      } else {
         this.attributeOrders = attributeOrders;
      }
   }

   public List getAttributeOrders() {
      return this.attributeOrders;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("orderBy(");
      Iterator iterator = this.attributeOrders.iterator();

      while(iterator.hasNext()) {
         AttributeOrder childQuery = (AttributeOrder)iterator.next();
         sb.append(childQuery);
         if (iterator.hasNext()) {
            sb.append(", ");
         }
      }

      sb.append(")");
      return sb.toString();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof OrderByOption)) {
         return false;
      } else {
         OrderByOption that = (OrderByOption)o;
         return this.attributeOrders.equals(that.attributeOrders);
      }
   }

   public int hashCode() {
      return this.attributeOrders.hashCode();
   }
}

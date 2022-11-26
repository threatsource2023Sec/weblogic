package com.googlecode.cqengine.resultset.order;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.OrderControlAttribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.AttributeOrder;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class AttributeOrdersComparator implements Comparator {
   final List attributeSortOrders;
   final QueryOptions queryOptions;

   public AttributeOrdersComparator(List attributeSortOrders, QueryOptions queryOptions) {
      this.attributeSortOrders = attributeSortOrders;
      this.queryOptions = queryOptions;
   }

   public int compare(Object o1, Object o2) {
      Iterator var3 = this.attributeSortOrders.iterator();

      AttributeOrder attributeOrder;
      int comparison;
      do {
         if (!var3.hasNext()) {
            if (o1.equals(o2)) {
               return 0;
            }

            return o1.hashCode() >= o2.hashCode() ? 1 : -1;
         }

         attributeOrder = (AttributeOrder)var3.next();
         Attribute attribute = attributeOrder.getAttribute();
         if (attribute instanceof OrderControlAttribute) {
            OrderControlAttribute orderControl = (OrderControlAttribute)attribute;
            comparison = ((Integer)orderControl.getValue(o1, this.queryOptions)).compareTo((Integer)orderControl.getValue(o2, this.queryOptions));
            if (comparison != 0) {
               return comparison;
            }

            attribute = orderControl.getDelegateAttribute();
         }

         comparison = this.compareAttributeValues(attribute, o1, o2);
      } while(comparison == 0);

      return attributeOrder.isDescending() ? comparison * -1 : comparison;
   }

   int compareAttributeValues(Attribute attribute, Object o1, Object o2) {
      if (attribute instanceof SimpleAttribute) {
         SimpleAttribute simpleAttribute = (SimpleAttribute)attribute;
         return ((Comparable)simpleAttribute.getValue(o1, this.queryOptions)).compareTo((Comparable)simpleAttribute.getValue(o2, this.queryOptions));
      } else {
         Iterator o1attributeValues = attribute.getValues(o1, this.queryOptions).iterator();
         Iterator o2attributeValues = attribute.getValues(o2, this.queryOptions).iterator();

         while(o1attributeValues.hasNext() && o2attributeValues.hasNext()) {
            Comparable o1attributeValue = (Comparable)o1attributeValues.next();
            Comparable o2attributeValue = (Comparable)o2attributeValues.next();
            int comparison = o1attributeValue.compareTo(o2attributeValue);
            if (comparison != 0) {
               return comparison;
            }
         }

         if (o2attributeValues.hasNext()) {
            return -1;
         } else {
            return o1attributeValues.hasNext() ? 1 : 0;
         }
      }
   }
}

package com.googlecode.cqengine.query.simple;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Iterator;

public class GreaterThan extends SimpleQuery {
   private final Comparable value;
   private final boolean valueInclusive;

   public GreaterThan(Attribute attribute, Comparable value, boolean valueInclusive) {
      super(attribute);
      this.value = value;
      this.valueInclusive = valueInclusive;
   }

   public Comparable getValue() {
      return this.value;
   }

   public boolean isValueInclusive() {
      return this.valueInclusive;
   }

   public String toString() {
      return this.valueInclusive ? "greaterThanOrEqualTo(" + asLiteral(super.getAttributeName()) + ", " + asLiteral(this.value) + ")" : "greaterThan(" + asLiteral(super.getAttributeName()) + ", " + asLiteral(this.value) + ")";
   }

   protected boolean matchesSimpleAttribute(SimpleAttribute attribute, Object object, QueryOptions queryOptions) {
      Comparable attributeValue = (Comparable)attribute.getValue(object, queryOptions);
      if (this.valueInclusive) {
         return this.value.compareTo(attributeValue) <= 0;
      } else {
         return this.value.compareTo(attributeValue) < 0;
      }
   }

   protected boolean matchesNonSimpleAttribute(Attribute attribute, Object object, QueryOptions queryOptions) {
      Iterable attributeValues = attribute.getValues(object, queryOptions);
      Iterator var5;
      Comparable attributeValue;
      if (this.valueInclusive) {
         var5 = attributeValues.iterator();

         while(var5.hasNext()) {
            attributeValue = (Comparable)var5.next();
            if (this.value.compareTo(attributeValue) <= 0) {
               return true;
            }
         }
      } else {
         var5 = attributeValues.iterator();

         while(var5.hasNext()) {
            attributeValue = (Comparable)var5.next();
            if (this.value.compareTo(attributeValue) < 0) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof GreaterThan)) {
         return false;
      } else {
         GreaterThan that = (GreaterThan)o;
         if (!this.attribute.equals(that.attribute)) {
            return false;
         } else if (this.valueInclusive != that.valueInclusive) {
            return false;
         } else {
            return this.value.equals(that.value);
         }
      }
   }

   protected int calcHashCode() {
      int result = this.attribute.hashCode();
      result = 31 * result + this.value.hashCode();
      result = 31 * result + (this.valueInclusive ? 1 : 0);
      return result;
   }
}

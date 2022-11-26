package com.googlecode.cqengine.query.simple;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Iterator;

public class Between extends SimpleQuery {
   private final Comparable lowerValue;
   private final boolean lowerInclusive;
   private final Comparable upperValue;
   private final boolean upperInclusive;

   public Between(Attribute attribute, Comparable lowerValue, boolean lowerInclusive, Comparable upperValue, boolean upperInclusive) {
      super(attribute);
      this.lowerValue = lowerValue;
      this.lowerInclusive = lowerInclusive;
      this.upperValue = upperValue;
      this.upperInclusive = upperInclusive;
   }

   public Comparable getLowerValue() {
      return this.lowerValue;
   }

   public boolean isLowerInclusive() {
      return this.lowerInclusive;
   }

   public Comparable getUpperValue() {
      return this.upperValue;
   }

   public boolean isUpperInclusive() {
      return this.upperInclusive;
   }

   public String toString() {
      return this.lowerInclusive && this.upperInclusive ? "between(" + asLiteral(super.getAttributeName()) + ", " + asLiteral(this.lowerValue) + ", " + asLiteral(this.upperValue) + ")" : "between(" + asLiteral(super.getAttributeName()) + ", " + asLiteral(this.lowerValue) + ", " + this.lowerInclusive + ", " + asLiteral(this.upperValue) + ", " + this.upperInclusive + ")";
   }

   protected boolean matchesSimpleAttribute(SimpleAttribute attribute, Object object, QueryOptions queryOptions) {
      Comparable attributeValue = (Comparable)attribute.getValue(object, queryOptions);
      if (this.lowerInclusive && this.upperInclusive) {
         if (this.lowerValue.compareTo(attributeValue) <= 0 && this.upperValue.compareTo(attributeValue) >= 0) {
            return true;
         }
      } else if (this.lowerInclusive) {
         if (this.lowerValue.compareTo(attributeValue) <= 0 && this.upperValue.compareTo(attributeValue) > 0) {
            return true;
         }
      } else if (this.upperInclusive) {
         if (this.lowerValue.compareTo(attributeValue) < 0 && this.upperValue.compareTo(attributeValue) >= 0) {
            return true;
         }
      } else if (this.lowerValue.compareTo(attributeValue) < 0 && this.upperValue.compareTo(attributeValue) > 0) {
         return true;
      }

      return false;
   }

   protected boolean matchesNonSimpleAttribute(Attribute attribute, Object object, QueryOptions queryOptions) {
      Iterable attributeValues = attribute.getValues(object, queryOptions);
      Iterator var5;
      Comparable attributeValue;
      if (this.lowerInclusive && this.upperInclusive) {
         var5 = attributeValues.iterator();

         while(var5.hasNext()) {
            attributeValue = (Comparable)var5.next();
            if (this.lowerValue.compareTo(attributeValue) <= 0 && this.upperValue.compareTo(attributeValue) >= 0) {
               return true;
            }
         }
      } else if (this.lowerInclusive) {
         var5 = attributeValues.iterator();

         while(var5.hasNext()) {
            attributeValue = (Comparable)var5.next();
            if (this.lowerValue.compareTo(attributeValue) <= 0 && this.upperValue.compareTo(attributeValue) > 0) {
               return true;
            }
         }
      } else if (this.upperInclusive) {
         var5 = attributeValues.iterator();

         while(var5.hasNext()) {
            attributeValue = (Comparable)var5.next();
            if (this.lowerValue.compareTo(attributeValue) < 0 && this.upperValue.compareTo(attributeValue) >= 0) {
               return true;
            }
         }
      } else {
         var5 = attributeValues.iterator();

         while(var5.hasNext()) {
            attributeValue = (Comparable)var5.next();
            if (this.lowerValue.compareTo(attributeValue) < 0 && this.upperValue.compareTo(attributeValue) > 0) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof Between)) {
         return false;
      } else {
         Between between = (Between)o;
         if (!this.attribute.equals(between.attribute)) {
            return false;
         } else if (this.lowerInclusive != between.lowerInclusive) {
            return false;
         } else if (this.upperInclusive != between.upperInclusive) {
            return false;
         } else if (!this.lowerValue.equals(between.lowerValue)) {
            return false;
         } else {
            return this.upperValue.equals(between.upperValue);
         }
      }
   }

   protected int calcHashCode() {
      int result = this.attribute.hashCode();
      result = 31 * result + this.lowerValue.hashCode();
      result = 31 * result + (this.lowerInclusive ? 1 : 0);
      result = 31 * result + this.upperValue.hashCode();
      result = 31 * result + (this.upperInclusive ? 1 : 0);
      return result;
   }
}

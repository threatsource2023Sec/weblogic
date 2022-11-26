package com.googlecode.cqengine.query.simple;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Iterator;
import java.util.Set;

public class In extends SimpleQuery {
   private final Set values;
   private final boolean disjoint;

   public In(Attribute attribute, boolean disjoint, Set values) {
      super(attribute);
      if (values != null && values.size() != 0) {
         this.values = values;
         this.disjoint = disjoint;
      } else {
         throw new IllegalArgumentException("The IN query must have at least one value.");
      }
   }

   public Set getValues() {
      return this.values;
   }

   public boolean isDisjoint() {
      return this.disjoint;
   }

   protected boolean matchesSimpleAttribute(SimpleAttribute attribute, Object object, QueryOptions queryOptions) {
      return this.values.contains(attribute.getValue(object, queryOptions));
   }

   protected boolean matchesNonSimpleAttribute(Attribute attribute, Object object, QueryOptions queryOptions) {
      Iterator var4 = attribute.getValues(object, queryOptions).iterator();

      Object attributeValue;
      do {
         if (!var4.hasNext()) {
            return false;
         }

         attributeValue = var4.next();
      } while(!this.values.contains(attributeValue));

      return true;
   }

   protected int calcHashCode() {
      int result = this.attribute.hashCode();
      result = 31 * result + this.values.hashCode();
      result = 31 * result + (this.disjoint ? 1 : 0);
      return result;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof In)) {
         return false;
      } else {
         In in = (In)o;
         if (this.disjoint != in.disjoint) {
            return false;
         } else {
            return !this.attribute.equals(in.attribute) ? false : this.values.equals(in.values);
         }
      }
   }

   public String toString() {
      return "in(" + asLiteral(super.getAttributeName()) + ", " + asLiteral(this.values) + ")";
   }
}

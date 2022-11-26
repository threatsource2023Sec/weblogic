package com.googlecode.cqengine.query.simple;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Iterator;

public class Has extends SimpleQuery {
   public Has(Attribute attribute) {
      super(attribute);
   }

   public String toString() {
      return "has(" + asLiteral(super.getAttributeName()) + ")";
   }

   protected boolean matchesSimpleAttribute(SimpleAttribute attribute, Object object, QueryOptions queryOptions) {
      return attribute.getValue(object, queryOptions) != null;
   }

   protected boolean matchesNonSimpleAttribute(Attribute attribute, Object object, QueryOptions queryOptions) {
      Iterator var4 = attribute.getValues(object, queryOptions).iterator();

      Object attributeValue;
      do {
         if (!var4.hasNext()) {
            return false;
         }

         attributeValue = var4.next();
      } while(attributeValue == null);

      return true;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof Has)) {
         return false;
      } else {
         Has equal = (Has)o;
         return this.attribute.equals(equal.attribute);
      }
   }

   protected int calcHashCode() {
      return this.attribute.hashCode();
   }
}

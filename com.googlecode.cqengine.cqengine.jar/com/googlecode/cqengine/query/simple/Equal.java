package com.googlecode.cqengine.query.simple;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Iterator;

public class Equal extends SimpleQuery {
   private final Object value;

   public Equal(Attribute attribute, Object value) {
      super(attribute);
      this.value = value;
   }

   public Object getValue() {
      return this.value;
   }

   public String toString() {
      return "equal(" + asLiteral(super.getAttributeName()) + ", " + asLiteral(this.value) + ")";
   }

   protected boolean matchesSimpleAttribute(SimpleAttribute attribute, Object object, QueryOptions queryOptions) {
      return this.value.equals(attribute.getValue(object, queryOptions));
   }

   protected boolean matchesNonSimpleAttribute(Attribute attribute, Object object, QueryOptions queryOptions) {
      Iterator var4 = attribute.getValues(object, queryOptions).iterator();

      Object attributeValue;
      do {
         if (!var4.hasNext()) {
            return false;
         }

         attributeValue = var4.next();
      } while(!this.value.equals(attributeValue));

      return true;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof Equal)) {
         return false;
      } else {
         Equal equal = (Equal)o;
         if (!this.attribute.equals(equal.attribute)) {
            return false;
         } else {
            return this.value.equals(equal.value);
         }
      }
   }

   protected int calcHashCode() {
      int result = this.attribute.hashCode();
      result = 31 * result + this.value.hashCode();
      return result;
   }
}

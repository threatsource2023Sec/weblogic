package com.googlecode.cqengine.query.simple;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Iterator;

public class StringIsContainedIn extends SimpleQuery {
   private final CharSequence value;

   public StringIsContainedIn(Attribute attribute, CharSequence value) {
      super(attribute);
      this.value = value;
   }

   public CharSequence getValue() {
      return this.value;
   }

   protected boolean matchesSimpleAttribute(SimpleAttribute attribute, Object object, QueryOptions queryOptions) {
      CharSequence attributeValue = (CharSequence)attribute.getValue(object, queryOptions);
      return StringContains.containsFragment(this.value, attributeValue);
   }

   protected boolean matchesNonSimpleAttribute(Attribute attribute, Object object, QueryOptions queryOptions) {
      Iterator var4 = attribute.getValues(object, queryOptions).iterator();

      CharSequence attributeValue;
      do {
         if (!var4.hasNext()) {
            return false;
         }

         attributeValue = (CharSequence)var4.next();
      } while(!StringContains.containsFragment(this.value, attributeValue));

      return true;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof StringIsContainedIn)) {
         return false;
      } else {
         StringIsContainedIn that = (StringIsContainedIn)o;
         if (!this.attribute.equals(that.attribute)) {
            return false;
         } else {
            return this.value.equals(that.value);
         }
      }
   }

   protected int calcHashCode() {
      int result = this.attribute.hashCode();
      result = 31 * result + this.value.hashCode();
      return result;
   }

   public String toString() {
      return "isContainedIn(" + asLiteral(super.getAttributeName()) + ", " + asLiteral(this.value) + ")";
   }
}

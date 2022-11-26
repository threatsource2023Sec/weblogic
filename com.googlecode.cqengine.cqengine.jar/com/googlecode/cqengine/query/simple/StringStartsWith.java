package com.googlecode.cqengine.query.simple;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Iterator;

public class StringStartsWith extends SimpleQuery {
   private final CharSequence value;

   public StringStartsWith(Attribute attribute, CharSequence value) {
      super(attribute);
      this.value = value;
   }

   public CharSequence getValue() {
      return this.value;
   }

   protected boolean matchesSimpleAttribute(SimpleAttribute attribute, Object object, QueryOptions queryOptions) {
      CharSequence attributeValue = (CharSequence)attribute.getValue(object, queryOptions);
      return this.matchesValue(attributeValue, queryOptions);
   }

   protected boolean matchesNonSimpleAttribute(Attribute attribute, Object object, QueryOptions queryOptions) {
      Iterator var4 = attribute.getValues(object, queryOptions).iterator();

      CharSequence attributeValue;
      do {
         if (!var4.hasNext()) {
            return false;
         }

         attributeValue = (CharSequence)var4.next();
      } while(!this.matchesValue(attributeValue, queryOptions));

      return true;
   }

   public boolean matchesValue(CharSequence aValue, QueryOptions queryOptions) {
      int charsMatched = 0;
      int i = 0;

      for(int length = Math.min(aValue.length(), this.value.length()); i < length && aValue.charAt(i) == this.value.charAt(i); ++i) {
         ++charsMatched;
      }

      return charsMatched == this.value.length();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof StringStartsWith)) {
         return false;
      } else {
         StringStartsWith that = (StringStartsWith)o;
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
      return "startsWith(" + asLiteral(super.getAttributeName()) + ", " + asLiteral(this.value) + ")";
   }
}

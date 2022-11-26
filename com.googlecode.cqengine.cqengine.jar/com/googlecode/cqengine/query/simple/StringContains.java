package com.googlecode.cqengine.query.simple;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Iterator;

public class StringContains extends SimpleQuery {
   private final CharSequence value;

   public StringContains(Attribute attribute, CharSequence value) {
      super(attribute);
      this.value = value;
   }

   public CharSequence getValue() {
      return this.value;
   }

   protected boolean matchesSimpleAttribute(SimpleAttribute attribute, Object object, QueryOptions queryOptions) {
      CharSequence attributeValue = (CharSequence)attribute.getValue(object, queryOptions);
      return containsFragment(attributeValue, this.value);
   }

   protected boolean matchesNonSimpleAttribute(Attribute attribute, Object object, QueryOptions queryOptions) {
      Iterator var4 = attribute.getValues(object, queryOptions).iterator();

      CharSequence attributeValue;
      do {
         if (!var4.hasNext()) {
            return false;
         }

         attributeValue = (CharSequence)var4.next();
      } while(!containsFragment(attributeValue, this.value));

      return true;
   }

   static boolean containsFragment(CharSequence document, CharSequence fragment) {
      int documentLength = document.length();
      int fragmentLength = fragment.length();
      int lastStartOffset = documentLength - fragmentLength;

      for(int startOffset = 0; startOffset <= lastStartOffset; ++startOffset) {
         int charsMatched = 0;
         int endOffset = startOffset;

         for(int j = 0; j < fragmentLength; ++endOffset) {
            char documentChar = document.charAt(endOffset);
            char fragmentChar = fragment.charAt(j);
            if (documentChar != fragmentChar) {
               break;
            }

            ++charsMatched;
            ++j;
         }

         if (charsMatched == fragmentLength) {
            return true;
         }
      }

      return false;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof StringContains)) {
         return false;
      } else {
         StringContains that = (StringContains)o;
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
      return "contains(" + asLiteral(super.getAttributeName()) + ", " + asLiteral(this.value) + ")";
   }
}

package com.googlecode.cqengine.query.simple;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Iterator;
import java.util.regex.Pattern;

public class StringMatchesRegex extends SimpleQuery {
   private final Pattern regexPattern;

   public StringMatchesRegex(Attribute attribute, Pattern regexPattern) {
      super(attribute);
      this.regexPattern = regexPattern;
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
      return this.regexPattern.matcher(aValue).matches();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof StringMatchesRegex)) {
         return false;
      } else {
         StringMatchesRegex that = (StringMatchesRegex)o;
         return this.attribute.equals(that.attribute) && this.regexPattern.pattern().equals(that.regexPattern.pattern()) && this.regexPattern.flags() == that.regexPattern.flags();
      }
   }

   protected int calcHashCode() {
      int result = this.attribute.hashCode();
      result = 31 * result + this.regexPattern.hashCode();
      return result;
   }

   public String toString() {
      return "matchesRegex(" + asLiteral(super.getAttributeName()) + ", " + asLiteral(this.regexPattern.pattern()) + ")";
   }
}

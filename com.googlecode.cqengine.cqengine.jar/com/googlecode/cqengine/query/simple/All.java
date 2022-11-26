package com.googlecode.cqengine.query.simple;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SelfAttribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;

public class All extends SimpleQuery {
   final Class objectType;

   public All(Class objectType) {
      super(new SelfAttribute(objectType, "true"));
      this.objectType = objectType;
   }

   protected boolean matchesSimpleAttribute(SimpleAttribute attribute, Object object, QueryOptions queryOptions) {
      return true;
   }

   protected boolean matchesNonSimpleAttribute(Attribute attribute, Object object, QueryOptions queryOptions) {
      return true;
   }

   protected int calcHashCode() {
      return 765906512;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof All)) {
         return false;
      } else {
         All that = (All)o;
         return this.objectType.equals(that.objectType);
      }
   }

   public String toString() {
      return "all(" + super.getAttribute().getObjectType().getSimpleName() + ".class)";
   }
}

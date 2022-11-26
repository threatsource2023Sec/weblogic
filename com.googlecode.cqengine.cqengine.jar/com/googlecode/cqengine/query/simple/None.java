package com.googlecode.cqengine.query.simple;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SelfAttribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;

public class None extends SimpleQuery {
   final Class objectType;

   public None(Class objectType) {
      super(new SelfAttribute(objectType, "false"));
      this.objectType = objectType;
   }

   protected boolean matchesSimpleAttribute(SimpleAttribute attribute, Object object, QueryOptions queryOptions) {
      return false;
   }

   protected boolean matchesNonSimpleAttribute(Attribute attribute, Object object, QueryOptions queryOptions) {
      return false;
   }

   protected int calcHashCode() {
      return 1357656699;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof None)) {
         return false;
      } else {
         None that = (None)o;
         return this.objectType.equals(that.objectType);
      }
   }

   public String toString() {
      return "none(" + super.getAttribute().getObjectType().getSimpleName() + ".class)";
   }
}

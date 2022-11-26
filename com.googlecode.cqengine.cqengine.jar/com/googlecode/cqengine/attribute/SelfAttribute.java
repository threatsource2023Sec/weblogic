package com.googlecode.cqengine.attribute;

import com.googlecode.cqengine.query.option.QueryOptions;

public class SelfAttribute extends SimpleAttribute {
   public SelfAttribute(Class objectType, String attributeName) {
      super(objectType, objectType, attributeName);
   }

   public SelfAttribute(Class objectType) {
      super(objectType, objectType, "self");
   }

   public Object getValue(Object object, QueryOptions queryOptions) {
      return object;
   }

   /** @deprecated */
   @Deprecated
   public static SelfAttribute self(Class type) {
      return new SelfAttribute(type);
   }
}

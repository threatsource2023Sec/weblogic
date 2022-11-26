package com.googlecode.cqengine.attribute.support;

import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;

public class FunctionalSimpleAttribute extends SimpleAttribute {
   final SimpleFunction function;

   public FunctionalSimpleAttribute(Class objectType, Class attributeType, String attributeName, SimpleFunction function) {
      super(objectType, attributeType, attributeName);
      this.function = function;
   }

   public Object getValue(Object object, QueryOptions queryOptions) {
      return this.function.apply(object);
   }
}

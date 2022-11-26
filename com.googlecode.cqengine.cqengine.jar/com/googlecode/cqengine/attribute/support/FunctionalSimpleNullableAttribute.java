package com.googlecode.cqengine.attribute.support;

import com.googlecode.cqengine.attribute.SimpleNullableAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;

public class FunctionalSimpleNullableAttribute extends SimpleNullableAttribute {
   final SimpleFunction function;

   public FunctionalSimpleNullableAttribute(Class objectType, Class attributeType, String attributeName, SimpleFunction function) {
      super(objectType, attributeType, attributeName);
      this.function = function;
   }

   public Object getValue(Object object, QueryOptions queryOptions) {
      return this.function.apply(object);
   }
}

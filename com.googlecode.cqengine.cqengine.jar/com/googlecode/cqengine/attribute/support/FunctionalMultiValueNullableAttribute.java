package com.googlecode.cqengine.attribute.support;

import com.googlecode.cqengine.attribute.MultiValueNullableAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;

public class FunctionalMultiValueNullableAttribute extends MultiValueNullableAttribute {
   final MultiValueFunction function;

   public FunctionalMultiValueNullableAttribute(Class objectType, Class attributeType, String attributeName, boolean componentValuesNullable, MultiValueFunction function) {
      super(objectType, attributeType, attributeName, componentValuesNullable);
      this.function = function;
   }

   public Iterable getNullableValues(Object object, QueryOptions queryOptions) {
      return this.function.apply(object);
   }
}

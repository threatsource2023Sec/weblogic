package com.googlecode.cqengine.attribute.support;

import com.googlecode.cqengine.attribute.MultiValueAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;

public class FunctionalMultiValueAttribute extends MultiValueAttribute {
   final MultiValueFunction function;

   public FunctionalMultiValueAttribute(Class objectType, Class attributeType, String attributeName, MultiValueFunction function) {
      super(objectType, attributeType, attributeName);
      this.function = function;
   }

   public Iterable getValues(Object object, QueryOptions queryOptions) {
      return this.function.apply(object);
   }
}

package com.googlecode.cqengine.attribute;

import com.googlecode.cqengine.attribute.support.AbstractAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;

public abstract class MultiValueAttribute extends AbstractAttribute {
   public MultiValueAttribute(String attributeName) {
      super(attributeName);
   }

   public MultiValueAttribute() {
   }

   public MultiValueAttribute(Class objectType, Class attributeType) {
      super(objectType, attributeType);
   }

   public MultiValueAttribute(Class objectType, Class attributeType, String attributeName) {
      super(objectType, attributeType, attributeName);
   }

   public abstract Iterable getValues(Object var1, QueryOptions var2);
}

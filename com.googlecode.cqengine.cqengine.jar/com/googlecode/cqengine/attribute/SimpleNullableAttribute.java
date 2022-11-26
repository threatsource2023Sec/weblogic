package com.googlecode.cqengine.attribute;

import com.googlecode.cqengine.attribute.support.AbstractAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Collections;

public abstract class SimpleNullableAttribute extends AbstractAttribute {
   public SimpleNullableAttribute(String attributeName) {
      super(attributeName);
   }

   public SimpleNullableAttribute() {
   }

   public SimpleNullableAttribute(Class objectType, Class attributeType) {
      super(objectType, attributeType);
   }

   public SimpleNullableAttribute(Class objectType, Class attributeType, String attributeName) {
      super(objectType, attributeType, attributeName);
   }

   public Iterable getValues(Object object, QueryOptions queryOptions) {
      Object value = this.getValue(object, queryOptions);
      return value == null ? Collections.emptyList() : Collections.singletonList(value);
   }

   public abstract Object getValue(Object var1, QueryOptions var2);
}

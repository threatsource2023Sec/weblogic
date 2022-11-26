package com.googlecode.cqengine.attribute;

import com.googlecode.cqengine.attribute.support.AbstractAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Collections;

public abstract class SimpleAttribute extends AbstractAttribute {
   public SimpleAttribute() {
   }

   public SimpleAttribute(String attributeName) {
      super(attributeName);
   }

   public SimpleAttribute(Class objectType, Class attributeType) {
      super(objectType, attributeType);
   }

   public SimpleAttribute(Class objectType, Class attributeType, String attributeName) {
      super(objectType, attributeType, attributeName);
   }

   public Iterable getValues(Object object, QueryOptions queryOptions) {
      return Collections.singletonList(this.getValue(object, queryOptions));
   }

   public abstract Object getValue(Object var1, QueryOptions var2);

   public boolean canEqual(Object other) {
      return other instanceof SimpleAttribute;
   }
}

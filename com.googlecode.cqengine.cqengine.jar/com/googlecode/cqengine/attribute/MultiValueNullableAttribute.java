package com.googlecode.cqengine.attribute;

import com.googlecode.cqengine.attribute.support.AbstractAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.iterator.IteratorUtil;
import java.util.Collections;
import java.util.Iterator;

public abstract class MultiValueNullableAttribute extends AbstractAttribute {
   final boolean componentValuesNullable;

   public MultiValueNullableAttribute(String attributeName, boolean componentValuesNullable) {
      super(attributeName);
      this.componentValuesNullable = componentValuesNullable;
   }

   public MultiValueNullableAttribute(boolean componentValuesNullable) {
      this.componentValuesNullable = componentValuesNullable;
   }

   public MultiValueNullableAttribute(Class objectType, Class attributeType, boolean componentValuesNullable) {
      super(objectType, attributeType);
      this.componentValuesNullable = componentValuesNullable;
   }

   public MultiValueNullableAttribute(Class objectType, Class attributeType, String attributeName, boolean componentValuesNullable) {
      super(objectType, attributeType, attributeName);
      this.componentValuesNullable = componentValuesNullable;
   }

   public Iterable getValues(Object object, QueryOptions queryOptions) {
      Iterable values = this.getNullableValues(object, queryOptions);
      final Iterable values = values == null ? Collections.emptyList() : values;
      return (Iterable)(!this.componentValuesNullable ? values : new Iterable() {
         public Iterator iterator() {
            return IteratorUtil.removeNulls(((Iterable)values).iterator());
         }
      });
   }

   public abstract Iterable getNullableValues(Object var1, QueryOptions var2);
}

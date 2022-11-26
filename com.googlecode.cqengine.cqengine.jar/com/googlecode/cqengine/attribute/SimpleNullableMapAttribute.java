package com.googlecode.cqengine.attribute;

import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Map;

public class SimpleNullableMapAttribute extends SimpleNullableAttribute {
   final Object mapKey;

   public SimpleNullableMapAttribute(Object mapKey, Class mapValueType) {
      super(Map.class, mapValueType, mapKey.toString());
      this.mapKey = mapKey;
   }

   public SimpleNullableMapAttribute(Object mapKey, Class mapValueType, String attributeName) {
      super(Map.class, mapValueType, attributeName);
      this.mapKey = mapKey;
   }

   public Object getValue(Map map, QueryOptions queryOptions) {
      Object result = map.get(this.mapKey);
      if (result != null && !this.getAttributeType().isAssignableFrom(result.getClass())) {
         throw new ClassCastException("Cannot cast " + result.getClass().getName() + " to " + this.getAttributeType().getName() + " for map key: " + this.mapKey);
      } else {
         return this.getAttributeType().cast(result);
      }
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + this.mapKey.hashCode();
      return result;
   }

   public boolean canEqual(Object other) {
      return other instanceof SimpleNullableMapAttribute;
   }

   public boolean equals(Object other) {
      return super.equals(other) && this.mapKey.equals(((SimpleNullableMapAttribute)other).mapKey);
   }
}

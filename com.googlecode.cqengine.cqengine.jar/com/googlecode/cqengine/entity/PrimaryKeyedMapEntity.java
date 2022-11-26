package com.googlecode.cqengine.entity;

import java.util.Map;

public class PrimaryKeyedMapEntity extends MapEntity {
   private final Object primaryKey;

   public PrimaryKeyedMapEntity(Map mapToWrap, Object mapPrimaryKey) {
      super(mapToWrap, getPrimaryKeyHashCode(mapToWrap, mapPrimaryKey));
      this.primaryKey = mapPrimaryKey;
   }

   static int getPrimaryKeyHashCode(Map map, Object primaryKey) {
      Object value = map.get(primaryKey);
      return value.hashCode();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof PrimaryKeyedMapEntity)) {
         return false;
      } else {
         PrimaryKeyedMapEntity that = (PrimaryKeyedMapEntity)o;
         if (this.cachedHashCode != that.cachedHashCode) {
            return false;
         } else {
            Object thisPkValue = this.get(this.primaryKey);
            Object thatPkValue = that.get(this.primaryKey);
            return thisPkValue.equals(thatPkValue);
         }
      }
   }

   public String toString() {
      return "PrimaryKeyedMapEntity{primaryKey=" + this.primaryKey + ", cachedHashCode=" + this.cachedHashCode + ", wrappedMap=" + this.wrappedMap + '}';
   }
}

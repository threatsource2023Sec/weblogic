package com.kenai.jffi;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class ObjectParameterInfo {
   private static final ConcurrentMap CACHE = new ConcurrentHashMap();
   private final int parameterIndex;
   private final int ioflags;
   private final int objectInfo;
   public static final int IN = 1;
   public static final int OUT = 2;
   public static final int PINNED = 8;
   public static final int NULTERMINATE = 4;
   public static final int CLEAR = 16;
   public static final ObjectType ARRAY;
   public static final ObjectType BUFFER;
   public static final ComponentType BYTE;
   public static final ComponentType SHORT;
   public static final ComponentType INT;
   public static final ComponentType LONG;
   public static final ComponentType FLOAT;
   public static final ComponentType DOUBLE;
   public static final ComponentType BOOLEAN;
   public static final ComponentType CHAR;

   public static ObjectParameterInfo create(int parameterIndex, ObjectType objectType, ComponentType componentType, int ioflags) {
      return getCachedInfo(ObjectBuffer.makeObjectFlags(ioflags, objectType.value | componentType.value, parameterIndex));
   }

   public static ObjectParameterInfo create(int parameterIndex, int ioflags) {
      return getCachedInfo(ObjectBuffer.makeObjectFlags(ioflags, 0, parameterIndex));
   }

   private static ObjectParameterInfo getCachedInfo(int objectInfo) {
      ObjectParameterInfo info = (ObjectParameterInfo)CACHE.get(objectInfo);
      if (info != null) {
         return info;
      } else {
         ObjectParameterInfo cachedInfo = (ObjectParameterInfo)CACHE.putIfAbsent(objectInfo, info = new ObjectParameterInfo(objectInfo));
         return cachedInfo != null ? cachedInfo : info;
      }
   }

   private ObjectParameterInfo(int objectInfo) {
      this.objectInfo = objectInfo;
      this.ioflags = objectInfo & 255;
      this.parameterIndex = (objectInfo & 16711680) >> 16;
   }

   final int asObjectInfo() {
      return this.objectInfo;
   }

   final int ioflags() {
      return this.ioflags;
   }

   public final int getParameterIndex() {
      return this.parameterIndex;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ObjectParameterInfo info = (ObjectParameterInfo)o;
         return this.objectInfo == info.objectInfo;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return 31 * this.objectInfo;
   }

   static {
      ARRAY = ObjectParameterInfo.ObjectType.ARRAY;
      BUFFER = ObjectParameterInfo.ObjectType.BUFFER;
      BYTE = ObjectParameterInfo.ComponentType.BYTE;
      SHORT = ObjectParameterInfo.ComponentType.SHORT;
      INT = ObjectParameterInfo.ComponentType.INT;
      LONG = ObjectParameterInfo.ComponentType.LONG;
      FLOAT = ObjectParameterInfo.ComponentType.FLOAT;
      DOUBLE = ObjectParameterInfo.ComponentType.DOUBLE;
      BOOLEAN = ObjectParameterInfo.ComponentType.BOOLEAN;
      CHAR = ObjectParameterInfo.ComponentType.CHAR;
   }

   public static enum ComponentType {
      BYTE(16777216),
      SHORT(33554432),
      INT(50331648),
      LONG(67108864),
      FLOAT(83886080),
      DOUBLE(100663296),
      BOOLEAN(117440512),
      CHAR(134217728);

      final int value;

      private ComponentType(int type) {
         this.value = type;
      }
   }

   public static enum ObjectType {
      ARRAY(268435456),
      BUFFER(536870912);

      final int value;

      private ObjectType(int type) {
         this.value = type;
      }
   }
}

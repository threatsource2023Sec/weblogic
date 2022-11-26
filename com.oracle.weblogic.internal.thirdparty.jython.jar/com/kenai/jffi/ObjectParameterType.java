package com.kenai.jffi;

import java.util.EnumSet;
import java.util.Iterator;

public final class ObjectParameterType {
   final int typeInfo;
   static final ObjectParameterType INVALID = new ObjectParameterType(0);
   static final ObjectParameterType NONE = new ObjectParameterType(0);
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

   public static ObjectParameterType create(ObjectType objectType, ComponentType componentType) {
      if (objectType == ObjectParameterType.ObjectType.ARRAY) {
         return ObjectParameterType.TypeCache.arrayTypeCache[componentType.ordinal()];
      } else {
         return objectType == ObjectParameterType.ObjectType.BUFFER ? ObjectParameterType.TypeCache.bufferTypeCache[componentType.ordinal()] : new ObjectParameterType(objectType.value | componentType.value);
      }
   }

   ObjectParameterType(int typeInfo) {
      this.typeInfo = typeInfo;
   }

   ObjectParameterType(ObjectType objectType, ComponentType componentType) {
      this.typeInfo = objectType.value | componentType.value;
   }

   public boolean equals(Object o) {
      return this == o || o instanceof ObjectParameterType && this.typeInfo == ((ObjectParameterType)o).typeInfo;
   }

   public int hashCode() {
      return this.typeInfo;
   }

   static {
      ARRAY = ObjectParameterType.ObjectType.ARRAY;
      BUFFER = ObjectParameterType.ObjectType.BUFFER;
      BYTE = ObjectParameterType.ComponentType.BYTE;
      SHORT = ObjectParameterType.ComponentType.SHORT;
      INT = ObjectParameterType.ComponentType.INT;
      LONG = ObjectParameterType.ComponentType.LONG;
      FLOAT = ObjectParameterType.ComponentType.FLOAT;
      DOUBLE = ObjectParameterType.ComponentType.DOUBLE;
      BOOLEAN = ObjectParameterType.ComponentType.BOOLEAN;
      CHAR = ObjectParameterType.ComponentType.CHAR;
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

   private static final class TypeCache {
      static final ObjectParameterType[] arrayTypeCache;
      static final ObjectParameterType[] bufferTypeCache;

      static {
         EnumSet componentTypes = EnumSet.allOf(ComponentType.class);
         arrayTypeCache = new ObjectParameterType[componentTypes.size()];
         bufferTypeCache = new ObjectParameterType[componentTypes.size()];

         ComponentType componentType;
         for(Iterator var1 = componentTypes.iterator(); var1.hasNext(); bufferTypeCache[componentType.ordinal()] = new ObjectParameterType(ObjectParameterType.BUFFER, componentType)) {
            componentType = (ComponentType)var1.next();
            arrayTypeCache[componentType.ordinal()] = new ObjectParameterType(ObjectParameterType.ARRAY, componentType);
         }

      }
   }
}

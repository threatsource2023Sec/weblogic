package com.googlecode.cqengine.attribute;

import com.googlecode.cqengine.query.option.QueryOptions;
import java.lang.reflect.Field;

public class ReflectiveAttribute extends SimpleAttribute {
   final Field field;

   public ReflectiveAttribute(Class objectType, Class fieldType, String fieldName) {
      super(objectType, fieldType, fieldName);

      Field field;
      try {
         field = getField(objectType, fieldName);
         if (!field.isAccessible()) {
            field.setAccessible(true);
         }
      } catch (Exception var6) {
         throw new IllegalStateException("Invalid attribute definition: No such field '" + fieldName + "' in object '" + objectType.getName() + "'");
      }

      if (!fieldType.isAssignableFrom(field.getType())) {
         throw new IllegalStateException("Invalid attribute definition: The type of field '" + fieldName + "', type '" + field.getType() + "', in object '" + objectType.getName() + "', is not assignable to the type indicated: " + fieldType.getName());
      } else {
         this.field = field;
      }
   }

   static Field getField(Class cls, String fieldName) throws NoSuchFieldException {
      while(cls != null && cls != Object.class) {
         try {
            return cls.getDeclaredField(fieldName);
         } catch (NoSuchFieldException var3) {
            cls = cls.getSuperclass();
         }
      }

      throw new NoSuchFieldException("No such field: " + fieldName);
   }

   public Object getValue(Object object, QueryOptions queryOptions) {
      try {
         Object value = this.field.get(object);
         return value;
      } catch (Exception var4) {
         throw new IllegalStateException("Failed to read value from field '" + this.field.getName() + "'", var4);
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof ReflectiveAttribute)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         ReflectiveAttribute that = (ReflectiveAttribute)o;
         if (!that.canEqual(this)) {
            return false;
         } else {
            return this.field.equals(that.field);
         }
      }
   }

   public boolean canEqual(Object other) {
      return other instanceof ReflectiveAttribute;
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + this.field.hashCode();
      return result;
   }

   public static ReflectiveAttribute forField(Class objectType, Class fieldType, String fieldName) {
      return new ReflectiveAttribute(objectType, fieldType, fieldName);
   }
}

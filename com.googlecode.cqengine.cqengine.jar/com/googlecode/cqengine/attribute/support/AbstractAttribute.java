package com.googlecode.cqengine.attribute.support;

import com.googlecode.cqengine.attribute.Attribute;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbstractAttribute implements Attribute {
   private final Class objectType;
   private final Class attributeType;
   private final String attributeName;
   private final int cachedHashCode;

   public AbstractAttribute() {
      this.attributeName = "<Unnamed attribute, " + this.getClass() + ">";
      this.objectType = readGenericObjectType(this.getClass(), this.attributeName);
      this.attributeType = readGenericAttributeType(this.getClass(), this.attributeName);
      this.cachedHashCode = this.calcHashCode();
   }

   public AbstractAttribute(String attributeName) {
      this.attributeName = attributeName;
      this.objectType = readGenericObjectType(this.getClass(), attributeName);
      this.attributeType = readGenericAttributeType(this.getClass(), attributeName);
      this.cachedHashCode = this.calcHashCode();
   }

   protected AbstractAttribute(Class objectType, Class attributeType) {
      this.attributeName = "<Unnamed attribute, " + this.getClass() + ">";
      this.objectType = objectType;
      this.attributeType = attributeType;
      this.cachedHashCode = this.calcHashCode();
   }

   protected AbstractAttribute(Class objectType, Class attributeType, String attributeName) {
      this.attributeName = attributeName;
      this.objectType = objectType;
      this.attributeType = attributeType;
      this.cachedHashCode = this.calcHashCode();
   }

   public Class getObjectType() {
      return this.objectType;
   }

   public Class getAttributeType() {
      return this.attributeType;
   }

   public String getAttributeName() {
      return this.attributeName;
   }

   public String toString() {
      return "Attribute{objectType=" + this.objectType + ", attributeType=" + this.attributeType + ", attributeName='" + this.attributeName + '\'' + '}';
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof AbstractAttribute)) {
         return false;
      } else {
         AbstractAttribute that = (AbstractAttribute)o;
         if (!that.canEqual(this)) {
            return false;
         } else if (!this.attributeName.equals(that.attributeName)) {
            return false;
         } else if (!this.attributeType.equals(that.attributeType)) {
            return false;
         } else {
            return this.objectType.equals(that.objectType);
         }
      }
   }

   public boolean canEqual(Object other) {
      return other instanceof AbstractAttribute;
   }

   public int hashCode() {
      return this.cachedHashCode;
   }

   protected int calcHashCode() {
      int result = this.objectType.hashCode();
      result = 31 * result + this.attributeType.hashCode();
      result = 31 * result + this.attributeName.hashCode();
      return result;
   }

   static Class readGenericObjectType(Class attributeClass, String attributeName) {
      try {
         ParameterizedType superclass = (ParameterizedType)attributeClass.getGenericSuperclass();
         Type actualType = superclass.getActualTypeArguments()[0];
         Class cls;
         if (actualType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)actualType;
            Class actualClass = (Class)parameterizedType.getRawType();
            cls = actualClass;
         } else {
            Class actualClass = (Class)actualType;
            cls = actualClass;
         }

         return cls;
      } catch (Exception var7) {
         String attributeClassStr = attributeName.startsWith("<Unnamed attribute, class ") ? "" : " (" + attributeClass + ")";
         throw new IllegalStateException("Attribute '" + attributeName + "'" + attributeClassStr + " is invalid, cannot read generic type information from it. Attributes should typically EITHER be declared in code with generic type information as a (possibly anonymous) subclass of one of the provided attribute types, OR you can use a constructor of the attribute which allows the types to be specified manually.");
      }
   }

   static Class readGenericAttributeType(Class attributeClass, String attributeName) {
      try {
         ParameterizedType superclass = (ParameterizedType)attributeClass.getGenericSuperclass();
         Type actualType = superclass.getActualTypeArguments()[1];
         Class cls;
         if (actualType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)actualType;
            Class actualClass = (Class)parameterizedType.getRawType();
            cls = actualClass;
         } else {
            Class actualClass = (Class)actualType;
            cls = actualClass;
         }

         return cls;
      } catch (Exception var7) {
         String attributeClassStr = attributeName.startsWith("<Unnamed attribute, class ") ? "" : " (" + attributeClass + ")";
         throw new IllegalStateException("Attribute '" + attributeName + "'" + attributeClassStr + " is invalid, cannot read generic type information from it. Attributes should typically EITHER be declared in code with generic type information as a (possibly anonymous) subclass of one of the provided attribute types, OR you can use a constructor of the attribute which allows the types to be specified manually.");
      }
   }
}

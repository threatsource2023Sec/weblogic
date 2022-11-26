package org.glassfish.admin.rest.model;

public class ArrayTypeInfo implements TypeInfo {
   private TypeInfo componentType;

   public ArrayTypeInfo(TypeInfo componentType) {
      this.componentType = componentType;
   }

   public TypeInfo getComponentType() {
      return this.componentType;
   }

   public String toString() {
      return "ArrayTypeInfo [componentType=" + this.getComponentType() + "]";
   }
}

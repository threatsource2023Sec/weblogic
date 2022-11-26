package com.bea.adaptive.harvester.jmx;

public class DataType {
   Class type;
   boolean isArray;

   DataType(Class type) {
      this(type, false);
   }

   DataType(Class type, boolean isArray) {
      this.type = type;
      this.isArray = isArray;
   }

   boolean isArray() {
      return this.isArray;
   }

   Class getType() {
      return this.type;
   }

   public String toString() {
      return "DataType: " + this.type;
   }

   static DataType classify(String typeName) throws ClassNotFoundException {
      if (typeName.equals("int")) {
         return new PrimitiveDataType(Integer.TYPE);
      } else if (typeName.equals("boolean")) {
         return new PrimitiveDataType(Boolean.TYPE);
      } else if (typeName.equals("long")) {
         return new PrimitiveDataType(Long.TYPE);
      } else if (typeName.equals("double")) {
         return new PrimitiveDataType(Double.TYPE);
      } else if (typeName.equals("char")) {
         return new PrimitiveDataType(Character.TYPE);
      } else if (typeName.equals("byte")) {
         return new PrimitiveDataType(Byte.TYPE);
      } else {
         Class clz = Class.forName(typeName);
         if (!typeName.equals("java.lang.String") && !typeName.equals("int") && !typeName.equals("boolean") && !typeName.equals("long") && !typeName.equals("java.lang.Integer") && !typeName.equals("java.lang.Boolean") && !typeName.equals("java.lang.Long") && !typeName.equals("double") && !typeName.equals("char") && !typeName.equals("byte") && !typeName.equals("java.lang.Float") && !typeName.equals("java.lang.Double") && !typeName.equals("java.lang.Character") && !typeName.equals("java.lang.Byte")) {
            if (typeName.equals("javax.management.ObjectName")) {
               return new ReferenceDataType();
            } else {
               return (DataType)(clz.isArray() ? new ArrayDataType(typeName, clz) : new StructuralDataType(clz));
            }
         } else {
            return new PrimitiveDataType(clz);
         }
      }
   }
}

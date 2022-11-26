package com.bea.core.repackaged.springframework.expression.spel.ast;

public enum TypeCode {
   OBJECT(Object.class),
   BOOLEAN(Boolean.TYPE),
   BYTE(Byte.TYPE),
   CHAR(Character.TYPE),
   DOUBLE(Double.TYPE),
   FLOAT(Float.TYPE),
   INT(Integer.TYPE),
   LONG(Long.TYPE),
   SHORT(Short.TYPE);

   private Class type;

   private TypeCode(Class type) {
      this.type = type;
   }

   public Class getType() {
      return this.type;
   }

   public static TypeCode forName(String name) {
      TypeCode[] tcs = values();

      for(int i = 1; i < tcs.length; ++i) {
         if (tcs[i].name().equalsIgnoreCase(name)) {
            return tcs[i];
         }
      }

      return OBJECT;
   }

   public static TypeCode forClass(Class clazz) {
      TypeCode[] allValues = values();
      TypeCode[] var2 = allValues;
      int var3 = allValues.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TypeCode typeCode = var2[var4];
         if (clazz == typeCode.getType()) {
            return typeCode;
         }
      }

      return OBJECT;
   }
}

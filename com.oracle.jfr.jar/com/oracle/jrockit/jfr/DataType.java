package com.oracle.jrockit.jfr;

public enum DataType {
   BOOLEAN(Boolean.TYPE, 1, true),
   BYTE(Byte.TYPE, 1, true),
   U1(Byte.TYPE, 1),
   SHORT(Short.TYPE, 2, true),
   U2(Short.TYPE, 2, false),
   INTEGER(Integer.TYPE, 4, true),
   U4(Integer.TYPE, 4),
   LONG(Long.TYPE, 8, true),
   U8(Long.TYPE, 8),
   FLOAT(Float.TYPE, 4, true),
   DOUBLE(Double.TYPE, 8, true),
   UTF8(String.class, 0),
   STRING(String.class, 0, true),
   ARRAY,
   STRUCT,
   STRUCTARRAY;

   private final Class javaType;
   private final int size;
   private final boolean primary;

   private DataType() {
      this((Class)null, 0, false);
   }

   private DataType(Class type, int size, boolean primary) {
      this.javaType = type;
      this.size = size;
      this.primary = primary;
   }

   private DataType(Class javaType, int size) {
      this(javaType, size, false);
   }

   public int getSize() {
      return this.size;
   }

   public boolean isPrimary() {
      return this.primary;
   }

   public Class getJavaType() {
      return this.javaType;
   }

   public boolean isPrimitive() {
      return this.ordinal() < ARRAY.ordinal();
   }
}

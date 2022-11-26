package com.bea.adaptive.harvester.jmx;

class ArrayDataType extends DataType {
   int levels;
   DataType baseType;

   ArrayDataType(String attrTypeName, Class clz) throws ClassNotFoundException {
      super(clz.getComponentType(), true);

      int index;
      for(index = 0; index < attrTypeName.length() - 1 && attrTypeName.charAt(index) == '['; index += 2) {
      }

      this.levels = index / 2;
      this.baseType = classify(this.type.getName());
   }

   DataType getBaseType() {
      return this.baseType;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append("Array: ( ").append(this.baseType);

      for(int i = 0; i < this.levels; ++i) {
         buffer.append("[]");
      }

      buffer.append(" )");
      return buffer.toString();
   }
}

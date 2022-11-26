package com.bea.adaptive.harvester.jmx;

class PrimitiveDataType extends DataType {
   public PrimitiveDataType(Class clz) {
      super(clz);
   }

   public String toString() {
      return "Primitive: " + this.type.getName();
   }
}

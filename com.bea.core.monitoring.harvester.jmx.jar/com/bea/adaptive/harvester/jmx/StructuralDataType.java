package com.bea.adaptive.harvester.jmx;

class StructuralDataType extends DataType {
   StructuralDataType(Class clz) {
      super(clz);
   }

   public String toString() {
      return "Structure: " + this.type.getName();
   }
}

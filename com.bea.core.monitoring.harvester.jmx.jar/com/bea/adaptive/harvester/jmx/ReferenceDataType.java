package com.bea.adaptive.harvester.jmx;

import javax.management.ObjectName;

class ReferenceDataType extends DataType {
   public ReferenceDataType() {
      super(ObjectName.class);
   }

   public String toString() {
      return "Reference";
   }
}

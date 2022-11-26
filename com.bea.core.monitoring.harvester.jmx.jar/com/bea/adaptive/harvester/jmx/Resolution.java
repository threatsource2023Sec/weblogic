package com.bea.adaptive.harvester.jmx;

class Resolution {
   AttributeSpec attrSpec;
   String error;
   String instance;

   Resolution(String instance, AttributeSpec attrSpec, String error) {
      this.instance = instance;
      this.attrSpec = attrSpec;
      this.error = error;
   }
}

package com.bea.logging;

public enum LoggingSupplementalAttribute {
   SUPP_ATTR_SEVERITY_VALUE("severity-value"),
   SUPP_ATTR_PARTITION_ID("partition-id"),
   SUPP_ATTR_PARTITION_NAME("partition-name"),
   SUPP_ATTR_RID("rid"),
   SUPP_ATTR_APPLICATION_NAME("app"),
   SUPP_ATTR_MODULE_NAME("module");

   private final String attributeName;

   private LoggingSupplementalAttribute(String name) {
      this.attributeName = name;
   }

   public String getAttributeName() {
      return this.attributeName;
   }
}

package com.bea.adaptive.harvester;

public final class RawValueDataImpl implements WatchedValues.Values.RawValueData {
   private String type;
   private String instance;
   private String attribute;
   private Object value;

   RawValueDataImpl(String type, String instance, String attribute, Object value) {
      this.type = type;
      this.instance = instance;
      this.attribute = attribute;
      this.value = value;
   }

   public String getInstanceName() {
      return this.instance;
   }

   public String getTypeName() {
      return this.type;
   }

   public Object getValue() {
      return this.value;
   }

   public String toString() {
      return "" + this.instance + ":" + this.value;
   }

   public String getAttributeName() {
      return this.attribute;
   }
}

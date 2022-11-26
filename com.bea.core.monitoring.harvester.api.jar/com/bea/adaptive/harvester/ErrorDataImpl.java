package com.bea.adaptive.harvester;

public class ErrorDataImpl implements WatchedValues.Values.ErrorData {
   String typeName;
   String reason;
   String instName;
   boolean isAttributeDeactivated;

   public ErrorDataImpl(String typeName, String instName, String reason, boolean isAttributeDeactivated) {
      this.typeName = typeName;
      this.instName = instName;
      this.reason = reason;
      this.isAttributeDeactivated = isAttributeDeactivated;
   }

   public String getTypeName() {
      return this.typeName;
   }

   public String getInstanceName() {
      return this.instName;
   }

   public String getReason() {
      return this.reason;
   }

   public boolean isAttributeDeactivated() {
      return this.isAttributeDeactivated;
   }

   public String toString() {
      return "" + this.instName + ":" + this.reason;
   }
}

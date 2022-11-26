package com.bea.adaptive.harvester.jmx;

public class SlotKey {
   private String instName;
   private String attrName;
   private String typeName;
   private AttributeSpec attr;
   private static HarvesterJMXTextTextFormatter mtf_base = HarvesterJMXTextTextFormatter.getInstance();
   private int hashCode;

   SlotKey(String typeName, String instName, AttributeSpec attr) {
      this.instName = instName;
      this.attrName = attr.getName();
      this.typeName = typeName;
      this.attr = attr;
      this.initHashCode();
   }

   SlotKey(String instName, String attrName) {
      this.instName = instName;
      this.attrName = attrName;
      this.initHashCode();
   }

   String dump(String indent) {
      StringBuffer buf = new StringBuffer(256);
      buf.append(indent + mtf_base.getKeyLabel() + ":").append("  ").append(mtf_base.getTypeNameLabel()).append(":").append(this.typeName).append("  ").append(mtf_base.getInstNameLabel()).append(":").append(this.instName).append("  ").append(mtf_base.getAttrNameLabel()).append(":").append(this.attrName);
      return buf.toString();
   }

   public int hashCode() {
      return this.hashCode;
   }

   public boolean equals(Object o) {
      if (!(o instanceof SlotKey)) {
         return false;
      } else {
         SlotKey other = (SlotKey)o;
         return this.instName.equals(other.instName) && this.attrName.equals(other.attrName);
      }
   }

   public String toString() {
      return this.instName + "//" + this.attrName;
   }

   public String getTypeName() {
      return this.typeName;
   }

   public String getInstanceName() {
      return this.instName;
   }

   public String getAttributeName() {
      return this.attrName;
   }

   public AttributeSpec getAttributeSpec() {
      return this.attr;
   }

   private void initHashCode() {
      this.hashCode = (this.instName + "//" + this.attrName).hashCode();
   }
}

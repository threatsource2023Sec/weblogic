package com.bea.adaptive.harvester.jmx;

import java.lang.reflect.Method;

public class AttributeSpec {
   private String attrName;
   private String dataType;
   private String mBeanType;
   private AttributeTerm evalChain;
   private boolean resolved;
   private Method readMethod;
   private String attributeDescription;
   private boolean visibleToPartitions;

   public String getAttributeDescription() {
      return this.attributeDescription;
   }

   public void setAttributeDescription(String attributeDescription) {
      this.attributeDescription = attributeDescription;
   }

   public Method getReadMethod() {
      return this.readMethod;
   }

   public void setReadMethod(Method readMethod) {
      this.readMethod = readMethod;
   }

   public AttributeSpec(String attrName, String mbeanType, String dataType, boolean isVisibleToPartitions, AttributeTerm evalChain) {
      this(attrName, mbeanType, dataType, isVisibleToPartitions, evalChain, true);
   }

   AttributeSpec(String attrName, String mbeanType, String dataType, boolean isVisibleToPartitions, AttributeTerm evalChain, boolean resolved) {
      this.attrName = null;
      this.attrName = attrName;
      this.dataType = dataType;
      this.evalChain = evalChain;
      this.mBeanType = mbeanType;
      this.resolved = resolved;
      this.visibleToPartitions = isVisibleToPartitions;
   }

   public boolean isResolved() {
      return this.resolved;
   }

   public void setResolved(boolean resolved) {
      this.resolved = resolved;
   }

   public boolean isVisibleToPartitions() {
      return this.visibleToPartitions;
   }

   public String getName() {
      return this.attrName;
   }

   public String getDataType() {
      return this.dataType;
   }

   AttributeTerm getEvalChain() {
      return this.evalChain;
   }

   String getMBeanType() {
      return this.mBeanType;
   }

   public String toString() {
      return this.getName() + "//" + this.getMBeanType() + "//" + this.getDataType() + "//" + this.getEvalChain();
   }

   public void setDataType(String dataType) {
      this.dataType = dataType;
   }

   public void setEvalChain(AttributeTerm evalChain) {
      this.evalChain = evalChain;
   }
}

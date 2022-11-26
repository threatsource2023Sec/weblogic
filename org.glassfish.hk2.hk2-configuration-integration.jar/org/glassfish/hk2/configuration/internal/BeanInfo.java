package org.glassfish.hk2.configuration.internal;

public class BeanInfo {
   private final String typeName;
   private final String instanceName;
   private final Object bean;
   private final Object metadata;

   BeanInfo(String typeName, String instanceName, Object bean, Object metadata) {
      this.typeName = typeName;
      this.instanceName = instanceName;
      this.bean = bean;
      this.metadata = metadata;
   }

   public String getTypeName() {
      return this.typeName;
   }

   public String getInstanceName() {
      return this.instanceName;
   }

   public Object getBean() {
      return this.bean;
   }

   public Object getMetadata() {
      return this.metadata;
   }

   public String toString() {
      return "BeanInfo(" + this.typeName + "," + this.instanceName + "," + this.bean + "," + this.metadata + "," + System.identityHashCode(this) + ")";
   }
}

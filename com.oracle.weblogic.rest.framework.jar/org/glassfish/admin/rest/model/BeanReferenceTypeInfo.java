package org.glassfish.admin.rest.model;

public class BeanReferenceTypeInfo implements TypeInfo {
   private String beanDisplayName;
   private String beanType;

   public BeanReferenceTypeInfo(String beanDisplayName, String beanType) {
      this.beanDisplayName = beanDisplayName;
      this.beanType = beanType;
   }

   public String getBeanDisplayName() {
      return this.beanDisplayName;
   }

   public String getBeanType() {
      return this.beanType;
   }

   public String toString() {
      return "BeanReferenceTypeInfo [beanDisplayName=" + this.getBeanDisplayName() + ", beanType=" + this.getBeanType() + "]";
   }
}

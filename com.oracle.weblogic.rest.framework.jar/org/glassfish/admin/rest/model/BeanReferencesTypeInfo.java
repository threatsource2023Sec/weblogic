package org.glassfish.admin.rest.model;

public class BeanReferencesTypeInfo implements TypeInfo {
   private String beanDisplayName;
   private String beanType;

   public BeanReferencesTypeInfo(String beanDisplayName, String beanType) {
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
      return "BeanReferencesTypeInfo [beanDisplayName=" + this.getBeanDisplayName() + ", beanType=" + this.getBeanType() + "]";
   }
}

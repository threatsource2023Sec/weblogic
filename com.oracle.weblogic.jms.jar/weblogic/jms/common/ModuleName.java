package weblogic.jms.common;

import weblogic.jms.module.JMSBeanHelper;

public class ModuleName {
   private String applicationName;
   private String earModuleName;
   private String fullyQualifiedModuleName;

   public ModuleName(String paramApplicationName, String paramModuleName) {
      this.applicationName = paramApplicationName;
      this.earModuleName = paramModuleName;
      this.fullyQualifiedModuleName = this.earModuleName == null ? this.applicationName : JMSBeanHelper.getDecoratedName(this.applicationName, this.earModuleName);
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   public String getEARModuleName() {
      return this.earModuleName;
   }

   public String getFullyQualifiedModuleName() {
      return this.fullyQualifiedModuleName;
   }

   public boolean equals(Object compareMe) {
      if (compareMe != null && compareMe instanceof ModuleName) {
         ModuleName mn = (ModuleName)compareMe;
         return mn.fullyQualifiedModuleName.equals(this.fullyQualifiedModuleName);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.fullyQualifiedModuleName.hashCode();
   }

   public String toString() {
      return this.fullyQualifiedModuleName;
   }
}

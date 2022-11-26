package weblogic.security.service;

import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.security.spi.ApplicationInfo;

public class SecurityApplicationInfoImpl implements SecurityApplicationInfo {
   private String applicationIdentifier;
   private ApplicationInfo.ComponentType componentType;
   private String componentName;
   private String securityDDModel = "DDOnly";
   private boolean validateDDSecurityData = false;

   public SecurityApplicationInfoImpl(AppDeploymentMBean mbean, ApplicationInfo.ComponentType componentType, String componentName) {
      this.componentType = componentType;
      this.componentName = componentName;
      if (mbean != null) {
         this.applicationIdentifier = mbean.getApplicationIdentifier();
         this.securityDDModel = mbean.getSecurityDDModel();
         this.validateDDSecurityData = mbean.isValidateDDSecurityData();
      } else {
         this.applicationIdentifier = componentName;
      }

   }

   public String getApplicationIdentifier() {
      return this.applicationIdentifier;
   }

   public String getComponentName() {
      return this.componentName;
   }

   public ApplicationInfo.ComponentType getComponentType() {
      return this.componentType;
   }

   public String getSecurityDDModel() {
      return this.securityDDModel;
   }

   public boolean isValidateDDSecurityData() {
      return this.validateDDSecurityData;
   }
}

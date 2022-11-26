package weblogic.connector.external.impl;

import java.util.HashMap;
import java.util.Map;
import weblogic.connector.external.SecurityIdentityInfo;
import weblogic.j2ee.descriptor.wl.AnonPrincipalBean;
import weblogic.j2ee.descriptor.wl.AnonPrincipalCallerBean;
import weblogic.j2ee.descriptor.wl.InboundCallerPrincipalMappingBean;
import weblogic.j2ee.descriptor.wl.InboundGroupPrincipalMappingBean;
import weblogic.j2ee.descriptor.wl.ResourceAdapterSecurityBean;
import weblogic.j2ee.descriptor.wl.SecurityWorkContextBean;

public class SecurityIdentityInfoImpl implements SecurityIdentityInfo {
   private ResourceAdapterSecurityBean raSecurityBean;
   private SecurityWorkContextBean securityWorkContext;

   public SecurityIdentityInfoImpl(ResourceAdapterSecurityBean raSecurityBean) {
      this.raSecurityBean = raSecurityBean;
      if (raSecurityBean == null) {
         this.securityWorkContext = null;
      } else if (raSecurityBean.isSecurityWorkContextSet()) {
         this.securityWorkContext = raSecurityBean.getSecurityWorkContext();
      }

   }

   public boolean useCallerForRunAs() {
      AnonPrincipalCallerBean runAs = this.raSecurityBean.getRunAsPrincipalName();
      if (runAs != null) {
         return runAs.isUseCallerIdentity();
      } else {
         return this.raSecurityBean.getDefaultPrincipalName() == null;
      }
   }

   public boolean useCallerForRunWorkAs() {
      AnonPrincipalCallerBean runWorkAs = this.raSecurityBean.getRunWorkAsPrincipalName();
      if (runWorkAs != null) {
         return runWorkAs.isUseCallerIdentity();
      } else {
         return this.raSecurityBean.getDefaultPrincipalName() == null;
      }
   }

   public String getManageAsPrincipalName() {
      AnonPrincipalBean manageAs = this.raSecurityBean.getManageAsPrincipalName();
      if (manageAs != null) {
         if (manageAs.isUseAnonymousIdentity()) {
            return null;
         }

         if (manageAs.getPrincipalName() != null) {
            return manageAs.getPrincipalName();
         }
      }

      return this.getDefaultPrincipalName();
   }

   public String getRunAsPrincipalName() {
      AnonPrincipalCallerBean runAs = this.raSecurityBean.getRunAsPrincipalName();
      if (runAs != null) {
         if (runAs.isUseAnonymousIdentity() || runAs.isUseCallerIdentity()) {
            return null;
         }

         if (runAs.getPrincipalName() != null) {
            return runAs.getPrincipalName();
         }
      }

      return this.getDefaultPrincipalName();
   }

   public String getRunWorkAsPrincipalName() {
      AnonPrincipalCallerBean runWorkAs = this.raSecurityBean.getRunWorkAsPrincipalName();
      if (runWorkAs != null) {
         if (runWorkAs.isUseAnonymousIdentity() || runWorkAs.isUseCallerIdentity()) {
            return null;
         }

         if (runWorkAs.getPrincipalName() != null) {
            return runWorkAs.getPrincipalName();
         }
      }

      return this.getDefaultPrincipalName();
   }

   public String getDefaultPrincipalName() {
      AnonPrincipalBean def = this.raSecurityBean.getDefaultPrincipalName();
      if (def != null) {
         if (def.isUseAnonymousIdentity()) {
            return null;
         } else {
            String principalName = def.getPrincipalName();
            return principalName != null && principalName.length() == 0 ? null : principalName;
         }
      } else {
         return null;
      }
   }

   public boolean isInboundMappingRequired() {
      return this.securityWorkContext == null ? false : this.securityWorkContext.isInboundMappingRequired();
   }

   public String getDefaultCallerPrincipalMapped() {
      if (this.securityWorkContext != null && this.securityWorkContext.isCallerPrincipalDefaultMappedSet()) {
         AnonPrincipalBean defaultCallerPrincipal = this.securityWorkContext.getCallerPrincipalDefaultMapped();
         return defaultCallerPrincipal.isUseAnonymousIdentity() ? "" : defaultCallerPrincipal.getPrincipalName();
      } else {
         return null;
      }
   }

   public Map getInboundCallerPrincipalMapping() {
      Map result = new HashMap();
      if (this.securityWorkContext != null) {
         InboundCallerPrincipalMappingBean[] callerPrincipalMappings = this.securityWorkContext.getCallerPrincipalMappings();
         if (callerPrincipalMappings != null) {
            InboundCallerPrincipalMappingBean[] var3 = callerPrincipalMappings;
            int var4 = callerPrincipalMappings.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               InboundCallerPrincipalMappingBean principalMapping = var3[var5];
               String eisUserName = principalMapping.getEisCallerPrincipal();
               String mappedUserName = "";
               AnonPrincipalBean mappedCaler = principalMapping.getMappedCallerPrincipal();
               if (!mappedCaler.isUseAnonymousIdentity()) {
                  mappedUserName = principalMapping.getMappedCallerPrincipal().getPrincipalName();
               }

               result.put(eisUserName, mappedUserName);
            }
         }
      }

      return result;
   }

   public String getDefaultGroupMappedPrincipal() {
      return this.securityWorkContext != null ? this.securityWorkContext.getGroupPrincipalDefaultMapped() : null;
   }

   public Map getInboundGroupPrincipalMapping() {
      Map result = new HashMap();
      if (this.securityWorkContext != null) {
         InboundGroupPrincipalMappingBean[] groupMappings = this.securityWorkContext.getGroupPrincipalMappings();
         if (groupMappings != null) {
            InboundGroupPrincipalMappingBean[] var3 = groupMappings;
            int var4 = groupMappings.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               InboundGroupPrincipalMappingBean groupMapping = var3[var5];
               String eisGroupName = groupMapping.getEisGroupPrincipal();
               String mappedGroupName = groupMapping.getMappedGroupPrincipal();
               result.put(eisGroupName, mappedGroupName);
            }
         }
      }

      return result;
   }
}

package weblogic.connector.configuration.validation.wl;

import java.util.List;
import weblogic.connector.configuration.validation.DefaultValidator;
import weblogic.connector.configuration.validation.ValidationContext;
import weblogic.j2ee.descriptor.wl.AnonPrincipalBean;
import weblogic.j2ee.descriptor.wl.InboundCallerPrincipalMappingBean;
import weblogic.j2ee.descriptor.wl.InboundGroupPrincipalMappingBean;
import weblogic.j2ee.descriptor.wl.SecurityWorkContextBean;

public class WLSecurityWorkContextValidator extends DefaultValidator {
   private final SecurityWorkContextBean securityWorkContext;

   WLSecurityWorkContextValidator(ValidationContext context, SecurityWorkContextBean securityWorkContextBean) {
      super(context);
      this.securityWorkContext = securityWorkContextBean;
   }

   public int order() {
      return 120;
   }

   public void doValidate() {
      String defaultGroup;
      if (this.securityWorkContext.isCallerPrincipalDefaultMappedSet()) {
         AnonPrincipalBean callerPrincipalDefault = this.securityWorkContext.getCallerPrincipalDefaultMapped();
         if (!callerPrincipalDefault.isUseAnonymousIdentity()) {
            defaultGroup = callerPrincipalDefault.getPrincipalName();
            if (defaultGroup == null || defaultGroup.length() == 0) {
               this.report(fmt.principalNameNotEmpty("caller-principal-default-mapped"));
            }
         }
      }

      InboundCallerPrincipalMappingBean[] callerPrincipalMappings = this.securityWorkContext.getCallerPrincipalMappings();
      String eisGroup;
      if (callerPrincipalMappings != null) {
         InboundCallerPrincipalMappingBean[] var11 = callerPrincipalMappings;
         int var3 = callerPrincipalMappings.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            InboundCallerPrincipalMappingBean callerPrincipalMapping = var11[var4];
            String eisPrincipal = callerPrincipalMapping.getEisCallerPrincipal();
            if (eisPrincipal.length() == 0) {
               this.report(fmt.principalNameNotEmpty("eis-caller-principal"));
            }

            AnonPrincipalBean mappedCallerPrincipal = callerPrincipalMapping.getMappedCallerPrincipal();
            if (!mappedCallerPrincipal.isUseAnonymousIdentity()) {
               eisGroup = mappedCallerPrincipal.getPrincipalName();
               if (eisGroup.length() == 0) {
                  this.report(fmt.principalNameNotEmpty("mapped-caller-principal"));
               }
            }
         }
      }

      defaultGroup = this.securityWorkContext.getGroupPrincipalDefaultMapped();
      if (defaultGroup != null && defaultGroup.length() == 0) {
         this.report(fmt.principalNameNotEmpty("group-principal-default-mapped"));
      }

      InboundGroupPrincipalMappingBean[] groupPrincipalMappings = this.securityWorkContext.getGroupPrincipalMappings();
      if (groupPrincipalMappings != null) {
         InboundGroupPrincipalMappingBean[] var13 = groupPrincipalMappings;
         int var14 = groupPrincipalMappings.length;

         for(int var15 = 0; var15 < var14; ++var15) {
            InboundGroupPrincipalMappingBean groupPrincipalMapping = var13[var15];
            eisGroup = groupPrincipalMapping.getEisGroupPrincipal();
            if (eisGroup.length() == 0) {
               this.report(fmt.principalNameNotEmpty("eis-group-principal"));
            }

            String mappedGroup = groupPrincipalMapping.getMappedGroupPrincipal();
            if (mappedGroup.length() == 0) {
               this.report(fmt.principalNameNotEmpty("mapped-group-principal"));
            }
         }
      }

   }

   private void report(String msg) {
      if (this.securityWorkContext.isInboundMappingRequired()) {
         this.error("General", "General", msg);
      } else {
         this.warning(msg);
      }

   }

   protected void reportDuplicateProperties(String subComponent, String key, String elementName, List duplicatedProperties) {
   }
}

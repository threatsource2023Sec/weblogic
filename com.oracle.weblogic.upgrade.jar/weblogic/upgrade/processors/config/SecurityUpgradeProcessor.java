package weblogic.upgrade.processors.config;

import javax.management.InvalidAttributeValueException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.authentication.IdentityAsserterMBean;
import weblogic.upgrade.UpgradeException;
import weblogic.upgrade.processors.UpgradeProcessor;
import weblogic.upgrade.processors.UpgradeProcessorContext;

public class SecurityUpgradeProcessor implements UpgradeProcessor {
   private static final String JWT_TOKEN = "weblogic-jwt-token";

   public void upgradeConfiguration(DomainMBean root, UpgradeProcessorContext ctx) throws UpgradeException {
      RealmMBean[] realms = root.getSecurityConfiguration().getRealms();

      for(int i = 0; i < realms.length; ++i) {
         boolean jwtTypeEnabled = false;
         AuthenticationProviderMBean[] atnProviders = realms[i].getAuthenticationProviders();
         AuthenticationProviderMBean[] var7 = atnProviders;
         int var8 = atnProviders.length;

         int var9;
         String[] supportedTypes;
         int var15;
         for(var9 = 0; var9 < var8; ++var9) {
            AuthenticationProviderMBean atnProvider = var7[var9];
            if (atnProvider instanceof IdentityAsserterMBean) {
               IdentityAsserterMBean iaProvider = (IdentityAsserterMBean)atnProvider;
               String[] activeTypes = iaProvider.getActiveTypes();
               if (activeTypes != null && activeTypes.length > 0) {
                  supportedTypes = activeTypes;
                  int var14 = activeTypes.length;

                  for(var15 = 0; var15 < var14; ++var15) {
                     String type = supportedTypes[var15];
                     if ("weblogic-jwt-token".equals(type)) {
                        jwtTypeEnabled = true;
                        break;
                     }
                  }
               }
            }

            if (jwtTypeEnabled) {
               break;
            }
         }

         if (!jwtTypeEnabled) {
            boolean jwtTypeUpdated = false;
            AuthenticationProviderMBean[] var23 = atnProviders;
            var9 = atnProviders.length;

            for(int var24 = 0; var24 < var9; ++var24) {
               AuthenticationProviderMBean atnProvider = var23[var24];
               if (atnProvider instanceof IdentityAsserterMBean) {
                  IdentityAsserterMBean iaProvider = (IdentityAsserterMBean)atnProvider;
                  supportedTypes = iaProvider.getSupportedTypes();
                  if (supportedTypes != null && supportedTypes.length > 0) {
                     String[] var27 = supportedTypes;
                     var15 = supportedTypes.length;

                     for(int var28 = 0; var28 < var15; ++var28) {
                        String type = var27[var28];
                        if ("weblogic-jwt-token".equals(type)) {
                           String[] updatedTypes = new String[]{"weblogic-jwt-token"};
                           String[] activeTypes = iaProvider.getActiveTypes();
                           if (activeTypes != null && activeTypes.length > 0) {
                              updatedTypes = new String[activeTypes.length + 1];
                              System.arraycopy(activeTypes, 0, updatedTypes, 0, activeTypes.length);
                              updatedTypes[activeTypes.length] = "weblogic-jwt-token";
                           }

                           try {
                              iaProvider.setActiveTypes(updatedTypes);
                           } catch (InvalidAttributeValueException var21) {
                              throw new UpgradeException(var21);
                           }

                           jwtTypeUpdated = true;
                           break;
                        }
                     }
                  }
               }

               if (jwtTypeUpdated) {
                  break;
               }
            }
         }
      }

   }
}

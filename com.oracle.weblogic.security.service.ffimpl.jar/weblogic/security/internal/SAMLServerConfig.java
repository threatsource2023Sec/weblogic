package weblogic.security.internal;

import java.security.AccessController;
import weblogic.management.provider.ManagementService;
import weblogic.management.security.RealmMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class SAMLServerConfig extends SAMLSingleSignOnServiceConfigInfoImpl {
   private static SAMLServerConfig theInstance = null;
   private RealmMBean realm;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private static void logDebug(String method, String msg) {
      logDebug("SAMLServerConfig", method, msg);
   }

   private SAMLServerConfig() {
      this.realm = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration().getDefaultRealm();
   }

   public static synchronized SAMLServerConfig getConfig() {
      if (theInstance == null) {
         theInstance = new SAMLServerConfig();
      }

      return theInstance;
   }

   public static String[] getConfiguredIntersiteTransferURIs(String appContext) {
      SAMLServerConfig config = getConfig();
      return config.getConfiguredURIs(appContext, config.getIntersiteTransferURIs());
   }

   public static String[] getConfiguredAssertionConsumerURIs(String appContext) {
      SAMLServerConfig config = getConfig();
      return config.getConfiguredURIs(appContext, config.getAssertionConsumerURIs());
   }

   public static String[] getConfiguredAssertionRetrievalURIs(String appContext) {
      SAMLServerConfig config = getConfig();
      return config.getConfiguredURIs(appContext, config.getAssertionRetrievalURIs());
   }

   private String[] getConfiguredURIs(String appContext, String[] samlURIs) {
      if (appContext != null && appContext.length() != 0) {
         if (samlURIs != null && samlURIs.length != 0) {
            int appContextLen = appContext.length();
            String matchPath = appContext + "/";
            int numMatches = 0;
            String[] tmp = new String[samlURIs.length];

            for(int i = 0; i < samlURIs.length; ++i) {
               if (samlURIs[i] != null && samlURIs[i].length() != 0 && samlURIs[i].startsWith(matchPath)) {
                  tmp[numMatches] = samlURIs[i].substring(appContextLen);
                  ++numMatches;
               }
            }

            if (numMatches <= 0) {
               return null;
            } else {
               String[] matches = new String[numMatches];

               for(int i = 0; i < numMatches; ++i) {
                  matches[i] = tmp[i];
               }

               return matches;
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public static String getRealmName() {
      SAMLServerConfig config = getConfig();
      return config.realm.getName();
   }
}

package weblogic.security.utils;

import java.security.Security;
import java.util.ArrayList;
import weblogic.common.internal.VersionInfo;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.AnyIdentityDomainAuthenticatorMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.authentication.IdentityDomainAuthenticatorMBean;
import weblogic.management.security.authentication.MultiIdentityDomainAuthenticatorMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.service.ContextHandler;
import weblogic.security.shared.LoggerWrapper;

public abstract class SecurityUtils {
   private static final boolean DUMP_CTX = Boolean.getBoolean("weblogic.security.DumpContextHandler");
   public static final String SHA2_DEFAULT_SINCE_VERSION = "12.3.1.0.0";

   public static boolean logContextHandlerEnabled() {
      return DUMP_CTX;
   }

   public static void logContextHandler(String identifier, LoggerWrapper log, ContextHandler handler) {
      if (log != null) {
         if (handler == null) {
            if (log.isDebugEnabled()) {
               log.debug("ContextHandler for " + identifier + "is null.");
            }

         } else {
            if (log.isDebugEnabled()) {
               String[] names = handler.getNames();
               log.debug("Logging ContextHandler for " + identifier);

               for(int i = 0; i < names.length; ++i) {
                  log.debug("\t" + names[i] + "=" + handler.getValue(names[i]).toString());
               }
            }

         }
      }
   }

   private static boolean setCryptoJSecurityProperty(String securityProperty, String securityValue, String systemProperty) {
      boolean set = false;
      String currentSecurityValue = Security.getProperty(securityProperty);
      if ((currentSecurityValue == null || currentSecurityValue.trim().length() == 0) && !Boolean.getBoolean(systemProperty)) {
         Security.setProperty(securityProperty, securityValue);
         set = true;
      }

      return set;
   }

   public static void turnOffCryptoJDefaultJCEVerification() {
      String allowCryptoJDefaultJCEVerProp = "weblogic.security.allowCryptoJDefaultJCEVerification";
      String rsaCryptoJJCEVerifyProp = "com.rsa.cryptoj.jce.no.verify.jar";
      if (setCryptoJSecurityProperty(rsaCryptoJJCEVerifyProp, "true", allowCryptoJDefaultJCEVerProp)) {
         SecurityLogger.logDisallowingCryptoJDefaultJCEVerification(allowCryptoJDefaultJCEVerProp);
      }

   }

   public static void changeCryptoJDefaultPRNG() {
      String allowCryptoJDefaultPRNGProp = "weblogic.security.allowCryptoJDefaultPRNG";
      String rsaCryptoJDefaultRandomProp = "com.rsa.crypto.default.random";
      String OOTBCryptoJRandomAlgId = "ECDRBG128";
      String newDefaultRandomAlgId = "HMACDRBG";
      if (setCryptoJSecurityProperty(rsaCryptoJDefaultRandomProp, newDefaultRandomAlgId, allowCryptoJDefaultPRNGProp)) {
         SecurityLogger.logChangingCryptoJDefaultPRNG(allowCryptoJDefaultPRNGProp, OOTBCryptoJRandomAlgId, newDefaultRandomAlgId);
      }

   }

   public static String[] getDefaultIdentityDomains(AuthenticationProviderMBean atn) throws IllegalArgumentException {
      String[] emptyIDDS = new String[0];
      if (!(atn.getParentBean() instanceof RealmMBean)) {
         return emptyIDDS;
      } else {
         RealmMBean realm = (RealmMBean)atn.getParentBean();
         if (!(realm.getParentBean() instanceof SecurityConfigurationMBean)) {
            return emptyIDDS;
         } else {
            SecurityConfigurationMBean secCfg = (SecurityConfigurationMBean)realm.getParentBean();
            if (!(secCfg.getParentBean() instanceof DomainMBean)) {
               return emptyIDDS;
            } else {
               DomainMBean domain = (DomainMBean)secCfg.getParentBean();
               if (secCfg.getAdministrativeIdentityDomain() == null) {
                  return emptyIDDS;
               } else if (!secCfg.isIdentityDomainDefaultEnabled()) {
                  return emptyIDDS;
               } else {
                  boolean isDefaultRealm = realm.equals(secCfg.getDefaultRealm());
                  ArrayList defaultIDDS = new ArrayList();
                  if (secCfg.getAdministrativeIdentityDomain() != null && isDefaultRealm) {
                     String idd = secCfg.getAdministrativeIdentityDomain();
                     if (!defaultIDDS.contains(idd)) {
                        defaultIDDS.add(secCfg.getAdministrativeIdentityDomain());
                     }
                  }

                  return defaultIDDS.isEmpty() ? emptyIDDS : (String[])defaultIDDS.toArray(emptyIDDS);
               }
            }
         }
      }
   }

   public static boolean isIDDDomain(DomainMBean domain) throws IllegalArgumentException {
      SecurityConfigurationMBean security = domain.getSecurityConfiguration();
      if (security.getAdministrativeIdentityDomain() != null && !security.getAdministrativeIdentityDomain().isEmpty()) {
         return true;
      } else {
         RealmMBean[] var2 = security.getRealms();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            RealmMBean realm = var2[var4];
            AuthenticationProviderMBean[] var6 = realm.getAuthenticationProviders();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               AuthenticationProviderMBean authenticator = var6[var8];
               if (authenticator instanceof IdentityDomainAuthenticatorMBean && ((IdentityDomainAuthenticatorMBean)authenticator).getIdentityDomain() != null && !((IdentityDomainAuthenticatorMBean)authenticator).getIdentityDomain().isEmpty()) {
                  return true;
               }

               if (authenticator instanceof MultiIdentityDomainAuthenticatorMBean && ((MultiIdentityDomainAuthenticatorMBean)authenticator).getIdentityDomains() != null && ((MultiIdentityDomainAuthenticatorMBean)authenticator).getIdentityDomains().length > 0) {
                  return true;
               }

               if (authenticator instanceof AnyIdentityDomainAuthenticatorMBean && ((AnyIdentityDomainAuthenticatorMBean)authenticator).isAnyIdentityDomainEnabled()) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public static boolean isNameCallbackAllowed(AuthenticationProviderMBean atn) throws IllegalArgumentException {
      RealmMBean realm = (RealmMBean)atn.getParentBean();
      SecurityConfigurationMBean secCfg = (SecurityConfigurationMBean)realm.getParentBean();
      if (!secCfg.isIdentityDomainDefaultEnabled()) {
         return false;
      } else if (!realm.isDefaultRealm()) {
         return false;
      } else {
         DomainMBean domain = (DomainMBean)secCfg.getParentBean();
         return false;
      }
   }

   public static RuntimeException wrapRCMCloseException(Exception e) {
      return e instanceof RuntimeException ? (RuntimeException)e : new RuntimeException(e);
   }

   public static String getDefaultMessageDigestAlgorithm(AuthenticationProviderMBean atn) throws IllegalArgumentException {
      String defaultAlgorithm = "SHA-256";
      if (!(atn.getParentBean() instanceof RealmMBean)) {
         return defaultAlgorithm;
      } else {
         RealmMBean realm = (RealmMBean)atn.getParentBean();
         if (!(realm.getParentBean() instanceof SecurityConfigurationMBean)) {
            return defaultAlgorithm;
         } else {
            SecurityConfigurationMBean secCfg = (SecurityConfigurationMBean)realm.getParentBean();
            if (!(secCfg.getParentBean() instanceof DomainMBean)) {
               return defaultAlgorithm;
            } else {
               DomainMBean domain = (DomainMBean)secCfg.getParentBean();
               VersionInfo currVersionInfo = new VersionInfo(domain.getDomainVersion());
               VersionInfo compareVersionInfo = new VersionInfo("12.3.1.0.0");
               return currVersionInfo.earlierThan(compareVersionInfo) ? "SHA-1" : defaultAlgorithm;
            }
         }
      }
   }
}

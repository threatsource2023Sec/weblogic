package weblogic.security.utils;

import java.security.AccessController;
import java.security.PrivilegedAction;
import weblogic.kernel.KernelStatus;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.security.SecurityRuntimeAccess;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.shared.LoggerWrapper;
import weblogic.utils.LocatorUtilities;

public class KeyStoreConfigurationHelper extends BaseKeyStoreConfigurationHelper {
   private static final LoggerWrapper LOGGER = LoggerWrapper.getInstance("SecurityKeyStore");
   static final String KSS_DEMO_IDENTITY_CERTIFICATE_CN_PREFIX = "DemoCertFor_";
   private NetworkAccessPointMBean channel = null;

   public KeyStoreConfigurationHelper(KeyStoreConfiguration config) {
      super(config);
   }

   public KeyStoreConfigurationHelper(KeyStoreConfiguration config, NetworkAccessPointMBean channel) {
      super(config);
      this.channel = channel;
   }

   public String getOutboundPrivateKeyAlias() {
      if (!this.isValid()) {
         return null;
      } else if (this.isDemoIdentity()) {
         return "DemoIdentity";
      } else {
         return this.channel != null ? this.emptyToNull(this.channel.getOutboundPrivateKeyAlias()) : this.emptyToNull(this.config.getOutboundPrivateKeyAlias());
      }
   }

   public char[] getOutboundPrivateKeyPassPhrase() {
      if (!this.isValid()) {
         return null;
      } else {
         String passphrase;
         if (this.isDemoIdentity()) {
            passphrase = "DemoIdentityPassPhrase";
         } else if (this.channel != null) {
            passphrase = this.emptyToNull(this.channel.getOutboundPrivateKeyPassPhrase());
         } else {
            passphrase = this.emptyToNull(this.config.getOutboundPrivateKeyPassPhrase());
         }

         return passphrase != null && passphrase.length() > 0 ? passphrase.toCharArray() : null;
      }
   }

   protected String getChannelPrivateKeyAlias() {
      return this.channel != null ? this.emptyToNull(this.channel.getPrivateKeyAlias()) : null;
   }

   protected String getChannelPrivateKeyPassPhrase() {
      return this.channel != null ? this.emptyToNull(this.channel.getPrivateKeyPassPhrase()) : null;
   }

   protected KeyStoreInfo getChannelIdentityKeyStoreInfo() {
      return null == this.channel ? null : new KeyStoreInfo(this.channel.getCustomIdentityKeyStoreFileName(), this.channel.getCustomIdentityKeyStoreType(), this.channel.getCustomIdentityKeyStorePassPhrase());
   }

   protected final boolean isUseKssForDemo() {
      return isUseKssForDemoOnServer();
   }

   static final boolean isUseKssForDemoOnServer() {
      if (!KernelStatus.isServer()) {
         return false;
      } else if (!SecurityServiceManager.isSecurityServiceInitialized()) {
         return false;
      } else {
         SecurityRuntimeAccess ra = KeyStoreConfigurationHelper.SecurityRuntimeAccessService.getRuntimeAccess();
         if (null == ra) {
            if (LOGGER.isDebugEnabled()) {
               LOGGER.debug("isUseKssForDemoOnServer, RuntimeAccess is null");
            }

            return false;
         } else {
            DomainMBean domainMBean = ra.getDomain();
            if (null == domainMBean) {
               if (LOGGER.isDebugEnabled()) {
                  LOGGER.debug("isUseKssForDemoOnServer, DomainMBean is null");
               }

               return false;
            } else {
               SecurityConfigurationMBean securityConfigMBean = domainMBean.getSecurityConfiguration();
               if (null == securityConfigMBean) {
                  if (LOGGER.isDebugEnabled()) {
                     LOGGER.debug("isUseKssForDemoOnServer, SecurityConfigurationMBean is null");
                  }

                  return false;
               } else {
                  return securityConfigMBean.isUseKSSForDemo();
               }
            }
         }
      }
   }

   static final boolean isKssDemoIdentityCert(String certhostname) {
      if (null == certhostname) {
         if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("isKssDemoIdentityCert given null certhostname.");
         }

         return false;
      } else if (!KernelStatus.isServer()) {
         return false;
      } else {
         SecurityRuntimeAccess ra = KeyStoreConfigurationHelper.SecurityRuntimeAccessService.getRuntimeAccess();
         if (null == ra) {
            if (LOGGER.isDebugEnabled()) {
               LOGGER.debug("isKssDemoIdentityCert, RuntimeAccess is null");
            }

            return false;
         } else {
            DomainMBean domainMBean = ra.getDomain();
            if (null == domainMBean) {
               if (LOGGER.isDebugEnabled()) {
                  LOGGER.debug("isKssDemoIdentityCert, DomainMBean is null");
               }

               return false;
            } else {
               String domainName = domainMBean.getName();
               if (null != domainName && !"".equals(domainName)) {
                  String expectedKssDemoCN = "DemoCertFor_" + domainName;
                  return expectedKssDemoCN.equalsIgnoreCase(certhostname);
               } else {
                  if (LOGGER.isDebugEnabled()) {
                     LOGGER.debug("isKssDemoIdentityCert, Null or empty domain name.");
                  }

                  return false;
               }
            }
         }
      }
   }

   private static final class SecurityRuntimeAccessService {
      private static volatile SecurityRuntimeAccess runtimeAccess = null;

      private static SecurityRuntimeAccess getRuntimeAccess() {
         if (null == runtimeAccess) {
            try {
               SecurityRuntimeAccess tempRuntimeAccess = (SecurityRuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
                  public SecurityRuntimeAccess run() {
                     return (SecurityRuntimeAccess)LocatorUtilities.getService(SecurityRuntimeAccess.class);
                  }
               });
               runtimeAccess = tempRuntimeAccess;
            } catch (Exception var1) {
               if (KeyStoreConfigurationHelper.LOGGER.isDebugEnabled()) {
                  KeyStoreConfigurationHelper.LOGGER.debug("Unable to obtain SecurityRuntimeAccess, which may be due to a missing config.xml file. " + var1.getClass().getName() + " occurred while getting " + SecurityRuntimeAccess.class.getName() + ", message: " + var1.getMessage(), var1);
               }
            }
         }

         return runtimeAccess;
      }
   }
}

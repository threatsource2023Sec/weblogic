package weblogic.management.mbeans.custom;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import javax.management.InvalidAttributeValueException;
import javax.management.JMException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.ManagementLogger;
import weblogic.management.configuration.ConfigurationError;
import weblogic.management.configuration.ConfigurationValidator;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.management.security.RealmMBean;
import weblogic.security.Salt;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;

public final class SecurityConfiguration extends ConfigurationMBeanCustomizer {
   private boolean _initialized = false;
   private byte[] _salt;
   private byte[] _encryptedSecretKey;
   private static ClearOrEncryptedService encryptionService;
   private static final boolean DEBUG = false;

   private void debug(String msg) {
   }

   private static ClearOrEncryptedService getEncryptionService() {
      if (encryptionService == null) {
         encryptionService = new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService());
      }

      return encryptionService;
   }

   public SecurityConfiguration(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void _postCreate() {
      this._initialized = true;
   }

   private SecurityConfigurationMBean getMyMBean() {
      return (SecurityConfigurationMBean)this.getMbean();
   }

   public RealmMBean createRealm(String realmName) throws JMException {
      ConfigurationValidator.validateName(realmName);
      SecurityConfigurationMBean smbean = this.getMyMBean();
      RealmMBean realmMBean = smbean.lookupRealm(realmName);
      if (realmMBean != null) {
         throw new IllegalArgumentException("The realm named '" + realmName + "' already exists!");
      } else {
         try {
            Class realmMBeanClass = Class.forName("weblogic.management.security.RealmMBeanImpl");
            Constructor realmMBeanConstructor = realmMBeanClass.getConstructor(DescriptorBean.class, Integer.TYPE);
            realmMBean = (RealmMBean)realmMBeanConstructor.newInstance(smbean, new Integer(-1));
            realmMBean.setName(realmName);
            Method addRealmMethod = smbean.getClass().getMethod("addRealm", RealmMBean.class);
            addRealmMethod.invoke(smbean, realmMBean);
            return realmMBean;
         } catch (Exception var7) {
            if (var7 instanceof RuntimeException) {
               throw (RuntimeException)var7;
            } else if (var7 instanceof JMException) {
               throw (JMException)var7;
            } else {
               throw new UndeclaredThrowableException(var7);
            }
         }
      }
   }

   public RealmMBean[] findRealms() {
      return this.getMyMBean().getRealms();
   }

   public RealmMBean findDefaultRealm() {
      return this.getMyMBean().getDefaultRealm();
   }

   public RealmMBean findRealm(String realmName) {
      return this.getMyMBean().lookupRealm(realmName);
   }

   public RealmMBean getDefaultRealmInternal() {
      return this.getMyMBean().getDefaultRealm();
   }

   public void setDefaultRealmInternal(RealmMBean r) {
      try {
         this.getMyMBean().setDefaultRealm(r);
      } catch (InvalidAttributeValueException var3) {
         throw new IllegalArgumentException(var3.toString());
      }
   }

   public synchronized byte[] getSalt() {
      if (this._salt == null) {
         try {
            byte[] temp = SerializedSystemIni.getSalt();
            if (temp == null || temp.length < 1) {
               throw new ConfigurationError("Empty salt");
            }

            this._salt = temp;
         } catch (Exception var2) {
            ManagementLogger.logExceptionInCustomizer(var2);
         }
      }

      return this._salt;
   }

   public synchronized byte[] getEncryptedSecretKey() {
      if (this._encryptedSecretKey == null) {
         try {
            if (!this.isAdmin() && !(this.getMbean() instanceof DescriptorBean)) {
               throw new ConfigurationError("EncryptedSecretKey null in config");
            }

            byte[] temp = SerializedSystemIni.getEncryptedSecretKey();
            if (temp == null || temp.length < 1) {
               throw new ConfigurationError("Empty encryptedSecretKey");
            }

            this._encryptedSecretKey = temp;
         } catch (Exception var2) {
            ManagementLogger.logExceptionInCustomizer(var2);
         }
      }

      return this._encryptedSecretKey;
   }

   public byte[] getEncryptedAESSecretKey() {
      byte[] _encryptedAESSecretKey = null;

      try {
         _encryptedAESSecretKey = SerializedSystemIni.getEncryptedAESSecretKey();
      } catch (Exception var3) {
         ManagementLogger.logExceptionInCustomizer(var3);
      }

      return _encryptedAESSecretKey;
   }

   public byte[] generateCredential() {
      byte[] unencryptedCredential = Salt.getRandomBytes(32);

      byte[] var2;
      try {
         var2 = getEncryptionService().encryptBytes(unencryptedCredential);
      } finally {
         Arrays.fill(unencryptedCredential, (byte)0);
      }

      return var2;
   }

   public void setCredentialGenerated(boolean credentialIsGenerated) {
      if (this._initialized && credentialIsGenerated) {
         try {
            this.getMyMBean().setCredentialEncrypted(this.generateCredential());
         } catch (InvalidAttributeValueException var3) {
            throw new RuntimeException(var3);
         }
      }

   }
}

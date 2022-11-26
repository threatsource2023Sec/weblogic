package weblogic.security.internal;

import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionService;
import weblogic.security.utils.AdminAccount;
import weblogic.utils.LocatorUtilities;

public final class InstallProxy {
   public byte[] getSalt(String targetFile) throws Throwable {
      return SerializedSystemIni.getSalt(targetFile);
   }

   public void generateLDIF(String user, String pass, String outputDir, String templateName) throws Throwable {
      AdminAccount.setupAdminAccount(user, pass, outputDir, templateName);
   }

   public void generateKeyStore() throws Exception {
      KeyStoreInstallationService installer = (KeyStoreInstallationService)LocatorUtilities.getService(KeyStoreInstallationService.class);
      installer.initDefaultKeyStore();
   }

   public String encrypt(String valueToBeEncrypted, String domain) throws Throwable {
      EncryptionService es = SerializedSystemIni.getEncryptionService(domain);
      ClearOrEncryptedService encrypter = new ClearOrEncryptedService(es);
      Object encryptedValue = encrypter.encrypt(valueToBeEncrypted);
      return (String)encryptedValue;
   }
}

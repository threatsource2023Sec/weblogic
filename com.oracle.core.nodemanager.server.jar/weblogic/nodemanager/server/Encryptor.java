package weblogic.nodemanager.server;

import com.bea.security.utils.random.SecureRandomData;
import java.io.File;
import java.io.IOException;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.common.ConfigException;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionServiceException;
import weblogic.security.internal.encryption.EncryptionServiceFactory;
import weblogic.security.internal.encryption.JSafeEncryptionServiceFactory;
import weblogic.utils.Hex;

class Encryptor {
   private byte[] salt;
   private byte[] key;
   private ClearOrEncryptedService ces;
   static final String ESP = "0x1f48730ab4957122fccb2856671df094bcc294af";
   static final String OESP = "0x194ce8ab97302f33a77c82de564091f1ac4873be";
   NMServerConfig conf;
   private boolean useOESP = false;
   static final String NAME_HASH_KEY_PROP = "nameHashKey";
   static final String ID_HASH_KEY_PROP = "idHashKey";
   static final String OLD_NAME_HASH_KEY_PROP = "nameHashkey";
   static final String OLD_ID_HASH_KEY_PROP = "idHashkey";
   public static final String NM_DATA_PROPERTIES = "nm_data.properties";
   public static final String NM_DATA_PROPERTIES_1 = "SerializedNodeManagerIni.dat";
   public static final String NM_DATA_PROPERTIES_2 = "NodeManagerProperties";
   public static final String KEY_PASSWORD_PROP = "keyPassword";
   public static final String DEFAULT_KEY_PASSWORD = "password";

   private File getEDataFile(String nmHome) {
      File file = new File(nmHome, "nm_data.properties");
      if (!file.exists()) {
         file = new File(nmHome, "SerializedNodeManagerIni.dat");
         if (!file.exists()) {
            file = new File(nmHome, "NodeManagerProperties");
            if (!file.exists()) {
               file = null;
            } else {
               this.useOESP = true;
            }
         }
      }

      return file;
   }

   Encryptor(String nmHome) throws ConfigException, IOException {
      this.ces = null;
      File file = this.getEDataFile(nmHome);
      if (file != null) {
         this.loadProperties(file);
         EncryptionServiceFactory esf = new JSafeEncryptionServiceFactory();
         this.ces = new ClearOrEncryptedService(esf.getEncryptionService(this.salt, "0x1f48730ab4957122fccb2856671df094bcc294af", this.key));
      } else {
         throw new IOException(NodeManagerTextTextFormatter.getInstance().getDataFileNotFound(nmHome));
      }
   }

   Encryptor(NMServerConfig conf) throws ConfigException, IOException {
      EncryptionServiceFactory esf = new JSafeEncryptionServiceFactory();
      this.conf = conf;
      String nmHome = conf.getNMHome();
      File file = this.getEDataFile(nmHome);
      if (file != null) {
         this.loadProperties(file);
      } else {
         this.createProperties(esf, new File(nmHome, "nm_data.properties"));
      }

      this.initService(esf);
   }

   private void loadProperties(File file) throws ConfigException, IOException {
      NMProperties props = new NMProperties();
      props.load(file);
      String nameHashKey = props.getProperty("nameHashKey");
      if (nameHashKey == null) {
         nameHashKey = props.getProperty("nameHashkey");
      }

      String idHashKey = props.getProperty("idHashKey");
      if (idHashKey == null) {
         idHashKey = props.getProperty("idHashkey");
      }

      if (nameHashKey != null && idHashKey != null) {
         byte[] nameHashKeyBytes = nameHashKey.getBytes();
         byte[] idHashKeyBytes = idHashKey.getBytes();
         this.salt = Hex.fromHexString(nameHashKeyBytes, nameHashKeyBytes.length);
         this.key = Hex.fromHexString(idHashKeyBytes, idHashKeyBytes.length);
      } else {
         throw new ConfigException(NodeManagerTextTextFormatter.getInstance().getInvalidDataFile(file.toString()));
      }
   }

   private void createProperties(EncryptionServiceFactory esf, File file) throws IOException {
      NMProperties props = new NMProperties();
      this.salt = SecureRandomData.getInstance().getRandomBytes(4);
      this.key = esf.createEncryptedSecretKey(this.salt, "0x1f48730ab4957122fccb2856671df094bcc294af");
      props.setProperty("nameHashKey", Hex.asHex(this.salt, this.salt.length));
      props.setProperty("idHashKey", Hex.asHex(this.key, this.key.length));
      props.save(file, (String)null);
   }

   private void initService(EncryptionServiceFactory esf) {
      String opwd = null;

      try {
         this.ces = new ClearOrEncryptedService(esf.getEncryptionService(this.salt, this.useOESP ? "0x194ce8ab97302f33a77c82de564091f1ac4873be" : "0x1f48730ab4957122fccb2856671df094bcc294af", this.key));
      } catch (EncryptionServiceException var6) {
         if (!this.useOESP || this.conf == null) {
            throw (InternalError)(new InternalError(NodeManagerTextTextFormatter.getInstance().getEncryptionServiceFailure())).initCause(var6);
         }

         opwd = this.conf.getConfigProperties().getProperty("keyPassword");
         if (opwd == null || opwd.equals("")) {
            opwd = "password";
         }

         try {
            this.ces = new ClearOrEncryptedService(esf.getEncryptionService(this.salt, opwd, this.key));
         } catch (EncryptionServiceException var5) {
            throw (InternalError)(new InternalError(NodeManagerTextTextFormatter.getInstance().getEncryptionServiceFailure())).initCause(var5);
         }
      }

   }

   String encrypt(String value) {
      if (value != null && !this.ces.isEncrypted(value)) {
         value = this.ces.encrypt(value);
      }

      return value;
   }

   String decrypt(String value) {
      if (value != null && this.ces.isEncrypted(value)) {
         value = this.ces.decrypt(value);
      }

      return value;
   }

   ClearOrEncryptedService getCES() {
      return this.ces;
   }
}

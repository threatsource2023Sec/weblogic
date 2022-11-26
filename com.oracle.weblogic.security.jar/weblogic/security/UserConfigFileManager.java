package weblogic.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import weblogic.security.internal.ServerAuthenticate;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionService;
import weblogic.security.internal.encryption.JSafeEncryptionServiceFactory;

public class UserConfigFileManager implements Map {
   static final String DEFAULT_KEY_FILE_NAME = "-WebLogicKey.properties";
   static final String DEFAULT_CONFIG_FILE_NAME = "-WebLogicConfig.properties";
   static final String DEFAULT_FILE_HOME_PROP = "user.home";
   static final String DEFAULT_FILE_USER_PROP = "user.name";
   static final String DEFAULT_FILE_USER = "user";
   static final String DEFAULT_FILE_HOME = ".";
   static final String DEFAULT_USERNAME_PROP = ".username";
   static final String DEFAULT_PASSWORD_PROP = ".password";
   static final String CONFIG_FILE_HEADER = "WebLogic User Configuration File";
   static final int VERSION = 2;
   private String configFileName = null;
   private FileInputStream configIn = null;
   private Properties configProperties = null;
   private String propName = null;
   private String keyFileName = null;
   private boolean debugUserConfig = false;
   private static ClearOrEncryptedService encrypter = null;
   private static String myvalue = getDefaultMyValue();

   public UserConfigFileManager() {
      this.configFileName = null;
      this.keyFileName = null;
   }

   public UserConfigFileManager(String configFile, String secretFile) {
      this.configFileName = configFile;
      this.keyFileName = secretFile;
   }

   public void setDebug(boolean x) {
      this.debugUserConfig = x;
   }

   public boolean getDebug() {
      return this.debugUserConfig;
   }

   public static UsernameAndPassword getUsernameAndPassword(String propertyName) {
      UserConfigFileManager cfm = new UserConfigFileManager();
      cfm.debug("DBG: UserConfigFileManager.getUsernameAndPassword(-" + propertyName + "-)");
      cfm.setPropName(propertyName);

      try {
         cfm.initEncryptionRead(false);
      } catch (KeyException var3) {
         return null;
      }

      return cfm.retrieveUandPValues();
   }

   public static void setUsernameAndPassword(UsernameAndPassword values, String propertyName) {
      UserConfigFileManager cfm = new UserConfigFileManager();
      cfm.debug("DBG: UserConfigFileManager.setUsernameAndPassword(UsernameAndPassword, -" + propertyName + "-)");
      cfm.setPropName(propertyName);

      try {
         cfm.initEncryption(true);
      } catch (KeyException var4) {
         return;
      }

      values.setEncryption(encrypter);
      cfm.putUandPValues(values);
   }

   public static UsernameAndPassword getUsernameAndPassword(String configFile, String secretFile, String propertyName) {
      UserConfigFileManager cfm = new UserConfigFileManager(configFile, secretFile);
      cfm.debug("DBG: UserConfigFileManager.getUsernameAndPassword(" + configFile + "," + secretFile + "," + propertyName + ")");

      try {
         cfm.initEncryptionRead(false);
      } catch (KeyException var5) {
         return null;
      }

      cfm.setPropName(propertyName);
      return cfm.retrieveUandPValues();
   }

   public static void setUsernameAndPassword(UsernameAndPassword values, String configFile, String secretFile, String propertyName) {
      UserConfigFileManager cfm = new UserConfigFileManager(configFile, secretFile);
      cfm.debug("DBG: UserConfigFileManager.setUsernameAndPassword(UsernameAndPassword," + configFile + "," + secretFile + "," + propertyName + ")");

      try {
         cfm.initEncryption(true);
      } catch (KeyException var6) {
         return;
      }

      values.setEncryption(encrypter);
      cfm.setPropName(propertyName);
      cfm.putUandPValues(values);
   }

   public static ClearOrEncryptedService getEncryptedService(String secretFile, String passKey) {
      UserConfigFileManager cfm = new UserConfigFileManager("-WebLogicConfig.properties", secretFile);
      if (passKey != null && passKey.length() > 0) {
         setMyvalue(passKey);
      } else {
         setMyvalue(getDefaultMyValue());
      }

      cfm.debug("DBG: UserConfigFileManager.getEncryptedService(" + secretFile + ",)");

      try {
         cfm.initEncryption(true);
      } catch (KeyException var4) {
         return null;
      }

      return encrypter;
   }

   public static String getDefaultConfigFileName() {
      UserConfigFileManager cfm = new UserConfigFileManager();
      cfm.debug("DBG: UserConfigFileManager.getDefaultConfigFileName()");
      cfm.resolveConfigFileName();
      return cfm.configFileName;
   }

   public static String getDefaultKeyFileName() {
      UserConfigFileManager cfm = new UserConfigFileManager();
      cfm.debug("DBG: UserConfigFileManager.getDefaultKeyFileName()");
      cfm.resolveKeyFileName();
      return cfm.keyFileName;
   }

   public void load() throws FileNotFoundException, IOException {
      this.debug("DBG: UserConfigFileManager.load()");
      this.configProperties = new Properties();
      this.configIn = new FileInputStream(this.configFileName);
      this.configProperties.load(this.configIn);
      this.configIn.close();
   }

   public void refresh() throws IOException {
      this.debug("DBG: UserConfigFileManager.refresh()");
      throw new UnsupportedOperationException();
   }

   public void store() throws FileNotFoundException, IOException {
      this.debug("DBG: UserConfigFileManager.store()");
      FileOutputStream configOut = new FileOutputStream(this.configFileName);

      try {
         this.configProperties.store(configOut, "WebLogic User Configuration File; " + String.valueOf(2));
         configOut.close();
      } catch (ClassCastException var3) {
         throw new IOException("Could not convert property to correct type while storing configuration");
      }
   }

   public void clear() {
      this.debug("DBG: UserConfigFileManager.clear()");
      this.configProperties.clear();
   }

   public boolean containsKey(Object key) {
      this.debug("DBG: UserConfigFileManager.containsKey(Object)");
      return this.configProperties.containsKey(key);
   }

   public boolean containsValue(Object value) {
      this.debug("DBG: UserConfigFileManager.containsValue(Object)");
      return this.configProperties.containsValue(value);
   }

   public Set entrySet() {
      this.debug("DBG: UserConfigFileManager.entrySet()");
      throw new UnsupportedOperationException();
   }

   public boolean equals(Object o) {
      this.debug("DBG: UserConfigFileManager.equals(Object)");
      throw new UnsupportedOperationException();
   }

   public Object get(Object key) {
      this.debug("DBG: UserConfigFileManager.get(Object)");
      throw new UnsupportedOperationException();
   }

   public int hashCode() {
      this.debug("DBG: UserConfigFileManager.hashCode()");
      throw new UnsupportedOperationException();
   }

   public boolean isEmpty() {
      this.debug("DBG: UserConfigFileManager.isEmpty()");
      return this.configProperties.isEmpty();
   }

   public Set keySet() {
      this.debug("DBG: UserConfigFileManager.keySet()");
      throw new UnsupportedOperationException();
   }

   public Object put(Object key, Object value) {
      this.debug("DBG: UserConfigFileManager.put(Object, Object)");
      throw new UnsupportedOperationException();
   }

   public void putAll(Map t) {
      this.debug("DBG: UserConfigFileManager.putAll(Map)");
      throw new UnsupportedOperationException();
   }

   public Object remove(Object key) {
      this.debug("DBG: UserConfigFileManager.remove(Object)");
      throw new UnsupportedOperationException();
   }

   public int size() {
      this.debug("DBG: UserConfigFileManager.size()");
      throw new UnsupportedOperationException();
   }

   public Collection values() {
      this.debug("DBG: UserConfigFileManager.values()");
      throw new UnsupportedOperationException();
   }

   private void resolveConfigFileName() {
      this.debug("DBG: UserConfigFileManager.resolveConfigFile()");
      if (this.configFileName == null) {
         String fileHome = System.getProperty("user.home");
         if (fileHome == null) {
            fileHome = ".";
         }

         String fileUser = System.getProperty("user.name");
         if (fileUser == null) {
            fileUser = "user";
         }

         this.configFileName = new String(fileHome + "/" + fileUser + "-WebLogicConfig.properties");
         this.debug("DBG: default config file name: " + this.configFileName);
      } else {
         this.debug("DBG: config file name: " + this.configFileName);
      }

   }

   private void resolveKeyFileName() {
      this.debug("DBG: UserConfigFileManager.resolveKeyFileName()");
      if (this.keyFileName == null) {
         String fileHome = System.getProperty("user.home");
         if (fileHome == null) {
            fileHome = ".";
         }

         String fileUser = System.getProperty("user.name");
         if (fileUser == null) {
            fileUser = "user";
         }

         this.keyFileName = new String(fileHome + "/" + fileUser + "-WebLogicKey.properties");
         this.debug("DBG: default key file name: " + this.keyFileName);
      } else {
         this.debug("DBG: key file name: " + this.keyFileName);
      }

   }

   private void setPropName(String propertyName) {
      if (propertyName == null) {
         propertyName = "null";
      }

      this.debug("DBG: UserConfigFileManager.setPropName(-" + propertyName + "-)");
      this.propName = new String(propertyName);
   }

   private String getPropName() {
      this.debug("DBG: UserConfigFilemanager.getPropName(); returning: " + this.propName);
      return this.propName;
   }

   private void putUandPValues(UsernameAndPassword values) {
      this.debug("DBG: UserConfigFileManager.putUandPValues()");
      this.resolveConfigFileName();
      if (encrypter == null) {
         this.debug("DBG: encrypter is null; should already be initialized");
      } else {
         try {
            this.load();
         } catch (FileNotFoundException var4) {
            this.debug("DBG: FileNotFoundException loading config file, may not exist; NOT AN ERROR");
         } catch (IOException var5) {
            this.debug("DBG: IOException loading config file, may not exist; NOT AN ERROR");
         }

         try {
            if (this.ensureEncrypted()) {
               this.store();
            }
         } catch (FileNotFoundException var6) {
            System.err.println("Unable to create or write the user configuration file. Check that the filename is correctly specified.");
            return;
         } catch (IOException var7) {
            System.err.println("Unable to write the user configuration file.  Check that the directory is writable.");
            return;
         }

         this.setUandP(values);

         try {
            this.store();
         } catch (IOException var3) {
            System.err.println("Unable to create or write the user configuration file. Check that the filename is correctly specified and writable.");
         }

      }
   }

   private UsernameAndPassword retrieveUandPValues() {
      this.debug("DBG: UserConfigFileManager.retrieveUandPValues()");
      this.resolveConfigFileName();
      if (encrypter == null) {
         this.debug("DBG: encrypter is null; should already be initialized");
         return null;
      } else {
         try {
            this.load();
         } catch (FileNotFoundException var2) {
            System.err.println("User configuration file was not found.  Check location of the file.");
            return null;
         } catch (IOException var3) {
            System.err.println("IO error loading user configuration file.  Check location and protection of file.");
            return null;
         }

         try {
            if (this.ensureEncrypted()) {
               this.store();
            }
         } catch (FileNotFoundException var4) {
            System.err.println("User configuration file was not found. Check location of the file.");
            return null;
         } catch (IOException var5) {
            System.err.println("IO error storing user configuration.  Check location and protection of user's configuration file.");
            return null;
         }

         return this.getUandP();
      }
   }

   private UsernameAndPassword getUandP() {
      this.debug("DBG: UserConfigFileManager.getUandP()");
      UsernameAndPassword retValues = new UsernameAndPassword();
      this.debug("DBG: UserConfigFileManager.getUandP(); setting encrypter on UsernameAndPassword");
      retValues.setEncryption(encrypter);
      String p;
      if (this.containsKey(this.getPropName() + ".username")) {
         p = this.configProperties.getProperty(this.getPropName() + ".username");
         retValues.setUsername(encrypter.decrypt(p));
      }

      if (this.containsKey(this.getPropName() + ".password")) {
         p = this.configProperties.getProperty(this.getPropName() + ".password");
         retValues.setPassword(encrypter.decrypt(p).toCharArray());
      }

      return retValues;
   }

   private static final void clear(byte[] x) {
      Arrays.fill(x, (byte)0);
   }

   private void setUandP(UsernameAndPassword values) {
      if (values.getUsername() != null) {
         this.configProperties.setProperty(this.getPropName() + ".username", encrypter.encrypt(values.getUsername()));
      }

      this.configProperties.setProperty(this.getPropName() + ".password", encrypter.encrypt(new String(values.getPassword())));
   }

   private boolean verifyKeyCreate() {
      String confirmed = System.getProperty("weblogic.management.confirmKeyfileCreation", "false");
      SecurityMessagesTextFormatter fmt = new SecurityMessagesTextFormatter();
      String negative;
      if (confirmed != null && (confirmed == null || confirmed.equalsIgnoreCase("true"))) {
         if (confirmed.equalsIgnoreCase("true")) {
            negative = fmt.getUserKeyConfigCreateNoPrompt();
            System.out.println(negative);
            return true;
         }
      } else {
         try {
            negative = fmt.getUserKeyConfigCreateNegative();
            String affirmative = fmt.getUserKeyConfigCreateAffirmative();
            String yesorno = ServerAuthenticate.promptValue(fmt.getUserKeyConfigCreatePrompt(affirmative, negative), true);
            if (yesorno != null && yesorno.equalsIgnoreCase(affirmative)) {
               return true;
            }

            if (yesorno != null && yesorno.equalsIgnoreCase(negative)) {
               return false;
            }

            yesorno = ServerAuthenticate.promptValue(fmt.getUserKeyConfigCreateConfig(affirmative, negative), true);
            if (yesorno != null && yesorno.equalsIgnoreCase(affirmative)) {
               return true;
            }

            if (yesorno != null && yesorno.equalsIgnoreCase(negative)) {
               return false;
            }

            System.out.println(fmt.getUserKeyConfigCreateFailure());
            return false;
         } catch (Exception var6) {
            System.err.println("Error: Failed To Get Response from Standard Input");
         }
      }

      return false;
   }

   private boolean ensureEncrypted() {
      this.debug("DBG: UserConfigFileManager.ensureEncrypted()");
      boolean mustUpdate = false;
      if (this.configProperties.isEmpty()) {
         return mustUpdate;
      } else {
         Enumeration e = this.configProperties.propertyNames();

         while(e.hasMoreElements()) {
            String name = (String)e.nextElement();
            String val = this.configProperties.getProperty(name);
            this.debug("DBG: UserConfigFileManager.ensureEncrypted(); checking: " + name);
            if (!encrypter.isEncrypted(val)) {
               mustUpdate = true;
               this.debug("DBG: UserConfigFileManager.ensureEncrypted(); encrypting: " + val);
               this.configProperties.setProperty(name, encrypter.encrypt(val));
            }
         }

         return mustUpdate;
      }
   }

   private static String getDefaultMyValue() {
      return "0xfe593a5c23b88c112b3c674e33ea4c7901e26a7c";
   }

   private static void setMyvalue(String myvalue) {
      UserConfigFileManager.myvalue = myvalue;
   }

   private void initEncryptionRead(boolean outputWarning) throws KeyException {
      this.debug("DBG: UserConfigFileManager.initEncryptionRead(boolean " + outputWarning + ")");
      this.resolveKeyFileName();
      File keyFile = new File(this.keyFileName);
      if (keyFile.exists()) {
         if (outputWarning) {
            SecurityMessagesTextFormatter fmt = new SecurityMessagesTextFormatter();
            System.out.println(fmt.getUsingExistingKeyFile());
         }

         try {
            FileInputStream keyIn = new FileInputStream(keyFile);
            int len = keyIn.read();
            byte[] salt = new byte[len];
            keyIn.read(salt);
            int v = keyIn.read();
            if (v > 2) {
               System.err.println("Version mismatch between key and supported version; will try to continue");
            }

            len = keyIn.read();
            byte[] encryptedKey = new byte[len];
            keyIn.read(encryptedKey);
            byte[] encryptedAESKey = null;
            if (v >= 2) {
               len = keyIn.read();
               encryptedAESKey = new byte[len];
               keyIn.read(encryptedAESKey);
            }

            keyIn.close();
            JSafeEncryptionServiceFactory factory = new JSafeEncryptionServiceFactory();
            EncryptionService es = factory.getEncryptionService(salt, myvalue, encryptedKey, encryptedAESKey);
            encrypter = new ClearOrEncryptedService(es);
         } catch (FileNotFoundException var11) {
            System.err.println("Error: Secret key file was not found. Check the location of the key file.");
         } catch (IOException var12) {
            System.err.println("Error: Not able to read secret key file. Check the location and access priviledges of key file.");
         }
      } else {
         throw new KeyException();
      }
   }

   private void initEncryption(boolean outputWarning) throws KeyException {
      this.debug("DBG: UserConfigFileManager.initEncryption(boolean " + outputWarning + ")");
      boolean createIt = false;

      try {
         this.initEncryptionRead(outputWarning);
      } catch (KeyException var12) {
         createIt = true;
      }

      if (createIt) {
         JSafeEncryptionServiceFactory factory = new JSafeEncryptionServiceFactory();
         this.resolveKeyFileName();

         try {
            if (this.verifyKeyCreate()) {
               this.debug("DBG: key creation verified");
               File keyFile = new File(this.keyFileName);
               if (keyFile.exists()) {
                  keyFile.delete();
               }

               keyFile.createNewFile();
               FileOutputStream outFile = new FileOutputStream(keyFile);
               byte[] salt = Salt.getRandomBytes(4);
               byte[] encryptedKey = factory.createEncryptedSecretKey(salt, myvalue);
               byte[] encryptedAESKey = factory.createAESEncryptedSecretKey(salt, myvalue);
               outFile.write(salt.length);
               outFile.write(salt);
               outFile.write(2);
               outFile.write(encryptedKey.length);
               outFile.write(encryptedKey);
               outFile.write(encryptedAESKey.length);
               outFile.write(encryptedAESKey);
               outFile.flush();
               outFile.close();
               EncryptionService es = factory.getEncryptionService(salt, myvalue, encryptedKey, encryptedAESKey);
               encrypter = new ClearOrEncryptedService(es);
            } else {
               throw new KeyException("Secured key storage aborted");
            }
         } catch (FileNotFoundException var10) {
            System.err.println("Error: Not able to write secret key file.  Check the specified location for the file.");
         } catch (IOException var11) {
            System.err.println("Error: Not able to write secret key file.  Check the location and priviledges of the specified file location.");
         }
      }
   }

   void debug(String msg) {
      if (this.getDebug()) {
         System.out.println(msg);
      }

   }
}

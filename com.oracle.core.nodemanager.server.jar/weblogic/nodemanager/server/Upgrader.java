package weblogic.nodemanager.server;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.common.ConfigException;
import weblogic.security.Salt;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionServiceException;
import weblogic.security.internal.encryption.EncryptionServiceFactory;
import weblogic.security.internal.encryption.JSafeEncryptionServiceFactory;
import weblogic.utils.Hex;

public class Upgrader {
   private NMServerConfig conf;
   private static Logger nmLog = Logger.getLogger("weblogic.nodemanager");
   byte[] salt;
   byte[] key;
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();

   public Upgrader(NMServerConfig conf) {
      this.conf = conf;
   }

   public static boolean upgrade(NMServerConfig conf) throws ConfigException, IOException {
      return (new Upgrader(conf)).upgrade();
   }

   public boolean upgrade() throws ConfigException, IOException {
      this.upgradeDataProperties();
      Encryptor enc = null;
      File nmDataFile = new File(this.conf.getNMHome(), "nm_data.properties");
      if (nmDataFile.exists()) {
         enc = new Encryptor(this.conf);
      }

      ClearOrEncryptedService ces = new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService());
      boolean changed = this.upgradeConfigProperties(enc, ces);
      if (nmDataFile.exists()) {
         nmDataFile.delete();
      }

      return changed;
   }

   private void loadDataProperties(File file) throws IOException {
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
         throw new IOException(nmText.getInvalidDataFile(file.toString()));
      }
   }

   public void upgradeDataProperties() {
      File dir = new File(this.conf.getNMHome());
      File dst = new File(dir, "nm_data.properties");
      if (!dst.exists()) {
         File src = new File(dir, "SerializedNodeManagerIni.dat");
         if (!src.exists()) {
            src = new File(dir, "NodeManagerProperties");
            if (!src.exists()) {
               return;
            }

            try {
               this.loadDataProperties(src);
               byte[] oldSalt = this.salt;
               byte[] oldKey = this.key;
               EncryptionServiceFactory esf = new JSafeEncryptionServiceFactory();
               byte[] newSalt = Salt.getRandomBytes(4);

               byte[] newKey;
               try {
                  newKey = esf.reEncryptEncryptedSecretKey(oldKey, oldSalt, newSalt, "0x194ce8ab97302f33a77c82de564091f1ac4873be", "0x1f48730ab4957122fccb2856671df094bcc294af");
               } catch (EncryptionServiceException var11) {
                  String opwd = "password";
                  if (this.conf != null) {
                     opwd = this.conf.getConfigProperties().getProperty("keyPassword");
                     if (opwd == null || opwd.equals("")) {
                        opwd = "password";
                     }
                  }

                  newKey = esf.reEncryptEncryptedSecretKey(oldKey, oldSalt, newSalt, opwd, "0x1f48730ab4957122fccb2856671df094bcc294af");
               }

               NMProperties props = new NMProperties();
               props.setProperty("nameHashKey", Hex.asHex(newSalt, newSalt.length));
               props.setProperty("idHashKey", Hex.asHex(newKey, newKey.length));
               props.save(dst, (String)null);
               src.delete();
               log(Level.INFO, nmText.getNMDataPropsMigrated(src.toString(), dst.toString()));
            } catch (Throwable var12) {
               log(Level.INFO, nmText.getNMDataPropsMigrateError(src.toString(), dst.toString()), var12);
            }
         } else {
            log(Level.INFO, nmText.getNMDataPropsRenamed(src.toString(), dst.toString()));
            if (!src.renameTo(dst)) {
               log(Level.WARNING, nmText.getNMDataPropsRenameError(src.toString()));
            }
         }

      }
   }

   private boolean upgradeConfigProperties(Encryptor enc, ClearOrEncryptedService ces) throws ConfigException, IOException {
      File dir = new File(this.conf.getNMHome());
      NMProperties props = new NMProperties();
      File file = new File(dir, "nodemanager.properties");
      if (!file.exists()) {
         return false;
      } else {
         props.loadWithComments(file);
         boolean changed = NMServerConfig.checkUpgrade(props, true);
         changed = SSLConfig.checkUpgrade(props, enc, true, ces) || changed;
         if (changed) {
            log(Level.INFO, nmText.getSavingUpgradedProps(file.toString()));
            props.saveWithComments(file);
         }

         return changed;
      }
   }

   public static void log(Level level, String msg, Throwable thrown) {
      LogRecord lr = new LogRecord(level, msg);
      lr.setParameters(new String[]{"Upgrade"});
      if (thrown != null) {
         lr.setThrown(thrown);
      }

      nmLog.log(lr);
   }

   public static void log(Level level, String msg) {
      log(level, msg, (Throwable)null);
   }

   public static void upgrade(File dir, boolean verbose) throws ConfigException, IOException {
      dir = dir.getAbsoluteFile();
      if (dir.exists() && dir.isDirectory()) {
         NMProperties props = new NMProperties();
         File propsFile = new File(dir, "nodemanager.properties");
         if (propsFile.exists()) {
            props.load(propsFile);
         }

         props.setProperty("NodeManagerHome", dir.getPath());
         if (verbose) {
            props.setProperty("LogLevel", "ALL");
            props.setProperty("LogToStderr", "true");
         }

         NMServerConfig conf = new NMServerConfig(props);
         log(Level.INFO, nmText.getUpgradeStarted(dir.toString()));
         (new Upgrader(conf)).upgrade();
      } else {
         throw new IOException(nmText.getNMDirError(dir.toString()));
      }
   }

   public static void main(String[] args) throws Throwable {
      boolean verbose = false;
      int count = 0;
      File dir = new File(".");
      if ("-v".equals(args[0]) || "-verbose".equals(args[0])) {
         verbose = true;
         ++count;
      }

      if (args.length > count) {
         dir = new File(args[0]);
         ++count;
      }

      if (args.length != count) {
         throw new IllegalArgumentException("Usage: java weblogic.nodemanager.server.Upgrader [-v] [dir]");
      } else {
         upgrade(dir, verbose);
      }
   }
}

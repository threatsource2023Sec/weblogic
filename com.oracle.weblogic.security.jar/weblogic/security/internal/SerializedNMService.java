package weblogic.security.internal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import weblogic.security.SecurityLogger;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionService;
import weblogic.security.internal.encryption.JSafeEncryptionServiceFactory;
import weblogic.utils.Hex;

public final class SerializedNMService {
   private static final String randomString = "0x1f48730ab4957122fccb2856671df094bcc294af";
   private static final boolean DEBUG = false;

   private static void debug(String msg) {
   }

   public static ClearOrEncryptedService getEncryptionService(String propertiesFile) {
      Properties props = new Properties();

      try {
         FileInputStream file = new FileInputStream(propertiesFile);
         props.load(file);
         file.close();
      } catch (FileNotFoundException var6) {
         SecurityLogger.logNodeManagerPropertiesNotFound();
         return null;
      } catch (IOException var7) {
         SecurityLogger.logNodeManagerPropertiesError();
         return null;
      }

      byte[] nameHashkey = props.getProperty("nameHashkey").getBytes();
      byte[] idHashkey = props.getProperty("idHashkey").getBytes();
      JSafeEncryptionServiceFactory factory = new JSafeEncryptionServiceFactory();
      EncryptionService es = factory.getEncryptionService(Hex.fromHexString(nameHashkey, nameHashkey.length), "0x1f48730ab4957122fccb2856671df094bcc294af", Hex.fromHexString(idHashkey, idHashkey.length));
      return new ClearOrEncryptedService(es);
   }
}

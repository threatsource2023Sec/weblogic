package org.apache.xml.security.keys;

import java.io.PrintStream;
import java.security.PublicKey;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.content.KeyName;
import org.apache.xml.security.keys.content.KeyValue;
import org.apache.xml.security.keys.content.MgmtData;
import org.apache.xml.security.keys.content.X509Data;

public final class KeyUtils {
   private KeyUtils() {
   }

   public static void prinoutKeyInfo(KeyInfo ki, PrintStream os) throws XMLSecurityException {
      int i;
      for(i = 0; i < ki.lengthKeyName(); ++i) {
         KeyName x = ki.itemKeyName(i);
         os.println("KeyName(" + i + ")=\"" + x.getKeyName() + "\"");
      }

      for(i = 0; i < ki.lengthKeyValue(); ++i) {
         KeyValue x = ki.itemKeyValue(i);
         PublicKey pk = x.getPublicKey();
         os.println("KeyValue Nr. " + i);
         os.println(pk);
      }

      for(i = 0; i < ki.lengthMgmtData(); ++i) {
         MgmtData x = ki.itemMgmtData(i);
         os.println("MgmtData(" + i + ")=\"" + x.getMgmtData() + "\"");
      }

      for(i = 0; i < ki.lengthX509Data(); ++i) {
         X509Data x = ki.itemX509Data(i);
         os.println("X509Data(" + i + ")=\"" + (x.containsCertificate() ? "Certificate " : "") + (x.containsIssuerSerial() ? "IssuerSerial " : "") + "\"");
      }

   }
}

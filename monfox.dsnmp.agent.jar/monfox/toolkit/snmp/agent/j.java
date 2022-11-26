package monfox.toolkit.snmp.agent;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

class j {
   public static j getInstance() {
      b.getInstance();
      return new j();
   }

   static InputStream a(String var0) throws FileNotFoundException {
      return b.getInputStream(var0);
   }

   static Properties a() {
      return new k();
   }
}

package org.glassfish.hk2.configuration.hub.internal;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import org.glassfish.hk2.configuration.hub.api.BeanDatabase;
import org.glassfish.hk2.configuration.hub.api.Type;
import org.glassfish.hk2.utilities.reflection.Pretty;

public class Utilities {
   public static void dumpDatabase(BeanDatabase database, PrintStream stream) {
      Iterator var2 = database.getAllTypes().iterator();

      while(var2.hasNext()) {
         Type type = (Type)var2.next();
         Set instanceNames = type.getInstances().keySet();
         stream.println(type.getName() + " -> " + Pretty.collection(instanceNames));
      }

   }

   public static String dumpDatabaseAsString(BeanDatabase database) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PrintStream printer = new PrintStream(baos);

      String var3;
      try {
         dumpDatabase(database, printer);
         printer.close();
         var3 = new String(baos.toByteArray(), StandardCharsets.UTF_8);
      } finally {
         printer.close();
      }

      return var3;
   }
}

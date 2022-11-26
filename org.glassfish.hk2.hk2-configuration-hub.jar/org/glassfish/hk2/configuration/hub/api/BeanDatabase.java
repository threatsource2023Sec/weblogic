package org.glassfish.hk2.configuration.hub.api;

import java.io.PrintStream;
import java.util.Set;

public interface BeanDatabase {
   Set getAllTypes();

   Type getType(String var1);

   Instance getInstance(String var1, String var2);

   void dumpDatabase();

   void dumpDatabase(PrintStream var1);

   String dumpDatabaseAsString();
}

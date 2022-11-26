package com.sun.faces.scripting;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GroovyHelperFactory {
   private static final Logger LOGGER;
   private static final String GROOVY_HELPER_IMPL = "com.sun.faces.scripting.GroovyHelperImpl";

   public static GroovyHelper createHelper() {
      try {
         if (Util.loadClass("groovy.util.GroovyScriptEngine", GroovyHelperFactory.class) != null) {
            try {
               Class c = Util.loadClass("com.sun.faces.scripting.GroovyHelperImpl", GroovyHelperFactory.class);
               return (GroovyHelper)c.newInstance();
            } catch (UnsupportedOperationException var1) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.fine("Groovy runtime available, but WEB-INF/groovy directory not present.  Groovy support will not be enabled.");
               }
            } catch (Exception var2) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, "Groovy support not available", var2);
               }
            }
         }
      } catch (ClassNotFoundException var3) {
      }

      return null;
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}

package org.glassfish.grizzly.nio;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;

class SelectionKeyHandlerInitializer {
   private static final String PROP = "org.glassfish.grizzly.DEFAULT_SELECTION_KEY_HANDLER";
   private static final Logger LOGGER = Grizzly.logger(SelectionKeyHandlerInitializer.class);

   static SelectionKeyHandler initHandler() {
      String className = System.getProperty("org.glassfish.grizzly.DEFAULT_SELECTION_KEY_HANDLER");
      if (className != null) {
         try {
            Class handlerClass = Class.forName(className, true, SelectionKeyHandler.class.getClassLoader());
            return (SelectionKeyHandler)handlerClass.newInstance();
         } catch (Exception var2) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "Unable to load or create a new instance of SelectionKeyHandler {0}.  Cause: {1}", new Object[]{className, var2.getMessage()});
            }

            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, var2.toString(), var2);
            }

            return new DefaultSelectionKeyHandler();
         }
      } else {
         return new DefaultSelectionKeyHandler();
      }
   }
}

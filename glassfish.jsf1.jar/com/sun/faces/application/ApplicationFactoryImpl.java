package com.sun.faces.application;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;

public class ApplicationFactoryImpl extends ApplicationFactory {
   private static final Logger logger;
   private Application application = null;

   public ApplicationFactoryImpl() {
      if (logger.isLoggable(Level.FINE)) {
         logger.log(Level.FINE, "Created ApplicationFactory ");
      }

   }

   public Application getApplication() {
      if (this.application == null) {
         this.application = new ApplicationImpl();
         if (logger.isLoggable(Level.FINE)) {
            logger.fine(MessageFormat.format("Created Application instance ''{0}''", this.application));
         }
      }

      return this.application;
   }

   public void setApplication(Application application) {
      if (application == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "application");
         throw new NullPointerException(message);
      } else {
         this.application = application;
         if (logger.isLoggable(Level.FINE)) {
            logger.fine(MessageFormat.format("set Application Instance to ''{0}''", application.getClass().getName()));
         }

      }
   }

   static {
      logger = FacesLogger.APPLICATION.getLogger();
   }
}

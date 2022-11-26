package com.sun.faces.application;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.FacesContext;

public class ApplicationFactoryImpl extends ApplicationFactory {
   private static final Logger LOGGER;
   private final Map applicationHolder = new ConcurrentHashMap(1);
   private final String createdBy = Util.generateCreatedBy(FacesContext.getCurrentInstance());

   public ApplicationFactoryImpl() {
      super((ApplicationFactory)null);
      LOGGER.log(Level.FINE, "Created ApplicationFactory ");
   }

   public Application getApplication() {
      return (Application)this.applicationHolder.computeIfAbsent("default", (e) -> {
         Application applicationImpl = new ApplicationImpl();
         InjectionApplicationFactory.setApplicationInstance(applicationImpl);
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine(MessageFormat.format("Created Application instance ''{0}''", this.applicationHolder));
         }

         return applicationImpl;
      });
   }

   public void setApplication(Application application) {
      Util.notNull("application", application);
      this.applicationHolder.put("default", application);
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine(MessageFormat.format("set Application Instance to ''{0}''", application.getClass().getName()));
      }

   }

   public String toString() {
      return super.toString() + " created by " + this.createdBy;
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}

package com.sun.faces.application;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.FacesContext;

public class InjectionApplicationFactory extends ApplicationFactory {
   private static final Logger LOGGER;
   private Application defaultApplication;
   private Field defaultApplicationField;
   private volatile Application application;

   public InjectionApplicationFactory(ApplicationFactory delegate) {
      super(delegate);
      Util.notNull("applicationFactory", delegate);
   }

   public Application getApplication() {
      if (this.application == null) {
         this.application = this.getWrapped().getApplication();
         if (this.application == null) {
            throw new IllegalStateException(MessageFormat.format("Delegate ApplicationContextFactory, {0}, returned null when calling getApplication().", this.getWrapped().getClass().getName()));
         }

         this.injectDefaultApplication();
      }

      return this.application;
   }

   public synchronized void setApplication(Application application) {
      this.application = application;
      this.getWrapped().setApplication(application);
      this.injectDefaultApplication();
   }

   private void injectDefaultApplication() {
      if (this.defaultApplication == null) {
         FacesContext ctx = FacesContext.getCurrentInstance();
         this.defaultApplication = removeApplicationInstance(ctx.getExternalContext().getApplicationMap());
      }

      if (this.defaultApplication != null) {
         try {
            if (this.defaultApplicationField == null) {
               this.defaultApplicationField = Application.class.getDeclaredField("defaultApplication");
               this.defaultApplicationField.setAccessible(true);
            }

            this.defaultApplicationField.set(this.application, this.defaultApplication);
         } catch (NoSuchFieldException var2) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "Unable to find private field named 'defaultApplication' in javax.faces.application.Application.");
            }
         } catch (IllegalArgumentException | IllegalAccessException | SecurityException var3) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, var3.toString(), var3);
            }
         }
      }

   }

   static void setApplicationInstance(Application app) {
      FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put(InjectionApplicationFactory.class.getName(), app);
   }

   static Application removeApplicationInstance(Map appMap) {
      return (Application)appMap.remove(InjectionApplicationFactory.class.getName());
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}

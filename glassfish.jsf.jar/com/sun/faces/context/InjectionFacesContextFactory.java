package com.sun.faces.context;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.servlet.ServletRequest;

public class InjectionFacesContextFactory extends FacesContextFactory {
   private static final Logger LOGGER;
   private Field defaultFacesContext;
   private Field defaultExternalContext;

   public InjectionFacesContextFactory(FacesContextFactory delegate) {
      super(delegate);
      Util.notNull("facesContextFactory", delegate);

      try {
         this.defaultFacesContext = FacesContext.class.getDeclaredField("defaultFacesContext");
         this.defaultFacesContext.setAccessible(true);
      } catch (NoSuchFieldException var5) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Unable to find private field named 'defaultFacesContext' in javax.faces.context.FacesContext.");
         }
      } catch (SecurityException var6) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, var6.toString(), var6);
         }

         this.defaultFacesContext = null;
      }

      try {
         this.defaultExternalContext = ExternalContext.class.getDeclaredField("defaultExternalContext");
         this.defaultExternalContext.setAccessible(true);
      } catch (NoSuchFieldException var3) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Unable to find private field named 'defaultExternalContext' in javax.faces.context.ExternalContext.");
         }
      } catch (SecurityException var4) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, var4.toString(), var4);
         }

         this.defaultExternalContext = null;
      }

   }

   public FacesContext getFacesContext(Object context, Object request, Object response, Lifecycle lifecycle) throws FacesException {
      FacesContext ctx = this.getWrapped().getFacesContext(context, request, response, lifecycle);
      if (ctx == null) {
         String message = MessageFormat.format("Delegate FacesContextFactory, {0}, returned null when calling getFacesContext().", this.getWrapped().getClass().getName());
         throw new IllegalStateException(message);
      } else {
         this.injectDefaults(ctx, request);
         return ctx;
      }
   }

   private void injectDefaults(FacesContext target, Object request) {
      if (this.defaultFacesContext != null) {
         FacesContext defaultFC = FacesContextImpl.getDefaultFacesContext();
         if (defaultFC != null) {
            try {
               this.defaultFacesContext.set(target, defaultFC);
            } catch (IllegalAccessException var6) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, var6.toString(), var6);
               }
            }
         }
      }

      if (this.defaultExternalContext != null) {
         ExternalContext defaultExtContext = null;
         if (request instanceof ServletRequest) {
            ServletRequest reqObj = (ServletRequest)request;
            defaultExtContext = (ExternalContext)reqObj.getAttribute(ExternalContextFactoryImpl.DEFAULT_EXTERNAL_CONTEXT_KEY);
            if (defaultExtContext != null) {
               reqObj.removeAttribute(ExternalContextFactoryImpl.DEFAULT_EXTERNAL_CONTEXT_KEY);
            }
         }

         if (defaultExtContext != null) {
            try {
               this.defaultExternalContext.set(target.getExternalContext(), defaultExtContext);
            } catch (IllegalAccessException var5) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, var5.toString(), var5);
               }
            }
         }
      }

   }

   static {
      LOGGER = FacesLogger.CONTEXT.getLogger();
   }
}

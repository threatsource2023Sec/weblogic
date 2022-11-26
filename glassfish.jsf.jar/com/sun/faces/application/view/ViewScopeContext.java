package com.sun.faces.application.view;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

public class ViewScopeContext implements Context, Serializable {
   private static final Logger LOGGER = Logger.getLogger(ViewScopeContext.class.getName());
   private static final long serialVersionUID = -6245899073989073951L;

   public ViewScopeContext() {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Creating ViewScope CDI context");
      }

   }

   private final void assertNotReleased() {
      if (!this.isActive()) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, "Trying to access ViewScope CDI context while it is not active");
         }

         throw new ContextNotActiveException();
      }
   }

   public Object get(Contextual contextual) {
      this.assertNotReleased();
      Object result = null;
      FacesContext facesContext = FacesContext.getCurrentInstance();
      if (facesContext != null) {
         ViewScopeManager manager = ViewScopeManager.getInstance(facesContext);
         if (manager != null) {
            result = manager.getContextManager().getBean(facesContext, contextual);
         }
      }

      return result;
   }

   public Object get(Contextual contextual, CreationalContext creational) {
      this.assertNotReleased();
      Object result = this.get(contextual);
      if (result == null) {
         FacesContext facesContext = FacesContext.getCurrentInstance();
         if (facesContext != null) {
            ViewScopeManager manager = ViewScopeManager.getInstance(facesContext);
            result = manager.getContextManager().getBean(facesContext, contextual);
            if (result == null) {
               result = manager.getContextManager().createBean(facesContext, contextual, creational);
            }
         }
      }

      return result;
   }

   public Class getScope() {
      return ViewScoped.class;
   }

   public boolean isActive() {
      boolean result = false;
      FacesContext facesContext = FacesContext.getCurrentInstance();
      if (facesContext != null) {
         UIViewRoot viewRoot = facesContext.getViewRoot();
         if (viewRoot != null) {
            result = true;
         }
      }

      return result;
   }
}

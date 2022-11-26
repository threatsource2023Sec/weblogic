package com.sun.faces.lifecycle;

import com.sun.faces.config.WebConfiguration;
import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.faces.lifecycle.ClientWindow;
import javax.faces.lifecycle.ClientWindowFactory;

public class ClientWindowFactoryImpl extends ClientWindowFactory {
   private boolean isClientWindowEnabled = false;
   private WebConfiguration config = null;

   public ClientWindowFactoryImpl() {
      super((ClientWindowFactory)null);
      FacesContext context = FacesContext.getCurrentInstance();
      context.getApplication().subscribeToEvent(PostConstructApplicationEvent.class, Application.class, new PostConstructApplicationListener());
   }

   public ClientWindowFactoryImpl(boolean ignored) {
      super((ClientWindowFactory)null);
      this.isClientWindowEnabled = false;
   }

   private void postConstructApplicationInitialization() {
      FacesContext context = FacesContext.getCurrentInstance();
      ExternalContext extContext = context.getExternalContext();
      this.config = WebConfiguration.getInstance(extContext);
      String optionValue = this.config.getOptionValue(WebConfiguration.WebContextInitParameter.ClientWindowMode);
      this.isClientWindowEnabled = null != optionValue && "url".equals(optionValue);
   }

   public ClientWindow getClientWindow(FacesContext context) {
      return !this.isClientWindowEnabled ? null : new ClientWindowImpl();
   }

   private class PostConstructApplicationListener implements SystemEventListener {
      private PostConstructApplicationListener() {
      }

      public boolean isListenerForSource(Object source) {
         return source instanceof Application;
      }

      public void processEvent(SystemEvent event) throws AbortProcessingException {
         ClientWindowFactoryImpl.this.postConstructApplicationInitialization();
      }

      // $FF: synthetic method
      PostConstructApplicationListener(Object x1) {
         this();
      }
   }
}

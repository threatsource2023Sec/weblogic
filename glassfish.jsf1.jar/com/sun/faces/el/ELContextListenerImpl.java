package com.sun.faces.el;

import javax.el.ELContext;
import javax.el.ELContextEvent;
import javax.el.ELContextListener;
import javax.faces.context.FacesContext;

public class ELContextListenerImpl implements ELContextListener {
   public void contextCreated(ELContextEvent ece) {
      FacesContext context = FacesContext.getCurrentInstance();
      if (context != null) {
         ELContext source = (ELContext)ece.getSource();
         source.putContext(FacesContext.class, context);
         ELContextListener[] listeners = context.getApplication().getELContextListeners();
         if (listeners != null) {
            for(int i = 0; i < listeners.length; ++i) {
               ELContextListener elcl = listeners[i];
               elcl.contextCreated(new ELContextEvent(source));
            }

         }
      }
   }
}

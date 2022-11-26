package com.sun.faces.cdi;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

public class SessionProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public SessionProducer() {
      super.name("session").scope(SessionScoped.class).create((e) -> {
         return FacesContext.getCurrentInstance().getExternalContext().getSession(false);
      });
   }
}

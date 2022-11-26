package com.sun.faces.application.view;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.ViewMapListener;

public class ViewScopeEventListener implements ViewMapListener {
   public void processEvent(SystemEvent se) throws AbortProcessingException {
      ViewScopeManager.getInstance(FacesContext.getCurrentInstance()).processEvent(se);
   }

   public boolean isListenerForSource(Object source) {
      return source instanceof UIViewRoot;
   }
}

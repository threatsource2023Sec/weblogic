package com.sun.faces.scripting;

import javax.faces.FacesException;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class ActionListenerProxy implements ActionListener {
   private String scriptName;
   private ActionListener alDelegate;

   public ActionListenerProxy(String scriptName, ActionListener alDelegate) {
      this.scriptName = scriptName;
      this.alDelegate = alDelegate;
   }

   public void processAction(ActionEvent event) throws AbortProcessingException {
      this.getGroovyDelegate().processAction(event);
   }

   private ActionListener getGroovyDelegate() {
      try {
         return (ActionListener)GroovyHelper.newInstance(this.scriptName, ActionListener.class, this.alDelegate);
      } catch (Exception var2) {
         throw new FacesException(var2);
      }
   }
}

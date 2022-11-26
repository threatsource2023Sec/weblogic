package com.sun.faces.scripting;

import javax.faces.FacesException;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class PhaseListenerProxy implements PhaseListener {
   private String scriptName;

   public PhaseListenerProxy(String scriptName) {
      this.scriptName = scriptName;
   }

   public void afterPhase(PhaseEvent event) {
      this.getGroovyDelegate().afterPhase(event);
   }

   public void beforePhase(PhaseEvent event) {
      this.getGroovyDelegate().beforePhase(event);
   }

   public PhaseId getPhaseId() {
      return this.getGroovyDelegate().getPhaseId();
   }

   private PhaseListener getGroovyDelegate() {
      try {
         return (PhaseListener)GroovyHelper.newInstance(this.scriptName);
      } catch (Exception var2) {
         throw new FacesException(var2);
      }
   }
}

package com.sun.faces.cdi;

import javax.faces.component.StateHolder;
import javax.faces.component.behavior.Behavior;
import javax.faces.context.FacesContext;
import javax.faces.event.BehaviorEvent;

class CdiBehavior implements Behavior, StateHolder {
   private String behaviorId;
   private transient Behavior delegate;

   public CdiBehavior(String behaviorId, Behavior delegate) {
      this.behaviorId = behaviorId;
      this.delegate = delegate;
   }

   public void broadcast(BehaviorEvent event) {
      this.getDelegate(event.getFacesContext()).broadcast(event);
   }

   private Behavior getDelegate(FacesContext facesContext) {
      if (this.delegate == null) {
         this.delegate = facesContext.getApplication().createBehavior(this.behaviorId);
      }

      return this.delegate;
   }

   public boolean isTransient() {
      return false;
   }

   public void restoreState(FacesContext facesContext, Object state) {
      Object[] stateArray = (Object[])((Object[])state);
      this.behaviorId = (String)stateArray[0];
   }

   public Object saveState(FacesContext facesContext) {
      return new Object[]{this.behaviorId};
   }

   public void setTransient(boolean transientValue) {
   }
}

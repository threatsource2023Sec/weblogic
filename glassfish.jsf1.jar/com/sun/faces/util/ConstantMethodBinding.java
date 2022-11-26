package com.sun.faces.util;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;

/** @deprecated */
public class ConstantMethodBinding extends MethodBinding implements StateHolder {
   private String outcome = null;
   private boolean transientFlag = false;

   public ConstantMethodBinding() {
   }

   public ConstantMethodBinding(String yourOutcome) {
      this.outcome = yourOutcome;
   }

   public Object invoke(FacesContext context, Object[] params) {
      return this.outcome;
   }

   public Class getType(FacesContext context) {
      return String.class;
   }

   public Object saveState(FacesContext context) {
      return this.outcome;
   }

   public void restoreState(FacesContext context, Object state) {
      this.outcome = (String)state;
   }

   public boolean isTransient() {
      return this.transientFlag;
   }

   public void setTransient(boolean transientFlag) {
      this.transientFlag = transientFlag;
   }
}

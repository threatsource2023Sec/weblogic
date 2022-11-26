package com.sun.faces.taglib.jsf_core;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class SetPropertyActionListenerImpl implements ActionListener, StateHolder {
   private ValueExpression targetExpression = null;
   private ValueExpression valueExpression = null;

   public SetPropertyActionListenerImpl() {
   }

   public SetPropertyActionListenerImpl(ValueExpression target, ValueExpression value) {
      this.targetExpression = target;
      this.valueExpression = value;
   }

   public void processAction(ActionEvent e) throws AbortProcessingException {
      ELContext elc = FacesContext.getCurrentInstance().getELContext();

      try {
         this.targetExpression.setValue(elc, this.valueExpression.getValue(elc));
      } catch (ELException var4) {
      }

   }

   public void setTransient(boolean newTransientValue) {
   }

   public boolean isTransient() {
      return false;
   }

   public Object saveState(FacesContext context) {
      Object[] state = new Object[]{this.targetExpression, this.valueExpression};
      return state;
   }

   public void restoreState(FacesContext context, Object state) {
      Object[] stateArray = (Object[])((Object[])state);
      this.targetExpression = (ValueExpression)stateArray[0];
      this.valueExpression = (ValueExpression)stateArray[1];
   }
}

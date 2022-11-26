package com.sun.faces.taglib.jsf_core;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class SetPropertyActionListenerImpl implements ActionListener, StateHolder {
   private ValueExpression target;
   private ValueExpression source;

   public SetPropertyActionListenerImpl() {
   }

   public SetPropertyActionListenerImpl(ValueExpression target, ValueExpression value) {
      this.target = target;
      this.source = value;
   }

   public void processAction(ActionEvent e) throws AbortProcessingException {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      ELContext elContext = facesContext.getELContext();

      try {
         Object value = this.source.getValue(elContext);
         if (value != null) {
            ExpressionFactory factory = facesContext.getApplication().getExpressionFactory();
            value = factory.coerceToType(value, this.target.getType(elContext));
         }

         this.target.setValue(elContext, value);
      } catch (ELException var6) {
         throw new AbortProcessingException(var6);
      }
   }

   public void setTransient(boolean trans) {
   }

   public boolean isTransient() {
      return false;
   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         Object[] state = new Object[]{this.target, this.source};
         return state;
      }
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else if (state != null) {
         Object[] stateArray = (Object[])((Object[])state);
         this.target = (ValueExpression)stateArray[0];
         this.source = (ValueExpression)stateArray[1];
      }
   }
}

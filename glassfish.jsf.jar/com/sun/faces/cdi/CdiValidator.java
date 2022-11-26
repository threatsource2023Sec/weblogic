package com.sun.faces.cdi;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class CdiValidator implements Validator, StateHolder {
   private String validatorId;
   private transient Validator delegate;

   public CdiValidator() {
   }

   public CdiValidator(String validatorId, Validator delegate) {
      this.validatorId = validatorId;
      this.delegate = delegate;
   }

   public Object saveState(FacesContext facesContext) {
      return new Object[]{this.validatorId};
   }

   public void restoreState(FacesContext facesContext, Object state) {
      Object[] stateArray = (Object[])((Object[])state);
      this.validatorId = (String)stateArray[0];
   }

   public boolean isTransient() {
      return false;
   }

   public void setTransient(boolean transientValue) {
   }

   private Validator getDelegate(FacesContext facesContext) {
      if (this.delegate == null) {
         this.delegate = facesContext.getApplication().createValidator(this.validatorId);
      }

      return this.delegate;
   }

   public void validate(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {
      this.getDelegate(facesContext).validate(facesContext, component, value);
   }
}

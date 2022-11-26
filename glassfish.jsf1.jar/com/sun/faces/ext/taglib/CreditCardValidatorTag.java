package com.sun.faces.ext.taglib;

import com.sun.faces.ext.validator.CreditCardValidator;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.webapp.ValidatorELTag;

public class CreditCardValidatorTag extends ValidatorELTag {
   protected Validator createValidator() {
      Application app = FacesContext.getCurrentInstance().getApplication();
      CreditCardValidator validator = (CreditCardValidator)app.createValidator("com.sun.faces.ext.validator.CreditCardValidator");
      return validator;
   }
}

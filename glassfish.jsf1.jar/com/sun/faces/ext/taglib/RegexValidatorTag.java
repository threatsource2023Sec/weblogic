package com.sun.faces.ext.taglib;

import com.sun.faces.ext.validator.RegexValidator;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.webapp.ValidatorELTag;

public class RegexValidatorTag extends ValidatorELTag {
   private static final long serialVersionUID = -8003419724035817795L;
   private ValueExpression regex;

   public void setPattern(ValueExpression pattern) {
      this.regex = pattern;
   }

   protected Validator createValidator() {
      FacesContext ctx = FacesContext.getCurrentInstance();
      Application app = ctx.getApplication();
      RegexValidator validator = (RegexValidator)app.createValidator("com.sun.faces.ext.validator.RegexValidator");
      validator.setPattern((String)this.regex.getValue(ctx.getELContext()));
      return validator;
   }
}

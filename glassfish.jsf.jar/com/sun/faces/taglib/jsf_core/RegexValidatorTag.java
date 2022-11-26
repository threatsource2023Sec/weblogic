package com.sun.faces.taglib.jsf_core;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.validator.RegexValidator;
import javax.faces.validator.Validator;
import javax.servlet.jsp.JspException;

public class RegexValidatorTag extends AbstractValidatorTag {
   private static final long serialVersionUID = 5353063400995625645L;
   private ValueExpression regex;
   private ValueExpression VALIDATOR_ID_EXPR;

   public RegexValidatorTag() {
      if (this.VALIDATOR_ID_EXPR == null) {
         FacesContext context = FacesContext.getCurrentInstance();
         ExpressionFactory factory = context.getApplication().getExpressionFactory();
         this.VALIDATOR_ID_EXPR = factory.createValueExpression(context.getELContext(), "javax.faces.RegularExpression", String.class);
      }

   }

   public void setPattern(ValueExpression pattern) {
      this.regex = pattern;
   }

   protected Validator createValidator() throws JspException {
      super.setValidatorId(this.VALIDATOR_ID_EXPR);
      RegexValidator validator = (RegexValidator)super.createValidator();

      assert validator != null;

      if (this.regex != null) {
         FacesContext ctx = FacesContext.getCurrentInstance();
         validator.setPattern((String)this.regex.getValue(ctx.getELContext()));
      }

      return validator;
   }
}

package com.sun.faces.taglib.jsf_core;

import com.sun.faces.el.ELUtils;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.validator.LengthValidator;
import javax.faces.validator.Validator;
import javax.servlet.jsp.JspException;

public class ValidateLengthTag extends MaxMinValidatorTag {
   private static final long serialVersionUID = -3594596279980791500L;
   private static ValueExpression VALIDATOR_ID_EXPR = null;
   protected ValueExpression maximumExpression = null;
   protected ValueExpression minimumExpression = null;
   protected int maximum = 0;
   protected int minimum = 0;

   public ValidateLengthTag() {
      if (VALIDATOR_ID_EXPR == null) {
         FacesContext context = FacesContext.getCurrentInstance();
         ExpressionFactory factory = context.getApplication().getExpressionFactory();
         VALIDATOR_ID_EXPR = factory.createValueExpression(context.getELContext(), "javax.faces.Length", String.class);
      }

   }

   public void setMaximum(ValueExpression newMaximum) {
      this.maximumSet = true;
      this.maximumExpression = newMaximum;
   }

   public void setMinimum(ValueExpression newMinimum) {
      this.minimumSet = true;
      this.minimumExpression = newMinimum;
   }

   public int doStartTag() throws JspException {
      super.setValidatorId(VALIDATOR_ID_EXPR);
      return super.doStartTag();
   }

   protected Validator createValidator() throws JspException {
      LengthValidator result = (LengthValidator)super.createValidator();

      assert null != result;

      this.evaluateExpressions();
      if (this.maximumSet) {
         result.setMaximum(this.maximum);
      }

      if (this.minimumSet) {
         result.setMinimum(this.minimum);
      }

      return result;
   }

   private void evaluateExpressions() {
      ELContext context = FacesContext.getCurrentInstance().getELContext();
      if (this.minimumExpression != null) {
         if (!this.minimumExpression.isLiteralText()) {
            this.minimum = ((Number)ELUtils.evaluateValueExpression(this.minimumExpression, context)).intValue();
         } else {
            this.minimum = Integer.valueOf(this.minimumExpression.getExpressionString());
         }
      }

      if (this.maximumExpression != null) {
         if (!this.maximumExpression.isLiteralText()) {
            this.maximum = ((Number)ELUtils.evaluateValueExpression(this.maximumExpression, context)).intValue();
         } else {
            this.maximum = Integer.valueOf(this.maximumExpression.getExpressionString());
         }
      }

   }
}

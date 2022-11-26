package com.sun.faces.taglib.jsf_core;

import com.sun.faces.el.ELUtils;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.validator.DoubleRangeValidator;
import javax.faces.validator.Validator;
import javax.servlet.jsp.JspException;

public class ValidateDoubleRangeTag extends MaxMinValidatorTag {
   private static final long serialVersionUID = 1677210040390032609L;
   private static ValueExpression VALIDATOR_ID_EXPR = null;
   protected ValueExpression maximumExpression = null;
   protected ValueExpression minimumExpression = null;
   protected double maximum = 0.0;
   protected double minimum = 0.0;

   public ValidateDoubleRangeTag() {
      if (VALIDATOR_ID_EXPR == null) {
         FacesContext context = FacesContext.getCurrentInstance();
         ExpressionFactory factory = context.getApplication().getExpressionFactory();
         VALIDATOR_ID_EXPR = factory.createValueExpression(context.getELContext(), "javax.faces.DoubleRange", String.class);
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
      DoubleRangeValidator result = (DoubleRangeValidator)super.createValidator();

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
            this.minimum = ((Number)ELUtils.evaluateValueExpression(this.minimumExpression, context)).doubleValue();
         } else {
            this.minimum = Double.valueOf(this.minimumExpression.getExpressionString());
         }
      }

      if (this.maximumExpression != null) {
         if (!this.maximumExpression.isLiteralText()) {
            this.maximum = ((Number)ELUtils.evaluateValueExpression(this.maximumExpression, context)).doubleValue();
         } else {
            this.maximum = Double.valueOf(this.maximumExpression.getExpressionString());
         }
      }

   }
}

package com.sun.faces.taglib.jsf_core;

import com.sun.faces.el.ELUtils;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.validator.LongRangeValidator;
import javax.faces.validator.Validator;
import javax.servlet.jsp.JspException;

public class ValidateLongRangeTag extends MaxMinValidatorTag {
   private static final long serialVersionUID = 292617728229736800L;
   private static ValueExpression VALIDATOR_ID_EXPR = null;
   protected ValueExpression maximumExpression = null;
   protected ValueExpression minimumExpression = null;
   protected long maximum = 0L;
   protected long minimum = 0L;

   public ValidateLongRangeTag() {
      if (VALIDATOR_ID_EXPR == null) {
         FacesContext context = FacesContext.getCurrentInstance();
         ExpressionFactory factory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
         VALIDATOR_ID_EXPR = factory.createValueExpression(context.getELContext(), "javax.faces.LongRange", String.class);
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
      LongRangeValidator result = (LongRangeValidator)super.createValidator();

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
            this.minimum = ((Number)ELUtils.evaluateValueExpression(this.minimumExpression, context)).longValue();
         } else {
            this.minimum = Long.valueOf(this.minimumExpression.getExpressionString());
         }
      }

      if (this.maximumExpression != null) {
         if (!this.maximumExpression.isLiteralText()) {
            this.maximum = ((Number)ELUtils.evaluateValueExpression(this.maximumExpression, context)).longValue();
         } else {
            this.maximum = Long.valueOf(this.maximumExpression.getExpressionString());
         }
      }

   }
}

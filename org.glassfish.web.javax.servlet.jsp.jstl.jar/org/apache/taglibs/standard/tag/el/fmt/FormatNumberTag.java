package org.apache.taglibs.standard.tag.el.fmt;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.fmt.FormatNumberSupport;

public class FormatNumberTag extends FormatNumberSupport {
   private String value_;
   private String type_;
   private String pattern_;
   private String currencyCode_;
   private String currencySymbol_;
   private String groupingUsed_;
   private String maxIntegerDigits_;
   private String minIntegerDigits_;
   private String maxFractionDigits_;
   private String minFractionDigits_;

   public FormatNumberTag() {
      this.init();
   }

   public int doStartTag() throws JspException {
      this.evaluateExpressions();
      return super.doStartTag();
   }

   public void release() {
      super.release();
      this.init();
   }

   public void setValue(String value_) {
      this.value_ = value_;
      this.valueSpecified = true;
   }

   public void setType(String type_) {
      this.type_ = type_;
   }

   public void setPattern(String pattern_) {
      this.pattern_ = pattern_;
   }

   public void setCurrencyCode(String currencyCode_) {
      this.currencyCode_ = currencyCode_;
   }

   public void setCurrencySymbol(String currencySymbol_) {
      this.currencySymbol_ = currencySymbol_;
   }

   public void setGroupingUsed(String groupingUsed_) {
      this.groupingUsed_ = groupingUsed_;
      this.groupingUsedSpecified = true;
   }

   public void setMaxIntegerDigits(String maxIntegerDigits_) {
      this.maxIntegerDigits_ = maxIntegerDigits_;
      this.maxIntegerDigitsSpecified = true;
   }

   public void setMinIntegerDigits(String minIntegerDigits_) {
      this.minIntegerDigits_ = minIntegerDigits_;
      this.minIntegerDigitsSpecified = true;
   }

   public void setMaxFractionDigits(String maxFractionDigits_) {
      this.maxFractionDigits_ = maxFractionDigits_;
      this.maxFractionDigitsSpecified = true;
   }

   public void setMinFractionDigits(String minFractionDigits_) {
      this.minFractionDigits_ = minFractionDigits_;
      this.minFractionDigitsSpecified = true;
   }

   private void init() {
      this.value_ = this.type_ = this.pattern_ = null;
      this.currencyCode_ = this.currencySymbol_ = null;
      this.groupingUsed_ = null;
      this.maxIntegerDigits_ = this.minIntegerDigits_ = null;
      this.maxFractionDigits_ = this.minFractionDigits_ = null;
   }

   private void evaluateExpressions() throws JspException {
      Object obj = null;
      if (this.value_ != null) {
         this.value = ExpressionEvaluatorManager.evaluate("value", this.value_, Object.class, this, this.pageContext);
      }

      if (this.type_ != null) {
         this.type = (String)ExpressionEvaluatorManager.evaluate("type", this.type_, String.class, this, this.pageContext);
      }

      if (this.pattern_ != null) {
         this.pattern = (String)ExpressionEvaluatorManager.evaluate("pattern", this.pattern_, String.class, this, this.pageContext);
      }

      if (this.currencyCode_ != null) {
         this.currencyCode = (String)ExpressionEvaluatorManager.evaluate("currencyCode", this.currencyCode_, String.class, this, this.pageContext);
      }

      if (this.currencySymbol_ != null) {
         this.currencySymbol = (String)ExpressionEvaluatorManager.evaluate("currencySymbol", this.currencySymbol_, String.class, this, this.pageContext);
      }

      if (this.groupingUsed_ != null) {
         obj = ExpressionEvaluatorManager.evaluate("groupingUsed", this.groupingUsed_, Boolean.class, this, this.pageContext);
         if (obj != null) {
            this.isGroupingUsed = (Boolean)obj;
         }
      }

      if (this.maxIntegerDigits_ != null) {
         obj = ExpressionEvaluatorManager.evaluate("maxIntegerDigits", this.maxIntegerDigits_, Integer.class, this, this.pageContext);
         if (obj != null) {
            this.maxIntegerDigits = (Integer)obj;
         }
      }

      if (this.minIntegerDigits_ != null) {
         obj = ExpressionEvaluatorManager.evaluate("minIntegerDigits", this.minIntegerDigits_, Integer.class, this, this.pageContext);
         if (obj != null) {
            this.minIntegerDigits = (Integer)obj;
         }
      }

      if (this.maxFractionDigits_ != null) {
         obj = ExpressionEvaluatorManager.evaluate("maxFractionDigits", this.maxFractionDigits_, Integer.class, this, this.pageContext);
         if (obj != null) {
            this.maxFractionDigits = (Integer)obj;
         }
      }

      if (this.minFractionDigits_ != null) {
         obj = ExpressionEvaluatorManager.evaluate("minFractionDigits", this.minFractionDigits_, Integer.class, this, this.pageContext);
         if (obj != null) {
            this.minFractionDigits = (Integer)obj;
         }
      }

   }
}

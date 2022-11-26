package com.sun.faces.taglib.jsf_core;

import com.sun.faces.el.ELUtils;
import java.util.Locale;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.NumberConverter;
import javax.servlet.jsp.JspException;

public class ConvertNumberTag extends AbstractConverterTag {
   private static final long serialVersionUID = -2710405278792415110L;
   private static ValueExpression CONVERTER_ID_EXPR = null;
   private ValueExpression currencyCodeExpression;
   private ValueExpression currencySymbolExpression;
   private ValueExpression groupingUsedExpression;
   private ValueExpression integerOnlyExpression;
   private ValueExpression maxFractionDigitsExpression;
   private ValueExpression maxIntegerDigitsExpression;
   private ValueExpression minFractionDigitsExpression;
   private ValueExpression minIntegerDigitsExpression;
   private ValueExpression localeExpression;
   private ValueExpression patternExpression;
   private ValueExpression typeExpression;
   private String currencyCode;
   private String currencySymbol;
   private boolean groupingUsed;
   private boolean integerOnly;
   private int maxFractionDigits;
   private int maxIntegerDigits;
   private int minFractionDigits;
   private int minIntegerDigits;
   private Locale locale;
   private String pattern;
   private String type;
   private boolean maxFractionDigitsSpecified;
   private boolean maxIntegerDigitsSpecified;
   private boolean minFractionDigitsSpecified;
   private boolean minIntegerDigitsSpecified;

   public ConvertNumberTag() {
      this.init();
   }

   public void release() {
      super.release();
      this.init();
   }

   private void init() {
      this.currencyCode = null;
      this.currencyCodeExpression = null;
      this.currencySymbol = null;
      this.currencySymbolExpression = null;
      this.groupingUsed = true;
      this.groupingUsedExpression = null;
      this.integerOnly = false;
      this.integerOnlyExpression = null;
      this.maxFractionDigits = 0;
      this.maxFractionDigitsExpression = null;
      this.maxFractionDigitsSpecified = false;
      this.maxIntegerDigits = 0;
      this.maxIntegerDigitsExpression = null;
      this.maxIntegerDigitsSpecified = false;
      this.minFractionDigits = 0;
      this.minFractionDigitsExpression = null;
      this.minFractionDigitsSpecified = false;
      this.minIntegerDigits = 0;
      this.minIntegerDigitsExpression = null;
      this.minIntegerDigitsSpecified = false;
      this.locale = null;
      this.localeExpression = null;
      this.pattern = null;
      this.patternExpression = null;
      this.type = "number";
      this.typeExpression = null;
      if (CONVERTER_ID_EXPR == null) {
         FacesContext context = FacesContext.getCurrentInstance();
         ExpressionFactory factory = context.getApplication().getExpressionFactory();
         CONVERTER_ID_EXPR = factory.createValueExpression(context.getELContext(), "javax.faces.Number", String.class);
      }

   }

   public void setCurrencyCode(ValueExpression currencyCode) {
      this.currencyCodeExpression = currencyCode;
   }

   public void setCurrencySymbol(ValueExpression currencySymbol) {
      this.currencySymbolExpression = currencySymbol;
   }

   public void setGroupingUsed(ValueExpression groupingUsed) {
      this.groupingUsedExpression = groupingUsed;
   }

   public void setIntegerOnly(ValueExpression integerOnly) {
      this.integerOnlyExpression = integerOnly;
   }

   public void setMaxFractionDigits(ValueExpression maxFractionDigits) {
      this.maxFractionDigitsExpression = maxFractionDigits;
      this.maxFractionDigitsSpecified = true;
   }

   public void setMaxIntegerDigits(ValueExpression maxIntegerDigits) {
      this.maxIntegerDigitsExpression = maxIntegerDigits;
      this.maxIntegerDigitsSpecified = true;
   }

   public void setMinFractionDigits(ValueExpression minFractionDigits) {
      this.minFractionDigitsExpression = minFractionDigits;
      this.minFractionDigitsSpecified = true;
   }

   public void setMinIntegerDigits(ValueExpression minIntegerDigits) {
      this.minIntegerDigitsExpression = minIntegerDigits;
   }

   public void setLocale(ValueExpression locale) {
      this.localeExpression = locale;
   }

   public void setPattern(ValueExpression pattern) {
      this.patternExpression = pattern;
   }

   public void setType(ValueExpression type) {
      this.typeExpression = type;
   }

   public int doStartTag() throws JspException {
      super.setConverterId(CONVERTER_ID_EXPR);
      return super.doStartTag();
   }

   protected Converter createConverter() throws JspException {
      NumberConverter result = (NumberConverter)super.createConverter();

      assert null != result;

      this.evaluateExpressions();
      result.setCurrencyCode(this.currencyCode);
      result.setCurrencySymbol(this.currencySymbol);
      result.setGroupingUsed(this.groupingUsed);
      result.setIntegerOnly(this.integerOnly);
      if (this.maxFractionDigitsSpecified) {
         result.setMaxFractionDigits(this.maxFractionDigits);
      }

      if (this.maxIntegerDigitsSpecified) {
         result.setMaxIntegerDigits(this.maxIntegerDigits);
      }

      if (this.minFractionDigitsSpecified) {
         result.setMinFractionDigits(this.minFractionDigits);
      }

      if (this.minIntegerDigitsSpecified) {
         result.setMinIntegerDigits(this.minIntegerDigits);
      }

      result.setLocale(this.locale);
      result.setPattern(this.pattern);
      result.setType(this.type);
      return result;
   }

   private void evaluateExpressions() {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      ELContext elContext = facesContext.getELContext();
      if (this.currencyCodeExpression != null) {
         this.currencyCode = (String)ELUtils.evaluateValueExpression(this.currencyCodeExpression, elContext);
      }

      if (this.currencySymbolExpression != null) {
         this.currencySymbol = (String)ELUtils.evaluateValueExpression(this.currencySymbolExpression, elContext);
      }

      if (this.patternExpression != null) {
         this.pattern = (String)ELUtils.evaluateValueExpression(this.patternExpression, elContext);
      }

      if (this.typeExpression != null) {
         this.type = (String)ELUtils.evaluateValueExpression(this.typeExpression, elContext);
      }

      if (this.groupingUsedExpression != null) {
         if (this.groupingUsedExpression.isLiteralText()) {
            this.groupingUsed = Boolean.valueOf(this.groupingUsedExpression.getExpressionString());
         } else {
            this.groupingUsed = (Boolean)ELUtils.evaluateValueExpression(this.groupingUsedExpression, elContext);
         }
      }

      if (this.integerOnlyExpression != null) {
         if (this.integerOnlyExpression.isLiteralText()) {
            this.integerOnly = Boolean.valueOf(this.integerOnlyExpression.getExpressionString());
         } else {
            this.integerOnly = (Boolean)ELUtils.evaluateValueExpression(this.integerOnlyExpression, elContext);
         }
      }

      if (this.maxFractionDigitsExpression != null) {
         if (this.maxFractionDigitsExpression.isLiteralText()) {
            this.maxFractionDigits = Integer.valueOf(this.maxFractionDigitsExpression.getExpressionString());
         } else {
            this.maxFractionDigits = (Integer)ELUtils.evaluateValueExpression(this.maxFractionDigitsExpression, elContext);
         }
      }

      if (this.maxIntegerDigitsExpression != null) {
         if (this.maxIntegerDigitsExpression.isLiteralText()) {
            this.maxIntegerDigits = Integer.valueOf(this.maxIntegerDigitsExpression.getExpressionString());
         } else {
            this.maxIntegerDigits = (Integer)ELUtils.evaluateValueExpression(this.maxIntegerDigitsExpression, elContext);
         }
      }

      if (this.minFractionDigitsExpression != null) {
         if (this.minFractionDigitsExpression.isLiteralText()) {
            this.minFractionDigits = Integer.valueOf(this.minFractionDigitsExpression.getExpressionString());
         } else {
            this.minFractionDigits = (Integer)ELUtils.evaluateValueExpression(this.minFractionDigitsExpression, elContext);
         }
      }

      if (this.minIntegerDigitsExpression != null) {
         if (this.minIntegerDigitsExpression.isLiteralText()) {
            this.minIntegerDigits = Integer.valueOf(this.minIntegerDigitsExpression.getExpressionString());
         } else {
            this.minIntegerDigits = (Integer)ELUtils.evaluateValueExpression(this.minIntegerDigitsExpression, elContext);
         }
      }

      if (this.localeExpression != null) {
         if (this.localeExpression.isLiteralText()) {
            this.locale = new Locale(this.localeExpression.getExpressionString(), "");
         } else {
            Locale loc = (Locale)ELUtils.evaluateValueExpression(this.localeExpression, elContext);
            if (loc != null) {
               this.locale = loc;
            } else {
               this.locale = facesContext.getViewRoot().getLocale();
            }
         }
      }

   }
}

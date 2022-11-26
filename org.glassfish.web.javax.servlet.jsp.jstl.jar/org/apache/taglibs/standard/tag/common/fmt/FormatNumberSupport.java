package org.apache.taglibs.standard.tag.common.fmt;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.taglibs.standard.resources.Resources;
import org.apache.taglibs.standard.tag.common.core.Util;

public abstract class FormatNumberSupport extends BodyTagSupport {
   private static final Class[] GET_INSTANCE_PARAM_TYPES = new Class[]{String.class};
   private static final String NUMBER = "number";
   private static final String CURRENCY = "currency";
   private static final String PERCENT = "percent";
   protected Object value;
   protected boolean valueSpecified;
   protected String type;
   protected String pattern;
   protected String currencyCode;
   protected String currencySymbol;
   protected boolean isGroupingUsed;
   protected boolean groupingUsedSpecified;
   protected int maxIntegerDigits;
   protected boolean maxIntegerDigitsSpecified;
   protected int minIntegerDigits;
   protected boolean minIntegerDigitsSpecified;
   protected int maxFractionDigits;
   protected boolean maxFractionDigitsSpecified;
   protected int minFractionDigits;
   protected boolean minFractionDigitsSpecified;
   private String var;
   private int scope;
   private static Class currencyClass;

   public FormatNumberSupport() {
      this.init();
   }

   private void init() {
      this.value = this.type = null;
      this.valueSpecified = false;
      this.pattern = this.var = this.currencyCode = this.currencySymbol = null;
      this.groupingUsedSpecified = false;
      this.maxIntegerDigitsSpecified = this.minIntegerDigitsSpecified = false;
      this.maxFractionDigitsSpecified = this.minFractionDigitsSpecified = false;
      this.scope = 1;
   }

   public void setVar(String var) {
      this.var = var;
   }

   public void setScope(String scope) {
      this.scope = Util.getScope(scope);
   }

   public int doEndTag() throws JspException {
      String formatted = null;
      Object input = null;
      if (this.valueSpecified) {
         input = this.value;
      } else if (this.bodyContent != null && this.bodyContent.getString() != null) {
         input = this.bodyContent.getString().trim();
      }

      if (input != null && !input.equals("")) {
         if (input instanceof String) {
            try {
               if (((String)input).indexOf(46) != -1) {
                  input = Double.valueOf((String)input);
               } else {
                  input = Long.valueOf((String)input);
               }
            } catch (NumberFormatException var8) {
               throw new JspException(Resources.getMessage("FORMAT_NUMBER_PARSE_ERROR", input), var8);
            }
         }

         Locale loc = SetLocaleSupport.getFormattingLocale(this.pageContext, this, false, true);
         if (loc != null) {
            NumberFormat formatter = null;
            if (this.pattern != null && !this.pattern.equals("")) {
               DecimalFormatSymbols symbols = new DecimalFormatSymbols(loc);
               formatter = new DecimalFormat(this.pattern, symbols);
            } else {
               formatter = this.createFormatter(loc);
            }

            if (this.pattern != null && !this.pattern.equals("") || "currency".equalsIgnoreCase(this.type)) {
               try {
                  this.setCurrency((NumberFormat)formatter);
               } catch (Exception var7) {
                  throw new JspException(Resources.getMessage("FORMAT_NUMBER_CURRENCY_ERROR"), var7);
               }
            }

            this.configureFormatter((NumberFormat)formatter);
            formatted = ((NumberFormat)formatter).format(input);
         } else {
            formatted = input.toString();
         }

         if (this.var != null) {
            this.pageContext.setAttribute(this.var, formatted, this.scope);
         } else {
            try {
               this.pageContext.getOut().print(formatted);
            } catch (IOException var6) {
               throw new JspTagException(var6.toString(), var6);
            }
         }

         return 6;
      } else {
         if (this.var != null) {
            this.pageContext.removeAttribute(this.var, this.scope);
         }

         return 6;
      }
   }

   public void release() {
      this.init();
   }

   private NumberFormat createFormatter(Locale loc) throws JspException {
      NumberFormat formatter = null;
      if (this.type != null && !"number".equalsIgnoreCase(this.type)) {
         if ("currency".equalsIgnoreCase(this.type)) {
            formatter = NumberFormat.getCurrencyInstance(loc);
         } else {
            if (!"percent".equalsIgnoreCase(this.type)) {
               throw new JspException(Resources.getMessage("FORMAT_NUMBER_INVALID_TYPE", (Object)this.type));
            }

            formatter = NumberFormat.getPercentInstance(loc);
         }
      } else {
         formatter = NumberFormat.getNumberInstance(loc);
      }

      return formatter;
   }

   private void configureFormatter(NumberFormat formatter) {
      if (this.groupingUsedSpecified) {
         formatter.setGroupingUsed(this.isGroupingUsed);
      }

      if (this.maxIntegerDigitsSpecified) {
         formatter.setMaximumIntegerDigits(this.maxIntegerDigits);
      }

      if (this.minIntegerDigitsSpecified) {
         formatter.setMinimumIntegerDigits(this.minIntegerDigits);
      }

      if (this.maxFractionDigitsSpecified) {
         formatter.setMaximumFractionDigits(this.maxFractionDigits);
      }

      if (this.minFractionDigitsSpecified) {
         formatter.setMinimumFractionDigits(this.minFractionDigits);
      }

   }

   private void setCurrency(NumberFormat formatter) throws Exception {
      String code = null;
      String symbol = null;
      if (this.currencyCode != null || this.currencySymbol != null) {
         if (this.currencyCode != null && this.currencySymbol != null) {
            if (currencyClass != null) {
               code = this.currencyCode;
            } else {
               symbol = this.currencySymbol;
            }
         } else if (this.currencyCode == null) {
            symbol = this.currencySymbol;
         } else if (currencyClass != null) {
            code = this.currencyCode;
         } else {
            symbol = this.currencyCode;
         }

         if (code != null) {
            Object[] methodArgs = new Object[1];
            Method m = currencyClass.getMethod("getInstance", GET_INSTANCE_PARAM_TYPES);
            methodArgs[0] = code;
            Object currency = m.invoke((Object)null, methodArgs);
            Class[] paramTypes = new Class[]{currencyClass};
            Class numberFormatClass = Class.forName("java.text.NumberFormat");
            m = numberFormatClass.getMethod("setCurrency", paramTypes);
            methodArgs[0] = currency;
            m.invoke(formatter, methodArgs);
         } else {
            DecimalFormat df = (DecimalFormat)formatter;
            DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
            dfs.setCurrencySymbol(symbol);
            df.setDecimalFormatSymbols(dfs);
         }

      }
   }

   static {
      try {
         currencyClass = Class.forName("java.util.Currency");
      } catch (Exception var1) {
      }

   }
}

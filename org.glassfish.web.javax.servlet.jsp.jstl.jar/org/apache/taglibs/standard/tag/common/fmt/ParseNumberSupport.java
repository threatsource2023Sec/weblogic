package org.apache.taglibs.standard.tag.common.fmt;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.taglibs.standard.resources.Resources;
import org.apache.taglibs.standard.tag.common.core.Util;

public abstract class ParseNumberSupport extends BodyTagSupport {
   private static final String NUMBER = "number";
   private static final String CURRENCY = "currency";
   private static final String PERCENT = "percent";
   protected String value;
   protected boolean valueSpecified;
   protected String type;
   protected String pattern;
   protected Locale parseLocale;
   protected boolean isIntegerOnly;
   protected boolean integerOnlySpecified;
   private String var;
   private int scope;

   public ParseNumberSupport() {
      this.init();
   }

   private void init() {
      this.value = this.type = this.pattern = this.var = null;
      this.valueSpecified = false;
      this.parseLocale = null;
      this.integerOnlySpecified = false;
      this.scope = 1;
   }

   public void setVar(String var) {
      this.var = var;
   }

   public void setScope(String scope) {
      this.scope = Util.getScope(scope);
   }

   public int doEndTag() throws JspException {
      String input = null;
      if (this.valueSpecified) {
         input = this.value;
      } else if (this.bodyContent != null && this.bodyContent.getString() != null) {
         input = this.bodyContent.getString().trim();
      }

      if (input != null && !input.equals("")) {
         Locale loc = this.parseLocale;
         if (loc == null) {
            loc = SetLocaleSupport.getFormattingLocale(this.pageContext, this, false, false);
         }

         if (loc == null) {
            throw new JspException(Resources.getMessage("PARSE_NUMBER_NO_PARSE_LOCALE"));
         } else {
            NumberFormat parser = null;
            DecimalFormatSymbols parsed;
            if (this.pattern != null && !this.pattern.equals("")) {
               parsed = new DecimalFormatSymbols(loc);
               parser = new DecimalFormat(this.pattern, parsed);
            } else {
               parser = this.createParser(loc);
            }

            if (this.integerOnlySpecified) {
               ((NumberFormat)parser).setParseIntegerOnly(this.isIntegerOnly);
            }

            parsed = null;

            Number parsed;
            try {
               parsed = ((NumberFormat)parser).parse(input);
            } catch (ParseException var7) {
               throw new JspException(Resources.getMessage("PARSE_NUMBER_PARSE_ERROR", (Object)input), var7);
            }

            if (this.var != null) {
               this.pageContext.setAttribute(this.var, parsed, this.scope);
            } else {
               try {
                  this.pageContext.getOut().print(parsed);
               } catch (IOException var6) {
                  throw new JspTagException(var6.toString(), var6);
               }
            }

            return 6;
         }
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

   private NumberFormat createParser(Locale loc) throws JspException {
      NumberFormat parser = null;
      if (this.type != null && !"number".equalsIgnoreCase(this.type)) {
         if ("currency".equalsIgnoreCase(this.type)) {
            parser = NumberFormat.getCurrencyInstance(loc);
         } else {
            if (!"percent".equalsIgnoreCase(this.type)) {
               throw new JspException(Resources.getMessage("PARSE_NUMBER_INVALID_TYPE", (Object)this.type));
            }

            parser = NumberFormat.getPercentInstance(loc);
         }
      } else {
         parser = NumberFormat.getNumberInstance(loc);
      }

      return parser;
   }
}

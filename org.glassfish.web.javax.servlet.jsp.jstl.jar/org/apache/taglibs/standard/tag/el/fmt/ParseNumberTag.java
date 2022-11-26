package org.apache.taglibs.standard.tag.el.fmt;

import java.util.Locale;
import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.fmt.ParseNumberSupport;
import org.apache.taglibs.standard.tag.common.fmt.SetLocaleSupport;

public class ParseNumberTag extends ParseNumberSupport {
   private String value_;
   private String type_;
   private String pattern_;
   private String parseLocale_;
   private String integerOnly_;

   public ParseNumberTag() {
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

   public void setParseLocale(String parseLocale_) {
      this.parseLocale_ = parseLocale_;
   }

   public void setIntegerOnly(String integerOnly_) {
      this.integerOnly_ = integerOnly_;
      this.integerOnlySpecified = true;
   }

   private void init() {
      this.value_ = this.type_ = this.pattern_ = this.parseLocale_ = this.integerOnly_ = null;
   }

   private void evaluateExpressions() throws JspException {
      Object obj = null;
      if (this.value_ != null) {
         this.value = (String)ExpressionEvaluatorManager.evaluate("value", this.value_, String.class, this, this.pageContext);
      }

      if (this.type_ != null) {
         this.type = (String)ExpressionEvaluatorManager.evaluate("type", this.type_, String.class, this, this.pageContext);
      }

      if (this.pattern_ != null) {
         this.pattern = (String)ExpressionEvaluatorManager.evaluate("pattern", this.pattern_, String.class, this, this.pageContext);
      }

      if (this.parseLocale_ != null) {
         obj = ExpressionEvaluatorManager.evaluate("parseLocale", this.parseLocale_, Object.class, this, this.pageContext);
         if (obj != null) {
            if (obj instanceof Locale) {
               this.parseLocale = (Locale)obj;
            } else {
               String localeStr = (String)obj;
               if (!"".equals(localeStr)) {
                  this.parseLocale = SetLocaleSupport.parseLocale(localeStr);
               }
            }
         }
      }

      if (this.integerOnly_ != null) {
         obj = ExpressionEvaluatorManager.evaluate("integerOnly", this.integerOnly_, Boolean.class, this, this.pageContext);
         if (obj != null) {
            this.isIntegerOnly = (Boolean)obj;
         }
      }

   }
}

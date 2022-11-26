package com.sun.faces.ext.validator;

import java.io.Serializable;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class RegexValidator implements Validator, Serializable {
   private static final long serialVersionUID = 1961950699958181806L;
   private String regex;

   public void setPattern(String pattern) {
      this.regex = pattern;
   }

   public void validate(FacesContext context, UIComponent component, Object obj) {
      Locale locale = context.getViewRoot().getLocale();
      FacesMessage fmsg;
      if (this.regex != null && this.regex.length() != 0) {
         try {
            Pattern pattern = Pattern.compile(this.regex);
            Matcher matcher = pattern.matcher((String)obj);
            if (!matcher.matches()) {
               Object[] params = new Object[]{this.regex};
               fmsg = MojarraMessageFactory.getMessage(locale, "com.sun.faces.ext.validator.regexValidator.NOT_MATCHED", params);
               throw new ValidatorException(fmsg);
            }
         } catch (PatternSyntaxException var9) {
            fmsg = MojarraMessageFactory.getMessage(locale, "com.sun.faces.ext.validator.regexValidator.EXP_ERR", (Object)null);
            throw new ValidatorException(fmsg, var9);
         }
      } else {
         fmsg = MojarraMessageFactory.getMessage(locale, "com.sun.faces.ext.validator.regexValidator.PATTERN_NOT_SET", (Object)null);
         throw new ValidatorException(fmsg);
      }
   }
}

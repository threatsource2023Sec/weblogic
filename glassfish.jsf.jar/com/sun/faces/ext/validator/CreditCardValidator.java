package com.sun.faces.ext.validator;

import java.io.Serializable;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class CreditCardValidator implements Validator, Serializable {
   private static final long serialVersionUID = 3534760827770436010L;

   public void validate(FacesContext context, UIComponent component, Object obj) {
      Locale locale = context.getViewRoot().getLocale();
      if (obj != null) {
         FacesMessage fmsg;
         if (!(obj instanceof String)) {
            fmsg = MojarraMessageFactory.getMessage(locale, "com.sun.faces.ext.validator.creditcardValidator.NOT_STRING", null);
            throw new ValidatorException(fmsg);
         } else {
            String input = (String)obj;
            if (!input.matches("^[0-9\\ \\-]*$")) {
               fmsg = MojarraMessageFactory.getMessage(locale, "com.sun.faces.ext.validator.creditcardValidator.INVALID_CHARS", null);
               throw new ValidatorException(fmsg);
            } else if (!this.luhnCheck(this.stripNonDigit(input))) {
               fmsg = MojarraMessageFactory.getMessage(locale, "com.sun.faces.ext.validator.creditcardValidator.INVALID_NUMBER", null);
               throw new ValidatorException(fmsg);
            }
         }
      }
   }

   private String stripNonDigit(String s) {
      return s.replaceAll(" ", "").replaceAll("-", "");
   }

   private boolean luhnCheck(String number) {
      int sum = 0;
      boolean timestwo = false;

      for(int i = number.length() - 1; i >= 0; --i) {
         int n = Integer.parseInt(number.substring(i, i + 1));
         if (timestwo) {
            n *= 2;
            if (n > 9) {
               n = n % 10 + 1;
            }
         }

         sum += n;
         timestwo = !timestwo;
      }

      return sum % 10 == 0;
   }
}

package javax.faces.validator;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.faces.application.FacesMessage;
import javax.faces.component.PartialStateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class RegexValidator implements Validator, PartialStateHolder {
   private String regex;
   public static final String VALIDATOR_ID = "javax.faces.RegularExpression";
   public static final String PATTERN_NOT_SET_MESSAGE_ID = "javax.faces.validator.RegexValidator.PATTERN_NOT_SET";
   public static final String NOT_MATCHED_MESSAGE_ID = "javax.faces.validator.RegexValidator.NOT_MATCHED";
   public static final String MATCH_EXCEPTION_MESSAGE_ID = "javax.faces.validator.RegexValidator.MATCH_EXCEPTION";
   private boolean transientValue;
   private boolean initialState;

   public void setPattern(String pattern) {
      this.clearInitialState();
      this.regex = pattern;
   }

   public String getPattern() {
      return this.regex;
   }

   public void validate(FacesContext context, UIComponent component, Object value) {
      if (context == null) {
         throw new NullPointerException();
      } else if (component == null) {
         throw new NullPointerException();
      } else if (value != null) {
         Locale locale = context.getViewRoot().getLocale();
         FacesMessage fmsg;
         if (this.regex != null && this.regex.length() != 0) {
            try {
               Pattern pattern = Pattern.compile(this.regex);
               Matcher matcher = pattern.matcher((String)value);
               if (!matcher.matches()) {
                  Object[] params = new Object[]{this.regex};
                  fmsg = MessageFactory.getMessage(locale, "javax.faces.validator.RegexValidator.NOT_MATCHED", params);
                  throw new ValidatorException(fmsg);
               }
            } catch (PatternSyntaxException var9) {
               fmsg = MessageFactory.getMessage(locale, "javax.faces.validator.RegexValidator.MATCH_EXCEPTION", null);
               throw new ValidatorException(fmsg, var9);
            }
         } else {
            fmsg = MessageFactory.getMessage(locale, "javax.faces.validator.RegexValidator.PATTERN_NOT_SET", null);
            throw new ValidatorException(fmsg);
         }
      }
   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (!this.initialStateMarked()) {
         Object[] values = new Object[]{this.regex};
         return values;
      } else {
         return null;
      }
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         if (state != null) {
            Object[] values = (Object[])((Object[])state);
            this.regex = (String)values[0];
         }

      }
   }

   public boolean isTransient() {
      return this.transientValue;
   }

   public void setTransient(boolean transientValue) {
      this.transientValue = transientValue;
   }

   public void markInitialState() {
      this.initialState = true;
   }

   public boolean initialStateMarked() {
      return this.initialState;
   }

   public void clearInitialState() {
      this.initialState = false;
   }
}

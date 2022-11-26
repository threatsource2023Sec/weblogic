package javax.faces.validator;

import javax.faces.component.PartialStateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class LengthValidator implements Validator, PartialStateHolder {
   public static final String VALIDATOR_ID = "javax.faces.Length";
   public static final String MAXIMUM_MESSAGE_ID = "javax.faces.validator.LengthValidator.MAXIMUM";
   public static final String MINIMUM_MESSAGE_ID = "javax.faces.validator.LengthValidator.MINIMUM";
   private Integer maximum;
   private Integer minimum;
   private boolean transientValue;
   private boolean initialState;

   public LengthValidator() {
   }

   public LengthValidator(int maximum) {
      this.setMaximum(maximum);
   }

   public LengthValidator(int maximum, int minimum) {
      this.setMaximum(maximum);
      this.setMinimum(minimum);
   }

   public int getMaximum() {
      return this.maximum != null ? this.maximum : 0;
   }

   public void setMaximum(int maximum) {
      this.clearInitialState();
      this.maximum = maximum;
   }

   public int getMinimum() {
      return this.minimum != null ? this.minimum : 0;
   }

   public void setMinimum(int minimum) {
      this.clearInitialState();
      this.minimum = minimum;
   }

   public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
      if (context != null && component != null) {
         if (value != null) {
            String converted = stringValue(value);
            if (this.isMaximumSet() && converted.length() > this.maximum) {
               throw new ValidatorException(MessageFactory.getMessage(context, "javax.faces.validator.LengthValidator.MAXIMUM", integerToString(component, this.maximum, context), MessageFactory.getLabel(context, component)));
            }

            if (this.isMinimumSet() && converted.length() < this.minimum) {
               throw new ValidatorException(MessageFactory.getMessage(context, "javax.faces.validator.LengthValidator.MINIMUM", integerToString(component, this.minimum, context), MessageFactory.getLabel(context, component)));
            }
         }

      } else {
         throw new NullPointerException();
      }
   }

   public boolean equals(Object otherObj) {
      if (!(otherObj instanceof LengthValidator)) {
         return false;
      } else {
         LengthValidator other = (LengthValidator)otherObj;
         return this.getMaximum() == other.getMaximum() && this.getMinimum() == other.getMinimum() && this.isMinimumSet() == other.isMinimumSet() && this.isMaximumSet() == other.isMaximumSet();
      }
   }

   public int hashCode() {
      int hashCode = Integer.valueOf(this.getMinimum()).hashCode() + Integer.valueOf(this.getMaximum()).hashCode() + Boolean.valueOf(this.isMaximumSet()).hashCode() + Boolean.valueOf(this.isMinimumSet()).hashCode();
      return hashCode;
   }

   private static String stringValue(Object attributeValue) {
      if (attributeValue == null) {
         return null;
      } else {
         return attributeValue instanceof String ? (String)attributeValue : attributeValue.toString();
      }
   }

   private static String integerToString(UIComponent component, Integer toConvert, FacesContext context) {
      Converter converter = context.getApplication().createConverter("javax.faces.Number");
      return converter.getAsString(context, component, toConvert);
   }

   private boolean isMaximumSet() {
      return this.maximum != null;
   }

   private boolean isMinimumSet() {
      return this.minimum != null;
   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (!this.initialStateMarked()) {
         Object[] values = new Object[]{this.maximum, this.minimum};
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
            this.maximum = (Integer)values[0];
            this.minimum = (Integer)values[1];
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

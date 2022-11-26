package javax.faces.validator;

import javax.faces.component.PartialStateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class LongRangeValidator implements Validator, PartialStateHolder {
   public static final String VALIDATOR_ID = "javax.faces.LongRange";
   public static final String MAXIMUM_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.MAXIMUM";
   public static final String MINIMUM_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.MINIMUM";
   public static final String NOT_IN_RANGE_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.NOT_IN_RANGE";
   public static final String TYPE_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.TYPE";
   private Long maximum;
   private Long minimum;
   private boolean transientValue;
   private boolean initialState;

   public LongRangeValidator() {
   }

   public LongRangeValidator(long maximum) {
      this.setMaximum(maximum);
   }

   public LongRangeValidator(long maximum, long minimum) {
      this.setMaximum(maximum);
      this.setMinimum(minimum);
   }

   public long getMaximum() {
      return this.maximum != null ? this.maximum : 0L;
   }

   public void setMaximum(long maximum) {
      this.clearInitialState();
      this.maximum = maximum;
   }

   public long getMinimum() {
      return this.minimum != null ? this.minimum : 0L;
   }

   public void setMinimum(long minimum) {
      this.clearInitialState();
      this.minimum = minimum;
   }

   public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
      if (context != null && component != null) {
         if (value != null) {
            try {
               long converted = longValue(value);
               if (this.isMaximumSet() && converted > this.maximum) {
                  if (this.isMinimumSet()) {
                     throw new ValidatorException(MessageFactory.getMessage(context, "javax.faces.validator.LongRangeValidator.NOT_IN_RANGE", stringValue(component, this.minimum, context), stringValue(component, this.maximum, context), MessageFactory.getLabel(context, component)));
                  }

                  throw new ValidatorException(MessageFactory.getMessage(context, "javax.faces.validator.LongRangeValidator.MAXIMUM", stringValue(component, this.maximum, context), MessageFactory.getLabel(context, component)));
               }

               if (this.isMinimumSet() && converted < this.minimum) {
                  if (this.isMaximumSet()) {
                     throw new ValidatorException(MessageFactory.getMessage(context, "javax.faces.validator.LongRangeValidator.NOT_IN_RANGE", stringValue(component, this.minimum, context), stringValue(component, this.maximum, context), MessageFactory.getLabel(context, component)));
                  }

                  throw new ValidatorException(MessageFactory.getMessage(context, "javax.faces.validator.LongRangeValidator.MINIMUM", stringValue(component, this.minimum, context), MessageFactory.getLabel(context, component)));
               }
            } catch (NumberFormatException var6) {
               throw new ValidatorException(MessageFactory.getMessage(context, "javax.faces.validator.LongRangeValidator.TYPE", MessageFactory.getLabel(context, component)), var6);
            }
         }

      } else {
         throw new NullPointerException();
      }
   }

   public boolean equals(Object otherObj) {
      if (!(otherObj instanceof LongRangeValidator)) {
         return false;
      } else {
         LongRangeValidator other = (LongRangeValidator)otherObj;
         return this.getMaximum() == other.getMaximum() && this.getMinimum() == other.getMinimum() && this.isMaximumSet() == other.isMaximumSet() && this.isMinimumSet() == other.isMinimumSet();
      }
   }

   public int hashCode() {
      int hashCode = Long.valueOf(this.getMinimum()).hashCode() + Long.valueOf(this.getMaximum()).hashCode() + Boolean.valueOf(this.isMinimumSet()).hashCode() + Boolean.valueOf(this.isMaximumSet()).hashCode();
      return hashCode;
   }

   private static long longValue(Object attributeValue) throws NumberFormatException {
      return attributeValue instanceof Number ? ((Number)attributeValue).longValue() : Long.parseLong(attributeValue.toString());
   }

   private static String stringValue(UIComponent component, Long toConvert, FacesContext context) {
      Converter converter = context.getApplication().createConverter("javax.faces.Number");
      return converter.getAsString(context, component, toConvert);
   }

   private boolean isMinimumSet() {
      return this.minimum != null;
   }

   private boolean isMaximumSet() {
      return this.maximum != null;
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
            this.maximum = (Long)values[0];
            this.minimum = (Long)values[1];
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

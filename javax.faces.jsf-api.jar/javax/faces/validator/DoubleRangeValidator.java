package javax.faces.validator;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class DoubleRangeValidator implements Validator, StateHolder {
   public static final String VALIDATOR_ID = "javax.faces.DoubleRange";
   public static final String MAXIMUM_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.MAXIMUM";
   public static final String MINIMUM_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.MINIMUM";
   public static final String NOT_IN_RANGE_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.NOT_IN_RANGE";
   public static final String TYPE_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.TYPE";
   private Double maximum;
   private Double minimum;
   private boolean transientValue = false;

   public DoubleRangeValidator() {
   }

   public DoubleRangeValidator(double maximum) {
      this.setMaximum(maximum);
   }

   public DoubleRangeValidator(double maximum, double minimum) {
      this.setMaximum(maximum);
      this.setMinimum(minimum);
   }

   public double getMaximum() {
      return this.maximum != null ? this.maximum : Double.MAX_VALUE;
   }

   public void setMaximum(double maximum) {
      this.maximum = maximum;
   }

   public double getMinimum() {
      return this.minimum != null ? this.minimum : Double.MIN_VALUE;
   }

   public void setMinimum(double minimum) {
      this.minimum = minimum;
   }

   public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
      if (context != null && component != null) {
         if (value != null) {
            try {
               double converted = doubleValue(value);
               if (this.isMaximumSet() && converted > this.maximum) {
                  if (this.isMinimumSet()) {
                     throw new ValidatorException(MessageFactory.getMessage(context, "javax.faces.validator.DoubleRangeValidator.NOT_IN_RANGE", stringValue(component, this.minimum, context), stringValue(component, this.maximum, context), MessageFactory.getLabel(context, component)));
                  }

                  throw new ValidatorException(MessageFactory.getMessage(context, "javax.faces.validator.DoubleRangeValidator.MAXIMUM", stringValue(component, this.maximum, context), MessageFactory.getLabel(context, component)));
               }

               if (this.isMinimumSet() && converted < this.minimum) {
                  if (this.isMaximumSet()) {
                     throw new ValidatorException(MessageFactory.getMessage(context, "javax.faces.validator.DoubleRangeValidator.NOT_IN_RANGE", stringValue(component, this.minimum, context), stringValue(component, this.maximum, context), MessageFactory.getLabel(context, component)));
                  }

                  throw new ValidatorException(MessageFactory.getMessage(context, "javax.faces.validator.DoubleRangeValidator.MINIMUM", stringValue(component, this.minimum, context), MessageFactory.getLabel(context, component)));
               }
            } catch (NumberFormatException var6) {
               throw new ValidatorException(MessageFactory.getMessage(context, "javax.faces.validator.DoubleRangeValidator.TYPE", MessageFactory.getLabel(context, component)), var6);
            }
         }

      } else {
         throw new NullPointerException();
      }
   }

   public boolean equals(Object otherObj) {
      if (!(otherObj instanceof DoubleRangeValidator)) {
         return false;
      } else {
         DoubleRangeValidator other = (DoubleRangeValidator)otherObj;
         return this.getMaximum() == other.getMaximum() && this.getMinimum() == other.getMinimum() && this.isMaximumSet() == other.isMaximumSet() && this.isMinimumSet() == other.isMinimumSet();
      }
   }

   public int hashCode() {
      int hashCode = Double.valueOf(this.getMinimum()).hashCode() + Double.valueOf(this.getMaximum()).hashCode() + Boolean.valueOf(this.isMinimumSet()).hashCode() + Boolean.valueOf(this.isMaximumSet()).hashCode();
      return hashCode;
   }

   private static double doubleValue(Object attributeValue) throws NumberFormatException {
      return attributeValue instanceof Number ? ((Number)attributeValue).doubleValue() : Double.parseDouble(attributeValue.toString());
   }

   private static String stringValue(UIComponent component, Double toConvert, FacesContext context) {
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
      Object[] values = new Object[]{this.maximum, this.minimum};
      return values;
   }

   public void restoreState(FacesContext context, Object state) {
      Object[] values = (Object[])((Object[])state);
      this.maximum = (Double)values[0];
      this.minimum = (Double)values[1];
   }

   public boolean isTransient() {
      return this.transientValue;
   }

   public void setTransient(boolean transientValue) {
      this.transientValue = transientValue;
   }
}

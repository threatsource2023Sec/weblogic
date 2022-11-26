package javax.faces.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class IntegerConverter implements Converter {
   public static final String CONVERTER_ID = "javax.faces.Integer";
   public static final String INTEGER_ID = "javax.faces.converter.IntegerConverter.INTEGER";
   public static final String STRING_ID = "javax.faces.converter.STRING";

   public Object getAsObject(FacesContext context, UIComponent component, String value) {
      if (context != null && component != null) {
         if (value == null) {
            return null;
         } else {
            value = value.trim();
            if (value.length() < 1) {
               return null;
            } else {
               try {
                  return Integer.valueOf(value);
               } catch (NumberFormatException var5) {
                  throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.IntegerConverter.INTEGER", value, "9346", MessageFactory.getLabel(context, component)), var5);
               } catch (Exception var6) {
                  throw new ConverterException(var6);
               }
            }
         }
      } else {
         throw new NullPointerException();
      }
   }

   public String getAsString(FacesContext context, UIComponent component, Object value) {
      if (context != null && component != null) {
         if (value == null) {
            return "";
         } else if (value instanceof String) {
            return (String)value;
         } else {
            try {
               return Integer.toString(((Number)value).intValue());
            } catch (Exception var5) {
               throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.STRING", value, MessageFactory.getLabel(context, component)), var5);
            }
         }
      } else {
         throw new NullPointerException();
      }
   }
}

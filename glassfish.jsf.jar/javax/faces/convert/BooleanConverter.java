package javax.faces.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class BooleanConverter implements Converter {
   public static final String CONVERTER_ID = "javax.faces.Boolean";
   public static final String BOOLEAN_ID = "javax.faces.converter.BooleanConverter.BOOLEAN";
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
                  return Boolean.valueOf(value);
               } catch (Exception var5) {
                  throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.BooleanConverter.BOOLEAN", value, MessageFactory.getLabel(context, component)), var5);
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
         } else {
            try {
               return value.toString();
            } catch (Exception var5) {
               throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.STRING", value, MessageFactory.getLabel(context, component)), var5);
            }
         }
      } else {
         throw new NullPointerException();
      }
   }
}

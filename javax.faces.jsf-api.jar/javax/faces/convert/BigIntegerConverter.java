package javax.faces.convert;

import java.math.BigInteger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class BigIntegerConverter implements Converter {
   public static final String CONVERTER_ID = "javax.faces.BigInteger";
   public static final String BIGINTEGER_ID = "javax.faces.converter.BigIntegerConverter.BIGINTEGER";
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
                  return new BigInteger(value);
               } catch (NumberFormatException var5) {
                  throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.BigIntegerConverter.BIGINTEGER", value, "9876", MessageFactory.getLabel(context, component)), var5);
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

package javax.faces.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class CharacterConverter implements Converter {
   public static final String CONVERTER_ID = "javax.faces.Character";
   public static final String CHARACTER_ID = "javax.faces.converter.CharacterConverter.CHARACTER";
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
                  return value.charAt(0);
               } catch (Exception var5) {
                  throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.CharacterConverter.CHARACTER", value, MessageFactory.getLabel(context, component)), var5);
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
               throw new ConverterException(var5);
            }
         }
      } else {
         throw new NullPointerException();
      }
   }
}

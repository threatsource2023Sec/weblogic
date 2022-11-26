package javax.faces.convert;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class EnumConverter implements Converter, StateHolder {
   public static final String CONVERTER_ID = "javax.faces.Enum";
   public static final String ENUM_ID = "javax.faces.converter.EnumConverter.ENUM";
   public static final String ENUM_NO_CLASS_ID = "javax.faces.converter.EnumConverter.ENUM_NO_CLASS";
   private Class targetClass;
   private boolean isTransient = false;

   public EnumConverter() {
   }

   public EnumConverter(Class targetClass) {
      this.targetClass = targetClass;
   }

   public Object getAsObject(FacesContext context, UIComponent component, String value) {
      if (context != null && component != null) {
         if (this.targetClass == null) {
            throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.EnumConverter.ENUM_NO_CLASS", value, MessageFactory.getLabel(context, component)));
         } else if (value == null) {
            return null;
         } else {
            value = value.trim();
            if (value.length() < 1) {
               return null;
            } else {
               try {
                  return Enum.valueOf(this.targetClass, value);
               } catch (IllegalArgumentException var5) {
                  throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.EnumConverter.ENUM", value, value, MessageFactory.getLabel(context, component)), var5);
               }
            }
         }
      } else {
         throw new NullPointerException();
      }
   }

   public String getAsString(FacesContext context, UIComponent component, Object value) {
      if (context != null && component != null) {
         if (this.targetClass == null) {
            throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.EnumConverter.ENUM_NO_CLASS", value, MessageFactory.getLabel(context, component)));
         } else if (value == null) {
            return null;
         } else if (this.targetClass.isInstance(value)) {
            return value.toString();
         } else {
            throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.EnumConverter.ENUM", value, value, MessageFactory.getLabel(context, component)));
         }
      } else {
         throw new NullPointerException();
      }
   }

   public void restoreState(FacesContext facesContext, Object object) {
      this.targetClass = (Class)object;
   }

   public Object saveState(FacesContext facesContext) {
      return this.targetClass;
   }

   public void setTransient(boolean b) {
      this.isTransient = b;
   }

   public boolean isTransient() {
      return this.isTransient;
   }
}

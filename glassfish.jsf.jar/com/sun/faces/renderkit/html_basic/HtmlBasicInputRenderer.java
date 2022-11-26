package com.sun.faces.renderkit.html_basic;

import com.sun.faces.util.MessageFactory;
import com.sun.faces.util.RequestStateManager;
import java.util.Map;
import java.util.logging.Level;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public abstract class HtmlBasicInputRenderer extends HtmlBasicRenderer {
   private boolean hasStringConverter = false;
   private boolean hasStringConverterSet = false;

   public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws ConverterException {
      String newValue = (String)submittedValue;
      ValueExpression valueExpression = component.getValueExpression("value");
      Converter converter = null;
      if (component instanceof ValueHolder) {
         converter = ((ValueHolder)component).getConverter();
      }

      if (null == converter && null != valueExpression) {
         Class converterType = valueExpression.getType(context.getELContext());
         if (converterType == null || converterType == Object.class) {
            if (logger.isLoggable(Level.FINE)) {
               logger.log(Level.FINE, "No conversion necessary for value {0} of component {1}", new Object[]{submittedValue, component.getId()});
            }

            return newValue;
         }

         if (converterType == String.class && !this.hasStringConverter(context)) {
            if (logger.isLoggable(Level.FINE)) {
               logger.log(Level.FINE, "No conversion necessary for value {0} of component {1}", new Object[]{submittedValue, component.getId()});
            }

            return newValue;
         }

         try {
            Application application = context.getApplication();
            converter = application.createConverter(converterType);
            if (logger.isLoggable(Level.FINE)) {
               logger.log(Level.FINE, "Created converter ({0}) for type {1} for component {2}.", new Object[]{converter.getClass().getName(), converterType.getClass().getName(), component.getId()});
            }
         } catch (Exception var9) {
            if (logger.isLoggable(Level.SEVERE)) {
               logger.log(Level.SEVERE, "Could not instantiate converter for type {0}: {1}", new Object[]{converterType, var9.toString()});
               logger.log(Level.SEVERE, "", var9);
            }

            return null;
         }
      } else if (converter == null) {
         if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "No conversion necessary for value {0} of component {1}", new Object[]{submittedValue, component.getId()});
            logger.fine(" since there is no explicitly registered converter and the component value is not bound to a model property");
         }

         return newValue;
      }

      if (converter != null) {
         RequestStateManager.set(context, "com.sun.faces.ComponentForValue", component);
         return converter.getAsObject(context, component, newValue);
      } else {
         Object[] params = new Object[]{newValue, "null Converter"};
         throw new ConverterException(MessageFactory.getMessage(context, "com.sun.faces.TYPECONVERSION_ERROR", params));
      }
   }

   public void setSubmittedValue(UIComponent component, Object value) {
      if (component instanceof UIInput) {
         ((UIInput)component).setSubmittedValue(value);
         if (logger.isLoggable(Level.FINE)) {
            logger.fine("Set submitted value " + value + " on component ");
         }
      }

   }

   protected Object getValue(UIComponent component) {
      if (component instanceof ValueHolder) {
         Object value = ((ValueHolder)component).getValue();
         if (logger.isLoggable(Level.FINE)) {
            logger.fine("component.getValue() returned " + value);
         }

         return value;
      } else {
         return null;
      }
   }

   protected static Map getNonOnChangeBehaviors(UIComponent component) {
      return getPassThruBehaviors(component, "change", "valueChange");
   }

   protected static Map getNonOnClickSelectBehaviors(UIComponent component) {
      return getPassThruBehaviors(component, "click", "valueChange");
   }

   private boolean hasStringConverter(FacesContext context) {
      if (!this.hasStringConverterSet) {
         this.hasStringConverter = null != context.getApplication().createConverter(String.class);
         this.hasStringConverterSet = true;
      }

      return this.hasStringConverter;
   }
}

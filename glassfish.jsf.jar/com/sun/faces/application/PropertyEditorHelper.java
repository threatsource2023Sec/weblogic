package com.sun.faces.application;

import com.sun.faces.util.MessageFactory;
import com.sun.faces.util.RequestStateManager;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class PropertyEditorHelper {
   private Application app;

   public PropertyEditorHelper(Application app) {
      this.app = app;
   }

   public Object convertToObject(Class targetClass, String textValue) {
      UIComponent component = this.getComponent();
      Converter converter = this.app.createConverter(targetClass);
      if (null == converter) {
         FacesException e = new FacesException("Cannot create Converter to convert value " + textValue + " to instance of target class " + targetClass.getName() + '.');
         throw e;
      } else {
         FacesContext currentInstance = FacesContext.getCurrentInstance();

         try {
            return converter.getAsObject(currentInstance, component, textValue);
         } catch (ConverterException var7) {
            this.addConversionErrorMessage(currentInstance, component, var7);
            return null;
         }
      }
   }

   public String convertToString(Class targetClass, Object value) {
      UIComponent component = this.getComponent();
      Converter converter = this.app.createConverter(targetClass);
      if (null == converter) {
         throw new FacesException("Cannot create Converter to convert " + targetClass.getName() + " value " + value + " to string.");
      } else {
         FacesContext currentInstance = FacesContext.getCurrentInstance();

         try {
            return converter.getAsString(currentInstance, component, value);
         } catch (ConverterException var7) {
            this.addConversionErrorMessage(currentInstance, component, var7);
            return null;
         }
      }
   }

   protected UIComponent getComponent() {
      FacesContext context = FacesContext.getCurrentInstance();
      return context != null ? (UIComponent)RequestStateManager.get(context, "com.sun.faces.ComponentForValue") : null;
   }

   protected void addConversionErrorMessage(FacesContext context, UIComponent component, ConverterException ce) {
      String converterMessageString = null;
      if (component instanceof UIInput) {
         UIInput input = (UIInput)component;
         converterMessageString = input.getConverterMessage();
         input.setValid(false);
      }

      FacesMessage message;
      if (null != converterMessageString) {
         message = new FacesMessage(FacesMessage.SEVERITY_ERROR, converterMessageString, converterMessageString);
      } else {
         message = ce.getFacesMessage();
         if (message == null) {
            message = MessageFactory.getMessage(context, "javax.faces.component.UIInput.CONVERSION");
            if (message.getDetail() == null) {
               message.setDetail(ce.getMessage());
            }
         }
      }

      context.addMessage(component != null ? component.getClientId(context) : null, message);
   }
}

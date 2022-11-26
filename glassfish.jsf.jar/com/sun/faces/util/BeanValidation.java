package com.sun.faces.util;

import java.util.Locale;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorContext;
import javax.validation.ValidatorFactory;

public class BeanValidation {
   public static Validator getBeanValidator(FacesContext context) {
      ValidatorFactory validatorFactory = getValidatorFactory(context);
      ValidatorContext validatorContext = validatorFactory.usingContext();
      MessageInterpolator jsfMessageInterpolator = new JsfAwareMessageInterpolator(context, validatorFactory.getMessageInterpolator());
      validatorContext.messageInterpolator(jsfMessageInterpolator);
      return validatorContext.getValidator();
   }

   public static ValidatorFactory getValidatorFactory(FacesContext context) {
      ValidatorFactory validatorFactory = null;
      Object cachedObject = context.getExternalContext().getApplicationMap().get("javax.faces.validator.beanValidator.ValidatorFactory");
      if (cachedObject instanceof ValidatorFactory) {
         validatorFactory = (ValidatorFactory)cachedObject;
      } else {
         try {
            validatorFactory = Validation.buildDefaultValidatorFactory();
         } catch (ValidationException var4) {
            throw new FacesException("Could not build a default Bean Validator factory", var4);
         }

         context.getExternalContext().getApplicationMap().put("javax.faces.validator.beanValidator.ValidatorFactory", validatorFactory);
      }

      return validatorFactory;
   }

   private static class JsfAwareMessageInterpolator implements MessageInterpolator {
      private final FacesContext context;
      private final MessageInterpolator delegate;

      public JsfAwareMessageInterpolator(FacesContext context, MessageInterpolator delegate) {
         this.context = context;
         this.delegate = delegate;
      }

      public String interpolate(String message, MessageInterpolator.Context context) {
         Locale locale = this.context.getViewRoot().getLocale();
         if (locale == null) {
            locale = Locale.getDefault();
         }

         return this.delegate.interpolate(message, context, locale);
      }

      public String interpolate(String message, MessageInterpolator.Context context, Locale locale) {
         return this.delegate.interpolate(message, context, locale);
      }
   }
}

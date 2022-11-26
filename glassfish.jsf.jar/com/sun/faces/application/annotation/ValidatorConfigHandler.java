package com.sun.faces.application.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;

public class ValidatorConfigHandler implements ConfigAnnotationHandler {
   private static final Collection HANDLES;
   private Map validators;

   public Collection getHandledAnnotations() {
      return HANDLES;
   }

   public void collect(Class target, Annotation annotation) {
      if (this.validators == null) {
         this.validators = new HashMap();
      }

      FacesValidator validatorAnnotation = (FacesValidator)annotation;
      String value = ((FacesValidator)annotation).value();
      if (null == value || 0 == value.length()) {
         value = target.getSimpleName();
         value = Character.toLowerCase(value.charAt(0)) + value.substring(1);
      }

      ValidatorInfo info = new ValidatorInfo(value, validatorAnnotation.isDefault());
      this.validators.put(info, target.getName());
   }

   public void push(FacesContext ctx) {
      if (this.validators != null) {
         Application app = ctx.getApplication();
         Iterator var3 = this.validators.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            app.addValidator(((ValidatorInfo)entry.getKey()).validatorId, (String)entry.getValue());
            if (((ValidatorInfo)entry.getKey()).isDefault) {
               app.addDefaultValidatorId(((ValidatorInfo)entry.getKey()).validatorId);
            }
         }
      }

   }

   static {
      Collection handles = new ArrayList(1);
      handles.add(FacesValidator.class);
      HANDLES = Collections.unmodifiableCollection(handles);
   }

   private static class ValidatorInfo {
      final String validatorId;
      final boolean isDefault;

      ValidatorInfo(String validatorId, boolean isDefault) {
         this.validatorId = validatorId;
         this.isDefault = isDefault;
      }
   }
}

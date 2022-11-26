package com.sun.faces.application.annotation;

import com.sun.faces.util.FacesLogger;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

public class ConverterConfigHandler implements ConfigAnnotationHandler {
   private static final Logger LOGGER;
   private static final Collection HANDLES;
   private Map converters;

   public Collection getHandledAnnotations() {
      return HANDLES;
   }

   public void collect(Class target, Annotation annotation) {
      if (this.converters == null) {
         this.converters = new HashMap();
      }

      FacesConverter converterAnnotation = (FacesConverter)annotation;
      if (converterAnnotation.value().length() > 0 && converterAnnotation.forClass() != null && converterAnnotation.forClass() != Object.class && LOGGER.isLoggable(Level.WARNING)) {
         LOGGER.log(Level.WARNING, "@FacesConverter is using both value and forClass, only value will be applied.");
      }

      Object key;
      if (0 == converterAnnotation.value().length()) {
         key = converterAnnotation.forClass();
      } else {
         key = converterAnnotation.value();
      }

      this.converters.put(key, target.getName());
   }

   public void push(FacesContext ctx) {
      if (this.converters != null) {
         Application app = ctx.getApplication();
         Iterator var3 = this.converters.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            if (entry.getKey() instanceof Class) {
               app.addConverter((Class)entry.getKey(), (String)entry.getValue());
            } else {
               app.addConverter((String)entry.getKey(), (String)entry.getValue());
            }
         }
      }

   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
      Collection handles = new ArrayList(1);
      handles.add(FacesConverter.class);
      HANDLES = Collections.unmodifiableCollection(handles);
   }
}

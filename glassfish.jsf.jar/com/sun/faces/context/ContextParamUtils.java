package com.sun.faces.context;

import javax.servlet.ServletContext;

public class ContextParamUtils {
   private ContextParamUtils() {
   }

   public static Object getValue(ServletContext servletContext, ContextParam contextParam) {
      Object result = contextParam.getDefaultValue();
      if (servletContext.getInitParameter(contextParam.getName()) != null) {
         if (contextParam.getType().equals(Boolean.class)) {
            result = Boolean.valueOf(servletContext.getInitParameter(contextParam.getName()));
         } else if (contextParam.getType().equals(Integer.class)) {
            result = Integer.valueOf(servletContext.getInitParameter(contextParam.getName()));
         }
      }

      return result;
   }

   public static Object getValue(ServletContext servletContext, ContextParam contextParam, Class clazz) {
      return clazz.cast(getValue(servletContext, contextParam));
   }
}

package com.sun.faces.application.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class JndiHandler implements RuntimeAnnotationHandler {
   private static final Logger LOGGER = Logger.getLogger(JndiHandler.class.getName());
   protected static final String JAVA_COMP_ENV = "java:comp/env/";

   public Object lookup(FacesContext facesContext, String name) {
      Object object = null;

      try {
         InitialContext context = new InitialContext();
         object = context.lookup(name);
      } catch (NamingException var5) {
         if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, "Unable to lookup: " + name, var5);
         }

         if (facesContext.isProjectStage(ProjectStage.Development)) {
            facesContext.addMessage((String)null, new FacesMessage("Unable to lookup: " + name, "Unable to lookup: " + name));
         }
      }

      return object;
   }

   public void setField(FacesContext facesContext, Field field, Object instance, Object value) {
      synchronized(instance) {
         try {
            boolean fieldAccessible = true;
            if (!field.isAccessible()) {
               field.setAccessible(true);
               fieldAccessible = false;
            }

            field.set(instance, value);
            if (!fieldAccessible) {
               field.setAccessible(false);
            }
         } catch (IllegalAccessException | IllegalArgumentException var8) {
            if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, "Unable to set field: " + field.getName(), var8);
            }

            if (facesContext.isProjectStage(ProjectStage.Development)) {
               facesContext.addMessage((String)null, new FacesMessage("Unable to set field: " + field.getName(), "Unable to set field: " + field.getName()));
            }
         }

      }
   }

   protected void invokeMethod(FacesContext facesContext, Method method, Object instance, Object value) {
      synchronized(instance) {
         try {
            boolean accessible = method.isAccessible();
            method.setAccessible(false);
            method.invoke(instance, value);
            method.setAccessible(accessible);
         } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException var8) {
            if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, "Unable to call method: " + method.getName(), var8);
            }

            if (facesContext.isProjectStage(ProjectStage.Development)) {
               facesContext.addMessage((String)null, new FacesMessage("Unable to call method: " + method.getName(), "Unable to call method: " + method.getName()));
            }
         }

      }
   }
}

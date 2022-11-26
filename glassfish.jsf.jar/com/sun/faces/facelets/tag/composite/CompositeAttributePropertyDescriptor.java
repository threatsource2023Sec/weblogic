package com.sun.faces.facelets.tag.composite;

import com.sun.faces.facelets.util.ReflectionUtil;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;

public class CompositeAttributePropertyDescriptor extends PropertyDescriptor {
   public CompositeAttributePropertyDescriptor(String propertyName, Method readMethod, Method writeMethod) throws IntrospectionException {
      super(propertyName, readMethod, writeMethod);
   }

   public Object getValue(String attributeName) {
      Object result = super.getValue(attributeName);
      if ("type".equals(attributeName) && null != result && !(result instanceof Class)) {
         FacesContext context = FacesContext.getCurrentInstance();
         ELContext elContext = context.getELContext();
         String classStr = (String)((ValueExpression)result).getValue(elContext);
         if (null != classStr) {
            try {
               result = ReflectionUtil.forName(classStr);
               this.setValue(attributeName, result);
            } catch (ClassNotFoundException var10) {
               classStr = "java.lang." + classStr;
               boolean throwException = false;

               try {
                  result = ReflectionUtil.forName(classStr);
                  this.setValue(attributeName, result);
               } catch (ClassNotFoundException var9) {
                  throwException = true;
               }

               if (throwException) {
                  String message = "Unable to obtain class for " + classStr;
                  throw new FacesException(message, var10);
               }
            }
         }
      }

      return result;
   }
}

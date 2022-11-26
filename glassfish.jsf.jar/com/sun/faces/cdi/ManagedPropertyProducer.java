package com.sun.faces.cdi;

import java.lang.reflect.Type;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.annotation.ManagedProperty;
import javax.faces.context.FacesContext;

public class ManagedPropertyProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;
   private Class expectedClass;

   public ManagedPropertyProducer(Type type, BeanManager beanManager) {
      super.beanClass(ManagedPropertyProducer.class).types(type).qualifiers(new ManagedPropertyLiteral()).addToId(type).create((creationalContext) -> {
         String expression = ((ManagedProperty)CdiUtils.getCurrentInjectionPoint(beanManager, creationalContext).getAnnotated().getAnnotation(ManagedProperty.class)).value();
         return evaluateExpressionGet(beanManager, expression, this.expectedClass);
      });
      this.expectedClass = getExpectedClass(type);
   }

   private static Class getExpectedClass(Type type) {
      if (type instanceof Class) {
         return (Class)type;
      } else {
         return type instanceof ParameterizedTypeImpl ? getExpectedClass(((ParameterizedTypeImpl)type).getRawType()) : Object.class;
      }
   }

   public static Object evaluateExpressionGet(BeanManager beanManager, String expression, Class expectedClass) {
      if (expression == null) {
         return null;
      } else {
         FacesContext context = (FacesContext)CdiUtils.getBeanReference(beanManager, FacesContext.class);
         return context.getApplication().evaluateExpressionGet(context, expression, expectedClass);
      }
   }
}

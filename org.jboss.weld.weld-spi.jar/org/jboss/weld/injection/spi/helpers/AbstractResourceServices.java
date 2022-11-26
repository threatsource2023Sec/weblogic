package org.jboss.weld.injection.spi.helpers;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.annotation.Resource;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.naming.Context;
import javax.naming.NamingException;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.injection.spi.ResourceInjectionServices;
import org.jboss.weld.injection.spi.ResourceReference;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;

public abstract class AbstractResourceServices implements Service, ResourceInjectionServices {
   private static final String RESOURCE_LOOKUP_PREFIX = "java:comp/env";
   private static final int GET_PREFIX_LENGTH = "get".length();

   public Object resolveResource(InjectionPoint injectionPoint) {
      if (this.getResourceAnnotation(injectionPoint) == null) {
         throw new IllegalArgumentException("No @Resource annotation found on injection point " + injectionPoint);
      } else if (injectionPoint.getMember() instanceof Method && ((Method)injectionPoint.getMember()).getParameterTypes().length != 1) {
         throw new IllegalArgumentException("Injection point represents a method which doesn't follow JavaBean conventions (must have exactly one parameter) " + injectionPoint);
      } else {
         String name = this.getResourceName(injectionPoint);

         try {
            return this.getContext().lookup(name);
         } catch (NamingException var4) {
            return this.handleNamingException(var4, name);
         }
      }
   }

   public Object resolveResource(String jndiName, String mappedName) {
      String name = this.getResourceName(jndiName, mappedName);

      try {
         return this.getContext().lookup(name);
      } catch (NamingException var5) {
         return this.handleNamingException(var5, name);
      }
   }

   private Object handleNamingException(NamingException e, String name) {
      throw new RuntimeException("Error looking up " + name + " in JNDI", e);
   }

   protected String getResourceName(String jndiName, String mappedName) {
      if (mappedName != null) {
         return mappedName;
      } else if (jndiName != null) {
         return jndiName;
      } else {
         throw new IllegalArgumentException("Both jndiName and mappedName are null");
      }
   }

   protected abstract Context getContext();

   protected String getResourceName(InjectionPoint injectionPoint) {
      Resource resource = this.getResourceAnnotation(injectionPoint);
      String mappedName = resource.mappedName();
      if (!mappedName.equals("")) {
         return mappedName;
      } else {
         String name = resource.name();
         if (!name.equals("")) {
            return "java:comp/env/" + name;
         } else {
            String propertyName;
            if (injectionPoint.getMember() instanceof Field) {
               propertyName = injectionPoint.getMember().getName();
            } else {
               if (!(injectionPoint.getMember() instanceof Method)) {
                  throw new AssertionError("Unable to inject into " + injectionPoint);
               }

               propertyName = getPropertyName((Method)injectionPoint.getMember());
               if (propertyName == null) {
                  throw new IllegalArgumentException("Injection point represents a method which doesn't follow JavaBean conventions (unable to determine property name) " + injectionPoint);
               }
            }

            String className = injectionPoint.getMember().getDeclaringClass().getName();
            return "java:comp/env/" + className + "/" + propertyName;
         }
      }
   }

   public static String getPropertyName(Method method) {
      String methodName = method.getName();
      if (methodName.matches("^(get).*") && method.getParameterTypes().length == 0) {
         return Introspector.decapitalize(methodName.substring(GET_PREFIX_LENGTH));
      } else {
         return methodName.matches("^(is).*") && method.getParameterTypes().length == 0 ? Introspector.decapitalize(methodName.substring(2)) : null;
      }
   }

   public ResourceReferenceFactory registerResourceInjectionPoint(final InjectionPoint injectionPoint) {
      return new ResourceReferenceFactory() {
         public ResourceReference createResource() {
            return new SimpleResourceReference(AbstractResourceServices.this.resolveResource(injectionPoint));
         }
      };
   }

   public ResourceReferenceFactory registerResourceInjectionPoint(final String jndiName, final String mappedName) {
      return new ResourceReferenceFactory() {
         public ResourceReference createResource() {
            return new SimpleResourceReference(AbstractResourceServices.this.resolveResource(jndiName, mappedName));
         }
      };
   }

   protected Resource getResourceAnnotation(InjectionPoint injectionPoint) {
      Annotated annotated = injectionPoint.getAnnotated();
      if (annotated instanceof AnnotatedParameter) {
         annotated = ((AnnotatedParameter)annotated).getDeclaringCallable();
      }

      return (Resource)((Annotated)annotated).getAnnotation(Resource.class);
   }

   public void cleanup() {
   }
}

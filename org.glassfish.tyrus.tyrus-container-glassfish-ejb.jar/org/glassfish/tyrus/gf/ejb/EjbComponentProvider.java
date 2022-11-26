package org.glassfish.tyrus.gf.ejb;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.glassfish.tyrus.core.ComponentProvider;

public class EjbComponentProvider extends ComponentProvider {
   private static final Logger LOGGER = Logger.getLogger(EjbComponentProvider.class.getName());

   public Object create(Class c) {
      String name = this.getName(c);
      Object result = null;
      if (name == null) {
         return null;
      } else {
         try {
            InitialContext ic = new InitialContext();
            result = this.lookup(ic, c, name);
         } catch (NamingException var6) {
            String message = "An instance of EJB class " + c.getName() + " could not be looked up using simple form name or the fully-qualified form name.";
            LOGGER.log(Level.SEVERE, message, var6);
         }

         return result;
      }
   }

   public boolean isApplicable(Class c) {
      return c.isAnnotationPresent(Singleton.class) || c.isAnnotationPresent(Stateful.class) || c.isAnnotationPresent(Stateless.class);
   }

   public boolean destroy(Object o) {
      return false;
   }

   public Method getInvocableMethod(Method method) {
      Class declaringClass = method.getDeclaringClass();
      List interfaces = new LinkedList();
      if (declaringClass.isAnnotationPresent(Remote.class)) {
         interfaces.addAll(Arrays.asList(((Remote)declaringClass.getAnnotation(Remote.class)).value()));
      }

      if (declaringClass.isAnnotationPresent(Local.class)) {
         interfaces.addAll(Arrays.asList(((Local)declaringClass.getAnnotation(Local.class)).value()));
      }

      Class[] var4 = declaringClass.getInterfaces();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Class i = var4[var6];
         if (i.isAnnotationPresent(Remote.class) || i.isAnnotationPresent(Local.class)) {
            interfaces.add(i);
         }
      }

      Iterator var9 = interfaces.iterator();

      while(var9.hasNext()) {
         Class iface = (Class)var9.next();

         try {
            Method interfaceMethod = iface.getDeclaredMethod(method.getName(), method.getParameterTypes());
            if (interfaceMethod != null) {
               return interfaceMethod;
            }
         } catch (Exception var8) {
            LOGGER.log(Level.WARNING, var8.getMessage(), var8);
         }
      }

      return method;
   }

   private String getName(Class c) {
      String name;
      if (c.isAnnotationPresent(Singleton.class)) {
         name = ((Singleton)c.getAnnotation(Singleton.class)).name();
      } else if (c.isAnnotationPresent(Stateful.class)) {
         name = ((Stateful)c.getAnnotation(Stateful.class)).name();
      } else {
         if (!c.isAnnotationPresent(Stateless.class)) {
            return null;
         }

         name = ((Stateless)c.getAnnotation(Stateless.class)).name();
      }

      if (name == null || name.length() == 0) {
         name = c.getSimpleName();
      }

      return name;
   }

   private Object lookup(InitialContext ic, Class c, String name) throws NamingException {
      try {
         return this.lookupSimpleForm(ic, name);
      } catch (NamingException var5) {
         LOGGER.log(Level.WARNING, "An instance of EJB class " + c.getName() + " could not be looked up using simple form name. Attempting to look up using the fully-qualified form name.", var5);
         return this.lookupFullyQualfiedForm(ic, c, name);
      }
   }

   private Object lookupSimpleForm(InitialContext ic, String name) throws NamingException {
      String jndiName = "java:module/" + name;
      return ic.lookup(jndiName);
   }

   private Object lookupFullyQualfiedForm(InitialContext ic, Class c, String name) throws NamingException {
      String jndiName = "java:module/" + name + "!" + c.getName();
      return ic.lookup(jndiName);
   }
}

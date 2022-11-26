package jnr.ffi.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public final class AnnotationProxy implements Annotation, InvocationHandler {
   private static final int MEMBER_NAME_MULTIPLICATOR = 127;
   private final Class annotationType;
   private final Map properties = new LinkedHashMap();
   private final Annotation proxedAnnotation;

   public static AnnotationProxy newProxy(Class annotationType) {
      if (annotationType == null) {
         throw new IllegalArgumentException("Parameter 'annotationType' must be not null");
      } else {
         return new AnnotationProxy(annotationType);
      }
   }

   private static AnnotationProxy getAnnotationProxy(Object obj) {
      if (Proxy.isProxyClass(obj.getClass())) {
         InvocationHandler handler = Proxy.getInvocationHandler(obj);
         if (handler instanceof AnnotationProxy) {
            return (AnnotationProxy)handler;
         }
      }

      return null;
   }

   private static Method[] getDeclaredMethods(final Class annotationType) {
      return (Method[])AccessController.doPrivileged(new PrivilegedAction() {
         public Method[] run() {
            Method[] declaredMethods = annotationType.getDeclaredMethods();
            AccessibleObject.setAccessible(declaredMethods, true);
            return declaredMethods;
         }
      });
   }

   private AnnotationProxy(Class annotationType) {
      this.annotationType = annotationType;
      Method[] var2 = getDeclaredMethods(annotationType);
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method method = var2[var4];
         String propertyName = method.getName();
         Class returnType = method.getReturnType();
         Object defaultValue = method.getDefaultValue();
         AnnotationProperty property = new AnnotationProperty(propertyName, returnType);
         property.setValue(defaultValue);
         this.properties.put(propertyName, property);
      }

      this.proxedAnnotation = (Annotation)annotationType.cast(Proxy.newProxyInstance(annotationType.getClassLoader(), new Class[]{annotationType}, this));
   }

   public void setProperty(String name, Object value) {
      if (name == null) {
         throw new IllegalArgumentException("Parameter 'name' must be not null");
      } else if (value == null) {
         throw new IllegalArgumentException("Parameter 'value' must be not null");
      } else if (!this.properties.containsKey(name)) {
         throw new IllegalArgumentException("Annotation '" + this.annotationType.getName() + "' does not contain a property named '" + name + "'");
      } else {
         ((AnnotationProperty)this.properties.get(name)).setValue(value);
      }
   }

   public Object getProperty(String name) {
      if (name == null) {
         throw new IllegalArgumentException("Parameter 'name' must be not null");
      } else {
         return ((AnnotationProperty)this.properties.get(name)).getValue();
      }
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      String name = method.getName();
      return this.properties.containsKey(name) ? ((AnnotationProperty)this.properties.get(name)).getValue() : method.invoke(this, args);
   }

   public Class annotationType() {
      return this.annotationType;
   }

   public Annotation getProxedAnnotation() {
      return this.proxedAnnotation;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (!this.annotationType.isInstance(obj)) {
         return false;
      } else {
         Method[] var2 = getDeclaredMethods(this.annotationType());
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method method = var2[var4];
            String propertyName = method.getName();
            if (!this.properties.containsKey(propertyName)) {
               return false;
            }

            AnnotationProperty expected = (AnnotationProperty)this.properties.get(propertyName);
            AnnotationProperty actual = new AnnotationProperty(propertyName, method.getReturnType());
            AnnotationProxy proxy = getAnnotationProxy(obj);
            if (proxy != null) {
               actual.setValue(proxy.getProperty(propertyName));
            } else {
               try {
                  actual.setValue(method.invoke(obj));
               } catch (IllegalArgumentException var11) {
                  return false;
               } catch (IllegalAccessException var12) {
                  throw new AssertionError(var12);
               } catch (InvocationTargetException var13) {
                  return false;
               }
            }

            if (!expected.equals(actual)) {
               return false;
            }
         }

         return true;
      }
   }

   public int hashCode() {
      int hashCode = 0;

      Map.Entry property;
      for(Iterator var2 = this.properties.entrySet().iterator(); var2.hasNext(); hashCode += 127 * ((String)property.getKey()).hashCode() ^ ((AnnotationProperty)property.getValue()).getValueHashCode()) {
         property = (Map.Entry)var2.next();
      }

      return hashCode;
   }

   public String toString() {
      StringBuilder stringBuilder = (new StringBuilder("@")).append(this.annotationType.getName()).append('(');
      int counter = 0;

      for(Iterator var3 = this.properties.entrySet().iterator(); var3.hasNext(); ++counter) {
         Map.Entry property = (Map.Entry)var3.next();
         if (counter > 0) {
            stringBuilder.append(", ");
         }

         stringBuilder.append((String)property.getKey()).append('=').append(((AnnotationProperty)property.getValue()).valueToString());
      }

      return stringBuilder.append(')').toString();
   }
}

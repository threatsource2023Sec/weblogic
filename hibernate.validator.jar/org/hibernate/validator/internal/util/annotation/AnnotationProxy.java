package org.hibernate.validator.internal.util.annotation;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import org.hibernate.validator.internal.util.privilegedactions.GetAnnotationAttributes;

class AnnotationProxy implements Annotation, InvocationHandler, Serializable {
   private static final long serialVersionUID = 6907601010599429454L;
   private final AnnotationDescriptor descriptor;

   AnnotationProxy(AnnotationDescriptor descriptor) {
      this.descriptor = descriptor;
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      Object value = this.descriptor.getAttribute(method.getName());
      return value != null ? value : method.invoke(this, args);
   }

   public Class annotationType() {
      return this.descriptor.getType();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (!this.descriptor.getType().isInstance(obj)) {
         return false;
      } else {
         Annotation other = (Annotation)this.descriptor.getType().cast(obj);
         Map otherAttributes = this.getAnnotationAttributes(other);
         if (this.descriptor.getAttributes().size() != otherAttributes.size()) {
            return false;
         } else {
            Iterator var4 = this.descriptor.getAttributes().entrySet().iterator();

            Object value;
            Object otherValue;
            do {
               if (!var4.hasNext()) {
                  return true;
               }

               Map.Entry member = (Map.Entry)var4.next();
               value = member.getValue();
               otherValue = otherAttributes.get(member.getKey());
            } while(this.areEqual(value, otherValue));

            return false;
         }
      }
   }

   public int hashCode() {
      return this.descriptor.hashCode();
   }

   public String toString() {
      return this.descriptor.toString();
   }

   private boolean areEqual(Object o1, Object o2) {
      return !o1.getClass().isArray() ? o1.equals(o2) : (o1.getClass() == boolean[].class ? Arrays.equals((boolean[])((boolean[])o1), (boolean[])((boolean[])o2)) : (o1.getClass() == byte[].class ? Arrays.equals((byte[])((byte[])o1), (byte[])((byte[])o2)) : (o1.getClass() == char[].class ? Arrays.equals((char[])((char[])o1), (char[])((char[])o2)) : (o1.getClass() == double[].class ? Arrays.equals((double[])((double[])o1), (double[])((double[])o2)) : (o1.getClass() == float[].class ? Arrays.equals((float[])((float[])o1), (float[])((float[])o2)) : (o1.getClass() == int[].class ? Arrays.equals((int[])((int[])o1), (int[])((int[])o2)) : (o1.getClass() == long[].class ? Arrays.equals((long[])((long[])o1), (long[])((long[])o2)) : (o1.getClass() == short[].class ? Arrays.equals((short[])((short[])o1), (short[])((short[])o2)) : Arrays.equals((Object[])((Object[])o1), (Object[])((Object[])o2))))))))));
   }

   private Map getAnnotationAttributes(Annotation annotation) {
      if (Proxy.isProxyClass(annotation.getClass()) && System.getSecurityManager() == null) {
         InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
         if (invocationHandler instanceof AnnotationProxy) {
            return ((AnnotationProxy)invocationHandler).descriptor.getAttributes();
         }
      }

      return (Map)this.run(GetAnnotationAttributes.action(annotation));
   }

   private Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}

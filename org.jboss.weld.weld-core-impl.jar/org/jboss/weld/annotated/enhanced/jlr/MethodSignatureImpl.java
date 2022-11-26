package org.jboss.weld.annotated.enhanced.jlr;

import java.lang.reflect.Method;
import java.util.Arrays;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.MethodSignature;
import org.jboss.weld.util.reflection.Reflections;

public class MethodSignatureImpl implements MethodSignature {
   private static final long serialVersionUID = 870948075030895317L;
   private final String methodName;
   private final String[] parameterTypes;

   public static MethodSignature of(AnnotatedMethod method) {
      return (MethodSignature)(method instanceof EnhancedAnnotatedMethod ? ((EnhancedAnnotatedMethod)Reflections.cast(method)).getSignature() : new MethodSignatureImpl(method));
   }

   public MethodSignatureImpl(AnnotatedMethod method) {
      this.methodName = method.getJavaMember().getName();
      this.parameterTypes = new String[method.getParameters().size()];

      for(int i = 0; i < method.getParameters().size(); ++i) {
         this.parameterTypes[i] = Reflections.getRawType(((AnnotatedParameter)method.getParameters().get(i)).getBaseType()).getName();
      }

   }

   public MethodSignatureImpl(Method method) {
      this.methodName = method.getName();
      this.parameterTypes = new String[method.getParameterTypes().length];

      for(int i = 0; i < method.getParameterTypes().length; ++i) {
         this.parameterTypes[i] = method.getParameterTypes()[i].getName();
      }

   }

   public MethodSignatureImpl(String methodName, String... parameterTypes) {
      this.methodName = methodName;
      this.parameterTypes = parameterTypes;
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + this.methodName.hashCode();
      result = 31 * result + Arrays.hashCode(this.parameterTypes);
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (!(obj instanceof MethodSignatureImpl)) {
         return false;
      } else {
         MethodSignatureImpl other = (MethodSignatureImpl)obj;
         if (this.methodName == null) {
            if (other.methodName != null) {
               return false;
            }
         } else if (!this.methodName.equals(other.methodName)) {
            return false;
         }

         return Arrays.equals(this.parameterTypes, other.parameterTypes);
      }
   }

   public String getMethodName() {
      return this.methodName;
   }

   public String[] getParameterTypes() {
      return (String[])Arrays.copyOf(this.parameterTypes, this.parameterTypes.length);
   }

   public String toString() {
      return "method " + this.getMethodName() + Arrays.toString(this.parameterTypes).replace('[', '(').replace(']', ')');
   }

   public boolean matches(Method method) {
      if (!this.methodName.equals(method.getName())) {
         return false;
      } else {
         Class[] methodParameterTypes = method.getParameterTypes();
         if (methodParameterTypes.length != this.parameterTypes.length) {
            return false;
         } else {
            for(int i = 0; i < this.parameterTypes.length; ++i) {
               if (!this.parameterTypes[i].equals(methodParameterTypes[i].getName())) {
                  return false;
               }
            }

            return true;
         }
      }
   }
}

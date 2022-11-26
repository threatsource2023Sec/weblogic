package org.jboss.weld.util.bytecode;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.reflect.Method;
import org.jboss.classfilewriter.util.DescriptorUtils;

public class RuntimeMethodInformation implements MethodInformation {
   private final Method method;
   private final String descriptor;
   private final String[] parameterTypes;
   private final String returnType;
   private final String declaringClass;
   private final int modifier;

   public RuntimeMethodInformation(Method method) {
      this.method = method;
      this.parameterTypes = DescriptorUtils.parameterDescriptors(method);
      this.returnType = DescriptorUtils.makeDescriptor(method.getReturnType());
      this.descriptor = DescriptorUtils.methodDescriptor(this.parameterTypes, this.returnType);
      this.declaringClass = method.getDeclaringClass().getName();
      int modifier;
      if (method.isBridge()) {
         modifier = 4161;
      } else {
         modifier = 1;
      }

      if (method.isVarArgs()) {
         modifier |= 128;
      }

      this.modifier = modifier;
   }

   public String getDeclaringClass() {
      return this.declaringClass;
   }

   public Method getMethod() {
      return this.method;
   }

   public String getDescriptor() {
      return this.descriptor;
   }

   @SuppressFBWarnings({"EI_EXPOSE_REP"})
   public String[] getParameterTypes() {
      return this.parameterTypes;
   }

   public String getReturnType() {
      return this.returnType;
   }

   public String getName() {
      return this.method.getName();
   }

   public int getModifiers() {
      return this.modifier;
   }
}

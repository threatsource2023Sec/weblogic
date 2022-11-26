package org.jboss.weld.util.bytecode;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.reflect.Method;
import org.jboss.classfilewriter.util.DescriptorUtils;

public class StaticMethodInformation implements MethodInformation {
   private final String name;
   private final String descriptor;
   private final String[] parameterTypes;
   private final String returnType;
   private final String declaringClass;
   private final int modifiers;

   public StaticMethodInformation(String name, Class[] parameterTypes, Class returnType, String declaringClass) {
      this(name, parameterTypes, returnType, declaringClass, 1);
   }

   public StaticMethodInformation(String name, Class[] parameterTypes, Class returnType, String declaringClass, int modifiers) {
      this.name = name;
      this.parameterTypes = DescriptorUtils.parameterDescriptors(parameterTypes);
      this.returnType = DescriptorUtils.makeDescriptor(returnType);
      this.declaringClass = declaringClass;
      StringBuilder builder = new StringBuilder("(");
      String[] var7 = this.parameterTypes;
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         String p = var7[var9];
         builder.append(p);
      }

      builder.append(')');
      builder.append(this.returnType);
      this.descriptor = builder.toString();
      this.modifiers = modifiers;
   }

   @SuppressFBWarnings({"EI_EXPOSE_REP"})
   public StaticMethodInformation(String name, String[] parameterTypes, String returnType, String declaringClass) {
      this.name = name;
      this.parameterTypes = parameterTypes;
      this.returnType = returnType;
      this.declaringClass = declaringClass;
      StringBuilder builder = new StringBuilder("(");
      String[] var6 = this.parameterTypes;
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         String p = var6[var8];
         builder.append(p);
      }

      builder.append(')');
      builder.append(returnType);
      this.descriptor = builder.toString();
      this.modifiers = 1;
   }

   public String getDeclaringClass() {
      return this.declaringClass;
   }

   public Method getMethod() {
      return null;
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
      return this.name;
   }

   public int getModifiers() {
      return this.modifiers;
   }
}

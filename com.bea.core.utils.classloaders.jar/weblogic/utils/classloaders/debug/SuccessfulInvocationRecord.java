package weblogic.utils.classloaders.debug;

import weblogic.utils.classloaders.GenericClassLoader;

class SuccessfulInvocationRecord extends Record {
   private final Object returnValue;

   SuccessfulInvocationRecord(GenericClassLoader instance, SupportedClassLoader clz, String methodName, String arg, Object returnValue) {
      super(instance, clz, methodName, arg);
      this.returnValue = returnValue;
   }

   void toString(StringBuilder builder) {
      super.toString(builder);
      builder.append(" completed: ");
      builder.append(this.returnValue);
   }
}

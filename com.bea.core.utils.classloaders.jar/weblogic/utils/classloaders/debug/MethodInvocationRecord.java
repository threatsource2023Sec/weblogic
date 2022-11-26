package weblogic.utils.classloaders.debug;

import weblogic.utils.classloaders.GenericClassLoader;

class MethodInvocationRecord extends Record {
   MethodInvocationRecord(GenericClassLoader instance, SupportedClassLoader clz, String methodName, String arg) {
      super(instance, clz, methodName, arg);
   }
}

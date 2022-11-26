package org.apache.xmlbeans;

public interface InterfaceExtension {
   String getInterface();

   String getStaticHandler();

   MethodSignature[] getMethods();

   public interface MethodSignature {
      String getName();

      String getReturnType();

      String[] getParameterTypes();

      String[] getExceptionTypes();
   }
}

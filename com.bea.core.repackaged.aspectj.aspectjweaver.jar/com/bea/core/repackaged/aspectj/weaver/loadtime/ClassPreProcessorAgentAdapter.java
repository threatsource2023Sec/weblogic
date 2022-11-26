package com.bea.core.repackaged.aspectj.weaver.loadtime;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ClassPreProcessorAgentAdapter implements ClassFileTransformer {
   private static ClassPreProcessor s_preProcessor;

   public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {
      if (classBeingRedefined != null) {
         System.err.println("INFO: (Enh120375):  AspectJ attempting reweave of '" + className + "'");
      }

      return s_preProcessor.preProcess(className, bytes, loader, protectionDomain);
   }

   static {
      try {
         s_preProcessor = new Aj();
         s_preProcessor.initialize();
      } catch (Exception var1) {
         throw new ExceptionInInitializerError("could not initialize JSR163 preprocessor due to: " + var1.toString());
      }
   }
}

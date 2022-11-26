package com.bea.wls.redef.agent;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.Method;

public class ClassRedefiner {
   private static Instrumentation instrumentation;

   public static Instrumentation getInstrumentation() {
      if (instrumentation == null) {
         ClassLoader cl = Thread.currentThread().getContextClassLoader();

         try {
            Class c = Class.forName("com.bea.wls.redef.agent.ClassRedefiner", false, cl);
            Method method = c.getMethod("getInstrumentation");
            instrumentation = (Instrumentation)method.invoke((Object)null);
         } catch (Exception var3) {
         }
      }

      return instrumentation;
   }

   public static void redefineClass(Class c, byte[] clazzBytes) throws ClassNotFoundException, UnmodifiableClassException {
      ClassDefinition[] definitions = new ClassDefinition[]{new ClassDefinition(c, clazzBytes)};
      getInstrumentation().redefineClasses(definitions);
   }

   public static void redefineClass(ClassDefinition[] definitions) throws ClassNotFoundException, UnmodifiableClassException {
      getInstrumentation().redefineClasses(definitions);
   }

   public static void premain(String args, Instrumentation inst) {
      instrumentation = inst;
   }

   public static void agentmain(String args, Instrumentation inst) {
      premain(args, inst);
   }
}

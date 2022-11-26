package com.bea.wls.redef;

import com.bea.wls.redef.agent.ClassRedefiner;
import com.bea.wls.redef.io.ClassChangeNotifier;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;

public class ClassRedefinerFactory {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugClassRedef");
   private static final boolean AGENT_LOADED = loadAgent();
   static ClassRedefInitializationException exception;

   private static boolean loadAgent() {
      try {
         AttachUtils.attachAndLoadAgent();
         return true;
      } catch (ClassRedefInitializationException var1) {
         exception = var1;
         return false;
      }
   }

   private ClassRedefinerFactory() {
   }

   public static GenericClassLoader makeClassLoader(ClassFinder finder, ClassLoader parent) throws ClassRedefInitializationException {
      if (ClassRedefiner.getInstrumentation() == null) {
         if (exception == null) {
            throw new ClassRedefInitializationException("Instrumentation is uninitialized. Specify -javaagent");
         } else {
            throw exception;
         }
      } else {
         ClassChangeNotifier notifier = new ClassChangeNotifier();
         RedefiningClassLoader loader = new RedefiningClassLoader(finder, parent, notifier);
         notifier.setFinder(loader.getClassFinder());
         return loader;
      }
   }
}

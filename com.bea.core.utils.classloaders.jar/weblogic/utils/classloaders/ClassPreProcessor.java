package weblogic.utils.classloaders;

import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.debug.ClassLoaderDebugger;

public interface ClassPreProcessor {
   String CLASSNAME_PROPERTY = "weblogic.classloader.preprocessor";
   String SEPARATOR = ",";

   void initialize(Hashtable var1);

   byte[] preProcess(String var1, byte[] var2);

   public static class ClassPreProcessorSupport {
      private final List processors;
      private static final DebugLogger vDebugLogger = DebugLogger.getDebugLogger("DebugClassLoadingVerbose");

      public ClassPreProcessorSupport() {
         this(true);
      }

      public ClassPreProcessorSupport(boolean loadingExternal) {
         this.processors = new CopyOnWriteArrayList();
         if (loadingExternal) {
            String arg = System.getProperty("weblogic.classloader.preprocessor");
            if (arg != null) {
               String[] classes = StringUtils.splitCompletely(arg, ",");
               if (classes != null) {
                  for(int i = 0; i < classes.length; ++i) {
                     String className = classes[i];
                     if (className != null) {
                        this.addClassPreProcessor(className);
                        if (vDebugLogger.isDebugEnabled()) {
                           ClassLoaderDebugger.verbose(this, "ClassPreProcessorSupport", Boolean.toString(loadingExternal), "Pre-processor " + className + " loaded and initialized");
                        }
                     }
                  }

               }
            }
         }
      }

      public void addClassPreProcessor(String className) {
         try {
            Class clazz = Class.forName(className.trim());
            ClassPreProcessor cpp = (ClassPreProcessor)clazz.newInstance();
            this.addClassPreProcessor(cpp);
         } catch (ClassNotFoundException var4) {
            ClassLoadersLogger.preProcessorClassNotFound(className, var4);
         } catch (Throwable var5) {
            ClassLoadersLogger.errorInitializingPreProcessorClass(className, var5);
         }

      }

      public void addClassPreProcessor(ClassPreProcessor cpp) {
         cpp.initialize((Hashtable)null);
         this.processors.add(cpp);
      }

      public byte[] preProcess(String name, byte[] bytes) {
         for(int i = 0; i < this.processors.size(); ++i) {
            try {
               bytes = ((ClassPreProcessor)this.processors.get(i)).preProcess(name, bytes);
            } catch (Throwable var8) {
               String preProcessorDescription = "<error printing preprocessor>";

               try {
                  preProcessorDescription = ((ClassPreProcessor)this.processors.get(i)).toString();
               } catch (Throwable var7) {
               }

               ClassLoadersLogger.errorPreProcessingClass(name, preProcessorDescription, var8);
            }
         }

         return bytes;
      }
   }
}

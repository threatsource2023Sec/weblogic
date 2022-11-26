package com.bea.wls.redef;

import com.bea.wls.redef.debug.DebugClassRedef;
import com.bea.wls.redef.debug.StoreEntry;
import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import serp.bytecode.BCClass;
import serp.bytecode.Project;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.classloaders.GenericClassLoader;

public class RedefinableClassFileTransformer implements ClassFileTransformer {
   private final Project project = new Project();
   private final MetaDataRepository repos;
   private final RedefiningClassLoader redefiningLoader;
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugClassRedef");
   private static final DebugLogger DEBUGSIZE = DebugLogger.getDebugLogger("DebugClassSize");

   public RedefinableClassFileTransformer(MetaDataRepository repos, RedefiningClassLoader redefiningLoader) {
      this.repos = repos;
      this.redefiningLoader = redefiningLoader;
   }

   public byte[] transform(ClassLoader loader, String clsName, Class classBeingRedefined, ProtectionDomain domain, byte[] bytes) throws IllegalClassFormatException {
      if (loader != this.redefiningLoader) {
         return null;
      } else if (clsName.indexOf("beaVersion") > -1) {
         return null;
      } else {
         ClassMetaData meta = this.repos.getMetaData(clsName.replace('/', '.'));
         if (meta == null) {
            return null;
         } else {
            ClassRedefinitionRuntime runtime = this.redefiningLoader.getRedefinitionRuntime();
            RedefinitionTask task = runtime.getCurrentTask();
            if (task != null && !task.isRunning()) {
               throw new TaskNotRunningException("Task is not running.");
            } else {
               byte[] var15;
               try {
                  BCClass bc = this.project.loadClass(new ByteArrayInputStream(bytes), loader);
                  Enhancer enhancer = new Enhancer(bc, meta, this.redefiningLoader);
                  RedefinableClassFileTransformer.Times.start();
                  if (!enhancer.run()) {
                     Object var23 = null;
                     return (byte[])var23;
                  }

                  long elapsed = RedefinableClassFileTransformer.Times.end();
                  byte[] clazzBytes = enhancer.getBytecode().toByteArray();
                  BCClass version = enhancer.getVersionBytecode();
                  if (DEBUG.isDebugEnabled()) {
                     this.debugWriteClass(enhancer, this.redefiningLoader, bytes);
                  }

                  if (DEBUGSIZE.isDebugEnabled()) {
                     this.debugSize(bytes, enhancer, elapsed);
                  }

                  if (version != null) {
                     this.redefiningLoader.addGeneratedSource(version.getName(), version.toByteArray());
                  }

                  runtime.updateProcessingTime(elapsed);
                  runtime.updateProcessedClassesCount(1);
                  var15 = clazzBytes;
               } catch (Exception var20) {
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("Problem during class redefinition", var20);
                  }

                  throw (IllegalClassFormatException)(new IllegalClassFormatException(var20.getMessage())).initCause(var20);
               } catch (Throwable var21) {
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("Problem during class redefinition", var21);
                  }

                  throw new AssertionError(var21);
               } finally {
                  this.project.clear();
               }

               return var15;
            }
         }
      }
   }

   private void debugSize(byte[] cBytes, Enhancer enhancer, long elapsed) {
      BCClass bc = enhancer.getBytecode();
      String className = bc != null ? bc.getName() : "<unknown>";
      int initialSize = cBytes.length;
      int finalSize = bc != null ? bc.toByteArray().length : 0;
      BCClass vbc = enhancer.getVersionBytecode();
      int finalInnerClassSize = vbc != null ? vbc.toByteArray().length : 0;
      DEBUGSIZE.debug("[CLASS-SIZE]," + className + "," + initialSize + "," + finalSize + "," + finalInnerClassSize + ", " + elapsed);
   }

   private void debugWriteClass(Enhancer enhancer, GenericClassLoader loader, byte[] origBytes) {
      StoreEntry entry = DebugClassRedef.getInstance(loader).get();
      BCClass bc = enhancer.getBytecode();
      if (bc != null) {
         entry.record(bc.getClassName(), bc.toByteArray());
         entry.record(bc.getClassName() + "_ORIG", origBytes);
      }

      BCClass vbc = enhancer.getVersionBytecode();
      if (vbc != null) {
         entry.record(vbc.getClassName(), vbc.toByteArray());
      }

      DebugClassRedef.getInstance(loader).commit();
   }

   static class Times {
      static long t;

      static void start() {
         t = System.nanoTime();
      }

      static long end() {
         return System.nanoTime() - t;
      }
   }
}

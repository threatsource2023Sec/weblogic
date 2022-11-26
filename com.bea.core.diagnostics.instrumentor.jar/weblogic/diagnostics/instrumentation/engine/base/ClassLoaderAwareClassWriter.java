package weblogic.diagnostics.instrumentation.engine.base;

import org.objectweb.asm.ClassWriter;

public class ClassLoaderAwareClassWriter extends ClassWriter {
   private ClassLoader loader = null;
   private InstrumentorEngineBase engine = null;
   private String className = null;

   ClassLoaderAwareClassWriter(int flags, String className, InstrumentorEngineBase engine) {
      super(flags);
      this.className = className.replace('/', '.');
      this.engine = engine;
   }

   ClassLoaderAwareClassWriter(int flags, ClassLoader loader, String className, InstrumentorEngineBase engine) {
      super(flags);
      this.className = className.replace('/', '.');
      this.loader = loader;
      this.engine = engine;
   }

   protected String getClassName() {
      return this.className;
   }

   protected String getCommonSuperClass(String type1, String type2) {
      if (this.loader == null) {
         this.loader = this.getClass().getClassLoader();
      }

      ClassInfo ci = this.engine.getClassInfo(type1, this.loader);
      ClassInfo di = this.engine.getClassInfo(type2, this.loader);
      if (ci.isAssignableFrom(di, this.loader)) {
         return type1;
      } else if (di.isAssignableFrom(ci, this.loader)) {
         return type2;
      } else if (!ci.isInterface() && !di.isInterface()) {
         do {
            ci = ci.getSuperClassName() == null ? null : ci.getSuperClassInfo();
            if (ci == null) {
               return "java/lang/Object";
            }
         } while(!ci.isAssignableFrom(di, this.loader));

         return ci.getClassName();
      } else {
         return "java/lang/Object";
      }
   }
}

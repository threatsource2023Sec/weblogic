package jnr.ffi.provider.jffi;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class AsmClassLoader extends ClassLoader {
   private final ConcurrentMap definedClasses = new ConcurrentHashMap();

   public AsmClassLoader() {
   }

   public AsmClassLoader(ClassLoader parent) {
      super(parent);
   }

   public Class defineClass(String name, byte[] b) {
      Class klass = this.defineClass(name, b, 0, b.length);
      this.definedClasses.putIfAbsent(name, klass);
      this.resolveClass(klass);
      return klass;
   }

   protected Class findClass(String name) throws ClassNotFoundException {
      Class klass = (Class)this.definedClasses.get(name);
      return klass != null ? klass : super.findClass(name);
   }
}

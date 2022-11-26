package weblogic.utils.classloaders;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

public final class JarClassLoader extends GenericClassLoader {
   public JarClassLoader(File jar) throws IOException {
      super((ClassFinder)(new JarClassFinder(jar)));
   }

   public JarClassLoader(JarFile jar) throws IOException {
      super((ClassFinder)(new JarClassFinder(new File(jar.getName()))));
   }

   public JarClassLoader(File jar, ClassLoader parent) throws IOException {
      super(new JarClassFinder(jar), parent);
   }

   public JarClassLoader(JarFile jar, ClassLoader parent) throws IOException {
      super(new JarClassFinder(new File(jar.getName())), parent);
   }

   static {
      ClassLoader.registerAsParallelCapable();
   }
}

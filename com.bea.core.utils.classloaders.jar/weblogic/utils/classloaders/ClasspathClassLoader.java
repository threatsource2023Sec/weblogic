package weblogic.utils.classloaders;

public class ClasspathClassLoader extends GenericClassLoader {
   public ClasspathClassLoader() {
      super((ClassFinder)(new ClasspathClassFinder2()));
   }

   public ClasspathClassLoader(String classpath) {
      super((ClassFinder)(new ClasspathClassFinder2(classpath)));
   }

   public ClasspathClassLoader(String classpath, ClassLoader parent) {
      super(new ClasspathClassFinder2(classpath), parent);
   }

   static {
      ClassLoader.registerAsParallelCapable();
   }
}

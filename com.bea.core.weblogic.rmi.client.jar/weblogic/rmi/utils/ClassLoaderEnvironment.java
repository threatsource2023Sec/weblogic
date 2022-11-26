package weblogic.rmi.utils;

public class ClassLoaderEnvironment {
   public ClassLoader findLoader(String applicationName) {
      return this.getClass().getClassLoader();
   }

   public ClassLoader findInterAppLoader(String applicationName, ClassLoader parent) {
      return parent;
   }
}

package weblogic.application;

import java.util.Iterator;
import org.jvnet.hk2.annotations.Contract;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;

@Contract
public interface AppClassLoaderManager {
   Class loadApplicationClass(Annotation var1, String var2) throws ClassNotFoundException;

   Source findApplicationSource(Annotation var1, String var2);

   GenericClassLoader findOrCreateIntraAppLoader(Annotation var1, ClassLoader var2);

   GenericClassLoader findOrCreateInterAppLoader(Annotation var1, ClassLoader var2);

   void addModuleLoader(GenericClassLoader var1, String var2);

   GenericClassLoader findModuleLoader(String var1, String var2);

   Iterator iterateModuleLoaders(String var1);

   GenericClassLoader findLoader(Annotation var1);

   void removeApplicationLoader(String var1);
}

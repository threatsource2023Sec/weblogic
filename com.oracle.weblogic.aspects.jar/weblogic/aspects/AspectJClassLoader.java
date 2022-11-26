package weblogic.aspects;

import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;

public final class AspectJClassLoader extends GenericClassLoader {
   public static final String WEBLOGIC_ASPECTS_PROPERTY = "weblogic.aspects";
   public static final String ASPECT_PRE_PROCESSOR_CLASS = "weblogic.aspects.AspectClassPreProcessor";
   public static final String CLASSLOADER_PREPROCESSOR = "weblogic.classloader.preprocessor";

   public AspectJClassLoader(ClassLoader parent) {
      super(new ClasspathClassFinder2(System.getProperty("java.class.path")), (ClassLoader)null, true);
      System.out.println("-----------------------");
      System.out.println("--- ASPECTJ ENABLED ---");
      System.out.println("-----------------------");
      System.setProperty("weblogic.aspects", "all");
      addClassPreProcessor("weblogic.aspects.AspectClassPreProcessor");
   }
}

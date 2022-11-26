package weblogic.aspects;

import java.lang.reflect.Method;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;

public class AspectRun {
   public static void main(String[] args) throws Exception {
      if (args.length == 0) {
         System.out.println("Usage: java weblogic.aspects.AspectRun [class name] [arguments]");
         System.exit(1);
      }

      ClasspathClassFinder2 ccf = new ClasspathClassFinder2();
      GenericClassLoader gcl = GenericClassLoader.getRootClassLoader(ccf);
      Thread.currentThread().setContextClassLoader(gcl);
      String className = args[0];
      String[] newArgs = new String[args.length - 1];
      if (newArgs.length > 0) {
         System.arraycopy(args, 1, newArgs, 0, args.length - 1);
      }

      System.setProperty("weblogic.aspects", "all");
      Class clazz = gcl.loadClass(className);
      Method m = clazz.getMethod("main", String[].class);
      m.invoke((Object)null, newArgs);
   }
}

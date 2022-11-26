package weblogic.application.utils.annotation;

import java.net.URL;
import java.util.Map;
import java.util.Set;

public interface ClassInfoFinder {
   Map getAnnotatedClassesByTargetsAndSources(String[] var1, Filter var2, boolean var3, ClassLoader var4);

   boolean hasAnnotatedClasses(String[] var1, Filter var2, boolean var3, ClassLoader var4);

   Set getClassNamesWithAnnotations(String... var1);

   Map getAnnotatedClasses(String... var1);

   URL getClassSourceUrl(String var1);

   Set getAllSubClassNames(String var1);

   Set getHandlesImpls(ClassLoader var1, String... var2);

   public static enum Target {
      CLASS,
      FIELD,
      METHOD;
   }

   public interface Filter {
      boolean accept(Target var1);

      boolean accept(Target var1, URL var2, CharSequence var3, CharSequence var4);
   }
}

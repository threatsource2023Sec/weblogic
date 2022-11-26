package weblogic.servlet.internal;

import java.net.URL;
import java.util.Collection;
import java.util.Set;
import weblogic.application.utils.annotation.ClassInfoFinder;

public interface WebAppHelper {
   Set getTagListeners(boolean var1);

   Set getTagHandlers(boolean var1);

   Set getManagedBeanClasses(String var1);

   Set getAnnotatedClasses(String... var1);

   Set getHandlesImpls(ClassLoader var1, String... var2);

   Collection getWebFragments();

   URL getClassSourceUrl(String var1);

   ClassInfoFinder getClassInfoFinder();

   void startAnnotationProcess();

   void completeAnnotationProcess();
}

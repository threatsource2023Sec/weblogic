package weblogic.application.utils.annotation;

import java.net.URL;
import java.util.List;
import java.util.Set;

public interface ClassInfo {
   String getClassName();

   String getSuperClassName();

   List getInterfaceNames();

   List getClassLevelAnnotationNames();

   URL getUrl();

   URL getCodeSourceUrl();

   boolean isInterface();

   boolean isAnnotation();

   boolean isEnum();

   boolean isClass();

   boolean shouldBePopulated();

   List getImplementations();

   List getAnnotatedClasses();

   Set getFieldAnnotations();

   Set getMethodAnnotations();

   boolean isAnnotated();

   void addImplementation(String var1);

   void addAnnotatedClass(String var1);

   void merge(ClassInfo var1);
}

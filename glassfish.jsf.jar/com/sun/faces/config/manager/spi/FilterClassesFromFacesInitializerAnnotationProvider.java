package com.sun.faces.config.manager.spi;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.spi.AnnotationProvider;
import com.sun.faces.util.Util;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;

public class FilterClassesFromFacesInitializerAnnotationProvider extends AnnotationProvider {
   public FilterClassesFromFacesInitializerAnnotationProvider(ServletContext servletContext) {
      super(servletContext);
   }

   public void setAnnotationScanner(com.sun.faces.spi.AnnotationScanner containerConnector, Set jarNamesWithoutMetadataComplete) {
   }

   public Map getAnnotatedClasses(Set urls) {
      return this.createAnnotatedMap((Set)this.servletContext.getAttribute("com.sun.faces.AnnotatedClasses"));
   }

   private Map createAnnotatedMap(Set annotatedSet) {
      HashMap annotatedMap = new HashMap();
      if (Util.isEmpty((Collection)annotatedSet)) {
         return annotatedMap;
      } else {
         WebConfiguration webConfig = WebConfiguration.getInstance();
         boolean annotationScanPackagesSet = webConfig.isSet(WebConfiguration.WebContextInitParameter.AnnotationScanPackages);
         String[] annotationScanPackages = annotationScanPackagesSet ? webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.AnnotationScanPackages).split("\\s+") : null;
         Iterator iterator = annotatedSet.iterator();

         while(iterator.hasNext()) {
            try {
               Class clazz = (Class)iterator.next();
               Arrays.stream(clazz.getAnnotations()).map((annotation) -> {
                  return annotation.annotationType();
               }).filter((annotationType) -> {
                  return AnnotationScanner.FACES_ANNOTATION_TYPE.contains(annotationType);
               }).forEach((annotationType) -> {
                  Set classes = (Set)annotatedMap.computeIfAbsent(annotationType, (e) -> {
                     return new HashSet();
                  });
                  if (annotationScanPackagesSet) {
                     if (this.matchesAnnotationScanPackages(clazz, annotationScanPackages)) {
                        classes.add(clazz);
                     }
                  } else {
                     classes.add(clazz);
                  }

               });
            } catch (NoClassDefFoundError var8) {
            }
         }

         return annotatedMap;
      }
   }

   private boolean matchesAnnotationScanPackages(Class clazz, String[] annotationScanPackages) {
      boolean result = false;

      for(int i = 0; i < annotationScanPackages.length; ++i) {
         String classUrlString = clazz.getProtectionDomain().getCodeSource().getLocation().toString();
         String classPackageName = clazz.getPackage().getName();
         if (classUrlString.contains("WEB-INF/classes") && annotationScanPackages[i].equals("*")) {
            result = true;
         } else if (classPackageName.equals(annotationScanPackages[i])) {
            result = true;
         } else if (annotationScanPackages[i].startsWith("jar:")) {
            String jarName = annotationScanPackages[i].substring(4, annotationScanPackages[i].indexOf(":", 5));
            String jarPackageName = annotationScanPackages[i].substring(annotationScanPackages[i].lastIndexOf(":") + 1);
            if (jarName.equals("*")) {
               if (jarPackageName.equals("*")) {
                  result = true;
               } else if (jarPackageName.equals(classPackageName)) {
                  result = true;
               }
            } else if (classUrlString.contains(jarName) && jarPackageName.equals("*")) {
               result = true;
            } else if (classUrlString.contains(jarName) && jarPackageName.equals(classPackageName)) {
               result = true;
            }
         }
      }

      return result;
   }
}

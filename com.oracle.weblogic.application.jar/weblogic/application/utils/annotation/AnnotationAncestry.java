package weblogic.application.utils.annotation;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import weblogic.application.internal.Controls;
import weblogic.diagnostics.debug.DebugLogger;

public class AnnotationAncestry {
   private Map isExtendedAnnotation = new HashMap();
   private ClassLoader cl;
   private static Logger m_logger = Logger.getLogger(AnnotationAncestry.class.getName());
   private static final DebugLogger debugger = DebugLogger.getDebugLogger("DebugAppAnnoQueryVerbose");

   public AnnotationAncestry(ClassLoader classLoader, String... extensibleAnnotations) {
      this.cl = classLoader;
      String[] var3 = extensibleAnnotations;
      int var4 = extensibleAnnotations.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String extensibleAnnotation = var3[var5];
         this.addAnnotationToCache(extensibleAnnotation, true);
      }

   }

   public boolean isExtendedAnnotation(String annotationName) {
      return this.isExtendedAnnotation(annotationName, (ClassInfo)null);
   }

   public boolean isExtendedAnnotation(String annotationName, ClassInfo annotationClassInfo) {
      String annotationClassName = annotationName.replace('/', '.');
      if (this.isExtendedAnnotationInCache(annotationClassName)) {
         if (debugger.isDebugEnabled()) {
            debugger.debug("AA: Found annotation in cache: " + annotationClassName);
         }

         return true;
      } else if (annotationClassInfo != null && Controls.loadannotationclassesonly.enabled && !annotationClassInfo.isAnnotation()) {
         if (debugger.isDebugEnabled()) {
            debugger.debug("AA: Class not found to be of type annotation: " + annotationClassInfo.getClassName());
         }

         this.addAnnotationToCache(annotationClassName, false);
         return false;
      } else {
         Class annotationClass = this.getAnnotationClass(annotationClassName);
         if (annotationClass == null) {
            if (debugger.isDebugEnabled()) {
               debugger.debug("AA: Annotation Class could not be loaded: " + annotationClassName);
            }

            this.addAnnotationToCache(annotationClassName, false);
            return false;
         } else {
            return this.isExtendedAnnotationDeep(new HashSet(), annotationClass);
         }
      }
   }

   private boolean isExtendedAnnotationDeep(Set annotationsInProcess, Class annotationClass) {
      annotationsInProcess.add(annotationClass);
      boolean result = false;
      if (debugger.isDebugEnabled()) {
         debugger.debug("AA: Checking annotation " + annotationClass + " using reflection");
      }

      try {
         Annotation[] annotations = annotationClass.getAnnotations();
         if (annotations != null) {
            Annotation[] var5 = annotations;
            int var6 = annotations.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Annotation anno = var5[var7];
               Class annotationType = anno.annotationType();
               if (debugger.isDebugEnabled()) {
                  debugger.debug("AA: Checking annotation " + annotationClass + " using reflection, found annotation: " + annotationType);
               }

               if (this.isExtendedAnnotationInCache(annotationType.getName()) || !annotationsInProcess.contains(annotationType) && this.isExtendedAnnotationDeep(annotationsInProcess, annotationType)) {
                  result = true;
                  break;
               }
            }

            this.addAnnotationToCache(annotationClass.getName(), result);
         }
      } catch (Exception var10) {
         m_logger.fine("Error parsing annotations for " + annotationClass.getName() + " : " + var10.getMessage());
      }

      if (debugger.isDebugEnabled()) {
         debugger.debug("AA: Checking annotation " + annotationClass + " using reflection: " + result);
      }

      return result;
   }

   private boolean isExtendedAnnotationInCache(String annotationName) {
      Boolean isExtended = (Boolean)this.isExtendedAnnotation.get(annotationName);
      return isExtended == null ? false : isExtended;
   }

   private void addAnnotationToCache(String annotationName, boolean flag) {
      if (debugger.isDebugEnabled()) {
         debugger.debug("AA: Adding extensible annotation to cache: " + annotationName + " with value " + flag);
      }

      this.isExtendedAnnotation.put(annotationName, flag);
   }

   private Class getAnnotationClass(String annotationClassName) {
      Class annotation = null;

      try {
         annotation = this.cl.loadClass(annotationClassName);
      } catch (SecurityException var4) {
         m_logger.warning("SecurityException loading annotation class: " + annotationClassName + " : " + var4.getMessage());
      } catch (NoClassDefFoundError | Exception var5) {
         if (debugger.isDebugEnabled()) {
            debugger.debug("Unable to load annotation class: " + annotationClassName + " : " + var5.getMessage());
         }
      } catch (Throwable var6) {
         if (debugger.isDebugEnabled()) {
            debugger.debug(var6.getMessage(), var6);
         }
      }

      return annotation;
   }
}

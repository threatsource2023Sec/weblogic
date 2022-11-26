package weblogic.application.utils.annotation;

import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;

class AggregateClassInfoFinder extends ClassfinderClassInfos implements ClassInfoFinder {
   private final List classInfoFinders;
   private static final DebugLogger queryDebugger = DebugLogger.getDebugLogger("DebugAppAnnoQuery");

   AggregateClassInfoFinder(List classInfoFinders) {
      this.classInfoFinders = classInfoFinders;
   }

   public Map getAnnotatedClassesByTargetsAndSources(String[] annotations, ClassInfoFinder.Filter filter, boolean includeExtendedAnnotations, ClassLoader classLoader) {
      AnnotationAncestry aa = getAnnotationAncestry(annotations, classLoader);
      Map classes = new HashMap();
      classes.putAll(((ClassfinderClassInfos)this.classInfoFinders.get(0)).getAnnotatedClassesByTargetsAndSources(annotations, filter, includeExtendedAnnotations, classLoader, false, aa));

      label65:
      for(int i = 1; i < this.classInfoFinders.size(); ++i) {
         Map libClasses = ((ClassfinderClassInfos)this.classInfoFinders.get(i)).getAnnotatedClassesByTargetsAndSources(annotations, filter, includeExtendedAnnotations, classLoader, false, aa);
         if (libClasses != null && !libClasses.isEmpty()) {
            Iterator var9 = libClasses.keySet().iterator();

            while(true) {
               Map urlMap;
               Map libUrlMap;
               do {
                  do {
                     while(true) {
                        if (!var9.hasNext()) {
                           continue label65;
                        }

                        ClassInfoFinder.Target target = (ClassInfoFinder.Target)var9.next();
                        urlMap = (Map)classes.get(target);
                        if (urlMap != null && !urlMap.isEmpty()) {
                           libUrlMap = (Map)libClasses.get(target);
                           break;
                        }

                        classes.put(target, libClasses.get(target));
                     }
                  } while(libUrlMap == null);
               } while(libUrlMap.isEmpty());

               Iterator var13 = libUrlMap.keySet().iterator();

               while(var13.hasNext()) {
                  URI uri = (URI)var13.next();
                  if (urlMap.containsKey(uri)) {
                     ((Set)urlMap.get(uri)).addAll((Collection)libUrlMap.get(uri));
                  } else {
                     urlMap.put(uri, libUrlMap.get(uri));
                  }
               }
            }
         }
      }

      if (queryDebugger.isDebugEnabled()) {
         this.debugQuery(queryDebugger, classes, annotations, filter, includeExtendedAnnotations, classLoader, false, aa, this.getClass(), "getAnnotatedClassesByTargetsAndSources");
      }

      return (Map)(classes.isEmpty() ? Collections.emptyMap() : classes);
   }

   public boolean hasAnnotatedClasses(String[] annotations, ClassInfoFinder.Filter filter, boolean includeExtendedAnnotations, ClassLoader classLoader) {
      AnnotationAncestry aa = getAnnotationAncestry(annotations, classLoader);
      Iterator var6 = this.classInfoFinders.iterator();

      ClassfinderClassInfos cif;
      do {
         if (!var6.hasNext()) {
            return false;
         }

         cif = (ClassfinderClassInfos)var6.next();
      } while(cif.getAnnotatedClassesByTargetsAndSources(annotations, filter, includeExtendedAnnotations, classLoader, true, aa).isEmpty());

      return true;
   }

   public Set getClassNamesWithAnnotations(String... annotations) {
      Set classNames = null;

      ClassInfoFinder cif;
      for(Iterator var3 = this.classInfoFinders.iterator(); var3.hasNext(); classNames = this.addAllIfNotEmpty(classNames, cif.getClassNamesWithAnnotations(annotations))) {
         cif = (ClassInfoFinder)var3.next();
      }

      return classNames;
   }

   public Map getAnnotatedClasses(String... annotations) {
      Map map = ((ClassfinderClassInfos)this.classInfoFinders.get(0)).getAnnotatedClasses(annotations);

      for(int i = 1; i < this.classInfoFinders.size(); ++i) {
         Map libMap = ((ClassfinderClassInfos)this.classInfoFinders.get(i)).getAnnotatedClasses(annotations);
         Iterator var5 = libMap.keySet().iterator();

         while(var5.hasNext()) {
            String className = (String)var5.next();
            if (map.containsKey(className)) {
               ((Set)map.get(className)).addAll((Collection)libMap.get(className));
            } else {
               map.put(className, libMap.get(className));
            }
         }
      }

      return map;
   }

   public URL getClassSourceUrl(String className) {
      Iterator var2 = this.classInfoFinders.iterator();

      URL url;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         ClassInfoFinder cif = (ClassInfoFinder)var2.next();
         url = cif.getClassSourceUrl(className);
      } while(url == null);

      return url;
   }

   public Set getHandlesImpls(ClassLoader cl, String... handlesTypes) {
      Set handlesImpls = null;

      ClassInfoFinder cif;
      for(Iterator var4 = this.classInfoFinders.iterator(); var4.hasNext(); handlesImpls = this.addAllIfNotEmpty(handlesImpls, cif.getHandlesImpls(cl, handlesTypes))) {
         cif = (ClassInfoFinder)var4.next();
      }

      return handlesImpls;
   }

   public Set getAllSubClassNames(String className) {
      Set allImplementations = null;

      ClassInfoFinder cif;
      for(Iterator var3 = this.classInfoFinders.iterator(); var3.hasNext(); allImplementations = this.addAllIfNotEmpty(allImplementations, cif.getAllSubClassNames(className))) {
         cif = (ClassInfoFinder)var3.next();
      }

      return allImplementations;
   }

   private Set addAllIfNotEmpty(Set target, Collection source) {
      Set result = target;
      if (target == null) {
         result = new LinkedHashSet();
      }

      if (source != null && !source.isEmpty()) {
         ((Set)result).addAll(source);
      }

      return (Set)result;
   }
}

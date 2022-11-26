package weblogic.application.utils.annotation;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public interface AnnotationDetectorAdapter {
   AnnotationDetectorAdapter INSTANCE = new AnnotationDetectorAdapter() {
      public boolean isAnnotated(String resourceName, byte[] bytes, String... annotationNames) {
         try {
            ClassInfo ci = new ClassInfoImpl(bytes, (URL)null, (URL)null);
            List foundAnnotationNames = ci.getClassLevelAnnotationNames();
            if (foundAnnotationNames.size() == 0) {
               return false;
            } else if (ci.getClassName().concat(".class").equals(resourceName.replaceAll("\\\\", "/"))) {
               return foundAnnotationNames.size() < annotationNames.length ? this.setContainsAtLeastOneName(new HashSet(foundAnnotationNames), Arrays.asList(annotationNames)) : this.setContainsAtLeastOneName(new HashSet(Arrays.asList(annotationNames)), foundAnnotationNames);
            } else {
               return false;
            }
         } catch (IOException var6) {
            return false;
         }
      }

      private boolean setContainsAtLeastOneName(HashSet nameSet, List names) {
         Iterator var3 = names.iterator();

         String name;
         do {
            if (!var3.hasNext()) {
               return false;
            }

            name = (String)var3.next();
         } while(!nameSet.contains(name));

         return true;
      }
   };

   boolean isAnnotated(String var1, byte[] var2, String... var3);
}

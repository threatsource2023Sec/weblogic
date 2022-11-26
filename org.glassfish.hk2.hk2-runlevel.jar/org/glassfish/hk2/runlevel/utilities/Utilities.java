package org.glassfish.hk2.runlevel.utilities;

import java.lang.annotation.Annotation;
import java.util.List;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.runlevel.RunLevel;

public class Utilities {
   public static int getRunLevelValue(ServiceLocator locator, Descriptor descriptor) {
      boolean isReified = false;
      ActiveDescriptor active = null;
      if (descriptor instanceof ActiveDescriptor) {
         active = (ActiveDescriptor)descriptor;
         isReified = active.isReified();
         if (isReified) {
            Annotation anno = active.getScopeAsAnnotation();
            if (anno instanceof RunLevel) {
               RunLevel runLevel = (RunLevel)anno;
               return runLevel.value();
            }
         }
      }

      List list = (List)descriptor.getMetadata().get("runLevelValue");
      if (list != null && !list.isEmpty()) {
         return Integer.parseInt((String)list.get(0));
      } else {
         if (active != null && !isReified) {
            active = locator.reifyDescriptor(active);
            Annotation anno = active.getScopeAsAnnotation();
            if (anno instanceof RunLevel) {
               RunLevel runLevel = (RunLevel)anno;
               return runLevel.value();
            }
         }

         return -1;
      }
   }

   public static int getRunLevelMode(ServiceLocator locator, Descriptor descriptor, Integer modeOverride) {
      if (modeOverride != null) {
         return modeOverride;
      } else {
         boolean isReified = false;
         ActiveDescriptor active = null;
         if (descriptor instanceof ActiveDescriptor) {
            active = (ActiveDescriptor)descriptor;
            isReified = active.isReified();
            if (isReified) {
               Annotation anno = active.getScopeAsAnnotation();
               if (anno instanceof RunLevel) {
                  RunLevel runLevel = (RunLevel)anno;
                  return runLevel.mode();
               }
            }
         }

         List list = (List)descriptor.getMetadata().get("runLevelMode");
         if (list != null && !list.isEmpty()) {
            return Integer.parseInt((String)list.get(0));
         } else {
            if (active != null && !isReified) {
               active = locator.reifyDescriptor(active);
               Annotation anno = active.getScopeAsAnnotation();
               if (anno instanceof RunLevel) {
                  RunLevel runLevel = (RunLevel)anno;
                  return runLevel.mode();
               }
            }

            return 1;
         }
      }
   }
}

package com.bea.core.repackaged.aspectj.weaver;

public class Utils {
   public static boolean isSuppressing(AnnotationAJ[] anns, String lintkey) {
      if (anns == null) {
         return false;
      } else {
         for(int i = 0; i < anns.length; ++i) {
            if (UnresolvedType.SUPPRESS_AJ_WARNINGS.getSignature().equals(anns[i].getTypeSignature())) {
               String value = anns[i].getStringFormOfValue("value");
               if (value == null || value.indexOf(lintkey) != -1) {
                  return true;
               }
            }
         }

         return false;
      }
   }
}

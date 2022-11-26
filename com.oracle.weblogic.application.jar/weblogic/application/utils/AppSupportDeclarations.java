package weblogic.application.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.AppSupportDeclaration;

public enum AppSupportDeclarations {
   instance;

   private Map supportedAnnotations = new HashMap();

   public void register(AppSupportDeclaration declaration) {
      ModuleType[] var2 = declaration.getSupportedModuleTypes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ModuleType type = var2[var4];
         if (!this.supportedAnnotations.containsKey(type)) {
            this.supportedAnnotations.put(type, new HashSet());
         }

         Class[] registeredAnnos = declaration.getSupportedClassLevelAnnotations();
         if (registeredAnnos != null) {
            ((Set)this.supportedAnnotations.get(type)).addAll(Arrays.asList(registeredAnnos));
         }
      }

   }

   public Class[] getAnnotations(ModuleType type) {
      return this.supportedAnnotations.containsKey(type) ? (Class[])((Set)this.supportedAnnotations.get(type)).toArray(new Class[0]) : null;
   }
}

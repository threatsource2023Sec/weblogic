package com.oracle.injection.integration;

import com.oracle.injection.InjectionArchive;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleExtensionContext;

class InjectionDeploymentHelper {
   private final List m_listofArchiveHolders = new ArrayList();

   void addArchiveHolder(InjectionArchive injectionArchive, Context componentContext, ModuleContext moduleContext, ModuleExtensionContext moduleExtensionContext) {
      this.m_listofArchiveHolders.add(new ArchiveHolder(injectionArchive, componentContext, moduleContext, moduleExtensionContext));
   }

   List getArchiveHolders() {
      return this.m_listofArchiveHolders;
   }

   void clear() {
      this.m_listofArchiveHolders.clear();
   }

   static class ArchiveHolder {
      private final InjectionArchive injectionArchive;
      private List componentContexts = new ArrayList();
      private final ModuleContext moduleContext;
      private ModuleExtensionContext moduleExtensionContext;

      ArchiveHolder(InjectionArchive injectionArchive, Context componentContext, ModuleContext moduleContext, ModuleExtensionContext moduleExtensionContext) {
         this.injectionArchive = injectionArchive;
         if (componentContext != null) {
            this.componentContexts.add(componentContext);
         }

         this.moduleContext = moduleContext;
         this.moduleExtensionContext = moduleExtensionContext;
      }

      InjectionArchive getInjectionArchive() {
         return this.injectionArchive;
      }

      List getComponentContexts() {
         return this.componentContexts;
      }

      void addComponentContext(Context context) {
         if (context != null) {
            this.componentContexts.add(context);
         }

      }

      ModuleContext getModuleContext() {
         return this.moduleContext;
      }

      ModuleExtensionContext getModuleExtensionContext() {
         return this.moduleExtensionContext;
      }
   }
}

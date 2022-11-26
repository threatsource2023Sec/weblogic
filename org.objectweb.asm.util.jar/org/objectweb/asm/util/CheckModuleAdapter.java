package org.objectweb.asm.util;

import java.util.HashSet;
import org.objectweb.asm.ModuleVisitor;

public class CheckModuleAdapter extends ModuleVisitor {
   private final boolean isOpen;
   private final NameSet requiredModules;
   private final NameSet exportedPackages;
   private final NameSet openedPackages;
   private final NameSet usedServices;
   private final NameSet providedServices;
   int classVersion;
   private boolean visitEndCalled;

   public CheckModuleAdapter(ModuleVisitor moduleVisitor, boolean isOpen) {
      this(458752, moduleVisitor, isOpen);
      if (this.getClass() != CheckModuleAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected CheckModuleAdapter(int api, ModuleVisitor moduleVisitor, boolean isOpen) {
      super(api, moduleVisitor);
      this.requiredModules = new NameSet("Modules requires");
      this.exportedPackages = new NameSet("Module exports");
      this.openedPackages = new NameSet("Module opens");
      this.usedServices = new NameSet("Module uses");
      this.providedServices = new NameSet("Module provides");
      this.isOpen = isOpen;
   }

   public void visitMainClass(String mainClass) {
      CheckMethodAdapter.checkInternalName(53, mainClass, "module main class");
      super.visitMainClass(mainClass);
   }

   public void visitPackage(String packaze) {
      CheckMethodAdapter.checkInternalName(53, packaze, "module package");
      super.visitPackage(packaze);
   }

   public void visitRequire(String module, int access, String version) {
      this.checkVisitEndNotCalled();
      CheckClassAdapter.checkFullyQualifiedName(53, module, "required module");
      this.requiredModules.checkNameNotAlreadyDeclared(module);
      CheckClassAdapter.checkAccess(access, 36960);
      if (this.classVersion >= 54 && module.equals("java.base") && (access & 96) != 0) {
         throw new IllegalArgumentException("Invalid access flags: " + access + " java.base can not be declared ACC_TRANSITIVE or ACC_STATIC_PHASE");
      } else {
         super.visitRequire(module, access, version);
      }
   }

   public void visitExport(String packaze, int access, String... modules) {
      this.checkVisitEndNotCalled();
      CheckMethodAdapter.checkInternalName(53, packaze, "package name");
      this.exportedPackages.checkNameNotAlreadyDeclared(packaze);
      CheckClassAdapter.checkAccess(access, 36864);
      if (modules != null) {
         String[] var4 = modules;
         int var5 = modules.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String module = var4[var6];
            CheckClassAdapter.checkFullyQualifiedName(53, module, "module export to");
         }
      }

      super.visitExport(packaze, access, modules);
   }

   public void visitOpen(String packaze, int access, String... modules) {
      this.checkVisitEndNotCalled();
      if (this.isOpen) {
         throw new UnsupportedOperationException("An open module can not use open directive");
      } else {
         CheckMethodAdapter.checkInternalName(53, packaze, "package name");
         this.openedPackages.checkNameNotAlreadyDeclared(packaze);
         CheckClassAdapter.checkAccess(access, 36864);
         if (modules != null) {
            String[] var4 = modules;
            int var5 = modules.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               String module = var4[var6];
               CheckClassAdapter.checkFullyQualifiedName(53, module, "module open to");
            }
         }

         super.visitOpen(packaze, access, modules);
      }
   }

   public void visitUse(String service) {
      this.checkVisitEndNotCalled();
      CheckMethodAdapter.checkInternalName(53, service, "service");
      this.usedServices.checkNameNotAlreadyDeclared(service);
      super.visitUse(service);
   }

   public void visitProvide(String service, String... providers) {
      this.checkVisitEndNotCalled();
      CheckMethodAdapter.checkInternalName(53, service, "service");
      this.providedServices.checkNameNotAlreadyDeclared(service);
      if (providers != null && providers.length != 0) {
         String[] var3 = providers;
         int var4 = providers.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String provider = var3[var5];
            CheckMethodAdapter.checkInternalName(53, provider, "provider");
         }

         super.visitProvide(service, providers);
      } else {
         throw new IllegalArgumentException("Providers cannot be null or empty");
      }
   }

   public void visitEnd() {
      this.checkVisitEndNotCalled();
      this.visitEndCalled = true;
      super.visitEnd();
   }

   private void checkVisitEndNotCalled() {
      if (this.visitEndCalled) {
         throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
      }
   }

   private static class NameSet {
      private final String type;
      private final HashSet names;

      NameSet(String type) {
         this.type = type;
         this.names = new HashSet();
      }

      void checkNameNotAlreadyDeclared(String name) {
         if (!this.names.add(name)) {
            throw new IllegalArgumentException(this.type + " '" + name + "' already declared");
         }
      }
   }
}

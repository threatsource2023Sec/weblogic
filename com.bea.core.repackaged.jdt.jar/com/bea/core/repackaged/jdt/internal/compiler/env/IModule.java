package com.bea.core.repackaged.jdt.internal.compiler.env;

import java.util.jar.Manifest;

public interface IModule {
   IModuleReference[] NO_MODULE_REFS = new IModuleReference[0];
   IPackageExport[] NO_EXPORTS = new IPackageExport[0];
   char[][] NO_USES = new char[0][];
   IService[] NO_PROVIDES = new IService[0];
   IModule[] NO_MODULES = new IModule[0];
   IPackageExport[] NO_OPENS = new IPackageExport[0];
   String MODULE_INFO = "module-info";
   String MODULE_INFO_JAVA = "module-info.java";
   String MODULE_INFO_CLASS = "module-info.class";

   char[] name();

   IModuleReference[] requires();

   IPackageExport[] exports();

   char[][] uses();

   IService[] provides();

   IPackageExport[] opens();

   default boolean isAutomatic() {
      return false;
   }

   default boolean isAutoNameFromManifest() {
      return false;
   }

   boolean isOpen();

   static IModule createAutomatic(char[] moduleName, boolean fromManifest) {
      final class AutoModule implements IModule {
         char[] name;
         boolean nameFromManifest;

         public AutoModule(char[] name, boolean nameFromManifest) {
            this.name = name;
            this.nameFromManifest = nameFromManifest;
         }

         public char[] name() {
            return this.name;
         }

         public IModuleReference[] requires() {
            return IModule.NO_MODULE_REFS;
         }

         public IPackageExport[] exports() {
            return IModule.NO_EXPORTS;
         }

         public char[][] uses() {
            return IModule.NO_USES;
         }

         public IService[] provides() {
            return IModule.NO_PROVIDES;
         }

         public IPackageExport[] opens() {
            return NO_OPENS;
         }

         public boolean isAutomatic() {
            return true;
         }

         public boolean isAutoNameFromManifest() {
            return this.nameFromManifest;
         }

         public boolean isOpen() {
            return false;
         }
      }

      return new AutoModule(moduleName, fromManifest);
   }

   static IModule createAutomatic(String fileName, boolean isFile, Manifest manifest) {
      boolean fromManifest = true;
      char[] inferredName = AutomaticModuleNaming.determineAutomaticModuleNameFromManifest(manifest);
      if (inferredName == null) {
         fromManifest = false;
         inferredName = AutomaticModuleNaming.determineAutomaticModuleNameFromFileName(fileName, true, isFile);
      }

      return createAutomatic(inferredName, fromManifest);
   }

   public interface IModuleReference {
      char[] name();

      default boolean isTransitive() {
         return (this.getModifiers() & 32) != 0;
      }

      int getModifiers();

      default boolean isStatic() {
         return (this.getModifiers() & 64) != 0;
      }
   }

   public interface IPackageExport {
      char[] name();

      char[][] targets();

      default boolean isQualified() {
         char[][] targets = this.targets();
         return targets != null && targets.length > 0;
      }
   }

   public interface IService {
      char[] name();

      char[][] with();
   }
}

package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModuleAwareNameEnvironment;
import java.util.HashMap;
import java.util.stream.Stream;

public class BinaryModuleBinding extends ModuleBinding {
   private IModule.IPackageExport[] unresolvedExports;
   private IModule.IPackageExport[] unresolvedOpens;
   private char[][] unresolvedUses;
   private IModule.IService[] unresolvedProvides;

   public static ModuleBinding create(IModule module, LookupEnvironment existingEnvironment) {
      return (ModuleBinding)(module.isAutomatic() ? new AutomaticModuleBinding(module, existingEnvironment) : new BinaryModuleBinding((IBinaryModule)module, existingEnvironment));
   }

   private BinaryModuleBinding(IBinaryModule module, LookupEnvironment existingEnvironment) {
      super(module.name(), existingEnvironment);
      existingEnvironment.root.knownModules.put(this.moduleName, this);
      this.cachePartsFrom(module);
   }

   void cachePartsFrom(IBinaryModule module) {
      if (module.isOpen()) {
         this.modifiers |= 32;
      }

      this.tagBits |= module.getTagBits();
      IModule.IModuleReference[] requiresReferences = module.requires();
      this.requires = new ModuleBinding[requiresReferences.length];
      this.requiresTransitive = new ModuleBinding[requiresReferences.length];
      int count = 0;
      int transitiveCount = 0;

      for(int i = 0; i < requiresReferences.length; ++i) {
         ModuleBinding requiredModule = this.environment.getModule(requiresReferences[i].name());
         if (requiredModule != null) {
            this.requires[count++] = requiredModule;
            if (requiresReferences[i].isTransitive()) {
               this.requiresTransitive[transitiveCount++] = requiredModule;
            }
         }
      }

      if (count < this.requires.length) {
         System.arraycopy(this.requires, 0, this.requires = new ModuleBinding[count], 0, count);
      }

      if (transitiveCount < this.requiresTransitive.length) {
         System.arraycopy(this.requiresTransitive, 0, this.requiresTransitive = new ModuleBinding[transitiveCount], 0, transitiveCount);
      }

      this.unresolvedExports = module.exports();
      this.unresolvedOpens = module.opens();
      this.unresolvedUses = module.uses();
      this.unresolvedProvides = module.provides();
      if (this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
         this.scanForNullDefaultAnnotation(module);
      }

      if ((this.tagBits & 70368744177664L) != 0L || this.environment.globalOptions.storeAnnotations) {
         this.setAnnotations(BinaryTypeBinding.createAnnotations(module.getAnnotations(), this.environment, (char[][][])null), true);
      }

   }

   private void scanForNullDefaultAnnotation(IBinaryModule binaryModule) {
      char[][] nonNullByDefaultAnnotationName = this.environment.getNonNullByDefaultAnnotationName();
      if (nonNullByDefaultAnnotationName != null) {
         IBinaryAnnotation[] annotations = binaryModule.getAnnotations();
         if (annotations != null) {
            int nullness = 0;
            int length = annotations.length;

            for(int i = 0; i < length; ++i) {
               char[] annotationTypeName = annotations[i].getTypeName();
               if (annotationTypeName[0] == 'L') {
                  int typeBit = this.environment.getNullAnnotationBit(BinaryTypeBinding.signature2qualifiedTypeName(annotationTypeName));
                  if (typeBit == 128) {
                     nullness |= BinaryTypeBinding.getNonNullByDefaultValue(annotations[i], this.environment);
                  }
               }
            }

            this.defaultNullness = nullness;
         }

      }
   }

   public PackageBinding[] getExports() {
      if (this.exportedPackages == null && this.unresolvedExports != null) {
         this.resolvePackages();
      }

      return super.getExports();
   }

   public PackageBinding[] getOpens() {
      if (this.openedPackages == null && this.unresolvedOpens != null) {
         this.resolvePackages();
      }

      return super.getOpens();
   }

   private void resolvePackages() {
      this.exportedPackages = new PackageBinding[this.unresolvedExports.length];
      int count = 0;

      int i;
      IModule.IPackageExport opens;
      PackageBinding declaredPackage;
      for(i = 0; i < this.unresolvedExports.length; ++i) {
         opens = this.unresolvedExports[i];
         declaredPackage = this.forcedGetExportedPackage(CharOperation.splitOn('.', opens.name()));
         if (declaredPackage != null) {
            this.exportedPackages[count++] = declaredPackage;
            if (declaredPackage instanceof SplitPackageBinding) {
               declaredPackage = ((SplitPackageBinding)declaredPackage).getIncarnation(this);
            }

            if (declaredPackage != null) {
               declaredPackage.isExported = Boolean.TRUE;
               this.recordExportRestrictions(declaredPackage, opens.targets());
            }
         }
      }

      if (count < this.exportedPackages.length) {
         System.arraycopy(this.exportedPackages, 0, this.exportedPackages = new PackageBinding[count], 0, count);
      }

      this.openedPackages = new PackageBinding[this.unresolvedOpens.length];
      count = 0;

      for(i = 0; i < this.unresolvedOpens.length; ++i) {
         opens = this.unresolvedOpens[i];
         declaredPackage = this.getVisiblePackage(CharOperation.splitOn('.', opens.name()));
         if (declaredPackage != null) {
            this.openedPackages[count++] = declaredPackage;
            if (declaredPackage instanceof SplitPackageBinding) {
               declaredPackage = ((SplitPackageBinding)declaredPackage).getIncarnation(this);
            }

            if (declaredPackage != null) {
               this.recordOpensRestrictions(declaredPackage, opens.targets());
            }
         }
      }

      if (count < this.openedPackages.length) {
         System.arraycopy(this.openedPackages, 0, this.openedPackages = new PackageBinding[count], 0, count);
      }

   }

   PackageBinding forcedGetExportedPackage(char[][] compoundName) {
      PackageBinding binding = this.getVisiblePackage(compoundName);
      if (binding != null) {
         return binding;
      } else if (compoundName.length > 1) {
         PackageBinding parent = this.forcedGetExportedPackage(CharOperation.subarray((char[][])compoundName, 0, compoundName.length - 1));
         binding = new PackageBinding(compoundName, parent, this.environment, this);
         parent.addPackage(binding, this);
         return binding;
      } else {
         binding = new PackageBinding(compoundName[0], this.environment, this);
         this.addPackage(binding, true);
         return binding;
      }
   }

   public TypeBinding[] getUses() {
      if (this.uses == null) {
         this.uses = new TypeBinding[this.unresolvedUses.length];

         for(int i = 0; i < this.unresolvedUses.length; ++i) {
            this.uses[i] = this.environment.getType(CharOperation.splitOn('.', this.unresolvedUses[i]), this);
         }
      }

      return super.getUses();
   }

   public TypeBinding[] getServices() {
      if (this.services == null) {
         this.resolveServices();
      }

      return super.getServices();
   }

   public TypeBinding[] getImplementations(TypeBinding binding) {
      if (this.implementations == null) {
         this.resolveServices();
      }

      return super.getImplementations(binding);
   }

   private void resolveServices() {
      this.services = new TypeBinding[this.unresolvedProvides.length];
      this.implementations = new HashMap();

      for(int i = 0; i < this.unresolvedProvides.length; ++i) {
         this.services[i] = this.environment.getType(CharOperation.splitOn('.', this.unresolvedProvides[i].name()), this);
         char[][] implNames = this.unresolvedProvides[i].with();
         TypeBinding[] impls = new TypeBinding[implNames.length];

         for(int j = 0; j < implNames.length; ++j) {
            impls[j] = this.environment.getType(CharOperation.splitOn('.', implNames[j]), this);
         }

         this.implementations.put(this.services[i], impls);
      }

   }

   public AnnotationBinding[] getAnnotations() {
      return this.retrieveAnnotations(this);
   }

   private static class AutomaticModuleBinding extends ModuleBinding {
      boolean autoNameFromManifest;

      public AutomaticModuleBinding(IModule module, LookupEnvironment existingEnvironment) {
         super(module.name(), existingEnvironment);
         existingEnvironment.root.knownModules.put(this.moduleName, this);
         this.isAuto = true;
         this.autoNameFromManifest = module.isAutoNameFromManifest();
         this.requires = Binding.NO_MODULES;
         this.requiresTransitive = Binding.NO_MODULES;
         this.exportedPackages = Binding.NO_PACKAGES;
      }

      public boolean hasUnstableAutoName() {
         return !this.autoNameFromManifest;
      }

      public ModuleBinding[] getRequiresTransitive() {
         if (this.requiresTransitive == NO_MODULES) {
            char[][] autoModules = ((IModuleAwareNameEnvironment)this.environment.nameEnvironment).getAllAutomaticModules();
            this.requiresTransitive = (ModuleBinding[])Stream.of(autoModules).filter((name) -> {
               return !CharOperation.equals(name, this.moduleName);
            }).map((name) -> {
               return this.environment.getModule(name);
            }).filter((m) -> {
               return m != null;
            }).toArray((var0) -> {
               return new ModuleBinding[var0];
            });
         }

         return this.requiresTransitive;
      }

      public char[] nameForLookup() {
         return ANY_NAMED;
      }

      public char[] nameForCUCheck() {
         return this.moduleName;
      }
   }
}

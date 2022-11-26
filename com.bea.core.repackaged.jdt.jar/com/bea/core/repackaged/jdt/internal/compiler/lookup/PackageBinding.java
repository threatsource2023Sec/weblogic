package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModuleAwareNameEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.util.HashtableOfPackage;
import com.bea.core.repackaged.jdt.internal.compiler.util.HashtableOfType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;

public class PackageBinding extends Binding implements TypeConstants {
   public long tagBits;
   public char[][] compoundName;
   PackageBinding parent;
   ArrayList wrappingSplitPackageBindings;
   public LookupEnvironment environment;
   public HashtableOfType knownTypes;
   HashtableOfPackage knownPackages;
   private int defaultNullness;
   public ModuleBinding enclosingModule;
   Boolean isExported;

   protected PackageBinding() {
      this.tagBits = 0L;
      this.defaultNullness = 0;
   }

   public PackageBinding(char[] topLevelPackageName, LookupEnvironment environment, ModuleBinding enclosingModule) {
      this(new char[][]{topLevelPackageName}, (PackageBinding)null, environment, enclosingModule);
   }

   public PackageBinding(char[][] compoundName, PackageBinding parent, LookupEnvironment environment, ModuleBinding enclosingModule) {
      this.tagBits = 0L;
      this.defaultNullness = 0;
      this.compoundName = compoundName;
      this.parent = parent;
      this.environment = environment;
      this.knownTypes = null;
      this.knownPackages = new HashtableOfPackage(3);
      if (compoundName != CharOperation.NO_CHAR_CHAR) {
         this.checkIfNullAnnotationPackage();
      }

      if (enclosingModule != null) {
         this.enclosingModule = enclosingModule;
      } else if (parent != null) {
         this.enclosingModule = parent.enclosingModule;
      }

      if (this.enclosingModule == null) {
         throw new IllegalStateException("Package should have an enclosing module");
      }
   }

   public PackageBinding(LookupEnvironment environment) {
      this(CharOperation.NO_CHAR_CHAR, (PackageBinding)null, environment, environment.module);
   }

   protected void addNotFoundPackage(char[] simpleName) {
      if (!this.environment.suppressImportErrors) {
         this.knownPackages.put(simpleName, LookupEnvironment.TheNotFoundPackage);
      }

   }

   private void addNotFoundType(char[] simpleName) {
      if (!this.environment.suppressImportErrors) {
         if (this.knownTypes == null) {
            this.knownTypes = new HashtableOfType(25);
         }

         this.knownTypes.put(simpleName, LookupEnvironment.TheNotFoundType);
      }
   }

   PackageBinding addPackage(PackageBinding element, ModuleBinding module) {
      if ((element.tagBits & 128L) == 0L) {
         this.clearMissingTagBit();
      }

      this.knownPackages.put(element.compoundName[element.compoundName.length - 1], element);
      return element;
   }

   void addType(ReferenceBinding element) {
      if ((element.tagBits & 128L) == 0L) {
         this.clearMissingTagBit();
      }

      if (this.knownTypes == null) {
         this.knownTypes = new HashtableOfType(25);
      }

      char[] name = element.compoundName[element.compoundName.length - 1];
      ReferenceBinding priorType = this.knownTypes.getput(name, element);
      if (priorType != null && priorType.isUnresolvedType() && !element.isUnresolvedType()) {
         ((UnresolvedReferenceBinding)priorType).setResolvedType(element, this.environment);
      }

      if (this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled && (element.isAnnotationType() || element instanceof UnresolvedReferenceBinding)) {
         this.checkIfNullAnnotationType(element);
      }

      if (!element.isUnresolvedType() && this.wrappingSplitPackageBindings != null) {
         Iterator var5 = this.wrappingSplitPackageBindings.iterator();

         while(var5.hasNext()) {
            SplitPackageBinding splitPackageBinding = (SplitPackageBinding)var5.next();
            if (splitPackageBinding.knownTypes != null) {
               ReferenceBinding prior = splitPackageBinding.knownTypes.get(name);
               if (prior != null && prior.isUnresolvedType() && !element.isUnresolvedType()) {
                  ((UnresolvedReferenceBinding)prior).setResolvedType(element, this.environment);
                  splitPackageBinding.knownTypes.put(name, (ReferenceBinding)null);
               }
            }
         }
      }

   }

   ModuleBinding[] getDeclaringModules() {
      return new ModuleBinding[]{this.enclosingModule};
   }

   void clearMissingTagBit() {
      PackageBinding current = this;

      do {
         current.tagBits &= -129L;
      } while((current = current.parent) != null);

   }

   public char[] computeUniqueKey(boolean isLeaf) {
      return CharOperation.concatWith(this.compoundName, '/');
   }

   protected PackageBinding findPackage(char[] name, ModuleBinding module) {
      return module.getPackage(this.compoundName, name);
   }

   PackageBinding getPackage(char[] name, ModuleBinding mod) {
      PackageBinding binding = this.getPackage0(name);
      if (binding != null) {
         return binding == LookupEnvironment.TheNotFoundPackage ? null : binding;
      } else if ((binding = this.findPackage(name, mod)) != null) {
         return binding;
      } else {
         this.addNotFoundPackage(name);
         return null;
      }
   }

   PackageBinding getPackage0(char[] name) {
      return this.knownPackages.get(name);
   }

   PackageBinding getPackage0Any(char[] name) {
      return this.knownPackages.get(name);
   }

   ReferenceBinding getType(char[] name, ModuleBinding mod) {
      ReferenceBinding referenceBinding = this.getType0(name);
      if (referenceBinding == null && (referenceBinding = this.environment.askForType(this, name, mod)) == null) {
         this.addNotFoundType(name);
         return null;
      } else if (referenceBinding == LookupEnvironment.TheNotFoundType) {
         return null;
      } else {
         referenceBinding = (ReferenceBinding)BinaryTypeBinding.resolveType(referenceBinding, this.environment, false);
         if (referenceBinding.isNestedType()) {
            return new ProblemReferenceBinding(new char[][]{name}, referenceBinding, 4);
         } else {
            return (ReferenceBinding)(!mod.canAccess(this) ? new ProblemReferenceBinding(referenceBinding.compoundName, referenceBinding, 30) : referenceBinding);
         }
      }
   }

   ReferenceBinding getType0(char[] name) {
      return this.knownTypes == null ? null : this.knownTypes.get(name);
   }

   public Binding getTypeOrPackage(char[] name, ModuleBinding mod, boolean splitPackageAllowed) {
      ReferenceBinding problemBinding = null;
      ReferenceBinding referenceBinding = this.getType0(name);
      if (referenceBinding != null && referenceBinding != LookupEnvironment.TheNotFoundType) {
         referenceBinding = (ReferenceBinding)BinaryTypeBinding.resolveType(referenceBinding, this.environment, false);
         if (referenceBinding.isNestedType()) {
            return new ProblemReferenceBinding(new char[][]{name}, referenceBinding, 4);
         }

         boolean isSameModule = this instanceof SplitPackageBinding ? referenceBinding.module() == mod : this.enclosingModule == mod;
         if (!isSameModule && referenceBinding.isValidBinding() && !mod.canAccess(referenceBinding.fPackage)) {
            problemBinding = new ProblemReferenceBinding(referenceBinding.compoundName, referenceBinding, 30);
         } else if ((referenceBinding.tagBits & 128L) == 0L) {
            return referenceBinding;
         }
      }

      PackageBinding packageBinding = this.getPackage0(name);
      if (packageBinding != null && packageBinding != LookupEnvironment.TheNotFoundPackage) {
         return !splitPackageAllowed && packageBinding instanceof SplitPackageBinding ? ((SplitPackageBinding)packageBinding).getVisibleFor(mod, false) : packageBinding;
      } else {
         if (referenceBinding == null && problemBinding == null) {
            if ((referenceBinding = this.environment.askForType(this, name, mod)) != null) {
               if (referenceBinding.isNestedType()) {
                  return new ProblemReferenceBinding(new char[][]{name}, referenceBinding, 4);
               }

               if (!referenceBinding.isValidBinding() || mod.canAccess(referenceBinding.fPackage)) {
                  return referenceBinding;
               }

               problemBinding = new ProblemReferenceBinding(referenceBinding.compoundName, referenceBinding, 30);
            } else {
               this.addNotFoundType(name);
            }
         }

         if (packageBinding == null) {
            if ((packageBinding = this.findPackage(name, mod)) != null) {
               if (!splitPackageAllowed && packageBinding instanceof SplitPackageBinding) {
                  return ((SplitPackageBinding)packageBinding).getVisibleFor(mod, false);
               }

               return packageBinding;
            }

            if (referenceBinding != null && referenceBinding != LookupEnvironment.TheNotFoundType) {
               if (problemBinding != null) {
                  return problemBinding;
               }

               return referenceBinding;
            }

            this.addNotFoundPackage(name);
         }

         return problemBinding;
      }
   }

   public final boolean isViewedAsDeprecated() {
      if ((this.tagBits & 17179869184L) == 0L) {
         this.tagBits |= 17179869184L;
         if (this.compoundName != CharOperation.NO_CHAR_CHAR) {
            ReferenceBinding packageInfo = this.getType(TypeConstants.PACKAGE_INFO_NAME, this.enclosingModule);
            if (packageInfo != null) {
               packageInfo.initializeDeprecatedAnnotationTagBits();
               this.tagBits |= packageInfo.tagBits & 8646911250191613952L;
            }
         }
      }

      return (this.tagBits & 70368744177664L) != 0L;
   }

   public int getDefaultNullness() {
      return this.defaultNullness == 0 ? this.enclosingModule.getDefaultNullness() : this.defaultNullness;
   }

   public void setDefaultNullness(int nullness) {
      this.defaultNullness = nullness;
   }

   public Binding findDefaultNullnessTarget(Predicate predicate) {
      if (predicate.test(this.defaultNullness)) {
         return this;
      } else {
         return this.defaultNullness == 0 && predicate.test(this.enclosingModule.getDefaultNullness()) ? this.enclosingModule : null;
      }
   }

   public final int kind() {
      return 16;
   }

   public int problemId() {
      return (this.tagBits & 128L) != 0L ? 1 : 0;
   }

   void checkIfNullAnnotationPackage() {
      LookupEnvironment env = this.environment;
      if (env.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
         if (this.isPackageOfQualifiedTypeName(this.compoundName, env.getNullableAnnotationName())) {
            env.nullableAnnotationPackage = this;
         }

         if (this.isPackageOfQualifiedTypeName(this.compoundName, env.getNonNullAnnotationName())) {
            env.nonnullAnnotationPackage = this;
         }

         if (this.isPackageOfQualifiedTypeName(this.compoundName, env.getNonNullByDefaultAnnotationName())) {
            env.nonnullByDefaultAnnotationPackage = this;
         }
      }

   }

   private boolean isPackageOfQualifiedTypeName(char[][] packageName, char[][] typeName) {
      int length;
      if (typeName != null && (length = packageName.length) == typeName.length - 1) {
         for(int i = 0; i < length; ++i) {
            if (!CharOperation.equals(packageName[i], typeName[i])) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   void checkIfNullAnnotationType(ReferenceBinding type) {
      if (this.environment.nullableAnnotationPackage == this && CharOperation.equals(type.compoundName, this.environment.getNullableAnnotationName())) {
         type.typeBits |= 64;
         if (!(type instanceof UnresolvedReferenceBinding)) {
            this.environment.nullableAnnotationPackage = null;
         }
      } else if (this.environment.nonnullAnnotationPackage == this && CharOperation.equals(type.compoundName, this.environment.getNonNullAnnotationName())) {
         type.typeBits |= 32;
         if (!(type instanceof UnresolvedReferenceBinding)) {
            this.environment.nonnullAnnotationPackage = null;
         }
      } else if (this.environment.nonnullByDefaultAnnotationPackage == this && CharOperation.equals(type.compoundName, this.environment.getNonNullByDefaultAnnotationName())) {
         type.typeBits |= 128;
         if (!(type instanceof UnresolvedReferenceBinding)) {
            this.environment.nonnullByDefaultAnnotationPackage = null;
         }
      } else {
         type.typeBits |= this.environment.getNullAnnotationBit(type.compoundName);
      }

   }

   public char[] readableName() {
      return CharOperation.concatWith(this.compoundName, '.');
   }

   public String toString() {
      String str;
      if (this.compoundName == CharOperation.NO_CHAR_CHAR) {
         str = "The Default Package";
      } else {
         str = "package " + (this.compoundName != null ? CharOperation.toString(this.compoundName) : "UNNAMED");
      }

      if ((this.tagBits & 128L) != 0L) {
         str = str + "[MISSING]";
      }

      return str;
   }

   public boolean isDeclaredIn(ModuleBinding moduleBinding) {
      return this.enclosingModule == moduleBinding;
   }

   public boolean subsumes(PackageBinding binding) {
      return binding == this;
   }

   public boolean isExported() {
      if (this.isExported == null) {
         if (this.enclosingModule.isAuto) {
            this.isExported = Boolean.TRUE;
         } else {
            this.enclosingModule.getExports();
            if (this.isExported == null) {
               this.isExported = Boolean.FALSE;
            }
         }
      }

      return this.isExported == Boolean.TRUE;
   }

   public PackageBinding getVisibleFor(ModuleBinding module, boolean preferLocal) {
      return this;
   }

   public boolean hasCompilationUnit(boolean checkCUs) {
      if (this.knownTypes != null) {
         ReferenceBinding[] var5;
         int var4 = (var5 = this.knownTypes.valueTable).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            ReferenceBinding knownType = var5[var3];
            if (knownType != null && knownType != LookupEnvironment.TheNotFoundType && !knownType.isUnresolvedType()) {
               return true;
            }
         }
      }

      if (this.environment.useModuleSystem) {
         IModuleAwareNameEnvironment moduleEnv = (IModuleAwareNameEnvironment)this.environment.nameEnvironment;
         return moduleEnv.hasCompilationUnit(this.compoundName, this.enclosingModule.nameForCUCheck(), checkCUs);
      } else {
         return false;
      }
   }

   public void addWrappingSplitPackageBinding(SplitPackageBinding splitPackageBinding) {
      if (this.wrappingSplitPackageBindings == null) {
         this.wrappingSplitPackageBindings = new ArrayList();
      }

      this.wrappingSplitPackageBindings.add(splitPackageBinding);
   }
}

package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModuleAwareNameEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.env.IUpdatableModule;
import com.bea.core.repackaged.jdt.internal.compiler.util.HashtableOfPackage;
import com.bea.core.repackaged.jdt.internal.compiler.util.SimpleLookupTable;
import com.bea.core.repackaged.jdt.internal.compiler.util.SimpleSetOfCharArray;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModuleBinding extends Binding implements IUpdatableModule {
   public static final char[] UNNAMED = "".toCharArray();
   public static final char[] ALL_UNNAMED = "ALL-UNNAMED".toCharArray();
   public static final char[] ANY = "".toCharArray();
   public static final char[] ANY_NAMED = "".toCharArray();
   public char[] moduleName;
   protected ModuleBinding[] requires;
   protected ModuleBinding[] requiresTransitive;
   protected PackageBinding[] exportedPackages;
   private Map exportRestrictions;
   protected PackageBinding[] openedPackages;
   private Map openRestrictions;
   protected TypeBinding[] uses;
   protected TypeBinding[] services;
   public Map implementations;
   public char[] mainClassName;
   private SimpleSetOfCharArray packageNames;
   public int modifiers;
   public LookupEnvironment environment;
   public long tagBits;
   public int defaultNullness;
   ModuleBinding[] requiredModules;
   boolean isAuto;
   private boolean[] isComplete;
   private Set transitiveRequires;
   boolean isPackageLookupActive;
   SimpleLookupTable storedAnnotations;
   public HashtableOfPackage declaredPackages;

   private ModuleBinding(LookupEnvironment env) {
      this.defaultNullness = 0;
      this.requiredModules = null;
      this.isAuto = false;
      this.isComplete = new boolean[IUpdatableModule.UpdateKind.values().length];
      this.isPackageLookupActive = false;
      this.storedAnnotations = null;
      this.moduleName = UNNAMED;
      this.environment = env;
      this.requires = Binding.NO_MODULES;
      this.requiresTransitive = Binding.NO_MODULES;
      this.exportedPackages = Binding.NO_PACKAGES;
      this.openedPackages = Binding.NO_PACKAGES;
      this.declaredPackages = new HashtableOfPackage(0);
      Arrays.fill(this.isComplete, true);
   }

   ModuleBinding(char[] moduleName) {
      this.defaultNullness = 0;
      this.requiredModules = null;
      this.isAuto = false;
      this.isComplete = new boolean[IUpdatableModule.UpdateKind.values().length];
      this.isPackageLookupActive = false;
      this.storedAnnotations = null;
      this.moduleName = moduleName;
      this.requires = Binding.NO_MODULES;
      this.requiresTransitive = Binding.NO_MODULES;
      this.exportedPackages = Binding.NO_PACKAGES;
      this.openedPackages = Binding.NO_PACKAGES;
      this.uses = Binding.NO_TYPES;
      this.services = Binding.NO_TYPES;
      this.declaredPackages = new HashtableOfPackage(5);
   }

   protected ModuleBinding(char[] moduleName, LookupEnvironment existingEnvironment) {
      this.defaultNullness = 0;
      this.requiredModules = null;
      this.isAuto = false;
      this.isComplete = new boolean[IUpdatableModule.UpdateKind.values().length];
      this.isPackageLookupActive = false;
      this.storedAnnotations = null;
      this.moduleName = moduleName;
      this.requires = Binding.NO_MODULES;
      this.requiresTransitive = Binding.NO_MODULES;
      this.environment = new LookupEnvironment(existingEnvironment.root, this);
      this.declaredPackages = new HashtableOfPackage(5);
   }

   public PackageBinding[] getExports() {
      this.completeIfNeeded(IUpdatableModule.UpdateKind.PACKAGE);
      return this.exportedPackages;
   }

   public String[] getExportRestrictions(PackageBinding pack) {
      this.completeIfNeeded(IUpdatableModule.UpdateKind.PACKAGE);
      if (this.exportRestrictions != null) {
         SimpleSetOfCharArray set = (SimpleSetOfCharArray)this.exportRestrictions.get(pack);
         if (set != null) {
            char[][] names = new char[set.elementSize][];
            set.asArray(names);
            return CharOperation.charArrayToStringArray(names);
         }
      }

      return CharOperation.NO_STRINGS;
   }

   public PackageBinding[] getOpens() {
      this.completeIfNeeded(IUpdatableModule.UpdateKind.PACKAGE);
      return this.openedPackages;
   }

   public String[] getOpenRestrictions(PackageBinding pack) {
      this.completeIfNeeded(IUpdatableModule.UpdateKind.PACKAGE);
      if (this.openRestrictions != null) {
         SimpleSetOfCharArray set = (SimpleSetOfCharArray)this.openRestrictions.get(pack);
         if (set != null) {
            char[][] names = new char[set.elementSize][];
            set.asArray(names);
            return CharOperation.charArrayToStringArray(names);
         }
      }

      return CharOperation.NO_STRINGS;
   }

   public TypeBinding[] getImplementations(TypeBinding binding) {
      return this.implementations != null ? (TypeBinding[])this.implementations.get(binding) : null;
   }

   public ModuleBinding[] getRequires() {
      this.completeIfNeeded(IUpdatableModule.UpdateKind.MODULE);
      return this.requires;
   }

   public ModuleBinding[] getRequiresTransitive() {
      this.completeIfNeeded(IUpdatableModule.UpdateKind.MODULE);
      return this.requiresTransitive;
   }

   public TypeBinding[] getUses() {
      return this.uses;
   }

   public TypeBinding[] getServices() {
      return this.services;
   }

   private void completeIfNeeded(IUpdatableModule.UpdateKind kind) {
      if (!this.isComplete[kind.ordinal()]) {
         this.isComplete[kind.ordinal()] = true;
         if (this.environment.nameEnvironment instanceof IModuleAwareNameEnvironment) {
            ((IModuleAwareNameEnvironment)this.environment.nameEnvironment).applyModuleUpdates(this, kind);
         }
      }

   }

   public void addReads(char[] requiredModuleName) {
      ModuleBinding requiredModule = this.environment.getModule(requiredModuleName);
      if (requiredModule != null) {
         int len = this.requires.length;
         if (len == 0) {
            this.requires = new ModuleBinding[]{requiredModule};
         } else {
            System.arraycopy(this.requires, 0, this.requires = new ModuleBinding[len + 1], 0, len);
            this.requires[len] = requiredModule;
         }

         HashtableOfPackage knownPackages = this.environment.knownPackages;

         for(int i = 0; i < knownPackages.valueTable.length; ++i) {
            PackageBinding packageBinding = knownPackages.valueTable[i];
            if (packageBinding != null) {
               PackageBinding newBinding = requiredModule.getVisiblePackage(packageBinding.compoundName);
               newBinding = SplitPackageBinding.combine(newBinding, packageBinding, this);
               if (packageBinding != newBinding) {
                  knownPackages.valueTable[i] = newBinding;
                  if (this.declaredPackages.containsKey(newBinding.readableName())) {
                     this.declaredPackages.put(newBinding.readableName(), newBinding);
                  }
               }
            }
         }

      } else {
         this.environment.problemReporter.missingModuleAddReads(requiredModuleName);
      }
   }

   public void addExports(char[] packageName, char[][] targetModules) {
      PackageBinding declaredPackage = this.getVisiblePackage(CharOperation.splitOn('.', packageName));
      if (declaredPackage != null && declaredPackage.isValidBinding()) {
         this.addResolvedExport(declaredPackage, targetModules);
      }

   }

   public void setMainClassName(char[] mainClassName) {
      this.mainClassName = mainClassName;
   }

   public void setPackageNames(SimpleSetOfCharArray packageNames) {
      this.packageNames = packageNames;
   }

   public char[][] getPackageNamesForClassFile() {
      if (this.packageNames == null) {
         return null;
      } else {
         PackageBinding[] var4;
         int var3 = (var4 = this.exportedPackages).length;

         PackageBinding packageBinding;
         int var2;
         for(var2 = 0; var2 < var3; ++var2) {
            packageBinding = var4[var2];
            this.packageNames.add(packageBinding.readableName());
         }

         var3 = (var4 = this.openedPackages).length;

         for(var2 = 0; var2 < var3; ++var2) {
            packageBinding = var4[var2];
            this.packageNames.add(packageBinding.readableName());
         }

         if (this.implementations != null) {
            Iterator var8 = this.implementations.values().iterator();

            while(var8.hasNext()) {
               TypeBinding[] types = (TypeBinding[])var8.next();
               TypeBinding[] var6 = types;
               int var5 = types.length;

               for(int var10 = 0; var10 < var5; ++var10) {
                  TypeBinding typeBinding = var6[var10];
                  this.packageNames.add(((ReferenceBinding)typeBinding).fPackage.readableName());
               }
            }
         }

         return this.packageNames.values;
      }
   }

   public void addResolvedExport(PackageBinding declaredPackage, char[][] targetModules) {
      int len = this.exportedPackages.length;
      if (declaredPackage != null && declaredPackage.isValidBinding()) {
         if (len == 0) {
            this.exportedPackages = new PackageBinding[]{declaredPackage};
         } else {
            System.arraycopy(this.exportedPackages, 0, this.exportedPackages = new PackageBinding[len + 1], 0, len);
            this.exportedPackages[len] = declaredPackage;
         }

         declaredPackage.isExported = Boolean.TRUE;
         this.recordExportRestrictions(declaredPackage, targetModules);
      }
   }

   public void addResolvedOpens(PackageBinding declaredPackage, char[][] targetModules) {
      int len = this.openedPackages.length;
      if (declaredPackage != null && declaredPackage.isValidBinding()) {
         if (len == 0) {
            this.openedPackages = new PackageBinding[]{declaredPackage};
         } else {
            System.arraycopy(this.openedPackages, 0, this.openedPackages = new PackageBinding[len + 1], 0, len);
            this.openedPackages[len] = declaredPackage;
         }

         this.recordOpensRestrictions(declaredPackage, targetModules);
      }
   }

   protected void recordExportRestrictions(PackageBinding exportedPackage, char[][] targetModules) {
      if (targetModules != null && targetModules.length > 0) {
         SimpleSetOfCharArray targetModuleSet = null;
         if (this.exportRestrictions != null) {
            targetModuleSet = (SimpleSetOfCharArray)this.exportRestrictions.get(exportedPackage);
         } else {
            this.exportRestrictions = new HashMap();
         }

         if (targetModuleSet == null) {
            targetModuleSet = new SimpleSetOfCharArray(targetModules.length);
            this.exportRestrictions.put(exportedPackage, targetModuleSet);
         }

         for(int i = 0; i < targetModules.length; ++i) {
            targetModuleSet.add(targetModules[i]);
         }
      }

   }

   protected void recordOpensRestrictions(PackageBinding openedPackage, char[][] targetModules) {
      if (targetModules != null && targetModules.length > 0) {
         SimpleSetOfCharArray targetModuleSet = null;
         if (this.openRestrictions != null) {
            targetModuleSet = (SimpleSetOfCharArray)this.openRestrictions.get(openedPackage);
         } else {
            this.openRestrictions = new HashMap();
         }

         if (targetModuleSet == null) {
            targetModuleSet = new SimpleSetOfCharArray(targetModules.length);
            this.openRestrictions.put(openedPackage, targetModuleSet);
         }

         for(int i = 0; i < targetModules.length; ++i) {
            targetModuleSet.add(targetModules[i]);
         }
      }

   }

   Stream getRequiredModules(boolean transitiveOnly) {
      return Stream.of(transitiveOnly ? this.getRequiresTransitive() : this.getRequires());
   }

   private void collectAllDependencies(Set deps) {
      this.getRequiredModules(false).forEach((m) -> {
         if (deps.add(m)) {
            m.collectAllDependencies(deps);
         }

      });
   }

   private void collectTransitiveDependencies(Set deps) {
      this.getRequiredModules(true).forEach((m) -> {
         if (deps.add(m)) {
            m.collectTransitiveDependencies(deps);
         }

      });
   }

   public Supplier dependencyGraphCollector() {
      return () -> {
         return (Collection)this.getRequiredModules(false).collect(HashSet::new, (set, mod) -> {
            set.add(mod);
            mod.collectAllDependencies(set);
         }, AbstractCollection::addAll);
      };
   }

   public Supplier dependencyCollector() {
      return () -> {
         return (Collection)this.getRequiredModules(false).collect(HashSet::new, (set, mod) -> {
            set.add(mod);
            mod.collectTransitiveDependencies(set);
         }, AbstractCollection::addAll);
      };
   }

   public ModuleBinding[] getAllRequiredModules() {
      if (this.requiredModules != null) {
         return this.requiredModules;
      } else {
         Collection allRequires = (Collection)this.dependencyCollector().get();
         if (allRequires.contains(this)) {
            return NO_MODULES;
         } else {
            ModuleBinding javaBase = this.environment.javaBaseModule();
            if (!CharOperation.equals(this.moduleName, TypeConstants.JAVA_BASE) && javaBase != null && javaBase != this.environment.UnNamedModule) {
               allRequires.add(javaBase);
            }

            return this.requiredModules = allRequires.size() > 0 ? (ModuleBinding[])allRequires.toArray(new ModuleBinding[allRequires.size()]) : Binding.NO_MODULES;
         }
      }
   }

   public char[] name() {
      return this.moduleName;
   }

   public char[] nameForLookup() {
      return this.moduleName;
   }

   public char[] nameForCUCheck() {
      return this.nameForLookup();
   }

   public boolean isPackageExportedTo(PackageBinding pkg, ModuleBinding client) {
      PackageBinding resolved = null;
      if (pkg instanceof SplitPackageBinding) {
         resolved = ((SplitPackageBinding)pkg).getIncarnation(this);
      } else if (pkg.enclosingModule == this) {
         resolved = pkg;
      }

      if (resolved != null) {
         if (this.isAuto) {
            if (pkg.enclosingModule == this) {
               return true;
            }

            return false;
         }

         PackageBinding[] initializedExports = this.getExports();

         for(int i = 0; i < initializedExports.length; ++i) {
            PackageBinding export = initializedExports[i];
            if (export.subsumes(resolved)) {
               if (this.exportRestrictions != null) {
                  SimpleSetOfCharArray restrictions = (SimpleSetOfCharArray)this.exportRestrictions.get(export);
                  if (restrictions != null) {
                     if (client.isUnnamed()) {
                        return restrictions.includes(ALL_UNNAMED);
                     }

                     return restrictions.includes(client.name());
                  }
               }

               return true;
            }
         }
      }

      return false;
   }

   public PackageBinding getTopLevelPackage(char[] name) {
      PackageBinding binding = this.declaredPackages.get(name);
      if (binding != null) {
         return binding;
      } else {
         binding = this.environment.getPackage0(name);
         if (binding != null) {
            return binding;
         } else {
            binding = this.getVisiblePackage((PackageBinding)null, name, true);
            if (binding != null) {
               this.environment.knownPackages.put(name, binding);
               binding = this.addPackage(binding, false);
            } else {
               this.environment.knownPackages.put(name, LookupEnvironment.TheNotFoundPackage);
            }

            return binding;
         }
      }
   }

   PackageBinding getDeclaredPackage(char[][] parentName, char[] name) {
      char[][] subPkgCompoundName = CharOperation.arrayConcat(parentName, name);
      char[] fullFlatName = CharOperation.concatWith(subPkgCompoundName, '.');
      PackageBinding pkg = this.declaredPackages.get(fullFlatName);
      if (pkg != null) {
         return pkg;
      } else {
         PackageBinding parent = parentName.length == 0 ? null : this.getVisiblePackage(parentName);
         PackageBinding binding = new PackageBinding(subPkgCompoundName, parent, this.environment, this);
         this.declaredPackages.put(fullFlatName, binding);
         if (parent == null) {
            this.environment.knownPackages.put(name, binding);
         }

         return binding;
      }
   }

   PackageBinding getVisiblePackage(PackageBinding parent, char[] name, boolean considerRequiredModules) {
      char[][] parentName = parent == null ? CharOperation.NO_CHAR_CHAR : parent.compoundName;
      char[][] subPkgCompoundName = CharOperation.arrayConcat(parentName, name);
      char[] fullFlatName = CharOperation.concatWith(subPkgCompoundName, '.');
      PackageBinding pkg = this.declaredPackages.get(fullFlatName);
      if (pkg != null) {
         return pkg;
      } else {
         if (parent != null) {
            pkg = parent.getPackage0(name);
         } else {
            pkg = this.environment.getPackage0(name);
         }

         if (pkg != null) {
            return pkg == LookupEnvironment.TheNotFoundPackage ? null : this.addPackage(pkg, false);
         } else {
            PackageBinding binding = null;
            char[][] declaringModuleNames = null;
            boolean packageMayBeIncomplete = !considerRequiredModules;
            if (this.environment.useModuleSystem) {
               IModuleAwareNameEnvironment moduleEnv = (IModuleAwareNameEnvironment)this.environment.nameEnvironment;
               declaringModuleNames = moduleEnv.getUniqueModulesDeclaringPackage(parentName, name, this.nameForLookup());
               if (declaringModuleNames != null) {
                  if (CharOperation.containsEqual(declaringModuleNames, this.moduleName)) {
                     if (parent instanceof SplitPackageBinding) {
                        PackageBinding singleParent = ((SplitPackageBinding)parent).getIncarnation(this);
                        if (singleParent != null) {
                           binding = singleParent.getPackage0(name);
                        }
                     }

                     if (binding == null) {
                        binding = new PackageBinding(subPkgCompoundName, parent, this.environment, this);
                     }
                  } else if (considerRequiredModules) {
                     char[][] var15 = declaringModuleNames;
                     int var14 = declaringModuleNames.length;

                     for(int var13 = 0; var13 < var14; ++var13) {
                        char[] declaringModuleName = var15[var13];
                        ModuleBinding declaringModule = this.environment.root.getModule(declaringModuleName);
                        if (declaringModule != null) {
                           if (declaringModule.isPackageLookupActive) {
                              packageMayBeIncomplete = true;
                           } else {
                              PackageBinding declaredPackage = declaringModule.getDeclaredPackage(parentName, name);
                              if (declaredPackage != null) {
                                 if (declaredPackage.parent != null) {
                                    declaredPackage.parent.addPackage(declaredPackage, declaringModule);
                                 }

                                 parent = null;
                                 binding = SplitPackageBinding.combine(declaredPackage, binding, this);
                              }
                           }
                        }
                     }
                  }
               }
            } else if (this.environment.nameEnvironment.isPackage(parentName, name)) {
               binding = new PackageBinding(subPkgCompoundName, parent, this.environment, this);
            }

            if (considerRequiredModules) {
               if (parent != null && binding != null) {
                  parent.addPackage(binding, this);
               }

               binding = this.combineWithPackagesFromOtherRelevantModules(binding, subPkgCompoundName, declaringModuleNames);
            }

            if (binding != null && binding.isValidBinding()) {
               if (parentName.length == 0) {
                  binding.environment.knownPackages.put(name, binding);
               } else if (parent != null) {
                  binding = parent.addPackage(binding, this);
               }

               return packageMayBeIncomplete ? binding : this.addPackage(binding, false);
            } else {
               if (parent != null && !packageMayBeIncomplete && !(parent instanceof SplitPackageBinding)) {
                  if (binding == null) {
                     parent.addNotFoundPackage(name);
                  } else {
                     parent.knownPackages.put(name, binding);
                  }
               }

               return null;
            }
         }
      }
   }

   public PackageBinding getVisiblePackage(char[][] qualifiedPackageName) {
      return this.getVisiblePackage(qualifiedPackageName, true);
   }

   PackageBinding getVisiblePackage(char[][] qualifiedPackageName, boolean considerRequiredModules) {
      if (qualifiedPackageName != null && qualifiedPackageName.length != 0) {
         PackageBinding parent = this.getTopLevelPackage(qualifiedPackageName[0]);
         if (parent != null && parent != LookupEnvironment.TheNotFoundPackage) {
            for(int i = 1; i < qualifiedPackageName.length; ++i) {
               PackageBinding binding = this.getVisiblePackage(parent, qualifiedPackageName[i], considerRequiredModules);
               if (binding == null || binding == LookupEnvironment.TheNotFoundPackage) {
                  return null;
               }

               parent = binding;
            }

            return parent;
         } else {
            return null;
         }
      } else {
         return this.environment.defaultPackage;
      }
   }

   public PackageBinding getPackage(char[][] parentPackageName, char[] packageName) {
      if (parentPackageName != null && parentPackageName.length != 0) {
         PackageBinding binding = null;
         PackageBinding parent = this.getVisiblePackage(parentPackageName);
         if (parent != null && parent != LookupEnvironment.TheNotFoundPackage) {
            binding = this.getVisiblePackage(parent, packageName, true);
         }

         return binding != null ? this.addPackage(binding, false) : null;
      } else {
         return this.getVisiblePackage((PackageBinding)null, packageName, true);
      }
   }

   PackageBinding addPackage(PackageBinding packageBinding, boolean checkForSplit) {
      if (packageBinding.isDeclaredIn(this)) {
         char[] packageName = packageBinding.readableName();
         if (checkForSplit && this.environment.useModuleSystem) {
            char[][] declaringModuleNames = null;
            if (this.isUnnamed()) {
               IModuleAwareNameEnvironment moduleEnv = (IModuleAwareNameEnvironment)this.environment.nameEnvironment;
               declaringModuleNames = moduleEnv.getUniqueModulesDeclaringPackage((char[][])null, packageName, ANY);
            }

            packageBinding = this.combineWithPackagesFromOtherRelevantModules(packageBinding, packageBinding.compoundName, declaringModuleNames);
         }

         this.declaredPackages.put(packageName, packageBinding);
         if (packageBinding.parent == null) {
            this.environment.knownPackages.put(packageName, packageBinding);
         }
      }

      return packageBinding;
   }

   private PackageBinding combineWithPackagesFromOtherRelevantModules(PackageBinding currentBinding, char[][] compoundName, char[][] declaringModuleNames) {
      boolean save = this.isPackageLookupActive;
      this.isPackageLookupActive = true;

      try {
         char[] singleName = compoundName[compoundName.length - 1];
         PackageBinding parent = currentBinding != null ? currentBinding.parent : null;
         Iterator var8 = this.otherRelevantModules(declaringModuleNames).iterator();

         while(var8.hasNext()) {
            ModuleBinding moduleBinding = (ModuleBinding)var8.next();
            if (!moduleBinding.isPackageLookupActive) {
               PackageBinding nextBinding = parent != null ? moduleBinding.getVisiblePackage(parent, singleName, false) : moduleBinding.getVisiblePackage(compoundName, false);
               currentBinding = SplitPackageBinding.combine(nextBinding, currentBinding, this);
            }
         }

         PackageBinding var11 = currentBinding;
         return var11;
      } finally {
         this.isPackageLookupActive = save;
      }
   }

   List otherRelevantModules(char[][] declaringModuleNames) {
      return this.isUnnamed() && declaringModuleNames != null ? (List)Arrays.stream(declaringModuleNames).filter((modName) -> {
         return modName != UNNAMED;
      }).map((modName) -> {
         return this.environment.getModule(modName);
      }).filter(Objects::nonNull).collect(Collectors.toList()) : Arrays.asList(this.getAllRequiredModules());
   }

   public boolean canAccess(PackageBinding pkg) {
      if (pkg.isDeclaredIn(this)) {
         return true;
      } else {
         ModuleBinding[] var5;
         int var4 = (var5 = this.getAllRequiredModules()).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            ModuleBinding requiredModule = var5[var3];
            if (requiredModule.isPackageExportedTo(pkg, this)) {
               return true;
            }
         }

         return false;
      }
   }

   public char[] computeUniqueKey(boolean isLeaf) {
      return CharOperation.prepend('"', this.moduleName);
   }

   public int kind() {
      return 64;
   }

   public char[] readableName() {
      return this.moduleName;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer(30);
      if (this.isOpen()) {
         buffer.append("open ");
      }

      buffer.append("module " + new String(this.readableName()));
      int i;
      int var5;
      if (this.requires.length > 0) {
         buffer.append("\n/*    requires    */\n");

         for(i = 0; i < this.requires.length; ++i) {
            buffer.append("\n\t");
            if (this.requiresTransitive != null) {
               ModuleBinding[] var6;
               var5 = (var6 = this.requiresTransitive).length;

               for(int var4 = 0; var4 < var5; ++var4) {
                  ModuleBinding reqTrans = var6[var4];
                  if (reqTrans == this.requires[i]) {
                     buffer.append("transitive ");
                     break;
                  }
               }
            }

            buffer.append(this.requires[i].moduleName);
         }
      } else {
         buffer.append("\nNo Requires");
      }

      char[] targetModule;
      int var8;
      int var9;
      char[][] var10;
      PackageBinding opens;
      SimpleSetOfCharArray restrictions;
      String sep;
      char[][] allNames;
      if (this.exportedPackages != null && this.exportedPackages.length > 0) {
         buffer.append("\n/*    exports    */\n");

         for(i = 0; i < this.exportedPackages.length; ++i) {
            opens = this.exportedPackages[i];
            buffer.append("\n\t");
            if (opens == null) {
               buffer.append("<unresolved>");
            } else {
               buffer.append(opens.readableName());
               restrictions = this.exportRestrictions != null ? (SimpleSetOfCharArray)this.exportRestrictions.get(opens) : null;
               if (restrictions != null) {
                  buffer.append(" to ");
                  sep = "";
                  allNames = new char[restrictions.elementSize][];
                  restrictions.asArray(allNames);
                  var10 = allNames;
                  var9 = allNames.length;

                  for(var8 = 0; var8 < var9; ++var8) {
                     targetModule = var10[var8];
                     buffer.append(sep);
                     buffer.append(targetModule);
                     sep = ", ";
                  }
               }
            }
         }
      } else {
         buffer.append("\nNo Exports");
      }

      if (this.openedPackages != null && this.openedPackages.length > 0) {
         buffer.append("\n/*    exports    */\n");

         for(i = 0; i < this.openedPackages.length; ++i) {
            opens = this.openedPackages[i];
            buffer.append("\n\t");
            if (opens == null) {
               buffer.append("<unresolved>");
            } else {
               buffer.append(opens.readableName());
               restrictions = this.openRestrictions != null ? (SimpleSetOfCharArray)this.openRestrictions.get(opens) : null;
               if (restrictions != null) {
                  buffer.append(" to ");
                  sep = "";
                  allNames = new char[restrictions.elementSize][];
                  restrictions.asArray(allNames);
                  var10 = allNames;
                  var9 = allNames.length;

                  for(var8 = 0; var8 < var9; ++var8) {
                     targetModule = var10[var8];
                     buffer.append(sep);
                     buffer.append(targetModule);
                     sep = ", ";
                  }
               }
            }
         }
      } else {
         buffer.append("\nNo Opens");
      }

      if (this.uses != null && this.uses.length > 0) {
         buffer.append("\n/*    uses    /*\n");

         for(i = 0; i < this.uses.length; ++i) {
            buffer.append("\n\t");
            buffer.append(this.uses[i].debugName());
         }
      } else {
         buffer.append("\nNo Uses");
      }

      if (this.services != null && this.services.length > 0) {
         buffer.append("\n/*    Services    */\n");

         for(i = 0; i < this.services.length; ++i) {
            buffer.append("\n\t");
            buffer.append("provides ");
            buffer.append(this.services[i].debugName());
            buffer.append(" with ");
            if (this.implementations != null && this.implementations.containsKey(this.services[i])) {
               String sep = "";
               TypeBinding[] var18;
               int var17 = (var18 = (TypeBinding[])this.implementations.get(this.services[i])).length;

               for(var5 = 0; var5 < var17; ++var5) {
                  TypeBinding impl = var18[var5];
                  buffer.append(sep).append(impl.debugName());
                  sep = ", ";
               }
            } else {
               buffer.append("<missing implementations>");
            }
         }
      } else {
         buffer.append("\nNo Services");
      }

      return buffer.toString();
   }

   public boolean isUnnamed() {
      return false;
   }

   public boolean isOpen() {
      return (this.modifiers & 32) != 0;
   }

   public boolean isDeprecated() {
      return (this.tagBits & 70368744177664L) != 0L;
   }

   public boolean hasUnstableAutoName() {
      return false;
   }

   public boolean isTransitivelyRequired(ModuleBinding otherModule) {
      if (this.transitiveRequires == null) {
         Set transitiveDeps = new HashSet();
         this.collectTransitiveDependencies(transitiveDeps);
         this.transitiveRequires = transitiveDeps;
      }

      return this.transitiveRequires.contains(otherModule);
   }

   public int getDefaultNullness() {
      this.getAnnotationTagBits();
      return this.defaultNullness;
   }

   SimpleLookupTable storedAnnotations(boolean forceInitialize, boolean forceStore) {
      if (forceInitialize && this.storedAnnotations == null) {
         if (!this.environment.globalOptions.storeAnnotations && !forceStore) {
            return null;
         }

         this.storedAnnotations = new SimpleLookupTable(3);
      }

      return this.storedAnnotations;
   }

   public AnnotationHolder retrieveAnnotationHolder(Binding binding, boolean forceInitialization) {
      SimpleLookupTable store = this.storedAnnotations(forceInitialization, false);
      return store == null ? null : (AnnotationHolder)store.get(binding);
   }

   AnnotationBinding[] retrieveAnnotations(Binding binding) {
      AnnotationHolder holder = this.retrieveAnnotationHolder(binding, true);
      return holder == null ? Binding.NO_ANNOTATIONS : holder.getAnnotations();
   }

   public void setAnnotations(AnnotationBinding[] annotations, boolean forceStore) {
      this.storeAnnotations(this, annotations, forceStore);
   }

   void storeAnnotationHolder(Binding binding, AnnotationHolder holder) {
      SimpleLookupTable store;
      if (holder == null) {
         store = this.storedAnnotations(false, false);
         if (store != null) {
            store.removeKey(binding);
         }
      } else {
         store = this.storedAnnotations(true, false);
         if (store != null) {
            store.put(binding, holder);
         }
      }

   }

   void storeAnnotations(Binding binding, AnnotationBinding[] annotations, boolean forceStore) {
      AnnotationHolder holder = null;
      SimpleLookupTable store;
      if (annotations != null && annotations.length != 0) {
         store = this.storedAnnotations(true, forceStore);
         if (store == null) {
            return;
         }

         holder = (AnnotationHolder)store.get(binding);
         if (holder == null) {
            holder = new AnnotationHolder();
         }
      } else {
         store = this.storedAnnotations(false, forceStore);
         if (store != null) {
            holder = (AnnotationHolder)store.get(binding);
         }

         if (holder == null) {
            return;
         }
      }

      this.storeAnnotationHolder(binding, holder.setAnnotations(annotations));
   }

   // $FF: synthetic method
   ModuleBinding(LookupEnvironment var1, ModuleBinding var2) {
      this(var1);
   }

   public static class UnNamedModule extends ModuleBinding {
      private static final char[] UNNAMED_READABLE_NAME = "<unnamed>".toCharArray();

      UnNamedModule(LookupEnvironment env) {
         super((LookupEnvironment)env, (ModuleBinding)null);
      }

      public ModuleBinding[] getAllRequiredModules() {
         return Binding.NO_MODULES;
      }

      public boolean canAccess(PackageBinding pkg) {
         if (!(pkg instanceof SplitPackageBinding)) {
            ModuleBinding mod = pkg.enclosingModule;
            return mod != null && mod != this ? mod.isPackageExportedTo(pkg, this) : true;
         } else {
            Iterator var3 = ((SplitPackageBinding)pkg).incarnations.iterator();

            while(var3.hasNext()) {
               PackageBinding p = (PackageBinding)var3.next();
               if (this.canAccess(p)) {
                  return true;
               }
            }

            return false;
         }
      }

      public boolean isPackageExportedTo(PackageBinding pkg, ModuleBinding client) {
         return pkg.isDeclaredIn(this) && pkg.hasCompilationUnit(false);
      }

      public boolean isUnnamed() {
         return true;
      }

      public char[] nameForLookup() {
         return ANY;
      }

      public char[] nameForCUCheck() {
         return UNNAMED;
      }

      public char[] readableName() {
         return UNNAMED_READABLE_NAME;
      }

      public String toString() {
         return "The Unnamed Module";
      }
   }
}

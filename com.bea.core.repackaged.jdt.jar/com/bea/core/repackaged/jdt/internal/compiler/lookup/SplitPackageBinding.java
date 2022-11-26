package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SplitPackageBinding extends PackageBinding {
   Set declaringModules = new HashSet();
   public Set incarnations = new HashSet();

   public static PackageBinding combine(PackageBinding binding, PackageBinding previous, ModuleBinding primaryModule) {
      int prevRank = rank(previous);
      int curRank = rank(binding);
      if (prevRank < curRank) {
         return binding;
      } else if (prevRank > curRank) {
         return previous;
      } else if (previous == null) {
         return null;
      } else if (previous.subsumes(binding)) {
         return previous;
      } else if (binding.subsumes(previous)) {
         return binding;
      } else {
         SplitPackageBinding split = new SplitPackageBinding(previous, primaryModule);
         split.add(binding);
         return split;
      }
   }

   private static int rank(PackageBinding candidate) {
      if (candidate == null) {
         return 0;
      } else if (candidate == LookupEnvironment.TheNotFoundPackage) {
         return 1;
      } else {
         return !candidate.isValidBinding() ? 2 : 3;
      }
   }

   public SplitPackageBinding(PackageBinding initialBinding, ModuleBinding primaryModule) {
      super(initialBinding.compoundName, initialBinding.parent, primaryModule.environment, primaryModule);
      this.add(initialBinding);
   }

   public void add(PackageBinding packageBinding) {
      if (packageBinding instanceof SplitPackageBinding) {
         SplitPackageBinding split = (SplitPackageBinding)packageBinding;
         this.declaringModules.addAll(split.declaringModules);
         Iterator var4 = split.incarnations.iterator();

         while(var4.hasNext()) {
            PackageBinding incarnation = (PackageBinding)var4.next();
            if (this.incarnations.add(incarnation)) {
               incarnation.addWrappingSplitPackageBinding(this);
            }
         }
      } else {
         this.declaringModules.add(packageBinding.enclosingModule);
         if (this.incarnations.add(packageBinding)) {
            packageBinding.addWrappingSplitPackageBinding(this);
         }
      }

   }

   PackageBinding addPackage(PackageBinding element, ModuleBinding module) {
      char[] simpleName = element.compoundName[element.compoundName.length - 1];
      element = this.combineWithSiblings(element, simpleName, module);
      PackageBinding visible = this.knownPackages.get(simpleName);
      visible = combine(element, visible, this.enclosingModule);
      this.knownPackages.put(simpleName, visible);
      PackageBinding incarnation = this.getIncarnation(element.enclosingModule);
      if (incarnation != null) {
         incarnation.addPackage(element, module);
      }

      return element;
   }

   PackageBinding combineWithSiblings(PackageBinding childPackage, char[] name, ModuleBinding module) {
      ModuleBinding primaryModule = childPackage != null ? childPackage.enclosingModule : this.enclosingModule;
      boolean activeSave = primaryModule.isPackageLookupActive;
      primaryModule.isPackageLookupActive = true;

      try {
         Iterator var7 = this.incarnations.iterator();

         while(var7.hasNext()) {
            PackageBinding incarnation = (PackageBinding)var7.next();
            ModuleBinding moduleBinding = incarnation.enclosingModule;
            if (moduleBinding != module && !childPackage.isDeclaredIn(moduleBinding)) {
               PackageBinding next = moduleBinding.getVisiblePackage(incarnation, name, false);
               childPackage = combine(next, childPackage, primaryModule);
            }
         }

         PackageBinding var11 = childPackage;
         return var11;
      } finally {
         primaryModule.isPackageLookupActive = activeSave;
      }
   }

   ModuleBinding[] getDeclaringModules() {
      return (ModuleBinding[])this.declaringModules.toArray(new ModuleBinding[this.declaringModules.size()]);
   }

   PackageBinding getPackage0(char[] name) {
      PackageBinding knownPackage = super.getPackage0(name);
      if (knownPackage != null) {
         return knownPackage;
      } else {
         PackageBinding candidate = null;

         PackageBinding package0;
         for(Iterator var5 = this.incarnations.iterator(); var5.hasNext(); candidate = combine(package0, candidate, this.enclosingModule)) {
            PackageBinding incarnation = (PackageBinding)var5.next();
            package0 = incarnation.getPackage0(name);
            if (package0 == null) {
               return null;
            }
         }

         if (candidate != null) {
            this.knownPackages.put(name, candidate);
         }

         return candidate;
      }
   }

   PackageBinding getPackage0Any(char[] name) {
      PackageBinding knownPackage = super.getPackage0(name);
      if (knownPackage != null) {
         return knownPackage;
      } else {
         PackageBinding candidate = null;
         Iterator var5 = this.incarnations.iterator();

         while(var5.hasNext()) {
            PackageBinding incarnation = (PackageBinding)var5.next();
            PackageBinding package0 = incarnation.getPackage0(name);
            if (package0 != null) {
               candidate = combine(package0, candidate, this.enclosingModule);
            }
         }

         return candidate;
      }
   }

   protected PackageBinding findPackage(char[] name, ModuleBinding module) {
      Set candidates = new HashSet();
      Iterator var5 = this.declaringModules.iterator();

      while(var5.hasNext()) {
         ModuleBinding candidateModule = (ModuleBinding)var5.next();
         PackageBinding candidate = super.findPackage(name, candidateModule);
         if (candidate != null && candidate != LookupEnvironment.TheNotFoundPackage && (candidate.tagBits & 128L) == 0L) {
            candidates.add(candidate);
         }
      }

      int count = candidates.size();
      PackageBinding result = null;
      if (count == 1) {
         result = (PackageBinding)candidates.iterator().next();
      } else if (count > 1) {
         Iterator iterator = candidates.iterator();
         SplitPackageBinding split = new SplitPackageBinding((PackageBinding)iterator.next(), this.enclosingModule);

         while(iterator.hasNext()) {
            split.add((PackageBinding)iterator.next());
         }

         result = split;
      }

      if (result == null) {
         this.addNotFoundPackage(name);
      } else {
         this.addPackage((PackageBinding)result, module);
      }

      return (PackageBinding)result;
   }

   public PackageBinding getIncarnation(ModuleBinding requestedModule) {
      Iterator var3 = this.incarnations.iterator();

      while(var3.hasNext()) {
         PackageBinding incarnation = (PackageBinding)var3.next();
         if (incarnation.enclosingModule == requestedModule) {
            return incarnation;
         }
      }

      return null;
   }

   public boolean subsumes(PackageBinding binding) {
      if (!CharOperation.equals(this.compoundName, binding.compoundName)) {
         return false;
      } else {
         return binding instanceof SplitPackageBinding ? this.declaringModules.containsAll(((SplitPackageBinding)binding).declaringModules) : this.declaringModules.contains(binding.enclosingModule);
      }
   }

   ReferenceBinding getType0(char[] name) {
      ReferenceBinding knownType = super.getType0(name);
      if (knownType != null && !(knownType instanceof UnresolvedReferenceBinding)) {
         return knownType;
      } else {
         ReferenceBinding candidate = null;
         Iterator var5 = this.incarnations.iterator();

         while(var5.hasNext()) {
            PackageBinding incarnation = (PackageBinding)var5.next();
            ReferenceBinding next = incarnation.getType0(name);
            if (next != null && next.isValidBinding() && !(knownType instanceof UnresolvedReferenceBinding)) {
               if (candidate != null) {
                  return null;
               }

               candidate = next;
            }
         }

         if (candidate != null) {
            this.addType(candidate);
         }

         return candidate;
      }
   }

   ReferenceBinding getType0ForModule(ModuleBinding module, char[] name) {
      return this.declaringModules.contains(module) ? this.getIncarnation(module).getType0(name) : null;
   }

   ReferenceBinding getType(char[] name, ModuleBinding mod) {
      ReferenceBinding candidate = null;
      boolean accessible = false;
      Iterator var6 = this.incarnations.iterator();

      PackageBinding incarnation;
      ReferenceBinding type;
      do {
         while(true) {
            do {
               if (!var6.hasNext()) {
                  if (candidate != null && !accessible) {
                     return new ProblemReferenceBinding(candidate.compoundName, candidate, 30);
                  }

                  return candidate;
               }

               incarnation = (PackageBinding)var6.next();
               type = incarnation.getType(name, mod);
            } while(type == null);

            if (candidate != null && accessible) {
               break;
            }

            candidate = type;
            accessible = mod.canAccess(incarnation);
         }
      } while(!mod.canAccess(incarnation));

      return new ProblemReferenceBinding(type.compoundName, candidate, 3);
   }

   public boolean isDeclaredIn(ModuleBinding moduleBinding) {
      return this.declaringModules.contains(moduleBinding);
   }

   public PackageBinding getVisibleFor(ModuleBinding clientModule, boolean preferLocal) {
      int visibleCount = 0;
      PackageBinding unique = null;
      Iterator var6 = this.incarnations.iterator();

      while(var6.hasNext()) {
         PackageBinding incarnation = (PackageBinding)var6.next();
         if (incarnation.hasCompilationUnit(false)) {
            if (preferLocal && incarnation.enclosingModule == clientModule) {
               return incarnation;
            }

            if (clientModule.canAccess(incarnation)) {
               ++visibleCount;
               if (visibleCount > 1) {
                  return this;
               }

               unique = incarnation;
            }
         }
      }

      return unique;
   }

   public String toString() {
      StringBuilder buf = new StringBuilder(super.toString());
      buf.append(" (from ");
      String sep = "";

      for(Iterator var4 = this.declaringModules.iterator(); var4.hasNext(); sep = ", ") {
         ModuleBinding mod = (ModuleBinding)var4.next();
         buf.append(sep).append(mod.readableName());
      }

      buf.append(")");
      return buf.toString();
   }
}

package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CategorizedProblem;
import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.ClassFile;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.ReferenceContext;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.CompilationUnitScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SplitPackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortCompilation;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortCompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortMethod;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortType;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemReporter;
import com.bea.core.repackaged.jdt.internal.compiler.util.HashtableOfObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ModuleDeclaration extends ASTNode implements ReferenceContext {
   public ExportsStatement[] exports;
   public RequiresStatement[] requires;
   public UsesStatement[] uses;
   public ProvidesStatement[] services;
   public OpensStatement[] opens;
   public Annotation[] annotations;
   public int exportsCount;
   public int requiresCount;
   public int usesCount;
   public int servicesCount;
   public int opensCount;
   public SourceModuleBinding binding;
   public int declarationSourceStart;
   public int declarationSourceEnd;
   public int bodyStart;
   public int bodyEnd;
   public int modifiersSourceStart;
   public BlockScope scope;
   public char[][] tokens;
   public char[] moduleName;
   public long[] sourcePositions;
   public int modifiers = 0;
   boolean ignoreFurtherInvestigation;
   boolean hasResolvedModuleDirectives;
   boolean hasResolvedPackageDirectives;
   boolean hasResolvedTypeDirectives;
   CompilationResult compilationResult;

   public ModuleDeclaration(CompilationResult compilationResult, char[][] tokens, long[] positions) {
      this.compilationResult = compilationResult;
      this.exportsCount = 0;
      this.requiresCount = 0;
      this.tokens = tokens;
      this.moduleName = CharOperation.concatWith(tokens, '.');
      this.sourcePositions = positions;
      this.sourceEnd = (int)(positions[positions.length - 1] & -1L);
      this.sourceStart = (int)(positions[0] >>> 32);
   }

   public ModuleBinding setBinding(SourceModuleBinding sourceModuleBinding) {
      this.binding = sourceModuleBinding;
      return sourceModuleBinding;
   }

   public void checkAndSetModifiers() {
      int realModifiers = this.modifiers & '\uffff';
      int expectedModifiers = 4128;
      if ((realModifiers & ~expectedModifiers) != 0) {
         this.scope.problemReporter().illegalModifierForModule(this);
         realModifiers &= expectedModifiers;
      }

      int effectiveModifiers = '耀' | realModifiers;
      this.modifiers = this.binding.modifiers = effectiveModifiers;
   }

   public boolean isOpen() {
      return (this.modifiers & 32) != 0;
   }

   public void createScope(final Scope parentScope) {
      this.scope = new MethodScope(parentScope, (ReferenceContext)null, true) {
         public ProblemReporter problemReporter() {
            return parentScope.problemReporter();
         }

         public ReferenceContext referenceContext() {
            return ModuleDeclaration.this;
         }

         public boolean isModuleScope() {
            return true;
         }
      };
   }

   public void generateCode() {
      if ((this.bits & 8192) == 0) {
         this.bits |= 8192;
         if (!this.ignoreFurtherInvestigation) {
            try {
               LookupEnvironment env = this.scope.environment();
               ClassFile classFile = env.classFilePool.acquireForModule(this.binding, env.globalOptions);
               classFile.initializeForModule(this.binding);
               classFile.addModuleAttributes(this.binding, this.annotations, this.scope.referenceCompilationUnit());
               this.scope.referenceCompilationUnit().compilationResult.record(this.binding.moduleName, classFile);
            } catch (AbortType var3) {
               if (this.binding == null) {
                  return;
               }
            }

         }
      }
   }

   public void resolveModuleDirectives(CompilationUnitScope cuScope) {
      if (this.binding == null) {
         this.ignoreFurtherInvestigation = true;
      } else if (!this.hasResolvedModuleDirectives) {
         this.hasResolvedModuleDirectives = true;
         Set requiredModules = new HashSet();
         Set requiredTransitiveModules = new HashSet();

         for(int i = 0; i < this.requiresCount; ++i) {
            RequiresStatement ref = this.requires[i];
            if (ref != null && ref.resolve(cuScope) != null) {
               if (!requiredModules.add(ref.resolvedBinding)) {
                  cuScope.problemReporter().duplicateModuleReference(8389909, ref.module);
               }

               if (ref.isTransitive()) {
                  requiredTransitiveModules.add(ref.resolvedBinding);
               }

               Collection deps = (Collection)ref.resolvedBinding.dependencyGraphCollector().get();
               if (deps.contains(this.binding)) {
                  cuScope.problemReporter().cyclicModuleDependency(this.binding, ref.module);
                  requiredModules.remove(ref.module.binding);
               }
            }
         }

         this.binding.setRequires((ModuleBinding[])requiredModules.toArray(new ModuleBinding[requiredModules.size()]), (ModuleBinding[])requiredTransitiveModules.toArray(new ModuleBinding[requiredTransitiveModules.size()]));
      }
   }

   public void resolvePackageDirectives(CompilationUnitScope cuScope) {
      if (this.binding == null) {
         this.ignoreFurtherInvestigation = true;
      } else if (!this.hasResolvedPackageDirectives) {
         this.hasResolvedPackageDirectives = true;
         Set exportedPkgs = new HashSet();

         for(int i = 0; i < this.exportsCount; ++i) {
            ExportsStatement ref = this.exports[i];
            if (ref != null && ref.resolve(cuScope)) {
               if (!exportedPkgs.add(ref.resolvedPackage)) {
                  cuScope.problemReporter().invalidPackageReference(8389910, ref);
               }

               char[][] targets = null;
               if (ref.targets != null) {
                  targets = new char[ref.targets.length][];

                  for(int j = 0; j < targets.length; ++j) {
                     targets[j] = ref.targets[j].moduleName;
                  }
               }

               this.binding.addResolvedExport(ref.resolvedPackage, targets);
            }
         }

         HashtableOfObject openedPkgs = new HashtableOfObject();

         for(int i = 0; i < this.opensCount; ++i) {
            OpensStatement ref = this.opens[i];
            if (this.isOpen()) {
               cuScope.problemReporter().invalidOpensStatement(ref, this);
            } else {
               if (openedPkgs.containsKey(ref.pkgName)) {
                  cuScope.problemReporter().invalidPackageReference(8389921, ref);
               } else {
                  openedPkgs.put(ref.pkgName, ref);
                  ref.resolve(cuScope);
               }

               char[][] targets = null;
               if (ref.targets != null) {
                  targets = new char[ref.targets.length][];

                  for(int j = 0; j < targets.length; ++j) {
                     targets[j] = ref.targets[j].moduleName;
                  }
               }

               this.binding.addResolvedOpens(ref.resolvedPackage, targets);
            }
         }

      }
   }

   public void resolveTypeDirectives(CompilationUnitScope cuScope) {
      if (this.binding == null) {
         this.ignoreFurtherInvestigation = true;
      } else if (!this.hasResolvedTypeDirectives) {
         this.hasResolvedTypeDirectives = true;
         ASTNode.resolveAnnotations(this.scope, (Annotation[])this.annotations, (Binding)this.binding);
         Set allTypes = new HashSet();

         for(int i = 0; i < this.usesCount; ++i) {
            TypeBinding serviceBinding = this.uses[i].serviceInterface.resolveType(this.scope);
            if (serviceBinding != null && serviceBinding.isValidBinding()) {
               if (!serviceBinding.isClass() && !serviceBinding.isInterface() && !serviceBinding.isAnnotationType()) {
                  cuScope.problemReporter().invalidServiceRef(8389924, this.uses[i].serviceInterface);
               }

               if (!allTypes.add(this.uses[i].serviceInterface.resolvedType)) {
                  cuScope.problemReporter().duplicateTypeReference(8389911, this.uses[i].serviceInterface);
               }
            }
         }

         this.binding.setUses((TypeBinding[])allTypes.toArray(new TypeBinding[allTypes.size()]));
         Set interfaces = new HashSet();

         for(int i = 0; i < this.servicesCount; ++i) {
            this.services[i].resolve(this.scope);
            TypeBinding infBinding = this.services[i].serviceInterface.resolvedType;
            if (infBinding != null && infBinding.isValidBinding()) {
               if (!interfaces.add(this.services[i].serviceInterface.resolvedType)) {
                  cuScope.problemReporter().duplicateTypeReference(8389912, this.services[i].serviceInterface);
               }

               this.binding.setImplementations(infBinding, this.services[i].getResolvedImplementations());
            }
         }

         this.binding.setServices((TypeBinding[])interfaces.toArray(new TypeBinding[interfaces.size()]));
      }
   }

   public void analyseCode(CompilationUnitScope skope) {
      this.analyseModuleGraph(skope);
      this.analyseReferencedPackages(skope);
   }

   private void analyseReferencedPackages(CompilationUnitScope skope) {
      if (this.exports != null) {
         ExportsStatement[] var5;
         int var4 = (var5 = this.exports).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            ExportsStatement export = var5[var3];
            PackageBinding pb = export.resolvedPackage;
            if (pb != null) {
               if (pb instanceof SplitPackageBinding) {
                  pb = ((SplitPackageBinding)pb).getIncarnation(this.binding);
               }

               if (!pb.hasCompilationUnit(true)) {
                  skope.problemReporter().invalidPackageReference(8389919, export);
               }
            }
         }
      }

   }

   public void analyseModuleGraph(CompilationUnitScope skope) {
      if (this.requires != null) {
         Map pack2mods = new HashMap();
         ModuleBinding[] var6;
         int var5 = (var6 = this.binding.getAllRequiredModules()).length;

         int var4;
         int var9;
         for(var4 = 0; var4 < var5; ++var4) {
            ModuleBinding requiredModule = var6[var4];
            PackageBinding[] var10;
            var9 = (var10 = requiredModule.getExports()).length;

            for(int var8 = 0; var8 < var9; ++var8) {
               PackageBinding exportedPackage = var10[var8];
               exportedPackage = exportedPackage.getVisibleFor(requiredModule, true);
               if (this.binding.canAccess(exportedPackage)) {
                  String packName = String.valueOf(exportedPackage.readableName());
                  Set mods = (Set)pack2mods.get(packName);
                  if (mods == null) {
                     pack2mods.put(packName, mods = new HashSet());
                  }

                  ((Set)mods).add(requiredModule);
               }
            }
         }

         RequiresStatement[] var14;
         var5 = (var14 = this.requires).length;

         for(var4 = 0; var4 < var5; ++var4) {
            RequiresStatement requiresStat = var14[var4];
            ModuleBinding requiredModule = requiresStat.resolvedBinding;
            if (requiredModule != null) {
               if (requiredModule.isDeprecated()) {
                  skope.problemReporter().deprecatedModule(requiresStat.module, requiredModule);
               }

               this.analyseOneDependency(requiresStat, requiredModule, skope, pack2mods);
               if (requiresStat.isTransitive()) {
                  ModuleBinding[] var18;
                  int var17 = (var18 = requiredModule.getAllRequiredModules()).length;

                  for(var9 = 0; var9 < var17; ++var9) {
                     ModuleBinding secondLevelModule = var18[var9];
                     this.analyseOneDependency(requiresStat, secondLevelModule, skope, pack2mods);
                  }
               }
            }
         }
      }

   }

   private void analyseOneDependency(RequiresStatement requiresStat, ModuleBinding requiredModule, CompilationUnitScope skope, Map pack2mods) {
      PackageBinding[] var8;
      int var7 = (var8 = requiredModule.getExports()).length;

      for(int var6 = 0; var6 < var7; ++var6) {
         PackageBinding pack = var8[var6];
         Set mods = (Set)pack2mods.get(String.valueOf(pack.readableName()));
         if (mods != null && mods.size() > 1) {
            CompilerOptions compilerOptions = skope.compilerOptions();
            boolean inJdtDebugCompileMode = compilerOptions.enableJdtDebugCompileMode;
            if (!inJdtDebugCompileMode) {
               skope.problemReporter().conflictingPackagesFromModules(pack, mods, requiresStat.sourceStart, requiresStat.sourceEnd);
            }
         }
      }

   }

   public void traverse(ASTVisitor visitor, CompilationUnitScope unitScope) {
      visitor.visit(this, unitScope);
   }

   public StringBuffer printHeader(int indent, StringBuffer output) {
      if (this.annotations != null) {
         for(int i = 0; i < this.annotations.length; ++i) {
            this.annotations[i].print(indent, output);
            if (i != this.annotations.length - 1) {
               output.append(" ");
            }
         }

         output.append('\n');
      }

      if (this.isOpen()) {
         output.append("open ");
      }

      output.append("module ");
      output.append(CharOperation.charToString(this.moduleName));
      return output;
   }

   public StringBuffer printBody(int indent, StringBuffer output) {
      output.append(" {");
      int i;
      if (this.requires != null) {
         for(i = 0; i < this.requiresCount; ++i) {
            output.append('\n');
            printIndent(indent + 1, output);
            this.requires[i].print(0, output);
         }
      }

      if (this.exports != null) {
         for(i = 0; i < this.exportsCount; ++i) {
            output.append('\n');
            this.exports[i].print(indent + 1, output);
         }
      }

      if (this.opens != null) {
         for(i = 0; i < this.opensCount; ++i) {
            output.append('\n');
            this.opens[i].print(indent + 1, output);
         }
      }

      if (this.uses != null) {
         for(i = 0; i < this.usesCount; ++i) {
            output.append('\n');
            this.uses[i].print(indent + 1, output);
         }
      }

      if (this.servicesCount != 0) {
         for(i = 0; i < this.servicesCount; ++i) {
            output.append('\n');
            this.services[i].print(indent + 1, output);
         }
      }

      output.append('\n');
      return printIndent(indent, output).append('}');
   }

   public StringBuffer print(int indent, StringBuffer output) {
      printIndent(indent, output);
      this.printHeader(0, output);
      return this.printBody(indent, output);
   }

   public void abort(int abortLevel, CategorizedProblem problem) {
      switch (abortLevel) {
         case 2:
            throw new AbortCompilation(this.compilationResult, problem);
         case 4:
            throw new AbortCompilationUnit(this.compilationResult, problem);
         case 16:
            throw new AbortMethod(this.compilationResult, problem);
         default:
            throw new AbortType(this.compilationResult, problem);
      }
   }

   public CompilationResult compilationResult() {
      return this.compilationResult;
   }

   public CompilationUnitDeclaration getCompilationUnitDeclaration() {
      return this.scope.referenceCompilationUnit();
   }

   public boolean hasErrors() {
      return this.ignoreFurtherInvestigation;
   }

   public void tagAsHavingErrors() {
      this.ignoreFurtherInvestigation = true;
   }

   public void tagAsHavingIgnoredMandatoryErrors(int problemId) {
   }
}

package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.util.HashtableOfObject;

public abstract class PackageVisibilityStatement extends ModuleStatement {
   public ImportReference pkgRef;
   public ModuleReference[] targets;
   public char[] pkgName;
   public PackageBinding resolvedPackage;

   public PackageVisibilityStatement(ImportReference pkgRef, ModuleReference[] targets) {
      this.pkgRef = pkgRef;
      this.pkgName = CharOperation.concatWith(this.pkgRef.tokens, '.');
      this.targets = targets;
   }

   public boolean isQualified() {
      return this.targets != null && this.targets.length > 0;
   }

   public ModuleReference[] getTargetedModules() {
      return this.targets;
   }

   public boolean resolve(Scope scope) {
      boolean errorsExist = this.resolvePackageReference(scope) == null;
      if (this.isQualified()) {
         HashtableOfObject modules = new HashtableOfObject(this.targets.length);

         for(int i = 0; i < this.targets.length; ++i) {
            ModuleReference ref = this.targets[i];
            if (modules.containsKey(ref.moduleName)) {
               scope.problemReporter().duplicateModuleReference(8389922, ref);
               errorsExist = true;
            } else {
               ref.resolve(scope.compilationUnitScope());
               modules.put(ref.moduleName, ref);
            }
         }
      }

      return !errorsExist;
   }

   protected int computeSeverity(int problemId) {
      return 1;
   }

   protected PackageBinding resolvePackageReference(Scope scope) {
      if (this.resolvedPackage != null) {
         return this.resolvedPackage;
      } else {
         ModuleDeclaration exportingModule = scope.compilationUnitScope().referenceContext.moduleDeclaration;
         ModuleBinding src = exportingModule.binding;
         this.resolvedPackage = src != null ? src.getVisiblePackage(this.pkgRef.tokens) : null;
         int problemId = 8389919;
         if (this.resolvedPackage == null) {
            scope.problemReporter().invalidPackageReference(problemId, this, this.computeSeverity(problemId));
         } else if (!this.resolvedPackage.isDeclaredIn(src)) {
            this.resolvedPackage = null;
            scope.problemReporter().invalidPackageReference(problemId, this, this.computeSeverity(problemId));
         }

         return this.resolvedPackage;
      }
   }

   public StringBuffer print(int indent, StringBuffer output) {
      this.pkgRef.print(indent, output);
      if (this.isQualified()) {
         output.append(" to ");

         for(int i = 0; i < this.targets.length; ++i) {
            if (i > 0) {
               output.append(", ");
            }

            this.targets[i].print(0, output);
         }
      }

      return output;
   }
}

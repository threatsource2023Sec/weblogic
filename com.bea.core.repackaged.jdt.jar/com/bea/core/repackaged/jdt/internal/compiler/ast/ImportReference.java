package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.CompilationUnitScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SplitPackageBinding;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ImportReference extends ASTNode {
   public char[][] tokens;
   public long[] sourcePositions;
   public int declarationEnd;
   public int declarationSourceStart;
   public int declarationSourceEnd;
   public int modifiers;
   public Annotation[] annotations;
   public int trailingStarPosition;

   public ImportReference(char[][] tokens, long[] sourcePositions, boolean onDemand, int modifiers) {
      this.tokens = tokens;
      this.sourcePositions = sourcePositions;
      if (onDemand) {
         this.bits |= 131072;
      }

      this.sourceEnd = (int)(sourcePositions[sourcePositions.length - 1] & -1L);
      this.sourceStart = (int)(sourcePositions[0] >>> 32);
      this.modifiers = modifiers;
   }

   public boolean isStatic() {
      return (this.modifiers & 8) != 0;
   }

   public char[][] getImportName() {
      return this.tokens;
   }

   public void checkPackageConflict(CompilationUnitScope scope) {
      ModuleBinding module = scope.module();
      PackageBinding visiblePackage = module.getVisiblePackage(this.tokens);
      if (visiblePackage instanceof SplitPackageBinding) {
         Set declaringMods = new HashSet();
         Iterator var6 = ((SplitPackageBinding)visiblePackage).incarnations.iterator();

         while(var6.hasNext()) {
            PackageBinding incarnation = (PackageBinding)var6.next();
            if (incarnation.enclosingModule != module && module.canAccess(incarnation)) {
               declaringMods.add(incarnation.enclosingModule);
            }
         }

         if (!declaringMods.isEmpty()) {
            CompilerOptions compilerOptions = scope.compilerOptions();
            boolean inJdtDebugCompileMode = compilerOptions.enableJdtDebugCompileMode;
            if (!inJdtDebugCompileMode) {
               scope.problemReporter().conflictingPackagesFromOtherModules(this, declaringMods);
            }
         }
      }

   }

   public StringBuffer print(int indent, StringBuffer output) {
      return this.print(indent, output, true);
   }

   public StringBuffer print(int tab, StringBuffer output, boolean withOnDemand) {
      for(int i = 0; i < this.tokens.length; ++i) {
         if (i > 0) {
            output.append('.');
         }

         output.append(this.tokens[i]);
      }

      if (withOnDemand && (this.bits & 131072) != 0) {
         output.append(".*");
      }

      return output;
   }

   public void traverse(ASTVisitor visitor, CompilationUnitScope scope) {
      visitor.visit(this, scope);
      visitor.endVisit(this, scope);
   }
}

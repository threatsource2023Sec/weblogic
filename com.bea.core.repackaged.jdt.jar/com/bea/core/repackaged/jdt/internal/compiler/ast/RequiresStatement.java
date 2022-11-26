package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;

public class RequiresStatement extends ModuleStatement {
   public ModuleReference module;
   public ModuleBinding resolvedBinding;
   public int modifiers = 0;
   public int modifiersSourceStart;

   public RequiresStatement(ModuleReference module) {
      this.module = module;
   }

   public boolean isTransitive() {
      return (this.modifiers & 32) != 0;
   }

   public boolean isStatic() {
      return (this.modifiers & 64) != 0;
   }

   public StringBuffer print(int indent, StringBuffer output) {
      output.append("requires ");
      if (this.isTransitive()) {
         output.append("transitive ");
      }

      if (this.isStatic()) {
         output.append("static ");
      }

      this.module.print(indent, output);
      output.append(";");
      return output;
   }

   public ModuleBinding resolve(Scope scope) {
      if (this.resolvedBinding != null) {
         return this.resolvedBinding;
      } else {
         this.resolvedBinding = this.module.resolve(scope);
         if (scope != null) {
            if (this.resolvedBinding == null) {
               scope.problemReporter().invalidModule(this.module);
            } else if (this.resolvedBinding.hasUnstableAutoName()) {
               scope.problemReporter().autoModuleWithUnstableName(this.module);
            }
         }

         return this.resolvedBinding;
      }
   }
}

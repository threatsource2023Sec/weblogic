package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;

public class ModuleReference extends ASTNode {
   public char[][] tokens;
   public long[] sourcePositions;
   public char[] moduleName;
   public ModuleBinding binding = null;

   public ModuleReference(char[][] tokens, long[] sourcePositions) {
      this.tokens = tokens;
      this.sourcePositions = sourcePositions;
      this.sourceEnd = (int)(sourcePositions[sourcePositions.length - 1] & -1L);
      this.sourceStart = (int)(sourcePositions[0] >>> 32);
      this.moduleName = CharOperation.concatWith(tokens, '.');
   }

   public StringBuffer print(int indent, StringBuffer output) {
      for(int i = 0; i < this.tokens.length; ++i) {
         if (i > 0) {
            output.append('.');
         }

         output.append(this.tokens[i]);
      }

      return output;
   }

   public ModuleBinding resolve(Scope scope) {
      return scope != null && this.binding == null ? (this.binding = scope.environment().getModule(this.moduleName)) : this.binding;
   }
}

package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public abstract class Literal extends Expression {
   public Literal(int s, int e) {
      this.sourceStart = s;
      this.sourceEnd = e;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      return flowInfo;
   }

   public abstract void computeConstant();

   public abstract TypeBinding literalType(BlockScope var1);

   public StringBuffer printExpression(int indent, StringBuffer output) {
      return output.append(this.source());
   }

   public TypeBinding resolveType(BlockScope scope) {
      this.resolvedType = this.literalType(scope);
      this.computeConstant();
      if (this.constant == null) {
         scope.problemReporter().constantOutOfRange(this, this.resolvedType);
         this.constant = Constant.NotAConstant;
      }

      return this.resolvedType;
   }

   public abstract char[] source();
}

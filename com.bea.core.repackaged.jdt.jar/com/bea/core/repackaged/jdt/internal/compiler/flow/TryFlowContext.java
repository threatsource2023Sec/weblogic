package com.bea.core.repackaged.jdt.internal.compiler.flow;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;

public abstract class TryFlowContext extends FlowContext {
   public FlowContext outerTryContext;

   public TryFlowContext(FlowContext parent, ASTNode associatedNode) {
      super(parent, associatedNode, true);
   }

   public void markFinallyNullStatus(LocalVariableBinding local, int nullStatus) {
      if (this.outerTryContext != null) {
         this.outerTryContext.markFinallyNullStatus(local, nullStatus);
      }

      super.markFinallyNullStatus(local, nullStatus);
   }

   public void mergeFinallyNullInfo(FlowInfo flowInfo) {
      if (this.outerTryContext != null) {
         this.outerTryContext.mergeFinallyNullInfo(flowInfo);
      }

      super.mergeFinallyNullInfo(flowInfo);
   }
}

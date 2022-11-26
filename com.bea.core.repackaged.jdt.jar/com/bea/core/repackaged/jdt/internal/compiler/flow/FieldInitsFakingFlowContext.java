package com.bea.core.repackaged.jdt.internal.compiler.flow;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;

public class FieldInitsFakingFlowContext extends ExceptionHandlingFlowContext {
   public FieldInitsFakingFlowContext(FlowContext parent, ASTNode associatedNode, ReferenceBinding[] handledExceptions, FlowContext initializationParent, BlockScope scope, UnconditionalFlowInfo flowInfo) {
      super(parent, associatedNode, handledExceptions, initializationParent, scope, flowInfo);
   }
}

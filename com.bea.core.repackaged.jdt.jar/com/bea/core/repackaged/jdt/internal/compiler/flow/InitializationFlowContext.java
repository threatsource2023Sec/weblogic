package com.bea.core.repackaged.jdt.internal.compiler.flow;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class InitializationFlowContext extends ExceptionHandlingFlowContext {
   public int exceptionCount;
   public TypeBinding[] thrownExceptions = new TypeBinding[5];
   public ASTNode[] exceptionThrowers = new ASTNode[5];
   public FlowInfo[] exceptionThrowerFlowInfos = new FlowInfo[5];
   public FlowInfo initsBeforeContext;

   public InitializationFlowContext(FlowContext parent, ASTNode associatedNode, FlowInfo initsBeforeContext, FlowContext initializationParent, BlockScope scope) {
      super(parent, associatedNode, Binding.NO_EXCEPTIONS, initializationParent, scope, FlowInfo.DEAD_END);
      this.initsBeforeContext = initsBeforeContext;
   }

   public void checkInitializerExceptions(BlockScope currentScope, FlowContext initializerContext, FlowInfo flowInfo) {
      for(int i = 0; i < this.exceptionCount; ++i) {
         initializerContext.checkExceptionHandlers(this.thrownExceptions[i], this.exceptionThrowers[i], this.exceptionThrowerFlowInfos[i], currentScope);
      }

   }

   public FlowContext getInitializationContext() {
      return this;
   }

   public String individualToString() {
      StringBuffer buffer = new StringBuffer("Initialization flow context");

      for(int i = 0; i < this.exceptionCount; ++i) {
         buffer.append('[').append(this.thrownExceptions[i].readableName());
         buffer.append('-').append(this.exceptionThrowerFlowInfos[i].toString()).append(']');
      }

      return buffer.toString();
   }

   public void recordHandlingException(ReferenceBinding exceptionType, UnconditionalFlowInfo flowInfo, TypeBinding raisedException, TypeBinding caughtException, ASTNode invocationSite, boolean wasMasked) {
      int size = this.thrownExceptions.length;
      if (this.exceptionCount == size) {
         System.arraycopy(this.thrownExceptions, 0, this.thrownExceptions = new TypeBinding[size * 2], 0, size);
         System.arraycopy(this.exceptionThrowers, 0, this.exceptionThrowers = new ASTNode[size * 2], 0, size);
         System.arraycopy(this.exceptionThrowerFlowInfos, 0, this.exceptionThrowerFlowInfos = new FlowInfo[size * 2], 0, size);
      }

      this.thrownExceptions[this.exceptionCount] = raisedException;
      this.exceptionThrowers[this.exceptionCount] = invocationSite;
      this.exceptionThrowerFlowInfos[this.exceptionCount++] = flowInfo.copy();
   }
}

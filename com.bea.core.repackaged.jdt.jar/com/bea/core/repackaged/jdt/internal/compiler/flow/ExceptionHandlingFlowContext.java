package com.bea.core.repackaged.jdt.internal.compiler.flow;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Argument;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SubRoutineStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TryStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.UnionTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.ObjectCache;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.CatchParameterBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import java.util.ArrayList;

public class ExceptionHandlingFlowContext extends FlowContext {
   public static final int BitCacheSize = 32;
   public ReferenceBinding[] handledExceptions;
   int[] isReached;
   int[] isNeeded;
   UnconditionalFlowInfo[] initsOnExceptions;
   ObjectCache indexes;
   boolean isMethodContext;
   public UnconditionalFlowInfo initsOnReturn;
   public FlowContext initializationParent;
   public ArrayList extendedExceptions;
   private static final Argument[] NO_ARGUMENTS = new Argument[0];
   public Argument[] catchArguments;
   private int[] exceptionToCatchBlockMap;

   public ExceptionHandlingFlowContext(FlowContext parent, ASTNode associatedNode, ReferenceBinding[] handledExceptions, FlowContext initializationParent, BlockScope scope, UnconditionalFlowInfo flowInfo) {
      this(parent, associatedNode, handledExceptions, (int[])null, NO_ARGUMENTS, initializationParent, scope, flowInfo);
   }

   public ExceptionHandlingFlowContext(FlowContext parent, TryStatement tryStatement, ReferenceBinding[] handledExceptions, int[] exceptionToCatchBlockMap, FlowContext initializationParent, BlockScope scope, FlowInfo flowInfo) {
      this(parent, tryStatement, handledExceptions, exceptionToCatchBlockMap, tryStatement.catchArguments, initializationParent, scope, flowInfo.unconditionalInits());
      UnconditionalFlowInfo unconditionalCopy = flowInfo.unconditionalCopy();
      unconditionalCopy.iNBit = -1L;
      unconditionalCopy.iNNBit = -1L;
      unconditionalCopy.tagBits |= 64;
      this.initsOnFinally = unconditionalCopy;
   }

   ExceptionHandlingFlowContext(FlowContext parent, ASTNode associatedNode, ReferenceBinding[] handledExceptions, int[] exceptionToCatchBlockMap, Argument[] catchArguments, FlowContext initializationParent, BlockScope scope, UnconditionalFlowInfo flowInfo) {
      super(parent, associatedNode, true);
      this.indexes = new ObjectCache();
      this.isMethodContext = scope == scope.methodScope();
      this.handledExceptions = handledExceptions;
      this.catchArguments = catchArguments;
      this.exceptionToCatchBlockMap = exceptionToCatchBlockMap;
      int count = handledExceptions.length;
      int cacheSize = count / 32 + 1;
      this.isReached = new int[cacheSize];
      this.isNeeded = new int[cacheSize];
      this.initsOnExceptions = new UnconditionalFlowInfo[count];
      boolean markExceptionsAndThrowableAsReached = !this.isMethodContext || scope.compilerOptions().reportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable;

      for(int i = 0; i < count; ++i) {
         ReferenceBinding handledException = handledExceptions[i];
         int catchBlock = this.exceptionToCatchBlockMap != null ? this.exceptionToCatchBlockMap[i] : i;
         this.indexes.put(handledException, i);
         if (!handledException.isUncheckedException(true)) {
            this.initsOnExceptions[catchBlock] = FlowInfo.DEAD_END;
         } else {
            if (markExceptionsAndThrowableAsReached || handledException.id != 21 && handledException.id != 25) {
               int[] var10000 = this.isReached;
               var10000[i / 32] |= 1 << i % 32;
            }

            this.initsOnExceptions[catchBlock] = flowInfo.unconditionalCopy();
         }
      }

      if (!this.isMethodContext) {
         System.arraycopy(this.isReached, 0, this.isNeeded, 0, cacheSize);
      }

      this.initsOnReturn = FlowInfo.DEAD_END;
      this.initializationParent = initializationParent;
   }

   public void complainIfUnusedExceptionHandlers(AbstractMethodDeclaration method) {
      MethodScope scope = method.scope;
      if ((method.binding.modifiers & 805306368) == 0 || scope.compilerOptions().reportUnusedDeclaredThrownExceptionWhenOverriding) {
         TypeBinding[] docCommentReferences = null;
         int docCommentReferencesLength = 0;
         int i;
         if (scope.compilerOptions().reportUnusedDeclaredThrownExceptionIncludeDocCommentReference && method.javadoc != null && method.javadoc.exceptionReferences != null && (docCommentReferencesLength = method.javadoc.exceptionReferences.length) > 0) {
            docCommentReferences = new TypeBinding[docCommentReferencesLength];

            for(i = 0; i < docCommentReferencesLength; ++i) {
               docCommentReferences[i] = method.javadoc.exceptionReferences[i].resolvedType;
            }
         }

         i = 0;

         label39:
         for(int count = this.handledExceptions.length; i < count; ++i) {
            int index = this.indexes.get(this.handledExceptions[i]);
            if ((this.isReached[index / 32] & 1 << index % 32) == 0) {
               for(int j = 0; j < docCommentReferencesLength; ++j) {
                  if (TypeBinding.equalsEquals(docCommentReferences[j], this.handledExceptions[i])) {
                     continue label39;
                  }
               }

               scope.problemReporter().unusedDeclaredThrownException(this.handledExceptions[index], method, method.thrownExceptions[index]);
            }
         }

      }
   }

   public void complainIfUnusedExceptionHandlers(BlockScope scope, TryStatement tryStatement) {
      int index = 0;

      for(int count = this.handledExceptions.length; index < count; ++index) {
         int cacheIndex = index / 32;
         int bitMask = 1 << index % 32;
         if ((this.isReached[cacheIndex] & bitMask) == 0) {
            scope.problemReporter().unreachableCatchBlock(this.handledExceptions[index], this.getExceptionType(index));
         } else if ((this.isNeeded[cacheIndex] & bitMask) == 0) {
            scope.problemReporter().hiddenCatchBlock(this.handledExceptions[index], this.getExceptionType(index));
         }
      }

   }

   private ASTNode getExceptionType(int index) {
      if (this.exceptionToCatchBlockMap == null) {
         return this.catchArguments[index].type;
      } else {
         int catchBlock = this.exceptionToCatchBlockMap[index];
         ASTNode node = this.catchArguments[catchBlock].type;
         if (node instanceof UnionTypeReference) {
            TypeReference[] typeRefs = ((UnionTypeReference)node).typeReferences;
            int i = 0;

            for(int len = typeRefs.length; i < len; ++i) {
               TypeReference typeRef = typeRefs[i];
               if (TypeBinding.equalsEquals(typeRef.resolvedType, this.handledExceptions[index])) {
                  return typeRef;
               }
            }
         }

         return node;
      }
   }

   public FlowContext getInitializationContext() {
      return this.initializationParent;
   }

   public String individualToString() {
      StringBuffer buffer = new StringBuffer("Exception flow context");
      int length = this.handledExceptions.length;

      for(int i = 0; i < length; ++i) {
         int cacheIndex = i / 32;
         int bitMask = 1 << i % 32;
         buffer.append('[').append(this.handledExceptions[i].readableName());
         if ((this.isReached[cacheIndex] & bitMask) != 0) {
            if ((this.isNeeded[cacheIndex] & bitMask) == 0) {
               buffer.append("-masked");
            } else {
               buffer.append("-reached");
            }
         } else {
            buffer.append("-not reached");
         }

         int catchBlock = this.exceptionToCatchBlockMap != null ? this.exceptionToCatchBlockMap[i] : i;
         buffer.append('-').append(this.initsOnExceptions[catchBlock].toString()).append(']');
      }

      buffer.append("[initsOnReturn -").append(this.initsOnReturn.toString()).append(']');
      return buffer.toString();
   }

   public UnconditionalFlowInfo initsOnException(int index) {
      return this.initsOnExceptions[index];
   }

   public UnconditionalFlowInfo initsOnReturn() {
      return this.initsOnReturn;
   }

   public void mergeUnhandledException(TypeBinding var1) {
      // $FF: Couldn't be decompiled
   }

   public void recordHandlingException(ReferenceBinding exceptionType, UnconditionalFlowInfo flowInfo, TypeBinding raisedException, TypeBinding caughtException, ASTNode invocationSite, boolean wasAlreadyDefinitelyCaught) {
      int index = this.indexes.get(exceptionType);
      int cacheIndex = index / 32;
      int bitMask = 1 << index % 32;
      int[] var10000;
      if (!wasAlreadyDefinitelyCaught) {
         var10000 = this.isNeeded;
         var10000[cacheIndex] |= bitMask;
      }

      var10000 = this.isReached;
      var10000[cacheIndex] |= bitMask;
      int catchBlock = this.exceptionToCatchBlockMap != null ? this.exceptionToCatchBlockMap[index] : index;
      if (caughtException != null && this.catchArguments != null && this.catchArguments.length > 0 && !wasAlreadyDefinitelyCaught) {
         CatchParameterBinding catchParameter = (CatchParameterBinding)this.catchArguments[catchBlock].binding;
         catchParameter.setPreciseType(caughtException);
      }

      this.initsOnExceptions[catchBlock] = (this.initsOnExceptions[catchBlock].tagBits & 3) == 0 ? this.initsOnExceptions[catchBlock].mergedWith(flowInfo) : flowInfo.unconditionalCopy();
   }

   public void recordReturnFrom(UnconditionalFlowInfo flowInfo) {
      if ((flowInfo.tagBits & 1) == 0) {
         if ((this.initsOnReturn.tagBits & 1) == 0) {
            this.initsOnReturn = this.initsOnReturn.mergedWith(flowInfo);
         } else {
            this.initsOnReturn = (UnconditionalFlowInfo)flowInfo.copy();
         }
      }

   }

   public SubRoutineStatement subroutine() {
      if (this.associatedNode instanceof SubRoutineStatement) {
         return this.parent.subroutine() == this.associatedNode ? null : (SubRoutineStatement)this.associatedNode;
      } else {
         return null;
      }
   }
}

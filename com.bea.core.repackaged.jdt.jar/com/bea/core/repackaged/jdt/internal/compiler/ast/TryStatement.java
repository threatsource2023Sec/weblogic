package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.ConstantPool;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.ExceptionLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.MultiCatchExceptionLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.StackMapFrameCodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.ExceptionHandlingFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FinallyFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.flow.InsideSubRoutineFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ArrayBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.InvocationSite;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.VariableBinding;

public class TryStatement extends SubRoutineStatement {
   static final char[] SECRET_RETURN_ADDRESS_NAME = " returnAddress".toCharArray();
   static final char[] SECRET_ANY_HANDLER_NAME = " anyExceptionHandler".toCharArray();
   static final char[] SECRET_PRIMARY_EXCEPTION_VARIABLE_NAME = " primaryException".toCharArray();
   static final char[] SECRET_CAUGHT_THROWABLE_VARIABLE_NAME = " caughtThrowable".toCharArray();
   static final char[] SECRET_RETURN_VALUE_NAME = " returnValue".toCharArray();
   public Statement[] resources = new Statement[0];
   public Block tryBlock;
   public Block[] catchBlocks;
   public Argument[] catchArguments;
   public Block finallyBlock;
   BlockScope scope;
   public UnconditionalFlowInfo subRoutineInits;
   ReferenceBinding[] caughtExceptionTypes;
   boolean[] catchExits;
   BranchLabel subRoutineStartLabel;
   public LocalVariableBinding anyExceptionVariable;
   public LocalVariableBinding returnAddressVariable;
   public LocalVariableBinding secretReturnValue;
   ExceptionLabel[] declaredExceptionLabels;
   private Object[] reusableJSRTargets;
   private BranchLabel[] reusableJSRSequenceStartLabels;
   private int[] reusableJSRStateIndexes;
   private int reusableJSRTargetsCount = 0;
   private static final int NO_FINALLY = 0;
   private static final int FINALLY_SUBROUTINE = 1;
   private static final int FINALLY_DOES_NOT_COMPLETE = 2;
   private static final int FINALLY_INLINE = 3;
   int mergedInitStateIndex = -1;
   int preTryInitStateIndex = -1;
   int postTryInitStateIndex = -1;
   int[] postResourcesInitStateIndexes;
   int naturalExitMergeInitStateIndex = -1;
   int[] catchExitInitStateIndexes;
   private LocalVariableBinding primaryExceptionVariable;
   private LocalVariableBinding caughtThrowableVariable;
   private ExceptionLabel[] resourceExceptionLabels;
   private int[] caughtExceptionsCatchBlocks;

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      this.preTryInitStateIndex = currentScope.methodScope().recordInitializationStates(flowInfo);
      if (this.anyExceptionVariable != null) {
         this.anyExceptionVariable.useFlag = 1;
      }

      if (this.primaryExceptionVariable != null) {
         this.primaryExceptionVariable.useFlag = 1;
      }

      if (this.caughtThrowableVariable != null) {
         this.caughtThrowableVariable.useFlag = 1;
      }

      if (this.returnAddressVariable != null) {
         this.returnAddressVariable.useFlag = 1;
      }

      int resourcesLength = this.resources.length;
      if (resourcesLength > 0) {
         this.postResourcesInitStateIndexes = new int[resourcesLength];
      }

      FlowInfo catchInfo;
      if (this.subRoutineStartLabel == null) {
         if (flowContext instanceof FinallyFlowContext) {
            FinallyFlowContext finallyContext = (FinallyFlowContext)flowContext;
            finallyContext.outerTryContext = finallyContext.tryContext;
         }

         ExceptionHandlingFlowContext handlingContext = new ExceptionHandlingFlowContext(flowContext, this, this.caughtExceptionTypes, this.caughtExceptionsCatchBlocks, (FlowContext)null, this.scope, flowInfo);
         handlingContext.conditionalLevel = 0;
         FlowInfo tryInfo = flowInfo.copy();

         int i;
         for(i = 0; i < resourcesLength; ++i) {
            Statement resource = this.resources[i];
            tryInfo = resource.analyseCode(currentScope, handlingContext, (FlowInfo)tryInfo);
            this.postResourcesInitStateIndexes[i] = currentScope.methodScope().recordInitializationStates((FlowInfo)tryInfo);
            catchInfo = null;
            LocalVariableBinding localVariableBinding = null;
            TypeBinding resolvedType;
            if (resource instanceof LocalDeclaration) {
               localVariableBinding = ((LocalDeclaration)resource).binding;
               resolvedType = localVariableBinding.type;
            } else {
               if (resource instanceof NameReference && ((NameReference)resource).binding instanceof LocalVariableBinding) {
                  localVariableBinding = (LocalVariableBinding)((NameReference)resource).binding;
               }

               resolvedType = ((Expression)resource).resolvedType;
            }

            if (localVariableBinding != null) {
               localVariableBinding.useFlag = 1;
               if (localVariableBinding.closeTracker != null) {
                  localVariableBinding.closeTracker.withdraw();
                  localVariableBinding.closeTracker = null;
               }
            }

            MethodBinding closeMethod = this.findCloseMethod(resource, resolvedType);
            if (closeMethod != null && closeMethod.isValidBinding() && closeMethod.returnType.id == 6) {
               ReferenceBinding[] thrownExceptions = closeMethod.thrownExceptions;
               int j = 0;

               for(int length = thrownExceptions.length; j < length; ++j) {
                  handlingContext.checkExceptionHandlers(thrownExceptions[j], this.resources[i], (FlowInfo)tryInfo, currentScope, true);
               }
            }
         }

         if (!this.tryBlock.isEmptyBlock()) {
            tryInfo = this.tryBlock.analyseCode(currentScope, handlingContext, (FlowInfo)tryInfo);
            if ((((FlowInfo)tryInfo).tagBits & 1) != 0) {
               this.bits |= 536870912;
            }
         }

         if (resourcesLength > 0) {
            this.postTryInitStateIndex = currentScope.methodScope().recordInitializationStates((FlowInfo)tryInfo);

            for(i = 0; i < resourcesLength; ++i) {
               if (this.resources[i] instanceof LocalDeclaration) {
                  ((FlowInfo)tryInfo).resetAssignmentInfo(((LocalDeclaration)this.resources[i]).binding);
               }
            }
         }

         handlingContext.complainIfUnusedExceptionHandlers(this.scope, this);
         if (this.catchArguments != null) {
            this.catchExits = new boolean[i = this.catchBlocks.length];
            this.catchExitInitStateIndexes = new int[i];

            for(int i = 0; i < i; ++i) {
               catchInfo = this.prepareCatchInfo(flowInfo, handlingContext, (FlowInfo)tryInfo, i);
               ++flowContext.conditionalLevel;
               catchInfo = this.catchBlocks[i].analyseCode(currentScope, flowContext, catchInfo);
               --flowContext.conditionalLevel;
               this.catchExitInitStateIndexes[i] = currentScope.methodScope().recordInitializationStates(catchInfo);
               this.catchExits[i] = (catchInfo.tagBits & 1) != 0;
               tryInfo = ((FlowInfo)tryInfo).mergedWith(catchInfo.unconditionalInits());
            }
         }

         this.mergedInitStateIndex = currentScope.methodScope().recordInitializationStates((FlowInfo)tryInfo);
         flowContext.mergeFinallyNullInfo(handlingContext.initsOnFinally);
         return (FlowInfo)tryInfo;
      } else {
         InsideSubRoutineFlowContext insideSubContext = new InsideSubRoutineFlowContext(flowContext, this);
         if (flowContext instanceof FinallyFlowContext) {
            insideSubContext.outerTryContext = ((FinallyFlowContext)flowContext).tryContext;
         }

         ExceptionHandlingFlowContext handlingContext = new ExceptionHandlingFlowContext(insideSubContext, this, this.caughtExceptionTypes, this.caughtExceptionsCatchBlocks, (FlowContext)null, this.scope, flowInfo);
         insideSubContext.initsOnFinally = handlingContext.initsOnFinally;
         FinallyFlowContext finallyContext;
         UnconditionalFlowInfo subInfo = this.finallyBlock.analyseCode(currentScope, finallyContext = new FinallyFlowContext(flowContext, this.finallyBlock, handlingContext), flowInfo.nullInfoLessUnconditionalCopy()).unconditionalInits();
         handlingContext.conditionalLevel = 0;
         int i;
         if (subInfo == FlowInfo.DEAD_END) {
            this.bits |= 16384;
            this.scope.problemReporter().finallyMustCompleteNormally(this.finallyBlock);
         } else {
            catchInfo = subInfo.copy();
            this.tryBlock.scope.finallyInfo = catchInfo;
            if (this.catchBlocks != null) {
               for(i = 0; i < this.catchBlocks.length; ++i) {
                  this.catchBlocks[i].scope.finallyInfo = catchInfo;
               }
            }
         }

         this.subRoutineInits = subInfo;
         FlowInfo tryInfo = flowInfo.copy();

         for(i = 0; i < resourcesLength; ++i) {
            Statement resource = this.resources[i];
            tryInfo = resource.analyseCode(currentScope, handlingContext, (FlowInfo)tryInfo);
            this.postResourcesInitStateIndexes[i] = currentScope.methodScope().recordInitializationStates((FlowInfo)tryInfo);
            TypeBinding resolvedType = null;
            LocalVariableBinding localVariableBinding = null;
            if (resource instanceof LocalDeclaration) {
               localVariableBinding = ((LocalDeclaration)this.resources[i]).binding;
               resolvedType = localVariableBinding.type;
            } else {
               if (resource instanceof NameReference && ((NameReference)resource).binding instanceof LocalVariableBinding) {
                  localVariableBinding = (LocalVariableBinding)((NameReference)resource).binding;
               }

               resolvedType = ((Expression)resource).resolvedType;
            }

            if (localVariableBinding != null) {
               localVariableBinding.useFlag = 1;
               if (localVariableBinding.closeTracker != null) {
                  localVariableBinding.closeTracker.withdraw();
               }
            }

            MethodBinding closeMethod = this.findCloseMethod(resource, resolvedType);
            if (closeMethod != null && closeMethod.isValidBinding() && closeMethod.returnType.id == 6) {
               ReferenceBinding[] thrownExceptions = closeMethod.thrownExceptions;
               int j = 0;

               for(int length = thrownExceptions.length; j < length; ++j) {
                  handlingContext.checkExceptionHandlers(thrownExceptions[j], this.resources[i], (FlowInfo)tryInfo, currentScope, true);
               }
            }
         }

         if (!this.tryBlock.isEmptyBlock()) {
            tryInfo = this.tryBlock.analyseCode(currentScope, handlingContext, (FlowInfo)tryInfo);
            if ((((FlowInfo)tryInfo).tagBits & 1) != 0) {
               this.bits |= 536870912;
            }
         }

         if (resourcesLength > 0) {
            this.postTryInitStateIndex = currentScope.methodScope().recordInitializationStates((FlowInfo)tryInfo);

            for(i = 0; i < resourcesLength; ++i) {
               if (this.resources[i] instanceof LocalDeclaration) {
                  ((FlowInfo)tryInfo).resetAssignmentInfo(((LocalDeclaration)this.resources[i]).binding);
               }
            }
         }

         handlingContext.complainIfUnusedExceptionHandlers(this.scope, this);
         if (this.catchArguments != null) {
            this.catchExits = new boolean[i = this.catchBlocks.length];
            this.catchExitInitStateIndexes = new int[i];

            for(int i = 0; i < i; ++i) {
               FlowInfo catchInfo = this.prepareCatchInfo(flowInfo, handlingContext, (FlowInfo)tryInfo, i);
               insideSubContext.conditionalLevel = 1;
               catchInfo = this.catchBlocks[i].analyseCode(currentScope, insideSubContext, catchInfo);
               this.catchExitInitStateIndexes[i] = currentScope.methodScope().recordInitializationStates(catchInfo);
               this.catchExits[i] = (catchInfo.tagBits & 1) != 0;
               tryInfo = ((FlowInfo)tryInfo).mergedWith(catchInfo.unconditionalInits());
            }
         }

         finallyContext.complainOnDeferredChecks(((FlowInfo)((((FlowInfo)tryInfo).tagBits & 3) == 0 ? flowInfo.unconditionalCopy().addPotentialInitializationsFrom((FlowInfo)tryInfo).addPotentialInitializationsFrom(insideSubContext.initsOnReturn) : insideSubContext.initsOnReturn)).addNullInfoFrom(handlingContext.initsOnFinally), currentScope);
         flowContext.mergeFinallyNullInfo(handlingContext.initsOnFinally);
         this.naturalExitMergeInitStateIndex = currentScope.methodScope().recordInitializationStates((FlowInfo)tryInfo);
         if (subInfo == FlowInfo.DEAD_END) {
            this.mergedInitStateIndex = currentScope.methodScope().recordInitializationStates(subInfo);
            return subInfo;
         } else {
            FlowInfo mergedInfo = ((FlowInfo)tryInfo).addInitializationsFrom(subInfo);
            this.mergedInitStateIndex = currentScope.methodScope().recordInitializationStates(mergedInfo);
            return mergedInfo;
         }
      }
   }

   private MethodBinding findCloseMethod(ASTNode resource, TypeBinding type) {
      MethodBinding closeMethod = null;
      if (type != null && type.isValidBinding() && type instanceof ReferenceBinding) {
         ReferenceBinding binding = (ReferenceBinding)type;
         closeMethod = binding.getExactMethod(ConstantPool.Close, new TypeBinding[0], this.scope.compilationUnitScope());
         if (closeMethod == null) {
            InvocationSite site = new InvocationSite.EmptyWithAstNode(resource);
            closeMethod = this.scope.compilationUnitScope().findMethod(binding, ConstantPool.Close, new TypeBinding[0], site, false);
         }
      }

      return closeMethod;
   }

   private FlowInfo prepareCatchInfo(FlowInfo flowInfo, ExceptionHandlingFlowContext handlingContext, FlowInfo tryInfo, int i) {
      FlowInfo catchInfo;
      if (this.isUncheckedCatchBlock(i)) {
         catchInfo = flowInfo.unconditionalCopy().addPotentialInitializationsFrom(handlingContext.initsOnException(i)).addPotentialInitializationsFrom(tryInfo).addPotentialInitializationsFrom(handlingContext.initsOnReturn).addNullInfoFrom(handlingContext.initsOnFinally);
      } else {
         FlowInfo initsOnException = handlingContext.initsOnException(i);
         catchInfo = flowInfo.nullInfoLessUnconditionalCopy().addPotentialInitializationsFrom(initsOnException).addNullInfoFrom(initsOnException).addPotentialInitializationsFrom(tryInfo.nullInfoLessUnconditionalCopy()).addPotentialInitializationsFrom(handlingContext.initsOnReturn.nullInfoLessUnconditionalCopy());
      }

      LocalVariableBinding catchArg = this.catchArguments[i].binding;
      catchInfo.markAsDefinitelyAssigned(catchArg);
      catchInfo.markAsDefinitelyNonNull(catchArg);
      if (this.tryBlock.statements == null && this.resources == null) {
         catchInfo.setReachMode(1);
      }

      return catchInfo;
   }

   private boolean isUncheckedCatchBlock(int catchBlock) {
      if (this.caughtExceptionsCatchBlocks == null) {
         return this.caughtExceptionTypes[catchBlock].isUncheckedException(true);
      } else {
         int i = 0;

         for(int length = this.caughtExceptionsCatchBlocks.length; i < length; ++i) {
            if (this.caughtExceptionsCatchBlocks[i] == catchBlock && this.caughtExceptionTypes[i].isUncheckedException(true)) {
               return true;
            }
         }

         return false;
      }
   }

   public ExceptionLabel enterAnyExceptionHandler(CodeStream codeStream) {
      return this.subRoutineStartLabel == null ? null : super.enterAnyExceptionHandler(codeStream);
   }

   public void enterDeclaredExceptionHandlers(CodeStream codeStream) {
      int resourceCount = 0;

      int i;
      for(i = this.declaredExceptionLabels == null ? 0 : this.declaredExceptionLabels.length; resourceCount < i; ++resourceCount) {
         this.declaredExceptionLabels[resourceCount].placeStart();
      }

      resourceCount = this.resources.length;
      if (resourceCount > 0 && this.resourceExceptionLabels != null) {
         for(i = resourceCount; i >= 0; --i) {
            this.resourceExceptionLabels[i].placeStart();
         }
      }

   }

   public void exitAnyExceptionHandler() {
      if (this.subRoutineStartLabel != null) {
         super.exitAnyExceptionHandler();
      }
   }

   public void exitDeclaredExceptionHandlers(CodeStream codeStream) {
      int i = 0;

      for(int length = this.declaredExceptionLabels == null ? 0 : this.declaredExceptionLabels.length; i < length; ++i) {
         this.declaredExceptionLabels[i].placeEnd();
      }

   }

   private int finallyMode() {
      if (this.subRoutineStartLabel == null) {
         return 0;
      } else if (this.isSubRoutineEscaping()) {
         return 2;
      } else {
         return this.scope.compilerOptions().inlineJsrBytecode ? 3 : 1;
      }
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream) {
      if ((this.bits & Integer.MIN_VALUE) != 0) {
         boolean isStackMapFrameCodeStream = codeStream instanceof StackMapFrameCodeStream;
         this.anyExceptionLabel = null;
         this.reusableJSRTargets = null;
         this.reusableJSRSequenceStartLabels = null;
         this.reusableJSRTargetsCount = 0;
         int pc = codeStream.position;
         int finallyMode = this.finallyMode();
         boolean requiresNaturalExit = false;
         int maxCatches = this.catchArguments == null ? 0 : this.catchArguments.length;
         ExceptionLabel[] exceptionLabels;
         int resourceCount;
         if (maxCatches > 0) {
            exceptionLabels = new ExceptionLabel[maxCatches];

            for(resourceCount = 0; resourceCount < maxCatches; ++resourceCount) {
               Argument argument = this.catchArguments[resourceCount];
               ExceptionLabel exceptionLabel = null;
               if ((argument.binding.tagBits & 4096L) != 0L) {
                  MultiCatchExceptionLabel multiCatchExceptionLabel = new MultiCatchExceptionLabel(codeStream, argument.binding.type);
                  multiCatchExceptionLabel.initialize((UnionTypeReference)argument.type, argument.annotations);
                  exceptionLabel = multiCatchExceptionLabel;
               } else {
                  exceptionLabel = new ExceptionLabel(codeStream, argument.binding.type, argument.type, argument.annotations);
               }

               ((ExceptionLabel)exceptionLabel).placeStart();
               exceptionLabels[resourceCount] = (ExceptionLabel)exceptionLabel;
            }
         } else {
            exceptionLabels = null;
         }

         if (this.subRoutineStartLabel != null) {
            this.subRoutineStartLabel.initialize(codeStream);
            this.enterAnyExceptionHandler(codeStream);
         }

         BranchLabel postCatchesFinallyLabel;
         int finallySequenceStartPC;
         try {
            this.declaredExceptionLabels = exceptionLabels;
            resourceCount = this.resources.length;
            int i;
            if (resourceCount > 0) {
               this.resourceExceptionLabels = new ExceptionLabel[resourceCount + 1];
               codeStream.aconst_null();
               codeStream.store(this.primaryExceptionVariable, false);
               codeStream.addVariable(this.primaryExceptionVariable);
               codeStream.aconst_null();
               codeStream.store(this.caughtThrowableVariable, false);
               codeStream.addVariable(this.caughtThrowableVariable);

               for(i = 0; i <= resourceCount; ++i) {
                  this.resourceExceptionLabels[i] = new ExceptionLabel(codeStream, (TypeBinding)null);
                  this.resourceExceptionLabels[i].placeStart();
                  if (i < resourceCount) {
                     Statement stmt = this.resources[i];
                     if (stmt instanceof NameReference) {
                        NameReference ref = (NameReference)stmt;
                        ref.bits |= 524288;
                        VariableBinding binding = (VariableBinding)ref.binding;
                        ref.checkEffectiveFinality(binding, this.scope);
                     } else if (stmt instanceof FieldReference) {
                        FieldReference fieldReference = (FieldReference)stmt;
                        if (!fieldReference.binding.isFinal()) {
                           this.scope.problemReporter().cannotReferToNonFinalField(fieldReference.binding, fieldReference);
                        }
                     }

                     stmt.generateCode(this.scope, codeStream);
                  }
               }
            }

            this.tryBlock.generateCode(this.scope, codeStream);
            if (resourceCount > 0) {
               for(i = resourceCount; i >= 0; --i) {
                  postCatchesFinallyLabel = new BranchLabel(codeStream);
                  this.resourceExceptionLabels[i].placeEnd();
                  Statement stmt = i > 0 ? this.resources[i - 1] : null;
                  if ((this.bits & 536870912) == 0) {
                     if (i > 0) {
                        finallySequenceStartPC = codeStream.position;
                        if (this.postTryInitStateIndex != -1) {
                           codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.postTryInitStateIndex);
                           codeStream.addDefinitelyAssignedVariables(currentScope, this.postTryInitStateIndex);
                        }

                        this.generateCodeSnippet(stmt, codeStream, postCatchesFinallyLabel, false);
                        codeStream.recordPositionsFrom(finallySequenceStartPC, this.tryBlock.sourceEnd);
                     }

                     codeStream.goto_(postCatchesFinallyLabel);
                  }

                  if (i > 0) {
                     codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.postResourcesInitStateIndexes[i - 1]);
                     codeStream.addDefinitelyAssignedVariables(currentScope, this.postResourcesInitStateIndexes[i - 1]);
                  } else {
                     codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.preTryInitStateIndex);
                     codeStream.addDefinitelyAssignedVariables(currentScope, this.preTryInitStateIndex);
                  }

                  codeStream.pushExceptionOnStack(this.scope.getJavaLangThrowable());
                  this.resourceExceptionLabels[i].place();
                  BranchLabel postCloseLabel;
                  if (i == resourceCount) {
                     codeStream.store(this.primaryExceptionVariable, false);
                  } else {
                     postCloseLabel = new BranchLabel(codeStream);
                     BranchLabel postElseLabel = new BranchLabel(codeStream);
                     codeStream.store(this.caughtThrowableVariable, false);
                     codeStream.load(this.primaryExceptionVariable);
                     codeStream.ifnonnull(postCloseLabel);
                     codeStream.load(this.caughtThrowableVariable);
                     codeStream.store(this.primaryExceptionVariable, false);
                     codeStream.goto_(postElseLabel);
                     postCloseLabel.place();
                     codeStream.load(this.primaryExceptionVariable);
                     codeStream.load(this.caughtThrowableVariable);
                     codeStream.if_acmpeq(postElseLabel);
                     codeStream.load(this.primaryExceptionVariable);
                     codeStream.load(this.caughtThrowableVariable);
                     codeStream.invokeThrowableAddSuppressed();
                     postElseLabel.place();
                  }

                  if (i > 0) {
                     postCloseLabel = new BranchLabel(codeStream);
                     this.generateCodeSnippet(stmt, codeStream, postCloseLabel, true, i, codeStream.position);
                     postCloseLabel.place();
                  }

                  codeStream.load(this.primaryExceptionVariable);
                  codeStream.athrow();
                  postCatchesFinallyLabel.place();
               }

               codeStream.removeVariable(this.primaryExceptionVariable);
               codeStream.removeVariable(this.caughtThrowableVariable);
            }
         } finally {
            this.declaredExceptionLabels = null;
            this.resourceExceptionLabels = null;
         }

         boolean tryBlockHasSomeCode = codeStream.position != pc;
         if (tryBlockHasSomeCode) {
            BranchLabel naturalExitLabel = new BranchLabel(codeStream);
            postCatchesFinallyLabel = null;

            int i;
            for(i = 0; i < maxCatches; ++i) {
               exceptionLabels[i].placeEnd();
            }

            if ((this.bits & 536870912) == 0) {
               i = codeStream.position;
               switch (finallyMode) {
                  case 0:
                     if (this.naturalExitMergeInitStateIndex != -1) {
                        codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.naturalExitMergeInitStateIndex);
                        codeStream.addDefinitelyAssignedVariables(currentScope, this.naturalExitMergeInitStateIndex);
                     }

                     codeStream.goto_(naturalExitLabel);
                     break;
                  case 1:
                  case 3:
                     requiresNaturalExit = true;
                     if (this.naturalExitMergeInitStateIndex != -1) {
                        codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.naturalExitMergeInitStateIndex);
                        codeStream.addDefinitelyAssignedVariables(currentScope, this.naturalExitMergeInitStateIndex);
                     }

                     codeStream.goto_(naturalExitLabel);
                     break;
                  case 2:
                     codeStream.goto_(this.subRoutineStartLabel);
               }

               codeStream.recordPositionsFrom(i, this.tryBlock.sourceEnd);
            }

            this.exitAnyExceptionHandler();
            int position;
            if (this.catchArguments != null) {
               postCatchesFinallyLabel = new BranchLabel(codeStream);

               for(i = 0; i < maxCatches; ++i) {
                  if (exceptionLabels[i].getCount() != 0) {
                     this.enterAnyExceptionHandler(codeStream);
                     if (this.preTryInitStateIndex != -1) {
                        codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.preTryInitStateIndex);
                        codeStream.addDefinitelyAssignedVariables(currentScope, this.preTryInitStateIndex);
                     }

                     codeStream.pushExceptionOnStack(exceptionLabels[i].exceptionType);
                     exceptionLabels[i].place();
                     position = codeStream.position;
                     LocalVariableBinding catchVar;
                     if ((catchVar = this.catchArguments[i].binding).resolvedPosition != -1) {
                        codeStream.store(catchVar, false);
                        catchVar.recordInitializationStartPC(codeStream.position);
                        codeStream.addVisibleLocalVariable(catchVar);
                     } else {
                        codeStream.pop();
                     }

                     codeStream.recordPositionsFrom(position, this.catchArguments[i].sourceStart);
                     this.catchBlocks[i].generateCode(this.scope, codeStream);
                     this.exitAnyExceptionHandler();
                     if (!this.catchExits[i]) {
                        switch (finallyMode) {
                           case 1:
                              requiresNaturalExit = true;
                           case 0:
                              if (this.naturalExitMergeInitStateIndex != -1) {
                                 codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.naturalExitMergeInitStateIndex);
                                 codeStream.addDefinitelyAssignedVariables(currentScope, this.naturalExitMergeInitStateIndex);
                              }

                              codeStream.goto_(naturalExitLabel);
                              break;
                           case 2:
                              codeStream.goto_(this.subRoutineStartLabel);
                              break;
                           case 3:
                              if (isStackMapFrameCodeStream) {
                                 ((StackMapFrameCodeStream)codeStream).pushStateIndex(this.naturalExitMergeInitStateIndex);
                              }

                              if (this.catchExitInitStateIndexes[i] != -1) {
                                 codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.catchExitInitStateIndexes[i]);
                                 codeStream.addDefinitelyAssignedVariables(currentScope, this.catchExitInitStateIndexes[i]);
                              }

                              this.finallyBlock.generateCode(this.scope, codeStream);
                              codeStream.goto_(postCatchesFinallyLabel);
                              if (isStackMapFrameCodeStream) {
                                 ((StackMapFrameCodeStream)codeStream).popStateIndex();
                              }
                        }
                     }
                  }
               }
            }

            ExceptionLabel naturalExitExceptionHandler = requiresNaturalExit && finallyMode == 1 ? new ExceptionLabel(codeStream, (TypeBinding)null) : null;
            finallySequenceStartPC = codeStream.position;
            if (this.subRoutineStartLabel != null && this.anyExceptionLabel.getCount() != 0) {
               codeStream.pushExceptionOnStack(this.scope.getJavaLangThrowable());
               if (this.preTryInitStateIndex != -1) {
                  codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.preTryInitStateIndex);
                  codeStream.addDefinitelyAssignedVariables(currentScope, this.preTryInitStateIndex);
               }

               this.placeAllAnyExceptionHandler();
               if (naturalExitExceptionHandler != null) {
                  naturalExitExceptionHandler.place();
               }

               switch (finallyMode) {
                  case 1:
                     codeStream.store(this.anyExceptionVariable, false);
                     codeStream.jsr(this.subRoutineStartLabel);
                     codeStream.recordPositionsFrom(finallySequenceStartPC, this.finallyBlock.sourceStart);
                     position = codeStream.position;
                     codeStream.throwAnyException(this.anyExceptionVariable);
                     codeStream.recordPositionsFrom(position, this.finallyBlock.sourceEnd);
                     this.subRoutineStartLabel.place();
                     codeStream.pushExceptionOnStack(this.scope.getJavaLangThrowable());
                     position = codeStream.position;
                     codeStream.store(this.returnAddressVariable, false);
                     codeStream.recordPositionsFrom(position, this.finallyBlock.sourceStart);
                     this.finallyBlock.generateCode(this.scope, codeStream);
                     position = codeStream.position;
                     codeStream.ret(this.returnAddressVariable.resolvedPosition);
                     codeStream.recordPositionsFrom(position, this.finallyBlock.sourceEnd);
                     break;
                  case 2:
                     codeStream.pop();
                     this.subRoutineStartLabel.place();
                     codeStream.recordPositionsFrom(finallySequenceStartPC, this.finallyBlock.sourceStart);
                     this.finallyBlock.generateCode(this.scope, codeStream);
                     break;
                  case 3:
                     codeStream.store(this.anyExceptionVariable, false);
                     codeStream.addVariable(this.anyExceptionVariable);
                     codeStream.recordPositionsFrom(finallySequenceStartPC, this.finallyBlock.sourceStart);
                     this.finallyBlock.generateCode(currentScope, codeStream);
                     position = codeStream.position;
                     codeStream.throwAnyException(this.anyExceptionVariable);
                     codeStream.removeVariable(this.anyExceptionVariable);
                     if (this.preTryInitStateIndex != -1) {
                        codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.preTryInitStateIndex);
                     }

                     this.subRoutineStartLabel.place();
                     codeStream.recordPositionsFrom(position, this.finallyBlock.sourceEnd);
               }

               if (requiresNaturalExit) {
                  switch (finallyMode) {
                     case 1:
                        naturalExitLabel.place();
                        position = codeStream.position;
                        naturalExitExceptionHandler.placeStart();
                        codeStream.jsr(this.subRoutineStartLabel);
                        naturalExitExceptionHandler.placeEnd();
                        codeStream.recordPositionsFrom(position, this.finallyBlock.sourceEnd);
                     case 2:
                        break;
                     case 3:
                        if (isStackMapFrameCodeStream) {
                           ((StackMapFrameCodeStream)codeStream).pushStateIndex(this.naturalExitMergeInitStateIndex);
                        }

                        if (this.naturalExitMergeInitStateIndex != -1) {
                           codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.naturalExitMergeInitStateIndex);
                           codeStream.addDefinitelyAssignedVariables(currentScope, this.naturalExitMergeInitStateIndex);
                        }

                        naturalExitLabel.place();
                        this.finallyBlock.generateCode(this.scope, codeStream);
                        if (postCatchesFinallyLabel != null) {
                           position = codeStream.position;
                           codeStream.goto_(postCatchesFinallyLabel);
                           codeStream.recordPositionsFrom(position, this.finallyBlock.sourceEnd);
                        }

                        if (isStackMapFrameCodeStream) {
                           ((StackMapFrameCodeStream)codeStream).popStateIndex();
                        }
                        break;
                     default:
                        naturalExitLabel.place();
                  }
               }

               if (postCatchesFinallyLabel != null) {
                  postCatchesFinallyLabel.place();
               }
            } else {
               naturalExitLabel.place();
            }
         } else if (this.subRoutineStartLabel != null) {
            this.finallyBlock.generateCode(this.scope, codeStream);
         }

         if (this.mergedInitStateIndex != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
            codeStream.addDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      }
   }

   private void generateCodeSnippet(Statement statement, CodeStream codeStream, BranchLabel postCloseLabel, boolean record, int... values) {
      int i = -1;
      int invokeCloseStartPc = -1;
      if (record) {
         i = values[0];
         invokeCloseStartPc = values[1];
      }

      if (statement instanceof LocalDeclaration) {
         this.generateCodeSnippet((LocalDeclaration)statement, codeStream, postCloseLabel, record, i, invokeCloseStartPc);
      } else if (statement instanceof Reference) {
         this.generateCodeSnippet((Reference)statement, codeStream, postCloseLabel, record, i, invokeCloseStartPc);
      }

   }

   private void generateCodeSnippet(Reference reference, CodeStream codeStream, BranchLabel postCloseLabel, boolean record, int i, int invokeCloseStartPc) {
      reference.generateCode(this.scope, codeStream, true);
      codeStream.ifnull(postCloseLabel);
      reference.generateCode(this.scope, codeStream, true);
      codeStream.invokeAutoCloseableClose(reference.resolvedType);
      if (record) {
         codeStream.recordPositionsFrom(invokeCloseStartPc, this.tryBlock.sourceEnd);
         this.isDuplicateResourceReference(i);
      }
   }

   private void generateCodeSnippet(LocalDeclaration localDeclaration, CodeStream codeStream, BranchLabel postCloseLabel, boolean record, int i, int invokeCloseStartPc) {
      LocalVariableBinding variableBinding = localDeclaration.binding;
      codeStream.load(variableBinding);
      codeStream.ifnull(postCloseLabel);
      codeStream.load(variableBinding);
      codeStream.invokeAutoCloseableClose(variableBinding.type);
      if (record) {
         codeStream.recordPositionsFrom(invokeCloseStartPc, this.tryBlock.sourceEnd);
         if (!this.isDuplicateResourceReference(i)) {
            codeStream.removeVariable(variableBinding);
         }

      }
   }

   private boolean isDuplicateResourceReference(int index) {
      int len = this.resources.length;
      if (index < len && this.resources[index] instanceof Reference) {
         Reference ref = (Reference)this.resources[index];
         Binding refBinding = ref instanceof NameReference ? ((NameReference)ref).binding : (ref instanceof FieldReference ? ((FieldReference)ref).binding : null);
         if (refBinding == null) {
            return false;
         }

         for(int i = 0; i < index; ++i) {
            Statement stmt = this.resources[i];
            Binding b = stmt instanceof LocalDeclaration ? ((LocalDeclaration)stmt).binding : (stmt instanceof NameReference ? ((NameReference)stmt).binding : (stmt instanceof FieldReference ? ((FieldReference)stmt).binding : null));
            if (b == refBinding) {
               this.scope.problemReporter().duplicateResourceReference(ref);
               return true;
            }
         }
      }

      return false;
   }

   public boolean generateSubRoutineInvocation(BlockScope currentScope, CodeStream codeStream, Object targetLocation, int stateIndex, LocalVariableBinding secretLocal) {
      int resourceCount = this.resources.length;
      if (resourceCount > 0 && this.resourceExceptionLabels != null) {
         for(int i = resourceCount; i > 0; --i) {
            this.resourceExceptionLabels[i].placeEnd();
            BranchLabel exitLabel = new BranchLabel(codeStream);
            int invokeCloseStartPc = codeStream.position;
            this.generateCodeSnippet(this.resources[i - 1], codeStream, exitLabel, false);
            codeStream.recordPositionsFrom(invokeCloseStartPc, this.tryBlock.sourceEnd);
            exitLabel.place();
         }

         this.resourceExceptionLabels[0].placeEnd();
      }

      boolean isStackMapFrameCodeStream = codeStream instanceof StackMapFrameCodeStream;
      int finallyMode = this.finallyMode();
      switch (finallyMode) {
         case 0:
            this.exitDeclaredExceptionHandlers(codeStream);
            return false;
         case 1:
         default:
            CompilerOptions options = this.scope.compilerOptions();
            if (options.shareCommonFinallyBlocks && targetLocation != null) {
               boolean reuseTargetLocation = true;
               if (this.reusableJSRTargetsCount > 0) {
                  int i = 0;

                  for(int count = this.reusableJSRTargetsCount; i < count; ++i) {
                     Object reusableJSRTarget = this.reusableJSRTargets[i];
                     if (targetLocation == reusableJSRTarget || targetLocation instanceof Constant && reusableJSRTarget instanceof Constant && ((Constant)targetLocation).hasSameValue((Constant)reusableJSRTarget)) {
                        if (this.reusableJSRStateIndexes[i] == stateIndex || finallyMode != 3) {
                           codeStream.goto_(this.reusableJSRSequenceStartLabels[i]);
                           return true;
                        }

                        reuseTargetLocation = false;
                        break;
                     }
                  }
               } else {
                  this.reusableJSRTargets = new Object[3];
                  this.reusableJSRSequenceStartLabels = new BranchLabel[3];
                  this.reusableJSRStateIndexes = new int[3];
               }

               if (reuseTargetLocation) {
                  if (this.reusableJSRTargetsCount == this.reusableJSRTargets.length) {
                     System.arraycopy(this.reusableJSRTargets, 0, this.reusableJSRTargets = new Object[2 * this.reusableJSRTargetsCount], 0, this.reusableJSRTargetsCount);
                     System.arraycopy(this.reusableJSRSequenceStartLabels, 0, this.reusableJSRSequenceStartLabels = new BranchLabel[2 * this.reusableJSRTargetsCount], 0, this.reusableJSRTargetsCount);
                     System.arraycopy(this.reusableJSRStateIndexes, 0, this.reusableJSRStateIndexes = new int[2 * this.reusableJSRTargetsCount], 0, this.reusableJSRTargetsCount);
                  }

                  this.reusableJSRTargets[this.reusableJSRTargetsCount] = targetLocation;
                  BranchLabel reusableJSRSequenceStartLabel = new BranchLabel(codeStream);
                  reusableJSRSequenceStartLabel.place();
                  this.reusableJSRStateIndexes[this.reusableJSRTargetsCount] = stateIndex;
                  this.reusableJSRSequenceStartLabels[this.reusableJSRTargetsCount++] = reusableJSRSequenceStartLabel;
               }
            }

            if (finallyMode == 3) {
               if (isStackMapFrameCodeStream) {
                  ((StackMapFrameCodeStream)codeStream).pushStateIndex(stateIndex);
               }

               this.exitAnyExceptionHandler();
               this.exitDeclaredExceptionHandlers(codeStream);
               this.finallyBlock.generateCode(currentScope, codeStream);
               if (isStackMapFrameCodeStream) {
                  ((StackMapFrameCodeStream)codeStream).popStateIndex();
               }
            } else {
               codeStream.jsr(this.subRoutineStartLabel);
               this.exitAnyExceptionHandler();
               this.exitDeclaredExceptionHandlers(codeStream);
            }

            return false;
         case 2:
            codeStream.goto_(this.subRoutineStartLabel);
            return true;
      }
   }

   public boolean isSubRoutineEscaping() {
      return (this.bits & 16384) != 0;
   }

   public StringBuffer printStatement(int indent, StringBuffer output) {
      int length = this.resources.length;
      printIndent(indent, output).append("try" + (length == 0 ? "\n" : " ("));

      int i;
      for(i = 0; i < length; ++i) {
         Statement stmt = this.resources[i];
         if (stmt instanceof LocalDeclaration) {
            ((LocalDeclaration)stmt).printAsExpression(0, output);
         } else {
            if (!(stmt instanceof Reference)) {
               continue;
            }

            ((Reference)stmt).printExpression(0, output);
         }

         if (i != length - 1) {
            output.append(";\n");
            printIndent(indent + 2, output);
         }
      }

      if (length > 0) {
         output.append(")\n");
      }

      this.tryBlock.printStatement(indent + 1, output);
      if (this.catchBlocks != null) {
         for(i = 0; i < this.catchBlocks.length; ++i) {
            output.append('\n');
            printIndent(indent, output).append("catch (");
            this.catchArguments[i].print(0, output).append(")\n");
            this.catchBlocks[i].printStatement(indent + 1, output);
         }
      }

      if (this.finallyBlock != null) {
         output.append('\n');
         printIndent(indent, output).append("finally\n");
         this.finallyBlock.printStatement(indent + 1, output);
      }

      return output;
   }

   public void resolve(BlockScope upperScope) {
      this.scope = new BlockScope(upperScope);
      BlockScope finallyScope = null;
      BlockScope resourceManagementScope = null;
      int resourceCount = this.resources.length;
      if (resourceCount > 0) {
         resourceManagementScope = new BlockScope(this.scope);
         this.primaryExceptionVariable = new LocalVariableBinding(SECRET_PRIMARY_EXCEPTION_VARIABLE_NAME, this.scope.getJavaLangThrowable(), 0, false);
         resourceManagementScope.addLocalVariable(this.primaryExceptionVariable);
         this.primaryExceptionVariable.setConstant(Constant.NotAConstant);
         this.caughtThrowableVariable = new LocalVariableBinding(SECRET_CAUGHT_THROWABLE_VARIABLE_NAME, this.scope.getJavaLangThrowable(), 0, false);
         resourceManagementScope.addLocalVariable(this.caughtThrowableVariable);
         this.caughtThrowableVariable.setConstant(Constant.NotAConstant);
      }

      TypeBinding methodReturnType;
      for(int i = 0; i < resourceCount; ++i) {
         this.resources[i].resolve(resourceManagementScope);
         if (this.resources[i] instanceof LocalDeclaration) {
            LocalDeclaration node = (LocalDeclaration)this.resources[i];
            LocalVariableBinding localVariableBinding = node.binding;
            if (localVariableBinding != null && localVariableBinding.isValidBinding()) {
               localVariableBinding.modifiers |= 16;
               localVariableBinding.tagBits |= 8192L;
               methodReturnType = localVariableBinding.type;
               if (methodReturnType instanceof ReferenceBinding) {
                  if (methodReturnType.findSuperTypeOriginatingFrom(62, false) == null && methodReturnType.isValidBinding()) {
                     upperScope.problemReporter().resourceHasToImplementAutoCloseable(methodReturnType, node.type);
                     localVariableBinding.type = new ProblemReferenceBinding(CharOperation.splitOn('.', methodReturnType.shortReadableName()), (ReferenceBinding)null, 15);
                  }
               } else if (methodReturnType != null) {
                  upperScope.problemReporter().resourceHasToImplementAutoCloseable(methodReturnType, node.type);
                  localVariableBinding.type = new ProblemReferenceBinding(CharOperation.splitOn('.', methodReturnType.shortReadableName()), (ReferenceBinding)null, 15);
               }
            }
         } else {
            Expression node = (Expression)this.resources[i];
            TypeBinding resourceType = node.resolvedType;
            if (resourceType instanceof ReferenceBinding) {
               if (resourceType.findSuperTypeOriginatingFrom(62, false) == null && resourceType.isValidBinding()) {
                  upperScope.problemReporter().resourceHasToImplementAutoCloseable(resourceType, node);
                  ((Expression)this.resources[i]).resolvedType = new ProblemReferenceBinding(CharOperation.splitOn('.', resourceType.shortReadableName()), (ReferenceBinding)null, 15);
               }
            } else if (resourceType != null) {
               upperScope.problemReporter().resourceHasToImplementAutoCloseable(resourceType, node);
               ((Expression)this.resources[i]).resolvedType = new ProblemReferenceBinding(CharOperation.splitOn('.', resourceType.shortReadableName()), (ReferenceBinding)null, 15);
            }
         }
      }

      BlockScope tryScope = new BlockScope(resourceManagementScope != null ? resourceManagementScope : this.scope);
      if (this.finallyBlock != null) {
         if (this.finallyBlock.isEmptyBlock()) {
            if ((this.finallyBlock.bits & 8) != 0) {
               this.scope.problemReporter().undocumentedEmptyBlock(this.finallyBlock.sourceStart, this.finallyBlock.sourceEnd);
            }
         } else {
            finallyScope = new BlockScope(this.scope, false);
            MethodScope methodScope = this.scope.methodScope();
            if (!upperScope.compilerOptions().inlineJsrBytecode) {
               this.returnAddressVariable = new LocalVariableBinding(SECRET_RETURN_ADDRESS_NAME, upperScope.getJavaLangObject(), 0, false);
               finallyScope.addLocalVariable(this.returnAddressVariable);
               this.returnAddressVariable.setConstant(Constant.NotAConstant);
            }

            this.subRoutineStartLabel = new BranchLabel();
            this.anyExceptionVariable = new LocalVariableBinding(SECRET_ANY_HANDLER_NAME, this.scope.getJavaLangThrowable(), 0, false);
            finallyScope.addLocalVariable(this.anyExceptionVariable);
            this.anyExceptionVariable.setConstant(Constant.NotAConstant);
            if (!methodScope.isInsideInitializer()) {
               MethodBinding methodBinding = methodScope.referenceContext instanceof AbstractMethodDeclaration ? ((AbstractMethodDeclaration)methodScope.referenceContext).binding : (methodScope.referenceContext instanceof LambdaExpression ? ((LambdaExpression)methodScope.referenceContext).binding : null);
               if (methodBinding != null) {
                  methodReturnType = methodBinding.returnType;
                  if (methodReturnType.id != 6) {
                     this.secretReturnValue = new LocalVariableBinding(SECRET_RETURN_VALUE_NAME, methodReturnType, 0, false);
                     finallyScope.addLocalVariable(this.secretReturnValue);
                     this.secretReturnValue.setConstant(Constant.NotAConstant);
                  }
               }
            }

            this.finallyBlock.resolveUsing(finallyScope);
            int shiftScopesLength = this.catchArguments == null ? 1 : this.catchArguments.length + 1;
            finallyScope.shiftScopes = new BlockScope[shiftScopesLength];
            finallyScope.shiftScopes[0] = tryScope;
         }
      }

      this.tryBlock.resolveUsing(tryScope);
      if (this.catchBlocks != null) {
         int length = this.catchArguments.length;
         TypeBinding[] argumentTypes = new TypeBinding[length];
         boolean containsUnionTypes = false;
         boolean catchHasError = false;

         for(int i = 0; i < length; ++i) {
            BlockScope catchScope = new BlockScope(this.scope);
            if (finallyScope != null) {
               finallyScope.shiftScopes[i + 1] = catchScope;
            }

            Argument catchArgument = this.catchArguments[i];
            containsUnionTypes |= (catchArgument.type.bits & 536870912) != 0;
            if ((argumentTypes[i] = catchArgument.resolveForCatch(catchScope)) == null) {
               catchHasError = true;
            }

            this.catchBlocks[i].resolveUsing(catchScope);
         }

         if (catchHasError) {
            return;
         }

         this.verifyDuplicationAndOrder(length, argumentTypes, containsUnionTypes);
      } else {
         this.caughtExceptionTypes = new ReferenceBinding[0];
      }

      if (finallyScope != null) {
         this.scope.addSubscope(finallyScope);
      }

   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      if (visitor.visit(this, blockScope)) {
         Statement[] statements = this.resources;
         int i = 0;

         int max;
         for(max = statements.length; i < max; ++i) {
            statements[i].traverse(visitor, this.scope);
         }

         this.tryBlock.traverse(visitor, this.scope);
         if (this.catchArguments != null) {
            i = 0;

            for(max = this.catchBlocks.length; i < max; ++i) {
               this.catchArguments[i].traverse(visitor, this.scope);
               this.catchBlocks[i].traverse(visitor, this.scope);
            }
         }

         if (this.finallyBlock != null) {
            this.finallyBlock.traverse(visitor, this.scope);
         }
      }

      visitor.endVisit(this, blockScope);
   }

   protected void verifyDuplicationAndOrder(int length, TypeBinding[] argumentTypes, boolean containsUnionTypes) {
      int totalCount;
      if (containsUnionTypes) {
         totalCount = 0;
         ReferenceBinding[][] allExceptionTypes = new ReferenceBinding[length][];

         int i;
         int max;
         int k;
         for(i = 0; i < length; ++i) {
            if (!(argumentTypes[i] instanceof ArrayBinding)) {
               ReferenceBinding currentExceptionType = (ReferenceBinding)argumentTypes[i];
               TypeReference catchArgumentType = this.catchArguments[i].type;
               if ((catchArgumentType.bits & 536870912) == 0) {
                  allExceptionTypes[i] = new ReferenceBinding[]{currentExceptionType};
                  ++totalCount;
               } else {
                  TypeReference[] typeReferences = ((UnionTypeReference)catchArgumentType).typeReferences;
                  max = typeReferences.length;
                  ReferenceBinding[] unionExceptionTypes = new ReferenceBinding[max];

                  for(k = 0; k < max; ++k) {
                     unionExceptionTypes[k] = (ReferenceBinding)typeReferences[k].resolvedType;
                  }

                  totalCount += max;
                  allExceptionTypes[i] = unionExceptionTypes;
               }
            }
         }

         this.caughtExceptionTypes = new ReferenceBinding[totalCount];
         this.caughtExceptionsCatchBlocks = new int[totalCount];
         i = 0;

         label92:
         for(int l = 0; i < length; ++i) {
            ReferenceBinding[] currentExceptions = allExceptionTypes[i];
            if (currentExceptions != null) {
               int j = 0;

               for(max = currentExceptions.length; j < max; ++j) {
                  ReferenceBinding exception = currentExceptions[j];
                  this.caughtExceptionTypes[l] = exception;
                  this.caughtExceptionsCatchBlocks[l++] = i;

                  for(k = 0; k < i; ++k) {
                     ReferenceBinding[] exceptions = allExceptionTypes[k];
                     if (exceptions != null) {
                        int n = 0;

                        for(int max2 = exceptions.length; n < max2; ++n) {
                           ReferenceBinding currentException = exceptions[n];
                           if (exception.isCompatibleWith(currentException)) {
                              TypeReference catchArgumentType = this.catchArguments[i].type;
                              if ((catchArgumentType.bits & 536870912) != 0) {
                                 catchArgumentType = ((UnionTypeReference)catchArgumentType).typeReferences[j];
                              }

                              this.scope.problemReporter().wrongSequenceOfExceptionTypesError(catchArgumentType, exception, currentException);
                              continue label92;
                           }
                        }
                     }
                  }
               }
            }
         }
      } else {
         this.caughtExceptionTypes = new ReferenceBinding[length];

         for(totalCount = 0; totalCount < length; ++totalCount) {
            if (!(argumentTypes[totalCount] instanceof ArrayBinding)) {
               this.caughtExceptionTypes[totalCount] = (ReferenceBinding)argumentTypes[totalCount];

               for(int j = 0; j < totalCount; ++j) {
                  if (this.caughtExceptionTypes[totalCount].isCompatibleWith(argumentTypes[j])) {
                     this.scope.problemReporter().wrongSequenceOfExceptionTypesError(this.catchArguments[totalCount].type, this.caughtExceptionTypes[totalCount], argumentTypes[j]);
                  }
               }
            }
         }
      }

   }

   public boolean doesNotCompleteNormally() {
      if (!this.tryBlock.doesNotCompleteNormally()) {
         return this.finallyBlock != null ? this.finallyBlock.doesNotCompleteNormally() : false;
      } else {
         if (this.catchBlocks != null) {
            for(int i = 0; i < this.catchBlocks.length; ++i) {
               if (!this.catchBlocks[i].doesNotCompleteNormally()) {
                  return this.finallyBlock != null ? this.finallyBlock.doesNotCompleteNormally() : false;
               }
            }
         }

         return true;
      }
   }

   public boolean completesByContinue() {
      if (this.tryBlock.completesByContinue()) {
         return this.finallyBlock == null ? true : !this.finallyBlock.doesNotCompleteNormally() || this.finallyBlock.completesByContinue();
      } else {
         if (this.catchBlocks != null) {
            for(int i = 0; i < this.catchBlocks.length; ++i) {
               if (this.catchBlocks[i].completesByContinue()) {
                  return this.finallyBlock == null ? true : !this.finallyBlock.doesNotCompleteNormally() || this.finallyBlock.completesByContinue();
               }
            }
         }

         return this.finallyBlock != null && this.finallyBlock.completesByContinue();
      }
   }
}

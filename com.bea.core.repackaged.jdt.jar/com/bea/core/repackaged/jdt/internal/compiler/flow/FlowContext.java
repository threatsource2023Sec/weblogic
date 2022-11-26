package com.bea.core.repackaged.jdt.internal.compiler.flow;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Expression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FakedTrackingVariable;
import com.bea.core.repackaged.jdt.internal.compiler.ast.LabeledStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.LambdaExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.NullAnnotationMatching;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Reference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SingleNameReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SubRoutineStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ThrowStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TryStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.CatchParameterBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.VariableBinding;
import java.util.ArrayList;

public class FlowContext implements TypeConstants {
   public static final FlowContext NotContinuableContext = new FlowContext((FlowContext)null, (ASTNode)null, true);
   public ASTNode associatedNode;
   public FlowContext parent;
   public FlowInfo initsOnFinally;
   public int conditionalLevel = -1;
   public int tagBits;
   public TypeBinding[][] providedExpectedTypes = null;
   private Reference[] nullCheckedFieldReferences = null;
   private int[] timesToLiveForNullCheckInfo = null;
   public static final int DEFER_NULL_DIAGNOSTIC = 1;
   public static final int PREEMPT_NULL_DIAGNOSTIC = 2;
   public static final int INSIDE_NEGATION = 4;
   public static final int HIDE_NULL_COMPARISON_WARNING = 4096;
   public static final int HIDE_NULL_COMPARISON_WARNING_MASK = 61440;
   public static final int CAN_ONLY_NULL_NON_NULL = 0;
   public static final int CAN_ONLY_NULL = 1;
   public static final int CAN_ONLY_NON_NULL = 2;
   public static final int MAY_NULL = 3;
   public static final int ASSIGN_TO_NONNULL = 128;
   public static final int IN_UNBOXING = 16;
   public static final int EXIT_RESOURCE = 2048;
   public static final int CHECK_MASK = 255;
   public static final int IN_COMPARISON_NULL = 256;
   public static final int IN_COMPARISON_NON_NULL = 512;
   public static final int IN_ASSIGNMENT = 768;
   public static final int IN_INSTANCEOF = 1024;
   public static final int CONTEXT_MASK = -61696;

   public FlowContext(FlowContext parent, ASTNode associatedNode, boolean inheritNullFieldChecks) {
      this.parent = parent;
      this.associatedNode = associatedNode;
      if (parent != null) {
         if ((parent.tagBits & 3) != 0) {
            this.tagBits |= 1;
         }

         this.initsOnFinally = parent.initsOnFinally;
         this.conditionalLevel = parent.conditionalLevel;
         if (inheritNullFieldChecks) {
            this.copyNullCheckedFieldsFrom(parent);
         }
      }

   }

   public void copyNullCheckedFieldsFrom(FlowContext other) {
      Reference[] fieldReferences = other.nullCheckedFieldReferences;
      if (fieldReferences != null && fieldReferences.length > 0 && fieldReferences[0] != null) {
         this.nullCheckedFieldReferences = other.nullCheckedFieldReferences;
         this.timesToLiveForNullCheckInfo = other.timesToLiveForNullCheckInfo;
      }

   }

   public void recordNullCheckedFieldReference(Reference reference, int timeToLive) {
      if (this.nullCheckedFieldReferences == null) {
         this.nullCheckedFieldReferences = new Reference[]{reference, null};
         this.timesToLiveForNullCheckInfo = new int[]{timeToLive, -1};
      } else {
         int len = this.nullCheckedFieldReferences.length;

         for(int i = 0; i < len; ++i) {
            if (this.nullCheckedFieldReferences[i] == null) {
               this.nullCheckedFieldReferences[i] = reference;
               this.timesToLiveForNullCheckInfo[i] = timeToLive;
               return;
            }
         }

         System.arraycopy(this.nullCheckedFieldReferences, 0, this.nullCheckedFieldReferences = new Reference[len + 2], 0, len);
         System.arraycopy(this.timesToLiveForNullCheckInfo, 0, this.timesToLiveForNullCheckInfo = new int[len + 2], 0, len);
         this.nullCheckedFieldReferences[len] = reference;
         this.timesToLiveForNullCheckInfo[len] = timeToLive;
      }

   }

   public void extendTimeToLiveForNullCheckedField(int t) {
      if (this.timesToLiveForNullCheckInfo != null) {
         for(int i = 0; i < this.timesToLiveForNullCheckInfo.length; ++i) {
            if (this.timesToLiveForNullCheckInfo[i] > 0) {
               int[] var10000 = this.timesToLiveForNullCheckInfo;
               var10000[i] += t;
            }
         }
      }

   }

   public void expireNullCheckedFieldInfo() {
      if (this.nullCheckedFieldReferences != null) {
         for(int i = 0; i < this.nullCheckedFieldReferences.length; ++i) {
            if (--this.timesToLiveForNullCheckInfo[i] == 0) {
               this.nullCheckedFieldReferences[i] = null;
            }
         }
      }

   }

   public boolean isNullcheckedFieldAccess(Reference reference) {
      if (this.nullCheckedFieldReferences == null) {
         return false;
      } else {
         int len = this.nullCheckedFieldReferences.length;

         for(int i = 0; i < len; ++i) {
            Reference checked = this.nullCheckedFieldReferences[i];
            if (checked != null && checked.isEquivalent(reference)) {
               return true;
            }
         }

         return false;
      }
   }

   public BranchLabel breakLabel() {
      return null;
   }

   public void checkExceptionHandlers(TypeBinding raisedException, ASTNode location, FlowInfo flowInfo, BlockScope scope) {
      this.checkExceptionHandlers(raisedException, location, flowInfo, scope, false);
   }

   public void checkExceptionHandlers(TypeBinding raisedException, ASTNode location, FlowInfo flowInfo, BlockScope scope, boolean isExceptionOnAutoClose) {
      FlowContext traversedContext = this;
      ArrayList abruptlyExitedLoops = null;
      if (scope.compilerOptions().sourceLevel >= 3342336L && location instanceof ThrowStatement) {
         Expression throwExpression = ((ThrowStatement)location).exception;
         LocalVariableBinding throwArgBinding = throwExpression.localVariableBinding();
         if (throwExpression instanceof SingleNameReference && throwArgBinding instanceof CatchParameterBinding && throwArgBinding.isEffectivelyFinal()) {
            CatchParameterBinding parameter = (CatchParameterBinding)throwArgBinding;
            this.checkExceptionHandlers(parameter.getPreciseTypes(), location, flowInfo, scope);
            return;
         }
      }

      for(; traversedContext != null; traversedContext = traversedContext.getLocalParent()) {
         SubRoutineStatement sub;
         if ((sub = traversedContext.subroutine()) != null && sub.isSubRoutineEscaping()) {
            return;
         }

         if (traversedContext instanceof ExceptionHandlingFlowContext) {
            ExceptionHandlingFlowContext exceptionContext = (ExceptionHandlingFlowContext)traversedContext;
            ReferenceBinding[] caughtExceptions;
            boolean shouldMergeUnhandledExceptions;
            if ((caughtExceptions = exceptionContext.handledExceptions) != Binding.NO_EXCEPTIONS) {
               shouldMergeUnhandledExceptions = false;
               int caughtIndex = 0;

               for(int caughtCount = caughtExceptions.length; caughtIndex < caughtCount; ++caughtIndex) {
                  ReferenceBinding caughtException = caughtExceptions[caughtIndex];
                  FlowInfo exceptionFlow = flowInfo;
                  int state = caughtException == null ? -1 : Scope.compareTypes(raisedException, caughtException);
                  if (abruptlyExitedLoops != null && caughtException != null && state != 0) {
                     int i = 0;

                     for(int abruptlyExitedLoopsCount = abruptlyExitedLoops.size(); i < abruptlyExitedLoopsCount; ++i) {
                        LoopingFlowContext loop = (LoopingFlowContext)abruptlyExitedLoops.get(i);
                        loop.recordCatchContextOfEscapingException(exceptionContext, caughtException, flowInfo);
                     }

                     exceptionFlow = FlowInfo.DEAD_END;
                  }

                  switch (state) {
                     case -1:
                        exceptionContext.recordHandlingException(caughtException, ((FlowInfo)exceptionFlow).unconditionalInits(), raisedException, raisedException, location, shouldMergeUnhandledExceptions);
                        shouldMergeUnhandledExceptions = true;
                     case 0:
                     default:
                        break;
                     case 1:
                        exceptionContext.recordHandlingException(caughtException, ((FlowInfo)exceptionFlow).unconditionalInits(), raisedException, caughtException, location, false);
                  }
               }

               if (shouldMergeUnhandledExceptions) {
                  return;
               }
            }

            if (exceptionContext.isMethodContext) {
               if (raisedException.isUncheckedException(false)) {
                  return;
               }

               shouldMergeUnhandledExceptions = exceptionContext instanceof ExceptionInferenceFlowContext;
               if (exceptionContext.associatedNode instanceof AbstractMethodDeclaration) {
                  AbstractMethodDeclaration method = (AbstractMethodDeclaration)exceptionContext.associatedNode;
                  if (method.isConstructor() && method.binding.declaringClass.isAnonymousType()) {
                     shouldMergeUnhandledExceptions = true;
                  }
               }

               if (shouldMergeUnhandledExceptions) {
                  exceptionContext.mergeUnhandledException(raisedException);
                  return;
               }
               break;
            }
         } else if (traversedContext instanceof LoopingFlowContext) {
            if (abruptlyExitedLoops == null) {
               abruptlyExitedLoops = new ArrayList(5);
            }

            abruptlyExitedLoops.add(traversedContext);
         }

         traversedContext.recordReturnFrom(flowInfo.unconditionalInits());
         if (!isExceptionOnAutoClose && traversedContext instanceof InsideSubRoutineFlowContext) {
            ASTNode node = traversedContext.associatedNode;
            if (node instanceof TryStatement) {
               TryStatement tryStatement = (TryStatement)node;
               flowInfo.addInitializationsFrom(tryStatement.subRoutineInits);
            }
         }
      }

      if (isExceptionOnAutoClose) {
         scope.problemReporter().unhandledExceptionFromAutoClose(raisedException, location);
      } else {
         scope.problemReporter().unhandledException(raisedException, location);
      }

   }

   public void checkExceptionHandlers(TypeBinding[] raisedExceptions, ASTNode location, FlowInfo flowInfo, BlockScope scope) {
      int raisedCount;
      if (raisedExceptions != null && (raisedCount = raisedExceptions.length) != 0) {
         int remainingCount = raisedCount;
         System.arraycopy(raisedExceptions, 0, raisedExceptions = new TypeBinding[raisedCount], 0, raisedCount);
         FlowContext traversedContext = this;

         for(ArrayList abruptlyExitedLoops = null; traversedContext != null; traversedContext = traversedContext.getLocalParent()) {
            SubRoutineStatement sub;
            if ((sub = traversedContext.subroutine()) != null && sub.isSubRoutineEscaping()) {
               return;
            }

            if (traversedContext instanceof ExceptionHandlingFlowContext) {
               ExceptionHandlingFlowContext exceptionContext = (ExceptionHandlingFlowContext)traversedContext;
               ReferenceBinding[] caughtExceptions;
               int i;
               if ((caughtExceptions = exceptionContext.handledExceptions) != Binding.NO_EXCEPTIONS) {
                  i = caughtExceptions.length;
                  boolean[] locallyCaught = new boolean[raisedCount];
                  int i = 0;

                  label164:
                  while(true) {
                     if (i >= i) {
                        i = 0;

                        while(true) {
                           if (i >= raisedCount) {
                              break label164;
                           }

                           if (locallyCaught[i]) {
                              raisedExceptions[i] = null;
                           }

                           ++i;
                        }
                     }

                     ReferenceBinding caughtException = caughtExceptions[i];

                     for(int raisedIndex = 0; raisedIndex < raisedCount; ++raisedIndex) {
                        TypeBinding raisedException;
                        if ((raisedException = raisedExceptions[raisedIndex]) != null) {
                           FlowInfo exceptionFlow = flowInfo;
                           int state = caughtException == null ? -1 : Scope.compareTypes(raisedException, caughtException);
                           if (abruptlyExitedLoops != null && caughtException != null && state != 0) {
                              int i = 0;

                              for(int abruptlyExitedLoopsCount = abruptlyExitedLoops.size(); i < abruptlyExitedLoopsCount; ++i) {
                                 LoopingFlowContext loop = (LoopingFlowContext)abruptlyExitedLoops.get(i);
                                 loop.recordCatchContextOfEscapingException(exceptionContext, caughtException, flowInfo);
                              }

                              exceptionFlow = FlowInfo.DEAD_END;
                           }

                           switch (state) {
                              case -1:
                                 exceptionContext.recordHandlingException(caughtException, ((FlowInfo)exceptionFlow).unconditionalInits(), raisedException, raisedException, location, locallyCaught[raisedIndex]);
                                 if (!locallyCaught[raisedIndex]) {
                                    locallyCaught[raisedIndex] = true;
                                    --remainingCount;
                                 }
                              case 0:
                              default:
                                 break;
                              case 1:
                                 exceptionContext.recordHandlingException(caughtException, ((FlowInfo)exceptionFlow).unconditionalInits(), raisedException, caughtException, location, false);
                           }
                        }
                     }

                     ++i;
                  }
               }

               if (exceptionContext.isMethodContext) {
                  for(i = 0; i < raisedCount; ++i) {
                     TypeBinding raisedException;
                     if ((raisedException = raisedExceptions[i]) != null && raisedException.isUncheckedException(false)) {
                        --remainingCount;
                        raisedExceptions[i] = null;
                     }
                  }

                  boolean shouldMergeUnhandledException = exceptionContext instanceof ExceptionInferenceFlowContext;
                  if (exceptionContext.associatedNode instanceof AbstractMethodDeclaration) {
                     AbstractMethodDeclaration method = (AbstractMethodDeclaration)exceptionContext.associatedNode;
                     if (method.isConstructor() && method.binding.declaringClass.isAnonymousType()) {
                        shouldMergeUnhandledException = true;
                     }
                  }

                  if (shouldMergeUnhandledException) {
                     for(int i = 0; i < raisedCount; ++i) {
                        TypeBinding raisedException;
                        if ((raisedException = raisedExceptions[i]) != null) {
                           exceptionContext.mergeUnhandledException(raisedException);
                        }
                     }

                     return;
                  }
                  break;
               }
            } else if (traversedContext instanceof LoopingFlowContext) {
               if (abruptlyExitedLoops == null) {
                  abruptlyExitedLoops = new ArrayList(5);
               }

               abruptlyExitedLoops.add(traversedContext);
            }

            if (remainingCount == 0) {
               return;
            }

            traversedContext.recordReturnFrom(flowInfo.unconditionalInits());
            if (traversedContext instanceof InsideSubRoutineFlowContext) {
               ASTNode node = traversedContext.associatedNode;
               if (node instanceof TryStatement) {
                  TryStatement tryStatement = (TryStatement)node;
                  flowInfo.addInitializationsFrom(tryStatement.subRoutineInits);
               }
            }
         }

         label122:
         for(int i = 0; i < raisedCount; ++i) {
            TypeBinding exception;
            if ((exception = raisedExceptions[i]) != null) {
               for(int j = 0; j < i; ++j) {
                  if (TypeBinding.equalsEquals(raisedExceptions[j], exception)) {
                     continue label122;
                  }
               }

               scope.problemReporter().unhandledException(exception, location);
            }
         }

      }
   }

   public BranchLabel continueLabel() {
      return null;
   }

   public FlowInfo getInitsForFinalBlankInitializationCheck(TypeBinding declaringType, FlowInfo flowInfo) {
      FlowContext current = this;
      FlowInfo inits = flowInfo;

      do {
         if (current instanceof InitializationFlowContext) {
            InitializationFlowContext initializationContext = (InitializationFlowContext)current;
            if (TypeBinding.equalsEquals(((TypeDeclaration)initializationContext.associatedNode).binding, declaringType)) {
               return inits;
            }

            inits = initializationContext.initsBeforeContext;
            current = initializationContext.initializationParent;
         } else if (current instanceof ExceptionHandlingFlowContext) {
            if (current instanceof FieldInitsFakingFlowContext) {
               return FlowInfo.DEAD_END;
            }

            ExceptionHandlingFlowContext exceptionContext = (ExceptionHandlingFlowContext)current;
            current = exceptionContext.initializationParent == null ? exceptionContext.parent : exceptionContext.initializationParent;
         } else {
            current = current.getLocalParent();
         }
      } while(current != null);

      throw new IllegalStateException(declaringType.debugName());
   }

   public FlowContext getTargetContextForBreakLabel(char[] labelName) {
      FlowContext current = this;

      for(FlowContext lastNonReturningSubRoutine = null; current != null; current = current.getLocalParent()) {
         if (current.isNonReturningContext()) {
            lastNonReturningSubRoutine = current;
         }

         char[] currentLabelName;
         if ((currentLabelName = current.labelName()) != null && CharOperation.equals(currentLabelName, labelName)) {
            LabeledStatement var10000 = (LabeledStatement)current.associatedNode;
            var10000.bits |= 64;
            if (lastNonReturningSubRoutine == null) {
               return current;
            }

            return lastNonReturningSubRoutine;
         }
      }

      return null;
   }

   public FlowContext getTargetContextForContinueLabel(char[] labelName) {
      FlowContext current = this;
      FlowContext lastContinuable = null;

      for(FlowContext lastNonReturningSubRoutine = null; current != null; current = current.getLocalParent()) {
         if (current.isNonReturningContext()) {
            lastNonReturningSubRoutine = current;
         } else if (current.isContinuable()) {
            lastContinuable = current;
         }

         char[] currentLabelName;
         if ((currentLabelName = current.labelName()) != null && CharOperation.equals(currentLabelName, labelName)) {
            LabeledStatement var10000 = (LabeledStatement)current.associatedNode;
            var10000.bits |= 64;
            if (lastContinuable != null && current.associatedNode.concreteStatement() == lastContinuable.associatedNode) {
               if (lastNonReturningSubRoutine == null) {
                  return lastContinuable;
               }

               return lastNonReturningSubRoutine;
            }

            return NotContinuableContext;
         }
      }

      return null;
   }

   public FlowContext getTargetContextForDefaultBreak() {
      FlowContext current = this;

      for(FlowContext lastNonReturningSubRoutine = null; current != null; current = current.getLocalParent()) {
         if (current.isNonReturningContext()) {
            lastNonReturningSubRoutine = current;
         }

         if (current.isBreakable() && current.labelName() == null) {
            if (lastNonReturningSubRoutine == null) {
               return current;
            }

            return lastNonReturningSubRoutine;
         }
      }

      return null;
   }

   public FlowContext getTargetContextForDefaultContinue() {
      FlowContext current = this;

      for(FlowContext lastNonReturningSubRoutine = null; current != null; current = current.getLocalParent()) {
         if (current.isNonReturningContext()) {
            lastNonReturningSubRoutine = current;
         }

         if (current.isContinuable()) {
            if (lastNonReturningSubRoutine == null) {
               return current;
            }

            return lastNonReturningSubRoutine;
         }
      }

      return null;
   }

   public FlowContext getInitializationContext() {
      return null;
   }

   public FlowContext getLocalParent() {
      return !(this.associatedNode instanceof AbstractMethodDeclaration) && !(this.associatedNode instanceof TypeDeclaration) && !(this.associatedNode instanceof LambdaExpression) ? this.parent : null;
   }

   public String individualToString() {
      return "Flow context";
   }

   public FlowInfo initsOnBreak() {
      return FlowInfo.DEAD_END;
   }

   public UnconditionalFlowInfo initsOnReturn() {
      return FlowInfo.DEAD_END;
   }

   public boolean isBreakable() {
      return false;
   }

   public boolean isContinuable() {
      return false;
   }

   public boolean isNonReturningContext() {
      return false;
   }

   public boolean isSubRoutine() {
      return false;
   }

   public char[] labelName() {
      return null;
   }

   public void markFinallyNullStatus(LocalVariableBinding local, int nullStatus) {
      if (this.initsOnFinally != null) {
         if (this.conditionalLevel != -1) {
            if (this.conditionalLevel == 0) {
               this.initsOnFinally.markNullStatus(local, nullStatus);
            } else {
               UnconditionalFlowInfo newInfo = this.initsOnFinally.unconditionalCopy();
               newInfo.markNullStatus(local, nullStatus);
               this.initsOnFinally = this.initsOnFinally.mergedWith(newInfo);
            }
         }
      }
   }

   public void mergeFinallyNullInfo(FlowInfo flowInfo) {
      if (this.initsOnFinally != null) {
         if (this.conditionalLevel != -1) {
            if (this.conditionalLevel == 0) {
               this.initsOnFinally.addNullInfoFrom(flowInfo);
            } else {
               this.initsOnFinally = this.initsOnFinally.mergedWith(flowInfo.unconditionalCopy());
            }
         }
      }
   }

   public void recordAbruptExit() {
      if (this.conditionalLevel > -1) {
         ++this.conditionalLevel;
         if (!(this instanceof ExceptionHandlingFlowContext) && this.parent != null) {
            this.parent.recordAbruptExit();
         }
      }

   }

   public void recordBreakFrom(FlowInfo flowInfo) {
   }

   public void recordBreakTo(FlowContext targetContext) {
   }

   public void recordContinueFrom(FlowContext innerFlowContext, FlowInfo flowInfo) {
   }

   public boolean recordExitAgainstResource(BlockScope scope, FlowInfo flowInfo, FakedTrackingVariable trackingVar, ASTNode reference) {
      return false;
   }

   protected void recordProvidedExpectedTypes(TypeBinding providedType, TypeBinding expectedType, int nullCount) {
      if (nullCount == 0) {
         this.providedExpectedTypes = new TypeBinding[5][];
      } else {
         int size;
         if (this.providedExpectedTypes == null) {
            for(size = 5; size <= nullCount; size *= 2) {
            }

            this.providedExpectedTypes = new TypeBinding[size][];
         } else if (nullCount >= this.providedExpectedTypes.length) {
            size = this.providedExpectedTypes.length;
            System.arraycopy(this.providedExpectedTypes, 0, this.providedExpectedTypes = new TypeBinding[nullCount * 2][], 0, size);
         }
      }

      this.providedExpectedTypes[nullCount] = new TypeBinding[]{providedType, expectedType};
   }

   protected boolean recordFinalAssignment(VariableBinding variable, Reference finalReference) {
      return true;
   }

   protected void recordNullReference(LocalVariableBinding local, ASTNode location, int checkType, FlowInfo nullInfo) {
   }

   public void recordUnboxing(Scope scope, Expression expression, int nullStatus, FlowInfo flowInfo) {
      this.checkUnboxing(scope, expression, flowInfo);
   }

   protected void checkUnboxing(Scope scope, Expression expression, FlowInfo flowInfo) {
      int status = expression.nullStatus(flowInfo, this);
      if ((status & 2) != 0) {
         scope.problemReporter().nullUnboxing(expression, expression.resolvedType);
      } else if ((status & 16) != 0) {
         scope.problemReporter().potentialNullUnboxing(expression, expression.resolvedType);
      } else if ((status & 4) == 0) {
         if (this.parent != null) {
            this.parent.recordUnboxing(scope, expression, 1, flowInfo);
         }

      }
   }

   public void recordReturnFrom(UnconditionalFlowInfo flowInfo) {
   }

   public void recordSettingFinal(VariableBinding variable, Reference finalReference, FlowInfo flowInfo) {
      if ((flowInfo.tagBits & 1) == 0) {
         for(FlowContext context = this; context != null && context.recordFinalAssignment(variable, finalReference); context = context.getLocalParent()) {
         }
      }

   }

   public void recordUsingNullReference(Scope scope, LocalVariableBinding local, ASTNode location, int checkType, FlowInfo flowInfo) {
      if ((flowInfo.tagBits & 3) == 0 && !flowInfo.isDefinitelyUnknown(local)) {
         checkType |= this.tagBits & 4096;
         int checkTypeWithoutHideNullWarning = checkType & -61441;
         switch (checkTypeWithoutHideNullWarning) {
            case 3:
               if (flowInfo.isDefinitelyNull(local)) {
                  scope.problemReporter().localVariableNullReference(local, location);
                  return;
               }

               if (flowInfo.isPotentiallyNull(local)) {
                  if (local.type.isFreeTypeVariable()) {
                     scope.problemReporter().localVariableFreeTypeVariableReference(local, location);
                     return;
                  }

                  scope.problemReporter().localVariablePotentialNullReference(local, location);
                  return;
               }
               break;
            case 256:
            case 512:
               if (flowInfo.isDefinitelyNonNull(local)) {
                  if (checkTypeWithoutHideNullWarning == 512) {
                     if ((checkType & 4096) == 0) {
                        scope.problemReporter().localVariableRedundantCheckOnNonNull(local, location);
                     }

                     flowInfo.initsWhenFalse().setReachMode(2);
                  } else {
                     scope.problemReporter().localVariableNonNullComparedToNull(local, location);
                     flowInfo.initsWhenTrue().setReachMode(2);
                  }

                  return;
               }

               if (flowInfo.cannotBeDefinitelyNullOrNonNull(local)) {
                  return;
               }
            case 257:
            case 513:
            case 769:
            case 1025:
               Expression reference = (Expression)location;
               if (flowInfo.isDefinitelyNull(local)) {
                  switch (checkTypeWithoutHideNullWarning & -61696) {
                     case 256:
                        if ((checkTypeWithoutHideNullWarning & 255) == 1 && (reference.implicitConversion & 1024) != 0) {
                           scope.problemReporter().localVariableNullReference(local, reference);
                           return;
                        }

                        if ((checkType & 4096) == 0) {
                           scope.problemReporter().localVariableRedundantCheckOnNull(local, reference);
                        }

                        flowInfo.initsWhenFalse().setReachMode(2);
                        return;
                     case 512:
                        if ((checkTypeWithoutHideNullWarning & 255) == 1 && (reference.implicitConversion & 1024) != 0) {
                           scope.problemReporter().localVariableNullReference(local, reference);
                           return;
                        }

                        scope.problemReporter().localVariableNullComparedToNonNull(local, reference);
                        flowInfo.initsWhenTrue().setReachMode(2);
                        return;
                     case 768:
                        scope.problemReporter().localVariableRedundantNullAssignment(local, reference);
                        return;
                     case 1024:
                        scope.problemReporter().localVariableNullInstanceof(local, reference);
                        return;
                  }
               } else if (flowInfo.isPotentiallyNull(local)) {
                  switch (checkTypeWithoutHideNullWarning & -61696) {
                     case 256:
                        if ((checkTypeWithoutHideNullWarning & 255) == 1 && (reference.implicitConversion & 1024) != 0) {
                           scope.problemReporter().localVariablePotentialNullReference(local, reference);
                           return;
                        }
                        break;
                     case 512:
                        if ((checkTypeWithoutHideNullWarning & 255) == 1 && (reference.implicitConversion & 1024) != 0) {
                           scope.problemReporter().localVariablePotentialNullReference(local, reference);
                           return;
                        }
                  }
               } else if (flowInfo.cannotBeDefinitelyNullOrNonNull(local)) {
                  return;
               }
         }

         if (this.parent != null) {
            this.parent.recordUsingNullReference(scope, local, location, checkType, flowInfo);
         }

      }
   }

   void removeFinalAssignmentIfAny(Reference reference) {
   }

   public SubRoutineStatement subroutine() {
      return null;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer();
      FlowContext current = this;

      int parentsCount;
      for(parentsCount = 0; (current = current.parent) != null; ++parentsCount) {
      }

      FlowContext[] parents = new FlowContext[parentsCount + 1];
      current = this;

      for(int index = parentsCount; index >= 0; current = current.parent) {
         parents[index--] = current;
      }

      int j;
      for(j = 0; j < parentsCount; ++j) {
         for(int j = 0; j < j; ++j) {
            buffer.append('\t');
         }

         buffer.append(parents[j].individualToString()).append('\n');
      }

      buffer.append('*');

      for(j = 0; j < parentsCount + 1; ++j) {
         buffer.append('\t');
      }

      buffer.append(this.individualToString()).append('\n');
      return buffer.toString();
   }

   public void recordNullityMismatch(BlockScope currentScope, Expression expression, TypeBinding providedType, TypeBinding expectedType, FlowInfo flowInfo, int nullStatus, NullAnnotationMatching annotationStatus) {
      if (providedType != null) {
         if (expression.localVariableBinding() != null) {
            for(FlowContext currentContext = this; currentContext != null; currentContext = currentContext.parent) {
               int isInsideAssert = 0;
               if ((this.tagBits & 4096) != 0) {
                  isInsideAssert = 4096;
               }

               if (currentContext.internalRecordNullityMismatch(expression, providedType, flowInfo, nullStatus, expectedType, 128 | isInsideAssert)) {
                  return;
               }
            }
         }

         if (annotationStatus != null) {
            currentScope.problemReporter().nullityMismatchingTypeAnnotation(expression, providedType, expectedType, annotationStatus);
         } else {
            currentScope.problemReporter().nullityMismatch(expression, providedType, expectedType, nullStatus, currentScope.environment().getNonNullAnnotationName());
         }

      }
   }

   protected boolean internalRecordNullityMismatch(Expression expression, TypeBinding providedType, FlowInfo flowInfo, int nullStatus, TypeBinding expectedType, int checkType) {
      return false;
   }
}

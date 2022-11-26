package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.flow.LoopingFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ArrayBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.CaptureBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class ForeachStatement extends Statement {
   public LocalDeclaration elementVariable;
   public int elementVariableImplicitWidening = -1;
   public Expression collection;
   public Statement action;
   private int kind;
   private static final int ARRAY = 0;
   private static final int RAW_ITERABLE = 1;
   private static final int GENERIC_ITERABLE = 2;
   private TypeBinding iteratorReceiverType;
   private TypeBinding collectionElementType;
   private BranchLabel breakLabel;
   private BranchLabel continueLabel;
   public BlockScope scope;
   public LocalVariableBinding indexVariable;
   public LocalVariableBinding collectionVariable;
   public LocalVariableBinding maxVariable;
   private static final char[] SecretIteratorVariableName = " iterator".toCharArray();
   private static final char[] SecretIndexVariableName = " index".toCharArray();
   private static final char[] SecretCollectionVariableName = " collection".toCharArray();
   private static final char[] SecretMaxVariableName = " max".toCharArray();
   int postCollectionInitStateIndex = -1;
   int mergedInitStateIndex = -1;

   public ForeachStatement(LocalDeclaration elementVariable, int start) {
      this.elementVariable = elementVariable;
      this.sourceStart = start;
      this.kind = -1;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      this.breakLabel = new BranchLabel();
      this.continueLabel = new BranchLabel();
      int initialComplaintLevel = (flowInfo.reachMode() & 3) != 0 ? 1 : 0;
      flowInfo = this.elementVariable.analyseCode(this.scope, flowContext, flowInfo);
      FlowInfo condInfo = this.collection.analyseCode(this.scope, flowContext, flowInfo.copy());
      this.collection.checkNPE(currentScope, flowContext, condInfo.copy(), 1);
      LocalVariableBinding elementVarBinding = this.elementVariable.binding;
      condInfo.markAsDefinitelyAssigned(elementVarBinding);
      this.postCollectionInitStateIndex = currentScope.methodScope().recordInitializationStates(condInfo);
      LoopingFlowContext loopingContext = new LoopingFlowContext(flowContext, flowInfo, this, this.breakLabel, this.continueLabel, this.scope, true);
      UnconditionalFlowInfo actionInfo = condInfo.nullInfoLessUnconditionalCopy();
      actionInfo.markAsDefinitelyUnknown(elementVarBinding);
      if (currentScope.compilerOptions().isAnnotationBasedNullAnalysisEnabled) {
         int elementNullStatus = NullAnnotationMatching.nullStatusFromExpressionType(this.collectionElementType);
         int nullStatus = NullAnnotationMatching.checkAssignment(currentScope, flowContext, elementVarBinding, (FlowInfo)null, elementNullStatus, this.collection, this.collectionElementType);
         if ((elementVarBinding.type.tagBits & 2L) == 0L) {
            actionInfo.markNullStatus(elementVarBinding, nullStatus);
         }
      }

      FlowInfo exitBranch;
      if (this.action != null && (!this.action.isEmptyBlock() || currentScope.compilerOptions().complianceLevel > 3080192L)) {
         if (this.action.complainIfUnreachable(actionInfo, this.scope, initialComplaintLevel, true) < 2) {
            actionInfo = this.action.analyseCode(this.scope, loopingContext, actionInfo).unconditionalCopy();
            if (this.action instanceof Block) {
               FakedTrackingVariable.markForeachElementVar(this.elementVariable);
               this.scope.checkUnclosedCloseables(actionInfo, loopingContext, (ASTNode)null, (BlockScope)null);
            }
         }

         exitBranch = flowInfo.unconditionalCopy().addInitializationsFrom(condInfo.initsWhenFalse());
         if ((actionInfo.tagBits & loopingContext.initsOnContinue.tagBits & 1) != 0) {
            this.continueLabel = null;
         } else {
            actionInfo = actionInfo.mergedWith(loopingContext.initsOnContinue);
            loopingContext.complainOnDeferredFinalChecks(this.scope, actionInfo);
            exitBranch.addPotentialInitializationsFrom(actionInfo);
         }
      } else {
         exitBranch = condInfo.initsWhenFalse();
         if (this.action instanceof Block && !this.action.isEmptyBlock()) {
            this.scope.checkUnclosedCloseables(actionInfo, loopingContext, (ASTNode)null, (BlockScope)null);
         }
      }

      boolean hasEmptyAction = this.action == null || this.action.isEmptyBlock() || (this.action.bits & 1) != 0;
      switch (this.kind) {
         case 0:
            if (!hasEmptyAction || elementVarBinding.resolvedPosition != -1) {
               this.collectionVariable.useFlag = 1;
               if (this.continueLabel != null) {
                  this.indexVariable.useFlag = 1;
                  this.maxVariable.useFlag = 1;
               }
            }
            break;
         case 1:
         case 2:
            this.indexVariable.useFlag = 1;
      }

      loopingContext.complainOnDeferredNullChecks(currentScope, actionInfo);
      if (loopingContext.hasEscapingExceptions()) {
         FlowInfo loopbackFlowInfo = flowInfo.copy();
         if (this.continueLabel != null) {
            loopbackFlowInfo = ((FlowInfo)loopbackFlowInfo).mergedWith(((FlowInfo)loopbackFlowInfo).unconditionalCopy().addNullInfoFrom(actionInfo).unconditionalInits());
         }

         loopingContext.simulateThrowAfterLoopBack((FlowInfo)loopbackFlowInfo);
      }

      FlowInfo mergedInfo = FlowInfo.mergedOptimizedBranches((FlowInfo)((loopingContext.initsOnBreak.tagBits & 3) != 0 ? loopingContext.initsOnBreak : flowInfo.addInitializationsFrom(loopingContext.initsOnBreak)), false, exitBranch, false, true);
      mergedInfo.resetAssignmentInfo(this.elementVariable.binding);
      this.mergedInitStateIndex = currentScope.methodScope().recordInitializationStates(mergedInfo);
      return mergedInfo;
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream) {
      if ((this.bits & Integer.MIN_VALUE) != 0) {
         int pc = codeStream.position;
         boolean hasEmptyAction = this.action == null || this.action.isEmptyBlock() || (this.action.bits & 1) != 0;
         if (hasEmptyAction && this.elementVariable.binding.resolvedPosition == -1 && this.kind == 0) {
            this.collection.generateCode(this.scope, codeStream, false);
            codeStream.exitUserScope(this.scope);
            if (this.mergedInitStateIndex != -1) {
               codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
               codeStream.addDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
            }

            codeStream.recordPositionsFrom(pc, this.sourceStart);
         } else {
            switch (this.kind) {
               case 0:
                  this.collection.generateCode(this.scope, codeStream, true);
                  codeStream.store(this.collectionVariable, true);
                  codeStream.addVariable(this.collectionVariable);
                  if (this.continueLabel != null) {
                     codeStream.arraylength();
                     codeStream.store(this.maxVariable, false);
                     codeStream.addVariable(this.maxVariable);
                     codeStream.iconst_0();
                     codeStream.store(this.indexVariable, false);
                     codeStream.addVariable(this.indexVariable);
                  }
                  break;
               case 1:
               case 2:
                  this.collection.generateCode(this.scope, codeStream, true);
                  codeStream.invokeIterableIterator(this.iteratorReceiverType);
                  codeStream.store(this.indexVariable, false);
                  codeStream.addVariable(this.indexVariable);
            }

            BranchLabel actionLabel = new BranchLabel(codeStream);
            actionLabel.tagBits |= 2;
            BranchLabel conditionLabel = new BranchLabel(codeStream);
            conditionLabel.tagBits |= 2;
            this.breakLabel.initialize(codeStream);
            int continuationPC;
            if (this.continueLabel == null) {
               conditionLabel.place();
               continuationPC = codeStream.position;
               switch (this.kind) {
                  case 0:
                     codeStream.arraylength();
                     codeStream.ifeq(this.breakLabel);
                     break;
                  case 1:
                  case 2:
                     codeStream.load(this.indexVariable);
                     codeStream.invokeJavaUtilIteratorHasNext();
                     codeStream.ifeq(this.breakLabel);
               }

               codeStream.recordPositionsFrom(continuationPC, this.elementVariable.sourceStart);
            } else {
               this.continueLabel.initialize(codeStream);
               BranchLabel var10000 = this.continueLabel;
               var10000.tagBits |= 2;
               codeStream.goto_(conditionLabel);
            }

            actionLabel.place();
            switch (this.kind) {
               case 0:
                  if (this.elementVariable.binding.resolvedPosition != -1) {
                     codeStream.load(this.collectionVariable);
                     if (this.continueLabel == null) {
                        codeStream.iconst_0();
                     } else {
                        codeStream.load(this.indexVariable);
                     }

                     codeStream.arrayAt(this.collectionElementType.id);
                     if (this.elementVariableImplicitWidening != -1) {
                        codeStream.generateImplicitConversion(this.elementVariableImplicitWidening);
                     }

                     codeStream.store(this.elementVariable.binding, false);
                     codeStream.addVisibleLocalVariable(this.elementVariable.binding);
                     if (this.postCollectionInitStateIndex != -1) {
                        codeStream.addDefinitelyAssignedVariables(currentScope, this.postCollectionInitStateIndex);
                     }
                  }
                  break;
               case 1:
               case 2:
                  codeStream.load(this.indexVariable);
                  codeStream.invokeJavaUtilIteratorNext();
                  if (this.elementVariable.binding.type.id != 1) {
                     if (this.elementVariableImplicitWidening != -1) {
                        codeStream.checkcast(this.collectionElementType);
                        codeStream.generateImplicitConversion(this.elementVariableImplicitWidening);
                     } else {
                        codeStream.checkcast(this.elementVariable.binding.type);
                     }
                  }

                  if (this.elementVariable.binding.resolvedPosition == -1) {
                     switch (this.elementVariable.binding.type.id) {
                        case 7:
                        case 8:
                           codeStream.pop2();
                           break;
                        default:
                           codeStream.pop();
                     }
                  } else {
                     codeStream.store(this.elementVariable.binding, false);
                     codeStream.addVisibleLocalVariable(this.elementVariable.binding);
                     if (this.postCollectionInitStateIndex != -1) {
                        codeStream.addDefinitelyAssignedVariables(currentScope, this.postCollectionInitStateIndex);
                     }
                  }
            }

            if (!hasEmptyAction) {
               this.action.generateCode(this.scope, codeStream);
            }

            codeStream.removeVariable(this.elementVariable.binding);
            if (this.postCollectionInitStateIndex != -1) {
               codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.postCollectionInitStateIndex);
            }

            if (this.continueLabel != null) {
               this.continueLabel.place();
               continuationPC = codeStream.position;
               switch (this.kind) {
                  case 0:
                     if (!hasEmptyAction || this.elementVariable.binding.resolvedPosition >= 0) {
                        codeStream.iinc(this.indexVariable.resolvedPosition, 1);
                     }

                     conditionLabel.place();
                     codeStream.load(this.indexVariable);
                     codeStream.load(this.maxVariable);
                     codeStream.if_icmplt(actionLabel);
                     break;
                  case 1:
                  case 2:
                     conditionLabel.place();
                     codeStream.load(this.indexVariable);
                     codeStream.invokeJavaUtilIteratorHasNext();
                     codeStream.ifne(actionLabel);
               }

               codeStream.recordPositionsFrom(continuationPC, this.elementVariable.sourceStart);
            }

            switch (this.kind) {
               case 0:
                  codeStream.removeVariable(this.indexVariable);
                  codeStream.removeVariable(this.maxVariable);
                  codeStream.removeVariable(this.collectionVariable);
                  break;
               case 1:
               case 2:
                  codeStream.removeVariable(this.indexVariable);
            }

            codeStream.exitUserScope(this.scope);
            if (this.mergedInitStateIndex != -1) {
               codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
               codeStream.addDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
            }

            this.breakLabel.place();
            codeStream.recordPositionsFrom(pc, this.sourceStart);
         }
      }
   }

   public StringBuffer printStatement(int indent, StringBuffer output) {
      printIndent(indent, output).append("for (");
      this.elementVariable.printAsExpression(0, output);
      output.append(" : ");
      if (this.collection != null) {
         this.collection.print(0, output).append(") ");
      } else {
         output.append(')');
      }

      if (this.action == null) {
         output.append(';');
      } else {
         output.append('\n');
         this.action.printStatement(indent + 1, output);
      }

      return output;
   }

   public static TypeBinding getCollectionElementType(BlockScope scope, TypeBinding collectionType) {
      if (collectionType == null) {
         return null;
      } else {
         boolean isTargetJsr14 = scope.compilerOptions().targetJDK == 3145728L;
         if (collectionType.isCapture()) {
            TypeBinding upperBound = ((CaptureBinding)collectionType).firstBound;
            if (upperBound != null && upperBound.isArrayType()) {
               collectionType = upperBound;
            }
         }

         if (collectionType.isArrayType()) {
            return ((ArrayBinding)collectionType).elementsType();
         } else if (collectionType instanceof ReferenceBinding) {
            ReferenceBinding iterableType = ((ReferenceBinding)collectionType).findSuperTypeOriginatingFrom(38, false);
            if (iterableType == null && isTargetJsr14) {
               iterableType = ((ReferenceBinding)collectionType).findSuperTypeOriginatingFrom(59, false);
            }

            if (iterableType == null) {
               return null;
            } else {
               TypeBinding[] arguments = null;
               switch (iterableType.kind()) {
                  case 260:
                     arguments = ((ParameterizedTypeBinding)iterableType).arguments;
                     break;
                  case 1028:
                     return scope.getJavaLangObject();
                  case 2052:
                     arguments = iterableType.typeVariables();
                     break;
                  default:
                     return null;
               }

               return (TypeBinding)(((Object[])arguments).length != 1 ? null : ((Object[])arguments)[0]);
            }
         } else {
            return null;
         }
      }
   }

   public void resolve(BlockScope upperScope) {
      this.scope = new BlockScope(upperScope);
      this.scope.blockStatement = this;
      this.elementVariable.resolve(this.scope);
      TypeBinding elementType = this.elementVariable.type.resolvedType;
      TypeBinding collectionType = this.collection == null ? null : this.collection.resolveType(upperScope);
      if (this.elementVariable.isTypeNameVar(upperScope)) {
         if (this.elementVariable.type.dimensions() > 0 || this.elementVariable.type.extraDimensions() > 0) {
            upperScope.problemReporter().varLocalCannotBeArray(this.elementVariable);
         }

         if (TypeBinding.equalsEquals(TypeBinding.NULL, collectionType)) {
            upperScope.problemReporter().varLocalInitializedToNull(this.elementVariable);
         } else if (TypeBinding.equalsEquals(TypeBinding.VOID, collectionType)) {
            upperScope.problemReporter().varLocalInitializedToVoid(this.elementVariable);
         }

         if ((elementType = getCollectionElementType(this.scope, collectionType)) == null) {
            elementType = collectionType;
         } else {
            elementType = this.elementVariable.patchType(elementType);
         }

         if (this.elementVariable.binding != null && this.elementVariable.binding.isValidBinding()) {
            this.elementVariable.validateNullAnnotations(this.scope);
         }
      }

      TypeBinding expectedCollectionType = null;
      if (elementType != null && collectionType != null) {
         boolean isTargetJsr14 = this.scope.compilerOptions().targetJDK == 3145728L;
         if (collectionType.isCapture()) {
            TypeBinding upperBound = ((CaptureBinding)collectionType).firstBound;
            if (upperBound != null && upperBound.isArrayType()) {
               collectionType = upperBound;
            }
         }

         if (collectionType.isArrayType()) {
            this.kind = 0;
            this.collectionElementType = ((ArrayBinding)collectionType).elementsType();
            if (!this.collectionElementType.isCompatibleWith(elementType) && !this.scope.isBoxingCompatibleWith(this.collectionElementType, elementType)) {
               this.scope.problemReporter().notCompatibleTypesErrorInForeach(this.collection, this.collectionElementType, elementType);
            } else if (this.collectionElementType.needsUncheckedConversion(elementType)) {
               this.scope.problemReporter().unsafeElementTypeConversion(this.collection, this.collectionElementType, elementType);
            }

            int compileTimeTypeID = this.collectionElementType.id;
            if (elementType.isBaseType()) {
               this.collection.computeConversion(this.scope, collectionType, collectionType);
               if (!this.collectionElementType.isBaseType()) {
                  compileTimeTypeID = this.scope.environment().computeBoxingType(this.collectionElementType).id;
                  this.elementVariableImplicitWidening = 1024;
                  if (elementType.isBaseType()) {
                     this.elementVariableImplicitWidening |= (elementType.id << 4) + compileTimeTypeID;
                     this.scope.problemReporter().autoboxing(this.collection, this.collectionElementType, elementType);
                  }
               } else {
                  this.elementVariableImplicitWidening = (elementType.id << 4) + compileTimeTypeID;
               }
            } else if (this.collectionElementType.isBaseType()) {
               this.collection.computeConversion(this.scope, collectionType, collectionType);
               int boxedID = this.scope.environment().computeBoxingType(this.collectionElementType).id;
               this.elementVariableImplicitWidening = 512 | compileTimeTypeID << 4 | compileTimeTypeID;
               this.scope.problemReporter().autoboxing(this.collection, this.collectionElementType, elementType);
            } else {
               expectedCollectionType = upperScope.createArrayType(elementType, 1);
               this.collection.computeConversion(this.scope, expectedCollectionType, collectionType);
            }
         } else if (collectionType instanceof ReferenceBinding) {
            ReferenceBinding iterableType = ((ReferenceBinding)collectionType).findSuperTypeOriginatingFrom(38, false);
            if (iterableType == null && isTargetJsr14) {
               iterableType = ((ReferenceBinding)collectionType).findSuperTypeOriginatingFrom(59, false);
            }

            if (iterableType != null) {
               label172: {
                  this.iteratorReceiverType = collectionType.erasure();
                  if (isTargetJsr14) {
                     if (((ReferenceBinding)this.iteratorReceiverType).findSuperTypeOriginatingFrom(59, false) == null) {
                        this.iteratorReceiverType = iterableType;
                        this.collection.computeConversion(this.scope, iterableType, collectionType);
                     } else {
                        this.collection.computeConversion(this.scope, collectionType, collectionType);
                     }
                  } else if (((ReferenceBinding)this.iteratorReceiverType).findSuperTypeOriginatingFrom(38, false) == null) {
                     this.iteratorReceiverType = iterableType;
                     this.collection.computeConversion(this.scope, iterableType, collectionType);
                  } else {
                     this.collection.computeConversion(this.scope, collectionType, collectionType);
                  }

                  TypeBinding[] arguments = null;
                  switch (iterableType.kind()) {
                     case 260:
                        arguments = ((ParameterizedTypeBinding)iterableType).arguments;
                        break;
                     case 1028:
                        this.kind = 1;
                        this.collectionElementType = this.scope.getJavaLangObject();
                        if (!this.collectionElementType.isCompatibleWith(elementType) && !this.scope.isBoxingCompatibleWith(this.collectionElementType, elementType)) {
                           this.scope.problemReporter().notCompatibleTypesErrorInForeach(this.collection, this.collectionElementType, elementType);
                        }
                        break label172;
                     case 2052:
                        arguments = iterableType.typeVariables();
                        break;
                     default:
                        break label172;
                  }

                  if (((Object[])arguments).length == 1) {
                     this.kind = 2;
                     this.collectionElementType = (TypeBinding)((Object[])arguments)[0];
                     if (!this.collectionElementType.isCompatibleWith(elementType) && !this.scope.isBoxingCompatibleWith(this.collectionElementType, elementType)) {
                        this.scope.problemReporter().notCompatibleTypesErrorInForeach(this.collection, this.collectionElementType, elementType);
                     } else if (this.collectionElementType.needsUncheckedConversion(elementType)) {
                        this.scope.problemReporter().unsafeElementTypeConversion(this.collection, this.collectionElementType, elementType);
                     }

                     int compileTimeTypeID = this.collectionElementType.id;
                     if (elementType.isBaseType()) {
                        if (!this.collectionElementType.isBaseType()) {
                           compileTimeTypeID = this.scope.environment().computeBoxingType(this.collectionElementType).id;
                           this.elementVariableImplicitWidening = 1024;
                           if (elementType.isBaseType()) {
                              this.elementVariableImplicitWidening |= (elementType.id << 4) + compileTimeTypeID;
                           }
                        } else {
                           this.elementVariableImplicitWidening = (elementType.id << 4) + compileTimeTypeID;
                        }
                     } else if (this.collectionElementType.isBaseType()) {
                        this.elementVariableImplicitWidening = 512 | compileTimeTypeID << 4 | compileTimeTypeID;
                     }
                  }
               }
            }
         }

         switch (this.kind) {
            case 0:
               this.indexVariable = new LocalVariableBinding(SecretIndexVariableName, TypeBinding.INT, 0, false);
               this.scope.addLocalVariable(this.indexVariable);
               this.indexVariable.setConstant(Constant.NotAConstant);
               this.maxVariable = new LocalVariableBinding(SecretMaxVariableName, TypeBinding.INT, 0, false);
               this.scope.addLocalVariable(this.maxVariable);
               this.maxVariable.setConstant(Constant.NotAConstant);
               if (expectedCollectionType == null) {
                  this.collectionVariable = new LocalVariableBinding(SecretCollectionVariableName, collectionType, 0, false);
               } else {
                  this.collectionVariable = new LocalVariableBinding(SecretCollectionVariableName, expectedCollectionType, 0, false);
               }

               this.scope.addLocalVariable(this.collectionVariable);
               this.collectionVariable.setConstant(Constant.NotAConstant);
               break;
            case 1:
            case 2:
               this.indexVariable = new LocalVariableBinding(SecretIteratorVariableName, this.scope.getJavaUtilIterator(), 0, false);
               this.scope.addLocalVariable(this.indexVariable);
               this.indexVariable.setConstant(Constant.NotAConstant);
               break;
            default:
               if (isTargetJsr14) {
                  this.scope.problemReporter().invalidTypeForCollectionTarget14(this.collection);
               } else {
                  this.scope.problemReporter().invalidTypeForCollection(this.collection);
               }
         }
      }

      if (this.action != null) {
         this.action.resolve(this.scope);
      }

   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      if (visitor.visit(this, blockScope)) {
         this.elementVariable.traverse(visitor, this.scope);
         if (this.collection != null) {
            this.collection.traverse(visitor, this.scope);
         }

         if (this.action != null) {
            this.action.traverse(visitor, this.scope);
         }
      }

      visitor.endVisit(this, blockScope);
   }

   public boolean doesNotCompleteNormally() {
      return false;
   }
}

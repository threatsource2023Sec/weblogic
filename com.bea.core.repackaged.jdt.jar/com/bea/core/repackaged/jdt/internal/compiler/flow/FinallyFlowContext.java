package com.bea.core.repackaged.jdt.internal.compiler.flow;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Expression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.NullAnnotationMatching;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Reference;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.VariableBinding;

public class FinallyFlowContext extends TryFlowContext {
   Reference[] finalAssignments;
   VariableBinding[] finalVariables;
   int assignCount;
   LocalVariableBinding[] nullLocals;
   ASTNode[] nullReferences;
   int[] nullCheckTypes;
   int nullCount;
   public FlowContext tryContext;

   public FinallyFlowContext(FlowContext parent, ASTNode associatedNode, ExceptionHandlingFlowContext tryContext) {
      super(parent, associatedNode);
      this.tryContext = tryContext;
   }

   public void complainOnDeferredChecks(FlowInfo flowInfo, BlockScope scope) {
      int i;
      for(i = 0; i < this.assignCount; ++i) {
         VariableBinding variable = this.finalVariables[i];
         if (variable != null) {
            boolean complained = false;
            if (variable instanceof FieldBinding) {
               if (flowInfo.isPotentiallyAssigned((FieldBinding)variable)) {
                  complained = true;
                  scope.problemReporter().duplicateInitializationOfBlankFinalField((FieldBinding)variable, this.finalAssignments[i]);
               }
            } else if (flowInfo.isPotentiallyAssigned((LocalVariableBinding)variable)) {
               variable.tagBits &= -2049L;
               if (variable.isFinal()) {
                  complained = true;
                  scope.problemReporter().duplicateInitializationOfFinalLocal((LocalVariableBinding)variable, this.finalAssignments[i]);
               }
            }

            if (complained) {
               for(FlowContext currentContext = this.getLocalParent(); currentContext != null; currentContext = currentContext.getLocalParent()) {
                  currentContext.removeFinalAssignmentIfAny(this.finalAssignments[i]);
               }
            }
         }
      }

      ASTNode location;
      if ((this.tagBits & 1) != 0) {
         for(i = 0; i < this.nullCount; ++i) {
            location = this.nullReferences[i];
            switch (this.nullCheckTypes[i] & -61441) {
               case 16:
                  this.checkUnboxing(scope, (Expression)location, flowInfo);
                  break;
               case 128:
                  int nullStatus = flowInfo.nullStatus(this.nullLocals[i]);
                  if (nullStatus != 4) {
                     this.parent.recordNullityMismatch(scope, (Expression)location, this.providedExpectedTypes[i][0], this.providedExpectedTypes[i][1], flowInfo, nullStatus, (NullAnnotationMatching)null);
                  }
                  break;
               default:
                  this.parent.recordUsingNullReference(scope, this.nullLocals[i], this.nullReferences[i], this.nullCheckTypes[i], flowInfo);
            }
         }
      } else {
         for(i = 0; i < this.nullCount; ++i) {
            location = this.nullReferences[i];
            LocalVariableBinding local = this.nullLocals[i];
            switch (this.nullCheckTypes[i] & -61441) {
               case 3:
                  if (flowInfo.isDefinitelyNull(local)) {
                     scope.problemReporter().localVariableNullReference(local, location);
                  } else if (flowInfo.isPotentiallyNull(local)) {
                     scope.problemReporter().localVariablePotentialNullReference(local, location);
                  }
                  break;
               case 16:
                  this.checkUnboxing(scope, (Expression)location, flowInfo);
                  break;
               case 128:
                  int nullStatus = flowInfo.nullStatus(local);
                  if (nullStatus != 4) {
                     char[][] annotationName = scope.environment().getNonNullAnnotationName();
                     scope.problemReporter().nullityMismatch((Expression)location, this.providedExpectedTypes[i][0], this.providedExpectedTypes[i][1], nullStatus, annotationName);
                  }
                  break;
               case 256:
               case 512:
                  if (flowInfo.isDefinitelyNonNull(local)) {
                     if ((this.nullCheckTypes[i] & -61441) == 512) {
                        if ((this.nullCheckTypes[i] & 4096) == 0) {
                           scope.problemReporter().localVariableRedundantCheckOnNonNull(local, location);
                        }
                     } else {
                        scope.problemReporter().localVariableNonNullComparedToNull(local, location);
                     }
                     break;
                  }
               case 257:
               case 513:
               case 769:
               case 1025:
                  Expression expression = (Expression)location;
                  if (flowInfo.isDefinitelyNull(local)) {
                     switch (this.nullCheckTypes[i] & -61696) {
                        case 256:
                           if ((this.nullCheckTypes[i] & 255 & -61441) == 1 && (expression.implicitConversion & 1024) != 0) {
                              scope.problemReporter().localVariableNullReference(local, expression);
                           } else if ((this.nullCheckTypes[i] & 4096) == 0) {
                              scope.problemReporter().localVariableRedundantCheckOnNull(local, expression);
                           }
                           break;
                        case 512:
                           if ((this.nullCheckTypes[i] & 255 & -61441) == 1 && (expression.implicitConversion & 1024) != 0) {
                              scope.problemReporter().localVariableNullReference(local, expression);
                              break;
                           }

                           scope.problemReporter().localVariableNullComparedToNonNull(local, expression);
                           break;
                        case 768:
                           scope.problemReporter().localVariableRedundantNullAssignment(local, expression);
                           break;
                        case 1024:
                           scope.problemReporter().localVariableNullInstanceof(local, expression);
                     }
                  } else if (flowInfo.isPotentiallyNull(local)) {
                     switch (this.nullCheckTypes[i] & -61696) {
                        case 256:
                           this.nullReferences[i] = null;
                           if ((this.nullCheckTypes[i] & 255 & -61441) == 1 && (expression.implicitConversion & 1024) != 0) {
                              scope.problemReporter().localVariablePotentialNullReference(local, expression);
                           }
                           break;
                        case 512:
                           this.nullReferences[i] = null;
                           if ((this.nullCheckTypes[i] & 255 & -61441) == 1 && (expression.implicitConversion & 1024) != 0) {
                              scope.problemReporter().localVariablePotentialNullReference(local, expression);
                           }
                     }
                  }
            }
         }
      }

   }

   public String individualToString() {
      StringBuffer buffer = new StringBuffer("Finally flow context");
      buffer.append("[finalAssignments count - ").append(this.assignCount).append(']');
      buffer.append("[nullReferences count - ").append(this.nullCount).append(']');
      return buffer.toString();
   }

   public boolean isSubRoutine() {
      return true;
   }

   protected boolean recordFinalAssignment(VariableBinding binding, Reference finalAssignment) {
      if (this.assignCount == 0) {
         this.finalAssignments = new Reference[5];
         this.finalVariables = new VariableBinding[5];
      } else {
         if (this.assignCount == this.finalAssignments.length) {
            System.arraycopy(this.finalAssignments, 0, this.finalAssignments = new Reference[this.assignCount * 2], 0, this.assignCount);
         }

         System.arraycopy(this.finalVariables, 0, this.finalVariables = new VariableBinding[this.assignCount * 2], 0, this.assignCount);
      }

      this.finalAssignments[this.assignCount] = finalAssignment;
      this.finalVariables[this.assignCount++] = binding;
      return true;
   }

   public void recordUsingNullReference(Scope scope, LocalVariableBinding local, ASTNode location, int checkType, FlowInfo flowInfo) {
      if ((flowInfo.tagBits & 3) == 0 && !flowInfo.isDefinitelyUnknown(local)) {
         checkType |= this.tagBits & 4096;
         int checkTypeWithoutHideNullWarning = checkType & -61441;
         Expression reference;
         if ((this.tagBits & 1) != 0) {
            switch (checkTypeWithoutHideNullWarning) {
               case 3:
                  if (flowInfo.cannotBeNull(local)) {
                     return;
                  }

                  if (flowInfo.canOnlyBeNull(local)) {
                     scope.problemReporter().localVariableNullReference(local, location);
                     return;
                  }
                  break;
               case 256:
               case 257:
               case 512:
               case 513:
               case 769:
               case 1025:
                  reference = (Expression)location;
                  if (flowInfo.cannotBeNull(local)) {
                     if (checkTypeWithoutHideNullWarning == 512) {
                        if ((checkType & 4096) == 0) {
                           scope.problemReporter().localVariableRedundantCheckOnNonNull(local, reference);
                        }

                        flowInfo.initsWhenFalse().setReachMode(2);
                     } else if (checkTypeWithoutHideNullWarning == 256) {
                        scope.problemReporter().localVariableNonNullComparedToNull(local, reference);
                        flowInfo.initsWhenTrue().setReachMode(2);
                     }

                     return;
                  }

                  if (flowInfo.canOnlyBeNull(local)) {
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
                  }
            }
         } else {
            switch (checkTypeWithoutHideNullWarning) {
               case 3:
                  if (flowInfo.isDefinitelyNull(local)) {
                     scope.problemReporter().localVariableNullReference(local, location);
                     return;
                  }

                  if (flowInfo.isPotentiallyNull(local)) {
                     scope.problemReporter().localVariablePotentialNullReference(local, location);
                     return;
                  }

                  if (flowInfo.isDefinitelyNonNull(local)) {
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
               case 257:
               case 513:
               case 769:
               case 1025:
                  reference = (Expression)location;
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
                  }
            }
         }

         this.recordNullReference(local, location, checkType, flowInfo);
      }

   }

   void removeFinalAssignmentIfAny(Reference reference) {
      for(int i = 0; i < this.assignCount; ++i) {
         if (this.finalAssignments[i] == reference) {
            this.finalAssignments[i] = null;
            this.finalVariables[i] = null;
            return;
         }
      }

   }

   protected void recordNullReference(LocalVariableBinding local, ASTNode expression, int checkType, FlowInfo nullInfo) {
      if (this.nullCount == 0) {
         this.nullLocals = new LocalVariableBinding[5];
         this.nullReferences = new ASTNode[5];
         this.nullCheckTypes = new int[5];
      } else if (this.nullCount == this.nullLocals.length) {
         int newLength = this.nullCount * 2;
         System.arraycopy(this.nullLocals, 0, this.nullLocals = new LocalVariableBinding[newLength], 0, this.nullCount);
         System.arraycopy(this.nullReferences, 0, this.nullReferences = new ASTNode[newLength], 0, this.nullCount);
         System.arraycopy(this.nullCheckTypes, 0, this.nullCheckTypes = new int[newLength], 0, this.nullCount);
      }

      this.nullLocals[this.nullCount] = local;
      this.nullReferences[this.nullCount] = expression;
      this.nullCheckTypes[this.nullCount++] = checkType;
   }

   public void recordUnboxing(Scope scope, Expression expression, int nullStatus, FlowInfo flowInfo) {
      if (nullStatus == 2) {
         super.recordUnboxing(scope, expression, nullStatus, flowInfo);
      } else {
         this.recordNullReference((LocalVariableBinding)null, expression, 16, flowInfo);
      }

   }

   protected boolean internalRecordNullityMismatch(Expression expression, TypeBinding providedType, FlowInfo flowInfo, int nullStatus, TypeBinding expectedType, int checkType) {
      if (nullStatus != 1 && ((this.tagBits & 1) == 0 || nullStatus == 2)) {
         return false;
      } else {
         this.recordProvidedExpectedTypes(providedType, expectedType, this.nullCount);
         this.recordNullReference(expression.localVariableBinding(), expression, checkType, flowInfo);
         return true;
      }
   }
}

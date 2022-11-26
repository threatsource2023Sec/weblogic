package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ArrayBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBindingVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.parser.RecoveryScanner;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class LocalDeclaration extends AbstractVariableDeclaration {
   public LocalVariableBinding binding;

   public LocalDeclaration(char[] name, int sourceStart, int sourceEnd) {
      this.name = name;
      this.sourceStart = sourceStart;
      this.sourceEnd = sourceEnd;
      this.declarationEnd = sourceEnd;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      if ((flowInfo.tagBits & 1) == 0) {
         this.bits |= 1073741824;
      }

      if (this.initialization == null) {
         return flowInfo;
      } else {
         this.initialization.checkNPEbyUnboxing(currentScope, flowContext, flowInfo);
         FlowInfo preInitInfo = null;
         boolean shouldAnalyseResource = this.binding != null && flowInfo.reachMode() == 0 && currentScope.compilerOptions().analyseResourceLeaks && FakedTrackingVariable.isAnyCloseable(this.initialization.resolvedType);
         if (shouldAnalyseResource) {
            preInitInfo = flowInfo.unconditionalCopy();
            FakedTrackingVariable.preConnectTrackerAcrossAssignment(this, this.binding, this.initialization, flowInfo);
         }

         FlowInfo flowInfo = this.initialization.analyseCode(currentScope, flowContext, flowInfo).unconditionalInits();
         if (shouldAnalyseResource) {
            FakedTrackingVariable.handleResourceAssignment(currentScope, preInitInfo, flowInfo, flowContext, this, this.initialization, this.binding);
         } else {
            FakedTrackingVariable.cleanUpAfterAssignment(currentScope, 2, this.initialization);
         }

         int nullStatus = this.initialization.nullStatus(flowInfo, flowContext);
         if (!flowInfo.isDefinitelyAssigned(this.binding)) {
            this.bits |= 8;
         } else {
            this.bits &= -9;
         }

         flowInfo.markAsDefinitelyAssigned(this.binding);
         if (currentScope.compilerOptions().isAnnotationBasedNullAnalysisEnabled) {
            nullStatus = NullAnnotationMatching.checkAssignment(currentScope, flowContext, this.binding, flowInfo, nullStatus, this.initialization, this.initialization.resolvedType);
         }

         if ((this.binding.type.tagBits & 2L) == 0L) {
            flowInfo.markNullStatus(this.binding, nullStatus);
         }

         return flowInfo;
      }
   }

   public void checkModifiers() {
      if ((this.modifiers & '\uffff' & -17) != 0) {
         this.modifiers = this.modifiers & -4194305 | 8388608;
      }

   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream) {
      if (this.binding.resolvedPosition != -1) {
         codeStream.addVisibleLocalVariable(this.binding);
      }

      if ((this.bits & Integer.MIN_VALUE) != 0) {
         int pc = codeStream.position;
         if (this.initialization != null) {
            if (this.binding.resolvedPosition < 0) {
               if (this.initialization.constant == Constant.NotAConstant) {
                  this.initialization.generateCode(currentScope, codeStream, false);
               }
            } else {
               this.initialization.generateCode(currentScope, codeStream, true);
               if (this.binding.type.isArrayType() && this.initialization instanceof CastExpression && ((CastExpression)this.initialization).innermostCastedExpression().resolvedType == TypeBinding.NULL) {
                  codeStream.checkcast(this.binding.type);
               }

               codeStream.store(this.binding, false);
               if ((this.bits & 8) != 0) {
                  this.binding.recordInitializationStartPC(codeStream.position);
               }
            }
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      }
   }

   public int getKind() {
      return 4;
   }

   public void getAllAnnotationContexts(int targetType, LocalVariableBinding localVariable, List allAnnotationContexts) {
      TypeReference.AnnotationCollector collector = new TypeReference.AnnotationCollector(this, targetType, localVariable, allAnnotationContexts);
      this.traverseWithoutInitializer(collector, (BlockScope)null);
   }

   public void getAllAnnotationContexts(int targetType, int parameterIndex, List allAnnotationContexts) {
      TypeReference.AnnotationCollector collector = new TypeReference.AnnotationCollector(this, targetType, parameterIndex, allAnnotationContexts);
      this.traverse(collector, (BlockScope)null);
   }

   public boolean isArgument() {
      return false;
   }

   public boolean isReceiver() {
      return false;
   }

   public TypeBinding patchType(TypeBinding newType) {
      TypeBinding[] mentionedTypeVariables = this.findCapturedTypeVariables(newType);
      if (mentionedTypeVariables != null && mentionedTypeVariables.length > 0) {
         newType = newType.upwardsProjection(this.binding.declaringScope, mentionedTypeVariables);
      }

      this.type.resolvedType = newType;
      if (this.binding != null) {
         this.binding.type = newType;
         this.binding.markInitialized();
      }

      return this.type.resolvedType;
   }

   private TypeVariableBinding[] findCapturedTypeVariables(TypeBinding typeBinding) {
      final Set mentioned = new HashSet();
      TypeBindingVisitor.visit(new TypeBindingVisitor() {
         public boolean visit(TypeVariableBinding typeVariable) {
            if (typeVariable.isCapture()) {
               mentioned.add(typeVariable);
            }

            return super.visit(typeVariable);
         }
      }, typeBinding);
      return mentioned.isEmpty() ? null : (TypeVariableBinding[])mentioned.toArray(new TypeVariableBinding[mentioned.size()]);
   }

   private static Expression findPolyExpression(Expression e) {
      if (e instanceof FunctionalExpression) {
         return e;
      } else {
         Expression re;
         if (e instanceof ConditionalExpression) {
            ConditionalExpression ce = (ConditionalExpression)e;
            re = findPolyExpression(ce.valueIfTrue);
            if (re == null) {
               re = findPolyExpression(ce.valueIfFalse);
            }

            if (re != null) {
               return re;
            }
         }

         if (e instanceof SwitchExpression) {
            SwitchExpression se = (SwitchExpression)e;
            Iterator var3 = se.resultExpressions.iterator();

            while(var3.hasNext()) {
               re = (Expression)var3.next();
               Expression candidate = findPolyExpression(re);
               if (candidate != null) {
                  return candidate;
               }
            }
         }

         return null;
      }
   }

   public void resolve(final BlockScope scope) {
      handleNonNullByDefault(scope, this.annotations, this);
      TypeBinding variableType = null;
      boolean variableTypeInferenceError = false;
      boolean isTypeNameVar = this.isTypeNameVar(scope);
      if (isTypeNameVar) {
         if ((this.bits & 16) == 0) {
            if (this.initialization != null) {
               variableType = this.checkInferredLocalVariableInitializer(scope);
               variableTypeInferenceError = variableType != null;
            } else {
               scope.problemReporter().varLocalWithoutInitizalier(this);
               variableType = scope.getJavaLangObject();
               variableTypeInferenceError = true;
            }
         }
      } else {
         variableType = this.type.resolveType(scope, true);
      }

      this.bits |= this.type.bits & 1048576;
      this.checkModifiers();
      if (variableType != null) {
         if (variableType == TypeBinding.VOID) {
            scope.problemReporter().variableTypeCannotBeVoid(this);
            return;
         }

         if (((TypeBinding)variableType).isArrayType() && ((ArrayBinding)variableType).leafComponentType == TypeBinding.VOID) {
            scope.problemReporter().variableTypeCannotBeVoidArray(this);
            return;
         }
      }

      Binding existingVariable = scope.getBinding(this.name, 3, this, false);
      boolean resolveAnnotationsEarly;
      if (existingVariable != null && existingVariable.isValidBinding()) {
         resolveAnnotationsEarly = existingVariable instanceof LocalVariableBinding;
         if (resolveAnnotationsEarly && (this.bits & 2097152) != 0 && scope.isLambdaSubscope() && this.hiddenVariableDepth == 0) {
            scope.problemReporter().lambdaRedeclaresLocal(this);
         } else if (resolveAnnotationsEarly && this.hiddenVariableDepth == 0) {
            scope.problemReporter().redefineLocal(this);
         } else {
            scope.problemReporter().localVariableHiding(this, existingVariable, false);
         }
      }

      if ((this.modifiers & 16) != 0 && this.initialization == null) {
         this.modifiers |= 67108864;
      }

      if (isTypeNameVar) {
         this.binding = new LocalVariableBinding(this, (TypeBinding)(variableType != null ? variableType : scope.getJavaLangObject()), this.modifiers, false) {
            private boolean isInitialized = false;

            public void markReferenced() {
               if (!this.isInitialized) {
                  scope.problemReporter().varLocalReferencesItself(LocalDeclaration.this);
                  this.type = null;
                  this.isInitialized = true;
               }

            }

            public void markInitialized() {
               this.isInitialized = true;
            }
         };
      } else {
         this.binding = new LocalVariableBinding(this, (TypeBinding)variableType, this.modifiers, false);
      }

      scope.addLocalVariable(this.binding);
      this.binding.setConstant(Constant.NotAConstant);
      if (variableType == null && this.initialization != null) {
         this.initialization.resolveType(scope);
         if (isTypeNameVar && this.initialization.resolvedType != null) {
            if (TypeBinding.equalsEquals(TypeBinding.NULL, this.initialization.resolvedType)) {
               scope.problemReporter().varLocalInitializedToNull(this);
               variableTypeInferenceError = true;
            } else if (TypeBinding.equalsEquals(TypeBinding.VOID, this.initialization.resolvedType)) {
               scope.problemReporter().varLocalInitializedToVoid(this);
               variableTypeInferenceError = true;
            }

            variableType = this.patchType(this.initialization.resolvedType);
         } else {
            variableTypeInferenceError = true;
         }
      }

      this.binding.markInitialized();
      if (!variableTypeInferenceError) {
         resolveAnnotationsEarly = false;
         if (scope.environment().usesNullTypeAnnotations() && !isTypeNameVar && variableType != null && ((TypeBinding)variableType).isValidBinding()) {
            resolveAnnotationsEarly = this.initialization instanceof Invocation || this.initialization instanceof ConditionalExpression || this.initialization instanceof SwitchExpression || this.initialization instanceof ArrayInitializer;
         }

         if (resolveAnnotationsEarly) {
            resolveAnnotations(scope, this.annotations, this.binding, true);
            variableType = this.type.resolvedType;
         }

         if (this.initialization != null) {
            TypeBinding initializationType;
            if (this.initialization instanceof ArrayInitializer) {
               initializationType = this.initialization.resolveTypeExpecting(scope, (TypeBinding)variableType);
               if (initializationType != null) {
                  ((ArrayInitializer)this.initialization).binding = (ArrayBinding)initializationType;
                  this.initialization.computeConversion(scope, (TypeBinding)variableType, initializationType);
               }
            } else {
               this.initialization.setExpressionContext(isTypeNameVar ? ExpressionContext.VANILLA_CONTEXT : ExpressionContext.ASSIGNMENT_CONTEXT);
               this.initialization.setExpectedType((TypeBinding)variableType);
               initializationType = this.initialization.resolvedType != null ? this.initialization.resolvedType : this.initialization.resolveType(scope);
               if (initializationType != null) {
                  if (TypeBinding.notEquals((TypeBinding)variableType, initializationType)) {
                     scope.compilationUnitScope().recordTypeConversion((TypeBinding)variableType, initializationType);
                  }

                  if (!this.initialization.isConstantValueOfTypeAssignableToType(initializationType, (TypeBinding)variableType) && !initializationType.isCompatibleWith((TypeBinding)variableType, scope)) {
                     if (this.isBoxingCompatible(initializationType, (TypeBinding)variableType, this.initialization, scope)) {
                        this.initialization.computeConversion(scope, (TypeBinding)variableType, initializationType);
                        if (this.initialization instanceof CastExpression && (this.initialization.bits & 16384) == 0) {
                           CastExpression.checkNeedForAssignedCast(scope, (TypeBinding)variableType, (CastExpression)this.initialization);
                        }
                     } else if ((((TypeBinding)variableType).tagBits & 128L) == 0L) {
                        scope.problemReporter().typeMismatchError(initializationType, (TypeBinding)variableType, (ASTNode)this.initialization, (ASTNode)null);
                     }
                  } else {
                     this.initialization.computeConversion(scope, (TypeBinding)variableType, initializationType);
                     if (initializationType.needsUncheckedConversion((TypeBinding)variableType)) {
                        scope.problemReporter().unsafeTypeConversion(this.initialization, initializationType, (TypeBinding)variableType);
                     }

                     if (this.initialization instanceof CastExpression && (this.initialization.bits & 16384) == 0) {
                        CastExpression.checkNeedForAssignedCast(scope, (TypeBinding)variableType, (CastExpression)this.initialization);
                     }
                  }
               }
            }

            if (this.binding == Expression.getDirectBinding(this.initialization)) {
               scope.problemReporter().assignmentHasNoEffect((AbstractVariableDeclaration)this, this.name);
            }

            this.binding.setConstant(this.binding.isFinal() ? this.initialization.constant.castTo((((TypeBinding)variableType).id << 4) + this.initialization.constant.typeID()) : Constant.NotAConstant);
         }

         if (!resolveAnnotationsEarly) {
            resolveAnnotations(scope, this.annotations, this.binding, true);
         }

         Annotation.isTypeUseCompatible(this.type, scope, this.annotations);
         this.validateNullAnnotations(scope);
      }
   }

   void validateNullAnnotations(BlockScope scope) {
      if (!scope.validateNullAnnotation(this.binding.tagBits, this.type, this.annotations)) {
         LocalVariableBinding var10000 = this.binding;
         var10000.tagBits &= -108086391056891905L;
      }

   }

   private TypeBinding checkInferredLocalVariableInitializer(BlockScope scope) {
      TypeBinding errorType = null;
      if (this.initialization instanceof ArrayInitializer) {
         scope.problemReporter().varLocalCannotBeArrayInitalizers(this);
         errorType = scope.createArrayType(scope.getJavaLangObject(), 1);
      } else {
         Expression polyExpression = findPolyExpression(this.initialization);
         if (polyExpression instanceof ReferenceExpression) {
            scope.problemReporter().varLocalCannotBeMethodReference(this);
            errorType = TypeBinding.NULL;
         } else if (polyExpression != null) {
            scope.problemReporter().varLocalCannotBeLambda(this);
            errorType = TypeBinding.NULL;
         }
      }

      if (this.type.dimensions() > 0 || this.type.extraDimensions() > 0) {
         scope.problemReporter().varLocalCannotBeArray(this);
         errorType = scope.createArrayType(scope.getJavaLangObject(), 1);
      }

      if ((this.bits & 4194304) != 0) {
         scope.problemReporter().varLocalMultipleDeclarators(this);
         errorType = this.initialization.resolveType(scope);
      }

      return (TypeBinding)errorType;
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         if (this.annotations != null) {
            int annotationsLength = this.annotations.length;

            for(int i = 0; i < annotationsLength; ++i) {
               this.annotations[i].traverse(visitor, scope);
            }
         }

         this.type.traverse(visitor, scope);
         if (this.initialization != null) {
            this.initialization.traverse(visitor, scope);
         }
      }

      visitor.endVisit(this, scope);
   }

   private void traverseWithoutInitializer(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         if (this.annotations != null) {
            int annotationsLength = this.annotations.length;

            for(int i = 0; i < annotationsLength; ++i) {
               this.annotations[i].traverse(visitor, scope);
            }
         }

         this.type.traverse(visitor, scope);
      }

      visitor.endVisit(this, scope);
   }

   public boolean isRecoveredFromLoneIdentifier() {
      return this.name == RecoveryScanner.FAKE_IDENTIFIER && (this.type instanceof SingleTypeReference || this.type instanceof QualifiedTypeReference && !(this.type instanceof ArrayQualifiedTypeReference)) && this.initialization == null && !this.type.isBaseTypeReference();
   }

   public boolean isTypeNameVar(Scope scope) {
      return this.type != null && this.type.isTypeNameVar(scope);
   }
}

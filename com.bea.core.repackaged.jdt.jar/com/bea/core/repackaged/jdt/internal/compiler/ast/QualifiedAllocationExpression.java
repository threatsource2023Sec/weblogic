package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ImplicitNullAnnotationVerifier;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.IntersectionTypeBinding18;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedGenericMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PolyTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.RawTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBindingVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import java.util.Arrays;

public class QualifiedAllocationExpression extends AllocationExpression {
   public Expression enclosingInstance;
   public TypeDeclaration anonymousType;

   public QualifiedAllocationExpression() {
   }

   public QualifiedAllocationExpression(TypeDeclaration anonymousType) {
      this.anonymousType = anonymousType;
      anonymousType.allocation = this;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      if (this.enclosingInstance != null) {
         flowInfo = this.enclosingInstance.analyseCode(currentScope, flowContext, flowInfo);
      } else if (this.binding != null && this.binding.declaringClass != null) {
         ReferenceBinding superclass = this.binding.declaringClass.superclass();
         if (superclass != null && superclass.isMemberType() && !superclass.isStatic()) {
            currentScope.tagAsAccessingEnclosingInstanceStateOf(superclass.enclosingType(), false);
         }
      }

      this.checkCapturedLocalInitializationIfNecessary((ReferenceBinding)(this.anonymousType == null ? this.binding.declaringClass.erasure() : this.binding.declaringClass.superclass().erasure()), currentScope, flowInfo);
      if (this.arguments != null) {
         boolean analyseResources = currentScope.compilerOptions().analyseResourceLeaks;
         boolean hasResourceWrapperType = analyseResources && this.resolvedType instanceof ReferenceBinding && ((ReferenceBinding)this.resolvedType).hasTypeBit(4);
         int i = 0;

         for(int count = this.arguments.length; i < count; ++i) {
            flowInfo = this.arguments[i].analyseCode(currentScope, flowContext, flowInfo);
            if (analyseResources && !hasResourceWrapperType) {
               flowInfo = FakedTrackingVariable.markPassedToOutside(currentScope, this.arguments[i], flowInfo, flowContext, false);
            }

            this.arguments[i].checkNPEbyUnboxing(currentScope, flowContext, flowInfo);
         }

         this.analyseArguments(currentScope, flowContext, flowInfo, this.binding, this.arguments);
      }

      if (this.anonymousType != null) {
         flowInfo = this.anonymousType.analyseCode(currentScope, flowContext, flowInfo);
      }

      ReferenceBinding[] thrownExceptions;
      if ((thrownExceptions = this.binding.thrownExceptions).length != 0) {
         if ((this.bits & 65536) != 0 && this.genericTypeArguments == null) {
            thrownExceptions = currentScope.environment().convertToRawTypes(this.binding.thrownExceptions, true, true);
         }

         flowContext.checkExceptionHandlers((TypeBinding[])thrownExceptions, this, flowInfo.unconditionalCopy(), currentScope);
      }

      if (currentScope.compilerOptions().analyseResourceLeaks && FakedTrackingVariable.isAnyCloseable(this.resolvedType)) {
         FakedTrackingVariable.analyseCloseableAllocation(currentScope, flowInfo, this);
      }

      this.manageEnclosingInstanceAccessIfNecessary(currentScope, flowInfo);
      this.manageSyntheticAccessIfNecessary(currentScope, flowInfo);
      flowContext.recordAbruptExit();
      return flowInfo;
   }

   public Expression enclosingInstance() {
      return this.enclosingInstance;
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      this.cleanUpInferenceContexts();
      if (!valueRequired) {
         currentScope.problemReporter().unusedObjectAllocation(this);
      }

      int pc = codeStream.position;
      MethodBinding codegenBinding = this.binding.original();
      ReferenceBinding allocatedType = codegenBinding.declaringClass;
      codeStream.new_(this.type, allocatedType);
      boolean isUnboxing = (this.implicitConversion & 1024) != 0;
      if (valueRequired || isUnboxing) {
         codeStream.dup();
      }

      if (this.type != null) {
         codeStream.recordPositionsFrom(pc, this.type.sourceStart);
      } else {
         codeStream.ldc(String.valueOf(this.enumConstant.name));
         codeStream.generateInlinedValue(this.enumConstant.binding.id);
      }

      if (allocatedType.isNestedType()) {
         codeStream.generateSyntheticEnclosingInstanceValues(currentScope, allocatedType, this.enclosingInstance(), this);
      }

      this.generateArguments(this.binding, this.arguments, currentScope, codeStream);
      if (allocatedType.isNestedType()) {
         codeStream.generateSyntheticOuterArgumentValues(currentScope, allocatedType, this);
      }

      if (this.syntheticAccessor == null) {
         codeStream.invoke((byte)-73, codegenBinding, (TypeBinding)null, this.typeArguments);
      } else {
         int i = 0;

         for(int max = this.syntheticAccessor.parameters.length - codegenBinding.parameters.length; i < max; ++i) {
            codeStream.aconst_null();
         }

         codeStream.invoke((byte)-73, this.syntheticAccessor, (TypeBinding)null, this.typeArguments);
      }

      if (valueRequired) {
         codeStream.generateImplicitConversion(this.implicitConversion);
      } else if (isUnboxing) {
         codeStream.generateImplicitConversion(this.implicitConversion);
         switch (this.postConversionType(currentScope).id) {
            case 7:
            case 8:
               codeStream.pop2();
               break;
            default:
               codeStream.pop();
         }
      }

      codeStream.recordPositionsFrom(pc, this.sourceStart);
      if (this.anonymousType != null) {
         this.anonymousType.generateCode(currentScope, codeStream);
      }

   }

   public boolean isSuperAccess() {
      return this.anonymousType != null;
   }

   public void manageEnclosingInstanceAccessIfNecessary(BlockScope currentScope, FlowInfo flowInfo) {
      if ((flowInfo.tagBits & 1) == 0) {
         ReferenceBinding allocatedTypeErasure = (ReferenceBinding)this.binding.declaringClass.erasure();
         if (allocatedTypeErasure.isNestedType() && (currentScope.enclosingSourceType().isLocalType() || currentScope.isLambdaSubscope())) {
            if (allocatedTypeErasure.isLocalType()) {
               ((LocalTypeBinding)allocatedTypeErasure).addInnerEmulationDependent(currentScope, this.enclosingInstance != null);
            } else {
               currentScope.propagateInnerEmulation(allocatedTypeErasure, this.enclosingInstance != null);
            }
         }
      }

   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      if (this.enclosingInstance != null) {
         this.enclosingInstance.printExpression(0, output).append('.');
      }

      super.printExpression(0, output);
      if (this.anonymousType != null) {
         this.anonymousType.print(indent, output);
      }

      return output;
   }

   public TypeBinding resolveType(BlockScope scope) {
      if (this.anonymousType == null && this.enclosingInstance == null) {
         return super.resolveType(scope);
      } else {
         TypeBinding result = this.resolveTypeForQualifiedAllocationExpression(scope);
         if (result != null && !result.isPolyType() && this.binding != null) {
            CompilerOptions compilerOptions = scope.compilerOptions();
            if (compilerOptions.isAnnotationBasedNullAnalysisEnabled) {
               ImplicitNullAnnotationVerifier.ensureNullnessIsKnown(this.binding, scope);
               if (compilerOptions.sourceLevel >= 3407872L && this.binding instanceof ParameterizedGenericMethodBinding && this.typeArguments != null) {
                  TypeVariableBinding[] typeVariables = this.binding.original().typeVariables();

                  for(int i = 0; i < this.typeArguments.length; ++i) {
                     this.typeArguments[i].checkNullConstraints(scope, (ParameterizedGenericMethodBinding)this.binding, typeVariables, i);
                  }
               }
            }

            if (compilerOptions.sourceLevel >= 3407872L && this.binding.getTypeAnnotations() != Binding.NO_ANNOTATIONS) {
               this.resolvedType = scope.environment().createAnnotatedType(this.resolvedType, this.binding.getTypeAnnotations());
            }
         }

         return result;
      }
   }

   private TypeBinding resolveTypeForQualifiedAllocationExpression(BlockScope scope) {
      boolean isDiamond = this.type != null && (this.type.bits & 524288) != 0;
      TypeBinding enclosingInstanceType = null;
      TypeBinding receiverType = null;
      long sourceLevel = scope.compilerOptions().sourceLevel;
      ReferenceBinding enclosingInstanceReference;
      ReferenceBinding targetEnclosing;
      if (this.constant != Constant.NotAConstant) {
         this.constant = Constant.NotAConstant;
         enclosingInstanceReference = null;
         boolean hasError = false;
         boolean enclosingInstanceContainsCast = false;
         int max;
         if (this.enclosingInstance != null) {
            if (this.enclosingInstance instanceof CastExpression) {
               Expression var10000 = this.enclosingInstance;
               var10000.bits |= 32;
               enclosingInstanceContainsCast = true;
            }

            if ((enclosingInstanceType = this.enclosingInstance.resolveType(scope)) == null) {
               hasError = true;
            } else if (!((TypeBinding)enclosingInstanceType).isBaseType() && !((TypeBinding)enclosingInstanceType).isArrayType()) {
               if (this.type instanceof QualifiedTypeReference) {
                  scope.problemReporter().illegalUsageOfQualifiedTypeReference((QualifiedTypeReference)this.type);
                  hasError = true;
               } else if (!(enclosingInstanceReference = (ReferenceBinding)enclosingInstanceType).canBeSeenBy((Scope)scope)) {
                  enclosingInstanceType = new ProblemReferenceBinding(enclosingInstanceReference.compoundName, enclosingInstanceReference, 2);
                  scope.problemReporter().invalidType(this.enclosingInstance, (TypeBinding)enclosingInstanceType);
                  hasError = true;
               } else {
                  this.resolvedType = (TypeBinding)(receiverType = ((SingleTypeReference)this.type).resolveTypeEnclosing(scope, (ReferenceBinding)enclosingInstanceType));
                  this.checkIllegalNullAnnotation(scope, (TypeBinding)receiverType);
                  if (receiverType != null && enclosingInstanceContainsCast) {
                     CastExpression.checkNeedForEnclosingInstanceCast(scope, this.enclosingInstance, (TypeBinding)enclosingInstanceType, (TypeBinding)receiverType);
                  }
               }
            } else {
               scope.problemReporter().illegalPrimitiveOrArrayTypeForEnclosingInstance((TypeBinding)enclosingInstanceType, this.enclosingInstance);
               hasError = true;
            }
         } else if (this.type == null) {
            receiverType = scope.enclosingSourceType();
         } else {
            receiverType = this.type.resolveType(scope, true);
            this.checkIllegalNullAnnotation(scope, (TypeBinding)receiverType);
            if (receiverType != null && ((TypeBinding)receiverType).isValidBinding() && this.type instanceof ParameterizedQualifiedTypeReference) {
               targetEnclosing = (ReferenceBinding)receiverType;

               label342:
               while((targetEnclosing.modifiers & 8) == 0 && !targetEnclosing.isRawType()) {
                  if ((targetEnclosing = targetEnclosing.enclosingType()) == null) {
                     ParameterizedQualifiedTypeReference qRef = (ParameterizedQualifiedTypeReference)this.type;
                     max = qRef.typeArguments.length - 2;

                     while(true) {
                        if (max < 0) {
                           break label342;
                        }

                        if (qRef.typeArguments[max] != null) {
                           scope.problemReporter().illegalQualifiedParameterizedTypeAllocation(this.type, (TypeBinding)receiverType);
                           break label342;
                        }

                        --max;
                     }
                  }
               }
            }
         }

         if (receiverType == null || !((TypeBinding)receiverType).isValidBinding()) {
            hasError = true;
         }

         int length;
         int i;
         if (this.typeArguments != null) {
            length = this.typeArguments.length;
            this.argumentsHaveErrors = sourceLevel < 3211264L;
            this.genericTypeArguments = new TypeBinding[length];

            for(i = 0; i < length; ++i) {
               TypeReference typeReference = this.typeArguments[i];
               if ((this.genericTypeArguments[i] = typeReference.resolveType(scope, true)) == null) {
                  this.argumentsHaveErrors = true;
               }

               if (this.argumentsHaveErrors && typeReference instanceof Wildcard) {
                  scope.problemReporter().illegalUsageOfWildcard(typeReference);
               }
            }

            if (isDiamond) {
               scope.problemReporter().diamondNotWithExplicitTypeArguments(this.typeArguments);
               return null;
            }

            if (this.argumentsHaveErrors) {
               if (this.arguments != null) {
                  i = 0;

                  for(max = this.arguments.length; i < max; ++i) {
                     this.arguments[i].resolveType(scope);
                  }
               }

               return null;
            }
         }

         this.argumentTypes = Binding.NO_PARAMETERS;
         if (this.arguments != null) {
            length = this.arguments.length;
            this.argumentTypes = new TypeBinding[length];

            for(i = 0; i < length; ++i) {
               Expression argument = this.arguments[i];
               if (argument instanceof CastExpression) {
                  argument.bits |= 32;
                  this.argsContainCast = true;
               }

               argument.setExpressionContext(ExpressionContext.INVOCATION_CONTEXT);
               if ((this.argumentTypes[i] = argument.resolveType(scope)) == null) {
                  hasError = true;
                  this.argumentsHaveErrors = true;
               }
            }
         }

         if (hasError) {
            if (isDiamond) {
               return null;
            }

            if (receiverType instanceof ReferenceBinding) {
               targetEnclosing = (ReferenceBinding)receiverType;
               if (((TypeBinding)receiverType).isValidBinding()) {
                  i = this.arguments == null ? 0 : this.arguments.length;
                  TypeBinding[] pseudoArgs = new TypeBinding[i];
                  int i = i;

                  while(true) {
                     --i;
                     if (i < 0) {
                        this.binding = scope.findMethod(targetEnclosing, TypeConstants.INIT, pseudoArgs, this, false);
                        if (this.binding != null && !this.binding.isValidBinding()) {
                           MethodBinding closestMatch = ((ProblemMethodBinding)this.binding).closestMatch;
                           if (closestMatch != null) {
                              if (((MethodBinding)closestMatch).original().typeVariables != Binding.NO_TYPE_VARIABLES) {
                                 closestMatch = scope.environment().createParameterizedGenericMethod(((MethodBinding)closestMatch).original(), (RawTypeBinding)null);
                              }

                              this.binding = (MethodBinding)closestMatch;
                              MethodBinding closestMatchOriginal = ((MethodBinding)closestMatch).original();
                              if (closestMatchOriginal.isOrEnclosedByPrivateType() && !scope.isDefinedInMethod(closestMatchOriginal)) {
                                 closestMatchOriginal.modifiers |= 134217728;
                              }
                           }
                        }
                        break;
                     }

                     pseudoArgs[i] = (TypeBinding)(this.argumentTypes[i] == null ? TypeBinding.NULL : this.argumentTypes[i]);
                  }
               }

               if (this.anonymousType != null) {
                  scope.addAnonymousType(this.anonymousType, targetEnclosing);
                  this.anonymousType.resolve(scope);
                  return this.resolvedType = this.anonymousType.binding;
               }
            }

            return this.resolvedType = (TypeBinding)receiverType;
         }

         if (this.anonymousType == null) {
            if (!((TypeBinding)receiverType).canBeInstantiated()) {
               scope.problemReporter().cannotInstantiate(this.type, (TypeBinding)receiverType);
               return this.resolvedType = (TypeBinding)receiverType;
            }
         } else {
            if (isDiamond && sourceLevel < 3473408L) {
               scope.problemReporter().diamondNotWithAnoymousClasses(this.type);
               return null;
            }

            targetEnclosing = (ReferenceBinding)receiverType;
            if (targetEnclosing.isTypeVariable()) {
               ReferenceBinding superType = new ProblemReferenceBinding(new char[][]{targetEnclosing.sourceName()}, targetEnclosing, 9);
               scope.problemReporter().invalidType(this, superType);
               return null;
            }

            if (this.type != null && targetEnclosing.isEnum()) {
               scope.problemReporter().cannotInstantiate(this.type, targetEnclosing);
               return this.resolvedType = targetEnclosing;
            }

            this.resolvedType = (TypeBinding)receiverType;
         }
      } else if (this.enclosingInstance != null) {
         enclosingInstanceType = this.enclosingInstance.resolvedType;
         this.resolvedType = (TypeBinding)(receiverType = this.type.resolvedType);
      }

      enclosingInstanceReference = null;
      MethodBinding constructorBinding;
      if (isDiamond) {
         this.binding = constructorBinding = this.inferConstructorOfElidedParameterizedType(scope);
         if (this.binding == null || !this.binding.isValidBinding()) {
            scope.problemReporter().cannotInferElidedTypes(this);
            return this.resolvedType = null;
         }

         if (this.typeExpected == null && sourceLevel >= 3407872L && this.expressionContext.definesTargetType()) {
            return new PolyTypeBinding(this);
         }

         this.resolvedType = this.type.resolvedType = (TypeBinding)(receiverType = this.binding.declaringClass);
         if (this.anonymousType != null) {
            constructorBinding = this.getAnonymousConstructorBinding((ReferenceBinding)receiverType, scope);
            if (constructorBinding == null) {
               return null;
            }

            this.resolvedType = this.anonymousType.binding;
            if (!this.validate((ParameterizedTypeBinding)receiverType, scope)) {
               return this.resolvedType;
            }
         } else if (this.binding.isVarargs()) {
            TypeBinding lastArg = this.binding.parameters[this.binding.parameters.length - 1].leafComponentType();
            if (!lastArg.erasure().canBeSeenBy(scope)) {
               scope.problemReporter().invalidType(this, new ProblemReferenceBinding(new char[][]{lastArg.readableName()}, (ReferenceBinding)lastArg, 2));
               return this.resolvedType = null;
            }
         }

         this.binding = resolvePolyExpressionArguments(this, this.binding, this.argumentTypes, scope);
      } else if (this.anonymousType != null) {
         constructorBinding = this.getAnonymousConstructorBinding((ReferenceBinding)receiverType, scope);
         if (constructorBinding == null) {
            return null;
         }

         this.resolvedType = this.anonymousType.binding;
      } else {
         this.binding = constructorBinding = this.findConstructorBinding(scope, this, (ReferenceBinding)receiverType, this.argumentTypes);
      }

      ReferenceBinding receiver = (ReferenceBinding)receiverType;
      ReferenceBinding superType = receiver.isInterface() ? scope.getJavaLangObject() : receiver;
      if (constructorBinding.isValidBinding()) {
         if (this.isMethodUseDeprecated(constructorBinding, scope, true, this)) {
            scope.problemReporter().deprecatedMethod(constructorBinding, this);
         }

         if (checkInvocationArguments(scope, (Expression)null, superType, constructorBinding, this.arguments, this.argumentTypes, this.argsContainCast, this)) {
            this.bits |= 65536;
         }

         if (this.typeArguments != null && constructorBinding.original().typeVariables == Binding.NO_TYPE_VARIABLES) {
            scope.problemReporter().unnecessaryTypeArgumentsForMethodInvocation(constructorBinding, this.genericTypeArguments, this.typeArguments);
         }

         if ((constructorBinding.tagBits & 128L) != 0L) {
            scope.problemReporter().missingTypeInConstructor(this, constructorBinding);
         }

         if (this.enclosingInstance != null) {
            targetEnclosing = constructorBinding.declaringClass.enclosingType();
            if (targetEnclosing == null) {
               scope.problemReporter().unnecessaryEnclosingInstanceSpecification(this.enclosingInstance, receiver);
               return this.resolvedType;
            }

            if (!((TypeBinding)enclosingInstanceType).isCompatibleWith(targetEnclosing) && !scope.isBoxingCompatibleWith((TypeBinding)enclosingInstanceType, targetEnclosing)) {
               scope.problemReporter().typeMismatchError((TypeBinding)enclosingInstanceType, (TypeBinding)targetEnclosing, (ASTNode)this.enclosingInstance, (ASTNode)null);
               return this.resolvedType;
            }

            this.enclosingInstance.computeConversion(scope, targetEnclosing, (TypeBinding)enclosingInstanceType);
         }

         if (!isDiamond && ((TypeBinding)receiverType).isParameterizedTypeWithActualArguments() && (this.anonymousType == null || sourceLevel >= 3473408L)) {
            this.checkTypeArgumentRedundancy((ParameterizedTypeBinding)receiverType, scope);
         }

         if (this.anonymousType != null) {
            LookupEnvironment environment = scope.environment();
            if (environment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
               ImplicitNullAnnotationVerifier.ensureNullnessIsKnown(constructorBinding, scope);
            }

            this.binding = this.anonymousType.createDefaultConstructorWithBinding(constructorBinding, (this.bits & 65536) != 0 && this.genericTypeArguments == null);
            return this.resolvedType;
         } else {
            return this.resolvedType = (TypeBinding)receiverType;
         }
      } else {
         if (constructorBinding.declaringClass == null) {
            constructorBinding.declaringClass = superType;
         }

         if (this.type != null && !this.type.resolvedType.isValidBinding()) {
            return null;
         } else {
            scope.problemReporter().invalidConstructor(this, constructorBinding);
            return this.resolvedType;
         }
      }
   }

   private boolean validate(final ParameterizedTypeBinding allocationType, final Scope scope) {
      class ValidityInspector extends TypeBindingVisitor {
         private boolean noErrors = true;

         public ValidityInspector() {
         }

         public boolean visit(IntersectionTypeBinding18 intersectionTypeBinding18) {
            Arrays.sort(intersectionTypeBinding18.intersectingTypes, (t1, t2) -> {
               return t1.id - t2.id;
            });
            scope.problemReporter().anonymousDiamondWithNonDenotableTypeArguments(QualifiedAllocationExpression.this.type, allocationType);
            return this.noErrors = false;
         }

         public boolean visit(TypeVariableBinding typeVariable) {
            if (typeVariable.isCapture()) {
               scope.problemReporter().anonymousDiamondWithNonDenotableTypeArguments(QualifiedAllocationExpression.this.type, allocationType);
               return this.noErrors = false;
            } else {
               return true;
            }
         }

         public boolean visit(ReferenceBinding ref) {
            if (!ref.canBeSeenBy(scope)) {
               scope.problemReporter().invalidType(QualifiedAllocationExpression.this.anonymousType, new ProblemReferenceBinding(ref.compoundName, ref, 2));
               return this.noErrors = false;
            } else {
               return true;
            }
         }

         public boolean isValid() {
            TypeBindingVisitor.visit(this, (TypeBinding)allocationType);
            return this.noErrors;
         }
      }

      return (new ValidityInspector()).isValid();
   }

   private MethodBinding getAnonymousConstructorBinding(ReferenceBinding receiverType, BlockScope scope) {
      ReferenceBinding anonymousSuperclass = receiverType.isInterface() ? scope.getJavaLangObject() : receiverType;
      scope.addAnonymousType(this.anonymousType, receiverType);
      this.anonymousType.resolve(scope);
      this.resolvedType = this.anonymousType.binding;
      return (this.resolvedType.tagBits & 131072L) != 0L ? null : this.findConstructorBinding(scope, this, anonymousSuperclass, this.argumentTypes);
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         if (this.enclosingInstance != null) {
            this.enclosingInstance.traverse(visitor, scope);
         }

         int argumentsLength;
         int i;
         if (this.typeArguments != null) {
            argumentsLength = 0;

            for(i = this.typeArguments.length; argumentsLength < i; ++argumentsLength) {
               this.typeArguments[argumentsLength].traverse(visitor, scope);
            }
         }

         if (this.type != null) {
            this.type.traverse(visitor, scope);
         }

         if (this.arguments != null) {
            argumentsLength = this.arguments.length;

            for(i = 0; i < argumentsLength; ++i) {
               this.arguments[i].traverse(visitor, scope);
            }
         }

         if (this.anonymousType != null) {
            this.anonymousType.traverse(visitor, scope);
         }
      }

      visitor.endVisit(this, scope);
   }
}

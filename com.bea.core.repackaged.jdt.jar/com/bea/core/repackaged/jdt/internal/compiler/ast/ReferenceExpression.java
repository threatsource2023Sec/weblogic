package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.IErrorHandlingPolicy;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.ConstantPool;
import com.bea.core.repackaged.jdt.internal.compiler.env.ICompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FieldInitsFakingFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.IrritantSet;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ArrayBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ImplicitNullAnnotationVerifier;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.InferenceContext18;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.InvocationSite;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedGenericMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PolyTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SyntheticArgumentBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.parser.Parser;
import com.bea.core.repackaged.jdt.internal.compiler.parser.Scanner;
import java.util.HashMap;
import java.util.Iterator;

public class ReferenceExpression extends FunctionalExpression implements IPolyExpression, InvocationSite {
   private static final String SecretReceiverVariableName = " rec_";
   private static final char[] ImplicitArgName = " arg".toCharArray();
   public LocalVariableBinding receiverVariable;
   public Expression lhs;
   public TypeReference[] typeArguments;
   public char[] selector;
   public int nameSourceStart;
   public TypeBinding receiverType;
   public boolean haveReceiver;
   public TypeBinding[] resolvedTypeArguments;
   private boolean typeArgumentsHaveErrors;
   MethodBinding syntheticAccessor;
   private int depth;
   private MethodBinding exactMethodBinding;
   private boolean receiverPrecedesParameters = false;
   private TypeBinding[] freeParameters;
   private boolean checkingPotentialCompatibility;
   private MethodBinding[] potentialMethods;
   protected ReferenceExpression original;
   private HashMap copiesPerTargetType;
   public char[] text;
   private HashMap inferenceContexts;
   private Scanner scanner;

   public ReferenceExpression(Scanner scanner) {
      this.potentialMethods = Binding.NO_METHODS;
      this.original = this;
      this.scanner = scanner;
   }

   public void initialize(CompilationResult result, Expression expression, TypeReference[] optionalTypeArguments, char[] identifierOrNew, int sourceEndPosition) {
      super.setCompilationResult(result);
      this.lhs = expression;
      this.typeArguments = optionalTypeArguments;
      this.selector = identifierOrNew;
      this.sourceStart = expression.sourceStart;
      this.sourceEnd = sourceEndPosition;
   }

   private ReferenceExpression copy() {
      Parser parser = new Parser(this.enclosingScope.problemReporter(), false);
      ICompilationUnit compilationUnit = this.compilationResult.getCompilationUnit();
      char[] source = compilationUnit != null ? compilationUnit.getContents() : this.text;
      parser.scanner = this.scanner;
      ReferenceExpression copy = (ReferenceExpression)parser.parseExpression(source, compilationUnit != null ? this.sourceStart : 0, this.sourceEnd - this.sourceStart + 1, this.enclosingScope.referenceCompilationUnit(), false);
      copy.original = this;
      copy.sourceStart = this.sourceStart;
      copy.sourceEnd = this.sourceEnd;
      return copy;
   }

   private boolean shouldGenerateSecretReceiverVariable() {
      if (this.isMethodReference() && this.haveReceiver) {
         return this.lhs instanceof Invocation ? true : (new ASTVisitor() {
            boolean accessesnonFinalOuterLocals;

            public boolean visit(SingleNameReference name, BlockScope skope) {
               Binding local = skope.getBinding(name.getName(), ReferenceExpression.this);
               if (local instanceof LocalVariableBinding) {
                  LocalVariableBinding localBinding = (LocalVariableBinding)local;
                  if (!localBinding.isFinal() && !localBinding.isEffectivelyFinal()) {
                     this.accessesnonFinalOuterLocals = true;
                  }
               }

               return false;
            }

            public boolean accessesnonFinalOuterLocals() {
               ReferenceExpression.this.lhs.traverse(this, (BlockScope)ReferenceExpression.this.enclosingScope);
               return this.accessesnonFinalOuterLocals;
            }
         }).accessesnonFinalOuterLocals();
      } else {
         return false;
      }
   }

   public void generateImplicitLambda(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      ReferenceExpression copy = this.copy();
      int argc = this.descriptor.parameters.length;
      LambdaExpression implicitLambda = new LambdaExpression(this.compilationResult, false, (this.binding.modifiers & 1073741824) != 0);
      Argument[] arguments = new Argument[argc];

      int parameterShift;
      for(parameterShift = 0; parameterShift < argc; ++parameterShift) {
         arguments[parameterShift] = new Argument(CharOperation.append(ImplicitArgName, Integer.toString(parameterShift).toCharArray()), 0L, (TypeReference)null, 0, true);
      }

      implicitLambda.setArguments(arguments);
      implicitLambda.setExpressionContext(this.expressionContext);
      implicitLambda.setExpectedType(this.expectedType);
      parameterShift = this.receiverPrecedesParameters ? 1 : 0;
      Expression[] argv = new SingleNameReference[argc - parameterShift];
      int i = 0;

      for(int length = argv.length; i < length; ++i) {
         char[] name = CharOperation.append(ImplicitArgName, Integer.toString(i + parameterShift).toCharArray());
         argv[i] = new SingleNameReference(name, 0L);
      }

      boolean generateSecretReceiverVariable = this.shouldGenerateSecretReceiverVariable();
      if (this.isMethodReference()) {
         if (generateSecretReceiverVariable) {
            this.lhs.generateCode(currentScope, codeStream, true);
            codeStream.store(this.receiverVariable, false);
            codeStream.addVariable(this.receiverVariable);
         }

         MessageSend message = new MessageSend();
         message.selector = this.selector;
         Expression receiver = generateSecretReceiverVariable ? new SingleNameReference(this.receiverVariable.name, 0L) : copy.lhs;
         message.receiver = (Expression)(this.receiverPrecedesParameters ? new SingleNameReference(CharOperation.append(ImplicitArgName, Integer.toString(0).toCharArray()), 0L) : receiver);
         message.typeArguments = copy.typeArguments;
         message.arguments = argv;
         implicitLambda.setBody(message);
      } else if (this.isArrayConstructorReference()) {
         ArrayAllocationExpression arrayAllocationExpression = new ArrayAllocationExpression();
         arrayAllocationExpression.dimensions = new Expression[]{argv[0]};
         if (this.lhs instanceof ArrayTypeReference) {
            ArrayTypeReference arrayTypeReference = (ArrayTypeReference)this.lhs;
            arrayAllocationExpression.type = (TypeReference)(arrayTypeReference.dimensions == 1 ? new SingleTypeReference(arrayTypeReference.token, 0L) : new ArrayTypeReference(arrayTypeReference.token, arrayTypeReference.dimensions - 1, 0L));
         } else {
            ArrayQualifiedTypeReference arrayQualifiedTypeReference = (ArrayQualifiedTypeReference)this.lhs;
            arrayAllocationExpression.type = (TypeReference)(arrayQualifiedTypeReference.dimensions == 1 ? new QualifiedTypeReference(arrayQualifiedTypeReference.tokens, arrayQualifiedTypeReference.sourcePositions) : new ArrayQualifiedTypeReference(arrayQualifiedTypeReference.tokens, arrayQualifiedTypeReference.dimensions - 1, arrayQualifiedTypeReference.sourcePositions));
         }

         implicitLambda.setBody(arrayAllocationExpression);
      } else {
         AllocationExpression allocation = new AllocationExpression();
         if (this.lhs instanceof TypeReference) {
            allocation.type = (TypeReference)this.lhs;
         } else if (this.lhs instanceof SingleNameReference) {
            allocation.type = new SingleTypeReference(((SingleNameReference)this.lhs).token, 0L);
         } else {
            if (!(this.lhs instanceof QualifiedNameReference)) {
               throw new IllegalStateException("Unexpected node type");
            }

            allocation.type = new QualifiedTypeReference(((QualifiedNameReference)this.lhs).tokens, new long[((QualifiedNameReference)this.lhs).tokens.length]);
         }

         allocation.typeArguments = copy.typeArguments;
         allocation.arguments = argv;
         implicitLambda.setBody(allocation);
      }

      BlockScope lambdaScope = this.receiverVariable != null ? this.receiverVariable.declaringScope : currentScope;
      IErrorHandlingPolicy oldPolicy = lambdaScope.problemReporter().switchErrorHandlingPolicy(silentErrorHandlingPolicy);

      try {
         implicitLambda.resolveType(lambdaScope, true);
         implicitLambda.analyseCode(lambdaScope, new FieldInitsFakingFlowContext((FlowContext)null, this, Binding.NO_EXCEPTIONS, (FlowContext)null, lambdaScope, FlowInfo.DEAD_END), UnconditionalFlowInfo.fakeInitializedFlowInfo(lambdaScope.outerMostMethodScope().analysisIndex, lambdaScope.referenceType().maxFieldCount));
      } finally {
         lambdaScope.problemReporter().switchErrorHandlingPolicy(oldPolicy);
      }

      SyntheticArgumentBinding[] outerLocals = this.receiverType.syntheticOuterLocalVariables();
      int i = 0;

      for(int length = outerLocals == null ? 0 : outerLocals.length; i < length; ++i) {
         implicitLambda.addSyntheticArgument(outerLocals[i].actualOuterLocalVariable);
      }

      implicitLambda.generateCode(lambdaScope, codeStream, valueRequired);
      if (generateSecretReceiverVariable) {
         codeStream.removeVariable(this.receiverVariable);
      }

   }

   private boolean shouldGenerateImplicitLambda(BlockScope currentScope) {
      return this.binding.isVarargs() || this.isConstructorReference() && this.receiverType.syntheticOuterLocalVariables() != null && this.shouldCaptureInstance || this.requiresBridges() || !this.isDirectCodeGenPossible();
   }

   private boolean isDirectCodeGenPossible() {
      if (this.binding != null) {
         if (this.isMethodReference() && this.syntheticAccessor == null && TypeBinding.notEquals(this.binding.declaringClass, this.lhs.resolvedType.erasure()) && !this.binding.declaringClass.canBeSeenBy((Scope)this.enclosingScope)) {
            return !this.binding.isFinal() && !this.binding.isStatic();
         }

         TypeBinding[] descriptorParams = this.descriptor.parameters;
         TypeBinding[] origParams = this.binding.original().parameters;
         TypeBinding[] origDescParams = this.descriptor.original().parameters;
         int offset = this.receiverPrecedesParameters ? 1 : 0;

         for(int i = 0; i < descriptorParams.length - offset; ++i) {
            TypeBinding descType = descriptorParams[i + offset];
            TypeBinding origDescType = origDescParams[i + offset];
            if (descType.isIntersectionType18() || descType.isTypeVariable() && ((TypeVariableBinding)descType).boundsCount() > 1) {
               return CharOperation.equals(origDescType.signature(), origParams[i].signature());
            }
         }
      }

      return true;
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      this.actualMethodBinding = this.binding;
      if (this.shouldGenerateImplicitLambda(currentScope)) {
         this.generateImplicitLambda(currentScope, codeStream, valueRequired);
      } else {
         SourceTypeBinding sourceType = currentScope.enclosingSourceType();
         if (this.receiverType.isArrayType()) {
            char[] lambdaName = CharOperation.concat(TypeConstants.ANONYMOUS_METHOD, Integer.toString(this.ordinal).toCharArray());
            if (this.isConstructorReference()) {
               this.actualMethodBinding = this.binding = sourceType.addSyntheticArrayMethod((ArrayBinding)this.receiverType, 14, lambdaName);
            } else if (CharOperation.equals(this.selector, TypeConstants.CLONE)) {
               this.actualMethodBinding = this.binding = sourceType.addSyntheticArrayMethod((ArrayBinding)this.receiverType, 15, lambdaName);
            }
         } else if (this.syntheticAccessor != null) {
            if (this.lhs.isSuper() || this.isMethodReference()) {
               this.binding = this.syntheticAccessor;
            }
         } else if (this.binding != null && this.isMethodReference() && TypeBinding.notEquals(this.binding.declaringClass, this.lhs.resolvedType.erasure()) && !this.binding.declaringClass.canBeSeenBy((Scope)currentScope)) {
            this.binding = new MethodBinding(this.binding.original(), (ReferenceBinding)this.lhs.resolvedType.erasure());
         }

         int pc = codeStream.position;
         StringBuffer buffer = new StringBuffer();
         int argumentsSize = 0;
         buffer.append('(');
         if (this.haveReceiver) {
            this.lhs.generateCode(currentScope, codeStream, true);
            if (this.isMethodReference() && !this.lhs.isThis() && !this.lhs.isSuper()) {
               MethodBinding mb = currentScope.getJavaLangObject().getExactMethod(TypeConstants.GETCLASS, Binding.NO_PARAMETERS, currentScope.compilationUnitScope());
               codeStream.dup();
               codeStream.invoke((byte)-74, mb, mb.declaringClass);
               codeStream.pop();
            }

            if (this.lhs.isSuper() && !this.actualMethodBinding.isPrivate()) {
               if (this.lhs instanceof QualifiedSuperReference) {
                  QualifiedSuperReference qualifiedSuperReference = (QualifiedSuperReference)this.lhs;
                  TypeReference qualification = qualifiedSuperReference.qualification;
                  if (qualification.resolvedType.isInterface()) {
                     buffer.append(sourceType.signature());
                  } else {
                     buffer.append(((QualifiedSuperReference)this.lhs).currentCompatibleType.signature());
                  }
               } else {
                  buffer.append(sourceType.signature());
               }
            } else {
               buffer.append(this.receiverType.signature());
            }

            argumentsSize = 1;
         } else if (this.isConstructorReference()) {
            ReferenceBinding[] enclosingInstances = Binding.UNINITIALIZED_REFERENCE_TYPES;
            if (this.receiverType.isNestedType()) {
               ReferenceBinding nestedType = (ReferenceBinding)this.receiverType;
               if ((enclosingInstances = nestedType.syntheticEnclosingInstanceTypes()) != null) {
                  int length = enclosingInstances.length;
                  argumentsSize = length;

                  for(int i = 0; i < length; ++i) {
                     ReferenceBinding syntheticArgumentType = enclosingInstances[i];
                     buffer.append(syntheticArgumentType.signature());
                     Object[] emulationPath = currentScope.getEmulationPath(syntheticArgumentType, false, true);
                     codeStream.generateOuterAccess(emulationPath, this, syntheticArgumentType, currentScope);
                  }
               } else {
                  enclosingInstances = Binding.NO_REFERENCE_TYPES;
               }
            }

            if (this.syntheticAccessor != null) {
               char[] lambdaName = CharOperation.concat(TypeConstants.ANONYMOUS_METHOD, Integer.toString(this.ordinal).toCharArray());
               this.binding = sourceType.addSyntheticFactoryMethod(this.binding, this.syntheticAccessor, enclosingInstances, lambdaName);
               this.syntheticAccessor = null;
            }
         }

         buffer.append(')');
         buffer.append('L');
         if (this.resolvedType.isIntersectionType18()) {
            buffer.append(this.descriptor.declaringClass.constantPoolName());
         } else {
            buffer.append(this.resolvedType.constantPoolName());
         }

         buffer.append(';');
         if (this.isSerializable) {
            sourceType.addSyntheticMethod(this);
         }

         int invokeDynamicNumber = codeStream.classFile.recordBootstrapMethod(this);
         codeStream.invokeDynamic(invokeDynamicNumber, argumentsSize, 1, this.descriptor.selector, buffer.toString().toCharArray(), this.isConstructorReference(), this.lhs instanceof TypeReference ? (TypeReference)this.lhs : null, this.typeArguments);
         if (!valueRequired) {
            codeStream.pop();
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      }
   }

   public void cleanUp() {
      ReferenceExpression copy;
      if (this.copiesPerTargetType != null) {
         for(Iterator var2 = this.copiesPerTargetType.values().iterator(); var2.hasNext(); copy.scanner = null) {
            copy = (ReferenceExpression)var2.next();
         }
      }

      if (this.original != null && this.original != this) {
         this.original.cleanUp();
      }

      this.scanner = null;
      this.receiverVariable = null;
   }

   public void manageSyntheticAccessIfNecessary(BlockScope currentScope, FlowInfo flowInfo) {
      if ((flowInfo.tagBits & 1) == 0 && this.binding != null && this.binding.isValidBinding()) {
         MethodBinding codegenBinding = this.binding.original();
         if (!codegenBinding.isVarargs()) {
            SourceTypeBinding enclosingSourceType = currentScope.enclosingSourceType();
            if (this.isConstructorReference()) {
               ReferenceBinding allocatedType = codegenBinding.declaringClass;
               if (codegenBinding.isPrivate() && TypeBinding.notEquals(enclosingSourceType, allocatedType = codegenBinding.declaringClass)) {
                  if ((allocatedType.tagBits & 16L) != 0L) {
                     codegenBinding.tagBits |= 512L;
                  } else {
                     if (currentScope.enclosingSourceType().isNestmateOf(this.binding.declaringClass)) {
                        this.syntheticAccessor = codegenBinding;
                        return;
                     }

                     this.syntheticAccessor = ((SourceTypeBinding)allocatedType).addSyntheticMethod(codegenBinding, false);
                     currentScope.problemReporter().needToEmulateMethodAccess(codegenBinding, this);
                  }
               }

            } else if (this.binding.isPrivate()) {
               if (TypeBinding.notEquals(enclosingSourceType, codegenBinding.declaringClass)) {
                  this.syntheticAccessor = ((SourceTypeBinding)codegenBinding.declaringClass).addSyntheticMethod(codegenBinding, false);
                  currentScope.problemReporter().needToEmulateMethodAccess(codegenBinding, this);
               }

            } else {
               SourceTypeBinding destinationType;
               if (this.lhs.isSuper()) {
                  destinationType = enclosingSourceType;
                  if (this.lhs instanceof QualifiedSuperReference) {
                     QualifiedSuperReference qualifiedSuperReference = (QualifiedSuperReference)this.lhs;
                     TypeReference qualification = qualifiedSuperReference.qualification;
                     if (!qualification.resolvedType.isInterface()) {
                        destinationType = (SourceTypeBinding)qualifiedSuperReference.currentCompatibleType;
                     }
                  }

                  this.syntheticAccessor = destinationType.addSyntheticMethod(codegenBinding, true);
                  currentScope.problemReporter().needToEmulateMethodAccess(codegenBinding, this);
               } else if (this.binding.isProtected() && (this.bits & 8160) != 0 && codegenBinding.declaringClass.getPackage() != enclosingSourceType.getPackage()) {
                  destinationType = (SourceTypeBinding)enclosingSourceType.enclosingTypeAt((this.bits & 8160) >> 5);
                  this.syntheticAccessor = destinationType.addSyntheticMethod(codegenBinding, this.isSuperAccess());
                  currentScope.problemReporter().needToEmulateMethodAccess(codegenBinding, this);
               }
            }
         }
      }
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      TypeBinding argumentType1;
      if (this.haveReceiver) {
         this.lhs.analyseCode(currentScope, flowContext, flowInfo, true);
         this.lhs.checkNPE(currentScope, flowContext, flowInfo);
      } else if (this.isConstructorReference()) {
         argumentType1 = this.receiverType.leafComponentType();
         if (argumentType1.isNestedType() && argumentType1 instanceof ReferenceBinding && !((ReferenceBinding)argumentType1).isStatic()) {
            currentScope.tagAsAccessingEnclosingInstanceStateOf((ReferenceBinding)argumentType1, false);
            this.shouldCaptureInstance = true;
            ReferenceBinding allocatedTypeErasure = (ReferenceBinding)argumentType1.erasure();
            if (allocatedTypeErasure.isLocalType()) {
               ((LocalTypeBinding)allocatedTypeErasure).addInnerEmulationDependent(currentScope, false);
            }
         }
      }

      if (currentScope.compilerOptions().isAnyEnabled(IrritantSet.UNLIKELY_ARGUMENT_TYPE) && this.binding.isValidBinding() && this.binding != null && this.binding.parameters != null) {
         UnlikelyArgumentCheck argumentCheck;
         if (this.binding.parameters.length == 1 && this.descriptor.parameters.length == (this.receiverPrecedesParameters ? 2 : 1) && !this.binding.isStatic()) {
            argumentType1 = this.descriptor.parameters[this.receiverPrecedesParameters ? 1 : 0];
            TypeBinding actualReceiverType = this.receiverPrecedesParameters ? this.descriptor.parameters[0] : this.binding.declaringClass;
            argumentCheck = UnlikelyArgumentCheck.determineCheckForNonStaticSingleArgumentMethod(argumentType1, currentScope, this.selector, (TypeBinding)actualReceiverType, this.binding.parameters);
            if (argumentCheck != null && argumentCheck.isDangerous(currentScope)) {
               currentScope.problemReporter().unlikelyArgumentType(this, this.binding, argumentType1, argumentCheck.typeToReport, argumentCheck.dangerousMethod);
            }
         } else if (this.binding.parameters.length == 2 && this.descriptor.parameters.length == 2 && this.binding.isStatic()) {
            argumentType1 = this.descriptor.parameters[0];
            TypeBinding argumentType2 = this.descriptor.parameters[1];
            argumentCheck = UnlikelyArgumentCheck.determineCheckForStaticTwoArgumentMethod(argumentType2, currentScope, this.selector, argumentType1, this.binding.parameters, this.receiverType);
            if (argumentCheck != null && argumentCheck.isDangerous(currentScope)) {
               currentScope.problemReporter().unlikelyArgumentType(this, this.binding, argumentType2, argumentCheck.typeToReport, argumentCheck.dangerousMethod);
            }
         }
      }

      this.manageSyntheticAccessIfNecessary(currentScope, flowInfo);
      return flowInfo;
   }

   public boolean checkingPotentialCompatibility() {
      return this.checkingPotentialCompatibility;
   }

   public void acceptPotentiallyCompatibleMethods(MethodBinding[] methods) {
      if (this.checkingPotentialCompatibility) {
         this.potentialMethods = methods;
      }

   }

   public TypeBinding resolveType(BlockScope scope) {
      CompilerOptions compilerOptions = scope.compilerOptions();
      TypeBinding lhsType;
      int parametersLength;
      if (this.constant != Constant.NotAConstant) {
         label453: {
            this.constant = Constant.NotAConstant;
            this.enclosingScope = scope;
            if (this.original == this) {
               this.ordinal = this.recordFunctionalType(scope);
            }

            Expression var10000 = this.lhs;
            var10000.bits |= 1073741824;
            lhsType = this.lhs.resolveType(scope);
            this.lhs.computeConversion(scope, lhsType, lhsType);
            if (this.typeArguments != null) {
               int length = this.typeArguments.length;
               this.typeArgumentsHaveErrors = compilerOptions.sourceLevel < 3211264L;
               this.resolvedTypeArguments = new TypeBinding[length];
               parametersLength = 0;

               while(true) {
                  if (parametersLength >= length) {
                     if (this.typeArgumentsHaveErrors || lhsType == null) {
                        return this.resolvedType = null;
                     }

                     if (this.isConstructorReference() && lhsType.isRawType()) {
                        scope.problemReporter().rawConstructorReferenceNotWithExplicitTypeArguments(this.typeArguments);
                        return this.resolvedType = null;
                     }
                     break;
                  }

                  TypeReference typeReference = this.typeArguments[parametersLength];
                  if ((this.resolvedTypeArguments[parametersLength] = typeReference.resolveType(scope, true)) == null) {
                     this.typeArgumentsHaveErrors = true;
                  }

                  if (this.typeArgumentsHaveErrors && typeReference instanceof Wildcard) {
                     scope.problemReporter().illegalUsageOfWildcard(typeReference);
                  }

                  ++parametersLength;
               }
            }

            if (!this.typeArgumentsHaveErrors && lhsType != null) {
               if (lhsType.problemId() == 21) {
                  lhsType = lhsType.closestMatch();
               }

               if (lhsType != null && lhsType.isValidBinding()) {
                  this.receiverType = lhsType;
                  this.haveReceiver = true;
                  if (this.lhs instanceof NameReference) {
                     if ((this.lhs.bits & 7) == 4) {
                        this.haveReceiver = false;
                     } else if (this.isConstructorReference()) {
                        scope.problemReporter().invalidType(this.lhs, new ProblemReferenceBinding(((NameReference)this.lhs).getName(), (ReferenceBinding)null, 1));
                        return this.resolvedType = null;
                     }
                  } else if (this.lhs instanceof TypeReference) {
                     this.haveReceiver = false;
                  }

                  if (!this.haveReceiver && !this.lhs.isSuper() && !this.isArrayConstructorReference()) {
                     this.receiverType = lhsType.capture(scope, this.sourceStart, this.sourceEnd);
                  }

                  if (!lhsType.isRawType()) {
                     this.binding = this.exactMethodBinding = this.isMethodReference() ? scope.getExactMethod(lhsType, this.selector, this) : scope.getExactConstructor(lhsType, this);
                  }

                  if (this.isConstructorReference() && !lhsType.canBeInstantiated()) {
                     scope.problemReporter().cannotInstantiate(this.lhs, lhsType);
                     return this.resolvedType = null;
                  }

                  if (this.lhs instanceof TypeReference && ((TypeReference)this.lhs).hasNullTypeAnnotation(TypeReference.AnnotationPosition.ANY)) {
                     scope.problemReporter().nullAnnotationUnsupportedLocation((TypeReference)this.lhs);
                  }

                  if (this.isConstructorReference() && lhsType.isArrayType()) {
                     TypeBinding leafComponentType = lhsType.leafComponentType();
                     if (!leafComponentType.isReifiable()) {
                        scope.problemReporter().illegalGenericArray(leafComponentType, this);
                        return this.resolvedType = null;
                     }

                     if (this.typeArguments != null) {
                        scope.problemReporter().invalidTypeArguments(this.typeArguments);
                        return this.resolvedType = null;
                     }

                     this.binding = this.exactMethodBinding = scope.getExactConstructor(lhsType, this);
                  }

                  if (this.isMethodReference() && this.haveReceiver && this.original == this) {
                     this.receiverVariable = new LocalVariableBinding((" rec_" + this.nameSourceStart).toCharArray(), this.lhs.resolvedType, 0, false);
                     scope.addLocalVariable(this.receiverVariable);
                     this.receiverVariable.setConstant(Constant.NotAConstant);
                     this.receiverVariable.useFlag = 1;
                  }

                  if (this.expectedType == null && this.expressionContext == ExpressionContext.INVOCATION_CONTEXT) {
                     if (compilerOptions.isAnnotationBasedNullAnalysisEnabled && this.binding != null) {
                        ImplicitNullAnnotationVerifier.ensureNullnessIsKnown(this.binding, scope);
                     }

                     return new PolyTypeBinding(this);
                  }
                  break label453;
               }

               return this.resolvedType = null;
            }

            return this.resolvedType = null;
         }
      } else {
         lhsType = this.lhs.resolvedType;
         if (this.typeArgumentsHaveErrors || lhsType == null) {
            return this.resolvedType = null;
         }
      }

      super.resolveType(scope);
      if (this.descriptor != null && this.descriptor.isValidBinding()) {
         TypeBinding[] descriptorParameters = this.descriptorParametersAsArgumentExpressions();
         if (lhsType.isBaseType()) {
            scope.problemReporter().errorNoMethodFor(this.lhs, lhsType, this.selector, descriptorParameters);
            return this.resolvedType = null;
         } else {
            parametersLength = descriptorParameters.length;
            if (this.isConstructorReference() && lhsType.isArrayType()) {
               if (parametersLength == 1 && scope.parameterCompatibilityLevel(descriptorParameters[0], TypeBinding.INT) != -1) {
                  if (this.descriptor.returnType.isProperType(true) && !lhsType.isCompatibleWith(this.descriptor.returnType) && this.descriptor.returnType.id != 6) {
                     scope.problemReporter().constructedArrayIncompatible(this, lhsType, this.descriptor.returnType);
                     return this.resolvedType = null;
                  } else {
                     this.checkNullAnnotations(scope);
                     return this.resolvedType;
                  }
               } else {
                  scope.problemReporter().invalidArrayConstructorReference(this, lhsType, descriptorParameters);
                  return this.resolvedType = null;
               }
            } else {
               boolean isMethodReference = this.isMethodReference();
               this.depth = 0;
               this.freeParameters = descriptorParameters;
               MethodBinding someMethod = null;
               if (isMethodReference) {
                  someMethod = scope.getMethod(this.receiverType, this.selector, descriptorParameters, this);
               } else {
                  if (this.argumentsTypeElided() && this.receiverType.isRawType()) {
                     boolean[] inferredReturnType = new boolean[1];
                     someMethod = AllocationExpression.inferDiamondConstructor(scope, this, this.receiverType, this.descriptor.parameters, inferredReturnType);
                  }

                  if (someMethod == null) {
                     someMethod = scope.getConstructor((ReferenceBinding)this.receiverType, descriptorParameters, this);
                  }
               }

               int someMethodDepth = this.depth;
               int anotherMethodDepth = 0;
               if (someMethod == null || !someMethod.isValidBinding() || !someMethod.isStatic() || !this.haveReceiver && !this.receiverType.isParameterizedTypeWithActualArguments()) {
                  if (this.lhs.isSuper() && this.lhs.resolvedType.isInterface()) {
                     scope.checkAppropriateMethodAgainstSupers(this.selector, someMethod, this.descriptor.parameters, this);
                  }

                  MethodBinding anotherMethod = null;
                  this.receiverPrecedesParameters = false;
                  TypeBinding returnType;
                  if (!this.haveReceiver && isMethodReference && parametersLength > 0) {
                     TypeBinding potentialReceiver = descriptorParameters[0];
                     if (potentialReceiver.isCompatibleWith(this.receiverType, scope)) {
                        TypeBinding typeToSearch = this.receiverType;
                        if (this.receiverType.isRawType()) {
                           returnType = potentialReceiver.findSuperTypeOriginatingFrom(this.receiverType);
                           if (returnType != null) {
                              typeToSearch = returnType.capture(scope, this.sourceStart, this.sourceEnd);
                           }
                        }

                        TypeBinding[] parameters = Binding.NO_PARAMETERS;
                        if (parametersLength > 1) {
                           parameters = new TypeBinding[parametersLength - 1];
                           System.arraycopy(descriptorParameters, 1, parameters, 0, parametersLength - 1);
                        }

                        this.depth = 0;
                        this.freeParameters = parameters;
                        anotherMethod = scope.getMethod(typeToSearch, this.selector, parameters, this);
                        anotherMethodDepth = this.depth;
                        this.depth = 0;
                     }
                  }

                  if (someMethod != null && someMethod.isValidBinding() && someMethod.isStatic() && anotherMethod != null && anotherMethod.isValidBinding() && !anotherMethod.isStatic()) {
                     scope.problemReporter().methodReferenceSwingsBothWays(this, anotherMethod, someMethod);
                     return this.resolvedType = null;
                  } else {
                     if (someMethod == null || !someMethod.isValidBinding() || anotherMethod != null && anotherMethod.isValidBinding() && !anotherMethod.isStatic()) {
                        if (anotherMethod == null || !anotherMethod.isValidBinding() || someMethod != null && someMethod.isValidBinding() && someMethod.isStatic()) {
                           this.binding = null;
                           this.bits &= -8161;
                        } else {
                           this.binding = anotherMethod;
                           this.receiverPrecedesParameters = true;
                           this.bits &= -8161;
                           if (anotherMethodDepth > 0) {
                              this.bits |= (anotherMethodDepth & 255) << 5;
                           }

                           if (anotherMethod.isStatic()) {
                              scope.problemReporter().methodMustBeAccessedStatically(this, anotherMethod);
                              return this.resolvedType = null;
                           }
                        }
                     } else {
                        this.binding = someMethod;
                        this.bits &= -8161;
                        if (someMethodDepth > 0) {
                           this.bits |= (someMethodDepth & 255) << 5;
                        }

                        if (!this.haveReceiver && !someMethod.isStatic() && !someMethod.isConstructor()) {
                           scope.problemReporter().methodMustBeAccessedWithInstance(this, someMethod);
                           return this.resolvedType = null;
                        }
                     }

                     if (this.binding == null) {
                        char[] visibleName = this.isConstructorReference() ? this.receiverType.sourceName() : this.selector;
                        scope.problemReporter().danglingReference(this, this.receiverType, visibleName, descriptorParameters);
                        return this.resolvedType = null;
                     } else {
                        if (this.binding.isAbstract() && this.lhs.isSuper()) {
                           scope.problemReporter().cannotDireclyInvokeAbstractMethod(this, this.binding);
                        }

                        if (this.binding.isStatic()) {
                           if (TypeBinding.notEquals(this.binding.declaringClass, this.receiverType)) {
                              scope.problemReporter().indirectAccessToStaticMethod(this, this.binding);
                           }
                        } else {
                           AbstractMethodDeclaration srcMethod = this.binding.sourceMethod();
                           if (srcMethod != null && srcMethod.isMethod()) {
                              srcMethod.bits &= -257;
                           }
                        }

                        if (this.isMethodUseDeprecated(this.binding, scope, true, this)) {
                           scope.problemReporter().deprecatedMethod(this.binding, this);
                        }

                        if (this.typeArguments != null && this.binding.original().typeVariables == Binding.NO_TYPE_VARIABLES) {
                           scope.problemReporter().unnecessaryTypeArgumentsForMethodInvocation(this.binding, this.resolvedTypeArguments, this.typeArguments);
                        }

                        if ((this.binding.tagBits & 128L) != 0L) {
                           scope.problemReporter().missingTypeInMethod(this, this.binding);
                        }

                        TypeBinding[] methodExceptions = this.binding.thrownExceptions;
                        TypeBinding[] kosherExceptions = this.descriptor.thrownExceptions;
                        int i = 0;

                        label323:
                        for(int iMax = methodExceptions.length; i < iMax; ++i) {
                           if (!methodExceptions[i].isUncheckedException(false)) {
                              int j = 0;

                              for(int jMax = kosherExceptions.length; j < jMax; ++j) {
                                 if (methodExceptions[i].isCompatibleWith(kosherExceptions[j], scope)) {
                                    continue label323;
                                 }
                              }

                              scope.problemReporter().unhandledException(methodExceptions[i], (ReferenceExpression)this);
                           }
                        }

                        this.checkNullAnnotations(scope);
                        this.freeParameters = null;
                        if (checkInvocationArguments(scope, (Expression)null, this.receiverType, this.binding, (Expression[])null, descriptorParameters, false, this)) {
                           this.bits |= 65536;
                        }

                        if (this.descriptor.returnType.id != 6) {
                           returnType = null;
                           if (this.binding.isConstructor()) {
                              returnType = this.receiverType;
                           } else if ((this.bits & 65536) != 0 && this.resolvedTypeArguments == null) {
                              returnType = this.binding.returnType;
                              if (returnType != null) {
                                 returnType = scope.environment().convertToRawType(returnType.erasure(), true);
                              }
                           } else {
                              returnType = this.binding.returnType;
                              if (returnType != null) {
                                 returnType = returnType.capture(scope, this.sourceStart, this.sourceEnd);
                              }
                           }

                           if (this.descriptor.returnType.isProperType(true) && !returnType.isCompatibleWith(this.descriptor.returnType, scope) && !this.isBoxingCompatible(returnType, this.descriptor.returnType, this, scope)) {
                              scope.problemReporter().incompatibleReturnType(this, this.binding, this.descriptor.returnType);
                              this.binding = null;
                              this.resolvedType = null;
                           }
                        }

                        return this.resolvedType;
                     }
                  }
               } else {
                  scope.problemReporter().methodMustBeAccessedStatically(this, someMethod);
                  return this.resolvedType = null;
               }
            }
         }
      } else {
         return this.resolvedType = null;
      }
   }

   protected void checkNullAnnotations(BlockScope scope) {
      CompilerOptions compilerOptions = scope.compilerOptions();
      if (compilerOptions.isAnnotationBasedNullAnalysisEnabled && (this.expectedType == null || !NullAnnotationMatching.hasContradictions(this.expectedType))) {
         ImplicitNullAnnotationVerifier.ensureNullnessIsKnown(this.binding, scope);
         int expectedlen = this.binding.parameters.length;
         int providedLen = this.descriptor.parameters.length;
         TypeBinding returnType;
         if (this.receiverPrecedesParameters) {
            --providedLen;
            TypeBinding descriptorParameter = this.descriptor.parameters[0];
            if ((descriptorParameter.tagBits & 36028797018963968L) != 0L) {
               returnType = scope.environment().createAnnotatedType(this.binding.declaringClass, (AnnotationBinding[])(new AnnotationBinding[]{scope.environment().getNonNullAnnotation()}));
               scope.problemReporter().referenceExpressionArgumentNullityMismatch(this, returnType, descriptorParameter, this.descriptor, -1, NullAnnotationMatching.NULL_ANNOTATIONS_MISMATCH);
            }
         }

         boolean isVarArgs = false;
         int len;
         if (this.binding.isVarargs()) {
            isVarArgs = providedLen == expectedlen ? !this.descriptor.parameters[expectedlen - 1].isCompatibleWith(this.binding.parameters[expectedlen - 1]) : true;
            len = providedLen;
         } else {
            len = Math.min(expectedlen, providedLen);
         }

         for(int i = 0; i < len; ++i) {
            TypeBinding descriptorParameter = this.descriptor.parameters[i + (this.receiverPrecedesParameters ? 1 : 0)];
            TypeBinding bindingParameter = InferenceContext18.getParameter(this.binding.parameters, i, isVarArgs);
            TypeBinding bindingParameterToCheck;
            if (bindingParameter.isPrimitiveType() && !descriptorParameter.isPrimitiveType()) {
               bindingParameterToCheck = scope.environment().createAnnotatedType(scope.boxing(bindingParameter), new AnnotationBinding[]{scope.environment().getNonNullAnnotation()});
            } else {
               bindingParameterToCheck = bindingParameter;
            }

            NullAnnotationMatching annotationStatus = NullAnnotationMatching.analyse(bindingParameterToCheck, descriptorParameter, 1);
            if (annotationStatus.isAnyMismatch()) {
               scope.problemReporter().referenceExpressionArgumentNullityMismatch(this, bindingParameter, descriptorParameter, this.descriptor, i, annotationStatus);
            }
         }

         returnType = this.binding.returnType;
         if (!returnType.isPrimitiveType()) {
            if (this.binding.isConstructor()) {
               returnType = scope.environment().createAnnotatedType(this.receiverType, new AnnotationBinding[]{scope.environment().getNonNullAnnotation()});
            }

            NullAnnotationMatching annotationStatus = NullAnnotationMatching.analyse(this.descriptor.returnType, returnType, 1);
            if (annotationStatus.isAnyMismatch()) {
               scope.problemReporter().illegalReturnRedefinition(this, this.descriptor, annotationStatus.isUnchecked(), returnType);
            }
         }
      }

   }

   private TypeBinding[] descriptorParametersAsArgumentExpressions() {
      if (this.descriptor != null && this.descriptor.parameters != null && this.descriptor.parameters.length != 0) {
         if (this.expectedType.isParameterizedType()) {
            ParameterizedTypeBinding type = (ParameterizedTypeBinding)this.expectedType;
            MethodBinding method = type.getSingleAbstractMethod(this.enclosingScope, true, this.sourceStart, this.sourceEnd);
            return method.parameters;
         } else {
            return this.descriptor.parameters;
         }
      } else {
         return Binding.NO_PARAMETERS;
      }
   }

   private ReferenceExpression cachedResolvedCopy(TypeBinding targetType) {
      ReferenceExpression copy = this.copiesPerTargetType != null ? (ReferenceExpression)this.copiesPerTargetType.get(targetType) : null;
      if (copy != null) {
         return copy;
      } else {
         IErrorHandlingPolicy oldPolicy = this.enclosingScope.problemReporter().switchErrorHandlingPolicy(silentErrorHandlingPolicy);

         ReferenceExpression var5;
         try {
            copy = this.copy();
            if (copy == null) {
               return null;
            }

            copy.setExpressionContext(this.expressionContext);
            copy.setExpectedType(targetType);
            copy.resolveType(this.enclosingScope);
            if (this.copiesPerTargetType == null) {
               this.copiesPerTargetType = new HashMap();
            }

            this.copiesPerTargetType.put(targetType, copy);
            var5 = copy;
         } finally {
            this.enclosingScope.problemReporter().switchErrorHandlingPolicy(oldPolicy);
         }

         return var5;
      }
   }

   public void registerInferenceContext(ParameterizedGenericMethodBinding method, InferenceContext18 context) {
      if (this.inferenceContexts == null) {
         this.inferenceContexts = new HashMap();
      }

      this.inferenceContexts.put(method, context);
   }

   public InferenceContext18 getInferenceContext(ParameterizedMethodBinding method) {
      return this.inferenceContexts == null ? null : (InferenceContext18)this.inferenceContexts.get(method);
   }

   public ReferenceExpression resolveExpressionExpecting(TypeBinding targetType, Scope scope, InferenceContext18 inferenceContext) {
      if (this.exactMethodBinding != null) {
         MethodBinding functionType = targetType.getSingleAbstractMethod(scope, true);
         if (functionType != null && functionType.problemId() != 17) {
            int n = functionType.parameters.length;
            int k = this.exactMethodBinding.parameters.length;
            if (!this.haveReceiver && this.isMethodReference() && !this.exactMethodBinding.isStatic()) {
               ++k;
            }

            return n == k ? this : null;
         } else {
            return null;
         }
      } else {
         ReferenceExpression copy = this.cachedResolvedCopy(targetType);
         return copy != null && copy.resolvedType != null && copy.resolvedType.isValidBinding() && copy.binding != null && copy.binding.isValidBinding() ? copy : null;
      }
   }

   public boolean isConstructorReference() {
      return CharOperation.equals(this.selector, ConstantPool.Init);
   }

   public boolean isExactMethodReference() {
      return this.exactMethodBinding != null;
   }

   public MethodBinding getExactMethod() {
      return this.exactMethodBinding;
   }

   public boolean isMethodReference() {
      return !CharOperation.equals(this.selector, ConstantPool.Init);
   }

   public boolean isPertinentToApplicability(TypeBinding targetType, MethodBinding method) {
      return !this.isExactMethodReference() ? false : super.isPertinentToApplicability(targetType, method);
   }

   public TypeBinding[] genericTypeArguments() {
      return this.resolvedTypeArguments;
   }

   public InferenceContext18 freshInferenceContext(Scope scope) {
      if (this.expressionContext != ExpressionContext.VANILLA_CONTEXT) {
         Expression[] arguments = this.createPseudoExpressions(this.freeParameters);
         return new InferenceContext18(scope, arguments, this, (InferenceContext18)null);
      } else {
         return null;
      }
   }

   public boolean isSuperAccess() {
      return this.lhs.isSuper();
   }

   public boolean isTypeAccess() {
      return !this.haveReceiver;
   }

   public void setActualReceiverType(ReferenceBinding receiverType) {
   }

   public void setDepth(int depth) {
      this.depth = depth;
   }

   public void setFieldIndex(int depth) {
   }

   public StringBuffer printExpression(int tab, StringBuffer output) {
      this.lhs.print(0, output);
      output.append("::");
      if (this.typeArguments != null) {
         output.append('<');
         int max = this.typeArguments.length - 1;

         for(int j = 0; j < max; ++j) {
            this.typeArguments[j].print(0, output);
            output.append(", ");
         }

         this.typeArguments[max].print(0, output);
         output.append('>');
      }

      if (this.isConstructorReference()) {
         output.append("new");
      } else {
         output.append(this.selector);
      }

      return output;
   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      if (visitor.visit(this, blockScope)) {
         this.lhs.traverse(visitor, blockScope);
         int length = this.typeArguments == null ? 0 : this.typeArguments.length;

         for(int i = 0; i < length; ++i) {
            this.typeArguments[i].traverse(visitor, blockScope);
         }
      }

      visitor.endVisit(this, blockScope);
   }

   public Expression[] createPseudoExpressions(TypeBinding[] p) {
      Expression[] expressions = new Expression[p.length];
      long pos = ((long)this.sourceStart << 32) + (long)this.sourceEnd;

      for(int i = 0; i < p.length; ++i) {
         expressions[i] = new SingleNameReference(("fakeArg" + i).toCharArray(), pos);
         expressions[i].resolvedType = p[i];
      }

      return expressions;
   }

   public boolean isPotentiallyCompatibleWith(TypeBinding targetType, Scope scope) {
      boolean isConstructorRef = this.isConstructorReference();
      if (isConstructorRef) {
         if (this.receiverType == null) {
            return false;
         }

         if (this.receiverType.isArrayType()) {
            TypeBinding leafComponentType = this.receiverType.leafComponentType();
            if (!leafComponentType.isReifiable()) {
               return false;
            }
         }
      }

      if (!super.isPertinentToApplicability(targetType, (MethodBinding)null)) {
         return true;
      } else {
         MethodBinding sam = targetType.getSingleAbstractMethod(this.enclosingScope, true);
         if (sam != null && sam.isValidBinding()) {
            if (!this.typeArgumentsHaveErrors && this.receiverType != null && this.receiverType.isValidBinding()) {
               int parametersLength = sam.parameters.length;
               TypeBinding[] descriptorParameters = new TypeBinding[parametersLength];

               for(int i = 0; i < parametersLength; ++i) {
                  descriptorParameters[i] = new ReferenceBinding() {
                     {
                        this.compoundName = CharOperation.NO_CHAR_CHAR;
                     }

                     public boolean isCompatibleWith(TypeBinding otherType, Scope captureScope) {
                        return true;
                     }

                     public TypeBinding findSuperTypeOriginatingFrom(TypeBinding otherType) {
                        return otherType;
                     }

                     public String toString() {
                        return "(wildcard)";
                     }
                  };
               }

               this.freeParameters = descriptorParameters;
               this.checkingPotentialCompatibility = true;

               try {
                  MethodBinding compileTimeDeclaration = this.getCompileTimeDeclaration(scope, isConstructorRef, descriptorParameters);
                  if (compileTimeDeclaration != null && compileTimeDeclaration.isValidBinding()) {
                     this.potentialMethods = new MethodBinding[]{compileTimeDeclaration};
                  }

                  int i = 0;

                  int length;
                  for(length = this.potentialMethods.length; i < length; ++i) {
                     if (!this.potentialMethods[i].isStatic() && !this.potentialMethods[i].isConstructor()) {
                        if (this.haveReceiver) {
                           return true;
                        }
                     } else if (!this.haveReceiver) {
                        return true;
                     }
                  }

                  if (this.haveReceiver || parametersLength == 0) {
                     return false;
                  }

                  System.arraycopy(descriptorParameters, 1, descriptorParameters = new TypeBinding[parametersLength - 1], 0, parametersLength - 1);
                  this.freeParameters = descriptorParameters;
                  this.potentialMethods = Binding.NO_METHODS;
                  compileTimeDeclaration = this.getCompileTimeDeclaration(scope, false, descriptorParameters);
                  if (compileTimeDeclaration != null && compileTimeDeclaration.isValidBinding()) {
                     this.potentialMethods = new MethodBinding[]{compileTimeDeclaration};
                  }

                  i = 0;

                  for(length = this.potentialMethods.length; i < length; ++i) {
                     if (!this.potentialMethods[i].isStatic() && !this.potentialMethods[i].isConstructor()) {
                        return true;
                     }
                  }
               } finally {
                  this.checkingPotentialCompatibility = false;
                  this.potentialMethods = Binding.NO_METHODS;
                  this.freeParameters = null;
               }

               return false;
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   MethodBinding getCompileTimeDeclaration(Scope scope, boolean isConstructorRef, TypeBinding[] parameters) {
      if (this.exactMethodBinding != null) {
         return this.exactMethodBinding;
      } else if (this.receiverType.isArrayType()) {
         return scope.findMethodForArray((ArrayBinding)this.receiverType, this.selector, Binding.NO_PARAMETERS, this);
      } else {
         return isConstructorRef ? scope.getConstructor((ReferenceBinding)this.receiverType, parameters, this) : scope.getMethod(this.receiverType, this.selector, parameters, this);
      }
   }

   public boolean isCompatibleWith(TypeBinding targetType, Scope scope) {
      ReferenceExpression copy = this.cachedResolvedCopy(targetType);
      return copy != null && copy.resolvedType != null && copy.resolvedType.isValidBinding() && copy.binding != null && copy.binding.isValidBinding();
   }

   public boolean sIsMoreSpecific(TypeBinding s, TypeBinding t, Scope scope) {
      if (super.sIsMoreSpecific(s, t, scope)) {
         return true;
      } else if (this.exactMethodBinding != null && t.findSuperTypeOriginatingFrom(s) == null) {
         s = s.capture(this.enclosingScope, this.sourceStart, this.sourceEnd);
         MethodBinding sSam = s.getSingleAbstractMethod(this.enclosingScope, true);
         if (sSam != null && sSam.isValidBinding()) {
            TypeBinding r1 = sSam.returnType;
            MethodBinding tSam = t.getSingleAbstractMethod(this.enclosingScope, true);
            if (tSam != null && tSam.isValidBinding()) {
               TypeBinding r2 = tSam.returnType;
               TypeBinding[] sParams = sSam.parameters;
               TypeBinding[] tParams = tSam.parameters;

               for(int i = 0; i < sParams.length; ++i) {
                  if (TypeBinding.notEquals(sParams[i], tParams[i])) {
                     return false;
                  }
               }

               if (r2.id == 6) {
                  return true;
               } else if (r1.id == 6) {
                  return false;
               } else if (r1.isCompatibleWith(r2, scope)) {
                  return true;
               } else {
                  return r1.isBaseType() != r2.isBaseType() && r1.isBaseType() == this.exactMethodBinding.returnType.isBaseType();
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public MethodBinding getMethodBinding() {
      if (this.actualMethodBinding == null) {
         this.actualMethodBinding = this.binding;
      }

      return this.actualMethodBinding;
   }

   public boolean isArrayConstructorReference() {
      return this.isConstructorReference() && this.lhs.resolvedType != null && this.lhs.resolvedType.isArrayType();
   }
}

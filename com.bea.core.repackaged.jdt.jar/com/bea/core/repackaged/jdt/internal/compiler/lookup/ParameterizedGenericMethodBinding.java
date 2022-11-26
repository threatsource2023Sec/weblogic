package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Expression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Invocation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.NullAnnotationMatching;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ReferenceExpression;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;

public class ParameterizedGenericMethodBinding extends ParameterizedMethodBinding implements Substitution {
   public TypeBinding[] typeArguments;
   protected LookupEnvironment environment;
   public boolean inferredReturnType;
   public boolean wasInferred;
   public boolean isRaw;
   private MethodBinding tiebreakMethod;
   public boolean inferredWithUncheckedConversion;
   public TypeBinding targetType;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$TypeConstants$BoundCheckStatus;

   public static MethodBinding computeCompatibleMethod(MethodBinding originalMethod, TypeBinding[] arguments, Scope scope, InvocationSite invocationSite) {
      LookupEnvironment environment = scope.environment();
      if (environment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
         ImplicitNullAnnotationVerifier.ensureNullnessIsKnown(originalMethod, scope);
      }

      TypeVariableBinding[] typeVariables = originalMethod.typeVariables;
      TypeBinding[] substitutes = invocationSite.genericTypeArguments();
      InferenceContext inferenceContext = null;
      TypeBinding[] uncheckedArguments = null;
      ParameterizedGenericMethodBinding methodSubstitute;
      TypeBinding[] parameters;
      int length;
      if (substitutes != null) {
         if (substitutes.length != typeVariables.length) {
            return new ProblemMethodBinding(originalMethod, originalMethod.selector, substitutes, 11);
         }

         methodSubstitute = environment.createParameterizedGenericMethod(originalMethod, substitutes);
      } else {
         parameters = originalMethod.parameters;
         CompilerOptions compilerOptions = scope.compilerOptions();
         if (compilerOptions.sourceLevel >= 3407872L) {
            return computeCompatibleMethod18(originalMethod, arguments, scope, invocationSite);
         }

         inferenceContext = new InferenceContext(originalMethod);
         methodSubstitute = inferFromArgumentTypes(scope, originalMethod, arguments, parameters, inferenceContext);
         if (methodSubstitute == null) {
            return null;
         }

         if (inferenceContext.hasUnresolvedTypeArgument()) {
            if (inferenceContext.isUnchecked) {
               length = inferenceContext.substitutes.length;
               System.arraycopy(inferenceContext.substitutes, 0, uncheckedArguments = new TypeBinding[length], 0, length);
            }

            if (methodSubstitute.returnType != TypeBinding.VOID) {
               TypeBinding expectedType = invocationSite.invocationTargetType();
               if (expectedType != null) {
                  inferenceContext.hasExplicitExpectedType = true;
               } else {
                  expectedType = scope.getJavaLangObject();
               }

               inferenceContext.expectedType = (TypeBinding)expectedType;
            }

            methodSubstitute = methodSubstitute.inferFromExpectedType(scope, inferenceContext);
            if (methodSubstitute == null) {
               return null;
            }
         } else if (compilerOptions.sourceLevel == 3342336L && methodSubstitute.returnType != TypeBinding.VOID) {
            TypeBinding expectedType = invocationSite.invocationTargetType();
            if (expectedType != null && !originalMethod.returnType.mentionsAny(originalMethod.parameters, -1)) {
               TypeBinding uncaptured = methodSubstitute.returnType.uncapture(scope);
               if (!methodSubstitute.returnType.isCompatibleWith(expectedType) && expectedType.isCompatibleWith(uncaptured)) {
                  InferenceContext oldContext = inferenceContext;
                  inferenceContext = new InferenceContext(originalMethod);
                  originalMethod.returnType.collectSubstitutes(scope, expectedType, inferenceContext, 1);
                  ParameterizedGenericMethodBinding substitute = inferFromArgumentTypes(scope, originalMethod, arguments, parameters, inferenceContext);
                  if (substitute != null && substitute.returnType.isCompatibleWith(expectedType)) {
                     if (scope.parameterCompatibilityLevel(substitute, arguments, false) > -1) {
                        methodSubstitute = substitute;
                     } else {
                        inferenceContext = oldContext;
                     }
                  } else {
                     inferenceContext = oldContext;
                  }
               }
            }
         }
      }

      parameters = null;
      Object substitution;
      if (inferenceContext != null) {
         substitution = new LingeringTypeVariableEliminator(typeVariables, inferenceContext.substitutes, scope);
      } else {
         substitution = methodSubstitute;
      }

      int i = 0;

      for(length = typeVariables.length; i < length; ++i) {
         TypeVariableBinding typeVariable = typeVariables[i];
         TypeBinding substitute = methodSubstitute.typeArguments[i];
         TypeBinding substituteForChecks;
         if (substitute instanceof TypeVariableBinding) {
            substituteForChecks = substitute;
         } else {
            substituteForChecks = Scope.substitute(new LingeringTypeVariableEliminator(typeVariables, (TypeBinding[])null, scope), (TypeBinding)substitute);
         }

         if (uncheckedArguments == null || uncheckedArguments[i] != null) {
            switch (typeVariable.boundCheck((Substitution)substitution, substituteForChecks, scope, (ASTNode)null)) {
               case UNCHECKED:
                  methodSubstitute.tagBits |= 256L;
                  break;
               case MISMATCH:
                  int argLength = arguments.length;
                  TypeBinding[] augmentedArguments = new TypeBinding[argLength + 2];
                  System.arraycopy(arguments, 0, augmentedArguments, 0, argLength);
                  augmentedArguments[argLength] = substitute;
                  augmentedArguments[argLength + 1] = typeVariable;
                  return new ProblemMethodBinding(methodSubstitute, originalMethod.selector, augmentedArguments, 10);
            }
         }
      }

      return methodSubstitute;
   }

   public static MethodBinding computeCompatibleMethod18(MethodBinding originalMethod, TypeBinding[] arguments, Scope scope, InvocationSite invocationSite) {
      TypeVariableBinding[] typeVariables = originalMethod.typeVariables;
      if (invocationSite.checkingPotentialCompatibility()) {
         return scope.environment().createParameterizedGenericMethod(originalMethod, (TypeBinding[])typeVariables);
      } else {
         ParameterizedGenericMethodBinding methodSubstitute = null;
         InferenceContext18 infCtx18 = invocationSite.freshInferenceContext(scope);
         if (infCtx18 == null) {
            return originalMethod;
         } else {
            TypeBinding[] parameters = originalMethod.parameters;
            CompilerOptions compilerOptions = scope.compilerOptions();
            boolean invocationTypeInferred = false;
            boolean requireBoxing = false;
            boolean allArgumentsAreProper = true;
            TypeBinding[] argumentsCopy = new TypeBinding[arguments.length];
            int i = 0;
            int length = arguments.length;

            for(int parametersLength = parameters.length; i < length; ++i) {
               TypeBinding parameter = i < parametersLength ? parameters[i] : parameters[parametersLength - 1];
               TypeBinding argument = arguments[i];
               allArgumentsAreProper &= argument.isProperType(true);
               if (argument.isPrimitiveType() != parameter.isPrimitiveType()) {
                  argumentsCopy[i] = scope.environment().computeBoxingType(argument);
                  requireBoxing = true;
               } else {
                  argumentsCopy[i] = argument;
               }
            }

            arguments = argumentsCopy;
            LookupEnvironment environment = scope.environment();
            InferenceContext18 previousContext = environment.currentInferenceContext;
            if (previousContext == null) {
               environment.currentInferenceContext = infCtx18;
            }

            try {
               BoundSet provisionalResult = null;
               BoundSet result = null;
               boolean isPolyExpression = invocationSite instanceof Expression && ((Expression)invocationSite).isTrulyExpression() && ((Expression)invocationSite).isPolyExpression(originalMethod);
               boolean isDiamond = isPolyExpression && originalMethod.isConstructor();
               if (arguments.length == parameters.length) {
                  infCtx18.inferenceKind = requireBoxing ? 2 : 1;
                  infCtx18.inferInvocationApplicability(originalMethod, arguments, isDiamond);
                  result = infCtx18.solve(true);
               }

               if (result == null && originalMethod.isVarargs()) {
                  infCtx18 = invocationSite.freshInferenceContext(scope);
                  infCtx18.inferenceKind = 3;
                  infCtx18.inferInvocationApplicability(originalMethod, arguments, isDiamond);
                  result = infCtx18.solve(true);
               }

               if (result == null) {
                  return null;
               } else if (!infCtx18.isResolved(result)) {
                  return null;
               } else {
                  infCtx18.stepCompleted = 1;
                  TypeBinding expectedType = invocationSite.invocationTargetType();
                  boolean hasReturnProblem = false;
                  if (expectedType != null || !invocationSite.getExpressionContext().definesTargetType() || !isPolyExpression) {
                     provisionalResult = result;
                     result = infCtx18.inferInvocationType(expectedType, invocationSite, originalMethod);
                     invocationTypeInferred = infCtx18.stepCompleted == 3;
                     hasReturnProblem |= result == null;
                     if (hasReturnProblem) {
                        result = provisionalResult;
                     }
                  }

                  if (result == null) {
                     return null;
                  } else {
                     TypeBinding[] solutions = infCtx18.getSolutions(typeVariables, invocationSite, result);
                     if (solutions == null) {
                        return null;
                     } else {
                        methodSubstitute = scope.environment().createParameterizedGenericMethod(originalMethod, solutions, infCtx18.usesUncheckedConversion, hasReturnProblem, expectedType);
                        if (invocationSite instanceof Invocation && allArgumentsAreProper && (expectedType == null || expectedType.isProperType(true))) {
                           infCtx18.forwardResults(result, (Invocation)invocationSite, (ParameterizedMethodBinding)methodSubstitute, expectedType);
                        }

                        try {
                           MethodBinding problemMethod;
                           MethodBinding var24;
                           if (hasReturnProblem) {
                              problemMethod = infCtx18.getReturnProblemMethodIfNeeded(expectedType, (MethodBinding)methodSubstitute);
                              if (problemMethod instanceof ProblemMethodBinding) {
                                 var24 = problemMethod;
                                 return var24;
                              }
                           }

                           if (invocationTypeInferred) {
                              if (compilerOptions.isAnnotationBasedNullAnalysisEnabled) {
                                 NullAnnotationMatching.checkForContradictions((MethodBinding)methodSubstitute, invocationSite, scope);
                              }

                              problemMethod = ((ParameterizedGenericMethodBinding)methodSubstitute).boundCheck18(scope, arguments, invocationSite);
                              if (problemMethod != null) {
                                 var24 = problemMethod;
                                 return var24;
                              }
                           } else {
                              methodSubstitute = new PolyParameterizedGenericMethodBinding((ParameterizedGenericMethodBinding)methodSubstitute);
                           }
                        } finally {
                           if (allArgumentsAreProper) {
                              if (invocationSite instanceof Invocation) {
                                 ((Invocation)invocationSite).registerInferenceContext((ParameterizedGenericMethodBinding)methodSubstitute, infCtx18);
                              } else if (invocationSite instanceof ReferenceExpression) {
                                 ((ReferenceExpression)invocationSite).registerInferenceContext((ParameterizedGenericMethodBinding)methodSubstitute, infCtx18);
                              }
                           }

                        }

                        Object var26 = methodSubstitute;
                        return (MethodBinding)var26;
                     }
                  }
               }
            } catch (InferenceFailureException var34) {
               scope.problemReporter().genericInferenceError(var34.getMessage(), invocationSite);
               return null;
            } finally {
               environment.currentInferenceContext = previousContext;
            }
         }
      }
   }

   MethodBinding boundCheck18(Scope scope, TypeBinding[] arguments, InvocationSite site) {
      Substitution substitution = this;
      ParameterizedGenericMethodBinding methodSubstitute = this;
      TypeVariableBinding[] originalTypeVariables = this.originalMethod.typeVariables;
      int i = 0;
      int length = originalTypeVariables.length;

      while(i < length) {
         TypeVariableBinding typeVariable = originalTypeVariables[i];
         TypeBinding substitute = methodSubstitute.typeArguments[i];
         ASTNode location = site instanceof ASTNode ? (ASTNode)site : null;
         switch (typeVariable.boundCheck(substitution, substitute, scope, location)) {
            case UNCHECKED:
               methodSubstitute.tagBits |= 256L;
            default:
               ++i;
               break;
            case MISMATCH:
               int argLength = arguments.length;
               TypeBinding[] augmentedArguments = new TypeBinding[argLength + 2];
               System.arraycopy(arguments, 0, augmentedArguments, 0, argLength);
               augmentedArguments[argLength] = substitute;
               augmentedArguments[argLength + 1] = typeVariable;
               return new ProblemMethodBinding(methodSubstitute, this.originalMethod.selector, augmentedArguments, 10);
         }
      }

      return null;
   }

   private static ParameterizedGenericMethodBinding inferFromArgumentTypes(Scope scope, MethodBinding originalMethod, TypeBinding[] arguments, TypeBinding[] parameters, InferenceContext inferenceContext) {
      int paramLength;
      int minArgLength;
      int i;
      if (originalMethod.isVarargs()) {
         paramLength = parameters.length;
         minArgLength = paramLength - 1;
         int argLength = arguments.length;

         for(i = 0; i < minArgLength; ++i) {
            parameters[i].collectSubstitutes(scope, arguments[i], inferenceContext, 1);
            if (inferenceContext.status == 1) {
               return null;
            }
         }

         if (minArgLength < argLength) {
            TypeBinding varargType;
            label80: {
               varargType = parameters[minArgLength];
               TypeBinding lastArgument = arguments[minArgLength];
               if (paramLength == argLength) {
                  if (lastArgument == TypeBinding.NULL) {
                     break label80;
                  }

                  switch (lastArgument.dimensions()) {
                     case 0:
                        break;
                     case 1:
                        if (!lastArgument.leafComponentType().isBaseType()) {
                           break label80;
                        }
                        break;
                     default:
                        break label80;
                  }
               }

               varargType = ((ArrayBinding)varargType).elementsType();
            }

            for(int i = minArgLength; i < argLength; ++i) {
               varargType.collectSubstitutes(scope, arguments[i], inferenceContext, 1);
               if (inferenceContext.status == 1) {
                  return null;
               }
            }
         }
      } else {
         paramLength = parameters.length;

         for(minArgLength = 0; minArgLength < paramLength; ++minArgLength) {
            parameters[minArgLength].collectSubstitutes(scope, arguments[minArgLength], inferenceContext, 1);
            if (inferenceContext.status == 1) {
               return null;
            }
         }
      }

      TypeVariableBinding[] originalVariables = originalMethod.typeVariables;
      if (!resolveSubstituteConstraints(scope, originalVariables, inferenceContext, false)) {
         return null;
      } else {
         TypeBinding[] inferredSustitutes = inferenceContext.substitutes;
         TypeBinding[] actualSubstitutes = inferredSustitutes;
         i = 0;

         for(int varLength = originalVariables.length; i < varLength; ++i) {
            if (inferredSustitutes[i] == null) {
               if (actualSubstitutes == inferredSustitutes) {
                  System.arraycopy(inferredSustitutes, 0, actualSubstitutes = new TypeBinding[varLength], 0, i);
               }

               actualSubstitutes[i] = originalVariables[i];
            } else if (actualSubstitutes != inferredSustitutes) {
               actualSubstitutes[i] = inferredSustitutes[i];
            }
         }

         ParameterizedGenericMethodBinding paramMethod = scope.environment().createParameterizedGenericMethod(originalMethod, actualSubstitutes);
         return paramMethod;
      }
   }

   private static boolean resolveSubstituteConstraints(Scope scope, TypeVariableBinding[] typeVariables, InferenceContext inferenceContext, boolean considerEXTENDSConstraints) {
      TypeBinding[] substitutes = inferenceContext.substitutes;
      int varLength = typeVariables.length;

      int i;
      TypeVariableBinding current;
      TypeBinding substitute;
      TypeBinding[] equalSubstitutes;
      label103:
      for(i = 0; i < varLength; ++i) {
         current = typeVariables[i];
         substitute = substitutes[i];
         if (substitute == null) {
            equalSubstitutes = inferenceContext.getSubstitutes(current, 0);
            if (equalSubstitutes != null) {
               int j = 0;

               for(int equalLength = equalSubstitutes.length; j < equalLength; ++j) {
                  TypeBinding equalSubstitute = equalSubstitutes[j];
                  if (equalSubstitute != null) {
                     if (TypeBinding.equalsEquals(equalSubstitute, current)) {
                        for(int k = j + 1; k < equalLength; ++k) {
                           equalSubstitute = equalSubstitutes[k];
                           if (TypeBinding.notEquals(equalSubstitute, current) && equalSubstitute != null) {
                              substitutes[i] = equalSubstitute;
                              continue label103;
                           }
                        }

                        substitutes[i] = current;
                        break;
                     }

                     substitutes[i] = equalSubstitute;
                     break;
                  }
               }
            }
         }
      }

      if (inferenceContext.hasUnresolvedTypeArgument()) {
         for(i = 0; i < varLength; ++i) {
            current = typeVariables[i];
            substitute = substitutes[i];
            if (substitute == null) {
               equalSubstitutes = inferenceContext.getSubstitutes(current, 2);
               if (equalSubstitutes != null) {
                  TypeBinding mostSpecificSubstitute = scope.lowerUpperBound(equalSubstitutes);
                  if (mostSpecificSubstitute == null) {
                     return false;
                  }

                  if (mostSpecificSubstitute != TypeBinding.VOID) {
                     substitutes[i] = mostSpecificSubstitute;
                  }
               }
            }
         }
      }

      if (considerEXTENDSConstraints && inferenceContext.hasUnresolvedTypeArgument()) {
         for(i = 0; i < varLength; ++i) {
            current = typeVariables[i];
            substitute = substitutes[i];
            if (substitute == null) {
               equalSubstitutes = inferenceContext.getSubstitutes(current, 1);
               if (equalSubstitutes != null) {
                  TypeBinding[] glb = Scope.greaterLowerBound(equalSubstitutes, scope, scope.environment());
                  TypeBinding mostSpecificSubstitute = null;
                  if (glb != null) {
                     if (glb.length == 1) {
                        mostSpecificSubstitute = glb[0];
                     } else {
                        TypeBinding[] otherBounds = new TypeBinding[glb.length - 1];
                        System.arraycopy(glb, 1, otherBounds, 0, glb.length - 1);
                        mostSpecificSubstitute = scope.environment().createWildcard((ReferenceBinding)null, 0, glb[0], otherBounds, 1);
                     }
                  }

                  if (mostSpecificSubstitute != null) {
                     substitutes[i] = (TypeBinding)mostSpecificSubstitute;
                  }
               }
            }
         }
      }

      return true;
   }

   public ParameterizedGenericMethodBinding(MethodBinding originalMethod, RawTypeBinding rawType, LookupEnvironment environment) {
      TypeVariableBinding[] originalVariables = originalMethod.typeVariables;
      int length = originalVariables.length;
      TypeBinding[] rawArguments = new TypeBinding[length];

      for(int i = 0; i < length; ++i) {
         rawArguments[i] = environment.convertToRawType(originalVariables[i].erasure(), false);
      }

      this.isRaw = true;
      this.tagBits = originalMethod.tagBits;
      this.environment = environment;
      this.modifiers = originalMethod.modifiers;
      this.selector = originalMethod.selector;
      this.declaringClass = (ReferenceBinding)(rawType == null ? originalMethod.declaringClass : rawType);
      this.typeVariables = Binding.NO_TYPE_VARIABLES;
      this.typeArguments = rawArguments;
      this.originalMethod = originalMethod;
      boolean ignoreRawTypeSubstitution = rawType == null || originalMethod.isStatic();
      this.parameters = Scope.substitute(this, (TypeBinding[])(ignoreRawTypeSubstitution ? originalMethod.parameters : Scope.substitute(rawType, (TypeBinding[])originalMethod.parameters)));
      this.thrownExceptions = Scope.substitute(this, (ReferenceBinding[])(ignoreRawTypeSubstitution ? originalMethod.thrownExceptions : Scope.substitute(rawType, (ReferenceBinding[])originalMethod.thrownExceptions)));
      if (this.thrownExceptions == null) {
         this.thrownExceptions = Binding.NO_EXCEPTIONS;
      }

      this.returnType = Scope.substitute(this, (TypeBinding)(ignoreRawTypeSubstitution ? originalMethod.returnType : Scope.substitute(rawType, (TypeBinding)originalMethod.returnType)));
      this.wasInferred = false;
      this.parameterNonNullness = originalMethod.parameterNonNullness;
      this.defaultNullness = originalMethod.defaultNullness;
   }

   public ParameterizedGenericMethodBinding(MethodBinding originalMethod, TypeBinding[] typeArguments, LookupEnvironment environment, boolean inferredWithUncheckConversion, boolean hasReturnProblem, TypeBinding targetType) {
      this.environment = environment;
      this.inferredWithUncheckedConversion = inferredWithUncheckConversion;
      this.targetType = targetType;
      this.modifiers = originalMethod.modifiers;
      this.selector = originalMethod.selector;
      this.declaringClass = originalMethod.declaringClass;
      if (inferredWithUncheckConversion && originalMethod.isConstructor() && this.declaringClass.isParameterizedType()) {
         this.declaringClass = (ReferenceBinding)environment.convertToRawType(this.declaringClass.erasure(), false);
      }

      this.typeVariables = Binding.NO_TYPE_VARIABLES;
      this.typeArguments = typeArguments;
      this.isRaw = false;
      this.tagBits = originalMethod.tagBits;
      this.originalMethod = originalMethod;
      this.parameters = Scope.substitute(this, (TypeBinding[])originalMethod.parameters);
      int len;
      if (inferredWithUncheckConversion) {
         this.returnType = this.getErasure18_5_2(originalMethod.returnType, environment, hasReturnProblem);
         this.thrownExceptions = new ReferenceBinding[originalMethod.thrownExceptions.length];

         for(len = 0; len < originalMethod.thrownExceptions.length; ++len) {
            this.thrownExceptions[len] = (ReferenceBinding)this.getErasure18_5_2(originalMethod.thrownExceptions[len], environment, false);
         }
      } else {
         this.returnType = Scope.substitute(this, (TypeBinding)originalMethod.returnType);
         this.thrownExceptions = Scope.substitute(this, (ReferenceBinding[])originalMethod.thrownExceptions);
      }

      if (this.thrownExceptions == null) {
         this.thrownExceptions = Binding.NO_EXCEPTIONS;
      }

      int i;
      if ((this.tagBits & 128L) == 0L) {
         if ((this.returnType.tagBits & 128L) != 0L) {
            this.tagBits |= 128L;
         } else {
            label81: {
               len = 0;

               for(i = this.parameters.length; len < i; ++len) {
                  if ((this.parameters[len].tagBits & 128L) != 0L) {
                     this.tagBits |= 128L;
                     break label81;
                  }
               }

               len = 0;

               for(i = this.thrownExceptions.length; len < i; ++len) {
                  if ((this.thrownExceptions[len].tagBits & 128L) != 0L) {
                     this.tagBits |= 128L;
                     break;
                  }
               }
            }
         }
      }

      this.wasInferred = true;
      this.parameterNonNullness = originalMethod.parameterNonNullness;
      this.defaultNullness = originalMethod.defaultNullness;
      len = this.parameters.length;

      for(i = 0; i < len; ++i) {
         if (this.parameters[i] == TypeBinding.NULL) {
            long nullBits = originalMethod.parameters[i].tagBits & 108086391056891904L;
            if (nullBits == 72057594037927936L) {
               if (this.parameterNonNullness == null) {
                  this.parameterNonNullness = new Boolean[len];
               }

               this.parameterNonNullness[i] = Boolean.TRUE;
            }
         }
      }

   }

   TypeBinding getErasure18_5_2(TypeBinding type, LookupEnvironment env, boolean substitute) {
      if (substitute) {
         type = Scope.substitute(this, (TypeBinding)type);
      }

      return env.convertToRawType(type.erasure(), true);
   }

   public char[] computeUniqueKey(boolean isLeaf) {
      StringBuffer buffer = new StringBuffer();
      buffer.append(this.originalMethod.computeUniqueKey(false));
      buffer.append('%');
      buffer.append('<');
      int length;
      if (!this.isRaw) {
         length = this.typeArguments.length;

         for(int i = 0; i < length; ++i) {
            TypeBinding typeArgument = this.typeArguments[i];
            buffer.append(typeArgument.computeUniqueKey(false));
         }
      }

      buffer.append('>');
      length = buffer.length();
      char[] result = new char[length];
      buffer.getChars(0, length, result, 0);
      return result;
   }

   public LookupEnvironment environment() {
      return this.environment;
   }

   public boolean hasSubstitutedParameters() {
      return this.wasInferred ? this.originalMethod.hasSubstitutedParameters() : super.hasSubstitutedParameters();
   }

   public boolean hasSubstitutedReturnType() {
      return this.inferredReturnType ? this.originalMethod.hasSubstitutedReturnType() : super.hasSubstitutedReturnType();
   }

   private ParameterizedGenericMethodBinding inferFromExpectedType(Scope scope, InferenceContext inferenceContext) {
      TypeVariableBinding[] originalVariables = this.originalMethod.typeVariables;
      int varLength = originalVariables.length;
      if (inferenceContext.expectedType != null) {
         this.returnType.collectSubstitutes(scope, inferenceContext.expectedType, inferenceContext, 2);
         if (inferenceContext.status == 1) {
            return null;
         }
      }

      int i;
      for(i = 0; i < varLength; ++i) {
         TypeVariableBinding originalVariable = originalVariables[i];
         TypeBinding argument = this.typeArguments[i];
         boolean argAlreadyInferred = TypeBinding.notEquals(argument, originalVariable);
         if (TypeBinding.equalsEquals(originalVariable.firstBound, originalVariable.superclass)) {
            TypeBinding substitutedBound = Scope.substitute(this, (TypeBinding)originalVariable.superclass);
            argument.collectSubstitutes(scope, substitutedBound, inferenceContext, 2);
            if (inferenceContext.status == 1) {
               return null;
            }

            if (argAlreadyInferred) {
               substitutedBound.collectSubstitutes(scope, argument, inferenceContext, 1);
               if (inferenceContext.status == 1) {
                  return null;
               }
            }
         }

         int j = 0;

         for(int max = originalVariable.superInterfaces.length; j < max; ++j) {
            TypeBinding substitutedBound = Scope.substitute(this, (TypeBinding)originalVariable.superInterfaces[j]);
            argument.collectSubstitutes(scope, substitutedBound, inferenceContext, 2);
            if (inferenceContext.status == 1) {
               return null;
            }

            if (argAlreadyInferred) {
               substitutedBound.collectSubstitutes(scope, argument, inferenceContext, 1);
               if (inferenceContext.status == 1) {
                  return null;
               }
            }
         }
      }

      if (!resolveSubstituteConstraints(scope, originalVariables, inferenceContext, true)) {
         return null;
      } else {
         for(i = 0; i < varLength; ++i) {
            TypeBinding substitute = inferenceContext.substitutes[i];
            if (substitute != null) {
               this.typeArguments[i] = substitute;
            } else {
               this.typeArguments[i] = inferenceContext.substitutes[i] = originalVariables[i].upperBound();
            }
         }

         this.typeArguments = Scope.substitute(this, (TypeBinding[])this.typeArguments);
         TypeBinding oldReturnType = this.returnType;
         this.returnType = Scope.substitute(this, (TypeBinding)this.returnType);
         this.inferredReturnType = inferenceContext.hasExplicitExpectedType && TypeBinding.notEquals(this.returnType, oldReturnType);
         this.parameters = Scope.substitute(this, (TypeBinding[])this.parameters);
         this.thrownExceptions = Scope.substitute(this, (ReferenceBinding[])this.thrownExceptions);
         if (this.thrownExceptions == null) {
            this.thrownExceptions = Binding.NO_EXCEPTIONS;
         }

         if ((this.tagBits & 128L) == 0L) {
            if ((this.returnType.tagBits & 128L) != 0L) {
               this.tagBits |= 128L;
            } else {
               int i = 0;

               int max;
               for(max = this.parameters.length; i < max; ++i) {
                  if ((this.parameters[i].tagBits & 128L) != 0L) {
                     this.tagBits |= 128L;
                     return this;
                  }
               }

               i = 0;

               for(max = this.thrownExceptions.length; i < max; ++i) {
                  if ((this.thrownExceptions[i].tagBits & 128L) != 0L) {
                     this.tagBits |= 128L;
                     break;
                  }
               }
            }
         }

         return this;
      }
   }

   public boolean isParameterizedGeneric() {
      return true;
   }

   public boolean isRawSubstitution() {
      return this.isRaw;
   }

   public TypeBinding substitute(TypeVariableBinding originalVariable) {
      TypeVariableBinding[] variables = this.originalMethod.typeVariables;
      int length = variables.length;
      if (originalVariable.rank < length && TypeBinding.equalsEquals(variables[originalVariable.rank], originalVariable)) {
         TypeBinding substitute = this.typeArguments[originalVariable.rank];
         return originalVariable.combineTypeAnnotations(substitute);
      } else {
         return originalVariable;
      }
   }

   public MethodBinding tiebreakMethod() {
      if (this.tiebreakMethod == null) {
         this.tiebreakMethod = this.originalMethod.asRawMethod(this.environment);
      }

      return this.tiebreakMethod;
   }

   public MethodBinding genericMethod() {
      return (MethodBinding)(this.isRaw ? this : this.originalMethod);
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$TypeConstants$BoundCheckStatus() {
      int[] var10000 = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$TypeConstants$BoundCheckStatus;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[TypeConstants.BoundCheckStatus.values().length];

         try {
            var0[TypeConstants.BoundCheckStatus.MISMATCH.ordinal()] = 4;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[TypeConstants.BoundCheckStatus.NULL_PROBLEM.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[TypeConstants.BoundCheckStatus.OK.ordinal()] = 1;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[TypeConstants.BoundCheckStatus.UNCHECKED.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$TypeConstants$BoundCheckStatus = var0;
         return var0;
      }
   }

   private static class LingeringTypeVariableEliminator implements Substitution {
      private final TypeVariableBinding[] variables;
      private final TypeBinding[] substitutes;
      private final Scope scope;

      public LingeringTypeVariableEliminator(TypeVariableBinding[] variables, TypeBinding[] substitutes, Scope scope) {
         this.variables = variables;
         this.substitutes = substitutes;
         this.scope = scope;
      }

      public TypeBinding substitute(TypeVariableBinding typeVariable) {
         if (typeVariable.rank < this.variables.length && !TypeBinding.notEquals(this.variables[typeVariable.rank], typeVariable)) {
            if (this.substitutes != null) {
               return Scope.substitute(new LingeringTypeVariableEliminator(this.variables, (TypeBinding[])null, this.scope), (TypeBinding)this.substitutes[typeVariable.rank]);
            } else {
               ReferenceBinding genericType = (ReferenceBinding)(typeVariable.declaringElement instanceof ReferenceBinding ? typeVariable.declaringElement : null);
               return this.scope.environment().createWildcard(genericType, typeVariable.rank, (TypeBinding)null, (TypeBinding[])null, 0, typeVariable.getTypeAnnotations());
            }
         } else {
            return typeVariable;
         }
      }

      public LookupEnvironment environment() {
         return this.scope.environment();
      }

      public boolean isRawSubstitution() {
         return false;
      }
   }
}

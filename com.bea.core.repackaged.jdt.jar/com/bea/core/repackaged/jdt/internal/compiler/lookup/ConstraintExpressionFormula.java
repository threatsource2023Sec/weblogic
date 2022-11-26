package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.internal.compiler.ast.Argument;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ConditionalExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Expression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ExpressionContext;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Invocation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.LambdaExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.MessageSend;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ReferenceExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SwitchExpression;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class ConstraintExpressionFormula extends ConstraintFormula {
   Expression left;
   boolean isSoft;

   ConstraintExpressionFormula(Expression expression, TypeBinding type, int relation) {
      this.left = expression;
      this.right = type;
      this.relation = relation;
   }

   ConstraintExpressionFormula(Expression expression, TypeBinding type, int relation, boolean isSoft) {
      this(expression, type, relation);
      this.isSoft = isSoft;
   }

   public Object reduce(InferenceContext18 inferenceContext) throws InferenceFailureException {
      if (this.relation == 8) {
         return this.left.isPotentiallyCompatibleWith(this.right, inferenceContext.scope) ? TRUE : FALSE;
      } else if (this.right.isProperType(true)) {
         if (!this.left.isCompatibleWith(this.right, inferenceContext.scope) && !this.left.isBoxingCompatibleWith(this.right, inferenceContext.scope)) {
            return FALSE;
         } else {
            if (this.left.resolvedType != null && this.left.resolvedType.needsUncheckedConversion(this.right)) {
               inferenceContext.usesUncheckedConversion = true;
            }

            return TRUE;
         }
      } else if (!this.canBePolyExpression(this.left)) {
         TypeBinding exprType = this.left.resolvedType;
         if (exprType != null && exprType.isValidBinding()) {
            return ConstraintTypeFormula.create(exprType, this.right, 1, this.isSoft);
         } else {
            return this.left instanceof MessageSend && ((MessageSend)this.left).actualReceiverType instanceof InferenceVariable ? null : FALSE;
         }
      } else {
         TypeBinding r;
         if (this.left instanceof Invocation) {
            Invocation invocation = (Invocation)this.left;
            MethodBinding previousMethod = invocation.binding();
            if (previousMethod == null) {
               return null;
            } else {
               MethodBinding method = previousMethod.shallowOriginal();
               InferenceContext18.SuspendedInferenceRecord prevInvocation = inferenceContext.enterPolyInvocation(invocation, invocation.arguments());
               InferenceContext18 innerCtx = null;

               try {
                  Expression[] arguments = invocation.arguments();
                  TypeBinding[] argumentTypes = arguments == null ? Binding.NO_PARAMETERS : new TypeBinding[arguments.length];

                  for(int i = 0; i < argumentTypes.length; ++i) {
                     argumentTypes[i] = arguments[i].resolvedType;
                  }

                  ConstraintTypeFormula var36;
                  if (previousMethod instanceof ParameterizedGenericMethodBinding) {
                     innerCtx = invocation.getInferenceContext((ParameterizedGenericMethodBinding)previousMethod);
                     if (innerCtx == null) {
                        r = this.left.resolvedType;
                        if (r != null && r.isValidBinding()) {
                           var36 = ConstraintTypeFormula.create(r, this.right, 1, this.isSoft);
                           return var36;
                        }

                        var36 = FALSE;
                        return var36;
                     }

                     if (innerCtx.stepCompleted < 1) {
                        var36 = FALSE;
                        return var36;
                     }

                     inferenceContext.integrateInnerInferenceB2(innerCtx);
                  } else {
                     inferenceContext.inferenceKind = inferenceContext.getInferenceKind(previousMethod, argumentTypes);
                     boolean isDiamond = method.isConstructor() && this.left.isPolyExpression(method);
                     inferInvocationApplicability(inferenceContext, method, argumentTypes, isDiamond, inferenceContext.inferenceKind);
                  }

                  if (inferenceContext.computeB3(invocation, this.right, method)) {
                     return null;
                  } else {
                     var36 = FALSE;
                     return var36;
                  }
               } finally {
                  inferenceContext.resumeSuspendedInference(prevInvocation, innerCtx);
               }
            }
         } else if (this.left instanceof ConditionalExpression) {
            ConditionalExpression conditional = (ConditionalExpression)this.left;
            return new ConstraintFormula[]{new ConstraintExpressionFormula(conditional.valueIfTrue, this.right, this.relation, this.isSoft), new ConstraintExpressionFormula(conditional.valueIfFalse, this.right, this.relation, this.isSoft)};
         } else if (this.left instanceof SwitchExpression) {
            SwitchExpression se = (SwitchExpression)this.left;
            ConstraintFormula[] cfs = new ConstraintFormula[se.resultExpressions.size()];
            int i = 0;

            Expression re;
            for(Iterator var27 = se.resultExpressions.iterator(); var27.hasNext(); cfs[i++] = new ConstraintExpressionFormula(re, this.right, this.relation, this.isSoft)) {
               re = (Expression)var27.next();
            }

            return cfs;
         } else if (!(this.left instanceof LambdaExpression)) {
            return this.left instanceof ReferenceExpression ? this.reduceReferenceExpressionCompatibility((ReferenceExpression)this.left, inferenceContext) : FALSE;
         } else {
            LambdaExpression lambda = (LambdaExpression)this.left;
            BlockScope scope = lambda.enclosingScope;
            if (this.right instanceof InferenceVariable) {
               return TRUE;
            } else if (!this.right.isFunctionalInterface(scope)) {
               return FALSE;
            } else {
               ReferenceBinding t = (ReferenceBinding)this.right;
               ParameterizedTypeBinding withWildCards = InferenceContext18.parameterizedWithWildcard(t);
               if (withWildCards != null) {
                  t = findGroundTargetType(inferenceContext, scope, lambda, withWildCards);
               }

               if (t == null) {
                  return FALSE;
               } else {
                  MethodBinding functionType = t.getSingleAbstractMethod(scope, true);
                  if (functionType == null) {
                     return FALSE;
                  } else {
                     TypeBinding[] parameters = functionType.parameters;
                     if (parameters.length != lambda.arguments().length) {
                        return FALSE;
                     } else {
                        if (lambda.argumentsTypeElided()) {
                           for(int i = 0; i < parameters.length; ++i) {
                              if (!parameters[i].isProperType(true)) {
                                 return FALSE;
                              }
                           }
                        }

                        lambda = lambda.resolveExpressionExpecting(t, inferenceContext.scope, inferenceContext);
                        if (lambda == null) {
                           return FALSE;
                        } else {
                           if (functionType.returnType == TypeBinding.VOID) {
                              if (!lambda.isVoidCompatible()) {
                                 return FALSE;
                              }
                           } else if (!lambda.isValueCompatible()) {
                              return FALSE;
                           }

                           List result = new ArrayList();
                           if (!lambda.argumentsTypeElided()) {
                              Argument[] arguments = lambda.arguments();

                              for(int i = 0; i < parameters.length; ++i) {
                                 result.add(ConstraintTypeFormula.create(parameters[i], arguments[i].type.resolvedType, 4));
                              }

                              if (lambda.resolvedType != null) {
                                 result.add(ConstraintTypeFormula.create(lambda.resolvedType, this.right, 2));
                              }
                           }

                           if (functionType.returnType != TypeBinding.VOID) {
                              r = functionType.returnType;
                              Expression[] exprs = lambda.resultExpressions();
                              int i = 0;

                              for(int length = exprs == null ? 0 : exprs.length; i < length; ++i) {
                                 Expression expr = exprs[i];
                                 if (r.isProperType(true) && expr.resolvedType != null) {
                                    TypeBinding exprType = expr.resolvedType;
                                    if (!expr.isConstantValueOfTypeAssignableToType(exprType, r) && !exprType.isCompatibleWith(r) && !expr.isBoxingCompatible(exprType, r, expr, scope)) {
                                       return FALSE;
                                    }
                                 } else {
                                    result.add(new ConstraintExpressionFormula(expr, r, 1, this.isSoft));
                                 }
                              }
                           }

                           return result.size() == 0 ? TRUE : result.toArray(new ConstraintFormula[result.size()]);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static ReferenceBinding findGroundTargetType(InferenceContext18 inferenceContext, BlockScope scope, LambdaExpression lambda, ParameterizedTypeBinding targetTypeWithWildCards) {
      if (lambda.argumentsTypeElided()) {
         return lambda.findGroundTargetTypeForElidedLambda(scope, targetTypeWithWildCards);
      } else {
         InferenceContext18.SuspendedInferenceRecord previous = inferenceContext.enterLambda(lambda);

         ReferenceBinding var6;
         try {
            var6 = inferenceContext.inferFunctionalInterfaceParameterization(lambda, scope, targetTypeWithWildCards);
         } finally {
            inferenceContext.resumeSuspendedInference(previous, (InferenceContext18)null);
         }

         return var6;
      }
   }

   private boolean canBePolyExpression(Expression expr) {
      ExpressionContext previousExpressionContext = expr.getExpressionContext();
      if (previousExpressionContext == ExpressionContext.VANILLA_CONTEXT) {
         this.left.setExpressionContext(ExpressionContext.ASSIGNMENT_CONTEXT);
      }

      boolean var4;
      try {
         var4 = expr.isPolyExpression();
      } finally {
         expr.setExpressionContext(previousExpressionContext);
      }

      return var4;
   }

   private Object reduceReferenceExpressionCompatibility(ReferenceExpression reference, InferenceContext18 inferenceContext) {
      TypeBinding t = this.right;
      if (t.isProperType(true)) {
         throw new IllegalStateException("Should not reach here with T being a proper type");
      } else if (!t.isFunctionalInterface(inferenceContext.scope)) {
         return FALSE;
      } else {
         MethodBinding functionType = t.getSingleAbstractMethod(inferenceContext.scope, true);
         if (functionType == null) {
            return FALSE;
         } else {
            reference = reference.resolveExpressionExpecting(t, inferenceContext.scope, inferenceContext);
            MethodBinding potentiallyApplicable = reference != null ? reference.binding : null;
            if (potentiallyApplicable == null) {
               return FALSE;
            } else if (reference.isExactMethodReference()) {
               List newConstraints = new ArrayList();
               TypeBinding[] p = functionType.parameters;
               int n = p.length;
               TypeBinding[] pPrime = potentiallyApplicable.parameters;
               int k = pPrime.length;
               int offset = 0;
               if (n == k + 1) {
                  newConstraints.add(ConstraintTypeFormula.create(p[0], reference.lhs.resolvedType, 1));
                  offset = 1;
               }

               for(int i = offset; i < n; ++i) {
                  newConstraints.add(ConstraintTypeFormula.create(p[i], pPrime[i - offset], 1));
               }

               TypeBinding r = functionType.returnType;
               if (r != TypeBinding.VOID) {
                  TypeBinding rAppl = potentiallyApplicable.isConstructor() && !reference.isArrayConstructorReference() ? potentiallyApplicable.declaringClass : potentiallyApplicable.returnType;
                  if (rAppl == TypeBinding.VOID) {
                     return FALSE;
                  }

                  TypeBinding rPrime = ((TypeBinding)rAppl).capture(inferenceContext.scope, reference.sourceStart, reference.sourceEnd);
                  newConstraints.add(ConstraintTypeFormula.create(rPrime, r, 1));
               }

               return newConstraints.toArray(new ConstraintFormula[newConstraints.size()]);
            } else {
               int n = functionType.parameters.length;

               for(int i = 0; i < n; ++i) {
                  if (!functionType.parameters[i].isProperType(true)) {
                     return FALSE;
                  }
               }

               MethodBinding compileTimeDecl = potentiallyApplicable;
               if (!potentiallyApplicable.isValidBinding()) {
                  return FALSE;
               } else {
                  TypeBinding r = functionType.isConstructor() ? functionType.declaringClass : functionType.returnType;
                  if (((TypeBinding)r).id == 6) {
                     return TRUE;
                  } else {
                     MethodBinding original = potentiallyApplicable.shallowOriginal();
                     if (this.needsInference(reference, original)) {
                        TypeBinding[] argumentTypes;
                        if (t.isParameterizedType()) {
                           MethodBinding capturedFunctionType = ((ParameterizedTypeBinding)t).getSingleAbstractMethod(inferenceContext.scope, true, reference.sourceStart, reference.sourceEnd);
                           argumentTypes = capturedFunctionType.parameters;
                        } else {
                           argumentTypes = functionType.parameters;
                        }

                        InferenceContext18.SuspendedInferenceRecord prevInvocation = inferenceContext.enterPolyInvocation(reference, reference.createPseudoExpressions(argumentTypes));
                        InferenceContext18 innerContext = null;

                        ConstraintTypeFormula var15;
                        try {
                           innerContext = reference.getInferenceContext((ParameterizedMethodBinding)compileTimeDecl);
                           int innerInferenceKind = this.determineInferenceKind(compileTimeDecl, argumentTypes, innerContext);
                           inferInvocationApplicability(inferenceContext, original, argumentTypes, original.isConstructor(), innerInferenceKind);
                           if (!inferenceContext.computeB3(reference, (TypeBinding)r, original)) {
                              var15 = FALSE;
                              return var15;
                           }

                           return null;
                        } catch (InferenceFailureException var18) {
                           var15 = FALSE;
                        } finally {
                           inferenceContext.resumeSuspendedInference(prevInvocation, innerContext);
                        }

                        return var15;
                     } else {
                        TypeBinding rPrime = potentiallyApplicable.isConstructor() ? potentiallyApplicable.declaringClass : potentiallyApplicable.returnType.capture(inferenceContext.scope, reference.sourceStart(), reference.sourceEnd());
                        return ((TypeBinding)rPrime).id == 6 ? FALSE : ConstraintTypeFormula.create((TypeBinding)rPrime, (TypeBinding)r, 1, this.isSoft);
                     }
                  }
               }
            }
         }
      }
   }

   private boolean needsInference(ReferenceExpression reference, MethodBinding original) {
      if (reference.typeArguments != null) {
         return false;
      } else {
         Object compileTimeReturn;
         if (original.isConstructor()) {
            if (original.declaringClass.typeVariables() != Binding.NO_TYPE_VARIABLES && reference.receiverType.isRawType()) {
               return true;
            }

            compileTimeReturn = original.declaringClass;
         } else {
            compileTimeReturn = original.returnType;
         }

         return original.typeVariables() != Binding.NO_TYPE_VARIABLES && ((TypeBinding)compileTimeReturn).mentionsAny(original.typeVariables(), -1);
      }
   }

   private int determineInferenceKind(MethodBinding original, TypeBinding[] argumentTypes, InferenceContext18 innerContext) {
      if (innerContext != null) {
         return innerContext.inferenceKind;
      } else {
         if (original.isVarargs()) {
            int expectedLen = original.parameters.length;
            int providedLen = argumentTypes.length;
            if (expectedLen < providedLen) {
               return 3;
            }

            if (expectedLen == providedLen) {
               TypeBinding providedLast = argumentTypes[expectedLen - 1];
               TypeBinding expectedLast = original.parameters[expectedLen - 1];
               if (!providedLast.isCompatibleWith(expectedLast) && expectedLast.isArrayType()) {
                  expectedLast = expectedLast.leafComponentType();
                  if (providedLast.isCompatibleWith(expectedLast)) {
                     return 3;
                  }
               }
            }
         }

         return 1;
      }
   }

   static void inferInvocationApplicability(InferenceContext18 inferenceContext, MethodBinding method, TypeBinding[] arguments, boolean isDiamond, int checkType) {
      TypeVariableBinding[] typeVariables = method.getAllTypeVariables(isDiamond);
      InferenceVariable[] inferenceVariables = inferenceContext.createInitialBoundSet(typeVariables);
      int paramLength = method.parameters.length;
      TypeBinding varArgsType = null;
      if (method.isVarargs()) {
         int varArgPos = paramLength - 1;
         varArgsType = method.parameters[varArgPos];
      }

      inferenceContext.createInitialConstraintsForParameters(method.parameters, checkType == 3, varArgsType, method);
      inferenceContext.addThrowsContraints(typeVariables, inferenceVariables, method.thrownExceptions);
   }

   static boolean inferPolyInvocationType(InferenceContext18 inferenceContext, InvocationSite invocationSite, TypeBinding targetType, MethodBinding method) throws InferenceFailureException {
      TypeBinding[] typeArguments = invocationSite.genericTypeArguments();
      if (typeArguments == null) {
         TypeBinding returnType = method.isConstructor() ? method.declaringClass : method.returnType;
         if (returnType == TypeBinding.VOID) {
            throw new InferenceFailureException("expression has no value");
         }

         TypeBinding rTheta;
         if (inferenceContext.usesUncheckedConversion) {
            rTheta = getRealErasure((TypeBinding)returnType, inferenceContext.environment);
            ConstraintTypeFormula newConstraint = ConstraintTypeFormula.create(rTheta, targetType, 1);
            return inferenceContext.reduceAndIncorporate(newConstraint);
         }

         rTheta = inferenceContext.substitute((TypeBinding)returnType);
         ParameterizedTypeBinding parameterizedType = InferenceContext18.parameterizedWithWildcard(rTheta);
         if (parameterizedType != null && parameterizedType.arguments != null) {
            TypeBinding[] arguments = parameterizedType.arguments;
            InferenceVariable[] betas = inferenceContext.addTypeVariableSubstitutions(arguments);
            ParameterizedTypeBinding gbeta = inferenceContext.environment.createParameterizedType(parameterizedType.genericType(), betas, parameterizedType.enclosingType(), parameterizedType.getTypeAnnotations());
            inferenceContext.currentBounds.captures.put(gbeta, parameterizedType);
            int i = 0;

            for(int length = arguments.length; i < length; ++i) {
               if (arguments[i].isWildcard()) {
                  WildcardBinding wc = (WildcardBinding)arguments[i];
                  switch (wc.boundKind) {
                     case 1:
                        inferenceContext.currentBounds.addBound(new TypeBound(betas[i], wc.bound(), 2), inferenceContext.environment);
                        break;
                     case 2:
                        inferenceContext.currentBounds.addBound(new TypeBound(betas[i], wc.bound(), 3), inferenceContext.environment);
                  }
               }
            }

            ConstraintTypeFormula newConstraint = ConstraintTypeFormula.create(gbeta, targetType, 1);
            return inferenceContext.reduceAndIncorporate(newConstraint);
         }

         if (rTheta.leafComponentType() instanceof InferenceVariable) {
            InferenceVariable alpha = (InferenceVariable)rTheta.leafComponentType();
            TypeBinding targetLeafType = targetType.leafComponentType();
            boolean toResolve = false;
            if (inferenceContext.currentBounds.condition18_5_2_bullet_3_3_1(alpha, targetLeafType)) {
               toResolve = true;
            } else if (inferenceContext.currentBounds.condition18_5_2_bullet_3_3_2(alpha, targetLeafType, inferenceContext)) {
               toResolve = true;
            } else if (targetLeafType.isPrimitiveType()) {
               TypeBinding wrapper = inferenceContext.currentBounds.findWrapperTypeBound(alpha);
               if (wrapper != null) {
                  toResolve = true;
               }
            }

            if (toResolve) {
               BoundSet solution = inferenceContext.solve(new InferenceVariable[]{alpha});
               if (solution == null) {
                  return false;
               }

               TypeBinding u = solution.getInstantiation(alpha, (LookupEnvironment)null).capture(inferenceContext.scope, invocationSite.sourceStart(), invocationSite.sourceEnd());
               if (rTheta.dimensions() != 0) {
                  u = inferenceContext.environment.createArrayType((TypeBinding)u, rTheta.dimensions());
               }

               ConstraintTypeFormula newConstraint = ConstraintTypeFormula.create((TypeBinding)u, targetType, 1);
               return inferenceContext.reduceAndIncorporate(newConstraint);
            }
         }

         ConstraintTypeFormula newConstraint = ConstraintTypeFormula.create(rTheta, targetType, 1);
         if (!inferenceContext.reduceAndIncorporate(newConstraint)) {
            return false;
         }
      }

      return true;
   }

   private static TypeBinding getRealErasure(TypeBinding type, LookupEnvironment environment) {
      TypeBinding erasure = type.erasure();
      TypeBinding erasedLeaf = erasure.leafComponentType();
      if (erasedLeaf.isGenericType()) {
         erasedLeaf = environment.convertToRawType(erasedLeaf, false);
      }

      return (TypeBinding)(erasure.isArrayType() ? environment.createArrayType(erasedLeaf, erasure.dimensions()) : erasedLeaf);
   }

   Collection inputVariables(InferenceContext18 context) {
      if (this.left instanceof LambdaExpression) {
         if (this.right instanceof InferenceVariable) {
            return Collections.singletonList((InferenceVariable)this.right);
         }

         if (this.right.isFunctionalInterface(context.scope)) {
            LambdaExpression lambda = (LambdaExpression)this.left;
            ReferenceBinding targetType = (ReferenceBinding)this.right;
            ParameterizedTypeBinding withWildCards = InferenceContext18.parameterizedWithWildcard(targetType);
            if (withWildCards != null) {
               targetType = findGroundTargetType(context, lambda.enclosingScope, lambda, withWildCards);
            }

            if (targetType == null) {
               return EMPTY_VARIABLE_LIST;
            }

            MethodBinding sam = targetType.getSingleAbstractMethod(context.scope, true);
            Set variables = new HashSet();
            if (lambda.argumentsTypeElided()) {
               int len = sam.parameters.length;

               for(int i = 0; i < len; ++i) {
                  sam.parameters[i].collectInferenceVariables(variables);
               }
            }

            if (sam.returnType != TypeBinding.VOID) {
               TypeBinding r = sam.returnType;
               LambdaExpression resolved = lambda.resolveExpressionExpecting(this.right, context.scope, context);
               Expression[] resultExpressions = resolved != null ? resolved.resultExpressions() : null;
               int i = 0;

               for(int length = resultExpressions == null ? 0 : resultExpressions.length; i < length; ++i) {
                  variables.addAll((new ConstraintExpressionFormula(resultExpressions[i], r, 1)).inputVariables(context));
               }
            }

            return variables;
         }
      } else {
         HashSet variables;
         if (this.left instanceof ReferenceExpression) {
            if (this.right instanceof InferenceVariable) {
               return Collections.singletonList((InferenceVariable)this.right);
            }

            if (this.right.isFunctionalInterface(context.scope) && !this.left.isExactMethodReference()) {
               MethodBinding sam = this.right.getSingleAbstractMethod(context.scope, true);
               variables = new HashSet();
               int len = sam.parameters.length;

               for(int i = 0; i < len; ++i) {
                  sam.parameters[i].collectInferenceVariables(variables);
               }

               return variables;
            }
         } else {
            if (this.left instanceof ConditionalExpression && this.left.isPolyExpression()) {
               ConditionalExpression expr = (ConditionalExpression)this.left;
               variables = new HashSet();
               variables.addAll((new ConstraintExpressionFormula(expr.valueIfTrue, this.right, 1)).inputVariables(context));
               variables.addAll((new ConstraintExpressionFormula(expr.valueIfFalse, this.right, 1)).inputVariables(context));
               return variables;
            }

            if (this.left instanceof SwitchExpression && this.left.isPolyExpression()) {
               SwitchExpression expr = (SwitchExpression)this.left;
               variables = new HashSet();
               Iterator var19 = expr.resultExpressions.iterator();

               while(var19.hasNext()) {
                  Expression re = (Expression)var19.next();
                  variables.addAll((new ConstraintExpressionFormula(re, this.right, 1)).inputVariables(context));
               }

               return variables;
            }
         }
      }

      return EMPTY_VARIABLE_LIST;
   }

   public String toString() {
      StringBuffer buf = (new StringBuffer()).append('⟨');
      this.left.printExpression(4, buf);
      buf.append(relationToString(this.relation));
      this.appendTypeName(buf, this.right);
      buf.append('⟩');
      return buf.toString();
   }
}

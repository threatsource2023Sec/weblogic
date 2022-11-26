package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Argument;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Expression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.MethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.NullAnnotationMatching;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImplicitNullAnnotationVerifier {
   ImplicitNullAnnotationVerifier buddyImplicitNullAnnotationsVerifier;
   private boolean inheritNullAnnotations;
   protected LookupEnvironment environment;

   public static void ensureNullnessIsKnown(MethodBinding methodBinding, Scope scope) {
      if ((methodBinding.tagBits & 4096L) == 0L) {
         LookupEnvironment environment2 = scope.environment();
         (new ImplicitNullAnnotationVerifier(environment2, environment2.globalOptions.inheritNullAnnotations)).checkImplicitNullAnnotations(methodBinding, (AbstractMethodDeclaration)null, false, scope);
      }

   }

   public ImplicitNullAnnotationVerifier(LookupEnvironment environment, boolean inheritNullAnnotations) {
      this.buddyImplicitNullAnnotationsVerifier = this;
      this.inheritNullAnnotations = inheritNullAnnotations;
      this.environment = environment;
   }

   ImplicitNullAnnotationVerifier(LookupEnvironment environment) {
      CompilerOptions options = environment.globalOptions;
      this.buddyImplicitNullAnnotationsVerifier = new ImplicitNullAnnotationVerifier(environment, options.inheritNullAnnotations);
      this.inheritNullAnnotations = options.inheritNullAnnotations;
      this.environment = environment;
   }

   public void checkImplicitNullAnnotations(MethodBinding currentMethod, AbstractMethodDeclaration srcMethod, boolean complain, Scope scope) {
      try {
         ReferenceBinding currentType = currentMethod.declaringClass;
         if (currentType.id == 1) {
            return;
         }

         boolean usesTypeAnnotations = scope.environment().usesNullTypeAnnotations();
         boolean needToApplyReturnNonNullDefault = currentMethod.hasNonNullDefaultForReturnType(srcMethod);
         ParameterNonNullDefaultProvider needToApplyParameterNonNullDefault = currentMethod.hasNonNullDefaultForParameter(srcMethod);
         boolean needToApplyNonNullDefault = needToApplyReturnNonNullDefault | needToApplyParameterNonNullDefault.hasAnyNonNullDefault();
         boolean isInstanceMethod = !currentMethod.isConstructor() && !currentMethod.isStatic();
         complain &= isInstanceMethod;
         if (!needToApplyNonNullDefault && !complain && (!this.inheritNullAnnotations || !isInstanceMethod)) {
            return;
         }

         if (isInstanceMethod) {
            List superMethodList = new ArrayList();
            if (currentType instanceof SourceTypeBinding && !currentType.isHierarchyConnected() && !currentType.isAnonymousType()) {
               ((SourceTypeBinding)currentType).scope.connectTypeHierarchy();
            }

            int paramLen = currentMethod.parameters.length;
            this.findAllOverriddenMethods(currentMethod.original(), currentMethod.selector, paramLen, currentType, new HashSet(), superMethodList);
            InheritedNonNullnessInfo[] inheritedNonNullnessInfos = new InheritedNonNullnessInfo[paramLen + 1];

            int length;
            for(length = 0; length < paramLen + 1; ++length) {
               inheritedNonNullnessInfos[length] = new InheritedNonNullnessInfo();
            }

            length = superMethodList.size();
            int i = length;

            while(true) {
               --i;
               if (i < 0) {
                  InheritedNonNullnessInfo info = inheritedNonNullnessInfos[0];
                  if (!info.complained) {
                     long tagBits = 0L;
                     if (info.inheritedNonNullness == Boolean.TRUE) {
                        tagBits = 72057594037927936L;
                     } else if (info.inheritedNonNullness == Boolean.FALSE) {
                        tagBits = 36028797018963968L;
                     }

                     if (tagBits != 0L) {
                        if (!usesTypeAnnotations) {
                           currentMethod.tagBits |= tagBits;
                        } else if (!currentMethod.returnType.isBaseType()) {
                           LookupEnvironment env = scope.environment();
                           currentMethod.returnType = env.createAnnotatedType(currentMethod.returnType, env.nullAnnotationsFromTagBits(tagBits));
                        }
                     }
                  }

                  for(int i = 0; i < paramLen; ++i) {
                     info = inheritedNonNullnessInfos[i + 1];
                     if (!info.complained && info.inheritedNonNullness != null) {
                        Argument currentArg = srcMethod == null ? null : srcMethod.arguments[i];
                        if (!usesTypeAnnotations) {
                           this.recordArgNonNullness(currentMethod, paramLen, i, currentArg, info.inheritedNonNullness);
                        } else {
                           this.recordArgNonNullness18(currentMethod, i, currentArg, info.inheritedNonNullness, scope.environment());
                        }
                     }
                  }
                  break;
               }

               MethodBinding currentSuper = (MethodBinding)superMethodList.get(i);
               if ((currentSuper.tagBits & 4096L) == 0L) {
                  this.checkImplicitNullAnnotations(currentSuper, (AbstractMethodDeclaration)null, false, scope);
               }

               this.checkNullSpecInheritance(currentMethod, srcMethod, needToApplyReturnNonNullDefault, needToApplyParameterNonNullDefault, complain, currentSuper, (MethodBinding[])null, scope, inheritedNonNullnessInfos);
               needToApplyNonNullDefault = false;
            }
         }

         if (needToApplyNonNullDefault) {
            if (!usesTypeAnnotations) {
               currentMethod.fillInDefaultNonNullness(srcMethod, needToApplyReturnNonNullDefault, needToApplyParameterNonNullDefault);
            } else {
               currentMethod.fillInDefaultNonNullness18(srcMethod, scope.environment());
            }
         }
      } finally {
         currentMethod.tagBits |= 4096L;
      }

   }

   private void findAllOverriddenMethods(MethodBinding original, char[] selector, int suggestedParameterLength, ReferenceBinding currentType, Set ifcsSeen, List result) {
      if (currentType.id != 1) {
         ReferenceBinding superclass = currentType.superclass();
         if (superclass != null) {
            this.collectOverriddenMethods(original, selector, suggestedParameterLength, superclass, ifcsSeen, result);
            ReferenceBinding[] superInterfaces = currentType.superInterfaces();
            int ifcLen = superInterfaces.length;

            for(int i = 0; i < ifcLen; ++i) {
               ReferenceBinding currentIfc = superInterfaces[i];
               if (ifcsSeen.add(currentIfc.original())) {
                  this.collectOverriddenMethods(original, selector, suggestedParameterLength, currentIfc, ifcsSeen, result);
               }
            }

         }
      }
   }

   private void collectOverriddenMethods(MethodBinding original, char[] selector, int suggestedParameterLength, ReferenceBinding superType, Set ifcsSeen, List result) {
      MethodBinding[] ifcMethods = superType.unResolvedMethods();
      int length = ifcMethods.length;
      boolean added = false;

      for(int i = 0; i < length; ++i) {
         MethodBinding currentMethod = ifcMethods[i];
         if (CharOperation.equals(selector, currentMethod.selector) && currentMethod.doesParameterLengthMatch(suggestedParameterLength) && !currentMethod.isStatic() && MethodVerifier.doesMethodOverride(original, currentMethod, this.environment)) {
            result.add(currentMethod);
            added = true;
         }
      }

      if (!added) {
         this.findAllOverriddenMethods(original, selector, suggestedParameterLength, superType, ifcsSeen, result);
      }

   }

   void checkNullSpecInheritance(MethodBinding currentMethod, AbstractMethodDeclaration srcMethod, boolean hasReturnNonNullDefault, ParameterNonNullDefaultProvider hasParameterNonNullDefault, boolean shouldComplain, MethodBinding inheritedMethod, MethodBinding[] allInheritedMethods, Scope scope, InheritedNonNullnessInfo[] inheritedNonNullnessInfos) {
      if (currentMethod.declaringClass.id != 1) {
         if ((inheritedMethod.tagBits & 4096L) == 0L) {
            this.buddyImplicitNullAnnotationsVerifier.checkImplicitNullAnnotations(inheritedMethod, (AbstractMethodDeclaration)null, false, scope);
         }

         boolean useTypeAnnotations = this.environment.usesNullTypeAnnotations();
         long inheritedNullnessBits = this.getReturnTypeNullnessTagBits(inheritedMethod, useTypeAnnotations);
         long currentNullnessBits = this.getReturnTypeNullnessTagBits(currentMethod, useTypeAnnotations);
         boolean shouldInherit = this.inheritNullAnnotations;
         TypeVariableBinding[] typeVariables;
         ParameterizedGenericMethodBinding substitute;
         if (currentMethod.returnType != null && !currentMethod.returnType.isBaseType()) {
            label248: {
               if (currentNullnessBits == 0L) {
                  if (shouldInherit && inheritedNullnessBits != 0L) {
                     if (hasReturnNonNullDefault && shouldComplain && inheritedNullnessBits == 36028797018963968L) {
                        scope.problemReporter().conflictingNullAnnotations(currentMethod, ((MethodDeclaration)srcMethod).returnType, inheritedMethod);
                     }

                     if (inheritedNonNullnessInfos != null && srcMethod != null) {
                        this.recordDeferredInheritedNullness(scope, ((MethodDeclaration)srcMethod).returnType, inheritedMethod, inheritedNullnessBits == 72057594037927936L, inheritedNonNullnessInfos[0]);
                     } else {
                        this.applyReturnNullBits(currentMethod, inheritedNullnessBits);
                     }
                     break label248;
                  }

                  if (hasReturnNonNullDefault && (!useTypeAnnotations || currentMethod.returnType.acceptsNonNullDefault())) {
                     currentNullnessBits = 72057594037927936L;
                     this.applyReturnNullBits(currentMethod, currentNullnessBits);
                  }
               }

               if (shouldComplain) {
                  if ((inheritedNullnessBits & 72057594037927936L) != 0L && currentNullnessBits != 72057594037927936L) {
                     if (srcMethod == null) {
                        scope.problemReporter().cannotImplementIncompatibleNullness(scope.referenceContext(), currentMethod, inheritedMethod, useTypeAnnotations);
                        return;
                     }

                     scope.problemReporter().illegalReturnRedefinition(srcMethod, inheritedMethod, this.environment.getNonNullAnnotationName());
                  } else if (useTypeAnnotations) {
                     TypeBinding substituteReturnType = null;
                     typeVariables = inheritedMethod.original().typeVariables;
                     if (typeVariables != null && currentMethod.returnType.id != 6) {
                        substitute = this.environment.createParameterizedGenericMethod(currentMethod, (TypeBinding[])typeVariables);
                        substituteReturnType = substitute.returnType;
                     }

                     if (NullAnnotationMatching.analyse(inheritedMethod.returnType, currentMethod.returnType, substituteReturnType, (Substitution)null, 0, (Expression)null, NullAnnotationMatching.CheckMode.OVERRIDE_RETURN).isAnyMismatch()) {
                        if (srcMethod != null) {
                           scope.problemReporter().illegalReturnRedefinition(srcMethod, inheritedMethod, this.environment.getNonNullAnnotationName());
                        } else {
                           scope.problemReporter().cannotImplementIncompatibleNullness(scope.referenceContext(), currentMethod, inheritedMethod, useTypeAnnotations);
                        }

                        return;
                     }
                  }
               }
            }
         }

         TypeBinding[] substituteParameters = null;
         if (shouldComplain) {
            typeVariables = currentMethod.original().typeVariables;
            if (typeVariables != Binding.NO_TYPE_VARIABLES) {
               substitute = this.environment.createParameterizedGenericMethod(inheritedMethod, (TypeBinding[])typeVariables);
               substituteParameters = substitute.parameters;
            }
         }

         Argument[] currentArguments = srcMethod == null ? null : srcMethod.arguments;
         int length = 0;
         if (currentArguments != null) {
            length = currentArguments.length;
         }

         if (useTypeAnnotations) {
            length = currentMethod.parameters.length;
         } else if (inheritedMethod.parameterNonNullness != null) {
            length = inheritedMethod.parameterNonNullness.length;
         } else if (currentMethod.parameterNonNullness != null) {
            length = currentMethod.parameterNonNullness.length;
         }

         label226:
         for(int i = 0; i < length; ++i) {
            if (!currentMethod.parameters[i].isBaseType()) {
               Argument currentArgument = currentArguments == null ? null : currentArguments[i];
               Boolean inheritedNonNullNess = this.getParameterNonNullness(inheritedMethod, i, useTypeAnnotations);
               Boolean currentNonNullNess = this.getParameterNonNullness(currentMethod, i, useTypeAnnotations);
               if (currentNonNullNess == null) {
                  if (inheritedNonNullNess != null && shouldInherit) {
                     if (hasParameterNonNullDefault.hasNonNullDefaultForParam(i) && shouldComplain && inheritedNonNullNess == Boolean.FALSE && currentArgument != null) {
                        scope.problemReporter().conflictingNullAnnotations(currentMethod, currentArgument, inheritedMethod);
                     }

                     if (inheritedNonNullnessInfos != null && srcMethod != null) {
                        this.recordDeferredInheritedNullness(scope, srcMethod.arguments[i].type, inheritedMethod, inheritedNonNullNess, inheritedNonNullnessInfos[i + 1]);
                        continue;
                     }

                     if (!useTypeAnnotations) {
                        this.recordArgNonNullness(currentMethod, length, i, currentArgument, inheritedNonNullNess);
                     } else {
                        this.recordArgNonNullness18(currentMethod, i, currentArgument, inheritedNonNullNess, this.environment);
                     }
                     continue;
                  }

                  if (hasParameterNonNullDefault.hasNonNullDefaultForParam(i)) {
                     currentNonNullNess = Boolean.TRUE;
                     if (!useTypeAnnotations) {
                        this.recordArgNonNullness(currentMethod, length, i, currentArgument, Boolean.TRUE);
                     } else if (currentMethod.parameters[i].acceptsNonNullDefault()) {
                        this.recordArgNonNullness18(currentMethod, i, currentArgument, Boolean.TRUE, this.environment);
                     } else {
                        currentNonNullNess = null;
                     }
                  }
               }

               if (shouldComplain) {
                  char[][] annotationName;
                  if (inheritedNonNullNess == Boolean.TRUE) {
                     annotationName = this.environment.getNonNullAnnotationName();
                  } else {
                     annotationName = this.environment.getNullableAnnotationName();
                  }

                  if (inheritedNonNullNess != Boolean.TRUE && currentNonNullNess == Boolean.TRUE) {
                     if (currentArgument != null) {
                        scope.problemReporter().illegalRedefinitionToNonNullParameter(currentArgument, inheritedMethod.declaringClass, inheritedNonNullNess == null ? null : this.environment.getNullableAnnotationName());
                     } else {
                        scope.problemReporter().cannotImplementIncompatibleNullness(scope.referenceContext(), currentMethod, inheritedMethod, false);
                     }
                  } else {
                     if (currentNonNullNess == null) {
                        if (inheritedNonNullNess == Boolean.FALSE) {
                           if (currentArgument != null) {
                              scope.problemReporter().parameterLackingNullableAnnotation(currentArgument, inheritedMethod.declaringClass, annotationName);
                           } else {
                              scope.problemReporter().cannotImplementIncompatibleNullness(scope.referenceContext(), currentMethod, inheritedMethod, false);
                           }
                           continue;
                        }

                        if (inheritedNonNullNess == Boolean.TRUE) {
                           if (allInheritedMethods != null) {
                              MethodBinding[] var27 = allInheritedMethods;
                              int var26 = allInheritedMethods.length;

                              for(int var36 = 0; var36 < var26; ++var36) {
                                 MethodBinding one = var27[var36];
                                 if (TypeBinding.equalsEquals(inheritedMethod.declaringClass, one.declaringClass) && this.getParameterNonNullness(one, i, useTypeAnnotations) != Boolean.TRUE) {
                                    continue label226;
                                 }
                              }
                           }

                           scope.problemReporter().parameterLackingNonnullAnnotation(currentArgument, inheritedMethod.declaringClass, annotationName);
                           continue;
                        }
                     }

                     if (useTypeAnnotations) {
                        TypeBinding inheritedParameter = inheritedMethod.parameters[i];
                        TypeBinding substituteParameter = substituteParameters != null ? substituteParameters[i] : null;
                        if (NullAnnotationMatching.analyse(currentMethod.parameters[i], inheritedParameter, substituteParameter, (Substitution)null, 0, (Expression)null, NullAnnotationMatching.CheckMode.OVERRIDE).isAnyMismatch()) {
                           if (currentArgument != null) {
                              scope.problemReporter().illegalParameterRedefinition(currentArgument, inheritedMethod.declaringClass, inheritedParameter);
                           } else {
                              scope.problemReporter().cannotImplementIncompatibleNullness(scope.referenceContext(), currentMethod, inheritedMethod, false);
                           }
                        }
                     }
                  }
               }
            }
         }

         if (shouldComplain && useTypeAnnotations && srcMethod != null) {
            TypeVariableBinding[] currentTypeVariables = currentMethod.typeVariables();
            TypeVariableBinding[] inheritedTypeVariables = inheritedMethod.typeVariables();
            if (currentTypeVariables != Binding.NO_TYPE_VARIABLES && currentTypeVariables.length == inheritedTypeVariables.length) {
               for(int i = 0; i < currentTypeVariables.length; ++i) {
                  TypeVariableBinding inheritedVariable = inheritedTypeVariables[i];
                  if (NullAnnotationMatching.analyse(inheritedVariable, currentTypeVariables[i], (TypeBinding)null, (Substitution)null, -1, (Expression)null, NullAnnotationMatching.CheckMode.BOUND_CHECK).isAnyMismatch()) {
                     scope.problemReporter().cannotRedefineTypeArgumentNullity(inheritedVariable, inheritedMethod, srcMethod.typeParameters()[i]);
                  }
               }
            }
         }

      }
   }

   void applyReturnNullBits(MethodBinding method, long nullnessBits) {
      if (this.environment.usesNullTypeAnnotations()) {
         if (!method.returnType.isBaseType()) {
            method.returnType = this.environment.createAnnotatedType(method.returnType, this.environment.nullAnnotationsFromTagBits(nullnessBits));
         }
      } else {
         method.tagBits |= nullnessBits;
      }

   }

   private Boolean getParameterNonNullness(MethodBinding method, int i, boolean useTypeAnnotations) {
      if (useTypeAnnotations) {
         TypeBinding parameter = method.parameters[i];
         if (parameter != null) {
            long nullBits = NullAnnotationMatching.validNullTagBits(parameter.tagBits);
            if (nullBits != 0L) {
               return nullBits == 72057594037927936L;
            }
         }

         return null;
      } else {
         return method.parameterNonNullness == null ? null : method.parameterNonNullness[i];
      }
   }

   private long getReturnTypeNullnessTagBits(MethodBinding method, boolean useTypeAnnotations) {
      if (useTypeAnnotations) {
         return method.returnType == null ? 0L : NullAnnotationMatching.validNullTagBits(method.returnType.tagBits);
      } else {
         return method.tagBits & 108086391056891904L;
      }
   }

   protected void recordDeferredInheritedNullness(Scope scope, ASTNode location, MethodBinding inheritedMethod, Boolean inheritedNonNullness, InheritedNonNullnessInfo nullnessInfo) {
      if (nullnessInfo.inheritedNonNullness != null && nullnessInfo.inheritedNonNullness != inheritedNonNullness) {
         scope.problemReporter().conflictingInheritedNullAnnotations(location, nullnessInfo.inheritedNonNullness, nullnessInfo.annotationOrigin, inheritedNonNullness, inheritedMethod);
         nullnessInfo.complained = true;
      } else {
         nullnessInfo.inheritedNonNullness = inheritedNonNullness;
         nullnessInfo.annotationOrigin = inheritedMethod;
      }

   }

   void recordArgNonNullness(MethodBinding method, int paramCount, int paramIdx, Argument currentArgument, Boolean nonNullNess) {
      if (method.parameterNonNullness == null) {
         method.parameterNonNullness = new Boolean[paramCount];
      }

      method.parameterNonNullness[paramIdx] = nonNullNess;
      if (currentArgument != null) {
         LocalVariableBinding var10000 = currentArgument.binding;
         var10000.tagBits |= nonNullNess ? 72057594037927936L : 36028797018963968L;
      }

   }

   void recordArgNonNullness18(MethodBinding method, int paramIdx, Argument currentArgument, Boolean nonNullNess, LookupEnvironment env) {
      AnnotationBinding annotationBinding = nonNullNess ? env.getNonNullAnnotation() : env.getNullableAnnotation();
      method.parameters[paramIdx] = env.createAnnotatedType(method.parameters[paramIdx], new AnnotationBinding[]{annotationBinding});
      if (currentArgument != null) {
         currentArgument.binding.type = method.parameters[paramIdx];
      }

   }

   static boolean areParametersEqual(MethodBinding one, MethodBinding two) {
      TypeBinding[] oneArgs = one.parameters;
      TypeBinding[] twoArgs = two.parameters;
      if (oneArgs == twoArgs) {
         return true;
      } else {
         int length = oneArgs.length;
         if (length != twoArgs.length) {
            return false;
         } else {
            int i = 0;

            while(true) {
               if (i < length) {
                  if (areTypesEqual(oneArgs[i], twoArgs[i])) {
                     ++i;
                     continue;
                  }

                  if (!oneArgs[i].leafComponentType().isRawType() || oneArgs[i].dimensions() != twoArgs[i].dimensions() || !oneArgs[i].leafComponentType().isEquivalentTo(twoArgs[i].leafComponentType())) {
                     return false;
                  }

                  if (one.typeVariables != Binding.NO_TYPE_VARIABLES) {
                     return false;
                  }

                  for(int j = 0; j < i; ++j) {
                     if (oneArgs[j].leafComponentType().isParameterizedTypeWithActualArguments()) {
                        return false;
                     }
                  }
               }

               ++i;

               for(; i < length; ++i) {
                  if (!areTypesEqual(oneArgs[i], twoArgs[i])) {
                     if (!oneArgs[i].leafComponentType().isRawType() || oneArgs[i].dimensions() != twoArgs[i].dimensions() || !oneArgs[i].leafComponentType().isEquivalentTo(twoArgs[i].leafComponentType())) {
                        return false;
                     }
                  } else if (oneArgs[i].leafComponentType().isParameterizedTypeWithActualArguments()) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   static boolean areTypesEqual(TypeBinding one, TypeBinding two) {
      if (TypeBinding.equalsEquals(one, two)) {
         return true;
      } else {
         label35:
         switch (one.kind()) {
            case 4:
               switch (two.kind()) {
                  case 260:
                  case 1028:
                     if (TypeBinding.equalsEquals(one, two.erasure())) {
                        return true;
                     }
                  default:
                     break label35;
               }
            case 260:
            case 1028:
               switch (two.kind()) {
                  case 4:
                     if (TypeBinding.equalsEquals(one.erasure(), two)) {
                        return true;
                     }
               }
         }

         if (one.isParameterizedType() && two.isParameterizedType()) {
            return one.isEquivalentTo(two) && two.isEquivalentTo(one);
         } else {
            return false;
         }
      }
   }

   static class InheritedNonNullnessInfo {
      Boolean inheritedNonNullness;
      MethodBinding annotationOrigin;
      boolean complained;
   }
}

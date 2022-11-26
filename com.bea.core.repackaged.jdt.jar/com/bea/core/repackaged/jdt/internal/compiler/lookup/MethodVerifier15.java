package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Argument;
import com.bea.core.repackaged.jdt.internal.compiler.ast.MethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeParameter;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.util.HashtableOfObject;
import com.bea.core.repackaged.jdt.internal.compiler.util.SimpleSet;
import com.bea.core.repackaged.jdt.internal.compiler.util.Sorting;
import java.util.Arrays;

class MethodVerifier15 extends MethodVerifier {
   MethodVerifier15(LookupEnvironment environment) {
      super(environment);
   }

   protected boolean canOverridingMethodDifferInErasure(MethodBinding overridingMethod, MethodBinding inheritedMethod) {
      if (overridingMethod.areParameterErasuresEqual(inheritedMethod)) {
         return false;
      } else {
         return !overridingMethod.declaringClass.isRawType();
      }
   }

   boolean canSkipInheritedMethods() {
      if (this.type.superclass() == null || !this.type.superclass().isAbstract() && !this.type.superclass().isParameterizedType()) {
         return this.type.superInterfaces() == Binding.NO_SUPERINTERFACES;
      } else {
         return false;
      }
   }

   boolean canSkipInheritedMethods(MethodBinding one, MethodBinding two) {
      return two == null || TypeBinding.equalsEquals(one.declaringClass, two.declaringClass) && !one.declaringClass.isParameterizedType();
   }

   void checkConcreteInheritedMethod(MethodBinding concreteMethod, MethodBinding[] abstractMethods) {
      super.checkConcreteInheritedMethod(concreteMethod, abstractMethods);
      boolean analyseNullAnnotations = this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled;
      AbstractMethodDeclaration srcMethod = null;
      if (analyseNullAnnotations && this.type.equals(concreteMethod.declaringClass)) {
         srcMethod = concreteMethod.sourceMethod();
      }

      boolean hasReturnNonNullDefault = analyseNullAnnotations && concreteMethod.hasNonNullDefaultForReturnType(srcMethod);
      ParameterNonNullDefaultProvider hasParameterNonNullDefault = analyseNullAnnotations ? concreteMethod.hasNonNullDefaultForParameter(srcMethod) : ParameterNonNullDefaultProvider.FALSE_PROVIDER;
      int i = 0;

      for(int l = abstractMethods.length; i < l; ++i) {
         MethodBinding abstractMethod = abstractMethods[i];
         if (concreteMethod.isVarargs() != abstractMethod.isVarargs()) {
            this.problemReporter().varargsConflict(concreteMethod, abstractMethod, this.type);
         }

         MethodBinding originalInherited = abstractMethod.original();
         if (TypeBinding.notEquals(originalInherited.returnType, concreteMethod.returnType) && !this.isAcceptableReturnTypeOverride(concreteMethod, abstractMethod)) {
            this.problemReporter().unsafeReturnTypeOverride(concreteMethod, originalInherited, this.type);
         }

         if (originalInherited.declaringClass.isInterface() && (TypeBinding.equalsEquals(concreteMethod.declaringClass, this.type.superclass) && this.type.superclass.isParameterizedType() && !this.areMethodsCompatible(concreteMethod, originalInherited) || this.type.superclass.erasure().findSuperTypeOriginatingFrom(originalInherited.declaringClass) == null)) {
            this.type.addSyntheticBridgeMethod(originalInherited, concreteMethod.original());
         }

         if (analyseNullAnnotations && !concreteMethod.isStatic() && !abstractMethod.isStatic()) {
            this.checkNullSpecInheritance(concreteMethod, srcMethod, hasReturnNonNullDefault, hasParameterNonNullDefault, true, abstractMethod, abstractMethods, this.type.scope, (ImplicitNullAnnotationVerifier.InheritedNonNullnessInfo[])null);
         }
      }

   }

   void checkForBridgeMethod(MethodBinding currentMethod, MethodBinding inheritedMethod, MethodBinding[] allInheritedMethods) {
      if (currentMethod.isVarargs() != inheritedMethod.isVarargs()) {
         this.problemReporter(currentMethod).varargsConflict(currentMethod, inheritedMethod, this.type);
      }

      MethodBinding originalInherited = inheritedMethod.original();
      if (TypeBinding.notEquals(originalInherited.returnType, currentMethod.returnType) && !this.isAcceptableReturnTypeOverride(currentMethod, inheritedMethod)) {
         this.problemReporter(currentMethod).unsafeReturnTypeOverride(currentMethod, originalInherited, this.type);
      }

      MethodBinding bridge = this.type.addSyntheticBridgeMethod(originalInherited, currentMethod.original());
      if (bridge != null) {
         int i = 0;

         int i;
         for(i = allInheritedMethods == null ? 0 : allInheritedMethods.length; i < i; ++i) {
            if (allInheritedMethods[i] != null && this.detectInheritedNameClash(originalInherited, allInheritedMethods[i].original())) {
               return;
            }
         }

         MethodBinding[] current = (MethodBinding[])this.currentMethods.get(bridge.selector);

         for(i = current.length - 1; i >= 0; --i) {
            MethodBinding thisMethod = current[i];
            if (thisMethod.areParameterErasuresEqual(bridge) && TypeBinding.equalsEquals(thisMethod.returnType.erasure(), bridge.returnType.erasure())) {
               this.problemReporter(thisMethod).methodNameClash(thisMethod, inheritedMethod.declaringClass.isRawType() ? inheritedMethod : inheritedMethod.original(), 1);
               return;
            }
         }
      }

   }

   void checkForNameClash(MethodBinding currentMethod, MethodBinding inheritedMethod) {
      if (!inheritedMethod.isStatic() && !currentMethod.isStatic()) {
         if (!this.detectNameClash(currentMethod, inheritedMethod, false)) {
            TypeBinding[] currentParams = currentMethod.parameters;
            TypeBinding[] inheritedParams = inheritedMethod.parameters;
            int length = currentParams.length;
            if (length == inheritedParams.length) {
               for(int i = 0; i < length; ++i) {
                  if (TypeBinding.notEquals(currentParams[i], inheritedParams[i]) && (currentParams[i].isBaseType() != inheritedParams[i].isBaseType() || !inheritedParams[i].isCompatibleWith(currentParams[i]))) {
                     return;
                  }
               }

               ReferenceBinding[] interfacesToVisit = null;
               int nextPosition = 0;
               ReferenceBinding superType = inheritedMethod.declaringClass;
               ReferenceBinding[] itsInterfaces = superType.superInterfaces();
               if (itsInterfaces != Binding.NO_SUPERINTERFACES) {
                  nextPosition = itsInterfaces.length;
                  interfacesToVisit = itsInterfaces;
               }

               int itsLength;
               for(superType = superType.superclass(); superType != null && superType.isValidBinding(); superType = superType.superclass()) {
                  MethodBinding[] methods = superType.getMethods(currentMethod.selector);
                  int itsLength = 0;

                  for(itsLength = methods.length; itsLength < itsLength; ++itsLength) {
                     MethodBinding substitute = this.computeSubstituteMethod(methods[itsLength], currentMethod);
                     if (substitute != null && !this.isSubstituteParameterSubsignature(currentMethod, substitute) && this.detectNameClash(currentMethod, substitute, true)) {
                        return;
                     }
                  }

                  if ((itsInterfaces = superType.superInterfaces()) != Binding.NO_SUPERINTERFACES) {
                     if (interfacesToVisit == null) {
                        interfacesToVisit = itsInterfaces;
                        nextPosition = itsInterfaces.length;
                     } else {
                        itsLength = itsInterfaces.length;
                        if (nextPosition + itsLength >= interfacesToVisit.length) {
                           System.arraycopy(interfacesToVisit, 0, interfacesToVisit = new ReferenceBinding[nextPosition + itsLength + 5], 0, nextPosition);
                        }

                        label150:
                        for(itsLength = 0; itsLength < itsLength; ++itsLength) {
                           ReferenceBinding next = itsInterfaces[itsLength];

                           for(int b = 0; b < nextPosition; ++b) {
                              if (TypeBinding.equalsEquals(next, interfacesToVisit[b])) {
                                 continue label150;
                              }
                           }

                           interfacesToVisit[nextPosition++] = next;
                        }
                     }
                  }
               }

               for(int i = 0; i < nextPosition; ++i) {
                  superType = interfacesToVisit[i];
                  if (superType.isValidBinding()) {
                     MethodBinding[] methods = superType.getMethods(currentMethod.selector);
                     itsLength = 0;

                     int a;
                     for(a = methods.length; itsLength < a; ++itsLength) {
                        MethodBinding substitute = this.computeSubstituteMethod(methods[itsLength], currentMethod);
                        if (substitute != null && !this.isSubstituteParameterSubsignature(currentMethod, substitute) && this.detectNameClash(currentMethod, substitute, true)) {
                           return;
                        }
                     }

                     if ((itsInterfaces = superType.superInterfaces()) != Binding.NO_SUPERINTERFACES) {
                        itsLength = itsInterfaces.length;
                        if (nextPosition + itsLength >= interfacesToVisit.length) {
                           System.arraycopy(interfacesToVisit, 0, interfacesToVisit = new ReferenceBinding[nextPosition + itsLength + 5], 0, nextPosition);
                        }

                        label113:
                        for(a = 0; a < itsLength; ++a) {
                           ReferenceBinding next = itsInterfaces[a];

                           for(int b = 0; b < nextPosition; ++b) {
                              if (TypeBinding.equalsEquals(next, interfacesToVisit[b])) {
                                 continue label113;
                              }
                           }

                           interfacesToVisit[nextPosition++] = next;
                        }
                     }
                  }
               }

            }
         }
      } else {
         MethodBinding original = inheritedMethod.original();
         if (this.type.scope.compilerOptions().complianceLevel >= 3342336L && currentMethod.areParameterErasuresEqual(original)) {
            this.problemReporter(currentMethod).methodNameClashHidden(currentMethod, inheritedMethod.declaringClass.isRawType() ? inheritedMethod : original);
         }

      }
   }

   void checkInheritedMethods(MethodBinding inheritedMethod, MethodBinding otherInheritedMethod) {
      if (!inheritedMethod.isStatic()) {
         if (this.environment.globalOptions.complianceLevel >= 3342336L || !inheritedMethod.declaringClass.isInterface()) {
            this.detectInheritedNameClash(inheritedMethod.original(), otherInheritedMethod.original());
         }
      }
   }

   void checkInheritedMethods(MethodBinding[] methods, int length, boolean[] isOverridden, boolean[] isInherited) {
      boolean continueInvestigation = true;
      MethodBinding concreteMethod = null;
      MethodBinding abstractSuperClassMethod = null;
      boolean playingTrump = false;

      int i;
      for(i = 0; i < length; ++i) {
         if (!methods[i].declaringClass.isInterface() && TypeBinding.notEquals(methods[i].declaringClass, this.type) && methods[i].isAbstract()) {
            abstractSuperClassMethod = methods[i];
            break;
         }
      }

      for(i = 0; i < length; ++i) {
         if (isInherited[i] && !methods[i].isAbstract()) {
            if (methods[i].isDefaultMethod() && abstractSuperClassMethod != null && areParametersEqual(abstractSuperClassMethod, methods[i]) && concreteMethod == null) {
               playingTrump = true;
            } else {
               playingTrump = false;
               if (concreteMethod != null) {
                  if (isOverridden[i] && this.areMethodsCompatible(concreteMethod, methods[i]) || TypeBinding.equalsEquals(concreteMethod.declaringClass, methods[i].declaringClass) && concreteMethod.typeVariables.length != methods[i].typeVariables.length && (concreteMethod.typeVariables == Binding.NO_TYPE_VARIABLES && concreteMethod.original() == methods[i] || methods[i].typeVariables == Binding.NO_TYPE_VARIABLES && methods[i].original() == concreteMethod)) {
                     continue;
                  }

                  this.problemReporter().duplicateInheritedMethods(this.type, concreteMethod, methods[i], this.environment.globalOptions.sourceLevel >= 3407872L);
                  continueInvestigation = false;
               }

               concreteMethod = methods[i];
            }
         }
      }

      if (continueInvestigation) {
         if (playingTrump) {
            if (!this.type.isAbstract()) {
               this.problemReporter().abstractMethodMustBeImplemented(this.type, abstractSuperClassMethod);
               return;
            }
         } else if (concreteMethod != null && concreteMethod.isDefaultMethod() && this.environment.globalOptions.complianceLevel >= 3407872L && !this.checkInheritedDefaultMethods(methods, isOverridden, length)) {
            return;
         }

         super.checkInheritedMethods(methods, length, isOverridden, isInherited);
      }

   }

   boolean checkInheritedDefaultMethods(MethodBinding[] methods, boolean[] isOverridden, int length) {
      if (length < 2) {
         return true;
      } else {
         boolean ok = true;

         for(int i = 0; i < length; ++i) {
            if (methods[i].isDefaultMethod() && !isOverridden[i]) {
               for(int j = 0; j < length; ++j) {
                  if (j != i && !isOverridden[j] && this.isMethodSubsignature(methods[i], methods[j]) && !this.doesMethodOverride(methods[i], methods[j]) && !this.doesMethodOverride(methods[j], methods[i])) {
                     this.problemReporter().inheritedDefaultMethodConflictsWithOtherInherited(this.type, methods[i], methods[j]);
                     ok = false;
                     break;
                  }
               }
            }
         }

         return ok;
      }
   }

   boolean checkInheritedReturnTypes(MethodBinding method, MethodBinding otherMethod) {
      if (this.areReturnTypesCompatible(method, otherMethod)) {
         return true;
      } else if (this.isUnsafeReturnTypeOverride(method, otherMethod)) {
         if (!method.declaringClass.implementsInterface(otherMethod.declaringClass, false)) {
            this.problemReporter(method).unsafeReturnTypeOverride(method, otherMethod, this.type);
         }

         return true;
      } else {
         return false;
      }
   }

   void checkAgainstInheritedMethods(MethodBinding currentMethod, MethodBinding[] methods, int length, MethodBinding[] allInheritedMethods) {
      super.checkAgainstInheritedMethods(currentMethod, methods, length, allInheritedMethods);
      CompilerOptions options = this.environment.globalOptions;
      if (options.isAnnotationBasedNullAnalysisEnabled && (currentMethod.tagBits & 4096L) == 0L) {
         AbstractMethodDeclaration srcMethod = null;
         if (this.type.equals(currentMethod.declaringClass)) {
            srcMethod = currentMethod.sourceMethod();
         }

         boolean hasReturnNonNullDefault = currentMethod.hasNonNullDefaultForReturnType(srcMethod);
         ParameterNonNullDefaultProvider hasParameterNonNullDefault = currentMethod.hasNonNullDefaultForParameter(srcMethod);
         int i = length;

         while(true) {
            --i;
            if (i < 0) {
               break;
            }

            if (!currentMethod.isStatic() && !methods[i].isStatic()) {
               this.checkNullSpecInheritance(currentMethod, srcMethod, hasReturnNonNullDefault, hasParameterNonNullDefault, true, methods[i], methods, this.type.scope, (ImplicitNullAnnotationVerifier.InheritedNonNullnessInfo[])null);
            }
         }
      }

   }

   void checkNullSpecInheritance(MethodBinding currentMethod, AbstractMethodDeclaration srcMethod, boolean hasReturnNonNullDefault, ParameterNonNullDefaultProvider hasParameterNonNullDefault, boolean complain, MethodBinding inheritedMethod, MethodBinding[] allInherited, Scope scope, ImplicitNullAnnotationVerifier.InheritedNonNullnessInfo[] inheritedNonNullnessInfos) {
      complain &= !currentMethod.isConstructor();
      if (!hasReturnNonNullDefault && !hasParameterNonNullDefault.hasAnyNonNullDefault() && !complain && !this.environment.globalOptions.inheritNullAnnotations) {
         currentMethod.tagBits |= 4096L;
      } else {
         if (TypeBinding.notEquals(currentMethod.declaringClass, this.type) && (currentMethod.tagBits & 4096L) == 0L) {
            this.buddyImplicitNullAnnotationsVerifier.checkImplicitNullAnnotations(currentMethod, srcMethod, complain, scope);
         }

         super.checkNullSpecInheritance(currentMethod, srcMethod, hasReturnNonNullDefault, hasParameterNonNullDefault, complain, inheritedMethod, allInherited, scope, inheritedNonNullnessInfos);
      }
   }

   void reportRawReferences() {
      CompilerOptions compilerOptions = this.type.scope.compilerOptions();
      if (compilerOptions.sourceLevel >= 3211264L && !compilerOptions.reportUnavoidableGenericTypeProblems) {
         Object[] methodArray = this.currentMethods.valueTable;
         int s = methodArray.length;

         while(true) {
            do {
               --s;
               if (s < 0) {
                  return;
               }
            } while(methodArray[s] == null);

            MethodBinding[] current = (MethodBinding[])methodArray[s];
            int i = 0;

            for(int length = current.length; i < length; ++i) {
               MethodBinding currentMethod = current[i];
               if ((currentMethod.modifiers & 805306368) == 0) {
                  AbstractMethodDeclaration methodDecl = currentMethod.sourceMethod();
                  if (methodDecl == null) {
                     return;
                  }

                  TypeBinding[] parameterTypes = currentMethod.parameters;
                  Argument[] arguments = methodDecl.arguments;
                  int j = 0;

                  for(int size = currentMethod.parameters.length; j < size; ++j) {
                     TypeBinding parameterType = parameterTypes[j];
                     Argument arg = arguments[j];
                     if (parameterType.leafComponentType().isRawType() && compilerOptions.getSeverity(536936448) != 256 && (arg.type.bits & 1073741824) == 0) {
                        methodDecl.scope.problemReporter().rawTypeReference(arg.type, parameterType);
                     }
                  }

                  if (!methodDecl.isConstructor() && methodDecl instanceof MethodDeclaration) {
                     TypeReference returnType = ((MethodDeclaration)methodDecl).returnType;
                     TypeBinding methodType = currentMethod.returnType;
                     if (returnType != null && methodType.leafComponentType().isRawType() && compilerOptions.getSeverity(536936448) != 256 && (returnType.bits & 1073741824) == 0) {
                        methodDecl.scope.problemReporter().rawTypeReference(returnType, methodType);
                     }
                  }
               }
            }
         }
      }
   }

   public void reportRawReferences(MethodBinding currentMethod, MethodBinding inheritedMethod) {
      CompilerOptions compilerOptions = this.type.scope.compilerOptions();
      if (compilerOptions.sourceLevel >= 3211264L && !compilerOptions.reportUnavoidableGenericTypeProblems) {
         AbstractMethodDeclaration methodDecl = currentMethod.sourceMethod();
         if (methodDecl != null) {
            TypeBinding[] parameterTypes = currentMethod.parameters;
            TypeBinding[] inheritedParameterTypes = inheritedMethod.parameters;
            Argument[] arguments = methodDecl.arguments;
            int j = 0;

            TypeBinding parameterType;
            for(int size = currentMethod.parameters.length; j < size; ++j) {
               parameterType = parameterTypes[j];
               TypeBinding inheritedParameterType = inheritedParameterTypes[j];
               Argument arg = arguments[j];
               if (parameterType.leafComponentType().isRawType()) {
                  if (inheritedParameterType.leafComponentType().isRawType()) {
                     LocalVariableBinding var10000 = arg.binding;
                     var10000.tagBits |= 512L;
                  } else if (compilerOptions.getSeverity(536936448) != 256 && (arg.type.bits & 1073741824) == 0) {
                     methodDecl.scope.problemReporter().rawTypeReference(arg.type, parameterType);
                  }
               }
            }

            TypeReference returnType = null;
            if (!methodDecl.isConstructor() && methodDecl instanceof MethodDeclaration && (returnType = ((MethodDeclaration)methodDecl).returnType) != null) {
               TypeBinding inheritedMethodType = inheritedMethod.returnType;
               parameterType = currentMethod.returnType;
               if (parameterType.leafComponentType().isRawType() && !inheritedMethodType.leafComponentType().isRawType() && (returnType.bits & 1073741824) == 0 && compilerOptions.getSeverity(536936448) != 256) {
                  methodDecl.scope.problemReporter().rawTypeReference(returnType, parameterType);
               }
            }

         }
      }
   }

   void checkMethods() {
      boolean mustImplementAbstractMethods = this.mustImplementAbstractMethods();
      boolean skipInheritedMethods = mustImplementAbstractMethods && this.canSkipInheritedMethods();
      boolean isOrEnclosedByPrivateType = this.type.isOrEnclosedByPrivateType();
      char[][] methodSelectors = this.inheritedMethods.keyTable;
      int s = methodSelectors.length;

      while(true) {
         while(true) {
            MethodBinding[] current;
            MethodBinding[] inherited;
            int index;
            int inheritedLength;
            MethodBinding var10000;
            do {
               do {
                  --s;
                  if (s < 0) {
                     return;
                  }
               } while(methodSelectors[s] == null);

               current = (MethodBinding[])this.currentMethods.get(methodSelectors[s]);
               inherited = (MethodBinding[])this.inheritedMethods.valueTable[s];
               inherited = Sorting.concreteFirst(inherited, inherited.length);
               if (current == null && !isOrEnclosedByPrivateType) {
                  index = inherited.length;

                  for(inheritedLength = 0; inheritedLength < index; ++inheritedLength) {
                     var10000 = inherited[inheritedLength].original();
                     var10000.modifiers |= 134217728;
                  }
               }

               if (current == null && this.type.isPublic()) {
                  index = inherited.length;

                  for(inheritedLength = 0; inheritedLength < index; ++inheritedLength) {
                     MethodBinding inheritedMethod = inherited[inheritedLength];
                     if (inheritedMethod.isPublic() && !inheritedMethod.declaringClass.isInterface() && !inheritedMethod.declaringClass.isPublic()) {
                        this.type.addSyntheticBridgeMethod(inheritedMethod.original());
                     }
                  }
               }
            } while(current == null && skipInheritedMethods);

            if (inherited.length == 1 && current == null) {
               if (mustImplementAbstractMethods && inherited[0].isAbstract()) {
                  this.checkAbstractMethod(inherited[0]);
               }
            } else {
               index = -1;
               inheritedLength = inherited.length;
               MethodBinding[] matchingInherited = new MethodBinding[inheritedLength];
               MethodBinding[] foundMatch = new MethodBinding[inheritedLength];
               boolean[] skip = new boolean[inheritedLength];
               boolean[] isOverridden = new boolean[inheritedLength];
               boolean[] isInherited = new boolean[inheritedLength];
               Arrays.fill(isInherited, true);
               int i;
               MethodBinding inheritedMethod;
               MethodBinding replaceMatch;
               if (current != null) {
                  i = 0;

                  for(int length1 = current.length; i < length1; ++i) {
                     inheritedMethod = current[i];
                     MethodBinding[] nonMatchingInherited = null;

                     for(int j = 0; j < inheritedLength; ++j) {
                        replaceMatch = this.computeSubstituteMethod(inherited[j], inheritedMethod);
                        if (replaceMatch != null) {
                           if (foundMatch[j] == null && this.isSubstituteParameterSubsignature(inheritedMethod, replaceMatch)) {
                              isOverridden[j] = skip[j] = couldMethodOverride(inheritedMethod, replaceMatch);
                              ++index;
                              matchingInherited[index] = replaceMatch;
                              foundMatch[j] = inheritedMethod;
                           } else {
                              this.checkForNameClash(inheritedMethod, replaceMatch);
                              if (inheritedLength > 1) {
                                 if (nonMatchingInherited == null) {
                                    nonMatchingInherited = new MethodBinding[inheritedLength];
                                 }

                                 nonMatchingInherited[j] = replaceMatch;
                              }
                           }
                        }
                     }

                     if (index >= 0) {
                        this.checkAgainstInheritedMethods(inheritedMethod, matchingInherited, index + 1, nonMatchingInherited);

                        while(index >= 0) {
                           matchingInherited[index--] = null;
                        }
                     }
                  }
               }

               MethodBinding matchMethod;
               int length;
               MethodBinding otherInheritedMethod;
               for(i = 0; i < inheritedLength; ++i) {
                  matchMethod = foundMatch[i];
                  if (matchMethod == null && current != null && this.type.isPublic()) {
                     inheritedMethod = inherited[i];
                     if (inheritedMethod.isPublic() && !inheritedMethod.declaringClass.isInterface() && !inheritedMethod.declaringClass.isPublic()) {
                        this.type.addSyntheticBridgeMethod(inheritedMethod.original());
                     }
                  }

                  if (!isOrEnclosedByPrivateType && matchMethod == null && current != null) {
                     var10000 = inherited[i].original();
                     var10000.modifiers |= 134217728;
                  }

                  inheritedMethod = inherited[i];

                  for(length = i + 1; length < inheritedLength; ++length) {
                     otherInheritedMethod = inherited[length];
                     if ((matchMethod != foundMatch[length] || matchMethod == null) && !this.canSkipInheritedMethods(inheritedMethod, otherInheritedMethod) && TypeBinding.notEquals(inheritedMethod.declaringClass, otherInheritedMethod.declaringClass) && !this.isSkippableOrOverridden(inheritedMethod, otherInheritedMethod, skip, isOverridden, isInherited, length) && this.isSkippableOrOverridden(otherInheritedMethod, inheritedMethod, skip, isOverridden, isInherited, i)) {
                     }
                  }
               }

               for(i = 0; i < inheritedLength; ++i) {
                  matchMethod = foundMatch[i];
                  if (!skip[i]) {
                     inheritedMethod = inherited[i];
                     if (matchMethod == null) {
                        ++index;
                        matchingInherited[index] = inheritedMethod;
                     }

                     for(length = i + 1; length < inheritedLength; ++length) {
                        if (foundMatch[length] == null) {
                           otherInheritedMethod = inherited[length];
                           if ((matchMethod != foundMatch[length] || matchMethod == null) && !this.canSkipInheritedMethods(inheritedMethod, otherInheritedMethod)) {
                              if ((replaceMatch = this.findReplacedMethod(inheritedMethod, otherInheritedMethod)) != null) {
                                 ++index;
                                 matchingInherited[index] = replaceMatch;
                                 skip[length] = true;
                              } else if ((replaceMatch = this.findReplacedMethod(otherInheritedMethod, inheritedMethod)) != null) {
                                 ++index;
                                 matchingInherited[index] = replaceMatch;
                                 skip[length] = true;
                              } else if (matchMethod == null) {
                                 this.checkInheritedMethods(inheritedMethod, otherInheritedMethod);
                              }
                           }
                        }
                     }

                     if (index != -1) {
                        if (index > 0) {
                           length = index + 1;
                           boolean[] matchingIsOverridden;
                           boolean[] matchingIsInherited;
                           if (length == inheritedLength) {
                              matchingIsOverridden = isOverridden;
                              matchingIsInherited = isInherited;
                           } else {
                              matchingIsOverridden = new boolean[length];
                              matchingIsInherited = new boolean[length];

                              for(int j = 0; j < length; ++j) {
                                 for(int k = 0; k < inheritedLength; ++k) {
                                    if (matchingInherited[j] == inherited[k]) {
                                       matchingIsOverridden[j] = isOverridden[k];
                                       matchingIsInherited[j] = isInherited[k];
                                       break;
                                    }
                                 }
                              }
                           }

                           this.checkInheritedMethods(matchingInherited, length, matchingIsOverridden, matchingIsInherited);
                        } else if (mustImplementAbstractMethods && matchingInherited[0].isAbstract() && matchMethod == null) {
                           this.checkAbstractMethod(matchingInherited[0]);
                        }

                        while(index >= 0) {
                           matchingInherited[index--] = null;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   boolean isSkippableOrOverridden(MethodBinding specific, MethodBinding general, boolean[] skip, boolean[] isOverridden, boolean[] isInherited, int idx) {
      boolean specificIsInterface = specific.declaringClass.isInterface();
      boolean generalIsInterface = general.declaringClass.isInterface();
      if (!specificIsInterface && generalIsInterface) {
         if (!specific.isAbstract() && this.isParameterSubsignature(specific, general)) {
            isInherited[idx] = false;
            return true;
         }

         if (this.isInterfaceMethodImplemented(general, specific, general.declaringClass)) {
            skip[idx] = true;
            isOverridden[idx] = true;
            return true;
         }
      } else if (specificIsInterface == generalIsInterface && specific.declaringClass.isCompatibleWith(general.declaringClass) && this.isMethodSubsignature(specific, general)) {
         skip[idx] = true;
         isOverridden[idx] = true;
         return true;
      }

      return false;
   }

   MethodBinding findReplacedMethod(MethodBinding specific, MethodBinding general) {
      MethodBinding generalSubstitute = this.computeSubstituteMethod(general, specific);
      return generalSubstitute != null && (!specific.isAbstract() || general.isAbstract() || general.isDefaultMethod() && specific.declaringClass.isClass()) && this.isSubstituteParameterSubsignature(specific, generalSubstitute) ? generalSubstitute : null;
   }

   void checkTypeVariableMethods(TypeParameter typeParameter) {
      char[][] methodSelectors = this.inheritedMethods.keyTable;
      int s = methodSelectors.length;

      while(true) {
         while(true) {
            MethodBinding[] inherited;
            do {
               do {
                  --s;
                  if (s < 0) {
                     return;
                  }
               } while(methodSelectors[s] == null);

               inherited = (MethodBinding[])this.inheritedMethods.valueTable[s];
            } while(inherited.length == 1);

            int index = -1;
            MethodBinding[] matchingInherited = new MethodBinding[inherited.length];
            int i = 0;

            for(int length = inherited.length; i < length; ++i) {
               while(index >= 0) {
                  matchingInherited[index--] = null;
               }

               MethodBinding inheritedMethod = inherited[i];
               if (inheritedMethod != null) {
                  ++index;
                  matchingInherited[index] = inheritedMethod;

                  for(int j = i + 1; j < length; ++j) {
                     MethodBinding otherInheritedMethod = inherited[j];
                     if (!this.canSkipInheritedMethods(inheritedMethod, otherInheritedMethod)) {
                        otherInheritedMethod = this.computeSubstituteMethod(otherInheritedMethod, inheritedMethod);
                        if (otherInheritedMethod != null && this.isSubstituteParameterSubsignature(inheritedMethod, otherInheritedMethod)) {
                           ++index;
                           matchingInherited[index] = otherInheritedMethod;
                           inherited[j] = null;
                        }
                     }
                  }
               }

               if (index > 0) {
                  MethodBinding first = matchingInherited[0];
                  int count = index + 1;

                  MethodBinding match;
                  do {
                     --count;
                     if (count <= 0) {
                        break;
                     }

                     match = matchingInherited[count];
                     MethodBinding interfaceMethod = null;
                     MethodBinding implementation = null;
                     if (first.declaringClass.isInterface()) {
                        interfaceMethod = first;
                     } else if (first.declaringClass.isClass()) {
                        implementation = first;
                     }

                     if (match.declaringClass.isInterface()) {
                        interfaceMethod = match;
                     } else if (match.declaringClass.isClass()) {
                        implementation = match;
                     }

                     if (interfaceMethod != null && implementation != null && !implementation.isAbstract() && !this.isAsVisible(implementation, interfaceMethod)) {
                        this.problemReporter().inheritedMethodReducesVisibility(typeParameter, implementation, new MethodBinding[]{interfaceMethod});
                     }
                  } while(this.areReturnTypesCompatible(first, match) || first.declaringClass.isInterface() && match.declaringClass.isInterface() && this.areReturnTypesCompatible(match, first));

                  if (count > 0) {
                     this.problemReporter().inheritedMethodsHaveIncompatibleReturnTypes(typeParameter, matchingInherited, index + 1);
                     break;
                  }
               }
            }
         }
      }
   }

   boolean detectInheritedNameClash(MethodBinding inherited, MethodBinding otherInherited) {
      if (!inherited.areParameterErasuresEqual(otherInherited)) {
         return false;
      } else if (TypeBinding.notEquals(inherited.returnType.erasure(), otherInherited.returnType.erasure())) {
         return false;
      } else {
         if (TypeBinding.notEquals(inherited.declaringClass.erasure(), otherInherited.declaringClass.erasure())) {
            if (inherited.declaringClass.findSuperTypeOriginatingFrom(otherInherited.declaringClass) != null) {
               return false;
            }

            if (otherInherited.declaringClass.findSuperTypeOriginatingFrom(inherited.declaringClass) != null) {
               return false;
            }
         }

         this.problemReporter().inheritedMethodsHaveNameClash(this.type, inherited, otherInherited);
         return true;
      }
   }

   boolean detectNameClash(MethodBinding current, MethodBinding inherited, boolean treatAsSynthetic) {
      MethodBinding methodToCheck = inherited;
      MethodBinding original = inherited.original();
      if (!current.areParameterErasuresEqual(original)) {
         return false;
      } else {
         int severity = 1;
         if (this.environment.globalOptions.complianceLevel == 3276800L && TypeBinding.notEquals(current.returnType.erasure(), original.returnType.erasure())) {
            severity = 0;
         }

         if (!treatAsSynthetic) {
            MethodBinding[] currentNamesakes = (MethodBinding[])this.currentMethods.get(inherited.selector);
            if (currentNamesakes.length > 1) {
               int i = 0;

               for(int length = currentNamesakes.length; i < length; ++i) {
                  MethodBinding currentMethod = currentNamesakes[i];
                  if (currentMethod != current && this.doesMethodOverride(currentMethod, inherited)) {
                     methodToCheck = currentMethod;
                     break;
                  }
               }
            }
         }

         original = methodToCheck.original();
         if (!current.areParameterErasuresEqual(original)) {
            return false;
         } else {
            original = inherited.original();
            this.problemReporter(current).methodNameClash(current, inherited.declaringClass.isRawType() ? inherited : original, severity);
            return severity != 0;
         }
      }
   }

   boolean doTypeVariablesClash(MethodBinding one, MethodBinding substituteTwo) {
      return one.typeVariables != Binding.NO_TYPE_VARIABLES && !(substituteTwo instanceof ParameterizedGenericMethodBinding);
   }

   SimpleSet findSuperinterfaceCollisions(ReferenceBinding superclass, ReferenceBinding[] superInterfaces) {
      ReferenceBinding[] interfacesToVisit = null;
      int nextPosition = 0;
      if (superInterfaces != Binding.NO_SUPERINTERFACES) {
         nextPosition = superInterfaces.length;
         interfacesToVisit = superInterfaces;
      }

      boolean isInconsistent = this.type.isHierarchyInconsistent();

      ReferenceBinding[] itsInterfaces;
      ReferenceBinding superType;
      int itsLength;
      int i;
      ReferenceBinding next;
      for(superType = superclass; superType != null && superType.isValidBinding(); superType = superType.superclass()) {
         isInconsistent |= superType.isHierarchyInconsistent();
         if ((itsInterfaces = superType.superInterfaces()) != Binding.NO_SUPERINTERFACES) {
            if (interfacesToVisit == null) {
               interfacesToVisit = itsInterfaces;
               nextPosition = itsInterfaces.length;
            } else {
               itsLength = itsInterfaces.length;
               if (nextPosition + itsLength >= interfacesToVisit.length) {
                  System.arraycopy(interfacesToVisit, 0, interfacesToVisit = new ReferenceBinding[nextPosition + itsLength + 5], 0, nextPosition);
               }

               label117:
               for(i = 0; i < itsLength; ++i) {
                  next = itsInterfaces[i];

                  for(int b = 0; b < nextPosition; ++b) {
                     if (TypeBinding.equalsEquals(next, interfacesToVisit[b])) {
                        continue label117;
                     }
                  }

                  interfacesToVisit[nextPosition++] = next;
               }
            }
         }
      }

      int j;
      for(itsLength = 0; itsLength < nextPosition; ++itsLength) {
         superType = interfacesToVisit[itsLength];
         if (superType.isValidBinding()) {
            isInconsistent |= superType.isHierarchyInconsistent();
            if ((itsInterfaces = superType.superInterfaces()) != Binding.NO_SUPERINTERFACES) {
               i = itsInterfaces.length;
               if (nextPosition + i >= interfacesToVisit.length) {
                  System.arraycopy(interfacesToVisit, 0, interfacesToVisit = new ReferenceBinding[nextPosition + i + 5], 0, nextPosition);
               }

               label97:
               for(int a = 0; a < i; ++a) {
                  ReferenceBinding next = itsInterfaces[a];

                  for(j = 0; j < nextPosition; ++j) {
                     if (TypeBinding.equalsEquals(next, interfacesToVisit[j])) {
                        continue label97;
                     }
                  }

                  interfacesToVisit[nextPosition++] = next;
               }
            }
         }
      }

      if (!isInconsistent) {
         return null;
      } else {
         SimpleSet copy = null;

         for(i = 0; i < nextPosition; ++i) {
            next = interfacesToVisit[i];
            if (next.isValidBinding()) {
               TypeBinding erasure = next.erasure();

               for(j = i + 1; j < nextPosition; ++j) {
                  ReferenceBinding next = interfacesToVisit[j];
                  if (next.isValidBinding() && TypeBinding.equalsEquals(next.erasure(), erasure)) {
                     if (copy == null) {
                        copy = new SimpleSet(nextPosition);
                     }

                     copy.add(interfacesToVisit[i]);
                     copy.add(interfacesToVisit[j]);
                  }
               }
            }
         }

         return copy;
      }
   }

   boolean isAcceptableReturnTypeOverride(MethodBinding currentMethod, MethodBinding inheritedMethod) {
      if (inheritedMethod.declaringClass.isRawType()) {
         return true;
      } else {
         MethodBinding originalInherited = inheritedMethod.original();
         TypeBinding originalInheritedReturnType = originalInherited.returnType.leafComponentType();
         if (originalInheritedReturnType.isParameterizedTypeWithActualArguments()) {
            return !currentMethod.returnType.leafComponentType().isRawType();
         } else {
            TypeBinding currentReturnType = currentMethod.returnType.leafComponentType();
            switch (currentReturnType.kind()) {
               case 4100:
                  if (TypeBinding.equalsEquals(currentReturnType, inheritedMethod.returnType.leafComponentType())) {
                     return true;
                  }
               default:
                  return !originalInheritedReturnType.isTypeVariable() || ((TypeVariableBinding)originalInheritedReturnType).declaringElement != originalInherited;
            }
         }
      }
   }

   boolean isInterfaceMethodImplemented(MethodBinding inheritedMethod, MethodBinding existingMethod, ReferenceBinding superType) {
      if (inheritedMethod.original() != inheritedMethod && existingMethod.declaringClass.isInterface()) {
         return false;
      } else {
         inheritedMethod = this.computeSubstituteMethod(inheritedMethod, existingMethod);
         if (inheritedMethod != null && this.doesMethodOverride(existingMethod, inheritedMethod)) {
            return TypeBinding.equalsEquals(inheritedMethod.returnType, existingMethod.returnType) || TypeBinding.notEquals(this.type, existingMethod.declaringClass) && !existingMethod.declaringClass.isInterface() && this.areReturnTypesCompatible(existingMethod, inheritedMethod);
         } else {
            return false;
         }
      }
   }

   public boolean isMethodSubsignature(MethodBinding method, MethodBinding inheritedMethod) {
      if (!CharOperation.equals(method.selector, inheritedMethod.selector)) {
         return false;
      } else {
         if (method.declaringClass.isParameterizedType()) {
            method = method.original();
         }

         MethodBinding inheritedOriginal = method.findOriginalInheritedMethod(inheritedMethod);
         return this.isParameterSubsignature(method, inheritedOriginal == null ? inheritedMethod : inheritedOriginal);
      }
   }

   boolean isUnsafeReturnTypeOverride(MethodBinding currentMethod, MethodBinding inheritedMethod) {
      if (TypeBinding.equalsEquals(currentMethod.returnType, inheritedMethod.returnType.erasure())) {
         TypeBinding[] currentParams = currentMethod.parameters;
         TypeBinding[] inheritedParams = inheritedMethod.parameters;
         int i = 0;

         for(int l = currentParams.length; i < l; ++i) {
            if (!areTypesEqual(currentParams[i], inheritedParams[i])) {
               return true;
            }
         }
      }

      return currentMethod.typeVariables == Binding.NO_TYPE_VARIABLES && inheritedMethod.original().typeVariables != Binding.NO_TYPE_VARIABLES && currentMethod.returnType.erasure().findSuperTypeOriginatingFrom(inheritedMethod.returnType.erasure()) != null;
   }

   boolean reportIncompatibleReturnTypeError(MethodBinding currentMethod, MethodBinding inheritedMethod) {
      if (this.isUnsafeReturnTypeOverride(currentMethod, inheritedMethod)) {
         this.problemReporter(currentMethod).unsafeReturnTypeOverride(currentMethod, inheritedMethod, this.type);
         return false;
      } else {
         return super.reportIncompatibleReturnTypeError(currentMethod, inheritedMethod);
      }
   }

   void verify() {
      if (this.type.isAnnotationType()) {
         this.type.detectAnnotationCycle();
      }

      super.verify();
      this.reportRawReferences();
      int i = this.type.typeVariables.length;

      while(true) {
         TypeVariableBinding var;
         do {
            do {
               --i;
               if (i < 0) {
                  return;
               }

               var = this.type.typeVariables[i];
            } while(var.superInterfaces == Binding.NO_SUPERINTERFACES);
         } while(var.superInterfaces.length == 1 && var.superclass.id == 1);

         this.currentMethods = new HashtableOfObject(0);
         ReferenceBinding superclass = var.superclass();
         if (superclass.kind() == 4100) {
            superclass = (ReferenceBinding)superclass.erasure();
         }

         ReferenceBinding[] itsInterfaces = var.superInterfaces();
         ReferenceBinding[] superInterfaces = new ReferenceBinding[itsInterfaces.length];
         int j = itsInterfaces.length;

         while(true) {
            --j;
            if (j < 0) {
               this.computeInheritedMethods(superclass, superInterfaces);
               this.checkTypeVariableMethods(this.type.scope.referenceContext.typeParameters[i]);
               break;
            }

            superInterfaces[j] = itsInterfaces[j].kind() == 4100 ? (ReferenceBinding)itsInterfaces[j].erasure() : itsInterfaces[j];
         }
      }
   }
}

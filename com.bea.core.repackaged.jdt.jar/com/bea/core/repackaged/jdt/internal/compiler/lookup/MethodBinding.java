package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ClassFile;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Annotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Argument;
import com.bea.core.repackaged.jdt.internal.compiler.ast.LambdaExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.MethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.ConstantPool;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.util.List;

public class MethodBinding extends Binding {
   public int modifiers;
   public char[] selector;
   public TypeBinding returnType;
   public TypeBinding[] parameters;
   public TypeBinding receiver;
   public ReferenceBinding[] thrownExceptions;
   public ReferenceBinding declaringClass;
   public TypeVariableBinding[] typeVariables;
   char[] signature;
   public long tagBits;
   protected AnnotationBinding[] typeAnnotations;
   public Boolean[] parameterNonNullness;
   public int defaultNullness;
   public char[][] parameterNames;

   protected MethodBinding() {
      this.typeVariables = Binding.NO_TYPE_VARIABLES;
      this.typeAnnotations = Binding.NO_ANNOTATIONS;
      this.parameterNames = Binding.NO_PARAMETER_NAMES;
   }

   public MethodBinding(int modifiers, char[] selector, TypeBinding returnType, TypeBinding[] parameters, ReferenceBinding[] thrownExceptions, ReferenceBinding declaringClass) {
      this.typeVariables = Binding.NO_TYPE_VARIABLES;
      this.typeAnnotations = Binding.NO_ANNOTATIONS;
      this.parameterNames = Binding.NO_PARAMETER_NAMES;
      this.modifiers = modifiers;
      this.selector = selector;
      this.returnType = returnType;
      this.parameters = parameters != null && parameters.length != 0 ? parameters : Binding.NO_PARAMETERS;
      this.thrownExceptions = thrownExceptions != null && thrownExceptions.length != 0 ? thrownExceptions : Binding.NO_EXCEPTIONS;
      this.declaringClass = declaringClass;
      if (this.declaringClass != null && this.declaringClass.isStrictfp() && !this.isNative() && !this.isAbstract()) {
         this.modifiers |= 2048;
      }

   }

   public MethodBinding(int modifiers, TypeBinding[] parameters, ReferenceBinding[] thrownExceptions, ReferenceBinding declaringClass) {
      this(modifiers, TypeConstants.INIT, TypeBinding.VOID, parameters, thrownExceptions, declaringClass);
   }

   public MethodBinding(MethodBinding initialMethodBinding, ReferenceBinding declaringClass) {
      this.typeVariables = Binding.NO_TYPE_VARIABLES;
      this.typeAnnotations = Binding.NO_ANNOTATIONS;
      this.parameterNames = Binding.NO_PARAMETER_NAMES;
      this.modifiers = initialMethodBinding.modifiers;
      this.selector = initialMethodBinding.selector;
      this.returnType = initialMethodBinding.returnType;
      this.parameters = initialMethodBinding.parameters;
      this.thrownExceptions = initialMethodBinding.thrownExceptions;
      this.declaringClass = declaringClass;
      declaringClass.storeAnnotationHolder(this, initialMethodBinding.declaringClass.retrieveAnnotationHolder(initialMethodBinding, true));
   }

   public final boolean areParameterErasuresEqual(MethodBinding method) {
      TypeBinding[] args = method.parameters;
      if (this.parameters == args) {
         return true;
      } else {
         int length = this.parameters.length;
         if (length != args.length) {
            return false;
         } else {
            for(int i = 0; i < length; ++i) {
               if (TypeBinding.notEquals(this.parameters[i], args[i]) && TypeBinding.notEquals(this.parameters[i].erasure(), args[i].erasure())) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public final boolean areParametersCompatibleWith(TypeBinding[] arguments) {
      int paramLength = this.parameters.length;
      int argLength = arguments.length;
      int lastIndex = argLength;
      if (this.isVarargs()) {
         lastIndex = paramLength - 1;
         TypeBinding varArgType;
         if (paramLength == argLength) {
            varArgType = this.parameters[lastIndex];
            TypeBinding lastArgument = arguments[lastIndex];
            if (TypeBinding.notEquals(varArgType, lastArgument) && !lastArgument.isCompatibleWith(varArgType)) {
               return false;
            }
         } else if (paramLength < argLength) {
            varArgType = ((ArrayBinding)this.parameters[lastIndex]).elementsType();

            for(int i = lastIndex; i < argLength; ++i) {
               if (TypeBinding.notEquals(varArgType, arguments[i]) && !arguments[i].isCompatibleWith(varArgType)) {
                  return false;
               }
            }
         } else if (lastIndex != argLength) {
            return false;
         }
      }

      for(int i = 0; i < lastIndex; ++i) {
         if (TypeBinding.notEquals(this.parameters[i], arguments[i]) && !arguments[i].isCompatibleWith(this.parameters[i])) {
            return false;
         }
      }

      return true;
   }

   public final boolean areParametersEqual(MethodBinding method) {
      TypeBinding[] args = method.parameters;
      if (this.parameters == args) {
         return true;
      } else {
         int length = this.parameters.length;
         if (length != args.length) {
            return false;
         } else {
            for(int i = 0; i < length; ++i) {
               if (TypeBinding.notEquals(this.parameters[i], args[i])) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public final boolean areTypeVariableErasuresEqual(MethodBinding method) {
      TypeVariableBinding[] vars = method.typeVariables;
      if (this.typeVariables == vars) {
         return true;
      } else {
         int length = this.typeVariables.length;
         if (length != vars.length) {
            return false;
         } else {
            for(int i = 0; i < length; ++i) {
               if (TypeBinding.notEquals(this.typeVariables[i], vars[i]) && TypeBinding.notEquals(this.typeVariables[i].erasure(), vars[i].erasure())) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public MethodBinding asRawMethod(LookupEnvironment env) {
      if (this.typeVariables == Binding.NO_TYPE_VARIABLES) {
         return this;
      } else {
         int length = this.typeVariables.length;
         TypeBinding[] arguments = new TypeBinding[length];

         for(int i = 0; i < length; ++i) {
            arguments[i] = this.makeRawArgument(env, this.typeVariables[i]);
         }

         return env.createParameterizedGenericMethod(this, arguments);
      }
   }

   private TypeBinding makeRawArgument(LookupEnvironment env, TypeVariableBinding var) {
      if (var.boundsCount() <= 1) {
         TypeBinding upperBound = var.upperBound();
         return upperBound.isTypeVariable() ? this.makeRawArgument(env, (TypeVariableBinding)upperBound) : env.convertToRawType(upperBound, false);
      } else {
         TypeBinding[] itsSuperinterfaces = var.superInterfaces();
         int superLength = itsSuperinterfaces.length;
         TypeBinding rawFirstBound = null;
         TypeBinding[] rawOtherBounds = null;
         int s;
         if (var.boundsCount() == superLength) {
            rawFirstBound = env.convertToRawType(itsSuperinterfaces[0], false);
            rawOtherBounds = new TypeBinding[superLength - 1];

            for(s = 1; s < superLength; ++s) {
               rawOtherBounds[s - 1] = env.convertToRawType(itsSuperinterfaces[s], false);
            }
         } else {
            rawFirstBound = env.convertToRawType(var.superclass(), false);
            rawOtherBounds = new TypeBinding[superLength];

            for(s = 0; s < superLength; ++s) {
               rawOtherBounds[s] = env.convertToRawType(itsSuperinterfaces[s], false);
            }
         }

         return env.createWildcard((ReferenceBinding)null, 0, rawFirstBound, rawOtherBounds, 1);
      }
   }

   public final boolean canBeSeenBy(InvocationSite invocationSite, Scope scope) {
      if (this.isPublic()) {
         return true;
      } else {
         SourceTypeBinding invocationType = scope.enclosingSourceType();
         if (TypeBinding.equalsEquals(invocationType, this.declaringClass)) {
            return true;
         } else if (this.isProtected()) {
            return invocationType.fPackage == this.declaringClass.fPackage ? true : invocationSite.isSuperAccess();
         } else if (!this.isPrivate()) {
            return invocationType.fPackage == this.declaringClass.fPackage;
         } else {
            ReferenceBinding outerInvocationType = invocationType;

            ReferenceBinding temp;
            for(temp = invocationType.enclosingType(); temp != null; temp = temp.enclosingType()) {
               outerInvocationType = temp;
            }

            ReferenceBinding outerDeclaringClass = (ReferenceBinding)this.declaringClass.erasure();

            for(temp = outerDeclaringClass.enclosingType(); temp != null; temp = temp.enclosingType()) {
               outerDeclaringClass = temp;
            }

            return TypeBinding.equalsEquals((TypeBinding)outerInvocationType, outerDeclaringClass);
         }
      }
   }

   public final boolean canBeSeenBy(PackageBinding invocationPackage) {
      if (this.isPublic()) {
         return true;
      } else if (this.isPrivate()) {
         return false;
      } else {
         return invocationPackage == this.declaringClass.getPackage();
      }
   }

   public final boolean canBeSeenBy(TypeBinding receiverType, InvocationSite invocationSite, Scope scope) {
      SourceTypeBinding invocationType = scope.enclosingSourceType();
      if (this.declaringClass.isInterface() && this.isStatic() && !this.isPrivate()) {
         if (scope.compilerOptions().sourceLevel < 3407872L) {
            return false;
         } else {
            return (invocationSite.isTypeAccess() || invocationSite.receiverIsImplicitThis()) && TypeBinding.equalsEquals(receiverType, this.declaringClass);
         }
      } else if (this.isPublic()) {
         return true;
      } else if (TypeBinding.equalsEquals(invocationType, this.declaringClass) && TypeBinding.equalsEquals(invocationType, receiverType)) {
         return true;
      } else if (invocationType == null) {
         return !this.isPrivate() && scope.getCurrentPackage() == this.declaringClass.fPackage;
      } else {
         Object currentType;
         ReferenceBinding outerDeclaringClass;
         TypeBinding receiverErasure;
         if (this.isProtected()) {
            if (TypeBinding.equalsEquals(invocationType, this.declaringClass)) {
               return true;
            } else if (invocationType.fPackage == this.declaringClass.fPackage) {
               return true;
            } else {
               currentType = invocationType;
               receiverErasure = receiverType.erasure();
               outerDeclaringClass = (ReferenceBinding)this.declaringClass.erasure();
               int depth = 0;

               do {
                  if (((ReferenceBinding)currentType).findSuperTypeOriginatingFrom(outerDeclaringClass) != null) {
                     if (invocationSite.isSuperAccess()) {
                        return true;
                     }

                     if (receiverType instanceof ArrayBinding) {
                        return false;
                     }

                     if (this.isStatic()) {
                        if (depth > 0) {
                           invocationSite.setDepth(depth);
                        }

                        return true;
                     }

                     if (TypeBinding.equalsEquals((TypeBinding)currentType, receiverErasure) || receiverErasure.findSuperTypeOriginatingFrom((TypeBinding)currentType) != null) {
                        if (depth > 0) {
                           invocationSite.setDepth(depth);
                        }

                        return true;
                     }
                  }

                  ++depth;
                  currentType = ((ReferenceBinding)currentType).enclosingType();
               } while(currentType != null);

               return false;
            }
         } else if (!this.isPrivate()) {
            PackageBinding declaringPackage = this.declaringClass.fPackage;
            if (invocationType.fPackage != declaringPackage) {
               return false;
            } else if (receiverType instanceof ArrayBinding) {
               return false;
            } else {
               receiverErasure = this.declaringClass.original();
               outerDeclaringClass = (ReferenceBinding)receiverType;

               do {
                  if (outerDeclaringClass.isCapture()) {
                     if (TypeBinding.equalsEquals(receiverErasure, outerDeclaringClass.erasure().original())) {
                        return true;
                     }
                  } else if (TypeBinding.equalsEquals(receiverErasure, outerDeclaringClass.original())) {
                     return true;
                  }

                  PackageBinding currentPackage = outerDeclaringClass.fPackage;
                  if (!outerDeclaringClass.isCapture() && currentPackage != null && currentPackage != declaringPackage) {
                     return false;
                  }
               } while((outerDeclaringClass = outerDeclaringClass.superclass()) != null);

               return false;
            }
         } else if (!TypeBinding.notEquals(receiverType, this.declaringClass) || scope.compilerOptions().complianceLevel <= 3276800L && receiverType.isTypeVariable() && ((TypeVariableBinding)receiverType).isErasureBoundTo(this.declaringClass.erasure())) {
            if (TypeBinding.notEquals(invocationType, this.declaringClass)) {
               currentType = invocationType;

               ReferenceBinding temp;
               for(temp = invocationType.enclosingType(); temp != null; temp = temp.enclosingType()) {
                  currentType = temp;
               }

               outerDeclaringClass = (ReferenceBinding)this.declaringClass.erasure();

               for(temp = outerDeclaringClass.enclosingType(); temp != null; temp = temp.enclosingType()) {
                  outerDeclaringClass = temp;
               }

               if (TypeBinding.notEquals((TypeBinding)currentType, outerDeclaringClass)) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public List collectMissingTypes(List missingTypes) {
      if ((this.tagBits & 128L) != 0L) {
         missingTypes = this.returnType.collectMissingTypes(missingTypes);
         int i = 0;

         int max;
         for(max = this.parameters.length; i < max; ++i) {
            missingTypes = this.parameters[i].collectMissingTypes(missingTypes);
         }

         i = 0;

         for(max = this.thrownExceptions.length; i < max; ++i) {
            missingTypes = this.thrownExceptions[i].collectMissingTypes(missingTypes);
         }

         i = 0;

         for(max = this.typeVariables.length; i < max; ++i) {
            TypeVariableBinding variable = this.typeVariables[i];
            missingTypes = variable.superclass().collectMissingTypes(missingTypes);
            ReferenceBinding[] interfaces = variable.superInterfaces();
            int j = 0;

            for(int length = interfaces.length; j < length; ++j) {
               missingTypes = interfaces[j].collectMissingTypes(missingTypes);
            }
         }
      }

      return missingTypes;
   }

   public MethodBinding computeSubstitutedMethod(MethodBinding method, LookupEnvironment env) {
      int length = this.typeVariables.length;
      TypeVariableBinding[] vars = method.typeVariables;
      if (length != vars.length) {
         return null;
      } else {
         ParameterizedGenericMethodBinding substitute = env.createParameterizedGenericMethod(method, (TypeBinding[])this.typeVariables);

         for(int i = 0; i < length; ++i) {
            if (!this.typeVariables[i].isInterchangeableWith(vars[i], substitute)) {
               return null;
            }
         }

         return substitute;
      }
   }

   public char[] computeUniqueKey(boolean isLeaf) {
      char[] declaringKey = this.declaringClass.computeUniqueKey(false);
      int declaringLength = declaringKey.length;
      int selectorLength = this.selector == TypeConstants.INIT ? 0 : this.selector.length;
      char[] sig = this.genericSignature();
      boolean isGeneric = sig != null;
      if (!isGeneric) {
         sig = this.signature();
      }

      int signatureLength = sig.length;
      int thrownExceptionsLength = this.thrownExceptions.length;
      int thrownExceptionsSignatureLength = 0;
      char[][] thrownExceptionsSignatures = null;
      boolean addThrownExceptions = thrownExceptionsLength > 0 && (!isGeneric || CharOperation.lastIndexOf('^', sig) < 0);
      if (addThrownExceptions) {
         thrownExceptionsSignatures = new char[thrownExceptionsLength][];

         for(int i = 0; i < thrownExceptionsLength; ++i) {
            if (this.thrownExceptions[i] != null) {
               thrownExceptionsSignatures[i] = this.thrownExceptions[i].signature();
               thrownExceptionsSignatureLength += thrownExceptionsSignatures[i].length + 1;
            }
         }
      }

      char[] uniqueKey = new char[declaringLength + 1 + selectorLength + signatureLength + thrownExceptionsSignatureLength];
      int index = 0;
      System.arraycopy(declaringKey, 0, uniqueKey, index, declaringLength);
      index = declaringLength + 1;
      uniqueKey[declaringLength] = '.';
      System.arraycopy(this.selector, 0, uniqueKey, index, selectorLength);
      index += selectorLength;
      System.arraycopy(sig, 0, uniqueKey, index, signatureLength);
      if (thrownExceptionsSignatureLength > 0) {
         index += signatureLength;

         for(int i = 0; i < thrownExceptionsLength; ++i) {
            char[] thrownExceptionSignature = thrownExceptionsSignatures[i];
            if (thrownExceptionSignature != null) {
               uniqueKey[index++] = '|';
               int length = thrownExceptionSignature.length;
               System.arraycopy(thrownExceptionSignature, 0, uniqueKey, index, length);
               index += length;
            }
         }
      }

      return uniqueKey;
   }

   public final char[] constantPoolName() {
      return this.selector;
   }

   protected void fillInDefaultNonNullness(AbstractMethodDeclaration sourceMethod, boolean needToApplyReturnNonNullDefault, ParameterNonNullDefaultProvider needToApplyParameterNonNullDefault) {
      if (this.parameterNonNullness == null) {
         this.parameterNonNullness = new Boolean[this.parameters.length];
      }

      boolean added = false;
      int length = this.parameterNonNullness.length;

      for(int i = 0; i < length; ++i) {
         if (needToApplyParameterNonNullDefault.hasNonNullDefaultForParam(i) && !this.parameters[i].isBaseType()) {
            if (this.parameterNonNullness[i] == null) {
               added = true;
               this.parameterNonNullness[i] = Boolean.TRUE;
               if (sourceMethod != null) {
                  LocalVariableBinding var10000 = sourceMethod.arguments[i].binding;
                  var10000.tagBits |= 72057594037927936L;
               }
            } else if (sourceMethod != null && this.parameterNonNullness[i]) {
               sourceMethod.scope.problemReporter().nullAnnotationIsRedundant(sourceMethod, i);
            }
         }
      }

      if (added) {
         this.tagBits |= 1024L;
      }

      if (needToApplyReturnNonNullDefault) {
         if (this.returnType != null && !this.returnType.isBaseType() && (this.tagBits & 108086391056891904L) == 0L) {
            this.tagBits |= 72057594037927936L;
         } else if (sourceMethod != null && (this.tagBits & 72057594037927936L) != 0L) {
            sourceMethod.scope.problemReporter().nullAnnotationIsRedundant(sourceMethod, -1);
         }

      }
   }

   protected void fillInDefaultNonNullness18(AbstractMethodDeclaration sourceMethod, LookupEnvironment env) {
      MethodBinding original = this.original();
      if (original != null) {
         ParameterNonNullDefaultProvider hasNonNullDefaultForParameter = this.hasNonNullDefaultForParameter(sourceMethod);
         if (hasNonNullDefaultForParameter.hasAnyNonNullDefault()) {
            boolean added = false;
            int length = this.parameters.length;

            for(int i = 0; i < length; ++i) {
               if (hasNonNullDefaultForParameter.hasNonNullDefaultForParam(i)) {
                  TypeBinding parameter = this.parameters[i];
                  if (original.parameters[i].acceptsNonNullDefault()) {
                     long existing = parameter.tagBits & 108086391056891904L;
                     if (existing == 0L) {
                        added = true;
                        if (!parameter.isBaseType()) {
                           this.parameters[i] = env.createAnnotatedType(parameter, new AnnotationBinding[]{env.getNonNullAnnotation()});
                           if (sourceMethod != null) {
                              sourceMethod.arguments[i].binding.type = this.parameters[i];
                           }
                        }
                     } else if (sourceMethod != null && (parameter.tagBits & 72057594037927936L) != 0L && sourceMethod.arguments[i].hasNullTypeAnnotation(TypeReference.AnnotationPosition.MAIN_TYPE)) {
                        sourceMethod.scope.problemReporter().nullAnnotationIsRedundant(sourceMethod, i);
                     }
                  }
               }
            }

            if (added) {
               this.tagBits |= 1024L;
            }
         }

         if (original.returnType != null && this.hasNonNullDefaultForReturnType(sourceMethod) && original.returnType.acceptsNonNullDefault()) {
            if ((this.returnType.tagBits & 108086391056891904L) == 0L) {
               this.returnType = env.createAnnotatedType(this.returnType, new AnnotationBinding[]{env.getNonNullAnnotation()});
            } else if (sourceMethod instanceof MethodDeclaration && (this.returnType.tagBits & 72057594037927936L) != 0L && ((MethodDeclaration)sourceMethod).hasNullTypeAnnotation(TypeReference.AnnotationPosition.MAIN_TYPE)) {
               sourceMethod.scope.problemReporter().nullAnnotationIsRedundant(sourceMethod, -1);
            }
         }

      }
   }

   public MethodBinding findOriginalInheritedMethod(MethodBinding inheritedMethod) {
      MethodBinding inheritedOriginal = inheritedMethod.original();
      TypeBinding superType = this.declaringClass.findSuperTypeOriginatingFrom(inheritedOriginal.declaringClass);
      if (superType != null && superType instanceof ReferenceBinding) {
         if (TypeBinding.notEquals(inheritedOriginal.declaringClass, superType)) {
            MethodBinding[] superMethods = ((ReferenceBinding)superType).getMethods(inheritedOriginal.selector, inheritedOriginal.parameters.length);
            int m = 0;

            for(int l = superMethods.length; m < l; ++m) {
               if (superMethods[m].original() == inheritedOriginal) {
                  return superMethods[m];
               }
            }
         }

         return inheritedOriginal;
      } else {
         return null;
      }
   }

   public char[] genericSignature() {
      if ((this.modifiers & 1073741824) == 0) {
         return null;
      } else {
         StringBuffer sig = new StringBuffer(10);
         int i;
         int length;
         if (this.typeVariables != Binding.NO_TYPE_VARIABLES) {
            sig.append('<');
            i = 0;

            for(length = this.typeVariables.length; i < length; ++i) {
               sig.append(this.typeVariables[i].genericSignature());
            }

            sig.append('>');
         }

         sig.append('(');
         i = 0;

         for(length = this.parameters.length; i < length; ++i) {
            sig.append(this.parameters[i].genericTypeSignature());
         }

         sig.append(')');
         if (this.returnType != null) {
            sig.append(this.returnType.genericTypeSignature());
         }

         boolean needExceptionSignatures = false;
         length = this.thrownExceptions.length;

         int i;
         for(i = 0; i < length; ++i) {
            if ((this.thrownExceptions[i].modifiers & 1073741824) != 0) {
               needExceptionSignatures = true;
               break;
            }
         }

         if (needExceptionSignatures) {
            for(i = 0; i < length; ++i) {
               sig.append('^');
               sig.append(this.thrownExceptions[i].genericTypeSignature());
            }
         }

         i = sig.length();
         char[] genericSignature = new char[i];
         sig.getChars(0, i, genericSignature, 0);
         return genericSignature;
      }
   }

   public final int getAccessFlags() {
      return this.modifiers & 131071;
   }

   public AnnotationBinding[] getAnnotations() {
      MethodBinding originalMethod = this.original();
      return originalMethod.declaringClass.retrieveAnnotations(originalMethod);
   }

   public long getAnnotationTagBits() {
      MethodBinding originalMethod = this.original();
      if ((originalMethod.tagBits & 8589934592L) == 0L && originalMethod.declaringClass instanceof SourceTypeBinding) {
         ClassScope scope = ((SourceTypeBinding)originalMethod.declaringClass).scope;
         if (scope != null) {
            TypeDeclaration typeDecl = scope.referenceContext;
            AbstractMethodDeclaration methodDecl = typeDecl.declarationOf(originalMethod);
            if (methodDecl != null) {
               ASTNode.resolveAnnotations(methodDecl.scope, (Annotation[])methodDecl.annotations, (Binding)originalMethod);
            }

            CompilerOptions options = scope.compilerOptions();
            if (options.isAnnotationBasedNullAnalysisEnabled) {
               long nullDefaultBits = (long)this.defaultNullness;
               if (nullDefaultBits != 0L && this.declaringClass instanceof SourceTypeBinding) {
                  Binding target = scope.checkRedundantDefaultNullness(this.defaultNullness, typeDecl.declarationSourceStart);
                  if (target != null) {
                     methodDecl.scope.problemReporter().nullDefaultAnnotationIsRedundant(methodDecl, methodDecl.annotations, target);
                  }
               }
            }
         }
      }

      return originalMethod.tagBits;
   }

   public Object getDefaultValue() {
      MethodBinding originalMethod = this.original();
      if ((originalMethod.tagBits & 576460752303423488L) == 0L) {
         if (originalMethod.declaringClass instanceof SourceTypeBinding) {
            SourceTypeBinding sourceType = (SourceTypeBinding)originalMethod.declaringClass;
            if (sourceType.scope != null) {
               AbstractMethodDeclaration methodDeclaration = originalMethod.sourceMethod();
               if (methodDeclaration != null && methodDeclaration.isAnnotationMethod()) {
                  methodDeclaration.resolve(sourceType.scope);
               }
            }
         }

         originalMethod.tagBits |= 576460752303423488L;
      }

      AnnotationHolder holder = originalMethod.declaringClass.retrieveAnnotationHolder(originalMethod, true);
      return holder == null ? null : holder.getDefaultValue();
   }

   public AnnotationBinding[][] getParameterAnnotations() {
      int length;
      if ((length = this.parameters.length) == 0) {
         return null;
      } else {
         MethodBinding originalMethod = this.original();
         AnnotationHolder holder = originalMethod.declaringClass.retrieveAnnotationHolder(originalMethod, true);
         AnnotationBinding[][] allParameterAnnotations = holder == null ? null : holder.getParameterAnnotations();
         if (allParameterAnnotations == null && (this.tagBits & 1024L) != 0L) {
            allParameterAnnotations = new AnnotationBinding[length][];
            if (this.declaringClass instanceof SourceTypeBinding) {
               SourceTypeBinding sourceType = (SourceTypeBinding)this.declaringClass;
               if (sourceType.scope != null) {
                  AbstractMethodDeclaration methodDecl = sourceType.scope.referenceType().declarationOf(originalMethod);

                  for(int i = 0; i < length; ++i) {
                     Argument argument = methodDecl.arguments[i];
                     if (argument.annotations != null) {
                        ASTNode.resolveAnnotations(methodDecl.scope, (Annotation[])argument.annotations, (Binding)argument.binding);
                        allParameterAnnotations[i] = argument.binding.getAnnotations();
                     } else {
                        allParameterAnnotations[i] = Binding.NO_ANNOTATIONS;
                     }
                  }
               } else {
                  for(int i = 0; i < length; ++i) {
                     allParameterAnnotations[i] = Binding.NO_ANNOTATIONS;
                  }
               }
            } else {
               for(int i = 0; i < length; ++i) {
                  allParameterAnnotations[i] = Binding.NO_ANNOTATIONS;
               }
            }

            this.setParameterAnnotations(allParameterAnnotations);
         }

         return allParameterAnnotations;
      }
   }

   public TypeVariableBinding getTypeVariable(char[] variableName) {
      int i = this.typeVariables.length;

      do {
         --i;
         if (i < 0) {
            return null;
         }
      } while(!CharOperation.equals(this.typeVariables[i].sourceName, variableName));

      return this.typeVariables[i];
   }

   public TypeVariableBinding[] getAllTypeVariables(boolean isDiamond) {
      TypeVariableBinding[] allTypeVariables = this.typeVariables;
      if (isDiamond) {
         TypeVariableBinding[] classTypeVariables = this.declaringClass.typeVariables();
         int l1 = allTypeVariables.length;
         int l2 = classTypeVariables.length;
         if (l1 == 0) {
            allTypeVariables = classTypeVariables;
         } else if (l2 != 0) {
            System.arraycopy(allTypeVariables, 0, allTypeVariables = new TypeVariableBinding[l1 + l2], 0, l1);
            System.arraycopy(classTypeVariables, 0, allTypeVariables, l1, l2);
         }
      }

      return allTypeVariables;
   }

   public boolean hasSubstitutedParameters() {
      return false;
   }

   public boolean hasSubstitutedReturnType() {
      return false;
   }

   public final boolean isAbstract() {
      return (this.modifiers & 1024) != 0;
   }

   public final boolean isBridge() {
      return (this.modifiers & 64) != 0;
   }

   public final boolean isConstructor() {
      return this.selector == TypeConstants.INIT;
   }

   public final boolean isDefault() {
      return !this.isPublic() && !this.isProtected() && !this.isPrivate();
   }

   public final boolean isDefaultAbstract() {
      return (this.modifiers & 524288) != 0;
   }

   public boolean isDefaultMethod() {
      return (this.modifiers & 65536) != 0;
   }

   public final boolean isDeprecated() {
      return (this.modifiers & 1048576) != 0;
   }

   public final boolean isFinal() {
      return (this.modifiers & 16) != 0;
   }

   public final boolean isImplementing() {
      return (this.modifiers & 536870912) != 0;
   }

   public final boolean isMain() {
      if (this.selector.length == 4 && CharOperation.equals(this.selector, TypeConstants.MAIN) && (this.modifiers & 9) != 0 && TypeBinding.VOID == this.returnType && this.parameters.length == 1) {
         TypeBinding paramType = this.parameters[0];
         if (paramType.dimensions() == 1 && paramType.leafComponentType().id == 11) {
            return true;
         }
      }

      return false;
   }

   public final boolean isNative() {
      return (this.modifiers & 256) != 0;
   }

   public final boolean isOverriding() {
      return (this.modifiers & 268435456) != 0;
   }

   public final boolean isPrivate() {
      return (this.modifiers & 2) != 0;
   }

   public final boolean isOrEnclosedByPrivateType() {
      if ((this.modifiers & 2) != 0) {
         return true;
      } else {
         return this.declaringClass != null && this.declaringClass.isOrEnclosedByPrivateType();
      }
   }

   public final boolean isProtected() {
      return (this.modifiers & 4) != 0;
   }

   public final boolean isPublic() {
      return (this.modifiers & 1) != 0;
   }

   public final boolean isStatic() {
      return (this.modifiers & 8) != 0;
   }

   public final boolean isStrictfp() {
      return (this.modifiers & 2048) != 0;
   }

   public final boolean isSynchronized() {
      return (this.modifiers & 32) != 0;
   }

   public final boolean isSynthetic() {
      return (this.modifiers & 4096) != 0;
   }

   public final boolean isUsed() {
      return (this.modifiers & 134217728) != 0;
   }

   public boolean isVarargs() {
      return (this.modifiers & 128) != 0;
   }

   public boolean isParameterizedGeneric() {
      return false;
   }

   public boolean isPolymorphic() {
      return false;
   }

   public final boolean isViewedAsDeprecated() {
      return (this.modifiers & 3145728) != 0;
   }

   public final int kind() {
      return 8;
   }

   public MethodBinding original() {
      return this;
   }

   public MethodBinding shallowOriginal() {
      return this.original();
   }

   public MethodBinding genericMethod() {
      return this;
   }

   public char[] readableName() {
      StringBuffer buffer = new StringBuffer(this.parameters.length + 20);
      if (this.isConstructor()) {
         buffer.append(this.declaringClass.sourceName());
      } else {
         buffer.append(this.selector);
      }

      buffer.append('(');
      if (this.parameters != Binding.NO_PARAMETERS) {
         int i = 0;

         for(int length = this.parameters.length; i < length; ++i) {
            if (i > 0) {
               buffer.append(", ");
            }

            buffer.append(this.parameters[i].sourceName());
         }
      }

      buffer.append(')');
      return buffer.toString().toCharArray();
   }

   public final AnnotationBinding[] getTypeAnnotations() {
      return this.typeAnnotations;
   }

   public void setTypeAnnotations(AnnotationBinding[] annotations) {
      this.typeAnnotations = annotations;
   }

   public void setAnnotations(AnnotationBinding[] annotations, boolean forceStore) {
      this.declaringClass.storeAnnotations(this, annotations, forceStore);
   }

   public void setAnnotations(AnnotationBinding[] annotations, AnnotationBinding[][] parameterAnnotations, Object defaultValue, LookupEnvironment optionalEnv) {
      this.declaringClass.storeAnnotationHolder(this, AnnotationHolder.storeAnnotations(annotations, parameterAnnotations, defaultValue, optionalEnv));
   }

   public void setDefaultValue(Object defaultValue) {
      MethodBinding originalMethod = this.original();
      originalMethod.tagBits |= 576460752303423488L;
      AnnotationHolder holder = this.declaringClass.retrieveAnnotationHolder(this, false);
      if (holder == null) {
         this.setAnnotations((AnnotationBinding[])null, (AnnotationBinding[][])null, defaultValue, (LookupEnvironment)null);
      } else {
         this.setAnnotations(holder.getAnnotations(), holder.getParameterAnnotations(), defaultValue, (LookupEnvironment)null);
      }

   }

   public void setParameterAnnotations(AnnotationBinding[][] parameterAnnotations) {
      AnnotationHolder holder = this.declaringClass.retrieveAnnotationHolder(this, false);
      if (holder == null) {
         this.setAnnotations((AnnotationBinding[])null, parameterAnnotations, (Object)null, (LookupEnvironment)null);
      } else {
         this.setAnnotations(holder.getAnnotations(), parameterAnnotations, holder.getDefaultValue(), (LookupEnvironment)null);
      }

   }

   protected final void setSelector(char[] selector) {
      this.selector = selector;
      this.signature = null;
   }

   public char[] shortReadableName() {
      StringBuffer buffer = new StringBuffer(this.parameters.length + 20);
      if (this.isConstructor()) {
         buffer.append(this.declaringClass.shortReadableName());
      } else {
         buffer.append(this.selector);
      }

      buffer.append('(');
      int i;
      if (this.parameters != Binding.NO_PARAMETERS) {
         i = 0;

         for(int length = this.parameters.length; i < length; ++i) {
            if (i > 0) {
               buffer.append(", ");
            }

            buffer.append(this.parameters[i].shortReadableName());
         }
      }

      buffer.append(')');
      i = buffer.length();
      char[] shortReadableName = new char[i];
      buffer.getChars(0, i, shortReadableName, 0);
      return shortReadableName;
   }

   public final char[] signature() {
      if (this.signature != null) {
         return this.signature;
      } else {
         StringBuffer buffer = new StringBuffer(this.parameters.length + 20);
         buffer.append('(');
         TypeBinding[] targetParameters = this.parameters;
         boolean isConstructor = this.isConstructor();
         if (isConstructor && this.declaringClass.isEnum()) {
            buffer.append(ConstantPool.JavaLangStringSignature);
            buffer.append(TypeBinding.INT.signature());
         }

         boolean needSynthetics = isConstructor && this.declaringClass.isNestedType();
         int count;
         int i;
         if (needSynthetics) {
            ReferenceBinding[] syntheticArgumentTypes = this.declaringClass.syntheticEnclosingInstanceTypes();
            if (syntheticArgumentTypes != null) {
               count = 0;

               for(i = syntheticArgumentTypes.length; count < i; ++count) {
                  buffer.append(syntheticArgumentTypes[count].signature());
               }
            }

            if (this instanceof SyntheticMethodBinding) {
               targetParameters = ((SyntheticMethodBinding)this).targetMethod.parameters;
            }
         }

         int nameLength;
         if (targetParameters != Binding.NO_PARAMETERS) {
            for(nameLength = 0; nameLength < targetParameters.length; ++nameLength) {
               buffer.append(targetParameters[nameLength].signature());
            }
         }

         if (needSynthetics) {
            SyntheticArgumentBinding[] syntheticOuterArguments = this.declaringClass.syntheticOuterLocalVariables();
            count = syntheticOuterArguments == null ? 0 : syntheticOuterArguments.length;

            for(i = 0; i < count; ++i) {
               buffer.append(syntheticOuterArguments[i].type.signature());
            }

            i = targetParameters.length;

            for(int extraLength = this.parameters.length; i < extraLength; ++i) {
               buffer.append(this.parameters[i].signature());
            }
         }

         buffer.append(')');
         if (this.returnType != null) {
            buffer.append(this.returnType.signature());
         }

         nameLength = buffer.length();
         this.signature = new char[nameLength];
         buffer.getChars(0, nameLength, this.signature, 0);
         return this.signature;
      }
   }

   public char[] signature(ClassFile classFile) {
      TypeBinding[] targetParameters;
      boolean needSynthetics;
      int count;
      int nameLength;
      TypeBinding leafParameterType;
      if (this.signature != null) {
         if ((this.tagBits & 2048L) != 0L) {
            boolean isConstructor = this.isConstructor();
            targetParameters = this.parameters;
            needSynthetics = isConstructor && this.declaringClass.isNestedType();
            if (needSynthetics) {
               ReferenceBinding[] syntheticArgumentTypes = this.declaringClass.syntheticEnclosingInstanceTypes();
               if (syntheticArgumentTypes != null) {
                  nameLength = 0;

                  for(count = syntheticArgumentTypes.length; nameLength < count; ++nameLength) {
                     ReferenceBinding syntheticArgumentType = syntheticArgumentTypes[nameLength];
                     if ((syntheticArgumentType.tagBits & 2048L) != 0L) {
                        Util.recordNestedType(classFile, syntheticArgumentType);
                     }
                  }
               }

               if (this instanceof SyntheticMethodBinding) {
                  targetParameters = ((SyntheticMethodBinding)this).targetMethod.parameters;
               }
            }

            int i;
            TypeBinding parameter;
            if (targetParameters != Binding.NO_PARAMETERS) {
               i = 0;

               for(nameLength = targetParameters.length; i < nameLength; ++i) {
                  parameter = targetParameters[i];
                  leafParameterType = parameter.leafComponentType();
                  if ((leafParameterType.tagBits & 2048L) != 0L) {
                     Util.recordNestedType(classFile, leafParameterType);
                  }
               }
            }

            if (needSynthetics) {
               i = targetParameters.length;

               for(nameLength = this.parameters.length; i < nameLength; ++i) {
                  parameter = this.parameters[i];
                  leafParameterType = parameter.leafComponentType();
                  if ((leafParameterType.tagBits & 2048L) != 0L) {
                     Util.recordNestedType(classFile, leafParameterType);
                  }
               }
            }

            if (this.returnType != null) {
               TypeBinding ret = this.returnType.leafComponentType();
               if ((ret.tagBits & 2048L) != 0L) {
                  Util.recordNestedType(classFile, ret);
               }
            }
         }

         return this.signature;
      } else {
         StringBuffer buffer = new StringBuffer((this.parameters.length + 1) * 20);
         buffer.append('(');
         targetParameters = this.parameters;
         needSynthetics = this.isConstructor();
         if (needSynthetics && this.declaringClass.isEnum()) {
            buffer.append(ConstantPool.JavaLangStringSignature);
            buffer.append(TypeBinding.INT.signature());
         }

         boolean needSynthetics = needSynthetics && this.declaringClass.isNestedType();
         int i;
         if (needSynthetics) {
            ReferenceBinding[] syntheticArgumentTypes = this.declaringClass.syntheticEnclosingInstanceTypes();
            if (syntheticArgumentTypes != null) {
               count = 0;

               for(i = syntheticArgumentTypes.length; count < i; ++count) {
                  ReferenceBinding syntheticArgumentType = syntheticArgumentTypes[count];
                  if ((syntheticArgumentType.tagBits & 2048L) != 0L) {
                     this.tagBits |= 2048L;
                     Util.recordNestedType(classFile, syntheticArgumentType);
                  }

                  buffer.append(syntheticArgumentType.signature());
               }
            }

            if (this instanceof SyntheticMethodBinding) {
               targetParameters = ((SyntheticMethodBinding)this).targetMethod.parameters;
            }
         }

         if (targetParameters != Binding.NO_PARAMETERS) {
            nameLength = 0;

            for(count = targetParameters.length; nameLength < count; ++nameLength) {
               leafParameterType = targetParameters[nameLength];
               TypeBinding leafTargetParameterType = leafParameterType.leafComponentType();
               if ((leafTargetParameterType.tagBits & 2048L) != 0L) {
                  this.tagBits |= 2048L;
                  Util.recordNestedType(classFile, leafTargetParameterType);
               }

               buffer.append(leafParameterType.signature());
            }
         }

         if (needSynthetics) {
            SyntheticArgumentBinding[] syntheticOuterArguments = this.declaringClass.syntheticOuterLocalVariables();
            count = syntheticOuterArguments == null ? 0 : syntheticOuterArguments.length;

            for(i = 0; i < count; ++i) {
               buffer.append(syntheticOuterArguments[i].type.signature());
            }

            i = targetParameters.length;

            for(int extraLength = this.parameters.length; i < extraLength; ++i) {
               TypeBinding parameter = this.parameters[i];
               TypeBinding leafParameterType = parameter.leafComponentType();
               if ((leafParameterType.tagBits & 2048L) != 0L) {
                  this.tagBits |= 2048L;
                  Util.recordNestedType(classFile, leafParameterType);
               }

               buffer.append(parameter.signature());
            }
         }

         buffer.append(')');
         if (this.returnType != null) {
            TypeBinding ret = this.returnType.leafComponentType();
            if ((ret.tagBits & 2048L) != 0L) {
               this.tagBits |= 2048L;
               Util.recordNestedType(classFile, ret);
            }

            buffer.append(this.returnType.signature());
         }

         nameLength = buffer.length();
         this.signature = new char[nameLength];
         buffer.getChars(0, nameLength, this.signature, 0);
         return this.signature;
      }
   }

   public final int sourceEnd() {
      AbstractMethodDeclaration method = this.sourceMethod();
      if (method == null) {
         return this.declaringClass instanceof SourceTypeBinding ? ((SourceTypeBinding)this.declaringClass).sourceEnd() : 0;
      } else {
         return method.sourceEnd;
      }
   }

   public AbstractMethodDeclaration sourceMethod() {
      if (this.isSynthetic()) {
         return null;
      } else {
         SourceTypeBinding sourceType;
         try {
            sourceType = (SourceTypeBinding)this.declaringClass;
         } catch (ClassCastException var4) {
            return null;
         }

         AbstractMethodDeclaration[] methods = sourceType.scope != null ? sourceType.scope.referenceContext.methods : null;
         if (methods != null) {
            int i = methods.length;

            while(true) {
               --i;
               if (i < 0) {
                  break;
               }

               if (this == methods[i].binding) {
                  return methods[i];
               }
            }
         }

         return null;
      }
   }

   public LambdaExpression sourceLambda() {
      return null;
   }

   public final int sourceStart() {
      AbstractMethodDeclaration method = this.sourceMethod();
      if (method == null) {
         return this.declaringClass instanceof SourceTypeBinding ? ((SourceTypeBinding)this.declaringClass).sourceStart() : 0;
      } else {
         return method.sourceStart;
      }
   }

   public MethodBinding tiebreakMethod() {
      return this;
   }

   public String toString() {
      StringBuffer output = new StringBuffer(10);
      if ((this.modifiers & 33554432) != 0) {
         output.append("[unresolved] ");
      }

      ASTNode.printModifiers(this.modifiers, output);
      output.append(this.returnType != null ? this.returnType.debugName() : "<no type>");
      output.append(" ");
      output.append(this.selector != null ? new String(this.selector) : "<no selector>");
      output.append("(");
      int i;
      int length;
      if (this.parameters != null) {
         if (this.parameters != Binding.NO_PARAMETERS) {
            i = 0;

            for(length = this.parameters.length; i < length; ++i) {
               if (i > 0) {
                  output.append(", ");
               }

               output.append(this.parameters[i] != null ? this.parameters[i].debugName() : "<no argument type>");
            }
         }
      } else {
         output.append("<no argument types>");
      }

      output.append(") ");
      if (this.thrownExceptions != null) {
         if (this.thrownExceptions != Binding.NO_EXCEPTIONS) {
            output.append("throws ");
            i = 0;

            for(length = this.thrownExceptions.length; i < length; ++i) {
               if (i > 0) {
                  output.append(", ");
               }

               output.append(this.thrownExceptions[i] != null ? this.thrownExceptions[i].debugName() : "<no exception type>");
            }
         }
      } else {
         output.append("<no exception types>");
      }

      return output.toString();
   }

   public TypeVariableBinding[] typeVariables() {
      return this.typeVariables;
   }

   public boolean hasNonNullDefaultForReturnType(AbstractMethodDeclaration srcMethod) {
      return this.hasNonNullDefaultFor(16, srcMethod, srcMethod == null ? -1 : srcMethod.declarationSourceStart);
   }

   static int getNonNullByDefaultValue(AnnotationBinding annotation) {
      ElementValuePair[] elementValuePairs = annotation.getElementValuePairs();
      if (elementValuePairs != null && elementValuePairs.length != 0) {
         if (elementValuePairs.length <= 0) {
            return 2;
         } else {
            int nullness = 0;

            for(int i = 0; i < elementValuePairs.length; ++i) {
               nullness |= Annotation.nullLocationBitsFromAnnotationValue(elementValuePairs[i].getValue());
            }

            return nullness;
         }
      } else {
         ReferenceBinding annotationType = annotation.getAnnotationType();
         if (annotationType == null) {
            return 0;
         } else {
            MethodBinding[] annotationMethods = annotationType.methods();
            if (annotationMethods != null && annotationMethods.length == 1) {
               Object value = annotationMethods[0].getDefaultValue();
               return Annotation.nullLocationBitsFromAnnotationValue(value);
            } else {
               return 56;
            }
         }
      }
   }

   public ParameterNonNullDefaultProvider hasNonNullDefaultForParameter(AbstractMethodDeclaration srcMethod) {
      int len = this.parameters.length;
      boolean[] result = new boolean[len];
      boolean trueFound = false;
      boolean falseFound = false;

      for(int i = 0; i < len; ++i) {
         int start = srcMethod != null && srcMethod.arguments != null && srcMethod.arguments.length != 0 ? srcMethod.arguments[i].declarationSourceStart : -1;
         int nonNullByDefaultValue = srcMethod != null && start >= 0 ? srcMethod.scope.localNonNullByDefaultValue(start) : 0;
         if (nonNullByDefaultValue == 0) {
            AnnotationBinding[][] parameterAnnotations = this.getParameterAnnotations();
            if (parameterAnnotations != null) {
               AnnotationBinding[] annotationBindings = parameterAnnotations[i];
               AnnotationBinding[] var14 = annotationBindings;
               int var13 = annotationBindings.length;

               for(int var12 = 0; var12 < var13; ++var12) {
                  AnnotationBinding annotationBinding = var14[var12];
                  ReferenceBinding annotationType = annotationBinding.getAnnotationType();
                  if (annotationType.hasNullBit(128)) {
                     nonNullByDefaultValue |= getNonNullByDefaultValue(annotationBinding);
                  }
               }
            }
         }

         boolean b;
         if (nonNullByDefaultValue != 0) {
            b = (nonNullByDefaultValue & 8) != 0;
         } else {
            b = this.hasNonNullDefaultFor(8, srcMethod, start);
         }

         if (b) {
            trueFound = true;
         } else {
            falseFound = true;
         }

         result[i] = b;
      }

      if (trueFound && falseFound) {
         return new ParameterNonNullDefaultProvider.MixedProvider(result);
      } else {
         return trueFound ? ParameterNonNullDefaultProvider.TRUE_PROVIDER : ParameterNonNullDefaultProvider.FALSE_PROVIDER;
      }
   }

   private boolean hasNonNullDefaultFor(int location, AbstractMethodDeclaration srcMethod, int start) {
      if ((this.modifiers & 67108864) != 0) {
         return false;
      } else if (this.defaultNullness != 0) {
         return (this.defaultNullness & location) != 0;
      } else {
         return this.declaringClass.hasNonNullDefaultFor(location, start);
      }
   }

   public boolean redeclaresPublicObjectMethod(Scope scope) {
      ReferenceBinding javaLangObject = scope.getJavaLangObject();
      MethodBinding[] methods = javaLangObject.getMethods(this.selector);
      int i = 0;

      for(int length = methods == null ? 0 : methods.length; i < length; ++i) {
         MethodBinding method = methods[i];
         if (method.isPublic() && !method.isStatic() && method.parameters.length == this.parameters.length && MethodVerifier.doesMethodOverride(this, method, scope.environment())) {
            return true;
         }
      }

      return false;
   }

   public boolean isVoidMethod() {
      return this.returnType == TypeBinding.VOID;
   }

   public boolean doesParameterLengthMatch(int suggestedParameterLength) {
      int len = this.parameters.length;
      return len <= suggestedParameterLength || this.isVarargs() && len == suggestedParameterLength + 1;
   }

   public void updateTypeVariableBinding(TypeVariableBinding previousBinding, TypeVariableBinding updatedBinding) {
      TypeVariableBinding[] bindings = this.typeVariables;
      if (bindings != null) {
         for(int i = 0; i < bindings.length; ++i) {
            if (bindings[i] == previousBinding) {
               bindings[i] = updatedBinding;
            }
         }
      }

   }
}

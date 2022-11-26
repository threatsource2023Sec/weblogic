package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRestriction;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ArrayBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.InferenceContext18;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.InvocationSite;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedGenericMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeIds;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.WildcardBinding;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class ASTNode implements TypeConstants, TypeIds {
   public int sourceStart;
   public int sourceEnd;
   public static final int Bit1 = 1;
   public static final int Bit2 = 2;
   public static final int Bit3 = 4;
   public static final int Bit4 = 8;
   public static final int Bit5 = 16;
   public static final int Bit6 = 32;
   public static final int Bit7 = 64;
   public static final int Bit8 = 128;
   public static final int Bit9 = 256;
   public static final int Bit10 = 512;
   public static final int Bit11 = 1024;
   public static final int Bit12 = 2048;
   public static final int Bit13 = 4096;
   public static final int Bit14 = 8192;
   public static final int Bit15 = 16384;
   public static final int Bit16 = 32768;
   public static final int Bit17 = 65536;
   public static final int Bit18 = 131072;
   public static final int Bit19 = 262144;
   public static final int Bit20 = 524288;
   public static final int Bit21 = 1048576;
   public static final int Bit22 = 2097152;
   public static final int Bit23 = 4194304;
   public static final int Bit24 = 8388608;
   public static final int Bit25 = 16777216;
   public static final int Bit26 = 33554432;
   public static final int Bit27 = 67108864;
   public static final int Bit28 = 134217728;
   public static final int Bit29 = 268435456;
   public static final int Bit30 = 536870912;
   public static final int Bit31 = 1073741824;
   public static final int Bit32 = Integer.MIN_VALUE;
   public static final long Bit32L = 2147483648L;
   public static final long Bit33L = 4294967296L;
   public static final long Bit34L = 8589934592L;
   public static final long Bit35L = 17179869184L;
   public static final long Bit36L = 34359738368L;
   public static final long Bit37L = 68719476736L;
   public static final long Bit38L = 137438953472L;
   public static final long Bit39L = 274877906944L;
   public static final long Bit40L = 549755813888L;
   public static final long Bit41L = 1099511627776L;
   public static final long Bit42L = 2199023255552L;
   public static final long Bit43L = 4398046511104L;
   public static final long Bit44L = 8796093022208L;
   public static final long Bit45L = 17592186044416L;
   public static final long Bit46L = 35184372088832L;
   public static final long Bit47L = 70368744177664L;
   public static final long Bit48L = 140737488355328L;
   public static final long Bit49L = 281474976710656L;
   public static final long Bit50L = 562949953421312L;
   public static final long Bit51L = 1125899906842624L;
   public static final long Bit52L = 2251799813685248L;
   public static final long Bit53L = 4503599627370496L;
   public static final long Bit54L = 9007199254740992L;
   public static final long Bit55L = 18014398509481984L;
   public static final long Bit56L = 36028797018963968L;
   public static final long Bit57L = 72057594037927936L;
   public static final long Bit58L = 144115188075855872L;
   public static final long Bit59L = 288230376151711744L;
   public static final long Bit60L = 576460752303423488L;
   public static final long Bit61L = 1152921504606846976L;
   public static final long Bit62L = 2305843009213693952L;
   public static final long Bit63L = 4611686018427387904L;
   public static final long Bit64L = Long.MIN_VALUE;
   public int bits = Integer.MIN_VALUE;
   public static final int ReturnTypeIDMASK = 15;
   public static final int OperatorSHIFT = 6;
   public static final int OperatorMASK = 4032;
   public static final int IsReturnedValue = 16;
   public static final int UnnecessaryCast = 16384;
   public static final int DisableUnnecessaryCastCheck = 32;
   public static final int GenerateCheckcast = 64;
   public static final int UnsafeCast = 128;
   public static final int RestrictiveFlagMASK = 7;
   public static final int IsTypeElided = 2;
   public static final int IsArgument = 4;
   public static final int IsLocalDeclarationReachable = 1073741824;
   public static final int IsForeachElementVariable = 16;
   public static final int ShadowsOuterLocal = 2097152;
   public static final int IsAdditionalDeclarator = 4194304;
   public static final int FirstAssignmentToLocal = 8;
   public static final int NeedReceiverGenericCast = 262144;
   public static final int IsImplicitThis = 4;
   public static final int DepthSHIFT = 5;
   public static final int DepthMASK = 8160;
   public static final int IsCapturedOuterLocal = 524288;
   public static final int IsReachable = Integer.MIN_VALUE;
   public static final int LabelUsed = 64;
   public static final int DocumentedFallthrough = 536870912;
   public static final int DocumentedCasesOmitted = 1073741824;
   public static final int IsSubRoutineEscaping = 16384;
   public static final int IsTryBlockExiting = 536870912;
   public static final int ContainsAssertion = 1;
   public static final int IsLocalType = 256;
   public static final int IsAnonymousType = 512;
   public static final int IsMemberType = 1024;
   public static final int HasAbstractMethods = 2048;
   public static final int IsSecondaryType = 4096;
   public static final int HasBeenGenerated = 8192;
   public static final int HasLocalType = 2;
   public static final int HasBeenResolved = 16;
   public static final int ParenthesizedSHIFT = 21;
   public static final int ParenthesizedMASK = 534773760;
   public static final int IgnoreNoEffectAssignCheck = 536870912;
   public static final int IsStrictlyAssigned = 8192;
   public static final int IsCompoundAssigned = 65536;
   public static final int DiscardEnclosingInstance = 8192;
   public static final int Unchecked = 65536;
   public static final int ResolveJavadoc = 65536;
   public static final int IsUsefulEmptyStatement = 1;
   public static final int UndocumentedEmptyBlock = 8;
   public static final int OverridingMethodWithSupercall = 16;
   public static final int CanBeStatic = 256;
   public static final int ErrorInSignature = 32;
   public static final int NeedFreeReturn = 64;
   public static final int IsDefaultConstructor = 128;
   public static final int HasAllMethodBodies = 16;
   public static final int IsImplicitUnit = 1;
   public static final int InsideJavadoc = 32768;
   public static final int SuperAccess = 16384;
   public static final int Empty = 262144;
   public static final int IsElseIfStatement = 536870912;
   public static final int ThenExit = 1073741824;
   public static final int IsElseStatementUnreachable = 128;
   public static final int IsThenStatementUnreachable = 256;
   public static final int IsSuperType = 16;
   public static final int IsVarArgs = 16384;
   public static final int IgnoreRawTypeCheck = 1073741824;
   public static final int IsAnnotationDefaultValue = 1;
   public static final int IsNonNull = 131072;
   public static final int NeededScope = 536870912;
   public static final int OnDemand = 131072;
   public static final int Used = 2;
   public static final int inModule = 262144;
   public static final int DidResolve = 262144;
   public static final int IsAnySubRoutineEscaping = 536870912;
   public static final int IsSynchronized = 1073741824;
   public static final int BlockExit = 536870912;
   public static final int IsRecovered = 32;
   public static final int HasSyntaxErrors = 524288;
   public static final int INVOCATION_ARGUMENT_OK = 0;
   public static final int INVOCATION_ARGUMENT_UNCHECKED = 1;
   public static final int INVOCATION_ARGUMENT_WILDCARD = 2;
   public static final int HasTypeAnnotations = 1048576;
   public static final int IsUnionType = 536870912;
   public static final int IsDiamond = 524288;
   public static final int InsideExpressionStatement = 1048576;
   public static final int IsSynthetic = 64;
   public static final int HasFunctionalInterfaceTypes = 2097152;
   public static final Argument[] NO_ARGUMENTS = new Argument[0];

   private static int checkInvocationArgument(BlockScope scope, Expression argument, TypeBinding parameterType, TypeBinding argumentType, TypeBinding originalParameterType) {
      argument.computeConversion(scope, parameterType, argumentType);
      if (argumentType != TypeBinding.NULL && parameterType.kind() == 516) {
         WildcardBinding wildcard = (WildcardBinding)parameterType;
         if (wildcard.boundKind != 2) {
            return 2;
         }
      }

      if (TypeBinding.notEquals(argumentType, parameterType) && argumentType.needsUncheckedConversion(parameterType)) {
         scope.problemReporter().unsafeTypeConversion(argument, argumentType, parameterType);
         return 1;
      } else {
         return 0;
      }
   }

   public static boolean checkInvocationArguments(BlockScope scope, Expression receiver, TypeBinding receiverType, MethodBinding method, Expression[] arguments, TypeBinding[] argumentTypes, boolean argsContainCast, InvocationSite invocationSite) {
      long sourceLevel = scope.compilerOptions().sourceLevel;
      boolean is1_7 = sourceLevel >= 3342336L;
      TypeBinding[] params = method.parameters;
      int paramLength = params.length;
      boolean isRawMemberInvocation = !method.isStatic() && !receiverType.isUnboundWildcard() && method.declaringClass.isRawType() && method.hasSubstitutedParameters();
      boolean uncheckedBoundCheck = (method.tagBits & 256L) != 0L;
      MethodBinding rawOriginalGenericMethod = null;
      if (!isRawMemberInvocation && method instanceof ParameterizedGenericMethodBinding) {
         ParameterizedGenericMethodBinding paramMethod = (ParameterizedGenericMethodBinding)method;
         if (paramMethod.isRaw && method.hasSubstitutedParameters()) {
            rawOriginalGenericMethod = method.original();
         }
      }

      int invocationStatus = 0;
      if (arguments == null) {
         if (method.isVarargs()) {
            TypeBinding parameterType = ((ArrayBinding)params[paramLength - 1]).elementsType();
            if (!parameterType.isReifiable() && (!is1_7 || (method.tagBits & 2251799813685248L) == 0L)) {
               scope.problemReporter().unsafeGenericArrayForVarargs(parameterType, (ASTNode)invocationSite);
            }
         }
      } else {
         int lastIndex;
         if (!method.isVarargs()) {
            for(lastIndex = 0; lastIndex < paramLength; ++lastIndex) {
               TypeBinding originalRawParam = rawOriginalGenericMethod == null ? null : rawOriginalGenericMethod.parameters[lastIndex];
               invocationStatus |= checkInvocationArgument(scope, arguments[lastIndex], params[lastIndex], argumentTypes[lastIndex], originalRawParam);
            }
         } else {
            lastIndex = paramLength - 1;

            int argLength;
            TypeBinding parameterType;
            for(argLength = 0; argLength < lastIndex; ++argLength) {
               parameterType = rawOriginalGenericMethod == null ? null : rawOriginalGenericMethod.parameters[argLength];
               invocationStatus |= checkInvocationArgument(scope, arguments[argLength], params[argLength], argumentTypes[argLength], parameterType);
            }

            argLength = arguments.length;
            if (lastIndex <= argLength) {
               parameterType = params[lastIndex];
               TypeBinding originalRawParam = null;
               if (paramLength != argLength || parameterType.dimensions() != argumentTypes[lastIndex].dimensions()) {
                  parameterType = ((ArrayBinding)parameterType).elementsType();
                  if (!parameterType.isReifiable() && (!is1_7 || (method.tagBits & 2251799813685248L) == 0L)) {
                     scope.problemReporter().unsafeGenericArrayForVarargs(parameterType, (ASTNode)invocationSite);
                  }

                  originalRawParam = rawOriginalGenericMethod == null ? null : ((ArrayBinding)rawOriginalGenericMethod.parameters[lastIndex]).elementsType();
               }

               for(int i = lastIndex; i < argLength; ++i) {
                  invocationStatus |= checkInvocationArgument(scope, arguments[i], parameterType, argumentTypes[i], originalRawParam);
               }
            }

            if (paramLength == argLength) {
               int varargsIndex = paramLength - 1;
               ArrayBinding varargsType = (ArrayBinding)params[varargsIndex];
               TypeBinding lastArgType = argumentTypes[varargsIndex];
               if (lastArgType == TypeBinding.NULL) {
                  if (!varargsType.leafComponentType().isBaseType() || varargsType.dimensions() != 1) {
                     scope.problemReporter().varargsArgumentNeedCast(method, lastArgType, invocationSite);
                  }
               } else {
                  int dimensions;
                  if (varargsType.dimensions <= (dimensions = lastArgType.dimensions())) {
                     if (lastArgType.leafComponentType().isBaseType()) {
                        --dimensions;
                     }

                     if (varargsType.dimensions < dimensions) {
                        scope.problemReporter().varargsArgumentNeedCast(method, lastArgType, invocationSite);
                     } else if (varargsType.dimensions == dimensions && TypeBinding.notEquals(lastArgType, varargsType) && TypeBinding.notEquals(lastArgType.leafComponentType().erasure(), varargsType.leafComponentType.erasure()) && lastArgType.isCompatibleWith(varargsType.elementsType()) && lastArgType.isCompatibleWith(varargsType)) {
                        scope.problemReporter().varargsArgumentNeedCast(method, lastArgType, invocationSite);
                     }
                  }
               }
            }
         }

         if (argsContainCast) {
            CastExpression.checkNeedForArgumentCasts(scope, receiver, receiverType, method, arguments, argumentTypes, invocationSite);
         }
      }

      if ((invocationStatus & 2) != 0) {
         scope.problemReporter().wildcardInvocation((ASTNode)invocationSite, receiverType, method, argumentTypes);
      } else if (!method.isStatic() && !receiverType.isUnboundWildcard() && method.declaringClass.isRawType() && method.hasSubstitutedParameters()) {
         if (scope.compilerOptions().reportUnavoidableGenericTypeProblems || receiver == null || !receiver.forcedToBeRaw(scope.referenceContext())) {
            scope.problemReporter().unsafeRawInvocation((ASTNode)invocationSite, method);
         }
      } else if (rawOriginalGenericMethod != null || uncheckedBoundCheck || (invocationStatus & 1) != 0) {
         if (method instanceof ParameterizedGenericMethodBinding) {
            scope.problemReporter().unsafeRawGenericMethodInvocation((ASTNode)invocationSite, method, argumentTypes);
            return true;
         }

         if (sourceLevel >= 3407872L) {
            return true;
         }
      }

      return false;
   }

   public ASTNode concreteStatement() {
      return this;
   }

   public final boolean isFieldUseDeprecated(FieldBinding field, Scope scope, int filteredBits) {
      if ((this.bits & '耀') == 0 && (filteredBits & 8192) == 0 && field.isOrEnclosedByPrivateType() && !scope.isDefinedInField(field)) {
         if ((filteredBits & 65536) != 0) {
            ++field.original().compoundUseFlag;
         } else {
            FieldBinding var10000 = field.original();
            var10000.modifiers |= 134217728;
         }
      }

      if ((field.modifiers & 262144) != 0) {
         ModuleBinding module = field.declaringClass.module();
         LookupEnvironment env = module == null ? scope.environment() : module.environment;
         AccessRestriction restriction = env.getAccessRestriction(field.declaringClass.erasure());
         if (restriction != null) {
            scope.problemReporter().forbiddenReference(field, this, restriction.classpathEntryType, restriction.classpathEntryName, restriction.getProblemId());
         }
      }

      if (!field.isViewedAsDeprecated()) {
         return false;
      } else if (scope.isDefinedInSameUnit(field.declaringClass)) {
         return false;
      } else {
         return scope.compilerOptions().reportDeprecationInsideDeprecatedCode || !scope.isInsideDeprecatedCode();
      }
   }

   public boolean isImplicitThis() {
      return false;
   }

   public boolean receiverIsImplicitThis() {
      return false;
   }

   public final boolean isMethodUseDeprecated(MethodBinding method, Scope scope, boolean isExplicitUse, InvocationSite invocation) {
      if ((this.bits & '耀') == 0 && method.isOrEnclosedByPrivateType() && !scope.isDefinedInMethod(method)) {
         MethodBinding var10000 = method.original();
         var10000.modifiers |= 134217728;
      }

      if (isExplicitUse && (method.modifiers & 262144) != 0) {
         ModuleBinding module = method.declaringClass.module();
         LookupEnvironment env = module == null ? scope.environment() : module.environment;
         AccessRestriction restriction = env.getAccessRestriction(method.declaringClass.erasure());
         if (restriction != null) {
            scope.problemReporter().forbiddenReference(method, invocation, restriction.classpathEntryType, restriction.classpathEntryName, restriction.getProblemId());
         }
      }

      if (!method.isViewedAsDeprecated()) {
         return false;
      } else if (scope.isDefinedInSameUnit(method.declaringClass)) {
         return false;
      } else if (!isExplicitUse && (method.modifiers & 1048576) == 0) {
         return false;
      } else {
         return scope.compilerOptions().reportDeprecationInsideDeprecatedCode || !scope.isInsideDeprecatedCode();
      }
   }

   public boolean isSuper() {
      return false;
   }

   public boolean isQualifiedSuper() {
      return false;
   }

   public boolean isThis() {
      return false;
   }

   public boolean isUnqualifiedSuper() {
      return false;
   }

   public final boolean isTypeUseDeprecated(TypeBinding type, Scope scope) {
      if (type.isArrayType()) {
         type = ((ArrayBinding)type).leafComponentType;
      }

      if (type.isBaseType()) {
         return false;
      } else {
         ReferenceBinding refType = (ReferenceBinding)type;
         if ((this.bits & '耀') == 0 && refType instanceof TypeVariableBinding) {
            refType.modifiers |= 134217728;
         }

         if ((this.bits & '耀') == 0 && refType.isOrEnclosedByPrivateType() && !scope.isDefinedInType(refType)) {
            ReferenceBinding var10000 = (ReferenceBinding)refType.erasure();
            var10000.modifiers |= 134217728;
         }

         if (refType.hasRestrictedAccess()) {
            ModuleBinding module = refType.module();
            LookupEnvironment env = module == null ? scope.environment() : module.environment;
            AccessRestriction restriction = env.getAccessRestriction(type.erasure());
            if (restriction != null) {
               scope.problemReporter().forbiddenReference(type, this, restriction.classpathEntryType, restriction.classpathEntryName, restriction.getProblemId());
            }
         }

         refType.initializeDeprecatedAnnotationTagBits();
         if (!refType.isViewedAsDeprecated()) {
            return false;
         } else if (scope.isDefinedInSameUnit(refType)) {
            return false;
         } else {
            return scope.compilerOptions().reportDeprecationInsideDeprecatedCode || !scope.isInsideDeprecatedCode();
         }
      }
   }

   public abstract StringBuffer print(int var1, StringBuffer var2);

   public static StringBuffer printAnnotations(Annotation[] annotations, StringBuffer output) {
      int length = annotations.length;

      for(int i = 0; i < length; ++i) {
         if (i > 0) {
            output.append(" ");
         }

         Annotation annotation2 = annotations[i];
         if (annotation2 != null) {
            annotation2.print(0, output);
         } else {
            output.append('?');
         }
      }

      return output;
   }

   public static StringBuffer printIndent(int indent, StringBuffer output) {
      for(int i = indent; i > 0; --i) {
         output.append("  ");
      }

      return output;
   }

   public static StringBuffer printModifiers(int modifiers, StringBuffer output) {
      if ((modifiers & 1) != 0) {
         output.append("public ");
      }

      if ((modifiers & 2) != 0) {
         output.append("private ");
      }

      if ((modifiers & 4) != 0) {
         output.append("protected ");
      }

      if ((modifiers & 8) != 0) {
         output.append("static ");
      }

      if ((modifiers & 16) != 0) {
         output.append("final ");
      }

      if ((modifiers & 32) != 0) {
         output.append("synchronized ");
      }

      if ((modifiers & 64) != 0) {
         output.append("volatile ");
      }

      if ((modifiers & 128) != 0) {
         output.append("transient ");
      }

      if ((modifiers & 256) != 0) {
         output.append("native ");
      }

      if ((modifiers & 1024) != 0) {
         output.append("abstract ");
      }

      if ((modifiers & 65536) != 0) {
         output.append("default ");
      }

      return output;
   }

   public static MethodBinding resolvePolyExpressionArguments(Invocation invocation, MethodBinding method, TypeBinding[] argumentTypes, BlockScope scope) {
      MethodBinding candidateMethod = method.isValidBinding() ? method : (method instanceof ProblemMethodBinding ? ((ProblemMethodBinding)method).closestMatch : null);
      if (candidateMethod == null) {
         return method;
      } else {
         ProblemMethodBinding problemMethod = null;
         boolean variableArity = candidateMethod.isVarargs();
         TypeBinding[] parameters = candidateMethod.parameters;
         Expression[] arguments = invocation.arguments();
         if (variableArity && arguments != null && parameters.length == arguments.length && arguments[arguments.length - 1].isCompatibleWith(parameters[parameters.length - 1], scope)) {
            variableArity = false;
         }

         int i = 0;

         for(int length = arguments == null ? 0 : arguments.length; i < length; ++i) {
            Expression argument = arguments[i];
            TypeBinding parameterType = InferenceContext18.getParameter(parameters, i, variableArity);
            if (parameterType != null && argumentTypes[i] != null && argumentTypes[i].isPolyType()) {
               argument.setExpectedType(parameterType);
               TypeBinding updatedArgumentType;
               if (argument instanceof LambdaExpression) {
                  LambdaExpression lambda = (LambdaExpression)argument;
                  boolean skipKosherCheck = method.problemId() == 3;
                  updatedArgumentType = lambda.resolveType(scope, skipKosherCheck);
                  if (lambda.hasErrors() || lambda.hasDescripterProblem) {
                     continue;
                  }

                  if (!lambda.isCompatibleWith(parameterType, scope)) {
                     if (method.isValidBinding() && problemMethod == null) {
                        TypeBinding[] originalArguments = (TypeBinding[])Arrays.copyOf(argumentTypes, argumentTypes.length);
                        if (lambda.reportShapeError(parameterType, scope)) {
                           problemMethod = new ProblemMethodBinding(candidateMethod, method.selector, originalArguments, 31);
                        } else {
                           problemMethod = new ProblemMethodBinding(candidateMethod, method.selector, originalArguments, 1);
                        }
                     }
                     continue;
                  }

                  lambda.updateLocalTypesInMethod(candidateMethod);
               } else {
                  updatedArgumentType = argument.resolveType(scope);
               }

               if (updatedArgumentType != null && updatedArgumentType.kind() != 65540) {
                  argumentTypes[i] = updatedArgumentType;
                  if (candidateMethod.isPolymorphic()) {
                     candidateMethod.parameters[i] = updatedArgumentType;
                  }
               }
            }
         }

         if (method instanceof ParameterizedGenericMethodBinding) {
            InferenceContext18 ic18 = invocation.getInferenceContext((ParameterizedMethodBinding)method);
            if (ic18 != null) {
               ic18.flushBoundOutbox();
            }
         }

         if (problemMethod != null) {
            return problemMethod;
         } else {
            return method;
         }
      }
   }

   public static void resolveAnnotations(BlockScope scope, Annotation[] sourceAnnotations, Binding recipient) {
      resolveAnnotations(scope, sourceAnnotations, recipient, false);
      if (recipient instanceof SourceTypeBinding) {
         ((SourceTypeBinding)recipient).evaluateNullAnnotations();
      }

   }

   public static AnnotationBinding[] resolveAnnotations(BlockScope scope, Annotation[] sourceAnnotations, Binding recipient, boolean copySE8AnnotationsToType) {
      AnnotationBinding[] annotations = null;
      int length = sourceAnnotations == null ? 0 : sourceAnnotations.length;
      FieldBinding field;
      LocalVariableBinding local;
      if (recipient != null) {
         switch (recipient.kind()) {
            case 1:
               field = (FieldBinding)recipient;
               if ((field.tagBits & 8589934592L) != 0L) {
                  return annotations;
               }

               field.tagBits |= 25769803776L;
               if (length > 0) {
                  annotations = new AnnotationBinding[length];
                  field.setAnnotations(annotations, false);
               }
               break;
            case 2:
               local = (LocalVariableBinding)recipient;
               if ((local.tagBits & 8589934592L) != 0L) {
                  return annotations;
               }

               local.tagBits |= 25769803776L;
               if (length > 0) {
                  annotations = new AnnotationBinding[length];
                  local.setAnnotations(annotations, scope, false);
               }
               break;
            case 4:
            case 2052:
               ReferenceBinding type = (ReferenceBinding)recipient;
               if ((type.tagBits & 8589934592L) != 0L) {
                  return annotations;
               }

               type.tagBits |= 25769803776L;
               if (length > 0) {
                  annotations = new AnnotationBinding[length];
                  type.setAnnotations(annotations, false);
               }
               break;
            case 8:
               MethodBinding method = (MethodBinding)recipient;
               if ((method.tagBits & 8589934592L) != 0L) {
                  return annotations;
               }

               method.tagBits |= 25769803776L;
               if (length > 0) {
                  annotations = new AnnotationBinding[length];
                  method.setAnnotations(annotations, false);
               }
               break;
            case 16:
               PackageBinding packageBinding = (PackageBinding)recipient;
               if ((packageBinding.tagBits & 8589934592L) != 0L) {
                  return annotations;
               }

               packageBinding.tagBits |= 25769803776L;
               break;
            case 64:
               ModuleBinding module = (ModuleBinding)recipient;
               if ((module.tagBits & 8589934592L) != 0L) {
                  return annotations;
               }

               module.tagBits |= 25769803776L;
               if (length > 0) {
                  annotations = new AnnotationBinding[length];
                  module.setAnnotations(annotations, scope, false);
               }
               break;
            case 4100:
            case 16388:
               annotations = new AnnotationBinding[length];
               break;
            default:
               return annotations;
         }
      }

      if (sourceAnnotations == null) {
         return annotations;
      } else {
         int j;
         int i;
         Annotation annotation;
         for(i = 0; i < length; ++i) {
            annotation = sourceAnnotations[i];
            Binding annotationRecipient = annotation.recipient;
            if (annotationRecipient != null && recipient != null) {
               switch (recipient.kind()) {
                  case 1:
                     field = (FieldBinding)recipient;
                     field.tagBits = ((FieldBinding)annotationRecipient).tagBits;
                     if (annotations != null) {
                        for(int j = 0; j < length; ++j) {
                           Annotation annot = sourceAnnotations[j];
                           annotations[j] = annot.getCompilerAnnotation();
                        }
                     }
                     break;
                  case 2:
                     local = (LocalVariableBinding)recipient;
                     long otherLocalTagBits = ((LocalVariableBinding)annotationRecipient).tagBits;
                     local.tagBits = otherLocalTagBits;
                     if ((otherLocalTagBits & 1125899906842624L) == 0L) {
                        if (annotations != null) {
                           for(j = 0; j < length; ++j) {
                              Annotation annot = sourceAnnotations[j];
                              annotations[j] = annot.getCompilerAnnotation();
                           }
                        }
                     } else if (annotations != null) {
                        LocalDeclaration localDeclaration = local.declaration;
                        int declarationSourceEnd = localDeclaration.declarationSourceEnd;
                        int declarationSourceStart = localDeclaration.declarationSourceStart;

                        for(int j = 0; j < length; ++j) {
                           Annotation annot = sourceAnnotations[j];
                           AnnotationBinding annotationBinding = annot.getCompilerAnnotation();
                           annotations[j] = annotationBinding;
                           if (annotationBinding != null) {
                              ReferenceBinding annotationType = annotationBinding.getAnnotationType();
                              if (annotationType != null && annotationType.id == 49) {
                                 annot.recordSuppressWarnings(scope, declarationSourceStart, declarationSourceEnd, scope.compilerOptions().suppressWarnings);
                              }
                           }
                        }
                     }
                     break;
                  case 16388:
                     if (annotations != null) {
                        for(int j = 0; j < length; ++j) {
                           annotations[j] = sourceAnnotations[j].getCompilerAnnotation();
                        }
                     }
               }

               return annotations;
            }

            annotation.recipient = recipient;
            annotation.resolveType(scope);
            if (annotations != null) {
               annotations[i] = annotation.getCompilerAnnotation();
            }
         }

         if (recipient != null && recipient.isTaggedRepeatable()) {
            for(i = 0; i < length; ++i) {
               annotation = sourceAnnotations[i];
               ReferenceBinding annotationType = annotations[i] != null ? annotations[i].getAnnotationType() : null;
               if (annotationType != null && annotationType.id == 90) {
                  annotation.checkRepeatableMetaAnnotation(scope);
               }
            }
         }

         if (annotations != null && length > 1) {
            AnnotationBinding[] distinctAnnotations = annotations;
            Map implicitContainerAnnotations = null;

            int i;
            ReferenceBinding annotationType;
            for(i = 0; i < length; ++i) {
               AnnotationBinding annotation = distinctAnnotations[i];
               if (annotation != null) {
                  annotationType = annotation.getAnnotationType();
                  boolean foundDuplicate = false;
                  ContainerAnnotation container = null;

                  for(j = i + 1; j < length; ++j) {
                     AnnotationBinding otherAnnotation = distinctAnnotations[j];
                     if (otherAnnotation != null && TypeBinding.equalsEquals(otherAnnotation.getAnnotationType(), annotationType)) {
                        if (distinctAnnotations == annotations) {
                           System.arraycopy(distinctAnnotations, 0, distinctAnnotations = new AnnotationBinding[length], 0, length);
                        }

                        distinctAnnotations[j] = null;
                        if (annotationType.isRepeatableAnnotationType()) {
                           Annotation persistibleAnnotation = sourceAnnotations[i].getPersistibleAnnotation();
                           if (persistibleAnnotation instanceof ContainerAnnotation) {
                              container = (ContainerAnnotation)persistibleAnnotation;
                           }

                           if (container == null) {
                              ReferenceBinding containerAnnotationType = annotationType.containerAnnotationType();
                              container = new ContainerAnnotation(sourceAnnotations[i], containerAnnotationType, scope);
                              if (implicitContainerAnnotations == null) {
                                 implicitContainerAnnotations = new HashMap(3);
                              }

                              implicitContainerAnnotations.put(containerAnnotationType, sourceAnnotations[i]);
                              Annotation.checkForInstancesOfRepeatableWithRepeatingContainerAnnotation(scope, annotationType, sourceAnnotations);
                           }

                           container.addContainee(sourceAnnotations[j]);
                        } else {
                           foundDuplicate = true;
                           scope.problemReporter().duplicateAnnotation(sourceAnnotations[j], scope.compilerOptions().sourceLevel);
                        }
                     }
                  }

                  if (container != null) {
                     container.resolveType(scope);
                  }

                  if (foundDuplicate) {
                     scope.problemReporter().duplicateAnnotation(sourceAnnotations[i], scope.compilerOptions().sourceLevel);
                  }
               }
            }

            if (implicitContainerAnnotations != null) {
               for(i = 0; i < length; ++i) {
                  if (distinctAnnotations[i] != null) {
                     Annotation annotation = sourceAnnotations[i];
                     annotationType = distinctAnnotations[i].getAnnotationType();
                     if (implicitContainerAnnotations.containsKey(annotationType)) {
                        scope.problemReporter().repeatedAnnotationWithContainer((Annotation)implicitContainerAnnotations.get(annotationType), annotation);
                     }
                  }
               }
            }
         }

         if (copySE8AnnotationsToType) {
            copySE8AnnotationsToType(scope, recipient, sourceAnnotations, false);
         }

         return annotations;
      }
   }

   public static TypeBinding resolveAnnotations(BlockScope scope, Annotation[][] sourceAnnotations, TypeBinding type) {
      int levels = sourceAnnotations == null ? 0 : sourceAnnotations.length;
      if (type != null && levels != 0) {
         AnnotationBinding[][] annotationBindings = new AnnotationBinding[levels][];

         for(int i = 0; i < levels; ++i) {
            Annotation[] annotations = sourceAnnotations[i];
            if (annotations != null && annotations.length > 0) {
               annotationBindings[i] = resolveAnnotations(scope, annotations, TypeBinding.TYPE_USE_BINDING, false);
            }
         }

         return scope.environment().createAnnotatedType(type, annotationBindings);
      } else {
         return type;
      }
   }

   public static void handleNonNullByDefault(BlockScope scope, Annotation[] sourceAnnotations, LocalDeclaration localDeclaration) {
      if (sourceAnnotations != null && sourceAnnotations.length != 0) {
         int length = sourceAnnotations.length;
         int defaultNullness = 0;
         Annotation lastNNBDAnnotation = null;

         for(int i = 0; i < length; ++i) {
            Annotation annotation = sourceAnnotations[i];
            long value = annotation.handleNonNullByDefault(scope);
            if (value != 0L) {
               defaultNullness = (int)((long)defaultNullness | value);
               lastNNBDAnnotation = annotation;
            }
         }

         if (defaultNullness != 0) {
            LocalVariableBinding binding = new LocalVariableBinding(localDeclaration, (TypeBinding)null, 0, false);
            Binding target = scope.checkRedundantDefaultNullness(defaultNullness, localDeclaration.sourceStart);
            boolean recorded = scope.recordNonNullByDefault(binding, defaultNullness, lastNNBDAnnotation, lastNNBDAnnotation.sourceStart, localDeclaration.declarationSourceEnd);
            if (recorded && target != null) {
               scope.problemReporter().nullDefaultAnnotationIsRedundant(localDeclaration, new Annotation[]{lastNNBDAnnotation}, target);
            }
         }

      }
   }

   public static void copySE8AnnotationsToType(BlockScope scope, Binding recipient, Annotation[] annotations, boolean annotatingEnumerator) {
      if (annotations != null && annotations.length != 0 && recipient != null) {
         long recipientTargetMask = 0L;
         switch (recipient.kind()) {
            case 1:
               recipientTargetMask = 137438953472L;
               break;
            case 2:
               recipientTargetMask = recipient.isParameter() ? 549755813888L : 2199023255552L;
               break;
            case 8:
               MethodBinding method = (MethodBinding)recipient;
               recipientTargetMask = method.isConstructor() ? 1099511627776L : 274877906944L;
               break;
            default:
               return;
         }

         AnnotationBinding[] se8Annotations = null;
         int se8count = 0;
         long se8nullBits = 0L;
         Annotation se8NullAnnotation = null;
         int firstSE8 = -1;
         int i = 0;

         int length;
         for(length = annotations.length; i < length; ++i) {
            AnnotationBinding annotation = annotations[i].getCompilerAnnotation();
            if (annotation != null) {
               ReferenceBinding annotationType = annotation.getAnnotationType();
               long metaTagBits = annotationType.getAnnotationTagBits();
               if ((metaTagBits & 9007199254740992L) != 0L) {
                  if (annotatingEnumerator) {
                     if ((metaTagBits & recipientTargetMask) == 0L) {
                        scope.problemReporter().misplacedTypeAnnotations(annotations[i], annotations[i]);
                     }
                  } else {
                     if (firstSE8 == -1) {
                        firstSE8 = i;
                     }

                     if (se8Annotations == null) {
                        se8Annotations = new AnnotationBinding[]{annotation};
                        se8count = 1;
                     } else {
                        System.arraycopy(se8Annotations, 0, se8Annotations = new AnnotationBinding[se8count + 1], 0, se8count);
                        se8Annotations[se8count++] = annotation;
                     }

                     if (annotationType.hasNullBit(32)) {
                        se8nullBits |= 72057594037927936L;
                        se8NullAnnotation = annotations[i];
                     } else if (annotationType.hasNullBit(64)) {
                        se8nullBits |= 36028797018963968L;
                        se8NullAnnotation = annotations[i];
                     }
                  }
               }
            }
         }

         if (se8Annotations != null) {
            TypeReference var10000;
            SourceTypeBinding sourceType;
            switch (recipient.kind()) {
               case 1:
                  FieldBinding field = (FieldBinding)recipient;
                  sourceType = (SourceTypeBinding)field.declaringClass;
                  FieldDeclaration fieldDeclaration = sourceType.scope.referenceContext.declarationOf(field);
                  if (Annotation.isTypeUseCompatible(fieldDeclaration.type, scope)) {
                     fieldDeclaration.bits |= 1048576;
                     var10000 = fieldDeclaration.type;
                     var10000.bits |= 1048576;
                     field.type = mergeAnnotationsIntoType(scope, se8Annotations, se8nullBits, se8NullAnnotation, fieldDeclaration.type, field.type);
                     if (scope.environment().usesNullTypeAnnotations()) {
                        field.tagBits &= ~se8nullBits;
                     }
                  }
                  break;
               case 2:
                  LocalVariableBinding local = (LocalVariableBinding)recipient;
                  TypeReference typeRef = local.declaration.type;
                  if (Annotation.isTypeUseCompatible(typeRef, scope)) {
                     LocalDeclaration var28 = local.declaration;
                     var28.bits |= 1048576;
                     typeRef.bits |= 1048576;
                     local.type = mergeAnnotationsIntoType(scope, se8Annotations, se8nullBits, se8NullAnnotation, typeRef, local.type);
                     if (scope.environment().usesNullTypeAnnotations()) {
                        local.tagBits &= ~se8nullBits;
                     }
                  }
                  break;
               case 8:
                  MethodBinding method = (MethodBinding)recipient;
                  if (!method.isConstructor()) {
                     sourceType = (SourceTypeBinding)method.declaringClass;
                     MethodDeclaration methodDecl = (MethodDeclaration)sourceType.scope.referenceContext.declarationOf(method);
                     if (Annotation.isTypeUseCompatible(methodDecl.returnType, scope)) {
                        methodDecl.bits |= 1048576;
                        var10000 = methodDecl.returnType;
                        var10000.bits |= 1048576;
                        method.returnType = mergeAnnotationsIntoType(scope, se8Annotations, se8nullBits, se8NullAnnotation, methodDecl.returnType, method.returnType);
                        if (scope.environment().usesNullTypeAnnotations()) {
                           method.tagBits &= ~se8nullBits;
                        }
                     }
                  } else {
                     method.setTypeAnnotations(se8Annotations);
                  }
            }

            AnnotationBinding[] recipientAnnotations = recipient.getAnnotations();
            length = recipientAnnotations == null ? 0 : recipientAnnotations.length;
            int newLength = 0;

            for(int i = 0; i < length; ++i) {
               AnnotationBinding recipientAnnotation = recipientAnnotations[i];
               if (recipientAnnotation != null) {
                  long annotationTargetMask = recipientAnnotation.getAnnotationType().getAnnotationTagBits() & 2332882164804222976L;
                  if (annotationTargetMask == 0L || (annotationTargetMask & recipientTargetMask) != 0L) {
                     recipientAnnotations[newLength++] = recipientAnnotation;
                  }
               }
            }

            if (newLength != length) {
               System.arraycopy(recipientAnnotations, 0, recipientAnnotations = new AnnotationBinding[newLength], 0, newLength);
               recipient.setAnnotations(recipientAnnotations, scope, false);
            }
         }

      }
   }

   private static TypeBinding mergeAnnotationsIntoType(BlockScope scope, AnnotationBinding[] se8Annotations, long se8nullBits, Annotation se8NullAnnotation, TypeReference typeRef, TypeBinding existingType) {
      if (existingType != null && existingType.isValidBinding()) {
         TypeReference unionRef = typeRef.isUnionType() ? ((UnionTypeReference)typeRef).typeReferences[0] : null;
         TypeBinding oldLeafType = unionRef == null ? existingType.leafComponentType() : unionRef.resolvedType;
         if (se8nullBits != 0L && typeRef instanceof ArrayTypeReference) {
            ArrayTypeReference arrayTypeReference = (ArrayTypeReference)typeRef;
            if (arrayTypeReference.leafComponentTypeWithoutDefaultNullness != null) {
               oldLeafType = arrayTypeReference.leafComponentTypeWithoutDefaultNullness;
            }
         }

         if (se8nullBits != 0L && oldLeafType.isBaseType()) {
            scope.problemReporter().illegalAnnotationForBaseType(typeRef, new Annotation[]{se8NullAnnotation}, se8nullBits);
            return existingType;
         } else {
            long prevNullBits = oldLeafType.tagBits & 108086391056891904L;
            if ((prevNullBits | se8nullBits) == 108086391056891904L) {
               if (!(oldLeafType instanceof TypeVariableBinding)) {
                  if (prevNullBits != 108086391056891904L && se8nullBits != 108086391056891904L) {
                     scope.problemReporter().contradictoryNullAnnotations(se8NullAnnotation);
                  }

                  se8Annotations = Binding.NO_ANNOTATIONS;
                  se8nullBits = 0L;
               }

               oldLeafType = oldLeafType.withoutToplevelNullAnnotation();
            }

            AnnotationBinding[][] goodies = new AnnotationBinding[typeRef.getAnnotatableLevels()][];
            goodies[0] = se8Annotations;
            TypeBinding newLeafType = scope.environment().createAnnotatedType(oldLeafType, goodies);
            if (unionRef == null) {
               typeRef.resolvedType = (TypeBinding)(existingType.isArrayType() ? scope.environment().createArrayType(newLeafType, existingType.dimensions(), existingType.getTypeAnnotations()) : newLeafType);
            } else {
               unionRef.resolvedType = newLeafType;
               unionRef.bits |= 1048576;
            }

            return typeRef.resolvedType;
         }
      } else {
         return existingType;
      }
   }

   public static void resolveDeprecatedAnnotations(BlockScope scope, Annotation[] annotations, Binding recipient) {
      if (recipient != null) {
         int kind = recipient.kind();
         int length;
         if (annotations != null && (length = annotations.length) >= 0) {
            switch (kind) {
               case 1:
                  FieldBinding field = (FieldBinding)recipient;
                  if ((field.tagBits & 17179869184L) != 0L) {
                     return;
                  }
                  break;
               case 2:
                  LocalVariableBinding local = (LocalVariableBinding)recipient;
                  if ((local.tagBits & 17179869184L) != 0L) {
                     return;
                  }
                  break;
               case 4:
               case 2052:
                  ReferenceBinding type = (ReferenceBinding)recipient;
                  if ((type.tagBits & 17179869184L) != 0L) {
                     return;
                  }
                  break;
               case 8:
                  MethodBinding method = (MethodBinding)recipient;
                  if ((method.tagBits & 17179869184L) != 0L) {
                     return;
                  }
                  break;
               case 16:
                  PackageBinding packageBinding = (PackageBinding)recipient;
                  if ((packageBinding.tagBits & 17179869184L) != 0L) {
                     return;
                  }
                  break;
               default:
                  return;
            }

            for(int i = 0; i < length; ++i) {
               TypeReference annotationTypeRef = annotations[i].type;
               if (CharOperation.equals(TypeConstants.JAVA_LANG_DEPRECATED[2], annotationTypeRef.getLastToken())) {
                  TypeBinding annotationType = annotations[i].type.resolveType(scope);
                  if (annotationType != null && annotationType.isValidBinding() && annotationType.id == 44) {
                     long deprecationTagBits = 70385924046848L;
                     if (scope.compilerOptions().complianceLevel >= 3473408L) {
                        MemberValuePair[] var13;
                        int var12 = (var13 = annotations[i].memberValuePairs()).length;

                        for(int var11 = 0; var11 < var12; ++var11) {
                           MemberValuePair memberValuePair = var13[var11];
                           if (CharOperation.equals(memberValuePair.name, TypeConstants.FOR_REMOVAL)) {
                              if (memberValuePair.value instanceof TrueLiteral) {
                                 deprecationTagBits |= 4611686018427387904L;
                              }
                              break;
                           }
                        }
                     }

                     switch (kind) {
                        case 1:
                           FieldBinding field = (FieldBinding)recipient;
                           field.tagBits |= deprecationTagBits;
                           return;
                        case 2:
                           LocalVariableBinding local = (LocalVariableBinding)recipient;
                           local.tagBits |= deprecationTagBits;
                           return;
                        case 4:
                        case 2052:
                        case 4100:
                           ReferenceBinding type = (ReferenceBinding)recipient;
                           type.tagBits |= deprecationTagBits;
                           return;
                        case 8:
                           MethodBinding method = (MethodBinding)recipient;
                           method.tagBits |= deprecationTagBits;
                           return;
                        case 16:
                           PackageBinding packageBinding = (PackageBinding)recipient;
                           packageBinding.tagBits |= deprecationTagBits;
                           return;
                        default:
                           return;
                     }
                  }
               }
            }
         }

         switch (kind) {
            case 1:
               FieldBinding field = (FieldBinding)recipient;
               field.tagBits |= 17179869184L;
               return;
            case 2:
               LocalVariableBinding local = (LocalVariableBinding)recipient;
               local.tagBits |= 17179869184L;
               return;
            case 4:
            case 2052:
            case 4100:
               ReferenceBinding type = (ReferenceBinding)recipient;
               type.tagBits |= 17179869184L;
               return;
            case 8:
               MethodBinding method = (MethodBinding)recipient;
               method.tagBits |= 17179869184L;
               return;
            case 16:
               PackageBinding packageBinding = (PackageBinding)recipient;
               packageBinding.tagBits |= 17179869184L;
               return;
            default:
         }
      }
   }

   public boolean checkingPotentialCompatibility() {
      return false;
   }

   public void acceptPotentiallyCompatibleMethods(MethodBinding[] methods) {
   }

   public int sourceStart() {
      return this.sourceStart;
   }

   public int sourceEnd() {
      return this.sourceEnd;
   }

   public String toString() {
      return this.print(0, new StringBuffer(30)).toString();
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
   }
}

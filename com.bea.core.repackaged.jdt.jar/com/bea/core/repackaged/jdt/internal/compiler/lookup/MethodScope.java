package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Argument;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ArrayTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ConstructorDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.LambdaExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.QualifiedNameReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.QualifiedTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SingleNameReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SingleTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeParameter;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.ConstantPool;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.ReferenceContext;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemReporter;

public class MethodScope extends BlockScope {
   public ReferenceContext referenceContext;
   public boolean isStatic;
   public boolean isConstructorCall;
   public FieldBinding initializedField;
   public int lastVisibleFieldID;
   public int analysisIndex;
   public boolean isPropagatingInnerClassEmulation;
   public int lastIndex;
   public long[] definiteInits;
   public long[][] extraDefiniteInits;
   public SyntheticArgumentBinding[] extraSyntheticArguments;
   public boolean hasMissingSwitchDefault;

   public MethodScope(Scope parent, ReferenceContext context, boolean isStatic) {
      super(2, parent);
      this.isConstructorCall = false;
      this.lastVisibleFieldID = -1;
      this.lastIndex = 0;
      this.definiteInits = new long[4];
      this.extraDefiniteInits = new long[4][];
      this.locals = new LocalVariableBinding[5];
      this.referenceContext = context;
      this.isStatic = isStatic;
      this.startIndex = 0;
   }

   public MethodScope(Scope parent, ReferenceContext context, boolean isStatic, int lastVisibleFieldID) {
      this(parent, context, isStatic);
      this.lastVisibleFieldID = lastVisibleFieldID;
   }

   String basicToString(int tab) {
      String newLine = "\n";
      int i = tab;

      while(true) {
         --i;
         if (i < 0) {
            String s = newLine + "--- Method Scope ---";
            newLine = newLine + "\t";
            s = s + newLine + "locals:";

            for(int i = 0; i < this.localIndex; ++i) {
               s = s + newLine + "\t" + this.locals[i].toString();
            }

            s = s + newLine + "startIndex = " + this.startIndex;
            s = s + newLine + "isConstructorCall = " + this.isConstructorCall;
            s = s + newLine + "initializedField = " + this.initializedField;
            s = s + newLine + "lastVisibleFieldID = " + this.lastVisibleFieldID;
            s = s + newLine + "referenceContext = " + this.referenceContext;
            return s;
         }

         newLine = newLine + "\t";
      }
   }

   private void checkAndSetModifiersForConstructor(MethodBinding methodBinding) {
      int modifiers = methodBinding.modifiers;
      ReferenceBinding declaringClass = methodBinding.declaringClass;
      if ((modifiers & 4194304) != 0) {
         this.problemReporter().duplicateModifierForMethod(declaringClass, (AbstractMethodDeclaration)this.referenceContext);
      }

      int realModifiers;
      if ((((ConstructorDeclaration)this.referenceContext).bits & 128) != 0 && (realModifiers = declaringClass.modifiers & 16389) != 0) {
         if ((realModifiers & 16384) != 0) {
            modifiers &= -8;
            modifiers |= 2;
         } else {
            modifiers &= -8;
            modifiers |= realModifiers;
         }
      }

      realModifiers = modifiers & '\uffff';
      if (declaringClass.isEnum() && (((ConstructorDeclaration)this.referenceContext).bits & 128) == 0) {
         if ((realModifiers & -2051) != 0) {
            this.problemReporter().illegalModifierForEnumConstructor((AbstractMethodDeclaration)this.referenceContext);
            modifiers &= -63486;
         } else if ((((AbstractMethodDeclaration)this.referenceContext).modifiers & 2048) != 0) {
            this.problemReporter().illegalModifierForMethod((AbstractMethodDeclaration)this.referenceContext);
         }

         modifiers |= 2;
      } else if ((realModifiers & -2056) != 0) {
         this.problemReporter().illegalModifierForMethod((AbstractMethodDeclaration)this.referenceContext);
         modifiers &= -63481;
      } else if ((((AbstractMethodDeclaration)this.referenceContext).modifiers & 2048) != 0) {
         this.problemReporter().illegalModifierForMethod((AbstractMethodDeclaration)this.referenceContext);
      }

      int accessorBits = realModifiers & 7;
      if ((accessorBits & accessorBits - 1) != 0) {
         this.problemReporter().illegalVisibilityModifierCombinationForMethod(declaringClass, (AbstractMethodDeclaration)this.referenceContext);
         if ((accessorBits & 1) != 0) {
            if ((accessorBits & 4) != 0) {
               modifiers &= -5;
            }

            if ((accessorBits & 2) != 0) {
               modifiers &= -3;
            }
         } else if ((accessorBits & 4) != 0 && (accessorBits & 2) != 0) {
            modifiers &= -3;
         }
      }

      methodBinding.modifiers = modifiers;
   }

   private void checkAndSetModifiersForMethod(MethodBinding methodBinding) {
      int modifiers = methodBinding.modifiers;
      ReferenceBinding declaringClass = methodBinding.declaringClass;
      if ((modifiers & 4194304) != 0) {
         this.problemReporter().duplicateModifierForMethod(declaringClass, (AbstractMethodDeclaration)this.referenceContext);
      }

      int realModifiers = modifiers & '\uffff';
      long sourceLevel = this.compilerOptions().sourceLevel;
      int accessorBits;
      if (declaringClass.isInterface()) {
         accessorBits = 1025;
         boolean isDefaultMethod = (modifiers & 65536) != 0;
         boolean reportIllegalModifierCombination = false;
         if (sourceLevel >= 3407872L && !declaringClass.isAnnotationType()) {
            accessorBits |= 67592;
            accessorBits |= sourceLevel >= 3473408L ? 2 : 0;
            if (!methodBinding.isAbstract()) {
               reportIllegalModifierCombination = isDefaultMethod && methodBinding.isStatic();
            } else {
               reportIllegalModifierCombination = isDefaultMethod || methodBinding.isStatic();
               if (methodBinding.isStrictfp()) {
                  this.problemReporter().illegalAbstractModifierCombinationForMethod((AbstractMethodDeclaration)this.referenceContext);
               }
            }

            if (reportIllegalModifierCombination) {
               this.problemReporter().illegalModifierCombinationForInterfaceMethod((AbstractMethodDeclaration)this.referenceContext);
            }

            if (sourceLevel >= 3473408L && (methodBinding.modifiers & 2) != 0) {
               int remaining = realModifiers & ~accessorBits;
               if (remaining == 0) {
                  remaining = realModifiers & -2059;
                  if (isDefaultMethod || remaining != 0) {
                     this.problemReporter().illegalModifierCombinationForPrivateInterfaceMethod((AbstractMethodDeclaration)this.referenceContext);
                  }
               }
            }

            if (isDefaultMethod) {
               realModifiers |= 65536;
            }
         }

         if ((realModifiers & ~accessorBits) != 0) {
            if ((declaringClass.modifiers & 8192) != 0) {
               this.problemReporter().illegalModifierForAnnotationMember((AbstractMethodDeclaration)this.referenceContext);
            } else {
               this.problemReporter().illegalModifierForInterfaceMethod((AbstractMethodDeclaration)this.referenceContext, sourceLevel);
            }

            methodBinding.modifiers &= accessorBits | -65536;
         }

      } else {
         if (declaringClass.isAnonymousType() && sourceLevel >= 3473408L) {
            LocalTypeBinding local = (LocalTypeBinding)declaringClass;
            TypeReference ref = local.scope.referenceContext.allocation.type;
            if (ref != null && (ref.bits & 524288) != 0 && (realModifiers & 10) == 0) {
               methodBinding.tagBits |= 562949953421312L;
            }
         }

         if ((realModifiers & -3392) != 0) {
            this.problemReporter().illegalModifierForMethod((AbstractMethodDeclaration)this.referenceContext);
            modifiers &= -62145;
         }

         accessorBits = realModifiers & 7;
         if ((accessorBits & accessorBits - 1) != 0) {
            this.problemReporter().illegalVisibilityModifierCombinationForMethod(declaringClass, (AbstractMethodDeclaration)this.referenceContext);
            if ((accessorBits & 1) != 0) {
               if ((accessorBits & 4) != 0) {
                  modifiers &= -5;
               }

               if ((accessorBits & 2) != 0) {
                  modifiers &= -3;
               }
            } else if ((accessorBits & 4) != 0 && (accessorBits & 2) != 0) {
               modifiers &= -3;
            }
         }

         if ((modifiers & 1024) != 0) {
            int incompatibleWithAbstract = 2362;
            if ((modifiers & incompatibleWithAbstract) != 0) {
               this.problemReporter().illegalAbstractModifierCombinationForMethod(declaringClass, (AbstractMethodDeclaration)this.referenceContext);
            }

            if (!methodBinding.declaringClass.isAbstract()) {
               this.problemReporter().abstractMethodInAbstractClass((SourceTypeBinding)declaringClass, (AbstractMethodDeclaration)this.referenceContext);
            }
         }

         if ((modifiers & 256) != 0 && (modifiers & 2048) != 0) {
            this.problemReporter().nativeMethodsCannotBeStrictfp(declaringClass, (AbstractMethodDeclaration)this.referenceContext);
         }

         if ((realModifiers & 8) != 0 && declaringClass.isNestedType() && !declaringClass.isStatic()) {
            this.problemReporter().unexpectedStaticModifierForMethod(declaringClass, (AbstractMethodDeclaration)this.referenceContext);
         }

         methodBinding.modifiers = modifiers;
      }
   }

   public void checkUnusedParameters(MethodBinding method) {
      if (!method.isAbstract() && (!method.isImplementing() || this.compilerOptions().reportUnusedParameterWhenImplementingAbstract) && (!method.isOverriding() || method.isImplementing() || this.compilerOptions().reportUnusedParameterWhenOverridingConcrete) && !method.isMain()) {
         int i = 0;

         for(int maxLocals = this.localIndex; i < maxLocals; ++i) {
            LocalVariableBinding local = this.locals[i];
            if (local == null || (local.tagBits & 1024L) == 0L) {
               break;
            }

            if (local.useFlag == 0 && (local.declaration.bits & 1073741824) != 0) {
               this.problemReporter().unusedArgument(local.declaration);
            }
         }

      }
   }

   public void computeLocalVariablePositions(int initOffset, CodeStream codeStream) {
      this.offset = initOffset;
      this.maxOffset = initOffset;
      int ilocal = 0;

      for(int maxLocals = this.localIndex; ilocal < maxLocals; ++ilocal) {
         LocalVariableBinding local = this.locals[ilocal];
         if (local == null || (local.tagBits & 1024L) == 0L) {
            break;
         }

         codeStream.record(local);
         local.resolvedPosition = this.offset;
         if (!TypeBinding.equalsEquals(local.type, TypeBinding.LONG) && !TypeBinding.equalsEquals(local.type, TypeBinding.DOUBLE)) {
            ++this.offset;
         } else {
            this.offset += 2;
         }

         if (this.offset > 255) {
            this.problemReporter().noMoreAvailableSpaceForArgument(local, local.declaration);
         }
      }

      if (this.extraSyntheticArguments != null) {
         int iarg = 0;

         for(int maxArguments = this.extraSyntheticArguments.length; iarg < maxArguments; ++iarg) {
            SyntheticArgumentBinding argument = this.extraSyntheticArguments[iarg];
            argument.resolvedPosition = this.offset;
            if (!TypeBinding.equalsEquals(argument.type, TypeBinding.LONG) && !TypeBinding.equalsEquals(argument.type, TypeBinding.DOUBLE)) {
               ++this.offset;
            } else {
               this.offset += 2;
            }

            if (this.offset > 255) {
               this.problemReporter().noMoreAvailableSpaceForArgument(argument, (ASTNode)this.referenceContext);
            }
         }
      }

      this.computeLocalVariablePositions(ilocal, this.offset, codeStream);
   }

   MethodBinding createMethod(AbstractMethodDeclaration method) {
      this.referenceContext = method;
      method.scope = this;
      long sourceLevel = this.compilerOptions().sourceLevel;
      SourceTypeBinding declaringClass = this.referenceType().binding;
      int modifiers = method.modifiers | 33554432;
      if (method.isConstructor()) {
         if (method.isDefaultConstructor()) {
            modifiers |= 67108864;
         }

         method.binding = new MethodBinding(modifiers, (TypeBinding[])null, (ReferenceBinding[])null, declaringClass);
         this.checkAndSetModifiersForConstructor(method.binding);
      } else {
         if (declaringClass.isInterface() && (sourceLevel < 3473408L || (method.modifiers & 2) == 0)) {
            if (!method.isDefaultMethod() && !method.isStatic()) {
               modifiers |= 1025;
            } else {
               modifiers |= 1;
            }
         }

         method.binding = new MethodBinding(modifiers, method.selector, (TypeBinding)null, (TypeBinding[])null, (ReferenceBinding[])null, declaringClass);
         this.checkAndSetModifiersForMethod(method.binding);
      }

      this.isStatic = method.binding.isStatic();
      Argument[] argTypes = method.arguments;
      int argLength = argTypes == null ? 0 : argTypes.length;
      MethodBinding var10000;
      if (argLength > 0) {
         --argLength;
         Argument argument = argTypes[argLength];
         if (argument.isVarArgs() && sourceLevel >= 3211264L) {
            var10000 = method.binding;
            var10000.modifiers |= 128;
         }

         if (CharOperation.equals(argument.name, ConstantPool.This)) {
            this.problemReporter().illegalThisDeclaration(argument);
         }

         while(true) {
            --argLength;
            if (argLength < 0) {
               break;
            }

            argument = argTypes[argLength];
            if (argument.isVarArgs() && sourceLevel >= 3211264L) {
               this.problemReporter().illegalVararg(argument, method);
            }

            if (CharOperation.equals(argument.name, ConstantPool.This)) {
               this.problemReporter().illegalThisDeclaration(argument);
            }
         }
      }

      if (method.receiver != null) {
         if (sourceLevel <= 3342336L) {
            this.problemReporter().illegalSourceLevelForThis(method.receiver);
         }

         if (method.receiver.annotations != null) {
            method.bits |= 1048576;
         }
      }

      TypeParameter[] typeParameters = method.typeParameters();
      if (typeParameters != null && typeParameters.length != 0) {
         method.binding.typeVariables = this.createTypeVariables(typeParameters, method.binding);
         var10000 = method.binding;
         var10000.modifiers |= 1073741824;
      } else {
         method.binding.typeVariables = Binding.NO_TYPE_VARIABLES;
      }

      return method.binding;
   }

   public FieldBinding findField(TypeBinding receiverType, char[] fieldName, InvocationSite invocationSite, boolean needResolve) {
      FieldBinding field = super.findField(receiverType, fieldName, invocationSite, needResolve);
      if (field == null) {
         return null;
      } else if (!field.isValidBinding()) {
         return field;
      } else if (receiverType.isInterface() && invocationSite.isQualifiedSuper()) {
         return new ProblemFieldBinding(field, field.declaringClass, fieldName, 28);
      } else if (field.isStatic()) {
         return field;
      } else if (this.isConstructorCall && !TypeBinding.notEquals(receiverType, this.enclosingSourceType())) {
         if (invocationSite instanceof SingleNameReference) {
            return new ProblemFieldBinding(field, field.declaringClass, fieldName, 6);
         } else {
            if (invocationSite instanceof QualifiedNameReference) {
               QualifiedNameReference name = (QualifiedNameReference)invocationSite;
               if (name.binding == null) {
                  return new ProblemFieldBinding(field, field.declaringClass, fieldName, 6);
               }
            }

            return field;
         }
      } else {
         return field;
      }
   }

   public boolean isInsideConstructor() {
      return this.referenceContext instanceof ConstructorDeclaration;
   }

   public boolean isInsideInitializer() {
      return this.referenceContext instanceof TypeDeclaration;
   }

   public boolean isLambdaScope() {
      return this.referenceContext instanceof LambdaExpression;
   }

   public boolean isInsideInitializerOrConstructor() {
      return this.referenceContext instanceof TypeDeclaration || this.referenceContext instanceof ConstructorDeclaration;
   }

   public ProblemReporter problemReporter() {
      ProblemReporter problemReporter = this.referenceCompilationUnit().problemReporter;
      problemReporter.referenceContext = this.referenceContext;
      return problemReporter;
   }

   public final int recordInitializationStates(FlowInfo flowInfo) {
      if ((flowInfo.tagBits & 1) != 0) {
         return -1;
      } else {
         UnconditionalFlowInfo unconditionalFlowInfo = flowInfo.unconditionalInitsWithoutSideEffect();
         long[] extraInits = unconditionalFlowInfo.extra == null ? null : unconditionalFlowInfo.extra[0];
         long inits = unconditionalFlowInfo.definiteInits;
         int i = this.lastIndex;

         label62:
         while(true) {
            long[] otherInits;
            label54:
            do {
               do {
                  do {
                     --i;
                     if (i < 0) {
                        if (this.definiteInits.length == this.lastIndex) {
                           System.arraycopy(this.definiteInits, 0, this.definiteInits = new long[this.lastIndex + 20], 0, this.lastIndex);
                           System.arraycopy(this.extraDefiniteInits, 0, this.extraDefiniteInits = new long[this.lastIndex + 20][], 0, this.lastIndex);
                        }

                        this.definiteInits[this.lastIndex] = inits;
                        if (extraInits != null) {
                           this.extraDefiniteInits[this.lastIndex] = new long[extraInits.length];
                           System.arraycopy(extraInits, 0, this.extraDefiniteInits[this.lastIndex], 0, extraInits.length);
                        }

                        return this.lastIndex++;
                     }
                  } while(this.definiteInits[i] != inits);

                  otherInits = this.extraDefiniteInits[i];
                  if (extraInits != null && otherInits != null) {
                     continue label54;
                  }
               } while(extraInits != null || otherInits != null);

               return i;
            } while(extraInits.length != otherInits.length);

            int j = 0;

            for(int max = extraInits.length; j < max; ++j) {
               if (extraInits[j] != otherInits[j]) {
                  continue label62;
               }
            }

            return i;
         }
      }
   }

   public AbstractMethodDeclaration referenceMethod() {
      return this.referenceContext instanceof AbstractMethodDeclaration ? (AbstractMethodDeclaration)this.referenceContext : null;
   }

   public MethodBinding referenceMethodBinding() {
      if (this.referenceContext instanceof LambdaExpression) {
         return ((LambdaExpression)this.referenceContext).binding;
      } else {
         return this.referenceContext instanceof AbstractMethodDeclaration ? ((AbstractMethodDeclaration)this.referenceContext).binding : null;
      }
   }

   public TypeDeclaration referenceType() {
      ClassScope scope = this.enclosingClassScope();
      return scope == null ? null : scope.referenceContext;
   }

   void resolveTypeParameter(TypeParameter typeParameter) {
      typeParameter.resolve((BlockScope)this);
   }

   public boolean hasDefaultNullnessFor(int location, int sourceStart) {
      int nonNullByDefaultValue = this.localNonNullByDefaultValue(sourceStart);
      if (nonNullByDefaultValue != 0) {
         return (nonNullByDefaultValue & location) != 0;
      } else {
         AbstractMethodDeclaration referenceMethod = this.referenceMethod();
         if (referenceMethod != null) {
            MethodBinding binding = referenceMethod.binding;
            if (binding != null && binding.defaultNullness != 0) {
               if ((binding.defaultNullness & location) != 0) {
                  return true;
               }

               return false;
            }
         }

         return this.parent.hasDefaultNullnessFor(location, sourceStart);
      }
   }

   public Binding checkRedundantDefaultNullness(int nullBits, int sourceStart) {
      Binding target = this.localCheckRedundantDefaultNullness(nullBits, sourceStart);
      if (target != null) {
         return target;
      } else {
         AbstractMethodDeclaration referenceMethod = this.referenceMethod();
         if (referenceMethod != null) {
            MethodBinding binding = referenceMethod.binding;
            if (binding != null && binding.defaultNullness != 0) {
               return binding.defaultNullness == nullBits ? binding : null;
            }
         }

         return this.parent.checkRedundantDefaultNullness(nullBits, sourceStart);
      }
   }

   public boolean shouldCheckAPILeaks(ReferenceBinding declaringClass, boolean memberIsPublic) {
      if (this.environment().useModuleSystem) {
         return memberIsPublic && declaringClass.isPublic() && declaringClass.fPackage.isExported();
      } else {
         return false;
      }
   }

   public void detectAPILeaks(ASTNode typeNode, TypeBinding type) {
      if (this.environment().useModuleSystem) {
         ASTVisitor visitor = new ASTVisitor() {
            public boolean visit(SingleTypeReference typeReference, BlockScope scope) {
               if (typeReference.resolvedType instanceof ReferenceBinding) {
                  this.checkType((ReferenceBinding)typeReference.resolvedType, typeReference.sourceStart, typeReference.sourceEnd);
               }

               return true;
            }

            public boolean visit(QualifiedTypeReference typeReference, BlockScope scope) {
               if (typeReference.resolvedType instanceof ReferenceBinding) {
                  this.checkType((ReferenceBinding)typeReference.resolvedType, typeReference.sourceStart, typeReference.sourceEnd);
               }

               return true;
            }

            public boolean visit(ArrayTypeReference typeReference, BlockScope scope) {
               TypeBinding leafComponentType = typeReference.resolvedType.leafComponentType();
               if (leafComponentType instanceof ReferenceBinding) {
                  this.checkType((ReferenceBinding)leafComponentType, typeReference.sourceStart, typeReference.originalSourceEnd);
               }

               return true;
            }

            private void checkType(ReferenceBinding referenceBinding, int sourceStart, int sourceEnd) {
               if (referenceBinding.isValidBinding()) {
                  ModuleBinding otherModule = referenceBinding.module();
                  if (otherModule != otherModule.environment.javaBaseModule()) {
                     if (!this.isFullyPublic(referenceBinding)) {
                        MethodScope.this.problemReporter().nonPublicTypeInAPI(referenceBinding, sourceStart, sourceEnd);
                     } else if (!referenceBinding.fPackage.isExported()) {
                        MethodScope.this.problemReporter().notExportedTypeInAPI(referenceBinding, sourceStart, sourceEnd);
                     } else if (this.isUnrelatedModule(referenceBinding.fPackage)) {
                        MethodScope.this.problemReporter().missingRequiresTransitiveForTypeInAPI(referenceBinding, sourceStart, sourceEnd);
                     }

                  }
               }
            }

            private boolean isFullyPublic(ReferenceBinding referenceBinding) {
               if (!referenceBinding.isPublic()) {
                  return false;
               } else {
                  return referenceBinding instanceof NestedTypeBinding ? this.isFullyPublic(((NestedTypeBinding)referenceBinding).enclosingType) : true;
               }
            }

            private boolean isUnrelatedModule(PackageBinding fPackage) {
               ModuleBinding otherModule = fPackage.enclosingModule;
               ModuleBinding thisModule = MethodScope.this.module();
               if (thisModule != otherModule) {
                  return !thisModule.isTransitivelyRequired(otherModule);
               } else {
                  return false;
               }
            }
         };
         typeNode.traverse(visitor, this);
      }

   }
}

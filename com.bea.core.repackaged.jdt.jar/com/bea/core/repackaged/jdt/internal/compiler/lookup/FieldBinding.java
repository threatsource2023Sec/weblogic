package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.IErrorHandlingPolicy;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Annotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FieldDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemReporter;

public class FieldBinding extends VariableBinding {
   public ReferenceBinding declaringClass;
   public int compoundUseFlag;

   protected FieldBinding() {
      super((char[])null, (TypeBinding)null, 0, (Constant)null);
      this.compoundUseFlag = 0;
   }

   public FieldBinding(char[] name, TypeBinding type, int modifiers, ReferenceBinding declaringClass, Constant constant) {
      super(name, type, modifiers, constant);
      this.compoundUseFlag = 0;
      this.declaringClass = declaringClass;
   }

   public FieldBinding(FieldBinding initialFieldBinding, ReferenceBinding declaringClass) {
      super(initialFieldBinding.name, initialFieldBinding.type, initialFieldBinding.modifiers, initialFieldBinding.constant());
      this.compoundUseFlag = 0;
      this.declaringClass = declaringClass;
      this.id = initialFieldBinding.id;
      this.setAnnotations(initialFieldBinding.getAnnotations(), false);
   }

   public FieldBinding(FieldDeclaration field, TypeBinding type, int modifiers, ReferenceBinding declaringClass) {
      this(field.name, type, modifiers, declaringClass, (Constant)null);
      field.binding = this;
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
      if (this.isPublic()) {
         return true;
      } else {
         SourceTypeBinding invocationType = scope.enclosingSourceType();
         if (TypeBinding.equalsEquals(invocationType, this.declaringClass) && TypeBinding.equalsEquals(invocationType, receiverType)) {
            return true;
         } else if (invocationType == null) {
            return !this.isPrivate() && scope.getCurrentPackage() == this.declaringClass.fPackage;
         } else {
            Object currentType;
            ReferenceBinding receiverErasure;
            if (this.isProtected()) {
               if (TypeBinding.equalsEquals(invocationType, this.declaringClass)) {
                  return true;
               } else if (invocationType.fPackage == this.declaringClass.fPackage) {
                  return true;
               } else {
                  currentType = invocationType;
                  int depth = 0;
                  receiverErasure = (ReferenceBinding)receiverType.erasure();
                  ReferenceBinding declaringErasure = (ReferenceBinding)this.declaringClass.erasure();

                  do {
                     if (((ReferenceBinding)currentType).findSuperTypeOriginatingFrom(declaringErasure) != null) {
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
                  TypeBinding originalDeclaringClass = this.declaringClass.original();
                  receiverErasure = (ReferenceBinding)receiverType;

                  do {
                     if (receiverErasure.isCapture()) {
                        if (TypeBinding.equalsEquals(originalDeclaringClass, receiverErasure.erasure().original())) {
                           return true;
                        }
                     } else if (TypeBinding.equalsEquals(originalDeclaringClass, receiverErasure.original())) {
                        return true;
                     }

                     PackageBinding currentPackage = receiverErasure.fPackage;
                     if (currentPackage != null && currentPackage != declaringPackage) {
                        return false;
                     }
                  } while((receiverErasure = receiverErasure.superclass()) != null);

                  return false;
               }
            } else if (TypeBinding.notEquals(receiverType, this.declaringClass) && (scope.compilerOptions().complianceLevel > 3276800L || !receiverType.isTypeVariable() || !((TypeVariableBinding)receiverType).isErasureBoundTo(this.declaringClass.erasure()))) {
               return false;
            } else {
               if (TypeBinding.notEquals(invocationType, this.declaringClass)) {
                  currentType = invocationType;

                  ReferenceBinding temp;
                  for(temp = invocationType.enclosingType(); temp != null; temp = temp.enclosingType()) {
                     currentType = temp;
                  }

                  receiverErasure = (ReferenceBinding)this.declaringClass.erasure();

                  for(temp = receiverErasure.enclosingType(); temp != null; temp = temp.enclosingType()) {
                     receiverErasure = temp;
                  }

                  if (TypeBinding.notEquals((TypeBinding)currentType, receiverErasure)) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   public char[] computeUniqueKey(boolean isLeaf) {
      char[] declaringKey = this.declaringClass == null ? CharOperation.NO_CHAR : this.declaringClass.computeUniqueKey(false);
      int declaringLength = declaringKey.length;
      int nameLength = this.name.length;
      char[] returnTypeKey = this.type == null ? new char[]{'V'} : this.type.computeUniqueKey(false);
      int returnTypeLength = returnTypeKey.length;
      char[] uniqueKey = new char[declaringLength + 1 + nameLength + 1 + returnTypeLength];
      int index = 0;
      System.arraycopy(declaringKey, 0, uniqueKey, index, declaringLength);
      index += declaringLength;
      uniqueKey[index++] = '.';
      System.arraycopy(this.name, 0, uniqueKey, index, nameLength);
      index += nameLength;
      uniqueKey[index++] = ')';
      System.arraycopy(returnTypeKey, 0, uniqueKey, index, returnTypeLength);
      return uniqueKey;
   }

   public Constant constant() {
      Constant fieldConstant = this.constant;
      if (fieldConstant == null) {
         if (this.isFinal()) {
            FieldBinding originalField = this.original();
            if (originalField.declaringClass instanceof SourceTypeBinding) {
               SourceTypeBinding sourceType = (SourceTypeBinding)originalField.declaringClass;
               if (sourceType.scope != null) {
                  TypeDeclaration typeDecl = sourceType.scope.referenceContext;
                  FieldDeclaration fieldDecl = typeDecl.declarationOf(originalField);
                  MethodScope initScope = originalField.isStatic() ? typeDecl.staticInitializerScope : typeDecl.initializerScope;
                  boolean old = initScope.insideTypeAnnotation;

                  try {
                     initScope.insideTypeAnnotation = false;
                     fieldDecl.resolve(initScope);
                  } finally {
                     initScope.insideTypeAnnotation = old;
                  }

                  fieldConstant = originalField.constant == null ? Constant.NotAConstant : originalField.constant;
               } else {
                  fieldConstant = Constant.NotAConstant;
               }
            } else {
               fieldConstant = Constant.NotAConstant;
            }
         } else {
            fieldConstant = Constant.NotAConstant;
         }

         this.constant = fieldConstant;
      }

      return fieldConstant;
   }

   public Constant constant(Scope scope) {
      if (this.constant != null) {
         return this.constant;
      } else {
         ProblemReporter problemReporter = scope.problemReporter();
         IErrorHandlingPolicy suspendedPolicy = problemReporter.suspendTempErrorHandlingPolicy();

         Constant var5;
         try {
            var5 = this.constant();
         } finally {
            problemReporter.resumeTempErrorHandlingPolicy(suspendedPolicy);
         }

         return var5;
      }
   }

   public void fillInDefaultNonNullness(FieldDeclaration sourceField, Scope scope) {
      if (this.type != null && !this.type.isBaseType()) {
         LookupEnvironment environment = scope.environment();
         if (environment.usesNullTypeAnnotations()) {
            if (!this.type.acceptsNonNullDefault()) {
               return;
            }

            if ((this.type.tagBits & 108086391056891904L) == 0L) {
               this.type = environment.createAnnotatedType(this.type, new AnnotationBinding[]{environment.getNonNullAnnotation()});
            } else if ((this.type.tagBits & 72057594037927936L) != 0L) {
               scope.problemReporter().nullAnnotationIsRedundant(sourceField);
            }
         } else if ((this.tagBits & 108086391056891904L) == 0L) {
            this.tagBits |= 72057594037927936L;
         } else if ((this.tagBits & 72057594037927936L) != 0L) {
            scope.problemReporter().nullAnnotationIsRedundant(sourceField);
         }

      }
   }

   public char[] genericSignature() {
      return (this.modifiers & 1073741824) == 0 ? null : this.type.genericTypeSignature();
   }

   public final int getAccessFlags() {
      return this.modifiers & '\uffff';
   }

   public AnnotationBinding[] getAnnotations() {
      FieldBinding originalField = this.original();
      ReferenceBinding declaringClassBinding = originalField.declaringClass;
      return declaringClassBinding == null ? Binding.NO_ANNOTATIONS : declaringClassBinding.retrieveAnnotations(originalField);
   }

   public long getAnnotationTagBits() {
      FieldBinding originalField = this.original();
      if ((originalField.tagBits & 8589934592L) == 0L && originalField.declaringClass instanceof SourceTypeBinding) {
         ClassScope scope = ((SourceTypeBinding)originalField.declaringClass).scope;
         if (scope == null) {
            this.tagBits |= 25769803776L;
            return 0L;
         }

         TypeDeclaration typeDecl = scope.referenceContext;
         FieldDeclaration fieldDecl = typeDecl.declarationOf(originalField);
         if (fieldDecl != null) {
            MethodScope initializationScope = this.isStatic() ? typeDecl.staticInitializerScope : typeDecl.initializerScope;
            FieldBinding previousField = initializationScope.initializedField;
            int previousFieldID = initializationScope.lastVisibleFieldID;

            try {
               initializationScope.initializedField = originalField;
               initializationScope.lastVisibleFieldID = originalField.id;
               ASTNode.resolveAnnotations(initializationScope, (Annotation[])fieldDecl.annotations, (Binding)originalField);
            } finally {
               initializationScope.initializedField = previousField;
               initializationScope.lastVisibleFieldID = previousFieldID;
            }
         }
      }

      return originalField.tagBits;
   }

   public final boolean isDefault() {
      return !this.isPublic() && !this.isProtected() && !this.isPrivate();
   }

   public final boolean isDeprecated() {
      return (this.modifiers & 1048576) != 0;
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

   public final boolean isSynthetic() {
      return (this.modifiers & 4096) != 0;
   }

   public final boolean isTransient() {
      return (this.modifiers & 128) != 0;
   }

   public final boolean isUsed() {
      return (this.modifiers & 134217728) != 0 || this.compoundUseFlag > 0;
   }

   public final boolean isUsedOnlyInCompound() {
      return (this.modifiers & 134217728) == 0 && this.compoundUseFlag > 0;
   }

   public final boolean isViewedAsDeprecated() {
      return (this.modifiers & 3145728) != 0;
   }

   public final boolean isVolatile() {
      return (this.modifiers & 64) != 0;
   }

   public final int kind() {
      return 1;
   }

   public FieldBinding original() {
      return this;
   }

   public void setAnnotations(AnnotationBinding[] annotations, boolean forceStore) {
      this.declaringClass.storeAnnotations(this, annotations, forceStore);
   }

   public FieldDeclaration sourceField() {
      SourceTypeBinding sourceType;
      try {
         sourceType = (SourceTypeBinding)this.declaringClass;
      } catch (ClassCastException var4) {
         return null;
      }

      FieldDeclaration[] fields = sourceType.scope.referenceContext.fields;
      if (fields != null) {
         int i = fields.length;

         while(true) {
            --i;
            if (i < 0) {
               break;
            }

            if (this == fields[i].binding) {
               return fields[i];
            }
         }
      }

      return null;
   }
}

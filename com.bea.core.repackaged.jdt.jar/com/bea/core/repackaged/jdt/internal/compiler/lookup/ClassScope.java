package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FieldDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.QualifiedAllocationExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeParameter;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRestriction;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortCompilation;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemReporter;
import com.bea.core.repackaged.jdt.internal.compiler.util.HashtableOfObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClassScope extends Scope {
   public TypeDeclaration referenceContext;
   public TypeReference superTypeReference;
   ArrayList deferredBoundChecks;

   public ClassScope(Scope parent, TypeDeclaration context) {
      super(3, parent);
      this.referenceContext = context;
      this.deferredBoundChecks = null;
   }

   void buildAnonymousTypeBinding(SourceTypeBinding enclosingType, ReferenceBinding supertype) {
      LocalTypeBinding anonymousType = this.buildLocalType(enclosingType, enclosingType.fPackage);
      anonymousType.modifiers |= 134217728;
      int inheritedBits = supertype.typeBits;
      if ((inheritedBits & 4) != 0) {
         AbstractMethodDeclaration[] methods = this.referenceContext.methods;
         if (methods != null) {
            for(int i = 0; i < methods.length; ++i) {
               if (CharOperation.equals(TypeConstants.CLOSE, methods[i].selector) && methods[i].arguments == null) {
                  inheritedBits &= 1811;
                  break;
               }
            }
         }
      }

      anonymousType.typeBits |= inheritedBits;
      TypeReference typeReference;
      if (supertype.isInterface()) {
         anonymousType.setSuperClass(this.getJavaLangObject());
         anonymousType.setSuperInterfaces(new ReferenceBinding[]{supertype});
         typeReference = this.referenceContext.allocation.type;
         if (typeReference != null) {
            this.referenceContext.superInterfaces = new TypeReference[]{typeReference};
            if ((supertype.tagBits & 1073741824L) != 0L) {
               this.problemReporter().superTypeCannotUseWildcard(anonymousType, typeReference, supertype);
               anonymousType.tagBits |= 131072L;
               anonymousType.setSuperInterfaces(Binding.NO_SUPERINTERFACES);
            }
         }
      } else {
         anonymousType.setSuperClass(supertype);
         anonymousType.setSuperInterfaces(Binding.NO_SUPERINTERFACES);
         typeReference = this.referenceContext.allocation.type;
         if (typeReference != null) {
            this.referenceContext.superclass = typeReference;
            if (supertype.erasure().id == 41) {
               this.problemReporter().cannotExtendEnum(anonymousType, typeReference, supertype);
               anonymousType.tagBits |= 131072L;
               anonymousType.setSuperClass(this.getJavaLangObject());
            } else if (supertype.isFinal()) {
               this.problemReporter().anonymousClassCannotExtendFinalClass(typeReference, supertype);
               anonymousType.tagBits |= 131072L;
               anonymousType.setSuperClass(this.getJavaLangObject());
            } else if ((supertype.tagBits & 1073741824L) != 0L) {
               this.problemReporter().superTypeCannotUseWildcard(anonymousType, typeReference, supertype);
               anonymousType.tagBits |= 131072L;
               anonymousType.setSuperClass(this.getJavaLangObject());
            }
         }
      }

      this.connectMemberTypes();
      this.buildFieldsAndMethods();
      anonymousType.faultInTypesForFieldsAndMethods();
      anonymousType.verifyMethods(this.environment().methodVerifier());
   }

   void buildFields() {
      SourceTypeBinding sourceType = this.referenceContext.binding;
      if (!sourceType.areFieldsInitialized()) {
         if (this.referenceContext.fields == null) {
            sourceType.setFields(Binding.NO_FIELDS);
         } else {
            FieldDeclaration[] fields = this.referenceContext.fields;
            int size = fields.length;
            int count = 0;
            int i = 0;

            while(i < size) {
               switch (fields[i].getKind()) {
                  case 1:
                  case 3:
                     ++count;
                  case 2:
                  default:
                     ++i;
               }
            }

            FieldBinding[] fieldBindings = new FieldBinding[count];
            HashtableOfObject knownFieldNames = new HashtableOfObject(count);
            count = 0;

            for(int i = 0; i < size; ++i) {
               FieldDeclaration field = fields[i];
               if (field.getKind() != 2) {
                  FieldBinding fieldBinding = new FieldBinding(field, (TypeBinding)null, field.modifiers | 33554432, sourceType);
                  fieldBinding.id = count;
                  this.checkAndSetModifiersForField(fieldBinding, field);
                  if (!knownFieldNames.containsKey(field.name)) {
                     knownFieldNames.put(field.name, fieldBinding);
                     fieldBindings[count++] = fieldBinding;
                  } else {
                     FieldBinding previousBinding = (FieldBinding)knownFieldNames.get(field.name);
                     if (previousBinding != null) {
                        for(int f = 0; f < i; ++f) {
                           FieldDeclaration previousField = fields[f];
                           if (previousField.binding == previousBinding) {
                              this.problemReporter().duplicateFieldInType(sourceType, previousField);
                              break;
                           }
                        }
                     }

                     knownFieldNames.put(field.name, (Object)null);
                     this.problemReporter().duplicateFieldInType(sourceType, field);
                     field.binding = null;
                  }
               }
            }

            if (count != fieldBindings.length) {
               System.arraycopy(fieldBindings, 0, fieldBindings = new FieldBinding[count], 0, count);
            }

            sourceType.tagBits &= -12289L;
            sourceType.setFields(fieldBindings);
         }
      }
   }

   void buildFieldsAndMethods() {
      this.buildFields();
      this.buildMethods();
      SourceTypeBinding sourceType = this.referenceContext.binding;
      if (!sourceType.isPrivate() && sourceType.superclass instanceof SourceTypeBinding && sourceType.superclass.isPrivate()) {
         ((SourceTypeBinding)sourceType.superclass).tagIndirectlyAccessibleMembers();
      }

      if (sourceType.isMemberType() && !sourceType.isLocalType()) {
         ((MemberTypeBinding)sourceType).checkSyntheticArgsAndFields();
      }

      ReferenceBinding[] memberTypes = sourceType.memberTypes;
      int i = 0;

      for(int length = memberTypes.length; i < length; ++i) {
         ((SourceTypeBinding)memberTypes[i]).scope.buildFieldsAndMethods();
      }

   }

   private LocalTypeBinding buildLocalType(SourceTypeBinding var1, PackageBinding var2) {
      // $FF: Couldn't be decompiled
   }

   void buildLocalTypeBinding(SourceTypeBinding enclosingType) {
      LocalTypeBinding localType = this.buildLocalType(enclosingType, enclosingType.fPackage);
      this.connectTypeHierarchy();
      if (this.compilerOptions().sourceLevel >= 3211264L) {
         this.checkParameterizedTypeBounds();
         this.checkParameterizedSuperTypeCollisions();
      }

      this.buildFieldsAndMethods();
      localType.faultInTypesForFieldsAndMethods();
      this.referenceContext.binding.verifyMethods(this.environment().methodVerifier());
   }

   private void buildMemberTypes(AccessRestriction var1) {
      // $FF: Couldn't be decompiled
   }

   void buildMethods() {
      SourceTypeBinding sourceType = this.referenceContext.binding;
      if (!sourceType.areMethodsInitialized()) {
         boolean isEnum = TypeDeclaration.kind(this.referenceContext.modifiers) == 3;
         if (this.referenceContext.methods == null && !isEnum) {
            this.referenceContext.binding.setMethods(Binding.NO_METHODS);
         } else {
            AbstractMethodDeclaration[] methods = this.referenceContext.methods;
            int size = methods == null ? 0 : methods.length;
            int clinitIndex = -1;

            int count;
            for(count = 0; count < size; ++count) {
               if (methods[count].isClinit()) {
                  clinitIndex = count;
                  break;
               }
            }

            count = isEnum ? 2 : 0;
            MethodBinding[] methodBindings = new MethodBinding[(clinitIndex == -1 ? size : size - 1) + count];
            if (isEnum) {
               methodBindings[0] = sourceType.addSyntheticEnumMethod(TypeConstants.VALUES);
               methodBindings[1] = sourceType.addSyntheticEnumMethod(TypeConstants.VALUEOF);
            }

            boolean hasNativeMethods = false;
            int i;
            int i;
            if (sourceType.isAbstract()) {
               for(i = 0; i < size; ++i) {
                  if (i != clinitIndex) {
                     MethodScope scope = new MethodScope(this, methods[i], false);
                     MethodBinding methodBinding = scope.createMethod(methods[i]);
                     if (methodBinding != null) {
                        methodBindings[count++] = methodBinding;
                        hasNativeMethods = hasNativeMethods || methodBinding.isNative();
                     }
                  }
               }
            } else {
               boolean hasAbstractMethods = false;

               for(i = 0; i < size; ++i) {
                  if (i != clinitIndex) {
                     MethodScope scope = new MethodScope(this, methods[i], false);
                     MethodBinding methodBinding = scope.createMethod(methods[i]);
                     if (methodBinding != null) {
                        methodBindings[count++] = methodBinding;
                        hasAbstractMethods = hasAbstractMethods || methodBinding.isAbstract();
                        hasNativeMethods = hasNativeMethods || methodBinding.isNative();
                     }
                  }
               }

               if (hasAbstractMethods) {
                  this.problemReporter().abstractMethodInConcreteClass(sourceType);
               }
            }

            if (count != methodBindings.length) {
               System.arraycopy(methodBindings, 0, methodBindings = new MethodBinding[count], 0, count);
            }

            sourceType.tagBits &= -49153L;
            sourceType.setMethods(methodBindings);
            if (hasNativeMethods) {
               for(i = 0; i < methodBindings.length; ++i) {
                  methodBindings[i].modifiers |= 134217728;
               }

               FieldBinding[] fields = sourceType.unResolvedFields();

               for(i = 0; i < fields.length; ++i) {
                  fields[i].modifiers |= 134217728;
               }
            }

            if (isEnum && this.compilerOptions().isAnnotationBasedNullAnalysisEnabled) {
               LookupEnvironment environment = this.environment();
               ((SyntheticMethodBinding)methodBindings[0]).markNonNull(environment);
               ((SyntheticMethodBinding)methodBindings[1]).markNonNull(environment);
            }

         }
      }
   }

   SourceTypeBinding buildType(SourceTypeBinding enclosingType, PackageBinding packageBinding, AccessRestriction accessRestriction) {
      this.referenceContext.scope = this;
      this.referenceContext.staticInitializerScope = new MethodScope(this, this.referenceContext, true);
      this.referenceContext.initializerScope = new MethodScope(this, this.referenceContext, false);
      char[][] className;
      if (enclosingType == null) {
         className = CharOperation.arrayConcat(packageBinding.compoundName, this.referenceContext.name);
         this.referenceContext.binding = new SourceTypeBinding(className, packageBinding, this);
      } else {
         className = CharOperation.deepCopy(enclosingType.compoundName);
         className[className.length - 1] = CharOperation.concat(className[className.length - 1], this.referenceContext.name, '$');
         ReferenceBinding existingType = packageBinding.getType0(className[className.length - 1]);
         if (existingType != null && !(existingType instanceof UnresolvedReferenceBinding)) {
            this.parent.problemReporter().duplicateNestedType(this.referenceContext);
         }

         this.referenceContext.binding = new MemberTypeBinding(className, this, enclosingType);
      }

      SourceTypeBinding sourceType = this.referenceContext.binding;
      sourceType.module = this.module();
      this.environment().setAccessRestriction(sourceType, accessRestriction);
      TypeParameter[] typeParameters = this.referenceContext.typeParameters;
      sourceType.typeVariables = typeParameters != null && typeParameters.length != 0 ? null : Binding.NO_TYPE_VARIABLES;
      sourceType.fPackage.addType(sourceType);
      this.checkAndSetModifiers();
      this.buildTypeVariables();
      this.buildMemberTypes(accessRestriction);
      return sourceType;
   }

   private void buildTypeVariables() {
      SourceTypeBinding sourceType = this.referenceContext.binding;
      TypeParameter[] typeParameters = this.referenceContext.typeParameters;
      if (typeParameters != null && typeParameters.length != 0) {
         sourceType.setTypeVariables(Binding.NO_TYPE_VARIABLES);
         if (sourceType.id == 1) {
            this.problemReporter().objectCannotBeGeneric(this.referenceContext);
         } else {
            sourceType.setTypeVariables(this.createTypeVariables(typeParameters, sourceType));
            sourceType.modifiers |= 1073741824;
         }
      } else {
         sourceType.setTypeVariables(Binding.NO_TYPE_VARIABLES);
      }
   }

   void resolveTypeParameter(TypeParameter typeParameter) {
      typeParameter.resolve(this);
   }

   private void checkAndSetModifiers() {
      SourceTypeBinding sourceType = this.referenceContext.binding;
      int modifiers = sourceType.modifiers;
      if ((modifiers & 4194304) != 0) {
         this.problemReporter().duplicateModifierForType(sourceType);
      }

      ReferenceBinding enclosingType = sourceType.enclosingType();
      boolean isMemberType = sourceType.isMemberType();
      if (isMemberType) {
         if (sourceType.hasEnclosingInstanceContext()) {
            modifiers |= enclosingType.modifiers & 1073741824;
         }

         modifiers |= enclosingType.modifiers & 2048;
         if (enclosingType.isInterface()) {
            modifiers |= 1;
         }

         if (sourceType.isEnum()) {
            if (!enclosingType.isStatic()) {
               this.problemReporter().nonStaticContextForEnumMemberType(sourceType);
            } else {
               modifiers |= 8;
            }
         } else if (sourceType.isInterface()) {
            modifiers |= 8;
         }
      } else if (sourceType.isLocalType()) {
         if (sourceType.isEnum()) {
            this.problemReporter().illegalLocalTypeDeclaration(this.referenceContext);
            sourceType.modifiers = 0;
            return;
         }

         if (sourceType.isAnonymousType()) {
            if (this.compilerOptions().complianceLevel < 3473408L) {
               modifiers |= 16;
            }

            if (this.referenceContext.allocation.type == null) {
               modifiers |= 16384;
            }
         }

         Scope scope = this;

         do {
            switch (((Scope)scope).kind) {
               case 2:
                  MethodScope methodScope = (MethodScope)scope;
                  if (methodScope.isLambdaScope()) {
                     methodScope = methodScope.namedMethodScope();
                  }

                  if (methodScope.isInsideInitializer()) {
                     SourceTypeBinding type = ((TypeDeclaration)methodScope.referenceContext).binding;
                     if (methodScope.initializedField != null) {
                        if (methodScope.initializedField.isViewedAsDeprecated() && !sourceType.isDeprecated()) {
                           modifiers |= 2097152;
                        }
                     } else {
                        if (type.isStrictfp()) {
                           modifiers |= 2048;
                        }

                        if (type.isViewedAsDeprecated() && !sourceType.isDeprecated()) {
                           modifiers |= 2097152;
                        }
                     }
                  } else {
                     MethodBinding method = ((AbstractMethodDeclaration)methodScope.referenceContext).binding;
                     if (method != null) {
                        if (method.isStrictfp()) {
                           modifiers |= 2048;
                        }

                        if (method.isViewedAsDeprecated() && !sourceType.isDeprecated()) {
                           modifiers |= 2097152;
                        }
                     }
                  }
                  break;
               case 3:
                  if (enclosingType.isStrictfp()) {
                     modifiers |= 2048;
                  }

                  if (enclosingType.isViewedAsDeprecated() && !sourceType.isDeprecated()) {
                     modifiers |= 2097152;
                     sourceType.tagBits |= enclosingType.tagBits & 4611686018427387904L;
                  }
            }

            scope = ((Scope)scope).parent;
         } while(scope != null);
      }

      int realModifiers = modifiers & '\uffff';
      if ((realModifiers & 512) != 0) {
         if (isMemberType) {
            if ((realModifiers & -11792) != 0) {
               if ((realModifiers & 8192) != 0) {
                  this.problemReporter().illegalModifierForAnnotationMemberType(sourceType);
               } else {
                  this.problemReporter().illegalModifierForMemberInterface(sourceType);
               }
            }
         } else if ((realModifiers & -11778) != 0) {
            if ((realModifiers & 8192) != 0) {
               this.problemReporter().illegalModifierForAnnotationType(sourceType);
            } else {
               this.problemReporter().illegalModifierForInterface(sourceType);
            }
         }

         if (sourceType.sourceName == TypeConstants.PACKAGE_INFO_NAME && this.compilerOptions().targetJDK > 3211264L) {
            modifiers |= 4096;
         }

         modifiers |= 1024;
      } else if ((realModifiers & 16384) != 0) {
         if (isMemberType) {
            if ((realModifiers & -18448) != 0) {
               this.problemReporter().illegalModifierForMemberEnum(sourceType);
               modifiers &= -1025;
               realModifiers &= -1025;
            }
         } else if (!sourceType.isLocalType() && (realModifiers & -18434) != 0) {
            this.problemReporter().illegalModifierForEnum(sourceType);
         }

         if (!sourceType.isAnonymousType()) {
            label285: {
               int fieldsLength;
               TypeDeclaration typeDeclaration;
               FieldDeclaration[] fields;
               if ((this.referenceContext.bits & 2048) != 0) {
                  modifiers |= 1024;
               } else {
                  typeDeclaration = this.referenceContext;
                  fields = typeDeclaration.fields;
                  fieldsLength = fields == null ? 0 : fields.length;
                  if (fieldsLength != 0) {
                     AbstractMethodDeclaration[] methods = typeDeclaration.methods;
                     int methodsLength = methods == null ? 0 : methods.length;
                     boolean definesAbstractMethod = typeDeclaration.superInterfaces != null;

                     for(int i = 0; i < methodsLength && !definesAbstractMethod; ++i) {
                        definesAbstractMethod = methods[i].isAbstract();
                     }

                     if (definesAbstractMethod) {
                        boolean needAbstractBit = false;
                        int i = 0;

                        while(true) {
                           if (i >= fieldsLength) {
                              if (needAbstractBit) {
                                 modifiers |= 1024;
                              }
                              break;
                           }

                           FieldDeclaration fieldDecl = fields[i];
                           if (fieldDecl.getKind() == 3) {
                              if (!(fieldDecl.initialization instanceof QualifiedAllocationExpression)) {
                                 break;
                              }

                              needAbstractBit = true;
                           }

                           ++i;
                        }
                     }
                  }
               }

               typeDeclaration = this.referenceContext;
               fields = typeDeclaration.fields;
               if (fields != null) {
                  fieldsLength = 0;

                  for(int fieldsLength = fields.length; fieldsLength < fieldsLength; ++fieldsLength) {
                     FieldDeclaration fieldDecl = fields[fieldsLength];
                     if (fieldDecl.getKind() == 3 && fieldDecl.initialization instanceof QualifiedAllocationExpression) {
                        break label285;
                     }
                  }
               }

               modifiers |= 16;
            }
         }
      } else {
         if (isMemberType) {
            if ((realModifiers & -3104) != 0) {
               this.problemReporter().illegalModifierForMemberClass(sourceType);
            }
         } else if (sourceType.isLocalType()) {
            if ((realModifiers & -3089) != 0) {
               this.problemReporter().illegalModifierForLocalClass(sourceType);
            }
         } else if ((realModifiers & -3090) != 0) {
            this.problemReporter().illegalModifierForClass(sourceType);
         }

         if ((realModifiers & 1040) == 1040) {
            this.problemReporter().illegalModifierCombinationFinalAbstractForClass(sourceType);
         }
      }

      if (isMemberType) {
         if (enclosingType.isInterface()) {
            if ((realModifiers & 6) != 0) {
               this.problemReporter().illegalVisibilityModifierForInterfaceMemberType(sourceType);
               if ((realModifiers & 4) != 0) {
                  modifiers &= -5;
               }

               if ((realModifiers & 2) != 0) {
                  modifiers &= -3;
               }
            }
         } else {
            int accessorBits = realModifiers & 7;
            if ((accessorBits & accessorBits - 1) > 1) {
               this.problemReporter().illegalVisibilityModifierCombinationForMemberType(sourceType);
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
         }

         if ((realModifiers & 8) == 0) {
            if (enclosingType.isInterface()) {
               modifiers |= 8;
            }
         } else if (!enclosingType.isStatic()) {
            this.problemReporter().illegalStaticModifierForMemberType(sourceType);
         }
      }

      sourceType.modifiers = modifiers;
   }

   private void checkAndSetModifiersForField(FieldBinding fieldBinding, FieldDeclaration fieldDecl) {
      int modifiers = fieldBinding.modifiers;
      ReferenceBinding declaringClass = fieldBinding.declaringClass;
      if ((modifiers & 4194304) != 0) {
         this.problemReporter().duplicateModifierForField(declaringClass, fieldDecl);
      }

      if (declaringClass.isInterface()) {
         modifiers |= 25;
         if ((modifiers & '\uffff') != 25) {
            if ((declaringClass.modifiers & 8192) != 0) {
               this.problemReporter().illegalModifierForAnnotationField(fieldDecl);
            } else {
               this.problemReporter().illegalModifierForInterfaceField(fieldDecl);
            }
         }

         fieldBinding.modifiers = modifiers;
      } else if (fieldDecl.getKind() == 3) {
         if ((modifiers & '\uffff') != 0) {
            this.problemReporter().illegalModifierForEnumConstant(declaringClass, fieldDecl);
         }

         fieldBinding.modifiers |= 134234137;
      } else {
         int realModifiers = modifiers & '\uffff';
         if ((realModifiers & -224) != 0) {
            this.problemReporter().illegalModifierForField(declaringClass, fieldDecl);
            modifiers &= -65313;
         }

         int accessorBits = realModifiers & 7;
         if ((accessorBits & accessorBits - 1) > 1) {
            this.problemReporter().illegalVisibilityModifierCombinationForField(declaringClass, fieldDecl);
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

         if ((realModifiers & 80) == 80) {
            this.problemReporter().illegalModifierCombinationFinalVolatileForField(declaringClass, fieldDecl);
         }

         if (fieldDecl.initialization == null && (modifiers & 16) != 0) {
            modifiers |= 67108864;
         }

         fieldBinding.modifiers = modifiers;
      }
   }

   public void checkParameterizedSuperTypeCollisions() {
      SourceTypeBinding sourceType = this.referenceContext.binding;
      ReferenceBinding[] interfaces = sourceType.superInterfaces;
      Map invocations = new HashMap(2);
      ReferenceBinding itsSuperclass = sourceType.isInterface() ? null : sourceType.superclass;
      int i = 0;

      int i;
      int size;
      for(i = interfaces.length; i < i; ++i) {
         ReferenceBinding one = interfaces[i];
         if (one != null && (itsSuperclass == null || !this.hasErasedCandidatesCollisions(itsSuperclass, one, invocations, sourceType, this.referenceContext))) {
            for(size = 0; size < i; ++size) {
               ReferenceBinding two = interfaces[size];
               if (two != null && this.hasErasedCandidatesCollisions(one, two, invocations, sourceType, this.referenceContext)) {
                  break;
               }
            }
         }
      }

      TypeParameter[] typeParameters = this.referenceContext.typeParameters;
      i = 0;

      int i;
      label91:
      for(i = typeParameters == null ? 0 : typeParameters.length; i < i; ++i) {
         TypeParameter typeParameter = typeParameters[i];
         TypeVariableBinding typeVariable = typeParameter.binding;
         if (typeVariable != null && typeVariable.isValidBinding()) {
            TypeReference[] boundRefs = typeParameter.bounds;
            if (boundRefs != null) {
               boolean checkSuperclass = TypeBinding.equalsEquals(typeVariable.firstBound, typeVariable.superclass);
               int j = 0;

               for(int boundLength = boundRefs.length; j < boundLength; ++j) {
                  TypeReference typeRef = boundRefs[j];
                  TypeBinding superType = typeRef.resolvedType;
                  if (superType != null && superType.isValidBinding()) {
                     if (checkSuperclass && this.hasErasedCandidatesCollisions(superType, typeVariable.superclass, invocations, typeVariable, typeRef)) {
                        break;
                     }

                     int index = typeVariable.superInterfaces.length;

                     while(true) {
                        --index;
                        if (index < 0) {
                           break;
                        }

                        if (this.hasErasedCandidatesCollisions(superType, typeVariable.superInterfaces[index], invocations, typeVariable, typeRef)) {
                           continue label91;
                        }
                     }
                  }
               }
            }
         }
      }

      ReferenceBinding[] memberTypes = this.referenceContext.binding.memberTypes;
      if (memberTypes != null && memberTypes != Binding.NO_MEMBER_TYPES) {
         i = 0;

         for(size = memberTypes.length; i < size; ++i) {
            ((SourceTypeBinding)memberTypes[i]).scope.checkParameterizedSuperTypeCollisions();
         }
      }

   }

   private void checkForInheritedMemberTypes(SourceTypeBinding sourceType) {
      ReferenceBinding currentType = sourceType;
      ReferenceBinding[] interfacesToVisit = null;
      int nextPosition = 0;

      int i;
      int itsLength;
      do {
         if (((ReferenceBinding)currentType).hasMemberTypes()) {
            return;
         }

         ReferenceBinding[] itsInterfaces = ((ReferenceBinding)currentType).superInterfaces();
         if (itsInterfaces != null && itsInterfaces != Binding.NO_SUPERINTERFACES) {
            if (interfacesToVisit == null) {
               interfacesToVisit = itsInterfaces;
               nextPosition = itsInterfaces.length;
            } else {
               i = itsInterfaces.length;
               if (nextPosition + i >= interfacesToVisit.length) {
                  System.arraycopy(interfacesToVisit, 0, interfacesToVisit = new ReferenceBinding[nextPosition + i + 5], 0, nextPosition);
               }

               label118:
               for(int a = 0; a < i; ++a) {
                  ReferenceBinding next = itsInterfaces[a];

                  for(itsLength = 0; itsLength < nextPosition; ++itsLength) {
                     if (TypeBinding.equalsEquals(next, interfacesToVisit[itsLength])) {
                        continue label118;
                     }
                  }

                  interfacesToVisit[nextPosition++] = next;
               }
            }
         }
      } while((currentType = ((ReferenceBinding)currentType).superclass()) != null && (((ReferenceBinding)currentType).tagBits & 65536L) == 0L);

      if (interfacesToVisit != null) {
         boolean needToTag = false;

         for(i = 0; i < nextPosition; ++i) {
            ReferenceBinding anInterface = interfacesToVisit[i];
            if ((anInterface.tagBits & 65536L) == 0L) {
               if (anInterface.hasMemberTypes()) {
                  return;
               }

               needToTag = true;
               ReferenceBinding[] itsInterfaces = anInterface.superInterfaces();
               if (itsInterfaces != null && itsInterfaces != Binding.NO_SUPERINTERFACES) {
                  itsLength = itsInterfaces.length;
                  if (nextPosition + itsLength >= interfacesToVisit.length) {
                     System.arraycopy(interfacesToVisit, 0, interfacesToVisit = new ReferenceBinding[nextPosition + itsLength + 5], 0, nextPosition);
                  }

                  label92:
                  for(int a = 0; a < itsLength; ++a) {
                     ReferenceBinding next = itsInterfaces[a];

                     for(int b = 0; b < nextPosition; ++b) {
                        if (TypeBinding.equalsEquals(next, interfacesToVisit[b])) {
                           continue label92;
                        }
                     }

                     interfacesToVisit[nextPosition++] = next;
                  }
               }
            }
         }

         if (needToTag) {
            for(i = 0; i < nextPosition; ++i) {
               interfacesToVisit[i].tagBits |= 65536L;
            }
         }
      }

      currentType = sourceType;

      do {
         ((ReferenceBinding)currentType).tagBits |= 65536L;
      } while((currentType = ((ReferenceBinding)currentType).superclass()) != null && (((ReferenceBinding)currentType).tagBits & 65536L) == 0L);

   }

   public void checkParameterizedTypeBounds() {
      int i = 0;

      int i;
      for(i = this.deferredBoundChecks == null ? 0 : this.deferredBoundChecks.size(); i < i; ++i) {
         Object toCheck = this.deferredBoundChecks.get(i);
         if (toCheck instanceof TypeReference) {
            ((TypeReference)toCheck).checkBounds(this);
         } else if (toCheck instanceof Runnable) {
            ((Runnable)toCheck).run();
         }
      }

      this.deferredBoundChecks = null;
      ReferenceBinding[] memberTypes = this.referenceContext.binding.memberTypes;
      if (memberTypes != null && memberTypes != Binding.NO_MEMBER_TYPES) {
         i = 0;

         for(int size = memberTypes.length; i < size; ++i) {
            ((SourceTypeBinding)memberTypes[i]).scope.checkParameterizedTypeBounds();
         }
      }

   }

   private void connectMemberTypes() {
      SourceTypeBinding sourceType = this.referenceContext.binding;
      ReferenceBinding[] memberTypes = sourceType.memberTypes;
      if (memberTypes != null && memberTypes != Binding.NO_MEMBER_TYPES) {
         int i = 0;

         for(int size = memberTypes.length; i < size; ++i) {
            ((SourceTypeBinding)memberTypes[i]).scope.connectTypeHierarchy();
         }
      }

   }

   private boolean connectSuperclass() {
      SourceTypeBinding sourceType = this.referenceContext.binding;
      if (sourceType.id == 1) {
         sourceType.setSuperClass((ReferenceBinding)null);
         sourceType.setSuperInterfaces(Binding.NO_SUPERINTERFACES);
         if (!sourceType.isClass()) {
            this.problemReporter().objectMustBeClass(sourceType);
         }

         if (this.referenceContext.superclass != null || this.referenceContext.superInterfaces != null && this.referenceContext.superInterfaces.length > 0) {
            this.problemReporter().objectCannotHaveSuperTypes(sourceType);
         }

         return true;
      } else if (this.referenceContext.superclass == null) {
         if (sourceType.isEnum() && this.compilerOptions().sourceLevel >= 3211264L) {
            return this.connectEnumSuperclass();
         } else {
            sourceType.setSuperClass(this.getJavaLangObject());
            return !this.detectHierarchyCycle(sourceType, sourceType.superclass, (TypeReference)null);
         }
      } else {
         TypeReference superclassRef = this.referenceContext.superclass;
         ReferenceBinding superclass = this.findSupertype(superclassRef);
         if (superclass != null) {
            if (!superclass.isClass() && (superclass.tagBits & 128L) == 0L) {
               this.problemReporter().superclassMustBeAClass(sourceType, superclassRef, superclass);
            } else if (superclass.isFinal()) {
               this.problemReporter().classExtendFinalClass(sourceType, superclassRef, superclass);
            } else if ((superclass.tagBits & 1073741824L) != 0L) {
               this.problemReporter().superTypeCannotUseWildcard(sourceType, superclassRef, superclass);
            } else {
               if (superclass.erasure().id != 41) {
                  if ((superclass.tagBits & 131072L) == 0L && superclassRef.resolvedType.isValidBinding()) {
                     sourceType.setSuperClass(superclass);
                     sourceType.typeBits |= superclass.typeBits & 1811;
                     if ((sourceType.typeBits & 3) != 0) {
                        sourceType.typeBits |= sourceType.applyCloseableClassWhitelists();
                     }

                     return true;
                  }

                  sourceType.setSuperClass(superclass);
                  sourceType.tagBits |= 131072L;
                  return superclassRef.resolvedType.isValidBinding();
               }

               this.problemReporter().cannotExtendEnum(sourceType, superclassRef, superclass);
            }
         }

         sourceType.tagBits |= 131072L;
         sourceType.setSuperClass(this.getJavaLangObject());
         if ((sourceType.superclass.tagBits & 256L) == 0L) {
            this.detectHierarchyCycle(sourceType, sourceType.superclass, (TypeReference)null);
         }

         return false;
      }
   }

   private boolean connectEnumSuperclass() {
      SourceTypeBinding sourceType = this.referenceContext.binding;
      ReferenceBinding rootEnumType = this.getJavaLangEnum();
      if ((rootEnumType.tagBits & 128L) != 0L) {
         sourceType.tagBits |= 131072L;
         sourceType.setSuperClass(rootEnumType);
         return false;
      } else {
         boolean foundCycle = this.detectHierarchyCycle(sourceType, rootEnumType, (TypeReference)null);
         TypeVariableBinding[] refTypeVariables = rootEnumType.typeVariables();
         if (refTypeVariables == Binding.NO_TYPE_VARIABLES) {
            this.problemReporter().nonGenericTypeCannotBeParameterized(0, (ASTNode)null, rootEnumType, new TypeBinding[]{sourceType});
            return false;
         } else if (1 != refTypeVariables.length) {
            this.problemReporter().incorrectArityForParameterizedType((ASTNode)null, rootEnumType, new TypeBinding[]{sourceType});
            return false;
         } else {
            ParameterizedTypeBinding superType = this.environment().createParameterizedType(rootEnumType, new TypeBinding[]{this.environment().convertToRawType(sourceType, false)}, (ReferenceBinding)null);
            sourceType.tagBits |= superType.tagBits & 131072L;
            sourceType.setSuperClass(superType);
            if (!refTypeVariables[0].boundCheck(superType, sourceType, this, (ASTNode)null).isOKbyJLS()) {
               this.problemReporter().typeMismatchError(rootEnumType, (TypeVariableBinding)refTypeVariables[0], (ReferenceBinding)sourceType, (ASTNode)null);
            }

            return !foundCycle;
         }
      }
   }

   private boolean connectSuperInterfaces() {
      SourceTypeBinding sourceType = this.referenceContext.binding;
      sourceType.setSuperInterfaces(Binding.NO_SUPERINTERFACES);
      if (this.referenceContext.superInterfaces == null) {
         if (sourceType.isAnnotationType() && this.compilerOptions().sourceLevel >= 3211264L) {
            ReferenceBinding annotationType = this.getJavaLangAnnotationAnnotation();
            boolean foundCycle = this.detectHierarchyCycle(sourceType, annotationType, (TypeReference)null);
            sourceType.setSuperInterfaces(new ReferenceBinding[]{annotationType});
            return !foundCycle;
         } else {
            return true;
         }
      } else if (sourceType.id == 1) {
         return true;
      } else {
         boolean noProblems = true;
         int length = this.referenceContext.superInterfaces.length;
         ReferenceBinding[] interfaceBindings = new ReferenceBinding[length];
         int count = 0;

         label83:
         for(int i = 0; i < length; ++i) {
            TypeReference superInterfaceRef = this.referenceContext.superInterfaces[i];
            ReferenceBinding superInterface = this.findSupertype(superInterfaceRef);
            if (superInterface == null) {
               sourceType.tagBits |= 131072L;
               noProblems = false;
            } else {
               for(int j = 0; j < i; ++j) {
                  if (TypeBinding.equalsEquals(interfaceBindings[j], superInterface)) {
                     this.problemReporter().duplicateSuperinterface(sourceType, superInterfaceRef, superInterface);
                     sourceType.tagBits |= 131072L;
                     noProblems = false;
                     continue label83;
                  }
               }

               if (!superInterface.isInterface() && (superInterface.tagBits & 128L) == 0L) {
                  this.problemReporter().superinterfaceMustBeAnInterface(sourceType, superInterfaceRef, superInterface);
                  sourceType.tagBits |= 131072L;
                  noProblems = false;
               } else {
                  if (superInterface.isAnnotationType()) {
                     this.problemReporter().annotationTypeUsedAsSuperinterface(sourceType, superInterfaceRef, superInterface);
                  }

                  if ((superInterface.tagBits & 1073741824L) != 0L) {
                     this.problemReporter().superTypeCannotUseWildcard(sourceType, superInterfaceRef, superInterface);
                     sourceType.tagBits |= 131072L;
                     noProblems = false;
                  } else {
                     if ((superInterface.tagBits & 131072L) != 0L || !superInterfaceRef.resolvedType.isValidBinding()) {
                        sourceType.tagBits |= 131072L;
                        noProblems &= superInterfaceRef.resolvedType.isValidBinding();
                     }

                     sourceType.typeBits |= superInterface.typeBits & 1811;
                     if ((sourceType.typeBits & 3) != 0) {
                        sourceType.typeBits |= sourceType.applyCloseableInterfaceWhitelists();
                     }

                     interfaceBindings[count++] = superInterface;
                  }
               }
            }
         }

         if (count > 0) {
            if (count != length) {
               System.arraycopy(interfaceBindings, 0, interfaceBindings = new ReferenceBinding[count], 0, count);
            }

            sourceType.setSuperInterfaces(interfaceBindings);
         }

         return noProblems;
      }
   }

   void connectTypeHierarchy() {
      SourceTypeBinding sourceType = this.referenceContext.binding;
      CompilationUnitScope compilationUnitScope = this.compilationUnitScope();
      boolean wasAlreadyConnecting = compilationUnitScope.connectingHierarchy;
      compilationUnitScope.connectingHierarchy = true;

      try {
         if ((sourceType.tagBits & 256L) == 0L) {
            sourceType.tagBits |= 256L;
            this.environment().typesBeingConnected.add(sourceType);
            boolean noProblems = this.connectSuperclass();
            noProblems &= this.connectSuperInterfaces();
            this.environment().typesBeingConnected.remove(sourceType);
            sourceType.tagBits |= 512L;
            noProblems &= this.connectTypeVariables(this.referenceContext.typeParameters, false);
            sourceType.tagBits |= 262144L;
            if (noProblems && sourceType.isHierarchyInconsistent()) {
               this.problemReporter().hierarchyHasProblems(sourceType);
            }
         }

         this.connectMemberTypes();
      } finally {
         compilationUnitScope.connectingHierarchy = wasAlreadyConnecting;
      }

      LookupEnvironment env = this.environment();

      try {
         env.missingClassFileLocation = this.referenceContext;
         this.checkForInheritedMemberTypes(sourceType);
      } catch (AbortCompilation var13) {
         var13.updateContext((ASTNode)this.referenceContext, this.referenceCompilationUnit().compilationResult);
         throw var13;
      } finally {
         env.missingClassFileLocation = null;
      }

   }

   public boolean deferCheck(Runnable check) {
      if (this.compilationUnitScope().connectingHierarchy) {
         if (this.deferredBoundChecks == null) {
            this.deferredBoundChecks = new ArrayList();
         }

         this.deferredBoundChecks.add(check);
         return true;
      } else {
         return false;
      }
   }

   private void connectTypeHierarchyWithoutMembers() {
      if (this.parent instanceof CompilationUnitScope) {
         if (((CompilationUnitScope)this.parent).imports == null) {
            ((CompilationUnitScope)this.parent).checkAndSetImports();
         }
      } else if (this.parent instanceof ClassScope) {
         ((ClassScope)this.parent).connectTypeHierarchyWithoutMembers();
      }

      SourceTypeBinding sourceType = this.referenceContext.binding;
      if ((sourceType.tagBits & 256L) == 0L) {
         CompilationUnitScope compilationUnitScope = this.compilationUnitScope();
         boolean wasAlreadyConnecting = compilationUnitScope.connectingHierarchy;
         compilationUnitScope.connectingHierarchy = true;

         try {
            sourceType.tagBits |= 256L;
            this.environment().typesBeingConnected.add(sourceType);
            boolean noProblems = this.connectSuperclass();
            noProblems &= this.connectSuperInterfaces();
            this.environment().typesBeingConnected.remove(sourceType);
            sourceType.tagBits |= 512L;
            noProblems &= this.connectTypeVariables(this.referenceContext.typeParameters, false);
            sourceType.tagBits |= 262144L;
            if (noProblems && sourceType.isHierarchyInconsistent()) {
               this.problemReporter().hierarchyHasProblems(sourceType);
            }
         } finally {
            compilationUnitScope.connectingHierarchy = wasAlreadyConnecting;
         }

      }
   }

   public boolean detectHierarchyCycle(TypeBinding superType, TypeReference reference) {
      if (!(superType instanceof ReferenceBinding)) {
         return false;
      } else if (reference == this.superTypeReference) {
         if (((TypeBinding)superType).isTypeVariable()) {
            return false;
         } else {
            if (((TypeBinding)superType).isParameterizedType()) {
               superType = ((ParameterizedTypeBinding)superType).genericType();
            }

            this.compilationUnitScope().recordSuperTypeReference((TypeBinding)superType);
            return this.detectHierarchyCycle(this.referenceContext.binding, (ReferenceBinding)superType, reference);
         }
      } else {
         if ((((TypeBinding)superType).tagBits & 256L) == 0L && superType instanceof SourceTypeBinding) {
            ((SourceTypeBinding)superType).scope.connectTypeHierarchyWithoutMembers();
         }

         return false;
      }
   }

   private boolean detectHierarchyCycle(SourceTypeBinding sourceType, ReferenceBinding superType, TypeReference reference) {
      if (superType.isRawType()) {
         superType = ((RawTypeBinding)superType).genericType();
      }

      if (TypeBinding.equalsEquals(sourceType, superType)) {
         this.problemReporter().hierarchyCircularity(sourceType, superType, reference);
         sourceType.tagBits |= 131072L;
         return true;
      } else {
         if (superType.isMemberType()) {
            ReferenceBinding current = superType.enclosingType();

            do {
               if (current.isHierarchyBeingActivelyConnected()) {
                  this.problemReporter().hierarchyCircularity(sourceType, current, reference);
                  sourceType.tagBits |= 131072L;
                  current.tagBits |= 131072L;
                  return true;
               }
            } while((current = current.enclosingType()) != null);
         }

         ReferenceBinding parentType;
         if (superType.isBinaryBinding()) {
            if (superType.problemId() != 1 && (superType.tagBits & 131072L) != 0L) {
               sourceType.tagBits |= 131072L;
               this.problemReporter().hierarchyHasProblems(sourceType);
               return true;
            } else {
               boolean hasCycle = false;
               parentType = superType.superclass();
               if (parentType != null) {
                  if (TypeBinding.equalsEquals(sourceType, parentType)) {
                     this.problemReporter().hierarchyCircularity(sourceType, superType, reference);
                     sourceType.tagBits |= 131072L;
                     superType.tagBits |= 131072L;
                     return true;
                  }

                  if (parentType.isParameterizedType()) {
                     parentType = ((ParameterizedTypeBinding)parentType).genericType();
                  }

                  hasCycle |= this.detectHierarchyCycle(sourceType, parentType, reference);
                  if ((parentType.tagBits & 131072L) != 0L) {
                     sourceType.tagBits |= 131072L;
                     parentType.tagBits |= 131072L;
                  }
               }

               ReferenceBinding[] itsInterfaces = superType.superInterfaces();
               if (itsInterfaces != null && itsInterfaces != Binding.NO_SUPERINTERFACES) {
                  int i = 0;

                  for(int length = itsInterfaces.length; i < length; ++i) {
                     ReferenceBinding anInterface = itsInterfaces[i];
                     if (TypeBinding.equalsEquals(sourceType, anInterface)) {
                        this.problemReporter().hierarchyCircularity(sourceType, superType, reference);
                        sourceType.tagBits |= 131072L;
                        superType.tagBits |= 131072L;
                        return true;
                     }

                     if (anInterface.isParameterizedType()) {
                        anInterface = ((ParameterizedTypeBinding)anInterface).genericType();
                     }

                     hasCycle |= this.detectHierarchyCycle(sourceType, anInterface, reference);
                     if ((anInterface.tagBits & 131072L) != 0L) {
                        sourceType.tagBits |= 131072L;
                        superType.tagBits |= 131072L;
                     }
                  }
               }

               return hasCycle;
            }
         } else {
            if (superType.isHierarchyBeingActivelyConnected()) {
               TypeReference ref = ((SourceTypeBinding)superType).scope.superTypeReference;
               if (ref != null && ref.resolvedType != null) {
                  parentType = (ReferenceBinding)ref.resolvedType;

                  do {
                     if (parentType.isHierarchyBeingActivelyConnected()) {
                        this.problemReporter().hierarchyCircularity(sourceType, superType, reference);
                        sourceType.tagBits |= 131072L;
                        superType.tagBits |= 131072L;
                        return true;
                     }
                  } while((parentType = parentType.enclosingType()) != null);
               }

               if (ref != null && ref.resolvedType == null) {
                  char[] referredName = ref.getLastToken();
                  Iterator iter = this.environment().typesBeingConnected.iterator();

                  while(iter.hasNext()) {
                     SourceTypeBinding type = (SourceTypeBinding)iter.next();
                     if (CharOperation.equals(referredName, type.sourceName())) {
                        this.problemReporter().hierarchyCircularity(sourceType, superType, reference);
                        sourceType.tagBits |= 131072L;
                        superType.tagBits |= 131072L;
                        return true;
                     }
                  }
               }
            }

            if ((superType.tagBits & 256L) == 0L && superType.isValidBinding() && !superType.isUnresolvedType()) {
               ((SourceTypeBinding)superType).scope.connectTypeHierarchyWithoutMembers();
            }

            if ((superType.tagBits & 131072L) != 0L) {
               sourceType.tagBits |= 131072L;
            }

            return false;
         }
      }
   }

   private ReferenceBinding findSupertype(TypeReference typeReference) {
      CompilationUnitScope unitScope = this.compilationUnitScope();
      LookupEnvironment env = unitScope.environment;

      ReferenceBinding var7;
      try {
         env.missingClassFileLocation = typeReference;
         typeReference.aboutToResolve(this);
         unitScope.recordQualifiedReference(typeReference.getTypeName());
         this.superTypeReference = typeReference;
         ReferenceBinding superType = (ReferenceBinding)typeReference.resolveSuperType(this);
         var7 = superType;
      } catch (AbortCompilation var10) {
         SourceTypeBinding sourceType = this.referenceContext.binding;
         if (sourceType.superInterfaces == null) {
            sourceType.setSuperInterfaces(Binding.NO_SUPERINTERFACES);
         }

         var10.updateContext((ASTNode)typeReference, this.referenceCompilationUnit().compilationResult);
         throw var10;
      } finally {
         env.missingClassFileLocation = null;
         this.superTypeReference = null;
      }

      return var7;
   }

   public ProblemReporter problemReporter() {
      MethodScope outerMethodScope;
      if ((outerMethodScope = this.outerMostMethodScope()) == null) {
         ProblemReporter problemReporter = this.referenceCompilationUnit().problemReporter;
         problemReporter.referenceContext = this.referenceContext;
         return problemReporter;
      } else {
         return outerMethodScope.problemReporter();
      }
   }

   public TypeDeclaration referenceType() {
      return this.referenceContext;
   }

   public boolean hasDefaultNullnessFor(int location, int sourceStart) {
      int nonNullByDefaultValue = this.localNonNullByDefaultValue(sourceStart);
      if (nonNullByDefaultValue != 0) {
         return (nonNullByDefaultValue & location) != 0;
      } else {
         SourceTypeBinding binding = this.referenceContext.binding;
         if (binding != null) {
            int nullDefault = binding.getNullDefault();
            if (nullDefault != 0) {
               if ((nullDefault & location) != 0) {
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
         SourceTypeBinding binding = this.referenceContext.binding;
         if (binding != null) {
            int nullDefault = binding.getNullDefault();
            if (nullDefault != 0) {
               return nullDefault == nullBits ? binding : null;
            }
         }

         return this.parent.checkRedundantDefaultNullness(nullBits, sourceStart);
      }
   }

   public String toString() {
      return this.referenceContext != null ? "--- Class Scope ---\n\n" + this.referenceContext.binding.toString() : "--- Class Scope ---\n\n Binding not initialized";
   }
}

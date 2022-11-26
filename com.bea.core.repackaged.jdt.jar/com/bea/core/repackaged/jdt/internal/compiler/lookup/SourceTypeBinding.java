package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.IErrorHandlingPolicy;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Annotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Argument;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FieldDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.LambdaExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.MethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ReferenceExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SwitchStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeParameter;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemReporter;
import com.bea.core.repackaged.jdt.internal.compiler.util.SimpleLookupTable;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class SourceTypeBinding extends ReferenceBinding {
   public ReferenceBinding superclass;
   public ReferenceBinding[] superInterfaces;
   private FieldBinding[] fields;
   private MethodBinding[] methods;
   public ReferenceBinding[] memberTypes;
   public TypeVariableBinding[] typeVariables;
   public ClassScope scope;
   protected SourceTypeBinding prototype;
   LookupEnvironment environment;
   public ModuleBinding module;
   private static final int METHOD_EMUL = 0;
   private static final int FIELD_EMUL = 1;
   private static final int CLASS_LITERAL_EMUL = 2;
   private static final int MAX_SYNTHETICS = 3;
   HashMap[] synthetics;
   char[] genericReferenceTypeSignature;
   private SimpleLookupTable storedAnnotations = null;
   public int defaultNullness;
   private int nullnessDefaultInitialized = 0;
   private ReferenceBinding containerAnnotationType = null;
   public ExternalAnnotationProvider externalAnnotationProvider;
   private SourceTypeBinding nestHost;
   public HashSet nestMembers;

   public SourceTypeBinding(char[][] compoundName, PackageBinding fPackage, ClassScope scope) {
      this.compoundName = compoundName;
      this.fPackage = fPackage;
      this.fileName = scope.referenceCompilationUnit().getFileName();
      this.modifiers = scope.referenceContext.modifiers;
      this.sourceName = scope.referenceContext.name;
      this.scope = scope;
      this.environment = scope.environment();
      this.fields = Binding.UNINITIALIZED_FIELDS;
      this.methods = Binding.UNINITIALIZED_METHODS;
      this.prototype = this;
      this.computeId();
   }

   public SourceTypeBinding(SourceTypeBinding prototype) {
      super(prototype);
      this.prototype = prototype.prototype;
      SourceTypeBinding var10000 = this.prototype;
      var10000.tagBits |= 8388608L;
      this.tagBits &= -8388609L;
      this.superclass = prototype.superclass;
      this.superInterfaces = prototype.superInterfaces;
      this.fields = prototype.fields;
      this.methods = prototype.methods;
      this.memberTypes = prototype.memberTypes;
      this.typeVariables = prototype.typeVariables;
      this.environment = prototype.environment;
      this.synthetics = prototype.synthetics;
      this.genericReferenceTypeSignature = prototype.genericReferenceTypeSignature;
      this.storedAnnotations = prototype.storedAnnotations;
      this.defaultNullness = prototype.defaultNullness;
      this.nullnessDefaultInitialized = prototype.nullnessDefaultInitialized;
      this.containerAnnotationType = prototype.containerAnnotationType;
      this.tagBits |= 268435456L;
   }

   private void addDefaultAbstractMethods() {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else if ((this.tagBits & 1024L) == 0L) {
         this.tagBits |= 1024L;
         if (this.isClass() && this.isAbstract()) {
            if (this.scope.compilerOptions().targetJDK >= 3014656L) {
               return;
            }

            ReferenceBinding[] itsInterfaces = this.superInterfaces();
            if (itsInterfaces != Binding.NO_SUPERINTERFACES) {
               MethodBinding[] defaultAbstracts = null;
               int defaultAbstractsCount = 0;
               ReferenceBinding[] interfacesToVisit = itsInterfaces;
               int nextPosition = itsInterfaces.length;

               int length;
               label97:
               for(length = 0; length < nextPosition; ++length) {
                  ReferenceBinding superType = interfacesToVisit[length];
                  if (superType.isValidBinding()) {
                     MethodBinding[] superMethods = superType.methods();
                     int itsLength = superMethods.length;

                     while(true) {
                        MethodBinding method;
                        label92:
                        while(true) {
                           do {
                              --itsLength;
                              if (itsLength < 0) {
                                 if ((itsInterfaces = superType.superInterfaces()) == Binding.NO_SUPERINTERFACES) {
                                    continue label97;
                                 }

                                 itsLength = itsInterfaces.length;
                                 if (nextPosition + itsLength >= interfacesToVisit.length) {
                                    System.arraycopy(interfacesToVisit, 0, interfacesToVisit = new ReferenceBinding[nextPosition + itsLength + 5], 0, nextPosition);
                                 }

                                 int a = 0;

                                 while(true) {
                                    if (a >= itsLength) {
                                       continue label97;
                                    }

                                    ReferenceBinding next = itsInterfaces[a];
                                    int b = 0;

                                    while(true) {
                                       if (b >= nextPosition) {
                                          interfacesToVisit[nextPosition++] = next;
                                          break;
                                       }

                                       if (TypeBinding.equalsEquals(next, interfacesToVisit[b])) {
                                          break;
                                       }

                                       ++b;
                                    }

                                    ++a;
                                 }
                              }

                              method = superMethods[itsLength];
                           } while(this.implementsMethod(method));

                           if (defaultAbstractsCount == 0) {
                              defaultAbstracts = new MethodBinding[5];
                              break;
                           }

                           int k = 0;

                           while(true) {
                              if (k >= defaultAbstractsCount) {
                                 break label92;
                              }

                              MethodBinding alreadyAdded = defaultAbstracts[k];
                              if (CharOperation.equals(alreadyAdded.selector, method.selector) && alreadyAdded.areParametersEqual(method)) {
                                 break;
                              }

                              ++k;
                           }
                        }

                        MethodBinding defaultAbstract = new MethodBinding(method.modifiers | 524288 | 4096, method.selector, method.returnType, method.parameters, method.thrownExceptions, this);
                        if (defaultAbstractsCount == defaultAbstracts.length) {
                           System.arraycopy(defaultAbstracts, 0, defaultAbstracts = new MethodBinding[2 * defaultAbstractsCount], 0, defaultAbstractsCount);
                        }

                        defaultAbstracts[defaultAbstractsCount++] = defaultAbstract;
                     }
                  }
               }

               if (defaultAbstractsCount > 0) {
                  length = this.methods.length;
                  System.arraycopy(this.methods, 0, this.setMethods(new MethodBinding[length + defaultAbstractsCount]), 0, length);
                  System.arraycopy(defaultAbstracts, 0, this.methods, length, defaultAbstractsCount);
                  length += defaultAbstractsCount;
                  if (length > 1) {
                     ReferenceBinding.sortMethods(this.methods, 0, length);
                  }
               }
            }
         }

      }
   }

   public FieldBinding addSyntheticFieldForInnerclass(LocalVariableBinding actualOuterLocalVariable) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
         }

         if (this.synthetics[1] == null) {
            this.synthetics[1] = new HashMap(5);
         }

         FieldBinding synthField = (FieldBinding)this.synthetics[1].get(actualOuterLocalVariable);
         if (synthField == null) {
            synthField = new SyntheticFieldBinding(CharOperation.concat(TypeConstants.SYNTHETIC_OUTER_LOCAL_PREFIX, actualOuterLocalVariable.name), actualOuterLocalVariable.type, 4114, this, Constant.NotAConstant, this.synthetics[1].size());
            this.synthetics[1].put(actualOuterLocalVariable, synthField);
         }

         int index = 1;

         boolean needRecheck;
         do {
            needRecheck = false;
            FieldBinding existingField;
            if ((existingField = this.getField(((FieldBinding)synthField).name, true)) != null) {
               TypeDeclaration typeDecl = this.scope.referenceContext;
               FieldDeclaration[] fieldDeclarations = typeDecl.fields;
               int max = fieldDeclarations == null ? 0 : fieldDeclarations.length;

               for(int i = 0; i < max; ++i) {
                  FieldDeclaration fieldDecl = fieldDeclarations[i];
                  if (fieldDecl.binding == existingField) {
                     ((FieldBinding)synthField).name = CharOperation.concat(TypeConstants.SYNTHETIC_OUTER_LOCAL_PREFIX, actualOuterLocalVariable.name, ("$" + String.valueOf(index++)).toCharArray());
                     needRecheck = true;
                     break;
                  }
               }
            }
         } while(needRecheck);

         return (FieldBinding)synthField;
      }
   }

   public FieldBinding addSyntheticFieldForInnerclass(ReferenceBinding enclosingType) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
         }

         if (this.synthetics[1] == null) {
            this.synthetics[1] = new HashMap(5);
         }

         FieldBinding synthField = (FieldBinding)this.synthetics[1].get(enclosingType);
         if (synthField == null) {
            synthField = new SyntheticFieldBinding(CharOperation.concat(TypeConstants.SYNTHETIC_ENCLOSING_INSTANCE_PREFIX, String.valueOf(enclosingType.depth()).toCharArray()), enclosingType, 4112, this, Constant.NotAConstant, this.synthetics[1].size());
            this.synthetics[1].put(enclosingType, synthField);
         }

         boolean needRecheck;
         do {
            needRecheck = false;
            FieldBinding existingField;
            if ((existingField = this.getField(((FieldBinding)synthField).name, true)) != null) {
               TypeDeclaration typeDecl = this.scope.referenceContext;
               FieldDeclaration[] fieldDeclarations = typeDecl.fields;
               int max = fieldDeclarations == null ? 0 : fieldDeclarations.length;

               for(int i = 0; i < max; ++i) {
                  FieldDeclaration fieldDecl = fieldDeclarations[i];
                  if (fieldDecl.binding == existingField) {
                     if (this.scope.compilerOptions().complianceLevel >= 3211264L) {
                        ((FieldBinding)synthField).name = CharOperation.concat(((FieldBinding)synthField).name, "$".toCharArray());
                        needRecheck = true;
                     } else {
                        this.scope.problemReporter().duplicateFieldInType(this, fieldDecl);
                     }
                     break;
                  }
               }
            }
         } while(needRecheck);

         return (FieldBinding)synthField;
      }
   }

   public FieldBinding addSyntheticFieldForClassLiteral(TypeBinding targetType, BlockScope blockScope) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
         }

         if (this.synthetics[2] == null) {
            this.synthetics[2] = new HashMap(5);
         }

         FieldBinding synthField = (FieldBinding)this.synthetics[2].get(targetType);
         if (synthField == null) {
            synthField = new SyntheticFieldBinding(CharOperation.concat(TypeConstants.SYNTHETIC_CLASS, String.valueOf(this.synthetics[2].size()).toCharArray()), blockScope.getJavaLangClass(), 4104, this, Constant.NotAConstant, this.synthetics[2].size());
            this.synthetics[2].put(targetType, synthField);
         }

         FieldBinding existingField;
         if ((existingField = this.getField(((FieldBinding)synthField).name, true)) != null) {
            TypeDeclaration typeDecl = blockScope.referenceType();
            FieldDeclaration[] typeDeclarationFields = typeDecl.fields;
            int max = typeDeclarationFields == null ? 0 : typeDeclarationFields.length;

            for(int i = 0; i < max; ++i) {
               FieldDeclaration fieldDecl = typeDeclarationFields[i];
               if (fieldDecl.binding == existingField) {
                  blockScope.problemReporter().duplicateFieldInType(this, fieldDecl);
                  break;
               }
            }
         }

         return (FieldBinding)synthField;
      }
   }

   public FieldBinding addSyntheticFieldForAssert(BlockScope blockScope) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
         }

         if (this.synthetics[1] == null) {
            this.synthetics[1] = new HashMap(5);
         }

         FieldBinding synthField = (FieldBinding)this.synthetics[1].get("assertionEmulation");
         if (synthField == null) {
            synthField = new SyntheticFieldBinding(TypeConstants.SYNTHETIC_ASSERT_DISABLED, TypeBinding.BOOLEAN, (this.isInterface() ? 1 : 0) | 8 | 4096 | 16, this, Constant.NotAConstant, this.synthetics[1].size());
            this.synthetics[1].put("assertionEmulation", synthField);
         }

         int index = 0;

         boolean needRecheck;
         do {
            needRecheck = false;
            FieldBinding existingField;
            if ((existingField = this.getField(((FieldBinding)synthField).name, true)) != null) {
               TypeDeclaration typeDecl = this.scope.referenceContext;
               int max = typeDecl.fields == null ? 0 : typeDecl.fields.length;

               for(int i = 0; i < max; ++i) {
                  FieldDeclaration fieldDecl = typeDecl.fields[i];
                  if (fieldDecl.binding == existingField) {
                     ((FieldBinding)synthField).name = CharOperation.concat(TypeConstants.SYNTHETIC_ASSERT_DISABLED, ("_" + String.valueOf(index++)).toCharArray());
                     needRecheck = true;
                     break;
                  }
               }
            }
         } while(needRecheck);

         return (FieldBinding)synthField;
      }
   }

   public FieldBinding addSyntheticFieldForEnumValues() {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
         }

         if (this.synthetics[1] == null) {
            this.synthetics[1] = new HashMap(5);
         }

         FieldBinding synthField = (FieldBinding)this.synthetics[1].get("enumConstantValues");
         if (synthField == null) {
            synthField = new SyntheticFieldBinding(TypeConstants.SYNTHETIC_ENUM_VALUES, this.scope.createArrayType(this, 1), 4122, this, Constant.NotAConstant, this.synthetics[1].size());
            this.synthetics[1].put("enumConstantValues", synthField);
         }

         int index = 0;

         boolean needRecheck;
         do {
            needRecheck = false;
            FieldBinding existingField;
            if ((existingField = this.getField(((FieldBinding)synthField).name, true)) != null) {
               TypeDeclaration typeDecl = this.scope.referenceContext;
               FieldDeclaration[] fieldDeclarations = typeDecl.fields;
               int max = fieldDeclarations == null ? 0 : fieldDeclarations.length;

               for(int i = 0; i < max; ++i) {
                  FieldDeclaration fieldDecl = fieldDeclarations[i];
                  if (fieldDecl.binding == existingField) {
                     ((FieldBinding)synthField).name = CharOperation.concat(TypeConstants.SYNTHETIC_ENUM_VALUES, ("_" + String.valueOf(index++)).toCharArray());
                     needRecheck = true;
                     break;
                  }
               }
            }
         } while(needRecheck);

         return (FieldBinding)synthField;
      }
   }

   public SyntheticMethodBinding addSyntheticMethod(FieldBinding targetField, boolean isReadAccess, boolean isSuperAccess) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
         }

         if (this.synthetics[0] == null) {
            this.synthetics[0] = new HashMap(5);
         }

         SyntheticMethodBinding accessMethod = null;
         SyntheticMethodBinding[] accessors = (SyntheticMethodBinding[])this.synthetics[0].get(targetField);
         if (accessors == null) {
            accessMethod = new SyntheticMethodBinding(targetField, isReadAccess, isSuperAccess, this);
            this.synthetics[0].put(targetField, accessors = new SyntheticMethodBinding[2]);
            accessors[isReadAccess ? 0 : 1] = accessMethod;
         } else if ((accessMethod = accessors[isReadAccess ? 0 : 1]) == null) {
            accessMethod = new SyntheticMethodBinding(targetField, isReadAccess, isSuperAccess, this);
            accessors[isReadAccess ? 0 : 1] = accessMethod;
         }

         return accessMethod;
      }
   }

   public SyntheticMethodBinding addSyntheticEnumMethod(char[] selector) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
         }

         if (this.synthetics[0] == null) {
            this.synthetics[0] = new HashMap(5);
         }

         SyntheticMethodBinding accessMethod = null;
         SyntheticMethodBinding[] accessors = (SyntheticMethodBinding[])this.synthetics[0].get(selector);
         if (accessors == null) {
            accessMethod = new SyntheticMethodBinding(this, selector);
            this.synthetics[0].put(selector, accessors = new SyntheticMethodBinding[2]);
            accessors[0] = accessMethod;
         } else if ((accessMethod = accessors[0]) == null) {
            accessMethod = new SyntheticMethodBinding(this, selector);
            accessors[0] = accessMethod;
         }

         return accessMethod;
      }
   }

   public SyntheticFieldBinding addSyntheticFieldForSwitchEnum(char[] fieldName, String key) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
         }

         if (this.synthetics[1] == null) {
            this.synthetics[1] = new HashMap(5);
         }

         SyntheticFieldBinding synthField = (SyntheticFieldBinding)this.synthetics[1].get(key);
         if (synthField == null) {
            synthField = new SyntheticFieldBinding(fieldName, this.scope.createArrayType(TypeBinding.INT, 1), (this.isInterface() ? 17 : 2) | 8 | 4096, this, Constant.NotAConstant, this.synthetics[1].size());
            this.synthetics[1].put(key, synthField);
         }

         int index = 0;

         boolean needRecheck;
         do {
            needRecheck = false;
            FieldBinding existingField;
            if ((existingField = this.getField(synthField.name, true)) != null) {
               TypeDeclaration typeDecl = this.scope.referenceContext;
               FieldDeclaration[] fieldDeclarations = typeDecl.fields;
               int max = fieldDeclarations == null ? 0 : fieldDeclarations.length;

               for(int i = 0; i < max; ++i) {
                  FieldDeclaration fieldDecl = fieldDeclarations[i];
                  if (fieldDecl.binding == existingField) {
                     synthField.name = CharOperation.concat(fieldName, ("_" + String.valueOf(index++)).toCharArray());
                     needRecheck = true;
                     break;
                  }
               }
            }
         } while(needRecheck);

         return synthField;
      }
   }

   public SyntheticMethodBinding addSyntheticMethodForSwitchEnum(TypeBinding enumBinding, SwitchStatement switchStatement) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
         }

         if (this.synthetics[0] == null) {
            this.synthetics[0] = new HashMap(5);
         }

         SyntheticMethodBinding accessMethod = null;
         char[] selector = CharOperation.concat(TypeConstants.SYNTHETIC_SWITCH_ENUM_TABLE, enumBinding.constantPoolName());
         CharOperation.replace(selector, '/', '$');
         String key = new String(selector);
         SyntheticMethodBinding[] accessors = (SyntheticMethodBinding[])this.synthetics[0].get(key);
         SyntheticFieldBinding fieldBinding;
         if (accessors == null) {
            fieldBinding = this.addSyntheticFieldForSwitchEnum(selector, key);
            accessMethod = new SyntheticMethodBinding(fieldBinding, this, enumBinding, selector, switchStatement);
            this.synthetics[0].put(key, accessors = new SyntheticMethodBinding[2]);
            accessors[0] = accessMethod;
         } else if ((accessMethod = accessors[0]) == null) {
            fieldBinding = this.addSyntheticFieldForSwitchEnum(selector, key);
            accessMethod = new SyntheticMethodBinding(fieldBinding, this, enumBinding, selector, switchStatement);
            accessors[0] = accessMethod;
         }

         return accessMethod;
      }
   }

   public SyntheticMethodBinding addSyntheticMethodForEnumInitialization(int begin, int end) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
         }

         if (this.synthetics[0] == null) {
            this.synthetics[0] = new HashMap(5);
         }

         SyntheticMethodBinding accessMethod = new SyntheticMethodBinding(this, begin, end);
         SyntheticMethodBinding[] accessors = new SyntheticMethodBinding[2];
         this.synthetics[0].put(accessMethod.selector, accessors);
         accessors[0] = accessMethod;
         return accessMethod;
      }
   }

   public SyntheticMethodBinding addSyntheticMethod(LambdaExpression lambda) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
         }

         if (this.synthetics[0] == null) {
            this.synthetics[0] = new HashMap(5);
         }

         SyntheticMethodBinding lambdaMethod = null;
         SyntheticMethodBinding[] lambdaMethods = (SyntheticMethodBinding[])this.synthetics[0].get(lambda);
         if (lambdaMethods == null) {
            lambdaMethod = new SyntheticMethodBinding(lambda, CharOperation.concat(TypeConstants.ANONYMOUS_METHOD, Integer.toString(lambda.ordinal).toCharArray()), this);
            this.synthetics[0].put(lambda, lambdaMethods = new SyntheticMethodBinding[1]);
            lambdaMethods[0] = lambdaMethod;
         } else {
            lambdaMethod = lambdaMethods[0];
         }

         if (lambda.isSerializable) {
            this.addDeserializeLambdaMethod();
         }

         return lambdaMethod;
      }
   }

   public SyntheticMethodBinding addSyntheticMethod(ReferenceExpression ref) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else if (!ref.isSerializable) {
         return null;
      } else {
         if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
         }

         if (this.synthetics[0] == null) {
            this.synthetics[0] = new HashMap(5);
         }

         SyntheticMethodBinding lambdaMethod = null;
         SyntheticMethodBinding[] lambdaMethods = (SyntheticMethodBinding[])this.synthetics[0].get(ref);
         if (lambdaMethods == null) {
            lambdaMethod = new SyntheticMethodBinding(ref, this);
            this.synthetics[0].put(ref, lambdaMethods = new SyntheticMethodBinding[1]);
            lambdaMethods[0] = lambdaMethod;
         } else {
            lambdaMethod = lambdaMethods[0];
         }

         this.addDeserializeLambdaMethod();
         return lambdaMethod;
      }
   }

   private void addDeserializeLambdaMethod() {
      SyntheticMethodBinding[] deserializeLambdaMethods = (SyntheticMethodBinding[])this.synthetics[0].get(TypeConstants.DESERIALIZE_LAMBDA);
      if (deserializeLambdaMethods == null) {
         SyntheticMethodBinding deserializeLambdaMethod = new SyntheticMethodBinding(this);
         this.synthetics[0].put(TypeConstants.DESERIALIZE_LAMBDA, deserializeLambdaMethods = new SyntheticMethodBinding[1]);
         deserializeLambdaMethods[0] = deserializeLambdaMethod;
      }

   }

   public SyntheticMethodBinding addSyntheticMethod(MethodBinding targetMethod, boolean isSuperAccess) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
         }

         if (this.synthetics[0] == null) {
            this.synthetics[0] = new HashMap(5);
         }

         SyntheticMethodBinding accessMethod = null;
         SyntheticMethodBinding[] accessors = (SyntheticMethodBinding[])this.synthetics[0].get(targetMethod);
         if (accessors == null) {
            accessMethod = new SyntheticMethodBinding(targetMethod, isSuperAccess, this);
            this.synthetics[0].put(targetMethod, accessors = new SyntheticMethodBinding[2]);
            accessors[isSuperAccess ? 0 : 1] = accessMethod;
         } else if ((accessMethod = accessors[isSuperAccess ? 0 : 1]) == null) {
            accessMethod = new SyntheticMethodBinding(targetMethod, isSuperAccess, this);
            accessors[isSuperAccess ? 0 : 1] = accessMethod;
         }

         if (targetMethod.declaringClass.isStatic()) {
            if (targetMethod.isConstructor() && targetMethod.parameters.length >= 254 || targetMethod.parameters.length >= 255) {
               this.scope.problemReporter().tooManyParametersForSyntheticMethod(targetMethod.sourceMethod());
            }
         } else if (targetMethod.isConstructor() && targetMethod.parameters.length >= 253 || targetMethod.parameters.length >= 254) {
            this.scope.problemReporter().tooManyParametersForSyntheticMethod(targetMethod.sourceMethod());
         }

         return accessMethod;
      }
   }

   public SyntheticMethodBinding addSyntheticArrayMethod(ArrayBinding arrayType, int purpose, char[] selector) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
         }

         if (this.synthetics[0] == null) {
            this.synthetics[0] = new HashMap(5);
         }

         SyntheticMethodBinding arrayMethod = null;
         SyntheticMethodBinding[] arrayMethods = (SyntheticMethodBinding[])this.synthetics[0].get(arrayType);
         if (arrayMethods == null) {
            arrayMethod = new SyntheticMethodBinding(purpose, arrayType, selector, this);
            this.synthetics[0].put(arrayType, arrayMethods = new SyntheticMethodBinding[2]);
            arrayMethods[purpose == 14 ? 0 : 1] = arrayMethod;
         } else if ((arrayMethod = arrayMethods[purpose == 14 ? 0 : 1]) == null) {
            arrayMethod = new SyntheticMethodBinding(purpose, arrayType, selector, this);
            arrayMethods[purpose == 14 ? 0 : 1] = arrayMethod;
         }

         return arrayMethod;
      }
   }

   public SyntheticMethodBinding addSyntheticFactoryMethod(MethodBinding privateConstructor, MethodBinding publicConstructor, TypeBinding[] enclosingInstances, char[] selector) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
         }

         if (this.synthetics[0] == null) {
            this.synthetics[0] = new HashMap(5);
         }

         SyntheticMethodBinding factory = new SyntheticMethodBinding(privateConstructor, publicConstructor, selector, enclosingInstances, this);
         this.synthetics[0].put(selector, new SyntheticMethodBinding[]{factory});
         return factory;
      }
   }

   public SyntheticMethodBinding addSyntheticBridgeMethod(MethodBinding inheritedMethodToBridge, MethodBinding targetMethod) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else if (this.isInterface() && this.scope.compilerOptions().sourceLevel <= 3342336L) {
         return null;
      } else if (TypeBinding.equalsEquals(inheritedMethodToBridge.returnType.erasure(), targetMethod.returnType.erasure()) && inheritedMethodToBridge.areParameterErasuresEqual(targetMethod)) {
         return null;
      } else {
         if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
         }

         Iterator synthMethods;
         if (this.synthetics[0] == null) {
            this.synthetics[0] = new HashMap(5);
         } else {
            synthMethods = this.synthetics[0].keySet().iterator();

            while(synthMethods.hasNext()) {
               Object synthetic = synthMethods.next();
               if (synthetic instanceof MethodBinding) {
                  MethodBinding method = (MethodBinding)synthetic;
                  if (CharOperation.equals(inheritedMethodToBridge.selector, method.selector) && TypeBinding.equalsEquals(inheritedMethodToBridge.returnType.erasure(), method.returnType.erasure()) && inheritedMethodToBridge.areParameterErasuresEqual(method)) {
                     return null;
                  }
               }
            }
         }

         synthMethods = null;
         SyntheticMethodBinding[] accessors = (SyntheticMethodBinding[])this.synthetics[0].get(inheritedMethodToBridge);
         SyntheticMethodBinding accessMethod;
         if (accessors == null) {
            accessMethod = new SyntheticMethodBinding(inheritedMethodToBridge, targetMethod, this);
            this.synthetics[0].put(inheritedMethodToBridge, accessors = new SyntheticMethodBinding[2]);
            accessors[1] = accessMethod;
         } else if ((accessMethod = accessors[1]) == null) {
            accessMethod = new SyntheticMethodBinding(inheritedMethodToBridge, targetMethod, this);
            accessors[1] = accessMethod;
         }

         return accessMethod;
      }
   }

   public SyntheticMethodBinding addSyntheticBridgeMethod(MethodBinding inheritedMethodToBridge) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else if (this.scope.compilerOptions().complianceLevel <= 3211264L) {
         return null;
      } else if (this.isInterface() && !inheritedMethodToBridge.isDefaultMethod()) {
         return null;
      } else if (!inheritedMethodToBridge.isAbstract() && !inheritedMethodToBridge.isFinal() && !inheritedMethodToBridge.isStatic()) {
         if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
         }

         Iterator synthMethods;
         if (this.synthetics[0] == null) {
            this.synthetics[0] = new HashMap(5);
         } else {
            synthMethods = this.synthetics[0].keySet().iterator();

            while(synthMethods.hasNext()) {
               Object synthetic = synthMethods.next();
               if (synthetic instanceof MethodBinding) {
                  MethodBinding method = (MethodBinding)synthetic;
                  if (CharOperation.equals(inheritedMethodToBridge.selector, method.selector) && TypeBinding.equalsEquals(inheritedMethodToBridge.returnType.erasure(), method.returnType.erasure()) && inheritedMethodToBridge.areParameterErasuresEqual(method)) {
                     return null;
                  }
               }
            }
         }

         synthMethods = null;
         SyntheticMethodBinding[] accessors = (SyntheticMethodBinding[])this.synthetics[0].get(inheritedMethodToBridge);
         SyntheticMethodBinding accessMethod;
         if (accessors == null) {
            accessMethod = new SyntheticMethodBinding(inheritedMethodToBridge, this);
            this.synthetics[0].put(inheritedMethodToBridge, accessors = new SyntheticMethodBinding[2]);
            accessors[1] = accessMethod;
         } else if ((accessMethod = accessors[1]) == null) {
            accessMethod = new SyntheticMethodBinding(inheritedMethodToBridge, this);
            accessors[1] = accessMethod;
         }

         return accessMethod;
      } else {
         return null;
      }
   }

   boolean areFieldsInitialized() {
      if (!this.isPrototype()) {
         return this.prototype.areFieldsInitialized();
      } else {
         return this.fields != Binding.UNINITIALIZED_FIELDS;
      }
   }

   boolean areMethodsInitialized() {
      if (!this.isPrototype()) {
         return this.prototype.areMethodsInitialized();
      } else {
         return this.methods != Binding.UNINITIALIZED_METHODS;
      }
   }

   public int kind() {
      if (!this.isPrototype()) {
         return this.prototype.kind();
      } else {
         return this.typeVariables != Binding.NO_TYPE_VARIABLES ? 2052 : 4;
      }
   }

   public TypeBinding clone(TypeBinding immaterial) {
      return new SourceTypeBinding(this);
   }

   public char[] computeUniqueKey(boolean isLeaf) {
      if (!this.isPrototype()) {
         return this.prototype.computeUniqueKey();
      } else {
         char[] uniqueKey = super.computeUniqueKey(isLeaf);
         if (uniqueKey.length == 2) {
            return uniqueKey;
         } else if (Util.isClassFileName(this.fileName)) {
            return uniqueKey;
         } else {
            int end = CharOperation.lastIndexOf('.', this.fileName);
            if (end != -1) {
               int start = CharOperation.lastIndexOf('/', this.fileName) + 1;
               char[] mainTypeName = CharOperation.subarray(this.fileName, start, end);
               start = CharOperation.lastIndexOf('/', uniqueKey) + 1;
               if (start == 0) {
                  start = 1;
               }

               if (this.isMemberType()) {
                  end = CharOperation.indexOf('$', uniqueKey, start);
               } else {
                  end = -1;
               }

               if (end == -1) {
                  end = CharOperation.indexOf('<', uniqueKey, start);
               }

               if (end == -1) {
                  end = CharOperation.indexOf(';', uniqueKey, start);
               }

               char[] topLevelType = CharOperation.subarray(uniqueKey, start, end);
               if (!CharOperation.equals(topLevelType, mainTypeName)) {
                  StringBuffer buffer = new StringBuffer();
                  buffer.append(uniqueKey, 0, start);
                  buffer.append(mainTypeName);
                  buffer.append('~');
                  buffer.append(topLevelType);
                  buffer.append(uniqueKey, end, uniqueKey.length - end);
                  int length = buffer.length();
                  uniqueKey = new char[length];
                  buffer.getChars(0, length, uniqueKey, 0);
                  return uniqueKey;
               }
            }

            return uniqueKey;
         }
      }
   }

   private void checkAnnotationsInType() {
      this.getAnnotationTagBits();
      ReferenceBinding enclosingType = this.enclosingType();
      if (enclosingType != null && enclosingType.isViewedAsDeprecated() && !this.isDeprecated()) {
         this.modifiers |= 2097152;
         this.tagBits |= enclosingType.tagBits & 4611686018427387904L;
      }

      int i = 0;

      for(int length = this.memberTypes.length; i < length; ++i) {
         ((SourceTypeBinding)this.memberTypes[i]).checkAnnotationsInType();
      }

   }

   void faultInTypesForFieldsAndMethods() {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         this.checkAnnotationsInType();
         this.internalFaultInTypeForFieldsAndMethods();
      }
   }

   private void internalFaultInTypeForFieldsAndMethods() {
      this.fields();
      this.methods();
      int i = 0;

      for(int length = this.memberTypes.length; i < length; ++i) {
         ((SourceTypeBinding)this.memberTypes[i]).internalFaultInTypeForFieldsAndMethods();
      }

   }

   public FieldBinding[] fields() {
      if (!this.isPrototype()) {
         if ((this.tagBits & 8192L) != 0L) {
            return this.fields;
         } else {
            this.tagBits |= 8192L;
            return this.fields = this.prototype.fields();
         }
      } else if ((this.tagBits & 8192L) != 0L) {
         return this.fields;
      } else {
         int failed = 0;
         FieldBinding[] resolvedFields = this.fields;

         try {
            if ((this.tagBits & 4096L) == 0L) {
               int length = this.fields.length;
               if (length > 1) {
                  ReferenceBinding.sortFields(this.fields, 0, length);
               }

               this.tagBits |= 4096L;
            }

            FieldBinding[] fieldsSnapshot = this.fields;
            int i = 0;

            for(int length = fieldsSnapshot.length; i < length; ++i) {
               if (this.resolveTypeFor(fieldsSnapshot[i]) == null) {
                  if (resolvedFields == fieldsSnapshot) {
                     System.arraycopy(fieldsSnapshot, 0, resolvedFields = new FieldBinding[length], 0, length);
                  }

                  resolvedFields[i] = null;
                  ++failed;
               }
            }
         } finally {
            if (failed > 0) {
               int newSize = resolvedFields.length - failed;
               if (newSize == 0) {
                  return this.setFields(Binding.NO_FIELDS);
               }

               FieldBinding[] newFields = new FieldBinding[newSize];
               int i = 0;
               int j = 0;

               for(int length = resolvedFields.length; i < length; ++i) {
                  if (resolvedFields[i] != null) {
                     newFields[j++] = resolvedFields[i];
                  }
               }

               this.setFields(newFields);
            }

         }

         this.tagBits |= 8192L;
         return this.fields;
      }
   }

   public char[] genericTypeSignature() {
      if (!this.isPrototype()) {
         return this.prototype.genericTypeSignature();
      } else {
         if (this.genericReferenceTypeSignature == null) {
            this.genericReferenceTypeSignature = this.computeGenericTypeSignature(this.typeVariables);
         }

         return this.genericReferenceTypeSignature;
      }
   }

   public char[] genericSignature() {
      if (!this.isPrototype()) {
         return this.prototype.genericSignature();
      } else {
         StringBuffer sig = null;
         int i;
         int length;
         if (this.typeVariables != Binding.NO_TYPE_VARIABLES) {
            sig = new StringBuffer(10);
            sig.append('<');
            i = 0;

            for(length = this.typeVariables.length; i < length; ++i) {
               sig.append(this.typeVariables[i].genericSignature());
            }

            sig.append('>');
         } else {
            if (this.superclass == null || !this.superclass.isParameterizedType()) {
               i = 0;
               length = this.superInterfaces.length;

               while(true) {
                  if (i >= length) {
                     return null;
                  }

                  if (this.superInterfaces[i].isParameterizedType()) {
                     break;
                  }

                  ++i;
               }
            }

            sig = new StringBuffer(10);
         }

         if (this.superclass != null) {
            sig.append(this.superclass.genericTypeSignature());
         } else {
            sig.append(this.scope.getJavaLangObject().genericTypeSignature());
         }

         i = 0;

         for(length = this.superInterfaces.length; i < length; ++i) {
            sig.append(this.superInterfaces[i].genericTypeSignature());
         }

         return sig.toString().toCharArray();
      }
   }

   public long getAnnotationTagBits() {
      if (!this.isPrototype()) {
         return this.prototype.getAnnotationTagBits();
      } else {
         if ((this.tagBits & 8589934592L) == 0L && this.scope != null) {
            TypeDeclaration typeDecl = this.scope.referenceContext;
            boolean old = typeDecl.staticInitializerScope.insideTypeAnnotation;

            try {
               typeDecl.staticInitializerScope.insideTypeAnnotation = true;
               ASTNode.resolveAnnotations(typeDecl.staticInitializerScope, (Annotation[])typeDecl.annotations, (Binding)this);
            } finally {
               typeDecl.staticInitializerScope.insideTypeAnnotation = old;
            }

            if ((this.tagBits & 70368744177664L) != 0L) {
               this.modifiers |= 1048576;
            }
         }

         return this.tagBits;
      }
   }

   public MethodBinding[] getDefaultAbstractMethods() {
      if (!this.isPrototype()) {
         return this.prototype.getDefaultAbstractMethods();
      } else {
         int count = 0;
         int i = this.methods.length;

         while(true) {
            --i;
            if (i < 0) {
               if (count == 0) {
                  return Binding.NO_METHODS;
               }

               MethodBinding[] result = new MethodBinding[count];
               count = 0;
               int i = this.methods.length;

               while(true) {
                  --i;
                  if (i < 0) {
                     return result;
                  }

                  if (this.methods[i].isDefaultAbstract()) {
                     result[count++] = this.methods[i];
                  }
               }
            }

            if (this.methods[i].isDefaultAbstract()) {
               ++count;
            }
         }
      }
   }

   public MethodBinding getExactConstructor(TypeBinding[] argumentTypes) {
      if (!this.isPrototype()) {
         return this.prototype.getExactConstructor(argumentTypes);
      } else {
         int argCount = argumentTypes.length;
         int imethod;
         int end;
         MethodBinding method;
         TypeBinding[] toMatch;
         int iarg;
         long range;
         if ((this.tagBits & 32768L) != 0L) {
            if ((range = ReferenceBinding.binarySearch(TypeConstants.INIT, this.methods)) >= 0L) {
               imethod = (int)range;

               label56:
               for(end = (int)(range >> 32); imethod <= end; ++imethod) {
                  method = this.methods[imethod];
                  if (method.parameters.length == argCount) {
                     toMatch = method.parameters;

                     for(iarg = 0; iarg < argCount; ++iarg) {
                        if (TypeBinding.notEquals(toMatch[iarg], argumentTypes[iarg])) {
                           continue label56;
                        }
                     }

                     return method;
                  }
               }
            }
         } else {
            if ((this.tagBits & 16384L) == 0L) {
               int length = this.methods.length;
               if (length > 1) {
                  ReferenceBinding.sortMethods(this.methods, 0, length);
               }

               this.tagBits |= 16384L;
            }

            if ((range = ReferenceBinding.binarySearch(TypeConstants.INIT, this.methods)) >= 0L) {
               imethod = (int)range;

               label81:
               for(end = (int)(range >> 32); imethod <= end; ++imethod) {
                  method = this.methods[imethod];
                  if (this.resolveTypesFor(method) == null || method.returnType == null) {
                     this.methods();
                     return this.getExactConstructor(argumentTypes);
                  }

                  if (method.parameters.length == argCount) {
                     toMatch = method.parameters;

                     for(iarg = 0; iarg < argCount; ++iarg) {
                        if (TypeBinding.notEquals(toMatch[iarg], argumentTypes[iarg])) {
                           continue label81;
                        }
                     }

                     return method;
                  }
               }
            }
         }

         return null;
      }
   }

   public MethodBinding getExactMethod(char[] selector, TypeBinding[] argumentTypes, CompilationUnitScope refScope) {
      if (!this.isPrototype()) {
         return this.prototype.getExactMethod(selector, argumentTypes, refScope);
      } else {
         int argCount = argumentTypes.length;
         boolean foundNothing = true;
         int start;
         int end;
         long range;
         if ((this.tagBits & 32768L) != 0L) {
            if ((range = ReferenceBinding.binarySearch(selector, this.methods)) >= 0L) {
               start = (int)range;

               label124:
               for(end = (int)(range >> 32); start <= end; ++start) {
                  MethodBinding method = this.methods[start];
                  foundNothing = false;
                  if (method.parameters.length == argCount) {
                     TypeBinding[] toMatch = method.parameters;

                     for(int iarg = 0; iarg < argCount; ++iarg) {
                        if (TypeBinding.notEquals(toMatch[iarg], argumentTypes[iarg])) {
                           continue label124;
                        }
                     }

                     return method;
                  }
               }
            }
         } else {
            if ((this.tagBits & 16384L) == 0L) {
               int length = this.methods.length;
               if (length > 1) {
                  ReferenceBinding.sortMethods(this.methods, 0, length);
               }

               this.tagBits |= 16384L;
            }

            if ((range = ReferenceBinding.binarySearch(selector, this.methods)) >= 0L) {
               start = (int)range;
               end = (int)(range >> 32);
               int imethod = start;

               label142:
               while(true) {
                  if (imethod > end) {
                     boolean isSource15 = this.scope.compilerOptions().sourceLevel >= 3211264L;

                     MethodBinding method;
                     int imethod;
                     for(imethod = start; imethod <= end; ++imethod) {
                        method = this.methods[imethod];

                        for(int j = end; j > imethod; --j) {
                           MethodBinding method2 = this.methods[j];
                           boolean paramsMatch = isSource15 ? method.areParameterErasuresEqual(method2) : method.areParametersEqual(method2);
                           if (paramsMatch) {
                              this.methods();
                              return this.getExactMethod(selector, argumentTypes, refScope);
                           }
                        }
                     }

                     imethod = start;

                     while(true) {
                        if (imethod > end) {
                           break label142;
                        }

                        method = this.methods[imethod];
                        TypeBinding[] toMatch = method.parameters;
                        if (toMatch.length == argCount) {
                           int iarg = 0;

                           while(true) {
                              if (iarg >= argCount) {
                                 return method;
                              }

                              if (TypeBinding.notEquals(toMatch[iarg], argumentTypes[iarg])) {
                                 break;
                              }

                              ++iarg;
                           }
                        }

                        ++imethod;
                     }
                  }

                  MethodBinding method = this.methods[imethod];
                  if (this.resolveTypesFor(method) == null || method.returnType == null) {
                     this.methods();
                     return this.getExactMethod(selector, argumentTypes, refScope);
                  }

                  ++imethod;
               }
            }
         }

         if (foundNothing) {
            if (this.isInterface()) {
               if (this.superInterfaces.length == 1) {
                  if (refScope != null) {
                     refScope.recordTypeReference(this.superInterfaces[0]);
                  }

                  return this.superInterfaces[0].getExactMethod(selector, argumentTypes, refScope);
               }
            } else if (this.superclass != null) {
               if (refScope != null) {
                  refScope.recordTypeReference(this.superclass);
               }

               return this.superclass.getExactMethod(selector, argumentTypes, refScope);
            }
         }

         return null;
      }
   }

   public FieldBinding getField(char[] fieldName, boolean needResolve) {
      if (!this.isPrototype()) {
         return this.prototype.getField(fieldName, needResolve);
      } else if ((this.tagBits & 8192L) != 0L) {
         return ReferenceBinding.binarySearch(fieldName, this.fields);
      } else {
         if ((this.tagBits & 4096L) == 0L) {
            int length = this.fields.length;
            if (length > 1) {
               ReferenceBinding.sortFields(this.fields, 0, length);
            }

            this.tagBits |= 4096L;
         }

         FieldBinding field = ReferenceBinding.binarySearch(fieldName, this.fields);
         if (field != null) {
            FieldBinding result = null;

            FieldBinding var6;
            try {
               result = this.resolveTypeFor(field);
               var6 = result;
            } finally {
               if (result == null) {
                  int newSize = this.fields.length - 1;
                  if (newSize == 0) {
                     this.setFields(Binding.NO_FIELDS);
                  } else {
                     FieldBinding[] newFields = new FieldBinding[newSize];
                     int index = 0;
                     int i = 0;

                     for(int length = this.fields.length; i < length; ++i) {
                        FieldBinding f = this.fields[i];
                        if (f != field) {
                           newFields[index++] = f;
                        }
                     }

                     this.setFields(newFields);
                  }
               }

            }

            return var6;
         } else {
            return null;
         }
      }
   }

   public MethodBinding[] getMethods(char[] selector) {
      if (!this.isPrototype()) {
         return this.prototype.getMethods(selector);
      } else {
         int start;
         int i;
         if ((this.tagBits & 32768L) != 0L) {
            long range;
            if ((range = ReferenceBinding.binarySearch(selector, this.methods)) >= 0L) {
               int start = (int)range;
               start = (int)(range >> 32);
               i = start - start + 1;
               MethodBinding[] result;
               System.arraycopy(this.methods, start, result = new MethodBinding[i], 0, i);
               return result;
            } else {
               return Binding.NO_METHODS;
            }
         } else {
            if ((this.tagBits & 16384L) == 0L) {
               int length = this.methods.length;
               if (length > 1) {
                  ReferenceBinding.sortMethods(this.methods, 0, length);
               }

               this.tagBits |= 16384L;
            }

            long range;
            if ((range = ReferenceBinding.binarySearch(selector, this.methods)) < 0L) {
               return Binding.NO_METHODS;
            } else {
               start = (int)range;
               i = (int)(range >> 32);

               int length;
               MethodBinding method;
               for(length = start; length <= i; ++length) {
                  method = this.methods[length];
                  if (this.resolveTypesFor(method) == null || method.returnType == null) {
                     this.methods();
                     return this.getMethods(selector);
                  }
               }

               length = i - start + 1;
               MethodBinding[] result;
               System.arraycopy(this.methods, start, result = new MethodBinding[length], 0, length);
               boolean isSource15 = this.scope.compilerOptions().sourceLevel >= 3211264L;
               i = 0;

               for(length = result.length - 1; i < length; ++i) {
                  method = result[i];

                  for(int j = length; j > i; --j) {
                     boolean paramsMatch = isSource15 ? method.areParameterErasuresEqual(result[j]) : method.areParametersEqual(result[j]);
                     if (paramsMatch) {
                        this.methods();
                        return this.getMethods(selector);
                     }
                  }
               }

               return result;
            }
         }
      }
   }

   public void generateSyntheticFinalFieldInitialization(CodeStream codeStream) {
      if (this.synthetics != null && this.synthetics[1] != null) {
         Collection syntheticFields = this.synthetics[1].values();
         Iterator var4 = syntheticFields.iterator();

         while(var4.hasNext()) {
            FieldBinding field = (FieldBinding)var4.next();
            if (CharOperation.prefixEquals(TypeConstants.SYNTHETIC_SWITCH_ENUM_TABLE, field.name) && field.isFinal()) {
               MethodBinding[] accessors = (MethodBinding[])this.synthetics[0].get(new String(field.name));
               if (accessors != null && accessors[0] != null) {
                  codeStream.invoke((byte)-72, accessors[0], (TypeBinding)null);
                  codeStream.fieldAccess((byte)-77, field, (TypeBinding)null);
               }
            }
         }

      }
   }

   public FieldBinding getSyntheticField(LocalVariableBinding actualOuterLocalVariable) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         return this.synthetics != null && this.synthetics[1] != null ? (FieldBinding)this.synthetics[1].get(actualOuterLocalVariable) : null;
      }
   }

   public FieldBinding getSyntheticField(ReferenceBinding targetEnclosingType, boolean onlyExactMatch) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else if (this.synthetics != null && this.synthetics[1] != null) {
         FieldBinding field = (FieldBinding)this.synthetics[1].get(targetEnclosingType);
         if (field != null) {
            return field;
         } else {
            if (!onlyExactMatch) {
               Iterator accessFields = this.synthetics[1].values().iterator();

               while(accessFields.hasNext()) {
                  field = (FieldBinding)accessFields.next();
                  if (CharOperation.prefixEquals(TypeConstants.SYNTHETIC_ENCLOSING_INSTANCE_PREFIX, field.name) && field.type.findSuperTypeOriginatingFrom(targetEnclosingType) != null) {
                     return field;
                  }
               }
            }

            return null;
         }
      } else {
         return null;
      }
   }

   public SyntheticMethodBinding getSyntheticBridgeMethod(MethodBinding inheritedMethodToBridge) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else if (this.synthetics == null) {
         return null;
      } else if (this.synthetics[0] == null) {
         return null;
      } else {
         SyntheticMethodBinding[] accessors = (SyntheticMethodBinding[])this.synthetics[0].get(inheritedMethodToBridge);
         return accessors == null ? null : accessors[1];
      }
   }

   public boolean hasTypeBit(int bit) {
      if (!this.isPrototype()) {
         return this.prototype.hasTypeBit(bit);
      } else {
         return (this.typeBits & bit) != 0;
      }
   }

   public void initializeDeprecatedAnnotationTagBits() {
      if (!this.isPrototype()) {
         this.prototype.initializeDeprecatedAnnotationTagBits();
      } else {
         if ((this.tagBits & 17179869184L) == 0L) {
            TypeDeclaration typeDecl = this.scope.referenceContext;
            boolean old = typeDecl.staticInitializerScope.insideTypeAnnotation;

            try {
               typeDecl.staticInitializerScope.insideTypeAnnotation = true;
               ASTNode.resolveDeprecatedAnnotations(typeDecl.staticInitializerScope, typeDecl.annotations, this);
               this.tagBits |= 17179869184L;
            } finally {
               typeDecl.staticInitializerScope.insideTypeAnnotation = old;
            }

            if ((this.tagBits & 70368744177664L) != 0L) {
               this.modifiers |= 1048576;
            }
         }

      }
   }

   void initializeForStaticImports() {
      if (!this.isPrototype()) {
         this.prototype.initializeForStaticImports();
      } else if (this.scope != null) {
         if (this.superInterfaces == null) {
            this.scope.connectTypeHierarchy();
         }

         this.scope.buildFields();
         this.scope.buildMethods();
      }
   }

   int getNullDefault() {
      if (!this.isPrototype()) {
         return this.prototype.getNullDefault();
      } else {
         switch (this.nullnessDefaultInitialized) {
            case 0:
               this.getAnnotationTagBits();
            case 1:
               this.getPackage().isViewedAsDeprecated();
               this.nullnessDefaultInitialized = 2;
            default:
               return this.defaultNullness;
         }
      }
   }

   public boolean isEquivalentTo(TypeBinding otherType) {
      if (!this.isPrototype()) {
         return this.prototype.isEquivalentTo(otherType);
      } else if (TypeBinding.equalsEquals(this, otherType)) {
         return true;
      } else if (otherType == null) {
         return false;
      } else {
         switch (otherType.kind()) {
            case 260:
               if ((otherType.tagBits & 1073741824L) == 0L && (!this.isMemberType() || !otherType.isMemberType())) {
                  return false;
               } else {
                  ParameterizedTypeBinding otherParamType = (ParameterizedTypeBinding)otherType;
                  if (TypeBinding.notEquals(this, otherParamType.genericType())) {
                     return false;
                  } else {
                     if (!this.isStatic()) {
                        ReferenceBinding enclosing = this.enclosingType();
                        if (enclosing != null) {
                           ReferenceBinding otherEnclosing = otherParamType.enclosingType();
                           if (otherEnclosing == null) {
                              return false;
                           }

                           if ((otherEnclosing.tagBits & 1073741824L) == 0L) {
                              if (TypeBinding.notEquals(enclosing, otherEnclosing)) {
                                 return false;
                              }
                           } else if (!enclosing.isEquivalentTo(otherParamType.enclosingType())) {
                              return false;
                           }
                        }
                     }

                     int length = this.typeVariables == null ? 0 : this.typeVariables.length;
                     TypeBinding[] otherArguments = otherParamType.arguments;
                     int otherLength = otherArguments == null ? 0 : otherArguments.length;
                     if (otherLength != length) {
                        return false;
                     } else {
                        for(int i = 0; i < length; ++i) {
                           if (!this.typeVariables[i].isTypeArgumentContainedBy(otherArguments[i])) {
                              return false;
                           }
                        }

                        return true;
                     }
                  }
               }
            case 516:
            case 8196:
               return ((WildcardBinding)otherType).boundCheck(this);
            case 1028:
               return TypeBinding.equalsEquals(otherType.erasure(), this);
            default:
               return false;
         }
      }
   }

   public boolean isGenericType() {
      if (!this.isPrototype()) {
         return this.prototype.isGenericType();
      } else {
         return this.typeVariables != Binding.NO_TYPE_VARIABLES;
      }
   }

   public boolean isHierarchyConnected() {
      if (!this.isPrototype()) {
         return this.prototype.isHierarchyConnected();
      } else {
         return (this.tagBits & 512L) != 0L;
      }
   }

   public boolean isRepeatableAnnotationType() {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         return this.containerAnnotationType != null;
      }
   }

   public boolean isTaggedRepeatable() {
      return (this.tagBits & 1152921504606846976L) != 0L;
   }

   public boolean canBeSeenBy(Scope sco) {
      SourceTypeBinding invocationType = sco.enclosingSourceType();
      if (TypeBinding.equalsEquals(invocationType, this)) {
         return true;
      } else {
         return this.environment.canTypeBeAccessed(this, sco) && super.canBeSeenBy(sco);
      }
   }

   public ReferenceBinding[] memberTypes() {
      if (!this.isPrototype()) {
         if ((this.tagBits & 268435456L) == 0L) {
            return this.memberTypes;
         }

         ReferenceBinding[] members = this.memberTypes = this.prototype.memberTypes();
         int membersLength = members == null ? 0 : members.length;
         this.memberTypes = new ReferenceBinding[membersLength];

         for(int i = 0; i < membersLength; ++i) {
            this.memberTypes[i] = this.environment.createMemberType(members[i], this);
         }

         this.tagBits &= -268435457L;
      }

      return this.memberTypes;
   }

   public boolean hasMemberTypes() {
      if (!this.isPrototype()) {
         return this.prototype.hasMemberTypes();
      } else {
         return this.memberTypes.length > 0;
      }
   }

   public MethodBinding[] methods() {
      if (!this.isPrototype()) {
         if ((this.tagBits & 32768L) != 0L) {
            return this.methods;
         } else {
            this.tagBits |= 32768L;
            return this.methods = this.prototype.methods();
         }
      } else if ((this.tagBits & 32768L) != 0L) {
         return this.methods;
      } else {
         if (!this.areMethodsInitialized()) {
            this.scope.buildMethods();
         }

         int failed;
         if ((this.tagBits & 16384L) == 0L) {
            failed = this.methods.length;
            if (failed > 1) {
               ReferenceBinding.sortMethods(this.methods, 0, failed);
            }

            this.tagBits |= 16384L;
         }

         failed = 0;
         MethodBinding[] resolvedMethods = this.methods;

         try {
            int i = 0;

            for(int length = this.methods.length; i < length; ++i) {
               if ((this.tagBits & 32768L) != 0L) {
                  MethodBinding[] var24 = this.methods;
                  return var24;
               }

               if (this.resolveTypesFor(this.methods[i]) == null) {
                  if (resolvedMethods == this.methods) {
                     System.arraycopy(this.methods, 0, resolvedMethods = new MethodBinding[length], 0, length);
                  }

                  resolvedMethods[i] = null;
                  ++failed;
               }
            }

            boolean complyTo15OrAbove = this.scope.compilerOptions().sourceLevel >= 3211264L;
            boolean compliance16 = this.scope.compilerOptions().complianceLevel == 3276800L;
            int i = 0;

            for(int length = this.methods.length; i < length; ++i) {
               int severity = 1;
               MethodBinding method = resolvedMethods[i];
               if (method != null) {
                  char[] selector = method.selector;
                  AbstractMethodDeclaration methodDecl = null;

                  for(int j = i + 1; j < length; ++j) {
                     MethodBinding method2 = resolvedMethods[j];
                     if (method2 != null) {
                        if (!CharOperation.equals(selector, method2.selector)) {
                           break;
                        }

                        if (complyTo15OrAbove) {
                           if (!method.areParameterErasuresEqual(method2)) {
                              continue;
                           }

                           if (compliance16 && method.returnType != null && method2.returnType != null && TypeBinding.notEquals(method.returnType.erasure(), method2.returnType.erasure())) {
                              TypeBinding[] params1 = method.parameters;
                              TypeBinding[] params2 = method2.parameters;
                              int pLength = params1.length;
                              TypeVariableBinding[] vars = method.typeVariables;
                              TypeVariableBinding[] vars2 = method2.typeVariables;
                              boolean equalTypeVars = vars == vars2;
                              MethodBinding subMethod = method2;
                              if (!equalTypeVars) {
                                 MethodBinding temp = method.computeSubstitutedMethod(method2, this.scope.environment());
                                 if (temp != null) {
                                    equalTypeVars = true;
                                    subMethod = temp;
                                 }
                              }

                              boolean equalParams = method.areParametersEqual(subMethod);
                              if (!equalParams || !equalTypeVars) {
                                 if (vars != Binding.NO_TYPE_VARIABLES && vars2 != Binding.NO_TYPE_VARIABLES) {
                                    severity = 0;
                                 } else if (pLength > 0) {
                                    int index = pLength;

                                    while(true) {
                                       --index;
                                       if (index < 0 || TypeBinding.notEquals(params1[index], params2[index].erasure()) && (!(params1[index] instanceof RawTypeBinding) || TypeBinding.notEquals(params2[index].erasure(), ((RawTypeBinding)params1[index]).actualType()))) {
                                          break;
                                       }

                                       if (TypeBinding.equalsEquals(params1[index], params2[index])) {
                                          TypeBinding type = params1[index].leafComponentType();
                                          if (type instanceof SourceTypeBinding && type.typeVariables() != Binding.NO_TYPE_VARIABLES) {
                                             index = pLength;
                                             break;
                                          }
                                       }
                                    }

                                    if (index >= 0 && index < pLength) {
                                       index = pLength;

                                       do {
                                          --index;
                                       } while(index >= 0 && (!TypeBinding.notEquals(params1[index].erasure(), params2[index]) || params2[index] instanceof RawTypeBinding && !TypeBinding.notEquals(params1[index].erasure(), ((RawTypeBinding)params2[index]).actualType())));
                                    }

                                    if (index >= 0) {
                                       severity = 0;
                                    }
                                 } else if (pLength != 0) {
                                    severity = 0;
                                 }
                              }
                           }
                        } else if (!method.areParametersEqual(method2)) {
                           continue;
                        }

                        boolean isEnumSpecialMethod = this.isEnum() && (CharOperation.equals(selector, TypeConstants.VALUEOF) || CharOperation.equals(selector, TypeConstants.VALUES));
                        boolean removeMethod2 = severity == 1;
                        if (methodDecl == null) {
                           methodDecl = method.sourceMethod();
                           if (methodDecl != null && methodDecl.binding != null) {
                              boolean removeMethod = method.returnType == null && method2.returnType != null;
                              if (isEnumSpecialMethod) {
                                 this.scope.problemReporter().duplicateEnumSpecialMethod(this, methodDecl);
                                 removeMethod = true;
                              } else {
                                 this.scope.problemReporter().duplicateMethodInType(methodDecl, method.areParametersEqual(method2), severity);
                              }

                              if (removeMethod) {
                                 removeMethod2 = false;
                                 methodDecl.binding = null;
                                 if (resolvedMethods == this.methods) {
                                    System.arraycopy(this.methods, 0, resolvedMethods = new MethodBinding[length], 0, length);
                                 }

                                 resolvedMethods[i] = null;
                                 ++failed;
                              }
                           }
                        }

                        AbstractMethodDeclaration method2Decl = method2.sourceMethod();
                        if (method2Decl != null && method2Decl.binding != null) {
                           if (isEnumSpecialMethod) {
                              this.scope.problemReporter().duplicateEnumSpecialMethod(this, method2Decl);
                              removeMethod2 = true;
                           } else {
                              this.scope.problemReporter().duplicateMethodInType(method2Decl, method.areParametersEqual(method2), severity);
                           }

                           if (removeMethod2) {
                              method2Decl.binding = null;
                              if (resolvedMethods == this.methods) {
                                 System.arraycopy(this.methods, 0, resolvedMethods = new MethodBinding[length], 0, length);
                              }

                              resolvedMethods[j] = null;
                              ++failed;
                           }
                        }
                     }
                  }

                  if (method.returnType == null && resolvedMethods[i] != null) {
                     methodDecl = method.sourceMethod();
                     if (methodDecl != null) {
                        methodDecl.binding = null;
                     }

                     if (resolvedMethods == this.methods) {
                        System.arraycopy(this.methods, 0, resolvedMethods = new MethodBinding[length], 0, length);
                     }

                     resolvedMethods[i] = null;
                     ++failed;
                  }
               }
            }
         } finally {
            if ((this.tagBits & 32768L) != 0L) {
               return this.methods;
            }

            if (failed > 0) {
               int newSize = resolvedMethods.length - failed;
               if (newSize == 0) {
                  this.setMethods(Binding.NO_METHODS);
               } else {
                  MethodBinding[] newMethods = new MethodBinding[newSize];
                  int i = 0;
                  int j = 0;

                  for(int length = resolvedMethods.length; i < length; ++i) {
                     if (resolvedMethods[i] != null) {
                        newMethods[j++] = resolvedMethods[i];
                     }
                  }

                  this.setMethods(newMethods);
               }
            }

            this.addDefaultAbstractMethods();
            this.tagBits |= 32768L;
         }

         return this.methods;
      }
   }

   public TypeBinding prototype() {
      return this.prototype;
   }

   public boolean isPrototype() {
      return this == this.prototype;
   }

   public ReferenceBinding containerAnnotationType() {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.containerAnnotationType instanceof UnresolvedReferenceBinding) {
            this.containerAnnotationType = (ReferenceBinding)BinaryTypeBinding.resolveType(this.containerAnnotationType, this.scope.environment(), false);
         }

         return this.containerAnnotationType;
      }
   }

   public FieldBinding resolveTypeFor(FieldBinding field) {
      if (!this.isPrototype()) {
         return this.prototype.resolveTypeFor(field);
      } else if ((field.modifiers & 33554432) == 0) {
         return field;
      } else {
         long sourceLevel = this.scope.compilerOptions().sourceLevel;
         if (sourceLevel >= 3211264L && (field.getAnnotationTagBits() & 70368744177664L) != 0L) {
            field.modifiers |= 1048576;
         }

         if (this.isViewedAsDeprecated() && !field.isDeprecated()) {
            field.modifiers |= 2097152;
            field.tagBits |= this.tagBits & 4611686018427387904L;
         }

         if (this.hasRestrictedAccess()) {
            field.modifiers |= 262144;
         }

         FieldDeclaration[] fieldDecls = this.scope.referenceContext.fields;
         int length = fieldDecls == null ? 0 : fieldDecls.length;

         for(int f = 0; f < length; ++f) {
            if (fieldDecls[f].binding == field) {
               MethodScope initializationScope = field.isStatic() ? this.scope.referenceContext.staticInitializerScope : this.scope.referenceContext.initializerScope;
               FieldBinding previousField = initializationScope.initializedField;

               label268: {
                  try {
                     initializationScope.initializedField = field;
                     FieldDeclaration fieldDecl = fieldDecls[f];
                     TypeBinding fieldType = fieldDecl.getKind() == 3 ? initializationScope.environment().convertToRawType(this, false) : fieldDecl.type.resolveType(initializationScope, true);
                     field.type = fieldType;
                     field.modifiers &= -33554433;
                     if (fieldType == null) {
                        fieldDecl.binding = null;
                        return null;
                     }

                     if (fieldType != TypeBinding.VOID) {
                        if (fieldType.isArrayType() && ((ArrayBinding)fieldType).leafComponentType == TypeBinding.VOID) {
                           this.scope.problemReporter().variableTypeCannotBeVoidArray(fieldDecl);
                           fieldDecl.binding = null;
                           return null;
                        }

                        if ((fieldType.tagBits & 128L) != 0L) {
                           field.tagBits |= 128L;
                        }

                        TypeBinding leafType = fieldType.leafComponentType();
                        if (leafType instanceof ReferenceBinding && (((ReferenceBinding)leafType).modifiers & 1073741824) != 0) {
                           field.modifiers |= 1073741824;
                        }

                        if (sourceLevel >= 3407872L) {
                           Annotation[] annotations = fieldDecl.annotations;
                           if (annotations != null && annotations.length != 0) {
                              ASTNode.copySE8AnnotationsToType(initializationScope, field, annotations, fieldDecl.getKind() == 3);
                           }

                           Annotation.isTypeUseCompatible(fieldDecl.type, this.scope, annotations);
                        }

                        if (this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
                           if (fieldDecl.getKind() == 3) {
                              field.tagBits |= 72057594037927936L;
                           } else {
                              if (this.hasNonNullDefaultFor(32, fieldDecl.sourceStart)) {
                                 field.fillInDefaultNonNullness(fieldDecl, initializationScope);
                              }

                              if (!this.scope.validateNullAnnotation(field.tagBits, fieldDecl.type, fieldDecl.annotations)) {
                                 field.tagBits &= -108086391056891905L;
                              }
                           }
                        }

                        if (initializationScope.shouldCheckAPILeaks(this, field.isPublic()) && fieldDecl.type != null) {
                           initializationScope.detectAPILeaks(fieldDecl.type, fieldType);
                        }
                        break label268;
                     }

                     this.scope.problemReporter().variableTypeCannotBeVoid(fieldDecl);
                     fieldDecl.binding = null;
                  } finally {
                     initializationScope.initializedField = previousField;
                  }

                  return null;
               }

               if (this.externalAnnotationProvider != null) {
                  ExternalAnnotationSuperimposer.annotateFieldBinding(field, this.externalAnnotationProvider, this.environment);
               }

               return field;
            }
         }

         return null;
      }
   }

   public MethodBinding resolveTypesFor(MethodBinding method) {
      ProblemReporter problemReporter = this.scope.problemReporter();
      IErrorHandlingPolicy suspendedPolicy = problemReporter.suspendTempErrorHandlingPolicy();

      MethodBinding var5;
      try {
         var5 = this.resolveTypesWithSuspendedTempErrorHandlingPolicy(method);
      } finally {
         problemReporter.resumeTempErrorHandlingPolicy(suspendedPolicy);
      }

      return var5;
   }

   private MethodBinding resolveTypesWithSuspendedTempErrorHandlingPolicy(MethodBinding method) {
      if (!this.isPrototype()) {
         return this.prototype.resolveTypesFor(method);
      } else if ((method.modifiers & 33554432) == 0) {
         return method;
      } else {
         long sourceLevel = this.scope.compilerOptions().sourceLevel;
         int i;
         if (sourceLevel >= 3211264L) {
            ReferenceBinding object = this.scope.getJavaLangObject();
            TypeVariableBinding[] tvb = method.typeVariables;

            for(i = 0; i < tvb.length; ++i) {
               tvb[i].superclass = object;
            }

            if ((method.getAnnotationTagBits() & 70368744177664L) != 0L) {
               method.modifiers |= 1048576;
            }
         }

         if (this.isViewedAsDeprecated() && !method.isDeprecated()) {
            method.modifiers |= 2097152;
            method.tagBits |= this.tagBits & 4611686018427387904L;
         }

         if (this.hasRestrictedAccess()) {
            method.modifiers |= 262144;
         }

         AbstractMethodDeclaration methodDecl = method.sourceMethod();
         if (methodDecl == null) {
            return null;
         } else {
            TypeParameter[] typeParameters = methodDecl.typeParameters();
            int size;
            if (typeParameters != null) {
               methodDecl.scope.connectTypeVariables(typeParameters, true);
               i = 0;

               for(size = typeParameters.length; i < size; ++i) {
                  typeParameters[i].checkBounds(methodDecl.scope);
               }
            }

            TypeReference[] exceptionTypes = methodDecl.thrownExceptions;
            if (exceptionTypes != null) {
               size = exceptionTypes.length;
               method.thrownExceptions = new ReferenceBinding[size];
               int count = 0;

               for(int i = 0; i < size; ++i) {
                  ReferenceBinding resolvedExceptionType = (ReferenceBinding)exceptionTypes[i].resolveType(methodDecl.scope, true);
                  if (resolvedExceptionType != null) {
                     if (resolvedExceptionType.isBoundParameterizedType()) {
                        methodDecl.scope.problemReporter().invalidParameterizedExceptionType(resolvedExceptionType, exceptionTypes[i]);
                     } else if (resolvedExceptionType.findSuperTypeOriginatingFrom(21, true) == null && resolvedExceptionType.isValidBinding()) {
                        methodDecl.scope.problemReporter().cannotThrowType(exceptionTypes[i], resolvedExceptionType);
                     } else {
                        if ((resolvedExceptionType.tagBits & 128L) != 0L) {
                           method.tagBits |= 128L;
                        }

                        if (exceptionTypes[i].hasNullTypeAnnotation(TypeReference.AnnotationPosition.ANY)) {
                           methodDecl.scope.problemReporter().nullAnnotationUnsupportedLocation(exceptionTypes[i]);
                        }

                        method.modifiers |= resolvedExceptionType.modifiers & 1073741824;
                        method.thrownExceptions[count++] = resolvedExceptionType;
                     }
                  }
               }

               if (count < size) {
                  System.arraycopy(method.thrownExceptions, 0, method.thrownExceptions = new ReferenceBinding[count], 0, count);
               }
            }

            if (methodDecl.receiver != null) {
               method.receiver = methodDecl.receiver.type.resolveType(methodDecl.scope, true);
            }

            boolean reportUnavoidableGenericTypeProblems = this.scope.compilerOptions().reportUnavoidableGenericTypeProblems;
            boolean foundArgProblem = false;
            boolean checkAPIleak = methodDecl.scope.shouldCheckAPILeaks(this, method.isPublic());
            Argument[] arguments = methodDecl.arguments;
            int i;
            if (arguments != null) {
               int size = arguments.length;
               method.parameters = Binding.NO_PARAMETERS;
               TypeBinding[] newParameters = new TypeBinding[size];

               for(i = 0; i < size; ++i) {
                  Argument arg = arguments[i];
                  if (arg.annotations != null) {
                     method.tagBits |= 1024L;
                  }

                  boolean deferRawTypeCheck = !reportUnavoidableGenericTypeProblems && !method.isConstructor() && (arg.type.bits & 1073741824) == 0;
                  TypeReference var10000;
                  if (deferRawTypeCheck) {
                     var10000 = arg.type;
                     var10000.bits |= 1073741824;
                  }

                  TypeBinding parameterType;
                  try {
                     ASTNode.handleNonNullByDefault(methodDecl.scope, arg.annotations, arg);
                     parameterType = arg.type.resolveType(methodDecl.scope, true);
                  } finally {
                     if (deferRawTypeCheck) {
                        var10000 = arg.type;
                        var10000.bits &= -1073741825;
                     }

                  }

                  if (parameterType == null) {
                     foundArgProblem = true;
                  } else if (parameterType == TypeBinding.VOID) {
                     methodDecl.scope.problemReporter().argumentTypeCannotBeVoid(methodDecl, arg);
                     foundArgProblem = true;
                  } else {
                     if ((parameterType.tagBits & 128L) != 0L) {
                        method.tagBits |= 128L;
                     }

                     TypeBinding leafType = parameterType.leafComponentType();
                     if (leafType instanceof ReferenceBinding && (((ReferenceBinding)leafType).modifiers & 1073741824) != 0) {
                        method.modifiers |= 1073741824;
                     }

                     newParameters[i] = parameterType;
                     if (checkAPIleak) {
                        methodDecl.scope.detectAPILeaks(arg.type, parameterType);
                     }

                     arg.binding = new LocalVariableBinding(arg, parameterType, arg.modifiers, methodDecl.scope);
                  }
               }

               if (!foundArgProblem) {
                  method.parameters = newParameters;
               }
            }

            if (sourceLevel >= 3342336L) {
               if ((method.tagBits & 2251799813685248L) != 0L) {
                  if (!method.isVarargs()) {
                     methodDecl.scope.problemReporter().safeVarargsOnFixedArityMethod(method);
                  } else if (!method.isStatic() && !method.isFinal() && !method.isConstructor() && (sourceLevel < 3473408L || !method.isPrivate())) {
                     methodDecl.scope.problemReporter().safeVarargsOnNonFinalInstanceMethod(method);
                  }
               } else if (method.parameters != null && method.parameters.length > 0 && method.isVarargs() && !method.parameters[method.parameters.length - 1].isReifiable()) {
                  methodDecl.scope.problemReporter().possibleHeapPollutionFromVararg(methodDecl.arguments[methodDecl.arguments.length - 1]);
               }
            }

            boolean foundReturnTypeProblem = false;
            if (!method.isConstructor()) {
               TypeReference returnType = methodDecl instanceof MethodDeclaration ? ((MethodDeclaration)methodDecl).returnType : null;
               if (returnType == null) {
                  methodDecl.scope.problemReporter().missingReturnType(methodDecl);
                  method.returnType = null;
                  foundReturnTypeProblem = true;
               } else {
                  boolean deferRawTypeCheck = !reportUnavoidableGenericTypeProblems && (returnType.bits & 1073741824) == 0;
                  if (deferRawTypeCheck) {
                     returnType.bits |= 1073741824;
                  }

                  TypeBinding methodType;
                  try {
                     methodType = returnType.resolveType(methodDecl.scope, true);
                  } finally {
                     if (deferRawTypeCheck) {
                        returnType.bits &= -1073741825;
                     }

                  }

                  if (methodType == null) {
                     foundReturnTypeProblem = true;
                  } else {
                     if ((methodType.tagBits & 128L) != 0L) {
                        method.tagBits |= 128L;
                     }

                     method.returnType = methodType;
                     if (sourceLevel >= 3407872L && !method.isVoidMethod()) {
                        Annotation[] annotations = methodDecl.annotations;
                        if (annotations != null && annotations.length != 0) {
                           ASTNode.copySE8AnnotationsToType(methodDecl.scope, method, methodDecl.annotations, false);
                        }

                        Annotation.isTypeUseCompatible(returnType, this.scope, methodDecl.annotations);
                     }

                     TypeBinding leafType = methodType.leafComponentType();
                     if (leafType instanceof ReferenceBinding && (((ReferenceBinding)leafType).modifiers & 1073741824) != 0) {
                        method.modifiers |= 1073741824;
                     } else if (leafType == TypeBinding.VOID && methodDecl.annotations != null) {
                        rejectTypeAnnotatedVoidMethod(methodDecl);
                     }

                     if (checkAPIleak) {
                        methodDecl.scope.detectAPILeaks(returnType, methodType);
                     }
                  }
               }
            } else if (sourceLevel >= 3407872L) {
               Annotation[] annotations = methodDecl.annotations;
               if (annotations != null && annotations.length != 0) {
                  ASTNode.copySE8AnnotationsToType(methodDecl.scope, method, methodDecl.annotations, false);
               }
            }

            if (!foundArgProblem) {
               CompilerOptions compilerOptions = this.scope.compilerOptions();
               if (compilerOptions.isAnnotationBasedNullAnalysisEnabled && !method.isConstructor() && method.returnType != null) {
                  long nullTagBits = method.tagBits & 108086391056891904L;
                  if (nullTagBits != 0L) {
                     TypeReference returnTypeRef = ((MethodDeclaration)methodDecl).returnType;
                     if (this.scope.environment().usesNullTypeAnnotations()) {
                        if (!this.scope.validateNullAnnotation(nullTagBits, returnTypeRef, methodDecl.annotations)) {
                           TypeBinding var42 = method.returnType;
                           var42.tagBits &= -108086391056891905L;
                        }

                        method.tagBits &= -108086391056891905L;
                     } else if (!this.scope.validateNullAnnotation(nullTagBits, returnTypeRef, methodDecl.annotations)) {
                        method.tagBits &= -108086391056891905L;
                     }
                  }
               }

               if (compilerOptions.storeAnnotations) {
                  this.createArgumentBindings(method, compilerOptions);
               }

               if (foundReturnTypeProblem) {
                  return method;
               } else {
                  method.modifiers &= -33554433;
                  if (this.externalAnnotationProvider != null) {
                     ExternalAnnotationSuperimposer.annotateMethodBinding(method, this.externalAnnotationProvider, this.environment);
                  }

                  return method;
               }
            } else {
               methodDecl.binding = null;
               method.parameters = Binding.NO_PARAMETERS;
               if (typeParameters != null) {
                  int i = 0;

                  for(i = typeParameters.length; i < i; ++i) {
                     typeParameters[i].binding = null;
                  }
               }

               return null;
            }
         }
      }
   }

   private static void rejectTypeAnnotatedVoidMethod(AbstractMethodDeclaration methodDecl) {
      Annotation[] annotations = methodDecl.annotations;
      int length = annotations == null ? 0 : annotations.length;

      for(int i = 0; i < length; ++i) {
         ReferenceBinding binding = (ReferenceBinding)annotations[i].resolvedType;
         if (binding != null && (binding.tagBits & 9007199254740992L) != 0L && (binding.tagBits & 274877906944L) == 0L) {
            methodDecl.scope.problemReporter().illegalUsageOfTypeAnnotations(annotations[i]);
         }
      }

   }

   private void createArgumentBindings(MethodBinding method, CompilerOptions compilerOptions) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (compilerOptions.isAnnotationBasedNullAnalysisEnabled) {
            this.getNullDefault();
         }

         AbstractMethodDeclaration methodDecl = method.sourceMethod();
         if (methodDecl != null) {
            if (method.parameters != Binding.NO_PARAMETERS) {
               methodDecl.createArgumentBindings();
            }

            if (compilerOptions.isAnnotationBasedNullAnalysisEnabled) {
               (new ImplicitNullAnnotationVerifier(this.scope.environment())).checkImplicitNullAnnotations(method, methodDecl, true, this.scope);
            }
         }

      }
   }

   public void evaluateNullAnnotations() {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else if (this.nullnessDefaultInitialized <= 0 && this.scope.compilerOptions().isAnnotationBasedNullAnalysisEnabled) {
         if ((this.tagBits & 108086391056891904L) != 0L) {
            Annotation[] annotations = this.scope.referenceContext.annotations;

            for(int i = 0; i < annotations.length; ++i) {
               ReferenceBinding annotationType = annotations[i].getCompilerAnnotation().getAnnotationType();
               if (annotationType != null && annotationType.hasNullBit(96)) {
                  this.scope.problemReporter().nullAnnotationUnsupportedLocation(annotations[i]);
                  this.tagBits &= -108086391056891905L;
               }
            }
         }

         boolean isPackageInfo = CharOperation.equals(this.sourceName, TypeConstants.PACKAGE_INFO_NAME);
         PackageBinding pkg = this.getPackage();
         boolean isInDefaultPkg = pkg.compoundName == CharOperation.NO_CHAR_CHAR;
         if (!isPackageInfo) {
            boolean isInNullnessAnnotationPackage = this.scope.environment().isNullnessAnnotationPackage(pkg);
            if (pkg.getDefaultNullness() == 0 && !isInDefaultPkg && !isInNullnessAnnotationPackage && !(this instanceof NestedTypeBinding)) {
               ReferenceBinding packageInfo = pkg.getType(TypeConstants.PACKAGE_INFO_NAME, this.module);
               if (packageInfo == null) {
                  this.scope.problemReporter().missingNonNullByDefaultAnnotation(this.scope.referenceContext);
                  pkg.setDefaultNullness(2);
               } else if (packageInfo instanceof SourceTypeBinding && (packageInfo.tagBits & 512L) == 0L) {
                  CompilationUnitScope pkgCUS = ((SourceTypeBinding)packageInfo).scope.compilationUnitScope();
                  boolean current = pkgCUS.connectingHierarchy;
                  pkgCUS.connectingHierarchy = true;

                  try {
                     packageInfo.getAnnotationTagBits();
                  } finally {
                     pkgCUS.connectingHierarchy = current;
                  }
               } else {
                  packageInfo.getAnnotationTagBits();
               }
            }
         }

         this.nullnessDefaultInitialized = 1;
         if (this.defaultNullness != 0) {
            TypeDeclaration typeDecl = this.scope.referenceContext;
            if (isPackageInfo) {
               if (pkg.enclosingModule.getDefaultNullness() == this.defaultNullness) {
                  this.scope.problemReporter().nullDefaultAnnotationIsRedundant(typeDecl, typeDecl.annotations, pkg.enclosingModule);
               } else {
                  pkg.setDefaultNullness(this.defaultNullness);
               }
            } else {
               Binding target = this.scope.parent.checkRedundantDefaultNullness(this.defaultNullness, typeDecl.declarationSourceStart);
               if (target != null) {
                  this.scope.problemReporter().nullDefaultAnnotationIsRedundant(typeDecl, typeDecl.annotations, target);
               }
            }
         } else if (isPackageInfo || isInDefaultPkg && !(this instanceof NestedTypeBinding)) {
            this.scope.problemReporter().missingNonNullByDefaultAnnotation(this.scope.referenceContext);
            if (!isInDefaultPkg) {
               pkg.setDefaultNullness(2);
            }
         }

         this.maybeMarkTypeParametersNonNull();
      }
   }

   private void maybeMarkTypeParametersNonNull() {
      if (this.typeVariables != null && this.typeVariables.length > 0) {
         if (this.scope == null || !this.scope.hasDefaultNullnessFor(128, this.sourceStart())) {
            return;
         }

         AnnotationBinding[] annots = new AnnotationBinding[]{this.environment.getNonNullAnnotation()};

         for(int i = 0; i < this.typeVariables.length; ++i) {
            TypeVariableBinding tvb = this.typeVariables[i];
            if ((tvb.tagBits & 108086391056891904L) == 0L) {
               this.typeVariables[i] = (TypeVariableBinding)this.environment.createAnnotatedType(tvb, (AnnotationBinding[])annots);
            }
         }
      }

   }

   boolean hasNonNullDefaultFor(int location, int sourceStart) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else if (this.scope == null) {
         return (this.defaultNullness & location) != 0;
      } else {
         Scope skope = this.scope.referenceContext.initializerScope;
         if (skope == null) {
            skope = this.scope;
         }

         return ((Scope)skope).hasDefaultNullnessFor(location, sourceStart);
      }
   }

   public AnnotationHolder retrieveAnnotationHolder(Binding binding, boolean forceInitialization) {
      if (!this.isPrototype()) {
         return this.prototype.retrieveAnnotationHolder(binding, forceInitialization);
      } else {
         if (forceInitialization) {
            binding.getAnnotationTagBits();
         }

         return super.retrieveAnnotationHolder(binding, false);
      }
   }

   public void setContainerAnnotationType(ReferenceBinding value) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         this.containerAnnotationType = value;
      }
   }

   public void tagAsHavingDefectiveContainerType() {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.containerAnnotationType != null && this.containerAnnotationType.isValidBinding()) {
            this.containerAnnotationType = new ProblemReferenceBinding(this.containerAnnotationType.compoundName, this.containerAnnotationType, 22);
         }

      }
   }

   public FieldBinding[] setFields(FieldBinding[] fields) {
      if (!this.isPrototype()) {
         return this.prototype.setFields(fields);
      } else {
         if ((this.tagBits & 8388608L) != 0L) {
            TypeBinding[] annotatedTypes = this.scope.environment().getAnnotatedTypes(this);
            int i = 0;

            for(int length = annotatedTypes == null ? 0 : annotatedTypes.length; i < length; ++i) {
               SourceTypeBinding annotatedType = (SourceTypeBinding)annotatedTypes[i];
               annotatedType.fields = fields;
            }
         }

         return this.fields = fields;
      }
   }

   public ReferenceBinding[] setMemberTypes(ReferenceBinding[] memberTypes) {
      if (!this.isPrototype()) {
         return this.prototype.setMemberTypes(memberTypes);
      } else {
         this.memberTypes = memberTypes;
         if ((this.tagBits & 8388608L) != 0L) {
            TypeBinding[] annotatedTypes = this.scope.environment().getAnnotatedTypes(this);
            int i = 0;

            for(int length = annotatedTypes == null ? 0 : annotatedTypes.length; i < length; ++i) {
               SourceTypeBinding annotatedType = (SourceTypeBinding)annotatedTypes[i];
               annotatedType.tagBits |= 268435456L;
               annotatedType.memberTypes();
            }
         }

         return this.memberTypes;
      }
   }

   public MethodBinding[] setMethods(MethodBinding[] methods) {
      if (!this.isPrototype()) {
         return this.prototype.setMethods(methods);
      } else {
         if ((this.tagBits & 8388608L) != 0L) {
            TypeBinding[] annotatedTypes = this.scope.environment().getAnnotatedTypes(this);
            int i = 0;

            for(int length = annotatedTypes == null ? 0 : annotatedTypes.length; i < length; ++i) {
               SourceTypeBinding annotatedType = (SourceTypeBinding)annotatedTypes[i];
               annotatedType.methods = methods;
            }
         }

         return this.methods = methods;
      }
   }

   public ReferenceBinding setSuperClass(ReferenceBinding superClass) {
      if (!this.isPrototype()) {
         return this.prototype.setSuperClass(superClass);
      } else {
         if ((this.tagBits & 8388608L) != 0L) {
            TypeBinding[] annotatedTypes = this.scope.environment().getAnnotatedTypes(this);
            int i = 0;

            for(int length = annotatedTypes == null ? 0 : annotatedTypes.length; i < length; ++i) {
               SourceTypeBinding annotatedType = (SourceTypeBinding)annotatedTypes[i];
               annotatedType.superclass = superClass;
            }
         }

         return this.superclass = superClass;
      }
   }

   public ReferenceBinding[] setSuperInterfaces(ReferenceBinding[] superInterfaces) {
      if (!this.isPrototype()) {
         return this.prototype.setSuperInterfaces(superInterfaces);
      } else {
         if ((this.tagBits & 8388608L) != 0L) {
            TypeBinding[] annotatedTypes = this.scope.environment().getAnnotatedTypes(this);
            int i = 0;

            for(int length = annotatedTypes == null ? 0 : annotatedTypes.length; i < length; ++i) {
               SourceTypeBinding annotatedType = (SourceTypeBinding)annotatedTypes[i];
               annotatedType.superInterfaces = superInterfaces;
            }
         }

         return this.superInterfaces = superInterfaces;
      }
   }

   public TypeVariableBinding[] setTypeVariables(TypeVariableBinding[] typeVariables) {
      if (!this.isPrototype()) {
         return this.prototype.setTypeVariables(typeVariables);
      } else {
         if ((this.tagBits & 8388608L) != 0L) {
            TypeBinding[] annotatedTypes = this.scope.environment().getAnnotatedTypes(this);
            int i = 0;

            for(int length = annotatedTypes == null ? 0 : annotatedTypes.length; i < length; ++i) {
               SourceTypeBinding annotatedType = (SourceTypeBinding)annotatedTypes[i];
               annotatedType.typeVariables = typeVariables;
            }
         }

         return this.typeVariables = typeVariables;
      }
   }

   public final int sourceEnd() {
      return !this.isPrototype() ? this.prototype.sourceEnd() : this.scope.referenceContext.sourceEnd;
   }

   public final int sourceStart() {
      return !this.isPrototype() ? this.prototype.sourceStart() : this.scope.referenceContext.sourceStart;
   }

   SimpleLookupTable storedAnnotations(boolean forceInitialize, boolean forceStore) {
      if (!this.isPrototype()) {
         return this.prototype.storedAnnotations(forceInitialize, forceStore);
      } else {
         if (forceInitialize && this.storedAnnotations == null && this.scope != null) {
            this.scope.referenceCompilationUnit().compilationResult.hasAnnotations = true;
            CompilerOptions globalOptions = this.scope.environment().globalOptions;
            if (!globalOptions.storeAnnotations && !forceStore) {
               return null;
            }

            this.storedAnnotations = new SimpleLookupTable(3);
         }

         return this.storedAnnotations;
      }
   }

   public ReferenceBinding superclass() {
      return !this.isPrototype() ? (this.superclass = this.prototype.superclass()) : this.superclass;
   }

   public ReferenceBinding[] superInterfaces() {
      if (!this.isPrototype()) {
         return this.superInterfaces = this.prototype.superInterfaces();
      } else {
         return this.superInterfaces != null ? this.superInterfaces : (this.isAnnotationType() ? (this.superInterfaces = new ReferenceBinding[]{this.scope.getJavaLangAnnotationAnnotation()}) : null);
      }
   }

   public SyntheticMethodBinding[] syntheticMethods() {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else if (this.synthetics != null && this.synthetics[0] != null && this.synthetics[0].size() != 0) {
         int index = 0;
         SyntheticMethodBinding[] bindings = new SyntheticMethodBinding[1];
         Iterator methodArrayIterator = this.synthetics[0].values().iterator();

         int i;
         while(methodArrayIterator.hasNext()) {
            SyntheticMethodBinding[] methodAccessors = (SyntheticMethodBinding[])methodArrayIterator.next();
            int i = 0;

            for(i = methodAccessors.length; i < i; ++i) {
               if (methodAccessors[i] != null) {
                  if (index + 1 > bindings.length) {
                     System.arraycopy(bindings, 0, bindings = new SyntheticMethodBinding[index + 1], 0, index);
                  }

                  bindings[index++] = methodAccessors[i];
               }
            }
         }

         int length;
         SyntheticMethodBinding[] sortedBindings = new SyntheticMethodBinding[length = bindings.length];

         for(i = 0; i < length; ++i) {
            SyntheticMethodBinding binding = bindings[i];
            sortedBindings[binding.index] = binding;
         }

         return sortedBindings;
      } else {
         return null;
      }
   }

   public FieldBinding[] syntheticFields() {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else if (this.synthetics == null) {
         return null;
      } else {
         int fieldSize = this.synthetics[1] == null ? 0 : this.synthetics[1].size();
         int literalSize = this.synthetics[2] == null ? 0 : this.synthetics[2].size();
         int totalSize = fieldSize + literalSize;
         if (totalSize == 0) {
            return null;
         } else {
            FieldBinding[] bindings = new FieldBinding[totalSize];
            Iterator elements;
            int i;
            SyntheticFieldBinding synthBinding;
            if (this.synthetics[1] != null) {
               elements = this.synthetics[1].values().iterator();

               for(i = 0; i < fieldSize; ++i) {
                  synthBinding = (SyntheticFieldBinding)elements.next();
                  bindings[synthBinding.index] = synthBinding;
               }
            }

            if (this.synthetics[2] != null) {
               elements = this.synthetics[2].values().iterator();

               for(i = 0; i < literalSize; ++i) {
                  synthBinding = (SyntheticFieldBinding)elements.next();
                  bindings[fieldSize + synthBinding.index] = synthBinding;
               }
            }

            return bindings;
         }
      }
   }

   public String toString() {
      if (this.hasTypeAnnotations()) {
         return this.annotatedDebugName();
      } else {
         StringBuffer buffer = new StringBuffer(30);
         buffer.append("(id=");
         if (this.id == Integer.MAX_VALUE) {
            buffer.append("NoId");
         } else {
            buffer.append(this.id);
         }

         buffer.append(")\n");
         if (this.isDeprecated()) {
            buffer.append("deprecated ");
         }

         if (this.isPublic()) {
            buffer.append("public ");
         }

         if (this.isProtected()) {
            buffer.append("protected ");
         }

         if (this.isPrivate()) {
            buffer.append("private ");
         }

         if (this.isAbstract() && this.isClass()) {
            buffer.append("abstract ");
         }

         if (this.isStatic() && this.isNestedType()) {
            buffer.append("static ");
         }

         if (this.isFinal()) {
            buffer.append("final ");
         }

         if (this.isEnum()) {
            buffer.append("enum ");
         } else if (this.isAnnotationType()) {
            buffer.append("@interface ");
         } else if (this.isClass()) {
            buffer.append("class ");
         } else {
            buffer.append("interface ");
         }

         buffer.append(this.compoundName != null ? CharOperation.toString(this.compoundName) : "UNNAMED TYPE");
         int i;
         int length;
         if (this.typeVariables == null) {
            buffer.append("<NULL TYPE VARIABLES>");
         } else if (this.typeVariables != Binding.NO_TYPE_VARIABLES) {
            buffer.append("<");
            i = 0;

            for(length = this.typeVariables.length; i < length; ++i) {
               if (i > 0) {
                  buffer.append(", ");
               }

               if (this.typeVariables[i] == null) {
                  buffer.append("NULL TYPE VARIABLE");
               } else {
                  char[] varChars = this.typeVariables[i].toString().toCharArray();
                  buffer.append(varChars, 1, varChars.length - 2);
               }
            }

            buffer.append(">");
         }

         buffer.append("\n\textends ");
         buffer.append(this.superclass != null ? this.superclass.debugName() : "NULL TYPE");
         if (this.superInterfaces != null) {
            if (this.superInterfaces != Binding.NO_SUPERINTERFACES) {
               buffer.append("\n\timplements : ");
               i = 0;

               for(length = this.superInterfaces.length; i < length; ++i) {
                  if (i > 0) {
                     buffer.append(", ");
                  }

                  buffer.append(this.superInterfaces[i] != null ? this.superInterfaces[i].debugName() : "NULL TYPE");
               }
            }
         } else {
            buffer.append("NULL SUPERINTERFACES");
         }

         if (this.enclosingType() != null) {
            buffer.append("\n\tenclosing type : ");
            buffer.append(this.enclosingType().debugName());
         }

         if (this.fields != null) {
            if (this.fields != Binding.NO_FIELDS) {
               buffer.append("\n/*   fields   */");
               i = 0;

               for(length = this.fields.length; i < length; ++i) {
                  buffer.append('\n').append(this.fields[i] != null ? this.fields[i].toString() : "NULL FIELD");
               }
            }
         } else {
            buffer.append("NULL FIELDS");
         }

         if (this.methods != null) {
            if (this.methods != Binding.NO_METHODS) {
               buffer.append("\n/*   methods   */");
               i = 0;

               for(length = this.methods.length; i < length; ++i) {
                  buffer.append('\n').append(this.methods[i] != null ? this.methods[i].toString() : "NULL METHOD");
               }
            }
         } else {
            buffer.append("NULL METHODS");
         }

         if (this.memberTypes != null) {
            if (this.memberTypes != Binding.NO_MEMBER_TYPES) {
               buffer.append("\n/*   members   */");
               i = 0;

               for(length = this.memberTypes.length; i < length; ++i) {
                  buffer.append('\n').append(this.memberTypes[i] != null ? this.memberTypes[i].toString() : "NULL TYPE");
               }
            }
         } else {
            buffer.append("NULL MEMBER TYPES");
         }

         buffer.append("\n\n");
         return buffer.toString();
      }
   }

   public TypeVariableBinding[] typeVariables() {
      if (!this.isPrototype()) {
         return this.typeVariables = this.prototype.typeVariables();
      } else {
         return this.typeVariables != null ? this.typeVariables : Binding.NO_TYPE_VARIABLES;
      }
   }

   void verifyMethods(MethodVerifier verifier) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         verifier.verify(this);
         int i = this.memberTypes.length;

         while(true) {
            --i;
            if (i < 0) {
               return;
            }

            ((SourceTypeBinding)this.memberTypes[i]).verifyMethods(verifier);
         }
      }
   }

   public TypeBinding unannotated() {
      return this.prototype;
   }

   public TypeBinding withoutToplevelNullAnnotation() {
      if (!this.hasNullTypeAnnotations()) {
         return this;
      } else {
         AnnotationBinding[] newAnnotations = this.environment.filterNullTypeAnnotations(this.typeAnnotations);
         return (TypeBinding)(newAnnotations.length > 0 ? this.environment.createAnnotatedType(this.prototype, (AnnotationBinding[])newAnnotations) : this.prototype);
      }
   }

   public FieldBinding[] unResolvedFields() {
      return !this.isPrototype() ? this.prototype.unResolvedFields() : this.fields;
   }

   public void tagIndirectlyAccessibleMembers() {
      if (!this.isPrototype()) {
         this.prototype.tagIndirectlyAccessibleMembers();
      } else {
         int i;
         for(i = 0; i < this.fields.length; ++i) {
            if (!this.fields[i].isPrivate()) {
               FieldBinding var10000 = this.fields[i];
               var10000.modifiers |= 134217728;
            }
         }

         for(i = 0; i < this.memberTypes.length; ++i) {
            if (!this.memberTypes[i].isPrivate()) {
               ReferenceBinding var2 = this.memberTypes[i];
               var2.modifiers |= 134217728;
            }
         }

         if (this.superclass.isPrivate() && this.superclass instanceof SourceTypeBinding) {
            ((SourceTypeBinding)this.superclass).tagIndirectlyAccessibleMembers();
         }

      }
   }

   public ModuleBinding module() {
      return !this.isPrototype() ? this.prototype.module : this.module;
   }

   public SourceTypeBinding getNestHost() {
      return this.nestHost;
   }

   public void setNestHost(SourceTypeBinding nestHost) {
      this.nestHost = nestHost;
   }

   public boolean isNestmateOf(SourceTypeBinding other) {
      CompilerOptions options = this.scope.compilerOptions();
      if (options.targetJDK >= 3604480L && options.complianceLevel >= 3604480L) {
         SourceTypeBinding otherHost = other.getNestHost();
         return TypeBinding.equalsEquals(this, other) || TypeBinding.equalsEquals(this.nestHost == null ? this : this.nestHost, otherHost == null ? other : otherHost);
      } else {
         return false;
      }
   }

   public void addNestMember(SourceTypeBinding member) {
      if (this.nestMembers == null) {
         this.nestMembers = new HashSet();
      }

      this.nestMembers.add(member);
   }

   public List getNestMembers() {
      if (this.nestMembers == null) {
         return null;
      } else {
         List list = (List)this.nestMembers.stream().map((s) -> {
            return new String(s.constantPoolName());
         }).sorted().collect(Collectors.toList());
         return list;
      }
   }
}

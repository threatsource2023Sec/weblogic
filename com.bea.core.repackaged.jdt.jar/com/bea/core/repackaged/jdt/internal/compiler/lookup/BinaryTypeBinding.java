package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Annotation;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.AnnotationInfo;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.MethodInfoWithAnnotations;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.NonNullDefaultAwareTypeAnnotationWalker;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.TypeAnnotationWalker;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.ConstantPool;
import com.bea.core.repackaged.jdt.internal.compiler.env.ClassSignature;
import com.bea.core.repackaged.jdt.internal.compiler.env.EnumConstantSignature;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryElementValuePair;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryField;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryMethod;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryNestedType;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryType;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryTypeAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.env.ITypeAnnotationWalker;
import com.bea.core.repackaged.jdt.internal.compiler.impl.BooleanConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortCompilation;
import com.bea.core.repackaged.jdt.internal.compiler.util.SimpleLookupTable;
import java.util.ArrayList;

public class BinaryTypeBinding extends ReferenceBinding {
   public static final char[] TYPE_QUALIFIER_DEFAULT = "TypeQualifierDefault".toCharArray();
   private static final IBinaryMethod[] NO_BINARY_METHODS = new IBinaryMethod[0];
   protected ReferenceBinding superclass;
   protected ReferenceBinding enclosingType;
   protected ReferenceBinding[] superInterfaces;
   protected FieldBinding[] fields;
   protected MethodBinding[] methods;
   protected ReferenceBinding[] memberTypes;
   protected TypeVariableBinding[] typeVariables;
   protected ModuleBinding module;
   private BinaryTypeBinding prototype;
   protected LookupEnvironment environment;
   protected SimpleLookupTable storedAnnotations;
   private ReferenceBinding containerAnnotationType;
   int defaultNullness;
   public ExternalAnnotationStatus externalAnnotationStatus;

   static Object convertMemberValue(Object binaryValue, LookupEnvironment env, char[][][] missingTypeNames, boolean resolveEnumConstants) {
      if (binaryValue == null) {
         return null;
      } else if (binaryValue instanceof Constant) {
         return binaryValue;
      } else if (binaryValue instanceof ClassSignature) {
         return env.getTypeFromSignature(((ClassSignature)binaryValue).getTypeName(), 0, -1, false, (TypeBinding)null, missingTypeNames, ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER);
      } else if (binaryValue instanceof IBinaryAnnotation) {
         return createAnnotation((IBinaryAnnotation)binaryValue, env, missingTypeNames);
      } else if (binaryValue instanceof EnumConstantSignature) {
         EnumConstantSignature ref = (EnumConstantSignature)binaryValue;
         ReferenceBinding enumType = (ReferenceBinding)env.getTypeFromSignature(ref.getTypeName(), 0, -1, false, (TypeBinding)null, missingTypeNames, ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER);
         if (enumType.isUnresolvedType() && !resolveEnumConstants) {
            return new ElementValuePair.UnresolvedEnumConstant(enumType, env, ref.getEnumConstantName());
         } else {
            enumType = (ReferenceBinding)resolveType(enumType, env, false);
            return enumType.getField(ref.getEnumConstantName(), false);
         }
      } else if (!(binaryValue instanceof Object[])) {
         throw new IllegalStateException();
      } else {
         Object[] objects = (Object[])binaryValue;
         int length = objects.length;
         if (length == 0) {
            return objects;
         } else {
            Object[] values = new Object[length];

            for(int i = 0; i < length; ++i) {
               values[i] = convertMemberValue(objects[i], env, missingTypeNames, resolveEnumConstants);
            }

            return values;
         }
      }
   }

   public TypeBinding clone(TypeBinding outerType) {
      BinaryTypeBinding copy = new BinaryTypeBinding(this);
      copy.enclosingType = (ReferenceBinding)outerType;
      if (copy.enclosingType != null) {
         copy.tagBits |= 134217728L;
      } else {
         copy.tagBits &= -134217729L;
      }

      copy.tagBits |= 268435456L;
      return copy;
   }

   static AnnotationBinding createAnnotation(IBinaryAnnotation annotationInfo, LookupEnvironment env, char[][][] missingTypeNames) {
      if (annotationInfo instanceof AnnotationInfo) {
         RuntimeException ex = ((AnnotationInfo)annotationInfo).exceptionDuringDecode;
         if (ex != null) {
            (new IllegalStateException("Accessing annotation with decode error", ex)).printStackTrace();
         }
      }

      IBinaryElementValuePair[] binaryPairs = annotationInfo.getElementValuePairs();
      int length = binaryPairs == null ? 0 : binaryPairs.length;
      ElementValuePair[] pairs = length == 0 ? Binding.NO_ELEMENT_VALUE_PAIRS : new ElementValuePair[length];

      for(int i = 0; i < length; ++i) {
         pairs[i] = new ElementValuePair(binaryPairs[i].getName(), convertMemberValue(binaryPairs[i].getValue(), env, missingTypeNames, false), (MethodBinding)null);
      }

      char[] typeName = annotationInfo.getTypeName();
      LookupEnvironment env2 = annotationInfo.isExternalAnnotation() ? env.root : env;
      ReferenceBinding annotationType = env2.getTypeFromConstantPoolName(typeName, 1, typeName.length - 1, false, missingTypeNames);
      return env2.createUnresolvedAnnotation(annotationType, pairs);
   }

   public static AnnotationBinding[] createAnnotations(IBinaryAnnotation[] annotationInfos, LookupEnvironment env, char[][][] missingTypeNames) {
      int length = annotationInfos == null ? 0 : annotationInfos.length;
      AnnotationBinding[] result = length == 0 ? Binding.NO_ANNOTATIONS : new AnnotationBinding[length];

      for(int i = 0; i < length; ++i) {
         result[i] = createAnnotation(annotationInfos[i], env, missingTypeNames);
      }

      return result;
   }

   public static TypeBinding resolveType(TypeBinding type, LookupEnvironment environment, boolean convertGenericToRawType) {
      switch (type.kind()) {
         case 68:
            ArrayBinding arrayBinding = (ArrayBinding)type;
            TypeBinding leafComponentType = arrayBinding.leafComponentType;
            resolveType(leafComponentType, environment, convertGenericToRawType);
            if (leafComponentType.hasNullTypeAnnotations() && environment.usesNullTypeAnnotations()) {
               if (arrayBinding.nullTagBitsPerDimension == null) {
                  arrayBinding.nullTagBitsPerDimension = new long[arrayBinding.dimensions + 1];
               }

               arrayBinding.nullTagBitsPerDimension[arrayBinding.dimensions] = leafComponentType.tagBits & 108086391056891904L;
            }
            break;
         case 260:
            ((ParameterizedTypeBinding)type).resolve();
            break;
         case 516:
         case 8196:
            return ((WildcardBinding)type).resolve();
         case 2052:
            if (convertGenericToRawType) {
               return environment.convertUnresolvedBinaryToRawType(type);
            }
            break;
         case 4100:
            ((TypeVariableBinding)type).resolve();
            break;
         default:
            if (type instanceof UnresolvedReferenceBinding) {
               return ((UnresolvedReferenceBinding)type).resolve(environment, convertGenericToRawType);
            }

            if (convertGenericToRawType) {
               return environment.convertUnresolvedBinaryToRawType(type);
            }
      }

      return type;
   }

   protected BinaryTypeBinding() {
      this.storedAnnotations = null;
      this.defaultNullness = 0;
      this.externalAnnotationStatus = BinaryTypeBinding.ExternalAnnotationStatus.NOT_EEA_CONFIGURED;
      this.prototype = this;
   }

   public BinaryTypeBinding(BinaryTypeBinding prototype) {
      super(prototype);
      this.storedAnnotations = null;
      this.defaultNullness = 0;
      this.externalAnnotationStatus = BinaryTypeBinding.ExternalAnnotationStatus.NOT_EEA_CONFIGURED;
      this.superclass = prototype.superclass;
      this.enclosingType = prototype.enclosingType;
      this.superInterfaces = prototype.superInterfaces;
      this.fields = prototype.fields;
      this.methods = prototype.methods;
      this.memberTypes = prototype.memberTypes;
      this.typeVariables = prototype.typeVariables;
      this.prototype = prototype.prototype;
      this.environment = prototype.environment;
      this.storedAnnotations = prototype.storedAnnotations;
   }

   public BinaryTypeBinding(PackageBinding packageBinding, IBinaryType binaryType, LookupEnvironment environment) {
      this(packageBinding, binaryType, environment, false);
   }

   public BinaryTypeBinding(PackageBinding packageBinding, IBinaryType binaryType, LookupEnvironment environment, boolean needFieldsAndMethods) {
      this.storedAnnotations = null;
      this.defaultNullness = 0;
      this.externalAnnotationStatus = BinaryTypeBinding.ExternalAnnotationStatus.NOT_EEA_CONFIGURED;
      this.prototype = this;
      this.compoundName = CharOperation.splitOn('/', binaryType.getName());
      this.computeId();
      this.tagBits |= 64L;
      this.environment = environment;
      this.fPackage = packageBinding;
      this.fileName = binaryType.getFileName();
      char[] typeSignature = binaryType.getGenericSignature();
      this.typeVariables = typeSignature != null && typeSignature.length > 0 && typeSignature[0] == '<' ? null : Binding.NO_TYPE_VARIABLES;
      this.sourceName = binaryType.getSourceName();
      this.modifiers = binaryType.getModifiers();
      if ((binaryType.getTagBits() & 131072L) != 0L) {
         this.tagBits |= 131072L;
      }

      if (binaryType.isAnonymous()) {
         this.tagBits |= 2100L;
      } else if (binaryType.isLocal()) {
         this.tagBits |= 2068L;
      } else if (binaryType.isMember()) {
         this.tagBits |= 2060L;
      }

      char[] enclosingTypeName = binaryType.getEnclosingTypeName();
      if (enclosingTypeName != null) {
         this.enclosingType = environment.getTypeFromConstantPoolName(enclosingTypeName, 0, -1, true, (char[][][])null);
         this.tagBits |= 2060L;
         this.tagBits |= 134217728L;
         if (this.enclosingType().isStrictfp()) {
            this.modifiers |= 2048;
         }

         if (this.enclosingType().isDeprecated()) {
            this.modifiers |= 2097152;
         }
      }

      if (needFieldsAndMethods) {
         this.cachePartsFrom(binaryType, true);
      }

   }

   public boolean canBeSeenBy(Scope sco) {
      ModuleBinding mod = sco.module();
      return mod.canAccess(this.fPackage) && super.canBeSeenBy(sco);
   }

   public FieldBinding[] availableFields() {
      if (!this.isPrototype()) {
         return this.prototype.availableFields();
      } else if ((this.tagBits & 8192L) != 0L) {
         return this.fields;
      } else {
         if ((this.tagBits & 4096L) == 0L) {
            int length = this.fields.length;
            if (length > 1) {
               ReferenceBinding.sortFields(this.fields, 0, length);
            }

            this.tagBits |= 4096L;
         }

         FieldBinding[] availableFields = new FieldBinding[this.fields.length];
         int count = 0;

         for(int i = 0; i < this.fields.length; ++i) {
            try {
               availableFields[count] = this.resolveTypeFor(this.fields[i]);
               ++count;
            } catch (AbortCompilation var4) {
            }
         }

         if (count < availableFields.length) {
            System.arraycopy(availableFields, 0, availableFields = new FieldBinding[count], 0, count);
         }

         return availableFields;
      }
   }

   private TypeVariableBinding[] addMethodTypeVariables(TypeVariableBinding[] methodTypeVars) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else if (this.typeVariables != null && this.typeVariables != Binding.NO_TYPE_VARIABLES) {
         if (methodTypeVars != null && methodTypeVars != Binding.NO_TYPE_VARIABLES) {
            int total = this.typeVariables.length + methodTypeVars.length;
            TypeVariableBinding[] combinedTypeVars = new TypeVariableBinding[total];
            System.arraycopy(this.typeVariables, 0, combinedTypeVars, 0, this.typeVariables.length);
            int size = this.typeVariables.length;
            int i = 0;

            label37:
            for(int len = methodTypeVars.length; i < len; ++i) {
               for(int j = this.typeVariables.length - 1; j >= 0; --j) {
                  if (CharOperation.equals(methodTypeVars[i].sourceName, this.typeVariables[j].sourceName)) {
                     continue label37;
                  }
               }

               combinedTypeVars[size++] = methodTypeVars[i];
            }

            if (size != total) {
               System.arraycopy(combinedTypeVars, 0, combinedTypeVars = new TypeVariableBinding[size], 0, size);
            }

            return combinedTypeVars;
         } else {
            return this.typeVariables;
         }
      } else {
         return methodTypeVars;
      }
   }

   public MethodBinding[] availableMethods() {
      if (!this.isPrototype()) {
         return this.prototype.availableMethods();
      } else if ((this.tagBits & 32768L) != 0L) {
         return this.methods;
      } else {
         if ((this.tagBits & 16384L) == 0L) {
            int length = this.methods.length;
            if (length > 1) {
               ReferenceBinding.sortMethods(this.methods, 0, length);
            }

            this.tagBits |= 16384L;
         }

         MethodBinding[] availableMethods = new MethodBinding[this.methods.length];
         int count = 0;

         for(int i = 0; i < this.methods.length; ++i) {
            try {
               availableMethods[count] = this.resolveTypesFor(this.methods[i]);
               ++count;
            } catch (AbortCompilation var4) {
            }
         }

         if (count < availableMethods.length) {
            System.arraycopy(availableMethods, 0, availableMethods = new MethodBinding[count], 0, count);
         }

         return availableMethods;
      }
   }

   void cachePartsFrom(IBinaryType binaryType, boolean needFieldsAndMethods) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         try {
            this.typeVariables = Binding.NO_TYPE_VARIABLES;
            this.superInterfaces = Binding.NO_SUPERINTERFACES;
            this.memberTypes = Binding.NO_MEMBER_TYPES;
            IBinaryNestedType[] memberTypeStructures = binaryType.getMemberTypes();
            if (memberTypeStructures != null) {
               int size = memberTypeStructures.length;
               if (size > 0) {
                  this.memberTypes = new ReferenceBinding[size];

                  for(int i = 0; i < size; ++i) {
                     this.memberTypes[i] = this.environment.getTypeFromConstantPoolName(memberTypeStructures[i].getName(), 0, -1, false, (char[][][])null);
                  }

                  this.tagBits |= 268435456L;
               }
            }

            CompilerOptions globalOptions = this.environment.globalOptions;
            long sourceLevel = globalOptions.originalSourceLevel;
            if (this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
               this.scanTypeForNullDefaultAnnotation(binaryType, this.fPackage);
            }

            ITypeAnnotationWalker walker = this.getTypeAnnotationWalker(binaryType.getTypeAnnotations(), 0);
            ITypeAnnotationWalker toplevelWalker = binaryType.enrichWithExternalAnnotationsFor(walker, (Object)null, this.environment);
            this.externalAnnotationStatus = binaryType.getExternalAnnotationStatus();
            if (this.externalAnnotationStatus.isPotentiallyUnannotatedLib() && this.defaultNullness != 0) {
               this.externalAnnotationStatus = BinaryTypeBinding.ExternalAnnotationStatus.TYPE_IS_ANNOTATED;
            }

            char[] typeSignature = binaryType.getGenericSignature();
            this.tagBits |= binaryType.getTagBits();
            if (this.environment.globalOptions.complianceLevel < 3407872L) {
               this.tagBits &= -9007199254740993L;
            }

            char[][][] missingTypeNames = binaryType.getMissingTypeNames();
            SignatureWrapper wrapper = null;
            if (typeSignature != null) {
               wrapper = new SignatureWrapper(typeSignature);
               if (wrapper.signature[wrapper.start] == '<') {
                  ++wrapper.start;
                  this.typeVariables = this.createTypeVariables(wrapper, true, missingTypeNames, toplevelWalker, true);
                  ++wrapper.start;
                  this.tagBits |= 16777216L;
                  this.modifiers |= 1073741824;
               }
            }

            TypeVariableBinding[] typeVars = Binding.NO_TYPE_VARIABLES;
            char[] methodDescriptor = binaryType.getEnclosingMethod();
            if (methodDescriptor != null) {
               MethodBinding enclosingMethod = this.findMethod(methodDescriptor, missingTypeNames);
               if (enclosingMethod != null) {
                  typeVars = enclosingMethod.typeVariables;
                  this.typeVariables = this.addMethodTypeVariables(typeVars);
               }
            }

            int size;
            int i;
            if (typeSignature == null) {
               char[] superclassName = binaryType.getSuperclassName();
               if (superclassName != null) {
                  this.superclass = this.environment.getTypeFromConstantPoolName(superclassName, 0, -1, false, missingTypeNames, toplevelWalker.toSupertype((short)-1, superclassName));
                  this.tagBits |= 33554432L;
               }

               this.superInterfaces = Binding.NO_SUPERINTERFACES;
               char[][] interfaceNames = binaryType.getInterfaceNames();
               if (interfaceNames != null) {
                  size = interfaceNames.length;
                  if (size > 0) {
                     this.superInterfaces = new ReferenceBinding[size];

                     for(i = 0; i < size; i = (short)(i + 1)) {
                        this.superInterfaces[i] = this.environment.getTypeFromConstantPoolName(interfaceNames[i], 0, -1, false, missingTypeNames, toplevelWalker.toSupertype((short)i, superclassName));
                     }

                     this.tagBits |= 67108864L;
                  }
               }
            } else {
               this.superclass = (ReferenceBinding)this.environment.getTypeFromTypeSignature(wrapper, typeVars, this, missingTypeNames, toplevelWalker.toSupertype((short)-1, wrapper.peekFullType()));
               this.tagBits |= 33554432L;
               this.superInterfaces = Binding.NO_SUPERINTERFACES;
               if (!wrapper.atEnd()) {
                  ArrayList types = new ArrayList(2);
                  short rank = 0;

                  do {
                     types.add(this.environment.getTypeFromTypeSignature(wrapper, typeVars, this, missingTypeNames, toplevelWalker.toSupertype(rank++, wrapper.peekFullType())));
                  } while(!wrapper.atEnd());

                  this.superInterfaces = new ReferenceBinding[types.size()];
                  types.toArray(this.superInterfaces);
                  this.tagBits |= 67108864L;
               }
            }

            boolean canUseNullTypeAnnotations = this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled && this.environment.globalOptions.sourceLevel >= 3407872L;
            if (canUseNullTypeAnnotations && this.externalAnnotationStatus.isPotentiallyUnannotatedLib()) {
               if (this.superclass != null && this.superclass.hasNullTypeAnnotations()) {
                  this.externalAnnotationStatus = BinaryTypeBinding.ExternalAnnotationStatus.TYPE_IS_ANNOTATED;
               } else {
                  ReferenceBinding[] var18;
                  i = (var18 = this.superInterfaces).length;

                  for(size = 0; size < i; ++size) {
                     TypeBinding ifc = var18[size];
                     if (ifc.hasNullTypeAnnotations()) {
                        this.externalAnnotationStatus = BinaryTypeBinding.ExternalAnnotationStatus.TYPE_IS_ANNOTATED;
                        break;
                     }
                  }
               }
            }

            if (needFieldsAndMethods) {
               IBinaryField[] iFields = binaryType.getFields();
               this.createFields(iFields, binaryType, sourceLevel, missingTypeNames);
               IBinaryMethod[] iMethods = this.createMethods(binaryType.getMethods(), binaryType, sourceLevel, missingTypeNames);
               boolean isViewedAsDeprecated = this.isViewedAsDeprecated();
               int i;
               if (isViewedAsDeprecated) {
                  i = 0;

                  int max;
                  for(max = this.fields.length; i < max; ++i) {
                     FieldBinding field = this.fields[i];
                     if (!field.isDeprecated()) {
                        field.modifiers |= 2097152;
                     }
                  }

                  i = 0;

                  for(max = this.methods.length; i < max; ++i) {
                     MethodBinding method = this.methods[i];
                     if (!method.isDeprecated()) {
                        method.modifiers |= 2097152;
                     }
                  }
               }

               if (this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
                  ITypeAnnotationWalker methodWalker;
                  if (iFields != null) {
                     for(i = 0; i < iFields.length; ++i) {
                        methodWalker = ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
                        if (sourceLevel < 3407872L) {
                           methodWalker = binaryType.enrichWithExternalAnnotationsFor(walker, iFields[i], this.environment);
                        }

                        this.scanFieldForNullAnnotation(iFields[i], this.fields[i], this.isEnum(), methodWalker);
                     }
                  }

                  if (iMethods != null) {
                     for(i = 0; i < iMethods.length; ++i) {
                        methodWalker = ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
                        if (sourceLevel < 3407872L) {
                           methodWalker = binaryType.enrichWithExternalAnnotationsFor(methodWalker, iMethods[i], this.environment);
                        }

                        this.scanMethodForNullAnnotation(iMethods[i], this.methods[i], methodWalker, canUseNullTypeAnnotations);
                     }
                  }
               }
            }

            if (this.environment.globalOptions.storeAnnotations) {
               this.setAnnotations(createAnnotations(binaryType.getAnnotations(), this.environment, missingTypeNames), false);
            } else if (sourceLevel >= 3473408L && this.isDeprecated() && binaryType.getAnnotations() != null) {
               IBinaryAnnotation[] var42;
               i = (var42 = binaryType.getAnnotations()).length;

               label509:
               for(size = 0; size < i; ++size) {
                  IBinaryAnnotation annotation = var42[size];
                  if (annotation.isDeprecatedAnnotation()) {
                     AnnotationBinding[] annotationBindings = createAnnotations(new IBinaryAnnotation[]{annotation}, this.environment, missingTypeNames);
                     this.setAnnotations(annotationBindings, true);
                     ElementValuePair[] var23;
                     int var22 = (var23 = annotationBindings[0].getElementValuePairs()).length;
                     int var21 = 0;

                     while(true) {
                        if (var21 >= var22) {
                           break label509;
                        }

                        ElementValuePair elementValuePair = var23[var21];
                        if (CharOperation.equals(elementValuePair.name, TypeConstants.FOR_REMOVAL) && elementValuePair.value instanceof BooleanConstant && ((BooleanConstant)elementValuePair.value).booleanValue()) {
                           this.tagBits |= 4611686018427387904L;
                           this.markImplicitTerminalDeprecation(this);
                        }

                        ++var21;
                     }
                  }
               }
            }

            if (this.isAnnotationType()) {
               this.scanTypeForContainerAnnotation(binaryType, missingTypeNames);
            }
         } finally {
            if (this.fields == null) {
               this.fields = Binding.NO_FIELDS;
            }

            if (this.methods == null) {
               this.methods = Binding.NO_METHODS;
            }

         }

      }
   }

   void markImplicitTerminalDeprecation(ReferenceBinding type) {
      ReferenceBinding[] var5;
      int var4 = (var5 = type.memberTypes()).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         ReferenceBinding member = var5[var3];
         member.tagBits |= 4611686018427387904L;
         this.markImplicitTerminalDeprecation(member);
      }

      MethodBinding[] methodsOfType = type.unResolvedMethods();
      int var12;
      if (methodsOfType != null) {
         MethodBinding[] var6 = methodsOfType;
         var12 = methodsOfType.length;

         for(var4 = 0; var4 < var12; ++var4) {
            MethodBinding methodBinding = var6[var4];
            methodBinding.tagBits |= 4611686018427387904L;
         }
      }

      FieldBinding[] fieldsOfType = type.unResolvedFields();
      if (fieldsOfType != null) {
         FieldBinding[] var7 = fieldsOfType;
         int var13 = fieldsOfType.length;

         for(var12 = 0; var12 < var13; ++var12) {
            FieldBinding fieldBinding = var7[var12];
            fieldBinding.tagBits |= 4611686018427387904L;
         }
      }

   }

   private ITypeAnnotationWalker getTypeAnnotationWalker(IBinaryTypeAnnotation[] annotations, int nullness) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else if (annotations != null && annotations.length != 0 && this.environment.usesAnnotatedTypeSystem()) {
         if (this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
            if (nullness == 0) {
               nullness = this.getNullDefault();
            }

            if (nullness > 2) {
               return new NonNullDefaultAwareTypeAnnotationWalker(annotations, nullness, this.environment);
            }
         }

         return new TypeAnnotationWalker(annotations);
      } else {
         if (this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
            if (nullness == 0) {
               nullness = this.getNullDefault();
            }

            if (nullness > 2) {
               return new NonNullDefaultAwareTypeAnnotationWalker(nullness, this.environment);
            }
         }

         return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
      }
   }

   private int getNullDefaultFrom(IBinaryAnnotation[] declAnnotations) {
      int result = 0;
      if (declAnnotations != null) {
         IBinaryAnnotation[] var6 = declAnnotations;
         int var5 = declAnnotations.length;

         for(int var4 = 0; var4 < var5; ++var4) {
            IBinaryAnnotation annotation = var6[var4];
            char[][] typeName = signature2qualifiedTypeName(annotation.getTypeName());
            if (this.environment.getNullAnnotationBit(typeName) == 128) {
               result |= getNonNullByDefaultValue(annotation, this.environment);
            }
         }
      }

      return result;
   }

   private void createFields(IBinaryField[] iFields, IBinaryType binaryType, long sourceLevel, char[][][] missingTypeNames) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         boolean save = this.environment.mayTolerateMissingType;
         this.environment.mayTolerateMissingType = true;

         try {
            this.fields = Binding.NO_FIELDS;
            if (iFields != null) {
               int size = iFields.length;
               if (size > 0) {
                  FieldBinding[] fields1 = new FieldBinding[size];
                  boolean use15specifics = sourceLevel >= 3211264L;
                  boolean hasRestrictedAccess = this.hasRestrictedAccess();
                  int firstAnnotatedFieldIndex = -1;

                  int i;
                  IBinaryField binaryField;
                  for(i = 0; i < size; ++i) {
                     binaryField = iFields[i];
                     char[] fieldSignature = use15specifics ? binaryField.getGenericSignature() : null;
                     ITypeAnnotationWalker walker = this.getTypeAnnotationWalker(binaryField.getTypeAnnotations(), this.getNullDefaultFrom(binaryField.getAnnotations()));
                     if (sourceLevel >= 3407872L) {
                        walker = binaryType.enrichWithExternalAnnotationsFor(walker, iFields[i], this.environment);
                     }

                     walker = walker.toField();
                     TypeBinding type = fieldSignature == null ? this.environment.getTypeFromSignature(binaryField.getTypeName(), 0, -1, false, this, missingTypeNames, walker) : this.environment.getTypeFromTypeSignature(new SignatureWrapper(fieldSignature), Binding.NO_TYPE_VARIABLES, this, missingTypeNames, walker);
                     FieldBinding field = new FieldBinding(binaryField.getName(), type, binaryField.getModifiers() | 33554432, this, binaryField.getConstant());
                     boolean forceStoreAnnotations = !this.environment.globalOptions.storeAnnotations && this.environment.globalOptions.sourceLevel >= 3473408L && binaryField.getAnnotations() != null && (binaryField.getTagBits() & 70368744177664L) != 0L;
                     if (firstAnnotatedFieldIndex < 0 && (this.environment.globalOptions.storeAnnotations || forceStoreAnnotations) && binaryField.getAnnotations() != null) {
                        firstAnnotatedFieldIndex = i;
                        if (forceStoreAnnotations) {
                           this.storedAnnotations(true, true);
                        }
                     }

                     field.id = i;
                     if (use15specifics) {
                        field.tagBits |= binaryField.getTagBits();
                     }

                     if (hasRestrictedAccess) {
                        field.modifiers |= 262144;
                     }

                     if (fieldSignature != null) {
                        field.modifiers |= 1073741824;
                     }

                     fields1[i] = field;
                  }

                  this.fields = fields1;
                  if (firstAnnotatedFieldIndex >= 0) {
                     for(i = firstAnnotatedFieldIndex; i < size; ++i) {
                        binaryField = iFields[i];
                        this.fields[i].setAnnotations(createAnnotations(binaryField.getAnnotations(), this.environment, missingTypeNames), false);
                     }
                  }
               }
            }
         } finally {
            this.environment.mayTolerateMissingType = save;
         }

      }
   }

   private MethodBinding createMethod(IBinaryMethod method, IBinaryType binaryType, long sourceLevel, char[][][] missingTypeNames) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         int methodModifiers = method.getModifiers() | 33554432;
         if (sourceLevel < 3211264L) {
            methodModifiers &= -129;
         }

         if (this.isInterface() && (methodModifiers & 1024) == 0 && (methodModifiers & 8) == 0 && (methodModifiers & 2) == 0) {
            methodModifiers |= 65536;
         }

         ReferenceBinding[] exceptions = Binding.NO_EXCEPTIONS;
         TypeBinding[] parameters = Binding.NO_PARAMETERS;
         TypeVariableBinding[] typeVars = Binding.NO_TYPE_VARIABLES;
         AnnotationBinding[][] paramAnnotations = null;
         TypeBinding returnType = null;
         char[][] argumentNames = method.getArgumentNames();
         boolean use15specifics = sourceLevel >= 3211264L;
         ITypeAnnotationWalker walker = this.getTypeAnnotationWalker(method.getTypeAnnotations(), this.getNullDefaultFrom(method.getAnnotations()));
         char[] methodSignature = method.getGenericSignature();
         int nextChar;
         int i;
         int startIndex;
         IBinaryAnnotation[] annotations;
         if (methodSignature == null) {
            char[] methodDescriptor = method.getMethodDescriptor();
            if (sourceLevel >= 3407872L) {
               walker = binaryType.enrichWithExternalAnnotationsFor(walker, method, this.environment);
            }

            int numOfParams = 0;
            i = 0;

            label213:
            while(true) {
               do {
                  do {
                     ++i;
                     if ((nextChar = methodDescriptor[i]) == 41) {
                        startIndex = 0;
                        if (method.isConstructor()) {
                           if (this.isMemberType() && !this.isStatic()) {
                              ++startIndex;
                           }

                           if (this.isEnum()) {
                              startIndex += 2;
                           }
                        }

                        int size = numOfParams - startIndex;
                        int end;
                        if (size > 0) {
                           parameters = new TypeBinding[size];
                           if (this.environment.globalOptions.storeAnnotations) {
                              paramAnnotations = new AnnotationBinding[size][];
                           }

                           i = 1;
                           short visibleIdx = 0;
                           end = 0;

                           for(int i = 0; i < numOfParams; ++i) {
                              do {
                                 ++end;
                              } while((nextChar = methodDescriptor[end]) == 91);

                              if (nextChar == 76) {
                                 do {
                                    ++end;
                                 } while(methodDescriptor[end] != ';');
                              }

                              if (i >= startIndex) {
                                 parameters[i - startIndex] = this.environment.getTypeFromSignature(methodDescriptor, i, end, false, this, missingTypeNames, walker.toMethodParameter(visibleIdx++));
                                 if (paramAnnotations != null) {
                                    paramAnnotations[i - startIndex] = createAnnotations(method.getParameterAnnotations(i - startIndex, this.fileName), this.environment, missingTypeNames);
                                 }
                              }

                              i = end + 1;
                           }
                        }

                        char[][] exceptionTypes = method.getExceptionTypeNames();
                        if (exceptionTypes != null) {
                           size = exceptionTypes.length;
                           if (size > 0) {
                              exceptions = new ReferenceBinding[size];

                              for(end = 0; end < size; ++end) {
                                 exceptions[end] = this.environment.getTypeFromConstantPoolName(exceptionTypes[end], 0, -1, false, missingTypeNames, walker.toThrows(end));
                              }
                           }
                        }

                        if (!method.isConstructor()) {
                           returnType = this.environment.getTypeFromSignature(methodDescriptor, i + 1, -1, false, this, missingTypeNames, walker.toMethodReturn());
                        }

                        end = argumentNames == null ? 0 : argumentNames.length;
                        if (startIndex > 0 && end > 0) {
                           if (startIndex >= end) {
                              argumentNames = Binding.NO_PARAMETER_NAMES;
                           } else {
                              char[][] slicedArgumentNames = new char[end - startIndex][];
                              System.arraycopy(argumentNames, startIndex, slicedArgumentNames, 0, end - startIndex);
                              argumentNames = slicedArgumentNames;
                           }
                        }
                        break label213;
                     }
                  } while(nextChar == 91);

                  ++numOfParams;
               } while(nextChar != 76);

               while(true) {
                  ++i;
                  if (methodDescriptor[i] == ';') {
                     break;
                  }
               }
            }
         } else {
            if (sourceLevel >= 3407872L) {
               walker = binaryType.enrichWithExternalAnnotationsFor(walker, method, this.environment);
            }

            methodModifiers |= 1073741824;
            SignatureWrapper wrapper = new SignatureWrapper(methodSignature, use15specifics);
            if (wrapper.signature[wrapper.start] == '<') {
               ++wrapper.start;
               typeVars = this.createTypeVariables(wrapper, false, missingTypeNames, walker, false);
               ++wrapper.start;
            }

            ArrayList types;
            if (wrapper.signature[wrapper.start] == '(') {
               ++wrapper.start;
               if (wrapper.signature[wrapper.start] == ')') {
                  ++wrapper.start;
               } else {
                  types = new ArrayList(2);

                  for(nextChar = 0; wrapper.signature[wrapper.start] != ')'; nextChar = (short)(nextChar + 1)) {
                     annotations = method.getParameterAnnotations(nextChar, this.fileName);
                     ITypeAnnotationWalker updatedWalker = NonNullDefaultAwareTypeAnnotationWalker.updateWalkerForParamNonNullDefault(walker, this.getNullDefaultFrom(annotations), this.environment);
                     types.add(this.environment.getTypeFromTypeSignature(wrapper, typeVars, this, missingTypeNames, updatedWalker.toMethodParameter((short)nextChar)));
                  }

                  ++wrapper.start;
                  i = types.size();
                  parameters = new TypeBinding[i];
                  types.toArray(parameters);
                  if (this.environment.globalOptions.storeAnnotations) {
                     paramAnnotations = new AnnotationBinding[i][];

                     for(startIndex = 0; startIndex < i; ++startIndex) {
                        paramAnnotations[startIndex] = createAnnotations(method.getParameterAnnotations(startIndex, this.fileName), this.environment, missingTypeNames);
                     }
                  }
               }
            }

            returnType = this.environment.getTypeFromTypeSignature(wrapper, typeVars, this, missingTypeNames, walker.toMethodReturn());
            if (!wrapper.atEnd() && wrapper.signature[wrapper.start] == '^') {
               types = new ArrayList(2);
               nextChar = 0;

               do {
                  ++wrapper.start;
                  types.add(this.environment.getTypeFromTypeSignature(wrapper, typeVars, this, missingTypeNames, walker.toThrows(nextChar++)));
               } while(!wrapper.atEnd() && wrapper.signature[wrapper.start] == '^');

               exceptions = new ReferenceBinding[types.size()];
               types.toArray(exceptions);
            } else {
               char[][] exceptionTypes = method.getExceptionTypeNames();
               if (exceptionTypes != null) {
                  nextChar = exceptionTypes.length;
                  if (nextChar > 0) {
                     exceptions = new ReferenceBinding[nextChar];

                     for(i = 0; i < nextChar; ++i) {
                        exceptions[i] = this.environment.getTypeFromConstantPoolName(exceptionTypes[i], 0, -1, false, missingTypeNames, walker.toThrows(i));
                     }
                  }
               }
            }
         }

         MethodBinding result = method.isConstructor() ? new MethodBinding(methodModifiers, parameters, exceptions, this) : new MethodBinding(methodModifiers, method.getSelector(), returnType, parameters, exceptions, this);
         IBinaryAnnotation[] receiverAnnotations = walker.toReceiver().getAnnotationsAtCursor(this.id, false);
         if (receiverAnnotations != null && receiverAnnotations.length > 0) {
            result.receiver = this.environment.createAnnotatedType(this, (AnnotationBinding[])createAnnotations(receiverAnnotations, this.environment, missingTypeNames));
         }

         boolean forceStoreAnnotations = !this.environment.globalOptions.storeAnnotations && this.environment.globalOptions.sourceLevel >= 3473408L && method instanceof MethodInfoWithAnnotations && (method.getTagBits() & 70368744177664L) != 0L;
         if (this.environment.globalOptions.storeAnnotations || forceStoreAnnotations) {
            if (forceStoreAnnotations) {
               this.storedAnnotations(true, true);
            }

            annotations = method.getAnnotations();
            if (method.isConstructor()) {
               IBinaryAnnotation[] tAnnotations = walker.toMethodReturn().getAnnotationsAtCursor(this.id, false);
               result.setTypeAnnotations(createAnnotations(tAnnotations, this.environment, missingTypeNames));
            }

            result.setAnnotations(createAnnotations(annotations, this.environment, missingTypeNames), paramAnnotations, this.isAnnotationType() ? convertMemberValue(method.getDefaultValue(), this.environment, missingTypeNames, true) : null, this.environment);
         }

         if (argumentNames != null) {
            result.parameterNames = argumentNames;
         }

         if (use15specifics) {
            result.tagBits |= method.getTagBits();
         }

         result.typeVariables = typeVars;
         i = 0;

         for(startIndex = typeVars.length; i < startIndex; ++i) {
            this.environment.typeSystem.fixTypeVariableDeclaringElement(typeVars[i], result);
         }

         return result;
      }
   }

   private IBinaryMethod[] createMethods(IBinaryMethod[] iMethods, IBinaryType binaryType, long sourceLevel, char[][][] missingTypeNames) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         boolean save = this.environment.mayTolerateMissingType;
         this.environment.mayTolerateMissingType = true;

         try {
            int total = 0;
            int initialTotal = 0;
            int iClinit = -1;
            int[] toSkip = null;
            boolean hasRestrictedAccess;
            if (iMethods != null) {
               total = initialTotal = iMethods.length;
               hasRestrictedAccess = sourceLevel < 3211264L;
               int i = total;

               label211:
               while(true) {
                  IBinaryMethod method;
                  do {
                     while(true) {
                        --i;
                        if (i < 0) {
                           break label211;
                        }

                        method = iMethods[i];
                        if ((method.getModifiers() & 4096) != 0) {
                           break;
                        }

                        if (iClinit == -1) {
                           char[] methodName = method.getSelector();
                           if (methodName.length == 8 && methodName[0] == '<') {
                              iClinit = i;
                              --total;
                           }
                        }
                     }
                  } while(hasRestrictedAccess && (method.getModifiers() & 64) != 0);

                  if (toSkip == null) {
                     toSkip = new int[iMethods.length];
                  }

                  toSkip[i] = -1;
                  --total;
               }
            }

            IBinaryMethod[] var18;
            if (total == 0) {
               this.methods = Binding.NO_METHODS;
               var18 = NO_BINARY_METHODS;
               return var18;
            } else {
               hasRestrictedAccess = this.hasRestrictedAccess();
               MethodBinding[] methods1 = new MethodBinding[total];
               if (total != initialTotal) {
                  IBinaryMethod[] mappedBinaryMethods = new IBinaryMethod[total];
                  int i = 0;

                  for(int index = 0; i < initialTotal; ++i) {
                     if (iClinit != i && (toSkip == null || toSkip[i] != -1)) {
                        MethodBinding method = this.createMethod(iMethods[i], binaryType, sourceLevel, missingTypeNames);
                        if (hasRestrictedAccess) {
                           method.modifiers |= 262144;
                        }

                        mappedBinaryMethods[index] = iMethods[i];
                        methods1[index++] = method;
                     }
                  }

                  this.methods = methods1;
                  var18 = mappedBinaryMethods;
                  return var18;
               } else {
                  for(int i = 0; i < initialTotal; ++i) {
                     MethodBinding method = this.createMethod(iMethods[i], binaryType, sourceLevel, missingTypeNames);
                     if (hasRestrictedAccess) {
                        method.modifiers |= 262144;
                     }

                     methods1[i] = method;
                  }

                  this.methods = methods1;
                  var18 = iMethods;
                  return var18;
               }
            }
         } finally {
            this.environment.mayTolerateMissingType = save;
         }
      }
   }

   private TypeVariableBinding[] createTypeVariables(SignatureWrapper wrapper, boolean assignVariables, char[][][] missingTypeNames, ITypeAnnotationWalker walker, boolean isClassTypeParameter) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         char[] typeSignature = wrapper.signature;
         int depth = false;
         int length = typeSignature.length;
         int rank = 0;
         ArrayList variables = new ArrayList(1);
         int depth = 0;
         boolean pendingVariable = true;

         int i;
         label55:
         for(int i = 1; i < length; ++i) {
            switch (typeSignature[i]) {
               case ';':
                  if (depth == 0 && i + 1 < length && typeSignature[i + 1] != ':') {
                     pendingVariable = true;
                  }
                  break;
               case '<':
                  ++depth;
                  break;
               case '=':
               default:
                  if (pendingVariable) {
                     pendingVariable = false;
                     i = CharOperation.indexOf(':', typeSignature, i);
                     char[] variableName = CharOperation.subarray(typeSignature, i, i);
                     TypeVariableBinding typeVariable = new TypeVariableBinding(variableName, this, rank, this.environment);
                     AnnotationBinding[] annotations = createAnnotations(walker.toTypeParameter(isClassTypeParameter, rank++).getAnnotationsAtCursor(0, false), this.environment, missingTypeNames);
                     if (annotations != null && annotations != Binding.NO_ANNOTATIONS) {
                        typeVariable.setTypeAnnotations(annotations, this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled);
                     }

                     variables.add(typeVariable);
                  }
                  break;
               case '>':
                  --depth;
                  if (depth < 0) {
                     break label55;
                  }
            }
         }

         TypeVariableBinding[] result;
         variables.toArray(result = new TypeVariableBinding[rank]);
         if (assignVariables) {
            this.typeVariables = result;
         }

         for(i = 0; i < rank; ++i) {
            this.initializeTypeVariable(result[i], result, wrapper, missingTypeNames, walker.toTypeParameterBounds(isClassTypeParameter, i));
            if (this.externalAnnotationStatus.isPotentiallyUnannotatedLib() && result[i].hasNullTypeAnnotations()) {
               this.externalAnnotationStatus = BinaryTypeBinding.ExternalAnnotationStatus.TYPE_IS_ANNOTATED;
            }
         }

         return result;
      }
   }

   public ReferenceBinding enclosingType() {
      if ((this.tagBits & 134217728L) == 0L) {
         return this.enclosingType;
      } else {
         this.enclosingType = (ReferenceBinding)resolveType(this.enclosingType, this.environment, false);
         this.tagBits &= -134217729L;
         return this.enclosingType;
      }
   }

   public FieldBinding[] fields() {
      if (!this.isPrototype()) {
         return this.fields = this.prototype.fields();
      } else if ((this.tagBits & 8192L) != 0L) {
         return this.fields;
      } else {
         int i;
         if ((this.tagBits & 4096L) == 0L) {
            i = this.fields.length;
            if (i > 1) {
               ReferenceBinding.sortFields(this.fields, 0, i);
            }

            this.tagBits |= 4096L;
         }

         i = this.fields.length;

         while(true) {
            --i;
            if (i < 0) {
               this.tagBits |= 8192L;
               return this.fields;
            }

            this.resolveTypeFor(this.fields[i]);
         }
      }
   }

   private MethodBinding findMethod(char[] methodDescriptor, char[][][] missingTypeNames) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         int index = -1;

         do {
            ++index;
         } while(methodDescriptor[index] != '(');

         char[] selector = new char[index];
         System.arraycopy(methodDescriptor, 0, selector, 0, index);
         TypeBinding[] parameters = Binding.NO_PARAMETERS;
         int numOfParams = 0;
         int paramStart = index;

         while(true) {
            char nextChar;
            do {
               do {
                  ++index;
                  if ((nextChar = methodDescriptor[index]) == ')') {
                     int end;
                     if (numOfParams > 0) {
                        parameters = new TypeBinding[numOfParams];
                        index = paramStart + 1;
                        end = paramStart;

                        for(int i = 0; i < numOfParams; ++i) {
                           do {
                              ++end;
                           } while((nextChar = methodDescriptor[end]) == '[');

                           if (nextChar == 'L') {
                              do {
                                 ++end;
                              } while(methodDescriptor[end] != ';');
                           }

                           TypeBinding param = this.environment.getTypeFromSignature(methodDescriptor, index, end, false, this, missingTypeNames, ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER);
                           if (param instanceof UnresolvedReferenceBinding) {
                              param = resolveType(param, this.environment, true);
                           }

                           parameters[i] = param;
                           index = end + 1;
                        }
                     }

                     end = parameters.length;
                     MethodBinding[] methods2 = this.enclosingType.getMethods(selector, end);
                     int i = 0;

                     label51:
                     for(int max = methods2.length; i < max; ++i) {
                        MethodBinding currentMethod = methods2[i];
                        TypeBinding[] parameters2 = currentMethod.parameters;
                        int currentMethodParameterLength = parameters2.length;
                        if (end == currentMethodParameterLength) {
                           for(int j = 0; j < currentMethodParameterLength; ++j) {
                              if (TypeBinding.notEquals(parameters[j], parameters2[j]) && TypeBinding.notEquals(parameters[j].erasure(), parameters2[j].erasure())) {
                                 continue label51;
                              }
                           }

                           return currentMethod;
                        }
                     }

                     return null;
                  }
               } while(nextChar == '[');

               ++numOfParams;
            } while(nextChar != 'L');

            while(true) {
               ++index;
               if (methodDescriptor[index] == ';') {
                  break;
               }
            }
         }
      }
   }

   public char[] genericTypeSignature() {
      return !this.isPrototype() ? this.prototype.computeGenericTypeSignature(this.typeVariables) : this.computeGenericTypeSignature(this.typeVariables);
   }

   public MethodBinding getExactConstructor(TypeBinding[] argumentTypes) {
      if (!this.isPrototype()) {
         return this.prototype.getExactConstructor(argumentTypes);
      } else {
         int argCount;
         if ((this.tagBits & 16384L) == 0L) {
            argCount = this.methods.length;
            if (argCount > 1) {
               ReferenceBinding.sortMethods(this.methods, 0, argCount);
            }

            this.tagBits |= 16384L;
         }

         argCount = argumentTypes.length;
         long range;
         if ((range = ReferenceBinding.binarySearch(TypeConstants.INIT, this.methods)) >= 0L) {
            int imethod = (int)range;

            label38:
            for(int end = (int)(range >> 32); imethod <= end; ++imethod) {
               MethodBinding method = this.methods[imethod];
               if (method.parameters.length == argCount) {
                  this.resolveTypesFor(method);
                  TypeBinding[] toMatch = method.parameters;

                  for(int iarg = 0; iarg < argCount; ++iarg) {
                     if (TypeBinding.notEquals(toMatch[iarg], argumentTypes[iarg])) {
                        continue label38;
                     }
                  }

                  return method;
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
         int argCount;
         if ((this.tagBits & 16384L) == 0L) {
            argCount = this.methods.length;
            if (argCount > 1) {
               ReferenceBinding.sortMethods(this.methods, 0, argCount);
            }

            this.tagBits |= 16384L;
         }

         argCount = argumentTypes.length;
         boolean foundNothing = true;
         long range;
         if ((range = ReferenceBinding.binarySearch(selector, this.methods)) >= 0L) {
            int imethod = (int)range;

            label57:
            for(int end = (int)(range >> 32); imethod <= end; ++imethod) {
               MethodBinding method = this.methods[imethod];
               foundNothing = false;
               if (method.parameters.length == argCount) {
                  this.resolveTypesFor(method);
                  TypeBinding[] toMatch = method.parameters;

                  for(int iarg = 0; iarg < argCount; ++iarg) {
                     if (TypeBinding.notEquals(toMatch[iarg], argumentTypes[iarg])) {
                        continue label57;
                     }
                  }

                  return method;
               }
            }
         }

         if (foundNothing) {
            if (this.isInterface()) {
               if (this.superInterfaces().length == 1) {
                  if (refScope != null) {
                     refScope.recordTypeReference(this.superInterfaces[0]);
                  }

                  return this.superInterfaces[0].getExactMethod(selector, argumentTypes, refScope);
               }
            } else if (this.superclass() != null) {
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
      } else {
         if ((this.tagBits & 4096L) == 0L) {
            int length = this.fields.length;
            if (length > 1) {
               ReferenceBinding.sortFields(this.fields, 0, length);
            }

            this.tagBits |= 4096L;
         }

         FieldBinding field = ReferenceBinding.binarySearch(fieldName, this.fields);
         return needResolve && field != null ? this.resolveTypeFor(field) : field;
      }
   }

   public ReferenceBinding getMemberType(char[] typeName) {
      if (!this.isPrototype()) {
         ReferenceBinding memberType = this.prototype.getMemberType(typeName);
         return memberType == null ? null : this.environment.createMemberType(memberType, this);
      } else {
         int i = this.memberTypes.length;

         while(true) {
            --i;
            if (i < 0) {
               return null;
            }

            ReferenceBinding memberType = this.memberTypes[i];
            if (memberType instanceof UnresolvedReferenceBinding) {
               char[] name = memberType.sourceName;
               int prefixLength = this.compoundName[this.compoundName.length - 1].length + 1;
               if (name.length == prefixLength + typeName.length && CharOperation.fragmentEquals(typeName, name, prefixLength, true)) {
                  return this.memberTypes[i] = (ReferenceBinding)resolveType(memberType, this.environment, false);
               }
            } else if (CharOperation.equals(typeName, memberType.sourceName)) {
               return memberType;
            }
         }
      }
   }

   public MethodBinding[] getMethods(char[] selector) {
      if (!this.isPrototype()) {
         return this.prototype.getMethods(selector);
      } else {
         int start;
         int end;
         int length;
         MethodBinding[] result;
         long range;
         if ((this.tagBits & 32768L) != 0L) {
            if ((range = ReferenceBinding.binarySearch(selector, this.methods)) >= 0L) {
               start = (int)range;
               end = (int)(range >> 32);
               length = end - start + 1;
               if ((this.tagBits & 32768L) != 0L) {
                  System.arraycopy(this.methods, start, result = new MethodBinding[length], 0, length);
                  return result;
               }
            }

            return Binding.NO_METHODS;
         } else {
            if ((this.tagBits & 16384L) == 0L) {
               int length = this.methods.length;
               if (length > 1) {
                  ReferenceBinding.sortMethods(this.methods, 0, length);
               }

               this.tagBits |= 16384L;
            }

            if ((range = ReferenceBinding.binarySearch(selector, this.methods)) < 0L) {
               return Binding.NO_METHODS;
            } else {
               start = (int)range;
               end = (int)(range >> 32);
               length = end - start + 1;
               result = new MethodBinding[length];
               int i = start;

               for(int index = 0; i <= end; ++index) {
                  result[index] = this.resolveTypesFor(this.methods[i]);
                  ++i;
               }

               return result;
            }
         }
      }
   }

   public MethodBinding[] getMethods(char[] selector, int suggestedParameterLength) {
      if (!this.isPrototype()) {
         return this.prototype.getMethods(selector, suggestedParameterLength);
      } else if ((this.tagBits & 32768L) != 0L) {
         return this.getMethods(selector);
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
            int start = (int)range;
            int end = (int)(range >> 32);
            int length = end - start + 1;
            int count = 0;

            for(int i = start; i <= end; ++i) {
               if (this.methods[i].doesParameterLengthMatch(suggestedParameterLength)) {
                  ++count;
               }
            }

            int i;
            int index;
            MethodBinding[] result;
            if (count != 0) {
               result = new MethodBinding[count];
               i = start;

               for(index = 0; i <= end; ++i) {
                  if (this.methods[i].doesParameterLengthMatch(suggestedParameterLength)) {
                     result[index++] = this.resolveTypesFor(this.methods[i]);
                  }
               }

               return result;
            } else {
               result = new MethodBinding[length];
               i = start;

               for(index = 0; i <= end; ++i) {
                  result[index++] = this.resolveTypesFor(this.methods[i]);
               }

               return result;
            }
         }
      }
   }

   public boolean hasMemberTypes() {
      if (!this.isPrototype()) {
         return this.prototype.hasMemberTypes();
      } else {
         return this.memberTypes.length > 0;
      }
   }

   public TypeVariableBinding getTypeVariable(char[] variableName) {
      if (!this.isPrototype()) {
         return this.prototype.getTypeVariable(variableName);
      } else {
         TypeVariableBinding variable = super.getTypeVariable(variableName);
         variable.resolve();
         return variable;
      }
   }

   public boolean hasTypeBit(int bit) {
      if (!this.isPrototype()) {
         return this.prototype.hasTypeBit(bit);
      } else {
         boolean wasToleratingMissingTypeProcessingAnnotations = this.environment.mayTolerateMissingType;
         this.environment.mayTolerateMissingType = true;

         try {
            this.superclass();
            this.superInterfaces();
         } finally {
            this.environment.mayTolerateMissingType = wasToleratingMissingTypeProcessingAnnotations;
         }

         return (this.typeBits & bit) != 0;
      }
   }

   private void initializeTypeVariable(TypeVariableBinding variable, TypeVariableBinding[] existingVariables, SignatureWrapper wrapper, char[][][] missingTypeNames, ITypeAnnotationWalker walker) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         int colon = CharOperation.indexOf(':', wrapper.signature, wrapper.start);
         wrapper.start = colon + 1;
         ReferenceBinding firstBound = null;
         short rank = 0;
         ReferenceBinding type;
         if (wrapper.signature[wrapper.start] == ':') {
            type = this.environment.getResolvedJavaBaseType(TypeConstants.JAVA_LANG_OBJECT, (Scope)null);
            ++rank;
         } else {
            TypeBinding typeFromTypeSignature = this.environment.getTypeFromTypeSignature(wrapper, existingVariables, this, missingTypeNames, walker.toTypeBound(rank++));
            if (typeFromTypeSignature instanceof ReferenceBinding) {
               type = (ReferenceBinding)typeFromTypeSignature;
            } else {
               type = this.environment.getResolvedJavaBaseType(TypeConstants.JAVA_LANG_OBJECT, (Scope)null);
            }

            firstBound = type;
         }

         variable.modifiers |= 33554432;
         variable.setSuperClass(type);
         ReferenceBinding[] bounds = null;
         if (wrapper.signature[wrapper.start] == ':') {
            ArrayList types = new ArrayList(2);

            do {
               ++wrapper.start;
               types.add(this.environment.getTypeFromTypeSignature(wrapper, existingVariables, this, missingTypeNames, walker.toTypeBound(rank++)));
            } while(wrapper.signature[wrapper.start] == ':');

            bounds = new ReferenceBinding[types.size()];
            types.toArray(bounds);
         }

         variable.setSuperInterfaces(bounds == null ? Binding.NO_SUPERINTERFACES : bounds);
         if (firstBound == null) {
            firstBound = variable.superInterfaces.length == 0 ? null : variable.superInterfaces[0];
         }

         variable.setFirstBound(firstBound);
      }
   }

   public boolean isEquivalentTo(TypeBinding otherType) {
      if (TypeBinding.equalsEquals(this, otherType)) {
         return true;
      } else if (otherType == null) {
         return false;
      } else {
         switch (otherType.kind()) {
            case 260:
            case 1028:
               return TypeBinding.equalsEquals(otherType.erasure(), this);
            case 516:
            case 8196:
               return ((WildcardBinding)otherType).boundCheck(this);
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
         return (this.tagBits & 100663296L) == 0L;
      }
   }

   public boolean isRepeatableAnnotationType() {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         return this.containerAnnotationType != null;
      }
   }

   public int kind() {
      if (!this.isPrototype()) {
         return this.prototype.kind();
      } else {
         return this.typeVariables != Binding.NO_TYPE_VARIABLES ? 2052 : 4;
      }
   }

   public ReferenceBinding[] memberTypes() {
      if (!this.isPrototype()) {
         if ((this.tagBits & 268435456L) == 0L) {
            return this.memberTypes;
         } else {
            ReferenceBinding[] members = this.prototype.memberTypes();
            int memberTypesLength = members == null ? 0 : members.length;
            if (memberTypesLength > 0) {
               this.memberTypes = new ReferenceBinding[memberTypesLength];

               for(int i = 0; i < memberTypesLength; ++i) {
                  this.memberTypes[i] = this.environment.createMemberType(members[i], this);
               }
            }

            this.tagBits &= -268435457L;
            return this.memberTypes;
         }
      } else if ((this.tagBits & 268435456L) == 0L) {
         return this.memberTypes;
      } else {
         int i = this.memberTypes.length;

         while(true) {
            --i;
            if (i < 0) {
               this.tagBits &= -268435457L;
               return this.memberTypes;
            }

            this.memberTypes[i] = (ReferenceBinding)resolveType(this.memberTypes[i], this.environment, false);
         }
      }
   }

   public MethodBinding[] methods() {
      if (!this.isPrototype()) {
         return this.methods = this.prototype.methods();
      } else if ((this.tagBits & 32768L) != 0L) {
         return this.methods;
      } else {
         int i;
         if ((this.tagBits & 16384L) == 0L) {
            i = this.methods.length;
            if (i > 1) {
               ReferenceBinding.sortMethods(this.methods, 0, i);
            }

            this.tagBits |= 16384L;
         }

         i = this.methods.length;

         while(true) {
            --i;
            if (i < 0) {
               this.tagBits |= 32768L;
               return this.methods;
            }

            this.resolveTypesFor(this.methods[i]);
         }
      }
   }

   public TypeBinding prototype() {
      return this.prototype;
   }

   private boolean isPrototype() {
      return this == this.prototype;
   }

   public ReferenceBinding containerAnnotationType() {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.containerAnnotationType instanceof UnresolvedReferenceBinding) {
            this.containerAnnotationType = (ReferenceBinding)resolveType(this.containerAnnotationType, this.environment, false);
         }

         return this.containerAnnotationType;
      }
   }

   private FieldBinding resolveTypeFor(FieldBinding field) {
      if (!this.isPrototype()) {
         return this.prototype.resolveTypeFor(field);
      } else if ((field.modifiers & 33554432) == 0) {
         return field;
      } else {
         TypeBinding resolvedType = resolveType(field.type, this.environment, true);
         field.type = resolvedType;
         if ((resolvedType.tagBits & 128L) != 0L) {
            field.tagBits |= 128L;
         }

         field.modifiers &= -33554433;
         return field;
      }
   }

   MethodBinding resolveTypesFor(MethodBinding method) {
      if (!this.isPrototype()) {
         return this.prototype.resolveTypesFor(method);
      } else if ((method.modifiers & 33554432) == 0) {
         return method;
      } else {
         if (!method.isConstructor()) {
            TypeBinding resolvedType = resolveType(method.returnType, this.environment, true);
            method.returnType = resolvedType;
            if ((resolvedType.tagBits & 128L) != 0L) {
               method.tagBits |= 128L;
            }
         }

         int i = method.parameters.length;

         while(true) {
            --i;
            if (i < 0) {
               i = method.thrownExceptions.length;

               while(true) {
                  --i;
                  if (i < 0) {
                     i = method.typeVariables.length;

                     while(true) {
                        --i;
                        if (i < 0) {
                           method.modifiers &= -33554433;
                           return method;
                        }

                        method.typeVariables[i].resolve();
                     }
                  }

                  ReferenceBinding resolvedType = (ReferenceBinding)resolveType(method.thrownExceptions[i], this.environment, true);
                  method.thrownExceptions[i] = resolvedType;
                  if ((resolvedType.tagBits & 128L) != 0L) {
                     method.tagBits |= 128L;
                  }
               }
            }

            TypeBinding resolvedType = resolveType(method.parameters[i], this.environment, true);
            method.parameters[i] = resolvedType;
            if ((resolvedType.tagBits & 128L) != 0L) {
               method.tagBits |= 128L;
            }
         }
      }
   }

   AnnotationBinding[] retrieveAnnotations(Binding binding) {
      return !this.isPrototype() ? this.prototype.retrieveAnnotations(binding) : AnnotationBinding.addStandardAnnotations(super.retrieveAnnotations(binding), binding.getAnnotationTagBits(), this.environment);
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

   SimpleLookupTable storedAnnotations(boolean forceInitialize, boolean forceStore) {
      if (!this.isPrototype()) {
         return this.prototype.storedAnnotations(forceInitialize, forceStore);
      } else {
         if (forceInitialize && this.storedAnnotations == null) {
            if (!this.environment.globalOptions.storeAnnotations && !forceStore) {
               return null;
            }

            this.storedAnnotations = new SimpleLookupTable(3);
         }

         return this.storedAnnotations;
      }
   }

   private void scanFieldForNullAnnotation(IBinaryField field, FieldBinding fieldBinding, boolean isEnum, ITypeAnnotationWalker externalAnnotationWalker) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else if (isEnum && (field.getModifiers() & 16384) != 0) {
         fieldBinding.tagBits |= 72057594037927936L;
      } else if (!CharOperation.equals(this.fPackage.compoundName, TypeConstants.JAVA_LANG_ANNOTATION) && this.environment.usesNullTypeAnnotations()) {
         TypeBinding fieldType = fieldBinding.type;
         if (fieldType != null && !fieldType.isBaseType() && (fieldType.tagBits & 108086391056891904L) == 0L && fieldType.acceptsNonNullDefault()) {
            int nullDefaultFromField = this.getNullDefaultFrom(field.getAnnotations());
            if (nullDefaultFromField == 0) {
               if (!this.hasNonNullDefaultFor(32, -1)) {
                  return;
               }
            } else if ((nullDefaultFromField & 32) == 0) {
               return;
            }

            fieldBinding.type = this.environment.createAnnotatedType(fieldType, new AnnotationBinding[]{this.environment.getNonNullAnnotation()});
         }

      } else if (fieldBinding.type != null && !fieldBinding.type.isBaseType()) {
         boolean explicitNullness = false;
         IBinaryAnnotation[] annotations = externalAnnotationWalker != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER ? externalAnnotationWalker.getAnnotationsAtCursor(fieldBinding.type.id, false) : field.getAnnotations();
         int nullDefaultFromField;
         if (annotations != null) {
            for(nullDefaultFromField = 0; nullDefaultFromField < annotations.length; ++nullDefaultFromField) {
               char[] annotationTypeName = annotations[nullDefaultFromField].getTypeName();
               if (annotationTypeName[0] == 'L') {
                  int typeBit = this.environment.getNullAnnotationBit(signature2qualifiedTypeName(annotationTypeName));
                  if (typeBit == 32) {
                     fieldBinding.tagBits |= 72057594037927936L;
                     explicitNullness = true;
                     break;
                  }

                  if (typeBit == 64) {
                     fieldBinding.tagBits |= 36028797018963968L;
                     explicitNullness = true;
                     break;
                  }
               }
            }
         }

         if (explicitNullness && this.externalAnnotationStatus.isPotentiallyUnannotatedLib()) {
            this.externalAnnotationStatus = BinaryTypeBinding.ExternalAnnotationStatus.TYPE_IS_ANNOTATED;
         }

         if (!explicitNullness) {
            nullDefaultFromField = this.getNullDefaultFrom(field.getAnnotations());
            if (nullDefaultFromField == 0) {
               if (!this.hasNonNullDefaultFor(32, -1)) {
                  return;
               }
            } else if ((nullDefaultFromField & 32) == 0) {
               return;
            }

            fieldBinding.tagBits |= 72057594037927936L;
         }

      }
   }

   private void scanMethodForNullAnnotation(IBinaryMethod method, MethodBinding methodBinding, ITypeAnnotationWalker externalAnnotationWalker, boolean useNullTypeAnnotations) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.isEnum()) {
            int purpose = 0;
            if (CharOperation.equals(TypeConstants.VALUEOF, method.getSelector()) && methodBinding.parameters.length == 1 && methodBinding.parameters[0].id == 11) {
               purpose = 10;
            } else if (CharOperation.equals(TypeConstants.VALUES, method.getSelector()) && methodBinding.parameters == Binding.NO_PARAMETERS) {
               purpose = 9;
            }

            if (purpose != 0) {
               boolean needToDefer = this.environment.globalOptions.useNullTypeAnnotations == null;
               if (needToDefer) {
                  this.environment.deferredEnumMethods.add(methodBinding);
               } else {
                  SyntheticMethodBinding.markNonNull(methodBinding, purpose, this.environment);
               }

               return;
            }
         }

         ITypeAnnotationWalker returnWalker = externalAnnotationWalker.toMethodReturn();
         IBinaryAnnotation[] annotations = returnWalker != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER ? returnWalker.getAnnotationsAtCursor(methodBinding.returnType.id, false) : method.getAnnotations();
         int numVisibleParams;
         int j;
         if (annotations != null) {
            int methodDefaultNullness = 0;

            for(numVisibleParams = 0; numVisibleParams < annotations.length; ++numVisibleParams) {
               char[] annotationTypeName = annotations[numVisibleParams].getTypeName();
               if (annotationTypeName[0] == 'L') {
                  j = this.environment.getNullAnnotationBit(signature2qualifiedTypeName(annotationTypeName));
                  if (j == 128) {
                     methodDefaultNullness |= getNonNullByDefaultValue(annotations[numVisibleParams], this.environment);
                  } else if (j == 32) {
                     methodBinding.tagBits |= 72057594037927936L;
                     if (this.environment.usesNullTypeAnnotations() && methodBinding.returnType != null && !methodBinding.returnType.hasNullTypeAnnotations()) {
                        methodBinding.returnType = this.environment.createAnnotatedType(methodBinding.returnType, new AnnotationBinding[]{this.environment.getNonNullAnnotation()});
                     }
                  } else if (j == 64) {
                     methodBinding.tagBits |= 36028797018963968L;
                     if (this.environment.usesNullTypeAnnotations() && methodBinding.returnType != null && !methodBinding.returnType.hasNullTypeAnnotations()) {
                        methodBinding.returnType = this.environment.createAnnotatedType(methodBinding.returnType, new AnnotationBinding[]{this.environment.getNullableAnnotation()});
                     }
                  }
               }
            }

            methodBinding.defaultNullness = methodDefaultNullness;
         }

         TypeBinding[] parameters = methodBinding.parameters;
         numVisibleParams = parameters.length;
         int numParamAnnotations = externalAnnotationWalker instanceof ExternalAnnotationProvider.IMethodAnnotationWalker ? ((ExternalAnnotationProvider.IMethodAnnotationWalker)externalAnnotationWalker).getParameterCount() : method.getAnnotatedParametersCount();
         int startIndex;
         if (numParamAnnotations > 0) {
            for(j = 0; j < numVisibleParams; ++j) {
               if (numParamAnnotations > 0) {
                  startIndex = numParamAnnotations - numVisibleParams;
                  ITypeAnnotationWalker parameterWalker = externalAnnotationWalker.toMethodParameter((short)(j + startIndex));
                  IBinaryAnnotation[] paramAnnotations = parameterWalker != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER ? parameterWalker.getAnnotationsAtCursor(parameters[j].id, false) : method.getParameterAnnotations(j + startIndex, this.fileName);
                  if (paramAnnotations != null) {
                     for(int i = 0; i < paramAnnotations.length; ++i) {
                        char[] annotationTypeName = paramAnnotations[i].getTypeName();
                        if (annotationTypeName[0] == 'L') {
                           int typeBit = this.environment.getNullAnnotationBit(signature2qualifiedTypeName(annotationTypeName));
                           if (typeBit == 32) {
                              if (methodBinding.parameterNonNullness == null) {
                                 methodBinding.parameterNonNullness = new Boolean[numVisibleParams];
                              }

                              methodBinding.parameterNonNullness[j] = Boolean.TRUE;
                              if (this.environment.usesNullTypeAnnotations() && methodBinding.parameters[j] != null && !methodBinding.parameters[j].hasNullTypeAnnotations()) {
                                 methodBinding.parameters[j] = this.environment.createAnnotatedType(methodBinding.parameters[j], new AnnotationBinding[]{this.environment.getNonNullAnnotation()});
                              }
                              break;
                           }

                           if (typeBit == 64) {
                              if (methodBinding.parameterNonNullness == null) {
                                 methodBinding.parameterNonNullness = new Boolean[numVisibleParams];
                              }

                              methodBinding.parameterNonNullness[j] = Boolean.FALSE;
                              if (this.environment.usesNullTypeAnnotations() && methodBinding.parameters[j] != null && !methodBinding.parameters[j].hasNullTypeAnnotations()) {
                                 methodBinding.parameters[j] = this.environment.createAnnotatedType(methodBinding.parameters[j], new AnnotationBinding[]{this.environment.getNullableAnnotation()});
                              }
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }

         if (useNullTypeAnnotations && this.externalAnnotationStatus.isPotentiallyUnannotatedLib()) {
            if (!methodBinding.returnType.hasNullTypeAnnotations() && (methodBinding.tagBits & 108086391056891904L) == 0L && methodBinding.parameterNonNullness == null) {
               TypeBinding[] var23 = parameters;
               int var22 = parameters.length;

               for(startIndex = 0; startIndex < var22; ++startIndex) {
                  TypeBinding parameter = var23[startIndex];
                  if (parameter.hasNullTypeAnnotations()) {
                     this.externalAnnotationStatus = BinaryTypeBinding.ExternalAnnotationStatus.TYPE_IS_ANNOTATED;
                     break;
                  }
               }
            } else {
               this.externalAnnotationStatus = BinaryTypeBinding.ExternalAnnotationStatus.TYPE_IS_ANNOTATED;
            }
         }

      }
   }

   private void scanTypeForNullDefaultAnnotation(IBinaryType binaryType, PackageBinding packageBinding) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         char[][] nonNullByDefaultAnnotationName = this.environment.getNonNullByDefaultAnnotationName();
         if (nonNullByDefaultAnnotationName != null) {
            if (!CharOperation.equals(CharOperation.splitOn('/', binaryType.getName()), nonNullByDefaultAnnotationName)) {
               IBinaryAnnotation[] annotations = binaryType.getAnnotations();
               boolean isPackageInfo = CharOperation.equals(this.sourceName(), TypeConstants.PACKAGE_INFO_NAME);
               if (annotations != null) {
                  int nullness = 0;
                  int length = annotations.length;

                  for(int i = 0; i < length; ++i) {
                     char[] annotationTypeName = annotations[i].getTypeName();
                     if (annotationTypeName[0] == 'L') {
                        int typeBit = this.environment.getNullAnnotationBit(signature2qualifiedTypeName(annotationTypeName));
                        if (typeBit == 128) {
                           nullness |= getNonNullByDefaultValue(annotations[i], this.environment);
                        }
                     }
                  }

                  this.defaultNullness = nullness;
                  if (nullness != 0) {
                     if (isPackageInfo) {
                        packageBinding.setDefaultNullness(nullness);
                     }

                     return;
                  }
               }

               if (isPackageInfo) {
                  packageBinding.setDefaultNullness(0);
               } else {
                  ReferenceBinding enclosingTypeBinding = this.enclosingType;
                  if (enclosingTypeBinding == null || !this.setNullDefault(enclosingTypeBinding.getNullDefault())) {
                     if (packageBinding.getDefaultNullness() == 0 && !isPackageInfo && (this.typeBits & 224) == 0) {
                        ReferenceBinding packageInfo = packageBinding.getType(TypeConstants.PACKAGE_INFO_NAME, packageBinding.enclosingModule);
                        if (packageInfo == null) {
                           packageBinding.setDefaultNullness(0);
                        }
                     }

                     this.setNullDefault(packageBinding.getDefaultNullness());
                  }
               }
            }
         }
      }
   }

   boolean setNullDefault(int newNullDefault) {
      this.defaultNullness = newNullDefault;
      return newNullDefault != 0;
   }

   static int getNonNullByDefaultValue(IBinaryAnnotation annotation, LookupEnvironment environment) {
      char[] annotationTypeName = annotation.getTypeName();
      char[][] typeName = signature2qualifiedTypeName(annotationTypeName);
      IBinaryElementValuePair[] elementValuePairs = annotation.getElementValuePairs();
      int nullness;
      if (elementValuePairs != null && elementValuePairs.length != 0) {
         if (elementValuePairs.length <= 0) {
            return 2;
         } else {
            int nullness = 0;

            for(nullness = 0; nullness < elementValuePairs.length; ++nullness) {
               nullness |= Annotation.nullLocationBitsFromAnnotationValue(elementValuePairs[nullness].getValue());
            }

            return nullness;
         }
      } else {
         ReferenceBinding annotationType = environment.getType(typeName, environment.UnNamedModule);
         if (annotationType == null) {
            return 0;
         } else {
            if (annotationType.isUnresolvedType()) {
               annotationType = ((UnresolvedReferenceBinding)annotationType).resolve(environment, false);
            }

            nullness = evaluateTypeQualifierDefault(annotationType);
            if (nullness != 0) {
               return nullness;
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
   }

   public static int evaluateTypeQualifierDefault(ReferenceBinding annotationType) {
      AnnotationBinding[] var4;
      int var3 = (var4 = annotationType.getAnnotations()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         AnnotationBinding annotationOnAnnotation = var4[var2];
         int var10001 = annotationOnAnnotation.type.compoundName.length;
         if (CharOperation.equals(annotationOnAnnotation.getAnnotationType().compoundName[var10001 - 1], TYPE_QUALIFIER_DEFAULT)) {
            ElementValuePair[] pairs2 = annotationOnAnnotation.getElementValuePairs();
            if (pairs2 != null) {
               ElementValuePair[] var9 = pairs2;
               int var8 = pairs2.length;

               for(int var7 = 0; var7 < var8; ++var7) {
                  ElementValuePair elementValuePair = var9[var7];
                  char[] name = elementValuePair.getName();
                  if (CharOperation.equals(name, TypeConstants.VALUE)) {
                     int nullness = 0;
                     Object value = elementValuePair.getValue();
                     if (value instanceof Object[]) {
                        Object[] values = (Object[])value;
                        Object[] var17 = values;
                        int var16 = values.length;

                        for(int var15 = 0; var15 < var16; ++var15) {
                           Object value1 = var17[var15];
                           nullness |= Annotation.nullLocationBitsFromElementTypeAnnotationValue(value1);
                        }
                     } else {
                        nullness |= Annotation.nullLocationBitsFromElementTypeAnnotationValue(value);
                     }

                     return nullness;
                  }
               }
            }
         }
      }

      return 0;
   }

   static char[][] signature2qualifiedTypeName(char[] typeSignature) {
      return CharOperation.splitOn('/', typeSignature, 1, typeSignature.length - 1);
   }

   int getNullDefault() {
      return this.defaultNullness;
   }

   private void scanTypeForContainerAnnotation(IBinaryType binaryType, char[][][] missingTypeNames) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         IBinaryAnnotation[] annotations = binaryType.getAnnotations();
         if (annotations != null) {
            int length = annotations.length;

            for(int i = 0; i < length; ++i) {
               char[] annotationTypeName = annotations[i].getTypeName();
               if (CharOperation.equals(annotationTypeName, ConstantPool.JAVA_LANG_ANNOTATION_REPEATABLE)) {
                  IBinaryElementValuePair[] elementValuePairs = annotations[i].getElementValuePairs();
                  if (elementValuePairs != null && elementValuePairs.length == 1) {
                     Object value = elementValuePairs[0].getValue();
                     if (value instanceof ClassSignature) {
                        this.containerAnnotationType = (ReferenceBinding)this.environment.getTypeFromSignature(((ClassSignature)value).getTypeName(), 0, -1, false, (TypeBinding)null, missingTypeNames, ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER);
                     }
                  }
                  break;
               }
            }
         }

      }
   }

   public ReferenceBinding superclass() {
      if (!this.isPrototype()) {
         return this.superclass = this.prototype.superclass();
      } else if ((this.tagBits & 33554432L) == 0L) {
         return this.superclass;
      } else {
         this.superclass = (ReferenceBinding)resolveType(this.superclass, this.environment, true);
         this.tagBits &= -33554433L;
         if (this.superclass.problemId() == 1) {
            this.tagBits |= 131072L;
         } else {
            boolean wasToleratingMissingTypeProcessingAnnotations = this.environment.mayTolerateMissingType;
            this.environment.mayTolerateMissingType = true;

            try {
               this.superclass.superclass();
               this.superclass.superInterfaces();
            } finally {
               this.environment.mayTolerateMissingType = wasToleratingMissingTypeProcessingAnnotations;
            }
         }

         this.typeBits |= this.superclass.typeBits & 1811;
         if ((this.typeBits & 3) != 0) {
            this.typeBits |= this.applyCloseableClassWhitelists();
         }

         return this.superclass;
      }
   }

   public ReferenceBinding[] superInterfaces() {
      if (!this.isPrototype()) {
         return this.superInterfaces = this.prototype.superInterfaces();
      } else if ((this.tagBits & 67108864L) == 0L) {
         return this.superInterfaces;
      } else {
         int i = this.superInterfaces.length;

         while(true) {
            while(true) {
               --i;
               if (i < 0) {
                  this.tagBits &= -67108865L;
                  return this.superInterfaces;
               }

               this.superInterfaces[i] = (ReferenceBinding)resolveType(this.superInterfaces[i], this.environment, true);
               if (this.superInterfaces[i].problemId() == 1) {
                  this.tagBits |= 131072L;
                  break;
               }

               boolean wasToleratingMissingTypeProcessingAnnotations = this.environment.mayTolerateMissingType;
               this.environment.mayTolerateMissingType = true;

               try {
                  this.superInterfaces[i].superclass();
                  if (this.superInterfaces[i].isParameterizedType()) {
                     ReferenceBinding superType = this.superInterfaces[i].actualType();
                     if (TypeBinding.equalsEquals(superType, this)) {
                        this.tagBits |= 131072L;
                        continue;
                     }
                  }

                  this.superInterfaces[i].superInterfaces();
                  break;
               } finally {
                  this.environment.mayTolerateMissingType = wasToleratingMissingTypeProcessingAnnotations;
               }
            }

            this.typeBits |= this.superInterfaces[i].typeBits & 1811;
            if ((this.typeBits & 3) != 0) {
               this.typeBits |= this.applyCloseableInterfaceWhitelists();
            }
         }
      }
   }

   public TypeVariableBinding[] typeVariables() {
      if (!this.isPrototype()) {
         return this.typeVariables = this.prototype.typeVariables();
      } else if ((this.tagBits & 16777216L) == 0L) {
         return this.typeVariables;
      } else {
         int i = this.typeVariables.length;

         while(true) {
            --i;
            if (i < 0) {
               this.tagBits &= -16777217L;
               return this.typeVariables;
            }

            this.typeVariables[i].resolve();
         }
      }
   }

   public String toString() {
      if (this.hasTypeAnnotations()) {
         return this.annotatedDebugName();
      } else {
         StringBuffer buffer = new StringBuffer();
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

         if (this.enclosingType != null) {
            buffer.append("\n\tenclosing type : ");
            buffer.append(this.enclosingType.debugName());
         }

         if (this.fields != null) {
            if (this.fields != Binding.NO_FIELDS) {
               buffer.append("\n/*   fields   */");
               i = 0;

               for(length = this.fields.length; i < length; ++i) {
                  buffer.append(this.fields[i] != null ? "\n" + this.fields[i].toString() : "\nNULL FIELD");
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
                  buffer.append(this.methods[i] != null ? "\n" + this.methods[i].toString() : "\nNULL METHOD");
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
                  buffer.append(this.memberTypes[i] != null ? "\n" + this.memberTypes[i].toString() : "\nNULL TYPE");
               }
            }
         } else {
            buffer.append("NULL MEMBER TYPES");
         }

         buffer.append("\n\n\n");
         return buffer.toString();
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

   MethodBinding[] unResolvedMethods() {
      return !this.isPrototype() ? this.prototype.unResolvedMethods() : this.methods;
   }

   public FieldBinding[] unResolvedFields() {
      return !this.isPrototype() ? this.prototype.unResolvedFields() : this.fields;
   }

   public ModuleBinding module() {
      return !this.isPrototype() ? this.prototype.module : this.module;
   }

   public static enum ExternalAnnotationStatus {
      FROM_SOURCE,
      NOT_EEA_CONFIGURED,
      NO_EEA_FILE,
      TYPE_IS_ANNOTATED;

      // $FF: synthetic field
      private static int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$BinaryTypeBinding$ExternalAnnotationStatus;

      public boolean isPotentiallyUnannotatedLib() {
         switch (this) {
            case FROM_SOURCE:
            case TYPE_IS_ANNOTATED:
               return false;
            case NOT_EEA_CONFIGURED:
            case NO_EEA_FILE:
            default:
               return true;
         }
      }

      // $FF: synthetic method
      static int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$BinaryTypeBinding$ExternalAnnotationStatus() {
         int[] var10000 = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$BinaryTypeBinding$ExternalAnnotationStatus;
         if (var10000 != null) {
            return var10000;
         } else {
            int[] var0 = new int[values().length];

            try {
               var0[FROM_SOURCE.ordinal()] = 1;
            } catch (NoSuchFieldError var4) {
            }

            try {
               var0[NOT_EEA_CONFIGURED.ordinal()] = 2;
            } catch (NoSuchFieldError var3) {
            }

            try {
               var0[NO_EEA_FILE.ordinal()] = 3;
            } catch (NoSuchFieldError var2) {
            }

            try {
               var0[TYPE_IS_ANNOTATED.ordinal()] = 4;
            } catch (NoSuchFieldError var1) {
            }

            $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$BinaryTypeBinding$ExternalAnnotationStatus = var0;
            return var0;
         }
      }
   }
}

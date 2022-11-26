package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.env.EnumConstantSignature;
import com.bea.core.repackaged.jdt.internal.compiler.impl.BooleanConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.IrritantSet;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ArrayBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BinaryTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ElementValuePair;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import java.util.Stack;

public abstract class Annotation extends Expression {
   Annotation persistibleAnnotation = this;
   static final MemberValuePair[] NoValuePairs = new MemberValuePair[0];
   static final int[] TYPE_PATH_ELEMENT_ARRAY = new int[2];
   static final int[] TYPE_PATH_INNER_TYPE = new int[]{1, 0};
   static final int[] TYPE_PATH_ANNOTATION_ON_WILDCARD_BOUND = new int[]{2, 0};
   public int declarationSourceEnd;
   public Binding recipient;
   public TypeReference type;
   protected AnnotationBinding compilerAnnotation = null;

   public static int[] getLocations(Expression reference, Annotation annotation) {
      if (reference == null) {
         return null;
      } else {
         class LocationCollector extends ASTVisitor {
            Stack typePathEntries = new Stack();
            Annotation searchedAnnotation;
            boolean continueSearch = true;

            public LocationCollector(Annotation currentAnnotation) {
               this.searchedAnnotation = currentAnnotation;
            }

            private int[] computeNestingDepth(TypeReference typeReference) {
               TypeBinding type = typeReference.resolvedType == null ? null : typeReference.resolvedType.leafComponentType();
               int[] nestingDepths = new int[typeReference.getAnnotatableLevels()];
               if (type != null && ((TypeBinding)type).isNestedType()) {
                  int depth = 0;

                  for(TypeBinding currentType = type; currentType != null; currentType = ((TypeBinding)currentType).enclosingType()) {
                     depth += ((TypeBinding)currentType).isStatic() ? 0 : 1;
                  }

                  for(int counter = nestingDepths.length - 1; type != null && counter >= 0; type = ((TypeBinding)type).enclosingType()) {
                     nestingDepths[counter--] = depth;
                     depth -= ((TypeBinding)type).isStatic() ? 0 : 1;
                  }
               }

               return nestingDepths;
            }

            private void inspectAnnotations(Annotation[] annotations) {
               int i = 0;

               for(int length = annotations == null ? 0 : annotations.length; this.continueSearch && i < length; ++i) {
                  if (annotations[i] == this.searchedAnnotation) {
                     this.continueSearch = false;
                  }
               }

            }

            private void inspectArrayDimensions(Annotation[][] annotationsOnDimensions, int dimensions) {
               for(int i = 0; this.continueSearch && i < dimensions; ++i) {
                  Annotation[] annotations = annotationsOnDimensions == null ? null : annotationsOnDimensions[i];
                  this.inspectAnnotations(annotations);
                  if (!this.continueSearch) {
                     return;
                  }

                  this.typePathEntries.push(Annotation.TYPE_PATH_ELEMENT_ARRAY);
               }

            }

            private void inspectTypeArguments(TypeReference[] typeReferences) {
               int i = 0;

               for(int length = typeReferences == null ? 0 : typeReferences.length; this.continueSearch && i < length; ++i) {
                  int size = this.typePathEntries.size();
                  this.typePathEntries.add(new int[]{3, i});
                  typeReferences[i].traverse(this, (BlockScope)null);
                  if (!this.continueSearch) {
                     return;
                  }

                  this.typePathEntries.setSize(size);
               }

            }

            public boolean visit(TypeReference typeReference, BlockScope scope) {
               if (this.continueSearch) {
                  this.inspectArrayDimensions(typeReference.getAnnotationsOnDimensions(), typeReference.dimensions());
                  if (this.continueSearch) {
                     int[] nestingDepths = this.computeNestingDepth(typeReference);
                     Annotation[][] annotations = typeReference.annotations;
                     TypeReference[][] typeArguments = typeReference.getTypeArguments();
                     int levels = typeReference.getAnnotatableLevels();
                     int size = this.typePathEntries.size();

                     for(int i = levels - 1; this.continueSearch && i >= 0; --i) {
                        this.typePathEntries.setSize(size);
                        int j = 0;

                        for(int depth = nestingDepths[i]; j < depth; ++j) {
                           this.typePathEntries.add(Annotation.TYPE_PATH_INNER_TYPE);
                        }

                        if (annotations != null) {
                           this.inspectAnnotations(annotations[i]);
                        }

                        if (this.continueSearch && typeArguments != null) {
                           this.inspectTypeArguments(typeArguments[i]);
                        }
                     }
                  }
               }

               return false;
            }

            public boolean visit(SingleTypeReference typeReference, BlockScope scope) {
               return this.visit((TypeReference)typeReference, scope);
            }

            public boolean visit(ArrayTypeReference typeReference, BlockScope scope) {
               return this.visit((TypeReference)typeReference, scope);
            }

            public boolean visit(ParameterizedSingleTypeReference typeReference, BlockScope scope) {
               return this.visit((TypeReference)typeReference, scope);
            }

            public boolean visit(QualifiedTypeReference typeReference, BlockScope scope) {
               return this.visit((TypeReference)typeReference, scope);
            }

            public boolean visit(ArrayQualifiedTypeReference typeReference, BlockScope scope) {
               return this.visit((TypeReference)typeReference, scope);
            }

            public boolean visit(ParameterizedQualifiedTypeReference typeReference, BlockScope scope) {
               return this.visit((TypeReference)typeReference, scope);
            }

            public boolean visit(Wildcard typeReference, BlockScope scope) {
               this.visit((TypeReference)typeReference, scope);
               if (this.continueSearch) {
                  TypeReference bound = typeReference.bound;
                  if (bound != null) {
                     int size = this.typePathEntries.size();
                     this.typePathEntries.push(Annotation.TYPE_PATH_ANNOTATION_ON_WILDCARD_BOUND);
                     bound.traverse(this, (BlockScope)scope);
                     if (this.continueSearch) {
                        this.typePathEntries.setSize(size);
                     }
                  }
               }

               return false;
            }

            public boolean visit(ArrayAllocationExpression allocationExpression, BlockScope scope) {
               if (this.continueSearch) {
                  this.inspectArrayDimensions(allocationExpression.getAnnotationsOnDimensions(), allocationExpression.dimensions.length);
                  if (this.continueSearch) {
                     allocationExpression.type.traverse(this, (BlockScope)scope);
                  }

                  if (this.continueSearch) {
                     throw new IllegalStateException();
                  }
               }

               return false;
            }

            public String toString() {
               StringBuffer buffer = new StringBuffer();
               buffer.append("search location for ").append(this.searchedAnnotation).append("\ncurrent type_path entries : ");
               int i = 0;

               for(int maxi = this.typePathEntries.size(); i < maxi; ++i) {
                  int[] typePathEntry = (int[])this.typePathEntries.get(i);
                  buffer.append('(').append(typePathEntry[0]).append(',').append(typePathEntry[1]).append(')');
               }

               return String.valueOf(buffer);
            }
         }

         LocationCollector collector = new LocationCollector(annotation);
         reference.traverse(collector, (BlockScope)null);
         if (collector.typePathEntries.isEmpty()) {
            return null;
         } else {
            int size = collector.typePathEntries.size();
            int[] result = new int[size * 2];
            int offset = 0;

            for(int i = 0; i < size; ++i) {
               int[] pathElement = (int[])collector.typePathEntries.get(i);
               result[offset++] = pathElement[0];
               result[offset++] = pathElement[1];
            }

            return result;
         }
      }
   }

   public static long getRetentionPolicy(char[] policyName) {
      if (policyName != null && policyName.length != 0) {
         switch (policyName[0]) {
            case 'C':
               if (CharOperation.equals(policyName, TypeConstants.UPPER_CLASS)) {
                  return 35184372088832L;
               }
               break;
            case 'R':
               if (CharOperation.equals(policyName, TypeConstants.UPPER_RUNTIME)) {
                  return 52776558133248L;
               }
               break;
            case 'S':
               if (CharOperation.equals(policyName, TypeConstants.UPPER_SOURCE)) {
                  return 17592186044416L;
               }
         }

         return 0L;
      } else {
         return 0L;
      }
   }

   public static long getTargetElementType(char[] elementName) {
      if (elementName != null && elementName.length != 0) {
         switch (elementName[0]) {
            case 'A':
               if (CharOperation.equals(elementName, TypeConstants.UPPER_ANNOTATION_TYPE)) {
                  return 4398046511104L;
               }
               break;
            case 'C':
               if (CharOperation.equals(elementName, TypeConstants.UPPER_CONSTRUCTOR)) {
                  return 1099511627776L;
               }
               break;
            case 'F':
               if (CharOperation.equals(elementName, TypeConstants.UPPER_FIELD)) {
                  return 137438953472L;
               }
               break;
            case 'L':
               if (CharOperation.equals(elementName, TypeConstants.UPPER_LOCAL_VARIABLE)) {
                  return 2199023255552L;
               }
               break;
            case 'M':
               if (CharOperation.equals(elementName, TypeConstants.UPPER_METHOD)) {
                  return 274877906944L;
               }

               if (CharOperation.equals(elementName, TypeConstants.UPPER_MODULE)) {
                  return 2305843009213693952L;
               }
               break;
            case 'P':
               if (CharOperation.equals(elementName, TypeConstants.UPPER_PARAMETER)) {
                  return 549755813888L;
               }

               if (CharOperation.equals(elementName, TypeConstants.UPPER_PACKAGE)) {
                  return 8796093022208L;
               }
               break;
            case 'T':
               if (CharOperation.equals(elementName, TypeConstants.TYPE)) {
                  return 68719476736L;
               }

               if (CharOperation.equals(elementName, TypeConstants.TYPE_USE_TARGET)) {
                  return 9007199254740992L;
               }

               if (CharOperation.equals(elementName, TypeConstants.TYPE_PARAMETER_TARGET)) {
                  return 18014398509481984L;
               }
         }

         return 0L;
      } else {
         return 0L;
      }
   }

   public ElementValuePair[] computeElementValuePairs() {
      return Binding.NO_ELEMENT_VALUE_PAIRS;
   }

   private long detectStandardAnnotation(Scope scope, ReferenceBinding annotationType, MemberValuePair valueAttribute) {
      long tagBits = 0L;
      Expression expr;
      FieldBinding field;
      switch (annotationType.id) {
         case 44:
            tagBits |= 70368744177664L;
            if (scope.compilerOptions().complianceLevel >= 3473408L) {
               MemberValuePair[] var19;
               int var18 = (var19 = this.memberValuePairs()).length;

               for(int var17 = 0; var17 < var18; ++var17) {
                  MemberValuePair memberValuePair = var19[var17];
                  if (CharOperation.equals(memberValuePair.name, TypeConstants.FOR_REMOVAL)) {
                     if (memberValuePair.value instanceof TrueLiteral) {
                        tagBits |= 4611686018427387904L;
                     }
                     break;
                  }
               }
            }
            break;
         case 45:
            tagBits |= 140737488355328L;
            break;
         case 46:
            tagBits |= 281474976710656L;
            break;
         case 47:
            tagBits |= 562949953421312L;
            break;
         case 48:
            if (valueAttribute != null) {
               expr = valueAttribute.value;
               if ((expr.bits & 3) == 1 && expr instanceof Reference) {
                  field = ((Reference)expr).fieldBinding();
                  if (field != null && field.declaringClass.id == 51) {
                     tagBits |= getRetentionPolicy(field.name);
                  }
               }
            }
            break;
         case 49:
            tagBits |= 1125899906842624L;
            break;
         case 50:
            tagBits |= 34359738368L;
            if (valueAttribute != null) {
               expr = valueAttribute.value;
               if (expr instanceof ArrayInitializer) {
                  ArrayInitializer initializer = (ArrayInitializer)expr;
                  Expression[] expressions = initializer.expressions;
                  if (expressions != null) {
                     int i = 0;

                     for(int length = expressions.length; i < length; ++i) {
                        Expression initExpr = expressions[i];
                        if ((initExpr.bits & 3) == 1) {
                           FieldBinding field = ((Reference)initExpr).fieldBinding();
                           if (field != null && field.declaringClass.id == 52) {
                              long element = getTargetElementType(field.name);
                              if ((tagBits & element) != 0L) {
                                 scope.problemReporter().duplicateTargetInTargetAnnotation(annotationType, (NameReference)initExpr);
                              } else {
                                 tagBits |= element;
                              }
                           }
                        }
                     }
                  }
               } else if ((expr.bits & 3) == 1) {
                  field = ((Reference)expr).fieldBinding();
                  if (field != null && field.declaringClass.id == 52) {
                     tagBits |= getTargetElementType(field.name);
                  }
               }
            }
            break;
         case 60:
            tagBits |= 2251799813685248L;
            break;
         case 61:
            tagBits |= 4503599627370496L;
            break;
         case 77:
            tagBits |= 576460752303423488L;
            break;
         case 90:
            tagBits |= 1152921504606846976L;
      }

      if (annotationType.hasNullBit(64)) {
         tagBits |= 36028797018963968L;
      } else if (annotationType.hasNullBit(32)) {
         tagBits |= 72057594037927936L;
      } else if (annotationType.hasNullBit(128)) {
         tagBits |= this.determineNonNullByDefaultTagBits(annotationType, valueAttribute);
      }

      return tagBits;
   }

   private long determineNonNullByDefaultTagBits(ReferenceBinding annotationType, MemberValuePair valueAttribute) {
      long tagBits = 0L;
      Object value = null;
      if (valueAttribute != null) {
         if (valueAttribute.compilerElementPair != null) {
            value = valueAttribute.compilerElementPair.value;
         }
      } else {
         MethodBinding[] methods = annotationType.methods();
         if (methods != null && methods.length == 1) {
            value = methods[0].getDefaultValue();
         } else {
            tagBits |= 56L;
         }
      }

      if (value instanceof BooleanConstant) {
         tagBits |= (long)(((BooleanConstant)value).booleanValue() ? 56 : 2);
      } else if (value != null) {
         tagBits |= (long)nullLocationBitsFromAnnotationValue(value);
      } else {
         int result = BinaryTypeBinding.evaluateTypeQualifierDefault(annotationType);
         if (result != 0) {
            return (long)result;
         }
      }

      return tagBits;
   }

   public static int nullLocationBitsFromAnnotationValue(Object value) {
      if (!(value instanceof Object[])) {
         return evaluateDefaultNullnessLocation(value);
      } else if (((Object[])value).length == 0) {
         return 2;
      } else {
         int bits = 0;
         Object[] var5;
         int var4 = (var5 = (Object[])value).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            Object single = var5[var3];
            bits |= evaluateDefaultNullnessLocation(single);
         }

         return bits;
      }
   }

   private static int evaluateDefaultNullnessLocation(Object value) {
      char[] name = null;
      if (value instanceof FieldBinding) {
         name = ((FieldBinding)value).name;
      } else if (value instanceof EnumConstantSignature) {
         name = ((EnumConstantSignature)value).getEnumConstantName();
      } else if (value instanceof ElementValuePair.UnresolvedEnumConstant) {
         name = ((ElementValuePair.UnresolvedEnumConstant)value).getEnumConstantName();
      } else if (value instanceof BooleanConstant) {
         return ((BooleanConstant)value).booleanValue() ? 56 : 2;
      }

      if (name != null) {
         switch (name.length) {
            case 5:
               if (CharOperation.equals(name, TypeConstants.DEFAULT_LOCATION__FIELD)) {
                  return 32;
               }
            case 6:
            case 7:
            case 8:
            case 12:
            default:
               break;
            case 9:
               if (CharOperation.equals(name, TypeConstants.DEFAULT_LOCATION__PARAMETER)) {
                  return 8;
               }
               break;
            case 10:
               if (CharOperation.equals(name, TypeConstants.DEFAULT_LOCATION__TYPE_BOUND)) {
                  return 256;
               }
               break;
            case 11:
               if (CharOperation.equals(name, TypeConstants.DEFAULT_LOCATION__RETURN_TYPE)) {
                  return 16;
               }
               break;
            case 13:
               if (CharOperation.equals(name, TypeConstants.DEFAULT_LOCATION__TYPE_ARGUMENT)) {
                  return 64;
               }
               break;
            case 14:
               if (CharOperation.equals(name, TypeConstants.DEFAULT_LOCATION__TYPE_PARAMETER)) {
                  return 128;
               }

               if (CharOperation.equals(name, TypeConstants.DEFAULT_LOCATION__ARRAY_CONTENTS)) {
                  return 512;
               }
         }
      }

      return 0;
   }

   public static int nullLocationBitsFromElementTypeAnnotationValue(Object value) {
      if (!(value instanceof Object[])) {
         return evaluateElementTypeNullnessLocation(value);
      } else if (((Object[])value).length == 0) {
         return 2;
      } else {
         int bits = 0;
         Object[] var5;
         int var4 = (var5 = (Object[])value).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            Object single = var5[var3];
            bits |= evaluateElementTypeNullnessLocation(single);
         }

         return bits;
      }
   }

   private static int evaluateElementTypeNullnessLocation(Object value) {
      char[] name = null;
      if (value instanceof FieldBinding) {
         name = ((FieldBinding)value).name;
      } else if (value instanceof EnumConstantSignature) {
         name = ((EnumConstantSignature)value).getEnumConstantName();
      } else if (value instanceof ElementValuePair.UnresolvedEnumConstant) {
         name = ((ElementValuePair.UnresolvedEnumConstant)value).getEnumConstantName();
      }

      if (name != null) {
         switch (name.length) {
            case 5:
               if (CharOperation.equals(name, TypeConstants.UPPER_FIELD)) {
                  return 32;
               }
               break;
            case 6:
               if (CharOperation.equals(name, TypeConstants.UPPER_METHOD)) {
                  return 16;
               }
            case 7:
            case 8:
            default:
               break;
            case 9:
               if (CharOperation.equals(name, TypeConstants.UPPER_PARAMETER)) {
                  return 8;
               }
         }
      }

      return 0;
   }

   static String getRetentionName(long tagBits) {
      if ((tagBits & 52776558133248L) == 52776558133248L) {
         return new String(UPPER_RUNTIME);
      } else {
         return (tagBits & 17592186044416L) != 0L ? new String(UPPER_SOURCE) : new String(TypeConstants.UPPER_CLASS);
      }
   }

   private static long getAnnotationRetention(ReferenceBinding binding) {
      long retention = binding.getAnnotationTagBits() & 52776558133248L;
      return retention != 0L ? retention : 35184372088832L;
   }

   public void checkRepeatableMetaAnnotation(BlockScope scope) {
      ReferenceBinding repeatableAnnotationType = (ReferenceBinding)this.recipient;
      MemberValuePair[] valuePairs = this.memberValuePairs();
      if (valuePairs != null && valuePairs.length == 1) {
         Object value = valuePairs[0].compilerElementPair.value;
         if (value instanceof ReferenceBinding) {
            ReferenceBinding containerAnnotationType = (ReferenceBinding)value;
            if (containerAnnotationType.isAnnotationType()) {
               repeatableAnnotationType.setContainerAnnotationType(containerAnnotationType);
               checkContainerAnnotationType(valuePairs[0], scope, containerAnnotationType, repeatableAnnotationType, false);
            }
         }
      }
   }

   public static void checkContainerAnnotationType(ASTNode culpritNode, BlockScope scope, ReferenceBinding containerAnnotationType, ReferenceBinding repeatableAnnotationType, boolean useSite) {
      MethodBinding[] annotationMethods = containerAnnotationType.methods();
      boolean sawValue = false;
      int i = 0;

      for(int length = annotationMethods.length; i < length; ++i) {
         MethodBinding method = annotationMethods[i];
         if (CharOperation.equals(method.selector, TypeConstants.VALUE)) {
            sawValue = true;
            if (method.returnType.isArrayType() && method.returnType.dimensions() == 1) {
               ArrayBinding array = (ArrayBinding)method.returnType;
               if (TypeBinding.equalsEquals(array.elementsType(), repeatableAnnotationType)) {
                  continue;
               }
            }

            repeatableAnnotationType.tagAsHavingDefectiveContainerType();
            scope.problemReporter().containerAnnotationTypeHasWrongValueType(culpritNode, containerAnnotationType, repeatableAnnotationType, method.returnType);
         } else if ((method.modifiers & 131072) == 0) {
            repeatableAnnotationType.tagAsHavingDefectiveContainerType();
            scope.problemReporter().containerAnnotationTypeHasNonDefaultMembers(culpritNode, containerAnnotationType, method.selector);
         }
      }

      if (!sawValue) {
         repeatableAnnotationType.tagAsHavingDefectiveContainerType();
         scope.problemReporter().containerAnnotationTypeMustHaveValue(culpritNode, containerAnnotationType);
      }

      if (useSite) {
         checkContainingAnnotationTargetAtUse((Annotation)culpritNode, scope, containerAnnotationType, repeatableAnnotationType);
      } else {
         checkContainerAnnotationTypeTarget(culpritNode, scope, containerAnnotationType, repeatableAnnotationType);
      }

      long annotationTypeBits = getAnnotationRetention(repeatableAnnotationType);
      long containerTypeBits = getAnnotationRetention(containerAnnotationType);
      if (containerTypeBits < annotationTypeBits) {
         repeatableAnnotationType.tagAsHavingDefectiveContainerType();
         scope.problemReporter().containerAnnotationTypeHasShorterRetention(culpritNode, repeatableAnnotationType, getRetentionName(annotationTypeBits), containerAnnotationType, getRetentionName(containerTypeBits));
      }

      if ((repeatableAnnotationType.getAnnotationTagBits() & 140737488355328L) != 0L && (containerAnnotationType.getAnnotationTagBits() & 140737488355328L) == 0L) {
         repeatableAnnotationType.tagAsHavingDefectiveContainerType();
         scope.problemReporter().repeatableAnnotationTypeIsDocumented(culpritNode, repeatableAnnotationType, containerAnnotationType);
      }

      if ((repeatableAnnotationType.getAnnotationTagBits() & 281474976710656L) != 0L && (containerAnnotationType.getAnnotationTagBits() & 281474976710656L) == 0L) {
         repeatableAnnotationType.tagAsHavingDefectiveContainerType();
         scope.problemReporter().repeatableAnnotationTypeIsInherited(culpritNode, repeatableAnnotationType, containerAnnotationType);
      }

   }

   private static void checkContainerAnnotationTypeTarget(ASTNode culpritNode, Scope scope, ReferenceBinding containerType, ReferenceBinding repeatableAnnotationType) {
      long tagBits = repeatableAnnotationType.getAnnotationTagBits();
      if ((tagBits & 2332882164804222976L) == 0L) {
         tagBits = 17523466567680L;
      }

      long containerAnnotationTypeTypeTagBits = containerType.getAnnotationTagBits();
      if ((containerAnnotationTypeTypeTagBits & 2332882164804222976L) == 0L) {
         containerAnnotationTypeTypeTagBits = 17523466567680L;
      }

      final long targets = tagBits & 2332882164804222976L;
      final long containerAnnotationTypeTargets = containerAnnotationTypeTypeTagBits & 2332882164804222976L;
      if ((containerAnnotationTypeTargets & ~targets) != 0L) {
         class MissingTargetBuilder {
            StringBuffer targetBuffer = new StringBuffer();

            void check(long targetMask, char[] targetName) {
               if ((containerAnnotationTypeTargets & targetMask & ~targets) != 0L) {
                  if (targetMask == 68719476736L && (targets & 9007199254740992L) != 0L) {
                     return;
                  }

                  this.add(targetName);
               }

            }

            void checkAnnotationType(char[] targetName) {
               if ((containerAnnotationTypeTargets & 4398046511104L) != 0L && (targets & 4466765987840L) == 0L) {
                  this.add(targetName);
               }

            }

            private void add(char[] targetName) {
               if (this.targetBuffer.length() != 0) {
                  this.targetBuffer.append(", ");
               }

               this.targetBuffer.append(targetName);
            }

            public String toString() {
               return this.targetBuffer.toString();
            }

            public boolean hasError() {
               return this.targetBuffer.length() != 0;
            }
         }

         MissingTargetBuilder builder = new MissingTargetBuilder();
         builder.check(68719476736L, TypeConstants.TYPE);
         builder.check(137438953472L, TypeConstants.UPPER_FIELD);
         builder.check(274877906944L, TypeConstants.UPPER_METHOD);
         builder.check(549755813888L, TypeConstants.UPPER_PARAMETER);
         builder.check(1099511627776L, TypeConstants.UPPER_CONSTRUCTOR);
         builder.check(2199023255552L, TypeConstants.UPPER_LOCAL_VARIABLE);
         builder.checkAnnotationType(TypeConstants.UPPER_ANNOTATION_TYPE);
         builder.check(8796093022208L, TypeConstants.UPPER_PACKAGE);
         builder.check(18014398509481984L, TypeConstants.TYPE_PARAMETER_TARGET);
         builder.check(9007199254740992L, TypeConstants.TYPE_USE_TARGET);
         builder.check(2305843009213693952L, TypeConstants.UPPER_MODULE);
         if (builder.hasError()) {
            repeatableAnnotationType.tagAsHavingDefectiveContainerType();
            scope.problemReporter().repeatableAnnotationTypeTargetMismatch(culpritNode, repeatableAnnotationType, containerType, builder.toString());
         }
      }

   }

   public static void checkContainingAnnotationTargetAtUse(Annotation repeatingAnnotation, BlockScope scope, TypeBinding containerAnnotationType, TypeBinding repeatingAnnotationType) {
      if (repeatingAnnotationType.isValidBinding()) {
         if (isAnnotationTargetAllowed(repeatingAnnotation, scope, containerAnnotationType, repeatingAnnotation.recipient.kind()) != Annotation.AnnotationTargetAllowed.YES) {
            scope.problemReporter().disallowedTargetForContainerAnnotation(repeatingAnnotation, containerAnnotationType);
         }

      }
   }

   public AnnotationBinding getCompilerAnnotation() {
      return this.compilerAnnotation;
   }

   public boolean isRuntimeInvisible() {
      TypeBinding annotationBinding = this.resolvedType;
      if (annotationBinding == null) {
         return false;
      } else {
         long metaTagBits = annotationBinding.getAnnotationTagBits();
         if ((metaTagBits & 27021597764222976L) != 0L && (metaTagBits & 17523466567680L) == 0L) {
            return false;
         } else if ((metaTagBits & 52776558133248L) == 0L) {
            return true;
         } else {
            return (metaTagBits & 52776558133248L) == 35184372088832L;
         }
      }
   }

   public boolean isRuntimeTypeInvisible() {
      TypeBinding annotationBinding = this.resolvedType;
      if (annotationBinding == null) {
         return false;
      } else {
         long metaTagBits = annotationBinding.getAnnotationTagBits();
         if ((metaTagBits & 2332882164804222976L) == 0L) {
            return false;
         } else if ((metaTagBits & 27021597764222976L) == 0L) {
            return false;
         } else if ((metaTagBits & 52776558133248L) == 0L) {
            return true;
         } else {
            return (metaTagBits & 52776558133248L) == 35184372088832L;
         }
      }
   }

   public boolean isRuntimeTypeVisible() {
      TypeBinding annotationBinding = this.resolvedType;
      if (annotationBinding == null) {
         return false;
      } else {
         long metaTagBits = annotationBinding.getAnnotationTagBits();
         if ((metaTagBits & 2332882164804222976L) == 0L) {
            return false;
         } else if ((metaTagBits & 27021597764222976L) == 0L) {
            return false;
         } else if ((metaTagBits & 52776558133248L) == 0L) {
            return false;
         } else {
            return (metaTagBits & 52776558133248L) == 52776558133248L;
         }
      }
   }

   public boolean isRuntimeVisible() {
      TypeBinding annotationBinding = this.resolvedType;
      if (annotationBinding == null) {
         return false;
      } else {
         long metaTagBits = annotationBinding.getAnnotationTagBits();
         if ((metaTagBits & 27021597764222976L) != 0L && (metaTagBits & 17523466567680L) == 0L) {
            return false;
         } else if ((metaTagBits & 52776558133248L) == 0L) {
            return false;
         } else {
            return (metaTagBits & 52776558133248L) == 52776558133248L;
         }
      }
   }

   public abstract MemberValuePair[] memberValuePairs();

   public StringBuffer printExpression(int indent, StringBuffer output) {
      output.append('@');
      this.type.printExpression(0, output);
      return output;
   }

   public void recordSuppressWarnings(Scope scope, int startSuppresss, int endSuppress, boolean isSuppressingWarnings) {
      IrritantSet suppressWarningIrritants = null;
      MemberValuePair[] pairs = this.memberValuePairs();
      int i = 0;

      for(int length = pairs.length; i < length; ++i) {
         MemberValuePair pair = pairs[i];
         if (CharOperation.equals(pair.name, TypeConstants.VALUE)) {
            Expression value = pair.value;
            if (value instanceof ArrayInitializer) {
               ArrayInitializer initializer = (ArrayInitializer)value;
               Expression[] inits = initializer.expressions;
               if (inits != null) {
                  int j = 0;

                  for(int initsLength = inits.length; j < initsLength; ++j) {
                     Constant cst = inits[j].constant;
                     if (cst != Constant.NotAConstant && cst.typeID() == 11) {
                        IrritantSet irritants = CompilerOptions.warningTokenToIrritants(cst.stringValue());
                        if (irritants != null) {
                           if (suppressWarningIrritants == null) {
                              suppressWarningIrritants = new IrritantSet(irritants);
                           } else if (suppressWarningIrritants.set(irritants) == null) {
                              scope.problemReporter().unusedWarningToken(inits[j]);
                           }
                        } else {
                           scope.problemReporter().unhandledWarningToken(inits[j]);
                        }
                     }
                  }
               }
            } else {
               Constant cst = value.constant;
               if (cst != Constant.NotAConstant && cst.typeID() == 11) {
                  IrritantSet irritants = CompilerOptions.warningTokenToIrritants(cst.stringValue());
                  if (irritants != null) {
                     suppressWarningIrritants = new IrritantSet(irritants);
                  } else {
                     scope.problemReporter().unhandledWarningToken(value);
                  }
               }
            }
            break;
         }
      }

      if (isSuppressingWarnings && suppressWarningIrritants != null) {
         scope.referenceCompilationUnit().recordSuppressWarnings(suppressWarningIrritants, this, startSuppresss, endSuppress, scope.referenceContext());
      }

   }

   public TypeBinding resolveType(BlockScope scope) {
      if (this.compilerAnnotation != null) {
         return this.resolvedType;
      } else {
         this.constant = Constant.NotAConstant;
         TypeBinding typeBinding;
         if (this.resolvedType == null) {
            typeBinding = this.type.resolveType(scope);
            if (typeBinding == null) {
               this.resolvedType = new ProblemReferenceBinding(this.type.getTypeName(), (ReferenceBinding)null, 1);
               return null;
            }

            this.resolvedType = typeBinding;
         } else {
            typeBinding = this.resolvedType;
         }

         if (!typeBinding.isAnnotationType() && typeBinding.isValidBinding()) {
            scope.problemReporter().notAnnotationType(typeBinding, this.type);
            return null;
         } else {
            ReferenceBinding annotationType = (ReferenceBinding)this.resolvedType;
            MethodBinding[] methods = annotationType.methods();
            MemberValuePair[] originalValuePairs = this.memberValuePairs();
            MemberValuePair valueAttribute = null;
            int pairsLength = originalValuePairs.length;
            MemberValuePair[] pairs;
            if (pairsLength > 0) {
               System.arraycopy(originalValuePairs, 0, pairs = new MemberValuePair[pairsLength], 0, pairsLength);
            } else {
               pairs = originalValuePairs;
            }

            int i = 0;

            label198:
            for(int requiredLength = methods.length; i < requiredLength; ++i) {
               MethodBinding method = methods[i];
               char[] selector = method.selector;
               boolean foundValue = false;

               for(int j = 0; j < pairsLength; ++j) {
                  MemberValuePair pair = pairs[j];
                  if (pair != null) {
                     char[] name = pair.name;
                     if (CharOperation.equals(name, selector)) {
                        if (valueAttribute == null && CharOperation.equals(name, TypeConstants.VALUE)) {
                           valueAttribute = pair;
                        }

                        pair.binding = method;
                        pair.resolveTypeExpecting(scope, method.returnType);
                        pairs[j] = null;
                        foundValue = true;
                        boolean foundDuplicate = false;

                        for(int k = j + 1; k < pairsLength; ++k) {
                           MemberValuePair otherPair = pairs[k];
                           if (otherPair != null && CharOperation.equals(otherPair.name, selector)) {
                              foundDuplicate = true;
                              scope.problemReporter().duplicateAnnotationValue(annotationType, otherPair);
                              otherPair.binding = method;
                              otherPair.resolveTypeExpecting(scope, method.returnType);
                              pairs[k] = null;
                           }
                        }

                        if (foundDuplicate) {
                           scope.problemReporter().duplicateAnnotationValue(annotationType, pair);
                           continue label198;
                        }
                     }
                  }
               }

               if (!foundValue && (method.modifiers & 131072) == 0 && (this.bits & 32) == 0 && annotationType.isValidBinding()) {
                  scope.problemReporter().missingValueForAnnotationMember(this, selector);
               }
            }

            for(i = 0; i < pairsLength; ++i) {
               if (pairs[i] != null) {
                  if (annotationType.isValidBinding()) {
                     scope.problemReporter().undefinedAnnotationValue(annotationType, pairs[i]);
                  }

                  pairs[i].resolveTypeExpecting(scope, (TypeBinding)null);
               }
            }

            this.compilerAnnotation = scope.environment().createAnnotation((ReferenceBinding)this.resolvedType, this.computeElementValuePairs());
            long tagBits = this.detectStandardAnnotation(scope, annotationType, valueAttribute);
            int defaultNullness = (int)(tagBits & 1018L);
            tagBits &= -1019L;
            CompilerOptions compilerOptions = scope.compilerOptions();
            if ((tagBits & 70368744177664L) != 0L && compilerOptions.complianceLevel >= 3473408L && !compilerOptions.storeAnnotations) {
               this.recipient.setAnnotations(new AnnotationBinding[]{this.compilerAnnotation}, true);
            }

            scope.referenceCompilationUnit().recordSuppressWarnings(IrritantSet.NLS, (Annotation)null, this.sourceStart, this.declarationSourceEnd, scope.referenceContext());
            if (this.recipient != null) {
               int kind = this.recipient.kind();
               if (tagBits != 0L || defaultNullness != 0) {
                  SourceTypeBinding sourceType;
                  switch (kind) {
                     case 1:
                        FieldBinding sourceField = (FieldBinding)this.recipient;
                        sourceField.tagBits |= tagBits;
                        FieldDeclaration fieldDeclaration;
                        if ((tagBits & 1125899906842624L) != 0L) {
                           sourceType = (SourceTypeBinding)sourceField.declaringClass;
                           fieldDeclaration = sourceType.scope.referenceContext.declarationOf(sourceField);
                           this.recordSuppressWarnings(scope, fieldDeclaration.declarationSourceStart, fieldDeclaration.declarationSourceEnd, compilerOptions.suppressWarnings);
                        }

                        if (defaultNullness != 0) {
                           sourceType = (SourceTypeBinding)sourceField.declaringClass;
                           fieldDeclaration = sourceType.scope.referenceContext.declarationOf(sourceField);
                           Binding target = scope.parent.checkRedundantDefaultNullness(defaultNullness | scope.localNonNullByDefaultValue(fieldDeclaration.sourceStart), fieldDeclaration.sourceStart);
                           scope.recordNonNullByDefault(fieldDeclaration.binding, defaultNullness, this, fieldDeclaration.declarationSourceStart, fieldDeclaration.declarationSourceEnd);
                           if (target != null) {
                              scope.problemReporter().nullDefaultAnnotationIsRedundant(fieldDeclaration, new Annotation[]{this}, target);
                           }
                        }

                        if ((sourceField.tagBits & 108086391056891904L) == 108086391056891904L) {
                           scope.problemReporter().contradictoryNullAnnotations(this);
                           sourceField.tagBits &= -108086391056891905L;
                        }
                        break;
                     case 2:
                        LocalVariableBinding variable = (LocalVariableBinding)this.recipient;
                        variable.tagBits |= tagBits;
                        if ((variable.tagBits & 108086391056891904L) == 108086391056891904L) {
                           scope.problemReporter().contradictoryNullAnnotations(this);
                           variable.tagBits &= -108086391056891905L;
                        }

                        if ((tagBits & 1125899906842624L) != 0L) {
                           LocalDeclaration localDeclaration = variable.declaration;
                           this.recordSuppressWarnings(scope, localDeclaration.declarationSourceStart, localDeclaration.declarationSourceEnd, compilerOptions.suppressWarnings);
                        }
                        break;
                     case 4:
                     case 2052:
                        sourceType = (SourceTypeBinding)this.recipient;
                        if ((tagBits & 1152921504606846976L) == 0L || sourceType.isAnnotationType()) {
                           sourceType.tagBits |= tagBits;
                        }

                        if ((tagBits & 1125899906842624L) != 0L) {
                           TypeDeclaration typeDeclaration = sourceType.scope.referenceContext;
                           int start;
                           if (scope.referenceCompilationUnit().types[0] == typeDeclaration) {
                              start = 0;
                           } else {
                              start = typeDeclaration.declarationSourceStart;
                           }

                           this.recordSuppressWarnings(scope, start, typeDeclaration.declarationSourceEnd, compilerOptions.suppressWarnings);
                        }

                        sourceType.defaultNullness |= defaultNullness;
                        break;
                     case 8:
                        MethodBinding sourceMethod = (MethodBinding)this.recipient;
                        sourceMethod.tagBits |= tagBits;
                        if ((tagBits & 1125899906842624L) != 0L) {
                           sourceType = (SourceTypeBinding)sourceMethod.declaringClass;
                           AbstractMethodDeclaration methodDeclaration = sourceType.scope.referenceContext.declarationOf(sourceMethod);
                           this.recordSuppressWarnings(scope, methodDeclaration.declarationSourceStart, methodDeclaration.declarationSourceEnd, compilerOptions.suppressWarnings);
                        }

                        long nullBits = sourceMethod.tagBits & 108086391056891904L;
                        if (nullBits == 108086391056891904L) {
                           scope.problemReporter().contradictoryNullAnnotations(this);
                           sourceMethod.tagBits &= -108086391056891905L;
                        }

                        if (nullBits != 0L && sourceMethod.isConstructor()) {
                           if (compilerOptions.sourceLevel >= 3407872L) {
                              scope.problemReporter().nullAnnotationUnsupportedLocation(this);
                           }

                           sourceMethod.tagBits &= -108086391056891905L;
                        }

                        sourceMethod.defaultNullness |= defaultNullness;
                        break;
                     case 16:
                        PackageBinding var10000 = (PackageBinding)this.recipient;
                        var10000.tagBits |= tagBits;
                        break;
                     case 64:
                        SourceModuleBinding module = (SourceModuleBinding)this.recipient;
                        module.tagBits |= tagBits;
                        if ((tagBits & 1125899906842624L) != 0L) {
                           ModuleDeclaration moduleDeclaration = module.scope.referenceContext.moduleDeclaration;
                           this.recordSuppressWarnings(scope, 0, moduleDeclaration.declarationSourceEnd, compilerOptions.suppressWarnings);
                        }

                        module.defaultNullness |= defaultNullness;
                  }
               }

               if (kind == 4) {
                  SourceTypeBinding sourceType = (SourceTypeBinding)this.recipient;
                  if (CharOperation.equals(sourceType.sourceName, TypeConstants.PACKAGE_INFO_NAME)) {
                     kind = 16;
                  }
               }

               checkAnnotationTarget(this, scope, annotationType, kind, this.recipient, tagBits & 108086391056891904L);
            }

            return this.resolvedType;
         }
      }
   }

   public long handleNonNullByDefault(BlockScope scope) {
      TypeBinding typeBinding = this.resolvedType;
      if (typeBinding == null) {
         typeBinding = this.type.resolveType(scope);
         if (typeBinding == null) {
            return 0L;
         }

         this.resolvedType = typeBinding;
      }

      if (!typeBinding.isAnnotationType()) {
         return 0L;
      } else {
         ReferenceBinding annotationType = (ReferenceBinding)typeBinding;
         if (!annotationType.hasNullBit(128)) {
            return 0L;
         } else {
            MethodBinding[] methods = annotationType.methods();
            MemberValuePair[] pairs = this.memberValuePairs();
            MemberValuePair valueAttribute = null;
            int pairsLength = pairs.length;
            int i = 0;

            for(int requiredLength = methods.length; i < requiredLength; ++i) {
               MethodBinding method = methods[i];
               char[] selector = method.selector;

               for(int j = 0; j < pairsLength; ++j) {
                  MemberValuePair pair = pairs[j];
                  if (pair != null) {
                     char[] name = pair.name;
                     if (CharOperation.equals(name, selector) && valueAttribute == null && CharOperation.equals(name, TypeConstants.VALUE)) {
                        valueAttribute = pair;
                        pair.binding = method;
                        pair.resolveTypeExpecting(scope, method.returnType);
                     }
                  }
               }
            }

            long tagBits = this.determineNonNullByDefaultTagBits(annotationType, valueAttribute);
            return (long)((int)(tagBits & 1018L));
         }
      }
   }

   private static AnnotationTargetAllowed isAnnotationTargetAllowed(Binding recipient, BlockScope scope, TypeBinding annotationType, int kind, long metaTagBits) {
      switch (kind) {
         case 1:
            if ((metaTagBits & 137438953472L) != 0L) {
               return Annotation.AnnotationTargetAllowed.YES;
            }

            if ((metaTagBits & 9007199254740992L) != 0L) {
               FieldBinding sourceField = (FieldBinding)recipient;
               SourceTypeBinding sourceType = (SourceTypeBinding)sourceField.declaringClass;
               FieldDeclaration fieldDeclaration = sourceType.scope.referenceContext.declarationOf(sourceField);
               if (isTypeUseCompatible(fieldDeclaration.type, scope)) {
                  return Annotation.AnnotationTargetAllowed.YES;
               }

               return Annotation.AnnotationTargetAllowed.TYPE_ANNOTATION_ON_QUALIFIED_NAME;
            }
            break;
         case 2:
            LocalVariableBinding localVariableBinding = (LocalVariableBinding)recipient;
            if ((localVariableBinding.tagBits & 1024L) != 0L) {
               if ((metaTagBits & 549755813888L) != 0L) {
                  return Annotation.AnnotationTargetAllowed.YES;
               }

               if ((metaTagBits & 9007199254740992L) != 0L) {
                  if (isTypeUseCompatible(localVariableBinding.declaration.type, scope)) {
                     return Annotation.AnnotationTargetAllowed.YES;
                  }

                  return Annotation.AnnotationTargetAllowed.TYPE_ANNOTATION_ON_QUALIFIED_NAME;
               }
            } else {
               if ((annotationType.tagBits & 2199023255552L) != 0L) {
                  return Annotation.AnnotationTargetAllowed.YES;
               }

               if ((metaTagBits & 9007199254740992L) != 0L) {
                  if (localVariableBinding.declaration.isTypeNameVar(scope)) {
                     return Annotation.AnnotationTargetAllowed.NO;
                  }

                  if (isTypeUseCompatible(localVariableBinding.declaration.type, scope)) {
                     return Annotation.AnnotationTargetAllowed.YES;
                  }

                  return Annotation.AnnotationTargetAllowed.TYPE_ANNOTATION_ON_QUALIFIED_NAME;
               }
            }
            break;
         case 4:
         case 2052:
            if (((ReferenceBinding)recipient).isAnnotationType()) {
               if ((metaTagBits & 9011666020728832L) != 0L) {
                  return Annotation.AnnotationTargetAllowed.YES;
               }
            } else {
               if ((metaTagBits & 9007267974217728L) != 0L) {
                  return Annotation.AnnotationTargetAllowed.YES;
               }

               if ((metaTagBits & 8796093022208L) != 0L && CharOperation.equals(((ReferenceBinding)recipient).sourceName, TypeConstants.PACKAGE_INFO_NAME)) {
                  return Annotation.AnnotationTargetAllowed.YES;
               }
            }
            break;
         case 8:
            MethodBinding methodBinding = (MethodBinding)recipient;
            if (methodBinding.isConstructor()) {
               if ((metaTagBits & 9008298766368768L) != 0L) {
                  return Annotation.AnnotationTargetAllowed.YES;
               }
            } else {
               if ((metaTagBits & 274877906944L) != 0L) {
                  return Annotation.AnnotationTargetAllowed.YES;
               }

               if ((metaTagBits & 9007199254740992L) != 0L) {
                  SourceTypeBinding sourceType = (SourceTypeBinding)methodBinding.declaringClass;
                  MethodDeclaration methodDecl = (MethodDeclaration)sourceType.scope.referenceContext.declarationOf(methodBinding);
                  if (isTypeUseCompatible(methodDecl.returnType, scope)) {
                     return Annotation.AnnotationTargetAllowed.YES;
                  }

                  return Annotation.AnnotationTargetAllowed.TYPE_ANNOTATION_ON_QUALIFIED_NAME;
               }
            }
            break;
         case 16:
            if ((metaTagBits & 8796093022208L) != 0L) {
               return Annotation.AnnotationTargetAllowed.YES;
            }

            if (scope.compilerOptions().sourceLevel <= 3276800L) {
               SourceTypeBinding sourceType = (SourceTypeBinding)recipient;
               if (CharOperation.equals(sourceType.sourceName, TypeConstants.PACKAGE_INFO_NAME)) {
                  return Annotation.AnnotationTargetAllowed.YES;
               }
            }
            break;
         case 64:
            if ((metaTagBits & 2305843009213693952L) != 0L) {
               return Annotation.AnnotationTargetAllowed.YES;
            }
            break;
         case 4100:
            if ((metaTagBits & 27021597764222976L) != 0L) {
               return Annotation.AnnotationTargetAllowed.YES;
            }
            break;
         case 16388:
            if ((metaTagBits & 9007199254740992L) != 0L) {
               return Annotation.AnnotationTargetAllowed.YES;
            }

            if (scope.compilerOptions().sourceLevel < 3407872L) {
               return Annotation.AnnotationTargetAllowed.YES;
            }
      }

      return Annotation.AnnotationTargetAllowed.NO;
   }

   public static boolean isAnnotationTargetAllowed(BlockScope scope, TypeBinding annotationType, Binding recipient) {
      long metaTagBits = annotationType.getAnnotationTagBits();
      if ((metaTagBits & 2332882164804222976L) == 0L) {
         return true;
      } else {
         return isAnnotationTargetAllowed(recipient, scope, annotationType, recipient.kind(), metaTagBits) == Annotation.AnnotationTargetAllowed.YES;
      }
   }

   static AnnotationTargetAllowed isAnnotationTargetAllowed(Annotation annotation, BlockScope scope, TypeBinding annotationType, int kind) {
      long metaTagBits = annotationType.getAnnotationTagBits();
      if ((metaTagBits & 2332882164804222976L) != 0L) {
         if ((metaTagBits & 17523466567680L) == 0L && (metaTagBits & 27021597764222976L) != 0L && scope.compilerOptions().sourceLevel < 3407872L) {
            switch (kind) {
               case 1:
               case 2:
               case 4:
               case 8:
               case 16:
               case 2052:
                  scope.problemReporter().invalidUsageOfTypeAnnotations(annotation);
            }
         }

         return isAnnotationTargetAllowed(annotation.recipient, scope, annotationType, kind, metaTagBits);
      } else {
         if (kind == 4100 || kind == 16388) {
            scope.problemReporter().explitAnnotationTargetRequired(annotation);
         }

         return Annotation.AnnotationTargetAllowed.YES;
      }
   }

   static void checkAnnotationTarget(Annotation annotation, BlockScope scope, ReferenceBinding annotationType, int kind, Binding recipient, long tagBitsToRevert) {
      if (annotationType.isValidBinding()) {
         AnnotationTargetAllowed annotationTargetAllowed = isAnnotationTargetAllowed(annotation, scope, annotationType, kind);
         if (annotationTargetAllowed != Annotation.AnnotationTargetAllowed.YES) {
            if (annotationTargetAllowed == Annotation.AnnotationTargetAllowed.TYPE_ANNOTATION_ON_QUALIFIED_NAME) {
               scope.problemReporter().typeAnnotationAtQualifiedName(annotation);
            } else {
               scope.problemReporter().disallowedTargetForAnnotation(annotation);
            }

            if (recipient instanceof TypeBinding) {
               ((TypeBinding)recipient).tagBits &= ~tagBitsToRevert;
            }
         }

      }
   }

   public static void checkForInstancesOfRepeatableWithRepeatingContainerAnnotation(BlockScope scope, ReferenceBinding repeatedAnnotationType, Annotation[] sourceAnnotations) {
      MethodBinding[] valueMethods = repeatedAnnotationType.getMethods(TypeConstants.VALUE);
      if (valueMethods.length == 1) {
         TypeBinding methodReturnType = valueMethods[0].returnType;
         if (methodReturnType.isArrayType() && methodReturnType.dimensions() == 1) {
            ArrayBinding array = (ArrayBinding)methodReturnType;
            TypeBinding elementsType = array.elementsType();
            if (elementsType.isRepeatableAnnotationType()) {
               for(int i = 0; i < sourceAnnotations.length; ++i) {
                  Annotation annotation = sourceAnnotations[i];
                  if (TypeBinding.equalsEquals(elementsType, annotation.resolvedType)) {
                     scope.problemReporter().repeatableAnnotationWithRepeatingContainer(annotation, repeatedAnnotationType);
                     return;
                  }
               }

            }
         }
      }
   }

   public static boolean isTypeUseCompatible(TypeReference reference, Scope scope) {
      if (reference != null && !(reference instanceof SingleTypeReference)) {
         Binding binding = scope.getPackage(reference.getTypeName());
         if (binding instanceof PackageBinding) {
            return false;
         }
      }

      return true;
   }

   public static void isTypeUseCompatible(TypeReference reference, Scope scope, Annotation[] annotations) {
      if (annotations != null && reference != null && reference.getAnnotatableLevels() != 1) {
         if (scope.environment().globalOptions.sourceLevel >= 3407872L) {
            TypeBinding resolvedType = reference.resolvedType == null ? null : reference.resolvedType.leafComponentType();
            if (resolvedType != null && resolvedType.isNestedType()) {
               int i = 0;

               for(int annotationsLength = annotations.length; i < annotationsLength; ++i) {
                  Annotation annotation = annotations[i];
                  long metaTagBits = annotation.resolvedType.getAnnotationTagBits();
                  if ((metaTagBits & 9007199254740992L) != 0L && (metaTagBits & 17523466567680L) == 0L) {
                     for(ReferenceBinding currentType = (ReferenceBinding)resolvedType; currentType.isNestedType(); currentType = currentType.enclosingType()) {
                        if (currentType.isStatic()) {
                           QualifiedTypeReference.rejectAnnotationsOnStaticMemberQualififer(scope, currentType, new Annotation[]{annotation});
                           break;
                        }

                        if (annotation.hasNullBit(96)) {
                           scope.problemReporter().nullAnnotationAtQualifyingType(annotation);
                           break;
                        }
                     }
                  }
               }

            }
         }
      }
   }

   public boolean hasNullBit(int bit) {
      return this.resolvedType instanceof ReferenceBinding && ((ReferenceBinding)this.resolvedType).hasNullBit(bit);
   }

   public abstract void traverse(ASTVisitor var1, BlockScope var2);

   public abstract void traverse(ASTVisitor var1, ClassScope var2);

   public Annotation getPersistibleAnnotation() {
      return this.persistibleAnnotation;
   }

   public void setPersistibleAnnotation(ContainerAnnotation container) {
      this.persistibleAnnotation = container;
   }

   public static enum AnnotationTargetAllowed {
      YES,
      TYPE_ANNOTATION_ON_QUALIFIED_NAME,
      NO;
   }
}

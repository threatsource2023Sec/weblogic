package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;

public class AnnotatableTypeSystem extends TypeSystem {
   private boolean isAnnotationBasedNullAnalysisEnabled;

   public AnnotatableTypeSystem(LookupEnvironment environment) {
      super(environment);
      this.environment = environment;
      this.isAnnotationBasedNullAnalysisEnabled = environment.globalOptions.isAnnotationBasedNullAnalysisEnabled;
   }

   public TypeBinding[] getAnnotatedTypes(TypeBinding type) {
      TypeBinding[] derivedTypes = this.getDerivedTypes(type);
      int length = derivedTypes.length;
      TypeBinding[] annotatedVersions = new TypeBinding[length];
      int versions = 0;

      for(int i = 0; i < length; ++i) {
         TypeBinding derivedType = derivedTypes[i];
         if (derivedType == null) {
            break;
         }

         if (derivedType.hasTypeAnnotations() && derivedType.id == type.id) {
            annotatedVersions[versions++] = derivedType;
         }
      }

      if (versions != length) {
         System.arraycopy(annotatedVersions, 0, annotatedVersions = new TypeBinding[versions], 0, versions);
      }

      return annotatedVersions;
   }

   public ArrayBinding getArrayType(TypeBinding leafType, int dimensions, AnnotationBinding[] annotations) {
      if (leafType instanceof ArrayBinding) {
         dimensions += leafType.dimensions();
         AnnotationBinding[] leafAnnotations = leafType.getTypeAnnotations();
         leafType = leafType.leafComponentType();
         AnnotationBinding[] allAnnotations = new AnnotationBinding[leafAnnotations.length + annotations.length + 1];
         System.arraycopy(annotations, 0, allAnnotations, 0, annotations.length);
         System.arraycopy(leafAnnotations, 0, allAnnotations, annotations.length + 1, leafAnnotations.length);
         annotations = allAnnotations;
      }

      ArrayBinding nakedType = null;
      TypeBinding[] derivedTypes = this.getDerivedTypes(leafType);
      int i = 0;

      for(int length = derivedTypes.length; i < length; ++i) {
         TypeBinding derivedType = derivedTypes[i];
         if (derivedType == null) {
            break;
         }

         if (derivedType.isArrayType() && derivedType.dimensions() == dimensions && derivedType.leafComponentType() == leafType) {
            if (Util.effectivelyEqual(derivedType.getTypeAnnotations(), annotations)) {
               return (ArrayBinding)derivedType;
            }

            if (!derivedType.hasTypeAnnotations()) {
               nakedType = (ArrayBinding)derivedType;
            }
         }
      }

      if (nakedType == null) {
         nakedType = super.getArrayType(leafType, dimensions);
      }

      if (!this.haveTypeAnnotations(leafType, annotations)) {
         return nakedType;
      } else {
         ArrayBinding arrayType = new ArrayBinding(leafType, dimensions, this.environment);
         arrayType.id = nakedType.id;
         arrayType.setTypeAnnotations(annotations, this.isAnnotationBasedNullAnalysisEnabled);
         return (ArrayBinding)this.cacheDerivedType(leafType, nakedType, arrayType);
      }
   }

   public ArrayBinding getArrayType(TypeBinding leaftType, int dimensions) {
      return this.getArrayType(leaftType, dimensions, Binding.NO_ANNOTATIONS);
   }

   public ReferenceBinding getMemberType(ReferenceBinding memberType, ReferenceBinding enclosingType) {
      return !this.haveTypeAnnotations(memberType, (TypeBinding)enclosingType) ? super.getMemberType(memberType, enclosingType) : (ReferenceBinding)this.getAnnotatedType(memberType, enclosingType, memberType.getTypeAnnotations());
   }

   public ParameterizedTypeBinding getParameterizedType(ReferenceBinding genericType, TypeBinding[] typeArguments, ReferenceBinding enclosingType, AnnotationBinding[] annotations) {
      if (genericType.hasTypeAnnotations()) {
         throw new IllegalStateException();
      } else {
         ParameterizedTypeBinding parameterizedType = this.parameterizedTypes.get(genericType, typeArguments, enclosingType, annotations);
         if (parameterizedType != null) {
            return parameterizedType;
         } else {
            ParameterizedTypeBinding nakedType = super.getParameterizedType(genericType, typeArguments, enclosingType);
            if (!this.haveTypeAnnotations(genericType, enclosingType, typeArguments, annotations)) {
               return nakedType;
            } else {
               parameterizedType = new ParameterizedTypeBinding(genericType, typeArguments, enclosingType, this.environment);
               parameterizedType.id = nakedType.id;
               parameterizedType.setTypeAnnotations(annotations, this.isAnnotationBasedNullAnalysisEnabled);
               this.parameterizedTypes.put(genericType, typeArguments, enclosingType, parameterizedType);
               return (ParameterizedTypeBinding)this.cacheDerivedType(genericType, nakedType, parameterizedType);
            }
         }
      }
   }

   public ParameterizedTypeBinding getParameterizedType(ReferenceBinding genericType, TypeBinding[] typeArguments, ReferenceBinding enclosingType) {
      return this.getParameterizedType(genericType, typeArguments, enclosingType, Binding.NO_ANNOTATIONS);
   }

   public RawTypeBinding getRawType(ReferenceBinding genericType, ReferenceBinding enclosingType, AnnotationBinding[] annotations) {
      if (genericType.hasTypeAnnotations()) {
         throw new IllegalStateException();
      } else {
         if (!genericType.hasEnclosingInstanceContext() && enclosingType != null) {
            enclosingType = (ReferenceBinding)enclosingType.original();
         }

         RawTypeBinding nakedType = null;
         TypeBinding[] derivedTypes = this.getDerivedTypes(genericType);
         int i = 0;

         for(int length = derivedTypes.length; i < length; ++i) {
            TypeBinding derivedType = derivedTypes[i];
            if (derivedType == null) {
               break;
            }

            if (derivedType.isRawType() && derivedType.actualType() == genericType && derivedType.enclosingType() == enclosingType) {
               if (Util.effectivelyEqual(derivedType.getTypeAnnotations(), annotations)) {
                  return (RawTypeBinding)derivedType;
               }

               if (!derivedType.hasTypeAnnotations()) {
                  nakedType = (RawTypeBinding)derivedType;
               }
            }
         }

         if (nakedType == null) {
            nakedType = super.getRawType(genericType, enclosingType);
         }

         if (!this.haveTypeAnnotations(genericType, enclosingType, (TypeBinding[])null, annotations)) {
            return nakedType;
         } else {
            RawTypeBinding rawType = new RawTypeBinding(genericType, enclosingType, this.environment);
            rawType.id = nakedType.id;
            rawType.setTypeAnnotations(annotations, this.isAnnotationBasedNullAnalysisEnabled);
            return (RawTypeBinding)this.cacheDerivedType(genericType, nakedType, rawType);
         }
      }
   }

   public RawTypeBinding getRawType(ReferenceBinding genericType, ReferenceBinding enclosingType) {
      return this.getRawType(genericType, enclosingType, Binding.NO_ANNOTATIONS);
   }

   public WildcardBinding getWildcard(ReferenceBinding genericType, int rank, TypeBinding bound, TypeBinding[] otherBounds, int boundKind, AnnotationBinding[] annotations) {
      if (genericType == null) {
         genericType = ReferenceBinding.LUB_GENERIC;
      }

      if (genericType.hasTypeAnnotations()) {
         throw new IllegalStateException();
      } else {
         WildcardBinding nakedType = null;
         boolean useDerivedTypesOfBound = bound instanceof TypeVariableBinding || bound instanceof ParameterizedTypeBinding && !(bound instanceof RawTypeBinding);
         TypeBinding[] derivedTypes = this.getDerivedTypes((TypeBinding)(useDerivedTypesOfBound ? bound : genericType));
         int i = 0;

         for(int length = derivedTypes.length; i < length; ++i) {
            TypeBinding derivedType = derivedTypes[i];
            if (derivedType == null) {
               break;
            }

            if (derivedType.isWildcard() && derivedType.actualType() == genericType && derivedType.rank() == rank && derivedType.boundKind() == boundKind && derivedType.bound() == bound && Util.effectivelyEqual(derivedType.additionalBounds(), otherBounds)) {
               if (Util.effectivelyEqual(derivedType.getTypeAnnotations(), annotations)) {
                  return (WildcardBinding)derivedType;
               }

               if (!derivedType.hasTypeAnnotations()) {
                  nakedType = (WildcardBinding)derivedType;
               }
            }
         }

         if (nakedType == null) {
            nakedType = super.getWildcard(genericType, rank, bound, otherBounds, boundKind);
         }

         if (!this.haveTypeAnnotations(genericType, bound, otherBounds, annotations)) {
            return nakedType;
         } else {
            WildcardBinding wildcard = new WildcardBinding(genericType, rank, bound, otherBounds, boundKind, this.environment);
            wildcard.id = nakedType.id;
            wildcard.setTypeAnnotations(annotations, this.isAnnotationBasedNullAnalysisEnabled);
            return (WildcardBinding)this.cacheDerivedType((TypeBinding)(useDerivedTypesOfBound ? bound : genericType), nakedType, wildcard);
         }
      }
   }

   public WildcardBinding getWildcard(ReferenceBinding genericType, int rank, TypeBinding bound, TypeBinding[] otherBounds, int boundKind) {
      return this.getWildcard(genericType, rank, bound, otherBounds, boundKind, Binding.NO_ANNOTATIONS);
   }

   public TypeBinding getAnnotatedType(TypeBinding type, AnnotationBinding[][] annotations) {
      if (type != null && type.isValidBinding() && annotations != null && annotations.length != 0) {
         TypeBinding annotatedType = null;
         switch (type.kind()) {
            case 4:
            case 132:
            case 260:
            case 516:
            case 1028:
            case 2052:
            case 4100:
            case 8196:
            case 32772:
               if (type.isUnresolvedType() && CharOperation.indexOf('$', type.sourceName()) > 0) {
                  type = BinaryTypeBinding.resolveType(type, this.environment, true);
               }

               int levels = type.depth() + 1;
               TypeBinding[] types = new TypeBinding[levels];
               --levels;
               types[levels] = type;

               for(TypeBinding enclosingType = type.enclosingType(); enclosingType != null; enclosingType = enclosingType.enclosingType()) {
                  --levels;
                  types[levels] = enclosingType;
               }

               levels = annotations.length;
               int j = types.length - levels;

               int i;
               for(i = 0; i < levels && (annotations[i] == null || annotations[i].length <= 0); ++j) {
                  ++i;
               }

               if (i == levels) {
                  return type;
               } else {
                  if (j < 0) {
                     return type;
                  }

                  for(TypeBinding enclosingType = j == 0 ? null : types[j - 1]; i < levels; ++j) {
                     TypeBinding currentType = types[j];
                     AnnotationBinding[] currentAnnotations = annotations[i] != null && annotations[i].length > 0 ? annotations[i] : currentType.getTypeAnnotations();
                     annotatedType = this.getAnnotatedType(currentType, (TypeBinding)enclosingType, currentAnnotations);
                     enclosingType = annotatedType;
                     ++i;
                  }

                  return (TypeBinding)annotatedType;
               }
            case 68:
               ArrayBinding arrayBinding = (ArrayBinding)type;
               annotatedType = this.getArrayType(arrayBinding.leafComponentType, arrayBinding.dimensions, flattenedAnnotations(annotations));
               return (TypeBinding)annotatedType;
            default:
               throw new IllegalStateException();
         }
      } else {
         return type;
      }
   }

   private TypeBinding getAnnotatedType(TypeBinding type, TypeBinding enclosingType, AnnotationBinding[] annotations) {
      if (type.kind() == 260) {
         return this.getParameterizedType(type.actualType(), type.typeArguments(), (ReferenceBinding)enclosingType, annotations);
      } else {
         TypeBinding nakedType = null;
         TypeBinding[] derivedTypes = this.getDerivedTypes(type);
         int i = 0;

         for(int length = derivedTypes.length; i < length; ++i) {
            TypeBinding derivedType = derivedTypes[i];
            if (derivedType == null) {
               break;
            }

            if (derivedType.enclosingType() == enclosingType && Util.effectivelyEqual(derivedType.typeArguments(), type.typeArguments())) {
               switch (type.kind()) {
                  case 68:
                     if (!derivedType.isArrayType() || derivedType.dimensions() != type.dimensions() || derivedType.leafComponentType() != type.leafComponentType()) {
                        continue;
                     }
                     break;
                  case 516:
                  case 8196:
                     if (!derivedType.isWildcard() || derivedType.actualType() != type.actualType() || derivedType.rank() != type.rank() || derivedType.boundKind() != type.boundKind() || derivedType.bound() != type.bound() || !Util.effectivelyEqual(derivedType.additionalBounds(), type.additionalBounds())) {
                        continue;
                     }
                     break;
                  case 1028:
                     if (!derivedType.isRawType() || derivedType.actualType() != type.actualType()) {
                        continue;
                     }
                     break;
                  default:
                     switch (derivedType.kind()) {
                        case 68:
                        case 516:
                        case 1028:
                        case 8196:
                        case 32772:
                           continue;
                     }
               }

               if (Util.effectivelyEqual(derivedType.getTypeAnnotations(), annotations)) {
                  return derivedType;
               }

               if (!derivedType.hasTypeAnnotations()) {
                  nakedType = derivedType;
               }
            }
         }

         if (nakedType == null) {
            nakedType = this.getUnannotatedType(type);
         }

         if (!this.haveTypeAnnotations(type, enclosingType, (TypeBinding[])null, annotations)) {
            return nakedType;
         } else {
            TypeBinding annotatedType = type.clone(enclosingType);
            annotatedType.id = nakedType.id;
            annotatedType.setTypeAnnotations(annotations, this.isAnnotationBasedNullAnalysisEnabled);
            if (this.isAnnotationBasedNullAnalysisEnabled && (annotatedType.tagBits & 108086391056891904L) == 0L) {
               annotatedType.tagBits |= type.tagBits & 108086391056891904L;
            }

            Object keyType;
            switch (type.kind()) {
               case 68:
                  keyType = type.leafComponentType();
                  break;
               case 516:
               case 1028:
                  keyType = type.actualType();
                  break;
               default:
                  keyType = nakedType;
            }

            return this.cacheDerivedType((TypeBinding)keyType, nakedType, annotatedType);
         }
      }
   }

   private boolean haveTypeAnnotations(TypeBinding baseType, TypeBinding someType, TypeBinding[] someTypes, AnnotationBinding[] annotations) {
      if (baseType != null && baseType.hasTypeAnnotations()) {
         return true;
      } else if (someType != null && someType.hasTypeAnnotations()) {
         return true;
      } else {
         int i = 0;

         int length;
         for(length = annotations == null ? 0 : annotations.length; i < length; ++i) {
            if (annotations[i] != null) {
               return true;
            }
         }

         i = 0;

         for(length = someTypes == null ? 0 : someTypes.length; i < length; ++i) {
            if (someTypes[i].hasTypeAnnotations()) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean haveTypeAnnotations(TypeBinding leafType, AnnotationBinding[] annotations) {
      return this.haveTypeAnnotations(leafType, (TypeBinding)null, (TypeBinding[])null, annotations);
   }

   private boolean haveTypeAnnotations(TypeBinding memberType, TypeBinding enclosingType) {
      return this.haveTypeAnnotations(memberType, enclosingType, (TypeBinding[])null, (AnnotationBinding[])null);
   }

   static AnnotationBinding[] flattenedAnnotations(AnnotationBinding[][] annotations) {
      if (annotations != null && annotations.length != 0) {
         int levels = annotations.length;
         int length = levels;

         for(int i = 0; i < levels; ++i) {
            length += annotations[i] == null ? 0 : annotations[i].length;
         }

         if (length == 0) {
            return Binding.NO_ANNOTATIONS;
         } else {
            AnnotationBinding[] series = new AnnotationBinding[length];
            int index = 0;

            for(int i = 0; i < levels; ++i) {
               int annotationsLength = annotations[i] == null ? 0 : annotations[i].length;
               if (annotationsLength > 0) {
                  System.arraycopy(annotations[i], 0, series, index, annotationsLength);
                  index += annotationsLength;
               }

               series[index++] = null;
            }

            if (index != length) {
               throw new IllegalStateException();
            } else {
               return series;
            }
         }
      } else {
         return Binding.NO_ANNOTATIONS;
      }
   }

   public boolean isAnnotatedTypeSystem() {
      return true;
   }
}

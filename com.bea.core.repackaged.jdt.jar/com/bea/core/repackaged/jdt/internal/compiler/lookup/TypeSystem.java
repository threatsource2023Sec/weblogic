package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.util.SimpleLookupTable;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.util.HashMap;

public class TypeSystem {
   private int typeid = 128;
   private TypeBinding[][] types;
   protected HashedParameterizedTypes parameterizedTypes;
   private SimpleLookupTable annotationTypes;
   LookupEnvironment environment;

   public TypeSystem(LookupEnvironment environment) {
      this.environment = environment;
      this.annotationTypes = new SimpleLookupTable(16);
      this.typeid = 128;
      this.types = new TypeBinding[256][];
      this.parameterizedTypes = new HashedParameterizedTypes();
   }

   public final TypeBinding getUnannotatedType(TypeBinding type) {
      UnresolvedReferenceBinding urb = null;
      if (((TypeBinding)type).isUnresolvedType()) {
         urb = (UnresolvedReferenceBinding)type;
         ReferenceBinding resolvedType = urb.resolvedType;
         if (resolvedType != null) {
            type = resolvedType;
         }
      }

      try {
         if (((TypeBinding)type).id == Integer.MAX_VALUE) {
            if (((TypeBinding)type).hasTypeAnnotations()) {
               throw new IllegalStateException();
            }

            int typesLength = this.types.length;
            if (this.typeid == typesLength) {
               System.arraycopy(this.types, 0, this.types = new TypeBinding[typesLength * 2][], 0, typesLength);
            }

            this.types[((TypeBinding)type).id = this.typeid++] = new TypeBinding[4];
         } else {
            TypeBinding nakedType = this.types[((TypeBinding)type).id] == null ? null : this.types[((TypeBinding)type).id][0];
            if (((TypeBinding)type).hasTypeAnnotations() && nakedType == null) {
               throw new IllegalStateException();
            }

            if (nakedType != null) {
               TypeBinding var5 = nakedType;
               return var5;
            }

            this.types[((TypeBinding)type).id] = new TypeBinding[4];
         }
      } finally {
         if (urb != null && urb.id == Integer.MAX_VALUE) {
            urb.id = ((TypeBinding)type).id;
         }

      }

      return this.types[((TypeBinding)type).id][0] = (TypeBinding)type;
   }

   public void forceRegisterAsDerived(TypeBinding derived) {
      int id = derived.id;
      if (id != Integer.MAX_VALUE && this.types[id] != null) {
         TypeBinding unannotated = this.types[id][0];
         if (unannotated == derived) {
            this.types[id][0] = unannotated = derived.clone((TypeBinding)null);
         }

         this.cacheDerivedType(unannotated, derived);
      } else {
         throw new IllegalStateException("Type was not yet registered as expected: " + derived);
      }
   }

   public TypeBinding[] getAnnotatedTypes(TypeBinding type) {
      return Binding.NO_TYPES;
   }

   public ArrayBinding getArrayType(TypeBinding leafType, int dimensions) {
      if (leafType instanceof ArrayBinding) {
         dimensions += leafType.dimensions();
         leafType = leafType.leafComponentType();
      }

      TypeBinding unannotatedLeafType = this.getUnannotatedType(leafType);
      TypeBinding[] derivedTypes = this.types[unannotatedLeafType.id];
      int length = derivedTypes.length;

      int i;
      TypeBinding arrayType;
      for(i = 0; i < length; ++i) {
         arrayType = derivedTypes[i];
         if (arrayType == null) {
            break;
         }

         if (arrayType.isArrayType() && !arrayType.hasTypeAnnotations() && arrayType.leafComponentType() == unannotatedLeafType && arrayType.dimensions() == dimensions) {
            return (ArrayBinding)arrayType;
         }
      }

      if (i == length) {
         System.arraycopy(derivedTypes, 0, derivedTypes = new TypeBinding[length * 2], 0, length);
         this.types[unannotatedLeafType.id] = derivedTypes;
      }

      arrayType = derivedTypes[i] = new ArrayBinding(unannotatedLeafType, dimensions, this.environment);
      int typesLength = this.types.length;
      if (this.typeid == typesLength) {
         System.arraycopy(this.types, 0, this.types = new TypeBinding[typesLength * 2][], 0, typesLength);
      }

      this.types[this.typeid] = new TypeBinding[1];
      return (ArrayBinding)(this.types[arrayType.id = this.typeid++][0] = arrayType);
   }

   public ArrayBinding getArrayType(TypeBinding leafComponentType, int dimensions, AnnotationBinding[] annotations) {
      return this.getArrayType(leafComponentType, dimensions);
   }

   public ReferenceBinding getMemberType(ReferenceBinding memberType, ReferenceBinding enclosingType) {
      return memberType;
   }

   public ParameterizedTypeBinding getParameterizedType(ReferenceBinding genericType, TypeBinding[] typeArguments, ReferenceBinding enclosingType) {
      ReferenceBinding unannotatedGenericType = (ReferenceBinding)this.getUnannotatedType(genericType);
      if (enclosingType == null && genericType instanceof UnresolvedReferenceBinding && !(unannotatedGenericType instanceof UnresolvedReferenceBinding)) {
         enclosingType = unannotatedGenericType.enclosingType();
      }

      int typeArgumentsLength = typeArguments == null ? 0 : typeArguments.length;
      TypeBinding[] unannotatedTypeArguments = typeArguments == null ? null : new TypeBinding[typeArgumentsLength];

      for(int i = 0; i < typeArgumentsLength; ++i) {
         unannotatedTypeArguments[i] = this.getUnannotatedType(typeArguments[i]);
      }

      ReferenceBinding unannotatedEnclosingType = enclosingType == null ? null : (ReferenceBinding)this.getUnannotatedType(enclosingType);
      ParameterizedTypeBinding parameterizedType = this.parameterizedTypes.get(unannotatedGenericType, unannotatedTypeArguments, unannotatedEnclosingType, Binding.NO_ANNOTATIONS);
      if (parameterizedType != null) {
         return parameterizedType;
      } else {
         parameterizedType = new ParameterizedTypeBinding(unannotatedGenericType, unannotatedTypeArguments, unannotatedEnclosingType, this.environment);
         this.cacheDerivedType(unannotatedGenericType, parameterizedType);
         this.parameterizedTypes.put(genericType, typeArguments, enclosingType, parameterizedType);
         int typesLength = this.types.length;
         if (this.typeid == typesLength) {
            System.arraycopy(this.types, 0, this.types = new TypeBinding[typesLength * 2][], 0, typesLength);
         }

         this.types[this.typeid] = new TypeBinding[1];
         return (ParameterizedTypeBinding)(this.types[parameterizedType.id = this.typeid++][0] = parameterizedType);
      }
   }

   public ParameterizedTypeBinding getParameterizedType(ReferenceBinding genericType, TypeBinding[] typeArguments, ReferenceBinding enclosingType, AnnotationBinding[] annotations) {
      return this.getParameterizedType(genericType, typeArguments, enclosingType);
   }

   public RawTypeBinding getRawType(ReferenceBinding genericType, ReferenceBinding enclosingType) {
      if (!genericType.hasEnclosingInstanceContext() && enclosingType != null) {
         enclosingType = (ReferenceBinding)enclosingType.original();
      }

      ReferenceBinding unannotatedGenericType = (ReferenceBinding)this.getUnannotatedType(genericType);
      ReferenceBinding unannotatedEnclosingType = enclosingType == null ? null : (ReferenceBinding)this.getUnannotatedType(enclosingType);
      TypeBinding[] derivedTypes = this.types[unannotatedGenericType.id];
      int length = derivedTypes.length;

      int i;
      TypeBinding rawTytpe;
      for(i = 0; i < length; ++i) {
         rawTytpe = derivedTypes[i];
         if (rawTytpe == null) {
            break;
         }

         if (rawTytpe.isRawType() && rawTytpe.actualType() == unannotatedGenericType && !rawTytpe.hasTypeAnnotations() && rawTytpe.enclosingType() == unannotatedEnclosingType) {
            return (RawTypeBinding)rawTytpe;
         }
      }

      if (i == length) {
         System.arraycopy(derivedTypes, 0, derivedTypes = new TypeBinding[length * 2], 0, length);
         this.types[unannotatedGenericType.id] = derivedTypes;
      }

      rawTytpe = derivedTypes[i] = new RawTypeBinding(unannotatedGenericType, unannotatedEnclosingType, this.environment);
      int typesLength = this.types.length;
      if (this.typeid == typesLength) {
         System.arraycopy(this.types, 0, this.types = new TypeBinding[typesLength * 2][], 0, typesLength);
      }

      this.types[this.typeid] = new TypeBinding[1];
      return (RawTypeBinding)(this.types[rawTytpe.id = this.typeid++][0] = rawTytpe);
   }

   public RawTypeBinding getRawType(ReferenceBinding genericType, ReferenceBinding enclosingType, AnnotationBinding[] annotations) {
      return this.getRawType(genericType, enclosingType);
   }

   public WildcardBinding getWildcard(ReferenceBinding genericType, int rank, TypeBinding bound, TypeBinding[] otherBounds, int boundKind) {
      if (genericType == null) {
         genericType = ReferenceBinding.LUB_GENERIC;
      }

      ReferenceBinding unannotatedGenericType = (ReferenceBinding)this.getUnannotatedType(genericType);
      int otherBoundsLength = otherBounds == null ? 0 : otherBounds.length;
      TypeBinding[] unannotatedOtherBounds = otherBounds == null ? null : new TypeBinding[otherBoundsLength];

      for(int i = 0; i < otherBoundsLength; ++i) {
         unannotatedOtherBounds[i] = this.getUnannotatedType(otherBounds[i]);
      }

      TypeBinding unannotatedBound = bound == null ? null : this.getUnannotatedType(bound);
      boolean useDerivedTypesOfBound = unannotatedBound instanceof TypeVariableBinding || unannotatedBound instanceof ParameterizedTypeBinding && !(unannotatedBound instanceof RawTypeBinding);
      TypeBinding[] derivedTypes = this.types[useDerivedTypesOfBound ? unannotatedBound.id : unannotatedGenericType.id];
      int length = derivedTypes.length;

      int i;
      TypeBinding wildcard;
      for(i = 0; i < length; ++i) {
         wildcard = derivedTypes[i];
         if (wildcard == null) {
            break;
         }

         if (wildcard.isWildcard() && wildcard.actualType() == unannotatedGenericType && !wildcard.hasTypeAnnotations() && wildcard.rank() == rank && wildcard.boundKind() == boundKind && wildcard.bound() == unannotatedBound && Util.effectivelyEqual(wildcard.additionalBounds(), unannotatedOtherBounds)) {
            return (WildcardBinding)wildcard;
         }
      }

      if (i == length) {
         System.arraycopy(derivedTypes, 0, derivedTypes = new TypeBinding[length * 2], 0, length);
         this.types[useDerivedTypesOfBound ? unannotatedBound.id : unannotatedGenericType.id] = derivedTypes;
      }

      wildcard = derivedTypes[i] = new WildcardBinding(unannotatedGenericType, rank, unannotatedBound, unannotatedOtherBounds, boundKind, this.environment);
      int typesLength = this.types.length;
      if (this.typeid == typesLength) {
         System.arraycopy(this.types, 0, this.types = new TypeBinding[typesLength * 2][], 0, typesLength);
      }

      this.types[this.typeid] = new TypeBinding[1];
      return (WildcardBinding)(this.types[wildcard.id = this.typeid++][0] = wildcard);
   }

   public final CaptureBinding getCapturedWildcard(WildcardBinding wildcard, ReferenceBinding contextType, int start, int end, ASTNode cud, int id) {
      WildcardBinding unannotatedWildcard = (WildcardBinding)this.getUnannotatedType(wildcard);
      TypeBinding[] derivedTypes = this.types[unannotatedWildcard.id];
      int length = derivedTypes.length;
      int nullSlot = length;

      int i;
      for(i = length - 1; i >= -1; --i) {
         if (i == -1) {
            i = nullSlot;
            break;
         }

         TypeBinding derivedType = derivedTypes[i];
         if (derivedType == null) {
            nullSlot = i;
         } else if (derivedType.isCapture()) {
            CaptureBinding prior = (CaptureBinding)derivedType;
            if (prior.cud != cud) {
               i = nullSlot;
               break;
            }

            if (prior.sourceType == contextType && prior.start == start && prior.end == end) {
               return prior;
            }
         }
      }

      if (i == length) {
         System.arraycopy(derivedTypes, 0, derivedTypes = new TypeBinding[length * 2], 0, length);
         this.types[unannotatedWildcard.id] = derivedTypes;
      }

      return (CaptureBinding)(derivedTypes[i] = new CaptureBinding(wildcard, contextType, start, end, cud, id));
   }

   public WildcardBinding getWildcard(ReferenceBinding genericType, int rank, TypeBinding bound, TypeBinding[] otherBounds, int boundKind, AnnotationBinding[] annotations) {
      return this.getWildcard(genericType, rank, bound, otherBounds, boundKind);
   }

   public TypeBinding getAnnotatedType(TypeBinding type, AnnotationBinding[][] annotations) {
      return type;
   }

   protected final TypeBinding[] getDerivedTypes(TypeBinding keyType) {
      keyType = this.getUnannotatedType(keyType);
      return this.types[keyType.id];
   }

   private TypeBinding cacheDerivedType(TypeBinding keyType, TypeBinding derivedType) {
      if (keyType != null && derivedType != null && keyType.id != Integer.MAX_VALUE) {
         TypeBinding[] derivedTypes = this.types[keyType.id];
         int length = derivedTypes.length;
         int first = 0;
         int last = length;
         int i = (first + length) / 2;

         do {
            if (derivedTypes[i] == null) {
               if (i == first || i > 0 && derivedTypes[i - 1] != null) {
                  break;
               }

               last = i - 1;
            } else {
               first = i + 1;
            }

            i = (first + last) / 2;
         } while(i < length && first <= last);

         if (i == length) {
            System.arraycopy(derivedTypes, 0, derivedTypes = new TypeBinding[length * 2], 0, length);
            this.types[keyType.id] = derivedTypes;
         }

         return derivedTypes[i] = derivedType;
      } else {
         throw new IllegalStateException();
      }
   }

   protected final TypeBinding cacheDerivedType(TypeBinding keyType, TypeBinding nakedType, TypeBinding derivedType) {
      this.cacheDerivedType(keyType, derivedType);
      if (nakedType.id != keyType.id) {
         this.cacheDerivedType(nakedType, derivedType);
      }

      return derivedType;
   }

   public final AnnotationBinding getAnnotationType(ReferenceBinding annotationType, boolean requiredResolved) {
      AnnotationBinding annotation = (AnnotationBinding)this.annotationTypes.get(annotationType);
      if (annotation == null) {
         if (requiredResolved) {
            annotation = new AnnotationBinding(annotationType, Binding.NO_ELEMENT_VALUE_PAIRS);
         } else {
            annotation = new UnresolvedAnnotationBinding(annotationType, Binding.NO_ELEMENT_VALUE_PAIRS, this.environment);
         }

         this.annotationTypes.put(annotationType, annotation);
      }

      if (requiredResolved) {
         ((AnnotationBinding)annotation).resolve();
      }

      return (AnnotationBinding)annotation;
   }

   public boolean isAnnotatedTypeSystem() {
      return false;
   }

   public void reset() {
      this.annotationTypes = new SimpleLookupTable(16);
      this.typeid = 128;
      this.types = new TypeBinding[256][];
      this.parameterizedTypes = new HashedParameterizedTypes();
   }

   public void updateCaches(UnresolvedReferenceBinding unresolvedType, ReferenceBinding resolvedType) {
      int unresolvedTypeId = unresolvedType.id;
      if (resolvedType.id != Integer.MAX_VALUE) {
         unresolvedType.id = resolvedType.id;
      }

      int i;
      int l;
      if (unresolvedTypeId != Integer.MAX_VALUE) {
         TypeBinding[] derivedTypes = this.types[unresolvedTypeId];
         i = 0;

         for(l = derivedTypes == null ? 0 : derivedTypes.length; i < l && derivedTypes[i] != null; ++i) {
            if (derivedTypes[i] == unresolvedType) {
               if (resolvedType.id == Integer.MAX_VALUE) {
                  resolvedType.id = unresolvedTypeId;
               }

               derivedTypes[i] = resolvedType;
            }
         }
      }

      if (this.annotationTypes.get(unresolvedType) != null) {
         Object[] keys = this.annotationTypes.keyTable;
         i = 0;

         for(l = keys.length; i < l; ++i) {
            if (keys[i] == unresolvedType) {
               keys[i] = resolvedType;
               break;
            }
         }
      }

   }

   public final TypeBinding getIntersectionType18(ReferenceBinding[] intersectingTypes) {
      int intersectingTypesLength = intersectingTypes == null ? 0 : intersectingTypes.length;
      if (intersectingTypesLength == 0) {
         return null;
      } else {
         TypeBinding keyType = intersectingTypes[0];
         if (keyType != null && intersectingTypesLength != 1) {
            TypeBinding[] derivedTypes = this.getDerivedTypes(keyType);
            int length = derivedTypes.length;

            label46:
            for(int i = 0; i < length; ++i) {
               TypeBinding derivedType = derivedTypes[i];
               if (derivedType == null) {
                  break;
               }

               if (derivedType.isIntersectionType18()) {
                  ReferenceBinding[] priorIntersectingTypes = derivedType.getIntersectingTypes();
                  if (priorIntersectingTypes.length == intersectingTypesLength) {
                     for(int j = 0; j < intersectingTypesLength; ++j) {
                        if (intersectingTypes[j] != priorIntersectingTypes[j]) {
                           continue label46;
                        }
                     }

                     return derivedType;
                  }
               }
            }

            return this.cacheDerivedType(keyType, new IntersectionTypeBinding18(intersectingTypes, this.environment));
         } else {
            return keyType;
         }
      }
   }

   public void fixTypeVariableDeclaringElement(TypeVariableBinding var, Binding declaringElement) {
      int id = var.id;
      if (id < this.typeid && this.types[id] != null) {
         TypeBinding[] var7;
         int var6 = (var7 = this.types[id]).length;

         for(int var5 = 0; var5 < var6; ++var5) {
            TypeBinding t = var7[var5];
            if (t instanceof TypeVariableBinding) {
               ((TypeVariableBinding)t).declaringElement = declaringElement;
            }
         }
      } else {
         var.declaringElement = declaringElement;
      }

   }

   public final class HashedParameterizedTypes {
      HashMap hashedParameterizedTypes = new HashMap(256);

      ParameterizedTypeBinding get(ReferenceBinding genericType, TypeBinding[] typeArguments, ReferenceBinding enclosingType, AnnotationBinding[] annotations) {
         ReferenceBinding unannotatedGenericType = (ReferenceBinding)TypeSystem.this.getUnannotatedType(genericType);
         int typeArgumentsLength = typeArguments == null ? 0 : typeArguments.length;
         TypeBinding[] unannotatedTypeArguments = typeArguments == null ? null : new TypeBinding[typeArgumentsLength];

         for(int ix = 0; ix < typeArgumentsLength; ++ix) {
            unannotatedTypeArguments[ix] = TypeSystem.this.getUnannotatedType(typeArguments[ix]);
         }

         ReferenceBinding unannotatedEnclosingType = enclosingType == null ? null : (ReferenceBinding)TypeSystem.this.getUnannotatedType(enclosingType);
         PTBKey key = new PTBKey(unannotatedGenericType, unannotatedTypeArguments, unannotatedEnclosingType, (LookupEnvironment)null);
         ReferenceBinding genericTypeToMatch = unannotatedGenericType;
         ReferenceBinding enclosingTypeToMatch = unannotatedEnclosingType;
         TypeBinding[] typeArgumentsToMatch = unannotatedTypeArguments;
         if (TypeSystem.this instanceof AnnotatableTypeSystem) {
            genericTypeToMatch = genericType;
            enclosingTypeToMatch = enclosingType;
            typeArgumentsToMatch = typeArguments;
         }

         ParameterizedTypeBinding[] parameterizedTypeBindings = (ParameterizedTypeBinding[])this.hashedParameterizedTypes.get(key);
         int i = 0;

         for(int length = parameterizedTypeBindings == null ? 0 : parameterizedTypeBindings.length; i < length; ++i) {
            ParameterizedTypeBinding parameterizedType = parameterizedTypeBindings[i];
            if (parameterizedType.actualType() == genericTypeToMatch && parameterizedType.enclosingType == enclosingTypeToMatch && Util.effectivelyEqual(parameterizedType.typeArguments(), typeArgumentsToMatch) && Util.effectivelyEqual(annotations, parameterizedType.getTypeAnnotations())) {
               return parameterizedType;
            }
         }

         return null;
      }

      void put(ReferenceBinding genericType, TypeBinding[] typeArguments, ReferenceBinding enclosingType, ParameterizedTypeBinding parameterizedType) {
         ReferenceBinding unannotatedGenericType = (ReferenceBinding)TypeSystem.this.getUnannotatedType(genericType);
         int typeArgumentsLength = typeArguments == null ? 0 : typeArguments.length;
         TypeBinding[] unannotatedTypeArguments = typeArguments == null ? null : new TypeBinding[typeArgumentsLength];

         for(int i = 0; i < typeArgumentsLength; ++i) {
            unannotatedTypeArguments[i] = TypeSystem.this.getUnannotatedType(typeArguments[i]);
         }

         ReferenceBinding unannotatedEnclosingType = enclosingType == null ? null : (ReferenceBinding)TypeSystem.this.getUnannotatedType(enclosingType);
         PTBKey key = new PTBKey(unannotatedGenericType, unannotatedTypeArguments, unannotatedEnclosingType, TypeSystem.this.environment);
         ParameterizedTypeBinding[] parameterizedTypeBindings = (ParameterizedTypeBinding[])this.hashedParameterizedTypes.get(key);
         int slot;
         if (parameterizedTypeBindings == null) {
            slot = 0;
            parameterizedTypeBindings = new ParameterizedTypeBinding[1];
         } else {
            slot = parameterizedTypeBindings.length;
            System.arraycopy(parameterizedTypeBindings, 0, parameterizedTypeBindings = new ParameterizedTypeBinding[slot + 1], 0, slot);
         }

         parameterizedTypeBindings[slot] = parameterizedType;
         this.hashedParameterizedTypes.put(key, parameterizedTypeBindings);
      }

      private final class PTBKey extends ReferenceBinding {
         protected ReferenceBinding type;
         public TypeBinding[] arguments;
         private ReferenceBinding enclosingType;

         public PTBKey(ReferenceBinding type, TypeBinding[] arguments, ReferenceBinding enclosingType, LookupEnvironment environment) {
            this.type = type;
            this.arguments = arguments;
            this.enclosingType = enclosingType;
            if (environment != null) {
               if (type instanceof UnresolvedReferenceBinding) {
                  ((UnresolvedReferenceBinding)type).addWrapper(this, environment);
               }

               if (arguments != null) {
                  int i = 0;

                  for(int l = arguments.length; i < l; ++i) {
                     if (arguments[i] instanceof UnresolvedReferenceBinding) {
                        ((UnresolvedReferenceBinding)arguments[i]).addWrapper(this, environment);
                     }

                     if (arguments[i].hasNullTypeAnnotations()) {
                        this.tagBits |= 1048576L;
                     }
                  }
               }
            }

         }

         public void swapUnresolved(UnresolvedReferenceBinding unresolvedType, ReferenceBinding resolvedType, LookupEnvironment env) {
            if (this.type == unresolvedType) {
               this.type = resolvedType;
               ReferenceBinding enclosing = resolvedType.enclosingType();
               if (enclosing != null) {
                  this.enclosingType = resolvedType.isStatic() ? enclosing : (ReferenceBinding)env.convertUnresolvedBinaryToRawType(enclosing);
               }
            }

            if (this.arguments != null) {
               int i = 0;

               for(int l = this.arguments.length; i < l; ++i) {
                  if (this.arguments[i] == unresolvedType) {
                     this.arguments[i] = env.convertUnresolvedBinaryToRawType(resolvedType);
                  }
               }
            }

         }

         public boolean equals(Object other) {
            PTBKey that = (PTBKey)other;
            return this.type == that.type && this.enclosingType == that.enclosingType && Util.effectivelyEqual(this.arguments, that.arguments);
         }

         final int hash(TypeBinding b) {
            return !(b instanceof WildcardBinding) && !(b instanceof TypeVariableBinding) && b.getClass() != ParameterizedTypeBinding.class ? b.hashCode() : System.identityHashCode(b);
         }

         public int hashCode() {
            int hashCode = 1 + this.hash(this.type);
            int i = 0;

            for(int length = this.arguments == null ? 0 : this.arguments.length; i < length; ++i) {
               hashCode = hashCode * 31 + this.hash(this.arguments[i]);
            }

            return hashCode;
         }
      }
   }
}

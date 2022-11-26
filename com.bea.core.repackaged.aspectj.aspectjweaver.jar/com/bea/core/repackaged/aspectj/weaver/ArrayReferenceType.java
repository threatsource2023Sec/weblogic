package com.bea.core.repackaged.aspectj.weaver;

public class ArrayReferenceType extends ReferenceType {
   private final ResolvedType componentType;

   public ArrayReferenceType(String sig, String erasureSig, World world, ResolvedType componentType) {
      super(sig, erasureSig, world);
      this.componentType = componentType;
   }

   public final ResolvedMember[] getDeclaredFields() {
      return ResolvedMember.NONE;
   }

   public final ResolvedMember[] getDeclaredMethods() {
      return ResolvedMember.NONE;
   }

   public final ResolvedType[] getDeclaredInterfaces() {
      return new ResolvedType[]{this.world.getCoreType(CLONEABLE), this.world.getCoreType(SERIALIZABLE)};
   }

   public AnnotationAJ getAnnotationOfType(UnresolvedType ofType) {
      return null;
   }

   public AnnotationAJ[] getAnnotations() {
      return AnnotationAJ.EMPTY_ARRAY;
   }

   public ResolvedType[] getAnnotationTypes() {
      return ResolvedType.NONE;
   }

   public final ResolvedMember[] getDeclaredPointcuts() {
      return ResolvedMember.NONE;
   }

   public boolean hasAnnotation(UnresolvedType ofType) {
      return false;
   }

   public final ResolvedType getSuperclass() {
      return this.world.getCoreType(OBJECT);
   }

   public final boolean isAssignableFrom(ResolvedType o) {
      if (!o.isArray()) {
         return false;
      } else {
         return o.getComponentType().isPrimitiveType() ? o.equals(this) : this.getComponentType().resolve(this.world).isAssignableFrom(o.getComponentType().resolve(this.world));
      }
   }

   public boolean isAssignableFrom(ResolvedType o, boolean allowMissing) {
      return this.isAssignableFrom(o);
   }

   public final boolean isCoerceableFrom(ResolvedType o) {
      if (!o.equals(UnresolvedType.OBJECT) && !o.equals(UnresolvedType.SERIALIZABLE) && !o.equals(UnresolvedType.CLONEABLE)) {
         if (!o.isArray()) {
            return false;
         } else {
            return o.getComponentType().isPrimitiveType() ? o.equals(this) : this.getComponentType().resolve(this.world).isCoerceableFrom(o.getComponentType().resolve(this.world));
         }
      } else {
         return true;
      }
   }

   public final int getModifiers() {
      int mask = 7;
      return this.componentType.getModifiers() & mask | 16;
   }

   public UnresolvedType getComponentType() {
      return this.componentType;
   }

   public ResolvedType getResolvedComponentType() {
      return this.componentType;
   }

   public ISourceContext getSourceContext() {
      return this.getResolvedComponentType().getSourceContext();
   }

   public TypeVariable[] getTypeVariables() {
      if (this.typeVariables == null && this.componentType.getTypeVariables() != null) {
         this.typeVariables = this.componentType.getTypeVariables();

         for(int i = 0; i < this.typeVariables.length; ++i) {
            this.typeVariables[i].resolve(this.world);
         }
      }

      return this.typeVariables;
   }

   public boolean isAnnotation() {
      return false;
   }

   public boolean isAnonymous() {
      return false;
   }

   public boolean isAnnotationStyleAspect() {
      return false;
   }

   public boolean isAspect() {
      return false;
   }

   public boolean isPrimitiveType() {
      return this.typeKind == UnresolvedType.TypeKind.PRIMITIVE;
   }

   public boolean isSimpleType() {
      return this.typeKind == UnresolvedType.TypeKind.SIMPLE;
   }

   public boolean isRawType() {
      return this.typeKind == UnresolvedType.TypeKind.RAW;
   }

   public boolean isGenericType() {
      return this.typeKind == UnresolvedType.TypeKind.GENERIC;
   }

   public boolean isParameterizedType() {
      return this.typeKind == UnresolvedType.TypeKind.PARAMETERIZED;
   }

   public boolean isTypeVariableReference() {
      return this.typeKind == UnresolvedType.TypeKind.TYPE_VARIABLE;
   }

   public boolean isGenericWildcard() {
      return this.typeKind == UnresolvedType.TypeKind.WILDCARD;
   }

   public boolean isEnum() {
      return false;
   }

   public boolean isNested() {
      return false;
   }

   public boolean isClass() {
      return false;
   }

   public boolean isExposedToWeaver() {
      return false;
   }

   public boolean canAnnotationTargetType() {
      return false;
   }

   public AnnotationTargetKind[] getAnnotationTargetKinds() {
      return null;
   }

   public boolean isAnnotationWithRuntimeRetention() {
      return false;
   }

   public boolean isPrimitiveArray() {
      if (this.componentType.isPrimitiveType()) {
         return true;
      } else {
         return this.componentType.isArray() ? this.componentType.isPrimitiveArray() : false;
      }
   }
}

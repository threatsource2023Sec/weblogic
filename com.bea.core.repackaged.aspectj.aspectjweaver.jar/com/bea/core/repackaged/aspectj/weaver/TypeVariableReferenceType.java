package com.bea.core.repackaged.aspectj.weaver;

import java.util.Map;

public class TypeVariableReferenceType extends ReferenceType implements TypeVariableReference {
   private TypeVariable typeVariable;

   public TypeVariableReferenceType(TypeVariable typeVariable, World world) {
      super(typeVariable.getGenericSignature(), typeVariable.getErasureSignature(), world);
      this.typeVariable = typeVariable;
   }

   public boolean equals(Object other) {
      if (other instanceof TypeVariableReferenceType) {
         return this.typeVariable == ((TypeVariableReferenceType)other).typeVariable;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.typeVariable.hashCode();
   }

   public ReferenceTypeDelegate getDelegate() {
      if (this.delegate == null) {
         ResolvedType resolvedFirstBound = this.typeVariable.getFirstBound().resolve(this.world);
         BoundedReferenceTypeDelegate brtd = null;
         if (resolvedFirstBound.isMissing()) {
            brtd = new BoundedReferenceTypeDelegate((ReferenceType)this.world.resolve(UnresolvedType.OBJECT));
            this.setDelegate(brtd);
            this.world.getLint().cantFindType.signal("Unable to find type for generic bound.  Missing type is " + resolvedFirstBound.getName(), this.getSourceLocation());
         } else {
            brtd = new BoundedReferenceTypeDelegate((ReferenceType)resolvedFirstBound);
            this.setDelegate(brtd);
         }
      }

      return this.delegate;
   }

   public UnresolvedType parameterize(Map typeBindings) {
      UnresolvedType ut = (UnresolvedType)typeBindings.get(this.getName());
      return (UnresolvedType)(ut != null ? this.world.resolve(ut) : this);
   }

   public TypeVariable getTypeVariable() {
      return this.typeVariable;
   }

   public boolean isTypeVariableReference() {
      return true;
   }

   public String toString() {
      return this.typeVariable.getName();
   }

   public boolean isGenericWildcard() {
      return false;
   }

   public boolean isAnnotation() {
      ReferenceType upper = (ReferenceType)this.typeVariable.getUpperBound();
      if (upper.isAnnotation()) {
         return true;
      } else {
         World world = upper.getWorld();
         this.typeVariable.resolve(world);
         ResolvedType annotationType = ResolvedType.ANNOTATION.resolve(world);
         UnresolvedType[] ifBounds = this.typeVariable.getSuperInterfaces();

         for(int i = 0; i < ifBounds.length; ++i) {
            if (((ReferenceType)ifBounds[i]).isAnnotation()) {
               return true;
            }

            if (ifBounds[i].equals(annotationType)) {
               return true;
            }
         }

         return false;
      }
   }

   public String getSignature() {
      StringBuffer sb = new StringBuffer();
      sb.append("T");
      sb.append(this.typeVariable.getName());
      sb.append(";");
      return sb.toString();
   }

   public String getTypeVariableName() {
      return this.typeVariable.getName();
   }

   public ReferenceType getUpperBound() {
      return (ReferenceType)this.typeVariable.resolve(this.world).getUpperBound();
   }

   public ResolvedType resolve(World world) {
      this.typeVariable.resolve(world);
      return this;
   }

   public boolean isTypeVariableResolved() {
      return this.typeVariable.isResolved;
   }
}

package com.bea.core.repackaged.aspectj.weaver;

public class UnresolvedTypeVariableReferenceType extends UnresolvedType implements TypeVariableReference {
   private TypeVariable typeVariable;

   public UnresolvedTypeVariableReferenceType() {
      super("Ljava/lang/Object;");
   }

   public UnresolvedTypeVariableReferenceType(TypeVariable aTypeVariable) {
      super("T" + aTypeVariable.getName() + ";", aTypeVariable.getFirstBound().getErasureSignature());
      this.typeVariable = aTypeVariable;
   }

   public void setTypeVariable(TypeVariable aTypeVariable) {
      this.signature = "T" + aTypeVariable.getName() + ";";
      this.signatureErasure = aTypeVariable.getFirstBound().getErasureSignature();
      this.typeVariable = aTypeVariable;
      this.typeKind = UnresolvedType.TypeKind.TYPE_VARIABLE;
   }

   public ResolvedType resolve(World world) {
      TypeVariableDeclaringElement typeVariableScope = world.getTypeVariableLookupScope();
      TypeVariable resolvedTypeVariable = null;
      TypeVariableReferenceType tvrt = null;
      if (typeVariableScope == null) {
         resolvedTypeVariable = this.typeVariable.resolve(world);
         tvrt = new TypeVariableReferenceType(resolvedTypeVariable, world);
      } else {
         boolean foundOK = false;
         resolvedTypeVariable = typeVariableScope.getTypeVariableNamed(this.typeVariable.getName());
         if (resolvedTypeVariable == null) {
            resolvedTypeVariable = this.typeVariable.resolve(world);
         } else {
            foundOK = true;
         }

         tvrt = new TypeVariableReferenceType(resolvedTypeVariable, world);
      }

      return tvrt;
   }

   public boolean isTypeVariableReference() {
      return true;
   }

   public TypeVariable getTypeVariable() {
      return this.typeVariable;
   }

   public String toString() {
      return this.typeVariable == null ? "<type variable not set!>" : "T" + this.typeVariable.getName() + ";";
   }

   public String toDebugString() {
      return this.typeVariable.getName();
   }

   public String getErasureSignature() {
      return this.typeVariable.getFirstBound().getSignature();
   }
}

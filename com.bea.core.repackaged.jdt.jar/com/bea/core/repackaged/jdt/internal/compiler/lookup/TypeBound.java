package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public class TypeBound extends ReductionResult {
   InferenceVariable left;
   boolean isSoft;
   long nullHints;

   static TypeBound createBoundOrDependency(InferenceSubstitution theta, TypeBinding type, InferenceVariable variable) {
      return new TypeBound(variable, theta.substitute(theta, type), 2, true);
   }

   TypeBound(InferenceVariable inferenceVariable, TypeBinding typeBinding, int relation) {
      this(inferenceVariable, typeBinding, relation, false);
   }

   TypeBound(InferenceVariable inferenceVariable, TypeBinding typeBinding, int relation, boolean isSoft) {
      this.left = inferenceVariable;
      this.right = typeBinding;
      if (((inferenceVariable.tagBits | this.right.tagBits) & 108086391056891904L) != 0L) {
         if ((inferenceVariable.tagBits & 108086391056891904L) == (this.right.tagBits & 108086391056891904L)) {
            this.left = (InferenceVariable)inferenceVariable.withoutToplevelNullAnnotation();
            this.right = this.right.withoutToplevelNullAnnotation();
         } else {
            long mask = 0L;
            switch (relation) {
               case 2:
                  mask = 72057594037927936L;
                  break;
               case 3:
                  mask = 36028797018963968L;
                  break;
               case 4:
                  mask = 108086391056891904L;
            }

            InferenceVariable var10000 = inferenceVariable.prototype();
            var10000.nullHints |= this.right.tagBits & mask;
         }
      }

      this.relation = relation;
      this.isSoft = isSoft;
   }

   boolean isBound() {
      return this.right.isProperType(true);
   }

   public int hashCode() {
      return this.left.hashCode() + this.right.hashCode() + this.relation;
   }

   public boolean equals(Object obj) {
      if (obj instanceof TypeBound) {
         TypeBound other = (TypeBound)obj;
         return this.relation == other.relation && TypeBinding.equalsEquals(this.left, other.left) && TypeBinding.equalsEquals(this.right, other.right);
      } else {
         return false;
      }
   }

   public String toString() {
      boolean isBound = this.right.isProperType(true);
      StringBuffer buf = new StringBuffer();
      buf.append(isBound ? "TypeBound  " : "Dependency ");
      buf.append(this.left.sourceName);
      buf.append(relationToString(this.relation));
      buf.append(this.right.readableName());
      return buf.toString();
   }
}

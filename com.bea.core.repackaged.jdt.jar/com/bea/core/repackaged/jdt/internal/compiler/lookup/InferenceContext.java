package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public class InferenceContext {
   private TypeBinding[][][] collectedSubstitutes;
   MethodBinding genericMethod;
   int depth;
   int status;
   TypeBinding expectedType;
   boolean hasExplicitExpectedType;
   public boolean isUnchecked;
   TypeBinding[] substitutes;
   static final int FAILED = 1;

   public InferenceContext(MethodBinding genericMethod) {
      this.genericMethod = genericMethod;
      TypeVariableBinding[] typeVariables = genericMethod.typeVariables;
      int varLength = typeVariables.length;
      this.collectedSubstitutes = new TypeBinding[varLength][3][];
      this.substitutes = new TypeBinding[varLength];
   }

   public TypeBinding[] getSubstitutes(TypeVariableBinding typeVariable, int constraint) {
      return this.collectedSubstitutes[typeVariable.rank][constraint];
   }

   public boolean hasUnresolvedTypeArgument() {
      int i = 0;

      for(int varLength = this.substitutes.length; i < varLength; ++i) {
         if (this.substitutes[i] == null) {
            return true;
         }
      }

      return false;
   }

   public void recordSubstitute(TypeVariableBinding typeVariable, TypeBinding actualType, int constraint) {
      TypeBinding[][] variableSubstitutes = this.collectedSubstitutes[typeVariable.rank];
      TypeBinding[] constraintSubstitutes = variableSubstitutes[constraint];
      int length;
      if (constraintSubstitutes == null) {
         length = 0;
         constraintSubstitutes = new TypeBinding[1];
      } else {
         length = constraintSubstitutes.length;

         for(int i = 0; i < length; ++i) {
            TypeBinding substitute = constraintSubstitutes[i];
            if (substitute == actualType) {
               return;
            }

            if (substitute == null) {
               constraintSubstitutes[i] = actualType;
               return;
            }
         }

         System.arraycopy(constraintSubstitutes, 0, constraintSubstitutes = new TypeBinding[length + 1], 0, length);
      }

      constraintSubstitutes[length] = actualType;
      variableSubstitutes[constraint] = constraintSubstitutes;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer(20);
      buffer.append("InferenceContex for ");
      int i = 0;

      int i;
      for(i = this.genericMethod.typeVariables.length; i < i; ++i) {
         buffer.append(this.genericMethod.typeVariables[i]);
      }

      buffer.append(this.genericMethod);
      buffer.append("\n\t[status=");
      switch (this.status) {
         case 0:
            buffer.append("ok]");
            break;
         case 1:
            buffer.append("failed]");
      }

      if (this.expectedType == null) {
         buffer.append(" [expectedType=null]");
      } else {
         buffer.append(" [expectedType=").append(this.expectedType.shortReadableName()).append(']');
      }

      buffer.append(" [depth=").append(this.depth).append(']');
      buffer.append("\n\t[collected={");
      i = 0;

      for(i = this.collectedSubstitutes == null ? 0 : this.collectedSubstitutes.length; i < i; ++i) {
         TypeBinding[][] collected = this.collectedSubstitutes[i];

         for(int j = 0; j <= 2; ++j) {
            TypeBinding[] constraintCollected = collected[j];
            if (constraintCollected != null) {
               int k = 0;

               for(int clength = constraintCollected.length; k < clength; ++k) {
                  buffer.append("\n\t\t").append(this.genericMethod.typeVariables[i].sourceName);
                  switch (j) {
                     case 0:
                        buffer.append("=");
                        break;
                     case 1:
                        buffer.append("<:");
                        break;
                     case 2:
                        buffer.append(">:");
                  }

                  if (constraintCollected[k] != null) {
                     buffer.append(constraintCollected[k].shortReadableName());
                  }
               }
            }
         }
      }

      buffer.append("}]");
      buffer.append("\n\t[inferred=");
      i = 0;
      i = 0;

      for(int length = this.substitutes == null ? 0 : this.substitutes.length; i < length; ++i) {
         if (this.substitutes[i] != null) {
            ++i;
            buffer.append('{').append(this.genericMethod.typeVariables[i].sourceName);
            buffer.append("=").append(this.substitutes[i].shortReadableName()).append('}');
         }
      }

      if (i == 0) {
         buffer.append("{}");
      }

      buffer.append(']');
      return buffer.toString();
   }
}

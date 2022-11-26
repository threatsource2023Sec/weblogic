package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import java.util.Map;
import java.util.Set;

public class InferenceVariable extends TypeVariableBinding {
   InvocationSite site;
   TypeBinding typeParameter;
   long nullHints;
   private InferenceVariable prototype;
   int varId;
   public boolean isFromInitialSubstitution;

   public static InferenceVariable get(TypeBinding typeParameter, int rank, InvocationSite site, Scope scope, ReferenceBinding object, boolean initial) {
      Map uniqueInferenceVariables = scope.compilationUnitScope().uniqueInferenceVariables;
      InferenceVariable var = null;
      InferenceVarKey key = null;
      if (site != null && typeParameter != null) {
         key = new InferenceVarKey(typeParameter, site, rank);
         var = (InferenceVariable)uniqueInferenceVariables.get(key);
      }

      if (var == null) {
         int newVarId = uniqueInferenceVariables.size();
         var = new InferenceVariable(typeParameter, rank, newVarId, site, scope.environment(), object, initial);
         if (key != null) {
            uniqueInferenceVariables.put(key, var);
         }
      }

      return var;
   }

   private InferenceVariable(TypeBinding typeParameter, int parameterRank, int iVarId, InvocationSite site, LookupEnvironment environment, ReferenceBinding object, boolean initial) {
      this(typeParameter, parameterRank, site, makeName(typeParameter, iVarId), environment, object);
      this.varId = iVarId;
      this.isFromInitialSubstitution = initial;
   }

   private static char[] makeName(TypeBinding typeParameter, int iVarId) {
      return typeParameter.getClass() == TypeVariableBinding.class ? CharOperation.concat(typeParameter.shortReadableName(), Integer.toString(iVarId).toCharArray(), '#') : CharOperation.concat(CharOperation.concat('(', typeParameter.shortReadableName(), ')'), Integer.toString(iVarId).toCharArray(), '#');
   }

   private InferenceVariable(TypeBinding typeParameter, int parameterRank, InvocationSite site, char[] sourceName, LookupEnvironment environment, ReferenceBinding object) {
      super(sourceName, (Binding)null, parameterRank, environment);
      this.site = site;
      this.typeParameter = typeParameter;
      this.tagBits |= typeParameter.tagBits & 108086391056891904L;
      if (typeParameter.isTypeVariable()) {
         TypeVariableBinding typeVariable = (TypeVariableBinding)typeParameter;
         if (typeVariable.firstBound != null) {
            long boundBits = typeVariable.firstBound.tagBits & 108086391056891904L;
            if (boundBits == 72057594037927936L) {
               this.tagBits |= boundBits;
            } else {
               this.nullHints |= boundBits;
            }
         }
      }

      this.superclass = object;
      this.prototype = this;
   }

   public TypeBinding clone(TypeBinding enclosingType) {
      InferenceVariable clone = new InferenceVariable(this.typeParameter, this.rank, this.site, this.sourceName, this.environment, this.superclass);
      clone.tagBits = this.tagBits;
      clone.nullHints = this.nullHints;
      clone.varId = this.varId;
      clone.isFromInitialSubstitution = this.isFromInitialSubstitution;
      clone.prototype = this;
      return clone;
   }

   public InferenceVariable prototype() {
      return this.prototype;
   }

   public char[] constantPoolName() {
      throw new UnsupportedOperationException();
   }

   public PackageBinding getPackage() {
      throw new UnsupportedOperationException();
   }

   public boolean isCompatibleWith(TypeBinding right, Scope scope) {
      return true;
   }

   public boolean isProperType(boolean admitCapture18) {
      return false;
   }

   TypeBinding substituteInferenceVariable(InferenceVariable var, TypeBinding substituteType) {
      return (TypeBinding)(TypeBinding.equalsEquals(this, var) ? substituteType : this);
   }

   void collectInferenceVariables(Set variables) {
      variables.add(this);
   }

   public ReferenceBinding[] superInterfaces() {
      return Binding.NO_SUPERINTERFACES;
   }

   public char[] qualifiedSourceName() {
      throw new UnsupportedOperationException();
   }

   public char[] sourceName() {
      return this.sourceName;
   }

   public char[] readableName() {
      return this.sourceName;
   }

   public boolean hasTypeBit(int bit) {
      throw new UnsupportedOperationException();
   }

   public String debugName() {
      return String.valueOf(this.sourceName);
   }

   public String toString() {
      return this.debugName();
   }

   public int hashCode() {
      int code = this.typeParameter.hashCode() + 17 * this.rank;
      if (this.site != null) {
         code = 31 * code + this.site.sourceStart();
         code = 31 * code + this.site.sourceEnd();
      }

      return code;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof InferenceVariable)) {
         return false;
      } else {
         InferenceVariable other = (InferenceVariable)obj;
         return this.rank == other.rank && InferenceContext18.isSameSite(this.site, other.site) && TypeBinding.equalsEquals(this.typeParameter, other.typeParameter);
      }
   }

   public TypeBinding erasure() {
      if (this.superclass == null) {
         this.superclass = this.environment.getType(TypeConstants.JAVA_LANG_OBJECT);
      }

      return super.erasure();
   }

   static class InferenceVarKey {
      TypeBinding typeParameter;
      long position;
      int rank;

      InferenceVarKey(TypeBinding typeParameter, InvocationSite site, int rank) {
         this.typeParameter = typeParameter;
         this.position = ((long)site.sourceStart() << 32) + (long)site.sourceEnd();
         this.rank = rank;
      }

      public int hashCode() {
         int result = 1;
         result = 31 * result + (int)(this.position ^ this.position >>> 32);
         result = 31 * result + this.rank;
         result = 31 * result + this.typeParameter.id;
         return result;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (!(obj instanceof InferenceVarKey)) {
            return false;
         } else {
            InferenceVarKey other = (InferenceVarKey)obj;
            if (this.position != other.position) {
               return false;
            } else if (this.rank != other.rank) {
               return false;
            } else {
               return !TypeBinding.notEquals(this.typeParameter, other.typeParameter);
            }
         }
      }
   }
}

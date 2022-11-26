package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.CaseStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.LambdaExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeReference;

public final class LocalTypeBinding extends NestedTypeBinding {
   static final char[] LocalTypePrefix = new char[]{'$', 'L', 'o', 'c', 'a', 'l', '$'};
   private InnerEmulationDependency[] dependents;
   public CaseStatement enclosingCase;
   public int sourceStart;
   public MethodBinding enclosingMethod;

   public LocalTypeBinding(ClassScope scope, SourceTypeBinding enclosingType, CaseStatement switchCase) {
      super(new char[][]{CharOperation.concat(LocalTypePrefix, scope.referenceContext.name)}, scope, enclosingType);
      TypeDeclaration typeDeclaration = scope.referenceContext;
      if ((typeDeclaration.bits & 512) != 0) {
         this.tagBits |= 2100L;
      } else {
         this.tagBits |= 2068L;
      }

      this.enclosingCase = switchCase;
      this.sourceStart = typeDeclaration.sourceStart;
      MethodScope methodScope = scope.enclosingMethodScope();
      MethodBinding methodBinding = methodScope.referenceMethodBinding();
      if (methodBinding != null) {
         this.enclosingMethod = methodBinding;
      }

      MethodScope lambdaScope = scope.enclosingLambdaScope();
      if (lambdaScope != null) {
         ((LambdaExpression)lambdaScope.referenceContext).addLocalType(this);
      }

   }

   public LocalTypeBinding(LocalTypeBinding prototype) {
      super(prototype);
      this.dependents = prototype.dependents;
      this.enclosingCase = prototype.enclosingCase;
      this.sourceStart = prototype.sourceStart;
      this.enclosingMethod = prototype.enclosingMethod;
   }

   public void addInnerEmulationDependent(BlockScope dependentScope, boolean wasEnclosingInstanceSupplied) {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         int index;
         if (this.dependents == null) {
            index = 0;
            this.dependents = new InnerEmulationDependency[1];
         } else {
            index = this.dependents.length;

            for(int i = 0; i < index; ++i) {
               if (this.dependents[i].scope == dependentScope) {
                  return;
               }
            }

            System.arraycopy(this.dependents, 0, this.dependents = new InnerEmulationDependency[index + 1], 0, index);
         }

         this.dependents[index] = new InnerEmulationDependency(dependentScope, wasEnclosingInstanceSupplied);
      }
   }

   public MethodBinding enclosingMethod() {
      return this.enclosingMethod;
   }

   public ReferenceBinding anonymousOriginalSuperType() {
      if (!this.isPrototype()) {
         return ((LocalTypeBinding)this.prototype).anonymousOriginalSuperType();
      } else if (this.superclass == null && this.scope != null) {
         return this.scope.getJavaLangObject();
      } else if (this.superInterfaces != Binding.NO_SUPERINTERFACES) {
         return this.superInterfaces[0];
      } else if ((this.tagBits & 131072L) == 0L) {
         return this.superclass;
      } else {
         if (this.scope != null) {
            TypeReference typeReference = this.scope.referenceContext.allocation.type;
            if (typeReference != null) {
               return (ReferenceBinding)typeReference.resolvedType;
            }
         }

         return this.superclass;
      }
   }

   public char[] computeUniqueKey(boolean isLeaf) {
      if (!this.isPrototype()) {
         return this.prototype.computeUniqueKey(isLeaf);
      } else {
         char[] outerKey = this.outermostEnclosingType().computeUniqueKey(isLeaf);
         int semicolon = CharOperation.lastIndexOf(';', outerKey);
         StringBuffer sig = new StringBuffer();
         sig.append(outerKey, 0, semicolon);
         sig.append('$');
         sig.append(String.valueOf(this.sourceStart));
         if (!this.isAnonymousType()) {
            sig.append('$');
            sig.append(this.sourceName);
         }

         sig.append(outerKey, semicolon, outerKey.length - semicolon);
         int sigLength = sig.length();
         char[] uniqueKey = new char[sigLength];
         sig.getChars(0, sigLength, uniqueKey, 0);
         return uniqueKey;
      }
   }

   public char[] constantPoolName() {
      if (this.constantPoolName != null) {
         return this.constantPoolName;
      } else if (!this.isPrototype()) {
         return this.constantPoolName = this.prototype.constantPoolName();
      } else {
         if (this.constantPoolName == null && this.scope != null) {
            this.constantPoolName = this.scope.compilationUnitScope().computeConstantPoolName(this);
         }

         return this.constantPoolName;
      }
   }

   public TypeBinding clone(TypeBinding outerType) {
      LocalTypeBinding copy = new LocalTypeBinding(this);
      copy.enclosingType = (SourceTypeBinding)outerType;
      return copy;
   }

   public int hashCode() {
      return this.enclosingType.hashCode();
   }

   public char[] genericTypeSignature() {
      if (!this.isPrototype()) {
         return this.prototype.genericTypeSignature();
      } else {
         if (this.genericReferenceTypeSignature == null && this.constantPoolName == null) {
            if (this.isAnonymousType()) {
               this.setConstantPoolName(this.superclass().sourceName());
            } else {
               this.setConstantPoolName(this.sourceName());
            }
         }

         return super.genericTypeSignature();
      }
   }

   public char[] readableName() {
      char[] readableName;
      if (this.isAnonymousType()) {
         readableName = CharOperation.concat(TypeConstants.ANONYM_PREFIX, this.anonymousOriginalSuperType().readableName(), TypeConstants.ANONYM_SUFFIX);
      } else if (this.isMemberType()) {
         readableName = CharOperation.concat(this.enclosingType().readableName(), this.sourceName, '.');
      } else {
         readableName = this.sourceName;
      }

      TypeVariableBinding[] typeVars;
      if ((typeVars = this.typeVariables()) != Binding.NO_TYPE_VARIABLES) {
         StringBuffer nameBuffer = new StringBuffer(10);
         nameBuffer.append(readableName).append('<');
         int i = 0;

         for(int length = typeVars.length; i < length; ++i) {
            if (i > 0) {
               nameBuffer.append(',');
            }

            nameBuffer.append(typeVars[i].readableName());
         }

         nameBuffer.append('>');
         i = nameBuffer.length();
         readableName = new char[i];
         nameBuffer.getChars(0, i, readableName, 0);
      }

      return readableName;
   }

   public char[] shortReadableName() {
      char[] shortReadableName;
      if (this.isAnonymousType()) {
         shortReadableName = CharOperation.concat(TypeConstants.ANONYM_PREFIX, this.anonymousOriginalSuperType().shortReadableName(), TypeConstants.ANONYM_SUFFIX);
      } else if (this.isMemberType()) {
         shortReadableName = CharOperation.concat(this.enclosingType().shortReadableName(), this.sourceName, '.');
      } else {
         shortReadableName = this.sourceName;
      }

      TypeVariableBinding[] typeVars;
      if ((typeVars = this.typeVariables()) != Binding.NO_TYPE_VARIABLES) {
         StringBuffer nameBuffer = new StringBuffer(10);
         nameBuffer.append(shortReadableName).append('<');
         int i = 0;

         for(int length = typeVars.length; i < length; ++i) {
            if (i > 0) {
               nameBuffer.append(',');
            }

            nameBuffer.append(typeVars[i].shortReadableName());
         }

         nameBuffer.append('>');
         i = nameBuffer.length();
         shortReadableName = new char[i];
         nameBuffer.getChars(0, i, shortReadableName, 0);
      }

      return shortReadableName;
   }

   public void setAsMemberType() {
      if (!this.isPrototype()) {
         this.tagBits |= 2060L;
         ((LocalTypeBinding)this.prototype).setAsMemberType();
      } else {
         this.tagBits |= 2060L;
      }
   }

   public void setConstantPoolName(char[] computedConstantPoolName) {
      if (!this.isPrototype()) {
         this.constantPoolName = computedConstantPoolName;
         ((LocalTypeBinding)this.prototype).setConstantPoolName(computedConstantPoolName);
      } else {
         this.constantPoolName = computedConstantPoolName;
      }
   }

   public void transferConstantPoolNameTo(TypeBinding substType) {
      if (this.constantPoolName != null && substType instanceof LocalTypeBinding) {
         LocalTypeBinding substLocalType = (LocalTypeBinding)substType;
         if (substLocalType.constantPoolName == null) {
            substLocalType.setConstantPoolName(this.constantPoolName);
            this.scope.compilationUnitScope().constantPoolNameUsage.put(substLocalType.constantPoolName, substLocalType);
         }
      }

   }

   public char[] signature() {
      if (!this.isPrototype()) {
         return this.prototype.signature();
      } else {
         if (this.signature == null && this.constantPoolName == null) {
            if (this.isAnonymousType()) {
               this.setConstantPoolName(this.superclass().sourceName());
            } else {
               this.setConstantPoolName(this.sourceName());
            }
         }

         return super.signature();
      }
   }

   public char[] sourceName() {
      return this.isAnonymousType() ? CharOperation.concat(TypeConstants.ANONYM_PREFIX, this.anonymousOriginalSuperType().sourceName(), TypeConstants.ANONYM_SUFFIX) : this.sourceName;
   }

   public String toString() {
      if (this.hasTypeAnnotations()) {
         return this.annotatedDebugName() + " (local)";
      } else if (this.isAnonymousType()) {
         return "Anonymous type : " + super.toString();
      } else {
         return this.isMemberType() ? "Local member type : " + new String(this.sourceName()) + " " + super.toString() : "Local type : " + new String(this.sourceName()) + " " + super.toString();
      }
   }

   public void updateInnerEmulationDependents() {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else {
         if (this.dependents != null) {
            for(int i = 0; i < this.dependents.length; ++i) {
               InnerEmulationDependency dependency = this.dependents[i];
               dependency.scope.propagateInnerEmulation(this, dependency.wasEnclosingInstanceSupplied);
            }
         }

      }
   }
}

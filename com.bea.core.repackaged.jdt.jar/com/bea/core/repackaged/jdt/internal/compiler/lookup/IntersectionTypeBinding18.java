package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.InvalidInputException;
import java.util.Set;

public class IntersectionTypeBinding18 extends ReferenceBinding {
   public ReferenceBinding[] intersectingTypes;
   private ReferenceBinding javaLangObject;
   int length;

   public IntersectionTypeBinding18(ReferenceBinding[] intersectingTypes, LookupEnvironment environment) {
      this.intersectingTypes = intersectingTypes;
      this.length = intersectingTypes.length;
      if (!intersectingTypes[0].isClass()) {
         this.javaLangObject = environment.getResolvedJavaBaseType(TypeConstants.JAVA_LANG_OBJECT, (Scope)null);
         this.modifiers |= 512;
      }

   }

   private IntersectionTypeBinding18(IntersectionTypeBinding18 prototype) {
      this.intersectingTypes = prototype.intersectingTypes;
      this.length = prototype.length;
      if (!this.intersectingTypes[0].isClass()) {
         this.javaLangObject = prototype.javaLangObject;
         this.modifiers |= 512;
      }

   }

   public TypeBinding clone(TypeBinding enclosingType) {
      return new IntersectionTypeBinding18(this);
   }

   protected MethodBinding[] getInterfaceAbstractContracts(Scope scope, boolean replaceWildcards, boolean filterDefaultMethods) throws InvalidInputException {
      int typesLength = this.intersectingTypes.length;
      MethodBinding[][] methods = new MethodBinding[typesLength][];
      int contractsLength = 0;

      for(int i = 0; i < typesLength; ++i) {
         methods[i] = this.intersectingTypes[i].getInterfaceAbstractContracts(scope, replaceWildcards, true);
         contractsLength += methods[i].length;
      }

      MethodBinding[] contracts = new MethodBinding[contractsLength];
      int idx = 0;

      for(int i = 0; i < typesLength; ++i) {
         int len = methods[i].length;
         System.arraycopy(methods[i], 0, contracts, idx, len);
         idx += len;
      }

      return contracts;
   }

   public boolean hasTypeBit(int bit) {
      for(int i = 0; i < this.length; ++i) {
         if (this.intersectingTypes[i].hasTypeBit(bit)) {
            return true;
         }
      }

      return false;
   }

   public boolean canBeInstantiated() {
      return false;
   }

   public boolean canBeSeenBy(PackageBinding invocationPackage) {
      for(int i = 0; i < this.length; ++i) {
         if (!this.intersectingTypes[i].canBeSeenBy(invocationPackage)) {
            return false;
         }
      }

      return true;
   }

   public boolean canBeSeenBy(Scope scope) {
      for(int i = 0; i < this.length; ++i) {
         if (!this.intersectingTypes[i].canBeSeenBy(scope)) {
            return false;
         }
      }

      return true;
   }

   public boolean canBeSeenBy(ReferenceBinding receiverType, ReferenceBinding invocationType) {
      for(int i = 0; i < this.length; ++i) {
         if (!this.intersectingTypes[i].canBeSeenBy(receiverType, invocationType)) {
            return false;
         }
      }

      return true;
   }

   public char[] constantPoolName() {
      TypeBinding erasure = this.erasure();
      if (erasure != this) {
         return erasure.constantPoolName();
      } else {
         return this.intersectingTypes[0].id == 1 && this.intersectingTypes.length > 1 ? this.intersectingTypes[1].constantPoolName() : this.intersectingTypes[0].constantPoolName();
      }
   }

   public PackageBinding getPackage() {
      throw new UnsupportedOperationException();
   }

   public ReferenceBinding[] getIntersectingTypes() {
      return this.intersectingTypes;
   }

   public ReferenceBinding superclass() {
      return this.intersectingTypes[0].isClass() ? this.intersectingTypes[0] : this.javaLangObject;
   }

   public ReferenceBinding[] superInterfaces() {
      if (this.intersectingTypes[0].isClass()) {
         ReferenceBinding[] superInterfaces = new ReferenceBinding[this.length - 1];
         System.arraycopy(this.intersectingTypes, 1, superInterfaces, 0, this.length - 1);
         return superInterfaces;
      } else {
         return this.intersectingTypes;
      }
   }

   public boolean isBoxedPrimitiveType() {
      return this.intersectingTypes[0].isBoxedPrimitiveType();
   }

   public boolean isCompatibleWith(TypeBinding right, Scope scope) {
      if (TypeBinding.equalsEquals(this, right)) {
         return true;
      } else {
         int rightKind = right.kind();
         TypeBinding[] rightIntersectingTypes = null;
         if (rightKind == 8196 && right.boundKind() == 1) {
            TypeBinding allRightBounds = ((WildcardBinding)right).allBounds();
            if (allRightBounds instanceof IntersectionTypeBinding18) {
               rightIntersectingTypes = ((IntersectionTypeBinding18)allRightBounds).intersectingTypes;
            }
         } else if (rightKind == 32772) {
            rightIntersectingTypes = ((IntersectionTypeBinding18)right).intersectingTypes;
         }

         if (rightIntersectingTypes != null) {
            ReferenceBinding[] var8 = rightIntersectingTypes;
            int var7 = rightIntersectingTypes.length;

            label44:
            for(int var6 = 0; var6 < var7; ++var6) {
               TypeBinding required = var8[var6];
               ReferenceBinding[] var12;
               int var11 = (var12 = this.intersectingTypes).length;

               for(int var10 = 0; var10 < var11; ++var10) {
                  TypeBinding provided = var12[var10];
                  if (provided.isCompatibleWith(required, scope)) {
                     continue label44;
                  }
               }

               return false;
            }

            return true;
         } else {
            for(int i = 0; i < this.length; ++i) {
               if (this.intersectingTypes[i].isCompatibleWith(right, scope)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public boolean isSubtypeOf(TypeBinding other, boolean simulatingBugJDK8026527) {
      if (TypeBinding.equalsEquals(this, other)) {
         return true;
      } else {
         if (other instanceof ReferenceBinding) {
            TypeBinding[] rightIntersectingTypes = ((ReferenceBinding)other).getIntersectingTypes();
            if (rightIntersectingTypes != null && rightIntersectingTypes.length > 1) {
               int numRequired = rightIntersectingTypes.length;
               TypeBinding[] required = new TypeBinding[numRequired];
               System.arraycopy(rightIntersectingTypes, 0, required, 0, numRequired);

               for(int i = 0; i < this.length; ++i) {
                  TypeBinding provided = this.intersectingTypes[i];

                  for(int j = 0; j < required.length; ++j) {
                     if (required[j] != null && provided.isSubtypeOf(required[j], simulatingBugJDK8026527)) {
                        required[j] = null;
                        --numRequired;
                        if (numRequired == 0) {
                           return true;
                        }
                        break;
                     }
                  }
               }

               return false;
            }
         }

         for(int i = 0; i < this.intersectingTypes.length; ++i) {
            if (this.intersectingTypes[i].isSubtypeOf(other, false)) {
               return true;
            }
         }

         return false;
      }
   }

   public TypeBinding erasure() {
      int classIdx = -1;

      for(int i = 0; i < this.intersectingTypes.length; ++i) {
         if (this.intersectingTypes[i].isClass() && this.intersectingTypes[i].id != 1) {
            if (classIdx != -1) {
               classIdx = Integer.MAX_VALUE;
               break;
            }

            classIdx = i;
         }
      }

      return (TypeBinding)(classIdx > -1 && classIdx < Integer.MAX_VALUE ? this.intersectingTypes[classIdx].erasure() : this);
   }

   public char[] qualifiedSourceName() {
      StringBuffer qualifiedSourceName = new StringBuffer(16);

      for(int i = 0; i < this.length; ++i) {
         qualifiedSourceName.append(this.intersectingTypes[i].qualifiedSourceName());
         if (i != this.length - 1) {
            qualifiedSourceName.append(" & ");
         }
      }

      return qualifiedSourceName.toString().toCharArray();
   }

   public char[] sourceName() {
      StringBuffer srcName = new StringBuffer(16);

      for(int i = 0; i < this.length; ++i) {
         srcName.append(this.intersectingTypes[i].sourceName());
         if (i != this.length - 1) {
            srcName.append(" & ");
         }
      }

      return srcName.toString().toCharArray();
   }

   public char[] readableName() {
      StringBuffer readableName = new StringBuffer(16);

      for(int i = 0; i < this.length; ++i) {
         readableName.append(this.intersectingTypes[i].readableName());
         if (i != this.length - 1) {
            readableName.append(" & ");
         }
      }

      return readableName.toString().toCharArray();
   }

   public char[] shortReadableName() {
      StringBuffer shortReadableName = new StringBuffer(16);

      for(int i = 0; i < this.length; ++i) {
         shortReadableName.append(this.intersectingTypes[i].shortReadableName());
         if (i != this.length - 1) {
            shortReadableName.append(" & ");
         }
      }

      return shortReadableName.toString().toCharArray();
   }

   public boolean isIntersectionType18() {
      return true;
   }

   public int kind() {
      return 32772;
   }

   public String debugName() {
      StringBuffer debugName = new StringBuffer(16);

      for(int i = 0; i < this.length; ++i) {
         debugName.append(this.intersectingTypes[i].debugName());
         if (i != this.length - 1) {
            debugName.append(" & ");
         }
      }

      return debugName.toString();
   }

   public String toString() {
      return this.debugName();
   }

   public TypeBinding getSAMType(Scope scope) {
      int i = 0;

      for(int max = this.intersectingTypes.length; i < max; ++i) {
         TypeBinding typeBinding = this.intersectingTypes[i];
         MethodBinding methodBinding = typeBinding.getSingleAbstractMethod(scope, true);
         if (methodBinding != null && methodBinding.problemId() != 17) {
            return typeBinding;
         }
      }

      return null;
   }

   void collectInferenceVariables(Set variables) {
      for(int i = 0; i < this.intersectingTypes.length; ++i) {
         this.intersectingTypes[i].collectInferenceVariables(variables);
      }

   }

   public ReferenceBinding upwardsProjection(Scope scope, TypeBinding[] mentionedTypeVariables) {
      ReferenceBinding[] projectedTypes = new ReferenceBinding[this.intersectingTypes.length];

      for(int i = 0; i < this.intersectingTypes.length; ++i) {
         projectedTypes[i] = this.intersectingTypes[i].upwardsProjection(scope, mentionedTypeVariables);
      }

      return (ReferenceBinding)scope.environment().createIntersectionType18(projectedTypes);
   }

   public ReferenceBinding downwardsProjection(Scope scope, TypeBinding[] mentionedTypeVariables) {
      ReferenceBinding[] projectedTypes = new ReferenceBinding[this.intersectingTypes.length];

      for(int i = 0; i < this.intersectingTypes.length; ++i) {
         projectedTypes[i] = this.intersectingTypes[i].downwardsProjection(scope, mentionedTypeVariables);
      }

      return (ReferenceBinding)scope.environment().createIntersectionType18(projectedTypes);
   }

   public boolean mentionsAny(TypeBinding[] parameters, int idx) {
      if (super.mentionsAny(parameters, idx)) {
         return true;
      } else {
         for(int i = 0; i < this.intersectingTypes.length; ++i) {
            if (this.intersectingTypes[i].mentionsAny(parameters, -1)) {
               return true;
            }
         }

         return false;
      }
   }

   public long updateTagBits() {
      ReferenceBinding[] var4;
      int var3 = (var4 = this.intersectingTypes).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         TypeBinding intersectingType = var4[var2];
         this.tagBits |= intersectingType.updateTagBits();
      }

      return super.updateTagBits();
   }
}

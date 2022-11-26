package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;

public class UnresolvedReferenceBinding extends ReferenceBinding {
   ReferenceBinding resolvedType;
   TypeBinding[] wrappers;
   UnresolvedReferenceBinding prototype;

   UnresolvedReferenceBinding(char[][] compoundName, PackageBinding packageBinding) {
      this.compoundName = compoundName;
      this.sourceName = compoundName[compoundName.length - 1];
      this.fPackage = packageBinding;
      this.wrappers = null;
      this.prototype = this;
      this.computeId();
   }

   public UnresolvedReferenceBinding(UnresolvedReferenceBinding prototype) {
      super(prototype);
      this.resolvedType = prototype.resolvedType;
      this.wrappers = null;
      this.prototype = prototype.prototype;
   }

   public TypeBinding clone(TypeBinding outerType) {
      if (this.resolvedType != null) {
         return this.resolvedType.clone(outerType);
      } else {
         UnresolvedReferenceBinding copy = new UnresolvedReferenceBinding(this);
         this.addWrapper(copy, (LookupEnvironment)null);
         return copy;
      }
   }

   void addWrapper(TypeBinding wrapper, LookupEnvironment environment) {
      if (this.resolvedType != null) {
         wrapper.swapUnresolved(this, this.resolvedType, environment);
      } else {
         if (this.wrappers == null) {
            this.wrappers = new TypeBinding[]{wrapper};
         } else {
            int length = this.wrappers.length;
            System.arraycopy(this.wrappers, 0, this.wrappers = new TypeBinding[length + 1], 0, length);
            this.wrappers[length] = wrapper;
         }

      }
   }

   public boolean isUnresolvedType() {
      return true;
   }

   public String debugName() {
      return this.toString();
   }

   public int depth() {
      int last = this.compoundName.length - 1;
      return CharOperation.occurencesOf('$', this.compoundName[last], 1);
   }

   public boolean hasTypeBit(int bit) {
      return false;
   }

   public TypeBinding prototype() {
      return this.prototype;
   }

   ReferenceBinding resolve(LookupEnvironment environment, boolean convertGenericToRawType) {
      if (this != this.prototype) {
         ReferenceBinding targetType = this.prototype.resolve(environment, convertGenericToRawType);
         if (convertGenericToRawType && targetType != null && targetType.isRawType()) {
            targetType = (ReferenceBinding)environment.createAnnotatedType(targetType, (AnnotationBinding[])this.typeAnnotations);
         } else {
            targetType = this.resolvedType;
         }

         return targetType;
      } else {
         ReferenceBinding targetType = this.resolvedType;
         if (targetType == null) {
            char[] typeName = this.compoundName[this.compoundName.length - 1];
            targetType = this.fPackage.getType0(typeName);
            if (targetType == this || targetType == null) {
               if (this.fPackage instanceof SplitPackageBinding) {
                  targetType = environment.askForType(this.fPackage, typeName, this.fPackage.enclosingModule);
               } else if (targetType == this) {
                  targetType = environment.askForType(this.compoundName, this.fPackage.enclosingModule);
               }
            }

            if ((targetType == null || targetType == this) && CharOperation.contains('.', typeName)) {
               targetType = environment.askForType(this.fPackage, CharOperation.replaceOnCopy(typeName, '.', '$'), this.fPackage.enclosingModule);
            }

            if (targetType == null || targetType == this) {
               if ((this.tagBits & 128L) == 0L && !environment.mayTolerateMissingType) {
                  environment.problemReporter.isClassPathCorrect(this.compoundName, environment.root.unitBeingCompleted, environment.missingClassFileLocation);
               }

               targetType = environment.createMissingType((PackageBinding)null, this.compoundName);
            }

            this.setResolvedType((ReferenceBinding)targetType, environment);
         }

         if (convertGenericToRawType) {
            targetType = (ReferenceBinding)environment.convertUnresolvedBinaryToRawType((TypeBinding)targetType);
         }

         return (ReferenceBinding)targetType;
      }
   }

   void setResolvedType(ReferenceBinding targetType, LookupEnvironment environment) {
      if (this.resolvedType != targetType) {
         this.resolvedType = targetType;
         environment.updateCaches(this, targetType);
         if (this.wrappers != null) {
            int i = 0;

            for(int l = this.wrappers.length; i < l; ++i) {
               this.wrappers[i].swapUnresolved(this, targetType, environment);
            }
         }

      }
   }

   public void swapUnresolved(UnresolvedReferenceBinding unresolvedType, ReferenceBinding unannotatedType, LookupEnvironment environment) {
      if (this.resolvedType == null) {
         ReferenceBinding annotatedType = (ReferenceBinding)unannotatedType.clone((TypeBinding)null);
         this.resolvedType = annotatedType;
         annotatedType.setTypeAnnotations(this.getTypeAnnotations(), environment.globalOptions.isAnnotationBasedNullAnalysisEnabled);
         environment.updateCaches(this, annotatedType);
         if (this.wrappers != null) {
            int i = 0;

            for(int l = this.wrappers.length; i < l; ++i) {
               this.wrappers[i].swapUnresolved(this, annotatedType, environment);
            }
         }

      }
   }

   public String toString() {
      return this.hasTypeAnnotations() ? super.annotatedDebugName() + "(unresolved)" : "Unresolved type " + (this.compoundName != null ? CharOperation.toString(this.compoundName) : "UNNAMED");
   }
}

package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;

public final class MemberTypeBinding extends NestedTypeBinding {
   public MemberTypeBinding(char[][] compoundName, ClassScope scope, SourceTypeBinding enclosingType) {
      super(compoundName, scope, enclosingType);
      this.tagBits |= 2060L;
   }

   public MemberTypeBinding(MemberTypeBinding prototype) {
      super(prototype);
   }

   void checkSyntheticArgsAndFields() {
      if (!this.isPrototype()) {
         throw new IllegalStateException();
      } else if (!this.isStatic()) {
         if (!this.isInterface()) {
            if (!this.isPrototype()) {
               ((MemberTypeBinding)this.prototype).checkSyntheticArgsAndFields();
            } else {
               this.addSyntheticArgumentAndField(this.enclosingType);
            }
         }
      }
   }

   public char[] constantPoolName() {
      if (this.constantPoolName != null) {
         return this.constantPoolName;
      } else {
         return !this.isPrototype() ? this.prototype.constantPoolName() : (this.constantPoolName = CharOperation.concat(this.enclosingType().constantPoolName(), this.sourceName, '$'));
      }
   }

   public TypeBinding clone(TypeBinding outerType) {
      MemberTypeBinding copy = new MemberTypeBinding(this);
      copy.enclosingType = (SourceTypeBinding)outerType;
      return copy;
   }

   public void initializeDeprecatedAnnotationTagBits() {
      if (!this.isPrototype()) {
         this.prototype.initializeDeprecatedAnnotationTagBits();
      } else {
         if ((this.tagBits & 17179869184L) == 0L) {
            super.initializeDeprecatedAnnotationTagBits();
            if ((this.tagBits & 70368744177664L) == 0L) {
               ReferenceBinding enclosing;
               if (((enclosing = this.enclosingType()).tagBits & 17179869184L) == 0L) {
                  enclosing.initializeDeprecatedAnnotationTagBits();
               }

               if (enclosing.isViewedAsDeprecated()) {
                  this.modifiers |= 2097152;
                  this.tagBits |= enclosing.tagBits & 4611686018427387904L;
               }
            }
         }

      }
   }

   public String toString() {
      return this.hasTypeAnnotations() ? this.annotatedDebugName() : "Member type : " + new String(this.sourceName()) + " " + super.toString();
   }

   public ModuleBinding module() {
      return this.enclosingType.module();
   }
}

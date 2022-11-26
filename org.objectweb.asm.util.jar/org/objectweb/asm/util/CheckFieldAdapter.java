package org.objectweb.asm.util;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.TypeReference;

public class CheckFieldAdapter extends FieldVisitor {
   private boolean visitEndCalled;

   public CheckFieldAdapter(FieldVisitor fieldVisitor) {
      this(458752, fieldVisitor);
      if (this.getClass() != CheckFieldAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected CheckFieldAdapter(int api, FieldVisitor fieldVisitor) {
      super(api, fieldVisitor);
   }

   public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
      this.checkVisitEndNotCalled();
      CheckMethodAdapter.checkDescriptor(49, descriptor, false);
      return new CheckAnnotationAdapter(super.visitAnnotation(descriptor, visible));
   }

   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      this.checkVisitEndNotCalled();
      int sort = (new TypeReference(typeRef)).getSort();
      if (sort != 19) {
         throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(sort));
      } else {
         CheckClassAdapter.checkTypeRef(typeRef);
         CheckMethodAdapter.checkDescriptor(49, descriptor, false);
         return new CheckAnnotationAdapter(super.visitTypeAnnotation(typeRef, typePath, descriptor, visible));
      }
   }

   public void visitAttribute(Attribute attribute) {
      this.checkVisitEndNotCalled();
      if (attribute == null) {
         throw new IllegalArgumentException("Invalid attribute (must not be null)");
      } else {
         super.visitAttribute(attribute);
      }
   }

   public void visitEnd() {
      this.checkVisitEndNotCalled();
      this.visitEndCalled = true;
      super.visitEnd();
   }

   private void checkVisitEndNotCalled() {
      if (this.visitEndCalled) {
         throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
      }
   }
}

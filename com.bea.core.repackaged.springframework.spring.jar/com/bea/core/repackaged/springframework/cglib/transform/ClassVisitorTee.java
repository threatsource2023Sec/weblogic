package com.bea.core.repackaged.springframework.cglib.transform;

import com.bea.core.repackaged.springframework.asm.AnnotationVisitor;
import com.bea.core.repackaged.springframework.asm.Attribute;
import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.asm.FieldVisitor;
import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.asm.TypePath;
import com.bea.core.repackaged.springframework.cglib.core.Constants;

public class ClassVisitorTee extends ClassVisitor {
   private ClassVisitor cv1;
   private ClassVisitor cv2;

   public ClassVisitorTee(ClassVisitor cv1, ClassVisitor cv2) {
      super(Constants.ASM_API);
      this.cv1 = cv1;
      this.cv2 = cv2;
   }

   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
      this.cv1.visit(version, access, name, signature, superName, interfaces);
      this.cv2.visit(version, access, name, signature, superName, interfaces);
   }

   public void visitEnd() {
      this.cv1.visitEnd();
      this.cv2.visitEnd();
      this.cv1 = this.cv2 = null;
   }

   public void visitInnerClass(String name, String outerName, String innerName, int access) {
      this.cv1.visitInnerClass(name, outerName, innerName, access);
      this.cv2.visitInnerClass(name, outerName, innerName, access);
   }

   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
      FieldVisitor fv1 = this.cv1.visitField(access, name, desc, signature, value);
      FieldVisitor fv2 = this.cv2.visitField(access, name, desc, signature, value);
      if (fv1 == null) {
         return fv2;
      } else {
         return (FieldVisitor)(fv2 == null ? fv1 : new FieldVisitorTee(fv1, fv2));
      }
   }

   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
      MethodVisitor mv1 = this.cv1.visitMethod(access, name, desc, signature, exceptions);
      MethodVisitor mv2 = this.cv2.visitMethod(access, name, desc, signature, exceptions);
      if (mv1 == null) {
         return mv2;
      } else {
         return (MethodVisitor)(mv2 == null ? mv1 : new MethodVisitorTee(mv1, mv2));
      }
   }

   public void visitSource(String source, String debug) {
      this.cv1.visitSource(source, debug);
      this.cv2.visitSource(source, debug);
   }

   public void visitOuterClass(String owner, String name, String desc) {
      this.cv1.visitOuterClass(owner, name, desc);
      this.cv2.visitOuterClass(owner, name, desc);
   }

   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
      return AnnotationVisitorTee.getInstance(this.cv1.visitAnnotation(desc, visible), this.cv2.visitAnnotation(desc, visible));
   }

   public void visitAttribute(Attribute attrs) {
      this.cv1.visitAttribute(attrs);
      this.cv2.visitAttribute(attrs);
   }

   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
      return AnnotationVisitorTee.getInstance(this.cv1.visitTypeAnnotation(typeRef, typePath, desc, visible), this.cv2.visitTypeAnnotation(typeRef, typePath, desc, visible));
   }
}

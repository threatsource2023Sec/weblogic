package org.python.objectweb.asm.commons;

import org.python.objectweb.asm.AnnotationVisitor;
import org.python.objectweb.asm.ClassVisitor;
import org.python.objectweb.asm.FieldVisitor;
import org.python.objectweb.asm.MethodVisitor;
import org.python.objectweb.asm.TypePath;

/** @deprecated */
public class RemappingClassAdapter extends ClassVisitor {
   protected final Remapper remapper;
   protected String className;

   public RemappingClassAdapter(ClassVisitor var1, Remapper var2) {
      this(327680, var1, var2);
   }

   protected RemappingClassAdapter(int var1, ClassVisitor var2, Remapper var3) {
      super(var1, var2);
      this.remapper = var3;
   }

   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      this.className = var3;
      super.visit(var1, var2, this.remapper.mapType(var3), this.remapper.mapSignature(var4, false), this.remapper.mapType(var5), var6 == null ? null : this.remapper.mapTypes(var6));
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      AnnotationVisitor var3 = super.visitAnnotation(this.remapper.mapDesc(var1), var2);
      return var3 == null ? null : this.createRemappingAnnotationAdapter(var3);
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      AnnotationVisitor var5 = super.visitTypeAnnotation(var1, var2, this.remapper.mapDesc(var3), var4);
      return var5 == null ? null : this.createRemappingAnnotationAdapter(var5);
   }

   public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      FieldVisitor var6 = super.visitField(var1, this.remapper.mapFieldName(this.className, var2, var3), this.remapper.mapDesc(var3), this.remapper.mapSignature(var4, true), this.remapper.mapValue(var5));
      return var6 == null ? null : this.createRemappingFieldAdapter(var6);
   }

   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      String var6 = this.remapper.mapMethodDesc(var3);
      MethodVisitor var7 = super.visitMethod(var1, this.remapper.mapMethodName(this.className, var2, var3), var6, this.remapper.mapSignature(var4, false), var5 == null ? null : this.remapper.mapTypes(var5));
      return var7 == null ? null : this.createRemappingMethodAdapter(var1, var6, var7);
   }

   public void visitInnerClass(String var1, String var2, String var3, int var4) {
      super.visitInnerClass(this.remapper.mapType(var1), var2 == null ? null : this.remapper.mapType(var2), var3, var4);
   }

   public void visitOuterClass(String var1, String var2, String var3) {
      super.visitOuterClass(this.remapper.mapType(var1), var2 == null ? null : this.remapper.mapMethodName(var1, var2, var3), var3 == null ? null : this.remapper.mapMethodDesc(var3));
   }

   protected FieldVisitor createRemappingFieldAdapter(FieldVisitor var1) {
      return new RemappingFieldAdapter(var1, this.remapper);
   }

   protected MethodVisitor createRemappingMethodAdapter(int var1, String var2, MethodVisitor var3) {
      return new RemappingMethodAdapter(var1, var2, var3, this.remapper);
   }

   protected AnnotationVisitor createRemappingAnnotationAdapter(AnnotationVisitor var1) {
      return new RemappingAnnotationAdapter(var1, this.remapper);
   }
}

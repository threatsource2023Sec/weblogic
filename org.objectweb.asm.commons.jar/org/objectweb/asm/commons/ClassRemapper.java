package org.objectweb.asm.commons;

import java.util.List;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ModuleVisitor;
import org.objectweb.asm.TypePath;

public class ClassRemapper extends ClassVisitor {
   protected final Remapper remapper;
   protected String className;

   public ClassRemapper(ClassVisitor classVisitor, Remapper remapper) {
      this(458752, classVisitor, remapper);
   }

   protected ClassRemapper(int api, ClassVisitor classVisitor, Remapper remapper) {
      super(api, classVisitor);
      this.remapper = remapper;
   }

   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
      this.className = name;
      super.visit(version, access, this.remapper.mapType(name), this.remapper.mapSignature(signature, false), this.remapper.mapType(superName), interfaces == null ? null : this.remapper.mapTypes(interfaces));
   }

   public ModuleVisitor visitModule(String name, int flags, String version) {
      ModuleVisitor moduleVisitor = super.visitModule(this.remapper.mapModuleName(name), flags, version);
      return moduleVisitor == null ? null : this.createModuleRemapper(moduleVisitor);
   }

   public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
      AnnotationVisitor annotationVisitor = super.visitAnnotation(this.remapper.mapDesc(descriptor), visible);
      return annotationVisitor == null ? null : this.createAnnotationRemapper(annotationVisitor);
   }

   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      AnnotationVisitor annotationVisitor = super.visitTypeAnnotation(typeRef, typePath, this.remapper.mapDesc(descriptor), visible);
      return annotationVisitor == null ? null : this.createAnnotationRemapper(annotationVisitor);
   }

   public void visitAttribute(Attribute attribute) {
      if (attribute instanceof ModuleHashesAttribute) {
         ModuleHashesAttribute moduleHashesAttribute = (ModuleHashesAttribute)attribute;
         List modules = moduleHashesAttribute.modules;

         for(int i = 0; i < modules.size(); ++i) {
            modules.set(i, this.remapper.mapModuleName((String)modules.get(i)));
         }
      }

      super.visitAttribute(attribute);
   }

   public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
      FieldVisitor fieldVisitor = super.visitField(access, this.remapper.mapFieldName(this.className, name, descriptor), this.remapper.mapDesc(descriptor), this.remapper.mapSignature(signature, true), value == null ? null : this.remapper.mapValue(value));
      return fieldVisitor == null ? null : this.createFieldRemapper(fieldVisitor);
   }

   public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
      String remappedDescriptor = this.remapper.mapMethodDesc(descriptor);
      MethodVisitor methodVisitor = super.visitMethod(access, this.remapper.mapMethodName(this.className, name, descriptor), remappedDescriptor, this.remapper.mapSignature(signature, false), exceptions == null ? null : this.remapper.mapTypes(exceptions));
      return methodVisitor == null ? null : this.createMethodRemapper(methodVisitor);
   }

   public void visitInnerClass(String name, String outerName, String innerName, int access) {
      super.visitInnerClass(this.remapper.mapType(name), outerName == null ? null : this.remapper.mapType(outerName), innerName == null ? null : this.remapper.mapInnerClassName(name, outerName, innerName), access);
   }

   public void visitOuterClass(String owner, String name, String descriptor) {
      super.visitOuterClass(this.remapper.mapType(owner), name == null ? null : this.remapper.mapMethodName(owner, name, descriptor), descriptor == null ? null : this.remapper.mapMethodDesc(descriptor));
   }

   public void visitNestHost(String nestHost) {
      super.visitNestHost(this.remapper.mapType(nestHost));
   }

   public void visitNestMember(String nestMember) {
      super.visitNestMember(this.remapper.mapType(nestMember));
   }

   protected FieldVisitor createFieldRemapper(FieldVisitor fieldVisitor) {
      return new FieldRemapper(this.api, fieldVisitor, this.remapper);
   }

   protected MethodVisitor createMethodRemapper(MethodVisitor methodVisitor) {
      return new MethodRemapper(this.api, methodVisitor, this.remapper);
   }

   protected AnnotationVisitor createAnnotationRemapper(AnnotationVisitor annotationVisitor) {
      return new AnnotationRemapper(this.api, annotationVisitor, this.remapper);
   }

   protected ModuleVisitor createModuleRemapper(ModuleVisitor moduleVisitor) {
      return new ModuleRemapper(this.api, moduleVisitor, this.remapper);
   }
}

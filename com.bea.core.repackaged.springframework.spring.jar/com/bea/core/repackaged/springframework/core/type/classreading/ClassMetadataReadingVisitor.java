package com.bea.core.repackaged.springframework.core.type.classreading;

import com.bea.core.repackaged.springframework.asm.AnnotationVisitor;
import com.bea.core.repackaged.springframework.asm.Attribute;
import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.asm.FieldVisitor;
import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.core.type.ClassMetadata;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

class ClassMetadataReadingVisitor extends ClassVisitor implements ClassMetadata {
   private String className = "";
   private boolean isInterface;
   private boolean isAnnotation;
   private boolean isAbstract;
   private boolean isFinal;
   @Nullable
   private String enclosingClassName;
   private boolean independentInnerClass;
   @Nullable
   private String superClassName;
   private String[] interfaces = new String[0];
   private Set memberClassNames = new LinkedHashSet(4);

   public ClassMetadataReadingVisitor() {
      super(458752);
   }

   public void visit(int version, int access, String name, String signature, @Nullable String supername, String[] interfaces) {
      this.className = ClassUtils.convertResourcePathToClassName(name);
      this.isInterface = (access & 512) != 0;
      this.isAnnotation = (access & 8192) != 0;
      this.isAbstract = (access & 1024) != 0;
      this.isFinal = (access & 16) != 0;
      if (supername != null && !this.isInterface) {
         this.superClassName = ClassUtils.convertResourcePathToClassName(supername);
      }

      this.interfaces = new String[interfaces.length];

      for(int i = 0; i < interfaces.length; ++i) {
         this.interfaces[i] = ClassUtils.convertResourcePathToClassName(interfaces[i]);
      }

   }

   public void visitOuterClass(String owner, String name, String desc) {
      this.enclosingClassName = ClassUtils.convertResourcePathToClassName(owner);
   }

   public void visitInnerClass(String name, @Nullable String outerName, String innerName, int access) {
      if (outerName != null) {
         String fqName = ClassUtils.convertResourcePathToClassName(name);
         String fqOuterName = ClassUtils.convertResourcePathToClassName(outerName);
         if (this.className.equals(fqName)) {
            this.enclosingClassName = fqOuterName;
            this.independentInnerClass = (access & 8) != 0;
         } else if (this.className.equals(fqOuterName)) {
            this.memberClassNames.add(fqName);
         }
      }

   }

   public void visitSource(String source, String debug) {
   }

   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
      return new EmptyAnnotationVisitor();
   }

   public void visitAttribute(Attribute attr) {
   }

   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
      return new EmptyFieldVisitor();
   }

   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
      return new EmptyMethodVisitor();
   }

   public void visitEnd() {
   }

   public String getClassName() {
      return this.className;
   }

   public boolean isInterface() {
      return this.isInterface;
   }

   public boolean isAnnotation() {
      return this.isAnnotation;
   }

   public boolean isAbstract() {
      return this.isAbstract;
   }

   public boolean isConcrete() {
      return !this.isInterface && !this.isAbstract;
   }

   public boolean isFinal() {
      return this.isFinal;
   }

   public boolean isIndependent() {
      return this.enclosingClassName == null || this.independentInnerClass;
   }

   public boolean hasEnclosingClass() {
      return this.enclosingClassName != null;
   }

   @Nullable
   public String getEnclosingClassName() {
      return this.enclosingClassName;
   }

   public boolean hasSuperClass() {
      return this.superClassName != null;
   }

   @Nullable
   public String getSuperClassName() {
      return this.superClassName;
   }

   public String[] getInterfaceNames() {
      return this.interfaces;
   }

   public String[] getMemberClassNames() {
      return StringUtils.toStringArray((Collection)this.memberClassNames);
   }

   private static class EmptyFieldVisitor extends FieldVisitor {
      public EmptyFieldVisitor() {
         super(458752);
      }
   }

   private static class EmptyMethodVisitor extends MethodVisitor {
      public EmptyMethodVisitor() {
         super(458752);
      }
   }

   private static class EmptyAnnotationVisitor extends AnnotationVisitor {
      public EmptyAnnotationVisitor() {
         super(458752);
      }

      public AnnotationVisitor visitAnnotation(String name, String desc) {
         return this;
      }

      public AnnotationVisitor visitArray(String name) {
         return this;
      }
   }
}

package org.objectweb.asm.tree;

import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.TypePath;

public class FieldNode extends FieldVisitor {
   public int access;
   public String name;
   public String desc;
   public String signature;
   public Object value;
   public List visibleAnnotations;
   public List invisibleAnnotations;
   public List visibleTypeAnnotations;
   public List invisibleTypeAnnotations;
   public List attrs;

   public FieldNode(int access, String name, String descriptor, String signature, Object value) {
      this(458752, access, name, descriptor, signature, value);
      if (this.getClass() != FieldNode.class) {
         throw new IllegalStateException();
      }
   }

   public FieldNode(int api, int access, String name, String descriptor, String signature, Object value) {
      super(api);
      this.access = access;
      this.name = name;
      this.desc = descriptor;
      this.signature = signature;
      this.value = value;
   }

   public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
      AnnotationNode annotation = new AnnotationNode(descriptor);
      if (visible) {
         if (this.visibleAnnotations == null) {
            this.visibleAnnotations = new ArrayList(1);
         }

         this.visibleAnnotations.add(annotation);
      } else {
         if (this.invisibleAnnotations == null) {
            this.invisibleAnnotations = new ArrayList(1);
         }

         this.invisibleAnnotations.add(annotation);
      }

      return annotation;
   }

   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      TypeAnnotationNode typeAnnotation = new TypeAnnotationNode(typeRef, typePath, descriptor);
      if (visible) {
         if (this.visibleTypeAnnotations == null) {
            this.visibleTypeAnnotations = new ArrayList(1);
         }

         this.visibleTypeAnnotations.add(typeAnnotation);
      } else {
         if (this.invisibleTypeAnnotations == null) {
            this.invisibleTypeAnnotations = new ArrayList(1);
         }

         this.invisibleTypeAnnotations.add(typeAnnotation);
      }

      return typeAnnotation;
   }

   public void visitAttribute(Attribute attribute) {
      if (this.attrs == null) {
         this.attrs = new ArrayList(1);
      }

      this.attrs.add(attribute);
   }

   public void visitEnd() {
   }

   public void check(int api) {
      if (api == 262144) {
         if (this.visibleTypeAnnotations != null && !this.visibleTypeAnnotations.isEmpty()) {
            throw new UnsupportedClassVersionException();
         }

         if (this.invisibleTypeAnnotations != null && !this.invisibleTypeAnnotations.isEmpty()) {
            throw new UnsupportedClassVersionException();
         }
      }

   }

   public void accept(ClassVisitor classVisitor) {
      FieldVisitor fieldVisitor = classVisitor.visitField(this.access, this.name, this.desc, this.signature, this.value);
      if (fieldVisitor != null) {
         int i;
         int n;
         AnnotationNode annotation;
         if (this.visibleAnnotations != null) {
            i = 0;

            for(n = this.visibleAnnotations.size(); i < n; ++i) {
               annotation = (AnnotationNode)this.visibleAnnotations.get(i);
               annotation.accept(fieldVisitor.visitAnnotation(annotation.desc, true));
            }
         }

         if (this.invisibleAnnotations != null) {
            i = 0;

            for(n = this.invisibleAnnotations.size(); i < n; ++i) {
               annotation = (AnnotationNode)this.invisibleAnnotations.get(i);
               annotation.accept(fieldVisitor.visitAnnotation(annotation.desc, false));
            }
         }

         TypeAnnotationNode typeAnnotation;
         if (this.visibleTypeAnnotations != null) {
            i = 0;

            for(n = this.visibleTypeAnnotations.size(); i < n; ++i) {
               typeAnnotation = (TypeAnnotationNode)this.visibleTypeAnnotations.get(i);
               typeAnnotation.accept(fieldVisitor.visitTypeAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, true));
            }
         }

         if (this.invisibleTypeAnnotations != null) {
            i = 0;

            for(n = this.invisibleTypeAnnotations.size(); i < n; ++i) {
               typeAnnotation = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(i);
               typeAnnotation.accept(fieldVisitor.visitTypeAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, false));
            }
         }

         if (this.attrs != null) {
            i = 0;

            for(n = this.attrs.size(); i < n; ++i) {
               fieldVisitor.visitAttribute((Attribute)this.attrs.get(i));
            }
         }

         fieldVisitor.visitEnd();
      }
   }
}

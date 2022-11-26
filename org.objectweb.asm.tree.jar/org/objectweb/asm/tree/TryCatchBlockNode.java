package org.objectweb.asm.tree;

import java.util.List;
import org.objectweb.asm.MethodVisitor;

public class TryCatchBlockNode {
   public LabelNode start;
   public LabelNode end;
   public LabelNode handler;
   public String type;
   public List visibleTypeAnnotations;
   public List invisibleTypeAnnotations;

   public TryCatchBlockNode(LabelNode start, LabelNode end, LabelNode handler, String type) {
      this.start = start;
      this.end = end;
      this.handler = handler;
      this.type = type;
   }

   public void updateIndex(int index) {
      int newTypeRef = 1107296256 | index << 8;
      int i;
      int n;
      if (this.visibleTypeAnnotations != null) {
         i = 0;

         for(n = this.visibleTypeAnnotations.size(); i < n; ++i) {
            ((TypeAnnotationNode)this.visibleTypeAnnotations.get(i)).typeRef = newTypeRef;
         }
      }

      if (this.invisibleTypeAnnotations != null) {
         i = 0;

         for(n = this.invisibleTypeAnnotations.size(); i < n; ++i) {
            ((TypeAnnotationNode)this.invisibleTypeAnnotations.get(i)).typeRef = newTypeRef;
         }
      }

   }

   public void accept(MethodVisitor methodVisitor) {
      methodVisitor.visitTryCatchBlock(this.start.getLabel(), this.end.getLabel(), this.handler == null ? null : this.handler.getLabel(), this.type);
      int i;
      int n;
      TypeAnnotationNode typeAnnotation;
      if (this.visibleTypeAnnotations != null) {
         i = 0;

         for(n = this.visibleTypeAnnotations.size(); i < n; ++i) {
            typeAnnotation = (TypeAnnotationNode)this.visibleTypeAnnotations.get(i);
            typeAnnotation.accept(methodVisitor.visitTryCatchAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, true));
         }
      }

      if (this.invisibleTypeAnnotations != null) {
         i = 0;

         for(n = this.invisibleTypeAnnotations.size(); i < n; ++i) {
            typeAnnotation = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(i);
            typeAnnotation.accept(methodVisitor.visitTryCatchAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, false));
         }
      }

   }
}

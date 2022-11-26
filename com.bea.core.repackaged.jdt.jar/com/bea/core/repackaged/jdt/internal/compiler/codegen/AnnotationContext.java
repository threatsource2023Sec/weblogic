package com.bea.core.repackaged.jdt.internal.compiler.codegen;

import com.bea.core.repackaged.jdt.internal.compiler.ast.Annotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Expression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Wildcard;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;

public class AnnotationContext {
   public static final int VISIBLE = 1;
   public static final int INVISIBLE = 2;
   public Annotation annotation;
   public Expression typeReference;
   public int targetType;
   public int info;
   public int info2;
   public int visibility;
   public LocalVariableBinding variableBinding;
   public Wildcard wildcard;

   public AnnotationContext(Annotation annotation, Expression typeReference, int targetType, int visibility) {
      this.annotation = annotation;
      this.typeReference = typeReference;
      this.targetType = targetType;
      this.visibility = visibility;
   }

   public String toString() {
      return "AnnotationContext [annotation=" + this.annotation + ", typeReference=" + this.typeReference + ", targetType=" + this.targetType + ", info =" + this.info + ", boundIndex=" + this.info2 + "]";
   }
}

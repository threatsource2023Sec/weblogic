package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.SpelNode;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InlineList extends SpelNodeImpl {
   @Nullable
   private TypedValue constant;

   public InlineList(int pos, SpelNodeImpl... args) {
      super(pos, args);
      this.checkIfConstant();
   }

   private void checkIfConstant() {
      boolean isConstant = true;
      int c = 0;

      int childcount;
      for(childcount = this.getChildCount(); c < childcount; ++c) {
         SpelNode child = this.getChild(c);
         if (!(child instanceof Literal)) {
            if (child instanceof InlineList) {
               InlineList inlineList = (InlineList)child;
               if (!inlineList.isConstant()) {
                  isConstant = false;
               }
            } else {
               isConstant = false;
            }
         }
      }

      if (isConstant) {
         List constantList = new ArrayList();
         childcount = this.getChildCount();

         for(int c = 0; c < childcount; ++c) {
            SpelNode child = this.getChild(c);
            if (child instanceof Literal) {
               constantList.add(((Literal)child).getLiteralValue().getValue());
            } else if (child instanceof InlineList) {
               constantList.add(((InlineList)child).getConstantValue());
            }
         }

         this.constant = new TypedValue(Collections.unmodifiableList(constantList));
      }

   }

   public TypedValue getValueInternal(ExpressionState expressionState) throws EvaluationException {
      if (this.constant != null) {
         return this.constant;
      } else {
         List returnValue = new ArrayList();
         int childCount = this.getChildCount();

         for(int c = 0; c < childCount; ++c) {
            returnValue.add(this.getChild(c).getValue(expressionState));
         }

         return new TypedValue(returnValue);
      }
   }

   public String toStringAST() {
      StringBuilder sb = new StringBuilder("{");
      int count = this.getChildCount();

      for(int c = 0; c < count; ++c) {
         if (c > 0) {
            sb.append(",");
         }

         sb.append(this.getChild(c).toStringAST());
      }

      sb.append("}");
      return sb.toString();
   }

   public boolean isConstant() {
      return this.constant != null;
   }

   @Nullable
   public List getConstantValue() {
      Assert.state(this.constant != null, "No constant");
      return (List)this.constant.getValue();
   }

   public boolean isCompilable() {
      return this.isConstant();
   }

   public void generateCode(MethodVisitor mv, CodeFlow codeflow) {
      String constantFieldName = "inlineList$" + codeflow.nextFieldId();
      String className = codeflow.getClassName();
      codeflow.registerNewField((cw, cflow) -> {
         cw.visitField(26, constantFieldName, "Ljava/util/List;", (String)null, (Object)null);
      });
      codeflow.registerNewClinit((mVisitor, cflow) -> {
         this.generateClinitCode(className, constantFieldName, mVisitor, cflow, false);
      });
      mv.visitFieldInsn(178, className, constantFieldName, "Ljava/util/List;");
      codeflow.pushDescriptor("Ljava/util/List");
   }

   void generateClinitCode(String clazzname, String constantFieldName, MethodVisitor mv, CodeFlow codeflow, boolean nested) {
      mv.visitTypeInsn(187, "java/util/ArrayList");
      mv.visitInsn(89);
      mv.visitMethodInsn(183, "java/util/ArrayList", "<init>", "()V", false);
      if (!nested) {
         mv.visitFieldInsn(179, clazzname, constantFieldName, "Ljava/util/List;");
      }

      int childCount = this.getChildCount();

      for(int c = 0; c < childCount; ++c) {
         if (!nested) {
            mv.visitFieldInsn(178, clazzname, constantFieldName, "Ljava/util/List;");
         } else {
            mv.visitInsn(89);
         }

         if (this.children[c] instanceof InlineList) {
            ((InlineList)this.children[c]).generateClinitCode(clazzname, constantFieldName, mv, codeflow, true);
         } else {
            this.children[c].generateCode(mv, codeflow);
            String lastDesc = codeflow.lastDescriptor();
            if (CodeFlow.isPrimitive(lastDesc)) {
               CodeFlow.insertBoxIfNecessary(mv, lastDesc.charAt(0));
            }
         }

         mv.visitMethodInsn(185, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
         mv.visitInsn(87);
      }

   }
}

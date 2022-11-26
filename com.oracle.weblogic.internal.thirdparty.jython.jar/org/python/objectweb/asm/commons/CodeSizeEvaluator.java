package org.python.objectweb.asm.commons;

import org.python.objectweb.asm.Handle;
import org.python.objectweb.asm.Label;
import org.python.objectweb.asm.MethodVisitor;
import org.python.objectweb.asm.Opcodes;

public class CodeSizeEvaluator extends MethodVisitor implements Opcodes {
   private int minSize;
   private int maxSize;

   public CodeSizeEvaluator(MethodVisitor var1) {
      this(327680, var1);
   }

   protected CodeSizeEvaluator(int var1, MethodVisitor var2) {
      super(var1, var2);
   }

   public int getMinSize() {
      return this.minSize;
   }

   public int getMaxSize() {
      return this.maxSize;
   }

   public void visitInsn(int var1) {
      ++this.minSize;
      ++this.maxSize;
      if (this.mv != null) {
         this.mv.visitInsn(var1);
      }

   }

   public void visitIntInsn(int var1, int var2) {
      if (var1 == 17) {
         this.minSize += 3;
         this.maxSize += 3;
      } else {
         this.minSize += 2;
         this.maxSize += 2;
      }

      if (this.mv != null) {
         this.mv.visitIntInsn(var1, var2);
      }

   }

   public void visitVarInsn(int var1, int var2) {
      if (var2 < 4 && var1 != 169) {
         ++this.minSize;
         ++this.maxSize;
      } else if (var2 >= 256) {
         this.minSize += 4;
         this.maxSize += 4;
      } else {
         this.minSize += 2;
         this.maxSize += 2;
      }

      if (this.mv != null) {
         this.mv.visitVarInsn(var1, var2);
      }

   }

   public void visitTypeInsn(int var1, String var2) {
      this.minSize += 3;
      this.maxSize += 3;
      if (this.mv != null) {
         this.mv.visitTypeInsn(var1, var2);
      }

   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      this.minSize += 3;
      this.maxSize += 3;
      if (this.mv != null) {
         this.mv.visitFieldInsn(var1, var2, var3, var4);
      }

   }

   /** @deprecated */
   public void visitMethodInsn(int var1, String var2, String var3, String var4) {
      if (this.api >= 327680) {
         super.visitMethodInsn(var1, var2, var3, var4);
      } else {
         this.doVisitMethodInsn(var1, var2, var3, var4, var1 == 185);
      }
   }

   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (this.api < 327680) {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      } else {
         this.doVisitMethodInsn(var1, var2, var3, var4, var5);
      }
   }

   private void doVisitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (var1 == 185) {
         this.minSize += 5;
         this.maxSize += 5;
      } else {
         this.minSize += 3;
         this.maxSize += 3;
      }

      if (this.mv != null) {
         this.mv.visitMethodInsn(var1, var2, var3, var4, var5);
      }

   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      this.minSize += 5;
      this.maxSize += 5;
      if (this.mv != null) {
         this.mv.visitInvokeDynamicInsn(var1, var2, var3, var4);
      }

   }

   public void visitJumpInsn(int var1, Label var2) {
      this.minSize += 3;
      if (var1 != 167 && var1 != 168) {
         this.maxSize += 8;
      } else {
         this.maxSize += 5;
      }

      if (this.mv != null) {
         this.mv.visitJumpInsn(var1, var2);
      }

   }

   public void visitLdcInsn(Object var1) {
      if (!(var1 instanceof Long) && !(var1 instanceof Double)) {
         this.minSize += 2;
         this.maxSize += 3;
      } else {
         this.minSize += 3;
         this.maxSize += 3;
      }

      if (this.mv != null) {
         this.mv.visitLdcInsn(var1);
      }

   }

   public void visitIincInsn(int var1, int var2) {
      if (var1 <= 255 && var2 <= 127 && var2 >= -128) {
         this.minSize += 3;
         this.maxSize += 3;
      } else {
         this.minSize += 6;
         this.maxSize += 6;
      }

      if (this.mv != null) {
         this.mv.visitIincInsn(var1, var2);
      }

   }

   public void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4) {
      this.minSize += 13 + var4.length * 4;
      this.maxSize += 16 + var4.length * 4;
      if (this.mv != null) {
         this.mv.visitTableSwitchInsn(var1, var2, var3, var4);
      }

   }

   public void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3) {
      this.minSize += 9 + var2.length * 8;
      this.maxSize += 12 + var2.length * 8;
      if (this.mv != null) {
         this.mv.visitLookupSwitchInsn(var1, var2, var3);
      }

   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      this.minSize += 4;
      this.maxSize += 4;
      if (this.mv != null) {
         this.mv.visitMultiANewArrayInsn(var1, var2);
      }

   }
}

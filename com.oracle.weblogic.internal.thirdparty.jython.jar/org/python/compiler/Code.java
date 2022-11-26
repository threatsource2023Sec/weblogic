package org.python.compiler;

import java.util.BitSet;
import java.util.Vector;
import org.python.objectweb.asm.AnnotationVisitor;
import org.python.objectweb.asm.Attribute;
import org.python.objectweb.asm.Handle;
import org.python.objectweb.asm.Label;
import org.python.objectweb.asm.MethodVisitor;
import org.python.objectweb.asm.Opcodes;

public class Code extends MethodVisitor implements Opcodes {
   MethodVisitor mv;
   String sig;
   String[] locals;
   int nlocals;
   int argcount;
   int returnLocal;
   BitSet finallyLocals = new BitSet();

   public Code(MethodVisitor mv, String sig, int access) {
      super(327680);
      this.mv = mv;
      this.sig = sig;
      this.nlocals = -this.sigSize(sig, false);
      if ((access & 8) != 8) {
         ++this.nlocals;
      }

      this.argcount = this.nlocals;
      this.locals = new String[this.nlocals + 128];
   }

   public int getLocal(String type) {
      for(int l = this.argcount; l < this.nlocals; ++l) {
         if (this.locals[l] == null) {
            this.locals[l] = type;
            return l;
         }
      }

      if (this.nlocals >= this.locals.length) {
         String[] new_locals = new String[this.locals.length * 2];
         System.arraycopy(this.locals, 0, new_locals, 0, this.locals.length);
         this.locals = new_locals;
      }

      this.locals[this.nlocals] = type;
      ++this.nlocals;
      return this.nlocals - 1;
   }

   public void freeLocal(int l) {
      if (this.locals[l] == null) {
         System.out.println("Double free:" + l);
      }

      this.locals[l] = null;
   }

   public int getFinallyLocal(String type) {
      int l = this.getLocal(type);
      this.finallyLocals.set(l);
      return l;
   }

   public void freeFinallyLocal(int l) {
      this.finallyLocals.clear(l);
      this.freeLocal(l);
   }

   public int getReturnLocal() {
      if (this.returnLocal == 0) {
         this.returnLocal = this.getLocal("return");
      }

      return this.returnLocal;
   }

   public Vector getActiveLocals() {
      Vector ret = new Vector();
      ret.setSize(this.nlocals);

      for(int l = this.argcount; l < this.nlocals; ++l) {
         if (l != this.returnLocal && !this.finallyLocals.get(l)) {
            ret.setElementAt(this.locals[l], l);
         }
      }

      return ret;
   }

   public AnnotationVisitor visitAnnotation(String arg0, boolean arg1) {
      return this.mv.visitAnnotation(arg0, arg1);
   }

   public AnnotationVisitor visitAnnotationDefault() {
      return this.mv.visitAnnotationDefault();
   }

   public void visitAttribute(Attribute arg0) {
      this.mv.visitAttribute(arg0);
   }

   public void visitCode() {
      this.mv.visitCode();
   }

   public void visitEnd() {
      this.mv.visitEnd();
   }

   public void visitFieldInsn(int arg0, String arg1, String arg2, String arg3) {
      this.mv.visitFieldInsn(arg0, arg1, arg2, arg3);
   }

   public void visitFrame(int arg0, int arg1, Object[] arg2, int arg3, Object[] arg4) {
      this.mv.visitFrame(arg0, arg1, arg2, arg3, arg4);
   }

   public void visitIincInsn(int arg0, int arg1) {
      this.mv.visitIincInsn(arg0, arg1);
   }

   public void visitInsn(int arg0) {
      this.mv.visitInsn(arg0);
   }

   public void visitIntInsn(int arg0, int arg1) {
      this.mv.visitIntInsn(arg0, arg1);
   }

   public void visitJumpInsn(int arg0, Label arg1) {
      this.mv.visitJumpInsn(arg0, arg1);
   }

   public void visitLabel(Label arg0) {
      this.mv.visitLabel(arg0);
   }

   public void visitLdcInsn(Object arg0) {
      this.mv.visitLdcInsn(arg0);
   }

   public void visitLineNumber(int arg0, Label arg1) {
      this.mv.visitLineNumber(arg0, arg1);
   }

   public void visitLocalVariable(String arg0, String arg1, String arg2, Label arg3, Label arg4, int arg5) {
      this.mv.visitLocalVariable(arg0, arg1, arg2, arg3, arg4, arg5);
   }

   public void visitLookupSwitchInsn(Label arg0, int[] arg1, Label[] arg2) {
      this.mv.visitLookupSwitchInsn(arg0, arg1, arg2);
   }

   public void visitMaxs(int arg0, int arg1) {
      this.mv.visitMaxs(arg0, arg1);
   }

   public void visitMethodInsn(int arg0, String arg1, String arg2, String arg3, boolean itf) {
      this.mv.visitMethodInsn(arg0, arg1, arg2, arg3, itf);
   }

   public void visitMultiANewArrayInsn(String arg0, int arg1) {
      this.mv.visitMultiANewArrayInsn(arg0, arg1);
   }

   public AnnotationVisitor visitParameterAnnotation(int arg0, String arg1, boolean arg2) {
      return this.mv.visitParameterAnnotation(arg0, arg1, arg2);
   }

   public void visitTableSwitchInsn(int arg0, int arg1, Label arg2, Label... arg3) {
      this.mv.visitTableSwitchInsn(arg0, arg1, arg2, arg3);
   }

   public void visitTryCatchBlock(Label arg0, Label arg1, Label arg2, String arg3) {
      this.mv.visitTryCatchBlock(arg0, arg1, arg2, arg3);
   }

   public void visitTypeInsn(int arg0, String arg1) {
      this.mv.visitTypeInsn(arg0, arg1);
   }

   public void visitVarInsn(int arg0, int arg1) {
      this.mv.visitVarInsn(arg0, arg1);
   }

   private int sigSize(String sig, boolean includeReturn) {
      int stack = 0;
      int i = 0;
      char[] c = sig.toCharArray();
      int n = c.length;
      boolean ret = false;
      boolean array = false;

      while(true) {
         while(true) {
            ++i;
            if (i >= n) {
               return stack;
            }

            switch (c[i]) {
               case ')':
                  if (!includeReturn) {
                     return stack;
                  }

                  ret = true;
                  break;
               case 'D':
               case 'J':
                  if (array) {
                     if (ret) {
                        ++stack;
                     } else {
                        --stack;
                     }

                     array = false;
                  } else if (ret) {
                     stack += 2;
                  } else {
                     stack -= 2;
                  }
                  break;
               case 'L':
                  while(true) {
                     ++i;
                     if (c[i] != ';') {
                        continue;
                     }
                     break;
                  }
               default:
                  if (ret) {
                     ++stack;
                  } else {
                     --stack;
                  }

                  array = false;
               case 'V':
                  break;
               case '[':
                  array = true;
            }
         }
      }
   }

   public void aaload() {
      this.mv.visitInsn(50);
   }

   public void aastore() {
      this.mv.visitInsn(83);
   }

   public void aconst_null() {
      this.mv.visitInsn(1);
   }

   public void aload(int index) {
      this.mv.visitVarInsn(25, index);
   }

   public void anewarray(String index) {
      this.mv.visitTypeInsn(189, index);
   }

   public void areturn() {
      this.mv.visitInsn(176);
   }

   public void arraylength() {
      this.mv.visitInsn(190);
   }

   public void astore(int index) {
      this.mv.visitVarInsn(58, index);
   }

   public void athrow() {
      this.mv.visitInsn(191);
   }

   public void baload() {
      this.mv.visitInsn(51);
   }

   public void bastore() {
      this.mv.visitInsn(84);
   }

   public void bipush(int value) {
      this.mv.visitIntInsn(16, value);
   }

   public void checkcast(String type) {
      this.mv.visitTypeInsn(192, type);
   }

   public void dconst_0() {
      this.mv.visitInsn(14);
   }

   public void dload(int index) {
      this.mv.visitVarInsn(24, index);
   }

   public void dreturn() {
      this.mv.visitInsn(175);
   }

   public void dup() {
      this.mv.visitInsn(89);
   }

   public void dup2() {
      this.mv.visitInsn(92);
   }

   public void dup_x1() {
      this.mv.visitInsn(90);
   }

   public void dup_x2() {
      this.mv.visitInsn(91);
   }

   public void dup2_x1() {
      this.mv.visitInsn(93);
   }

   public void dup2_x2() {
      this.mv.visitInsn(94);
   }

   public void fconst_0() {
      this.mv.visitInsn(11);
   }

   public void fload(int index) {
      this.mv.visitVarInsn(23, index);
   }

   public void freturn() {
      this.mv.visitInsn(174);
   }

   public void getfield(String owner, String name, String type) {
      this.mv.visitFieldInsn(180, owner, name, type);
   }

   public void getstatic(String owner, String name, String type) {
      this.mv.visitFieldInsn(178, owner, name, type);
   }

   public void goto_(Label label) {
      this.mv.visitJumpInsn(167, label);
   }

   public void iconst(int value) {
      if (value <= 127 && value >= -128) {
         switch (value) {
            case -1:
               this.iconst_m1();
               break;
            case 0:
               this.iconst_0();
               break;
            case 1:
               this.iconst_1();
               break;
            case 2:
               this.iconst_2();
               break;
            case 3:
               this.iconst_3();
               break;
            case 4:
               this.iconst_4();
               break;
            case 5:
               this.iconst_5();
               break;
            default:
               this.bipush(value);
         }
      } else if (value <= 32767 && value >= -32768) {
         this.sipush(value);
      } else {
         this.ldc(value);
      }

   }

   public void iconst_m1() {
      this.mv.visitInsn(2);
   }

   public void iconst_0() {
      this.mv.visitInsn(3);
   }

   public void iconst_1() {
      this.mv.visitInsn(4);
   }

   public void iconst_2() {
      this.mv.visitInsn(5);
   }

   public void iconst_3() {
      this.mv.visitInsn(6);
   }

   public void iconst_4() {
      this.mv.visitInsn(7);
   }

   public void iconst_5() {
      this.mv.visitInsn(8);
   }

   public void ifeq(Label label) {
      this.mv.visitJumpInsn(153, label);
   }

   public void ifle(Label label) {
      this.mv.visitJumpInsn(158, label);
   }

   public void ifne(Label label) {
      this.mv.visitJumpInsn(154, label);
   }

   public void ifnull(Label label) {
      this.mv.visitJumpInsn(198, label);
   }

   public void ifnonnull(Label label) {
      this.mv.visitJumpInsn(199, label);
   }

   public void if_acmpne(Label label) {
      this.mv.visitJumpInsn(166, label);
   }

   public void if_acmpeq(Label label) {
      this.mv.visitJumpInsn(165, label);
   }

   public void if_icmple(Label label) {
      this.mv.visitJumpInsn(164, label);
   }

   public void if_icmpgt(Label label) {
      this.mv.visitJumpInsn(163, label);
   }

   public void if_icmplt(Label label) {
      this.mv.visitJumpInsn(161, label);
   }

   public void if_icmpne(Label label) {
      this.mv.visitJumpInsn(160, label);
   }

   public void if_icmpeq(Label label) {
      this.mv.visitJumpInsn(159, label);
   }

   public void iadd() {
      this.mv.visitInsn(96);
   }

   public void iaload() {
      this.mv.visitInsn(46);
   }

   public void iinc() {
      this.mv.visitInsn(132);
   }

   public void iload(int index) {
      this.mv.visitVarInsn(21, index);
   }

   public void instanceof_(String type) {
      this.mv.visitTypeInsn(193, type);
   }

   public void invokeinterface(String owner, String name, String type) {
      this.mv.visitMethodInsn(185, owner, name, type, false);
   }

   public void invokeinterface(String owner, String name, String type, boolean itf) {
      this.mv.visitMethodInsn(185, owner, name, type, itf);
   }

   public void invokespecial(String owner, String name, String type) {
      this.mv.visitMethodInsn(183, owner, name, type, false);
   }

   public void invokestatic(String owner, String name, String type) {
      this.mv.visitMethodInsn(184, owner, name, type, false);
   }

   public void invokevirtual(String owner, String name, String type) {
      this.mv.visitMethodInsn(182, owner, name, type, false);
   }

   public void ireturn() {
      this.mv.visitInsn(172);
   }

   public void istore(int index) {
      this.mv.visitVarInsn(54, index);
   }

   public void isub() {
      this.mv.visitInsn(100);
   }

   public void label(Label label) {
      this.mv.visitLabel(label);
   }

   public void lconst_0() {
      this.mv.visitInsn(9);
   }

   public void ldc(Object cst) {
      if (cst instanceof String) {
         String value = (String)cst;
         int len = value.length();
         int maxlen = true;
         if (len > 16000) {
            this.new_("java/lang/StringBuilder");
            this.dup();
            this.iconst(len);
            this.invokespecial("java/lang/StringBuilder", "<init>", "(I)V");

            for(int i = 0; i < len; i += 16000) {
               int j = i + 16000;
               if (j > len) {
                  j = len;
               }

               this.mv.visitLdcInsn(value.substring(i, j));
               this.invokevirtual("java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;");
            }

            this.invokevirtual("java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
         } else {
            this.mv.visitLdcInsn(value);
         }
      } else {
         this.mv.visitLdcInsn(cst);
      }

   }

   public void lload(int index) {
      this.mv.visitVarInsn(22, index);
   }

   public void lreturn() {
      this.mv.visitInsn(173);
   }

   public void newarray(int atype) {
      this.mv.visitIntInsn(188, atype);
   }

   public void new_(String type) {
      this.mv.visitTypeInsn(187, type);
   }

   public void nop() {
      this.mv.visitInsn(0);
   }

   public void pop() {
      this.mv.visitInsn(87);
   }

   public void pop2() {
      this.mv.visitInsn(88);
   }

   public void putstatic(String owner, String name, String type) {
      this.mv.visitFieldInsn(179, owner, name, type);
   }

   public void putfield(String owner, String name, String type) {
      this.mv.visitFieldInsn(181, owner, name, type);
   }

   public void ret(int index) {
      this.mv.visitVarInsn(169, index);
   }

   public void return_() {
      this.mv.visitInsn(177);
   }

   public void sipush(int value) {
      this.mv.visitIntInsn(17, value);
   }

   public void swap() {
      this.mv.visitInsn(95);
   }

   public void swap2() {
      this.dup2_x2();
      this.pop2();
   }

   public void tableswitch(int arg0, int arg1, Label arg2, Label[] arg3) {
      this.mv.visitTableSwitchInsn(arg0, arg1, arg2, arg3);
   }

   public void trycatch(Label start, Label end, Label handlerStart, String type) {
      this.mv.visitTryCatchBlock(start, end, handlerStart, type);
   }

   public void setline(int line) {
      this.mv.visitLineNumber(line, new Label());
   }

   public void visitInvokeDynamicInsn(String name, String descriptor, Handle bsmHandle, Object... bmsArgs) {
      this.mv.visitInvokeDynamicInsn(name, descriptor, bsmHandle, bmsArgs);
   }
}

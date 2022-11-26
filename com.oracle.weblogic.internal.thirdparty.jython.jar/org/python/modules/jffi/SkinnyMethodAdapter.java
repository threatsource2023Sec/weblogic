package org.python.modules.jffi;

import java.io.PrintStream;
import java.io.PrintWriter;
import org.python.objectweb.asm.AnnotationVisitor;
import org.python.objectweb.asm.Attribute;
import org.python.objectweb.asm.ClassReader;
import org.python.objectweb.asm.ClassVisitor;
import org.python.objectweb.asm.ClassWriter;
import org.python.objectweb.asm.Label;
import org.python.objectweb.asm.MethodVisitor;
import org.python.objectweb.asm.Opcodes;
import org.python.objectweb.asm.util.Printer;
import org.python.objectweb.asm.util.Textifier;
import org.python.objectweb.asm.util.TraceMethodVisitor;

public class SkinnyMethodAdapter extends MethodVisitor implements Opcodes {
   private static final boolean DEBUG = Boolean.getBoolean("jython.compile.dump");
   private MethodVisitor method;
   private Printer printer;
   private String name;
   private ClassVisitor cv;

   public SkinnyMethodAdapter(ClassVisitor cv, int flags, String name, String signature, String something, String[] exceptions) {
      super(262144);
      this.setMethodVisitor(cv.visitMethod(flags, name, signature, something, exceptions));
      this.cv = cv;
      this.name = name;
   }

   public MethodVisitor getMethodVisitor() {
      return this.method;
   }

   public void setMethodVisitor(MethodVisitor mv) {
      if (DEBUG) {
         this.printer = new Textifier();
         this.method = new TraceMethodVisitor(mv, this.printer);
      } else {
         this.method = mv;
      }

   }

   public void aloadMany(int... args) {
      int[] var2 = args;
      int var3 = args.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int arg = var2[var4];
         this.aload(arg);
      }

   }

   public void aload(int arg0) {
      this.getMethodVisitor().visitVarInsn(25, arg0);
   }

   public void iload(int arg0) {
      this.getMethodVisitor().visitVarInsn(21, arg0);
   }

   public void lload(int arg0) {
      this.getMethodVisitor().visitVarInsn(22, arg0);
   }

   public void fload(int arg0) {
      this.getMethodVisitor().visitVarInsn(23, arg0);
   }

   public void dload(int arg0) {
      this.getMethodVisitor().visitVarInsn(24, arg0);
   }

   public void astore(int arg0) {
      this.getMethodVisitor().visitVarInsn(58, arg0);
   }

   public void istore(int arg0) {
      this.getMethodVisitor().visitVarInsn(54, arg0);
   }

   public void lstore(int arg0) {
      this.getMethodVisitor().visitVarInsn(55, arg0);
   }

   public void fstore(int arg0) {
      this.getMethodVisitor().visitVarInsn(56, arg0);
   }

   public void dstore(int arg0) {
      this.getMethodVisitor().visitVarInsn(57, arg0);
   }

   public void ldc(Object arg0) {
      this.getMethodVisitor().visitLdcInsn(arg0);
   }

   public void bipush(int arg) {
      this.getMethodVisitor().visitIntInsn(16, arg);
   }

   public void sipush(int arg) {
      this.getMethodVisitor().visitIntInsn(17, arg);
   }

   public void pushInt(int value) {
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

   public void pushBoolean(boolean bool) {
      if (bool) {
         this.iconst_1();
      } else {
         this.iconst_0();
      }

   }

   public void invokestatic(String arg1, String arg2, String arg3) {
      this.getMethodVisitor().visitMethodInsn(184, arg1, arg2, arg3);
   }

   public void invokespecial(String arg1, String arg2, String arg3) {
      this.getMethodVisitor().visitMethodInsn(183, arg1, arg2, arg3);
   }

   public void invokevirtual(String arg1, String arg2, String arg3) {
      this.getMethodVisitor().visitMethodInsn(182, arg1, arg2, arg3);
   }

   public void invokeinterface(String arg1, String arg2, String arg3) {
      this.getMethodVisitor().visitMethodInsn(185, arg1, arg2, arg3);
   }

   public void aprintln() {
      this.dup();
      this.getstatic(CodegenUtils.p(System.class), "out", CodegenUtils.ci(PrintStream.class));
      this.swap();
      this.invokevirtual(CodegenUtils.p(PrintStream.class), "println", CodegenUtils.sig(Void.TYPE, CodegenUtils.params(Object.class)));
   }

   public void iprintln() {
      this.dup();
      this.getstatic(CodegenUtils.p(System.class), "out", CodegenUtils.ci(PrintStream.class));
      this.swap();
      this.invokevirtual(CodegenUtils.p(PrintStream.class), "println", CodegenUtils.sig(Void.TYPE, CodegenUtils.params(Integer.TYPE)));
   }

   public void areturn() {
      this.getMethodVisitor().visitInsn(176);
   }

   public void ireturn() {
      this.getMethodVisitor().visitInsn(172);
   }

   public void freturn() {
      this.getMethodVisitor().visitInsn(174);
   }

   public void lreturn() {
      this.getMethodVisitor().visitInsn(173);
   }

   public void dreturn() {
      this.getMethodVisitor().visitInsn(175);
   }

   public void newobj(String arg0) {
      this.getMethodVisitor().visitTypeInsn(187, arg0);
   }

   public void dup() {
      this.getMethodVisitor().visitInsn(89);
   }

   public void swap() {
      this.getMethodVisitor().visitInsn(95);
   }

   public void swap2() {
      this.dup2_x2();
      this.pop2();
   }

   public void getstatic(String arg1, String arg2, String arg3) {
      this.getMethodVisitor().visitFieldInsn(178, arg1, arg2, arg3);
   }

   public void putstatic(String arg1, String arg2, String arg3) {
      this.getMethodVisitor().visitFieldInsn(179, arg1, arg2, arg3);
   }

   public void getfield(String arg1, String arg2, String arg3) {
      this.getMethodVisitor().visitFieldInsn(180, arg1, arg2, arg3);
   }

   public void putfield(String arg1, String arg2, String arg3) {
      this.getMethodVisitor().visitFieldInsn(181, arg1, arg2, arg3);
   }

   public void voidreturn() {
      this.getMethodVisitor().visitInsn(177);
   }

   public void anewarray(String arg0) {
      this.getMethodVisitor().visitTypeInsn(189, arg0);
   }

   public void multianewarray(String arg0, int dims) {
      this.getMethodVisitor().visitMultiANewArrayInsn(arg0, dims);
   }

   public void newarray(int arg0) {
      this.getMethodVisitor().visitIntInsn(188, arg0);
   }

   public void iconst_m1() {
      this.getMethodVisitor().visitInsn(2);
   }

   public void iconst_0() {
      this.getMethodVisitor().visitInsn(3);
   }

   public void iconst_1() {
      this.getMethodVisitor().visitInsn(4);
   }

   public void iconst_2() {
      this.getMethodVisitor().visitInsn(5);
   }

   public void iconst_3() {
      this.getMethodVisitor().visitInsn(6);
   }

   public void iconst_4() {
      this.getMethodVisitor().visitInsn(7);
   }

   public void iconst_5() {
      this.getMethodVisitor().visitInsn(8);
   }

   public void lconst_0() {
      this.getMethodVisitor().visitInsn(9);
   }

   public void aconst_null() {
      this.getMethodVisitor().visitInsn(1);
   }

   public void label(Label label) {
      this.getMethodVisitor().visitLabel(label);
   }

   public void nop() {
      this.getMethodVisitor().visitInsn(0);
   }

   public void pop() {
      this.getMethodVisitor().visitInsn(87);
   }

   public void pop2() {
      this.getMethodVisitor().visitInsn(88);
   }

   public void arrayload() {
      this.getMethodVisitor().visitInsn(50);
   }

   public void arraystore() {
      this.getMethodVisitor().visitInsn(83);
   }

   public void iarrayload() {
      this.getMethodVisitor().visitInsn(46);
   }

   public void barrayload() {
      this.getMethodVisitor().visitInsn(51);
   }

   public void barraystore() {
      this.getMethodVisitor().visitInsn(84);
   }

   public void aaload() {
      this.getMethodVisitor().visitInsn(50);
   }

   public void aastore() {
      this.getMethodVisitor().visitInsn(83);
   }

   public void iaload() {
      this.getMethodVisitor().visitInsn(46);
   }

   public void iastore() {
      this.getMethodVisitor().visitInsn(79);
   }

   public void laload() {
      this.getMethodVisitor().visitInsn(47);
   }

   public void lastore() {
      this.getMethodVisitor().visitInsn(80);
   }

   public void baload() {
      this.getMethodVisitor().visitInsn(51);
   }

   public void bastore() {
      this.getMethodVisitor().visitInsn(84);
   }

   public void saload() {
      this.getMethodVisitor().visitInsn(53);
   }

   public void sastore() {
      this.getMethodVisitor().visitInsn(86);
   }

   public void caload() {
      this.getMethodVisitor().visitInsn(52);
   }

   public void castore() {
      this.getMethodVisitor().visitInsn(85);
   }

   public void faload() {
      this.getMethodVisitor().visitInsn(48);
   }

   public void fastore() {
      this.getMethodVisitor().visitInsn(81);
   }

   public void daload() {
      this.getMethodVisitor().visitInsn(49);
   }

   public void dastore() {
      this.getMethodVisitor().visitInsn(82);
   }

   public void fcmpl() {
      this.getMethodVisitor().visitInsn(149);
   }

   public void fcmpg() {
      this.getMethodVisitor().visitInsn(150);
   }

   public void dcmpl() {
      this.getMethodVisitor().visitInsn(151);
   }

   public void dcmpg() {
      this.getMethodVisitor().visitInsn(152);
   }

   public void dup_x2() {
      this.getMethodVisitor().visitInsn(91);
   }

   public void dup_x1() {
      this.getMethodVisitor().visitInsn(90);
   }

   public void dup2_x2() {
      this.getMethodVisitor().visitInsn(94);
   }

   public void dup2_x1() {
      this.getMethodVisitor().visitInsn(93);
   }

   public void dup2() {
      this.getMethodVisitor().visitInsn(92);
   }

   public void trycatch(Label arg0, Label arg1, Label arg2, String arg3) {
      this.getMethodVisitor().visitTryCatchBlock(arg0, arg1, arg2, arg3);
   }

   public void trycatch(String type, Runnable body, Runnable catchBody) {
      Label before = new Label();
      Label after = new Label();
      Label catchStart = new Label();
      Label done = new Label();
      this.trycatch(before, after, catchStart, type);
      this.label(before);
      body.run();
      this.label(after);
      this.go_to(done);
      if (catchBody != null) {
         this.label(catchStart);
         catchBody.run();
      }

      this.label(done);
   }

   public void go_to(Label arg0) {
      this.getMethodVisitor().visitJumpInsn(167, arg0);
   }

   public void lookupswitch(Label arg0, int[] arg1, Label[] arg2) {
      this.getMethodVisitor().visitLookupSwitchInsn(arg0, arg1, arg2);
   }

   public void athrow() {
      this.getMethodVisitor().visitInsn(191);
   }

   public void instance_of(String arg0) {
      this.getMethodVisitor().visitTypeInsn(193, arg0);
   }

   public void ifeq(Label arg0) {
      this.getMethodVisitor().visitJumpInsn(153, arg0);
   }

   public void iffalse(Label arg0) {
      this.ifeq(arg0);
   }

   public void ifne(Label arg0) {
      this.getMethodVisitor().visitJumpInsn(154, arg0);
   }

   public void iftrue(Label arg0) {
      this.ifne(arg0);
   }

   public void if_acmpne(Label arg0) {
      this.getMethodVisitor().visitJumpInsn(166, arg0);
   }

   public void if_acmpeq(Label arg0) {
      this.getMethodVisitor().visitJumpInsn(165, arg0);
   }

   public void if_icmple(Label arg0) {
      this.getMethodVisitor().visitJumpInsn(164, arg0);
   }

   public void if_icmpgt(Label arg0) {
      this.getMethodVisitor().visitJumpInsn(163, arg0);
   }

   public void if_icmplt(Label arg0) {
      this.getMethodVisitor().visitJumpInsn(161, arg0);
   }

   public void if_icmpne(Label arg0) {
      this.getMethodVisitor().visitJumpInsn(160, arg0);
   }

   public void if_icmpeq(Label arg0) {
      this.getMethodVisitor().visitJumpInsn(159, arg0);
   }

   public void checkcast(String arg0) {
      this.getMethodVisitor().visitTypeInsn(192, arg0);
   }

   public void start() {
      this.getMethodVisitor().visitCode();
   }

   public void end() {
      if (DEBUG) {
         PrintWriter pw = new PrintWriter(System.out);
         String className = "(unknown class)";
         if (this.cv instanceof ClassWriter) {
            className = (new ClassReader(((ClassWriter)this.cv).toByteArray())).getClassName();
         }

         if (this.name != null) {
            pw.write("*** Dumping " + className + "." + this.name + " ***\n");
         } else {
            pw.write("*** Dumping ***\n");
         }

         this.printer.print(pw);
         pw.flush();
      }

      this.getMethodVisitor().visitMaxs(1, 1);
      this.getMethodVisitor().visitEnd();
   }

   public void line(int line) {
      Label label = new Label();
      this.label(label);
      this.visitLineNumber(line, label);
   }

   public void line(int line, Label label) {
      this.visitLineNumber(line, label);
   }

   public void ifnonnull(Label arg0) {
      this.getMethodVisitor().visitJumpInsn(199, arg0);
   }

   public void ifnull(Label arg0) {
      this.getMethodVisitor().visitJumpInsn(198, arg0);
   }

   public void iflt(Label arg0) {
      this.getMethodVisitor().visitJumpInsn(155, arg0);
   }

   public void ifle(Label arg0) {
      this.getMethodVisitor().visitJumpInsn(158, arg0);
   }

   public void ifgt(Label arg0) {
      this.getMethodVisitor().visitJumpInsn(157, arg0);
   }

   public void ifge(Label arg0) {
      this.getMethodVisitor().visitJumpInsn(156, arg0);
   }

   public void arraylength() {
      this.getMethodVisitor().visitInsn(190);
   }

   public void ishr() {
      this.getMethodVisitor().visitInsn(122);
   }

   public void ishl() {
      this.getMethodVisitor().visitInsn(120);
   }

   public void iushr() {
      this.getMethodVisitor().visitInsn(124);
   }

   public void lshr() {
      this.getMethodVisitor().visitInsn(123);
   }

   public void lshl() {
      this.getMethodVisitor().visitInsn(121);
   }

   public void lushr() {
      this.getMethodVisitor().visitInsn(125);
   }

   public void lcmp() {
      this.getMethodVisitor().visitInsn(148);
   }

   public void iand() {
      this.getMethodVisitor().visitInsn(126);
   }

   public void ior() {
      this.getMethodVisitor().visitInsn(128);
   }

   public void ixor() {
      this.getMethodVisitor().visitInsn(130);
   }

   public void land() {
      this.getMethodVisitor().visitInsn(127);
   }

   public void lor() {
      this.getMethodVisitor().visitInsn(129);
   }

   public void lxor() {
      this.getMethodVisitor().visitInsn(131);
   }

   public void iadd() {
      this.getMethodVisitor().visitInsn(96);
   }

   public void ladd() {
      this.getMethodVisitor().visitInsn(97);
   }

   public void fadd() {
      this.getMethodVisitor().visitInsn(98);
   }

   public void dadd() {
      this.getMethodVisitor().visitInsn(99);
   }

   public void isub() {
      this.getMethodVisitor().visitInsn(100);
   }

   public void lsub() {
      this.getMethodVisitor().visitInsn(101);
   }

   public void fsub() {
      this.getMethodVisitor().visitInsn(102);
   }

   public void dsub() {
      this.getMethodVisitor().visitInsn(103);
   }

   public void idiv() {
      this.getMethodVisitor().visitInsn(108);
   }

   public void irem() {
      this.getMethodVisitor().visitInsn(112);
   }

   public void ineg() {
      this.getMethodVisitor().visitInsn(116);
   }

   public void i2d() {
      this.getMethodVisitor().visitInsn(135);
   }

   public void i2l() {
      this.getMethodVisitor().visitInsn(133);
   }

   public void i2f() {
      this.getMethodVisitor().visitInsn(134);
   }

   public void i2s() {
      this.getMethodVisitor().visitInsn(147);
   }

   public void i2c() {
      this.getMethodVisitor().visitInsn(146);
   }

   public void i2b() {
      this.getMethodVisitor().visitInsn(145);
   }

   public void ldiv() {
      this.getMethodVisitor().visitInsn(109);
   }

   public void lrem() {
      this.getMethodVisitor().visitInsn(113);
   }

   public void lneg() {
      this.getMethodVisitor().visitInsn(117);
   }

   public void l2d() {
      this.getMethodVisitor().visitInsn(138);
   }

   public void l2i() {
      this.getMethodVisitor().visitInsn(136);
   }

   public void l2f() {
      this.getMethodVisitor().visitInsn(137);
   }

   public void fdiv() {
      this.getMethodVisitor().visitInsn(110);
   }

   public void frem() {
      this.getMethodVisitor().visitInsn(114);
   }

   public void fneg() {
      this.getMethodVisitor().visitInsn(118);
   }

   public void f2d() {
      this.getMethodVisitor().visitInsn(141);
   }

   public void f2i() {
      this.getMethodVisitor().visitInsn(141);
   }

   public void f2l() {
      this.getMethodVisitor().visitInsn(140);
   }

   public void ddiv() {
      this.getMethodVisitor().visitInsn(111);
   }

   public void drem() {
      this.getMethodVisitor().visitInsn(115);
   }

   public void dneg() {
      this.getMethodVisitor().visitInsn(119);
   }

   public void d2f() {
      this.getMethodVisitor().visitInsn(144);
   }

   public void d2i() {
      this.getMethodVisitor().visitInsn(142);
   }

   public void d2l() {
      this.getMethodVisitor().visitInsn(143);
   }

   public void imul() {
      this.getMethodVisitor().visitInsn(104);
   }

   public void lmul() {
      this.getMethodVisitor().visitInsn(105);
   }

   public void fmul() {
      this.getMethodVisitor().visitInsn(106);
   }

   public void dmul() {
      this.getMethodVisitor().visitInsn(107);
   }

   public void iinc(int arg0, int arg1) {
      this.getMethodVisitor().visitIincInsn(arg0, arg1);
   }

   public void monitorenter() {
      this.getMethodVisitor().visitInsn(194);
   }

   public void monitorexit() {
      this.getMethodVisitor().visitInsn(195);
   }

   public void jsr(Label branch) {
      this.getMethodVisitor().visitJumpInsn(168, branch);
   }

   public void ret(int arg0) {
      this.getMethodVisitor().visitVarInsn(169, arg0);
   }

   public AnnotationVisitor visitAnnotationDefault() {
      return this.getMethodVisitor().visitAnnotationDefault();
   }

   public AnnotationVisitor visitAnnotation(String arg0, boolean arg1) {
      return this.getMethodVisitor().visitAnnotation(arg0, arg1);
   }

   public AnnotationVisitor visitParameterAnnotation(int arg0, String arg1, boolean arg2) {
      return this.getMethodVisitor().visitParameterAnnotation(arg0, arg1, arg2);
   }

   public void visitAttribute(Attribute arg0) {
      this.getMethodVisitor().visitAttribute(arg0);
   }

   public void visitCode() {
      this.getMethodVisitor().visitCode();
   }

   public void visitInsn(int arg0) {
      this.getMethodVisitor().visitInsn(arg0);
   }

   public void visitIntInsn(int arg0, int arg1) {
      this.getMethodVisitor().visitIntInsn(arg0, arg1);
   }

   public void visitVarInsn(int arg0, int arg1) {
      this.getMethodVisitor().visitVarInsn(arg0, arg1);
   }

   public void visitTypeInsn(int arg0, String arg1) {
      this.getMethodVisitor().visitTypeInsn(arg0, arg1);
   }

   public void visitFieldInsn(int arg0, String arg1, String arg2, String arg3) {
      this.getMethodVisitor().visitFieldInsn(arg0, arg1, arg2, arg3);
   }

   public void visitMethodInsn(int arg0, String arg1, String arg2, String arg3) {
      this.getMethodVisitor().visitMethodInsn(arg0, arg1, arg2, arg3);
   }

   public void visitJumpInsn(int arg0, Label arg1) {
      this.getMethodVisitor().visitJumpInsn(arg0, arg1);
   }

   public void visitLabel(Label arg0) {
      this.getMethodVisitor().visitLabel(arg0);
   }

   public void visitLdcInsn(Object arg0) {
      this.getMethodVisitor().visitLdcInsn(arg0);
   }

   public void visitIincInsn(int arg0, int arg1) {
      this.getMethodVisitor().visitIincInsn(arg0, arg1);
   }

   public void visitTableSwitchInsn(int arg0, int arg1, Label arg2, Label... arg3) {
      this.getMethodVisitor().visitTableSwitchInsn(arg0, arg1, arg2, arg3);
   }

   public void visitLookupSwitchInsn(Label arg0, int[] arg1, Label[] arg2) {
      this.getMethodVisitor().visitLookupSwitchInsn(arg0, arg1, arg2);
   }

   public void visitMultiANewArrayInsn(String arg0, int arg1) {
      this.getMethodVisitor().visitMultiANewArrayInsn(arg0, arg1);
   }

   public void visitTryCatchBlock(Label arg0, Label arg1, Label arg2, String arg3) {
      this.getMethodVisitor().visitTryCatchBlock(arg0, arg1, arg2, arg3);
   }

   public void visitLocalVariable(String arg0, String arg1, String arg2, Label arg3, Label arg4, int arg5) {
      this.getMethodVisitor().visitLocalVariable(arg0, arg1, arg2, arg3, arg4, arg5);
   }

   public void visitLineNumber(int arg0, Label arg1) {
      this.getMethodVisitor().visitLineNumber(arg0, arg1);
   }

   public void visitMaxs(int arg0, int arg1) {
      if (DEBUG) {
         PrintWriter pw = new PrintWriter(System.out);
         pw.write("*** Dumping ***\n");
         this.printer.print(pw);
         pw.flush();
      }

      this.getMethodVisitor().visitMaxs(arg0, arg1);
   }

   public void visitEnd() {
      this.getMethodVisitor().visitEnd();
   }

   public void tableswitch(int min, int max, Label defaultLabel, Label[] cases) {
      this.getMethodVisitor().visitTableSwitchInsn(min, max, defaultLabel, cases);
   }

   public void visitFrame(int arg0, int arg1, Object[] arg2, int arg3, Object[] arg4) {
      this.getMethodVisitor().visitFrame(arg0, arg1, arg2, arg3, arg4);
   }
}

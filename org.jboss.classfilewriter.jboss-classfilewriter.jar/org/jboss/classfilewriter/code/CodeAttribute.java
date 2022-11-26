package org.jboss.classfilewriter.code;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jboss.classfilewriter.ClassMethod;
import org.jboss.classfilewriter.InvalidBytecodeException;
import org.jboss.classfilewriter.attributes.Attribute;
import org.jboss.classfilewriter.attributes.StackMapTableAttribute;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;
import org.jboss.classfilewriter.util.DescriptorUtils;
import org.jboss.classfilewriter.util.LazySize;

public class CodeAttribute extends Attribute {
   public static final String NAME = "Code";
   private final ClassMethod method;
   private final ConstPool constPool;
   private final ByteArrayOutputStream finalDataBytes;
   private final DataOutputStream data;
   private int maxLocals = 0;
   private int maxStackDepth = 0;
   private final LinkedHashMap stackFrames = new LinkedHashMap();
   private final Map jumpLocations = new HashMap();
   private final Map jumpLocations32 = new HashMap();
   private StackFrame currentFrame;
   private int currentOffset;
   private final List attributes = new ArrayList();
   private final StackMapTableAttribute stackMapTableAttribute;
   private final List exceptionTable = new ArrayList();
   private StackFrameTypeResolver stackFrameTypeResolver;

   public CodeAttribute(ClassMethod method, ConstPool constPool) {
      super("Code", constPool);
      this.method = method;
      this.constPool = constPool;
      this.finalDataBytes = new ByteArrayOutputStream();
      this.data = new DataOutputStream(this.finalDataBytes);
      if (!Modifier.isStatic(method.getAccessFlags())) {
         ++this.maxLocals;
      }

      String[] var3 = method.getParameters();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String param = var3[var5];
         if (DescriptorUtils.isWide(param)) {
            this.maxLocals += 2;
         } else {
            ++this.maxLocals;
         }
      }

      this.currentFrame = new StackFrame(method);
      this.stackFrames.put(0, this.currentFrame);
      this.currentOffset = 0;
      this.stackMapTableAttribute = new StackMapTableAttribute(method, constPool);
   }

   public StackFrameTypeResolver getStackFrameTypeResolver() {
      return this.stackFrameTypeResolver;
   }

   public void setStackFrameTypeResolver(StackFrameTypeResolver stackFrameTypeResolver) {
      this.stackFrameTypeResolver = stackFrameTypeResolver;
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      if (this.method.getClassFile().getClassLoader() != null) {
         this.attributes.add(this.stackMapTableAttribute);
      }

      if (this.finalDataBytes.size() == 0) {
         throw new RuntimeException("Code attribute is empty for method " + this.method.getName() + "  " + this.method.getDescriptor());
      } else {
         byte[] bytecode = this.finalDataBytes.toByteArray();
         Iterator var3 = this.jumpLocations.entrySet().iterator();

         Map.Entry e;
         while(var3.hasNext()) {
            e = (Map.Entry)var3.next();
            this.overwriteShort(bytecode, (Integer)e.getKey(), (Integer)e.getValue());
         }

         var3 = this.jumpLocations32.entrySet().iterator();

         while(var3.hasNext()) {
            e = (Map.Entry)var3.next();
            this.overwriteInt(bytecode, (Integer)e.getKey(), (Integer)e.getValue());
         }

         LazySize size = stream.writeSize();
         stream.writeShort(this.maxStackDepth);
         stream.writeShort(this.maxLocals);
         stream.writeInt(bytecode.length);
         stream.write(bytecode);
         stream.writeShort(this.exceptionTable.size());
         Iterator var7 = this.exceptionTable.iterator();

         while(var7.hasNext()) {
            ExceptionHandler exception = (ExceptionHandler)var7.next();
            stream.writeShort(exception.getStart());
            stream.writeShort(exception.getEnd());
            stream.writeShort(exception.getHandler());
            stream.writeShort(exception.getExceptionIndex());
         }

         stream.writeShort(this.attributes.size());
         var7 = this.attributes.iterator();

         while(var7.hasNext()) {
            Attribute attribute = (Attribute)var7.next();
            attribute.write(stream);
         }

         size.markEnd();
      }
   }

   public void aaload() {
      this.assertTypeOnStack(StackEntryType.INT, "aaload requires int on top of stack");
      if (!this.getStack().top_1().getDescriptor().startsWith("[")) {
         throw new InvalidBytecodeException("aaload needs an array in position 2 on the stack");
      } else {
         this.writeByte(50);
         ++this.currentOffset;
         this.advanceFrame(this.currentFrame.pop2push1("Ljava/lang/Object;"));
      }
   }

   public void aastore() {
      this.assertTypeOnStack(StackEntryType.OBJECT, "aastore requires reference type on top of stack");
      this.assertTypeOnStack(1, StackEntryType.INT, "aastore requires an int on position 2 stack");
      if (!this.getStack().top_2().getDescriptor().startsWith("[")) {
         throw new InvalidBytecodeException("aaload needs an array in position 3 on the stack");
      } else {
         this.writeByte(83);
         ++this.currentOffset;
         this.advanceFrame(this.currentFrame.pop3());
      }
   }

   public void aconstNull() {
      this.writeByte(1);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.aconstNull());
   }

   public void aload(int no) {
      LocalVariableState locals = this.getLocalVars();
      if (locals.size() <= no) {
         throw new InvalidBytecodeException("Cannot load variable at " + no + ". Local Variables: " + locals.toString());
      } else {
         StackEntry entry = locals.get(no);
         if (entry.getType() != StackEntryType.OBJECT && entry.getType() != StackEntryType.NULL && entry.getType() != StackEntryType.UNINITIALIZED_THIS && entry.getType() != StackEntryType.UNITITIALIZED_OBJECT) {
            throw new InvalidBytecodeException("Invalid local variable at location " + no + " Local Variables " + locals.toString());
         } else {
            if (no > 255) {
               this.writeByte(196);
               this.writeByte(25);
               this.writeShort(no);
               this.currentOffset += 4;
            } else if (no >= 0 && no < 4) {
               this.writeByte(42 + no);
               ++this.currentOffset;
            } else {
               this.writeByte(25);
               this.writeByte(no);
               this.currentOffset += 2;
            }

            this.advanceFrame(this.currentFrame.push(entry));
         }
      }
   }

   public void anewarray(String arrayType) {
      this.assertTypeOnStack(StackEntryType.INT, "anewarray requires int on stack");
      int index = this.constPool.addClassEntry(arrayType);
      this.writeByte(189);
      this.writeShort(index);
      this.currentOffset += 3;
      if (arrayType.startsWith("[")) {
         this.advanceFrame(this.currentFrame.replace("[" + arrayType));
      } else {
         this.advanceFrame(this.currentFrame.replace("[L" + arrayType + ";"));
      }

   }

   public void arraylength() {
      this.assertTypeOnStack(StackEntryType.OBJECT, "arraylength requires array on stack");
      this.writeByte(190);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.replace("I"));
   }

   public void astore(int no) {
      this.assertTypeOnStack(StackEntryType.OBJECT, "aastore requires reference type on stack");
      if (no > 255) {
         this.writeByte(196);
         this.writeByte(58);
         this.writeShort(no);
         this.currentOffset += 4;
      } else if (no >= 0 && no < 4) {
         this.writeByte(75 + no);
         ++this.currentOffset;
      } else {
         this.writeByte(58);
         this.writeByte(no);
         this.currentOffset += 2;
      }

      this.advanceFrame(this.currentFrame.store(no));
   }

   public void athrow() {
      this.assertTypeOnStack(StackEntryType.OBJECT, "athrow requires an object on the stack");
      this.writeByte(191);
      ++this.currentOffset;
      this.currentFrame = null;
   }

   public void baload() {
      this.assertTypeOnStack(StackEntryType.INT, "baload requires an int on top of the stack");
      this.assertTypeOnStack(1, StackEntryType.OBJECT, "baload requires an array in position 2 on the stack");
      this.writeByte(51);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2push1("I"));
   }

   public void bastore() {
      this.assertTypeOnStack(StackEntryType.INT, "bastore requires an int on top of the stack");
      this.assertTypeOnStack(1, StackEntryType.INT, "bastore requires an int in position 2 on the stack");
      this.assertTypeOnStack(2, StackEntryType.OBJECT, "bastore requires an array reference in position 3 on the stack");
      this.writeByte(84);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop3());
   }

   public void caload() {
      this.assertTypeOnStack(StackEntryType.INT, "caload requires an int on top of the stack");
      this.assertTypeOnStack(1, StackEntryType.OBJECT, "caload requires an array in position 2 on the stack");
      this.writeByte(52);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2push1("I"));
   }

   public void castore() {
      this.assertTypeOnStack(StackEntryType.INT, "castore requires an int on top of the stack");
      this.assertTypeOnStack(1, StackEntryType.INT, "castore requires an int in position 2 on the stack");
      this.assertTypeOnStack(2, StackEntryType.OBJECT, "castore requires an array reference in position 3 on the stack");
      this.writeByte(85);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop3());
   }

   public void bipush(byte value) {
      this.writeByte(16);
      this.writeByte(value);
      this.currentOffset += 2;
      this.advanceFrame(this.currentFrame.push("B"));
   }

   public void branchEnd(BranchEnd end) {
      this.mergeStackFrames(end.getStackFrame());
      int jump = this.currentOffset - end.getOffsetLocation();
      if (end.isJump32Bit()) {
         this.jumpLocations32.put(end.getBranchLocation(), jump);
      } else {
         if (jump > 32767) {
            throw new RuntimeException(jump + " is to big to be written as a 16 bit value");
         }

         this.jumpLocations.put(end.getBranchLocation(), jump);
      }

   }

   public void checkcast(String className) {
      if (!className.startsWith("[") && className.endsWith(";")) {
         throw new RuntimeException("Invalid cast format " + className);
      } else {
         className = className.replace('.', '/');
         this.assertTypeOnStack(StackEntryType.OBJECT, "checkcast requires reference type on stack");
         int classIndex = this.constPool.addClassEntry(className);
         this.writeByte(192);
         this.writeShort(classIndex);
         this.currentOffset += 3;
         this.advanceFrame(this.currentFrame.replace(className));
      }
   }

   public void checkcast(Class clazz) {
      this.checkcast(clazz.getName());
   }

   public void d2f() {
      this.assertTypeOnStack(StackEntryType.DOUBLE, "d2f requires double on stack");
      this.writeByte(144);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2push1("F"));
   }

   public void d2i() {
      this.assertTypeOnStack(StackEntryType.DOUBLE, "d2i requires double on stack");
      this.writeByte(142);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2push1("I"));
   }

   public void d2l() {
      this.assertTypeOnStack(StackEntryType.DOUBLE, "d2l requires double on stack");
      this.writeByte(143);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2push1("J"));
   }

   public void dadd() {
      this.assertTypeOnStack(StackEntryType.DOUBLE, "dadd requires double on stack");
      this.assertTypeOnStack(2, StackEntryType.DOUBLE, "dadd requires double on stack");
      this.writeByte(99);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2());
   }

   public void daload() {
      this.assertTypeOnStack(StackEntryType.INT, "daload requires an int on top of the stack");
      this.assertTypeOnStack(1, StackEntryType.OBJECT, "daload requires an array in position 2 on the stack");
      this.writeByte(49);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2push1("D"));
   }

   public void dastore() {
      this.assertTypeOnStack(StackEntryType.DOUBLE, "dastore requires an int on top of the stack");
      this.assertTypeOnStack(2, StackEntryType.INT, "dastore requires an int in position 2 on the stack");
      this.assertTypeOnStack(3, StackEntryType.OBJECT, "dastore requires an array reference in position 3 on the stack");
      this.writeByte(82);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop4());
   }

   public void dcmpg() {
      this.assertTypeOnStack(StackEntryType.DOUBLE, "dcmpg requires double on stack");
      this.assertTypeOnStack(2, StackEntryType.DOUBLE, "dcmpg requires double on stack");
      this.writeByte(152);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop4push1("I"));
   }

   public void dcmpl() {
      this.assertTypeOnStack(StackEntryType.DOUBLE, "dcmpl requires double on stack");
      this.assertTypeOnStack(2, StackEntryType.DOUBLE, "dcmpl requires double in position 3 on stack");
      this.writeByte(151);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop4push1("I"));
   }

   public void dconst(double value) {
      if (value == 0.0) {
         this.writeByte(14);
      } else {
         if (value != 1.0) {
            this.ldc2(value);
            return;
         }

         this.writeByte(15);
      }

      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.push("D"));
   }

   public void ddiv() {
      this.assertTypeOnStack(StackEntryType.DOUBLE, "ddiv requires double on stack");
      this.assertTypeOnStack(2, StackEntryType.DOUBLE, "ddiv requires double in position 3 on stack");
      this.writeByte(111);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2());
   }

   public void dload(int no) {
      LocalVariableState locals = this.getLocalVars();
      if (locals.size() <= no) {
         throw new InvalidBytecodeException("Cannot load variable at " + no + ". Local Variables: " + locals.toString());
      } else {
         StackEntry entry = locals.get(no);
         if (entry.getType() != StackEntryType.DOUBLE) {
            throw new InvalidBytecodeException("Invalid local variable at location " + no + " Local Variables " + locals.toString());
         } else {
            if (no > 255) {
               this.writeByte(196);
               this.writeByte(24);
               this.writeShort(no);
               this.currentOffset += 4;
            } else if (no >= 0 && no < 4) {
               this.writeByte(38 + no);
               ++this.currentOffset;
            } else {
               this.writeByte(24);
               this.writeByte(no);
               this.currentOffset += 2;
            }

            this.advanceFrame(this.currentFrame.push(entry));
         }
      }
   }

   public void dmul() {
      this.assertTypeOnStack(StackEntryType.DOUBLE, "dmul requires double on stack");
      this.assertTypeOnStack(2, StackEntryType.DOUBLE, "dmul requires double in position 3 on stack");
      this.writeByte(107);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2());
   }

   public void dneg() {
      this.assertTypeOnStack(StackEntryType.DOUBLE, "dneg requires double on stack");
      this.writeByte(119);
      ++this.currentOffset;
      this.duplicateFrame();
   }

   public void drem() {
      this.assertTypeOnStack(StackEntryType.DOUBLE, "drem requires double on stack");
      this.assertTypeOnStack(2, StackEntryType.DOUBLE, "drem requires double in position 3 on stack");
      this.writeByte(115);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2());
   }

   public void dstore(int no) {
      this.assertTypeOnStack(StackEntryType.DOUBLE, "dastore requires double on stack");
      if (no > 255) {
         this.writeByte(196);
         this.writeByte(57);
         this.writeShort(no);
         this.currentOffset += 4;
      } else if (no >= 0 && no < 4) {
         this.writeByte(71 + no);
         ++this.currentOffset;
      } else {
         this.writeByte(57);
         this.writeByte(no);
         this.currentOffset += 2;
      }

      this.advanceFrame(this.currentFrame.store(no));
   }

   public void dsub() {
      this.assertTypeOnStack(StackEntryType.DOUBLE, "dsub requires double on stack");
      this.assertTypeOnStack(2, StackEntryType.DOUBLE, "dsub requires double in position 3 on stack");
      this.writeByte(103);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2());
   }

   public void dup() {
      this.assertNotWideOnStack("dup acnnot be used if double or long is on top of the stack");
      this.writeByte(89);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.dup());
   }

   public void dupX1() {
      this.assertNotWideOnStack("dup_x1 cannot be used if double or long is on top of the stack");
      this.assertNotWideOnStack(1, "dup_x1 cannot be used if double or long is in position 2 on the stack");
      this.writeByte(90);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.dupX1());
   }

   public void dupX2() {
      this.assertNotWideOnStack("dup_x2 acnnot be used if double or long is on top of the stack");
      this.writeByte(91);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.dupX2());
   }

   public void dup2() {
      this.writeByte(92);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.dup2());
   }

   public void dup2X1() {
      this.assertNotWideOnStack(2, "dup2_x1 cannot be used if double or long is in position 3 on the stack");
      this.writeByte(93);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.dup2X1());
   }

   public void dup2X2() {
      this.assertNotWideOnStack(3, "dup2_x2 cannot be used if double or long is in position 4 on the stack");
      this.writeByte(94);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.dup2X2());
   }

   public ExceptionHandler exceptionBlockStart(String exceptionType) {
      return new ExceptionHandler(this.currentOffset, this.constPool.addClassEntry(exceptionType), exceptionType, this.currentFrame);
   }

   public void exceptionBlockEnd(ExceptionHandler handler) {
      handler.setEnd(this.currentOffset);
   }

   public void exceptionHandlerStart(ExceptionHandler handler) {
      if (handler.getEnd() == 0) {
         throw new InvalidBytecodeException("handler end location must be initialised via exceptionHandlerEnd before calling exceptionHandlerAdd");
      } else {
         handler.setHandler(this.currentOffset);
         this.exceptionTable.add(handler);
         this.mergeStackFrames(new StackFrame(new StackState(handler.getExceptionType(), this.constPool), handler.getFrame().getLocalVariableState(), StackFrameType.FULL_FRAME));
      }
   }

   public void f2d() {
      this.assertTypeOnStack(StackEntryType.FLOAT, "f2s requires float on stack");
      this.writeByte(141);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.replace("D"));
   }

   public void f2i() {
      this.assertTypeOnStack(StackEntryType.FLOAT, "f2i requires float on stack");
      this.writeByte(139);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.replace("I"));
   }

   public void f2l() {
      this.assertTypeOnStack(StackEntryType.FLOAT, "f2l requires float on stack");
      this.writeByte(140);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.replace("J"));
   }

   public void fadd() {
      this.assertTypeOnStack(StackEntryType.FLOAT, "fadd requires float on stack");
      this.assertTypeOnStack(1, StackEntryType.FLOAT, "fadd requires float on stack");
      this.writeByte(98);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void faload() {
      this.assertTypeOnStack(StackEntryType.INT, "faload requires an int on top of the stack");
      this.assertTypeOnStack(1, StackEntryType.OBJECT, "faload requires an array in position 2 on the stack");
      this.writeByte(48);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2push1("F"));
   }

   public void fastore() {
      this.assertTypeOnStack(StackEntryType.FLOAT, "fastore requires an int on top of the stack");
      this.assertTypeOnStack(1, StackEntryType.INT, "fastore requires an int in position 2 on the stack");
      this.assertTypeOnStack(2, StackEntryType.OBJECT, "fastore requires an array reference in position 3 on the stack");
      this.writeByte(81);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop3());
   }

   public void fcmpg() {
      this.assertTypeOnStack(StackEntryType.FLOAT, "fcmpg requires float on stack");
      this.assertTypeOnStack(1, StackEntryType.FLOAT, "fcmpg requires float on stack");
      this.writeByte(150);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2push1("I"));
   }

   public void fcmpl() {
      this.assertTypeOnStack(StackEntryType.FLOAT, "fcmpl requires float on stack");
      this.assertTypeOnStack(1, StackEntryType.FLOAT, "fcmpl requires float in position 2 on stack");
      this.writeByte(149);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2push1("I"));
   }

   public void fconst(float value) {
      if (value == 0.0F) {
         this.writeByte(11);
      } else if (value == 1.0F) {
         this.writeByte(12);
      } else {
         if (value != 2.0F) {
            this.ldc(value);
            return;
         }

         this.writeByte(13);
      }

      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.push("F"));
   }

   public void fdiv() {
      this.assertTypeOnStack(StackEntryType.FLOAT, "fdiv requires float on stack");
      this.assertTypeOnStack(1, StackEntryType.FLOAT, "fdiv requires float in position 2 on stack");
      this.writeByte(110);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void fload(int no) {
      LocalVariableState locals = this.getLocalVars();
      if (locals.size() <= no) {
         throw new InvalidBytecodeException("Cannot load variable at " + no + ". Local Variables: " + locals.toString());
      } else {
         StackEntry entry = locals.get(no);
         if (entry.getType() != StackEntryType.FLOAT) {
            throw new InvalidBytecodeException("Invalid local variable at location " + no + " Local Variables " + locals.toString());
         } else {
            if (no > 255) {
               this.writeByte(196);
               this.writeByte(23);
               this.writeShort(no);
               this.currentOffset += 4;
            } else if (no >= 0 && no < 4) {
               this.writeByte(34 + no);
               ++this.currentOffset;
            } else {
               this.writeByte(23);
               this.writeByte(no);
               this.currentOffset += 2;
            }

            this.advanceFrame(this.currentFrame.push(entry));
         }
      }
   }

   public void fmul() {
      this.assertTypeOnStack(StackEntryType.FLOAT, "fmul requires float on stack");
      this.assertTypeOnStack(1, StackEntryType.FLOAT, "fmul requires float in position 2 on stack");
      this.writeByte(106);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void fneg() {
      this.assertTypeOnStack(StackEntryType.FLOAT, "fneg requires float on stack");
      this.writeByte(118);
      ++this.currentOffset;
      this.duplicateFrame();
   }

   public void frem() {
      this.assertTypeOnStack(StackEntryType.FLOAT, "frem requires float on stack");
      this.assertTypeOnStack(1, StackEntryType.FLOAT, "frem requires float in position 2 on stack");
      this.writeByte(114);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void fstore(int no) {
      this.assertTypeOnStack(StackEntryType.FLOAT, "fstore requires float on stack");
      if (no > 255) {
         this.writeByte(196);
         this.writeByte(56);
         this.writeShort(no);
         this.currentOffset += 4;
      } else if (no >= 0 && no < 4) {
         this.writeByte(67 + no);
         ++this.currentOffset;
      } else {
         this.writeByte(56);
         this.writeByte(no);
         this.currentOffset += 2;
      }

      this.advanceFrame(this.currentFrame.store(no));
   }

   public void fsub() {
      this.assertTypeOnStack(StackEntryType.FLOAT, "fsub requires float on stack");
      this.assertTypeOnStack(1, StackEntryType.FLOAT, "fsub requires float in position 2 on stack");
      this.writeByte(102);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void getfield(String className, String field, Class fieldType) {
      this.getfield(className, field, DescriptorUtils.makeDescriptor(fieldType));
   }

   public void getfield(String className, String field, String descriptor) {
      this.assertTypeOnStack(StackEntryType.OBJECT, "getfield requires object on stack");
      int index = this.constPool.addFieldEntry(className, field, descriptor);
      this.writeByte(180);
      this.writeShort(index);
      this.currentOffset += 3;
      this.advanceFrame(this.currentFrame.replace(descriptor));
   }

   public void getstatic(String className, String field, Class fieldType) {
      this.getstatic(className, field, DescriptorUtils.makeDescriptor(fieldType));
   }

   public void getstatic(String className, String field, String descriptor) {
      int index = this.constPool.addFieldEntry(className, field, descriptor);
      this.writeByte(178);
      this.writeShort(index);
      this.currentOffset += 3;
      this.advanceFrame(this.currentFrame.push(descriptor));
   }

   public void gotoInstruction(CodeLocation location) {
      this.writeByte(167);
      this.writeShort(location.getLocation() - this.currentOffset);
      this.mergeStackFrames(location.getStackFrame());
      this.currentOffset += 3;
      this.currentFrame = null;
   }

   public BranchEnd gotoInstruction() {
      this.writeByte(167);
      this.writeShort(0);
      this.currentOffset += 3;
      BranchEnd ret = new BranchEnd(this.currentOffset - 2, this.currentFrame, this.currentOffset - 3);
      this.currentFrame = null;
      return ret;
   }

   public void i2b() {
      this.assertTypeOnStack(StackEntryType.INT, "i2b requires int on stack");
      this.writeByte(145);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.replace("B"));
   }

   public void i2c() {
      this.assertTypeOnStack(StackEntryType.INT, "i2c requires int on stack");
      this.writeByte(146);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.replace("C"));
   }

   public void i2d() {
      this.assertTypeOnStack(StackEntryType.INT, "i2d requires int on stack");
      this.writeByte(135);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.replace("D"));
   }

   public void i2f() {
      this.assertTypeOnStack(StackEntryType.INT, "i2f requires int on stack");
      this.writeByte(134);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.replace("F"));
   }

   public void i2l() {
      this.assertTypeOnStack(StackEntryType.INT, "i2l requires int on stack");
      this.writeByte(133);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.replace("J"));
   }

   public void i2s() {
      this.assertTypeOnStack(StackEntryType.INT, "i2s requires int on stack");
      this.writeByte(147);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.replace("S"));
   }

   public void iadd() {
      this.assertTypeOnStack(StackEntryType.INT, "iadd requires int on stack");
      this.assertTypeOnStack(1, StackEntryType.INT, "iadd requires int on stack");
      this.writeByte(96);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void iaload() {
      this.assertTypeOnStack(StackEntryType.INT, "iaload requires an int on top of the stack");
      this.assertTypeOnStack(1, StackEntryType.OBJECT, "iaload requires an array in position 2 on the stack");
      this.writeByte(46);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2push1("I"));
   }

   public void iand() {
      this.assertTypeOnStack(StackEntryType.INT, "iand requires int on stack");
      this.assertTypeOnStack(1, StackEntryType.INT, "iand requires int on stack");
      this.writeByte(126);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void iastore() {
      this.assertTypeOnStack(StackEntryType.INT, "iastore requires an int on top of the stack");
      this.assertTypeOnStack(1, StackEntryType.INT, "iastore requires an int in position 2 on the stack");
      this.assertTypeOnStack(2, StackEntryType.OBJECT, "iastore requires an array reference in position 3 on the stack");
      this.writeByte(79);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop3());
   }

   public void iconst(int value) {
      if (value >= -1 && value <= 5) {
         this.writeByte(3 + value);
         ++this.currentOffset;
         this.advanceFrame(this.currentFrame.push("I"));
      } else {
         if (value >= -128 && value <= 127) {
            this.writeByte(16);
            this.writeByte(value);
            this.currentOffset += 2;
            this.advanceFrame(this.currentFrame.push("I"));
         } else {
            this.ldc(value);
         }

      }
   }

   public void idiv() {
      this.assertTypeOnStack(StackEntryType.INT, "idiv requires int on stack");
      this.assertTypeOnStack(1, StackEntryType.INT, "idiv requires int in position 2 on stack");
      this.writeByte(108);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void ifAcmpeq(CodeLocation location) {
      this.assertTypeOnStack(StackEntryType.OBJECT, "ifAcmpeq requires reference type on stack");
      this.assertTypeOnStack(1, StackEntryType.OBJECT, "ifAcmpeq requires reference type in position 2 on stack");
      this.writeByte(165);
      this.writeShort(location.getLocation() - this.currentOffset);
      this.mergeStackFrames(location.getStackFrame());
      this.currentOffset += 3;
      this.advanceFrame(this.currentFrame.pop2());
   }

   public BranchEnd ifAcmpeq() {
      this.assertTypeOnStack(StackEntryType.OBJECT, "ifAcmpeq requires reference type on stack");
      this.assertTypeOnStack(1, StackEntryType.OBJECT, "ifAcmpeq requires reference type int position 2 on stack");
      this.writeByte(165);
      this.writeShort(0);
      this.currentOffset += 3;
      this.advanceFrame(this.currentFrame.pop2());
      return new BranchEnd(this.currentOffset - 2, this.currentFrame, this.currentOffset - 3);
   }

   public void ifAcmpne(CodeLocation location) {
      this.assertTypeOnStack(StackEntryType.OBJECT, "ifAcmpne requires reference type on stack");
      this.assertTypeOnStack(1, StackEntryType.OBJECT, "ifAcmpne requires reference type in position 2 on stack");
      this.writeByte(166);
      this.writeShort(location.getLocation() - this.currentOffset);
      this.mergeStackFrames(location.getStackFrame());
      this.currentOffset += 3;
      this.advanceFrame(this.currentFrame.pop2());
   }

   public BranchEnd ifAcmpne() {
      this.assertTypeOnStack(StackEntryType.OBJECT, "ifAcmpne requires reference type on stack");
      this.assertTypeOnStack(1, StackEntryType.OBJECT, "ifAcmpne requires reference type int position 2 on stack");
      this.writeByte(166);
      this.writeShort(0);
      this.currentOffset += 3;
      this.advanceFrame(this.currentFrame.pop2());
      BranchEnd ret = new BranchEnd(this.currentOffset - 2, this.currentFrame, this.currentOffset - 3);
      return ret;
   }

   public void ifIcmpeq(CodeLocation location) {
      this.addIfIcmp(location, 159, "ifIcmpeq");
   }

   public BranchEnd ifIcmpeq() {
      return this.addIfIcmp(159, "ifIcmpeq");
   }

   public void ifIcmpne(CodeLocation location) {
      this.addIfIcmp(location, 160, "ifIcmpne");
   }

   public BranchEnd ifIcmpne() {
      return this.addIfIcmp(160, "ifIcmpne");
   }

   public void ifIcmplt(CodeLocation location) {
      this.addIfIcmp(location, 161, "ifIcmplt");
   }

   public BranchEnd ifIcmplt() {
      return this.addIfIcmp(161, "ifIcmplt");
   }

   public void ifIcmple(CodeLocation location) {
      this.addIfIcmp(location, 164, "ifIcmple");
   }

   public BranchEnd ifIcmple() {
      return this.addIfIcmp(164, "ifIcmple");
   }

   public void ifIcmpgt(CodeLocation location) {
      this.addIfIcmp(location, 163, "ifIcmpgt");
   }

   public BranchEnd ifIcmpgt() {
      return this.addIfIcmp(163, "ifIcmpgt");
   }

   public void ifIcmpge(CodeLocation location) {
      this.addIfIcmp(location, 162, "ifIcmpge");
   }

   public BranchEnd ifIcmpge() {
      return this.addIfIcmp(162, "ifIcmpge");
   }

   public void ifEq(CodeLocation location) {
      this.addIf(location, 153, "ifeq");
   }

   public BranchEnd ifeq() {
      return this.addIf(153, "ifeq");
   }

   public void ifne(CodeLocation location) {
      this.addIf(location, 154, "ifne");
   }

   public BranchEnd ifne() {
      return this.addIf(154, "ifne");
   }

   public void iflt(CodeLocation location) {
      this.addIf(location, 155, "iflt");
   }

   public BranchEnd iflt() {
      return this.addIf(155, "iflt");
   }

   public void ifle(CodeLocation location) {
      this.addIf(location, 158, "ifle");
   }

   public BranchEnd ifle() {
      return this.addIf(158, "ifle");
   }

   public void ifgt(CodeLocation location) {
      this.addIf(location, 157, "ifgt");
   }

   public BranchEnd ifgt() {
      return this.addIf(157, "ifgt");
   }

   public void ifge(CodeLocation location) {
      this.addIf(location, 156, "ifge");
   }

   public BranchEnd ifge() {
      return this.addIf(156, "ifge");
   }

   public void ifnotnull(CodeLocation location) {
      this.addNullComparison(location, 199, "ifnotnull");
   }

   public BranchEnd ifnotnull() {
      return this.addNullComparison(199, "ifnotnull");
   }

   public void ifnull(CodeLocation location) {
      this.addNullComparison(location, 198, "ifnull");
   }

   public BranchEnd ifnull() {
      return this.addNullComparison(198, "ifnull");
   }

   public void iinc(int local, int amount) {
      if (this.getLocalVars().get(local).getType() != StackEntryType.INT) {
         throw new InvalidBytecodeException("iinc requires int at local variable position " + local + " " + this.getLocalVars().toString());
      } else {
         if (local <= 255 && amount <= 255) {
            this.writeByte(132);
            this.writeByte(local);
            this.writeByte(amount);
            this.currentOffset += 3;
         } else {
            this.writeByte(196);
            this.writeByte(132);
            this.writeShort(local);
            this.writeShort(amount);
            this.currentOffset += 6;
         }

         this.duplicateFrame();
      }
   }

   public void iload(int no) {
      LocalVariableState locals = this.getLocalVars();
      if (locals.size() <= no) {
         throw new InvalidBytecodeException("Cannot load variable at " + no + ". Local Variables: " + locals.toString());
      } else {
         StackEntry entry = locals.get(no);
         if (entry.getType() != StackEntryType.INT) {
            throw new InvalidBytecodeException("Invalid local variable at location " + no + " Local Variables " + locals.toString());
         } else {
            if (no > 255) {
               this.writeByte(196);
               this.writeByte(21);
               this.writeShort(no);
               this.currentOffset += 4;
            } else if (no >= 0 && no < 4) {
               this.writeByte(26 + no);
               ++this.currentOffset;
            } else {
               this.writeByte(21);
               this.writeByte(no);
               this.currentOffset += 2;
            }

            this.advanceFrame(this.currentFrame.push(entry));
         }
      }
   }

   public void imul() {
      this.assertTypeOnStack(StackEntryType.INT, "imul requires int on stack");
      this.assertTypeOnStack(1, StackEntryType.INT, "imul requires int in position 2 on stack");
      this.writeByte(104);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void ineg() {
      this.assertTypeOnStack(StackEntryType.INT, "ineg requires int on stack");
      this.writeByte(116);
      ++this.currentOffset;
      this.duplicateFrame();
   }

   public void instanceofInstruction(String className) {
      this.assertTypeOnStack(StackEntryType.OBJECT, "instanceof requires an object reference on the stack");
      int classIndex = this.constPool.addClassEntry(className);
      this.writeByte(193);
      this.writeShort(classIndex);
      this.currentOffset += 3;
      this.advanceFrame(this.currentFrame.replace("I"));
   }

   public void invokespecial(String className, String methodName, String descriptor) {
      String[] params = DescriptorUtils.parameterDescriptors(descriptor);
      String returnType = DescriptorUtils.returnType(descriptor);
      this.invokespecial(className, methodName, descriptor, returnType, params);
   }

   public void invokespecial(String className, String methodName, String returnType, String[] parameterTypes) {
      String descriptor = DescriptorUtils.methodDescriptor(parameterTypes, returnType);
      this.invokespecial(className, methodName, descriptor, returnType, parameterTypes);
   }

   public void invokespecial(Constructor constructor) {
      this.invokespecial(constructor.getDeclaringClass().getName(), "<init>", DescriptorUtils.makeDescriptor(constructor), "V", DescriptorUtils.parameterDescriptors(constructor.getParameterTypes()));
   }

   public void invokespecial(Method method) {
      if (Modifier.isStatic(method.getModifiers())) {
         throw new InvalidBytecodeException("Cannot use invokespacial to invoke a static method");
      } else {
         this.invokespecial(method.getDeclaringClass().getName(), method.getName(), DescriptorUtils.methodDescriptor(method), DescriptorUtils.makeDescriptor(method.getReturnType()), DescriptorUtils.parameterDescriptors(method.getParameterTypes()));
      }
   }

   private void invokespecial(String className, String methodName, String descriptor, String returnType, String[] parameterTypes) {
      int method = this.constPool.addMethodEntry(className, methodName, descriptor);
      this.writeByte(183);
      this.writeShort(method);
      this.currentOffset += 3;
      int pop = 1 + parameterTypes.length;
      String[] var8 = parameterTypes;
      int var9 = parameterTypes.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         String argument = var8[var10];
         if (argument.equals("D") || argument.equals("J")) {
            ++pop;
         }
      }

      if (methodName.equals("<init>")) {
         this.advanceFrame(this.currentFrame.constructorCall(pop - 1));
      } else if (returnType.equals("V")) {
         this.advanceFrame(this.currentFrame.pop(pop));
      } else {
         this.advanceFrame(this.currentFrame.pop(pop).push(returnType));
      }

   }

   public void invokestatic(String className, String methodName, String descriptor) {
      String[] params = DescriptorUtils.parameterDescriptors(descriptor);
      String returnType = DescriptorUtils.returnType(descriptor);
      this.invokestatic(className, methodName, descriptor, returnType, params);
   }

   public void invokestatic(String className, String methodName, String returnType, String[] parameterTypes) {
      String descriptor = DescriptorUtils.methodDescriptor(parameterTypes, returnType);
      this.invokestatic(className, methodName, descriptor, returnType, parameterTypes);
   }

   public void invokestatic(Method method) {
      if (!Modifier.isStatic(method.getModifiers())) {
         throw new InvalidBytecodeException("Cannot use invokestatic to invoke a non static method");
      } else {
         this.invokestatic(method.getDeclaringClass().getName(), method.getName(), DescriptorUtils.methodDescriptor(method), DescriptorUtils.makeDescriptor(method.getReturnType()), DescriptorUtils.parameterDescriptors(method.getParameterTypes()));
      }
   }

   private void invokestatic(String className, String methodName, String descriptor, String returnType, String[] parameterTypes) {
      int method = this.constPool.addMethodEntry(className, methodName, descriptor);
      this.writeByte(184);
      this.writeShort(method);
      this.currentOffset += 3;
      int pop = parameterTypes.length;
      String[] var8 = parameterTypes;
      int var9 = parameterTypes.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         String argument = var8[var10];
         if (argument.equals("D") || argument.equals("J")) {
            ++pop;
         }
      }

      if (returnType.equals("V")) {
         this.advanceFrame(this.currentFrame.pop(pop));
      } else {
         this.advanceFrame(this.currentFrame.pop(pop).push(returnType));
      }

   }

   public void invokevirtual(String className, String methodName, String descriptor) {
      String[] params = DescriptorUtils.parameterDescriptors(descriptor);
      String returnType = DescriptorUtils.returnType(descriptor);
      this.invokevirtual(className, methodName, descriptor, returnType, params);
   }

   public void invokevirtual(String className, String methodName, String returnType, String[] parameterTypes) {
      String descriptor = DescriptorUtils.methodDescriptor(parameterTypes, returnType);
      this.invokevirtual(className, methodName, descriptor, returnType, parameterTypes);
   }

   public void invokevirtual(Method method) {
      if (Modifier.isStatic(method.getModifiers())) {
         throw new InvalidBytecodeException("Cannot use invokevirtual to invoke a static method");
      } else if (Modifier.isPrivate(method.getModifiers())) {
         throw new InvalidBytecodeException("Cannot use invokevirtual to invoke a private method");
      } else if (method.getDeclaringClass().isInterface()) {
         throw new InvalidBytecodeException("Cannot use invokevirtual to invoke an interface method");
      } else {
         this.invokevirtual(method.getDeclaringClass().getName(), method.getName(), DescriptorUtils.methodDescriptor(method), DescriptorUtils.makeDescriptor(method.getReturnType()), DescriptorUtils.parameterDescriptors(method.getParameterTypes()));
      }
   }

   private void invokevirtual(String className, String methodName, String descriptor, String returnType, String[] parameterTypes) {
      int method = this.constPool.addMethodEntry(className, methodName, descriptor);
      this.writeByte(182);
      this.writeShort(method);
      this.currentOffset += 3;
      int pop = 1 + parameterTypes.length;
      String[] var8 = parameterTypes;
      int var9 = parameterTypes.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         String argument = var8[var10];
         if (argument.equals("D") || argument.equals("J")) {
            ++pop;
         }
      }

      if (returnType.equals("V")) {
         this.advanceFrame(this.currentFrame.pop(pop));
      } else {
         this.advanceFrame(this.currentFrame.pop(pop).push(returnType));
      }

   }

   public void invokeinterface(String className, String methodName, String descriptor) {
      String[] params = DescriptorUtils.parameterDescriptors(descriptor);
      String returnType = DescriptorUtils.returnType(descriptor);
      this.invokeinterface(className, methodName, descriptor, returnType, params);
   }

   public void invokeinterface(String className, String methodName, String returnType, String[] parameterTypes) {
      String descriptor = DescriptorUtils.methodDescriptor(parameterTypes, returnType);
      this.invokeinterface(className, methodName, descriptor, returnType, parameterTypes);
   }

   public void invokeinterface(Method method) {
      if (Modifier.isStatic(method.getModifiers())) {
         throw new InvalidBytecodeException("Cannot use invokeinterface to invoke a static method");
      } else if (Modifier.isPrivate(method.getModifiers())) {
         throw new InvalidBytecodeException("Cannot use invokeinterface to invoke a private method");
      } else if (!method.getDeclaringClass().isInterface()) {
         throw new InvalidBytecodeException("Cannot use invokeinterface to invoke a non interface method");
      } else {
         this.invokeinterface(method.getDeclaringClass().getName(), method.getName(), DescriptorUtils.methodDescriptor(method), DescriptorUtils.makeDescriptor(method.getReturnType()), DescriptorUtils.parameterDescriptors(method.getParameterTypes()));
      }
   }

   private void invokeinterface(String className, String methodName, String descriptor, String returnType, String[] parameterTypes) {
      int pop = 1 + parameterTypes.length;
      String[] var7 = parameterTypes;
      int var8 = parameterTypes.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         String argument = var7[var9];
         if (argument.equals("D") || argument.equals("J")) {
            ++pop;
         }
      }

      int method = this.constPool.addInterfaceMethodEntry(className, methodName, descriptor);
      this.writeByte(185);
      this.writeShort(method);
      this.writeByte(pop);
      this.writeByte(0);
      this.currentOffset += 5;
      if (returnType.equals("V")) {
         this.advanceFrame(this.currentFrame.pop(pop));
      } else {
         this.advanceFrame(this.currentFrame.pop(pop).push(returnType));
      }

   }

   public void ior() {
      this.assertTypeOnStack(StackEntryType.INT, "ior requires int on stack");
      this.assertTypeOnStack(1, StackEntryType.INT, "ior requires int on stack");
      this.writeByte(128);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void irem() {
      this.assertTypeOnStack(StackEntryType.INT, "irem requires int on stack");
      this.assertTypeOnStack(1, StackEntryType.INT, "irem requires int on stack");
      this.writeByte(112);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void ishl() {
      this.assertTypeOnStack(StackEntryType.INT, "ishl requires int on stack");
      this.assertTypeOnStack(1, StackEntryType.INT, "ishl requires int on stack");
      this.writeByte(120);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void ishr() {
      this.assertTypeOnStack(StackEntryType.INT, "ishr requires int on stack");
      this.assertTypeOnStack(1, StackEntryType.INT, "ishr requires int on stack");
      this.writeByte(122);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void istore(int no) {
      this.assertTypeOnStack(StackEntryType.INT, "istore requires int on stack");
      if (no > 255) {
         this.writeByte(196);
         this.writeByte(54);
         this.writeShort(no);
         this.currentOffset += 4;
      } else if (no >= 0 && no < 4) {
         this.writeByte(59 + no);
         ++this.currentOffset;
      } else {
         this.writeByte(54);
         this.writeByte(no);
         this.currentOffset += 2;
      }

      this.advanceFrame(this.currentFrame.store(no));
   }

   public void isub() {
      this.assertTypeOnStack(StackEntryType.INT, "isub requires int on stack");
      this.assertTypeOnStack(1, StackEntryType.INT, "isub requires int on stack");
      this.writeByte(100);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void iushr() {
      this.assertTypeOnStack(StackEntryType.INT, "iushr requires int on stack");
      this.assertTypeOnStack(1, StackEntryType.INT, "iushr requires int on stack");
      this.writeByte(124);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void ixor() {
      this.assertTypeOnStack(StackEntryType.INT, "ixor requires int on stack");
      this.assertTypeOnStack(1, StackEntryType.INT, "ixor requires int on stack");
      this.writeByte(130);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void l2d() {
      this.assertTypeOnStack(StackEntryType.LONG, "l2d requires long on stack");
      this.writeByte(138);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2push1("D"));
   }

   public void l2f() {
      this.assertTypeOnStack(StackEntryType.LONG, "l2f requires long on stack");
      this.writeByte(137);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2push1("F"));
   }

   public void l2i() {
      this.assertTypeOnStack(StackEntryType.LONG, "l2i requires long on stack");
      this.writeByte(136);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2push1("I"));
   }

   public void ladd() {
      this.assertTypeOnStack(StackEntryType.LONG, "ladd requires long on stack");
      this.assertTypeOnStack(2, StackEntryType.LONG, "ladd requires long on stack");
      this.writeByte(97);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2());
   }

   public void laload() {
      this.assertTypeOnStack(StackEntryType.INT, "laload requires an int on top of the stack");
      this.assertTypeOnStack(1, StackEntryType.OBJECT, "laload requires an array in position 2 on the stack");
      this.writeByte(47);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2push1("J"));
   }

   public void land() {
      this.assertTypeOnStack(StackEntryType.LONG, "land requires long on stack");
      this.assertTypeOnStack(2, StackEntryType.LONG, "land requires long on stack");
      this.writeByte(127);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2());
   }

   public void lastore() {
      this.assertTypeOnStack(StackEntryType.LONG, "lastore requires an long on top of the stack");
      this.assertTypeOnStack(2, StackEntryType.INT, "lastore requires an int in position 2 on the stack");
      this.assertTypeOnStack(3, StackEntryType.OBJECT, "lastore requires an array reference in position 3 on the stack");
      this.writeByte(80);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop4());
   }

   public void lcmp() {
      this.assertTypeOnStack(StackEntryType.LONG, "lcmp requires long on stack");
      this.assertTypeOnStack(2, StackEntryType.LONG, "lcmp requires long on stack");
      this.writeByte(148);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop4push1("I"));
   }

   public void lconst(long value) {
      if (value == 0L) {
         this.writeByte(9);
      } else {
         if (value != 1L) {
            this.ldc2(value);
            return;
         }

         this.writeByte(10);
      }

      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.push("J"));
   }

   public void ldc(int value) {
      if (value > -2 && value < 6) {
         this.iconst(value);
      } else {
         int index = this.constPool.addIntegerEntry(value);
         this.ldcInternal(index);
         this.advanceFrame(this.currentFrame.push("I"));
      }
   }

   public void ldc(float value) {
      int index = this.constPool.addFloatEntry(value);
      this.ldcInternal(index);
      this.advanceFrame(this.currentFrame.push("F"));
   }

   public void ldc(String value) {
      int index = this.constPool.addStringEntry(value);
      this.ldcInternal(index);
      this.advanceFrame(this.currentFrame.push("Ljava/lang/String;"));
   }

   private void ldcInternal(int index) {
      if (index > 255) {
         this.writeByte(19);
         this.writeShort(index);
         this.currentOffset += 3;
      } else {
         this.writeByte(18);
         this.writeByte(index);
         this.currentOffset += 2;
      }

   }

   public void ldc2(double value) {
      int index = this.constPool.addDoubleEntry(value);
      this.writeByte(20);
      this.writeShort(index);
      this.currentOffset += 3;
      this.advanceFrame(this.currentFrame.push("D"));
   }

   public void ldc2(long value) {
      int index = this.constPool.addLongEntry(value);
      this.writeByte(20);
      this.writeShort(index);
      this.currentOffset += 3;
      this.advanceFrame(this.currentFrame.push("J"));
   }

   public void ldiv() {
      this.assertTypeOnStack(StackEntryType.LONG, "ldiv requires long on stack");
      this.assertTypeOnStack(2, StackEntryType.LONG, "ldiv requires long in position 3 on stack");
      this.writeByte(109);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2());
   }

   public void lload(int no) {
      LocalVariableState locals = this.getLocalVars();
      if (locals.size() <= no) {
         throw new InvalidBytecodeException("Cannot load variable at " + no + ". Local Variables: " + locals.toString());
      } else {
         StackEntry entry = locals.get(no);
         if (entry.getType() != StackEntryType.LONG) {
            throw new InvalidBytecodeException("Invalid local variable at location " + no + " Local Variables " + locals.toString());
         } else {
            if (no > 255) {
               this.writeByte(196);
               this.writeByte(22);
               this.writeShort(no);
               this.currentOffset += 4;
            } else if (no >= 0 && no < 4) {
               this.writeByte(30 + no);
               ++this.currentOffset;
            } else {
               this.writeByte(22);
               this.writeByte(no);
               this.currentOffset += 2;
            }

            this.advanceFrame(this.currentFrame.push(entry));
         }
      }
   }

   public void lmul() {
      this.assertTypeOnStack(StackEntryType.LONG, "lmul requires long on stack");
      this.assertTypeOnStack(2, StackEntryType.LONG, "lmul requires long in position 3 on stack");
      this.writeByte(105);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2());
   }

   public void lneg() {
      this.assertTypeOnStack(StackEntryType.LONG, "lneg requires long on stack");
      this.writeByte(117);
      ++this.currentOffset;
      this.duplicateFrame();
   }

   public void load(Class type, int no) {
      this.load(DescriptorUtils.makeDescriptor(type), no);
   }

   public void load(String descriptor, int no) {
      if (descriptor.length() != 1) {
         this.aload(no);
      } else {
         char type = descriptor.charAt(0);
         switch (type) {
            case 'B':
            case 'C':
            case 'I':
            case 'S':
            case 'Z':
               this.iload(no);
               break;
            case 'D':
               this.dload(no);
               break;
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            default:
               throw new InvalidBytecodeException("Could not load primitive type: " + type);
            case 'F':
               this.fload(no);
               break;
            case 'J':
               this.lload(no);
         }
      }

   }

   public void loadClass(String className) {
      int index = this.constPool.addClassEntry(className);
      this.ldcInternal(index);
      this.advanceFrame(this.currentFrame.push("Ljava/lang/Class;"));
   }

   public void loadType(String descriptor) {
      if (descriptor.length() != 1) {
         if (descriptor.startsWith("L") && descriptor.endsWith(";")) {
            descriptor = descriptor.substring(1, descriptor.length() - 1);
         }

         this.loadClass(descriptor);
      } else {
         char type = descriptor.charAt(0);
         switch (type) {
            case 'B':
               this.getstatic(Byte.class.getName(), "TYPE", "Ljava/lang/Class;");
               break;
            case 'C':
               this.getstatic(Character.class.getName(), "TYPE", "Ljava/lang/Class;");
               break;
            case 'D':
               this.getstatic(Double.class.getName(), "TYPE", "Ljava/lang/Class;");
               break;
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'U':
            case 'W':
            case 'X':
            case 'Y':
            default:
               throw new InvalidBytecodeException("Unkown primitive type: " + type);
            case 'F':
               this.getstatic(Float.class.getName(), "TYPE", "Ljava/lang/Class;");
               break;
            case 'I':
               this.getstatic(Integer.class.getName(), "TYPE", "Ljava/lang/Class;");
               break;
            case 'J':
               this.getstatic(Long.class.getName(), "TYPE", "Ljava/lang/Class;");
               break;
            case 'S':
               this.getstatic(Short.class.getName(), "TYPE", "Ljava/lang/Class;");
               break;
            case 'V':
               this.getstatic(Void.class.getName(), "TYPE", "Ljava/lang/Class;");
               break;
            case 'Z':
               this.getstatic(Boolean.class.getName(), "TYPE", "Ljava/lang/Class;");
         }
      }

   }

   public void lookupswitch(LookupSwitchBuilder lookupSwitchBuilder) {
      this.assertTypeOnStack(StackEntryType.INT, "lookupswitch requires an int on the stack");
      this.writeByte(171);

      int startOffset;
      for(startOffset = this.currentOffset++; this.currentOffset % 4 != 0; ++this.currentOffset) {
         this.writeByte(0);
      }

      StackFrame frame = this.currentFrame.pop();
      List values = new ArrayList(lookupSwitchBuilder.getValues());
      if (lookupSwitchBuilder.getDefaultLocation() != null) {
         this.writeInt(lookupSwitchBuilder.getDefaultLocation().getLocation() - this.currentOffset);
      } else {
         this.writeInt(0);
         BranchEnd ret = new BranchEnd(this.currentOffset, frame, true, startOffset);
         lookupSwitchBuilder.getDefaultBranchEnd().set(ret);
      }

      this.writeInt(values.size());
      this.currentOffset += 8;
      Collections.sort(values);
      Iterator var8 = values.iterator();

      while(var8.hasNext()) {
         LookupSwitchBuilder.ValuePair value = (LookupSwitchBuilder.ValuePair)var8.next();
         this.writeInt(value.getValue());
         this.currentOffset += 4;
         if (value.getLocation() != null) {
            this.writeInt(value.getLocation().getLocation());
            this.currentOffset += 4;
         } else {
            this.writeInt(0);
            BranchEnd ret = new BranchEnd(this.currentOffset, frame, true, startOffset);
            value.getBranchEnd().set(ret);
            this.currentOffset += 4;
         }
      }

      this.currentFrame = null;
   }

   public void lor() {
      this.assertTypeOnStack(StackEntryType.LONG, "lor requires long on stack");
      this.assertTypeOnStack(2, StackEntryType.LONG, "lor requires long in position 3 on stack");
      this.writeByte(129);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2());
   }

   public void lrem() {
      this.assertTypeOnStack(StackEntryType.LONG, "lrem requires long on stack");
      this.assertTypeOnStack(2, StackEntryType.LONG, "lrem requires long in position 3 on stack");
      this.writeByte(113);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2());
   }

   public void lshl() {
      this.assertTypeOnStack(StackEntryType.INT, "lshl requires int on stack");
      this.assertTypeOnStack(1, StackEntryType.LONG, "lshl requires long in position 2 on stack");
      this.writeByte(121);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void lshr() {
      this.assertTypeOnStack(StackEntryType.INT, "lshr requires int on stack");
      this.assertTypeOnStack(1, StackEntryType.LONG, "lshr requires long in position 2 on stack");
      this.writeByte(123);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void lstore(int no) {
      this.assertTypeOnStack(StackEntryType.LONG, "lstore requires long on stack");
      if (no > 255) {
         this.writeByte(196);
         this.writeByte(55);
         this.writeShort(no);
         this.currentOffset += 4;
      } else if (no >= 0 && no < 4) {
         this.writeByte(63 + no);
         ++this.currentOffset;
      } else {
         this.writeByte(55);
         this.writeByte(no);
         this.currentOffset += 2;
      }

      this.advanceFrame(this.currentFrame.store(no));
   }

   public void lsub() {
      this.assertTypeOnStack(StackEntryType.LONG, "lsub requires long on stack");
      this.assertTypeOnStack(2, StackEntryType.LONG, "lsub requires long in position 3 on stack");
      this.writeByte(101);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2());
   }

   public void lushr() {
      this.assertTypeOnStack(StackEntryType.INT, "lushr requires int on stack");
      this.assertTypeOnStack(1, StackEntryType.LONG, "lushr requires long in position 2 on stack");
      this.writeByte(125);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void lxor() {
      this.assertTypeOnStack(StackEntryType.LONG, "lxor requires long on stack");
      this.assertTypeOnStack(2, StackEntryType.LONG, "lxor requires long in position 3 on stack");
      this.writeByte(131);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2());
   }

   public CodeLocation mark() {
      return new CodeLocation(this.currentOffset, this.currentFrame);
   }

   public void monitorenter() {
      this.assertTypeOnStack(StackEntryType.OBJECT, "monitorenter requires object reference on stack");
      this.writeByte(194);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void monitorexit() {
      this.assertTypeOnStack(StackEntryType.OBJECT, "monitorexit requires object reference on stack");
      this.writeByte(195);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void multianewarray(String arrayType, int dimensions) {
      StringBuilder newType = new StringBuilder();

      int classIndex;
      for(classIndex = 0; classIndex < dimensions; ++classIndex) {
         this.assertTypeOnStack(classIndex, StackEntryType.INT, "multianewarray requires int on stack in position " + classIndex);
         newType.append('[');
      }

      if (!arrayType.startsWith("[")) {
         newType.append('L');
         newType.append(arrayType.replace('.', '/'));
         newType.append(";");
      } else {
         newType.append(arrayType);
      }

      classIndex = this.constPool.addClassEntry(newType.toString());
      this.writeByte(197);
      this.writeShort(classIndex);
      this.writeByte(dimensions);
      this.currentOffset += 4;
      this.advanceFrame(this.currentFrame.pop(dimensions).push(newType.toString()));
   }

   public void newInstruction(String classname) {
      int classIndex = this.constPool.addClassEntry(classname);
      this.writeByte(187);
      this.writeShort(classIndex);
      StackEntry entry = new StackEntry(StackEntryType.UNITITIALIZED_OBJECT, DescriptorUtils.makeDescriptor(classname), this.currentOffset);
      this.currentOffset += 3;
      this.advanceFrame(this.currentFrame.push(entry));
   }

   public void newInstruction(Class clazz) {
      this.newInstruction(clazz.getName());
   }

   public void newarray(Class arrayType) {
      this.assertTypeOnStack(StackEntryType.INT, "newarray requires int on stack");
      byte type;
      String desc;
      if (arrayType == Boolean.TYPE) {
         type = 4;
         desc = "[Z";
      } else if (arrayType == Character.TYPE) {
         type = 5;
         desc = "[C";
      } else if (arrayType == Float.TYPE) {
         type = 6;
         desc = "[F";
      } else if (arrayType == Double.TYPE) {
         type = 7;
         desc = "[D";
      } else if (arrayType == Byte.TYPE) {
         type = 8;
         desc = "[B";
      } else if (arrayType == Short.TYPE) {
         type = 9;
         desc = "[S";
      } else if (arrayType == Integer.TYPE) {
         type = 10;
         desc = "[I";
      } else {
         if (arrayType != Long.TYPE) {
            throw new InvalidBytecodeException("Class " + arrayType + " is not a primitive type");
         }

         type = 11;
         desc = "[J";
      }

      this.writeByte(188);
      this.writeByte(type);
      this.currentOffset += 2;
      this.advanceFrame(this.currentFrame.replace(desc));
   }

   public void nop() {
      this.writeByte(0);
      ++this.currentOffset;
      this.duplicateFrame();
   }

   public void pop() {
      this.writeByte(87);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop());
   }

   public void pop2() {
      this.writeByte(88);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2());
   }

   public void putfield(String className, String field, Class fieldType) {
      this.putfield(className, field, DescriptorUtils.makeDescriptor(fieldType));
   }

   public void putfield(String className, String field, String descriptor) {
      if (!this.getStack().isOnTop(descriptor)) {
         throw new InvalidBytecodeException("Attempting to put wrong type into  field. Field:" + className + "." + field + " (" + descriptor + "). Stack State: " + this.getStack().toString());
      } else {
         if (this.getStack().top_1().getType() != StackEntryType.UNINITIALIZED_THIS) {
            this.assertTypeOnStack(1, StackEntryType.OBJECT, "expected object in position 2 on stack");
         }

         int index = this.constPool.addFieldEntry(className, field, descriptor);
         this.writeByte(181);
         this.writeShort(index);
         this.currentOffset += 3;
         this.advanceFrame(this.currentFrame.pop2());
      }
   }

   public void putstatic(String className, String field, Class fieldType) {
      this.putstatic(className, field, DescriptorUtils.makeDescriptor(fieldType));
   }

   public void putstatic(String className, String field, String descriptor) {
      if (!this.getStack().isOnTop(descriptor)) {
         throw new InvalidBytecodeException("Attempting to put wrong type into static field. Field:" + className + "." + field + " (" + descriptor + "). Stack State: " + this.getStack().toString());
      } else {
         int index = this.constPool.addFieldEntry(className, field, descriptor);
         this.writeByte(179);
         this.writeShort(index);
         this.currentOffset += 3;
         this.advanceFrame(this.currentFrame.pop());
      }
   }

   public void returnInstruction() {
      String returnType = this.method.getReturnType();
      if (!returnType.equals("V") && !this.getStack().isOnTop(returnType)) {
         throw new InvalidBytecodeException(returnType + " is not on top of stack. " + this.getStack().toString());
      } else {
         ++this.currentOffset;
         if (returnType.length() > 1) {
            this.writeByte(176);
         } else {
            char ret = this.method.getReturnType().charAt(0);
            switch (ret) {
               case 'B':
               case 'C':
               case 'I':
               case 'S':
               case 'Z':
                  this.writeByte(172);
                  break;
               case 'D':
                  this.writeByte(175);
               case 'E':
               case 'G':
               case 'H':
               case 'K':
               case 'L':
               case 'M':
               case 'N':
               case 'O':
               case 'P':
               case 'Q':
               case 'R':
               case 'T':
               case 'U':
               case 'W':
               case 'X':
               case 'Y':
               default:
                  break;
               case 'F':
                  this.writeByte(174);
                  break;
               case 'J':
                  this.writeByte(173);
                  break;
               case 'V':
                  this.writeByte(177);
            }
         }

         this.currentFrame = null;
      }
   }

   public void saload() {
      this.assertTypeOnStack(StackEntryType.INT, "saload requires an int on top of the stack");
      this.assertTypeOnStack(1, StackEntryType.OBJECT, "saload requires an array in position 2 on the stack");
      this.writeByte(53);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop2push1("I"));
   }

   public void sastore() {
      this.assertTypeOnStack(StackEntryType.INT, "sastore requires an int on top of the stack");
      this.assertTypeOnStack(1, StackEntryType.INT, "sastore requires an int in position 2 on the stack");
      this.assertTypeOnStack(2, StackEntryType.OBJECT, "sastore requires an array reference in position 3 on the stack");
      this.writeByte(86);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.pop3());
   }

   public void sipush(short value) {
      this.writeByte(17);
      this.writeShort(value);
      this.currentOffset += 3;
      this.advanceFrame(this.currentFrame.push("S"));
   }

   public void swap() {
      this.assertNotWideOnStack("swap cannot be used when wide type is on top of stack");
      this.assertNotWideOnStack(1, "swap cannot be used when wide type is on position 1 of the stack");
      this.writeByte(95);
      ++this.currentOffset;
      this.advanceFrame(this.currentFrame.swap());
   }

   public void tableswitch(TableSwitchBuilder builder) {
      this.assertTypeOnStack(StackEntryType.INT, "lookupswitch requires an int on the stack");
      this.writeByte(170);

      int startOffset;
      for(startOffset = this.currentOffset++; this.currentOffset % 4 != 0; ++this.currentOffset) {
         this.writeByte(0);
      }

      if (builder.getHigh() - builder.getLow() + 1 != builder.getValues().size()) {
         throw new RuntimeException("high - low + 1 != the number of values in the table");
      } else {
         StackFrame frame = this.currentFrame.pop();
         if (builder.getDefaultLocation() != null) {
            this.writeInt(builder.getDefaultLocation().getLocation() - this.currentOffset);
         } else {
            this.writeInt(0);
            BranchEnd ret = new BranchEnd(this.currentOffset, frame, true, startOffset);
            builder.getDefaultBranchEnd().set(ret);
         }

         this.writeInt(builder.getLow());
         this.writeInt(builder.getHigh());
         this.currentOffset += 12;
         Iterator var7 = builder.getValues().iterator();

         while(var7.hasNext()) {
            TableSwitchBuilder.ValuePair value = (TableSwitchBuilder.ValuePair)var7.next();
            if (value.getLocation() != null) {
               this.writeInt(value.getLocation().getLocation());
               this.currentOffset += 4;
            } else {
               this.writeInt(0);
               BranchEnd ret = new BranchEnd(this.currentOffset, frame, true, startOffset);
               value.getBranchEnd().set(ret);
               this.currentOffset += 4;
            }
         }

         this.currentFrame = null;
      }
   }

   public void loadMethodParameters() {
      int index = this.method.isStatic() ? 0 : 1;
      String[] var2 = this.method.getParameters();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String type = var2[var4];
         if (type.length() > 1) {
            this.aload(index);
         } else if (type.equals("D")) {
            this.dload(index);
            ++index;
         } else if (type.equals("J")) {
            this.lload(index);
            ++index;
         } else if (type.equals("F")) {
            this.fload(index);
         } else {
            this.iload(index);
         }

         ++index;
      }

   }

   private void writeByte(int n) {
      try {
         this.data.writeByte(n);
      } catch (IOException var3) {
         throw new RuntimeException(var3);
      }
   }

   private void writeShort(int n) {
      try {
         if (n > 65534) {
            throw new RuntimeException(n + " is to big to be written as a 16 bit value");
         } else {
            this.data.writeShort(n);
         }
      } catch (IOException var3) {
         throw new RuntimeException(var3);
      }
   }

   private void writeInt(int n) {
      try {
         this.data.writeInt(n);
      } catch (IOException var3) {
         throw new RuntimeException(var3);
      }
   }

   private void overwriteShort(byte[] bytecode, int offset, int value) {
      bytecode[offset] = (byte)(value >> 8);
      bytecode[offset + 1] = (byte)value;
   }

   private void overwriteInt(byte[] bytecode, int offset, int value) {
      bytecode[offset] = (byte)(value >> 24);
      bytecode[offset + 1] = (byte)(value >> 16);
      bytecode[offset + 2] = (byte)(value >> 8);
      bytecode[offset + 3] = (byte)value;
   }

   public LinkedHashMap getStackFrames() {
      return new LinkedHashMap(this.stackFrames);
   }

   public void setupFrame(String... types) {
      LocalVariableState localVariableState = new LocalVariableState(this.constPool, types);
      StackFrame f = new StackFrame(new StackState(this.constPool), localVariableState, StackFrameType.FULL_FRAME);
      this.mergeStackFrames(f);
   }

   public ConstPool getConstPool() {
      return this.constPool;
   }

   private void duplicateFrame() {
      this.stackFrames.put(this.currentOffset, this.currentFrame);
      this.updateMaxValues();
   }

   private void advanceFrame(StackFrame frame) {
      this.stackFrames.put(this.currentOffset, frame);
      this.currentFrame = frame;
      this.updateMaxValues();
   }

   private void updateMaxValues() {
      if (this.getStack().getContents().size() > this.maxStackDepth) {
         this.maxStackDepth = this.getStack().getContents().size();
      }

      if (this.getLocalVars().getContents().size() > this.maxLocals) {
         this.maxLocals = this.getLocalVars().getContents().size();
      }

   }

   private LocalVariableState getLocalVars() {
      if (this.currentFrame == null) {
         throw new RuntimeException("No local variable information available, call setupFrame first");
      } else {
         return this.currentFrame.getLocalVariableState();
      }
   }

   private StackState getStack() {
      return this.currentFrame.getStackState();
   }

   public void assertTypeOnStack(int position, StackEntryType type, String message) {
      if (this.getStack().size() <= position) {
         throw new InvalidBytecodeException(message + " Stack State: " + this.getStack().toString());
      } else {
         int index = this.getStack().getContents().size() - 1 - position;
         if (type == StackEntryType.DOUBLE || type == StackEntryType.LONG) {
            --index;
         }

         StackEntryType stype = ((StackEntry)this.getStack().getContents().get(index)).getType();
         if (stype != type && (type != StackEntryType.OBJECT || stype != StackEntryType.NULL)) {
            throw new InvalidBytecodeException(message + " Stack State: " + this.getStack().toString());
         }
      }
   }

   public void assertTypeOnStack(StackEntryType type, String message) {
      this.assertTypeOnStack(0, type, message);
   }

   public void assertNotWideOnStack(int position, String message) {
      if (this.getStack().size() <= position) {
         throw new InvalidBytecodeException(message + " Stack State: " + this.getStack().toString());
      } else {
         int index = this.getStack().getContents().size() - 1 - position;
         StackEntryType stype = ((StackEntry)this.getStack().getContents().get(index)).getType();
         if (stype == StackEntryType.TOP) {
            throw new InvalidBytecodeException(message + " Stack State: " + this.getStack().toString());
         }
      }
   }

   public void assertNotWideOnStack(String message) {
      this.assertNotWideOnStack(0, message);
   }

   private void mergeStackFrames(StackFrame stackFrame) {
      if (this.currentFrame == null) {
         this.currentFrame = stackFrame;
         this.stackFrames.put(this.currentOffset, this.currentFrame);
      } else {
         StackState currentStackState = this.getStack();
         StackState mergeStackState = stackFrame.getStackState();
         if (currentStackState.size() != mergeStackState.size()) {
            throw new InvalidBytecodeException("Cannot merge stack frames, different stack sizes " + this.currentFrame + " " + stackFrame);
         } else {
            for(int i = 0; i < mergeStackState.size(); ++i) {
               StackEntry currentEntry = (StackEntry)currentStackState.getContents().get(i);
               StackEntry mergeEntry = (StackEntry)mergeStackState.getContents().get(i);
               if (mergeEntry.getType() == currentEntry.getType()) {
                  if (mergeEntry.getType() == StackEntryType.OBJECT && !mergeEntry.getDescriptor().equals(currentEntry.getDescriptor()) && this.method.getClassFile().getClassLoader() != null) {
                     String superType = this.findSuperType(mergeEntry.getDescriptor(), currentEntry.getDescriptor());
                     if (superType == null) {
                        throw new InvalidBytecodeException("Could not find common supertype for " + mergeEntry.getDescriptor() + " and " + currentEntry.getDescriptor() + " " + this.currentFrame + " " + stackFrame);
                     }

                     if (!superType.equals(currentEntry.getDescriptor())) {
                        this.stackFrames.put(this.currentOffset, this.currentFrame = this.currentFrame.mergeStack(i, new StackEntry(StackEntryType.OBJECT, DescriptorUtils.makeDescriptor(superType), this.constPool)));
                     }
                  }
               } else if ((mergeEntry.getType() != StackEntryType.NULL || currentEntry.getType() != StackEntryType.OBJECT) && (mergeEntry.getType() != StackEntryType.OBJECT || currentEntry.getType() != StackEntryType.NULL)) {
                  throw new InvalidBytecodeException("Cannot merge stack frame " + currentStackState + " with frame " + mergeStackState + " stack entry " + i + " is invalid " + this.currentFrame + " " + stackFrame);
               }
            }

            LocalVariableState currentLocalVariableState = this.getLocalVars();
            LocalVariableState mergeLocalVariableState = stackFrame.getLocalVariableState();
            if (currentLocalVariableState.size() < mergeLocalVariableState.size()) {
               throw new InvalidBytecodeException("Cannot merge stack frames, merge location has less locals than current location " + this.currentFrame + " " + stackFrame);
            } else {
               for(int i = 0; i < mergeLocalVariableState.size(); ++i) {
                  StackEntry currentEntry = (StackEntry)currentLocalVariableState.getContents().get(i);
                  StackEntry mergeEntry = (StackEntry)mergeLocalVariableState.getContents().get(i);
                  if (mergeEntry.getType() == currentEntry.getType()) {
                     if (mergeEntry.getType() == StackEntryType.OBJECT && !mergeEntry.getDescriptor().equals(currentEntry.getDescriptor()) && !mergeEntry.getDescriptor().equals(currentEntry.getDescriptor()) && this.method.getClassFile().getClassLoader() != null) {
                        String superType = this.findSuperType(mergeEntry.getDescriptor(), currentEntry.getDescriptor());
                        if (superType == null) {
                           throw new InvalidBytecodeException("Could not find common supertype for " + mergeEntry.getDescriptor() + " and " + currentEntry.getDescriptor() + " " + this.currentFrame + " " + stackFrame);
                        }

                        if (!superType.equals(currentEntry.getDescriptor())) {
                           this.stackFrames.put(this.currentOffset, this.currentFrame = this.currentFrame.mergeLocals(i, new StackEntry(StackEntryType.OBJECT, DescriptorUtils.makeDescriptor(superType), this.constPool)));
                        }
                     }
                  } else if ((mergeEntry.getType() != StackEntryType.NULL || currentEntry.getType() != StackEntryType.OBJECT) && (mergeEntry.getType() != StackEntryType.OBJECT || currentEntry.getType() != StackEntryType.NULL)) {
                     throw new InvalidBytecodeException("Cannot merge stack frame " + currentLocalVariableState + " with frame " + currentLocalVariableState + " local variable entry " + i + " is invalid " + this.currentFrame + " " + stackFrame);
                  }
               }

            }
         }
      }
   }

   private String findSuperType(String ds1, String ds2) {
      String d1 = ds1;
      if (ds1.endsWith(";")) {
         d1 = ds1.substring(1, ds1.length() - 1).replace("/", ".");
      }

      String d2 = ds2;
      if (ds2.endsWith(";")) {
         d2 = ds2.substring(1, ds2.length() - 1).replace("/", ".");
      }

      if (this.stackFrameTypeResolver != null) {
         return this.stackFrameTypeResolver.resolve(this.method.getClassFile().getClassLoader(), d1, d2);
      } else {
         ClassLoader cl = this.method.getClassFile().getClassLoader();

         try {
            Class c1 = cl.loadClass(d1);
            Class c2 = cl.loadClass(d2);
            if (c1.isAssignableFrom(c2)) {
               return c1.getName();
            } else if (c2.isAssignableFrom(c1)) {
               return c2.getName();
            } else {
               Class p;
               for(p = c1; p != Object.class; p = p.getSuperclass()) {
                  if (p.isAssignableFrom(c2)) {
                     return p.getName();
                  }
               }

               for(p = c2; p != Object.class; p = p.getSuperclass()) {
                  if (p.isAssignableFrom(c1)) {
                     return p.getName();
                  }
               }

               Set s1 = this.getAllSuperclassesAndInterface(c1, new HashSet());
               Set s2 = this.getAllSuperclassesAndInterface(c2, new HashSet());
               this.leavesOnly(s1);
               this.leavesOnly(s2);
               Set interfaces = new HashSet();
               interfaces.addAll(s1);
               interfaces.addAll(s2);
               interfaces.remove(c1);
               interfaces.remove(c2);
               if (interfaces.size() == 1) {
                  return ((Class)interfaces.iterator().next()).getName();
               } else if (interfaces.size() > 1) {
                  throw new RuntimeException("Could not resolve common superclass for " + d1 + " and " + d2);
               } else {
                  return Object.class.getName();
               }
            }
         } catch (ClassNotFoundException var12) {
            return null;
         }
      }
   }

   private void leavesOnly(Set s2) {
      List keys = new ArrayList(s2);
      Iterator var3 = keys.iterator();

      while(true) {
         while(var3.hasNext()) {
            Class key = (Class)var3.next();
            Iterator var5 = s2.iterator();

            while(var5.hasNext()) {
               Class content = (Class)var5.next();
               if (key != content && key.isAssignableFrom(content)) {
                  s2.remove(key);
                  break;
               }
            }
         }

         return;
      }
   }

   private Set getAllSuperclassesAndInterface(Class c, Set set) {
      set.addAll(Arrays.asList(c.getInterfaces()));
      set.add(c);
      if (c.getSuperclass() != null) {
         this.getAllSuperclassesAndInterface(c.getSuperclass(), set);
      }

      return set;
   }

   private void addIfIcmp(CodeLocation location, int opcode, String name) {
      this.assertTypeOnStack(StackEntryType.INT, name + " requires int on stack");
      this.assertTypeOnStack(1, StackEntryType.INT, name + " requires int in position 2 on stack");
      this.writeByte(opcode);
      this.writeShort(location.getLocation() - this.currentOffset);
      this.currentOffset += 3;
      this.advanceFrame(this.currentFrame.pop2());
      this.mergeStackFrames(location.getStackFrame());
   }

   private BranchEnd addIfIcmp(int opcode, String name) {
      this.assertTypeOnStack(StackEntryType.INT, name + " requires int on stack");
      this.assertTypeOnStack(1, StackEntryType.INT, name + " requires int int position 2 on stack");
      this.writeByte(opcode);
      this.writeShort(0);
      this.currentOffset += 3;
      this.advanceFrame(this.currentFrame.pop2());
      BranchEnd ret = new BranchEnd(this.currentOffset - 2, this.currentFrame, this.currentOffset - 3);
      return ret;
   }

   private void addIf(CodeLocation location, int opcode, String name) {
      this.assertTypeOnStack(StackEntryType.INT, name + " requires int on stack");
      this.writeByte(opcode);
      this.writeShort(location.getLocation() - this.currentOffset);
      this.currentOffset += 3;
      this.advanceFrame(this.currentFrame.pop());
      this.mergeStackFrames(location.getStackFrame());
   }

   private BranchEnd addIf(int opcode, String name) {
      this.assertTypeOnStack(StackEntryType.INT, name + " requires int on stack");
      this.writeByte(opcode);
      this.writeShort(0);
      this.currentOffset += 3;
      this.advanceFrame(this.currentFrame.pop());
      return new BranchEnd(this.currentOffset - 2, this.currentFrame, this.currentOffset - 3);
   }

   private void addNullComparison(CodeLocation location, int opcode, String name) {
      this.assertTypeOnStack(StackEntryType.OBJECT, name + " requires reference type on stack");
      this.writeByte(opcode);
      this.writeShort(location.getLocation() - this.currentOffset);
      this.currentOffset += 3;
      this.advanceFrame(this.currentFrame.pop());
      this.mergeStackFrames(location.getStackFrame());
   }

   private BranchEnd addNullComparison(int opcode, String name) {
      this.assertTypeOnStack(StackEntryType.OBJECT, name + " requires reference type on stack");
      this.writeByte(opcode);
      this.writeShort(0);
      this.currentOffset += 3;
      this.advanceFrame(this.currentFrame.pop());
      return new BranchEnd(this.currentOffset - 2, this.currentFrame, this.currentOffset - 3);
   }

   public interface StackFrameTypeResolver {
      String resolve(ClassLoader var1, String var2, String var3);
   }
}

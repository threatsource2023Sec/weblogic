package weblogic.utils.classfile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintStream;
import weblogic.utils.classfile.cp.CPClass;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.expr.Statement;

public class CodeAttribute extends attribute_info implements AttributeParent {
   public static final String NAME = "Code";
   public static final int END_OF_CODE = -1;
   private int maxStack;
   private int maxLocals;
   public Bytecodes code;
   public exception_struct[] exception_table;
   AttributeTable attributes;
   protected MethodInfo methodInfo;
   private static final boolean DEBUG = false;

   public CodeAttribute() {
   }

   public CodeAttribute(MethodInfo methodInfo) {
      this.methodInfo = methodInfo;
      this.constant_pool = methodInfo.getClassFile().getConstantPool();
      this.attribute_name = this.constant_pool.getUtf8("Code");
      this.exception_table = new exception_struct[0];
      this.attributes = new AttributeTable((AttributeParent)null, methodInfo.getClassFile());
   }

   public Scope getScope() {
      if (this.methodInfo == null) {
         throw new AssertionError("scope not defined");
      } else {
         return this.methodInfo.getScope();
      }
   }

   public void setCode(Statement statement) {
      this.code = new Bytecodes(this, this.methodInfo.getConstantPool());
      statement.code(this, this.code);
      this.maxLocals = this.methodInfo.getScope().getMaxLocals();
      this.maxStack = statement.getMaxStack();
   }

   public AttributeTable getAttributes() {
      return this.attributes;
   }

   public MethodInfo getMethodInfo() {
      return this.methodInfo;
   }

   public ConstantPool getConstantPool() {
      return this.methodInfo.getConstantPool();
   }

   public void read(DataInput in) throws IOException, BadBytecodesException {
      super.read(in);
      this.maxStack = in.readUnsignedShort();
      this.maxLocals = in.readUnsignedShort();
      this.code = new Bytecodes(this, this.constant_pool);
      this.code.read(in);

      try {
         int size = in.readUnsignedShort();
         this.exception_table = new exception_struct[size];

         for(int i = 0; i < this.exception_table.length; ++i) {
            exception_struct excep = new exception_struct();
            int start_pc = in.readUnsignedShort();
            int end_pc = in.readUnsignedShort();
            int hand_pc = in.readUnsignedShort();
            excep.start_op = this.code.opAtPC(start_pc);
            excep.end_op = this.code.opAtPC(end_pc);
            excep.handler_pc = this.code.opAtPC(hand_pc);

            try {
               excep.catch_type = this.constant_pool.classAt(in.readUnsignedShort());
            } catch (MalformedClassException var9) {
               throw new IOException(String.valueOf(var9));
            }

            this.exception_table[i] = excep;
            String ex = start_pc + "  " + end_pc + "  " + hand_pc + "  ";
            if (excep.catch_type == null) {
               ex = ex + "finally";
            } else {
               (new StringBuilder()).append(ex).append(excep.catch_type.name.getValue()).toString();
            }
         }
      } catch (BadBytecodesException var10) {
         throw new IOException(String.valueOf(var10));
      }

      this.attributes = new AttributeTable((AttributeParent)null, this.methodInfo.getClassFile());
      this.attributes.read(in);
   }

   public void write(DataOutput out) throws IOException, BadBytecodesException {
      out.writeShort(this.attribute_name.getIndex());
      DataBuffer buf = new DataBuffer();
      buf.writeShort(this.maxStack);
      buf.writeShort(this.maxLocals);
      this.code.write(buf);
      buf.writeShort(this.exception_table.length);

      for(int i = 0; i < this.exception_table.length; ++i) {
         exception_struct excep = this.exception_table[i];
         int pc = this.code.pcForOp(excep.start_op);
         buf.writeShort(pc);
         buf.writeShort(this.code.pcForOp(excep.end_op));
         buf.writeShort(this.code.pcForOp(excep.handler_pc));
         if (excep.catch_type == null) {
            buf.writeShort(0);
         } else {
            buf.writeShort(excep.catch_type.getIndex());
         }
      }

      this.attributes.write(buf);
      buf.writeWithLenAsInt(out);
   }

   public void addException(Op start_op, Op end_op, Op handler_pc, CPClass catch_type) {
      exception_struct[] tmp = new exception_struct[this.exception_table.length + 1];
      System.arraycopy(this.exception_table, 0, tmp, 0, this.exception_table.length);
      tmp[this.exception_table.length] = new exception_struct();
      tmp[this.exception_table.length].start_op = start_op;
      tmp[this.exception_table.length].end_op = end_op;
      tmp[this.exception_table.length].handler_pc = handler_pc;
      tmp[this.exception_table.length].catch_type = catch_type;
      this.exception_table = tmp;
   }

   public void dump(PrintStream out) throws BadBytecodesException {
      out.println("max_stack  = " + this.maxStack);
      out.println("max_locals = " + this.maxLocals);
      this.code.dump(out);
      out.println("exceptions");

      for(int i = 0; i < this.exception_table.length; ++i) {
         exception_struct excep = this.exception_table[i];
         out.print(this.code.pcForOp(excep.start_op) + " " + this.code.pcForOp(excep.end_op) + " " + this.code.pcForOp(excep.handler_pc) + " ");
         if (excep.catch_type == null) {
            out.println("null");
         } else {
            out.println(excep.catch_type.name.getValue());
         }
      }

   }

   private static void say(String s) {
      System.out.println(s);
   }

   public static void debug(String msg) {
   }

   class exception_struct {
      public Op start_op;
      public Op end_op;
      public Op handler_pc;
      public CPClass catch_type;
   }
}

package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class Code extends Attribute {
   private int maxStack;
   private int maxLocals;
   private byte[] code;
   private CodeException[] exceptionTable;
   private Attribute[] attributes;
   private static final CodeException[] NO_EXCEPTIONS = new CodeException[0];

   public Code(Code c) {
      this(c.getNameIndex(), c.getLength(), c.getMaxStack(), c.getMaxLocals(), c.getCode(), c.getExceptionTable(), c.getAttributes(), c.getConstantPool());
   }

   Code(int name_index, int length, DataInputStream file, ConstantPool constant_pool) throws IOException {
      this(name_index, length, file.readUnsignedShort(), file.readUnsignedShort(), (byte[])null, (CodeException[])null, (Attribute[])null, constant_pool);
      int len = file.readInt();
      this.code = new byte[len];
      file.readFully(this.code);
      len = file.readUnsignedShort();
      if (len == 0) {
         this.exceptionTable = NO_EXCEPTIONS;
      } else {
         this.exceptionTable = new CodeException[len];

         for(int i = 0; i < len; ++i) {
            this.exceptionTable[i] = new CodeException(file);
         }
      }

      this.attributes = AttributeUtils.readAttributes(file, constant_pool);
      this.length = length;
   }

   public Code(int name_index, int length, int max_stack, int max_locals, byte[] code, CodeException[] exception_table, Attribute[] attributes, ConstantPool constant_pool) {
      super((byte)2, name_index, length, constant_pool);
      this.maxStack = max_stack;
      this.maxLocals = max_locals;
      this.setCode(code);
      this.setExceptionTable(exception_table);
      this.setAttributes(attributes);
   }

   public void accept(ClassVisitor v) {
      v.visitCode(this);
   }

   public final void dump(DataOutputStream file) throws IOException {
      super.dump(file);
      file.writeShort(this.maxStack);
      file.writeShort(this.maxLocals);
      file.writeInt(this.code.length);
      file.write(this.code, 0, this.code.length);
      file.writeShort(this.exceptionTable.length);

      int i;
      for(i = 0; i < this.exceptionTable.length; ++i) {
         this.exceptionTable[i].dump(file);
      }

      file.writeShort(this.attributes.length);

      for(i = 0; i < this.attributes.length; ++i) {
         this.attributes[i].dump(file);
      }

   }

   public final Attribute[] getAttributes() {
      return this.attributes;
   }

   public LineNumberTable getLineNumberTable() {
      for(int i = 0; i < this.attributes.length; ++i) {
         if (this.attributes[i].tag == 4) {
            return (LineNumberTable)this.attributes[i];
         }
      }

      return null;
   }

   public LocalVariableTable getLocalVariableTable() {
      for(int i = 0; i < this.attributes.length; ++i) {
         if (this.attributes[i].tag == 5) {
            return (LocalVariableTable)this.attributes[i];
         }
      }

      return null;
   }

   public final byte[] getCode() {
      return this.code;
   }

   public final CodeException[] getExceptionTable() {
      return this.exceptionTable;
   }

   public final int getMaxLocals() {
      return this.maxLocals;
   }

   public final int getMaxStack() {
      return this.maxStack;
   }

   private final int getInternalLength() {
      return 8 + (this.code == null ? 0 : this.code.length) + 2 + 8 * (this.exceptionTable == null ? 0 : this.exceptionTable.length) + 2;
   }

   private final int calculateLength() {
      int len = 0;
      if (this.attributes != null) {
         for(int i = 0; i < this.attributes.length; ++i) {
            len += this.attributes[i].length + 6;
         }
      }

      return len + this.getInternalLength();
   }

   public final void setAttributes(Attribute[] attributes) {
      this.attributes = attributes;
      this.length = this.calculateLength();
   }

   public final void setCode(byte[] code) {
      this.code = code;
   }

   public final void setExceptionTable(CodeException[] exception_table) {
      this.exceptionTable = exception_table;
   }

   public final void setMaxLocals(int max_locals) {
      this.maxLocals = max_locals;
   }

   public final void setMaxStack(int max_stack) {
      this.maxStack = max_stack;
   }

   public final String toString(boolean verbose) {
      StringBuffer buf = new StringBuffer("Code(max_stack = " + this.maxStack + ", max_locals = " + this.maxLocals + ", code_length = " + this.code.length + ")\n" + Utility.codeToString(this.code, this.cpool, 0, -1, verbose));
      int i;
      if (this.exceptionTable.length > 0) {
         buf.append("\nException handler(s) = \nFrom\tTo\tHandler\tType\n");

         for(i = 0; i < this.exceptionTable.length; ++i) {
            buf.append(this.exceptionTable[i].toString(this.cpool, verbose) + "\n");
         }
      }

      if (this.attributes.length > 0) {
         buf.append("\nAttribute(s) = \n");

         for(i = 0; i < this.attributes.length; ++i) {
            buf.append(this.attributes[i].toString() + "\n");
         }
      }

      return buf.toString();
   }

   public final String toString() {
      return this.toString(true);
   }

   public String getCodeString() {
      StringBuffer codeString = new StringBuffer();
      codeString.append("Code(max_stack = ").append(this.maxStack);
      codeString.append(", max_locals = ").append(this.maxLocals);
      codeString.append(", code_length = ").append(this.code.length).append(")\n");
      codeString.append(Utility.codeToString(this.code, this.cpool, 0, -1, true));
      if (this.exceptionTable.length > 0) {
         codeString.append("\n").append("Exception entries =  ").append(this.exceptionTable.length).append("\n");

         for(int i = 0; i < this.exceptionTable.length; ++i) {
            CodeException exc = this.exceptionTable[i];
            int type = exc.getCatchType();
            String name = "finally";
            if (type != 0) {
               name = this.cpool.getConstantString(type, (byte)7);
            }

            codeString.append(name).append("[");
            codeString.append(exc.getStartPC()).append(">").append(exc.getEndPC()).append("]\n");
         }
      }

      return codeString.toString();
   }
}

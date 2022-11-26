package weblogic.utils.classfile.ops;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.classfile.MalformedClassException;
import weblogic.utils.classfile.Op;
import weblogic.utils.classfile.cp.CPInterfaceMethodref;
import weblogic.utils.classfile.cp.ConstantPool;

public class InvokeInterfaceOp extends Op {
   private ConstantPool constant_pool;
   private CPInterfaceMethodref interfaceMethodRef;
   private int nargs;

   public InvokeInterfaceOp(int op_code, ConstantPool constant_pool) {
      super(op_code);
      this.constant_pool = constant_pool;
   }

   public InvokeInterfaceOp(int op_code, ConstantPool constant_pool, CPInterfaceMethodref interfaceMethodRef, int nargs) {
      super(op_code);
      this.constant_pool = constant_pool;
      this.interfaceMethodRef = interfaceMethodRef;
      this.nargs = nargs;
   }

   public void read(DataInput in) throws IOException {
      try {
         this.interfaceMethodRef = this.constant_pool.interfaceMethodrefAt(in.readUnsignedShort());
      } catch (MalformedClassException var3) {
         throw new IOException(var3.toString());
      }

      this.nargs = in.readUnsignedByte();
      in.readByte();
   }

   public void write(DataOutput out) throws IOException {
      out.writeByte(this.op_code);
      out.writeShort(this.interfaceMethodRef.getIndex());
      out.writeByte(this.nargs);
      out.writeByte(0);
   }

   public String toString() {
      return super.toString() + " " + this.interfaceMethodRef;
   }
}

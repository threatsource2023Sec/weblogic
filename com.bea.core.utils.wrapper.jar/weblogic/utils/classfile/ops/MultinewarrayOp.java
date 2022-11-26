package weblogic.utils.classfile.ops;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.classfile.MalformedClassException;
import weblogic.utils.classfile.Op;
import weblogic.utils.classfile.cp.CPClass;
import weblogic.utils.classfile.cp.ConstantPool;

public class MultinewarrayOp extends Op {
   ConstantPool constant_pool;
   CPClass clazz;
   int dimensions;

   public MultinewarrayOp(int op_code, ConstantPool constant_pool) {
      super(op_code);
      this.constant_pool = constant_pool;
   }

   public MultinewarrayOp(int op_code, ConstantPool constant_pool, CPClass clazz, int dimensions) {
      this(op_code, constant_pool);
      this.clazz = clazz;
      this.dimensions = dimensions;
   }

   public void read(DataInput in) throws IOException {
      try {
         this.clazz = this.constant_pool.classAt(in.readUnsignedShort());
      } catch (MalformedClassException var3) {
         throw new IOException(String.valueOf(var3));
      }

      this.dimensions = in.readUnsignedByte();
   }

   public void write(DataOutput out) throws IOException {
      out.writeByte(this.op_code);
      out.writeShort(this.clazz.getIndex());
      out.writeByte(this.dimensions);
   }

   public String toString() {
      return super.toString() + " clazzz = " + this.clazz + " dim = " + this.dimensions;
   }
}

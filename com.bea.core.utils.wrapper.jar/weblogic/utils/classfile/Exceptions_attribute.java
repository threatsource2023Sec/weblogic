package weblogic.utils.classfile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.classfile.cp.CPClass;
import weblogic.utils.classfile.cp.ConstantPool;

public class Exceptions_attribute extends attribute_info {
   public static final String NAME = "Exceptions";
   public int number_of_exceptions;
   public CPClass[] exception_table;

   public Exceptions_attribute() {
   }

   public Exceptions_attribute(ConstantPool constant_pool) {
      this.constant_pool = constant_pool;
      this.attribute_name = constant_pool.getUtf8("Exceptions");
   }

   public CPClass[] getExceptionTable() {
      return this.exception_table;
   }

   public void read(DataInput in) throws IOException, BadBytecodesException {
      super.read(in);

      try {
         this.number_of_exceptions = in.readUnsignedShort();
         this.exception_table = new CPClass[this.number_of_exceptions];

         for(int i = 0; i < this.number_of_exceptions; ++i) {
            int idx = in.readUnsignedShort();
            if (idx == 0) {
               this.exception_table[i] = null;
            } else {
               this.exception_table[i] = this.constant_pool.classAt(idx);
            }
         }

      } catch (MalformedClassException var4) {
         throw new IOException(String.valueOf(var4));
      }
   }

   public void write(DataOutput out) throws IOException, BadBytecodesException {
      this.attribute_length = 2 + 2 * this.number_of_exceptions;
      super.write(out);
      out.writeShort(this.number_of_exceptions);

      for(int i = 0; i < this.number_of_exceptions; ++i) {
         if (this.exception_table[i] == null) {
            out.writeShort(0);
         } else {
            out.writeShort(this.exception_table[i].getIndex());
         }
      }

   }

   private static void say(String s) {
      System.out.println(s);
   }
}

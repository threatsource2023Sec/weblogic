package weblogic.utils.classfile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.classfile.cp.CPUtf8;
import weblogic.utils.classfile.cp.ConstantPool;

public class LocalVariableTable_attribute extends attribute_info {
   public static final String NAME = "LocalVariableTable";
   int local_variable_table_length;
   local_var_struct[] local_variable_table;

   public void read(DataInput in) throws IOException, BadBytecodesException {
      super.read(in);
      this.local_variable_table_length = in.readUnsignedShort();
      this.local_variable_table = new local_var_struct[this.local_variable_table_length];

      for(int i = 0; i < this.local_variable_table_length; ++i) {
         local_var_struct var = new local_var_struct(this.constant_pool);
         var.read(in);
         this.local_variable_table[i] = var;
      }

   }

   public void write(DataOutput out) throws IOException, BadBytecodesException {
      super.write(out);
      out.writeShort(this.local_variable_table_length);

      for(int i = 0; i < this.local_variable_table_length; ++i) {
         this.local_variable_table[i].write(out);
      }

   }

   class local_var_struct {
      int start_pc;
      int length;
      CPUtf8 name;
      CPUtf8 descriptor;
      int index;
      ConstantPool constant_pool;

      local_var_struct(ConstantPool constant_pool) {
         this.constant_pool = constant_pool;
      }

      public void read(DataInput in) throws IOException {
         try {
            this.start_pc = in.readUnsignedShort();
            this.length = in.readUnsignedShort();
            this.name = this.constant_pool.utf8At(in.readUnsignedShort());
            this.descriptor = this.constant_pool.utf8At(in.readUnsignedShort());
            this.index = in.readUnsignedShort();
         } catch (MalformedClassException var3) {
            throw new IOException(String.valueOf(var3));
         }
      }

      public void write(DataOutput out) throws IOException {
         out.writeShort(this.start_pc);
         out.writeShort(this.length);
         out.writeShort(this.name.getIndex());
         out.writeShort(this.descriptor.getIndex());
         out.writeShort(this.index);
      }
   }
}

package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LocalVariableTypeTable extends Attribute {
   private int local_variable_type_table_length;
   private LocalVariable[] local_variable_type_table;

   public LocalVariableTypeTable(LocalVariableTypeTable c) {
      this(c.getNameIndex(), c.getLength(), c.getLocalVariableTypeTable(), c.getConstantPool());
   }

   public LocalVariableTypeTable(int name_index, int length, LocalVariable[] local_variable_table, ConstantPool constant_pool) {
      super((byte)16, name_index, length, constant_pool);
      this.setLocalVariableTable(local_variable_table);
   }

   LocalVariableTypeTable(int nameIdx, int len, DataInputStream dis, ConstantPool cpool) throws IOException {
      this(nameIdx, len, (LocalVariable[])null, cpool);
      this.local_variable_type_table_length = dis.readUnsignedShort();
      this.local_variable_type_table = new LocalVariable[this.local_variable_type_table_length];

      for(int i = 0; i < this.local_variable_type_table_length; ++i) {
         this.local_variable_type_table[i] = new LocalVariable(dis, cpool);
      }

   }

   public void accept(ClassVisitor v) {
      v.visitLocalVariableTypeTable(this);
   }

   public final void dump(DataOutputStream file) throws IOException {
      super.dump(file);
      file.writeShort(this.local_variable_type_table_length);

      for(int i = 0; i < this.local_variable_type_table_length; ++i) {
         this.local_variable_type_table[i].dump(file);
      }

   }

   public final LocalVariable[] getLocalVariableTypeTable() {
      return this.local_variable_type_table;
   }

   public final LocalVariable getLocalVariable(int index) {
      for(int i = 0; i < this.local_variable_type_table_length; ++i) {
         if (this.local_variable_type_table[i].getIndex() == index) {
            return this.local_variable_type_table[i];
         }
      }

      return null;
   }

   public final void setLocalVariableTable(LocalVariable[] local_variable_table) {
      this.local_variable_type_table = local_variable_table;
      this.local_variable_type_table_length = local_variable_table == null ? 0 : local_variable_table.length;
   }

   public final String toString() {
      StringBuffer buf = new StringBuffer("");

      for(int i = 0; i < this.local_variable_type_table_length; ++i) {
         buf.append(this.local_variable_type_table[i].toString());
         if (i < this.local_variable_type_table_length - 1) {
            buf.append('\n');
         }
      }

      return buf.toString();
   }

   public final int getTableLength() {
      return this.local_variable_type_table_length;
   }
}

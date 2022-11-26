package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LocalVariableTable extends Attribute {
   private boolean isInPackedState;
   private byte[] data;
   private int localVariableTableLength;
   private LocalVariable[] localVariableTable;

   public LocalVariableTable(LocalVariableTable c) {
      this(c.getNameIndex(), c.getLength(), c.getLocalVariableTable(), c.getConstantPool());
   }

   public LocalVariableTable(int name_index, int length, LocalVariable[] local_variable_table, ConstantPool constant_pool) {
      super((byte)5, name_index, length, constant_pool);
      this.isInPackedState = false;
      this.setLocalVariableTable(local_variable_table);
   }

   LocalVariableTable(int name_index, int length, DataInputStream file, ConstantPool constant_pool) throws IOException {
      super((byte)5, name_index, length, constant_pool);
      this.isInPackedState = false;
      this.data = new byte[length];
      file.readFully(this.data);
      this.isInPackedState = true;
   }

   public void accept(ClassVisitor v) {
      this.unpack();
      v.visitLocalVariableTable(this);
   }

   public final void dump(DataOutputStream file) throws IOException {
      super.dump(file);
      if (this.isInPackedState) {
         file.write(this.data);
      } else {
         file.writeShort(this.localVariableTableLength);

         for(int i = 0; i < this.localVariableTableLength; ++i) {
            this.localVariableTable[i].dump(file);
         }
      }

   }

   public final LocalVariable[] getLocalVariableTable() {
      this.unpack();
      return this.localVariableTable;
   }

   public final LocalVariable getLocalVariable(int index) {
      this.unpack();

      for(int i = 0; i < this.localVariableTableLength; ++i) {
         if (this.localVariableTable[i] != null && this.localVariableTable[i].getIndex() == index) {
            return this.localVariableTable[i];
         }
      }

      return null;
   }

   public final void setLocalVariableTable(LocalVariable[] local_variable_table) {
      this.data = null;
      this.isInPackedState = false;
      this.localVariableTable = local_variable_table;
      this.localVariableTableLength = local_variable_table == null ? 0 : local_variable_table.length;
   }

   public final String toString() {
      StringBuffer buf = new StringBuffer("");
      this.unpack();

      for(int i = 0; i < this.localVariableTableLength; ++i) {
         buf.append(this.localVariableTable[i].toString());
         if (i < this.localVariableTableLength - 1) {
            buf.append('\n');
         }
      }

      return buf.toString();
   }

   public final int getTableLength() {
      this.unpack();
      return this.localVariableTableLength;
   }

   private void unpack() {
      if (this.isInPackedState) {
         try {
            ByteArrayInputStream bs = new ByteArrayInputStream(this.data);
            DataInputStream dis = new DataInputStream(bs);
            this.localVariableTableLength = dis.readUnsignedShort();
            this.localVariableTable = new LocalVariable[this.localVariableTableLength];
            int i = 0;

            while(true) {
               if (i >= this.localVariableTableLength) {
                  dis.close();
                  this.data = null;
                  break;
               }

               this.localVariableTable[i] = new LocalVariable(dis, this.cpool);
               ++i;
            }
         } catch (IOException var4) {
            throw new RuntimeException("Unpacking of LocalVariableTable attribute failed");
         }

         this.isInPackedState = false;
      }
   }
}

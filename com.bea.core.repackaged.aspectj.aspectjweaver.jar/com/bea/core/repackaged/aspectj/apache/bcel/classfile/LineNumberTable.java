package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class LineNumberTable extends Attribute {
   private boolean isInPackedState;
   private byte[] data;
   private int tableLength;
   private LineNumber[] table;

   public LineNumberTable(LineNumberTable c) {
      this(c.getNameIndex(), c.getLength(), c.getLineNumberTable(), c.getConstantPool());
   }

   public LineNumberTable(int nameIndex, int length, LineNumber[] lineNumberTable, ConstantPool constantPool) {
      super((byte)4, nameIndex, length, constantPool);
      this.isInPackedState = false;
      this.setLineNumberTable(lineNumberTable);
      this.isInPackedState = false;
   }

   LineNumberTable(int name_index, int length, DataInputStream file, ConstantPool constant_pool) throws IOException {
      this(name_index, length, (LineNumber[])null, constant_pool);
      this.data = new byte[length];
      file.readFully(this.data);
      this.isInPackedState = true;
   }

   private void unpack() {
      if (this.isInPackedState) {
         try {
            ByteArrayInputStream bs = new ByteArrayInputStream(this.data);
            DataInputStream dis = new DataInputStream(bs);
            this.tableLength = dis.readUnsignedShort();
            this.table = new LineNumber[this.tableLength];
            int i = 0;

            while(true) {
               if (i >= this.tableLength) {
                  dis.close();
                  this.data = null;
                  break;
               }

               this.table[i] = new LineNumber(dis);
               ++i;
            }
         } catch (IOException var4) {
            throw new RuntimeException("Unpacking of LineNumberTable attribute failed");
         }

         this.isInPackedState = false;
      }

   }

   public void accept(ClassVisitor v) {
      this.unpack();
      v.visitLineNumberTable(this);
   }

   public final void dump(DataOutputStream file) throws IOException {
      super.dump(file);
      if (this.isInPackedState) {
         file.write(this.data);
      } else {
         file.writeShort(this.tableLength);

         for(int i = 0; i < this.tableLength; ++i) {
            this.table[i].dump(file);
         }
      }

   }

   public final LineNumber[] getLineNumberTable() {
      this.unpack();
      return this.table;
   }

   public final void setLineNumberTable(LineNumber[] line_number_table) {
      this.data = null;
      this.isInPackedState = false;
      this.table = line_number_table;
      this.tableLength = line_number_table == null ? 0 : line_number_table.length;
   }

   public final String toString() {
      this.unpack();
      StringBuffer buf = new StringBuffer();
      StringBuffer line = new StringBuffer();

      for(int i = 0; i < this.tableLength; ++i) {
         line.append(this.table[i].toString());
         if (i < this.tableLength - 1) {
            line.append(", ");
         }

         if (line.length() > 72) {
            line.append('\n');
            buf.append(line);
            line.setLength(0);
         }
      }

      buf.append(line);
      return buf.toString();
   }

   public int getSourceLine(int pos) {
      this.unpack();
      int l = 0;
      int r = this.tableLength - 1;
      if (r < 0) {
         return -1;
      } else {
         int min_index = -1;
         int min = -1;

         do {
            int i = (l + r) / 2;
            int j = this.table[i].getStartPC();
            if (j == pos) {
               return this.table[i].getLineNumber();
            }

            if (pos < j) {
               r = i - 1;
            } else {
               l = i + 1;
            }

            if (j < pos && j > min) {
               min = j;
               min_index = i;
            }
         } while(l <= r);

         if (min_index < 0) {
            return -1;
         } else {
            return this.table[min_index].getLineNumber();
         }
      }
   }

   public final int getTableLength() {
      this.unpack();
      return this.tableLength;
   }
}

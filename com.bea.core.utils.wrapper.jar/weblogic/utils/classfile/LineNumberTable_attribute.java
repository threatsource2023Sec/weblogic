package weblogic.utils.classfile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.collections.ArrayMap;

public class LineNumberTable_attribute extends attribute_info {
   public static final String NAME = "LineNumberTable";
   int line_number_table_length;
   line_num_struct[] line_number_table;
   private ArrayMap ops2Lines = new ArrayMap();

   public int getLineNumber(int pc) {
      int lineNumber = -1;

      for(int i = 0; i < this.line_number_table.length; ++i) {
         line_num_struct lns = this.line_number_table[i];
         if (lns != null) {
            Op op = lns.start_op;
            int opPc = lns.code.pcForOp(op);
            if (opPc > pc) {
               break;
            }

            lineNumber = lns.line_number;
         }
      }

      if (lineNumber != -1) {
         return lineNumber;
      } else {
         throw new OpNotInMethodException("no line number found for pc = " + pc);
      }
   }

   public int getLineNumber(Op op) throws OpNotInMethodException {
      System.out.println("Looking for op " + op);
      Integer i = (Integer)this.ops2Lines.get(op);
      if (i != null) {
         return i;
      } else {
         throw new OpNotInMethodException(op.toString());
      }
   }

   public void read(DataInput in) throws IOException, BadBytecodesException {
      super.read(in);
      this.line_number_table_length = in.readUnsignedShort();
      this.line_number_table = new line_num_struct[this.line_number_table_length];

      for(int i = 0; i < this.line_number_table_length; ++i) {
         line_num_struct num = new line_num_struct();
         num.read(in);
         this.line_number_table[i] = num;
      }

   }

   public void write(DataOutput out) throws IOException, BadBytecodesException {
      super.write(out);
      out.writeShort(this.line_number_table_length);

      for(int i = 0; i < this.line_number_table_length; ++i) {
         this.line_number_table[i].write(out);
      }

   }

   class line_num_struct {
      Op start_op;
      int line_number;
      Bytecodes code;

      line_num_struct() {
         this.code = ((CodeAttribute)LineNumberTable_attribute.this.parent).code;
      }

      public void read(DataInput in) throws IOException {
         int start_pc = in.readUnsignedShort();

         try {
            this.start_op = this.code.opAtPC(start_pc);
         } catch (BadBytecodesException var4) {
            throw new IOException(String.valueOf(var4));
         }

         this.line_number = in.readUnsignedShort();
         LineNumberTable_attribute.this.ops2Lines.put(this.start_op, new Integer(this.line_number));
      }

      public void write(DataOutput out) throws IOException {
         out.writeShort(this.code.pcForOp(this.start_op));
         out.writeShort(this.line_number);
      }
   }
}

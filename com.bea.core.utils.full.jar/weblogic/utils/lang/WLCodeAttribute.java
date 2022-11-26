package weblogic.utils.lang;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WLCodeAttribute extends WLAttribute {
   short max_stack;
   short max_locals;
   int code_length;
   byte[] code;
   short exception_table_length;
   WLExceptionTableEntry[] exception_table;
   short attributes_count;
   WLAttribute[] attributes;

   WLCodeAttribute(short attribute_name_index, int attribute_length) {
      this.attribute_name_index = attribute_name_index;
      this.attribute_length = attribute_length;
   }

   public void write(OutputStream os) throws IOException {
      DataOutputStream dos = new DataOutputStream(os);
      dos.writeShort(this.attribute_name_index);
      dos.writeInt(this.attribute_length);
      dos.writeShort(this.max_stack);
      dos.writeShort(this.max_locals);
      dos.writeInt(this.code_length);
      dos.write(this.code, 0, this.code_length);
      dos.writeShort(this.exception_table_length);

      int i;
      for(i = 0; i < this.exception_table_length; ++i) {
         this.exception_table[i].write(os);
      }

      dos.writeShort(this.attributes_count);

      for(i = 0; i < this.attributes_count; ++i) {
         this.attributes[i].write(os);
      }

   }

   public WLAttribute read(InputStream is) throws IOException {
      DataInputStream dis = new DataInputStream(is);
      this.max_stack = dis.readShort();
      this.max_locals = dis.readShort();
      this.code_length = dis.readInt();
      this.code = new byte[this.code_length];
      dis.readFully(this.code);
      this.exception_table_length = dis.readShort();
      this.exception_table = new WLExceptionTableEntry[this.exception_table_length];

      int i;
      for(i = 0; i < this.exception_table_length; ++i) {
         this.exception_table[i] = (new WLExceptionTableEntry()).read(is);
      }

      this.attributes_count = dis.readShort();
      this.attributes = new WLAttribute[this.attributes_count];

      for(i = 0; i < this.attributes_count; ++i) {
         this.attributes[i] = (new WLAttribute()).read(is);
      }

      return this;
   }
}

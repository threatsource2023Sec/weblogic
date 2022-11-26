package weblogic.utils.lang;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class WLExceptionTableEntry implements Writable {
   short start_pc;
   short end_pc;
   short handler_pc;
   short catch_type;

   public void write(OutputStream os) throws IOException {
      DataOutputStream dos = new DataOutputStream(os);
      dos.writeShort(this.start_pc);
      dos.writeShort(this.end_pc);
      dos.writeShort(this.handler_pc);
      dos.writeShort(this.catch_type);
   }

   public WLExceptionTableEntry read(InputStream is) throws IOException {
      DataInputStream dis = new DataInputStream(is);
      this.start_pc = dis.readShort();
      this.end_pc = dis.readShort();
      this.handler_pc = dis.readShort();
      this.catch_type = dis.readShort();
      return this;
   }
}

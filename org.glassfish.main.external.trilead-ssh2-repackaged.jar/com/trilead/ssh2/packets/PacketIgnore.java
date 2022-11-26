package com.trilead.ssh2.packets;

import java.io.IOException;

public class PacketIgnore {
   byte[] payload;
   byte[] data;

   public void setData(byte[] data) {
      this.data = data;
      this.payload = null;
   }

   public PacketIgnore() {
   }

   public PacketIgnore(byte[] payload, int off, int len) throws IOException {
      this.payload = new byte[len];
      System.arraycopy(payload, off, this.payload, 0, len);
      TypesReader tr = new TypesReader(payload, off, len);
      int packet_type = tr.readByte();
      if (packet_type != 2) {
         throw new IOException("This is not a SSH_MSG_IGNORE packet! (" + packet_type + ")");
      }
   }

   public byte[] getPayload() {
      if (this.payload == null) {
         TypesWriter tw = new TypesWriter();
         tw.writeByte(2);
         if (this.data != null) {
            tw.writeString(this.data, 0, this.data.length);
         } else {
            tw.writeString("");
         }

         this.payload = tw.getBytes();
      }

      return this.payload;
   }
}

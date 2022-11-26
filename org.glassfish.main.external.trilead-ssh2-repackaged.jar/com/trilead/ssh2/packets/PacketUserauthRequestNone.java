package com.trilead.ssh2.packets;

import java.io.IOException;

public class PacketUserauthRequestNone {
   byte[] payload;
   String userName;
   String serviceName;

   public PacketUserauthRequestNone(String serviceName, String user) {
      this.serviceName = serviceName;
      this.userName = user;
   }

   public PacketUserauthRequestNone(byte[] payload, int off, int len) throws IOException {
      this.payload = new byte[len];
      System.arraycopy(payload, off, this.payload, 0, len);
      TypesReader tr = new TypesReader(payload, off, len);
      int packet_type = tr.readByte();
      if (packet_type != 50) {
         throw new IOException("This is not a SSH_MSG_USERAUTH_REQUEST! (" + packet_type + ")");
      } else {
         this.userName = tr.readString();
         this.serviceName = tr.readString();
         String method = tr.readString();
         if (!method.equals("none")) {
            throw new IOException("This is not a SSH_MSG_USERAUTH_REQUEST with type none!");
         } else if (tr.remain() != 0) {
            throw new IOException("Padding in SSH_MSG_USERAUTH_REQUEST packet!");
         }
      }
   }

   public byte[] getPayload() {
      if (this.payload == null) {
         TypesWriter tw = new TypesWriter();
         tw.writeByte(50);
         tw.writeString(this.userName);
         tw.writeString(this.serviceName);
         tw.writeString("none");
         this.payload = tw.getBytes();
      }

      return this.payload;
   }
}

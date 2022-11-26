package com.trilead.ssh2.packets;

import java.io.IOException;

public class PacketUserauthFailure {
   byte[] payload;
   String[] authThatCanContinue;
   boolean partialSuccess;

   public PacketUserauthFailure(String[] authThatCanContinue, boolean partialSuccess) {
      this.authThatCanContinue = authThatCanContinue;
      this.partialSuccess = partialSuccess;
   }

   public PacketUserauthFailure(byte[] payload, int off, int len) throws IOException {
      this.payload = new byte[len];
      System.arraycopy(payload, off, this.payload, 0, len);
      TypesReader tr = new TypesReader(payload, off, len);
      int packet_type = tr.readByte();
      if (packet_type != 51) {
         throw new IOException("This is not a SSH_MSG_USERAUTH_FAILURE! (" + packet_type + ")");
      } else {
         this.authThatCanContinue = tr.readNameList();
         this.partialSuccess = tr.readBoolean();
         if (tr.remain() != 0) {
            throw new IOException("Padding in SSH_MSG_USERAUTH_FAILURE packet!");
         }
      }
   }

   public String[] getAuthThatCanContinue() {
      return this.authThatCanContinue;
   }

   public boolean isPartialSuccess() {
      return this.partialSuccess;
   }
}

package com.trilead.ssh2.packets;

public class PacketUserauthInfoResponse {
   byte[] payload;
   String[] responses;

   public PacketUserauthInfoResponse(String[] responses) {
      this.responses = responses;
   }

   public byte[] getPayload() {
      if (this.payload == null) {
         TypesWriter tw = new TypesWriter();
         tw.writeByte(61);
         tw.writeUINT32(this.responses.length);

         for(int i = 0; i < this.responses.length; ++i) {
            tw.writeString(this.responses[i]);
         }

         this.payload = tw.getBytes();
      }

      return this.payload;
   }
}

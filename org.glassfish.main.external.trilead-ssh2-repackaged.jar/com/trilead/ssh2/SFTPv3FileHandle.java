package com.trilead.ssh2;

public class SFTPv3FileHandle {
   final SFTPv3Client client;
   final byte[] fileHandle;
   boolean isClosed = false;

   SFTPv3FileHandle(SFTPv3Client client, byte[] h) {
      this.client = client;
      this.fileHandle = h;
   }

   public SFTPv3Client getClient() {
      return this.client;
   }

   public boolean isClosed() {
      return this.isClosed;
   }
}

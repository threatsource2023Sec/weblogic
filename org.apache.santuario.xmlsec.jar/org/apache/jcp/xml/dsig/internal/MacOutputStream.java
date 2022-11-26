package org.apache.jcp.xml.dsig.internal;

import java.io.ByteArrayOutputStream;
import javax.crypto.Mac;

public class MacOutputStream extends ByteArrayOutputStream {
   private final Mac mac;

   public MacOutputStream(Mac mac) {
      this.mac = mac;
   }

   public void write(int arg0) {
      super.write(arg0);
      this.mac.update((byte)arg0);
   }

   public void write(byte[] arg0, int arg1, int arg2) {
      super.write(arg0, arg1, arg2);
      this.mac.update(arg0, arg1, arg2);
   }
}

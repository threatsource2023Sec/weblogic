package org.jboss.classfilewriter.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ByteArrayDataOutputStream extends DataOutputStream {
   private final ByteArrayOutputStream bytes;
   private final List sizes;

   public ByteArrayDataOutputStream(ByteArrayOutputStream bytes) {
      super(bytes);
      this.sizes = new ArrayList();
      this.bytes = bytes;
   }

   public ByteArrayDataOutputStream() {
      this(new ByteArrayOutputStream());
   }

   public LazySize writeSize() throws IOException {
      LazySizeImpl sv = new LazySizeImpl(this.written);
      this.sizes.add(sv);
      this.writeInt(0);
      return sv;
   }

   public byte[] getBytes() {
      byte[] data = this.bytes.toByteArray();
      Iterator var2 = this.sizes.iterator();

      while(var2.hasNext()) {
         LazySizeImpl i = (LazySizeImpl)var2.next();
         this.overwriteInt(data, i.position, i.value);
      }

      return data;
   }

   private void overwriteInt(byte[] bytecode, int offset, int value) {
      bytecode[offset] = (byte)(value >> 24);
      bytecode[offset + 1] = (byte)(value >> 16);
      bytecode[offset + 2] = (byte)(value >> 8);
      bytecode[offset + 3] = (byte)value;
   }

   private class LazySizeImpl implements LazySize {
      private final int position;
      private int value;

      public LazySizeImpl(int position) {
         this.position = position;
      }

      public void markEnd() {
         this.value = ByteArrayDataOutputStream.this.written - this.position - 4;
      }
   }
}

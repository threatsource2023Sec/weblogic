package com.oracle.buzzmessagebus.impl;

import com.oracle.buzzmessagebus.api.BuzzLogger;
import com.oracle.common.io.BufferSequence;
import com.oracle.common.io.BufferSequenceInputStream;
import com.oracle.common.net.exabus.EndPoint;
import java.io.IOException;

class BSIS {
   private final BuzzLogger l;
   private final EndPoint endPoint;
   private final BufferSequence bs;
   private final BufferSequenceInputStream is;

   BSIS(BuzzLogger l, EndPoint endPoint, BufferSequence bs) {
      this.l = l;
      this.endPoint = endPoint;
      this.bs = bs;
      this.is = new BufferSequenceInputStream(bs);
   }

   void close() {
      this.is.close();
   }

   void skipBytes(int n) {
      try {
         this.is.skipBytes(n);
      } catch (IOException var3) {
         this.fail(var3);
      }

   }

   byte readByte() {
      try {
         return this.is.readByte();
      } catch (IOException var2) {
         this.fail(var2);
         return 0;
      }
   }

   short readShort() {
      try {
         return this.is.readShort();
      } catch (IOException var2) {
         this.fail(var2);
         return 0;
      }
   }

   int readInt() {
      try {
         return this.is.readInt();
      } catch (IOException var2) {
         this.fail(var2);
         return 0;
      }
   }

   long readLong() {
      try {
         return this.is.readLong();
      } catch (IOException var2) {
         this.fail(var2);
         return 0L;
      }
   }

   private void fail(IOException e) {
      this.l.errorRecvInternalExReadingBufferSequenceInputStream(this.endPoint, this.bs, e);
      String msg = "SEVERE: INTERNAL ERROR: got exception reading from BufferSequenceInputStream";
      throw new RuntimeException("SEVERE: INTERNAL ERROR: got exception reading from BufferSequenceInputStream", e);
   }
}

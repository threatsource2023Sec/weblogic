package org.python.netty.channel.oio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.WritableByteChannel;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.Channel;
import org.python.netty.channel.FileRegion;
import org.python.netty.channel.RecvByteBufAllocator;

public abstract class OioByteStreamChannel extends AbstractOioByteChannel {
   private static final InputStream CLOSED_IN = new InputStream() {
      public int read() {
         return -1;
      }
   };
   private static final OutputStream CLOSED_OUT = new OutputStream() {
      public void write(int b) throws IOException {
         throw new ClosedChannelException();
      }
   };
   private InputStream is;
   private OutputStream os;
   private WritableByteChannel outChannel;

   protected OioByteStreamChannel(Channel parent) {
      super(parent);
   }

   protected final void activate(InputStream is, OutputStream os) {
      if (this.is != null) {
         throw new IllegalStateException("input was set already");
      } else if (this.os != null) {
         throw new IllegalStateException("output was set already");
      } else if (is == null) {
         throw new NullPointerException("is");
      } else if (os == null) {
         throw new NullPointerException("os");
      } else {
         this.is = is;
         this.os = os;
      }
   }

   public boolean isActive() {
      InputStream is = this.is;
      if (is != null && is != CLOSED_IN) {
         OutputStream os = this.os;
         return os != null && os != CLOSED_OUT;
      } else {
         return false;
      }
   }

   protected int available() {
      try {
         return this.is.available();
      } catch (IOException var2) {
         return 0;
      }
   }

   protected int doReadBytes(ByteBuf buf) throws Exception {
      RecvByteBufAllocator.Handle allocHandle = this.unsafe().recvBufAllocHandle();
      allocHandle.attemptedBytesRead(Math.max(1, Math.min(this.available(), buf.maxWritableBytes())));
      return buf.writeBytes(this.is, allocHandle.attemptedBytesRead());
   }

   protected void doWriteBytes(ByteBuf buf) throws Exception {
      OutputStream os = this.os;
      if (os == null) {
         throw new NotYetConnectedException();
      } else {
         buf.readBytes(os, buf.readableBytes());
      }
   }

   protected void doWriteFileRegion(FileRegion region) throws Exception {
      OutputStream os = this.os;
      if (os == null) {
         throw new NotYetConnectedException();
      } else {
         if (this.outChannel == null) {
            this.outChannel = Channels.newChannel(os);
         }

         long written = 0L;

         do {
            long localWritten = region.transferTo(this.outChannel, written);
            if (localWritten == -1L) {
               checkEOF(region);
               return;
            }

            written += localWritten;
         } while(written < region.count());

      }
   }

   private static void checkEOF(FileRegion region) throws IOException {
      if (region.transferred() < region.count()) {
         throw new EOFException("Expected to be able to write " + region.count() + " bytes, but only wrote " + region.transferred());
      }
   }

   protected void doClose() throws Exception {
      InputStream is = this.is;
      OutputStream os = this.os;
      this.is = CLOSED_IN;
      this.os = CLOSED_OUT;

      try {
         if (is != null) {
            is.close();
         }
      } finally {
         if (os != null) {
            os.close();
         }

      }

   }
}

package org.python.core.io;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import org.python.core.Py;
import org.python.modules.posix.PosixModule;

public class StreamIO extends RawIOBase {
   private ReadableByteChannel readChannel;
   private WritableByteChannel writeChannel;
   private InputStream inputStream;
   private OutputStream outputStream;
   private boolean closefd;

   public StreamIO(ReadableByteChannel readChannel, boolean closefd) {
      this.readChannel = readChannel;
      this.closefd = closefd;
   }

   public StreamIO(ReadableByteChannel readChannel) {
      this(readChannel, true);
   }

   public StreamIO(WritableByteChannel writeChannel, boolean closefd) {
      this.writeChannel = writeChannel;
      this.closefd = closefd;
   }

   public StreamIO(WritableByteChannel writeChannel) {
      this(writeChannel, true);
   }

   public StreamIO(InputStream inputStream, boolean closefd) {
      this(newChannel(inputStream), closefd);
      this.inputStream = inputStream;
   }

   public StreamIO(OutputStream outputStream, boolean closefd) {
      this(Channels.newChannel(outputStream), closefd);
      this.outputStream = outputStream;
   }

   public int readinto(ByteBuffer buf) {
      this.checkClosed();
      this.checkReadable();

      try {
         return this.readChannel.read(buf);
      } catch (IOException var3) {
         throw Py.IOError(var3);
      }
   }

   public int write(ByteBuffer buf) {
      this.checkClosed();
      this.checkWritable();

      try {
         return this.writeChannel.write(buf);
      } catch (IOException var3) {
         throw Py.IOError(var3);
      }
   }

   public void flush() {
      if (this.outputStream != null) {
         try {
            this.outputStream.flush();
         } catch (IOException var2) {
            throw Py.IOError(var2);
         }
      }
   }

   public void close() {
      if (!this.closed()) {
         if (this.closefd) {
            try {
               if (this.readChannel != null) {
                  this.readChannel.close();
                  if (this.writeChannel != null && this.readChannel != this.writeChannel) {
                     this.writeChannel.close();
                  }
               } else {
                  this.writeChannel.close();
               }
            } catch (IOException var2) {
               throw Py.IOError(var2);
            }
         }

         super.close();
      }
   }

   private static FileDescriptor getInputFileDescriptor(InputStream stream) throws IOException {
      if (stream == null) {
         return null;
      } else if (stream instanceof FileInputStream) {
         return ((FileInputStream)stream).getFD();
      } else if (stream instanceof FilterInputStream) {
         Field inField = null;

         FileDescriptor var2;
         try {
            inField = FilterInputStream.class.getDeclaredField("in");
            inField.setAccessible(true);
            var2 = getInputFileDescriptor((InputStream)inField.get(stream));
         } catch (Exception var6) {
            return null;
         } finally {
            if (inField != null && inField.isAccessible()) {
               inField.setAccessible(false);
            }

         }

         return var2;
      } else {
         return null;
      }
   }

   private static FileDescriptor getOutputFileDescriptor(OutputStream stream) throws IOException {
      if (stream == null) {
         return null;
      } else if (stream instanceof FileOutputStream) {
         return ((FileOutputStream)stream).getFD();
      } else if (stream instanceof FilterOutputStream) {
         Field outField = null;

         FileDescriptor var2;
         try {
            outField = FilterOutputStream.class.getDeclaredField("out");
            outField.setAccessible(true);
            var2 = getOutputFileDescriptor((OutputStream)outField.get(stream));
         } catch (Exception var6) {
            return null;
         } finally {
            if (outField != null && outField.isAccessible()) {
               outField.setAccessible(false);
            }

         }

         return var2;
      } else {
         return null;
      }
   }

   public boolean isatty() {
      this.checkClosed();

      FileDescriptor fd;
      try {
         if ((fd = getInputFileDescriptor(this.inputStream)) == null && (fd = getOutputFileDescriptor(this.outputStream)) == null) {
            return false;
         }
      } catch (IOException var3) {
         return false;
      }

      return PosixModule.getPOSIX().isatty(fd);
   }

   public boolean readable() {
      return this.readChannel != null;
   }

   public boolean writable() {
      return this.writeChannel != null;
   }

   public OutputStream asOutputStream() {
      if (this.writable()) {
         return this.outputStream == null ? Channels.newOutputStream(this.writeChannel) : this.outputStream;
      } else {
         return super.asOutputStream();
      }
   }

   public InputStream asInputStream() {
      if (this.readable()) {
         return this.inputStream == null ? Channels.newInputStream(this.readChannel) : this.inputStream;
      } else {
         return super.asInputStream();
      }
   }

   public Channel getChannel() {
      return (Channel)(this.readable() ? this.readChannel : this.writeChannel);
   }

   private static ReadableByteChannel newChannel(InputStream in) {
      return Channels.newChannel(in);
   }
}

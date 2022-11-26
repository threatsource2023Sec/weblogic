package org.python.core.io;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import org.python.core.BufferProtocol;
import org.python.core.Py;
import org.python.core.PyArray;
import org.python.core.PyBuffer;
import org.python.core.PyObject;
import org.python.core.PyString;

public abstract class TextIOBase extends IOBase {
   public static final int CHUNK_SIZE = 300;
   protected static final byte CR_BYTE = 13;
   protected BufferedIOBase bufferedIO;
   protected ByteBuffer readahead;
   protected StringBuilder builder;
   protected char[] interimBuilder;

   public TextIOBase(BufferedIOBase bufferedIO) {
      this.bufferedIO = bufferedIO;
      this.readahead = ByteBuffer.allocate(300);
      this.readahead.flip();
      this.builder = new StringBuilder(300);
      this.interimBuilder = new char[300];
   }

   public String read(int size) {
      this.unsupported("read");
      return null;
   }

   public String readall() {
      this.unsupported("readall");
      return null;
   }

   public String readline(int size) {
      this.unsupported("read");
      return null;
   }

   public int readinto(PyObject buf) {
      if (buf instanceof PyArray) {
         PyArray array = (PyArray)buf;
         String read = this.read(array.__len__());

         for(int i = 0; i < read.length(); ++i) {
            array.set(i, new PyString(read.charAt(i)));
         }

         return read.length();
      } else if (buf instanceof BufferProtocol) {
         PyBuffer view = ((BufferProtocol)buf).getBuffer(284);
         Throwable var3 = null;

         try {
            if (view.isReadonly()) {
               throw Py.TypeError("cannot read into read-only " + buf.getType().fastGetName());
            } else {
               String read = this.read(view.getLen());
               int n = read.length();

               int i;
               for(i = 0; i < n; ++i) {
                  view.storeAt((byte)read.charAt(i), i);
               }

               i = read.length();
               return i;
            }
         } catch (Throwable var15) {
            var3 = var15;
            throw var15;
         } finally {
            if (view != null) {
               if (var3 != null) {
                  try {
                     view.close();
                  } catch (Throwable var14) {
                     var3.addSuppressed(var14);
                  }
               } else {
                  view.close();
               }
            }

         }
      } else {
         throw Py.TypeError("argument 1 must be read-write buffer, not " + buf.getType().fastGetName());
      }
   }

   public int write(String buf) {
      this.unsupported("write");
      return -1;
   }

   public long truncate(long pos) {
      long initialPos = this.tell();
      this.flush();
      pos = this.bufferedIO.truncate(pos);
      if (initialPos > pos) {
         this.seek(initialPos);
      }

      return pos;
   }

   public void flush() {
      this.bufferedIO.flush();
   }

   public void close() {
      this.bufferedIO.close();
   }

   public long seek(long pos, int whence) {
      pos = this.bufferedIO.seek(pos, whence);
      this.clearReadahead();
      return pos;
   }

   public long tell() {
      return this.bufferedIO.tell() - (long)this.readahead.remaining();
   }

   public RawIOBase fileno() {
      return this.bufferedIO.fileno();
   }

   public boolean isatty() {
      return this.bufferedIO.isatty();
   }

   public boolean readable() {
      return this.bufferedIO.readable();
   }

   public boolean writable() {
      return this.bufferedIO.writable();
   }

   public boolean closed() {
      return this.bufferedIO.closed();
   }

   public InputStream asInputStream() {
      return this.bufferedIO.asInputStream();
   }

   public OutputStream asOutputStream() {
      return this.bufferedIO.asOutputStream();
   }

   public PyObject getNewlines() {
      return Py.None;
   }

   protected boolean atEOF() {
      return this.readahead.hasRemaining() ? false : this.readChunk() == 0;
   }

   protected int readChunk() {
      this.readahead.clear();
      if (this.readahead.remaining() > 300) {
         this.readahead.limit(this.readahead.position() + 300);
      }

      this.bufferedIO.read1(this.readahead);
      this.readahead.flip();
      return this.readahead.remaining();
   }

   protected int readChunk(int size) {
      if (size > 300) {
         this.readahead = ByteBuffer.allocate(size);
      } else {
         int size = 300;
         this.readahead.clear().limit(size);
      }

      this.bufferedIO.readinto(this.readahead);
      this.readahead.flip();
      return this.readahead.remaining();
   }

   protected void packReadahead() {
      if (this.readahead.capacity() > 300) {
         ByteBuffer old = this.readahead;
         this.readahead = ByteBuffer.allocate(300);
         this.readahead.put(old);
         this.readahead.flip();
      }

   }

   protected void clearReadahead() {
      this.readahead.clear().flip();
   }

   protected String drainBuilder() {
      String result = this.builder.toString();
      if (this.builder.capacity() > 300) {
         this.builder = new StringBuilder(300);
      } else {
         this.builder.setLength(0);
      }

      return result;
   }
}

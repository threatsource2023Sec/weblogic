package org.python.core.io;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jnr.constants.Constant;
import jnr.constants.platform.Errno;
import jnr.posix.util.FieldAccess;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.util.RelativeFile;
import org.python.modules.posix.PosixModule;

public class FileIO extends RawIOBase {
   private FileChannel fileChannel;
   private RandomAccessFile file;
   private FileOutputStream fileOutputStream;
   private boolean reading;
   private boolean writing;
   private boolean appending;
   private boolean plus;
   private boolean emulateAppend;

   public FileIO(String name, String mode) {
      this((PyString)Py.newUnicode(name), mode);
   }

   public FileIO(PyString name, String mode) {
      this.parseMode(mode);
      File absPath = new RelativeFile(Py.fileSystemDecode(name));

      try {
         if ((!this.appending || this.reading || this.plus) && (!this.writing || this.reading || this.plus)) {
            this.fromRandomAccessFile(absPath);
            this.emulateAppend = this.appending;
         } else {
            this.fromFileOutputStream(absPath);
         }
      } catch (FileNotFoundException var5) {
         if (absPath.isDirectory()) {
            throw Py.IOError(Errno.EISDIR, (PyObject)name);
         }

         if ((!this.writing || absPath.canWrite()) && !var5.getMessage().endsWith("(Permission denied)")) {
            throw Py.IOError(Errno.ENOENT, (PyObject)name);
         }

         throw Py.IOError(Errno.EACCES, (PyObject)name);
      }

      this.initPosition();
   }

   public FileIO(FileChannel fileChannel, String mode) {
      this.parseMode(mode);
      this.fileChannel = fileChannel;
      this.initPosition();
   }

   private void parseMode(String mode) {
      boolean rwa = false;

      for(int i = 0; i < mode.length(); ++i) {
         switch (mode.charAt(i)) {
            case '+':
               if (this.plus || !rwa) {
                  this.badMode();
               }

               this.writing = this.plus = true;
               break;
            case 'a':
               if (this.plus || rwa) {
                  this.badMode();
               }

               rwa = true;
               this.appending = this.writing = true;
               break;
            case 'r':
               if (this.plus || rwa) {
                  this.badMode();
               }

               rwa = true;
               this.reading = true;
               break;
            case 'w':
               if (this.plus || rwa) {
                  this.badMode();
               }

               rwa = true;
               this.writing = true;
               break;
            default:
               throw Py.ValueError("invalid mode: '" + mode + "'");
         }
      }

      if (!rwa) {
         this.badMode();
      }

   }

   private void fromRandomAccessFile(File absPath) throws FileNotFoundException {
      String rafMode = "r" + (this.writing ? "w" : "");
      if (this.plus && this.reading && !absPath.isFile()) {
         this.writing = false;
         throw new FileNotFoundException("");
      } else {
         this.file = new RandomAccessFile(absPath, rafMode);
         this.fileChannel = this.file.getChannel();
      }
   }

   private void fromFileOutputStream(File absPath) throws FileNotFoundException {
      this.fileOutputStream = new FileOutputStream(absPath, this.appending);
      this.fileChannel = this.fileOutputStream.getChannel();
   }

   private void badMode() {
      throw Py.ValueError("Must have exactly one of read/write/append mode");
   }

   private void initPosition() {
      if (this.appending) {
         this.seek(0L, 2);
      } else if (this.writing && !this.reading) {
         try {
            this.fileChannel.truncate(0L);
         } catch (IOException var2) {
         }
      }

   }

   public boolean isatty() {
      this.checkClosed();
      if (this.file != null && this.fileOutputStream != null) {
         try {
            return PosixModule.getPOSIX().isatty(this.file != null ? this.file.getFD() : this.fileOutputStream.getFD());
         } catch (IOException var2) {
            return false;
         }
      } else {
         return false;
      }
   }

   public int readinto(ByteBuffer buf) {
      this.checkClosed();
      this.checkReadable();

      try {
         int n = this.fileChannel.read(buf);
         return n > 0 ? n : 0;
      } catch (IOException var3) {
         throw Py.IOError(var3);
      }
   }

   public long readinto(ByteBuffer[] bufs) {
      this.checkClosed();
      this.checkReadable();

      try {
         long n = this.fileChannel.read(bufs);
         return n > 0L ? n : 0L;
      } catch (IOException var5) {
         throw Py.IOError(var5);
      }
   }

   public ByteBuffer readall() {
      this.checkClosed();
      this.checkReadable();

      long toRead;
      try {
         toRead = Math.max(0L, this.fileChannel.size() - this.fileChannel.position());
      } catch (IOException var4) {
         throw Py.IOError(var4);
      }

      if (toRead > 2147483647L) {
         throw Py.OverflowError("requested number of bytes is more than a Python string can hold");
      } else if (toRead == 0L) {
         return this.readallInChunks();
      } else {
         ByteBuffer all = ByteBuffer.allocate((int)toRead);
         this.readinto(all);
         all.flip();
         return all;
      }
   }

   private ByteBuffer readallInChunks() {
      List chunks = new ArrayList();
      int MAX_CHUNK_SIZE = true;
      int length = 0;

      ByteBuffer all;
      int chunkSize;
      do {
         all = ByteBuffer.allocate(8192);
         this.readinto(all);
         chunkSize = all.position();
         length += chunkSize;
         all.flip();
         chunks.add(all);
      } while(chunkSize >= 8192);

      if (chunks.size() == 1) {
         return (ByteBuffer)chunks.get(0);
      } else {
         all = ByteBuffer.allocate(length);
         Iterator var7 = chunks.iterator();

         while(var7.hasNext()) {
            ByteBuffer chunk = (ByteBuffer)var7.next();
            all.put(chunk);
         }

         all.flip();
         return all;
      }
   }

   public int write(ByteBuffer buf) {
      this.checkClosed();
      this.checkWritable();

      try {
         return this.emulateAppend ? this.appendFromByteBuffer(buf) : this.fileChannel.write(buf);
      } catch (IOException var3) {
         throw Py.IOError(var3);
      }
   }

   private int appendFromByteBuffer(ByteBuffer buf) throws IOException {
      int written = this.fileChannel.write(buf, this.fileChannel.position());
      if (written > 0) {
         this.fileChannel.position(this.fileChannel.position() + (long)written);
      }

      return written;
   }

   public long write(ByteBuffer[] bufs) {
      this.checkClosed();
      this.checkWritable();

      try {
         return !this.emulateAppend ? this.fileChannel.write(bufs) : this.writeAppend(bufs);
      } catch (IOException var3) {
         throw Py.IOError(var3);
      }
   }

   private long writeAppend(ByteBuffer[] bufs) throws IOException {
      long count = 0L;
      ByteBuffer[] var4 = bufs;
      int var5 = bufs.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ByteBuffer buf = var4[var6];
         if (buf.hasRemaining()) {
            int bufCount = this.appendFromByteBuffer(buf);
            if (bufCount == 0) {
               break;
            }

            count += (long)bufCount;
         }
      }

      return count;
   }

   public long seek(long pos, int whence) {
      this.checkClosed();

      try {
         switch (whence) {
            case 0:
               break;
            case 1:
               pos += this.fileChannel.position();
               break;
            case 2:
               pos += this.fileChannel.size();
               break;
            default:
               throw Py.IOError((Constant)Errno.EINVAL);
         }

         this.fileChannel.position(pos);
         return pos;
      } catch (IOException var5) {
         throw Py.IOError(var5);
      }
   }

   public long tell() {
      this.checkClosed();

      try {
         return this.fileChannel.position();
      } catch (IOException var2) {
         throw Py.IOError(var2);
      }
   }

   public long truncate(long size) {
      this.checkClosed();
      this.checkWritable();

      try {
         long oldPosition = this.fileChannel.position();
         this.fileChannel.truncate(size);
         this.fileChannel.position(oldPosition);
         return size;
      } catch (IOException var6) {
         throw Py.IOError(var6);
      }
   }

   public void close() {
      if (!this.closed()) {
         try {
            this.fileChannel.close();
         } catch (IOException var2) {
            throw Py.IOError(var2);
         }

         super.close();
      }
   }

   public OutputStream asOutputStream() {
      return this.writing ? Channels.newOutputStream(this.fileChannel) : super.asOutputStream();
   }

   public InputStream asInputStream() {
      return this.readable() ? Channels.newInputStream(this.fileChannel) : super.asInputStream();
   }

   public boolean readable() {
      return this.reading || this.plus;
   }

   public boolean writable() {
      return this.writing;
   }

   public FileChannel getChannel() {
      return this.fileChannel;
   }

   public FileDescriptor getFD() {
      if (this.file != null) {
         try {
            return this.file.getFD();
         } catch (IOException var2) {
            throw Py.OSError(var2);
         }
      } else if (this.fileOutputStream != null) {
         try {
            return this.fileOutputStream.getFD();
         } catch (IOException var3) {
            throw Py.OSError(var3);
         }
      } else {
         throw Py.OSError((Constant)Errno.EBADF);
      }
   }

   public PyObject __int__() {
      int intFD = -1;

      try {
         Field fdField = FieldAccess.getProtectedField(FileDescriptor.class, "fd");
         intFD = fdField.getInt(this.getFD());
      } catch (SecurityException var3) {
      } catch (IllegalArgumentException var4) {
      } catch (IllegalAccessException var5) {
      }

      return Py.newInteger(intFD);
   }

   public PyObject __add__(PyObject otherObj) {
      return this.__int__().__add__(otherObj);
   }

   private static class os {
      public static final int SEEK_SET = 0;
      public static final int SEEK_CUR = 1;
      public static final int SEEK_END = 2;
   }
}

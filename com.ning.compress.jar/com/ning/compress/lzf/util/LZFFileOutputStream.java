package com.ning.compress.lzf.util;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.ChunkEncoder;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.FileChannel.MapMode;

public class LZFFileOutputStream extends FileOutputStream implements WritableByteChannel {
   private static final int OUTPUT_BUFFER_SIZE = 65535;
   private final ChunkEncoder _encoder;
   private final BufferRecycler _recycler;
   protected byte[] _outputBuffer;
   protected int _position;
   protected boolean _cfgFinishBlockOnFlush;
   protected boolean _outputStreamClosed;
   private final Wrapper _wrapper;

   public LZFFileOutputStream(File file) throws FileNotFoundException {
      this(ChunkEncoderFactory.optimalInstance(65535), file);
   }

   public LZFFileOutputStream(File file, boolean append) throws FileNotFoundException {
      this(ChunkEncoderFactory.optimalInstance(65535), file, append);
   }

   public LZFFileOutputStream(FileDescriptor fdObj) {
      this(ChunkEncoderFactory.optimalInstance(65535), fdObj);
   }

   public LZFFileOutputStream(String name) throws FileNotFoundException {
      this(ChunkEncoderFactory.optimalInstance(65535), name);
   }

   public LZFFileOutputStream(String name, boolean append) throws FileNotFoundException {
      this(ChunkEncoderFactory.optimalInstance(65535), name, append);
   }

   public LZFFileOutputStream(ChunkEncoder encoder, File file) throws FileNotFoundException {
      this(encoder, file, encoder.getBufferRecycler());
   }

   public LZFFileOutputStream(ChunkEncoder encoder, File file, boolean append) throws FileNotFoundException {
      this(encoder, file, append, encoder.getBufferRecycler());
   }

   public LZFFileOutputStream(ChunkEncoder encoder, FileDescriptor fdObj) {
      this(encoder, fdObj, encoder.getBufferRecycler());
   }

   public LZFFileOutputStream(ChunkEncoder encoder, String name) throws FileNotFoundException {
      this(encoder, name, encoder.getBufferRecycler());
   }

   public LZFFileOutputStream(ChunkEncoder encoder, String name, boolean append) throws FileNotFoundException {
      this(encoder, name, append, encoder.getBufferRecycler());
   }

   public LZFFileOutputStream(ChunkEncoder encoder, File file, BufferRecycler bufferRecycler) throws FileNotFoundException {
      super(file);
      this._position = 0;
      this._cfgFinishBlockOnFlush = true;
      this._encoder = encoder;
      if (bufferRecycler == null) {
         bufferRecycler = encoder.getBufferRecycler();
      }

      this._recycler = bufferRecycler;
      this._outputBuffer = bufferRecycler.allocOutputBuffer(65535);
      this._wrapper = new Wrapper();
   }

   public LZFFileOutputStream(ChunkEncoder encoder, File file, boolean append, BufferRecycler bufferRecycler) throws FileNotFoundException {
      super(file, append);
      this._position = 0;
      this._cfgFinishBlockOnFlush = true;
      this._encoder = encoder;
      this._recycler = bufferRecycler;
      this._outputBuffer = bufferRecycler.allocOutputBuffer(65535);
      this._wrapper = new Wrapper();
   }

   public LZFFileOutputStream(ChunkEncoder encoder, FileDescriptor fdObj, BufferRecycler bufferRecycler) {
      super(fdObj);
      this._position = 0;
      this._cfgFinishBlockOnFlush = true;
      this._encoder = encoder;
      this._recycler = bufferRecycler;
      this._outputBuffer = bufferRecycler.allocOutputBuffer(65535);
      this._wrapper = new Wrapper();
   }

   public LZFFileOutputStream(ChunkEncoder encoder, String name, BufferRecycler bufferRecycler) throws FileNotFoundException {
      super(name);
      this._position = 0;
      this._cfgFinishBlockOnFlush = true;
      this._encoder = encoder;
      this._recycler = bufferRecycler;
      this._outputBuffer = bufferRecycler.allocOutputBuffer(65535);
      this._wrapper = new Wrapper();
   }

   public LZFFileOutputStream(ChunkEncoder encoder, String name, boolean append, BufferRecycler bufferRecycler) throws FileNotFoundException {
      super(name, append);
      this._position = 0;
      this._cfgFinishBlockOnFlush = true;
      this._encoder = encoder;
      this._recycler = bufferRecycler;
      this._outputBuffer = bufferRecycler.allocOutputBuffer(65535);
      this._wrapper = new Wrapper();
   }

   public LZFFileOutputStream setFinishBlockOnFlush(boolean b) {
      this._cfgFinishBlockOnFlush = b;
      return this;
   }

   public boolean isOpen() {
      return !this._outputStreamClosed;
   }

   public void close() throws IOException {
      if (!this._outputStreamClosed) {
         if (this._position > 0) {
            this.writeCompressedBlock();
         }

         super.flush();
         super.close();
         this._outputStreamClosed = true;
         this._encoder.close();
         byte[] buf = this._outputBuffer;
         if (buf != null) {
            this._outputBuffer = null;
            this._recycler.releaseOutputBuffer(buf);
         }
      }

   }

   public void flush() throws IOException {
      this.checkNotClosed();
      if (this._cfgFinishBlockOnFlush && this._position > 0) {
         this.writeCompressedBlock();
      }

      super.flush();
   }

   public void write(byte[] b) throws IOException {
      this.write(b, 0, b.length);
   }

   public void write(byte[] buffer, int offset, int length) throws IOException {
      this.checkNotClosed();

      int BUFFER_LEN;
      for(BUFFER_LEN = this._outputBuffer.length; this._position == 0 && length >= BUFFER_LEN; length -= BUFFER_LEN) {
         this._encoder.encodeAndWriteChunk(buffer, offset, BUFFER_LEN, this._wrapper);
         offset += BUFFER_LEN;
      }

      int free = BUFFER_LEN - this._position;
      if (free > length) {
         System.arraycopy(buffer, offset, this._outputBuffer, this._position, length);
         this._position += length;
      } else {
         System.arraycopy(buffer, offset, this._outputBuffer, this._position, free);
         offset += free;
         length -= free;
         this._position += free;
         this.writeCompressedBlock();

         while(length >= BUFFER_LEN) {
            this._encoder.encodeAndWriteChunk(buffer, offset, BUFFER_LEN, this._wrapper);
            offset += BUFFER_LEN;
            length -= BUFFER_LEN;
         }

         if (length > 0) {
            System.arraycopy(buffer, offset, this._outputBuffer, 0, length);
         }

         this._position = length;
      }
   }

   public void write(int b) throws IOException {
      this.checkNotClosed();
      if (this._position >= this._outputBuffer.length) {
         this.writeCompressedBlock();
      }

      this._outputBuffer[this._position++] = (byte)b;
   }

   public void write(InputStream in) throws IOException {
      this.writeCompressedBlock();

      int read;
      while((read = in.read(this._outputBuffer)) >= 0) {
         this._position = read;
         this.writeCompressedBlock();
      }

   }

   public synchronized int write(ByteBuffer src) throws IOException {
      int r = src.remaining();
      if (r <= 0) {
         return r;
      } else {
         this.writeCompressedBlock();
         if (src.hasArray()) {
            this.write(src.array(), src.arrayOffset(), src.limit() - src.arrayOffset());
         } else {
            while(src.hasRemaining()) {
               int toRead = Math.min(src.remaining(), this._outputBuffer.length);
               src.get(this._outputBuffer, 0, toRead);
               this._position = toRead;
               this.writeCompressedBlock();
            }
         }

         return r;
      }
   }

   public void write(FileChannel in) throws IOException {
      MappedByteBuffer src = in.map(MapMode.READ_ONLY, 0L, in.size());
      this.write((ByteBuffer)src);
   }

   public boolean getFinishBlockOnFlush() {
      return this._cfgFinishBlockOnFlush;
   }

   public LZFFileOutputStream finishBlock() throws IOException {
      this.checkNotClosed();
      if (this._position > 0) {
         this.writeCompressedBlock();
      }

      return this;
   }

   protected void writeCompressedBlock() throws IOException {
      int left = this._position;
      this._position = 0;

      int chunkLen;
      for(int offset = 0; left > 0; left -= chunkLen) {
         chunkLen = Math.min(65535, left);
         this._encoder.encodeAndWriteChunk(this._outputBuffer, offset, chunkLen, this._wrapper);
         offset += chunkLen;
      }

   }

   protected void rawWrite(byte[] buffer, int offset, int length) throws IOException {
      super.write(buffer, offset, length);
   }

   protected void checkNotClosed() throws IOException {
      if (this._outputStreamClosed) {
         throw new IOException(this.getClass().getName() + " already closed");
      }
   }

   private final class Wrapper extends OutputStream {
      private Wrapper() {
      }

      public void write(int arg0) throws IOException {
         throw new UnsupportedOperationException();
      }

      public void write(byte[] buffer, int offset, int length) throws IOException {
         LZFFileOutputStream.this.rawWrite(buffer, offset, length);
      }

      // $FF: synthetic method
      Wrapper(Object x1) {
         this();
      }
   }
}

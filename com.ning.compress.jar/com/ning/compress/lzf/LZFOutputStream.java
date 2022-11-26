package com.ning.compress.lzf;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.util.ChunkEncoderFactory;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.FileChannel.MapMode;

public class LZFOutputStream extends FilterOutputStream implements WritableByteChannel {
   private static final int DEFAULT_OUTPUT_BUFFER_SIZE = 65535;
   private final ChunkEncoder _encoder;
   private final BufferRecycler _recycler;
   protected byte[] _outputBuffer;
   protected int _position;
   protected boolean _cfgFinishBlockOnFlush;
   protected boolean _outputStreamClosed;

   public LZFOutputStream(OutputStream outputStream) {
      this(ChunkEncoderFactory.optimalInstance(65535), outputStream);
   }

   public LZFOutputStream(ChunkEncoder encoder, OutputStream outputStream) {
      this(encoder, outputStream, 65535, encoder._recycler);
   }

   public LZFOutputStream(OutputStream outputStream, BufferRecycler bufferRecycler) {
      this(ChunkEncoderFactory.optimalInstance(bufferRecycler), outputStream, bufferRecycler);
   }

   public LZFOutputStream(ChunkEncoder encoder, OutputStream outputStream, BufferRecycler bufferRecycler) {
      this(encoder, outputStream, 65535, bufferRecycler);
   }

   public LZFOutputStream(ChunkEncoder encoder, OutputStream outputStream, int bufferSize, BufferRecycler bufferRecycler) {
      super(outputStream);
      this._position = 0;
      this._cfgFinishBlockOnFlush = true;
      this._encoder = encoder;
      if (bufferRecycler == null) {
         bufferRecycler = this._encoder._recycler;
      }

      this._recycler = bufferRecycler;
      this._outputBuffer = bufferRecycler.allocOutputBuffer(bufferSize);
      this._outputStreamClosed = false;
   }

   public LZFOutputStream setFinishBlockOnFlush(boolean b) {
      this._cfgFinishBlockOnFlush = b;
      return this;
   }

   public void write(int singleByte) throws IOException {
      this.checkNotClosed();
      if (this._position >= this._outputBuffer.length) {
         this.writeCompressedBlock();
      }

      this._outputBuffer[this._position++] = (byte)singleByte;
   }

   public void write(byte[] buffer, int offset, int length) throws IOException {
      this.checkNotClosed();

      int BUFFER_LEN;
      for(BUFFER_LEN = this._outputBuffer.length; this._position == 0 && length >= BUFFER_LEN; length -= BUFFER_LEN) {
         this._encoder.encodeAndWriteChunk(buffer, offset, BUFFER_LEN, this.out);
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
            this._encoder.encodeAndWriteChunk(buffer, offset, BUFFER_LEN, this.out);
            offset += BUFFER_LEN;
            length -= BUFFER_LEN;
         }

         if (length > 0) {
            System.arraycopy(buffer, offset, this._outputBuffer, 0, length);
         }

         this._position = length;
      }
   }

   public void write(InputStream in) throws IOException {
      this.writeCompressedBlock();

      int read;
      while((read = in.read(this._outputBuffer)) >= 0) {
         this._position = read;
         this.writeCompressedBlock();
      }

   }

   public void write(FileChannel in) throws IOException {
      MappedByteBuffer src = in.map(MapMode.READ_ONLY, 0L, in.size());
      this.write((ByteBuffer)src);
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

   public void flush() throws IOException {
      this.checkNotClosed();
      if (this._cfgFinishBlockOnFlush && this._position > 0) {
         this.writeCompressedBlock();
      }

      super.flush();
   }

   public boolean isOpen() {
      return !this._outputStreamClosed;
   }

   public void close() throws IOException {
      if (!this._outputStreamClosed) {
         if (this._position > 0) {
            this.writeCompressedBlock();
         }

         super.close();
         this._encoder.close();
         this._outputStreamClosed = true;
         byte[] buf = this._outputBuffer;
         if (buf != null) {
            this._outputBuffer = null;
            this._recycler.releaseOutputBuffer(buf);
         }
      }

   }

   public OutputStream getUnderlyingOutputStream() {
      return this.out;
   }

   public boolean getFinishBlockOnFlush() {
      return this._cfgFinishBlockOnFlush;
   }

   public LZFOutputStream finishBlock() throws IOException {
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
         this._encoder.encodeAndWriteChunk(this._outputBuffer, offset, chunkLen, this.out);
         offset += chunkLen;
      }

   }

   protected void checkNotClosed() throws IOException {
      if (this._outputStreamClosed) {
         throw new IOException(this.getClass().getName() + " already closed");
      }
   }
}

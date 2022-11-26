package org.python.core.io;

import java.nio.ByteBuffer;

public class BufferedRandom extends BufferedIOMixin {
   protected BufferedIOBase reader;
   protected BufferedIOBase writer;

   public BufferedRandom(RawIOBase rawIO, int bufferSize) {
      super(rawIO, bufferSize);
      this.initChildBuffers();
   }

   protected void initChildBuffers() {
      this.reader = new BufferedReader(this.rawIO, this.bufferSize);
      this.writer = new BufferedWriter(this.rawIO, this.bufferSize);
   }

   public long seek(long pos, int whence) {
      this.flush();
      pos = this.writer.seek(pos, whence);
      this.reader.clear();
      return pos;
   }

   public long tell() {
      return this.writer.buffered() ? this.writer.tell() : this.reader.tell();
   }

   public ByteBuffer read(int size) {
      this.flush();
      return this.reader.read(size);
   }

   public ByteBuffer readall() {
      this.flush();
      return this.reader.readall();
   }

   public int readinto(ByteBuffer bytes) {
      this.flush();
      return this.reader.readinto(bytes);
   }

   public int write(ByteBuffer bytes) {
      if (this.reader.buffered()) {
         this.reader.clear();
      }

      return this.writer.write(bytes);
   }

   public ByteBuffer peek(int size) {
      this.flush();
      return this.reader.peek(size);
   }

   public int read1(ByteBuffer bytes) {
      this.flush();
      return this.reader.read1(bytes);
   }

   public void flush() {
      this.writer.flush();
   }
}

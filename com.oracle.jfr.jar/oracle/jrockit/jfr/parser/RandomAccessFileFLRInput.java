package oracle.jrockit.jfr.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

class RandomAccessFileFLRInput implements FLRInput {
   private final RandomAccessFile file;

   public RandomAccessFileFLRInput(String file) throws FileNotFoundException {
      this(new File(file));
   }

   public RandomAccessFileFLRInput(File file) throws FileNotFoundException {
      this(new RandomAccessFile(file, "r"));
   }

   public RandomAccessFileFLRInput(RandomAccessFile file) {
      this.file = file;
   }

   public byte get() throws IOException {
      return this.file.readByte();
   }

   public void get(byte[] dst, int offset, int length) throws IOException {
      this.file.readFully(dst, offset, length);
   }

   public void get(byte[] dst) throws IOException {
      this.file.readFully(dst);
   }

   public char getChar() throws IOException {
      return this.file.readChar();
   }

   public double getDouble() throws IOException {
      return this.file.readDouble();
   }

   public float getFloat() throws IOException {
      return this.file.readFloat();
   }

   public int getInt() throws IOException {
      return this.file.readInt();
   }

   public long getLong() throws IOException {
      return this.file.readLong();
   }

   public short getShort() throws IOException {
      return this.file.readShort();
   }

   public long position() throws IOException {
      return this.file.getFilePointer();
   }

   public void position(long newPosition) throws IOException {
      this.file.seek(newPosition);
   }

   public long size() throws IOException {
      return this.file.length();
   }

   public void close() throws IOException {
      this.file.close();
   }
}

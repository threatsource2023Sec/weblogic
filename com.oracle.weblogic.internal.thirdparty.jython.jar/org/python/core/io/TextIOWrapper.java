package org.python.core.io;

import java.nio.ByteBuffer;
import java.util.regex.Pattern;

public class TextIOWrapper extends BinaryIOWrapper {
   private static final Pattern LF_PATTERN = Pattern.compile("\n");
   private String newline = System.getProperty("line.separator");
   private boolean newlineIsLF;

   public TextIOWrapper(BufferedIOBase bufferedIO) {
      super(bufferedIO);
      this.newlineIsLF = this.newline.equals("\n");
   }

   public String read(int size) {
      if (this.newlineIsLF) {
         return super.read(size);
      } else if (size < 0) {
         return this.readall();
      } else {
         char[] builderArray = new char[size];
         int builderPos = 0;

         int readaheadPos;
         do {
            byte[] readaheadArray = this.readahead.array();

            char next;
            for(readaheadPos = this.readahead.position(); readaheadPos < this.readahead.limit() && builderPos < size; builderArray[builderPos++] = next) {
               next = (char)(readaheadArray[readaheadPos++] & 255);
               if (next == '\r') {
                  if (readaheadPos == this.readahead.limit()) {
                     if (this.readChunk() == 0) {
                        builderArray[builderPos++] = next;
                        return new String(builderArray, 0, builderPos);
                     }

                     readaheadArray = this.readahead.array();
                     readaheadPos = this.readahead.position();
                  }

                  if (readaheadArray[readaheadPos] == 10) {
                     next = '\n';
                     ++readaheadPos;
                  }
               }
            }
         } while(builderPos < size && this.readChunk(size - builderPos) > 0);

         if (this.readahead.hasRemaining()) {
            this.readahead.position(readaheadPos);
         }

         this.packReadahead();
         return new String(builderArray, 0, builderPos);
      }
   }

   public String readall() {
      if (this.newlineIsLF) {
         return super.readall();
      } else {
         ByteBuffer remaining = this.bufferedIO.readall();
         int length = 0;
         if (this.readahead.hasRemaining() && this.readahead.get(this.readahead.limit() - 1) == 13 && remaining.hasRemaining() && remaining.get(remaining.position()) == 10) {
            --length;
         }

         char[] all = new char[this.readahead.remaining() + remaining.remaining()];
         length += this.readLoop(this.readahead.array(), this.readahead.position(), all, 0, this.readahead.remaining());
         this.readahead.position(this.readahead.limit());
         length += this.readLoop(remaining.array(), remaining.position(), all, length, remaining.remaining());
         return new String(all, 0, length);
      }
   }

   private int readLoop(byte[] src, int srcPos, char[] dest, int destPos, int length) {
      int destStartPos = destPos;
      int srcEndPos = srcPos + length;

      while(true) {
         while(srcPos < srcEndPos) {
            char next = (char)(src[srcPos++] & 255);
            if (next == '\r') {
               if (srcPos == srcEndPos) {
                  dest[destPos++] = next;
                  continue;
               }

               if (src[srcPos] == 10) {
                  next = '\n';
                  ++srcPos;
               }
            }

            dest[destPos++] = next;
         }

         return destPos - destStartPos;
      }
   }

   public String readline(int size) {
      if (this.newlineIsLF) {
         return super.readline(size);
      } else {
         int readaheadPos;
         do {
            byte[] readaheadArray = this.readahead.array();
            readaheadPos = this.readahead.position();
            int interimBuilderPos = 0;

            while(readaheadPos < this.readahead.limit() && (size < 0 || this.builder.length() + interimBuilderPos < size)) {
               char next = (char)(readaheadArray[readaheadPos++] & 255);
               this.interimBuilder[interimBuilderPos++] = next;
               if (next == '\r') {
                  boolean flushInterimBuilder = false;
                  if (readaheadPos == this.readahead.limit()) {
                     if (this.readChunk() == 0) {
                        this.builder.append(this.interimBuilder, 0, interimBuilderPos);
                        return this.drainBuilder();
                     }

                     readaheadArray = this.readahead.array();
                     readaheadPos = this.readahead.position();
                     flushInterimBuilder = true;
                  }

                  if (readaheadArray[readaheadPos] == 10) {
                     ++readaheadPos;
                     int var10001 = interimBuilderPos - 1;
                     next = '\n';
                     this.interimBuilder[var10001] = '\n';
                  }

                  if (flushInterimBuilder) {
                     this.builder.append(this.interimBuilder, 0, interimBuilderPos);
                     interimBuilderPos = 0;
                  }
               }

               if (next == '\n') {
                  this.builder.append(this.interimBuilder, 0, interimBuilderPos);
                  this.readahead.position(readaheadPos);
                  return this.drainBuilder();
               }
            }

            this.builder.append(this.interimBuilder, 0, interimBuilderPos);
         } while((size < 0 || this.builder.length() < size) && this.readChunk() > 0);

         if (this.readahead.hasRemaining()) {
            this.readahead.position(readaheadPos);
         }

         return this.drainBuilder();
      }
   }

   public int write(String buf) {
      if (!this.newlineIsLF) {
         buf = LF_PATTERN.matcher(buf).replaceAll(this.newline);
      }

      return super.write(buf);
   }
}

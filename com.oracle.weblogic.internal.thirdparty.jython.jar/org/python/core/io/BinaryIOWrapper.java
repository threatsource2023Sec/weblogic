package org.python.core.io;

import java.nio.ByteBuffer;
import org.python.core.util.StringUtil;

public class BinaryIOWrapper extends TextIOBase {
   public BinaryIOWrapper(BufferedIOBase bufferedIO) {
      super(bufferedIO);
   }

   public String read(int size) {
      if (size < 0) {
         return this.readall();
      } else if (!this.readahead.hasRemaining()) {
         return StringUtil.fromBytes(this.bufferedIO.read(size));
      } else {
         ByteBuffer data = ByteBuffer.allocate(size);
         if (this.readahead.remaining() >= size) {
            int readaheadLimit = this.readahead.limit();
            this.readahead.limit(this.readahead.position() + size);
            data.put(this.readahead);
            this.readahead.limit(readaheadLimit);
            data.flip();
            return StringUtil.fromBytes(data);
         } else {
            data.put(this.readahead);
            this.clearReadahead();
            this.bufferedIO.readinto(data);
            data.flip();
            return StringUtil.fromBytes(data);
         }
      }
   }

   public String readall() {
      if (!this.readahead.hasRemaining()) {
         return StringUtil.fromBytes(this.bufferedIO.readall());
      } else {
         ByteBuffer remaining = this.bufferedIO.readall();
         ByteBuffer all = ByteBuffer.allocate(this.readahead.remaining() + remaining.remaining());
         all.put(this.readahead);
         this.clearReadahead();
         all.put(remaining);
         all.flip();
         return StringUtil.fromBytes(all);
      }
   }

   public String readline(int size) {
      int readaheadPos;
      do {
         byte[] readaheadArray = this.readahead.array();
         readaheadPos = this.readahead.position();
         int interimBuilderPos = 0;

         while(readaheadPos < this.readahead.limit() && (size < 0 || this.builder.length() + interimBuilderPos < size)) {
            char next = (char)(readaheadArray[readaheadPos++] & 255);
            this.interimBuilder[interimBuilderPos++] = next;
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

   public int write(String buf) {
      if (this.readahead.hasRemaining()) {
         this.clearReadahead();
      }

      return this.bufferedIO.write(ByteBuffer.wrap(StringUtil.toBytes(buf)));
   }
}

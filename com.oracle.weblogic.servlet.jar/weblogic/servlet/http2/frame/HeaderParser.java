package weblogic.servlet.http2.frame;

import java.nio.ByteBuffer;
import weblogic.servlet.http2.hpack.HeaderListener;
import weblogic.servlet.http2.hpack.HpackDecoder;
import weblogic.servlet.http2.hpack.HpackException;

public class HeaderParser {
   static final int DEFAULT_HEADER_READ_BUFFER_SIZE = 1024;
   private final HpackDecoder hpackDecoder;
   private ByteBuffer headersBuffer = ByteBuffer.allocate(1024);
   private boolean endOfStream = false;
   private HeaderListener hs;

   public HeaderParser(HpackDecoder hpackDecoder) {
      this.hpackDecoder = hpackDecoder;
   }

   public void parse(ByteBuffer buffer) throws HpackException {
      if (buffer != null && buffer.hasRemaining()) {
         if (this.hs != null) {
            while(buffer.remaining() > 0) {
               if (this.headersBuffer.remaining() == 0) {
                  this.expand(this.headersBuffer.capacity() * 2);
               }

               int count = Math.min(this.headersBuffer.remaining(), buffer.remaining());
               int oldLimit = buffer.limit();
               buffer.limit(buffer.position() + count);
               this.headersBuffer.put(buffer);
               buffer.limit(oldLimit);
               this.headersBuffer.flip();
               this.hpackDecoder.decode(this.headersBuffer, this.hs);
               this.headersBuffer.compact();
            }

         }
      }
   }

   private ByteBuffer expand(int newSize) {
      ByteBuffer out = ByteBuffer.allocate(newSize);
      this.headersBuffer.flip();
      out.put(this.headersBuffer);
      return out;
   }

   public boolean allDataProcessed() {
      return this.headersBuffer.position() == 0;
   }

   public void reset() {
      if (this.headersBuffer.capacity() > 1024) {
         this.headersBuffer = ByteBuffer.allocate(1024);
      }

      this.hs = null;
      this.setEndOfStream(false);
   }

   public boolean isEndOfStream() {
      return this.endOfStream;
   }

   public void setEndOfStream(boolean endOfStream) {
      this.endOfStream = endOfStream;
   }

   public HeaderListener getHeaderListener() {
      return this.hs;
   }

   public void setHeaderListener(HeaderListener hs) {
      this.hs = hs;
   }
}

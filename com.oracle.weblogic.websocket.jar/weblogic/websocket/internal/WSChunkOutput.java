package weblogic.websocket.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import weblogic.utils.io.Chunk;

public class WSChunkOutput {
   private static final Random rand = new Random(System.currentTimeMillis());
   private OutputStream outStream;
   private Chunk head;
   private Chunk tail;
   protected int count;
   private int payloadOffset = 2;
   private byte[] maskBytes = null;
   private boolean mask = false;
   private boolean fragment = false;

   public WSChunkOutput(OutputStream os) {
      this.outStream = os;
      this.head = this.tail = Chunk.getChunk();
      this.head.buf[0] = 0;
      this.head.buf[1] = 0;
   }

   public void sendTextMessage(String message) throws IOException {
      if (message != null) {
         this.setFinBit(true);
         this.setOpcode(WebSocketMessage.Type.TEXT.getOpcode());
         this.setMaskBit(this.mask);
         byte[] bytes = message.getBytes("UTF-8");
         this.setPayloadLength((long)bytes.length);
         this.writeToBuffer(bytes, 0, bytes.length);
         this.flush();
      }
   }

   public void sendBinaryMessage(byte[] message) throws IOException {
      if (message != null) {
         this.setFinBit(true);
         this.setOpcode(WebSocketMessage.Type.BINARY.getOpcode());
         this.setMaskBit(this.mask);
         this.setPayloadLength((long)message.length);
         this.writeToBuffer(message, 0, message.length);
         this.flush();
      }
   }

   public void sendFragment(boolean last, String message) throws IOException {
      if (message != null) {
         this.setFinBit(last);
         this.setOpcode(this.fragment ? WebSocketMessage.Type.CONTINUATION.getOpcode() : WebSocketMessage.Type.TEXT.getOpcode());
         this.setContinuationStatus(last);
         this.setMaskBit(this.mask);
         byte[] bytes = message.getBytes("UTF-8");
         this.setPayloadLength((long)bytes.length);
         this.writeToBuffer(bytes, 0, bytes.length);
         this.flush();
      }
   }

   public void sendFragment(boolean last, byte[] message, int off, int length) throws IOException {
      this.setFinBit(last);
      this.setOpcode(this.fragment ? WebSocketMessage.Type.CONTINUATION.getOpcode() : WebSocketMessage.Type.BINARY.getOpcode());
      this.setContinuationStatus(last);
      this.setMaskBit(this.mask);
      this.setPayloadLength((long)length);
      this.writeToBuffer(message, off, length);
      this.flush();
   }

   public void sendCloseMessage(int code, String reason) throws IOException {
      this.setFinBit(true);
      this.setOpcode(WebSocketMessage.Type.CLOSING.getOpcode());
      this.setMaskBit(this.mask);
      byte[] closeBody = null;
      if (code != -1) {
         if (reason != null) {
            closeBody = reason.getBytes("UTF-8");
            this.setPayloadLength((long)(closeBody.length + 2));
         } else {
            this.setPayloadLength(2L);
         }

         this.head.buf[this.payloadOffset + 1] = (byte)(code & 255);
         this.head.buf[this.payloadOffset] = (byte)(code >> 8 & 255);
         Chunk var10000 = this.head;
         var10000.end += 2;
      } else {
         this.setPayloadLength(0L);
      }

      if (closeBody != null) {
         this.writeToBuffer(closeBody, 0, closeBody.length);
      }

      this.flush();
   }

   public void sendPingMessage(byte[] message) throws IOException {
      this.setFinBit(true);
      this.setOpcode(WebSocketMessage.Type.PING.getOpcode());
      this.setMaskBit(this.mask);
      this.setPayloadLength(message == null ? 0L : (long)message.length);
      if (message != null) {
         this.writeToBuffer(message, 0, message.length);
      }

      this.flush();
   }

   public void sendPongMessage(byte[] message) throws IOException {
      this.setFinBit(true);
      this.setOpcode(WebSocketMessage.Type.PONG.getOpcode());
      this.setMaskBit(this.mask);
      this.setPayloadLength(message == null ? 0L : (long)message.length);
      if (message != null) {
         this.writeToBuffer(message, 0, message.length);
      }

      this.flush();
   }

   public void sendRawData(byte[] bytes) throws IOException {
      if (bytes != null && bytes.length != 0) {
         this.writeToBuffer(bytes, 0, bytes.length);
         this.flush();
      }
   }

   public void maskMessage() {
      this.mask = true;
   }

   private void reset() {
      this.clearBuffer();
      this.payloadOffset = 2;
      this.maskBytes = null;
      this.mask = false;
   }

   private void setFinBit(boolean isFinal) {
      byte[] var10000;
      if (isFinal) {
         var10000 = this.head.buf;
         var10000[0] = (byte)(var10000[0] | 128);
      } else {
         var10000 = this.head.buf;
         var10000[0] = (byte)(var10000[0] & 127);
      }

   }

   private void setOpcode(byte opcode) {
      byte[] var10000 = this.head.buf;
      var10000[0] |= opcode;
      ++this.head.end;
   }

   private void setMaskBit(boolean mask) {
      byte[] var10000;
      if (mask) {
         var10000 = this.head.buf;
         var10000[1] = (byte)(var10000[1] | 128);
         this.maskBytes = randomBytes(4);
         this.payloadOffset += this.maskBytes.length;
      } else {
         var10000 = this.head.buf;
         var10000[1] = (byte)(var10000[1] & 127);
         this.maskBytes = null;
      }

   }

   private void setPayloadLength(long length) {
      byte[] var10000;
      if (length < 126L) {
         var10000 = this.head.buf;
         var10000[1] |= (byte)((int)length);
         ++this.head.end;
      } else {
         Chunk var3;
         if (length <= 65535L) {
            var10000 = this.head.buf;
            var10000[1] = (byte)(var10000[1] | 126);
            this.longToBytes(2, 3, length);
            this.payloadOffset += 2;
            var3 = this.head;
            var3.end += 3;
         } else {
            var10000 = this.head.buf;
            var10000[1] = (byte)(var10000[1] | 127);
            this.longToBytes(2, 9, length);
            this.payloadOffset += 8;
            var3 = this.head;
            var3.end += 9;
         }
      }

   }

   private void longToBytes(int headOffset, int headEnd, long length) {
      long value = length;

      for(int i = headEnd; i >= headOffset; --i) {
         this.head.buf[i] = (byte)((int)(value & 255L));
         value >>= 8;
      }

   }

   private void writeToBuffer(byte[] b, int off, int len) throws IOException {
      while(len > 0) {
         this.tail = ensureCapacity(this.tail);
         int toCopy = Math.min(Chunk.CHUNK_SIZE - this.tail.end, len);
         System.arraycopy(b, off, this.tail.buf, this.tail.end, toCopy);
         this.count += toCopy;
         Chunk var10000 = this.tail;
         var10000.end += toCopy;
         off += toCopy;
         len -= toCopy;
      }

   }

   private void flush() throws IOException {
      if (this.outStream != null) {
         this.writeToSocket();
         this.reset();
      }

   }

   private void clearBuffer() {
      if (this.head != null) {
         Chunk.releaseChunks(this.head.next);
         this.tail = this.head;
         this.head.next = null;
         this.head.end = 0;
         this.head.buf[0] = 0;
         this.head.buf[1] = 0;
         this.count = 0;
      }
   }

   private void writeToSocket() throws IOException {
      int length = 0;

      for(Chunk current = this.head; current != null; current = current.next) {
         this.outStream.write(current.buf, 0, current.end);
         length += current.end;
      }

      if (WebSocketDebugLogger.isEnabled()) {
         WebSocketDebugLogger.debug("WebSocket: Sending DataFrame: ", this.head, length, true);
      }

   }

   private void setContinuationStatus(boolean last) {
      if (!this.fragment) {
         this.fragment = true;
      } else if (last) {
         this.fragment = false;
      }

   }

   static Chunk ensureCapacity(Chunk chunk) {
      if (chunk.end == Chunk.CHUNK_SIZE) {
         chunk.next = Chunk.getChunk();
         chunk.next.end = 0;
         return chunk.next;
      } else {
         return chunk;
      }
   }

   static byte[] randomBytes(int count) {
      byte[] b = new byte[count];

      for(int i = 0; i < count; ++i) {
         rand.nextBytes(b);
      }

      return b;
   }
}

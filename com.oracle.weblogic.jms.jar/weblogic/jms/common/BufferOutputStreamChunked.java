package weblogic.jms.common;

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.OutputStream;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.utils.StringUtils;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkOutput;
import weblogic.utils.io.ChunkedDataInputStream;
import weblogic.utils.io.ChunkedDataOutputStream;
import weblogic.utils.io.DataIO;
import weblogic.utils.io.StringOutput;

final class BufferOutputStreamChunked extends BufferOutputStream implements StringOutput, ChunkOutput, ObjectOutput {
   private final ObjectIOBypass objectIOBypass;
   private boolean isBypassOutputStream;
   private boolean isJMSStoreOutputStream;
   private boolean isJMSMulticastOutputStream;
   private ChunkedDataOutputStream cdos;
   private Chunk chunk;
   private static final int VERSION = 1234;

   BufferOutputStreamChunked(ObjectIOBypass objectIOBypass) {
      this.objectIOBypass = objectIOBypass;
      this.cdos = new ChunkedDataOutputStream();
      this.chunk = this.cdos.getCurrentChunk();
   }

   BufferOutputStreamChunked() {
      this((ObjectIOBypass)null);
   }

   public final boolean isJMSStoreOutputStream() {
      return this.isJMSStoreOutputStream;
   }

   public final boolean isBypassOutputStream() {
      return this.isBypassOutputStream;
   }

   public final boolean isJMSMulticastOutputStream() {
      return this.isJMSMulticastOutputStream;
   }

   public final void setIsJMSStoreOutputStream() {
      this.isJMSStoreOutputStream = true;
   }

   public final void setIsBypassOutputStream() {
      this.isBypassOutputStream = true;
   }

   public final void setIsJMSMulticastOutputStream() {
      this.isJMSMulticastOutputStream = true;
   }

   public final void write(int b) {
      this.cdos.write(b);
   }

   public final void write(byte[] b, int off, int len) {
      this.cdos.write(b, off, len);
   }

   public final void reset() {
      this.cdos.reset();
   }

   public final int size() {
      return this.cdos.getPosition();
   }

   public final void writeObject(Object o) throws IOException {
      this.writeInt(1234);
      if (this.objectIOBypass == null) {
         throw new IOException(JMSClientExceptionLogger.logRawObjectError2Loggable().getMessage());
      } else {
         this.objectIOBypass.writeObject(this, o);
      }
   }

   public final void writeBoolean(boolean data) throws IOException {
      this.cdos.writeBoolean(data);
   }

   public final void writeByte(int data) throws IOException {
      this.cdos.writeByte(data);
   }

   public final void writeShort(int data) throws IOException {
      this.cdos.writeShort(data);
   }

   public final void writeChar(int data) throws IOException {
      this.cdos.writeChar(data);
   }

   public final void writeInt(int data) throws IOException {
      this.cdos.writeInt(data);
   }

   public final void writeLong(long data) throws IOException {
      this.cdos.writeLong(data);
   }

   public final void writeFloat(float data) throws IOException {
      this.cdos.writeFloat(data);
   }

   public final void writeDouble(double data) throws IOException {
      this.cdos.writeDouble(data);
   }

   public final void writeBytes(String s) throws IOException {
      this.cdos.writeBytes(s);
   }

   public final void writeChars(String s) throws IOException {
      this.cdos.writeChars(s);
   }

   public final void writeUTF32(String str) throws IOException {
      writeUTF32(this, str);
   }

   public static void writeUTF32(DataOutput out, String str) throws IOException {
      int utflen = StringUtils.getUTFLength(str);
      out.writeInt(utflen);
      int strlen = str.length();

      for(int i = 0; i < strlen; ++i) {
         DataIO.writeUTFChar(out, str.charAt(i));
      }

   }

   public final void writeUTF(String str) throws IOException {
      this.cdos.writeUTF(str);
   }

   public int getLength() {
      Chunk update = this.cdos.getCurrentChunk();
      update.end = Math.max(update.end, this.cdos.getChunkPos());
      return Chunk.size(this.chunk);
   }

   public void writeLengthAndData(DataOutput out) throws IOException {
      Chunk update = this.cdos.getCurrentChunk();
      update.end = Math.max(update.end, this.cdos.getChunkPos());
      PayloadChunkBase.internalWriteLengthAndData(out, this.chunk);
   }

   public void writeTo(OutputStream out) throws IOException {
      Chunk update = this.cdos.getCurrentChunk();
      update.end = Math.max(update.end, this.cdos.getChunkPos());

      for(Chunk current = this.chunk; current != null; current = current.next) {
         if (current.end > 0) {
            out.write(current.buf, 0, current.end);
         }
      }

   }

   public BufferInputStream getInputStream() throws IOException {
      return new BufferInputStreamChunked(this.objectIOBypass, new ChunkedDataInputStream(this.cdos.getSharedBeforeCopyTail(), 0));
   }

   public Payload moveToPayload() {
      Chunk movedData = this.cdos.getChunks();
      this.chunk = null;
      this.cdos = null;
      int stolen = movedData.setShareBuffer();
      stolen >>= PayloadFactoryImpl.SHIFT_REPLACEMENT_STOLEN_CHUNK_COUNT;
      Chunk.replaceStolenChunks(stolen);
      return new PayloadChunkBase(movedData);
   }

   PayloadStream copyPayloadWithoutSharedStream() throws JMSException {
      Chunk update = this.cdos.getCurrentChunk();
      update.end = Math.max(update.end, this.cdos.getChunkPos());
      return new PayloadChunkBase(PayloadChunkBase.copyWithoutSharedData(this.chunk));
   }

   public void writeChunks(Chunk s) throws IOException {
      Chunk tail = Chunk.tail(s);
      if (tail.isReadOnlySharedBuf()) {
         tail.next = Chunk.getChunk();
      }

      this.cdos.writeChunks(s);
   }

   public void copyBuffer() throws JMSException {
      Chunk update = this.cdos.getCurrentChunk();
      update.end = Math.max(update.end, this.cdos.getChunkPos());
      ChunkedDataOutputStream newCdos = new ChunkedDataOutputStream();
      Chunk newChunk = newCdos.getCurrentChunk();

      try {
         this.writeTo(newCdos);
      } catch (IOException var5) {
         throw new JMSException(var5);
      }

      this.cdos = newCdos;
      this.chunk = newChunk;
   }

   public ObjectOutput getObjectOutput() {
      return this;
   }

   public void writeASCII(String s) throws IOException {
      this.cdos.writeASCII(s);
   }

   public void writeUTF8(String s) throws IOException {
      this.cdos.writeUTF8(s);
   }
}

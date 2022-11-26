package weblogic.jms.common;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.jms.BytesMessage;
import weblogic.jms.JMSClientExceptionLogger;

public final class BytesMessageImpl extends MessageImpl implements BytesMessage, Externalizable {
   private static final byte EXTVERSION1 = 1;
   private static final byte EXTVERSION2 = 2;
   private static final byte EXTVERSION3 = 3;
   private static final byte VERSIONMASK = 127;
   static final long serialVersionUID = -8264735281046103996L;
   transient PayloadStream payload;
   private transient BufferOutputStream bos;
   private transient BufferInputStream bis;

   public BytesMessageImpl() {
   }

   public BytesMessageImpl(BytesMessage message) throws IOException, javax.jms.JMSException {
      this(message, (javax.jms.Destination)null, (javax.jms.Destination)null);
   }

   public BytesMessageImpl(BytesMessage message, javax.jms.Destination destination, javax.jms.Destination replyDestination) throws IOException, javax.jms.JMSException {
      super(message, destination, replyDestination);
      int size = 4096;
      byte[] buff = new byte[size];
      message.reset();

      for(int len = message.readBytes(buff, size); len > 0; len = message.readBytes(buff, size)) {
         this.writeBytes(buff, 0, len);
      }

      this.reset();
      this.setPropertiesWritable(false);
   }

   public byte getType() {
      return 1;
   }

   public void nullBody() {
      this.payload = null;
      this.bis = null;
      this.bos = null;
      this.payloadCopyOnWrite = false;
   }

   private String getReadPastEnd(int place) {
      return JMSClientExceptionLogger.logReadPastEnd2Loggable(place).getMessage();
   }

   static String getReadError(int place) {
      return JMSClientExceptionLogger.logReadErrorLoggable(place).getMessage();
   }

   public boolean readBoolean() throws javax.jms.JMSException {
      try {
         this.checkReadable();
         return this.bis.readBoolean();
      } catch (EOFException var3) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.getReadPastEnd(10));
         mee.setLinkedException(var3);
         throw mee;
      } catch (IOException var4) {
         throw new JMSException(getReadError(10), var4);
      }
   }

   public byte readByte() throws javax.jms.JMSException {
      try {
         this.checkReadable();
         return this.bis.readByte();
      } catch (EOFException var3) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.getReadPastEnd(20));
         mee.setLinkedException(var3);
         throw mee;
      } catch (IOException var4) {
         throw new JMSException(getReadError(20), var4);
      }
   }

   public int readUnsignedByte() throws javax.jms.JMSException {
      try {
         this.checkReadable();
         return this.bis.readUnsignedByte();
      } catch (EOFException var3) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.getReadPastEnd(30));
         mee.setLinkedException(var3);
         throw mee;
      } catch (IOException var4) {
         throw new JMSException(getReadError(30), var4);
      }
   }

   public short readShort() throws javax.jms.JMSException {
      try {
         this.checkReadable();
         return this.bis.readShort();
      } catch (EOFException var3) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.getReadPastEnd(40));
         mee.setLinkedException(var3);
         throw mee;
      } catch (IOException var4) {
         throw new JMSException(getReadError(40), var4);
      }
   }

   public int readUnsignedShort() throws javax.jms.JMSException {
      try {
         this.checkReadable();
         return this.bis.readUnsignedShort();
      } catch (EOFException var3) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.getReadPastEnd(50));
         mee.setLinkedException(var3);
         throw mee;
      } catch (IOException var4) {
         throw new JMSException(getReadError(50), var4);
      }
   }

   public char readChar() throws javax.jms.JMSException {
      try {
         this.checkReadable();
         return this.bis.readChar();
      } catch (EOFException var3) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.getReadPastEnd(60));
         mee.setLinkedException(var3);
         throw mee;
      } catch (IOException var4) {
         throw new JMSException(getReadError(60), var4);
      }
   }

   public int readInt() throws javax.jms.JMSException {
      try {
         this.checkReadable();
         return this.bis.readInt();
      } catch (EOFException var3) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.getReadPastEnd(70));
         mee.setLinkedException(var3);
         throw mee;
      } catch (IOException var4) {
         throw new JMSException(getReadError(70), var4);
      }
   }

   public long readLong() throws javax.jms.JMSException {
      try {
         this.checkReadable();
         return this.bis.readLong();
      } catch (EOFException var3) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.getReadPastEnd(80));
         mee.setLinkedException(var3);
         throw mee;
      } catch (IOException var4) {
         throw new JMSException(getReadError(80), var4);
      }
   }

   public float readFloat() throws javax.jms.JMSException {
      try {
         this.checkReadable();
         return this.bis.readFloat();
      } catch (EOFException var3) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.getReadPastEnd(90));
         mee.setLinkedException(var3);
         throw mee;
      } catch (IOException var4) {
         throw new JMSException(getReadError(90), var4);
      }
   }

   public double readDouble() throws javax.jms.JMSException {
      try {
         this.checkReadable();
         return this.bis.readDouble();
      } catch (EOFException var3) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.getReadPastEnd(100));
         mee.setLinkedException(var3);
         throw mee;
      } catch (IOException var4) {
         throw new JMSException(getReadError(100), var4);
      }
   }

   public String readUTF() throws javax.jms.JMSException {
      try {
         this.checkReadable();
         return this.bis.readUTF();
      } catch (EOFException var3) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.getReadPastEnd(110));
         mee.setLinkedException(var3);
         throw mee;
      } catch (IOException var4) {
         throw new JMSException(getReadError(110), var4);
      }
   }

   public int readBytes(byte[] value) throws javax.jms.JMSException {
      return this.readBytes(value, value.length);
   }

   public int readBytes(byte[] value, int length) throws javax.jms.JMSException {
      if (length < 0) {
         throw new IndexOutOfBoundsException(JMSClientExceptionLogger.logNegativeLengthLoggable(length).getMessage());
      } else if (length > value.length) {
         throw new IndexOutOfBoundsException(JMSClientExceptionLogger.logTooMuchLengthLoggable(length, value.length).getMessage());
      } else {
         try {
            this.checkReadable();
         } catch (javax.jms.MessageEOFException var5) {
            return -1;
         }

         try {
            return this.bis.read(value, 0, length);
         } catch (IOException var4) {
            throw new JMSException(getReadError(5), var4);
         }
      }
   }

   private String getWriteError(int place) {
      return JMSClientExceptionLogger.logWriteErrorLoggable(place).getMessage();
   }

   public void writeBoolean(boolean value) throws javax.jms.JMSException {
      try {
         this.checkWritable();
         this.bos.writeBoolean(value);
      } catch (IOException var3) {
         throw new JMSException(this.getWriteError(10), var3);
      }
   }

   public void writeByte(byte value) throws javax.jms.JMSException {
      try {
         this.checkWritable();
         this.bos.writeByte(value);
      } catch (IOException var3) {
         throw new JMSException(this.getWriteError(20), var3);
      }
   }

   public void writeShort(short value) throws javax.jms.JMSException {
      try {
         this.checkWritable();
         this.bos.writeShort(value);
      } catch (IOException var3) {
         throw new JMSException(this.getWriteError(30), var3);
      }
   }

   public void writeChar(char value) throws javax.jms.JMSException {
      try {
         this.checkWritable();
         this.bos.writeChar(value);
      } catch (IOException var3) {
         throw new JMSException(this.getWriteError(40), var3);
      }
   }

   public void writeInt(int value) throws javax.jms.JMSException {
      try {
         this.checkWritable();
         this.bos.writeInt(value);
      } catch (IOException var3) {
         throw new JMSException(this.getWriteError(50), var3);
      }
   }

   public void writeLong(long value) throws javax.jms.JMSException {
      try {
         this.checkWritable();
         this.bos.writeLong(value);
      } catch (IOException var4) {
         throw new JMSException(this.getWriteError(60), var4);
      }
   }

   public void writeFloat(float value) throws javax.jms.JMSException {
      try {
         this.checkWritable();
         this.bos.writeFloat(value);
      } catch (IOException var3) {
         throw new JMSException(this.getWriteError(70), var3);
      }
   }

   public void writeDouble(double value) throws javax.jms.JMSException {
      try {
         this.checkWritable();
         this.bos.writeDouble(value);
      } catch (IOException var4) {
         throw new JMSException(this.getWriteError(80), var4);
      }
   }

   public void writeUTF(String value) throws javax.jms.JMSException {
      try {
         this.checkWritable();
         this.bos.writeUTF(value);
      } catch (IOException var3) {
         throw new JMSException(this.getWriteError(90), var3);
      }
   }

   public void writeBytes(byte[] value) throws javax.jms.JMSException {
      try {
         this.checkWritable();
         this.bos.write(value);
      } catch (IOException var3) {
         throw new JMSException(this.getWriteError(100), var3);
      }
   }

   public void writeBytes(byte[] value, int offset, int length) throws javax.jms.JMSException {
      this.checkWritable();

      try {
         this.bos.write(value, offset, length);
      } catch (IOException var5) {
         throw new JMSException(var5);
      }
   }

   public void writeObject(Object value) throws javax.jms.JMSException {
      if (value instanceof Boolean) {
         this.writeBoolean((Boolean)value);
      } else if (value instanceof Number) {
         if (value instanceof Byte) {
            this.writeByte(((Number)value).byteValue());
         } else if (value instanceof Short) {
            this.writeShort(((Number)value).shortValue());
         } else if (value instanceof Integer) {
            this.writeInt(((Number)value).intValue());
         } else if (value instanceof Long) {
            this.writeLong(((Number)value).longValue());
         } else if (value instanceof Float) {
            this.writeFloat(((Number)value).floatValue());
         } else if (value instanceof Double) {
            this.writeDouble(((Number)value).doubleValue());
         }
      } else if (value instanceof String) {
         this.writeUTF((String)value);
      } else {
         if (!(value instanceof byte[])) {
            throw new javax.jms.MessageFormatException(JMSClientExceptionLogger.logInvalidObjectLoggable(value.getClass().getName()).getMessage());
         }

         this.writeBytes((byte[])((byte[])value));
      }

   }

   public void reset() throws javax.jms.JMSException {
      try {
         this.setBodyWritable(false);
         if (this.bis != null) {
            this.bis.reset();
         } else if (this.bos != null) {
            this.payload = (PayloadStream)this.bos.moveToPayload();
            this.bos = null;
         }

         this.payloadCopyOnWrite = false;
      } catch (IOException var2) {
         throw new JMSException(getReadError(578), var2);
      }
   }

   public MessageImpl copy() throws javax.jms.JMSException {
      BytesMessageImpl message = new BytesMessageImpl();
      super.copy(message);
      if (this.bos != null) {
         message.payload = this.bos.copyPayloadWithoutSharedStream();
      } else if (this.payload != null) {
         message.payload = this.payload.copyPayloadWithoutSharedStream();
      }

      message.payloadCopyOnWrite = this.payloadCopyOnWrite = true;
      message.setBodyWritable(false);
      message.setPropertiesWritable(false);
      return message;
   }

   private void checkWritable() throws javax.jms.JMSException {
      super.writeMode();
      if (this.bos == null) {
         this.bos = PayloadFactoryImpl.createOutputStream();
      } else if (this.payloadCopyOnWrite) {
         this.bos.copyBuffer();
         this.payloadCopyOnWrite = false;
      }

   }

   private void checkReadable() throws javax.jms.JMSException {
      super.readMode();
      this.decompressMessageBody();
      if (this.bis == null) {
         if (this.payload == null) {
            throw new javax.jms.MessageEOFException(this.getReadPastEnd(120));
         }

         try {
            this.bis = this.payload.getInputStream();
         } catch (IOException var2) {
            throw new JMSException(getReadError(510), var2);
         }
      }

   }

   public String toString() {
      return "BytesMessage[" + this.getJMSMessageID() + "]";
   }

   public void writeExternal(ObjectOutput tOut) throws IOException {
      super.writeExternal(tOut);
      int compressionThreshold = Integer.MAX_VALUE;
      ObjectOutput out;
      if (tOut instanceof MessageImpl.JMSObjectOutputWrapper) {
         out = ((MessageImpl.JMSObjectOutputWrapper)tOut).getInnerObjectOutput();
         compressionThreshold = ((MessageImpl.JMSObjectOutputWrapper)tOut).getCompressionThreshold();
      } else {
         out = tOut;
      }

      byte flag;
      if (this.getVersion(out) >= 30) {
         if (this.needToDecompressDueToInterop(out)) {
            flag = 3;
         } else {
            flag = (byte)(3 | (this.shouldCompress(out, compressionThreshold) ? -128 : 0));
         }
      } else {
         flag = 2;
      }

      out.writeByte(flag);
      if (debugWire && JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("BytesMessageImpl.write versionInt x" + Integer.toHexString(flag).toUpperCase() + ((flag & -128) != 0 ? " compression is on" : ""));
      }

      if (this.isCompressed()) {
         if (flag == 2) {
            this.decompress().writeLengthAndData(out);
         } else if (this.needToDecompressDueToInterop(out)) {
            this.decompress().writeLengthAndData(out);
         } else {
            this.flushCompressedMessageBody(out);
         }

      } else {
         Object localPayload;
         if (this.bos != null) {
            localPayload = this.bos;
         } else {
            if (this.payload == null) {
               out.writeInt(0);
               return;
            }

            localPayload = this.payload;
         }

         if ((flag & -128) != 0) {
            this.writeExternalCompressPayload(out, (Payload)localPayload);
         } else {
            ((Payload)localPayload).writeLengthAndData(out);
         }

      }
   }

   public final void decompressMessageBody() throws javax.jms.JMSException {
      if (this.isCompressed()) {
         try {
            this.payload = (PayloadStream)this.decompress();
         } catch (IOException var5) {
            throw new JMSException(JMSClientExceptionLogger.logErrorDecompressMessageBodyLoggable().getMessage(), var5);
         } finally {
            this.cleanupCompressedMessageBody();
         }

      }
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      byte unmaskedVrsn = in.readByte();
      byte version = (byte)(unmaskedVrsn & 127);
      if (debugWire && JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("BytesMessageImpl.read  versionInt x" + Integer.toHexString(version).toUpperCase());
      }

      if (version >= 1 && version <= 3) {
         switch (version) {
            case 1:
               if (in.readBoolean()) {
                  this.payload = (PayloadStream)PayloadFactoryImpl.createPayload((InputStream)in);
               }
               break;
            case 3:
               if ((unmaskedVrsn & -128) != 0) {
                  this.readExternalCompressedMessageBody(in);
                  break;
               }
            case 2:
               this.payload = (PayloadStream)PayloadFactoryImpl.createPayload((InputStream)in);
         }

         this.setBodyWritable(false);
         this.setPropertiesWritable(false);
      } else {
         throw JMSUtilities.versionIOException(version, 1, 3);
      }
   }

   public long getPayloadSize() {
      if (this.isCompressed()) {
         return (long)this.getCompressedMessageBodySize();
      } else if (super.bodySize != -1L) {
         return super.bodySize;
      } else if (this.payload != null) {
         return super.bodySize = (long)this.payload.getLength();
      } else {
         return this.bos != null ? (long)this.bos.size() : (super.bodySize = 0L);
      }
   }

   private long getLen() {
      if (this.bos != null) {
         return (long)this.bos.size();
      } else if (this.payload != null) {
         return (long)this.payload.getLength();
      } else {
         return this.isCompressed() ? (long)this.getOriginalMessageBodySize() : 0L;
      }
   }

   public long getBodyLength() throws javax.jms.JMSException {
      super.readMode();
      return this.getLen();
   }

   public byte[] getBodyBytes() throws javax.jms.JMSException {
      Object localPayload;
      if (this.payload != null) {
         localPayload = this.payload;
      } else {
         if (this.bos == null) {
            return new byte[0];
         }

         localPayload = this.bos;
      }

      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ((Payload)localPayload).writeTo(baos);
         baos.flush();
         return baos.toByteArray();
      } catch (IOException var3) {
         throw new JMSException(var3);
      }
   }

   public PayloadStream getPayload() throws javax.jms.JMSException {
      if (this.isCompressed()) {
         try {
            this.payload = (PayloadStream)this.decompress();
         } catch (IOException var2) {
            throw new JMSException(JMSClientExceptionLogger.logErrorDecompressMessageBodyLoggable().getMessage(), var2);
         }
      }

      return this.payload;
   }

   public void setPayload(PayloadStream payload) {
      if (this.payload == null && this.bis == null && this.bos == null && !this.payloadCopyOnWrite) {
         try {
            this.writeMode();
         } catch (javax.jms.JMSException var3) {
            throw new AssertionError(var3);
         }

         this.payload = payload;
      } else {
         throw new AssertionError();
      }
   }
}

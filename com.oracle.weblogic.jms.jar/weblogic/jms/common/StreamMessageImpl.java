package weblogic.jms.common;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import javax.jms.StreamMessage;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.utils.io.FilteringObjectInputStream;

public final class StreamMessageImpl extends MessageImpl implements StreamMessage, Externalizable {
   private static final byte EXTVERSION1 = 1;
   private static final byte EXTVERSION2 = 2;
   private static final byte EXTVERSION3 = 3;
   private static final byte VERSIONMASK = 127;
   static final long serialVersionUID = 7748687583664395357L;
   private static final byte UNKNOWN_TYPECODE = 0;
   private static final byte BOOLEAN_TYPE = 1;
   private static final byte BYTE_TYPE = 2;
   private static final byte CHAR_TYPE = 3;
   private static final byte DOUBLE_TYPE = 4;
   private static final byte FLOAT_TYPE = 5;
   private static final byte INT_TYPE = 6;
   private static final byte LONG_TYPE = 7;
   private static final byte SHORT_TYPE = 8;
   private static final byte STRING_UTF_TYPE = 9;
   private static final byte STRING_UTF32_TYPE = 10;
   private static final byte BYTES_TYPE = 11;
   private static final byte NULL_TYPE = 12;
   private static final String[] TYPE_CODE_STRINGS = new String[]{"invalid type code", "boolean", "byte", "char", "double", "float", "integer", "long", "short", "String", "String", "byte array", "null object"};
   private static final String ERROR_MSG_SEGMENT = ". Previous attempt to read bytes from the stream message is not complete. As per the JMS standard, if the readBytes method does not return the value -1, a subsequent readBytes call must be made in order to ensure that there are no more bytes left to be read in. For more information, see the JMS API doc for the method readBytes in interface StreamMessage";
   private boolean readingByteArray;
   private int available_bytes;
   private transient PayloadStream payload;
   private transient boolean copyOnWrite;
   private transient BufferOutputStream bos;
   private transient BufferInputStream bis;

   public StreamMessageImpl() {
   }

   public StreamMessageImpl(StreamMessage message) throws IOException, javax.jms.JMSException {
      this(message, (javax.jms.Destination)null, (javax.jms.Destination)null);
   }

   public StreamMessageImpl(StreamMessage message, javax.jms.Destination destination, javax.jms.Destination replyDestination) throws IOException, javax.jms.JMSException {
      super(message, destination, replyDestination);
      if (!(message instanceof StreamMessageImpl)) {
         message.reset();
      }

      try {
         while(true) {
            this.writeObject(message.readObject());
         }
      } catch (javax.jms.MessageEOFException var5) {
         this.reset();
         this.setPropertiesWritable(false);
      }
   }

   public byte getType() {
      return 5;
   }

   public void nullBody() {
      this.payload = null;
      this.copyOnWrite = false;
      this.bis = null;
      this.bos = null;
      this.readingByteArray = false;
      this.available_bytes = 0;
   }

   private void putTypeBack() throws IOException {
      if (!this.readingByteArray) {
         this.bis.unput();
      }

   }

   private String readPastEnd() {
      return JMSClientExceptionLogger.logReadPastEndLoggable().getMessage();
   }

   private String readPastEnd3(int place) {
      return JMSClientExceptionLogger.logReadPastEnd3Loggable(place).getMessage();
   }

   private String streamReadError() {
      return JMSClientExceptionLogger.logStreamReadErrorLoggable().getMessage();
   }

   private String streamReadError(int place) {
      return JMSClientExceptionLogger.logReadErrorLoggable(place).getMessage();
   }

   private String streamWriteError() {
      return JMSClientExceptionLogger.logStreamWriteErrorLoggable().getMessage();
   }

   private String streamWriteError(int place) {
      return JMSClientExceptionLogger.logWriteErrorLoggable(place).getMessage();
   }

   private String streamConversionError(String from, String to) {
      return JMSClientExceptionLogger.logConversionErrorLoggable(from, to).getMessage();
   }

   private byte readType() throws javax.jms.JMSException {
      this.decompressMessageBody();
      this.checkReadable();
      if (this.readingByteArray) {
         return 11;
      } else {
         try {
            return this.bis.readByte();
         } catch (EOFException var3) {
            javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.readPastEnd3(0));
            mee.setLinkedException(var3);
            throw mee;
         } catch (IOException var4) {
            throw new JMSException(this.streamReadError(0), var4);
         }
      }
   }

   private void writeType(byte type) throws javax.jms.JMSException {
      this.checkWritable();

      try {
         this.bos.writeByte(type);
      } catch (IOException var3) {
         throw new JMSException(JMSClientExceptionLogger.logStreamWriteErrorLoggable().getMessage(), var3);
      }
   }

   public boolean readBoolean() throws javax.jms.JMSException {
      byte type = this.readType();

      try {
         switch (type) {
            case 1:
               return this.bis.readBoolean();
            case 9:
            case 10:
               return Boolean.valueOf(this.readStringInternal(type));
            default:
               this.putTypeBack();
               String msgExtension = "";
               if (this.readingByteArray) {
                  msgExtension = ". Previous attempt to read bytes from the stream message is not complete. As per the JMS standard, if the readBytes method does not return the value -1, a subsequent readBytes call must be made in order to ensure that there are no more bytes left to be read in. For more information, see the JMS API doc for the method readBytes in interface StreamMessage";
               }

               throw new javax.jms.MessageFormatException(this.streamConversionError(this.typeCodeToString(type), this.typeCodeToString(1)) + msgExtension);
         }
      } catch (EOFException var4) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.readPastEnd3(10));
         mee.setLinkedException(var4);
         throw mee;
      } catch (IOException var5) {
         throw new JMSException(this.streamReadError(10), var5);
      }
   }

   public byte readByte() throws javax.jms.JMSException {
      byte type = this.readType();

      try {
         switch (type) {
            case 2:
               return this.bis.readByte();
            case 9:
            case 10:
               int pos = this.bis.pos();

               try {
                  return Byte.parseByte(this.readStringInternal(type));
               } catch (NumberFormatException var4) {
                  this.bis.gotoPos(pos);
                  this.bis.unput();
                  throw var4;
               }
            default:
               this.putTypeBack();
               String msgExtension = "";
               if (this.readingByteArray) {
                  msgExtension = ". Previous attempt to read bytes from the stream message is not complete. As per the JMS standard, if the readBytes method does not return the value -1, a subsequent readBytes call must be made in order to ensure that there are no more bytes left to be read in. For more information, see the JMS API doc for the method readBytes in interface StreamMessage";
               }

               throw new javax.jms.MessageFormatException(this.streamConversionError(this.typeCodeToString(type), this.typeCodeToString(2)) + msgExtension);
         }
      } catch (EOFException var5) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.readPastEnd3(20));
         mee.setLinkedException(var5);
         throw mee;
      } catch (IOException var6) {
         throw new JMSException(this.streamReadError(20), var6);
      }
   }

   public short readShort() throws javax.jms.JMSException {
      byte type = this.readType();

      try {
         switch (type) {
            case 2:
               return (short)this.bis.readByte();
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            default:
               this.putTypeBack();
               String msgExtension = "";
               if (this.readingByteArray) {
                  msgExtension = ". Previous attempt to read bytes from the stream message is not complete. As per the JMS standard, if the readBytes method does not return the value -1, a subsequent readBytes call must be made in order to ensure that there are no more bytes left to be read in. For more information, see the JMS API doc for the method readBytes in interface StreamMessage";
               }

               throw new javax.jms.MessageFormatException(this.streamConversionError(this.typeCodeToString(type), this.typeCodeToString(8)) + msgExtension);
            case 8:
               return this.bis.readShort();
            case 9:
            case 10:
               int pos = this.bis.pos();

               try {
                  return Short.parseShort(this.readStringInternal(type));
               } catch (NumberFormatException var4) {
                  this.bis.gotoPos(pos);
                  this.bis.unput();
                  throw var4;
               }
         }
      } catch (EOFException var5) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.readPastEnd3(40));
         mee.setLinkedException(var5);
         throw mee;
      } catch (IOException var6) {
         throw new JMSException(this.streamReadError(40), var6);
      }
   }

   public char readChar() throws javax.jms.JMSException {
      byte type = this.readType();

      try {
         switch (type) {
            case 3:
               return this.bis.readChar();
            case 12:
               this.putTypeBack();
               throw new NullPointerException();
            default:
               this.putTypeBack();
               String msgExtension = "";
               if (this.readingByteArray) {
                  msgExtension = ". Previous attempt to read bytes from the stream message is not complete. As per the JMS standard, if the readBytes method does not return the value -1, a subsequent readBytes call must be made in order to ensure that there are no more bytes left to be read in. For more information, see the JMS API doc for the method readBytes in interface StreamMessage";
               }

               throw new javax.jms.MessageFormatException(this.streamConversionError(this.typeCodeToString(type), this.typeCodeToString(3)) + msgExtension);
         }
      } catch (EOFException var4) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.readPastEnd3(60));
         mee.setLinkedException(var4);
         throw mee;
      } catch (IOException var5) {
         throw new JMSException(this.streamReadError(60), var5);
      }
   }

   public int readInt() throws javax.jms.JMSException {
      byte type = this.readType();

      try {
         switch (type) {
            case 2:
               return this.bis.readByte();
            case 3:
            case 4:
            case 5:
            case 7:
            default:
               this.putTypeBack();
               String msgExtension = "";
               if (this.readingByteArray) {
                  msgExtension = ". Previous attempt to read bytes from the stream message is not complete. As per the JMS standard, if the readBytes method does not return the value -1, a subsequent readBytes call must be made in order to ensure that there are no more bytes left to be read in. For more information, see the JMS API doc for the method readBytes in interface StreamMessage";
               }

               throw new javax.jms.MessageFormatException(this.streamConversionError(this.typeCodeToString(type), this.typeCodeToString(6)) + msgExtension);
            case 6:
               return this.bis.readInt();
            case 8:
               return this.bis.readShort();
            case 9:
            case 10:
               int pos = this.bis.pos();

               try {
                  return Integer.parseInt(this.readStringInternal(type));
               } catch (NumberFormatException var4) {
                  this.bis.gotoPos(pos);
                  this.bis.unput();
                  throw var4;
               }
         }
      } catch (EOFException var5) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.readPastEnd3(70));
         mee.setLinkedException(var5);
         throw mee;
      } catch (IOException var6) {
         throw new JMSException(this.streamReadError(70), var6);
      }
   }

   public long readLong() throws javax.jms.JMSException {
      byte type = this.readType();

      try {
         switch (type) {
            case 2:
               return (long)this.bis.readByte();
            case 3:
            case 4:
            case 5:
            default:
               this.putTypeBack();
               String msgExtension = "";
               if (this.readingByteArray) {
                  msgExtension = ". Previous attempt to read bytes from the stream message is not complete. As per the JMS standard, if the readBytes method does not return the value -1, a subsequent readBytes call must be made in order to ensure that there are no more bytes left to be read in. For more information, see the JMS API doc for the method readBytes in interface StreamMessage";
               }

               throw new javax.jms.MessageFormatException(this.streamConversionError(this.typeCodeToString(type), this.typeCodeToString(7)) + msgExtension);
            case 6:
               return (long)this.bis.readInt();
            case 7:
               return this.bis.readLong();
            case 8:
               return (long)this.bis.readShort();
            case 9:
            case 10:
               int pos = this.bis.pos();

               try {
                  return Long.parseLong(this.readStringInternal(type));
               } catch (NumberFormatException var4) {
                  this.bis.gotoPos(pos);
                  this.bis.unput();
                  throw var4;
               }
         }
      } catch (EOFException var5) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.readPastEnd3(80));
         mee.setLinkedException(var5);
         throw mee;
      } catch (IOException var6) {
         throw new JMSException(this.streamReadError(80), var6);
      }
   }

   public float readFloat() throws javax.jms.JMSException {
      byte type = this.readType();

      try {
         switch (type) {
            case 5:
               return this.bis.readFloat();
            case 9:
            case 10:
               int pos = this.bis.pos();

               try {
                  return Float.parseFloat(this.readStringInternal(type));
               } catch (NumberFormatException var4) {
                  this.bis.gotoPos(pos);
                  this.bis.unput();
                  throw var4;
               }
            default:
               this.putTypeBack();
               String msgExtension = "";
               if (this.readingByteArray) {
                  msgExtension = ". Previous attempt to read bytes from the stream message is not complete. As per the JMS standard, if the readBytes method does not return the value -1, a subsequent readBytes call must be made in order to ensure that there are no more bytes left to be read in. For more information, see the JMS API doc for the method readBytes in interface StreamMessage";
               }

               throw new javax.jms.MessageFormatException(this.streamConversionError(this.typeCodeToString(type), this.typeCodeToString(5)) + msgExtension);
         }
      } catch (EOFException var5) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.readPastEnd3(90));
         mee.setLinkedException(var5);
         throw mee;
      } catch (IOException var6) {
         throw new JMSException(this.streamReadError(90), var6);
      }
   }

   public double readDouble() throws javax.jms.JMSException {
      byte type = this.readType();

      try {
         switch (type) {
            case 4:
               return this.bis.readDouble();
            case 5:
               return (double)this.bis.readFloat();
            case 6:
            case 7:
            case 8:
            default:
               this.putTypeBack();
               String msgExtension = "";
               if (this.readingByteArray) {
                  msgExtension = ". Previous attempt to read bytes from the stream message is not complete. As per the JMS standard, if the readBytes method does not return the value -1, a subsequent readBytes call must be made in order to ensure that there are no more bytes left to be read in. For more information, see the JMS API doc for the method readBytes in interface StreamMessage";
               }

               throw new javax.jms.MessageFormatException(this.streamConversionError(this.typeCodeToString(type), this.typeCodeToString(4)) + msgExtension);
            case 9:
            case 10:
               int pos = this.bis.pos();

               try {
                  return Double.parseDouble(this.readStringInternal(type));
               } catch (NumberFormatException var4) {
                  this.bis.gotoPos(pos);
                  this.bis.unput();
                  throw var4;
               }
         }
      } catch (EOFException var5) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.readPastEnd3(100));
         mee.setLinkedException(var5);
         throw mee;
      } catch (IOException var6) {
         throw new JMSException(this.streamReadError(100), var6);
      }
   }

   public String readString() throws javax.jms.JMSException {
      byte type = this.readType();

      try {
         switch (type) {
            case 1:
               return String.valueOf(this.bis.readBoolean());
            case 2:
               return String.valueOf(this.bis.readByte());
            case 3:
               return String.valueOf(this.bis.readChar());
            case 4:
               return String.valueOf(this.bis.readDouble());
            case 5:
               return String.valueOf(this.bis.readFloat());
            case 6:
               return String.valueOf(this.bis.readInt());
            case 7:
               return String.valueOf(this.bis.readLong());
            case 8:
               return String.valueOf(this.bis.readShort());
            case 9:
               return this.readStringInternal(type);
            case 10:
               return this.readStringInternal(type);
            case 11:
            default:
               this.putTypeBack();
               String msgExtension = "";
               if (this.readingByteArray) {
                  msgExtension = ". Previous attempt to read bytes from the stream message is not complete. As per the JMS standard, if the readBytes method does not return the value -1, a subsequent readBytes call must be made in order to ensure that there are no more bytes left to be read in. For more information, see the JMS API doc for the method readBytes in interface StreamMessage";
               }

               throw new javax.jms.MessageFormatException(this.streamConversionError(this.typeCodeToString(type), this.typeCodeToString(9)) + msgExtension);
            case 12:
               return null;
         }
      } catch (EOFException var4) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.readPastEnd());
         mee.setLinkedException(var4);
         throw mee;
      } catch (IOException var5) {
         throw new JMSException(this.streamReadError(), var5);
      }
   }

   public int readBytes(byte[] value) throws javax.jms.JMSException {
      int ret = true;
      if (value == null) {
         throw new NullPointerException();
      } else {
         try {
            if (!this.readingByteArray) {
               byte type;
               if ((type = this.readType()) != 11) {
                  if (type == 12) {
                     return -1;
                  }

                  this.bis.unput();
                  throw new javax.jms.MessageFormatException(this.streamConversionError(this.typeCodeToString(type), this.typeCodeToString(11)));
               }

               this.available_bytes = this.bis.readInt();
               if (this.available_bytes == 0) {
                  return 0;
               }

               this.readingByteArray = true;
            }

            if (this.available_bytes == 0) {
               this.readingByteArray = false;
               return -1;
            } else {
               int ret;
               if (value.length > this.available_bytes) {
                  ret = this.bis.read(value, 0, this.available_bytes);
                  this.readingByteArray = false;
               } else {
                  ret = this.bis.read(value, 0, value.length);
                  this.available_bytes -= value.length;
               }

               return ret;
            }
         } catch (EOFException var6) {
            javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.readPastEnd());
            mee.setLinkedException(var6);
            throw mee;
         } catch (IOException var7) {
            throw new JMSException(this.streamReadError(), var7);
         } catch (ArrayIndexOutOfBoundsException var8) {
            throw new JMSException(JMSClientExceptionLogger.logStreamReadErrorIndexLoggable().getMessage(), var8);
         } catch (ArrayStoreException var9) {
            throw new JMSException(JMSClientExceptionLogger.logStreamReadErrorStoreLoggable().getMessage(), var9);
         }
      }
   }

   public Object readObject() throws javax.jms.JMSException {
      byte type = this.readType();

      try {
         switch (type) {
            case 1:
               return new Boolean(this.bis.readBoolean());
            case 2:
               return new Byte(this.bis.readByte());
            case 3:
               return new Character(this.bis.readChar());
            case 4:
               return new Double(this.bis.readDouble());
            case 5:
               return new Float(this.bis.readFloat());
            case 6:
               return new Integer(this.bis.readInt());
            case 7:
               return new Long(this.bis.readLong());
            case 8:
               return new Short(this.bis.readShort());
            case 9:
               return this.readStringInternal(type);
            case 10:
               return this.readStringInternal(type);
            case 11:
               if (this.readingByteArray) {
                  throw new javax.jms.MessageFormatException("Can not read next data. Previous attempt to read bytes from the stream message is not complete. As per the JMS standard, if the readBytes method does not return the value -1, a subsequent readBytes call must be made in order to ensure that there are no more bytes left to be read in. For more information, see the JMS API doc for the method readBytes in interface StreamMessage");
               } else {
                  int bytesLength = this.bis.readInt();
                  byte[] ba = new byte[bytesLength];
                  int n = this.bis.read(ba, 0, bytesLength);
                  if (n != bytesLength) {
                     throw new EOFException("");
                  }

                  return ba;
               }
            case 12:
               return null;
            default:
               this.bis.unput();
               throw new javax.jms.MessageFormatException(this.streamConversionError(this.typeCodeToString(type), "Object"));
         }
      } catch (EOFException var5) {
         javax.jms.MessageEOFException mee = new javax.jms.MessageEOFException(this.readPastEnd());
         mee.setLinkedException(var5);
         throw mee;
      } catch (IOException var6) {
         throw new JMSException(this.streamReadError(), var6);
      }
   }

   public void writeBoolean(boolean value) throws javax.jms.JMSException {
      this.writeType((byte)1);

      try {
         this.bos.writeBoolean(value);
      } catch (IOException var3) {
         throw new JMSException(this.streamWriteError(10), var3);
      }
   }

   public void writeByte(byte value) throws javax.jms.JMSException {
      this.writeType((byte)2);

      try {
         this.bos.writeByte(value);
      } catch (IOException var3) {
         throw new JMSException(this.streamWriteError(20), var3);
      }
   }

   public void writeShort(short value) throws javax.jms.JMSException {
      this.writeType((byte)8);

      try {
         this.bos.writeShort(value);
      } catch (IOException var3) {
         throw new JMSException(this.streamWriteError(30), var3);
      }
   }

   public void writeChar(char value) throws javax.jms.JMSException {
      this.writeType((byte)3);

      try {
         this.bos.writeChar(value);
      } catch (IOException var3) {
         throw new JMSException(this.streamWriteError(40), var3);
      }
   }

   public void writeInt(int value) throws javax.jms.JMSException {
      this.writeType((byte)6);

      try {
         this.bos.writeInt(value);
      } catch (IOException var3) {
         throw new JMSException(this.streamWriteError(50), var3);
      }
   }

   public void writeLong(long value) throws javax.jms.JMSException {
      this.writeType((byte)7);

      try {
         this.bos.writeLong(value);
      } catch (IOException var4) {
         throw new JMSException(this.streamWriteError(60), var4);
      }
   }

   public void writeFloat(float value) throws javax.jms.JMSException {
      this.writeType((byte)5);

      try {
         this.bos.writeFloat(value);
      } catch (IOException var3) {
         throw new JMSException(this.streamWriteError(70), var3);
      }
   }

   public void writeDouble(double value) throws javax.jms.JMSException {
      this.writeType((byte)4);

      try {
         this.bos.writeDouble(value);
      } catch (IOException var4) {
         throw new JMSException(this.streamWriteError(80), var4);
      }
   }

   public void writeString(String value) throws javax.jms.JMSException {
      if (value == null) {
         this.writeType((byte)12);
      } else {
         try {
            this.writeStringInternal(value);
         } catch (IOException var3) {
            throw new JMSException(this.streamWriteError(), var3);
         }
      }

   }

   public void writeBytes(byte[] value) throws javax.jms.JMSException {
      this.writeBytes(value, 0, value.length);
   }

   public void writeBytes(byte[] value, int offset, int length) throws javax.jms.JMSException {
      if (value == null) {
         throw new NullPointerException();
      } else {
         this.writeType((byte)11);

         try {
            this.bos.writeInt(length);
            this.bos.write(value, offset, length);
         } catch (IOException var5) {
            throw new JMSException(this.streamWriteError(100), var5);
         }
      }
   }

   public void writeObject(Object value) throws javax.jms.JMSException {
      if (value instanceof Boolean) {
         this.writeBoolean((Boolean)value);
      } else if (value instanceof Number) {
         if (value instanceof Byte) {
            this.writeByte((Byte)value);
         } else if (value instanceof Double) {
            this.writeDouble((Double)value);
         } else if (value instanceof Float) {
            this.writeFloat((Float)value);
         } else if (value instanceof Integer) {
            this.writeInt((Integer)value);
         } else if (value instanceof Long) {
            this.writeLong((Long)value);
         } else if (value instanceof Short) {
            this.writeShort((Short)value);
         }
      } else if (value instanceof Character) {
         this.writeChar((Character)value);
      } else if (value instanceof String) {
         this.writeString((String)value);
      } else if (value instanceof byte[]) {
         this.writeBytes((byte[])((byte[])value));
      } else {
         if (value != null) {
            throw new javax.jms.MessageFormatException("Invalid Type: " + value.getClass().getName());
         }

         this.writeType((byte)12);
      }

   }

   public void reset() throws javax.jms.JMSException {
      this.setBodyWritable(false);
      if (this.bis != null) {
         try {
            this.bis.reset();
         } catch (IOException var2) {
            throw new JMSException(this.streamReadError(217), var2);
         }
      } else if (this.bos != null) {
         this.payload = (PayloadStream)this.bos.moveToPayload();
         this.bos = null;
      }

      this.copyOnWrite = false;
   }

   public MessageImpl copy() throws javax.jms.JMSException {
      StreamMessageImpl message = new StreamMessageImpl();
      super.copy(message);
      if (this.bos != null) {
         message.payload = this.bos.copyPayloadWithoutSharedStream();
      } else if (this.payload != null) {
         message.payload = this.payload.copyPayloadWithoutSharedStream();
      }

      message.copyOnWrite = this.copyOnWrite = true;
      message.setBodyWritable(false);
      message.setPropertiesWritable(false);
      return message;
   }

   private void checkWritable() throws javax.jms.JMSException {
      super.writeMode();
      if (this.bos == null) {
         this.bos = PayloadFactoryImpl.createOutputStream();
      } else if (this.copyOnWrite) {
         this.bos.copyBuffer();
         this.copyOnWrite = false;
      }

   }

   private void checkReadable() throws javax.jms.JMSException {
      super.readMode();
      if (this.payload == null) {
         throw new javax.jms.MessageEOFException(this.readPastEnd3(500));
      } else {
         if (this.bis == null) {
            try {
               this.bis = this.payload.getInputStream();
            } catch (IOException var2) {
               throw new JMSException(this.streamReadError(510), var2);
            }
         }

      }
   }

   public String toString() {
      return "StreamMessage[" + this.getJMSMessageID() + "]";
   }

   public void writeExternal(ObjectOutput tOut) throws IOException {
      super.writeExternal(tOut);
      int compressionThreshold = Integer.MAX_VALUE;
      ObjectOutput out;
      if (tOut instanceof MessageImpl.JMSObjectOutputWrapper) {
         compressionThreshold = ((MessageImpl.JMSObjectOutputWrapper)tOut).getCompressionThreshold();
         out = ((MessageImpl.JMSObjectOutputWrapper)tOut).getInnerObjectOutput();
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
      byte unmaskedVersion = in.readByte();
      byte vrsn = (byte)(unmaskedVersion & 127);
      if (vrsn >= 1 && vrsn <= 3) {
         switch (vrsn) {
            case 1:
               this.payload = (PayloadStream)PayloadFactoryImpl.createPayload((InputStream)in);
               InputStream is = this.payload.getInputStream();
               ObjectInputStream ois = new FilteringObjectInputStream(is);
               this.setBodyWritable(true);
               this.setPropertiesWritable(true);

               try {
                  while(true) {
                     this.writeObject(ois.readObject());
                  }
               } catch (EOFException var9) {
                  try {
                     this.reset();
                     this.setPropertiesWritable(false);
                     PayloadStream replacement = this.payload.copyPayloadWithoutSharedStream();
                     this.payload = replacement;
                  } catch (javax.jms.JMSException var8) {
                     JMSClientExceptionLogger.logStackTrace(var8);
                  }
               } catch (javax.jms.MessageNotWriteableException var10) {
                  JMSClientExceptionLogger.logStackTrace(var10);
               } catch (javax.jms.MessageFormatException var11) {
                  JMSClientExceptionLogger.logStackTrace(var11);
               } catch (javax.jms.JMSException var12) {
                  JMSClientExceptionLogger.logStackTrace(var12);
               }
               break;
            case 3:
               if ((unmaskedVersion & -128) != 0) {
                  this.readExternalCompressedMessageBody(in);
                  break;
               }
            case 2:
               this.payload = (PayloadStream)PayloadFactoryImpl.createPayload((InputStream)in);
         }

      } else {
         throw JMSUtilities.versionIOException(vrsn, 1, 3);
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

   private String typeCodeToString(int typeCode) {
      try {
         return TYPE_CODE_STRINGS[typeCode];
      } catch (Throwable var3) {
         return TYPE_CODE_STRINGS[0];
      }
   }

   private void writeStringInternal(String s) throws IOException, javax.jms.JMSException {
      if (s.length() > 20000) {
         this.writeType((byte)10);
         this.bos.writeUTF32(s);
      } else {
         this.writeType((byte)9);
         this.bos.writeUTF(s);
      }

   }

   private String readStringInternal(byte type) throws IOException {
      return type == 10 ? this.bis.readUTF32() : this.bis.readUTF();
   }

   private long getLen() {
      if (this.bos != null) {
         return (long)this.bos.size();
      } else {
         return this.payload != null ? (long)this.payload.getLength() : 0L;
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
      if (this.payload == null && this.bis == null && this.bos == null && !this.copyOnWrite) {
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

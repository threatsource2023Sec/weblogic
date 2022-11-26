package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import javax.jms.TextMessage;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.utils.io.FilteringObjectInputStream;
import weblogic.utils.io.StringOutput;

public final class TextMessageImpl extends MessageImpl implements TextMessage, Externalizable {
   private static final byte EXTVERSION1 = 1;
   private static final byte EXTVERSION2 = 2;
   private static final byte EXTVERSION3 = 3;
   private static final byte VERSIONMASK = 127;
   static final long serialVersionUID = 5844425982189539558L;
   private String text;
   private PayloadText payload;
   private byte subflag;
   private static final boolean mydebug = false;
   private static final boolean SHOWMSGONDBG = System.getProperty("weblogic.jms.debug.DeserializeTextMessageContents", "false").equals("true");

   public TextMessageImpl() {
   }

   public TextMessageImpl(TextMessage message) throws javax.jms.JMSException {
      this(message, (javax.jms.Destination)null, (javax.jms.Destination)null);
   }

   public TextMessageImpl(TextMessage message, javax.jms.Destination destination, javax.jms.Destination replyDestination) throws javax.jms.JMSException {
      super(message, destination, replyDestination);
      this.setText(message.getText());
   }

   public byte getType() {
      return 6;
   }

   public TextMessageImpl(String text) {
      this.text = text;
   }

   public void setText(String text) throws javax.jms.JMSException {
      this.writeMode();
      this.payload = null;
      this.text = text;
   }

   public void setUTF8Buffer(PayloadText payload) {
      if (this.text != null) {
         throw new AssertionError();
      } else {
         this.payload = payload;
         this.subflag = 2;
      }
   }

   public String getText() throws javax.jms.JMSException {
      if (this.text != null) {
         return this.text;
      } else {
         this.decompressMessageBody();
         if (this.text != null) {
            return this.text;
         } else {
            if (this.payload != null) {
               try {
                  if ((this.subflag & 2) != 0) {
                     this.text = this.payload.readUTF8();
                  } else {
                     ObjectInputStream ois = new FilteringObjectInputStream(this.payload.getInputStream());
                     this.text = (String)ois.readObject();
                  }

                  this.payload = null;
               } catch (IOException var2) {
                  throw new JMSException(var2);
               } catch (ClassNotFoundException var3) {
                  throw new JMSException(var3);
               }
            }

            return this.text;
         }
      }
   }

   private final String interopDecompressMessageBody() throws IOException {
      String data = null;

      try {
         data = this.interopMessageBody((PayloadText)this.decompress());
         return data;
      } catch (IOException var3) {
         throw new IOException(JMSClientExceptionLogger.logErrorInteropTextMessageLoggable().getMessage());
      }
   }

   private final String interopMessageBody(PayloadText localPayload) throws IOException {
      if ((this.subflag & 2) != 0) {
         return localPayload.readUTF8();
      } else {
         String data = null;

         try {
            ObjectInputStream ouinput = new FilteringObjectInputStream(localPayload.getInputStream());
            data = (String)ouinput.readObject();
            return data;
         } catch (IOException var4) {
            throw new IOException(JMSClientExceptionLogger.logErrorInteropTextMessageLoggable().getMessage());
         } catch (ClassNotFoundException var5) {
            throw new IOException(JMSClientExceptionLogger.logErrorInteropTextMessageLoggable().getMessage());
         }
      }
   }

   public final Object getMessageBody() throws javax.jms.JMSException {
      if (!this.isCompressed()) {
         return this.payload != null ? this.payload : this.text;
      } else if (this.text != null) {
         return this.text;
      } else {
         try {
            PayloadText localPayload = (PayloadText)this.decompress();
            if ((this.subflag & 2) != 0) {
               return localPayload;
            } else {
               ObjectInputStream ouinput = new FilteringObjectInputStream(localPayload.getInputStream());
               return (String)ouinput.readObject();
            }
         } catch (IOException var3) {
            throw new JMSException(JMSClientExceptionLogger.logErrorDeserializeMessageBodyLoggable().getMessage(), var3);
         } catch (ClassNotFoundException var4) {
            throw new JMSException(JMSClientExceptionLogger.logErrorDeserializeMessageBodyLoggable().getMessage(), var4);
         }
      }
   }

   public final void decompressMessageBody() throws javax.jms.JMSException {
      if (this.isCompressed()) {
         try {
            this.payload = (PayloadText)this.decompress();
            if ((this.subflag & 2) != 0) {
               this.text = this.payload.readUTF8();
            } else {
               ObjectInputStream ois = new FilteringObjectInputStream(this.payload.getInputStream());
               this.text = (String)ois.readObject();
            }
         } catch (IOException var6) {
            throw new JMSException(JMSClientExceptionLogger.logErrorDeserializeMessageBodyLoggable().getMessage(), var6);
         } catch (ClassNotFoundException var7) {
            throw new JMSException(JMSClientExceptionLogger.logErrorDeserializeMessageBodyLoggable().getMessage(), var7);
         } finally {
            this.cleanupCompressedMessageBody();
         }

      }
   }

   public void nullBody() {
      this.text = null;
      this.payload = null;
   }

   public String toString() {
      String text = this.text;

      try {
         if (text == null && (this.isCompressed() || this.payload != null)) {
            if (!SHOWMSGONDBG) {
               return "TextMessage[" + this.getJMSMessageID() + ", [TextMessage Compressed]";
            }

            text = this.getText();
         }
      } catch (javax.jms.JMSException var3) {
      }

      return "TextMessage[" + this.getJMSMessageID() + ", " + (text == null ? "null" : (text.length() < 40 ? text : text.substring(0, 30) + "...")) + "]";
   }

   public void writeExternal(ObjectOutput tOut) throws IOException {
      super.writeExternal(tOut);
      int compressionThreshold = Integer.MAX_VALUE;
      boolean readStringAsObject = true;
      ObjectOutput out;
      if (tOut instanceof MessageImpl.JMSObjectOutputWrapper) {
         compressionThreshold = ((MessageImpl.JMSObjectOutputWrapper)tOut).getCompressionThreshold();
         out = ((MessageImpl.JMSObjectOutputWrapper)tOut).getInnerObjectOutput();
         readStringAsObject = ((MessageImpl.JMSObjectOutputWrapper)tOut).getReadStringAsObject();
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
         this.debugWireProtocol(flag, "TextMessageImpl.write");
      }

      String str;
      if ((flag & 127) == 3) {
         if (this.isCompressed()) {
            if (this.needToDecompressDueToInterop(out)) {
               out.writeBoolean(true);
               str = this.interopDecompressMessageBody();
               if (out instanceof StringOutput) {
                  out.writeByte(2 | (readStringAsObject ? 128 : 0));
                  ((StringOutput)out).writeUTF8(str);
               } else {
                  out.writeByte(4);
                  out.writeObject(str);
               }
            } else {
               out.writeByte(this.subflag);
               this.flushCompressedMessageBody(out);
            }
         } else if ((flag & -128) != 0) {
            if (this.text != null) {
               out.writeByte(4);
               this.writeExternalCompressPayload(out, PayloadFactoryImpl.convertObjectToPayload(this.text));
            } else {
               out.writeByte(2);
               this.writeExternalCompressPayload(out, this.payload);
            }
         } else if (this.payload != null) {
            out.writeBoolean(true);
            if (out instanceof StringOutput) {
               out.writeByte(2 | (readStringAsObject ? 128 : 0));
            } else {
               out.writeByte(2);
            }

            this.payload.writeLengthAndData(out);
         } else if (this.text != null) {
            out.writeBoolean(true);
            if (out instanceof StringOutput) {
               out.writeByte(2 | (readStringAsObject ? 128 : 0));
               ((StringOutput)out).writeUTF8(this.text);
            } else {
               out.writeByte(4);
               out.writeObject(this.text);
            }
         } else {
            out.writeBoolean(false);
         }
      } else {
         if (this.text != null && this.text.length() > 0) {
            str = this.text;
         } else if (this.payload != null) {
            str = this.interopMessageBody(this.payload);
         } else if (this.isCompressed()) {
            str = this.interopDecompressMessageBody();
         } else {
            str = null;
         }

         if (str != null) {
            out.writeBoolean(true);
            out.writeObject(str);
         } else {
            out.writeBoolean(false);
         }
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      boolean readStringAsObject = false;
      super.readExternal(in);
      byte unmaskedVrsn = in.readByte();
      byte vrsn = (byte)(unmaskedVrsn & 127);
      if (debugWire && JMSDebug.JMSDispatcher.isDebugEnabled()) {
         this.debugWireProtocol(unmaskedVrsn, "TextMessageImpl.read ");
      }

      if (vrsn == 1) {
         if (in.readBoolean()) {
            this.text = in.readUTF();
         }

      } else if (vrsn == 2) {
         if (in.readBoolean()) {
            this.text = (String)in.readObject();
         }

      } else if (vrsn != 3) {
         throw JMSUtilities.versionIOException(vrsn, 1, 3);
      } else if ((unmaskedVrsn & -128) != 0) {
         this.subflag = in.readByte();
         this.readExternalCompressedMessageBody(in);
      } else if (in.readBoolean()) {
         byte mysubflag = in.readByte();
         if ((mysubflag & 2) != 0) {
            this.payload = (PayloadText)PayloadFactoryImpl.createPayload((InputStream)in);
            this.subflag = -126;
         } else {
            this.text = (String)in.readObject();
         }

      }
   }

   private void debugWireProtocol(byte unmaskedVrsn, String prefix) {
      JMSDebug.JMSDispatcher.debug(prefix + " versionInt 0x" + Integer.toHexString(unmaskedVrsn).toUpperCase() + ((unmaskedVrsn & -128) != 0 ? " compress on" : " compress off"));
   }

   public MessageImpl copy() throws javax.jms.JMSException {
      TextMessageImpl tmi = new TextMessageImpl();
      this.copy(tmi);
      tmi.text = this.text;
      if (this.payload != null) {
         tmi.payload = this.payload.copyPayloadWithoutSharedText();
      }

      tmi.subflag = this.subflag;
      tmi.setBodyWritable(false);
      tmi.setPropertiesWritable(false);
      return tmi;
   }

   public long getPayloadSize() {
      if (super.bodySize != -1L) {
         return super.bodySize;
      } else if (this.isCompressed()) {
         return super.bodySize = (long)this.getCompressedMessageBodySize();
      } else if (this.text != null) {
         return super.bodySize = (long)this.text.length();
      } else {
         return this.payload != null ? (super.bodySize = (long)this.payload.getLength()) : (super.bodySize = 0L);
      }
   }
}

package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import javax.jms.MapMessage;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.utils.io.FilteringObjectInputStream;

public final class MapMessageImpl extends MessageImpl implements MapMessage, Externalizable {
   private static final byte EXTVERSION = 1;
   private static final byte EXTVERSION2 = 2;
   private static final byte VERSIONMASK = 127;
   static final long serialVersionUID = -3363325517439700010L;
   private HashMap hashMap;
   private PayloadStream payload;
   private PeerInfo peerInfo;

   public MapMessageImpl() {
      this.peerInfo = PeerInfo.getPeerInfo();
   }

   public MapMessageImpl(MapMessage message) throws javax.jms.JMSException {
      this(message, (javax.jms.Destination)null, (javax.jms.Destination)null);
   }

   public MapMessageImpl(MapMessage message, javax.jms.Destination destination, javax.jms.Destination replyDestination) throws javax.jms.JMSException {
      super(message, destination, replyDestination);
      Enumeration keys = message.getMapNames();
      if (keys.hasMoreElements()) {
         this.hashMap = new HashMap();

         do {
            String name = (String)keys.nextElement();
            this.hashMap.put(name, message.getObject(name));
         } while(keys.hasMoreElements());
      }

      this.peerInfo = PeerInfo.getPeerInfo();
   }

   public byte getType() {
      return 3;
   }

   public boolean getBoolean(String name) throws javax.jms.JMSException {
      return TypeConverter.toBoolean(this.getObject(name));
   }

   public byte getByte(String name) throws javax.jms.JMSException {
      return TypeConverter.toByte(this.getObject(name));
   }

   public byte[] getBytes(String name) throws javax.jms.JMSException {
      return TypeConverter.toByteArray(this.getObject(name));
   }

   public char getChar(String name) throws javax.jms.JMSException {
      return TypeConverter.toChar(this.getObject(name));
   }

   public double getDouble(String name) throws javax.jms.JMSException {
      return TypeConverter.toDouble(this.getObject(name));
   }

   public float getFloat(String name) throws javax.jms.JMSException {
      return TypeConverter.toFloat(this.getObject(name));
   }

   public short getShort(String name) throws javax.jms.JMSException {
      return TypeConverter.toShort(this.getObject(name));
   }

   public int getInt(String name) throws javax.jms.JMSException {
      return TypeConverter.toInt(this.getObject(name));
   }

   public long getLong(String name) throws javax.jms.JMSException {
      return TypeConverter.toLong(this.getObject(name));
   }

   public String getString(String name) throws javax.jms.JMSException {
      return TypeConverter.toString(this.getObject(name));
   }

   public Object getObject(String name) throws javax.jms.JMSException {
      return this.getHashMap().get(name);
   }

   public Enumeration getMapNames() throws javax.jms.JMSException {
      return Collections.enumeration(this.getHashMap().keySet());
   }

   public void setBoolean(String name, boolean value) throws javax.jms.JMSException {
      this.setObjectInternal(name, new Boolean(value));
   }

   public void setByte(String name, byte value) throws javax.jms.JMSException {
      this.setObjectInternal(name, new Byte(value));
   }

   public void setBytes(String name, byte[] value) throws javax.jms.JMSException {
      this.setObjectInternal(name, value);
   }

   public void setBytes(String name, byte[] value, int offset, int length) throws javax.jms.JMSException {
      byte[] copy = new byte[length];
      System.arraycopy(value, offset, copy, 0, length);
      this.setObjectInternal(name, copy);
   }

   public void setChar(String name, char value) throws javax.jms.JMSException {
      this.setObjectInternal(name, new Character(value));
   }

   public void setDouble(String name, double value) throws javax.jms.JMSException {
      this.setObjectInternal(name, new Double(value));
   }

   public void setFloat(String name, float value) throws javax.jms.JMSException {
      this.setObjectInternal(name, new Float(value));
   }

   public void setInt(String name, int value) throws javax.jms.JMSException {
      this.setObjectInternal(name, new Integer(value));
   }

   public void setLong(String name, long value) throws javax.jms.JMSException {
      this.setObjectInternal(name, new Long(value));
   }

   public void setObject(String name, Object value) throws javax.jms.JMSException {
      if (!(value instanceof Number) && !(value instanceof String) && !(value instanceof Boolean) && !(value instanceof byte[]) && !(value instanceof Character) && value != null) {
         throw new javax.jms.MessageFormatException(JMSClientExceptionLogger.logInvalidDataTypeLoggable(value.getClass().getName()).getMessage());
      } else {
         this.setObjectInternal(name, value);
      }
   }

   public void setShort(String name, short value) throws javax.jms.JMSException {
      this.setObjectInternal(name, new Short(value));
   }

   public void setString(String name, String value) throws javax.jms.JMSException {
      this.setObjectInternal(name, value);
   }

   private void setObjectInternal(String name, Object value) throws javax.jms.JMSException {
      this.writeMode();
      if (name != null && name.length() != 0) {
         this.getHashMap().put(name, value);
      } else {
         throw new IllegalArgumentException(JMSClientExceptionLogger.logIllegalNameLoggable(name).getMessage());
      }
   }

   public boolean itemExists(String name) throws javax.jms.JMSException {
      return this.getHashMap().containsKey(name);
   }

   public void nullBody() {
      this.payload = null;
      this.hashMap = null;
   }

   public String toString() {
      return "MapMessage[" + this.getJMSMessageID() + "]";
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

      PeerInfo peerInfo;
      if (out instanceof PeerInfoable) {
         peerInfo = ((PeerInfoable)out).getPeerInfo();
      } else {
         peerInfo = PeerInfo.getPeerInfo();
      }

      PayloadStream localPayload;
      if (!this.isCompressed()) {
         synchronized(this) {
            localPayload = this.getMapPayload(peerInfo);
            if (this.payload == null) {
               this.payload = localPayload;
               this.peerInfo = peerInfo;
               this.hashMap = null;
            }
         }
      } else {
         localPayload = null;
      }

      byte flag;
      if (this.getVersion(out) >= 30) {
         if (this.needToDecompressDueToInterop(out)) {
            flag = 2;
         } else {
            flag = (byte)(2 | (this.shouldCompress(out, compressionThreshold) ? -128 : 0));
         }
      } else {
         flag = 1;
      }

      out.writeByte(flag);
      if (this.isCompressed()) {
         if (flag == 1) {
            this.decompress().writeLengthAndData(out);
         } else if (this.needToDecompressDueToInterop(out)) {
            this.decompress().writeLengthAndData(out);
         } else {
            this.flushCompressedMessageBody(out);
         }
      } else if ((flag & -128) != 0) {
         this.writeExternalCompressPayload(out, localPayload);
      } else {
         localPayload.writeLengthAndData(out);
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
      if (vrsn >= 1 && vrsn <= 2) {
         this.peerInfo = PeerInfo.getPeerInfo();
         if (in instanceof PeerInfoable) {
            PeerInfo peerInfo = ((PeerInfoable)in).getPeerInfo();
            if (peerInfo != null && peerInfo.compareTo(this.peerInfo) < 0) {
               this.peerInfo = peerInfo;
            }
         }

         if ((unmaskedVersion & -128) != 0) {
            this.readExternalCompressedMessageBody(in);
         } else {
            this.payload = (PayloadStream)PayloadFactoryImpl.createPayload((InputStream)in);
         }

      } else {
         throw JMSUtilities.versionIOException(vrsn, 1, 2);
      }
   }

   public MessageImpl copy() throws javax.jms.JMSException {
      MapMessageImpl mmi = new MapMessageImpl();
      this.copy(mmi);

      try {
         mmi.peerInfo = PeerInfo.getPeerInfo();
         PayloadStream localPayload = this.getMapPayload(mmi.peerInfo);
         mmi.payload = localPayload.copyPayloadWithoutSharedStream();
      } catch (IOException var3) {
         throw new JMSException(JMSClientExceptionLogger.logCopyErrorLoggable().getMessage(), var3);
      }

      mmi.setBodyWritable(false);
      mmi.setPropertiesWritable(false);
      return mmi;
   }

   public long getPayloadSize() {
      if (this.isCompressed()) {
         return (long)this.getCompressedMessageBodySize();
      } else {
         return super.bodySize != -1L ? super.bodySize : (super.bodySize = this.payload == null ? 0L : (long)this.payload.getLength());
      }
   }

   private HashMap getHashMap() throws javax.jms.JMSException {
      if (this.hashMap != null) {
         return this.hashMap;
      } else {
         this.decompressMessageBody();
         if (this.payload == null) {
            this.hashMap = new HashMap();
         } else {
            try {
               ObjectInputStream ois = new FilteringObjectInputStream(this.payload.getInputStream());
               this.hashMap = JMSUtilities.readBigStringBasicMap(ois);
            } catch (IOException var2) {
               throw new JMSException(JMSClientExceptionLogger.logDeserializationErrorLoggable().getMessage(), var2);
            }
         }

         this.payload = null;
         return this.hashMap;
      }
   }

   private PayloadStream getMapPayload(PeerInfo peerInfo) throws IOException {
      HashMap hashMap = this.hashMap;
      if (this.payload != null) {
         if (this.peerInfo != null && this.peerInfo.equals(peerInfo)) {
            return this.payload;
         }

         ObjectInputStream ois = new FilteringObjectInputStream(this.payload.getInputStream());
         hashMap = JMSUtilities.readBigStringBasicMap(ois);
      } else if (hashMap == null) {
         hashMap = new HashMap();
      }

      BufferOutputStream bos = PayloadFactoryImpl.createOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(bos);
      JMSUtilities.writeBigStringBasicMap(oos, hashMap, peerInfo, true);
      oos.flush();
      return (PayloadStream)bos.moveToPayload();
   }
}

package weblogic.jms.common;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.VersionInfoFactory;

final class PrimitiveObjectMap {
   private HashMap map;
   PayloadStream payload;
   private static PeerInfo LATEST_PEER_INFO = VersionInfoFactory.getPeerInfo();

   PrimitiveObjectMap() {
   }

   PrimitiveObjectMap(DataInput in, int messageVersion) throws IOException {
      if (messageVersion >= 30) {
         this.payload = (PayloadStream)PayloadFactoryImpl.createPayload((InputStream)in);
      } else {
         this.map = JMSUtilities.readBasicMap(in);
      }

   }

   PrimitiveObjectMap(PrimitiveObjectMap origMap) throws javax.jms.JMSException {
      synchronized(origMap) {
         try {
            origMap.ensurePayload();
         } catch (IOException var5) {
            throw new JMSException(var5);
         }

         this.payload = origMap.payload.copyPayloadWithoutSharedStream();
      }
   }

   public Set entrySet() throws javax.jms.JMSException {
      this.ensureMap();
      return this.map.entrySet();
   }

   public Set keySet() throws javax.jms.JMSException {
      this.ensureMap();
      return this.map.keySet();
   }

   public Object put(Object name, Object value) throws javax.jms.JMSException {
      this.ensureMap();
      this.invalidatePayload();
      return this.map.put(name, value);
   }

   public Object get(Object name) throws javax.jms.JMSException {
      this.ensureMap();
      return this.map.get(name);
   }

   public Object remove(Object name) throws javax.jms.JMSException {
      this.ensureMap();
      Object ret = this.map.remove(name);
      if (ret != null) {
         this.invalidatePayload();
      }

      return ret;
   }

   public boolean containsKey(Object name) throws javax.jms.JMSException {
      this.ensureMap();
      return this.map.containsKey(name);
   }

   public boolean isEmpty() {
      if (this.map != null) {
         return this.map.isEmpty();
      } else {
         return this.payload == null;
      }
   }

   public int getSizeInBytes() throws javax.jms.JMSException {
      try {
         this.ensurePayload();
      } catch (IOException var2) {
         throw new JMSException(var2);
      }

      return this.payload.getLength();
   }

   void writeToStream(DataOutput out, int messageVersion) throws IOException {
      if (messageVersion >= 30) {
         synchronized(this) {
            this.ensurePayload();
            this.payload.writeLengthAndData(out);
         }
      } else {
         this.writeToStream(out, LATEST_PEER_INFO);
      }

   }

   void writeToStream(DataOutput out, PeerInfo clientPeerInfo) throws IOException {
      try {
         this.ensureMap();
      } catch (javax.jms.JMSException var5) {
         IOException ioe = new IOException(var5.toString());
         ioe.initCause(var5);
         throw ioe;
      }

      JMSUtilities.writeBasicMap(out, this.map, clientPeerInfo);
   }

   private void invalidatePayload() {
      synchronized(this) {
         this.payload = null;
      }
   }

   private synchronized void ensureMap() throws javax.jms.JMSException {
      if (this.map == null) {
         if (this.payload != null) {
            try {
               this.map = JMSUtilities.readBasicMap(this.payload.getInputStream());
            } catch (IOException var2) {
               throw new JMSException(var2);
            }
         } else {
            this.map = new HashMap();
         }
      }

   }

   private synchronized void ensurePayload() throws IOException {
      if (this.payload == null) {
         BufferOutputStream bos = PayloadFactoryImpl.createOutputStream();
         JMSUtilities.writeBasicMap(bos, this.map, LATEST_PEER_INFO);
         bos.close();
         this.payload = (PayloadStream)bos.moveToPayload();
      }

   }
}

package weblogic.jms.dotnet.proxy.protocol;

import java.util.Enumeration;
import java.util.Iterator;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyMapMessageImpl extends ProxyMessageImpl {
   private static final int EXTVERSION = 1;
   private static final int _HAS_DATA = 1;
   private PrimitiveMap map;
   private transient String contentStr;

   public ProxyMapMessageImpl() {
   }

   public ProxyMapMessageImpl(PrimitiveMap map) {
      this.map = map;
   }

   public ProxyMapMessageImpl(MapMessage message) throws JMSException {
      super(message);
      this.copyMap(message);
   }

   private void copyMap(MapMessage message) throws JMSException {
      Enumeration keys = message.getMapNames();
      if (keys.hasMoreElements()) {
         this.map = new PrimitiveMap();

         do {
            String name = (String)keys.nextElement();
            this.map.put(name, message.getObject(name));
         } while(keys.hasMoreElements());
      }

   }

   public byte getType() {
      return 3;
   }

   public boolean itemExists(String name) throws JMSException {
      return this.map.containsKey(name);
   }

   public String toString() {
      if (this.contentStr == null) {
         this.contentStr = "";

         String key;
         Object value;
         for(Iterator itr = this.map.keySet().iterator(); itr.hasNext(); this.contentStr = this.contentStr + "(" + key + "," + value + ")") {
            key = (String)itr.next();
            value = this.map.get(key);
         }
      }

      return "MapMessage[" + this.getMessageID() + this.contentStr + "]";
   }

   public void populateJMSMessage(MapMessage msg) throws JMSException {
      super.populateJMSMessage(msg);
      if (this.map != null) {
         Iterator props = this.map.keySet().iterator();

         while(props.hasNext()) {
            String name = (String)props.next();
            Object obj = this.map.get(name);
            msg.setObject(name, obj);
         }
      }

   }

   public int getMarshalTypeCode() {
      return 37;
   }

   public void marshal(MarshalWriter mw) {
      super.marshal(mw);
      MarshalBitMask versionFlags = new MarshalBitMask(1);
      if (this.map != null) {
         versionFlags.setBit(1);
      }

      versionFlags.marshal(mw);
      if (this.map != null) {
         this.map.marshal(mw);
      }

   }

   public void unmarshal(MarshalReader mr) {
      super.unmarshal(mr);
      MarshalBitMask versionFlags = new MarshalBitMask();
      versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(versionFlags.getVersion(), 1, 1);
      if (versionFlags.isSet(1)) {
         this.map = new PrimitiveMap();
         this.map.unmarshal(mr);
      }

   }
}

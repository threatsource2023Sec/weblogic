package weblogic.jms.dotnet.proxy.protocol;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import weblogic.jms.common.ObjectMessageImpl;
import weblogic.jms.common.PayloadFactoryImpl;
import weblogic.jms.common.PayloadStream;
import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyObjectMessageImpl extends ProxyMessageImpl {
   private static final int EXTVERSION = 1;
   private static final int _HAS_DATA = 1;
   private PayloadStream payload;

   public ProxyObjectMessageImpl() {
   }

   public ProxyObjectMessageImpl(PayloadStream ps) {
      this.payload = ps;
   }

   public ProxyObjectMessageImpl(ObjectMessage message) throws JMSException {
      super(message);
      this.payload = ((ObjectMessageImpl)message).getMessageBody();
   }

   public byte getType() {
      return 4;
   }

   public String toString() {
      return "ObjectMessage[" + this.getMessageID() + " bytes = " + this.payload + "]";
   }

   public void populateJMSMessage(ObjectMessage msg) throws JMSException {
      super.populateJMSMessage(msg);
      ((ObjectMessageImpl)msg).setBodyBytes(this.payload);
   }

   public int getMarshalTypeCode() {
      return 39;
   }

   public void marshal(MarshalWriter mw) {
      super.marshal(mw);
      MarshalBitMask versionFlags = new MarshalBitMask(1);
      if (this.payload != null && this.payload.getLength() != 0) {
         versionFlags.setBit(1);
      }

      versionFlags.marshal(mw);
      if (versionFlags.isSet(1)) {
         try {
            this.payload.writeLengthAndData(mw.getDataOutputStream());
         } catch (IOException var4) {
            throw new RuntimeException(var4);
         }
      }

   }

   public void unmarshal(MarshalReader mr) {
      super.unmarshal(mr);
      MarshalBitMask versionFlags = new MarshalBitMask();
      versionFlags.unmarshal(mr);
      int version = versionFlags.getVersion();
      ProxyUtil.checkVersion(versionFlags.getVersion(), 1, 1);
      if (versionFlags.isSet(1)) {
         try {
            DataInput dis = mr.getDataInputStream();
            this.payload = (PayloadStream)PayloadFactoryImpl.createPayload((InputStream)dis);
         } catch (IOException var5) {
            throw new RuntimeException(var5);
         }
      }

   }
}

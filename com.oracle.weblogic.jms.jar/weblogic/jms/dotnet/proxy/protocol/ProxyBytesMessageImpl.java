package weblogic.jms.dotnet.proxy.protocol;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import weblogic.jms.common.BytesMessageImpl;
import weblogic.jms.common.PayloadFactoryImpl;
import weblogic.jms.common.PayloadStream;
import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyBytesMessageImpl extends ProxyMessageImpl {
   private static final int EXTVERSION = 1;
   private static final int _HAS_DATA = 1;
   private PayloadStream payload;

   public ProxyBytesMessageImpl() {
   }

   public ProxyBytesMessageImpl(BytesMessage message) throws JMSException {
      super(message);
      this.payload = ((BytesMessageImpl)message).getPayload();
   }

   public byte getType() {
      return 1;
   }

   public void populateJMSMessage(BytesMessage msg) throws JMSException {
      super.populateJMSMessage(msg);
      ((BytesMessageImpl)msg).setPayload(this.payload);
   }

   public String toString() {
      return "BytesMessage[" + this.getMessageID() + " payload = " + this.payload + "]";
   }

   public int getMarshalTypeCode() {
      return 36;
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
      ProxyUtil.checkVersion(versionFlags.getVersion(), 1, 1);
      if (versionFlags.isSet(1)) {
         try {
            DataInput dis = mr.getDataInputStream();
            this.payload = (PayloadStream)PayloadFactoryImpl.createPayload((InputStream)dis);
         } catch (IOException var4) {
            throw new RuntimeException(var4);
         }
      }

   }
}

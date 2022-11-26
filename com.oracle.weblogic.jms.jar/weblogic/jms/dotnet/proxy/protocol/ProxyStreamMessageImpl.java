package weblogic.jms.dotnet.proxy.protocol;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import javax.jms.JMSException;
import javax.jms.StreamMessage;
import weblogic.jms.common.PayloadFactoryImpl;
import weblogic.jms.common.PayloadStream;
import weblogic.jms.common.StreamMessageImpl;
import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyStreamMessageImpl extends ProxyMessageImpl {
   private static final int EXTVERSION = 1;
   private static final int _HAS_DATA = 1;
   private PayloadStream payload;

   public ProxyStreamMessageImpl() {
   }

   public ProxyStreamMessageImpl(StreamMessage message) throws JMSException {
      super(message);
      this.payload = ((StreamMessageImpl)message).getPayload();
   }

   public byte getType() {
      return 5;
   }

   public void populateJMSMessage(StreamMessage msg) throws JMSException {
      super.populateJMSMessage(msg);
      ((StreamMessageImpl)msg).setPayload(this.payload);
   }

   public String toString() {
      return "StreamMessage[" + this.getMessageID() + " payload = " + this.payload + "]";
   }

   public int getMarshalTypeCode() {
      return 38;
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

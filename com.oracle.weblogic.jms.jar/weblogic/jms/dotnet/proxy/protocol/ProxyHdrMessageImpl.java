package weblogic.jms.dotnet.proxy.protocol;

import javax.jms.JMSException;
import javax.jms.Message;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyHdrMessageImpl extends ProxyMessageImpl {
   public ProxyHdrMessageImpl() {
   }

   public ProxyHdrMessageImpl(Message message) throws JMSException {
      super(message);
   }

   public byte getType() {
      return 2;
   }

   public void populateJMSMessage(Message msg) throws JMSException {
      super.populateJMSMessage(msg);
   }

   public int getMarshalTypeCode() {
      return 48;
   }

   public void marshal(MarshalWriter mw) {
      super.marshal(mw);
   }

   public void unmarshal(MarshalReader mr) {
      super.unmarshal(mr);
   }
}

package weblogic.jms.dotnet.proxy.protocol;

import javax.jms.JMSException;
import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;
import weblogic.jms.dotnet.transport.TransportError;

public final class ProxyPushExceptionRequest extends ProxyResponse {
   private static final int EXTVERSION = 1;
   private TransportError error;

   public ProxyPushExceptionRequest(JMSException exception) {
      this.error = new TransportError(exception);
   }

   public TransportError getTransportError() {
      return this.error;
   }

   public ProxyPushExceptionRequest() {
   }

   public int getMarshalTypeCode() {
      return 43;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      this.versionFlags.marshal(mw);
      this.error.marshal(mw);
   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      this.error = new TransportError();
      this.error.unmarshal(mr);
   }
}

package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyVoidResponse extends ProxyResponse {
   public static final ProxyVoidResponse THE_ONE = new ProxyVoidResponse();

   public int getMarshalTypeCode() {
      return 46;
   }

   public void marshal(MarshalWriter mw) {
   }

   public void unmarshal(MarshalReader mr) {
   }
}

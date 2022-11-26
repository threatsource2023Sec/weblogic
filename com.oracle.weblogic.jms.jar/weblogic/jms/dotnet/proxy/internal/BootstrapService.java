package weblogic.jms.dotnet.proxy.internal;

import javax.jms.JMSException;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.dotnet.proxy.protocol.ProxyBootstrapResponse;
import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.ReceivedTwoWay;
import weblogic.jms.dotnet.transport.ServiceTwoWay;
import weblogic.jms.dotnet.transport.Transport;
import weblogic.jms.dotnet.transport.TransportError;

public class BootstrapService implements ServiceTwoWay {
   private static final ProxyManagerImpl manager = ProxyManagerImpl.getProxyManager();

   public final void invoke(ReceivedTwoWay rr) {
      if (manager.isShutdown()) {
         rr.send(new TransportError(new JMSException("The JMS service is shutting down")));
      } else {
         MarshalWritable response = null;
         MarshalReadable request = rr.getRequest();
         switch (request.getMarshalTypeCode()) {
            case 20000:
               if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
                  JMSDebug.JMSDotNetProxy.debug("Got bootstrap request -- ");
               }

               Transport transport = rr.getTransport();
               transport.addMarshalReadableFactory(manager);
               transport.registerService(10004L, manager);
               response = new ProxyBootstrapResponse(10004L);
               break;
            default:
               response = new TransportError("Invalid MarshalReadableType : " + request.getMarshalTypeCode(), false);
         }

         rr.send((MarshalWritable)response);
      }
   }

   public void onPeerGone(TransportError te) {
   }

   public void onShutdown() {
   }

   public void onUnregister() {
   }
}

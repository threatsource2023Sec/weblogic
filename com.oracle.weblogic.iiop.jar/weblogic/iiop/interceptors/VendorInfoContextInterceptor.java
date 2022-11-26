package weblogic.iiop.interceptors;

import javax.validation.constraints.NotNull;
import weblogic.iiop.EndPoint;
import weblogic.iiop.contexts.ServiceContextList;
import weblogic.iiop.contexts.VendorInfo;
import weblogic.iiop.protocol.CorbaInputStream;

public class VendorInfoContextInterceptor implements ServerContextInterceptor {
   public void handleReceivedRequest(@NotNull ServiceContextList serviceContexts, @NotNull EndPoint endPoint, CorbaInputStream inputStream) {
      VendorInfo vendorInfo = (VendorInfo)serviceContexts.getServiceContext(1111834880);
      if (vendorInfo != null && endPoint.getPeerInfo() == null) {
         endPoint.setPeerInfo(vendorInfo.getPeerInfo());
      }

   }
}

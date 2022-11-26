package weblogic.rmi.internal;

import java.io.IOException;
import java.rmi.RemoteException;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.OutboundResponse;

public abstract class Skeleton {
   protected Skeleton() {
   }

   public abstract OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception;

   protected abstract Object invoke(int var1, Object[] var2, Object var3) throws Exception;

   protected void associateResponseData(InboundRequest request, OutboundResponse response) throws RemoteException {
      if (response != null) {
         try {
            response.transferThreadLocalContext(request);
         } catch (IOException var4) {
            throw new RemoteException(var4.getMessage(), var4);
         }
      }

   }
}

package weblogic.rmi.internal;

import java.rmi.UnmarshalException;
import weblogic.rmi.extensions.server.HeartbeatHelper;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.OutboundResponse;

public final class HeartbeatHelperImpl_WLSkel extends Skeleton {
   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      switch (var1) {
         case 0:
            ((HeartbeatHelper)var4).ping();
            this.associateResponseData(var2, var3);
            return var3;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            ((HeartbeatHelper)var3).ping();
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}

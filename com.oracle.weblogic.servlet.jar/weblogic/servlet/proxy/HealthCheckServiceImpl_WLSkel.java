package weblogic.servlet.proxy;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.OutboundResponse;

public final class HealthCheckServiceImpl_WLSkel extends Skeleton {
   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      switch (var1) {
         case 0:
            int var5 = ((HealthCheck)var4).getServerID();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var5);
               break;
            } catch (IOException var7) {
               throw new MarshalException("error marshalling return", var7);
            }
         case 1:
            ((HealthCheck)var4).ping();
            this.associateResponseData(var2, var3);
            break;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }

      return var3;
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            return new Integer(((HealthCheck)var3).getServerID());
         case 1:
            ((HealthCheck)var3).ping();
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}

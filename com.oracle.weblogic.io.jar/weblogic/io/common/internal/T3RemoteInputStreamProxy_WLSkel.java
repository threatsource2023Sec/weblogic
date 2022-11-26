package weblogic.io.common.internal;

import java.io.IOException;
import java.rmi.UnmarshalException;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class T3RemoteInputStreamProxy_WLSkel extends Skeleton {
   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      switch (var1) {
         case 0:
            ((OneWayInputServer)var4).close();
            this.associateResponseData(var2, var3);
            break;
         case 1:
            int var12;
            try {
               MsgInput var6 = var2.getMsgInput();
               var12 = var6.readInt();
            } catch (IOException var11) {
               throw new UnmarshalException("error unmarshalling arguments", var11);
            }

            ((OneWayInputServer)var4).read(var12);
            this.associateResponseData(var2, var3);
            break;
         case 2:
            long var7;
            try {
               MsgInput var5 = var2.getMsgInput();
               var7 = var5.readLong();
            } catch (IOException var10) {
               throw new UnmarshalException("error unmarshalling arguments", var10);
            }

            ((OneWayInputServer)var4).skip(var7);
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
            ((OneWayInputServer)var3).close();
            return null;
         case 1:
            ((OneWayInputServer)var3).read((Integer)var2[0]);
            return null;
         case 2:
            ((OneWayInputServer)var3).skip((Long)var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}

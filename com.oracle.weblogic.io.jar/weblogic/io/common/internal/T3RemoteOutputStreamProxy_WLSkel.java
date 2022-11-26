package weblogic.io.common.internal;

import java.io.IOException;
import java.rmi.UnmarshalException;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class T3RemoteOutputStreamProxy_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$B;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      int var5;
      switch (var1) {
         case 0:
            ((OneWayOutputServer)var4).close();
            this.associateResponseData(var2, var3);
            break;
         case 1:
            try {
               MsgInput var6 = var2.getMsgInput();
               var5 = var6.readInt();
            } catch (IOException var12) {
               throw new UnmarshalException("error unmarshalling arguments", var12);
            }

            ((OneWayOutputServer)var4).flush(var5);
            this.associateResponseData(var2, var3);
            break;
         case 2:
            byte[] var7;
            try {
               MsgInput var8 = var2.getMsgInput();
               var5 = var8.readInt();
               var7 = (byte[])var8.readObject(array$B == null ? (array$B = class$("[B")) : array$B);
            } catch (IOException var10) {
               throw new UnmarshalException("error unmarshalling arguments", var10);
            } catch (ClassNotFoundException var11) {
               throw new UnmarshalException("error unmarshalling arguments", var11);
            }

            ((OneWayOutputServer)var4).write(var5, var7);
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
            ((OneWayOutputServer)var3).close();
            return null;
         case 1:
            ((OneWayOutputServer)var3).flush((Integer)var2[0]);
            return null;
         case 2:
            ((OneWayOutputServer)var3).write((Integer)var2[0], (byte[])var2[1]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}

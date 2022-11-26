package weblogic.io.common.internal;

import java.io.IOException;
import java.rmi.UnmarshalException;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class T3RemoteInputStream_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$B;
   // $FF: synthetic field
   private static Class class$java$lang$Exception;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      switch (var1) {
         case 0:
            Exception var17;
            try {
               MsgInput var6 = var2.getMsgInput();
               var17 = (Exception)var6.readObject(class$java$lang$Exception == null ? (class$java$lang$Exception = class$("java.lang.Exception")) : class$java$lang$Exception);
            } catch (IOException var14) {
               throw new UnmarshalException("error unmarshalling arguments", var14);
            } catch (ClassNotFoundException var15) {
               throw new UnmarshalException("error unmarshalling arguments", var15);
            }

            ((OneWayInputClient)var4).error(var17);
            this.associateResponseData(var2, var3);
            break;
         case 1:
            byte[] var7;
            int var16;
            try {
               MsgInput var8 = var2.getMsgInput();
               var16 = var8.readInt();
               var7 = (byte[])var8.readObject(array$B == null ? (array$B = class$("[B")) : array$B);
            } catch (IOException var12) {
               throw new UnmarshalException("error unmarshalling arguments", var12);
            } catch (ClassNotFoundException var13) {
               throw new UnmarshalException("error unmarshalling arguments", var13);
            }

            ((OneWayInputClient)var4).readResult(var16, var7);
            this.associateResponseData(var2, var3);
            break;
         case 2:
            long var9;
            try {
               MsgInput var5 = var2.getMsgInput();
               var9 = var5.readLong();
            } catch (IOException var11) {
               throw new UnmarshalException("error unmarshalling arguments", var11);
            }

            ((OneWayInputClient)var4).skipResult(var9);
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
            ((OneWayInputClient)var3).error((Exception)var2[0]);
            return null;
         case 1:
            ((OneWayInputClient)var3).readResult((Integer)var2[0], (byte[])var2[1]);
            return null;
         case 2:
            ((OneWayInputClient)var3).skipResult((Long)var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}

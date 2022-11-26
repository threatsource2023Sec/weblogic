package weblogic.io.common.internal;

import java.io.IOException;
import java.rmi.UnmarshalException;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class T3RemoteOutputStream_WLSkel extends Skeleton {
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
            ((OneWayOutputClient)var4).closeResult();
            this.associateResponseData(var2, var3);
            break;
         case 1:
            Exception var12;
            try {
               MsgInput var6 = var2.getMsgInput();
               var12 = (Exception)var6.readObject(class$java$lang$Exception == null ? (class$java$lang$Exception = class$("java.lang.Exception")) : class$java$lang$Exception);
            } catch (IOException var10) {
               throw new UnmarshalException("error unmarshalling arguments", var10);
            } catch (ClassNotFoundException var11) {
               throw new UnmarshalException("error unmarshalling arguments", var11);
            }

            ((OneWayOutputClient)var4).error(var12);
            this.associateResponseData(var2, var3);
            break;
         case 2:
            ((OneWayOutputClient)var4).flushResult();
            this.associateResponseData(var2, var3);
            break;
         case 3:
            int var5;
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = var7.readInt();
            } catch (IOException var9) {
               throw new UnmarshalException("error unmarshalling arguments", var9);
            }

            ((OneWayOutputClient)var4).writeResult(var5);
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
            ((OneWayOutputClient)var3).closeResult();
            return null;
         case 1:
            ((OneWayOutputClient)var3).error((Exception)var2[0]);
            return null;
         case 2:
            ((OneWayOutputClient)var3).flushResult();
            return null;
         case 3:
            ((OneWayOutputClient)var3).writeResult((Integer)var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}

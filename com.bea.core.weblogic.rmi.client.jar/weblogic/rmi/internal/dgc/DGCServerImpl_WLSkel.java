package weblogic.rmi.internal.dgc;

import java.io.IOException;
import java.rmi.UnmarshalException;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class DGCServerImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$I;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      int[] var5;
      switch (var1) {
         case 0:
            try {
               MsgInput var6 = var2.getMsgInput();
               var5 = (int[])var6.readObject(array$I == null ? (array$I = class$("[I")) : array$I);
            } catch (IOException var14) {
               throw new UnmarshalException("error unmarshalling arguments", var14);
            } catch (ClassNotFoundException var15) {
               throw new UnmarshalException("error unmarshalling arguments", var15);
            }

            ((DGCServer)var4).enroll(var5);
            this.associateResponseData(var2, var3);
            break;
         case 1:
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (int[])var7.readObject(array$I == null ? (array$I = class$("[I")) : array$I);
            } catch (IOException var12) {
               throw new UnmarshalException("error unmarshalling arguments", var12);
            } catch (ClassNotFoundException var13) {
               throw new UnmarshalException("error unmarshalling arguments", var13);
            }

            ((DGCServer)var4).renewLease(var5);
            this.associateResponseData(var2, var3);
            break;
         case 2:
            try {
               MsgInput var8 = var2.getMsgInput();
               var5 = (int[])var8.readObject(array$I == null ? (array$I = class$("[I")) : array$I);
            } catch (IOException var10) {
               throw new UnmarshalException("error unmarshalling arguments", var10);
            } catch (ClassNotFoundException var11) {
               throw new UnmarshalException("error unmarshalling arguments", var11);
            }

            ((DGCServer)var4).unenroll(var5);
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
            ((DGCServer)var3).enroll((int[])var2[0]);
            return null;
         case 1:
            ((DGCServer)var3).renewLease((int[])var2[0]);
            return null;
         case 2:
            ((DGCServer)var3).unenroll((int[])var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}

package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.rmi.UnmarshalException;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class JDBCOutputStreamImpl_WLSkel extends Skeleton {
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
      switch (var1) {
         case 0:
            ((JDBCOutputStream)var4).close();
            this.associateResponseData(var2, var3);
            break;
         case 1:
            ((JDBCOutputStream)var4).flush();
            this.associateResponseData(var2, var3);
            break;
         case 2:
            int var12;
            try {
               MsgInput var6 = var2.getMsgInput();
               var12 = var6.readInt();
            } catch (IOException var11) {
               throw new UnmarshalException("error unmarshalling arguments", var11);
            }

            ((JDBCOutputStream)var4).write(var12);
            this.associateResponseData(var2, var3);
            break;
         case 3:
            byte[] var5;
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (byte[])var7.readObject(array$B == null ? (array$B = class$("[B")) : array$B);
            } catch (IOException var9) {
               throw new UnmarshalException("error unmarshalling arguments", var9);
            } catch (ClassNotFoundException var10) {
               throw new UnmarshalException("error unmarshalling arguments", var10);
            }

            ((JDBCOutputStream)var4).writeBlock(var5);
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
            ((JDBCOutputStream)var3).close();
            return null;
         case 1:
            ((JDBCOutputStream)var3).flush();
            return null;
         case 2:
            ((JDBCOutputStream)var3).write((Integer)var2[0]);
            return null;
         case 3:
            ((JDBCOutputStream)var3).writeBlock((byte[])var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}

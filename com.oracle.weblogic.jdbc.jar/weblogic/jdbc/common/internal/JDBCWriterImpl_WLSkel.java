package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.rmi.UnmarshalException;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class JDBCWriterImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$C;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      char[] var5;
      switch (var1) {
         case 0:
            ((JDBCWriter)var4).close();
            this.associateResponseData(var2, var3);
            break;
         case 1:
            ((JDBCWriter)var4).flush();
            this.associateResponseData(var2, var3);
            break;
         case 2:
            int var7;
            int var14;
            try {
               MsgInput var8 = var2.getMsgInput();
               var5 = (char[])var8.readObject(array$C == null ? (array$C = class$("[C")) : array$C);
               var14 = var8.readInt();
               var7 = var8.readInt();
            } catch (IOException var12) {
               throw new UnmarshalException("error unmarshalling arguments", var12);
            } catch (ClassNotFoundException var13) {
               throw new UnmarshalException("error unmarshalling arguments", var13);
            }

            ((JDBCWriter)var4).write(var5, var14, var7);
            this.associateResponseData(var2, var3);
            break;
         case 3:
            try {
               MsgInput var6 = var2.getMsgInput();
               var5 = (char[])var6.readObject(array$C == null ? (array$C = class$("[C")) : array$C);
            } catch (IOException var10) {
               throw new UnmarshalException("error unmarshalling arguments", var10);
            } catch (ClassNotFoundException var11) {
               throw new UnmarshalException("error unmarshalling arguments", var11);
            }

            ((JDBCWriter)var4).writeBlock(var5);
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
            ((JDBCWriter)var3).close();
            return null;
         case 1:
            ((JDBCWriter)var3).flush();
            return null;
         case 2:
            ((JDBCWriter)var3).write((char[])var2[0], (Integer)var2[1], (Integer)var2[2]);
            return null;
         case 3:
            ((JDBCWriter)var3).writeBlock((char[])var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}

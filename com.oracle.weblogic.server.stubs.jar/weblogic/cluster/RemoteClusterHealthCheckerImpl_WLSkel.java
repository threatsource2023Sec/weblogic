package weblogic.cluster;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import java.util.ArrayList;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class RemoteClusterHealthCheckerImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$util$ArrayList;

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
            long var5;
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = var7.readLong();
            } catch (IOException var11) {
               throw new UnmarshalException("error unmarshalling arguments", var11);
            }

            ArrayList var8 = ((RemoteClusterHealthChecker)var4).checkClusterMembership(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var8, class$java$util$ArrayList == null ? (class$java$util$ArrayList = class$("java.util.ArrayList")) : class$java$util$ArrayList);
               return var3;
            } catch (IOException var10) {
               throw new MarshalException("error marshalling return", var10);
            }
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            return ((RemoteClusterHealthChecker)var3).checkClusterMembership((Long)var2[0]);
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}

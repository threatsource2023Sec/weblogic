package weblogic.cluster.messaging.internal;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class RMIClusterMessageEndPointImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$weblogic$cluster$messaging$internal$ClusterMessage;
   // $FF: synthetic field
   private static Class class$weblogic$cluster$messaging$internal$ClusterResponse;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      ClusterMessage var5;
      switch (var1) {
         case 0:
            try {
               MsgInput var6 = var2.getMsgInput();
               var5 = (ClusterMessage)var6.readObject(class$weblogic$cluster$messaging$internal$ClusterMessage == null ? (class$weblogic$cluster$messaging$internal$ClusterMessage = class$("weblogic.cluster.messaging.internal.ClusterMessage")) : class$weblogic$cluster$messaging$internal$ClusterMessage);
            } catch (IOException var12) {
               throw new UnmarshalException("error unmarshalling arguments", var12);
            } catch (ClassNotFoundException var13) {
               throw new UnmarshalException("error unmarshalling arguments", var13);
            }

            ClusterResponse var14 = ((ClusterMessageEndPoint)var4).process(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var14, class$weblogic$cluster$messaging$internal$ClusterResponse == null ? (class$weblogic$cluster$messaging$internal$ClusterResponse = class$("weblogic.cluster.messaging.internal.ClusterResponse")) : class$weblogic$cluster$messaging$internal$ClusterResponse);
               break;
            } catch (IOException var11) {
               throw new MarshalException("error marshalling return", var11);
            }
         case 1:
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (ClusterMessage)var7.readObject(class$weblogic$cluster$messaging$internal$ClusterMessage == null ? (class$weblogic$cluster$messaging$internal$ClusterMessage = class$("weblogic.cluster.messaging.internal.ClusterMessage")) : class$weblogic$cluster$messaging$internal$ClusterMessage);
            } catch (IOException var9) {
               throw new UnmarshalException("error unmarshalling arguments", var9);
            } catch (ClassNotFoundException var10) {
               throw new UnmarshalException("error unmarshalling arguments", var10);
            }

            ((ClusterMessageEndPoint)var4).processOneWay(var5);
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
            return ((ClusterMessageEndPoint)var3).process((ClusterMessage)var2[0]);
         case 1:
            ((ClusterMessageEndPoint)var3).processOneWay((ClusterMessage)var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}

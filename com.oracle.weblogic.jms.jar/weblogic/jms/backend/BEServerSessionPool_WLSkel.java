package weblogic.jms.backend;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import javax.jms.ServerSession;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class BEServerSessionPool_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$javax$jms$ServerSession;
   // $FF: synthetic field
   private static Class class$weblogic$messaging$dispatcher$DispatcherId;

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
            ((BEServerSessionPoolRemote)var4).close();
            this.associateResponseData(var2, var3);
            break;
         case 1:
            DispatcherId var5;
            try {
               MsgInput var6 = var2.getMsgInput();
               var5 = (DispatcherId)var6.readObject(class$weblogic$messaging$dispatcher$DispatcherId == null ? (class$weblogic$messaging$dispatcher$DispatcherId = class$("weblogic.messaging.dispatcher.DispatcherId")) : class$weblogic$messaging$dispatcher$DispatcherId);
            } catch (IOException var10) {
               throw new UnmarshalException("error unmarshalling arguments", var10);
            } catch (ClassNotFoundException var11) {
               throw new UnmarshalException("error unmarshalling arguments", var11);
            }

            ServerSession var7 = ((BEServerSessionPoolRemote)var4).getServerSession(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var7, class$javax$jms$ServerSession == null ? (class$javax$jms$ServerSession = class$("javax.jms.ServerSession")) : class$javax$jms$ServerSession);
               break;
            } catch (IOException var9) {
               throw new MarshalException("error marshalling return", var9);
            }
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }

      return var3;
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            ((BEServerSessionPoolRemote)var3).close();
            return null;
         case 1:
            return ((BEServerSessionPoolRemote)var3).getServerSession((DispatcherId)var2[0]);
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}

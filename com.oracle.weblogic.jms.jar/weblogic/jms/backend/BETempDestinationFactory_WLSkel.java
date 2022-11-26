package weblogic.jms.backend;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import javax.jms.Destination;
import weblogic.jms.common.JMSID;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class BETempDestinationFactory_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$javax$jms$Destination;
   // $FF: synthetic field
   private static Class class$weblogic$jms$common$JMSID;
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
            DispatcherId var5;
            JMSID var6;
            boolean var7;
            int var8;
            long var9;
            String var11;
            boolean var12;
            int var13;
            String var14;
            try {
               MsgInput var15 = var2.getMsgInput();
               var5 = (DispatcherId)var15.readObject(class$weblogic$messaging$dispatcher$DispatcherId == null ? (class$weblogic$messaging$dispatcher$DispatcherId = class$("weblogic.messaging.dispatcher.DispatcherId")) : class$weblogic$messaging$dispatcher$DispatcherId);
               var6 = (JMSID)var15.readObject(class$weblogic$jms$common$JMSID == null ? (class$weblogic$jms$common$JMSID = class$("weblogic.jms.common.JMSID")) : class$weblogic$jms$common$JMSID);
               var7 = var15.readBoolean();
               var8 = var15.readInt();
               var9 = var15.readLong();
               var11 = (String)var15.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var12 = var15.readBoolean();
               var13 = var15.readInt();
               var14 = (String)var15.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var19) {
               throw new UnmarshalException("error unmarshalling arguments", var19);
            } catch (ClassNotFoundException var20) {
               throw new UnmarshalException("error unmarshalling arguments", var20);
            }

            Destination var16 = ((BETempDestinationFactoryRemote)var4).createTempDestination(var5, var6, var7, var8, var9, var11, var12, var13, var14);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var16, class$javax$jms$Destination == null ? (class$javax$jms$Destination = class$("javax.jms.Destination")) : class$javax$jms$Destination);
               return var3;
            } catch (IOException var18) {
               throw new MarshalException("error marshalling return", var18);
            }
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            return ((BETempDestinationFactoryRemote)var3).createTempDestination((DispatcherId)var2[0], (JMSID)var2[1], (Boolean)var2[2], (Integer)var2[3], (Long)var2[4], (String)var2[5], (Boolean)var2[6], (Integer)var2[7], (String)var2[8]);
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}

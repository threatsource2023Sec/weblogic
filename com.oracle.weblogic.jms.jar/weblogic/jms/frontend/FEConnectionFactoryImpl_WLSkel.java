package weblogic.jms.frontend;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import weblogic.jms.client.JMSConnection;
import weblogic.jms.dispatcher.DispatcherWrapper;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class FEConnectionFactoryImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$weblogic$jms$client$JMSConnection;
   // $FF: synthetic field
   private static Class class$weblogic$jms$dispatcher$DispatcherWrapper;
   // $FF: synthetic field
   private static Class class$weblogic$jms$frontend$FEConnectionCreateRequest;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      DispatcherWrapper var21;
      switch (var1) {
         case 0:
            try {
               MsgInput var6 = var2.getMsgInput();
               var21 = (DispatcherWrapper)var6.readObject(class$weblogic$jms$dispatcher$DispatcherWrapper == null ? (class$weblogic$jms$dispatcher$DispatcherWrapper = class$("weblogic.jms.dispatcher.DispatcherWrapper")) : class$weblogic$jms$dispatcher$DispatcherWrapper);
            } catch (IOException var19) {
               throw new UnmarshalException("error unmarshalling arguments", var19);
            } catch (ClassNotFoundException var20) {
               throw new UnmarshalException("error unmarshalling arguments", var20);
            }

            JMSConnection var23 = ((FEConnectionFactoryRemote)var4).connectionCreate(var21);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var23, class$weblogic$jms$client$JMSConnection == null ? (class$weblogic$jms$client$JMSConnection = class$("weblogic.jms.client.JMSConnection")) : class$weblogic$jms$client$JMSConnection);
               break;
            } catch (IOException var18) {
               throw new MarshalException("error marshalling return", var18);
            }
         case 1:
            String var22;
            String var24;
            try {
               MsgInput var9 = var2.getMsgInput();
               var21 = (DispatcherWrapper)var9.readObject(class$weblogic$jms$dispatcher$DispatcherWrapper == null ? (class$weblogic$jms$dispatcher$DispatcherWrapper = class$("weblogic.jms.dispatcher.DispatcherWrapper")) : class$weblogic$jms$dispatcher$DispatcherWrapper);
               var22 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var24 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var16) {
               throw new UnmarshalException("error unmarshalling arguments", var16);
            } catch (ClassNotFoundException var17) {
               throw new UnmarshalException("error unmarshalling arguments", var17);
            }

            JMSConnection var10 = ((FEConnectionFactoryRemote)var4).connectionCreate(var21, var22, var24);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var10, class$weblogic$jms$client$JMSConnection == null ? (class$weblogic$jms$client$JMSConnection = class$("weblogic.jms.client.JMSConnection")) : class$weblogic$jms$client$JMSConnection);
               break;
            } catch (IOException var15) {
               throw new MarshalException("error marshalling return", var15);
            }
         case 2:
            FEConnectionCreateRequest var5;
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (FEConnectionCreateRequest)var7.readObject(class$weblogic$jms$frontend$FEConnectionCreateRequest == null ? (class$weblogic$jms$frontend$FEConnectionCreateRequest = class$("weblogic.jms.frontend.FEConnectionCreateRequest")) : class$weblogic$jms$frontend$FEConnectionCreateRequest);
            } catch (IOException var13) {
               throw new UnmarshalException("error unmarshalling arguments", var13);
            } catch (ClassNotFoundException var14) {
               throw new UnmarshalException("error unmarshalling arguments", var14);
            }

            JMSConnection var8 = ((FEConnectionFactoryRemote)var4).connectionCreateRequest(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var8, class$weblogic$jms$client$JMSConnection == null ? (class$weblogic$jms$client$JMSConnection = class$("weblogic.jms.client.JMSConnection")) : class$weblogic$jms$client$JMSConnection);
               break;
            } catch (IOException var12) {
               throw new MarshalException("error marshalling return", var12);
            }
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }

      return var3;
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            return ((FEConnectionFactoryRemote)var3).connectionCreate((DispatcherWrapper)var2[0]);
         case 1:
            return ((FEConnectionFactoryRemote)var3).connectionCreate((DispatcherWrapper)var2[0], (String)var2[1], (String)var2[2]);
         case 2:
            return ((FEConnectionFactoryRemote)var3).connectionCreateRequest((FEConnectionCreateRequest)var2[0]);
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}

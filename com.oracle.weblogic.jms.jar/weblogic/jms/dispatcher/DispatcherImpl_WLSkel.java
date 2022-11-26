package weblogic.jms.dispatcher;

import java.io.IOException;
import java.rmi.UnmarshalException;
import weblogic.rmi.extensions.AsyncResult;
import weblogic.rmi.extensions.server.FutureResponse;
import weblogic.rmi.internal.AsyncResultImpl;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class DispatcherImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$weblogic$jms$dispatcher$Request;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      Request var5;
      AsyncResultImpl var25;
      switch (var1) {
         case 0:
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (Request)var7.readObject(class$weblogic$jms$dispatcher$Request == null ? (class$weblogic$jms$dispatcher$Request = class$("weblogic.jms.dispatcher.Request")) : class$weblogic$jms$dispatcher$Request);
               var25 = new AsyncResultImpl(var2, var3);
            } catch (IOException var23) {
               throw new UnmarshalException("error unmarshalling arguments", var23);
            } catch (ClassNotFoundException var24) {
               throw new UnmarshalException("error unmarshalling arguments", var24);
            }

            ((DispatcherImpl)var4).dispatchAsyncFuture(var5, var25, (FutureResponse)var3);
            break;
         case 1:
            try {
               MsgInput var8 = var2.getMsgInput();
               var5 = (Request)var8.readObject(class$weblogic$jms$dispatcher$Request == null ? (class$weblogic$jms$dispatcher$Request = class$("weblogic.jms.dispatcher.Request")) : class$weblogic$jms$dispatcher$Request);
               var25 = new AsyncResultImpl(var2, var3);
            } catch (IOException var21) {
               throw new UnmarshalException("error unmarshalling arguments", var21);
            } catch (ClassNotFoundException var22) {
               throw new UnmarshalException("error unmarshalling arguments", var22);
            }

            ((DispatcherImpl)var4).dispatchAsyncTranFuture(var5, var25, (FutureResponse)var3);
            break;
         case 2:
            try {
               MsgInput var6 = var2.getMsgInput();
               var5 = (Request)var6.readObject(class$weblogic$jms$dispatcher$Request == null ? (class$weblogic$jms$dispatcher$Request = class$("weblogic.jms.dispatcher.Request")) : class$weblogic$jms$dispatcher$Request);
            } catch (IOException var19) {
               throw new UnmarshalException("error unmarshalling arguments", var19);
            } catch (ClassNotFoundException var20) {
               throw new UnmarshalException("error unmarshalling arguments", var20);
            }

            ((DispatcherOneWay)var4).dispatchOneWay(var5);
            this.associateResponseData(var2, var3);
            break;
         case 3:
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = (Request)var9.readObject(class$weblogic$jms$dispatcher$Request == null ? (class$weblogic$jms$dispatcher$Request = class$("weblogic.jms.dispatcher.Request")) : class$weblogic$jms$dispatcher$Request);
            } catch (IOException var17) {
               throw new UnmarshalException("error unmarshalling arguments", var17);
            } catch (ClassNotFoundException var18) {
               throw new UnmarshalException("error unmarshalling arguments", var18);
            }

            ((DispatcherImpl)var4).dispatchSyncFuture(var5, (FutureResponse)var3);
            break;
         case 4:
            try {
               MsgInput var10 = var2.getMsgInput();
               var5 = (Request)var10.readObject(class$weblogic$jms$dispatcher$Request == null ? (class$weblogic$jms$dispatcher$Request = class$("weblogic.jms.dispatcher.Request")) : class$weblogic$jms$dispatcher$Request);
            } catch (IOException var15) {
               throw new UnmarshalException("error unmarshalling arguments", var15);
            } catch (ClassNotFoundException var16) {
               throw new UnmarshalException("error unmarshalling arguments", var16);
            }

            ((DispatcherImpl)var4).dispatchSyncNoTranFuture(var5, (FutureResponse)var3);
            break;
         case 5:
            try {
               MsgInput var11 = var2.getMsgInput();
               var5 = (Request)var11.readObject(class$weblogic$jms$dispatcher$Request == null ? (class$weblogic$jms$dispatcher$Request = class$("weblogic.jms.dispatcher.Request")) : class$weblogic$jms$dispatcher$Request);
            } catch (IOException var13) {
               throw new UnmarshalException("error unmarshalling arguments", var13);
            } catch (ClassNotFoundException var14) {
               throw new UnmarshalException("error unmarshalling arguments", var14);
            }

            ((DispatcherImpl)var4).dispatchSyncTranFuture(var5, (FutureResponse)var3);
            break;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }

      return var3;
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            ((DispatcherRemote)var3).dispatchAsyncFuture((Request)var2[0], (AsyncResult)var2[1]);
            return null;
         case 1:
            ((DispatcherRemote)var3).dispatchAsyncTranFuture((Request)var2[0], (AsyncResult)var2[1]);
            return null;
         case 2:
            ((DispatcherOneWay)var3).dispatchOneWay((Request)var2[0]);
            return null;
         case 3:
            return ((DispatcherRemote)var3).dispatchSyncFuture((Request)var2[0]);
         case 4:
            return ((DispatcherRemote)var3).dispatchSyncNoTranFuture((Request)var2[0]);
         case 5:
            return ((DispatcherRemote)var3).dispatchSyncTranFuture((Request)var2[0]);
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}

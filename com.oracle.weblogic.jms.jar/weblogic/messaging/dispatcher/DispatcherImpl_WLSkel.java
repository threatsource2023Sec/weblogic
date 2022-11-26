package weblogic.messaging.dispatcher;

import java.io.IOException;
import java.rmi.MarshalException;
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
   private static Class class$weblogic$messaging$dispatcher$Request;
   // $FF: synthetic field
   private static Class class$weblogic$messaging$dispatcher$Response;

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
      AsyncResultImpl var40;
      int var42;
      switch (var1) {
         case 0:
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (Request)var7.readObject(class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
               var40 = new AsyncResultImpl(var2, var3);
            } catch (IOException var38) {
               throw new UnmarshalException("error unmarshalling arguments", var38);
            } catch (ClassNotFoundException var39) {
               throw new UnmarshalException("error unmarshalling arguments", var39);
            }

            ((DispatcherImpl)var4).dispatchAsyncFuture(var5, var40, (FutureResponse)var3);
            break;
         case 1:
            int var41;
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = (Request)var9.readObject(class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
               var40 = new AsyncResultImpl(var2, var3);
               var41 = var9.readInt();
            } catch (IOException var36) {
               throw new UnmarshalException("error unmarshalling arguments", var36);
            } catch (ClassNotFoundException var37) {
               throw new UnmarshalException("error unmarshalling arguments", var37);
            }

            ((DispatcherImpl)var4).dispatchAsyncFutureWithId(var5, var40, var41, (FutureResponse)var3);
            break;
         case 2:
            try {
               MsgInput var8 = var2.getMsgInput();
               var5 = (Request)var8.readObject(class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
               var40 = new AsyncResultImpl(var2, var3);
            } catch (IOException var34) {
               throw new UnmarshalException("error unmarshalling arguments", var34);
            } catch (ClassNotFoundException var35) {
               throw new UnmarshalException("error unmarshalling arguments", var35);
            }

            ((DispatcherImpl)var4).dispatchAsyncTranFuture(var5, var40, (FutureResponse)var3);
            break;
         case 3:
            try {
               MsgInput var11 = var2.getMsgInput();
               var5 = (Request)var11.readObject(class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
               var40 = new AsyncResultImpl(var2, var3);
               var42 = var11.readInt();
            } catch (IOException var32) {
               throw new UnmarshalException("error unmarshalling arguments", var32);
            } catch (ClassNotFoundException var33) {
               throw new UnmarshalException("error unmarshalling arguments", var33);
            }

            ((DispatcherImpl)var4).dispatchAsyncTranFutureWithId(var5, var40, var42, (FutureResponse)var3);
            break;
         case 4:
            try {
               MsgInput var6 = var2.getMsgInput();
               var5 = (Request)var6.readObject(class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
            } catch (IOException var30) {
               throw new UnmarshalException("error unmarshalling arguments", var30);
            } catch (ClassNotFoundException var31) {
               throw new UnmarshalException("error unmarshalling arguments", var31);
            }

            ((DispatcherOneWay)var4).dispatchOneWay(var5);
            this.associateResponseData(var2, var3);
            break;
         case 5:
            try {
               MsgInput var12 = var2.getMsgInput();
               var5 = (Request)var12.readObject(class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
               var42 = var12.readInt();
            } catch (IOException var28) {
               throw new UnmarshalException("error unmarshalling arguments", var28);
            } catch (ClassNotFoundException var29) {
               throw new UnmarshalException("error unmarshalling arguments", var29);
            }

            ((DispatcherOneWay)var4).dispatchOneWayWithId(var5, var42);
            this.associateResponseData(var2, var3);
            break;
         case 6:
            try {
               MsgInput var10 = var2.getMsgInput();
               var5 = (Request)var10.readObject(class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
            } catch (IOException var26) {
               throw new UnmarshalException("error unmarshalling arguments", var26);
            } catch (ClassNotFoundException var27) {
               throw new UnmarshalException("error unmarshalling arguments", var27);
            }

            ((DispatcherImpl)var4).dispatchSyncFuture(var5, (FutureResponse)var3);
            break;
         case 7:
            try {
               MsgInput var13 = var2.getMsgInput();
               var5 = (Request)var13.readObject(class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
            } catch (IOException var24) {
               throw new UnmarshalException("error unmarshalling arguments", var24);
            } catch (ClassNotFoundException var25) {
               throw new UnmarshalException("error unmarshalling arguments", var25);
            }

            ((DispatcherImpl)var4).dispatchSyncNoTranFuture(var5, (FutureResponse)var3);
            break;
         case 8:
            try {
               MsgInput var14 = var2.getMsgInput();
               var5 = (Request)var14.readObject(class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
            } catch (IOException var22) {
               throw new UnmarshalException("error unmarshalling arguments", var22);
            } catch (ClassNotFoundException var23) {
               throw new UnmarshalException("error unmarshalling arguments", var23);
            }

            ((DispatcherImpl)var4).dispatchSyncTranFuture(var5, (FutureResponse)var3);
            break;
         case 9:
            int var15;
            try {
               MsgInput var16 = var2.getMsgInput();
               var5 = (Request)var16.readObject(class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
               var15 = var16.readInt();
            } catch (IOException var20) {
               throw new UnmarshalException("error unmarshalling arguments", var20);
            } catch (ClassNotFoundException var21) {
               throw new UnmarshalException("error unmarshalling arguments", var21);
            }

            Response var17 = ((DispatcherRemote)var4).dispatchSyncTranFutureWithId(var5, var15);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var17, class$weblogic$messaging$dispatcher$Response == null ? (class$weblogic$messaging$dispatcher$Response = class$("weblogic.messaging.dispatcher.Response")) : class$weblogic$messaging$dispatcher$Response);
               break;
            } catch (IOException var19) {
               throw new MarshalException("error marshalling return", var19);
            }
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
            ((DispatcherRemote)var3).dispatchAsyncFutureWithId((Request)var2[0], (AsyncResult)var2[1], (Integer)var2[2]);
            return null;
         case 2:
            ((DispatcherRemote)var3).dispatchAsyncTranFuture((Request)var2[0], (AsyncResult)var2[1]);
            return null;
         case 3:
            ((DispatcherRemote)var3).dispatchAsyncTranFutureWithId((Request)var2[0], (AsyncResult)var2[1], (Integer)var2[2]);
            return null;
         case 4:
            ((DispatcherOneWay)var3).dispatchOneWay((Request)var2[0]);
            return null;
         case 5:
            ((DispatcherOneWay)var3).dispatchOneWayWithId((Request)var2[0], (Integer)var2[1]);
            return null;
         case 6:
            return ((DispatcherRemote)var3).dispatchSyncFuture((Request)var2[0]);
         case 7:
            return ((DispatcherRemote)var3).dispatchSyncNoTranFuture((Request)var2[0]);
         case 8:
            return ((DispatcherRemote)var3).dispatchSyncTranFuture((Request)var2[0]);
         case 9:
            return ((DispatcherRemote)var3).dispatchSyncTranFutureWithId((Request)var2[0], (Integer)var2[1]);
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}

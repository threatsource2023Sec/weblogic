package weblogic.messaging.dispatcher;

import java.io.IOException;
import java.rmi.Remote;
import javax.rmi.CORBA.Tie;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.CORBA.portable.UnknownException;
import org.omg.CORBA_2_3.portable.ObjectImpl;
import weblogic.rmi.extensions.AsyncResult;

public class _DispatcherImpl_Tie extends ObjectImpl implements Tie {
   private volatile DispatcherImpl target = null;
   private static final String[] _type_ids = new String[]{"RMI:weblogic.messaging.dispatcher.DispatcherImpl:0000000000000000", "RMI:weblogic.messaging.dispatcher.DispatcherRemote:0000000000000000", "RMI:weblogic.messaging.dispatcher.DispatcherOneWay:0000000000000000"};
   // $FF: synthetic field
   static Class class$weblogic$messaging$dispatcher$Request;
   // $FF: synthetic field
   static Class class$weblogic$messaging$dispatcher$Response;
   // $FF: synthetic field
   static Class class$weblogic$rmi$extensions$AsyncResult;

   public String[] _ids() {
      return (String[])_type_ids.clone();
   }

   public OutputStream _invoke(String var1, InputStream var2, ResponseHandler var3) throws SystemException {
      try {
         DispatcherImpl var4 = this.target;
         if (var4 == null) {
            throw new IOException();
         } else {
            org.omg.CORBA_2_3.portable.InputStream var5 = (org.omg.CORBA_2_3.portable.InputStream)var2;
            Request var6;
            AsyncResult var7;
            int var8;
            OutputStream var9;
            int var12;
            Response var13;
            org.omg.CORBA_2_3.portable.OutputStream var15;
            OutputStream var16;
            switch (var1.length()) {
               case 14:
                  if (var1.equals("dispatchOneWay")) {
                     var6 = (Request)var5.read_value(class$weblogic$messaging$dispatcher$Request != null ? class$weblogic$messaging$dispatcher$Request : (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")));
                     var4.dispatchOneWay(var6);
                     OutputStream var18 = var3.createReply();
                     return var18;
                  }
               case 18:
                  if (var1.equals("dispatchSyncFuture")) {
                     var6 = (Request)var5.read_value(class$weblogic$messaging$dispatcher$Request != null ? class$weblogic$messaging$dispatcher$Request : (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")));
                     var13 = var4.dispatchSyncFuture(var6);
                     var15 = (org.omg.CORBA_2_3.portable.OutputStream)var3.createReply();
                     var15.write_value(var13, class$weblogic$messaging$dispatcher$Response != null ? class$weblogic$messaging$dispatcher$Response : (class$weblogic$messaging$dispatcher$Response = class$("weblogic.messaging.dispatcher.Response")));
                     return var15;
                  }
               case 19:
                  if (var1.equals("dispatchAsyncFuture")) {
                     var6 = (Request)var5.read_value(class$weblogic$messaging$dispatcher$Request != null ? class$weblogic$messaging$dispatcher$Request : (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")));
                     var7 = (AsyncResult)var5.read_value(class$weblogic$rmi$extensions$AsyncResult != null ? class$weblogic$rmi$extensions$AsyncResult : (class$weblogic$rmi$extensions$AsyncResult = class$("weblogic.rmi.extensions.AsyncResult")));
                     var4.dispatchAsyncFuture(var6, var7);
                     var16 = var3.createReply();
                     return var16;
                  }
               case 20:
                  if (var1.equals("dispatchOneWayWithId")) {
                     var6 = (Request)var5.read_value(class$weblogic$messaging$dispatcher$Request != null ? class$weblogic$messaging$dispatcher$Request : (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")));
                     var12 = var5.read_long();
                     var4.dispatchOneWayWithId(var6, var12);
                     var16 = var3.createReply();
                     return var16;
                  }
               case 22:
                  if (var1.equals("dispatchSyncTranFuture")) {
                     var6 = (Request)var5.read_value(class$weblogic$messaging$dispatcher$Request != null ? class$weblogic$messaging$dispatcher$Request : (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")));
                     var13 = var4.dispatchSyncTranFuture(var6);
                     var15 = (org.omg.CORBA_2_3.portable.OutputStream)var3.createReply();
                     var15.write_value(var13, class$weblogic$messaging$dispatcher$Response != null ? class$weblogic$messaging$dispatcher$Response : (class$weblogic$messaging$dispatcher$Response = class$("weblogic.messaging.dispatcher.Response")));
                     return var15;
                  }
               case 23:
                  if (var1.equals("dispatchAsyncTranFuture")) {
                     var6 = (Request)var5.read_value(class$weblogic$messaging$dispatcher$Request != null ? class$weblogic$messaging$dispatcher$Request : (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")));
                     var7 = (AsyncResult)var5.read_value(class$weblogic$rmi$extensions$AsyncResult != null ? class$weblogic$rmi$extensions$AsyncResult : (class$weblogic$rmi$extensions$AsyncResult = class$("weblogic.rmi.extensions.AsyncResult")));
                     var4.dispatchAsyncTranFuture(var6, var7);
                     var16 = var3.createReply();
                     return var16;
                  }
               case 24:
                  if (var1.equals("dispatchSyncNoTranFuture")) {
                     var6 = (Request)var5.read_value(class$weblogic$messaging$dispatcher$Request != null ? class$weblogic$messaging$dispatcher$Request : (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")));
                     var13 = var4.dispatchSyncNoTranFuture(var6);
                     var15 = (org.omg.CORBA_2_3.portable.OutputStream)var3.createReply();
                     var15.write_value(var13, class$weblogic$messaging$dispatcher$Response != null ? class$weblogic$messaging$dispatcher$Response : (class$weblogic$messaging$dispatcher$Response = class$("weblogic.messaging.dispatcher.Response")));
                     return var15;
                  }
               case 25:
                  if (var1.equals("dispatchAsyncFutureWithId")) {
                     var6 = (Request)var5.read_value(class$weblogic$messaging$dispatcher$Request != null ? class$weblogic$messaging$dispatcher$Request : (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")));
                     var7 = (AsyncResult)var5.read_value(class$weblogic$rmi$extensions$AsyncResult != null ? class$weblogic$rmi$extensions$AsyncResult : (class$weblogic$rmi$extensions$AsyncResult = class$("weblogic.rmi.extensions.AsyncResult")));
                     var8 = var5.read_long();
                     var4.dispatchAsyncFutureWithId(var6, var7, var8);
                     var9 = var3.createReply();
                     return var9;
                  }
               case 28:
                  if (var1.equals("dispatchSyncTranFutureWithId")) {
                     var6 = (Request)var5.read_value(class$weblogic$messaging$dispatcher$Request != null ? class$weblogic$messaging$dispatcher$Request : (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")));
                     var12 = var5.read_long();
                     Response var14 = var4.dispatchSyncTranFutureWithId(var6, var12);
                     org.omg.CORBA_2_3.portable.OutputStream var17 = (org.omg.CORBA_2_3.portable.OutputStream)var3.createReply();
                     var17.write_value(var14, class$weblogic$messaging$dispatcher$Response != null ? class$weblogic$messaging$dispatcher$Response : (class$weblogic$messaging$dispatcher$Response = class$("weblogic.messaging.dispatcher.Response")));
                     return var17;
                  }
               case 29:
                  if (var1.equals("dispatchAsyncTranFutureWithId")) {
                     var6 = (Request)var5.read_value(class$weblogic$messaging$dispatcher$Request != null ? class$weblogic$messaging$dispatcher$Request : (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")));
                     var7 = (AsyncResult)var5.read_value(class$weblogic$rmi$extensions$AsyncResult != null ? class$weblogic$rmi$extensions$AsyncResult : (class$weblogic$rmi$extensions$AsyncResult = class$("weblogic.rmi.extensions.AsyncResult")));
                     var8 = var5.read_long();
                     var4.dispatchAsyncTranFutureWithId(var6, var7, var8);
                     var9 = var3.createReply();
                     return var9;
                  }
               case 15:
               case 16:
               case 17:
               case 21:
               case 26:
               case 27:
               default:
                  throw new BAD_OPERATION();
            }
         }
      } catch (SystemException var10) {
         throw var10;
      } catch (Throwable var11) {
         throw new UnknownException(var11);
      }
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public void deactivate() {
      this._orb().disconnect(this);
      this._set_delegate((Delegate)null);
      this.target = null;
   }

   public Remote getTarget() {
      return this.target;
   }

   public ORB orb() {
      return this._orb();
   }

   public void orb(ORB var1) {
      var1.connect(this);
   }

   public void setTarget(Remote var1) {
      this.target = (DispatcherImpl)var1;
   }

   public Object thisObject() {
      return this;
   }
}

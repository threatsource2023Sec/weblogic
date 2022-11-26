package weblogic.messaging.dispatcher;

import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import weblogic.rmi.extensions.AsyncResult;

public final class _DispatcherImpl_Stub extends Stub implements DispatcherRemote, DispatcherOneWay {
   private static String[] _type_ids = new String[]{"RMI:weblogic.messaging.dispatcher.DispatcherImpl:0000000000000000", "RMI:weblogic.messaging.dispatcher.DispatcherRemote:0000000000000000", "RMI:weblogic.messaging.dispatcher.DispatcherOneWay:0000000000000000"};
   // $FF: synthetic field
   private static Class class$weblogic$messaging$dispatcher$DispatcherException;
   // $FF: synthetic field
   private static Class class$weblogic$messaging$dispatcher$Request;
   // $FF: synthetic field
   private static Class class$weblogic$messaging$dispatcher$Response;
   // $FF: synthetic field
   private static Class class$weblogic$rmi$extensions$AsyncResult;

   public String[] _ids() {
      return _type_ids;
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public final void dispatchAsyncFuture(Request var1, AsyncResult var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            OutputStream var4 = (OutputStream)this._request("dispatchAsyncFuture", true);
            var4.write_value(var1, class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
            var4.write_value(var2, class$weblogic$rmi$extensions$AsyncResult == null ? (class$weblogic$rmi$extensions$AsyncResult = class$("weblogic.rmi.extensions.AsyncResult")) : class$weblogic$rmi$extensions$AsyncResult);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            if (var6.equals("IDL:weblogic/messaging/dispatcher/DispatcherEx:1.0")) {
               throw (DispatcherException)((DispatcherException)var3.read_value(class$weblogic$messaging$dispatcher$DispatcherException == null ? (class$weblogic$messaging$dispatcher$DispatcherException = class$("weblogic.messaging.dispatcher.DispatcherException")) : class$weblogic$messaging$dispatcher$DispatcherException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.dispatchAsyncFuture(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final void dispatchAsyncFutureWithId(Request var1, AsyncResult var2, int var3) throws RemoteException {
      try {
         InputStream var4 = null;

         try {
            OutputStream var5 = (OutputStream)this._request("dispatchAsyncFutureWithId", true);
            var5.write_value(var1, class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
            var5.write_value(var2, class$weblogic$rmi$extensions$AsyncResult == null ? (class$weblogic$rmi$extensions$AsyncResult = class$("weblogic.rmi.extensions.AsyncResult")) : class$weblogic$rmi$extensions$AsyncResult);
            var5.write_long(var3);
            this._invoke(var5);
         } catch (ApplicationException var13) {
            var4 = (InputStream)var13.getInputStream();
            String var7 = var4.read_string();
            if (var7.equals("IDL:weblogic/messaging/dispatcher/DispatcherEx:1.0")) {
               throw (DispatcherException)((DispatcherException)var4.read_value(class$weblogic$messaging$dispatcher$DispatcherException == null ? (class$weblogic$messaging$dispatcher$DispatcherException = class$("weblogic.messaging.dispatcher.DispatcherException")) : class$weblogic$messaging$dispatcher$DispatcherException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var14) {
            this.dispatchAsyncFutureWithId(var1, var2, var3);
         } finally {
            this._releaseReply(var4);
         }

      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final void dispatchAsyncTranFuture(Request var1, AsyncResult var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            OutputStream var4 = (OutputStream)this._request("dispatchAsyncTranFuture", true);
            var4.write_value(var1, class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
            var4.write_value(var2, class$weblogic$rmi$extensions$AsyncResult == null ? (class$weblogic$rmi$extensions$AsyncResult = class$("weblogic.rmi.extensions.AsyncResult")) : class$weblogic$rmi$extensions$AsyncResult);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            if (var6.equals("IDL:weblogic/messaging/dispatcher/DispatcherEx:1.0")) {
               throw (DispatcherException)((DispatcherException)var3.read_value(class$weblogic$messaging$dispatcher$DispatcherException == null ? (class$weblogic$messaging$dispatcher$DispatcherException = class$("weblogic.messaging.dispatcher.DispatcherException")) : class$weblogic$messaging$dispatcher$DispatcherException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.dispatchAsyncTranFuture(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final void dispatchAsyncTranFutureWithId(Request var1, AsyncResult var2, int var3) throws RemoteException {
      try {
         InputStream var4 = null;

         try {
            OutputStream var5 = (OutputStream)this._request("dispatchAsyncTranFutureWithId", true);
            var5.write_value(var1, class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
            var5.write_value(var2, class$weblogic$rmi$extensions$AsyncResult == null ? (class$weblogic$rmi$extensions$AsyncResult = class$("weblogic.rmi.extensions.AsyncResult")) : class$weblogic$rmi$extensions$AsyncResult);
            var5.write_long(var3);
            this._invoke(var5);
         } catch (ApplicationException var13) {
            var4 = (InputStream)var13.getInputStream();
            String var7 = var4.read_string();
            if (var7.equals("IDL:weblogic/messaging/dispatcher/DispatcherEx:1.0")) {
               throw (DispatcherException)((DispatcherException)var4.read_value(class$weblogic$messaging$dispatcher$DispatcherException == null ? (class$weblogic$messaging$dispatcher$DispatcherException = class$("weblogic.messaging.dispatcher.DispatcherException")) : class$weblogic$messaging$dispatcher$DispatcherException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var14) {
            this.dispatchAsyncTranFutureWithId(var1, var2, var3);
         } finally {
            this._releaseReply(var4);
         }

      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final void dispatchOneWay(Request var1) throws RemoteException {
      try {
         InputStream var2 = null;

         try {
            OutputStream var3 = (OutputStream)this._request("dispatchOneWay", false);
            var3.write_value(var1, class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
            this._invoke(var3);
         } catch (ApplicationException var11) {
            var2 = (InputStream)var11.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var12) {
            this.dispatchOneWay(var1);
         } finally {
            this._releaseReply(var2);
         }

      } catch (SystemException var14) {
         throw Util.mapSystemException(var14);
      }
   }

   public final void dispatchOneWayWithId(Request var1, int var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            OutputStream var4 = (OutputStream)this._request("dispatchOneWayWithId", false);
            var4.write_value(var1, class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
            var4.write_long(var2);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.dispatchOneWayWithId(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final Response dispatchSyncFuture(Request var1) throws RemoteException {
      try {
         InputStream var2 = null;

         Response var6;
         try {
            OutputStream var3 = (OutputStream)this._request("dispatchSyncFuture", true);
            var3.write_value(var1, class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
            var2 = (InputStream)this._invoke(var3);
            Response var4 = (Response)var2.read_value(class$weblogic$messaging$dispatcher$Response == null ? (class$weblogic$messaging$dispatcher$Response = class$("weblogic.messaging.dispatcher.Response")) : class$weblogic$messaging$dispatcher$Response);
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            if (var5.equals("IDL:weblogic/messaging/dispatcher/DispatcherEx:1.0")) {
               throw (DispatcherException)((DispatcherException)var2.read_value(class$weblogic$messaging$dispatcher$DispatcherException == null ? (class$weblogic$messaging$dispatcher$DispatcherException = class$("weblogic.messaging.dispatcher.DispatcherException")) : class$weblogic$messaging$dispatcher$DispatcherException));
            }

            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.dispatchSyncFuture(var1);
         } finally {
            this._releaseReply(var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final Response dispatchSyncNoTranFuture(Request var1) throws RemoteException {
      try {
         InputStream var2 = null;

         Response var6;
         try {
            OutputStream var3 = (OutputStream)this._request("dispatchSyncNoTranFuture", true);
            var3.write_value(var1, class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
            var2 = (InputStream)this._invoke(var3);
            Response var4 = (Response)var2.read_value(class$weblogic$messaging$dispatcher$Response == null ? (class$weblogic$messaging$dispatcher$Response = class$("weblogic.messaging.dispatcher.Response")) : class$weblogic$messaging$dispatcher$Response);
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            if (var5.equals("IDL:weblogic/messaging/dispatcher/DispatcherEx:1.0")) {
               throw (DispatcherException)((DispatcherException)var2.read_value(class$weblogic$messaging$dispatcher$DispatcherException == null ? (class$weblogic$messaging$dispatcher$DispatcherException = class$("weblogic.messaging.dispatcher.DispatcherException")) : class$weblogic$messaging$dispatcher$DispatcherException));
            }

            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.dispatchSyncNoTranFuture(var1);
         } finally {
            this._releaseReply(var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final Response dispatchSyncTranFuture(Request var1) throws RemoteException {
      try {
         InputStream var2 = null;

         Response var6;
         try {
            OutputStream var3 = (OutputStream)this._request("dispatchSyncTranFuture", true);
            var3.write_value(var1, class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
            var2 = (InputStream)this._invoke(var3);
            Response var4 = (Response)var2.read_value(class$weblogic$messaging$dispatcher$Response == null ? (class$weblogic$messaging$dispatcher$Response = class$("weblogic.messaging.dispatcher.Response")) : class$weblogic$messaging$dispatcher$Response);
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            if (var5.equals("IDL:weblogic/messaging/dispatcher/DispatcherEx:1.0")) {
               throw (DispatcherException)((DispatcherException)var2.read_value(class$weblogic$messaging$dispatcher$DispatcherException == null ? (class$weblogic$messaging$dispatcher$DispatcherException = class$("weblogic.messaging.dispatcher.DispatcherException")) : class$weblogic$messaging$dispatcher$DispatcherException));
            }

            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.dispatchSyncTranFuture(var1);
         } finally {
            this._releaseReply(var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final Response dispatchSyncTranFutureWithId(Request var1, int var2) throws RemoteException {
      try {
         InputStream var3 = null;

         Response var7;
         try {
            OutputStream var4 = (OutputStream)this._request("dispatchSyncTranFutureWithId", true);
            var4.write_value(var1, class$weblogic$messaging$dispatcher$Request == null ? (class$weblogic$messaging$dispatcher$Request = class$("weblogic.messaging.dispatcher.Request")) : class$weblogic$messaging$dispatcher$Request);
            var4.write_long(var2);
            var3 = (InputStream)this._invoke(var4);
            Response var5 = (Response)var3.read_value(class$weblogic$messaging$dispatcher$Response == null ? (class$weblogic$messaging$dispatcher$Response = class$("weblogic.messaging.dispatcher.Response")) : class$weblogic$messaging$dispatcher$Response);
            return var5;
         } catch (ApplicationException var14) {
            var3 = (InputStream)var14.getInputStream();
            String var6 = var3.read_string();
            if (var6.equals("IDL:weblogic/messaging/dispatcher/DispatcherEx:1.0")) {
               throw (DispatcherException)((DispatcherException)var3.read_value(class$weblogic$messaging$dispatcher$DispatcherException == null ? (class$weblogic$messaging$dispatcher$DispatcherException = class$("weblogic.messaging.dispatcher.DispatcherException")) : class$weblogic$messaging$dispatcher$DispatcherException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var15) {
            var7 = this.dispatchSyncTranFutureWithId(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

         return var7;
      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }
}

package weblogic.jms.dispatcher;

import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import javax.jms.JMSException;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import weblogic.rmi.extensions.AsyncResult;

public final class _DispatcherRemote_Stub extends Stub implements DispatcherRemote {
   private static String[] _type_ids = new String[]{"RMI:weblogic.jms.dispatcher.DispatcherRemote:0000000000000000"};
   // $FF: synthetic field
   private static Class class$javax$jms$JMSException;
   // $FF: synthetic field
   private static Class class$weblogic$jms$dispatcher$DispatcherException;
   // $FF: synthetic field
   private static Class class$weblogic$jms$dispatcher$Request;
   // $FF: synthetic field
   private static Class class$weblogic$jms$dispatcher$Response;
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
            var4.write_value(var1, class$weblogic$jms$dispatcher$Request == null ? (class$weblogic$jms$dispatcher$Request = class$("weblogic.jms.dispatcher.Request")) : class$weblogic$jms$dispatcher$Request);
            var4.write_value(var2, class$weblogic$rmi$extensions$AsyncResult == null ? (class$weblogic$rmi$extensions$AsyncResult = class$("weblogic.rmi.extensions.AsyncResult")) : class$weblogic$rmi$extensions$AsyncResult);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
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

   public final void dispatchAsyncTranFuture(Request var1, AsyncResult var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            OutputStream var4 = (OutputStream)this._request("dispatchAsyncTranFuture", true);
            var4.write_value(var1, class$weblogic$jms$dispatcher$Request == null ? (class$weblogic$jms$dispatcher$Request = class$("weblogic.jms.dispatcher.Request")) : class$weblogic$jms$dispatcher$Request);
            var4.write_value(var2, class$weblogic$rmi$extensions$AsyncResult == null ? (class$weblogic$rmi$extensions$AsyncResult = class$("weblogic.rmi.extensions.AsyncResult")) : class$weblogic$rmi$extensions$AsyncResult);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
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

   public final Response dispatchSyncFuture(Request var1) throws RemoteException {
      try {
         InputStream var2 = null;

         Response var6;
         try {
            OutputStream var3 = (OutputStream)this._request("dispatchSyncFuture", true);
            var3.write_value(var1, class$weblogic$jms$dispatcher$Request == null ? (class$weblogic$jms$dispatcher$Request = class$("weblogic.jms.dispatcher.Request")) : class$weblogic$jms$dispatcher$Request);
            var2 = (InputStream)this._invoke(var3);
            Response var4 = (Response)var2.read_value(class$weblogic$jms$dispatcher$Response == null ? (class$weblogic$jms$dispatcher$Response = class$("weblogic.jms.dispatcher.Response")) : class$weblogic$jms$dispatcher$Response);
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            if (var5.equals("IDL:javax/jms/JMSEx:1.0")) {
               throw (JMSException)((JMSException)var2.read_value(class$javax$jms$JMSException == null ? (class$javax$jms$JMSException = class$("javax.jms.JMSException")) : class$javax$jms$JMSException));
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
            var3.write_value(var1, class$weblogic$jms$dispatcher$Request == null ? (class$weblogic$jms$dispatcher$Request = class$("weblogic.jms.dispatcher.Request")) : class$weblogic$jms$dispatcher$Request);
            var2 = (InputStream)this._invoke(var3);
            Response var4 = (Response)var2.read_value(class$weblogic$jms$dispatcher$Response == null ? (class$weblogic$jms$dispatcher$Response = class$("weblogic.jms.dispatcher.Response")) : class$weblogic$jms$dispatcher$Response);
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            if (var5.equals("IDL:javax/jms/JMSEx:1.0")) {
               throw (JMSException)((JMSException)var2.read_value(class$javax$jms$JMSException == null ? (class$javax$jms$JMSException = class$("javax.jms.JMSException")) : class$javax$jms$JMSException));
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
            var3.write_value(var1, class$weblogic$jms$dispatcher$Request == null ? (class$weblogic$jms$dispatcher$Request = class$("weblogic.jms.dispatcher.Request")) : class$weblogic$jms$dispatcher$Request);
            var2 = (InputStream)this._invoke(var3);
            Response var4 = (Response)var2.read_value(class$weblogic$jms$dispatcher$Response == null ? (class$weblogic$jms$dispatcher$Response = class$("weblogic.jms.dispatcher.Response")) : class$weblogic$jms$dispatcher$Response);
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            if (var5.equals("IDL:javax/jms/JMSEx:1.0")) {
               throw (JMSException)((JMSException)var2.read_value(class$javax$jms$JMSException == null ? (class$javax$jms$JMSException = class$("javax.jms.JMSException")) : class$javax$jms$JMSException));
            }

            if (var5.equals("IDL:weblogic/jms/dispatcher/DispatcherEx:1.0")) {
               throw (DispatcherException)((DispatcherException)var2.read_value(class$weblogic$jms$dispatcher$DispatcherException == null ? (class$weblogic$jms$dispatcher$DispatcherException = class$("weblogic.jms.dispatcher.DispatcherException")) : class$weblogic$jms$dispatcher$DispatcherException));
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
}

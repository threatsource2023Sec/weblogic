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

public final class _DispatcherOneWay_Stub extends Stub implements DispatcherOneWay {
   private static String[] _type_ids = new String[]{"RMI:weblogic.messaging.dispatcher.DispatcherOneWay:0000000000000000"};
   // $FF: synthetic field
   private static Class class$weblogic$messaging$dispatcher$Request;

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

   public final void dispatchOneWay(Request var1) throws RemoteException {
      try {
         InputStream var2 = null;

         try {
            OutputStream var3 = (OutputStream)this._request("dispatchOneWay", true);
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
            OutputStream var4 = (OutputStream)this._request("dispatchOneWayWithId", true);
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
}

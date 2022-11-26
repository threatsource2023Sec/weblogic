package weblogic.jms.dispatcher;

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
   private static String[] _type_ids = new String[]{"RMI:weblogic.jms.dispatcher.DispatcherOneWay:0000000000000000"};
   // $FF: synthetic field
   private static Class class$weblogic$jms$dispatcher$Request;

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
            var3.write_value(var1, class$weblogic$jms$dispatcher$Request == null ? (class$weblogic$jms$dispatcher$Request = class$("weblogic.jms.dispatcher.Request")) : class$weblogic$jms$dispatcher$Request);
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
}

package weblogic.management.scripting;

import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import javax.management.Notification;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import weblogic.management.RemoteNotificationListener;

public final class _ChangeListener_Stub extends Stub implements RemoteNotificationListener {
   private static String[] _type_ids = new String[]{"RMI:weblogic.management.scripting.ChangeListener:0000000000000000", "RMI:weblogic.management.RemoteNotificationListener:0000000000000000"};
   // $FF: synthetic field
   private static Class class$javax$management$Notification;

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

   public final void handleNotification(Notification var1, Object var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            OutputStream var4 = (OutputStream)this._request("handleNotification", true);
            var4.write_value(var1, class$javax$management$Notification == null ? (class$javax$management$Notification = class$("javax.management.Notification")) : class$javax$management$Notification);
            Util.writeAny(var4, var2);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.handleNotification(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }
}

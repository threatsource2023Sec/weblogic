package weblogic.rmi.extensions.server;

import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA_2_3.portable.InputStream;

public final class _HeartbeatHelper_Stub extends Stub implements HeartbeatHelper {
   private static String[] _type_ids = new String[]{"RMI:weblogic.rmi.extensions.server.HeartbeatHelper:0000000000000000"};

   public String[] _ids() {
      return _type_ids;
   }

   public final void ping() throws RemoteException {
      try {
         InputStream var1 = null;

         try {
            OutputStream var2 = this._request("ping", true);
            this._invoke(var2);
         } catch (ApplicationException var10) {
            var1 = (InputStream)var10.getInputStream();
            String var4 = var1.read_string();
            throw new UnexpectedException(var4);
         } catch (RemarshalException var11) {
            this.ping();
         } finally {
            this._releaseReply(var1);
         }

      } catch (SystemException var13) {
         throw Util.mapSystemException(var13);
      }
   }
}

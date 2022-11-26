package weblogic.cluster;

import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA_2_3.portable.InputStream;

public final class _RemoteClusterHealthCheckerImpl_Stub extends Stub implements RemoteClusterHealthChecker {
   private static String[] _type_ids = new String[]{"RMI:weblogic.cluster.RemoteClusterHealthCheckerImpl:0000000000000000", "RMI:weblogic.cluster.RemoteClusterHealthChecker:0000000000000000"};
   // $FF: synthetic field
   private static Class class$java$util$ArrayList;

   public String[] _ids() {
      return _type_ids;
   }

   public final ArrayList checkClusterMembership(long var1) throws RemoteException {
      try {
         InputStream var3 = null;

         ArrayList var7;
         try {
            OutputStream var4 = this._request("checkClusterMembership", true);
            var4.write_longlong(var1);
            var3 = (InputStream)this._invoke(var4);
            ArrayList var5 = (ArrayList)var3.read_value(class$java$util$ArrayList == null ? (class$java$util$ArrayList = class$("java.util.ArrayList")) : class$java$util$ArrayList);
            return var5;
         } catch (ApplicationException var14) {
            var3 = (InputStream)var14.getInputStream();
            String var6 = var3.read_string();
            throw new UnexpectedException(var6);
         } catch (RemarshalException var15) {
            var7 = this.checkClusterMembership(var1);
         } finally {
            this._releaseReply(var3);
         }

         return var7;
      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
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
}

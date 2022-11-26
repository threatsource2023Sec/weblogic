package weblogic.cluster.messaging.internal;

import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class _ClusterMessageEndPoint_Stub extends Stub implements ClusterMessageEndPoint {
   private static String[] _type_ids = new String[]{"RMI:weblogic.cluster.messaging.internal.ClusterMessageEndPoint:0000000000000000"};
   // $FF: synthetic field
   private static Class class$weblogic$cluster$messaging$internal$ClusterMessage;

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

   public final ClusterResponse process(ClusterMessage var1) throws RemoteException {
      try {
         InputStream var2 = null;

         ClusterResponse var6;
         try {
            OutputStream var3 = (OutputStream)this._request("process", true);
            var3.write_value(var1, class$weblogic$cluster$messaging$internal$ClusterMessage == null ? (class$weblogic$cluster$messaging$internal$ClusterMessage = class$("weblogic.cluster.messaging.internal.ClusterMessage")) : class$weblogic$cluster$messaging$internal$ClusterMessage);
            var2 = (InputStream)this._invoke(var3);
            ClusterResponse var4 = (ClusterResponse)var2.read_abstract_interface();
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.process(var1);
         } finally {
            this._releaseReply(var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final void processOneWay(ClusterMessage var1) throws RemoteException {
      try {
         InputStream var2 = null;

         try {
            OutputStream var3 = (OutputStream)this._request("processOneWay", true);
            var3.write_value(var1, class$weblogic$cluster$messaging$internal$ClusterMessage == null ? (class$weblogic$cluster$messaging$internal$ClusterMessage = class$("weblogic.cluster.messaging.internal.ClusterMessage")) : class$weblogic$cluster$messaging$internal$ClusterMessage);
            this._invoke(var3);
         } catch (ApplicationException var11) {
            var2 = (InputStream)var11.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var12) {
            this.processOneWay(var1);
         } finally {
            this._releaseReply(var2);
         }

      } catch (SystemException var14) {
         throw Util.mapSystemException(var14);
      }
   }
}
